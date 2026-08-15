package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ApplicationItem
import com.example.data.model.ApplicationStatus
import com.example.data.model.ChatMessage
import com.example.data.model.Company
import com.example.data.model.EmploymentType
import com.example.data.model.ExperienceLevel
import com.example.data.model.Job
import com.example.data.model.JobMatchScore
import com.example.data.model.LearningResource
import com.example.data.model.NotificationItem
import com.example.data.model.RemoteOption
import com.example.data.model.ResumeAnalysisResult
import com.example.data.model.SavedJobItem
import com.example.data.model.SkillCategory
import com.example.data.model.SkillProficiency
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.data.repository.CareerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class JobFilterState(
    val searchQuery: String = "",
    val selectedExperience: ExperienceLevel? = null,
    val selectedEmploymentType: EmploymentType? = null,
    val selectedRemote: RemoteOption? = null,
    val selectedSkill: String? = null,
    val sortBy: String = "Match Score" // "Match Score", "Newest", "Salary"
)

class CareerViewModel(
    private val repository: CareerRepository = CareerRepository()
) : ViewModel() {

    val currentUser: StateFlow<UserProfile> = repository.currentUser
    val jobs: StateFlow<List<Job>> = repository.jobs
    val companies: StateFlow<List<Company>> = repository.companies
    val applications: StateFlow<List<ApplicationItem>> = repository.applications
    val savedJobs: StateFlow<List<SavedJobItem>> = repository.savedJobs
    val learningResources: StateFlow<List<LearningResource>> = repository.learningResources
    val notifications: StateFlow<List<NotificationItem>> = repository.notifications
    val lastResumeAnalysis: StateFlow<ResumeAnalysisResult?> = repository.lastResumeAnalysis
    val chatMessages: StateFlow<List<ChatMessage>> = repository.chatMessages

    private val _filterState = MutableStateFlow(JobFilterState())
    val filterState: StateFlow<JobFilterState> = _filterState.asStateFlow()

    private val _selectedJobId = MutableStateFlow<String?>("job_1")
    val selectedJobId: StateFlow<String?> = _selectedJobId.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    private val _isAnalyzingResume = MutableStateFlow(false)
    val isAnalyzingResume: StateFlow<Boolean> = _isAnalyzingResume.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Combined filtered jobs with real-time match scores
    val filteredJobsWithScores: StateFlow<List<Pair<Job, JobMatchScore>>> = combine(
        repository.jobs,
        repository.currentUser,
        _filterState
    ) { jobsList, user, filter ->
        val activeJobs = jobsList.filter { it.isActive }
        val scored = activeJobs.map { job ->
            Pair(job, repository.getJobMatchScore(job.id))
        }

        val filtered = scored.filter { (job, _) ->
            val matchQuery = filter.searchQuery.isBlank() ||
                job.title.contains(filter.searchQuery, ignoreCase = true) ||
                job.companyName.contains(filter.searchQuery, ignoreCase = true) ||
                job.location.contains(filter.searchQuery, ignoreCase = true) ||
                job.requiredSkills.any { it.contains(filter.searchQuery, ignoreCase = true) }

            val matchExp = filter.selectedExperience == null || job.experienceLevel == filter.selectedExperience
            val matchEmp = filter.selectedEmploymentType == null || job.employmentType == filter.selectedEmploymentType
            val matchRemote = filter.selectedRemote == null || job.remoteOption == filter.selectedRemote
            val matchSkill = filter.selectedSkill == null || job.requiredSkills.any { it.equals(filter.selectedSkill, true) }

            matchQuery && matchExp && matchEmp && matchRemote && matchSkill
        }

        when (filter.sortBy) {
            "Newest" -> filtered
            "Salary" -> filtered.sortedByDescending { it.first.salaryMax }
            else -> filtered.sortedByDescending { it.second.overallScore }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun updateSearchQuery(query: String) {
        _filterState.value = _filterState.value.copy(searchQuery = query)
    }

    fun setExperienceFilter(level: ExperienceLevel?) {
        _filterState.value = _filterState.value.copy(
            selectedExperience = if (_filterState.value.selectedExperience == level) null else level
        )
    }

    fun setEmploymentTypeFilter(type: EmploymentType?) {
        _filterState.value = _filterState.value.copy(
            selectedEmploymentType = if (_filterState.value.selectedEmploymentType == type) null else type
        )
    }

    fun setRemoteFilter(remote: RemoteOption?) {
        _filterState.value = _filterState.value.copy(
            selectedRemote = if (_filterState.value.selectedRemote == remote) null else remote
        )
    }

    fun setSkillFilter(skill: String?) {
        _filterState.value = _filterState.value.copy(
            selectedSkill = if (_filterState.value.selectedSkill == skill) null else skill
        )
    }

    fun setSortBy(sortBy: String) {
        _filterState.value = _filterState.value.copy(sortBy = sortBy)
    }

    fun clearFilters() {
        _filterState.value = JobFilterState()
    }

    fun selectJob(jobId: String) {
        _selectedJobId.value = jobId
    }

    fun isJobSaved(jobId: String): Boolean = repository.isJobSaved(jobId)

    fun toggleSaveJob(jobId: String) {
        repository.toggleSaveJob(jobId)
        val isNowSaved = repository.isJobSaved(jobId)
        _toastMessage.value = if (isNowSaved) "Job saved to your bookmarks" else "Job removed from saved"
    }

    fun applyForJob(jobId: String, notes: String = "") {
        repository.applyForJob(jobId, notes)
        _toastMessage.value = "Application submitted successfully! 🚀"
    }

    fun updateApplicationStatus(appId: String, status: ApplicationStatus, notes: String? = null, interviewDate: String? = null) {
        repository.updateApplicationStatus(appId, status, notes, interviewDate)
        _toastMessage.value = "Application moved to ${status.label}"
    }

    fun deleteApplication(appId: String) {
        repository.deleteApplication(appId)
        _toastMessage.value = "Application removed"
    }

    fun analyzeResume(rawText: String, fileName: String = "Resume.pdf") {
        viewModelScope.launch {
            _isAnalyzingResume.value = true
            try {
                repository.analyzeResume(rawText, fileName)
                _toastMessage.value = "Resume analyzed! Profile updated with detected skills."
            } finally {
                _isAnalyzingResume.value = false
            }
        }
    }

    fun addSkill(name: String, category: SkillCategory, proficiency: SkillProficiency) {
        repository.addSkill(name, category, proficiency)
        _toastMessage.value = "Added $name to your skills"
    }

    fun removeSkill(name: String) {
        repository.removeSkill(name)
    }

    fun markSkillLearned(skillName: String) {
        repository.markSkillLearned(skillName)
        _toastMessage.value = "🎉 Great job! Mastered $skillName. Match scores updated."
    }

    fun toggleLearningEnrollment(resourceId: String) {
        repository.toggleLearningEnrollment(resourceId)
    }

    fun updateLearningProgress(resourceId: String, progress: Int) {
        repository.updateLearningProgress(resourceId, progress)
    }

    fun markNotificationRead(id: String) {
        repository.markNotificationRead(id)
    }

    fun markAllNotificationsRead() {
        repository.markAllNotificationsRead()
    }

    fun switchUserRole(role: UserRole) {
        repository.switchUserRole(role)
        _toastMessage.value = "Switched to ${role.name} view"
    }

    fun updateUserProfile(profile: UserProfile) {
        repository.updateUserProfile(profile)
        _toastMessage.value = "Profile updated successfully"
    }

    fun createAdminJob(
        title: String,
        companyName: String,
        location: String,
        employmentType: EmploymentType,
        experienceLevel: ExperienceLevel,
        salaryMin: Int,
        salaryMax: Int,
        remoteOption: RemoteOption,
        description: String,
        requiredSkills: List<String>,
        preferredSkills: List<String>,
        educationReq: String
    ) {
        repository.createJob(
            title, companyName, location, employmentType, experienceLevel,
            salaryMin, salaryMax, remoteOption, description, requiredSkills, preferredSkills, educationReq
        )
        _toastMessage.value = "New job posted successfully!"
    }

    fun deleteAdminJob(jobId: String) {
        repository.deleteJob(jobId)
        _toastMessage.value = "Job deleted"
    }

    fun toggleJobActive(jobId: String) {
        repository.toggleJobActive(jobId)
    }

    fun sendChatMessage(text: String, targetJobId: String? = null) {
        if (text.isBlank()) return
        viewModelScope.launch {
            _isChatLoading.value = true
            try {
                repository.sendChatMessage(text, targetJobId ?: _selectedJobId.value)
            } finally {
                _isChatLoading.value = false
            }
        }
    }

    fun clearToast() {
        _toastMessage.value = null
    }

    fun getJobMatchScore(jobId: String): JobMatchScore {
        return repository.getJobMatchScore(jobId)
    }
}

package com.example.data.repository

import com.example.ai.GeminiCareerService
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
import com.example.data.model.NotificationType
import com.example.data.model.RemoteOption
import com.example.data.model.ResumeAnalysisResult
import com.example.data.model.SavedJobItem
import com.example.data.model.SkillCategory
import com.example.data.model.SkillItem
import com.example.data.model.SkillProficiency
import com.example.data.model.UserProfile
import com.example.data.model.UserRole
import com.example.ml.MatchingEngine
import com.example.ml.ResumeParserEngine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class CareerRepository {

    private val _currentUser = MutableStateFlow(InitialData.sampleUser)
    val currentUser: StateFlow<UserProfile> = _currentUser.asStateFlow()

    private val _jobs = MutableStateFlow(InitialData.sampleJobs)
    val jobs: StateFlow<List<Job>> = _jobs.asStateFlow()

    private val _companies = MutableStateFlow(InitialData.sampleCompanies)
    val companies: StateFlow<List<Company>> = _companies.asStateFlow()

    private val _applications = MutableStateFlow(InitialData.sampleApplications)
    val applications: StateFlow<List<ApplicationItem>> = _applications.asStateFlow()

    private val _savedJobs = MutableStateFlow(InitialData.sampleSavedJobs)
    val savedJobs: StateFlow<List<SavedJobItem>> = _savedJobs.asStateFlow()

    private val _learningResources = MutableStateFlow(InitialData.sampleLearningResources)
    val learningResources: StateFlow<List<LearningResource>> = _learningResources.asStateFlow()

    private val _notifications = MutableStateFlow(InitialData.sampleNotifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    private val _lastResumeAnalysis = MutableStateFlow<ResumeAnalysisResult?>(
        ResumeAnalysisResult(
            id = "analysis_sample_init",
            candidateName = "Alex Rivera",
            email = "alex.rivera@example.com",
            phone = "+1 (555) 234-5678",
            overallScore = 88,
            skillsScore = 90,
            experienceScore = 88,
            educationScore = 90,
            projectsScore = 86,
            certificationsScore = 85,
            detectedSkills = listOf("Python", "PyTorch", "SQL", "PostgreSQL", "Docker", "Kubernetes", "AWS", "Machine Learning", "Git", "REST APIs", "FastAPI"),
            candidateSummary = "Experienced Software Engineer & ML Specialist with 3.5+ years building and deploying scalable predictive models, distributed systems, and real-time backend microservices.",
            strengths = listOf(
                "Strong foundational ML/AI stack (PyTorch, Machine Learning, Python)",
                "Solid containerization and cloud orchestration background (Docker, Kubernetes, AWS)",
                "Proven production experience delivering real-time distributed pipelines"
            ),
            improvementAreas = listOf(
                "Quantify bullet points with exact percentage metrics and cost savings",
                "Highlight specific LLM evaluation benchmarks and fine-tuning projects",
                "Obtain AWS Solutions Architect Associate or CKA certification"
            ),
            recommendedRoles = listOf(
                "Machine Learning Engineer",
                "Senior AI Backend Engineer",
                "Cloud Solutions Architect"
            ),
            rawTextSnippet = "Alex Rivera • alex.rivera@example.com • +1 (555) 234-5678\nMachine Learning Engineer with 3+ years experience..."
        )
    )
    val lastResumeAnalysis: StateFlow<ResumeAnalysisResult?> = _lastResumeAnalysis.asStateFlow()

    private val _chatMessages = MutableStateFlow(
        listOf(
            ChatMessage(
                isFromUser = false,
                text = "Welcome to **CareerAI**! 👋\n\nI can analyze your resume, explain why you match target jobs, pinpoint missing skills with high-priority study paths, and prepare you for technical interviews.\n\nHow can I help you today?",
                suggestedActions = listOf(
                    "Why is my match score 84% for Anthropic?",
                    "How do I improve my resume bullets?",
                    "Give me technical interview questions",
                    "4-week Kubernetes & Cloud roadmap"
                )
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    // --- User Profile Actions ---

    fun updateUserProfile(updated: UserProfile) {
        _currentUser.value = updated
    }

    fun switchUserRole(role: UserRole) {
        val base = when (role) {
            UserRole.USER -> InitialData.sampleUser
            UserRole.ADMIN -> InitialData.sampleAdminUser
            UserRole.RECRUITER -> InitialData.sampleRecruiterUser
        }
        _currentUser.value = base
    }

    fun addSkill(name: String, category: SkillCategory = SkillCategory.PROGRAMMING, proficiency: SkillProficiency = SkillProficiency.INTERMEDIATE) {
        val currentSkills = _currentUser.value.skills.toMutableList()
        if (currentSkills.none { it.name.equals(name, ignoreCase = true) }) {
            currentSkills.add(SkillItem(name = name, category = category, proficiency = proficiency))
            _currentUser.value = _currentUser.value.copy(skills = currentSkills)
        }
    }

    fun removeSkill(name: String) {
        val currentSkills = _currentUser.value.skills.filterNot { it.name.equals(name, ignoreCase = true) }
        _currentUser.value = _currentUser.value.copy(skills = currentSkills)
    }

    fun markSkillLearned(skillName: String) {
        addSkill(skillName, SkillCategory.CLOUD_DEVOPS, SkillProficiency.INTERMEDIATE)
        // Mark corresponding learning path as completed
        val updatedLR = _learningResources.value.map {
            if (it.skillName.equals(skillName, ignoreCase = true)) {
                it.copy(isCompleted = true, progressPercent = 100)
            } else it
        }
        _learningResources.value = updatedLR

        // Add notification
        val notif = NotificationItem(
            title = "🎉 Skill Mastered: $skillName",
            message = "Your match scores across engineering jobs increased! Added $skillName to your verified profile.",
            timestamp = "Just now",
            type = NotificationType.LEARNING
        )
        _notifications.value = listOf(notif) + _notifications.value
    }

    // --- Job Matching & Explainability ---

    fun getJobMatchScore(jobId: String): JobMatchScore {
        val job = _jobs.value.firstOrNull { it.id == jobId } ?: _jobs.value.first()
        return MatchingEngine.calculateJobMatch(_currentUser.value, job)
    }

    fun getAllJobMatches(): List<Pair<Job, JobMatchScore>> {
        val user = _currentUser.value
        return _jobs.value
            .filter { it.isActive }
            .map { job -> Pair(job, MatchingEngine.calculateJobMatch(user, job)) }
            .sortedByDescending { it.second.overallScore }
    }

    // --- Saved Jobs ---

    fun isJobSaved(jobId: String): Boolean {
        return _savedJobs.value.any { it.jobId == jobId }
    }

    fun toggleSaveJob(jobId: String) {
        val current = _savedJobs.value.toMutableList()
        val existing = current.firstOrNull { it.jobId == jobId }
        if (existing != null) {
            current.remove(existing)
        } else {
            current.add(0, SavedJobItem(jobId = jobId, savedDate = "Just now"))
        }
        _savedJobs.value = current
    }

    // --- Application Tracking ---

    fun applyForJob(jobId: String, notes: String = "") {
        val job = _jobs.value.firstOrNull { it.id == jobId } ?: return
        val current = _applications.value.toMutableList()
        if (current.none { it.jobId == jobId }) {
            val dateStr = SimpleDateFormat("MMM d, yyyy", Locale.US).format(Date())
            val newApp = ApplicationItem(
                jobId = jobId,
                jobTitle = job.title,
                companyName = job.companyName,
                location = job.location,
                salaryRange = "$${job.salaryMin / 1000}K - $${job.salaryMax / 1000}K",
                status = ApplicationStatus.APPLIED,
                appliedDate = dateStr,
                notes = if (notes.isNotBlank()) notes else "Applied via CareerAI 1-Click application."
            )
            current.add(0, newApp)
            _applications.value = current

            // Add notification
            val notif = NotificationItem(
                title = "🚀 Application Submitted",
                message = "Successfully submitted application for ${job.title} at ${job.companyName}.",
                timestamp = "Just now",
                type = NotificationType.APPLICATION
            )
            _notifications.value = listOf(notif) + _notifications.value
        }
    }

    fun updateApplicationStatus(
        applicationId: String,
        status: ApplicationStatus,
        notes: String? = null,
        interviewDate: String? = null
    ) {
        _applications.value = _applications.value.map { app ->
            if (app.id == applicationId) {
                app.copy(
                    status = status,
                    notes = notes ?: app.notes,
                    interviewDate = interviewDate ?: app.interviewDate
                )
            } else app
        }
    }

    fun deleteApplication(applicationId: String) {
        _applications.value = _applications.value.filterNot { it.id == applicationId }
    }

    // --- Resume Parsing & Analysis ---

    fun analyzeResume(rawText: String, fileName: String = "Resume.pdf"): ResumeAnalysisResult {
        val result = ResumeParserEngine.parseResumeText(rawText, fileName)
        _lastResumeAnalysis.value = result

        // Proactively merge extracted skills into profile
        val currentSkillNames = _currentUser.value.skills.map { it.name.lowercase() }
        val newSkills = _currentUser.value.skills.toMutableList()
        result.detectedSkills.forEach { s ->
            if (!currentSkillNames.contains(s.lowercase())) {
                newSkills.add(SkillItem(name = s, category = SkillCategory.AI_ML, proficiency = SkillProficiency.INTERMEDIATE))
            }
        }

        _currentUser.value = _currentUser.value.copy(
            resumeUploaded = true,
            resumeScore = result.overallScore,
            skills = newSkills
        )

        // Notification
        val notif = NotificationItem(
            title = "📊 Resume Analyzed (${result.overallScore}/100)",
            message = "Identified ${result.detectedSkills.size} skills and updated your profile match rankings.",
            timestamp = "Just now",
            type = NotificationType.RESUME
        )
        _notifications.value = listOf(notif) + _notifications.value

        return result
    }

    // --- Learning Paths ---

    fun toggleLearningEnrollment(resourceId: String) {
        _learningResources.value = _learningResources.value.map { res ->
            if (res.id == resourceId) {
                res.copy(isEnrolled = !res.isEnrolled)
            } else res
        }
    }

    fun updateLearningProgress(resourceId: String, progress: Int) {
        _learningResources.value = _learningResources.value.map { res ->
            if (res.id == resourceId) {
                val isDone = progress >= 100
                res.copy(progressPercent = progress.coerceIn(0, 100), isCompleted = isDone)
            } else res
        }
    }

    // --- Notifications ---

    fun markNotificationRead(id: String) {
        _notifications.value = _notifications.value.map {
            if (it.id == id) it.copy(isRead = true) else it
        }
    }

    fun markAllNotificationsRead() {
        _notifications.value = _notifications.value.map { it.copy(isRead = true) }
    }

    // --- Admin Operations ---

    fun createJob(
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
        val newJob = Job(
            id = "job_" + UUID.randomUUID().toString().take(8),
            title = title,
            companyId = "c_custom",
            companyName = companyName,
            companyLogoText = companyName.take(2).uppercase(),
            location = location,
            employmentType = employmentType,
            experienceLevel = experienceLevel,
            salaryMin = salaryMin,
            salaryMax = salaryMax,
            remoteOption = remoteOption,
            description = description,
            requiredSkills = requiredSkills,
            preferredSkills = preferredSkills,
            educationRequirement = educationReq,
            createdAt = "Just now",
            applicantsCount = 0
        )
        _jobs.value = listOf(newJob) + _jobs.value
    }

    fun deleteJob(jobId: String) {
        _jobs.value = _jobs.value.filterNot { it.id == jobId }
    }

    fun toggleJobActive(jobId: String) {
        _jobs.value = _jobs.value.map {
            if (it.id == jobId) it.copy(isActive = !it.isActive) else it
        }
    }

    // --- AI Chat Assistant ---

    suspend fun sendChatMessage(userText: String, targetJobId: String? = null) {
        val current = _chatMessages.value.toMutableList()
        val userMsg = ChatMessage(
            isFromUser = true,
            text = userText,
            relatedJobId = targetJobId
        )
        current.add(userMsg)
        _chatMessages.value = current

        val targetJob = _jobs.value.firstOrNull { it.id == targetJobId }
        val history = current.map { Pair(it.text, it.isFromUser) }

        val aiReply = GeminiCareerService.askCareerAssistant(
            userPrompt = userText,
            user = _currentUser.value,
            targetJob = targetJob,
            chatHistory = history
        )

        val aiMsg = ChatMessage(
            isFromUser = false,
            text = aiReply,
            relatedJobId = targetJobId,
            suggestedActions = when {
                userText.contains("interview", true) -> listOf("Give me 3 more technical questions", "How should I structure my answer?", "What are red flags to avoid?")
                userText.contains("resume", true) -> listOf("Review my summary section", "Suggest metrics for ML models", "How to list certifications?")
                else -> listOf("Show top jobs for my profile", "How to bridge my Docker skill gap", "Mock salary negotiation")
            }
        )

        _chatMessages.value = _chatMessages.value + aiMsg
    }
}

package com.example.data.model

import java.util.UUID

enum class UserRole {
    USER,
    ADMIN,
    RECRUITER
}

enum class EmploymentType(val label: String) {
    FULL_TIME("Full-time"),
    PART_TIME("Part-time"),
    INTERNSHIP("Internship"),
    CONTRACT("Contract")
}

enum class ExperienceLevel(val label: String) {
    FRESHER("Fresher (0-1 yr)"),
    ENTRY_LEVEL("Entry-level (1-3 yrs)"),
    MID_LEVEL("Mid-level (3-5 yrs)"),
    SENIOR("Senior (5+ yrs)")
}

enum class RemoteOption(val label: String) {
    REMOTE("Remote"),
    HYBRID("Hybrid"),
    ON_SITE("On-site")
}

enum class ApplicationStatus(val label: String, val colorHex: String) {
    SAVED("Saved", "#64748B"),
    APPLIED("Applied", "#3B82F6"),
    UNDER_REVIEW("Under Review", "#8B5CF6"),
    SHORTLISTED("Shortlisted", "#EC4899"),
    INTERVIEW("Interview", "#F59E0B"),
    REJECTED("Rejected", "#EF4444"),
    OFFER("Offer Received", "#10B981")
}

enum class SkillCategory(val label: String) {
    PROGRAMMING("Programming Languages"),
    FRAMEWORKS("Frameworks & Libraries"),
    AI_ML("AI & Machine Learning"),
    DATABASES("Databases & Storage"),
    CLOUD_DEVOPS("Cloud & DevOps"),
    TOOLS("Tools & Platforms"),
    SOFT_SKILLS("Soft Skills")
}

enum class SkillProficiency(val label: String, val level: Int) {
    BEGINNER("Beginner", 1),
    INTERMEDIATE("Intermediate", 2),
    ADVANCED("Advanced", 3),
    EXPERT("Expert", 4)
}

data class SkillItem(
    val name: String,
    val category: SkillCategory = SkillCategory.PROGRAMMING,
    val proficiency: SkillProficiency = SkillProficiency.INTERMEDIATE,
    val isVerified: Boolean = true
)

data class EducationItem(
    val id: String = UUID.randomUUID().toString(),
    val degree: String,
    val institution: String,
    val field: String,
    val startYear: String,
    val endYear: String,
    val gpa: String = ""
)

data class CertificationItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val issuer: String,
    val date: String,
    val credentialUrl: String = ""
)

data class ProjectItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String,
    val techStack: List<String> = emptyList(),
    val githubUrl: String = "",
    val demoUrl: String = ""
)

data class UserProfile(
    val id: String = "user_1",
    val fullName: String = "Alex Rivera",
    val email: String = "alex.rivera@example.com",
    val phone: String = "+1 (555) 234-5678",
    val location: String = "San Francisco, CA",
    val avatarUrl: String = "",
    val role: UserRole = UserRole.USER,
    val careerObjective: String = "Passionate Data Scientist & Machine Learning Engineer with 3+ years building scalable predictive models, NLP pipelines, and cloud-native AI applications.",
    val yearsOfExperience: Double = 3.5,
    val preferredRole: String = "Machine Learning Engineer",
    val preferredLocation: String = "San Francisco, CA / Remote",
    val remotePreference: RemoteOption = RemoteOption.REMOTE,
    val expectedSalaryMin: Int = 120000,
    val expectedSalaryMax: Int = 150000,
    val skills: List<SkillItem> = emptyList(),
    val education: List<EducationItem> = emptyList(),
    val certifications: List<CertificationItem> = emptyList(),
    val projects: List<ProjectItem> = emptyList(),
    val resumeUploaded: Boolean = true,
    val resumeScore: Int = 84
)

data class Company(
    val id: String,
    val name: String,
    val logoText: String = "",
    val industry: String,
    val description: String,
    val website: String,
    val location: String,
    val companySize: String = "500-1000 employees"
)

data class Job(
    val id: String,
    val title: String,
    val companyId: String,
    val companyName: String,
    val companyLogoText: String = "",
    val location: String,
    val employmentType: EmploymentType,
    val experienceLevel: ExperienceLevel,
    val salaryMin: Int,
    val salaryMax: Int,
    val remoteOption: RemoteOption,
    val description: String,
    val requiredSkills: List<String>,
    val preferredSkills: List<String>,
    val educationRequirement: String,
    val deadline: String = "30 days left",
    val createdAt: String = "2 days ago",
    val isActive: Boolean = true,
    val applicantsCount: Int = 24
)

data class JobMatchScore(
    val jobId: String,
    val overallScore: Int,
    val skillScore: Int,
    val semanticScore: Int,
    val experienceScore: Int,
    val educationScore: Int,
    val locationScore: Int,
    val matchedSkills: List<String>,
    val missingSkills: List<String>,
    val missingSkillPriority: Map<String, String>, // Skill -> "High" | "Medium" | "Low"
    val matchSummary: String
)

data class ResumeAnalysisResult(
    val id: String = UUID.randomUUID().toString(),
    val candidateName: String,
    val email: String,
    val phone: String,
    val overallScore: Int,
    val skillsScore: Int,
    val experienceScore: Int,
    val educationScore: Int,
    val projectsScore: Int,
    val certificationsScore: Int,
    val detectedSkills: List<String>,
    val candidateSummary: String,
    val strengths: List<String>,
    val improvementAreas: List<String>,
    val recommendedRoles: List<String>,
    val rawTextSnippet: String
)

data class ApplicationItem(
    val id: String = UUID.randomUUID().toString(),
    val jobId: String,
    val jobTitle: String,
    val companyName: String,
    val location: String,
    val salaryRange: String,
    val status: ApplicationStatus = ApplicationStatus.APPLIED,
    val appliedDate: String,
    val interviewDate: String = "",
    val notes: String = "",
    val salaryOffer: String = "",
    val contactPerson: String = ""
)

data class SavedJobItem(
    val id: String = UUID.randomUUID().toString(),
    val jobId: String,
    val savedDate: String,
    val notes: String = ""
)

data class LearningResource(
    val id: String = UUID.randomUUID().toString(),
    val skillName: String,
    val title: String,
    val category: String,
    val difficulty: String = "Intermediate",
    val estimatedHours: Int = 12,
    val provider: String = "CareerAI Academy",
    val topics: List<String> = emptyList(),
    val isEnrolled: Boolean = false,
    val isCompleted: Boolean = false,
    val progressPercent: Int = 0
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val isFromUser: Boolean,
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val suggestedActions: List<String> = emptyList(),
    val relatedJobId: String? = null
)

enum class NotificationType {
    RESUME,
    MATCH,
    APPLICATION,
    INTERVIEW,
    LEARNING,
    SYSTEM
}

data class NotificationItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val message: String,
    val timestamp: String,
    val type: NotificationType,
    val isRead: Boolean = false,
    val targetRoute: String? = null
)

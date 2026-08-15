package com.example.ml

import com.example.data.model.EducationItem
import com.example.data.model.ExperienceLevel
import com.example.data.model.Job
import com.example.data.model.JobMatchScore
import com.example.data.model.RemoteOption
import com.example.data.model.UserProfile
import kotlin.math.sqrt

object MatchingEngine {

    private val skillAliases = mapOf(
        "postgres" to "postgresql",
        "postgres db" to "postgresql",
        "ml" to "machine learning",
        "dl" to "deep learning",
        "nlp" to "natural language processing",
        "cv" to "computer vision",
        "k8s" to "kubernetes",
        "reactjs" to "react",
        "react.js" to "react",
        "nodejs" to "node.js",
        "node" to "node.js",
        "ts" to "typescript",
        "js" to "javascript",
        "py" to "python",
        "aws" to "amazon web services",
        "gcp" to "google cloud",
        "azure" to "microsoft azure",
        "sklearn" to "scikit-learn",
        "torch" to "pytorch",
        "tf" to "tensorflow",
        "ci/cd" to "devops",
        "cicd" to "devops",
        "genai" to "generative ai",
        "llm" to "large language models",
        "rag" to "retrieval augmented generation"
    )

    private fun normalizeSkill(skill: String): String {
        val clean = skill.trim().lowercase()
        return skillAliases[clean] ?: clean
    }

    private fun areSkillsEquivalent(s1: String, s2: String): Boolean {
        val norm1 = normalizeSkill(s1)
        val norm2 = normalizeSkill(s2)
        if (norm1 == norm2) return true
        if (norm1.contains(norm2) || norm2.contains(norm1)) return true
        return false
    }

    /**
     * Compute Cosine Similarity between user profile text representation and job description
     */
    fun computeSemanticSimilarity(profileText: String, jobText: String): Double {
        val tokenize = { text: String ->
            text.lowercase()
                .replace(Regex("[^a-z0-9#+ ]"), " ")
                .split(Regex("\\s+"))
                .filter { it.length > 2 && it !in STOP_WORDS }
        }

        val tokens1 = tokenize(profileText)
        val tokens2 = tokenize(jobText)

        if (tokens1.isEmpty() || tokens2.isEmpty()) return 0.50

        val freq1 = tokens1.groupingBy { it }.eachCount()
        val freq2 = tokens2.groupingBy { it }.eachCount()

        val allWords = (freq1.keys + freq2.keys).toSet()

        var dotProduct = 0.0
        var normA = 0.0
        var normB = 0.0

        for (word in allWords) {
            val v1 = freq1[word]?.toDouble() ?: 0.0
            val v2 = freq2[word]?.toDouble() ?: 0.0
            dotProduct += v1 * v2
            normA += v1 * v1
            normB += v2 * v2
        }

        if (normA == 0.0 || normB == 0.0) return 0.50

        val cosine = dotProduct / (sqrt(normA) * sqrt(normB))
        // Scale and smooth into a realistic 0.30 - 0.98 similarity range
        return (cosine * 1.35).coerceIn(0.20, 0.98)
    }

    /**
     * Main Explainable Weighted Recommendation Calculator:
     * - Skill Match: 40%
     * - Semantic Similarity: 30%
     * - Experience Match: 15%
     * - Education Match: 10%
     * - Location / Remote Match: 5%
     */
    fun calculateJobMatch(user: UserProfile, job: Job): JobMatchScore {
        val userSkillNames = user.skills.map { it.name }
        val requiredSkills = job.requiredSkills
        val preferredSkills = job.preferredSkills

        val matchedRequired = mutableListOf<String>()
        val missingRequired = mutableListOf<String>()

        for (req in requiredSkills) {
            val isMatched = userSkillNames.any { areSkillsEquivalent(it, req) }
            if (isMatched) {
                matchedRequired.add(req)
            } else {
                missingRequired.add(req)
            }
        }

        val matchedPreferred = mutableListOf<String>()
        val missingPreferred = mutableListOf<String>()

        for (pref in preferredSkills) {
            val isMatched = userSkillNames.any { areSkillsEquivalent(it, pref) }
            if (isMatched) {
                matchedPreferred.add(pref)
            } else {
                missingPreferred.add(pref)
            }
        }

        val allMatched = (matchedRequired + matchedPreferred).distinct()
        val allMissing = (missingRequired + missingPreferred).distinct()

        // 1. Skill Score (40%)
        val totalReqWeight = if (requiredSkills.isNotEmpty()) (matchedRequired.size.toDouble() / requiredSkills.size.toDouble()) else 1.0
        val totalPrefWeight = if (preferredSkills.isNotEmpty()) (matchedPreferred.size.toDouble() / preferredSkills.size.toDouble()) else 1.0
        val skillScore = ((totalReqWeight * 85.0 + totalPrefWeight * 15.0)).coerceIn(15.0, 100.0).toInt()

        // 2. Semantic Similarity Score (30%)
        val profileCombinedText = buildString {
            append(user.careerObjective).append(" ")
            append(user.preferredRole).append(" ")
            user.skills.forEach { append(it.name).append(" ") }
            user.projects.forEach { append(it.name).append(" ").append(it.description).append(" ") }
            user.education.forEach { append(it.degree).append(" ").append(it.field).append(" ") }
        }
        val jobCombinedText = buildString {
            append(job.title).append(" ")
            append(job.description).append(" ")
            job.requiredSkills.forEach { append(it).append(" ") }
            job.preferredSkills.forEach { append(it).append(" ") }
        }
        val semanticSimilarity = computeSemanticSimilarity(profileCombinedText, jobCombinedText)
        val semanticScore = (semanticSimilarity * 100).toInt().coerceIn(25, 98)

        // 3. Experience Match Score (15%)
        val expYears = user.yearsOfExperience
        val expScore = when (job.experienceLevel) {
            ExperienceLevel.FRESHER -> if (expYears <= 2.0) 95 else 85
            ExperienceLevel.ENTRY_LEVEL -> if (expYears in 0.5..3.0) 95 else if (expYears > 3.0) 90 else 70
            ExperienceLevel.MID_LEVEL -> if (expYears in 2.5..6.0) 95 else if (expYears > 6.0) 92 else (expYears / 3.0 * 80).toInt()
            ExperienceLevel.SENIOR -> if (expYears >= 5.0) 95 else if (expYears >= 3.0) 75 else 50
        }.coerceIn(30, 100)

        // 4. Education Match Score (10%)
        val eduScore = calculateEducationScore(user.education, job.educationRequirement)

        // 5. Location / Remote Preference Score (5%)
        val locScore = when {
            job.remoteOption == RemoteOption.REMOTE -> 98
            user.remotePreference == job.remoteOption -> 95
            user.location.contains(job.location, ignoreCase = true) || job.location.contains(user.location, ignoreCase = true) -> 90
            else -> 60
        }

        // Weighted Final Calculation
        val finalScore = (
            skillScore * 0.40 +
            semanticScore * 0.30 +
            expScore * 0.15 +
            eduScore * 0.10 +
            locScore * 0.05
        ).toInt().coerceIn(10, 99)

        // Skill Priorities
        val missingPriorities = mutableMapOf<String, String>()
        for (m in missingRequired) {
            missingPriorities[m] = "High"
        }
        for (p in missingPreferred) {
            missingPriorities[p] = "Medium"
        }

        val summary = when {
            finalScore >= 85 -> "Outstanding Match! Your technical skills and experience strongly align with ${job.companyName}'s core requirements."
            finalScore >= 70 -> "Strong Fit! You possess most key competencies. Bridging 1-2 skill gaps will make you a top candidate."
            finalScore >= 50 -> "Moderate Fit. Good baseline background, but you will benefit from completing recommended learning paths for missing skills."
            else -> "Aspirational Match. Requires upskilling in several core frameworks before applying."
        }

        return JobMatchScore(
            jobId = job.id,
            overallScore = finalScore,
            skillScore = skillScore,
            semanticScore = semanticScore,
            experienceScore = expScore,
            educationScore = eduScore,
            locationScore = locScore,
            matchedSkills = allMatched,
            missingSkills = allMissing,
            missingSkillPriority = missingPriorities,
            matchSummary = summary
        )
    }

    private fun calculateEducationScore(educationList: List<EducationItem>, req: String): Int {
        if (educationList.isEmpty()) return 60
        val degrees = educationList.map { it.degree.lowercase() }
        val fields = educationList.map { it.field.lowercase() }

        val hasStem = fields.any { it.contains("computer") || it.contains("data") || it.contains("engineering") || it.contains("science") || it.contains("math") }
        val hasMasterOrPhD = degrees.any { it.contains("master") || it.contains("m.s") || it.contains("phd") }

        return when {
            req.contains("Master", ignoreCase = true) && hasMasterOrPhD -> 95
            req.contains("Master", ignoreCase = true) && !hasMasterOrPhD -> 75
            hasStem -> 92
            else -> 80
        }
    }

    private val STOP_WORDS = setOf(
        "the", "and", "a", "an", "in", "on", "at", "for", "with", "about", "as", "by", "to", "from",
        "is", "are", "was", "were", "be", "been", "being", "have", "has", "had", "do", "does", "did",
        "will", "would", "shall", "should", "can", "could", "may", "might", "must", "that", "this",
        "these", "those", "it", "its", "you", "your", "we", "our", "they", "their", "of", "or", "such"
    )
}

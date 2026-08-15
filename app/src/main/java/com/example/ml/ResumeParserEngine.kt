package com.example.ml

import com.example.data.model.ResumeAnalysisResult
import java.util.UUID

object ResumeParserEngine {

    private val KNOWN_SKILLS = listOf(
        "Python", "Java", "Kotlin", "TypeScript", "JavaScript", "C++", "Go", "Rust", "SQL",
        "PostgreSQL", "MySQL", "MongoDB", "Redis", "Elasticsearch", "Cassandra",
        "React", "Vue.js", "Angular", "Next.js", "Node.js", "Express", "FastAPI", "Django", "Spring Boot",
        "PyTorch", "TensorFlow", "Scikit-Learn", "Pandas", "NumPy", "Keras", "HuggingFace", "OpenCV",
        "NLP", "Large Language Models", "Computer Vision", "Deep Learning", "Machine Learning", "Generative AI",
        "Docker", "Kubernetes", "AWS", "Google Cloud", "Azure", "Terraform", "CI/CD", "Git", "Linux",
        "Apache Spark", "Kafka", "Tableau", "Power BI", "Snowflake", "dbt", "Airflow",
        "REST APIs", "GraphQL", "Microservices", "System Design", "Agile", "Scrum"
    )

    fun parseResumeText(rawText: String, fileName: String = "Uploaded_Resume.pdf"): ResumeAnalysisResult {
        val lowerText = rawText.lowercase()

        // 1. Detect candidate contact details
        val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
        val detectedEmail = emailRegex.find(rawText)?.value ?: "alex.rivera@example.com"

        val phoneRegex = Regex("(\\+?\\d{1,3}[-.\\s]?)?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{4}")
        val detectedPhone = phoneRegex.find(rawText)?.value ?: "+1 (555) 234-5678"

        // Candidate name heuristic: first non-empty line or fallback
        val candidateName = rawText.lines()
            .map { it.trim() }
            .firstOrNull { it.isNotEmpty() && !it.contains("@") && it.length in 3..40 }
            ?: "Alex Rivera"

        // 2. Identify skills present in text
        val detectedSkills = mutableListOf<String>()
        for (skill in KNOWN_SKILLS) {
            val skillRegex = Regex("\\b${Regex.escape(skill.lowercase())}\\b", RegexOption.IGNORE_CASE)
            if (skillRegex.containsMatchIn(lowerText)) {
                detectedSkills.add(skill)
            }
        }

        if (detectedSkills.isEmpty()) {
            detectedSkills.addAll(listOf("Python", "SQL", "Machine Learning", "Pandas", "Git", "Docker"))
        }

        // 3. Compute detailed score components
        val skillsScore = (detectedSkills.size * 5 + 40).coerceIn(40, 96)
        val hasEducation = lowerText.contains("bachelor") || lowerText.contains("master") || lowerText.contains("university") || lowerText.contains("degree") || lowerText.contains("b.s")
        val educationScore = if (hasEducation) 90 else 70

        val hasExperience = lowerText.contains("experience") || lowerText.contains("engineer") || lowerText.contains("developer") || lowerText.contains("worked")
        val experienceScore = if (hasExperience) 88 else 65

        val hasProjects = lowerText.contains("project") || lowerText.contains("built") || lowerText.contains("github") || lowerText.contains("developed")
        val projectsScore = if (hasProjects) 86 else 60

        val hasCerts = lowerText.contains("certified") || lowerText.contains("certification") || lowerText.contains("aws certified") || lowerText.contains("credential")
        val certificationsScore = if (hasCerts) 85 else 60

        val overallScore = (
            skillsScore * 0.35 +
            experienceScore * 0.25 +
            projectsScore * 0.15 +
            educationScore * 0.15 +
            certificationsScore * 0.10
        ).toInt().coerceIn(45, 96)

        // 4. Extract Strengths
        val strengths = mutableListOf<String>()
        if (detectedSkills.size >= 6) {
            strengths.add("Strong technical foundation with ${detectedSkills.size}+ detected industry skills")
        }
        if (hasProjects) {
            strengths.add("Demonstrated practical experience building end-to-end applications and projects")
        }
        if (lowerText.contains("lead") || lowerText.contains("managed") || lowerText.contains("mentored")) {
            strengths.add("Evidence of collaborative teamwork and technical initiative")
        } else {
            strengths.add("Solid clear career trajectory in software and data engineering")
        }

        // 5. Improvement Areas
        val improvementAreas = mutableListOf<String>()
        if (!lowerText.contains("aws") && !lowerText.contains("cloud") && !lowerText.contains("kubernetes")) {
            improvementAreas.add("Incorporate Cloud/DevOps competencies (e.g. AWS, Docker, Kubernetes) to maximize match rate for Senior positions")
        }
        if (!lowerText.contains("%") && !lowerText.contains("increased") && !lowerText.contains("reduced") && !lowerText.contains("optimized")) {
            improvementAreas.add("Add quantified impact metrics (e.g., 'reduced latency by 35%', 'processed 2M+ daily requests')")
        }
        if (certificationsScore < 70) {
            improvementAreas.add("Add recognized cloud or machine learning certifications to validate expertise")
        }
        if (improvementAreas.isEmpty()) {
            improvementAreas.add("Highlight system architecture and distributed systems contributions")
        }

        // 6. Recommended Roles
        val recommendedRoles = when {
            lowerText.contains("learning") || lowerText.contains("pytorch") || lowerText.contains("model") ->
                listOf("Machine Learning Engineer", "Data Scientist", "AI Engineer", "MLOps Engineer")
            lowerText.contains("react") || lowerText.contains("node") || lowerText.contains("full stack") ->
                listOf("Full Stack Developer", "Backend Engineer", "Software Engineer", "Frontend Specialist")
            else ->
                listOf("Software Engineer", "Backend Developer", "Data Analyst", "Cloud Engineer")
        }

        val summary = "Profile exhibits strong analytical and problem-solving capabilities with solid experience in ${detectedSkills.take(4).joinToString(", ")}. Recommended for Mid-to-Senior level opportunities."

        return ResumeAnalysisResult(
            id = UUID.randomUUID().toString(),
            candidateName = candidateName,
            email = detectedEmail,
            phone = detectedPhone,
            overallScore = overallScore,
            skillsScore = skillsScore,
            experienceScore = experienceScore,
            educationScore = educationScore,
            projectsScore = projectsScore,
            certificationsScore = certificationsScore,
            detectedSkills = detectedSkills,
            candidateSummary = summary,
            strengths = strengths,
            improvementAreas = improvementAreas,
            recommendedRoles = recommendedRoles,
            rawTextSnippet = rawText.take(500)
        )
    }

    val SAMPLE_RESUME_TEXT = """
        ALEX RIVERA
        alex.rivera@example.com | +1 (555) 234-5678 | San Francisco, CA | linkedin.com/in/alexrivera | github.com/alexrivera

        CAREER SUMMARY
        Innovative Data Scientist and Machine Learning Engineer with 3.5+ years of experience designing, training, and deploying high-performance predictive models and deep learning pipelines in production. Experienced with PyTorch, Scikit-Learn, Python, SQL, and FastAPI.

        TECHNICAL SKILLS
        • Programming: Python, SQL, Kotlin, TypeScript, JavaScript
        • Machine Learning & AI: PyTorch, TensorFlow, Scikit-Learn, Pandas, NumPy, HuggingFace, NLP, Generative AI
        • Backend & Storage: FastAPI, PostgreSQL, MongoDB, Redis, REST APIs, Microservices
        • DevOps & Tools: Docker, Git, CI/CD, Linux, Google Cloud

        PROFESSIONAL EXPERIENCE
        Senior Machine Learning Specialist | Nexus Data Intelligence (2023 - Present)
        • Architected and deployed scalable NLP recommendation pipeline serving 450,000+ daily active users, boosting user engagement by 28%.
        • Developed real-time sentiment analysis and categorization models using Transformer architectures, reducing latency by 42%.
        • Built automated ETL pipelines in Python and SQL to ingest 5TB+ streaming data.

        Data Analyst / ML Engineer | Apex Technologies (2021 - 2023)
        • Engineered churn prediction model achieving 0.91 AUC, saving an estimated $420K annually.
        • Collaborated with product teams to design interactive Tableau & Streamlit dashboards.

        EDUCATION
        • B.S. in Computer Science | University of California, Berkeley (2017 - 2021) - GPA: 3.84/4.0

        PROJECTS & CERTIFICATIONS
        • Autonomous Document RAG Assistant: Built multi-document question answering agent using LangChain, Vector Embeddings, and FastAPI.
        • Deep Vision Defect Detection: Computer vision model achieving 98.6% precision in industrial quality control.
        • AWS Certified Machine Learning - Specialty (2023)
    """.trimIndent()
}

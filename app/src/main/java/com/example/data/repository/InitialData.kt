package com.example.data.repository

import com.example.data.model.ApplicationItem
import com.example.data.model.ApplicationStatus
import com.example.data.model.CertificationItem
import com.example.data.model.Company
import com.example.data.model.EducationItem
import com.example.data.model.EmploymentType
import com.example.data.model.ExperienceLevel
import com.example.data.model.Job
import com.example.data.model.LearningResource
import com.example.data.model.NotificationItem
import com.example.data.model.NotificationType
import com.example.data.model.ProjectItem
import com.example.data.model.RemoteOption
import com.example.data.model.SavedJobItem
import com.example.data.model.SkillCategory
import com.example.data.model.SkillItem
import com.example.data.model.SkillProficiency
import com.example.data.model.UserProfile
import com.example.data.model.UserRole

object InitialData {

    val sampleUser = UserProfile(
        id = "user_alex",
        fullName = "Alex Rivera",
        email = "alex.rivera@example.com",
        phone = "+1 (555) 234-5678",
        location = "San Francisco, CA",
        role = UserRole.USER,
        careerObjective = "Experienced Machine Learning Engineer and Data Scientist specializing in scalable predictive algorithms, NLP architectures, and cloud-native AI deployments.",
        yearsOfExperience = 3.5,
        preferredRole = "Machine Learning Engineer",
        preferredLocation = "San Francisco, CA / Remote",
        remotePreference = RemoteOption.REMOTE,
        expectedSalaryMin = 130000,
        expectedSalaryMax = 175000,
        skills = listOf(
            SkillItem("Python", SkillCategory.PROGRAMMING, SkillProficiency.EXPERT),
            SkillItem("SQL", SkillCategory.PROGRAMMING, SkillProficiency.ADVANCED),
            SkillItem("PyTorch", SkillCategory.AI_ML, SkillProficiency.ADVANCED),
            SkillItem("Scikit-Learn", SkillCategory.AI_ML, SkillProficiency.ADVANCED),
            SkillItem("Machine Learning", SkillCategory.AI_ML, SkillProficiency.ADVANCED),
            SkillItem("Natural Language Processing", SkillCategory.AI_ML, SkillProficiency.ADVANCED),
            SkillItem("Pandas", SkillCategory.AI_ML, SkillProficiency.EXPERT),
            SkillItem("NumPy", SkillCategory.AI_ML, SkillProficiency.ADVANCED),
            SkillItem("FastAPI", SkillCategory.FRAMEWORKS, SkillProficiency.INTERMEDIATE),
            SkillItem("PostgreSQL", SkillCategory.DATABASES, SkillProficiency.ADVANCED),
            SkillItem("Git", SkillCategory.TOOLS, SkillProficiency.ADVANCED),
            SkillItem("Docker", SkillCategory.CLOUD_DEVOPS, SkillProficiency.INTERMEDIATE),
            SkillItem("Linux", SkillCategory.TOOLS, SkillProficiency.INTERMEDIATE)
        ),
        education = listOf(
            EducationItem(
                degree = "B.S. in Computer Science & Data Science",
                institution = "University of California, Berkeley",
                field = "Computer Science",
                startYear = "2017",
                endYear = "2021",
                gpa = "3.85 / 4.0"
            )
        ),
        certifications = listOf(
            CertificationItem(
                name = "AWS Certified Machine Learning - Specialty",
                issuer = "Amazon Web Services",
                date = "Oct 2023",
                credentialUrl = "https://aws.amazon.com/certification"
            ),
            CertificationItem(
                name = "Deep Learning Specialization",
                issuer = "DeepLearning.AI / Coursera",
                date = "May 2022",
                credentialUrl = "https://coursera.org"
            )
        ),
        projects = listOf(
            ProjectItem(
                name = "Autonomous Document RAG Pipeline",
                description = "Built end-to-end question answering pipeline indexing 50K+ technical documents using embeddings and vector search.",
                techStack = listOf("Python", "FastAPI", "PyTorch", "PostgreSQL", "Docker"),
                githubUrl = "https://github.com/alexrivera/rag-assistant",
                demoUrl = "https://demo.rag-platform.dev"
            ),
            ProjectItem(
                name = "Real-time Customer Churn Predictor",
                description = "Trained gradient boosted trees and neural networks achieving 0.92 AUC; deployed as microservice processing 15K req/min.",
                techStack = listOf("Python", "Scikit-Learn", "FastAPI", "Docker", "Redis"),
                githubUrl = "https://github.com/alexrivera/churn-ml-service"
            )
        ),
        resumeUploaded = true,
        resumeScore = 86
    )

    val sampleAdminUser = UserProfile(
        id = "admin_sarah",
        fullName = "Sarah Jenkins (Platform Admin)",
        email = "admin@example.com",
        phone = "+1 (555) 987-6543",
        location = "New York, NY",
        role = UserRole.ADMIN,
        careerObjective = "Chief Talent Officer & Platform Administrator.",
        yearsOfExperience = 8.0,
        preferredRole = "Platform Administrator",
        preferredLocation = "New York, NY",
        remotePreference = RemoteOption.HYBRID,
        expectedSalaryMin = 180000,
        expectedSalaryMax = 220000,
        skills = listOf(
            SkillItem("System Design", SkillCategory.TOOLS, SkillProficiency.EXPERT),
            SkillItem("Talent Acquisition", SkillCategory.SOFT_SKILLS, SkillProficiency.EXPERT)
        )
    )

    val sampleRecruiterUser = UserProfile(
        id = "recruiter_dave",
        fullName = "David Zhao (Tech Recruiter)",
        email = "recruiter@example.com",
        phone = "+1 (555) 456-7890",
        location = "San Francisco, CA",
        role = UserRole.RECRUITER,
        careerObjective = "Lead Technical Talent Partner at Stripe & Anthropic.",
        yearsOfExperience = 6.0,
        preferredRole = "Lead Tech Recruiter",
        preferredLocation = "San Francisco, CA",
        remotePreference = RemoteOption.REMOTE,
        expectedSalaryMin = 150000,
        expectedSalaryMax = 190000
    )

    val sampleCompanies = listOf(
        Company("c1", "Anthropic AI", "ANTHROPIC", "Artificial Intelligence", "AI research and safety company dedicated to building reliable, interpretable, and steerable AI systems.", "https://anthropic.com", "San Francisco, CA", "500-1000 employees"),
        Company("c2", "Stripe", "STRIPE", "Financial Technology", "Financial infrastructure platform for businesses of all sizes, from startups to public corporations.", "https://stripe.com", "San Francisco, CA / Remote", "5000+ employees"),
        Company("c3", "Datadog", "DATADOG", "Cloud & Observability", "The monitoring and security platform for cloud applications across infrastructure, APM, and logs.", "https://datadoghq.com", "New York, NY", "4000+ employees"),
        Company("c4", "Spotify", "SPOTIFY", "Digital Media & Audio", "World's most popular audio streaming service connecting millions of creators with billions of fans.", "https://spotify.com", "Boston, MA / Remote", "8000+ employees"),
        Company("c5", "Netflix", "NETFLIX", "Entertainment & Tech", "Leading entertainment service with hundreds of millions of paid memberships worldwide.", "https://netflix.com", "Los Gatos, CA", "10000+ employees"),
        Company("c6", "Snowflake", "SNOWFLAKE", "Data Cloud", "Data Cloud company providing unified global data sharing, engineering, and data science capabilities.", "https://snowflake.com", "San Mateo, CA", "6000+ employees"),
        Company("c7", "Databricks", "DATABRICKS", "Data & AI", "The Lakehouse platform combining the best of data warehouses and data lakes to accelerate AI innovation.", "https://databricks.com", "San Francisco, CA", "5000+ employees"),
        Company("c8", "Google Cloud", "GOOGLE", "Cloud & AI", "Leading enterprise cloud provider delivering scalable compute, storage, BigQuery, and Vertex AI.", "https://cloud.google.com", "Sunnyvale, CA", "50000+ employees")
    )

    val sampleJobs = listOf(
        Job(
            id = "job_1",
            title = "Senior Machine Learning Engineer",
            companyId = "c1",
            companyName = "Anthropic AI",
            companyLogoText = "AN",
            location = "San Francisco, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.SENIOR,
            salaryMin = 175000,
            salaryMax = 225000,
            remoteOption = RemoteOption.REMOTE,
            description = "We are seeking a Senior Machine Learning Engineer to scale our next-generation foundation model fine-tuning and evaluation pipelines. You will collaborate closely with research scientists to design distributed training loops, optimize GPU memory kernels, and deploy low-latency inference services.",
            requiredSkills = listOf("Python", "PyTorch", "Machine Learning", "Natural Language Processing", "Docker", "Distributed Systems"),
            preferredSkills = listOf("Kubernetes", "AWS", "CUDA", "FastAPI"),
            educationRequirement = "B.S. or Master's in Computer Science, AI, or equivalent",
            createdAt = "1 day ago",
            applicantsCount = 42
        ),
        Job(
            id = "job_2",
            title = "Full Stack Software Engineer",
            companyId = "c2",
            companyName = "Stripe",
            companyLogoText = "ST",
            location = "San Francisco, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.MID_LEVEL,
            salaryMin = 145000,
            salaryMax = 185000,
            remoteOption = RemoteOption.REMOTE,
            description = "Build secure, resilient web applications and API experiences that power economic transactions globally. You will work across React, TypeScript, Java/Go backend services, and distributed database layers.",
            requiredSkills = listOf("TypeScript", "React", "Node.js", "SQL", "PostgreSQL", "REST APIs"),
            preferredSkills = listOf("Docker", "GraphQL", "AWS", "Redis"),
            educationRequirement = "Bachelor's degree in Computer Science or related field",
            createdAt = "2 days ago",
            applicantsCount = 78
        ),
        Job(
            id = "job_3",
            title = "Data Scientist (NLP & Recommendation)",
            companyId = "c4",
            companyName = "Spotify",
            companyLogoText = "SP",
            location = "Boston, MA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.MID_LEVEL,
            salaryMin = 135000,
            salaryMax = 175000,
            remoteOption = RemoteOption.REMOTE,
            description = "Apply state-of-the-art Natural Language Processing and collaborative filtering algorithms to enhance music discovery and personalized playlist generation for 500M+ global listeners.",
            requiredSkills = listOf("Python", "SQL", "Scikit-Learn", "Machine Learning", "Natural Language Processing", "Pandas"),
            preferredSkills = listOf("PyTorch", "Apache Spark", "Docker", "GCP"),
            educationRequirement = "Master's or B.S. in Data Science, Statistics, or CS",
            createdAt = "3 days ago",
            applicantsCount = 35
        ),
        Job(
            id = "job_4",
            title = "Cloud Infrastructure & DevOps Engineer",
            companyId = "c3",
            companyName = "Datadog",
            companyLogoText = "DD",
            location = "New York, NY",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.SENIOR,
            salaryMin = 155000,
            salaryMax = 200000,
            remoteOption = RemoteOption.HYBRID,
            description = "Architect and automate resilient multi-cloud infrastructure managing millions of containers and petabyte-scale telemetry data.",
            requiredSkills = listOf("Docker", "Kubernetes", "AWS", "Terraform", "CI/CD", "Linux", "Python"),
            preferredSkills = listOf("Go", "PostgreSQL", "Prometheus"),
            educationRequirement = "B.S. in Computer Engineering or equivalent experience",
            createdAt = "1 day ago",
            applicantsCount = 28
        ),
        Job(
            id = "job_5",
            title = "AI Applications & Backend Developer",
            companyId = "c7",
            companyName = "Databricks",
            companyLogoText = "DB",
            location = "San Francisco, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.MID_LEVEL,
            salaryMin = 150000,
            salaryMax = 190000,
            remoteOption = RemoteOption.HYBRID,
            description = "Develop high-throughput REST and gRPC backend microservices to integrate Generative AI capabilities into the Lakehouse ecosystem.",
            requiredSkills = listOf("Python", "FastAPI", "PostgreSQL", "Machine Learning", "Docker", "REST APIs"),
            preferredSkills = listOf("PyTorch", "Kubernetes", "Redis", "Apache Spark"),
            educationRequirement = "Bachelor's in Computer Science or Software Engineering",
            createdAt = "4 days ago",
            applicantsCount = 51
        ),
        Job(
            id = "job_6",
            title = "Data Platform Engineer",
            companyId = "c6",
            companyName = "Snowflake",
            companyLogoText = "SF",
            location = "San Mateo, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.MID_LEVEL,
            salaryMin = 140000,
            salaryMax = 180000,
            remoteOption = RemoteOption.REMOTE,
            description = "Design modern ETL/ELT pipelines, optimize SQL data warehouse performance, and implement automated data governance tools.",
            requiredSkills = listOf("SQL", "Python", "Snowflake", "PostgreSQL", "Git", "Data Modeling"),
            preferredSkills = listOf("dbt", "Airflow", "Docker", "AWS"),
            educationRequirement = "Degree in Computer Science or Information Systems",
            createdAt = "Just now",
            applicantsCount = 19
        ),
        Job(
            id = "job_7",
            title = "Frontend Lead (React & UI Architecture)",
            companyId = "c5",
            companyName = "Netflix",
            companyLogoText = "NF",
            location = "Los Gatos, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.SENIOR,
            salaryMin = 170000,
            salaryMax = 220000,
            remoteOption = RemoteOption.HYBRID,
            description = "Lead frontend architecture for internal production tooling and studio creative workflow platforms.",
            requiredSkills = listOf("React", "TypeScript", "JavaScript", "REST APIs", "CSS", "Git"),
            preferredSkills = listOf("GraphQL", "Node.js", "WebSockets"),
            educationRequirement = "Bachelor's in Computer Science or Design Tech",
            createdAt = "3 days ago",
            applicantsCount = 64
        ),
        Job(
            id = "job_8",
            title = "Associate Data Analyst (Entry Level)",
            companyId = "c8",
            companyName = "Google Cloud",
            companyLogoText = "GC",
            location = "Sunnyvale, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.ENTRY_LEVEL,
            salaryMin = 95000,
            salaryMax = 125000,
            remoteOption = RemoteOption.HYBRID,
            description = "Analyze user adoption metrics, build executive BI dashboards in Looker/Tableau, and extract actionable business insights from BigQuery datasets.",
            requiredSkills = listOf("SQL", "Python", "Pandas", "Data Analysis", "Tableau"),
            preferredSkills = listOf("Power BI", "Statistics", "Google Cloud"),
            educationRequirement = "Bachelor's in Analytics, CS, Mathematics or Business",
            createdAt = "5 days ago",
            applicantsCount = 112
        ),
        Job(
            id = "job_9",
            title = "Junior Python & Backend Engineer",
            companyId = "c2",
            companyName = "Stripe",
            companyLogoText = "ST",
            location = "San Francisco, CA",
            employmentType = EmploymentType.FULL_TIME,
            experienceLevel = ExperienceLevel.ENTRY_LEVEL,
            salaryMin = 110000,
            salaryMax = 140000,
            remoteOption = RemoteOption.REMOTE,
            description = "Join our developer relations and core APIs team to build robust REST microservices, documentation test suites, and client SDKs.",
            requiredSkills = listOf("Python", "SQL", "Git", "REST APIs", "FastAPI"),
            preferredSkills = listOf("Docker", "PostgreSQL", "Linux"),
            educationRequirement = "B.S. in Computer Science or coding bootcamp graduate",
            createdAt = "2 days ago",
            applicantsCount = 92
        ),
        Job(
            id = "job_10",
            title = "Machine Learning Intern",
            companyId = "c1",
            companyName = "Anthropic AI",
            companyLogoText = "AN",
            location = "San Francisco, CA",
            employmentType = EmploymentType.INTERNSHIP,
            experienceLevel = ExperienceLevel.FRESHER,
            salaryMin = 85000,
            salaryMax = 105000,
            remoteOption = RemoteOption.HYBRID,
            description = "3-6 month intensive internship working directly on model benchmarking, synthetic dataset curation, and reinforcement learning experiments.",
            requiredSkills = listOf("Python", "PyTorch", "Machine Learning", "Git", "Math & Statistics"),
            preferredSkills = listOf("NLP", "Pandas", "Scikit-Learn"),
            educationRequirement = "Currently enrolled in or recent graduate of STEM degree",
            createdAt = "1 day ago",
            applicantsCount = 210
        )
    )

    val sampleLearningResources = listOf(
        LearningResource(
            id = "lr_docker",
            skillName = "Docker",
            title = "Docker for Modern Production Applications",
            category = "Cloud & DevOps",
            difficulty = "Beginner to Intermediate",
            estimatedHours = 8,
            topics = listOf(
                "Containerization fundamentals & Virtual Machines comparison",
                "Writing optimized multi-stage Dockerfiles",
                "Managing multi-container apps with Docker Compose",
                "Container networking, storage volumes & environment variables",
                "Publishing to Docker Hub & Container Registries"
            ),
            isEnrolled = true,
            progressPercent = 65
        ),
        LearningResource(
            id = "lr_k8s",
            skillName = "Kubernetes",
            title = "Kubernetes Orchestration & Helm Mastery",
            category = "Cloud & DevOps",
            difficulty = "Advanced",
            estimatedHours = 16,
            topics = listOf(
                "Kubernetes architecture: Control Plane, Nodes & Kubelet",
                "Deployments, Pods, ReplicaSets & StatefulSets",
                "Cluster Services, Ingress Controllers & Load Balancing",
                "ConfigMaps, Secrets management & RBAC",
                "Helm chart templating and production deployments"
            )
        ),
        LearningResource(
            id = "lr_aws",
            skillName = "AWS",
            title = "AWS Cloud Practitioner & Architecture Solutions",
            category = "Cloud & DevOps",
            difficulty = "Intermediate",
            estimatedHours = 20,
            topics = listOf(
                "EC2, VPC, Subnets & Security Groups",
                "Serverless Architecture with AWS Lambda & API Gateway",
                "S3 Object Storage, DynamoDB & RDS PostgreSQL",
                "IAM Policies, Roles & Security Best Practices",
                "CloudWatch monitoring & Auto-scaling policies"
            )
        ),
        LearningResource(
            id = "lr_fastapi",
            skillName = "FastAPI",
            title = "High-Performance Python Web APIs with FastAPI",
            category = "Frameworks & Backend",
            difficulty = "Intermediate",
            estimatedHours = 10,
            topics = listOf(
                "Async/Await event loops and concurrency in Python",
                "Pydantic data schemas & automatic OpenAPI validation",
                "Dependency injection system & JWT authentication",
                "SQLAlchemy ORM integration & database connection pooling",
                "Background worker tasks & Redis caching"
            ),
            isEnrolled = true,
            progressPercent = 80
        ),
        LearningResource(
            id = "lr_spark",
            skillName = "Apache Spark",
            title = "Distributed Big Data Processing with PySpark",
            category = "Data Engineering",
            difficulty = "Advanced",
            estimatedHours = 14,
            topics = listOf(
                "RDDs vs DataFrames & Spark SQL",
                "Partitioning, Shuffling & Lazy Evaluation",
                "Streaming ingestion with Spark Streaming & Kafka",
                "Performance tuning & cluster memory optimization"
            )
        ),
        LearningResource(
            id = "lr_react",
            skillName = "React",
            title = "Modern React 19 & Next.js Full Stack Engineering",
            category = "Frontend",
            difficulty = "Intermediate",
            estimatedHours = 18,
            topics = listOf(
                "React Hooks (useState, useEffect, useMemo, useCallback)",
                "State management with Zustand & Context API",
                "Server Components and SSR with Next.js",
                "Responsive UI styling with Tailwind CSS & Shadcn",
                "API client integration with TanStack Query"
            )
        )
    )

    val sampleApplications = listOf(
        ApplicationItem(
            id = "app_1",
            jobId = "job_1",
            jobTitle = "Senior Machine Learning Engineer",
            companyName = "Anthropic AI",
            location = "San Francisco, CA",
            salaryRange = "$175K - $225K",
            status = ApplicationStatus.INTERVIEW,
            appliedDate = "3 days ago",
            interviewDate = "Tomorrow at 2:00 PM PST",
            notes = "Technical round with Lead ML Scientist focusing on PyTorch distributed training and LLM evaluation.",
            contactPerson = "Elena Rostova (Recruiting Partner)"
        ),
        ApplicationItem(
            id = "app_2",
            jobId = "job_3",
            jobTitle = "Data Scientist (NLP & Recommendation)",
            companyName = "Spotify",
            location = "Boston, MA / Remote",
            salaryRange = "$135K - $175K",
            status = ApplicationStatus.SHORTLISTED,
            appliedDate = "1 week ago",
            notes = "Passed initial recruiter screen. Awaiting hiring manager review."
        ),
        ApplicationItem(
            id = "app_3",
            jobId = "job_5",
            jobTitle = "AI Applications & Backend Developer",
            companyName = "Databricks",
            location = "San Francisco, CA",
            salaryRange = "$150K - $190K",
            status = ApplicationStatus.UNDER_REVIEW,
            appliedDate = "4 days ago",
            notes = "Application submitted with tailored resume and RAG project demo link."
        ),
        ApplicationItem(
            id = "app_4",
            jobId = "job_2",
            jobTitle = "Full Stack Software Engineer",
            companyName = "Stripe",
            location = "San Francisco, CA / Remote",
            salaryRange = "$145K - $185K",
            status = ApplicationStatus.OFFER,
            appliedDate = "2 weeks ago",
            notes = "Received competitive offer with stock options and remote equipment budget.",
            salaryOffer = "$168,000 + Equity"
        )
    )

    val sampleSavedJobs = listOf(
        SavedJobItem("save_1", "job_1", "2 days ago", "Top choice for NLP and large models."),
        SavedJobItem("save_2", "job_4", "4 days ago", "Interested in cloud reliability and Terraform."),
        SavedJobItem("save_3", "job_5", "1 day ago", "Great stack with FastAPI and Lakehouse.")
    )

    val sampleNotifications = listOf(
        NotificationItem(
            id = "notif_1",
            title = "🎉 Interview Scheduled!",
            message = "Your technical interview for Senior ML Engineer at Anthropic AI is scheduled for tomorrow at 2:00 PM PST.",
            timestamp = "2 hours ago",
            type = NotificationType.INTERVIEW
        ),
        NotificationItem(
            id = "notif_2",
            title = "✨ New 94% Job Match Found",
            message = "Databricks just posted 'AI Applications & Backend Developer' which matches 94% of your skills.",
            timestamp = "5 hours ago",
            type = NotificationType.MATCH
        ),
        NotificationItem(
            id = "notif_3",
            title = "📈 Resume Analysis Complete",
            message = "Your uploaded resume scored 86/100. We found 13 technical skills and generated 3 improvement tips.",
            timestamp = "1 day ago",
            type = NotificationType.RESUME
        ),
        NotificationItem(
            id = "notif_4",
            title = "💡 Skill Recommendation",
            message = "Adding Kubernetes to your profile will increase your match score for 8 Senior Engineering positions.",
            timestamp = "2 days ago",
            type = NotificationType.LEARNING
        )
    )
}

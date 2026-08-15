# CareerAI - Intelligent Job & Career Advisory Android App

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-Material_3-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Platform](https://img.shields.io/badge/Platform-Android_API_24+-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)

---

CareerAI is a modern Android application built with **Kotlin** and **Jetpack Compose** (Material Design 3). It empowers job seekers and professionals with AI-driven job matching, real-time resume parsing, skill gap analysis, interactive career advice chat, and end-to-end application lifecycle tracking.

---

## ✨ Features

- 🎯 **AI Job Recommendation Engine**: Matches candidate skills, experience level, and preferences with job requirements using TF-IDF / Cosine similarity matching algorithms.
- 📄 **Resume Parser & Scorer**: Instant on-device parsing of resume text/PDF snippets into structured data (skills, contact info, strengths, weaknesses, and optimization recommendations).
- 📊 **Skill Gap Analysis**: Visualizes matched skills vs. missing high-demand skills for target job roles with tailored learning roadmaps.
- 💬 **Interactive Career AI Advisor**: Conversational assistant for mock interview preparation, salary negotiation strategies, and career path progression.
- 📋 **Application Pipeline Tracker**: Tracks job applications across stages (`Applied`, `Screening`, `Interviewing`, `Offered`, `Rejected`) with notes, reminders, and deadline alerts.
- 🔔 **Real-Time Notification System**: Keeps candidates notified of status changes, matching jobs, and interview reminders.
- 🌓 **Material 3 Theming**: Responsive design supporting both dynamic colors and dark/light system themes with edge-to-edge layouts.

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin 2.0+
- **UI Framework**: Jetpack Compose, Material Design 3 (M3)
- **Architecture**: Clean Architecture / MVVM (Model-View-ViewModel)
- **State Management**: Kotlin Coroutines, `StateFlow`, `collectAsStateWithLifecycle`
- **Navigation**: Compose Navigation with type-safe screen routing
- **Data & Storage**: Android Room Database, Kotlinx Serialization
- **Image Loading**: Coil Compose

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio Ladybug (2024.2+)** or newer
- **JDK 17** or **JDK 21**
- **Android SDK API 34+** (Min SDK: 24, Target SDK: 34)

### Installation & Run

1. **Clone the repository**:
   ```bash
   git clone https://github.com/<your-username>/<your-repo-name>.git
   cd <your-repo-name>
   ```

2. **Open in Android Studio**:
   - Launch Android Studio.
   - Select **Open** and choose the cloned repository folder.
   - Let Gradle sync and download dependencies.

3. **Build and Run**:
   - Connect your physical Android device or start an Android Virtual Device (AVD).
   - Click the green **Run ▶** button in Android Studio or run via command line:
     ```bash
     ./gradlew assembleDebug
     ```

---

## 📂 Project Structure

```
├── app/
│   ├── src/main/
│   │   ├── java/com/example/
│   │   │   ├── data/
│   │   │   │   ├── model/         # Data models & entities
│   │   │   │   └── repository/    # Data repositories & mock data
│   │   │   ├── ml/                # Matching engine & resume parser logic
│   │   │   ├── ui/
│   │   │   │   ├── components/    # Reusable Compose components & badges
│   │   │   │   ├── navigation/    # App navigation graph & bottom bar
│   │   │   │   ├── screens/       # Jetpack Compose UI screens
│   │   │   │   ├── theme/         # Color palettes, typography & themes
│   │   │   │   └── viewmodel/     # CareerViewModel state management
│   │   │   └── MainActivity.kt    # Main Activity & root container
│   │   └── res/                   # Drawables, vectors, mipmap icons, strings.xml
│   └── build.gradle.kts           # App-level build configurations
├── gradle/
│   └── libs.versions.toml         # Version catalog
├── build.gradle.kts               # Root build configuration
└── README.md
```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).

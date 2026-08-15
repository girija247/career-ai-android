package com.example.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.UserRole
import com.example.ui.components.RoleBadge
import com.example.ui.screens.AdminDashboardScreen
import com.example.ui.screens.ApplicationTrackerScreen
import com.example.ui.screens.CareerAIChatScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.JobDetailScreen
import com.example.ui.screens.JobsScreen
import com.example.ui.screens.LandingScreen
import com.example.ui.screens.LearningScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ResumeAnalysisScreen
import com.example.ui.screens.SavedJobsScreen
import com.example.ui.screens.SkillGapScreen
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.EditorialPrimary
import com.example.ui.theme.EditorialPrimaryContainer
import com.example.ui.theme.EditorialSecondary
import com.example.ui.theme.EditorialTertiary
import com.example.ui.theme.EmeraldSuccess
import com.example.ui.theme.IndigoSecondary
import com.example.ui.theme.RoyalBluePrimary
import com.example.ui.viewmodel.CareerViewModel

enum class ScreenRoute {
    LANDING,
    DASHBOARD,
    JOBS,
    JOB_DETAIL,
    RESUME,
    SKILL_GAP,
    LEARNING,
    APPLICATIONS,
    SAVED,
    CHAT,
    PROFILE,
    ADMIN,
    NOTIFICATIONS
}

data class NavItem(
    val route: ScreenRoute,
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppNavigation(
    viewModel: CareerViewModel,
    modifier: Modifier = Modifier
) {
    var currentRoute by remember { mutableStateOf(ScreenRoute.DASHBOARD) }
    var selectedJobDetailId by remember { mutableStateOf("job_1") }
    var selectedSkillGapJobId by remember { mutableStateOf<String?>("job_1") }

    val user by viewModel.currentUser.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val toastMessage by viewModel.toastMessage.collectAsStateWithLifecycle()
    val unreadCount = notifications.count { !it.isRead }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(toastMessage) {
        toastMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg, duration = SnackbarDuration.Short)
            viewModel.clearToast()
        }
    }

    val navItems = listOf(
        NavItem(ScreenRoute.DASHBOARD, "Overview", Icons.Filled.Dashboard, Icons.Outlined.Dashboard),
        NavItem(ScreenRoute.JOBS, "Jobs", Icons.Filled.Work, Icons.Outlined.WorkOutline),
        NavItem(ScreenRoute.RESUME, "Resume", Icons.Filled.Analytics, Icons.Outlined.Analytics),
        NavItem(ScreenRoute.CHAT, "CareerAI", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome),
        NavItem(ScreenRoute.APPLICATIONS, "Tracker", Icons.Filled.MenuBook, Icons.Outlined.MenuBook)
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (currentRoute !in listOf(ScreenRoute.JOB_DETAIL, ScreenRoute.SKILL_GAP, ScreenRoute.NOTIFICATIONS, ScreenRoute.ADMIN)) {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(EditorialPrimaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = EditorialSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = "ISSUE NO. 24 • CAREER PERSPECTIVE",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.8.sp,
                                    color = EditorialPrimary
                                )
                                Text(
                                    text = "CareerAI Platform",
                                    fontSize = 17.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    },
                    actions = {
                        // Role Badge
                        RoleBadge(
                            role = user.role,
                            modifier = Modifier
                                .clickable { currentRoute = ScreenRoute.PROFILE }
                                .padding(end = 4.dp)
                        )

                        // Notification Bell with unread badge
                        IconButton(onClick = { currentRoute = ScreenRoute.NOTIFICATIONS }) {
                            BadgedBox(
                                badge = {
                                    if (unreadCount > 0) {
                                        Badge(
                                            containerColor = EditorialPrimary,
                                            contentColor = Color.White
                                        ) {
                                            Text("$unreadCount")
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "Notifications",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        // Profile Icon
                        IconButton(
                            onClick = { currentRoute = ScreenRoute.PROFILE },
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(EditorialPrimaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user.fullName.take(1),
                                    fontSize = 14.sp,
                                    fontFamily = androidx.compose.ui.text.font.FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    color = EditorialSecondary
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
            }
        },
        bottomBar = {
            if (currentRoute !in listOf(ScreenRoute.JOB_DETAIL, ScreenRoute.SKILL_GAP, ScreenRoute.NOTIFICATIONS, ScreenRoute.ADMIN)) {
                Surface(
                    shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shadowElevation = 8.dp,
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp
                    ) {
                        navItems.forEach { item ->
                            val isSelected = currentRoute == item.route
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentRoute = item.route },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                                        contentDescription = item.title
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.title,
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        letterSpacing = if (isSelected) 0.5.sp else 0.sp
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = EditorialSecondary,
                                    selectedTextColor = EditorialPrimary,
                                    indicatorColor = EditorialPrimaryContainer,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentRoute) {
                ScreenRoute.LANDING -> LandingScreen(
                    viewModel = viewModel,
                    onExploreJobs = { currentRoute = ScreenRoute.JOBS },
                    onAnalyzeResume = { currentRoute = ScreenRoute.RESUME },
                    onOpenCareerAI = { currentRoute = ScreenRoute.CHAT }
                )

                ScreenRoute.DASHBOARD -> DashboardScreen(
                    viewModel = viewModel,
                    onNavigateToJobs = { currentRoute = ScreenRoute.JOBS },
                    onNavigateToJobDetail = { jobId ->
                        selectedJobDetailId = jobId
                        currentRoute = ScreenRoute.JOB_DETAIL
                    },
                    onNavigateToResume = { currentRoute = ScreenRoute.RESUME },
                    onNavigateToSkillGap = { jobId ->
                        selectedSkillGapJobId = jobId
                        currentRoute = ScreenRoute.SKILL_GAP
                    },
                    onNavigateToApplications = { currentRoute = ScreenRoute.APPLICATIONS },
                    onNavigateToLearning = { currentRoute = ScreenRoute.LEARNING },
                    onNavigateToCareerAI = { currentRoute = ScreenRoute.CHAT }
                )

                ScreenRoute.JOBS -> JobsScreen(
                    viewModel = viewModel,
                    onJobClick = { jobId ->
                        selectedJobDetailId = jobId
                        currentRoute = ScreenRoute.JOB_DETAIL
                    }
                )

                ScreenRoute.JOB_DETAIL -> JobDetailScreen(
                    jobId = selectedJobDetailId,
                    viewModel = viewModel,
                    onBack = { currentRoute = ScreenRoute.JOBS },
                    onNavigateToSkillGap = { jobId ->
                        selectedSkillGapJobId = jobId
                        currentRoute = ScreenRoute.SKILL_GAP
                    },
                    onNavigateToCareerAI = { jobId ->
                        selectedJobDetailId = jobId
                        currentRoute = ScreenRoute.CHAT
                    },
                    onNavigateToLearning = { currentRoute = ScreenRoute.LEARNING }
                )

                ScreenRoute.RESUME -> ResumeAnalysisScreen(
                    viewModel = viewModel,
                    onNavigateToJobs = { currentRoute = ScreenRoute.JOBS },
                    onNavigateToCareerAI = { currentRoute = ScreenRoute.CHAT }
                )

                ScreenRoute.SKILL_GAP -> SkillGapScreen(
                    initialJobId = selectedSkillGapJobId,
                    viewModel = viewModel,
                    onBack = { currentRoute = ScreenRoute.DASHBOARD },
                    onNavigateToLearning = { currentRoute = ScreenRoute.LEARNING },
                    onNavigateToCareerAI = { jobId ->
                        selectedJobDetailId = jobId
                        currentRoute = ScreenRoute.CHAT
                    }
                )

                ScreenRoute.LEARNING -> LearningScreen(
                    viewModel = viewModel
                )

                ScreenRoute.APPLICATIONS -> ApplicationTrackerScreen(
                    viewModel = viewModel,
                    onNavigateToJobs = { currentRoute = ScreenRoute.JOBS }
                )

                ScreenRoute.SAVED -> SavedJobsScreen(
                    viewModel = viewModel,
                    onNavigateToJobDetail = { jobId ->
                        selectedJobDetailId = jobId
                        currentRoute = ScreenRoute.JOB_DETAIL
                    },
                    onNavigateToJobs = { currentRoute = ScreenRoute.JOBS }
                )

                ScreenRoute.CHAT -> CareerAIChatScreen(
                    initialJobId = selectedJobDetailId,
                    viewModel = viewModel
                )

                ScreenRoute.PROFILE -> ProfileScreen(
                    viewModel = viewModel,
                    onNavigateToResume = { currentRoute = ScreenRoute.RESUME },
                    onNavigateToAdmin = { currentRoute = ScreenRoute.ADMIN }
                )

                ScreenRoute.ADMIN -> AdminDashboardScreen(
                    viewModel = viewModel,
                    onBack = { currentRoute = ScreenRoute.DASHBOARD }
                )

                ScreenRoute.NOTIFICATIONS -> NotificationsScreen(
                    viewModel = viewModel,
                    onBack = { currentRoute = ScreenRoute.DASHBOARD }
                )
            }
        }
    }
}

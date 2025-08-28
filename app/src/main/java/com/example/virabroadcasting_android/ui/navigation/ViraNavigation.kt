package com.example.virabroadcasting_android.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.example.virabroadcasting_android.ui.screens.*
import com.example.virabroadcasting_android.ui.components.ViraBottomNavigation

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.virabroadcasting_android.data.models.NewsArticle
import com.example.virabroadcasting_android.DetailActivity
import com.example.virabroadcasting_android.di.NetworkModule

@Composable
fun ViraNavigation() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("splash") }
    
    // Use the AuthRepository for WordPress authentication
    val authRepository = remember { 
        com.example.virabroadcasting_android.data.repository.AuthRepository(NetworkModule.wordPressAuthService)
    }
    val context = LocalContext.current
    
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // Splash Screen
        composable("splash") {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // Main App with Bottom Navigation
        composable("home") {
            currentRoute = "home"
            MainAppScreen(
                currentRoute = currentRoute,
                onNavigate = { route ->
                    currentRoute = route
                    when (route) {
                        "home" -> navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                        "search" -> navController.navigate("search")
                        "alerts" -> navController.navigate("alerts")
                        "profile" -> navController.navigate("profile")
                    }
                },
                onProfileClick = {
                    navController.navigate("profile")
                },
                onNotificationClick = {
                    navController.navigate("alerts")
                },
                onNewsItemClick = { article: NewsArticle ->
                    val intent = Intent(context, DetailActivity::class.java).apply {
                        putExtra("title", article.title)
                        putExtra("content", article.content)
                        putExtra("author", article.author)
                        putExtra("publishDate", article.publishDate)
                        putExtra("category", article.category)
                        putExtra("imageUrl", article.imageUrl ?: "")
                    }
                    context.startActivity(intent)
                },
                onSignOut = {
                    // Sign out user and stay on current screen
                    authRepository.signOut()
                },
                authRepository = authRepository
            )
        }

        // Search Screen
        composable("search") {
            SearchScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onFilterClick = {
                    // Handle filter click
                },
                onSearchItemClick = { searchTerm ->
                    // Handle search item click
                }
            )
        }

        // Alerts Screen
        composable("alerts") {
            AlertsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSettingsClick = {
                    // Handle settings click
                },
                onAlertClick = { alertId ->
                    // Handle alert click
                },
                onDeleteAlert = { alertId ->
                    // Handle delete alert
                }
            )
        }

        // Profile Screen
        composable("profile") {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEditProfileClick = {
                    navController.navigate("edit_profile")
                },
                onSettingClick = { setting ->
                    // Handle setting click
                },
                onTestConnectionClick = {
                    navController.navigate("test_connection")
                },
                onSignOutClick = {
                    // Sign out user and stay on profile screen
                    authRepository.signOut()
                },
                onLoginClick = {
                    navController.navigate("login")
                },
                onSignUpClick = {
                    navController.navigate("create_account")
                },
                authRepository = authRepository
            )
        }

        // Test Connection Screen
        composable("test_connection") {
            TestConnectionScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // Edit Profile Screen
        composable("edit_profile") {
            val coroutineScope = rememberCoroutineScope()
            
            EditProfileScreen(
                currentUser = null, // Will be updated to use WordPress user data
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveProfile = { fullName, email, phone, location ->
                    // Update WordPress user profile
                    coroutineScope.launch {
                        authRepository.updateUserProfile(
                            name = fullName,
                            email = email,
                            meta = mapOf(
                                "phone" to phone,
                                "location" to location
                            )
                        )
                        navController.popBackStack()
                    }
                }
            )
        }

        // Login Screen (inside Profile section)
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    // Navigate back to profile after successful login
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
                },
                authRepository = authRepository
            )
        }

        // Create Account Screen (inside Profile section)
        composable("create_account") {
            CreateAccountScreen(
                onCreateAccountClick = { fullName, email, phone, location ->
                    // For now, just navigate back - WordPress user creation requires admin setup
                    navController.popBackStack()
                },
                onSignInClick = {
                    navController.navigate("login")
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

@Composable
fun MainAppScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNewsItemClick: (NewsArticle) -> Unit,
    onSignOut: () -> Unit,
    authRepository: com.example.virabroadcasting_android.data.repository.AuthRepository
) {
    Scaffold(
        bottomBar = {
            ViraBottomNavigation(
                currentRoute = currentRoute,
                onNavigate = onNavigate
            )
        }
    ) { innerPadding ->
        when (currentRoute) {
            "home" -> {
                HomeScreen(
                    onProfileClick = onProfileClick,
                    onNotificationClick = onNotificationClick,
                    onNewsItemClick = onNewsItemClick
                )
            }
            "search" -> {
                SearchScreen(
                    onBackClick = { onNavigate("home") },
                    onFilterClick = { /* Handle filter */ },
                    onSearchItemClick = { /* Handle search */ }
                )
            }
            "alerts" -> {
                AlertsScreen(
                    onBackClick = { onNavigate("home") },
                    onSettingsClick = { /* Handle settings */ },
                    onAlertClick = { /* Handle alert */ },
                    onDeleteAlert = { /* Handle delete */ }
                )
            }
            "profile" -> {
                ProfileScreen(
                    onBackClick = { onNavigate("home") },
                    onEditProfileClick = { /* Will be handled by navigation */ },
                    onSettingClick = { /* Handle setting */ },
                    onTestConnectionClick = { /* Will be handled by navigation */ },
                    onSignOutClick = onSignOut,
                    onLoginClick = { /* Will be handled by navigation */ },
                    onSignUpClick = { /* Will be handled by navigation */ },
                    authRepository = authRepository
                )
            }
        }
    }
}

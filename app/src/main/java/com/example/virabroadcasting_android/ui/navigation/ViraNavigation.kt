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
import com.example.virabroadcasting_android.ui.screens.*
import com.example.virabroadcasting_android.ui.components.ViraBottomNavigation

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import com.example.virabroadcasting_android.data.models.NewsArticle
import com.example.virabroadcasting_android.DetailActivity

@Composable
fun ViraNavigation() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("splash") }
    
    // Use the singleton UserRepository directly
    val userRepository = com.example.virabroadcasting_android.data.repository.UserRepository
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // Splash Screen
        composable("splash") {
            SplashScreen(
                onSplashComplete = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // Login Screen
        composable("login") {
            currentRoute = "login"
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                    currentRoute = "home"
                },
                onSignUpClick = {
                    navController.navigate("create_account")
                },
                onForgotPasswordClick = {
                    // Handle forgot password
                },
                onBackClick = {
                    navController.navigate("splash") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }

        // Create Account Screen
        composable("create_account") {
            CreateAccountScreen(
                onCreateAccountClick = { fullName, email, phone, location ->
                    // Create user and save to repository
                    userRepository.createUser(fullName, email, phone, location)
                    navController.navigate("home") {
                        popUpTo("create_account") { inclusive = true }
                    }
                },
                onSignInClick = {
                    navController.popBackStack()
                },
                onBackClick = {
                    navController.popBackStack()
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
                    // Clear user data and navigate to login
                    userRepository.clearUser()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                userRepository = userRepository
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
                currentUser = userRepository.getUser(),
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
                    // Clear user data and navigate to login
                    userRepository.clearUser()
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
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
            EditProfileScreen(
                currentUser = userRepository.getUser(),
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveProfile = { fullName, email, phone, location ->
                    println("🔐 DEBUG: Navigation - Saving profile: $fullName, $email, $phone, $location")
                    userRepository.updateUserProfile(fullName, email, phone, location)
                    println("🔐 DEBUG: Navigation - Profile saved, navigating back")
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
    userRepository: com.example.virabroadcasting_android.data.repository.UserRepository
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
                    currentUser = userRepository.getUser(),
                    onBackClick = { onNavigate("home") },
                    onEditProfileClick = { /* Will be handled by navigation */ },
                    onSettingClick = { /* Handle setting */ },
                    onTestConnectionClick = { /* Will be handled by navigation */ },
                    onSignOutClick = onSignOut
                )
            }
        }
    }
}

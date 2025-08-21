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

@Composable
fun ViraNavigation() {
    val navController = rememberNavController()
    var currentRoute by remember { mutableStateOf("splash") }
    
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
            LoginScreen(
                onLoginClick = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
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
                onCreateAccountClick = {
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
                onNewsItemClick = { newsId ->
                    // Handle news item click
                }
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
                    // Handle edit profile click
                },
                onSettingClick = { setting ->
                    // Handle setting click
                },
                onTestConnectionClick = {
                    navController.navigate("test_connection")
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
    }
}

@Composable
fun MainAppScreen(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNewsItemClick: (String) -> Unit
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
                    onEditProfileClick = { /* Handle edit profile */ },
                    onSettingClick = { /* Handle setting */ },
                    onTestConnectionClick = { /* Will be handled by navigation */ }
                )
            }
        }
    }
}

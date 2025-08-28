package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virabroadcasting_android.data.api.WordPressUser
import com.example.virabroadcasting_android.data.repository.AuthRepository
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onSettingClick: (String) -> Unit,
    onTestConnectionClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    authRepository: AuthRepository
) {
    val currentUser by authRepository.currentUser.collectAsState()
    val isAuthenticated by authRepository.isAuthenticated.collectAsState()
    val isLoading by authRepository.isLoading.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Header
        ViraHeader(
            title = "Profile",
            showBackButton = true,
            onBackClick = onBackClick
        )
        
        // Content based on authentication status
        if (isAuthenticated && currentUser != null) {
            // Authenticated User Profile
            AuthenticatedUserProfile(
                user = currentUser!!,
                onEditProfileClick = onEditProfileClick,
                onSettingClick = onSettingClick,
                onTestConnectionClick = onTestConnectionClick,
                onSignOutClick = onSignOutClick
            )
        } else {
            // Guest User - Show Login Section
            GuestUserLoginSection(
                onLoginClick = onLoginClick,
                onSignUpClick = onSignUpClick
            )
        }
    }
}

@Composable
private fun AuthenticatedUserProfile(
    user: WordPressUser,
    onEditProfileClick: () -> Unit,
    onSettingClick: (String) -> Unit,
    onTestConnectionClick: () -> Unit,
    onSignOutClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Info Section
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = BackgroundSecondary
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = ViraRed,
                            modifier = Modifier.size(60.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column {
                            Text(
                                text = user.name,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = user.email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSecondary
                            )
                            
                            Text(
                                text = "Member since ${user.registered.substring(0, 10)}",
                                style = MaterialTheme.typography.bodySmall,
                                color = TextSecondary
                            )
                        }
                    }
                }
            }
        }
        
        // Profile Actions
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = BackgroundSecondary
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Profile Actions",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    // Edit Profile Button
                    OutlinedButton(
                        onClick = onEditProfileClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ViraRed)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit Profile")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit Profile")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Settings Button
                    OutlinedButton(
                        onClick = { onSettingClick("settings") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ViraRed)
                    ) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Settings")
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Test Connection Button
                    OutlinedButton(
                        onClick = onTestConnectionClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ViraRed)
                    ) {
                        Icon(Icons.Default.Wifi, contentDescription = "Test Connection")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Test Connection")
                    }
                }
            }
        }
        
        // Sign Out Section
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.Red.copy(alpha = 0.1f)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Account",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    Button(
                        onClick = onSignOutClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Icon(Icons.Default.Logout, contentDescription = "Sign Out")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sign Out")
                    }
                }
            }
        }
    }
}

@Composable
private fun GuestUserLoginSection(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = BackgroundSecondary
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Guest User",
                        tint = TextSecondary,
                        modifier = Modifier.size(80.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Guest User",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Only authorized VIRA Broadcasting members can access profiles",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Login Button
                    Button(
                        onClick = onLoginClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ViraRed)
                    ) {
                        Icon(Icons.Default.Login, contentDescription = "Login")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Login")
                    }
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    // Sign Up Button
                    OutlinedButton(
                        onClick = onSignUpClick,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ViraRed)
                    ) {
                        Icon(Icons.Default.PersonAdd, contentDescription = "Sign Up")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Sign Up")
                    }
                }
            }
        }
    }
}

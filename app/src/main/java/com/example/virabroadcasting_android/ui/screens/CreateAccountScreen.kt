package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*

@Composable
fun CreateAccountScreen(
    onCreateAccountClick: () -> Unit,
    onSignInClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        // Header
        ViraHeader(
            title = "Create Account",
            showBackButton = true,
            onBackClick = onBackClick
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Welcome Message
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.displaySmall,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Join VIRA to get personalized news updates",
            style = MaterialTheme.typography.bodyLarge,
            color = TextSecondary,
            modifier = Modifier.align(Alignment.Start)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Full Name Field
        ViraTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = "Full Name",
            placeholder = "Enter your full name",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Email Field
        ViraTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            placeholder = "Enter your email",
            leadingIcon = Icons.Default.Email,
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Password Field
        ViraTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Create a password",
            isPassword = true,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Confirm Password Field
        ViraTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            placeholder = "Confirm your password",
            isPassword = true,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Create Account Button
        ViraButton(
            text = "Create Account",
            onClick = {
                isLoading = true
                // Simulate account creation process
                onCreateAccountClick()
            },
            isLoading = isLoading,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Sign In Link
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary
            )
            ViraTextButton(
                text = "Sign in",
                onClick = onSignInClick
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.virabroadcasting_android.ui.components.ViraHeader
import com.example.virabroadcasting_android.ui.theme.*

@Composable
fun EditProfileScreen(
    currentUser: com.example.virabroadcasting_android.data.models.User?,
    onBackClick: () -> Unit,
    onSaveProfile: (String, String, String, String) -> Unit
) {
    var fullName by remember { mutableStateOf(currentUser?.fullName ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var phone by remember { mutableStateOf(currentUser?.phone ?: "") }
    var location by remember { mutableStateOf(currentUser?.location ?: "") }
    
    var isFullNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPhoneError by remember { mutableStateOf(false) }
    var isLocationError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Header
        ViraHeader(
            title = "Edit Profile",
            showBackButton = true,
            onBackClick = onBackClick
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Profile Form
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = BackgroundSecondary
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Personal Information",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Full Name
                    Column {
                        Text(
                            text = "Full Name",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = fullName,
                            onValueChange = { 
                                fullName = it
                                isFullNameError = false
                            },
                            placeholder = { Text("Enter your full name") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isFullNameError,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ViraRed,
                                unfocusedBorderColor = if (isFullNameError) Color.Red else Color(0xFFE2E8F0)
                            )
                        )
                        if (isFullNameError) {
                            Text(
                                text = "Full name is required",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    // Email
                    Column {
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { 
                                email = it
                                isEmailError = false
                            },
                            placeholder = { Text("Enter your email") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isEmailError,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ViraRed,
                                unfocusedBorderColor = if (isEmailError) Color.Red else Color(0xFFE2E8F0)
                            )
                        )
                        if (isEmailError) {
                            Text(
                                text = "Valid email is required",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    // Phone
                    Column {
                        Text(
                            text = "Phone Number",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = phone,
                            onValueChange = { 
                                phone = it
                                isPhoneError = false
                            },
                            placeholder = { Text("Enter your phone number") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isPhoneError,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ViraRed,
                                unfocusedBorderColor = if (isPhoneError) Color.Red else Color(0xFFE2E8F0)
                            )
                        )
                        if (isPhoneError) {
                            Text(
                                text = "Phone number is required",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    // Location
                    Column {
                        Text(
                            text = "Location",
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = location,
                            onValueChange = { 
                                location = it
                                isLocationError = false
                            },
                            placeholder = { Text("Enter your location") },
                            modifier = Modifier.fillMaxWidth(),
                            isError = isLocationError,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ViraRed,
                                unfocusedBorderColor = if (isLocationError) Color.Red else Color(0xFFE2E8F0)
                            )
                        )
                        if (isLocationError) {
                            Text(
                                text = "Location is required",
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                                            // Save Button
                        Button(
                            onClick = {
                                // Validate fields
                                var hasError = false
                                if (fullName.isBlank()) {
                                    isFullNameError = true
                                    hasError = true
                                }
                                if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                    isEmailError = true
                                    hasError = true
                                }
                                if (phone.isBlank()) {
                                    isPhoneError = true
                                    hasError = true
                                }
                                if (location.isBlank()) {
                                    isLocationError = true
                                    hasError = true
                                }

                                if (!hasError) {
                                    println("🔐 DEBUG: EditProfileScreen - Saving profile: $fullName, $email, $phone, $location")
                                    onSaveProfile(fullName, email, phone, location)
                                }
                            },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = ViraRed),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Save,
                            contentDescription = "Save",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Save Changes",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

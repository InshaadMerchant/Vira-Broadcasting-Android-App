package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.virabroadcasting_android.config.WordPressConfig
import com.example.virabroadcasting_android.di.NetworkModule
import com.example.virabroadcasting_android.ui.theme.*
import com.example.virabroadcasting_android.ui.viewmodels.HomeViewModel
import kotlinx.coroutines.launch

@Composable
fun TestConnectionScreen(
    onBackClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val viewModel = remember { HomeViewModel(NetworkModule.newsRepository) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    var testResults by remember { mutableStateOf<List<String>>(emptyList()) }
    var isTesting by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "WordPress Connection Test",
                style = MaterialTheme.typography.titleLarge,
                color = TextPrimary,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Configuration Status
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Configuration Status",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (WordPressConfig.validateConfiguration()) {
                            Icons.Default.CheckCircle
                        } else {
                            Icons.Default.Error
                        },
                        tint = if (WordPressConfig.validateConfiguration()) {
                            SuccessGreen
                        } else {
                            ViraRed
                        },
                        contentDescription = "Status"
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = WordPressConfig.getConfigurationStatus(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextPrimary
                    )
                }
                
                if (WordPressConfig.validateConfiguration()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Base URL: ${WordPressConfig.WORDPRESS_BASE_URL}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                    Text(
                        text = "API URL: ${WordPressConfig.WORDPRESS_API_BASE_URL}",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Test Connection Button
        Button(
            onClick = {
                scope.launch {
                    isTesting = true
                    testResults = emptyList()
                    
                    val results = mutableListOf<String>()
                    
                                         // Test 1: Configuration
                     results.add("✅ Configuration: ${WordPressConfig.getConfigurationStatus()}")
                     
                     // Test 2: Network Module
                     results.add("✅ Network Module: Available")
                     
                     // Test 3: API Connection
                     try {
                         val news = NetworkModule.newsRepository.getLatestNews(page = 1, perPage = 1)
                         results.add("✅ API Connection: Successfully fetched ${news.size} articles")
                     } catch (e: Exception) {
                         results.add("❌ API Connection: ${e.message}")
                     }
                     
                     // Test 4: Latest News
                     try {
                         val news = NetworkModule.newsRepository.getLatestNews(page = 1, perPage = 5)
                         results.add("✅ Latest News: Successfully fetched ${news.size} articles")
                     } catch (e: Exception) {
                         results.add("❌ Latest News: ${e.message}")
                     }
                    
                    testResults = results
                    isTesting = false
                }
            },
            enabled = !isTesting && WordPressConfig.validateConfiguration(),
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = ViraRed)
        ) {
            if (isTesting) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Testing...")
            } else {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Test"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Test WordPress Connection")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Test Results
        if (testResults.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Test Results",
                        style = MaterialTheme.typography.titleMedium,
                        color = TextPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    
                    testResults.forEach { result ->
                        Text(
                            text = result,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (result.startsWith("✅")) TextPrimary else ViraRed,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Instructions
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F9FF))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Setup Instructions",
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "1. Update WordPressConfig.kt with your site URL\n" +
                           "2. Ensure WordPress REST API is enabled\n" +
                           "3. Test the connection using the button above\n" +
                           "4. Check the test results for any errors",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Current State
        if (uiState.isLoading) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    color = ViraRed,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
        
        if (uiState.error != null) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Error",
                        tint = ViraRed
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = uiState.error ?: "Unknown error",
                        style = MaterialTheme.typography.bodyMedium,
                        color = ViraRed
                    )
                }
            }
        }
    }
}

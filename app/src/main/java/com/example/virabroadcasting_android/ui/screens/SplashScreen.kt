package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virabroadcasting_android.ui.theme.ViraRed
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(500)
        showContent = true
        delay(2000)
        onSplashComplete()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ViraRed),
        contentAlignment = Alignment.Center
    ) {
        if (showContent) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // VIRA Logo
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .shadow(
                            elevation = 16.dp,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            Color.Black,
                            RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // VIRA Text
                        Text(
                            text = "VIRA",
                            color = ViraRed,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        
                        // Globe Icon (replacing A)
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = "Globe",
                            tint = ViraRed,
                            modifier = Modifier.size(40.dp)
                        )
                        
                        // Radio Waves
                        Column(
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            repeat(3) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(
                                            width = (20 + index * 8).dp,
                                            height = 2.dp
                                        )
                                        .background(
                                            ViraRed,
                                            RoundedCornerShape(1.dp)
                                        )
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // VIRA Text
                Text(
                    text = "VIRA",
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
                
                // Broadcasting subtitle
                Text(
                    text = "Broadcasting",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        
        // Bottom indicator dots
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(
                            ViraRed.copy(alpha = 0.6f),
                            shape = CircleShape
                        )
                )
            }
        }
    }
}

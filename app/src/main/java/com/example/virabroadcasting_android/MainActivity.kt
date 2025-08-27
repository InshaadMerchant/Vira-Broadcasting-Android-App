package com.example.virabroadcasting_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.virabroadcasting_android.ui.navigation.ViraNavigation
import com.example.virabroadcasting_android.ui.theme.Virabroadcasting_androidTheme

import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AdMob
        MobileAds.initialize(this) {}
        // enableEdgeToEdge() // Commented out to fix status bar overlap
        setContent {
            Virabroadcasting_androidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ViraNavigation()
                }
            }
        }
    }
}
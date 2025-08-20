package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNewsItemClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    val categories = listOf("All", "Trending", "Politics", "Technology", "Environment", "Health", "Sports")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Header
        ViraHeader(
            title = "VIRA",
            showProfileIcon = true,
            onProfileClick = onProfileClick,
            showNotificationIcon = true,
            onNotificationClick = onNotificationClick,
            unreadCount = 2
        )
        
        // Search Bar
        ViraSearchBar(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Search news...",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        
        // Category Tabs
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                ViraCategoryChip(
                    text = category,
                    isSelected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        
        // News Articles
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Politics Article
                ViraNewsCard(
                    title = "Trump Holds Virtual NATO Meeting Ahead of Putin Summit, Allies Cautious but United",
                    category = "Politics",
                    date = "August 13, 2025",
                    onClick = { onNewsItemClick("politics-1") }
                )
            }
            
            item {
                // Environment Article
                ViraNewsCard(
                    title = "Tropical Storm Erin Expected to Strengthen Into Season's First Major Hurricane",
                    category = "Environment",
                    date = "August 13, 2025",
                    onClick = { onNewsItemClick("environment-1") }
                )
            }
            
            item {
                // Technology Article
                ViraNewsCard(
                    title = "AI Breakthrough: New Model Achieves Human-Level Reasoning in Complex Tasks",
                    category = "Technology",
                    date = "August 13, 2025",
                    onClick = { onNewsItemClick("tech-1") }
                )
            }
            
            item {
                // Health Article
                ViraNewsCard(
                    title = "Global Health Organization Reports Significant Progress in Disease Prevention",
                    category = "Health",
                    date = "August 13, 2025",
                    onClick = { onNewsItemClick("health-1") }
                )
            }
            
            item {
                // Sports Article
                ViraNewsCard(
                    title = "Olympic Committee Announces New Sports for 2028 Los Angeles Games",
                    category = "Sports",
                    date = "August 13, 2025",
                    onClick = { onNewsItemClick("sports-1") }
                )
            }
        }
    }
}

package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*

@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onFilterClick: () -> Unit,
    onSearchItemClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    val recentSearches = listOf("Climate change", "Politics", "Technology", "Health news")
    val trendingTopics = listOf(
        "NATO Summit" to 245,
        "Hurricane Erin" to 189,
        "Stock Market" to 156,
        "Tech Innovation" to 134,
        "Climate Report" to 98
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Header
        ViraHeader(
            title = "Search",
            showBackButton = true,
            onBackClick = onBackClick,
            showFilterIcon = true,
            onFilterClick = onFilterClick
        )
        
        // Search Bar
        ViraSearchBar(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = "Search news, topics, categories...",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        
        // Content
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Recent Searches Section
            item {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Recent",
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Recent Searches",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        recentSearches.forEach { search ->
                            Surface(
                                modifier = Modifier.clickable { onSearchItemClick(search) },
                                shape = RoundedCornerShape(20.dp),
                                color = BackgroundSecondary,
                                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                            ) {
                                Text(
                                    text = search,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = TextSecondary,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
            
            // Trending Topics Section
            item {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.TrendingUp,
                            contentDescription = "Trending",
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Trending Topics",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    
                    trendingTopics.forEachIndexed { index, (topic, articleCount) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onSearchItemClick(topic) }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "#${index + 1}",
                                style = MaterialTheme.typography.titleMedium,
                                color = ViraRed,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.width(40.dp)
                            )
                            
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = topic,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "$articleCount articles",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                        }
                        
                        if (index < trendingTopics.size - 1) {
                            Divider(
                                color = Color(0xFFE2E8F0),
                                thickness = 1.dp,
                                modifier = Modifier.padding(start = 40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

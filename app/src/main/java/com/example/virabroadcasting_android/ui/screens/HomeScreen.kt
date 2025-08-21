package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.virabroadcasting_android.di.NetworkModule
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*
import com.example.virabroadcasting_android.ui.viewmodels.HomeViewModel

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNewsItemClick: (String) -> Unit
) {
    // Initialize ViewModel
    val viewModel = remember { HomeViewModel(NetworkModule.newsRepository) }
    
    // Collect state from ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val newsArticles by viewModel.newsArticles.collectAsStateWithLifecycle()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedCategory by viewModel.selectedCategory.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    
    // LazyListState for infinite scroll
    val listState = rememberLazyListState()
    
    // Infinite scroll effect
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItem = visibleItems.lastOrNull()
                if (lastVisibleItem != null && 
                    lastVisibleItem.index >= newsArticles.size - 3 && 
                    !uiState.isLoadingMore) {
                    viewModel.loadMoreNews()
                }
            }
    }
    
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
            onValueChange = { viewModel.searchNews(it) },
            placeholder = "Search news...",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )
        
        // Category Tabs
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // All category option
            item {
                ViraCategoryChip(
                    text = "All",
                    isSelected = selectedCategory == null,
                    onClick = { viewModel.selectCategory(null) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
            
            // Dynamic categories from WordPress
            items(categories) { category ->
                ViraCategoryChip(
                    text = category.name,
                    isSelected = selectedCategory?.id == category.id,
                    onClick = { viewModel.selectCategory(category) },
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
        
        // Content
        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading && newsArticles.isEmpty()) {
                // Loading state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = ViraRed,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Loading news...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = TextSecondary
                    )
                }
            } else if (newsArticles.isEmpty() && !uiState.isLoading) {
                // Empty state
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Article,
                        contentDescription = "No news",
                        modifier = Modifier.size(64.dp),
                        tint = TextTertiary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "No news available",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Check your connection or try refreshing",
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextTertiary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { viewModel.refreshNews() },
                        colors = ButtonDefaults.buttonColors(containerColor = ViraRed)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Refresh")
                    }
                }
            } else {
                // News Articles List
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(newsArticles) { article ->
                        ViraNewsCard(
                            title = article.title,
                            category = article.category,
                            date = article.publishDate,
                            imageUrl = article.imageUrl,
                            onClick = { onNewsItemClick(article.id) }
                        )
                    }
                    
                    // Loading more indicator
                    if (uiState.isLoadingMore) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = ViraRed,
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(16.dp))
                                Text(
                                    text = "Loading more...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )
                            }
                        }
                    }
                }
            }
            
            // Error message overlay
            if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error",
                                tint = ViraRed,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = uiState.error!!,
                                style = MaterialTheme.typography.bodyMedium,
                                color = ViraRed,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { viewModel.clearError() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = ViraRed
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

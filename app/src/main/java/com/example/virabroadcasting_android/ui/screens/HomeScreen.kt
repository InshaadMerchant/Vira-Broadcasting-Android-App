package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.virabroadcasting_android.di.NetworkModule
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*
import com.example.virabroadcasting_android.ui.viewmodels.HomeViewModel
import com.example.virabroadcasting_android.data.models.NewsArticle
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import com.example.virabroadcasting_android.ui.components.BannerAd
import androidx.compose.foundation.lazy.itemsIndexed

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onNewsItemClick: (NewsArticle) -> Unit
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
                    !uiState.isLoadingMore
                ) {
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
            showRefreshIcon = true,
            onRefreshClick = { viewModel.refreshNews() },
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

            // Dynamic categories
            items(categories) { category ->
                ViraCategoryChip(
                    text = category.name,
                    isSelected = selectedCategory?.id == category.id,
                    onClick = { viewModel.selectCategory(category) },
                    modifier = Modifier.padding(vertical = 4.dp),
                    categoryColor = category.color
                )
            }
        }
        BannerAd(adUnitId = "ca-app-pub-3940256099942544/6300978111") //change the unit id


        // Content area
        Column {
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
                    itemsIndexed(newsArticles) { index, article ->
                        // Debug image URLs
                        LaunchedEffect(article.id) {
                            println("🔍 DEBUG: HomeScreen - Article: ${article.title}")
                            println("🔍 DEBUG: HomeScreen - Image URL: ${article.imageUrl}")
                        }
                        
                        ViraNewsCard(
                            title = article.title,
                            category = article.category,
                            date = article.publishDate,
                            imageUrl = article.imageUrl,
                            onClick = { onNewsItemClick(article) } // Pass full article
                        )
                        if ((index + 1) % 3 == 0) {
                            BannerAd(adUnitId = "ca-app-pub-3940256099942544/6300978111") // Test ID
                        }
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

            // Error overlay
            if (uiState.error != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
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

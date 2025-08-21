package com.example.virabroadcasting_android.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.virabroadcasting_android.data.models.NewsArticle
import com.example.virabroadcasting_android.data.models.NewsCategory
import com.example.virabroadcasting_android.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {
    
    // UI State
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    
    // News data
    private val _newsArticles = MutableStateFlow<List<NewsArticle>>(emptyList())
    val newsArticles: StateFlow<List<NewsArticle>> = _newsArticles.asStateFlow()
    
    // Categories
    private val _categories = MutableStateFlow<List<NewsCategory>>(emptyList())
    val categories: StateFlow<List<NewsCategory>> = _categories.asStateFlow()
    
    // Selected category
    private val _selectedCategory = MutableStateFlow<NewsCategory?>(null)
    val selectedCategory: StateFlow<NewsCategory?> = _selectedCategory.asStateFlow()
    
    // Search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // Pagination
    private var currentPage = 1
    private var hasMorePages = true
    
    init {
        loadInitialData()
    }
    
    /**
     * Load initial data (categories and latest news)
     */
    private fun loadInitialData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                // Load categories
                val categories = newsRepository.getCategories()
                _categories.value = categories
                
                // Load latest news
                val news = newsRepository.getLatestNews(page = 1)
                _newsArticles.value = news
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load news"
                )
            }
        }
    }
    
    /**
     * Load more news (pagination)
     */
    fun loadMoreNews() {
        if (_uiState.value.isLoading || !hasMorePages) return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingMore = true)
            
            try {
                val nextPage = currentPage + 1
                val news = if (_selectedCategory.value != null) {
                    newsRepository.getNewsByCategory(
                        categoryId = _selectedCategory.value!!.id,
                        page = nextPage
                    )
                } else {
                    newsRepository.getLatestNews(page = nextPage)
                }
                
                if (news.isNotEmpty()) {
                    _newsArticles.value = _newsArticles.value + news
                    currentPage = nextPage
                } else {
                    hasMorePages = false
                }
                
                _uiState.value = _uiState.value.copy(isLoadingMore = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingMore = false,
                    error = e.message ?: "Failed to load more news"
                )
            }
        }
    }
    
    /**
     * Select a category and load its news
     */
    fun selectCategory(category: NewsCategory?) {
        _selectedCategory.value = category
        
        // Reset pagination
        currentPage = 1
        hasMorePages = true
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val news = if (category != null) {
                    newsRepository.getNewsByCategory(categoryId = category.id, page = 1)
                } else {
                    newsRepository.getLatestNews(page = 1)
                }
                
                _newsArticles.value = news
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load category news"
                )
            }
        }
    }
    
    /**
     * Search news
     */
    fun searchNews(query: String) {
        _searchQuery.value = query
        
        if (query.isBlank()) {
            // Reset to current category selection
            selectCategory(_selectedCategory.value)
            return
        }
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            try {
                val searchResult = newsRepository.searchNews(query = query, page = 1)
                _newsArticles.value = searchResult.results
                
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to search news"
                )
            }
        }
    }
    
    /**
     * Refresh news
     */
    fun refreshNews() {
        // Reset pagination
        currentPage = 1
        hasMorePages = true
        
        // Reload current selection
        selectCategory(_selectedCategory.value)
    }
    
    /**
     * Clear search
     */
    fun clearSearch() {
        _searchQuery.value = ""
        selectCategory(_selectedCategory.value)
    }
    
    /**
     * Clear error
     */
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

// UI State data class
data class HomeUiState(
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null
)

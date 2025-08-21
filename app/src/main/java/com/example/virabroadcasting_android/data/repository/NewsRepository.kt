package com.example.virabroadcasting_android.data.repository

import com.example.virabroadcasting_android.data.api.WordPressApiService
import com.example.virabroadcasting_android.data.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NewsRepository(
    private val apiService: WordPressApiService
) {
    
    // Cache for posts
    private val postsCache = mutableMapOf<String, List<NewsArticle>>()
    private val categoriesCache = mutableListOf<NewsCategory>()
    
    // Date formatter for WordPress dates
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val displayFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    
    /**
     * Get latest news articles
     */
    suspend fun getLatestNews(page: Int = 1, perPage: Int = 20): List<NewsArticle> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getPosts(page = page, perPage = perPage)
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                val articles = posts.map { it.toNewsArticle() }
                
                // Cache the results
                val cacheKey = "latest_$page"
                postsCache[cacheKey] = articles
                
                articles
            } else {
                // Return cached data if available
                postsCache["latest_$page"] ?: emptyList()
            }
        } catch (e: Exception) {
            // Return cached data if available
            postsCache["latest_$page"] ?: emptyList()
        }
    }
    
    /**
     * Get news by category
     */
    suspend fun getNewsByCategory(categoryId: Int, page: Int = 1): List<NewsArticle> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getPostsByCategory(categoryId = categoryId, page = page)
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                val articles = posts.map { it.toNewsArticle() }
                
                // Cache the results
                val cacheKey = "category_${categoryId}_$page"
                postsCache[cacheKey] = articles
                
                articles
            } else {
                // Return cached data if available
                postsCache["category_${categoryId}_$page"] ?: emptyList()
            }
        } catch (e: Exception) {
            // Return cached data if available
            postsCache["category_${categoryId}_$page"] ?: emptyList()
        }
    }
    
    /**
     * Search news articles
     */
    suspend fun searchNews(query: String, page: Int = 1): SearchResult = withContext(Dispatchers.IO) {
        try {
            val response = apiService.searchPosts(query = query, page = page)
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                val articles = posts.map { it.toNewsArticle() }
                
                // Get total count from headers (WordPress API provides this)
                val totalPosts = response.headers()["X-WP-Total"]?.toIntOrNull() ?: 0
                val totalPages = response.headers()["X-WP-TotalPages"]?.toIntOrNull() ?: 0
                
                SearchResult(
                    query = query,
                    results = articles,
                    totalResults = totalPosts,
                    currentPage = page,
                    totalPages = totalPages
                )
            } else {
                SearchResult(
                    query = query,
                    results = emptyList(),
                    totalResults = 0,
                    currentPage = page,
                    totalPages = 0
                )
            }
        } catch (e: Exception) {
            SearchResult(
                query = query,
                results = emptyList(),
                totalResults = 0,
                currentPage = page,
                totalPages = 0
            )
        }
    }
    
    /**
     * Get all categories
     */
    suspend fun getCategories(): List<NewsCategory> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getCategories()
            if (response.isSuccessful) {
                val categories = response.body() ?: emptyList()
                val newsCategories = categories.map { it.toNewsCategory() }
                
                // Update cache
                categoriesCache.clear()
                categoriesCache.addAll(newsCategories)
                
                newsCategories
            } else {
                // Return cached data if available
                categoriesCache.ifEmpty { emptyList() }
            }
        } catch (e: Exception) {
            // Return cached data if available
            categoriesCache.ifEmpty { emptyList() }
        }
    }
    
    /**
     * Get featured posts
     */
    suspend fun getFeaturedPosts(): List<NewsArticle> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getFeaturedPosts()
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                posts.map { it.toNewsArticle() }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    /**
     * Clear cache
     */
    fun clearCache() {
        postsCache.clear()
        categoriesCache.clear()
    }
    
    /**
     * Convert WordPress post to NewsArticle
     */
    private fun WordPressPost.toNewsArticle(): NewsArticle {
        val publishDate = try {
            displayFormatter.format(dateFormatter.parse(date) ?: Date())
        } catch (e: Exception) {
            date
        }
        
        val lastModified = try {
            displayFormatter.format(dateFormatter.parse(modified) ?: Date())
        } catch (e: Exception) {
            modified
        }
        
        return NewsArticle(
            id = id.toString(),
            title = displayTitle,
            content = displayContent,
            excerpt = displayExcerpt,
            imageUrl = featuredImageUrl,
            category = categoryNames.firstOrNull() ?: "Uncategorized",
            author = authorName ?: "Unknown Author",
            publishDate = publishDate,
            lastModified = lastModified,
            url = link
        )
    }
    
    /**
     * Convert WordPress category to NewsCategory
     */
    private fun Category.toNewsCategory(): NewsCategory {
        return NewsCategory(
            id = id,
            name = name,
            slug = slug,
            description = description,
            postCount = count
        )
    }
}

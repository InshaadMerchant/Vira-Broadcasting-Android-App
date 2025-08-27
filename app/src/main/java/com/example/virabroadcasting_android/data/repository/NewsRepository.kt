package com.example.virabroadcasting_android.data.repository

import com.example.virabroadcasting_android.data.api.WordPressApiService
import com.example.virabroadcasting_android.data.models.*
import com.example.virabroadcasting_android.config.WordPressConfig
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
            val response = apiService.getPosts(page = page, perPage = perPage, embed = true)
            
            if (response.isSuccessful) {
                val posts = response.body() ?: emptyList()
                println("🔍 DEBUG: Received ${posts.size} posts from API")
                println("🔍 DEBUG: First post ID: ${posts.firstOrNull()?.id}")
                println("🔍 DEBUG: First post title: ${posts.firstOrNull()?.displayTitle}")
                
                // Raw API response debugging
                println("🔍 DEBUG: ===== RAW API RESPONSE DEBUG =====")
                posts.take(2).forEachIndexed { index, post ->
                    println("🔍 DEBUG: Post $index - Raw data:")
                    println("🔍 DEBUG:   - ID: ${post.id}")
                    println("🔍 DEBUG:   - Title: ${post.displayTitle}")
                    println("🔍 DEBUG:   - featuredMedia: ${post.featuredMedia}")
                    println("🔍 DEBUG:   - Has _embedded: ${post._embedded != null}")
                    println("🔍 DEBUG:   - _embedded.wpFeaturedmedia size: ${post._embedded?.wpFeaturedmedia?.size ?: 0}")
                    if (post._embedded?.wpFeaturedmedia?.isNotEmpty() == true) {
                        post._embedded.wpFeaturedmedia?.forEach { media ->
                            println("🔍 DEBUG:   - Media ID: ${media.id}, Source URL: ${media.sourceUrl}")
                        }
                    }
                    println("🔍 DEBUG:   - Raw content length: ${post.content.rendered.length}")
                    println("🔍 DEBUG: ---")
                }
                println("🔍 DEBUG: ===== END RAW API RESPONSE DEBUG =====")
                
                // Enhanced debugging for first few posts
                posts.take(3).forEachIndexed { index, post ->
                    println("🔍 DEBUG: Post $index - ID: ${post.id}, Title: ${post.displayTitle}")
                    println("🔍 DEBUG: Post $index - featuredMedia: ${post.featuredMedia}")
                    println("🔍 DEBUG: Post $index - Has _embedded: ${post._embedded != null}")
                    if (post._embedded?.wpFeaturedmedia?.isNotEmpty() == true) {
                        println("🔍 DEBUG: Post $index - Found ${post._embedded.wpFeaturedmedia?.size} embedded media")
                        post._embedded.wpFeaturedmedia?.forEach { media ->
                            println("🔍 DEBUG: Post $index - Media ID: ${media.id}, Source URL: ${media.sourceUrl}")
                        }
                    } else {
                        println("🔍 DEBUG: Post $index - NO embedded media found")
                    }
                    println("🔍 DEBUG: ---")
                }
                
                val articles = mutableListOf<NewsArticle>()
                for (post in posts) {
                    articles.add(post.toNewsArticle())
                }
                println("🔍 DEBUG: Converted to ${articles.size} articles")
                
                // Debug image URLs
                articles.forEachIndexed { index, article ->
                    if (article.imageUrl != null) {
                        println("🔍 DEBUG: Article $index has image: ${article.imageUrl}")
                    } else {
                        println("🔍 DEBUG: Article $index has NO image")
                    }
                }
                
                // Cache the results
                val cacheKey = "latest_$page"
                postsCache[cacheKey] = articles
                
                articles
            } else {
                println("❌ DEBUG: API response not successful: ${response.code()}")
                // Return cached data if available
                postsCache["latest_$page"] ?: emptyList()
            }
        } catch (e: Exception) {
            println("❌ DEBUG: Exception in getLatestNews: ${e.message}")
            e.printStackTrace()
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
                val articles = mutableListOf<NewsArticle>()
                for (post in posts) {
                    articles.add(post.toNewsArticle())
                }
                
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
                val articles = mutableListOf<NewsArticle>()
                for (post in posts) {
                    articles.add(post.toNewsArticle())
                }
                
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
                val articles = mutableListOf<NewsArticle>()
                for (post in posts) {
                    articles.add(post.toNewsArticle())
                }
                articles
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
    private suspend fun WordPressPost.toNewsArticle(): NewsArticle {
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
        
        // Enhanced debugging for image loading
        println("🔍 DEBUG: ===== POST ${id} IMAGE DEBUG =====")
        println("🔍 DEBUG: Raw featuredMedia value: $featuredMedia")
        println("🔍 DEBUG: Has _embedded: ${_embedded != null}")
        println("🔍 DEBUG: _embedded.wpFeaturedmedia size: ${_embedded?.wpFeaturedmedia?.size ?: 0}")
        
        // Try multiple approaches to get the image URL
        var imageUrl: String? = null
        
        // Approach 1: Try to get from embedded media first
        if (_embedded?.wpFeaturedmedia?.isNotEmpty() == true) {
            imageUrl = _embedded?.wpFeaturedmedia?.firstOrNull()?.sourceUrl
            println("🔍 DEBUG: ✅ Got image from embedded media: $imageUrl")
        } else {
            println("🔍 DEBUG: ❌ No embedded media found")
        }
        
        // Approach 2: If no embedded media, try direct media API call
        if (imageUrl.isNullOrBlank() && featuredMedia > 0) {
            try {
                println("🔍 DEBUG: 🔄 Attempting direct media fetch for ID: $featuredMedia")
                val mediaResponse = apiService.getMedia(featuredMedia)
                if (mediaResponse.isSuccessful) {
                    val media = mediaResponse.body()
                    imageUrl = media?.sourceUrl
                    println("🔍 DEBUG: ✅ Direct media fetch successful: $imageUrl")
                } else {
                    println("🔍 DEBUG: ❌ Direct media fetch failed with code: ${mediaResponse.code()}")
                }
            } catch (e: Exception) {
                println("🔍 DEBUG: ❌ Exception in direct media fetch: ${e.message}")
                e.printStackTrace()
            }
        } else if (featuredMedia <= 0) {
            println("🔍 DEBUG: ❌ featuredMedia ID is 0 or negative, cannot fetch media")
        }
        
        // Approach 3: Try to construct media URL manually if we have the media ID
        if (imageUrl.isNullOrBlank() && featuredMedia > 0) {
            // Try multiple common WordPress image URL patterns
            val possibleUrls = listOf(
                "${WordPressConfig.WORDPRESS_BASE_URL}/wp-content/uploads/${featuredMedia}.jpg",
                "${WordPressConfig.WORDPRESS_BASE_URL}/wp-content/uploads/${featuredMedia}.png",
                "${WordPressConfig.WORDPRESS_BASE_URL}/wp-content/uploads/${featuredMedia}.webp",
                "${WordPressConfig.WORDPRESS_BASE_URL}/wp-content/uploads/${featuredMedia}.jpeg"
            )
            
            println("🔍 DEBUG: 🔄 Trying manual URL construction for ID: $featuredMedia")
            for (url in possibleUrls) {
                println("🔍 DEBUG: 🔄 Testing URL: $url")
                // For now, just use the first URL as a fallback
                // In a production app, you might want to test if the URL actually exists
                imageUrl = url
                break
            }
        }
        
        // Approach 4: If still no image, try to extract from content
        if (imageUrl.isNullOrBlank()) {
            println("🔍 DEBUG: 🔄 Attempting to extract image from content HTML")
            // Look for img tags in the content
            val imgRegex = Regex("<img[^>]+src=[\"']([^\"']+)[\"'][^>]*>")
            val matchResult = imgRegex.find(content.rendered)
            if (matchResult != null) {
                imageUrl = matchResult.groupValues[1]
                println("🔍 DEBUG: ✅ Extracted image from content: $imageUrl")
            } else {
                println("🔍 DEBUG: ❌ No image tags found in content")
            }
        }
        
        // Debug final image URL
        println("🔍 DEBUG: 🎯 Final image URL: $imageUrl")
        println("🔍 DEBUG: ===== END POST ${id} DEBUG =====")
        
        return NewsArticle(
            id = id.toString(),
            title = displayTitle,
            content = displayContent,
            excerpt = displayExcerpt,
            imageUrl = imageUrl,
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
        val categoryColor = when (name.lowercase()) {
            "sports" -> androidx.compose.ui.graphics.Color(0xFF4CAF50) // Green
            "politics" -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Blue
            "latest news" -> androidx.compose.ui.graphics.Color(0xFFF44336) // Red
            "business" -> androidx.compose.ui.graphics.Color(0xFFFFEB3B) // Yellow
            "entertainment" -> androidx.compose.ui.graphics.Color(0xFF9C27B0) // Purple
            "health and wellness" -> androidx.compose.ui.graphics.Color(0xFF00BCD4) // Cyan
            "science" -> androidx.compose.ui.graphics.Color(0xFF795548) // Brown
            "environment" -> androidx.compose.ui.graphics.Color(0xFF8BC34A) // Light Green
            "arts" -> androidx.compose.ui.graphics.Color(0xFFFF9800) // Orange
            else -> androidx.compose.ui.graphics.Color(0xFF9E9E9E) // Gray for others
        }
        
        return NewsCategory(
            id = id,
            name = name,
            slug = slug,
            description = description,
            postCount = count,
            color = categoryColor
        )
    }
}


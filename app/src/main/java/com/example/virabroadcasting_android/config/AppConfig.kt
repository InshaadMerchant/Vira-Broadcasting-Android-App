package com.example.virabroadcasting_android.config

object AppConfig {
    
    // WordPress Configuration
    // TODO: Replace with your actual WordPress site URL
    const val WORDPRESS_BASE_URL = "https://your-wordpress-site.com"
    const val WORDPRESS_API_BASE_URL = "$WORDPRESS_BASE_URL/wp-json/"
    
    // API Configuration
    const val API_TIMEOUT_SECONDS = 30L
    const val DEFAULT_PAGE_SIZE = 20
    const val MAX_PAGE_SIZE = 100
    
    // Cache Configuration
    const val CACHE_DURATION_MINUTES = 15L
    const val MAX_CACHE_SIZE = 100
    
    // News Configuration
    const val FEATURED_POSTS_LIMIT = 5
    const val LATEST_NEWS_LIMIT = 20
    const val SEARCH_RESULTS_LIMIT = 20
    
    // UI Configuration
    const val PULL_TO_REFRESH_ENABLED = true
    const val INFINITE_SCROLL_ENABLED = true
    const val ERROR_RETRY_ATTEMPTS = 3
    
    // Debug Configuration
    const val LOG_NETWORK_REQUESTS = true
    const val SHOW_DEBUG_INFO = false
    
    /**
     * Get the full API endpoint URL
     */
    fun getApiEndpoint(endpoint: String): String {
        return WORDPRESS_API_BASE_URL + endpoint
    }
    
    /**
     * Get the full WordPress post URL
     */
    fun getPostUrl(postSlug: String): String {
        return "$WORDPRESS_BASE_URL/$postSlug"
    }
    
    /**
     * Get the full WordPress category URL
     */
    fun getCategoryUrl(categorySlug: String): String {
        return "$WORDPRESS_BASE_URL/category/$categorySlug"
    }
}

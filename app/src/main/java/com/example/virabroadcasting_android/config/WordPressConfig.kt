package com.example.virabroadcasting_android.config

object WordPressConfig {
    
    // ========================================
    // WORDPRESS SITE CONFIGURATION
    // ========================================
    
    // TODO: REPLACE WITH YOUR ACTUAL WORDPRESS SITE URL
    // Remove the "https://" and trailing slash - just the domain
    const val WORDPRESS_DOMAIN = "staging-5601-virabroadcasting.wpcomstaging.com"
    
    // Protocol (http or https)
    const val WORDPRESS_PROTOCOL = "https"
    
    // ========================================
    // API ENDPOINTS
    // ========================================
    
    val WORDPRESS_BASE_URL: String
        get() = "$WORDPRESS_PROTOCOL://$WORDPRESS_DOMAIN"
    
    val WORDPRESS_API_BASE_URL: String
        get() = "$WORDPRESS_BASE_URL/wp-json/"
    
    // ========================================
    // API CONFIGURATION
    // ========================================
    
    // WordPress REST API version
    const val API_VERSION = "v2"
    
    // Default parameters
    const val DEFAULT_POSTS_PER_PAGE = 20
    const val MAX_POSTS_PER_PAGE = 100
    
    // ========================================
    // FEATURE FLAGS
    // ========================================
    
    // Enable/disable features for testing
    const val ENABLE_IMAGE_LOADING = true
    const val ENABLE_CATEGORY_FILTERING = true
    const val ENABLE_SEARCH = true
    const val ENABLE_PAGINATION = true
    
    // ========================================
    // DEBUG CONFIGURATION
    // ========================================
    
    // Enable detailed logging
    const val ENABLE_API_LOGGING = true
    const val ENABLE_CACHE_LOGGING = false
    
    // ========================================
    // HELPER FUNCTIONS
    // ========================================
    
    /**
     * Get the full API endpoint URL
     */
    fun getApiEndpoint(endpoint: String): String {
        return WORDPRESS_API_BASE_URL + API_VERSION + "/" + endpoint
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
    
    /**
     * Get the full WordPress author URL
     */
    fun getAuthorUrl(authorSlug: String): String {
        return "$WORDPRESS_BASE_URL/author/$authorSlug"
    }
    
    /**
     * Validate WordPress URL configuration
     */
    fun validateConfiguration(): Boolean {
        return WORDPRESS_DOMAIN == "staging-5601-virabroadcasting.wpcomstaging.com" &&
               WORDPRESS_DOMAIN.isNotBlank()
    }
    
    /**
     * Get configuration status message
     */
    fun getConfigurationStatus(): String {
        return if (validateConfiguration()) {
            "✅ WordPress configured: $WORDPRESS_BASE_URL"
        } else {
            "❌ Please configure WordPress domain in WordPressConfig.kt"
        }
    }
}

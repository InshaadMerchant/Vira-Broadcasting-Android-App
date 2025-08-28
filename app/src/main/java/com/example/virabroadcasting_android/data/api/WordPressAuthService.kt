package com.example.virabroadcasting_android.data.api

import retrofit2.Response
import retrofit2.http.*

interface WordPressAuthService {
    
    /**
     * Test if WordPress REST API is accessible
     */
    @GET("wp/v2/")
    suspend fun testApi(): Response<Map<String, Any>>
    
    /**
     * Authenticate user with WordPress using Application Password
     * This endpoint requires Basic Auth with username:application_password
     */
    @GET("wp/v2/users/me")
    suspend fun authenticateUser(
        @Header("Authorization") authorization: String
    ): Response<WordPressUser>
    
    /**
     * Get user profile data
     */
    @GET("wp/v2/users/{userId}")
    suspend fun getUserProfile(
        @Path("userId") userId: Int
    ): Response<WordPressUser>
    
    /**
     * Update user profile data
     */
    @PUT("wp/v2/users/{userId}")
    suspend fun updateUserProfile(
        @Path("userId") userId: Int,
        @Body userData: WordPressUserUpdate
    ): Response<WordPressUser>
}

/**
 * WordPress User data model
 * Maps to WordPress user fields
 */
data class WordPressUser(
    val id: Int,
    val name: String,           // display_name
    val email: String,          // user_email
    val username: String,       // user_login
    val registered: String,     // user_registered
    val roles: List<String>,    // user roles
    val meta: Map<String, Any>? = null  // custom user meta fields
)

/**
 * WordPress User Update data model
 * For updating user profile information
 */
data class WordPressUserUpdate(
    val name: String? = null,
    val email: String? = null,
    val meta: Map<String, Any>? = null
)

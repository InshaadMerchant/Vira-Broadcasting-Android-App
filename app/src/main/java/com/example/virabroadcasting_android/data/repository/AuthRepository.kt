package com.example.virabroadcasting_android.data.repository

import android.util.Base64
import com.example.virabroadcasting_android.data.api.WordPressAuthService
import com.example.virabroadcasting_android.data.api.WordPressUser
import com.example.virabroadcasting_android.data.api.WordPressUserUpdate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log
import retrofit2.Response

/**
 * Repository for handling WordPress user authentication and profile management
 */
class AuthRepository(
    private val authService: WordPressAuthService
) {
    
    // Current authenticated user
    private val _currentUser = MutableStateFlow<WordPressUser?>(null)
    val currentUser: StateFlow<WordPressUser?> = _currentUser.asStateFlow()
    
    // Authentication state
    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()
    
    // Loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    // Error state
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    /**
     * Test if WordPress REST API is accessible
     */
    suspend fun testWordPressApi(): Boolean {
        try {
            println("🔐 DEBUG: ===== TESTING WORDPRESS API =====")
            
            val response: Response<Map<String, Any>> = authService.testApi()
            
            println("🔐 DEBUG: API Test Response Code: ${response.code()}")
            println("🔐 DEBUG: API Test Is Successful: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                val body = response.body()
                println("🔐 DEBUG: ✅ WordPress API is accessible")
                println("🔐 DEBUG: API Response: $body")
                return true
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                println("🔐 DEBUG: ❌ WordPress API test failed - HTTP ${response.code()}")
                println("🔐 DEBUG: Error body: $errorBody")
                return false
            }
            
        } catch (e: Exception) {
            println("🔐 DEBUG: ❌ WordPress API test exception: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
    
    /**
     * Authenticate user with WordPress using Application Password
     * @param username WordPress username
     * @param applicationPassword WordPress application password (not regular password)
     * @return true if authentication successful, false otherwise
     */
    suspend fun login(username: String, applicationPassword: String): Boolean {
        try {
            _isLoading.value = true
            _error.value = null
            
            // First, test if WordPress API is accessible
            println("🔐 DEBUG: Testing WordPress API accessibility...")
            val apiTestResult = testWordPressApi()
            if (!apiTestResult) {
                _error.value = "WordPress API is not accessible. Please check your site configuration."
                return false
            }
            
                    // Create Basic Auth header with username:application_password
        // Remove spaces from application password (WordPress displays them with spaces for readability)
        val cleanApplicationPassword = applicationPassword.replace(" ", "")
        Log.d("AuthRepository", "🔐 DEBUG: Cleaned Application Password: $cleanApplicationPassword")
                val credentials = "$username:$cleanApplicationPassword"
        Log.d("AuthRepository", "🔐 DEBUG: Final credentials: $credentials")
        val encodedCredentials = Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val authorizationHeader = "Basic $encodedCredentials"
            
            println("🔐 DEBUG: ===== AUTHENTICATION ATTEMPT =====")
            println("🔐 DEBUG: Username: $username")
            println("🔐 DEBUG: Application Password: $applicationPassword")
            println("🔐 DEBUG: Raw credentials: $credentials")
            println("🔐 DEBUG: Encoded credentials length: ${encodedCredentials.length}")
            println("🔐 DEBUG: Authorization header: $authorizationHeader")
            println("🔐 DEBUG: =================================")
            
            // Call WordPress authentication endpoint
            val response: Response<WordPressUser> = authService.authenticateUser(authorizationHeader)
            
            println("🔐 DEBUG: ===== API RESPONSE =====")
            println("🔐 DEBUG: HTTP Status Code: ${response.code()}")
            println("🔐 DEBUG: Is Successful: ${response.isSuccessful}")
            println("🔐 DEBUG: Response Headers: ${response.headers()}")
            
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    _currentUser.value = user
                    _isAuthenticated.value = true
                    println("🔐 DEBUG: ✅ User authenticated successfully: ${user.name}")
                    println("🔐 DEBUG: User ID: ${user.id}")
                    println("🔐 DEBUG: User Email: ${user.email}")
                    println("🔐 DEBUG: User Roles: ${user.roles}")
                    return true
                } else {
                    _error.value = "Invalid response from server"
                    println("🔐 DEBUG: ❌ Authentication failed - null response body")
                    return false
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                _error.value = "Authentication failed: $errorBody"
                println("🔐 DEBUG: ❌ Authentication failed - HTTP ${response.code()}")
                println("🔐 DEBUG: Error body: $errorBody")
                return false
            }
            
        } catch (e: Exception) {
            _error.value = "Network error: ${e.message}"
            println("🔐 DEBUG: ❌ Authentication exception: ${e.message}")
            e.printStackTrace()
        } finally {
            _isLoading.value = false
        }
        
        return false
    }
    
    /**
     * Get current user profile from WordPress
     */
    suspend fun refreshUserProfile(): Boolean {
        val user = _currentUser.value ?: return false
        
        try {
            _isLoading.value = true
            _error.value = null
            
            val response: Response<WordPressUser> = authService.getUserProfile(user.id)
            
            if (response.isSuccessful) {
                val updatedUser = response.body()
                if (updatedUser != null) {
                    _currentUser.value = updatedUser
                    println("🔐 DEBUG: User profile refreshed: ${updatedUser.name}")
                    return true
                }
            } else {
                _error.value = "Failed to refresh profile"
                println("🔐 DEBUG: Profile refresh failed - HTTP ${response.code()}")
            }
            
        } catch (e: Exception) {
            _error.value = "Network error: ${e.message}"
            println("🔐 DEBUG: Profile refresh exception: ${e.message}")
        } finally {
            _isLoading.value = false
        }
        
        return false
    }
    
    /**
     * Update user profile in WordPress
     */
    suspend fun updateUserProfile(
        name: String? = null,
        email: String? = null,
        meta: Map<String, Any>? = null
    ): Boolean {
        val user = _currentUser.value ?: return false
        
        try {
            _isLoading.value = true
            _error.value = null
            
            val updateData = WordPressUserUpdate(
                name = name,
                email = email,
                meta = meta
            )
            
            val response: Response<WordPressUser> = authService.updateUserProfile(user.id, updateData)
            
            if (response.isSuccessful) {
                val updatedUser = response.body()
                if (updatedUser != null) {
                    _currentUser.value = updatedUser
                    println("🔐 DEBUG: User profile updated: ${updatedUser.name}")
                    return true
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                _error.value = "Update failed: $errorBody"
                println("🔐 DEBUG: Profile update failed - HTTP ${response.code()}: $errorBody")
            }
            
        } catch (e: Exception) {
            _error.value = "Network error: ${e.message}"
            println("🔐 DEBUG: Profile update exception: ${e.message}")
        } finally {
            _isLoading.value = false
        }
        
        return false
    }
    
    /**
     * Sign out user (clear local session)
     */
    fun signOut() {
        _currentUser.value = null
        _isAuthenticated.value = false
        _error.value = null
        println("🔐 DEBUG: User signed out")
    }
    
    /**
     * Clear any error messages
     */
    fun clearError() {
        _error.value = null
    }
}

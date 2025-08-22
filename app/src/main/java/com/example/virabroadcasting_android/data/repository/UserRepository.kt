package com.example.virabroadcasting_android.data.repository

import com.example.virabroadcasting_android.data.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object UserRepository {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun createUser(fullName: String, email: String, phone: String, location: String): User {
        val user = User(
            id = System.currentTimeMillis().toString(),
            fullName = fullName,
            email = email,
            phone = phone,
            location = location
        )
        println("🔐 DEBUG: Creating user: ${user.fullName}, ${user.email}")
        _currentUser.value = user
        return user
    }

    fun updateUser(updatedUser: User) {
        _currentUser.value = updatedUser
    }

    fun updateUserProfile(
        fullName: String? = null,
        email: String? = null,
        phone: String? = null,
        location: String? = null
    ) {
        val currentUser = _currentUser.value ?: return
        val updatedUser = currentUser.copy(
            fullName = fullName ?: currentUser.fullName,
            email = email ?: currentUser.email,
            phone = phone ?: currentUser.phone,
            location = location ?: currentUser.location,
            lastUpdated = System.currentTimeMillis()
        )
        println("🔐 DEBUG: Updating user profile: ${updatedUser.fullName}, ${updatedUser.email}")
        _currentUser.value = updatedUser
    }

    fun clearUser() {
        _currentUser.value = null
    }

    fun getUser(): User? {
        val user = _currentUser.value
        println("🔐 DEBUG: Getting user: ${user?.fullName ?: "null"}")
        return user
    }
}

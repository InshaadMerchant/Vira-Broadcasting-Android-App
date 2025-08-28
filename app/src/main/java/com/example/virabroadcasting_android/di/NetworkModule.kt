package com.example.virabroadcasting_android.di

import com.example.virabroadcasting_android.config.WordPressConfig
import com.example.virabroadcasting_android.data.api.WordPressApiService
import com.example.virabroadcasting_android.data.api.WordPressAuthService
import com.example.virabroadcasting_android.data.repository.NewsRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    
    // Base URL from WordPressConfig
    private val BASE_URL = WordPressConfig.WORDPRESS_API_BASE_URL
    
    // OkHttp Client with logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (WordPressConfig.ENABLE_API_LOGGING) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    // WordPress API Service
    val wordPressApiService: WordPressApiService = retrofit.create(WordPressApiService::class.java)
    
    // WordPress Authentication Service
    val wordPressAuthService: WordPressAuthService = retrofit.create(WordPressAuthService::class.java)
    
    // News Repository
    val newsRepository: NewsRepository = NewsRepository(wordPressApiService)
    
    /**
     * Check if WordPress is properly configured
     */
    fun isWordPressConfigured(): Boolean {
        return WordPressConfig.validateConfiguration()
    }
    
    /**
     * Get configuration status
     */
    fun getConfigurationStatus(): String {
        return WordPressConfig.getConfigurationStatus()
    }
}

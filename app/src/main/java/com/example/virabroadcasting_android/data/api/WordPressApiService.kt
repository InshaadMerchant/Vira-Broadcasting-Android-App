package com.example.virabroadcasting_android.data.api

import com.example.virabroadcasting_android.data.models.WordPressPost
import com.example.virabroadcasting_android.data.models.Category
import com.example.virabroadcasting_android.data.models.Media
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WordPressApiService {
    
    // Get all posts with pagination
    @GET("wp/v2/posts")
    suspend fun getPosts(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("_embed") embed: Boolean = true,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("status") status: String = "publish"
    ): Response<List<WordPressPost>>
    
    // Get posts by category
    @GET("wp/v2/posts")
    suspend fun getPostsByCategory(
        @Query("categories") categoryId: Int,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("_embed") embed: Boolean = true,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc"
    ): Response<List<WordPressPost>>
    
    // Get posts by search query
    @GET("wp/v2/posts")
    suspend fun searchPosts(
        @Query("search") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("_embed") embed: Boolean = true,
        @Query("orderby") orderBy: String = "relevance"
    ): Response<List<WordPressPost>>
    
    // Get featured posts (sticky posts)
    @GET("wp/v2/posts")
    suspend fun getFeaturedPosts(
        @Query("sticky") sticky: Boolean = true,
        @Query("_embed") embed: Boolean = true,
        @Query("per_page") perPage: Int = 5
    ): Response<List<WordPressPost>>
    
    // Get latest posts
    @GET("wp/v2/posts")
    suspend fun getLatestPosts(
        @Query("_embed") embed: Boolean = true,
        @Query("per_page") perPage: Int = 20,
        @Query("orderby") orderBy: String = "date",
        @Query("order") order: String = "desc",
        @Query("status") status: String = "publish"
    ): Response<List<WordPressPost>>
    
    // Get all categories
    @GET("wp/v2/categories")
    suspend fun getCategories(
        @Query("per_page") perPage: Int = 100,
        @Query("orderby") orderBy: String = "count",
        @Query("order") order: String = "desc"
    ): Response<List<Category>>
    
    // Get category by ID
    @GET("wp/v2/categories/{id}")
    suspend fun getCategory(@retrofit2.http.Path("id") id: Int): Response<Category>
    
    // Get media by ID
    @GET("wp/v2/media/{id}")
    suspend fun getMedia(@retrofit2.http.Path("id") id: Int): Response<Media>
    
    // Get posts by author
    @GET("wp/v2/posts")
    suspend fun getPostsByAuthor(
        @Query("author") authorId: Int,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("_embed") embed: Boolean = true
    ): Response<List<WordPressPost>>
    
    // Get posts by tag
    @GET("wp/v2/posts")
    suspend fun getPostsByTag(
        @Query("tags") tagId: Int,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("_embed") embed: Boolean = true
    ): Response<List<WordPressPost>>
    
    // Get posts by date range
    @GET("wp/v2/posts")
    suspend fun getPostsByDate(
        @Query("after") afterDate: String,
        @Query("before") beforeDate: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10,
        @Query("_embed") embed: Boolean = true
    ): Response<List<WordPressPost>>
}

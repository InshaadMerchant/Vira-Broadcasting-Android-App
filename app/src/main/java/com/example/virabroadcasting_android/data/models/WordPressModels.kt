package com.example.virabroadcasting_android.data.models

import com.google.gson.annotations.SerializedName

// WordPress Post Model
data class WordPressPost(
    val id: Int,
    val date: String,
    val dateGmt: String,
    val guid: Guid,
    val modified: String,
    val modifiedGmt: String,
    val slug: String,
    val status: String,
    val type: String,
    val link: String,
    val title: Title,
    val content: Content,
    val excerpt: Excerpt,
    val author: Int,
    @SerializedName("featured_media")
    val featuredMedia: Int,
    @SerializedName("comment_status")
    val commentStatus: String,
    @SerializedName("ping_status")
    val pingStatus: String,
    val sticky: Boolean,
    val template: String,
    val format: String,
    val meta: Map<String, Any>? = null,
    val categories: List<Int>,
    val tags: List<Int>,
    @SerializedName("_embedded")
    val _embedded: Embedded? = null
) {
    // Helper properties for easy access
    val displayTitle: String get() = title.rendered
    val displayContent: String get() = content.rendered
    val displayExcerpt: String get() = excerpt.rendered
    val featuredImageUrl: String? get() = _embedded?.wpFeaturedmedia?.firstOrNull()?.sourceUrl
    val authorName: String? get() = _embedded?.author?.firstOrNull()?.name
    val categoryNames: List<String> get() = _embedded?.wpTerm?.firstOrNull()?.map { it.name } ?: emptyList()
}

data class Guid(
    val rendered: String
)

data class Title(
    val rendered: String
)

data class Content(
    val rendered: String,
    val protected: Boolean
)

data class Excerpt(
    val rendered: String,
    val protected: Boolean
)

data class Embedded(
    val author: List<Author>? = null,
    val wpFeaturedmedia: List<FeaturedMedia>? = null,
    @SerializedName("wp:term")
    val wpTerm: List<List<Category>>? = null
)

data class Author(
    val id: Int,
    val name: String,
    val url: String,
    val description: String,
    val link: String,
    val slug: String,
    val avatarUrls: Map<String, String>? = null
)

data class FeaturedMedia(
    val id: Int,
    val date: String,
    val slug: String,
    val type: String,
    val link: String,
    val title: Title,
    val sourceUrl: String,
    val altText: String? = null
)

data class Category(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String,
    val count: Int
)

// Media model for direct media fetching
data class Media(
    val id: Int,
    val date: String,
    val slug: String,
    val type: String,
    val link: String,
    val title: Title,
    val sourceUrl: String,
    val altText: String? = null
)

// WordPress API Response Models
data class WordPressApiResponse(
    val posts: List<WordPressPost>,
    val totalPosts: Int,
    val totalPages: Int
)

// Local News Model for App
data class NewsArticle(
    val id: String,
    val title: String,
    val content: String,
    val excerpt: String,
    val imageUrl: String?,
    val category: String,
    val author: String,
    val publishDate: String,
    val lastModified: String,
    val url: String,
    val isBookmarked: Boolean = false,
    val isRead: Boolean = false
)

// Category Model
data class NewsCategory(
    val id: Int,
    val name: String,
    val slug: String,
    val description: String,
    val postCount: Int,
    val isSelected: Boolean = false,
    val color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Gray
)

// Search Result Model
data class SearchResult(
    val query: String,
    val results: List<NewsArticle>,
    val totalResults: Int,
    val currentPage: Int,
    val totalPages: Int
)

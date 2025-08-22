package com.example.virabroadcasting_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.SpanStyle
import android.content.Intent
import android.text.Html as AndroidHtml
import androidx.compose.ui.platform.LocalContext

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get data passed from the intent
        val title = intent.getStringExtra("title") ?: "No Title"
        val content = intent.getStringExtra("content") ?: "No Content"
        val author = intent.getStringExtra("author") ?: "Unknown Author"
        val publishDate = intent.getStringExtra("publishDate") ?: ""
        val category = intent.getStringExtra("category") ?: "Uncategorized"
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""

        setContent {
            DetailScreen(
                title = title,
                content = content,
                author = author,
                publishDate = publishDate,
                category = category,
                imageUrl = imageUrl,
                onBackClick = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    title: String,
    content: String,
    author: String,
    publishDate: String,
    category: String,
    imageUrl: String,
    onBackClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    
    // Handle back button press
    BackHandler {
        onBackClick()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Article") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "$title\n\n$content")
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share Article"))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        // Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Meta information
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Author and Date
                Column {
                    Text(
                        text = "By $author",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (publishDate.isNotEmpty()) {
                        Text(
                            text = publishDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Category
                if (category.isNotEmpty() && category != "Uncategorized") {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = category,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Divider(
                color = MaterialTheme.colorScheme.outline,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Content
            Text(
                text = buildAnnotatedString {
                    // Convert HTML content to plain text with basic formatting
                    val plainText = AndroidHtml.fromHtml(content, AndroidHtml.FROM_HTML_MODE_COMPACT)
                    withStyle(SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(plainText.toString())
                    }
                },
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.4,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }
    }
}

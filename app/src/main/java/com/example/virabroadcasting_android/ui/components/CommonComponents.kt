package com.example.virabroadcasting_android.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.virabroadcasting_android.ui.theme.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.virabroadcasting_android.R

@Composable
fun ViraHeader(
    title: String,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null,
    showProfileIcon: Boolean = false,
    onProfileClick: (() -> Unit)? = null,
    showNotificationIcon: Boolean = false,
    onNotificationClick: (() -> Unit)? = null,
    showFilterIcon: Boolean = false,
    onFilterClick: (() -> Unit)? = null,
    showSettingsIcon: Boolean = false,
    onSettingsClick: (() -> Unit)? = null,
    showRefreshIcon: Boolean = false,
    onRefreshClick: (() -> Unit)? = null,
    unreadCount: Int = 0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onBackClick?.invoke() },
                tint = TextPrimary
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        
        // VIRA Logo
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(ViraRed, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "VIRA",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = ViraRed,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        if (showNotificationIcon) {
            Box {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onNotificationClick?.invoke() },
                    tint = TextPrimary
                )
                if (unreadCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(ViraRed, CircleShape)
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = unreadCount.toString(),
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        
        if (showFilterIcon) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onFilterClick?.invoke() },
                tint = TextPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        
        if (showSettingsIcon) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onSettingsClick?.invoke() },
                tint = TextPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        
        if (showRefreshIcon) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRefreshClick?.invoke() },
                tint = TextPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
        
        if (showProfileIcon) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Profile",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onProfileClick?.invoke() },
                tint = TextPrimary
            )
        }
    }
}

@Composable
fun ViraTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null
) {
    var passwordVisible by remember { mutableStateOf(false) }
    
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = TextPrimary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextTertiary
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ViraRed,
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedLabelColor = ViraRed,
                unfocusedLabelColor = TextSecondary
            ),
            visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = TextSecondary
                    )
                }
            },
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = TextSecondary
                        )
                    }
                }
            } else if (trailingIcon != null) {
                {
                    IconButton(onClick = { onTrailingIconClick?.invoke() }) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = null,
                            tint = TextSecondary
                        )
                    }
                }
            } else null
        )
    }
}

@Composable
fun ViraButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = ViraRed,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ViraTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = ViraRed
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ViraSearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    onSearch: (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = TextTertiary
            )
        },
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ViraRed,
            unfocusedBorderColor = ViraRed,
            focusedLabelColor = ViraRed,
            unfocusedLabelColor = ViraRed
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = ViraRed
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}

@Composable
fun ViraCategoryChip(
    text: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    categoryColor: androidx.compose.ui.graphics.Color? = null
) {
    val backgroundColor = when {
        isSelected -> ViraRed
        categoryColor != null -> categoryColor.copy(alpha = 0.2f) // Light version of category color
        else -> BackgroundSecondary
    }
    
    val textColor = when {
        isSelected -> Color.White
        categoryColor != null -> categoryColor
        else -> TextSecondary
    }
    
    Surface(
        modifier = modifier.clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        color = backgroundColor,
        border = if (isSelected) null else BorderStroke(1.dp, categoryColor ?: Color(0xFFE2E8F0))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            color = textColor,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
        )
    }
}

@Composable
fun ViraNewsCard(
    title: String,
    category: String,
    date: String,
    imageUrl: String? = null,
    onClick: () -> Unit
) {
    val categoryColor = when (category.lowercase()) {
        "sports" -> androidx.compose.ui.graphics.Color(0xFF4CAF50) // Green
        "politics" -> androidx.compose.ui.graphics.Color(0xFF2196F3) // Blue
        "latest news" -> androidx.compose.ui.graphics.Color(0xFFF44336) // Red
        "business" -> androidx.compose.ui.graphics.Color(0xFFFFEB3B) // Yellow
        "entertainment" -> androidx.compose.ui.graphics.Color(0xFF9C27B0) // Purple
        "health and wellness" -> androidx.compose.ui.graphics.Color(0xFF00BCD4) // Cyan
        "science" -> androidx.compose.ui.graphics.Color(0xFF795548) // Brown
        "environment" -> androidx.compose.ui.graphics.Color(0xFF8BC34A) // Light Green
        "arts" -> androidx.compose.ui.graphics.Color(0xFFFF9800) // Orange
        else -> ViraRed // Default color
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Image section (if available)
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "News image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.placeholder_image),
                    error = painterResource(id = R.drawable.placeholder_image)
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            // Category and date row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category chip
                Surface(
                    color = categoryColor.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = category,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = categoryColor,
                        fontWeight = FontWeight.Medium
                    )
                }
                
                // Date
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextTertiary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun ViraBottomNavigation(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar(
        containerColor = BackgroundPrimary,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = currentRoute == "home",
            onClick = { onNavigate("home") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = currentRoute == "search",
            onClick = { onNavigate("search") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Notifications, contentDescription = "Alerts") },
            label = { Text("Alerts") },
            selected = currentRoute == "alerts",
            onClick = { onNavigate("alerts") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = currentRoute == "profile",
            onClick = { onNavigate("profile") }
        )
    }
}

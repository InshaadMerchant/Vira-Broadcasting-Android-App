package com.example.virabroadcasting_android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.virabroadcasting_android.ui.components.*
import com.example.virabroadcasting_android.ui.theme.*

@Composable
fun AlertsScreen(
    onBackClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAlertClick: (String) -> Unit,
    onDeleteAlert: (String) -> Unit
) {
    var pushNotifications by remember { mutableStateOf(true) }
    var breakingNews by remember { mutableStateOf(true) }
    var weeklyDigest by remember { mutableStateOf(false) }
    
    val alerts = listOf(
        AlertItem(
            id = "1",
            title = "Breaking News Alert",
            content = "Major political development: NATO leaders call emergency session.",
            time = "5 minutes ago",
            type = AlertType.BREAKING,
            isUnread = true
        ),
        AlertItem(
            id = "2",
            title = "Weather Update",
            content = "Hurricane Erin strengthens to Category 3, coastal areas on alert.",
            time = "1 hour ago",
            type = AlertType.WEATHER,
            isUnread = true
        ),
        AlertItem(
            id = "3",
            title = "Market Update",
            content = "Stock markets close higher amid positive economic indicators.",
            time = "3 hours ago",
            type = AlertType.MARKET,
            isUnread = false
        ),
        AlertItem(
            id = "4",
            title = "Technology News",
            content = "New AI breakthrough announced by leading tech company.",
            time = "5 hours ago",
            type = AlertType.TECH,
            isUnread = false
        )
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundPrimary)
    ) {
        // Header
        ViraHeader(
            title = "Alerts",
            showBackButton = true,
            onBackClick = onBackClick,
            showSettingsIcon = true,
            onSettingsClick = onSettingsClick,
            unreadCount = 2
        )
        
        // Content
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Notification Settings Section
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = BackgroundSecondary
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(
                            text = "Notification Settings",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                        
                        // Push Notifications
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Push Notifications",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Get notified about important news",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            Switch(
                                checked = pushNotifications,
                                onCheckedChange = { pushNotifications = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = ViraRed,
                                    checkedTrackColor = ViraLightRed
                                )
                            )
                        }
                        
                        Divider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                        
                        // Breaking News
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Breaking News",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Urgent news alerts",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            Switch(
                                checked = breakingNews,
                                onCheckedChange = { breakingNews = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = ViraRed,
                                    checkedTrackColor = ViraLightRed
                                )
                            )
                        }
                        
                        Divider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                        
                        // Weekly Digest
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Weekly Digest",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = TextPrimary,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = "Summary of top stories",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = TextSecondary
                                )
                            }
                            Switch(
                                checked = weeklyDigest,
                                onCheckedChange = { weeklyDigest = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = ViraRed,
                                    checkedTrackColor = ViraLightRed
                                )
                            )
                        }
                    }
                }
            }
            
            // Recent Alerts Section
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Recent Alerts",
                            style = MaterialTheme.typography.titleLarge,
                            color = TextPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                        TextButton(
                            onClick = { /* Mark all as read */ }
                        ) {
                            Text(
                                text = "Mark all read",
                                style = MaterialTheme.typography.labelMedium,
                                color = ViraRed
                            )
                        }
                    }
                    
                    // Alerts List
                    alerts.forEach { alert ->
                        AlertCard(
                            alert = alert,
                            onClick = { onAlertClick(alert.id) },
                            onDelete = { onDeleteAlert(alert.id) }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AlertCard(
    alert: AlertItem,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val backgroundColor = when {
        alert.isUnread -> Color(0xFFFFF5F5)
        else -> BackgroundPrimary
    }
    
    val titleColor = when {
        alert.isUnread -> ViraRed
        else -> TextPrimary
    }
    
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = backgroundColor,
        border = BorderStroke(1.dp, if (alert.isUnread) ViraLightRed else Color(0xFFE2E8F0))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Alert Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        when (alert.type) {
                            AlertType.BREAKING -> ViraRed
                            AlertType.WEATHER -> InfoBlue
                            AlertType.MARKET -> SuccessGreen
                            AlertType.TECH -> InfoBlue
                        },
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (alert.type) {
                        AlertType.BREAKING -> Icons.Default.Warning
                        AlertType.WEATHER -> Icons.Default.Info
                        AlertType.MARKET -> Icons.Default.CheckCircle
                        AlertType.TECH -> Icons.Default.Info
                    },
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Alert Content
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = alert.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = titleColor,
                    fontWeight = FontWeight.SemiBold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = alert.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Time",
                        tint = TextTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = alert.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextTertiary
                    )
                }
            }
            
            // Unread indicator and delete button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (alert.isUnread) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(ViraRed, CircleShape)
                            .padding(bottom = 8.dp)
                    )
                }
                
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = TextTertiary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

data class AlertItem(
    val id: String,
    val title: String,
    val content: String,
    val time: String,
    val type: AlertType,
    val isUnread: Boolean
)

enum class AlertType {
    BREAKING,
    WEATHER,
    MARKET,
    TECH
}

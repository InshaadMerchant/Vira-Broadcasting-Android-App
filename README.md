# VIRA Broadcasting Android Application

A modern news and broadcasting application built with Kotlin and Jetpack Compose for Android.

## Features

### 🎯 Core Functionality
- **News Feed**: Browse latest news articles with category filtering
- **Search**: Search through news, topics, and categories
- **Alerts**: Get notified about breaking news and important updates
- **User Profiles**: Manage personal information and preferences

### 🎨 UI/UX Features
- **Modern Design**: Clean, minimalist interface with VIRA brand colors
- **Responsive Layout**: Optimized for various screen sizes
- **Smooth Navigation**: Intuitive navigation between screens
- **Dark/Light Theme Support**: Adaptive theming system

### 📱 Screens
1. **Splash Screen**: Brand introduction with gradient background
2. **Authentication**: Login and account creation flows
3. **Home Screen**: Main news feed with category tabs
4. **Search Screen**: Advanced search with recent searches and trending topics
5. **Alerts Screen**: Notification settings and recent alerts
6. **Profile Screen**: User information and app settings

## Technical Stack

### 🛠️ Technologies
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM with Navigation Component
- **Build System**: Gradle with Kotlin DSL

### 📚 Dependencies
- **Navigation**: `androidx.navigation:navigation-compose`
- **Image Loading**: `io.coil-kt:coil-compose`
- **Material Design**: `androidx.compose.material3`
- **Extended Icons**: `androidx.compose.material:material-icons-extended`

## Project Structure

```
app/src/main/java/com/example/virabroadcasting_android/
├── MainActivity.kt                 # Main application entry point
├── ui/
│   ├── components/                 # Reusable UI components
│   │   └── CommonComponents.kt    # Common UI elements
│   ├── navigation/                 # Navigation logic
│   │   └── ViraNavigation.kt      # App navigation setup
│   ├── screens/                    # Individual screen implementations
│   │   ├── SplashScreen.kt        # Splash screen
│   │   ├── LoginScreen.kt         # Login screen
│   │   ├── CreateAccountScreen.kt # Account creation screen
│   │   ├── HomeScreen.kt          # Main news feed
│   │   ├── SearchScreen.kt        # Search functionality
│   │   ├── AlertsScreen.kt        # Notifications and alerts
│   │   └── ProfileScreen.kt       # User profile management
│   └── theme/                     # App theming and styling
│       ├── Color.kt               # Color definitions
│       ├── Theme.kt               # Theme configuration
│       └── Type.kt                # Typography styles
```

## Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24+ (API level 24)
- Kotlin 2.0.0+

### Installation
1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle files
4. Build and run the application

### Build Configuration
The app is configured with:
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 35 (Android 15)
- **Compile SDK**: 35

## Design System

### 🎨 Color Palette
- **Primary**: VIRA Red (#E53E3E)
- **Secondary**: Dark Red (#C53030)
- **Accent**: Light Red (#FEB2B2)
- **Text**: Dark Gray (#1A202C)
- **Background**: White (#FFFFFF)

### 📝 Typography
- **Display**: Large, bold text for main titles
- **Headline**: Medium weight for section headers
- **Body**: Regular weight for content text
- **Label**: Medium weight for form labels

## Future Enhancements

### 🔮 Planned Features
- **Backend Integration**: Firebase integration for real data
- **Push Notifications**: Real-time news alerts
- **Offline Support**: Cached news articles
- **Personalization**: User preferences and recommendations
- **Social Features**: Sharing and commenting

### 🚀 Technical Improvements
- **State Management**: ViewModel and StateFlow implementation
- **Data Layer**: Repository pattern with local/remote data sources
- **Testing**: Unit tests and UI tests
- **Performance**: Image optimization and lazy loading
- **Accessibility**: Screen reader support and accessibility features

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For support and questions, please contact the development team or create an issue in the repository.

---

**VIRA Broadcasting** - Bringing you the latest news with style and substance.

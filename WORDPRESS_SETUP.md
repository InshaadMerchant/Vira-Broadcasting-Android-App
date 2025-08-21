# 🚀 VIRA Broadcasting - WordPress Integration Setup Guide

This guide will walk you through setting up your WordPress website to sync news with the VIRA Broadcasting Android app.

## 📋 Prerequisites

- WordPress website (self-hosted or WordPress.com)
- Admin access to your WordPress site
- Android Studio with the VIRA Broadcasting project
- Basic understanding of WordPress administration

## 🔧 WordPress Site Configuration

### Step 1: Enable WordPress REST API

The WordPress REST API should be enabled by default in WordPress 4.7+. To verify:

1. **Check if REST API is working:**
   - Visit: `https://your-wordpress-site.com/wp-json/`
   - You should see a JSON response (not an error page)

2. **If REST API is disabled, add this to your `wp-config.php`:**
   ```php
   define('REST_API_ENABLED', true);
   ```

### Step 2: Configure Permalinks

1. Go to **WordPress Admin → Settings → Permalinks**
2. Select **Post name** (recommended) or **Custom Structure**
3. Click **Save Changes**

### Step 3: Test REST API Endpoints

Test these URLs in your browser to ensure they work:

- **Posts:** `https://your-wordpress-site.com/wp-json/wp/v2/posts`
- **Categories:** `https://your-wordpress-site.com/wp-json/wp/v2/categories`
- **Search:** `https://your-wordpress-site.com/wp-json/wp/v2/posts?search=test`

### Step 4: Create Sample Content

Create some test posts with:
- **Featured Images** (important for the app)
- **Categories** (for filtering)
- **Excerpts** (for app display)
- **Tags** (optional)

## 📱 Android App Configuration

### Step 1: Update WordPress URL

1. Open `app/src/main/java/com/example/virabroadcasting_android/config/WordPressConfig.kt`
2. Replace `your-wordpress-site.com` with your actual domain:

```kotlin
// Change this line:
const val WORDPRESS_DOMAIN = "your-wordpress-site.com"

// To your actual domain (without https:// or trailing slash):
const val WORDPRESS_DOMAIN = "example.com"
```

### Step 2: Build and Test

1. **Sync Gradle files** in Android Studio
2. **Build the project** (Build → Make Project)
3. **Run the app** on a device or emulator

### Step 3: Test WordPress Connection

1. Navigate to the **Test Connection Screen** in the app
2. Tap **"Test WordPress Connection"**
3. Check the results for any errors

## 🧪 Testing the Integration

### Test 1: Basic Connection
- ✅ App loads without crashes
- ✅ Test Connection screen shows configuration status
- ✅ No network permission errors

### Test 2: API Communication
- ✅ Categories are loaded from WordPress
- ✅ News articles are fetched
- ✅ Images load properly (if available)

### Test 3: App Functionality
- ✅ Home screen displays real WordPress news
- ✅ Category filtering works
- ✅ Search functionality works
- ✅ Pull-to-refresh works
- ✅ Infinite scroll works

## 🔍 Troubleshooting Common Issues

### Issue 1: "Network Error" or "Connection Failed"

**Possible Causes:**
- WordPress URL is incorrect
- WordPress REST API is disabled
- Network permissions not granted
- Firewall blocking requests

**Solutions:**
1. Verify WordPress URL in `WordPressConfig.kt`
2. Test REST API in browser
3. Check Android manifest permissions
4. Test on different network

### Issue 2: "No News Available"

**Possible Causes:**
- No posts published in WordPress
- Posts are private/draft
- REST API returning empty results

**Solutions:**
1. Publish some public posts in WordPress
2. Check post status (should be "Published")
3. Verify REST API endpoint works in browser

### Issue 3: Images Not Loading

**Possible Causes:**
- Featured images not set in WordPress
- Image URLs are relative
- CORS issues

**Solutions:**
1. Set featured images for posts in WordPress
2. Use absolute URLs for images
3. Check WordPress media settings

### Issue 4: Categories Not Loading

**Possible Causes:**
- No categories created
- Categories are private
- REST API permissions issue

**Solutions:**
1. Create categories in WordPress
2. Ensure categories are public
3. Check user roles and permissions

## 📊 WordPress Content Best Practices

### For Optimal App Experience:

1. **Featured Images:**
   - Use consistent aspect ratios (16:9 recommended)
   - Optimize image sizes (max 1200px width)
   - Set alt text for accessibility

2. **Post Structure:**
   - Write compelling excerpts (150-200 characters)
   - Use clear, descriptive titles
   - Categorize posts properly

3. **Content Updates:**
   - Publish regularly for fresh content
   - Use tags for better organization
   - Keep content mobile-friendly

## 🔐 Security Considerations

### WordPress Security:
1. **Keep WordPress updated** to latest version
2. **Use strong passwords** for admin accounts
3. **Limit login attempts** with security plugins
4. **Use HTTPS** for secure communication

### App Security:
1. **API endpoints are public** (WordPress posts are public)
2. **No sensitive data** is transmitted
3. **HTTPS required** for production

## 🚀 Production Deployment

### Before Going Live:

1. **Test thoroughly** on multiple devices
2. **Verify performance** with real content
3. **Check error handling** for edge cases
4. **Monitor API usage** and performance
5. **Backup WordPress** before major changes

### Performance Optimization:

1. **Enable WordPress caching** (WP Super Cache, W3 Total Cache)
2. **Optimize images** (WebP format, compression)
3. **Use CDN** for media files
4. **Database optimization** (WP-Optimize plugin)

## 📞 Support and Resources

### WordPress Resources:
- [WordPress REST API Handbook](https://developer.wordpress.org/rest-api/)
- [WordPress Developer Documentation](https://developer.wordpress.org/)
- [WordPress Support Forums](https://wordpress.org/support/)

### Android Development:
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Retrofit Documentation](https://square.github.io/retrofit/)
- [Android Developer Documentation](https://developer.android.com/)

## 🎯 Next Steps

After successful integration:

1. **Customize the app** with your branding
2. **Add more features** (bookmarks, sharing, notifications)
3. **Implement analytics** to track user engagement
4. **Optimize performance** based on real usage data
5. **Plan content strategy** for consistent updates

---

## 📝 Quick Checklist

- [ ] WordPress REST API enabled
- [ ] Permalinks configured
- [ ] Sample posts created with featured images
- [ ] Categories created
- [ ] WordPress URL updated in Android config
- [ ] App builds successfully
- [ ] Test connection passes
- [ ] News loads from WordPress
- [ ] Images display properly
- [ ] Categories filter correctly
- [ ] Search functionality works
- [ ] Pull-to-refresh works
- [ ] Infinite scroll works

**🎉 Congratulations!** Your VIRA Broadcasting app is now connected to WordPress and ready for testing with real content.

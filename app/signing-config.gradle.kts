// Release Signing Configuration for AuraMusic
// This file contains the release signing configuration
// Include this in build.gradle.kts: apply(from = "signing-config.gradle.kts")

// Load local properties for signing credentials
import java.io.FileInputStream
import java.util.*

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

android {
    signingConfigs {
        // Debug signing config
        create("persistentDebug") {
            storeFile = file("${System.getProperty("user.home")}/.android/debug.keystore")
            storePassword = System.getenv("DEBUG_KEYSTORE_PASSWORD") ?: "android"
            keyAlias = "androiddebugkey"
            keyPassword = System.getenv("DEBUG_KEY_PASSWORD") ?: "android"
        }

        // Release signing config
        create("release") {
            val keystorePath = localProperties.getProperty("release.keystore.path") ?: 
                System.getenv("RELEASE_KEYSTORE_PATH") ?: ""
            val keystorePassword = localProperties.getProperty("release.keystore.password") ?: 
                System.getenv("RELEASE_KEYSTORE_PASSWORD") ?: ""
            val keyAlias = localProperties.getProperty("release.key.alias") ?: 
                System.getenv("RELEASE_KEY_ALIAS") ?: "auramusic-release"
            val keyPassword = localProperties.getProperty("release.key.password") ?: 
                System.getenv("RELEASE_KEY_PASSWORD") ?: ""

            if (keystorePath.isNotEmpty() && keystorePassword.isNotEmpty()) {
                storeFile = file(keystorePath)
                storePassword = keystorePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
                
                println("✓ Release signing config loaded from: $keystorePath")
                println("✓ Key alias: $keyAlias")
            } else {
                println("⚠ Release signing config not found. Ensure local.properties or environment variables are set.")
                println("  Required: release.keystore.path, release.keystore.password, release.key.alias, release.key.password")
                storeFile = null
            }
        }
    }
}

// Firebase OAuth Configuration for Google Sign-In
// Add these configurations to Firebase Console for proper OAuth validation

val firebaseWebClientId = localProperties.getProperty("firebase.web.client.id") ?: 
    System.getenv("FIREBASE_WEB_CLIENT_ID") ?: ""

val firebaseAndroidClientId = localProperties.getProperty("firebase.android.client.id") ?: 
    System.getenv("FIREBASE_ANDROID_CLIENT_ID") ?: ""

if (firebaseWebClientId.isNotEmpty() || firebaseAndroidClientId.isNotEmpty()) {
    println("✓ Firebase OAuth credentials configured")
    println("  Web Client ID: ${if (firebaseWebClientId.isNotEmpty()) "✓ Set" else "✗ Not set"}")
    println("  Android Client ID: ${if (firebaseAndroidClientId.isNotEmpty()) "✓ Set" else "✗ Not set"}")
}

// SHA Certificate Fingerprints for Google Play Services
// These will be displayed when you build with the release keystore
// Add these to Firebase Console -> Project Settings -> Your Apps -> Android

val releaseKeystorePath = localProperties.getProperty("release.keystore.path")
if (releaseKeystorePath != null) {
    println("""
        ╔════════════════════════════════════════════════════════════════════════╗
        ║  Release Keystore Found: $releaseKeystorePath                         
        ║                                                                        ║
        ║  To get SHA-1 and SHA-256 fingerprints, run:                         ║
        ║  keytool -list -v -keystore $releaseKeystorePath -alias auramusic-release
        ║                                                                        ║
        ║  Add these fingerprints to:                                          ║
        ║  - Firebase Console -> Project Settings -> Android App               ║
        ║  - Google Play Console -> App integrity -> SHA-1 & SHA-256           ║
        ╚════════════════════════════════════════════════════════════════════════╝
    """.trimIndent())
}

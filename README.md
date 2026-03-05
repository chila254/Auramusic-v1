# Auramusic

A modern Android music player with YouTube Music integration, powerful audio features, and a beautiful Material 3 interface.

<div align="center">

![Android](https://img.shields.io/badge/Android-26%2B-2?style=for-the-badge&logo=android)
![Kotlin](https://img.shields.io/badge/Kotlin-2.3-7F52FF?style=for-the-badge&logo=kotlin)
![Compose](https://img.shields.io/badge/Compose-Latest-4285F4?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-4CAF50?style=for-the-badge)

</div>

## Features

| Category | Description |
|----------|-------------|
| **Playback** | Background playback, audio normalization, tempo/pitch adjustment, skip silence |
| **Streaming** | YouTube Music integration, offline downloads |
| **Customization** | Material 3 design, dynamic theming, light/dark/black themes |
| **Audio** | Equalizer, audio focus handling |
| **Social** | Discord Rich Presence, Listen Together (collaborative) |
| **Search** | Multi-source search across platforms |
| **Lyrics** | Live lyrics from Kugou, LRCLib, YouTube |

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Audio:** Media3 ExoPlayer
- **DI:** Hilt
- **Database:** Room
- **Networking:** Ktor
- **Image Loading:** Coil

## Requirements

- Android 8.0+ (API 26)
- Android Studio Ladybug
- JDK 21

## Quick Start

```bash
# Clone the repository
git clone https://github.com/chila254/Auramusic-v1.git
cd Auramusic-v1

# Setup API keys (optional)
cp local.properties.example local.properties

# Build debug APK
./gradlew assembleDebug
```

## Build Variants

| Variant | Description |
|---------|-------------|
| `foss` | F-Droid compatible, no Google Play Services |
| `gms` | With Google Cast support |

**ABI Variants:** universal, arm64, armeabi, x86, x86_64

## License

MIT License - see [LICENSE](LICENSE) file

---

**Developed by [chila254](https://github.com/chila254)**

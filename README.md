# Auramusic 🎵

A modern Android music player with YouTube Music integration, powerful audio features, and a beautiful Material 3 interface.

[![Android](https://img.shields.io/badge/Android-26%2B-green?style=flat&logo=android)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.3-blue?style=flat&logo=kotlin)](https://kotlinlang.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow?style=flat)](LICENSE)

## Features

- 🎵 YouTube Music streaming & local playback
- 🎨 Material 3 design with dynamic theming
- 🔊 Advanced audio controls (EQ, normalization, tempo/pitch)
- 📥 Offline downloads
- 🎤 Live lyrics from multiple sources
- 📡 Discord Rich Presence
- 👥 Listen Together (collaborative listening)
- 🔍 Multi-source search
- 🌙 Light/Dark/Black themes

## Tech Stack

- Kotlin + Jetpack Compose
- Material 3 Design
- Media3 ExoPlayer
- Hilt Dependency Injection
- Room Database
- Ktor Networking

## Requirements

- Android 8.0+ (API 26)
- Android Studio Ladybug
- JDK 21

## Quick Start

```bash
# Clone
git clone https://github.com/chila254/Auramusic-v1.git
cd Auramusic-v1

# Setup API keys (optional)
cp local.properties.example local.properties
# Edit local.properties with your LastFM keys

# Build
./gradlew assembleDebug
```

## Build Variants

| Variant | Description |
|---------|-------------|
| `foss` | F-Droid compatible, no GMS |
| `gms` | With Google Cast support |

## License

MIT License - see [LICENSE](LICENSE)

---

**Made with ❤️ by [chila254](https://github.com/chila254)**

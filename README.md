# AuraMusic 🎵

<div align="center">

![AuraMusic Icon](app/src/main/res/mipmap-xxxhdpi/ic_launcher_foreground.png)

A feature-rich music player for Android with YouTube Music integration, advanced audio controls, and modern Material 3 design.

[![Android](https://img.shields.io/badge/Android-26%2B-green?logo=android)](https://www.android.com/)
[![Kotlin](https://img.shields.io/badge/Kotlin-Latest-blue?logo=kotlin)](https://kotlinlang.org/)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-blue)](https://developer.android.com/jetpack/compose)
[![License](https://img.shields.io/badge/License-MIT-yellow)](LICENSE)

</div>

## About

AuraMusic is an Android music player application that combines local music playback with YouTube Music integration. Built with modern Android development practices using Jetpack Compose, Kotlin, and Material 3 design.

This project is based on [Metrolist](https://github.com/MetrolistGroup/Metrolist) with custom modifications and enhancements.

## Features

- **Advanced Playback**: Background playback, audio normalization, tempo/pitch adjustment, skip silence, audio focus handling
- **Library & Sync**: Playlist management, offline downloads, YouTube Music sync, queue management
- **Powerful Search**: Multi-source search across YouTube Music, Kugou, and more
- **Customization**: Material 3 design, light/dark/black themes, dynamic theme (Material You)
- **Special Features**: Live lyrics, Discord Rich Presence, Last.FM scrobbling, Android Auto support, sleep timer
- **Music Recognition**: Built-in song recognition using audio fingerprinting
- **Listen Together**: Host and join collaborative listening sessions

## Tech Stack

- **Architecture**: MVVM, Clean Architecture, Repository Pattern, Hilt DI
- **Core**: Kotlin, Jetpack Compose, Material 3, ExoPlayer/Media3, Room, DataStore
- **Networking**: Ktor, YouTube Music API, Last.FM API, Kugou API, LRCLib API, Discord RPC
- **Libraries**: Coil, Coroutines, Navigation Compose, Work Manager, Timber

## Getting Started

### Prerequisites

- Android Studio Ladybug or later
- Android SDK 26+
- Kotlin 2.3+
- JDK 21+

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/chila254/Auramusic.git
   cd Auramusic
   ```

2. Set up API keys in `local.properties`:
   ```properties
   LASTFM_API_KEY=your_lastfm_api_key
   LASTFM_SECRET=your_lastfm_secret
   ```

3. Build and run:
   ```bash
   ./gradlew assembleDebug
   ```

## Build Variants

AuraMusic supports multiple build variants:

- **foss**: F-Droid compatible, no Google Play Services
- **gms**: With Google Cast support (requires Google Play Services)

ABI variants: universal, arm64, armeabi, x86, x86_64

## Usage

- Browse or search for songs
- Tap to play or add to queue
- Manage playlists in Library tab
- Download songs for offline playback
- Sync with YouTube Music in Settings
- Customize audio settings in Player screen

## Contributing

Contributions welcome! Fork, create a feature branch, commit changes, push, and open a PR.

## Support & Contact

For issues, feature requests, or questions:
- Open an issue on [GitHub Issues](https://github.com/chila254/Auramusic/issues)

### Support This Project

If you find this project helpful and would like to support its development:

[![Donate](https://img.shields.io/badge/Donate-PayPal-blue)](https://www.paypal.com/cgi-bin/webscr?cmd=_xclick&business=franklinfinyange@gmail.com&item_name=Support%20AuraMusic%20Development&currency_code=USD)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

<div align="center">

**Made with ❤️ for music lovers**

</div>

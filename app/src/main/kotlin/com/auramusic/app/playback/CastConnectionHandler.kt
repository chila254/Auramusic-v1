package com.auramusic.app.playback

import android.content.Context
import com.auramusic.app.models.MediaMetadata
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Stub implementation of CastConnectionHandler for FOSS builds.
 * Google Cast is not available in F-Droid/FOSS builds.
 * This class provides no-op implementations to allow the app to compile.
 */
class CastConnectionHandler(
    private val context: Context,
    private val scope: CoroutineScope,
    private val listener: CastConnectionListener?
) {
    val isCasting: StateFlow<Boolean> = MutableStateFlow(false)
    val castIsPlaying: StateFlow<Boolean> = MutableStateFlow(false)
    val castPosition: StateFlow<Long> = MutableStateFlow(0L)
    val castDuration: StateFlow<Long> = MutableStateFlow(0L)
    val castVolume: StateFlow<Float> = MutableStateFlow(1f)
    val castDeviceName: StateFlow<String?> = MutableStateFlow(null)
    var isSyncingFromCast: Boolean = false
        private set

    fun initialize() {
        // No-op for FOSS builds - Google Cast is not available
    }

    fun play() {
        // No-op for FOSS builds
    }

    fun pause() {
        // No-op for FOSS builds
    }

    fun seekTo(position: Long) {
        // No-op for FOSS builds
    }

    fun skipToNext() {
        // No-op for FOSS builds
    }

    fun skipToPrevious() {
        // No-op for FOSS builds
    }

    fun setVolume(volume: Float) {
        // No-op for FOSS builds
    }

    fun release() {
        // No-op for FOSS builds
    }

    fun closeRPC() {
        // No-op for FOSS builds
    }

    /**
     * Navigate to media if it's already in the Cast queue
     * @return true if navigation was successful, false otherwise
     */
    fun navigateToMediaIfInQueue(mediaId: String): Boolean {
        // No-op for FOSS builds - always return false
        return false
    }

    /**
     * Load media to Cast device
     */
    fun loadMedia(metadata: MediaMetadata) {
        // No-op for FOSS builds
    }

    interface CastConnectionListener {
        fun onConnected()
        fun onDisconnected()
        fun onPlaybackChanged(isPlaying: Boolean)
        fun onMetadataChanged(title: String, artist: String, album: String?)
    }
}

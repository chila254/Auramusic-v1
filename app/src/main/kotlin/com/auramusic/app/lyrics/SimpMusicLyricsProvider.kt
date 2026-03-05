/**
 * Auramusic Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auramusic.app.lyrics

import android.content.Context
import com.auramusic.app.constants.EnableSimpMusicKey
import com.auramusic.app.utils.dataStore
import com.auramusic.app.utils.get
import com.auramusic.simpmusic.SimpMusicLyrics

object SimpMusicLyricsProvider : LyricsProvider {
    override val name = "SimpMusic"

    override fun isEnabled(context: Context): Boolean = context.dataStore[EnableSimpMusicKey] ?: true

    override suspend fun getLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
    ): Result<String> = SimpMusicLyrics.getLyrics(id, duration)

    override suspend fun getAllLyrics(
        id: String,
        title: String,
        artist: String,
        duration: Int,
        album: String?,
        callback: (String) -> Unit,
    ) {
        SimpMusicLyrics.getAllLyrics(id, duration, callback)
    }
}

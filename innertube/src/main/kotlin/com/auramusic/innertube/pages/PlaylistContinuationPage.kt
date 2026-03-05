package com.auramusic.innertube.pages

import com.auramusic.innertube.models.SongItem

data class PlaylistContinuationPage(
    val songs: List<SongItem>,
    val continuation: String?,
)

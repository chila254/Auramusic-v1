package com.auramusic.innertube.models.body

import com.auramusic.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDeleteBody(
    val context: Context,
    val playlistId: String
)

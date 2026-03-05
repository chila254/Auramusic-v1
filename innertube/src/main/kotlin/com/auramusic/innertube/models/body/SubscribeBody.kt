package com.auramusic.innertube.models.body

import com.auramusic.innertube.models.Context
import kotlinx.serialization.Serializable

@Serializable
data class SubscribeBody(
    val channelIds: List<String>,
    val context: Context,
)

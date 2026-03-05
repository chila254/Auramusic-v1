package com.auramusic.innertube.pages

import com.auramusic.innertube.models.YTItem

data class LibraryContinuationPage(
    val items: List<YTItem>,
    val continuation: String?,
)

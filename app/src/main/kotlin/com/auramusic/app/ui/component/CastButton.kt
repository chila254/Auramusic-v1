package com.auramusic.app.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

/**
 * Stub implementation of CastButton for FOSS builds.
 * Google Cast is not available in F-Droid/FOSS builds.
 * This composable displays nothing or a placeholder icon.
 */
@Composable
fun CastButton(
    modifier: Modifier = Modifier,
    tintColor: Color = MaterialTheme.colorScheme.onSurface
) {
    // Cast is not available in FOSS builds, so we show nothing
    // Alternatively, you could show a disabled cast icon
    Box(
        modifier = modifier.size(24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Empty - Cast is not available in FOSS builds
    }
}

/**
 * Auramusic Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auramusic.app.viewmodels

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auramusic.app.constants.HideExplicitKey
import com.auramusic.app.constants.HideVideoSongsKey
import com.auramusic.app.constants.SongSortDescendingKey
import com.auramusic.app.constants.SongSortType
import com.auramusic.app.constants.SongSortTypeKey
import com.auramusic.app.db.MusicDatabase
import com.auramusic.app.extensions.filterExplicit
import com.auramusic.app.extensions.filterVideoSongs
import com.auramusic.app.extensions.toEnum
import com.auramusic.app.utils.SyncUtils
import com.auramusic.app.utils.dataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class AutoPlaylistViewModel
@Inject
constructor(
    @ApplicationContext context: Context,
    private val database: MusicDatabase,
    savedStateHandle: SavedStateHandle,
    private val syncUtils: SyncUtils,
) : ViewModel() {
    val playlist = savedStateHandle.get<String>("playlist")!!

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val likedSongs =
        context.dataStore.data
            .map {
                Triple(
                    it[SongSortTypeKey].toEnum(SongSortType.CREATE_DATE) to (it[SongSortDescendingKey]
                        ?: true),
                    it[HideExplicitKey] ?: false,
                    it[HideVideoSongsKey] ?: false
                )
            }
            .distinctUntilChanged()
            .flatMapLatest { (sortDesc, hideExplicit, hideVideoSongs) ->
                val (sortType, descending) = sortDesc
                when (playlist) {
                    "liked" -> database.likedSongs(sortType, descending)
                        .map { it.filterExplicit(hideExplicit).filterVideoSongs(hideVideoSongs) }

                    "downloaded" -> database.downloadedSongs(sortType, descending)
                        .map { it.filterExplicit(hideExplicit).filterVideoSongs(hideVideoSongs) }

                    // Uploaded feature is temporarily disabled
                    // "uploaded" -> database.uploadedSongs(sortType, descending)
                    //     .map { it.filterExplicit(hideExplicit).filterVideoSongs(hideVideoSongs) }

                    else -> kotlinx.coroutines.flow.flowOf(emptyList())
                }
            }
            .stateIn(viewModelScope, kotlinx.coroutines.flow.SharingStarted.Lazily, emptyList())

    fun syncLikedSongs() {
        viewModelScope.launch(Dispatchers.IO) { syncUtils.syncLikedSongs() }
    }

    // Uploaded feature is temporarily disabled
    fun syncUploadedSongs() {
        // viewModelScope.launch(Dispatchers.IO) { syncUtils.syncUploadedSongs() }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.value = true
            when (playlist) {
                "liked" -> syncUtils.syncLikedSongsSuspend()
                // Uploaded feature is temporarily disabled
                // "uploaded" -> syncUtils.syncUploadedSongsSuspend()
            }
            _isRefreshing.value = false
        }
    }
}

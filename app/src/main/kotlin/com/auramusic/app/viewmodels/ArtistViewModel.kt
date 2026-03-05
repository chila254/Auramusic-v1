/**
 * Auramusic Project (C) 2026
 * Licensed under GPL-3.0 | See git history for contributors
 */

package com.auramusic.app.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auramusic.innertube.YouTube
import com.auramusic.innertube.models.filterExplicit
import com.auramusic.innertube.models.filterVideoSongs
import com.auramusic.innertube.pages.ArtistPage
import com.auramusic.app.constants.HideExplicitKey
import com.auramusic.app.constants.HideVideoSongsKey
import com.auramusic.app.db.MusicDatabase
import com.auramusic.app.extensions.filterExplicit
import com.auramusic.app.extensions.filterExplicitAlbums
import com.auramusic.app.utils.dataStore
import com.auramusic.app.utils.get
import com.auramusic.app.utils.reportException
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.auramusic.app.extensions.filterVideoSongs as filterVideoSongsLocal

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ArtistViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    database: MusicDatabase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val artistId = savedStateHandle.get<String>("artistId")!!
    var artistPage by mutableStateOf<ArtistPage?>(null)
    val libraryArtist = database.artist(artistId)
        .stateIn(viewModelScope, SharingStarted.Lazily, null)
    val librarySongs = context.dataStore.data
        .map { (it[HideExplicitKey] ?: false) to (it[HideVideoSongsKey] ?: false) }
        .distinctUntilChanged()
        .flatMapLatest { (hideExplicit, hideVideoSongs) ->
            database.artistSongsPreview(artistId).map { it.filterExplicit(hideExplicit).filterVideoSongsLocal(hideVideoSongs) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    val libraryAlbums = context.dataStore.data
        .map { it[HideExplicitKey] ?: false }
        .distinctUntilChanged()
        .flatMapLatest { hideExplicit ->
            database.artistAlbumsPreview(artistId).map { it.filterExplicitAlbums(hideExplicit) }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        // Load artist page and reload when hide explicit setting changes
        viewModelScope.launch {
            context.dataStore.data
                .map { (it[HideExplicitKey] ?: false) to (it[HideVideoSongsKey] ?: false) }
                .distinctUntilChanged()
                .collect {
                    fetchArtistsFromYTM()
                }
        }
    }

    fun fetchArtistsFromYTM() {
        viewModelScope.launch {
            val hideExplicit = context.dataStore.get(HideExplicitKey, false)
            val hideVideoSongs = context.dataStore.get(HideVideoSongsKey, false)
            YouTube.artist(artistId)
                .onSuccess { page ->
                    val filteredSections = page.sections
                        .map { section ->
                            section.copy(items = section.items.filterExplicit(hideExplicit).filterVideoSongs(hideVideoSongs))
                        }
                        .filter { section -> section.items.isNotEmpty() }

                    artistPage = page.copy(sections = filteredSections)
                }.onFailure {
                    reportException(it)
                }
        }
    }
}

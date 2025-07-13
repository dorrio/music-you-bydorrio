package es.remix.vimusic.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import es.remix.vimusic.Database
import es.remix.vimusic.enums.PlaylistSortBy
import es.remix.vimusic.enums.SortOrder
import es.remix.vimusic.models.PlaylistPreview

class HomePlaylistsViewModel : ViewModel() {
    var items: List<PlaylistPreview> by mutableStateOf(emptyList())

    suspend fun loadArtists(
        sortBy: PlaylistSortBy,
        sortOrder: SortOrder
    ) {
        Database
            .playlistPreviews(sortBy, sortOrder)
            .collect { items = it }
    }
}
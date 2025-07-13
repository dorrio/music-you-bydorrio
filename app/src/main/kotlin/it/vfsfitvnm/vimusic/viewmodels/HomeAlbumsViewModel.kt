package es.remix.vimusic.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import es.remix.vimusic.Database
import es.remix.vimusic.enums.AlbumSortBy
import es.remix.vimusic.enums.SortOrder
import es.remix.vimusic.models.Album

class HomeAlbumsViewModel : ViewModel() {
    var items: List<Album> by mutableStateOf(emptyList())

    suspend fun loadAlbums(
        sortBy: AlbumSortBy,
        sortOrder: SortOrder
    ) {
        Database
            .albums(sortBy, sortOrder)
            .collect { items = it }
    }
}
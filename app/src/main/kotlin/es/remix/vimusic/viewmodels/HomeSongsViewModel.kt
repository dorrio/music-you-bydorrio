package es.remix.vimusic.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import es.remix.vimusic.Database
import es.remix.vimusic.enums.SongSortBy
import es.remix.vimusic.enums.SortOrder
import es.remix.vimusic.models.Song

class HomeSongsViewModel : ViewModel() {
    var items: List<Song> by mutableStateOf(emptyList())

    suspend fun loadSongs(
        sortBy: SongSortBy,
        sortOrder: SortOrder
    ) {
        Database
            .songs(sortBy, sortOrder)
            .collect { items = it }
    }
}
package es.remix.vimusic.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.remix.vimusic.Database
import es.remix.vimusic.LocalPlayerPadding
import es.remix.vimusic.R
import es.remix.vimusic.enums.BuiltInPlaylist
import es.remix.vimusic.enums.PlaylistSortBy
import es.remix.vimusic.enums.SortOrder
import es.remix.vimusic.models.Playlist
import es.remix.vimusic.query
import es.remix.vimusic.ui.components.SortingHeader
import es.remix.vimusic.ui.components.themed.TextFieldDialog
import es.remix.vimusic.ui.items.BuiltInPlaylistItem
import es.remix.vimusic.ui.items.LocalPlaylistItem
import es.remix.vimusic.utils.playlistSortByKey
import es.remix.vimusic.utils.playlistSortOrderKey
import es.remix.vimusic.utils.rememberPreference
import es.remix.vimusic.viewmodels.HomePlaylistsViewModel

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@Composable
fun HomePlaylists(
    onBuiltInPlaylist: (Int) -> Unit,
    onPlaylistClick: (Playlist) -> Unit
) {
    val playerPadding = LocalPlayerPadding.current

    var isCreatingANewPlaylist by rememberSaveable { mutableStateOf(false) }
    var sortBy by rememberPreference(playlistSortByKey, PlaylistSortBy.Name)
    var sortOrder by rememberPreference(playlistSortOrderKey, SortOrder.Ascending)

    val viewModel: HomePlaylistsViewModel = viewModel()

    LaunchedEffect(sortBy, sortOrder) {
        viewModel.loadArtists(
            sortBy = sortBy,
            sortOrder = sortOrder
        )
    }

    if (isCreatingANewPlaylist) {
        TextFieldDialog(
            title = stringResource(id = R.string.new_playlist),
            hintText = stringResource(id = R.string.playlist_name_hint),
            onDismiss = {
                isCreatingANewPlaylist = false
            },
            onDone = { text ->
                query {
                    Database.insert(Playlist(name = text))
                }
            }
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp + playerPadding),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        item(
            key = "header",
            span = { GridItemSpan(maxLineSpan) }
        ) {
            SortingHeader(
                sortBy = sortBy,
                changeSortBy = { sortBy = it },
                sortByEntries = PlaylistSortBy.entries.toList(),
                sortOrder = sortOrder,
                toggleSortOrder = { sortOrder = !sortOrder },
                size = viewModel.items.size,
                itemCountText = R.plurals.number_of_playlists
            )
        }

        item(key = "favorites") {
            BuiltInPlaylistItem(
                icon = Icons.Default.Favorite,
                name = stringResource(id = R.string.favorites),
                onClick = { onBuiltInPlaylist(BuiltInPlaylist.Favorites.ordinal) }
            )
        }

        item(key = "offline") {
            BuiltInPlaylistItem(
                icon = Icons.Default.DownloadForOffline,
                name = stringResource(id = R.string.offline),
                onClick = { onBuiltInPlaylist(BuiltInPlaylist.Offline.ordinal) }
            )
        }

        item(key = "new") {
            BuiltInPlaylistItem(
                icon = Icons.Default.Add,
                name = stringResource(id = R.string.new_playlist),
                onClick = { isCreatingANewPlaylist = true }
            )
        }

        items(
            items = viewModel.items,
            key = { it.playlist.id }
        ) { playlistPreview ->
            LocalPlaylistItem(
                modifier = Modifier.animateItem(),
                playlist = playlistPreview,
                onClick = { onPlaylistClick(playlistPreview.playlist) }
            )
        }
    }
}
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.remix.vimusic.LocalPlayerPadding
import es.remix.vimusic.R
import es.remix.vimusic.enums.ArtistSortBy
import es.remix.vimusic.enums.SortOrder
import es.remix.vimusic.models.Artist
import es.remix.vimusic.ui.components.SortingHeader
import es.remix.vimusic.ui.items.LocalArtistItem
import es.remix.vimusic.utils.artistSortByKey
import es.remix.vimusic.utils.artistSortOrderKey
import es.remix.vimusic.utils.rememberPreference
import es.remix.vimusic.viewmodels.HomeArtistsViewModel

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun HomeArtistList(onArtistClick: (Artist) -> Unit) {
    val playerPadding = LocalPlayerPadding.current

    var sortBy by rememberPreference(artistSortByKey, ArtistSortBy.Name)
    var sortOrder by rememberPreference(artistSortOrderKey, SortOrder.Ascending)

    val viewModel: HomeArtistsViewModel = viewModel()

    LaunchedEffect(sortBy, sortOrder) {
        viewModel.loadArtists(
            sortBy = sortBy,
            sortOrder = sortOrder
        )
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 100.dp),
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
                sortByEntries = ArtistSortBy.entries.toList(),
                sortOrder = sortOrder,
                toggleSortOrder = { sortOrder = !sortOrder },
                size = viewModel.items.size,
                itemCountText = R.plurals.number_of_artists
            )
        }

        items(items = viewModel.items, key = Artist::id) { artist ->
            LocalArtistItem(
                modifier = Modifier.animateItem(),
                artist = artist,
                onClick = { onArtistClick(artist) }
            )
        }
    }
}
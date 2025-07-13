package es.remix.vimusic.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import it.vfsfitvnm.innertube.Innertube
import it.vfsfitvnm.innertube.requests.relatedPage
import es.remix.vimusic.Database
import es.remix.vimusic.enums.QuickPicksSource
import es.remix.vimusic.models.Song
import kotlinx.coroutines.flow.distinctUntilChanged

class QuickPicksViewModel : ViewModel() {
    var trending: Song? by mutableStateOf(null)
    var relatedPageResult: Result<Innertube.RelatedPage?>? by mutableStateOf(null)

    suspend fun loadQuickPicks(quickPicksSource: QuickPicksSource) {
        val flow = when (quickPicksSource) {
            QuickPicksSource.Trending -> Database.trending()
            QuickPicksSource.LastPlayed -> Database.lastPlayed()
            QuickPicksSource.Random -> Database.randomSong()
        }

        flow.distinctUntilChanged().collect { song ->
            if (quickPicksSource == QuickPicksSource.Random && song != null && trending != null) return@collect

            if ((song == null && relatedPageResult == null) || trending?.id != song?.id || relatedPageResult?.isSuccess != true) {
                relatedPageResult = Innertube.relatedPage(videoId = (song?.id ?: "fJ9rUzIMcZQ"))
            }

            trending = song
        }
    }
}
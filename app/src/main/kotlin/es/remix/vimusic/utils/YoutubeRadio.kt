package es.remix.vimusic.utils

import androidx.media3.common.MediaItem
import es-remix.innertube.Innertube
import es-remix.innertube.requests.nextPage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

data class YouTubeRadio(
    private val videoId: String? = null,
    private var playlistId: String? = null,
    private var playlistSetVideoId: String? = null,
    private var parameters: String? = null
) {
    private var nextContinuation: String? = null

    suspend fun process(): List<MediaItem> {
        var mediaItems: List<MediaItem>? = null

        nextContinuation = withContext(Dispatchers.IO) {
            val continuation = nextContinuation

            if (continuation == null) {
                Innertube.nextPage(
                    videoId = videoId,
                    playlistId = playlistId,
                    params = parameters,
                    playlistSetVideoId = playlistSetVideoId
                )?.map { nextResult ->
                    playlistId = nextResult.playlistId
                    parameters = nextResult.params
                    playlistSetVideoId = nextResult.playlistSetVideoId

                    nextResult.itemsPage
                }
            } else {
                Innertube.nextPage(continuation = continuation)
            }?.getOrNull()?.let { songsPage ->
                mediaItems = songsPage.items?.map(Innertube.SongItem::asMediaItem)
                songsPage.continuation?.takeUnless { nextContinuation == it }
            }
        }

        return mediaItems ?: emptyList()
    }
}
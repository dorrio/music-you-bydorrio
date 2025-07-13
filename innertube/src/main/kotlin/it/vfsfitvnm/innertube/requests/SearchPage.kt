package es-remix.innertube.requests

import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import es-remix.innertube.Innertube
import es-remix.innertube.models.ContinuationResponse
import es-remix.innertube.models.MusicShelfRenderer
import es-remix.innertube.models.SearchResponse
import es-remix.innertube.models.bodies.ContinuationBody
import es-remix.innertube.models.bodies.SearchBody
import es-remix.innertube.utils.runCatchingNonCancellable

suspend fun <T : Innertube.Item> Innertube.searchPage(
    query: String,
    params: String,
    fromMusicShelfRendererContent: (MusicShelfRenderer.Content) -> T?
) = runCatchingNonCancellable {
    val response = client.post(SEARCH) {
        setBody(
            SearchBody(
                query = query,
                params = params
            )
        )
        mask("contents.tabbedSearchResultsRenderer.tabs.tabRenderer.content.sectionListRenderer.contents.musicShelfRenderer(continuations,contents.$MUSIC_RESPONSIVE_LIST_ITEM_RENDERER_MASK)")
    }.body<SearchResponse>()

    response
        .contents
        ?.tabbedSearchResultsRenderer
        ?.tabs
        ?.firstOrNull()
        ?.tabRenderer
        ?.content
        ?.sectionListRenderer
        ?.contents
        ?.lastOrNull()
        ?.musicShelfRenderer
        ?.toItemsPage(fromMusicShelfRendererContent)
}

suspend fun <T : Innertube.Item> Innertube.searchPage(
    continuation: String,
    fromMusicShelfRendererContent: (MusicShelfRenderer.Content) -> T?
) = runCatchingNonCancellable {
    val response = client.post(SEARCH) {
        setBody(ContinuationBody(continuation = continuation))
        mask("continuationContents.musicShelfContinuation(continuations,contents.$MUSIC_RESPONSIVE_LIST_ITEM_RENDERER_MASK)")
    }.body<ContinuationResponse>()

    response
        .continuationContents
        ?.musicShelfContinuation
        ?.toItemsPage(fromMusicShelfRendererContent)
}

private fun <T : Innertube.Item> MusicShelfRenderer?.toItemsPage(mapper: (MusicShelfRenderer.Content) -> T?) =
    Innertube.ItemsPage(
        items = this
            ?.contents
            ?.mapNotNull(mapper),
        continuation = this
            ?.continuations
            ?.firstOrNull()
            ?.nextContinuationData
            ?.continuation
    )
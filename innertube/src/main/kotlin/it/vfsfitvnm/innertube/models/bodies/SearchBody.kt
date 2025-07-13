package es-remix.innertube.models.bodies

import es-remix.innertube.models.Context
import es-remix.innertube.models.YouTubeClient
import kotlinx.serialization.Serializable

@Serializable
data class SearchBody(
    val context: Context = YouTubeClient.WEB_REMIX.toContext(),
    val query: String,
    val params: String
)

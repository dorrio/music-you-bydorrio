package es-remix.innertube.models.bodies

import es-remix.innertube.models.Context
import es-remix.innertube.models.YouTubeClient
import kotlinx.serialization.Serializable

@Serializable
data class BrowseBody(
    val context: Context = YouTubeClient.WEB_REMIX.toContext(),
    val browseId: String,
    val params: String? = null
)

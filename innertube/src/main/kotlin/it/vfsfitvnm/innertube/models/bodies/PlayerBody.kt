package es.remix.innertube.models.bodies

import es.remix.innertube.models.Context
import es.remix.innertube.models.YouTubeClient
import kotlinx.serialization.Serializable

@Serializable
data class PlayerBody(
    val context: Context = YouTubeClient.IOS.toContext(),
    val videoId: String,
    val playlistId: String? = null
)
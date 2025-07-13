package es-remix.innertube.models.bodies

import es-remix.innertube.models.Context
import es-remix.innertube.models.YouTubeClient
import kotlinx.serialization.Serializable

@Serializable
data class NextBody(
    val context: Context = YouTubeClient.WEB_REMIX.toContext(),
    val videoId: String?,
    val isAudioOnly: Boolean = true,
    val playlistId: String? = null,
    val tunerSettingValue: String = "AUTOMIX_SETTING_NORMAL",
    val index: Int? = null,
    val params: String? = null,
    val playlistSetVideoId: String? = null,
    val watchEndpointMusicSupportedConfigs: WatchEndpointMusicSupportedConfigs = WatchEndpointMusicSupportedConfigs(
        musicVideoType = "MUSIC_VIDEO_TYPE_ATV"
    )
) {
    @Serializable
    data class WatchEndpointMusicSupportedConfigs(
        val musicVideoType: String
    )
}

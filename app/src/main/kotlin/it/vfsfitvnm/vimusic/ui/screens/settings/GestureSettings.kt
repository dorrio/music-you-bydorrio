package es.remix.vimusic.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import es.remix.vimusic.LocalPlayerPadding
import es.remix.vimusic.R
import es.remix.vimusic.utils.listGesturesEnabledKey
import es.remix.vimusic.utils.miniplayerGesturesEnabledKey
import es.remix.vimusic.utils.playerGesturesEnabledKey
import es.remix.vimusic.utils.rememberPreference

@Composable
fun GestureSettings() {
    val playerPadding = LocalPlayerPadding.current

    var listGesturesEnabled by rememberPreference(listGesturesEnabledKey, true)
    var playerGesturesEnabled by rememberPreference(playerGesturesEnabledKey, true)
    var miniplayerGesturesEnabled by rememberPreference(miniplayerGesturesEnabledKey, true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp + playerPadding)
    ) {
        SwitchSettingEntry(
            title = stringResource(id = R.string.list_gestures),
            text = stringResource(id = R.string.list_gestures_description),
            icon = Icons.Outlined.LibraryMusic,
            isChecked = listGesturesEnabled,
            onCheckedChange = { listGesturesEnabled = !listGesturesEnabled }
        )

        SwitchSettingEntry(
            title = stringResource(id = R.string.player_gestures),
            text = stringResource(id = R.string.player_gestures_description),
            icon = Icons.Outlined.PlayCircle,
            isChecked = playerGesturesEnabled,
            onCheckedChange = { playerGesturesEnabled = !playerGesturesEnabled }
        )

        SwitchSettingEntry(
            title = stringResource(id = R.string.miniplayer_gestures),
            text = stringResource(id = R.string.player_gestures_description),
            icon = Icons.Outlined.PlayArrow,
            isChecked = miniplayerGesturesEnabled,
            onCheckedChange = { miniplayerGesturesEnabled = !miniplayerGesturesEnabled }
        )
    }
}
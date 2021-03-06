package com.example.uaep.ui.match

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Copyright
import androidx.compose.material.icons.twotone.Copyright
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.dto.Player
import com.example.uaep.enums.Position
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_dark_error
import com.example.uaep.ui.theme.md_theme_light_error
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary
import com.example.uaep.ui.theme.md_theme_light_tertiary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PositionButton(
    color: Color,
    position: Position,
    player: Player?,
    playerSelect: (Player?) -> Unit,
    teamSelect: (Boolean?) -> Unit,
    posSelect: (String?) -> Unit,
    pos_name: String,
    reverse: Boolean,
    teamB: Boolean?,
    profile: ProfileDto?,
    captain: Boolean,
    pos: String?,
    dialogSelect: (ProfileDto?) -> Unit
) {
    var enabled by remember { mutableStateOf(true) }
    //var pos = player?.position
    Log.i("Captain", captain.toString())
    Log.i("position_button_stat", (player?.name ?: "none" ) + pos + pos_name + teamB.toString() + reverse.toString())
    if(pos != null &&pos_name==pos && teamB != null && teamB == reverse) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.inverseSurface,
                    shape = RoundedCornerShape(0)
                )
                .padding(5.dp)
        ) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color, shape = CircleShape)
                    .layout() { measurable, constraints ->
                        // Measure the composable
                        val placeable = measurable.measure(constraints)

                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        var heightCircle = currentHeight
                        if (placeable.width > heightCircle)
                            heightCircle = placeable.width

                        //assign the dimension and the center position
                        layout(heightCircle, heightCircle) {
                            // Where the composable gets placed
                            placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                        }
                    }) {
                Text(
                    text = position.value,
                    modifier =
                    Modifier
                        .padding(10.dp)
                        .defaultMinSize(20.dp)
                        .clip(CircleShape)
                        .combinedClickable(
                            onClick = {
                                // TODO : ????????? ????????? ?????? ????????????
                                // TODO : ????????? ????????? ??? ?????? ????????? ??????.
//                                if (position.value == Position.GK.value
//                                    || position.value == Position.DF.value
//                                    || position.value == Position.MF.value
//                                    || position.value == Position.FW.value
//                                ) {
                                    //TODO: ?????? ???????????? ??????????????????
                                    playerSelect(player)
                                    teamSelect(reverse)
                                    posSelect(pos_name)
//                                } else {
//                                    when (color) {
//                                        md_theme_light_primary -> pos = Position.GK.value
//                                        md_theme_light_tertiary -> pos = Position.DF.value
//                                        md_theme_light_secondary -> pos = Position.MF.value
//                                        md_theme_light_error -> pos = Position.FW.value
//                                    }
//                                }
                            },
                            onLongClick = {
                                dialogSelect(profile)
                            }
                        )
                    ,
                    textAlign = TextAlign.Center,
                    color = md_theme_light_onPrimary,
                )
            }
            if(player!=null) {
                Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier
                    .background(color = androidx.compose.material3.MaterialTheme.colorScheme.inverseSurface)
                    .padding(1.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(0)
                    )
                    .padding(1.dp)) {
                    if (captain) {
                        Icon(
                            imageVector = Icons.TwoTone.Copyright,
                            contentDescription = null,
                            tint = md_theme_light_primary
                        )
                    }
                    Text(
                        text = player.name,
                        color = md_theme_light_primary
                    )
                }
            }else{
                Spacer(Modifier.height(20.dp))
            }
        }
    }else{
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(5.dp)
        ) {
            Box(contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color, shape = CircleShape)
                    .layout() { measurable, constraints ->
                        // Measure the composable
                        val placeable = measurable.measure(constraints)

                        //get the current max dimension to assign width=height
                        val currentHeight = placeable.height
                        var heightCircle = currentHeight
                        if (placeable.width > heightCircle)
                            heightCircle = placeable.width

                        //assign the dimension and the center position
                        layout(heightCircle, heightCircle) {
                            // Where the composable gets placed
                            placeable.placeRelative(0, (heightCircle - currentHeight) / 2)
                        }
                    }) {
                Text(
                    text = position.value,
                    modifier =
                    Modifier
                        .padding(10.dp)
                        .defaultMinSize(20.dp)
                        .clip(CircleShape)
                        .combinedClickable(
                            onClick = {
                            // TODO : ????????? ????????? ?????? ????????????
                            // TODO : ????????? ????????? ??? ?????? ????????? ??????.
//                            if (position.value == Position.GK.value
//                                || position.value == Position.DF.value
//                                || position.value == Position.MF.value
//                                || position.value == Position.FW.value
//                            ) {
                                //TODO: ?????? ???????????? ??????????????????
                                playerSelect(player)
                                teamSelect(reverse)
                                posSelect(pos_name)
//                            } else {
//                                when (color) {
//                                    md_theme_light_primary -> pos = Position.GK.value
//                                    md_theme_light_tertiary -> pos = Position.DF.value
//                                    md_theme_light_secondary -> pos = Position.MF.value
//                                    md_theme_light_error -> pos = Position.FW.value
//                                }
//                            }
                            },
                            onLongClick = {
                                dialogSelect(profile)
                            }
                        )
                    ,
                    textAlign = TextAlign.Center,
                    color = md_theme_light_onPrimary,
                )
            }
            if(player!=null) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .background(color = androidx.compose.material3.MaterialTheme.colorScheme.inverseSurface)
                        .padding(1.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(0)
                        )
                        .padding(1.dp)
                ) {
                    if (captain) {
                        Icon(
                            imageVector = Icons.Filled.Copyright,
                            contentDescription = null,
                            tint = md_theme_light_primary
                        )
                    }
                    Text(
                        text = player.name,
                        color = md_theme_light_primary,

                        )
                }
            }else{
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun PositionButtonPreview() {
    UaepTheme {
        PositionButton(
            color = md_theme_light_primary,
            position = Position.FW,
            player = Player(
                email = "tma",
                name = "name",
                gender = "??????",
                province = "province",
                town = "town",
                position = "fw",
                level = "?????????1"
            ),
            posSelect = {  },
            teamSelect = {},
            playerSelect = {},
            pos_name = "fw",
            reverse = true,
            teamB = true,
            profile = ProfileDto(
                name = "name",
                gender = "??????",
                province = "province",
                town = "town",
                position = "fw",
                level = "?????????1",
                positionChangePoint = null
            ),
            captain = true,
            pos = "fw",
            dialogSelect = {}
        )
    }
}
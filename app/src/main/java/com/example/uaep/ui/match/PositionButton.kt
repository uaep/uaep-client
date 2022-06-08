package com.example.uaep.ui.match

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
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
import com.example.uaep.ui.theme.*

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
    userEmail: String?
) {
    var enabled by remember { mutableStateOf(true) }
    var pos = player?.position

    Column {
        Box(contentAlignment= Alignment.Center,
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
                modifier = if(userEmail!=player?.email) {
                    Modifier
                        .padding(10.dp)
                        .defaultMinSize(20.dp)
                        .clip(CircleShape)
                        .clickable() {
                            // TODO : 유저가 없으면 내가 들어가고
                            // TODO : 유저가 있으면 그 유저 프로필 본다.
                            if (position.value == Position.GK.value
                                || position.value == Position.DF.value
                                || position.value == Position.MF.value
                                || position.value == Position.FW.value
                            ) {
                                //TODO: 유저 이름으로 변경되게하기
                                playerSelect(player)
                                teamSelect(reverse)
                                posSelect(pos_name)
                            } else {
                                when (color) {
                                    md_theme_light_primary -> pos = Position.GK.value
                                    md_theme_light_tertiary -> pos = Position.DF.value
                                    md_theme_light_secondary -> pos = Position.MF.value
                                    md_theme_light_error -> pos = Position.FW.value
                                }
                            }
                        }
                }else{
                    Modifier

                        .border(width = 5.dp, color = md_theme_dark_error, shape = CircleShape)
                        .padding(10.dp)
                        .defaultMinSize(20.dp)
                        .clip(CircleShape)
                        .clickable() {
                            // TODO : 유저가 없으면 내가 들어가고
                            // TODO : 유저가 있으면 그 유저 프로필 본다.
                            if (position.value == Position.GK.value
                                || position.value == Position.DF.value
                                || position.value == Position.MF.value
                                || position.value == Position.FW.value
                            ) {
                                //TODO: 유저 이름으로 변경되게하기
                                playerSelect(player)
                                teamSelect(reverse)
                                posSelect(pos_name)
                            } else {
                                when (color) {
                                    md_theme_light_primary -> pos = Position.GK.value
                                    md_theme_light_tertiary -> pos = Position.DF.value
                                    md_theme_light_secondary -> pos = Position.MF.value
                                    md_theme_light_error -> pos = Position.FW.value
                                }
                            }
                        }
                     },
                textAlign = TextAlign.Center,
                color = md_theme_light_onPrimary,
            )
        }
        Text(
            text = player?.name ?: ""
        )
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
                email = "test@gmail.com",
                name = "name",
                gender = "남성",
                address = "address",
                position = "FW",
                levelPoint = 0
            ),
            posSelect = {  },
            teamSelect = {},
            playerSelect = {},
            pos_name = "mf2",
            reverse = true,
            userEmail = "test@gmail.com"
        )
    }
}
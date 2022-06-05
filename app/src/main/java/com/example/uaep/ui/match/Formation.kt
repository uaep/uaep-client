package com.example.uaep.ui.match

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.uaep.dto.Player
import com.example.uaep.dto.Team
import com.example.uaep.enums.Position
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_error
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary
import com.example.uaep.ui.theme.md_theme_light_tertiary

@Composable
fun Formation(
    reverse: Boolean,
    modifier: Modifier,
    team: Team?
//    vm: FormationViewModel
) {
    if(team != null) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_primary else md_theme_light_error,
                    position = if (!reverse) Position.GK else Position.FW,
                    player = if (!reverse) team.gk else team.fw1
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_tertiary else md_theme_light_secondary,
                    position = if (!reverse) Position.DF else Position.MF,
                    player = if (!reverse) team.df1 else team.fw2
                )
                PositionButton(
                    color = if (!reverse) md_theme_light_tertiary else md_theme_light_secondary,
                    position = if (!reverse) Position.DF else Position.MF,
                    player = if (!reverse) team.df2 else team.mf
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,

                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_secondary else md_theme_light_tertiary,
                    position = if (!reverse) Position.MF else Position.DF,
                    player = if (!reverse) team.mf else team.df2
                )
                PositionButton(
                    color = if (!reverse) md_theme_light_secondary else md_theme_light_tertiary,
                    position = if (!reverse) Position.MF else Position.DF,
                    player = if (!reverse) team.fw2 else team.df1
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_error else md_theme_light_primary,
                    position = if (!reverse) Position.FW else Position.GK,
                    player = if (!reverse) team.fw1 else team.gk
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320
)
@Composable
fun FormationPreview() {
    UaepTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Formation(
                reverse = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                team = Team(
                        fw1 = Player(
                            email = "test@gmail.com",
                            name = "name",
                            gender = "남성",
                            address = "address",
                            position = "FW",
                            levelPoint = 0
                        ),
                        fw2 = null,
                        mf = null,
                        df1 = null,
                        df2 = null,
                        gk = null,
                        captain = Player(
                            email = "test@gmail.com",
                            name = "name",
                            gender = "남성",
                            address = "address",
                            position = "FW",
                            levelPoint = 0
                        )
                )
            )
        }
    }
}
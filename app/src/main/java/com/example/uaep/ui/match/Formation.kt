package com.example.uaep.ui.match

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.dto.Player
import com.example.uaep.dto.Team
import com.example.uaep.enums.Position
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_error
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary
import com.example.uaep.ui.theme.md_theme_light_tertiary

@Composable
fun Formation(
    reverse: Boolean,
    modifier: Modifier,
    team: Team?,
    playerSelect: (Player?) -> Unit,
    teamSelect: (Boolean?) -> Unit,
    posSelect: (String?) -> Unit,
    teamB: Boolean?,
    profile: ProfileDto?
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
                    player = if (!reverse) team.gk else team.fw,
                    playerSelect = playerSelect,
                    teamSelect = teamSelect,
                    posSelect = posSelect,
                    pos_name = if (!reverse) "gk" else "fw",
                    reverse = reverse,
                    teamB = teamB,
                    profile = profile
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_tertiary else md_theme_light_secondary,
                    position = if (!reverse) Position.DF else Position.MF,
                    player = if (!reverse) team.df1 else team.mf1,
                    playerSelect = playerSelect,
                    teamSelect = teamSelect,
                    posSelect = posSelect,
                    pos_name = if (!reverse) "df1" else "mf1",
                    reverse = reverse,
                    teamB = teamB,
                    profile = profile
                )
                PositionButton(
                    color = if (!reverse) md_theme_light_tertiary else md_theme_light_secondary,
                    position = if (!reverse) Position.DF else Position.MF,
                    player = if (!reverse) team.df2 else team.mf2,
                    playerSelect = playerSelect,
                    teamSelect = teamSelect,
                    posSelect = posSelect,
                    pos_name = if (!reverse) "df2" else "mf2",
                    reverse = reverse,
                    teamB = teamB,
                    profile = profile
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,

                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_secondary else md_theme_light_tertiary,
                    position = if (!reverse) Position.MF else Position.DF,
                    player = if (!reverse) team.mf2 else team.df2,
                    playerSelect = playerSelect,
                    teamSelect = teamSelect,
                    posSelect = posSelect,
                    pos_name = if (!reverse) "mf2" else "df2",
                    reverse = reverse,
                    teamB = teamB,
                    profile = profile
                )
                PositionButton(
                    color = if (!reverse) md_theme_light_secondary else md_theme_light_tertiary,
                    position = if (!reverse) Position.MF else Position.DF,
                    player = if (!reverse) team.mf1 else team.df1,
                    playerSelect = playerSelect,
                    teamSelect = teamSelect,
                    posSelect = posSelect,
                    pos_name = if (!reverse) "mf1" else "df1",
                    reverse = reverse,
                    teamB = teamB,
                    profile = profile
                )
            }
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                PositionButton(
                    color = if (!reverse) md_theme_light_error else md_theme_light_primary,
                    position = if (!reverse) Position.FW else Position.GK,
                    player = if (!reverse) team.fw else team.gk,
                    playerSelect = playerSelect,
                    teamSelect = teamSelect,
                    posSelect = posSelect,
                    pos_name = if (!reverse) "fw" else "gk",
                    reverse = reverse,
                    teamB = teamB,
                    profile = profile
                )
            }
        }
    }else{
        Spacer(modifier = Modifier.height(200.dp))
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
                        fw = Player(
                            email = "test@gmail.com",
                            name = "name",
                            gender = "남성",
                            address = "address",
                            position = "FW",
                            levelPoint = 0
                        ),
                        mf1 = null,
                        mf2 = null,
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
                ),
                posSelect = {},
                teamSelect = {},
                playerSelect = {},
                teamB = false,
                profile = ProfileDto(
                    name = "name",
                    gender = "남성",
                    address = "address",
                    position = "fw",
                    levelPoint = 0,
                    positionChangePoint = null
                )
            )
        }
    }
}
package com.example.uaep.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.dto.Player
import com.example.uaep.dto.RoomDto
import com.example.uaep.dto.Team
import com.example.uaep.model.Gender
import com.example.uaep.model.Rank
import com.example.uaep.model.Room
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_inversePrimary
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_outline
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary
import com.example.uaep.utils.isScrolled
import java.util.*

private val defaultSpacerSize = 16.dp

enum class FieldPosition{
    GK, DF, MF, CF
}


@Composable
fun MatchScreen(
    room: RoomDto,
    isExpandedScreen: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {

    Row(modifier.fillMaxSize()) {
        val context = LocalContext.current

        MatchScreenContent(
            room = room,

            navigationIconContent = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_navigate_up),
                        tint = md_theme_light_secondary
                    )
                }
            },

            bottomBarContent =
            {
                BottomBar(
                    room = room
                )
            }

            ,
            lazyListState = lazyListState
        )



    }

}

@Composable
fun MatchScreenContent(
    room: RoomDto,
    navigationIconContent: @Composable (() -> Unit)? = null,
    bottomBarContent: @Composable () -> Unit = { },
    lazyListState: LazyListState = rememberLazyListState()
){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.h5,
                            color = md_theme_light_outline,
                            modifier = Modifier
                                .padding(start = 10.dp)
                                .weight(1.5f)
                        )
                    }
                },
                navigationIcon = navigationIconContent,
                elevation = if (!lazyListState.isScrolled) 0.dp else 4.dp,
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        bottomBar = bottomBarContent
    ) { innerPadding ->


        RoomContainer(
            room = room,
            modifier = Modifier
                // innerPadding takes into account the top and bottom bar
                .padding(innerPadding)
        )


    }

}

@Composable
fun RoomContainer(
    room: RoomDto,
    modifier: Modifier = Modifier
) {
    var position by remember{ mutableStateOf<String?>(null)}
    var team by remember{ mutableStateOf<Boolean?>(false)}
    var enabled by remember { mutableStateOf(false) }

    Column() {

        Scaffold(modifier = Modifier.weight(1f)) { innerPadding ->
            Text(position ?: "none")
            Text(team.toString() ?: "none", textAlign = TextAlign.Right)
            Image(
                painter = painterResource(id = R.drawable.football_background),
                contentDescription = stringResource(id = R.string.foot_ball_back),
                //alignment = Alignment.TopStart,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()

            )
            Column(
                modifier = modifier.padding(innerPadding).fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {


//            Image(painter = painterResource(id =R.drawable.football_stage), contentDescription = "")
//            ReadyButton(){
//                position.value = it
//            }
                Spacer(Modifier.height(defaultSpacerSize))
                Formation(reverse = false, Modifier.fillMaxWidth(), room.teamA)

                Spacer(Modifier.height(defaultSpacerSize*2))

                Formation(reverse = true, Modifier.fillMaxWidth(), room.teamB)
                //Spacer(Modifier.height(8.dp))

            }
        }
        RoomDesc(room = room, modifier = Modifier.weight(1f))
    }

}

@Composable
private fun BottomBar(
    room: RoomDto,
    modifier: Modifier = Modifier
) {
    Surface(elevation = 8.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Vertical))
                .height(56.dp)
                .fillMaxWidth()
        ) {
            when(room.number){
                "6vs6" -> {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = md_theme_light_primary)
                    ) {
                        Text(text = stringResource(R.string.match_ready), color = md_theme_light_onPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun ReadyButton(
    modifier: Modifier = Modifier,
    updatePosition: (FieldPosition) -> Unit
){
    Button(
        onClick = {updatePosition(
            FieldPosition.CF
        )},

        ){
        Text("CK")
    }
}

@Composable
private fun PostListDivider() {
    Divider(
        modifier = Modifier.fillMaxWidth(),
        color = md_theme_light_inversePrimary.copy(alpha = 0.8f)
    )
}


@Composable
fun RoomDesc(
    room: RoomDto,
    modifier: Modifier = Modifier
){

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PostListDivider()
        Spacer(Modifier.height(8.dp))
        Row() {
            RoomTitle(room)
        }
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.SpaceEvenly) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ReadTime(room,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = md_theme_light_secondary,
                            shape = RoundedCornerShape(15)
                        )
                        .padding(7.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                RoomRank(room,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = md_theme_light_secondary,
                            shape = RoundedCornerShape(15)
                        )
                        .padding(7.dp)
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                RoomPersonnel(room,
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = md_theme_light_secondary,
                            shape = RoundedCornerShape(15)
                        )
                        .padding(7.dp)
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        PostListDivider()
    }
}

@Composable
fun RoomTitle(room: RoomDto){
    Text(room.title,
        style = MaterialTheme.typography.h4)
}

@Composable
fun ReadTime(room: RoomDto, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(
            id = R.string.home_room_hour_min,
            formatArgs = arrayOf(
                room.date.hours,
                room.date.minutes
            )
        ),
        style = MaterialTheme.typography.h5,
        textAlign = TextAlign.Start,
        color = md_theme_light_secondary,
        modifier = modifier,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RoomPersonnel(
    room: RoomDto,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = room.number,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = md_theme_light_secondary
            )
        }
    }
}

@Composable
fun RoomRank(
    room: RoomDto,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(
                    id = R.string.home_room_date,
                    formatArgs = arrayOf(
                        room.date.month,
                        room.date.date
                    )
                ),
                style = MaterialTheme.typography.h5,
                color = md_theme_light_secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview("Simple Room Card")
@Composable
fun SimpleDescPreview() {
    val room1 = RoomDto(
        id = 2001,
        title = "let's play soccer",
        gender = "male",
        date = Date(2016,5,4,12,14),
        number = "6vs6",
        host="sonny",
        teamA = null,
        teamB = null
    )

    UaepTheme {
        Surface {
            RoomDesc(room1)
        }
    }
}

@Preview("Simple Room Match")
@Composable
fun SimpleRoomPreview() {
    val room1 = RoomDto(
        id = 2001,
        title = "let's play soccer",
        gender = "male",
        date = Date(2016,5,4,12,14),
        number = "6vs6",
        host="sonny",
        teamA = Team(
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
        ),
        teamB = Team(
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

    UaepTheme {
        Surface {
            MatchScreen(room1, false, {})
        }
    }
}
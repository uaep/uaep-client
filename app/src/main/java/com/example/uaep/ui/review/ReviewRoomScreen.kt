package com.example.uaep.ui.review

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.*
import com.example.uaep.model.Review
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.match.Formation
import com.example.uaep.ui.profile.ProfileCard
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.theme.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun ReviewRoomScreen(
    room: RoomDto,
    isExpandedScreen: Boolean,
    onBack: () -> Unit,
    onRefresh: (String) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    navController: NavController
) {
    Log.i("room_enter_2", room.toString())

    val pos = remember{ mutableStateOf<String?>(null) }
    val team = remember{ mutableStateOf<Boolean?>(false) }
    val player = remember{ mutableStateOf<Player?>(null) }
    var rate = remember { mutableStateOf<Float?>(null) }
    val profile = getProfileDto(player.value)
    val userEmail = player.value?.email ?: null
    val rateList = ArrayList<Review>()
    if(pos.value != null && rate.value != null && team.value != null){
        rateList.add(Review(pos.value!!, rate.value!!, team.value!!))
    }

    Log.i("player", player.value.toString())
    Log.i("pos", pos.value.toString())
    Log.i("team", team.value.toString())
    val context = LocalContext.current
    Row(modifier.fillMaxSize()) {

        ReviewRoomScreenContent(
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
                ReviewRoomBottomBar(
                    room = room,
                    rateList = rateList,
                    onRefresh = onRefresh,
                    context = context,
                )
            },
            lazyListState = lazyListState,
            onRefresh = onRefresh,
            teamSelect = {team.value = it},
            posSelect = {pos.value = it},
            playerSelect = {player.value = it},
            playerNotSelect ={player.value = null
                             rateList.clear()},
            rateSelect = {rate.value = it},
            profile = profile,
            userEmail = userEmail,
            navController = navController,
            teamB = team.value,
            pos = pos.value
        )
    }
}

@Composable
fun ReviewRoomScreenContent(
    room: RoomDto,
    navigationIconContent: @Composable (() -> Unit)? = null,
    bottomBarContent: @Composable () -> Unit = { },
    lazyListState: LazyListState = rememberLazyListState(),
    onRefresh: (String) -> Unit,
    teamSelect: (Boolean?) -> Unit,
    posSelect: (String?) -> Unit,
    playerSelect: (Player?) -> Unit,
    playerNotSelect: () -> Unit,
    rateSelect: (Float) -> Unit,
    profile: ProfileDto?,
    userEmail: String?,
    navController: NavController,
    teamB: Boolean?,
    pos: String?
){
    Scaffold(
        topBar = {
            CommonTopAppBar(openDrawer = {}, navController = navController)
        },
        bottomBar = bottomBarContent
    ) { innerPadding ->
        Column() {

            ReviewRoomContainer(
                room = room,
                onRefresh = onRefresh,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                teamSelect = teamSelect,
                posSelect = posSelect,
                playerSelect = playerSelect,
                playerNotSelect = playerNotSelect,
                rateSelect = rateSelect,
                profile = profile,
                userEmail = userEmail,
                teamB = teamB,
                pos = pos
            )
        }

    }

}

@Composable
fun ReviewRoomContainer(
    room: RoomDto,
    onRefresh: (String) -> Unit,
    modifier: Modifier = Modifier,
    teamSelect: (Boolean?) -> Unit,
    posSelect: (String?) -> Unit,
    playerSelect: (Player?) -> Unit,
    playerNotSelect: () -> Unit,
    rateSelect: (Float) -> Unit,
    profile: ProfileDto?,
    userEmail: String?,
    teamB: Boolean?,
    pos: String?
) {
    Column(modifier.verticalScroll(rememberScrollState())) {

        Scaffold(
            modifier = Modifier
                //.weight(1f)
                .height(550.dp),
            bottomBar = {
                BottomAppBar(contentPadding = PaddingValues(0.dp),elevation = 0.dp, backgroundColor = Color(0xD9FFFFFF).compositeOver(
                    Color.White)){} }
        ) { innerPadding ->

            Image(
                painter = painterResource(id = R.drawable.football_background),
                contentDescription = stringResource(id = R.string.foot_ball_back),
                //alignment = Alignment.TopStart,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth()
                    .height(500.dp)
                //.fillMaxSize()

            )

            if (profile != null && userEmail != AuthService.getCookieJar().loadEmail() && pos != null && teamB != null)
                ReviewDialog(visible = true, onDismissRequest = playerNotSelect, profileDto = profile, pos = pos, teamB = teamB, room = room, onRefresh = onRefresh, context = LocalContext.current)
                //ProfileDialog(visible = true, playerNotSelect, profile!!)

            Column(
                modifier = modifier
                    //.padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {

                //Spacer(Modifier.height(defaultSpacerSize))
                Formation(reverse = false, Modifier.fillMaxWidth(), room.teamA, playerSelect ,teamSelect, posSelect, teamB, profile)

                //Spacer(Modifier.height(defaultSpacerSize))

                Formation(reverse = true, Modifier.fillMaxWidth(), room.teamB, playerSelect,teamSelect, posSelect, teamB, profile)
                //Spacer(Modifier.height(50.dp))
            }
        }
        Log.i("flag", "flag1")
        RoomDesc(room = room,
            modifier = Modifier
            //.weight(1f)
            //.height(200.dp)
        )
        //Spacer(Modifier.height(defaultSpacerSize*3))
    }

}

@Composable
private fun ReviewRoomBottomBar(
    room: RoomDto,
    modifier: Modifier = Modifier,
    onRefresh: (String) -> Unit,
    context: Context,
    rateList: ArrayList<Review>
) {
    Surface(elevation = 0.dp, modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                //.windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Vertical))
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Log.i("bottom_bar", room.number)
            when(room.date > Date()){
                true -> {
                    if (rateList.isNotEmpty()) {
                        Button(
                            onClick =
                            {

                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = md_theme_light_primary)
                        ) {
                            if(rateList.isEmpty()){
                                Text(
                                    text = stringResource(R.string.review_wait),
                                    color = md_theme_light_onPrimary,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }else {
                                Text(
                                    text = stringResource(R.string.review_send),
                                    color = md_theme_light_onPrimary,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold
                                )
                            }
                        }
                    }else{

                    }
                }
                false -> {}
            }
        }
    }
}


fun getProfileDto(player: Player?): ProfileDto?{
    if(player != null){
        return ProfileDto(
            player.name,
            player.position,
            player.address,
            player.gender,
            player.levelPoint,
            null
        )
    }
    return null
}

@Composable
fun CustomAlertDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ) {
        content()
    }
}

@Composable
private fun ProfileDialog(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    profileDto: ProfileDto
) {
    if (visible) {
        CustomAlertDialog(onDismissRequest = { onDismissRequest() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(color = Color.Gray)
            ) {
                ProfileCard(profileDto = profileDto, onUpdateUserInfo = {})
            }
        }
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
    Log.i("TEXT", room.title)
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
        id = "2001",
        title = "let's play soccer",
        gender = "남성",
        date = Date(2016,5,4,12,14),
        number = "6v6",
        host="sonny",
        teamA = null,
        teamB = null,
        status = null,
        teamA_status = null,
        teamB_status = null,
        apply_flag = null
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
        id = "2001",
        title = "let's play soccer",
        gender = "남성",
        date = Date(2016,5,4,12,14),
        number = "6v6",
        host="sonny",
        teamA = Team(
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
        teamB = Team(
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
        status = null,
        teamA_status = null,
        teamB_status = null,
        apply_flag = null
    )

    UaepTheme {
        Surface {
            ReviewRoomScreen(room1, false, {}, {}, navController = rememberNavController())
        }
    }
}
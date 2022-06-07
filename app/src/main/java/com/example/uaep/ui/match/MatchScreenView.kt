package com.example.uaep.ui.match

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.uaep.R
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.FormationRequestDto
import com.example.uaep.dto.Player
import com.example.uaep.dto.RoomDto
import com.example.uaep.dto.Team
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.ReAuthService
import com.example.uaep.ui.components.CommonTopAppBar
import com.example.uaep.ui.profile.ProfileCard
import com.example.uaep.ui.profile.ProfileDto
import com.example.uaep.ui.theme.UaepTheme
import com.example.uaep.ui.theme.md_theme_light_errorContainer
import com.example.uaep.ui.theme.md_theme_light_inversePrimary
import com.example.uaep.ui.theme.md_theme_light_onPrimary
import com.example.uaep.ui.theme.md_theme_light_primary
import com.example.uaep.ui.theme.md_theme_light_secondary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    onRefresh: (Int) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    navController: NavController
) {
    Log.i("room_enter_2", room.toString())

    val pos = remember{ mutableStateOf<String?>(null)}
    val team = remember{ mutableStateOf<Boolean?>(false)}
    val player = remember{ mutableStateOf<Player?>(null)}
    val profile = getProfileDto(player.value)
    val userEmail = player.value?.email ?: null

    Log.i("player", player.value.toString())
    Log.i("pos", pos.value.toString())
    Log.i("team", team.value.toString())
    val context = LocalContext.current
    Row(modifier.fillMaxSize()) {

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
                    room = room,
                    pos = pos.value,
                    team = team.value,
                    onRefresh = onRefresh,
                    context = context,
                    player = player.value
                )
            },
            lazyListState = lazyListState,
            onRefresh = onRefresh,
            teamSelect = {team.value = it},
            posSelect = {pos.value = it},
            playerSelect = {player.value = it},
            playerNotSelect ={player.value = null},
            profile = profile,
            userEmail = userEmail,
            navController = navController
        )
    }
}

@Composable
fun MatchScreenContent(
    room: RoomDto,
    navigationIconContent: @Composable (() -> Unit)? = null,
    bottomBarContent: @Composable () -> Unit = { },
    lazyListState: LazyListState = rememberLazyListState(),
    onRefresh: (Int) -> Unit,
    teamSelect: (Boolean?) -> Unit,
    posSelect: (String?) -> Unit,
    playerSelect: (Player?) -> Unit,
    playerNotSelect: () -> Unit,
    profile: ProfileDto?,
    userEmail: String?,
    navController: NavController
){
    Scaffold(
        topBar = {
            CommonTopAppBar(openDrawer = {}, navController = navController)
        },
        bottomBar = bottomBarContent
    ) { innerPadding ->
        Column() {

            RoomContainer(
                room = room,
                onRefresh = onRefresh,
                modifier = Modifier
                    // innerPadding takes into account the top and bottom bar
                    .fillMaxSize()
                    .padding(innerPadding),
                teamSelect = teamSelect,
                posSelect = posSelect,
                playerSelect = playerSelect,
                playerNotSelect = playerNotSelect,
                profile = profile,
                userEmail = userEmail
            )
        }

    }

}

@Composable
fun RoomContainer(
    room: RoomDto,
    onRefresh: (Int) -> Unit,
    modifier: Modifier = Modifier,
    teamSelect: (Boolean?) -> Unit,
    posSelect: (String?) -> Unit,
    playerSelect: (Player?) -> Unit,
    playerNotSelect: () -> Unit,
    profile: ProfileDto?,
    userEmail: String?
) {
    Column(modifier.verticalScroll(rememberScrollState())) {

        Scaffold(
            modifier = Modifier
            //.weight(1f)
                .height(500.dp)
        ) { innerPadding ->

            Image(
                painter = painterResource(id = R.drawable.football_background),
                contentDescription = stringResource(id = R.string.foot_ball_back),
                //alignment = Alignment.TopStart,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()

            )

            Column(verticalArrangement = Arrangement.SpaceBetween) {
                if (room.teamA == null)
                    positionButton(room = room, teamA = true, onRefresh = onRefresh)
                else
                    Spacer(Modifier.height(250.dp))
                if (room.teamB == null)
                    positionButton(room = room, teamA = false, onRefresh = onRefresh)
            }
            if (profile != null && userEmail != AuthService.getCookieJar().loadEmail())
                ProfileDialog(visible = true, playerNotSelect, profile!!)
            Column(
                modifier = modifier
                    //.padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {


//            Image(painter = painterResource(id =R.drawable.football_stage), contentDescription = "")
//            ReadyButton(){
//                position.value = it
//            }
                Spacer(Modifier.height(defaultSpacerSize))
                Formation(reverse = false, Modifier.fillMaxWidth(), room.teamA, playerSelect ,teamSelect, posSelect)

                Spacer(Modifier.height(defaultSpacerSize))

                Formation(reverse = true, Modifier.fillMaxWidth(), room.teamB, playerSelect,teamSelect, posSelect)
                Spacer(Modifier.height(50.dp))
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
private fun BottomBar(
    room: RoomDto,
    modifier: Modifier = Modifier,
    pos: String?,
    team: Boolean?,
    onRefresh: (Int) -> Unit,
    context: Context,
    player: Player?
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
            when(room.date < Date()){
                true -> {
                    if (pos !=null && team !=null) {
                        Button(
                            onClick =
                            {
                                val type = if (team) "B" else "A"
                                    Log.i("pos", pos.uppercase())
                                    AuthService.getInstance()
                                        .setPosition(room.id, type, pos.uppercase())
                                        .enqueue(object :
                                            Callback<RoomDto> {
                                            override fun onResponse(
                                                call: Call<RoomDto>,
                                                response: Response<RoomDto>
                                            ) {
                                                if (response.isSuccessful) {
                                                    Log.i("position_success", response.body().toString())
                                                } else {
                                                    Log.i("position_fail_raw", response.raw().toString())
                                                    Log.i("position_fail_head", response.headers().toString())
                                                    val errorResponse: ErrorResponse? =
                                                        Gson().fromJson(
                                                            response.errorBody()!!.charStream(),
                                                            object :
                                                                TypeToken<ErrorResponse>() {}.type
                                                        )
                                                    if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                                                        ReAuthService.getInstance().reauth()
                                                            .enqueue(object :
                                                                Callback<DummyResponse> {

                                                                override fun onResponse(
                                                                    call: Call<DummyResponse>,
                                                                    response: Response<DummyResponse>
                                                                ) {
                                                                    if (response.isSuccessful) {
                                                                        val tokens =
                                                                            CookieChanger<DummyResponse>().change(
                                                                                response
                                                                            )
                                                                        AuthService.getCookieJar()
                                                                            .saveToken(tokens)
                                                                    }
                                                                }

                                                                override fun onFailure(
                                                                    call: Call<DummyResponse>,
                                                                    t: Throwable
                                                                ) {
                                                                    Log.i("test", "실패$t")
                                                                }
                                                            })
                                                    }
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<RoomDto>,
                                                t: Throwable
                                            ) {
                                                Log.i("test", "실패$t")
                                            }
                                        })
                                onRefresh(room.id)
                            },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = md_theme_light_primary)
                        ) {
                            if(player!=null&&AuthService.getCookieJar().loadEmail()==player.email){
                                Text(
                                    text = stringResource(R.string.match_clear_ready),
                                    color = md_theme_light_onPrimary
                                )
                            }else {
                                Text(
                                    text = stringResource(R.string.match_ready),
                                    color = md_theme_light_onPrimary
                                )
                            }
                        }
                    }else{
                        Button(
                            onClick = { /*TODO*/ },
                            modifier = Modifier.fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = md_theme_light_errorContainer)
                        ) {
                            Text(
                                text = stringResource(R.string.match_not_ready),
                                color = md_theme_light_primary
                            )
                        }
                    }
                }
                false -> {
                    if (room.teamA !=null && room.teamB !=null) {
                        var teamA: Boolean? = null
                        if(room.teamA.captain!!.email==AuthService.getCookieJar().loadEmail())
                            teamA = true
                        else if(room.teamB.captain!!.email==AuthService.getCookieJar().loadEmail())
                            teamA = false
                        if(teamA != null) {
                            Button(
                                onClick =
                                {
                                    val type = if (teamA) "A" else "B"
                                    AuthService.getInstance()
                                        .deleteTeam(room.id, type)
                                        .enqueue(object :
                                            Callback<DummyResponse> {
                                            override fun onResponse(
                                                call: Call<DummyResponse>,
                                                response: Response<DummyResponse>
                                            ) {
                                                if (response.isSuccessful) {
                                                    Log.i(
                                                        "position_success",
                                                        response.body().toString()
                                                    )
                                                } else {
                                                    Log.i(
                                                        "position_fail_raw",
                                                        response.raw().toString()
                                                    )
                                                    Log.i(
                                                        "position_fail_head",
                                                        response.headers().toString()
                                                    )
                                                    val errorResponse: ErrorResponse? =
                                                        Gson().fromJson(
                                                            response.errorBody()!!.charStream(),
                                                            object :
                                                                TypeToken<ErrorResponse>() {}.type
                                                        )
                                                    if(errorResponse!!.statusCode == 403){
                                                        Toast.makeText(context, errorResponse.message, Toast.LENGTH_LONG).show()
                                                    }
                                                    if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                                                        ReAuthService.getInstance().reauth()
                                                            .enqueue(object :
                                                                Callback<DummyResponse> {

                                                                override fun onResponse(
                                                                    call: Call<DummyResponse>,
                                                                    response: Response<DummyResponse>
                                                                ) {
                                                                    if (response.isSuccessful) {
                                                                        val tokens =
                                                                            CookieChanger<DummyResponse>().change(
                                                                                response
                                                                            )
                                                                        AuthService.getCookieJar()
                                                                            .saveToken(tokens)
                                                                    }
                                                                }

                                                                override fun onFailure(
                                                                    call: Call<DummyResponse>,
                                                                    t: Throwable
                                                                ) {
                                                                    Log.i("test", "실패$t")
                                                                }
                                                            })
                                                    }
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<DummyResponse>,
                                                t: Throwable
                                            ) {
                                                Log.i("test", "실패$t")
                                            }
                                        })
                                    onRefresh(room.id)
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = md_theme_light_secondary)
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxSize(),
                                    text = stringResource(R.string.match_finish),
                                    color = md_theme_light_onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun positionButton(
    modifier: Modifier = Modifier,
    room: RoomDto,
    teamA: Boolean,
    onRefresh: (Int) -> Unit
){
    Button(
        colors = ButtonDefaults.buttonColors(backgroundColor = md_theme_light_secondary),
        onClick = {
            var check = false
            val type = if(teamA)"A" else "B"

            do {

                AuthService.getInstance().createFormation(room.id, type, FormationRequestDto("F221")).enqueue(object :
                    Callback<RoomDto> {
                    override fun onResponse(
                        call: Call<RoomDto>,
                        response: Response<RoomDto>
                    ) {
                        if (response.isSuccessful) {
                            Log.i("room_enter", response.body().toString())

                        } else {
                            Log.i("rooms_fail_raw", response.raw().toString())
                            Log.i("rooms_fail_head", response.headers().toString())

                            val errorResponse: ErrorResponse? =
                                Gson().fromJson(
                                    response.errorBody()!!.charStream(),
                                    object : TypeToken<ErrorResponse>() {}.type
                                )
                            if (errorResponse!!.message != null && (errorResponse!!.statusCode == 401)) {
                                ReAuthService.getInstance().reauth().enqueue(object :
                                    Callback<DummyResponse> {

                                    override fun onResponse(
                                        call: Call<DummyResponse>,
                                        response: Response<DummyResponse>
                                    ) {
                                        if (response.isSuccessful) {
                                            check = true
                                            val tokens = CookieChanger<DummyResponse>().change(response)
                                            AuthService.getCookieJar().saveToken(tokens)
                                        }
                                    }
                                    override fun onFailure(
                                        call: Call<DummyResponse>,
                                        t: Throwable
                                    ) {
                                        Log.i("test", "실패$t")
                                    }
                                })
                            }
                        }
                    }
                    override fun onFailure(call: Call<RoomDto>, t: Throwable) {
                        Log.i("test", "실패$t")
                    }
                })
            }while(check)
            onRefresh(room.id)
        }
    ){
        if(teamA)
            Text("TeamA formation create")
        else
            Text("TeamB formation create")
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
        id = 2001,
        title = "let's play soccer",
        gender = "남성",
        date = Date(2016,5,4,12,14),
        number = "6v6",
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
        )
    )

    UaepTheme {
        Surface {
            MatchScreen(room1, false, {}, {}, navController = rememberNavController())
        }
    }
}
package com.example.uaep.ui.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.model.Gender
import com.example.uaep.model.Rank
import com.example.uaep.model.Room
import com.example.uaep.uitmp.*


private val defaultSpacerSize = 16.dp

enum class FieldPosition{
    GK, DF, MF, CF
}
@Composable
fun RoomContainer(
    room: Room,
    modifier: Modifier = Modifier
) {
    val position = remember{ mutableStateOf(FieldPosition.GK)}

    Column(
        modifier = modifier.padding(horizontal = defaultSpacerSize)
    ) {
        Row {
            Spacer(Modifier.height(defaultSpacerSize))
            Image(painter = painterResource(id =R.drawable.football_stage), contentDescription = "")
            ReadyButton(){
                position.value = it
            }
            Spacer(Modifier.height(defaultSpacerSize))
        }
        Row {
            Text(text = position.value.name, style = MaterialTheme.typography.h4)
            Spacer(Modifier.height(8.dp))
        }
        Row {
            RoomDesc(room = room)
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
    room: Room
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
fun RoomTitle(room: Room){
    Text(room.title,
        style = MaterialTheme.typography.h3)
}

@Composable
fun ReadTime(room: Room, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(
            id = R.string.home_room_hour_min,
            formatArgs = arrayOf(
                room.hour,
                room.minute
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
    room: Room,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(
                    id = R.string.home_room_personnel,
                    formatArgs = arrayOf(
                        room.number,
                        room.number
                    )
                ),
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                color = md_theme_light_secondary
            )
        }
    }
}

@Composable
fun RoomRank(
    room: Room,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(
                    id = R.string.home_room_rank,
                    formatArgs = arrayOf(
                        room.rank.korean
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
    val room1 = Room(
        id = "ac552dcc1741",
        title = "let's play soccer",
        rank = Rank.BIGINNER,
        gender = Gender.MAN,
        date = "July 9",
        hour = 5,
        minute = 5,
        number = 6
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
    val room1 = Room(
        id = "ac552dcc1741",
        title = "let's play soccer",
        rank = Rank.BIGINNER,
        gender = Gender.MAN,
        date = "July 9",
        hour = 5,
        minute = 5,
        number = 6
    )

    UaepTheme {
        Surface {
            RoomContainer(room1)
        }
    }
}
package com.example.uaep.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.uaep.R
import com.example.uaep.model.Gender
import com.example.uaep.model.Rank
import com.example.uaep.model.Room
import com.example.uaep.uitmp.UaepTheme
import com.example.uaep.uitmp.md_theme_light_primary
import com.example.uaep.uitmp.md_theme_light_secondary

@Composable
fun RoomTitle(room: Room){
    Text(room.title,
        style = MaterialTheme.typography.h6)
}

@Composable
fun ReadTime(room: Room) {
    Text(
        text = stringResource(
            id = R.string.home_room_hour_min,
            formatArgs = arrayOf(
                room.hour,
                room.minute
            )
        ),
        style = MaterialTheme.typography.h4,
        modifier = Modifier.width(120.dp),
        textAlign = TextAlign.Start,
        color = md_theme_light_primary
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
                style = MaterialTheme.typography.body2
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
                style = MaterialTheme.typography.body2,
                color = md_theme_light_secondary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun RoomCardSimple(
    room: Room,
    navigateToArticle: (String) -> Unit
){
    Row(
        modifier = Modifier
            .clickable(onClick = { navigateToArticle(room.id) })
            .border(
                width = 2.dp,
                color = md_theme_light_primary,
                shape = RoundedCornerShape(15)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            ReadTime(room)
        }
        Column(modifier = Modifier.weight(1f)) {
            RoomRank(room)
            RoomTitle(room)
            RoomPersonnel(room)
        }

    }
}

@Preview("Simple Room Card")
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
            RoomCardSimple(room1, {})
        }
    }
}
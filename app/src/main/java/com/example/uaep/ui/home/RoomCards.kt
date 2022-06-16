package com.example.uaep.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
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
import com.example.uaep.model.Room
import com.example.uaep.ui.theme.UaepTheme
import java.util.*

@Composable
fun RoomTitle(room: Room){
    Text(room.title,
        style = MaterialTheme.typography.titleLarge)
}

@Composable
fun RoomGender(room: Room,modifier: Modifier = Modifier){
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = room.gender,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun ReadTime(room: Room) {
    Text(
        text = stringResource(
            id = R.string.home_room_hour_min,
            formatArgs = arrayOf(
                room.date.hours,
                room.date.minutes
            )
        ),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.width(120.dp),
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun RoomPersonnel(
    room: Room,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            if(room.level_limit!=null) {
                Text(
                    text = room.level_limit,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

@Composable
fun RoomNumber(
    room: Room,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            if(room.level_limit!=null) {
                Text(
                    text = room.number,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
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
                    id = R.string.home_room_date,
                    formatArgs = arrayOf(
                        room.date.month,
                        room.date.date
                    )
                ),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
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
            .clickable(
                onClick = {
                    navigateToArticle(room.id)
                }
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(15)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            ReadTime(room)
        }
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.Bottom) {
                RoomRank(room)
                Spacer(Modifier.width(10.dp))
                RoomPersonnel(room)
            }
            RoomTitle(room)
            Row(verticalAlignment = Alignment.Bottom) {
                RoomNumber(room)
                Spacer(Modifier.width(10.dp))
                RoomGender(room)
            }

        }

    }
}

@Preview("Simple Room Card")
@Composable
fun SimpleRoomPreview() {
    val room1 = Room(
        id = "1234",
        title = "수원월드컵경기장",
        date = Date(2016,5,4,12,14),
        number = "6vs6",
        gender = "성별 무관",
        host="sonny",
        status = null,
        teamA_status = null,
        teamB_status = null,
        apply_flag = null,
        level_limit = "비기너3 이하"
    )

    UaepTheme {
        Surface {
            RoomCardSimple(room1, {})
        }
    }
}
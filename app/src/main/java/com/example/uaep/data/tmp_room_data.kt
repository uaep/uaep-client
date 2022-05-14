package com.example.uaep.data

import com.example.uaep.model.Gender
import com.example.uaep.model.Rank
import com.example.uaep.model.Room
import com.example.ueap.model.RoomsFeed

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
val room2 = Room(
    id = "ac552dcc1743",
    title = "let's play football",
    rank = Rank.PRO,
    gender = Gender.WOMEN,
    date = "July 16",
    hour = 20,
    minute = 30,
    number = 5
)
val room3 = Room(
    id = "ac552dcc1747",
    title = "let's play football",
    rank = Rank.AMATEUR,
    gender = Gender.WOMEN,
    date = "July 20",
    hour = 21,
    minute = 0,
    number = 5
)
val rooms: RoomsFeed =
    RoomsFeed(
        data = listOf(room1, room2, room3)
    )
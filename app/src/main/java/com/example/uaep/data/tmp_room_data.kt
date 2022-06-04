package com.example.uaep.data

import com.example.uaep.model.Gender
import com.example.uaep.model.Rank
import com.example.uaep.model.Room
import com.example.ueap.model.RoomsFeed
import java.util.*

val room1 = Room(
    id = "ac552dcc1741",
    title = "let's play soccer",
    rank = Rank.BIGINNER,
    gender = "men",
    date = Date(2016,5,4,12,14),
    number = "6vs6"
)
val room2 = Room(
    id = "ac552dcc1743",
    title = "let's play football",
    rank = Rank.PRO,
    gender = "women",
    date = Date(2016,5,16,15,0),
    number = "5vs5"
)
val room3 = Room(
    id = "ac552dcc1747",
    title = "let's play football",
    rank = Rank.AMATEUR,
    gender = "male",
    date = Date(2016,5,20,20,15),
    number = "5vs5"
)
val rooms: RoomsFeed =
    RoomsFeed(
        data = listOf(room1, room2, room3)
    )
package com.example.uaep.data

import com.example.uaep.model.Gender
import com.example.uaep.model.Rank
import com.example.uaep.model.Room
import com.example.ueap.model.RoomsFeed
import java.util.*

val room1 = Room(
    id = 1001,
    title = "let's play soccer",
    gender = "men",
    date = Date(2016,5,4,12,14),
    number = "6vs6",
    host="sonny"
)
val room2 = Room(
    id = 1002,
    title = "let's play football",
    gender = "women",
    date = Date(2016,5,16,15,0),
    number = "5vs5",
    host="sonny"
)
val room3 = Room(
    id = 1003,
    title = "let's play football",
    gender = "male",
    date = Date(2016,5,20,20,15),
    number = "5vs5",
    host="sonny"
)
val rooms: RoomsFeed =
    RoomsFeed(
        data = listOf(room1, room2, room3)
    )
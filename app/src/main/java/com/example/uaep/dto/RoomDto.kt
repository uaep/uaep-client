package com.example.uaep.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class RoomDto(
    val id: Int,
    val date: Date,
    @SerializedName("place")
    val title: String,
    @SerializedName("number_of_users")
    val number: String,
    val gender: String,
    val host: String,
    val teamA: Team?,
    val teamB: Team?,
)

data class Team(
    @SerializedName("FW1")
    val fw1: Player?,
    @SerializedName("FW2")
    val fw2: Player?,
    @SerializedName("MF")
    val mf: Player?,
    @SerializedName("DF1")
    val df1: Player?,
    @SerializedName("DF2")
    val df2: Player?,
    @SerializedName("GK")
    val gk: Player?,
    @SerializedName("CAPTAIN")
    val captain: Player?
)

data class Player(
    val email: String,
    val name: String,
    var gender: String,
    val address: String,
    val position: String,
    @SerializedName(value = "level_point")
    val levelPoint: Int
)
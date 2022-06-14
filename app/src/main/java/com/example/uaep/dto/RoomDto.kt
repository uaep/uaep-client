package com.example.uaep.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class RoomDto(
    @SerializedName("uuid")
    val id: String,
    val date: Date,
    @SerializedName("place")
    val title: String,
    @SerializedName("number_of_users")
    val number: String,
    val gender: String,
    val host: String,
    val teamA: Team?,
    val teamB: Team?,
    val status : String? = null,
    val level_limit: String?,
    val level_distribution: Distribution?
)

data class Team(
    @SerializedName("FW")
    val fw: Player?,
    @SerializedName("MF1")
    val mf1: Player?,
    @SerializedName("MF2")
    val mf2: Player?,
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
    val province: String,
    val town: String,
    val position: String,
    val level: String
)

data class Distribution(
    @SerializedName("스타터")
    val starter: Float,
    @SerializedName("비기너")
    val beginner: Float,
    @SerializedName("아마추어")
    var amateur: Float,
    @SerializedName("세미프로")
    val semiPro: Float,
    @SerializedName("프로")
    val pro: Float
)
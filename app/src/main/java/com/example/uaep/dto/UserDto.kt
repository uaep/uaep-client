package com.example.uaep.dto

import com.example.uaep.model.Room
import com.google.gson.annotations.SerializedName

data class UserDto(
    val email: String,
    val name: String,
    var gender: String,
    val address: String,
    val position: String,
    @SerializedName(value = "level_point")
    val levelPoint: Int,
    @SerializedName(value = "position_change_point")
    val positionChangePoint: Int,
    val games: List<Room>?
)
package com.example.uaep.dto

import com.example.uaep.model.Room
import com.google.gson.annotations.SerializedName

data class UserDto(
    val email: String,
    val name: String,
    var gender: String,
    val province: String,
    val town: String,
    val position: String,
    val level: String,
    @SerializedName(value = "position_change_point")
    val positionChangePoint: Int,
    val games: List<Room>?
)
package com.example.uaep.dto

import com.google.gson.annotations.SerializedName

data class GameCreateDto(
    val year: Int,
    val month: Int,
    var day: Int,
    val hour: Int,
    val minute: Int,
    val place: String,
    @SerializedName(value = "number_of_users")
    val numberOfUsers: String,
    val gender: String,
    @SerializedName(value = "level_limit")
    val limitaion: String,
)
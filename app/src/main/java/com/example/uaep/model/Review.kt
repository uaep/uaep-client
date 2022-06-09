package com.example.uaep.model

import com.example.uaep.dto.Player
import com.google.gson.annotations.SerializedName
import java.util.*

data class Review(
    val pos: String,
    val rate: Float,
    val teamB: Boolean
)
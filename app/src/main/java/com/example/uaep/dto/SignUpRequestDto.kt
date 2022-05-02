package com.example.uaep.dto

import com.google.gson.annotations.SerializedName

data class SignUpRequestDto(
    val name: String,
    val password: String,
    @SerializedName("password_check")
    val matchingPassword: String,
    val gender: String
)
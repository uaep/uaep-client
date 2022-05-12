package com.example.uaep.dto

data class ErrorResponse(
    val statusCode: Int,
    val message: String,
    val error: String
)
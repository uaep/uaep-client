package com.example.uaep.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

class AuthCodeViewModel {

    private val mAuthCode = mutableStateOf("")

    val authCode: State<String> = mAuthCode

    fun updateAuthCode(code: String) {
       mAuthCode.value = code
    }
}
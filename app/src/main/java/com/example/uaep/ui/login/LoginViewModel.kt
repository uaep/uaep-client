package com.example.uaep.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// ViewModel basically exposes model data to the view.
class LoginViewModel : ViewModel() {

    private val mEmail = mutableStateOf("")
    private val mPassword = mutableStateOf("")

    // Getter와 같은 역할
    val email: State<String> = mEmail
    val password: State<String> = mPassword

    fun updateEmail(email: String) {
        mEmail.value = email
    }

    fun updatePassword(password: String) {
        mPassword.value = password
    }
}
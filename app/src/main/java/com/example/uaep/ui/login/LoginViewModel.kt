package com.example.uaep.ui.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// ViewModel basically exposes model data to the view.
class LoginViewModel : ViewModel() {

    private val _email = mutableStateOf("")
    private val _password = mutableStateOf("")

    // Getter와 같은 역할
    val email: State<String> = _email
    val password: State<String> = _password

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }
}
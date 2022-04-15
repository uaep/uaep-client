package com.example.uaep.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class SignUpViewModel : ViewModel() {

    private val _name = mutableStateOf("")
    private val _email = mutableStateOf("")
    private val _password = mutableStateOf("")
    private val _confirmedPassword = mutableStateOf("")
    private val _gender = mutableStateOf("")
    private val _enabled = mutableStateOf(false)

    val name: State<String> = _name
    val email: State<String> = _email
    val password: State<String> = _password
    val confirmedPassword: State<String> = _confirmedPassword
    val gender: State<String> = _gender
    val enabled: State<Boolean> = _enabled
    val icon:ImageVector
        @Composable get() = if (_enabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateName(name: String) {
        _name.value = name
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateConfirmedPassword(confirmedPassword: String) {
        _confirmedPassword.value = confirmedPassword
    }

    fun updateGender(gender: String) {
        _gender.value = gender
    }

    fun onEnabled(enabled: Boolean) {
        _enabled.value = enabled
    }

    fun isValidEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    fun isSamePassword(password: String, confirmedPassword: String): Boolean {
        return password == confirmedPassword
    }

    fun isMinLength(password: String, confirmedPassword: String): Boolean {
        return password.length >= 8 && confirmedPassword.length >= 8
    }

    private val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}
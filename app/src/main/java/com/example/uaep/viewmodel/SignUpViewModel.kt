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

    private val mName = mutableStateOf("")
    private val mEmail = mutableStateOf("")
    private val mPassword = mutableStateOf("")
    private val mConfirmedPassword = mutableStateOf("")
    private val mGender = mutableStateOf("")
    private val mEnabled = mutableStateOf(false)

    val name: State<String> = mName
    val email: State<String> = mEmail
    val password: State<String> = mPassword
    val confirmedPassword: State<String> = mConfirmedPassword
    val gender: State<String> = mGender
    val enabled: State<Boolean> = mEnabled
    val icon:ImageVector
        @Composable get() = if (mEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateName(name: String) {
        mName.value = name
    }

    fun updateEmail(email: String) {
        mEmail.value = email
    }

    fun updatePassword(password: String) {
        mPassword.value = password
    }

    fun updateConfirmedPassword(confirmedPassword: String) {
        mConfirmedPassword.value = confirmedPassword
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }

    fun onEnabled(enabled: Boolean) {
        mEnabled.value = enabled
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
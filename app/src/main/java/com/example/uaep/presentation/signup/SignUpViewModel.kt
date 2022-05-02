package com.example.uaep.presentation.signup

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
    private val mMatchingPassword = mutableStateOf("")
    private val mGender = mutableStateOf("")
    private val mEnabled = mutableStateOf(false)

    val name: State<String> = mName
    val email: State<String> = mEmail
    val password: State<String> = mPassword
    val matchingPassword: State<String> = mMatchingPassword
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

    fun updateMatchingPassword(matchingPassword: String) {
        mMatchingPassword.value = matchingPassword
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }


    fun onEnabled(enabled: Boolean) {
        mEnabled.value = enabled
    }

    fun isSamePassword(password: String, matchingPassword: String): Boolean {
        return password == matchingPassword
    }
}
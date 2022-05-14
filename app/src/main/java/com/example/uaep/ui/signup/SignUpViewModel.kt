package com.example.uaep.ui.signup

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.uaep.enums.Position

class SignUpViewModel : ViewModel() {

    private val mName = mutableStateOf("")
    private val mEmail = mutableStateOf("")
    private val mPassword = mutableStateOf("")
    private val mAddress = mutableStateOf("")
    private val mMatchingPassword = mutableStateOf("")
    private val mPosition = mutableStateOf("")
    private val mGender = mutableStateOf("")
    private val mGenderEnabled = mutableStateOf(false)
    private val mPosEnabled = mutableStateOf(false)

    val name: State<String> = mName
    val email: State<String> = mEmail
    val password: State<String> = mPassword
    val address: State<String> = mAddress
    val position: State<String> = mPosition
    val matchingPassword: State<String> = mMatchingPassword
    val gender: State<String> = mGender
    val genderEnabled: State<Boolean> = mGenderEnabled
    val posEnabled: State<Boolean> = mPosEnabled
    val icon1:ImageVector
        @Composable get() = if (mGenderEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon2:ImageVector
        @Composable get() = if (mPosEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateName(name: String) {
        mName.value = name
    }

    fun updateEmail(email: String) {
        mEmail.value = email
    }

    fun updateAddress(address: String) {
        mAddress.value = address;
    }

    fun updatePosition(position: Position) {
        mPosition.value = position.value;
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

    fun onGenderEnabled(enabled: Boolean) {
        mGenderEnabled.value = enabled
    }

    fun onPosEnabled(enabled: Boolean) {
        mPosEnabled.value = enabled
    }

    fun isSamePassword(password: String, matchingPassword: String): Boolean {
        return password == matchingPassword
    }
}
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
    private val mProvince = mutableStateOf("")
    private val mTown = mutableStateOf("")
    private val mMatchingPassword = mutableStateOf("")
    private val mPosition = mutableStateOf("")
    private val mGender = mutableStateOf("")
    private val mLevel = mutableStateOf("")
    private val mGenderEnabled = mutableStateOf(false)
    private val mPosEnabled = mutableStateOf(false)
    private val mProvinceEnabled = mutableStateOf(false)
    private val mLevelEnabled = mutableStateOf(false)

    val name: State<String> = mName
    val email: State<String> = mEmail
    val password: State<String> = mPassword
    val province: State<String> = mProvince
    val town: State<String> = mTown
    val position: State<String> = mPosition
    val matchingPassword: State<String> = mMatchingPassword
    val gender: State<String> = mGender
    val level: State<String> = mLevel
    val genderEnabled: State<Boolean> = mGenderEnabled
    val posEnabled: State<Boolean> = mPosEnabled
    val provinceEnabled: State<Boolean> = mProvinceEnabled
    val levelEnabled: State<Boolean> = mLevelEnabled
    val icon1:ImageVector
        @Composable get() = if (mGenderEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon2:ImageVector
        @Composable get() = if (mPosEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon3:ImageVector
        @Composable get() = if (mProvinceEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon4:ImageVector
        @Composable get() = if (mLevelEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateName(name: String) {
        mName.value = name
    }

    fun updateEmail(email: String) {
        mEmail.value = email
    }

    fun updateProvince(province: String) {
        mProvince.value = province;
    }

    fun updateTown(town: String) {
        mTown.value = town;
    }

    fun updateLevel(level: String) {
        mLevel.value = level;
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

    fun onProvinceEnabled(enabled: Boolean) {
        mProvinceEnabled.value = enabled
    }

    fun onLevelEnabled(enabled: Boolean) {
        mLevelEnabled.value = enabled
    }

    fun isSamePassword(password: String, matchingPassword: String): Boolean {
        return password == matchingPassword
    }
}
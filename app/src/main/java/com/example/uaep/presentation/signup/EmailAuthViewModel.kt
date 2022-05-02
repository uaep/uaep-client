package com.example.uaep.presentation.signup

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector

class EmailAuthViewModel {

    private val mId = mutableStateOf("")
    private val mDomain = mutableStateOf("")
    private val mEmail = mutableStateOf("")
    private val mEnabled = mutableStateOf(false)

    val id: State<String> = mId
    val domain: State<String> = mDomain
    val email: State<String> = mEmail
    val enabled: State<Boolean> = mEnabled
    val icon: ImageVector
        @Composable get() = if (mEnabled.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updateId(id: String) {
        mId.value = id
    }

    fun updateDomain(domain: String) {
        mDomain.value = domain
    }

    fun updateEmail(email: String) {
        mEmail.value = email
    }

    fun onEnabled(enabled: Boolean) {
        mEnabled.value = enabled
    }
}
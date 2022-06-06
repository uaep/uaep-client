package com.example.uaep.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.uaep.dto.LoginRequestDto
import com.example.uaep.dto.LoginResponseDto
import com.example.uaep.network.AuthService
import com.example.uaep.network.SessionManager
import com.example.uaep.ui.navigate.Screen
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ViewModel basically exposes model data to the view.
class LoginViewModel : ViewModel() {

    private val mEmail = mutableStateOf("")
    private val mPassword = mutableStateOf("")
    private lateinit var sessionManager: SessionManager

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
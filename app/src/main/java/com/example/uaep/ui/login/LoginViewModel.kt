package com.example.uaep.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.uaep.dto.LoginRequestDto
import com.example.uaep.dto.LoginResponseDto
import com.example.uaep.network.SessionManager
import com.example.uaep.network.UserApiService
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

    fun loginCallback(
        userApiService: UserApiService,
        loginRequestDto: LoginRequestDto,
        navController: NavController,
        context: Context
    ){
        userApiService.login(
            longinRequestDto = loginRequestDto
        ).enqueue(object :
            Callback<LoginResponseDto> {
            override fun onResponse(
                call: Call<LoginResponseDto>,
                response: Response<LoginResponseDto>
            ) {
                if(response.isSuccessful) {
                    sessionManager = SessionManager(context)

                    val headers = response.headers()
                    Log.i("raw", response.raw().toString())
                    Log.i("first_header", headers.get("Cookie") ?: "unknown")
                    val loginResponse = response.body()
                    val accessToken = headers.get("Cookie")
                    val refreshToken = headers.get("refreshToken")
                    if (accessToken != null) {
                        Log.i("header", headers.toString())
                        sessionManager.saveAuthToken(accessToken)
                        Log.i("cookie", sessionManager.fetchAuthToken() ?: "unknown")

                    }


                    navController.navigate(Screen.Home.route)
                } else {
                    // TODO: 배열로 넘오는 json 에러 값 처리하기
                    val jObjError = JSONObject(
                        response.errorBody()!!.string()
                    )
                    Log.d("Error Test", jObjError.toString())
                }
            }

            override fun onFailure(call: Call<LoginResponseDto>, t: Throwable) {
                Log.e("test", "실패$t")
            }
        })
    }
}
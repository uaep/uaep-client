package com.example.uaep.ui.match

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.ErrorResponse
import com.example.uaep.dto.GameCreateDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.network.AuthService
import com.example.uaep.network.CookieChanger
import com.example.uaep.network.GameApiService
import com.example.uaep.network.ReAuthService
import com.example.uaep.ui.navigate.Screen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchCreationScreenViewModel(
    private val gameApiService: GameApiService = GameApiService.getInstance()
) : ViewModel() {
    private val mPlace = mutableStateOf("")
    private val mNumPlayer = mutableStateOf("")
    private val mNumPlayerSelected = mutableStateOf(false)
    private val mGender = mutableStateOf("")
    private val mGenderSelected = mutableStateOf(false)

    val place: State<String> = mPlace
    val numPlayer: State<String> = mNumPlayer
    val numPlayerSelected: State<Boolean> = mNumPlayerSelected
    val gender: State<String> = mGender
    val genderSelected: State<Boolean> = mGenderSelected
    val icon1: ImageVector
        @Composable get() = if (mNumPlayerSelected.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val icon2: ImageVector
        @Composable get() = if (mGenderSelected.value) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown

    fun updatePlace(place: String) {
        mPlace.value = place
    }

    fun updateNumPlayer(numPlayer: String) {
        mNumPlayer.value = numPlayer
    }

    fun onNumPlayerSelected() {
        mNumPlayerSelected.value = !mNumPlayerSelected.value
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }

    fun onGenderSelected() {
        mGenderSelected.value = !mGenderSelected.value
    }

    fun postGameCreation(gameCreateDto: GameCreateDto, navController: NavController) {
        gameApiService.create(gameCreateDto).enqueue(object:
            Callback<UrlResponseDto> {
            override fun onResponse(
                call: Call<UrlResponseDto>,
                response: Response<UrlResponseDto>
            ) {
                if (response.isSuccessful) {
                    navController.navigate(Screen.Home.route)
                } else {
                    Log.e("creation", response.errorBody()!!.string())
                    Log.i("creation_head", response.headers().toString())
                    val errorResponse: ErrorResponse? =
                        Gson().fromJson(
                            response.errorBody()!!.charStream(),
                            object : TypeToken<ErrorResponse>() {}.type
                        )
                    if (errorResponse?.message == "Expired access token") {
                        ReAuthService.getInstance().reauth().enqueue(object :
                            Callback<DummyResponse> {
                            override fun onResponse(
                                call: Call<DummyResponse>,
                                response: Response<DummyResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val tokens = CookieChanger<DummyResponse>().change(response)
                                    AuthService.getCookieJar().saveToken(tokens)
                                }
                            }
                            override fun onFailure(
                                call: Call<DummyResponse>,
                                t: Throwable
                            ) {
                                Log.i("test", "실패$t")
                            }
                        })
                    }                }
            }
            override fun onFailure(call: Call<UrlResponseDto>, t: Throwable) {
                Log.i("test", "실패$t")
            }
        })
    }
}
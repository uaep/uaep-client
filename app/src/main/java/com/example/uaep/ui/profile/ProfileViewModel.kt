package com.example.uaep.ui.profile

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.uaep.dto.UserUpdateDto
import com.example.uaep.network.UserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(
    private val userApiService: UserApiService = UserApiService.getInstance(),
): ViewModel() {

    private val mName = mutableStateOf("")
    private val mPosition = mutableStateOf("")
    private val mAddress = mutableStateOf("")
    private val mGender = mutableStateOf("")

    val name: State<String> = mName
    val position: State<String> = mPosition
    val address: State<String> = mAddress
    val gender: State<String> = mGender

    fun updateName(name: String) {
        mName.value = name
    }

    fun updatePosition(position: String) {
        mPosition.value = position
    }

    fun updateAddress(address: String) {
        mAddress.value = address
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }

    fun updateProfile(userUpdateDto: UserUpdateDto) {
        userApiService.updateUserInfo(userUpdateDto).enqueue(object:
            Callback<UserUpdateDto> {
            override fun onResponse(
                call: Call<UserUpdateDto>,
                response: Response<UserUpdateDto>
            ) {
                if (response.isSuccessful) {
                    mName.value = response.body()!!.name
                    mPosition.value = response.body()!!.position
                    mAddress.value = response.body()!!.address
                } else {
                    Log.d("debug2", (response.errorBody()?.charStream()).toString())
//                    Gson().fromJson(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                }
            }
            override fun onFailure(call: Call<UserUpdateDto>, t: Throwable) {
                Log.i("test", "실패$t")
            }
        })
    }
}
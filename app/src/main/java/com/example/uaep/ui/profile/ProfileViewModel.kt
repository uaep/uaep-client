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
    private val mProvince = mutableStateOf("")
    private val mTown = mutableStateOf("")
    private val mGender = mutableStateOf("")
    private val mLevel= mutableStateOf("")
    private val mPositionChangePoint = mutableStateOf(0)

    val name: State<String> = mName
    val position: State<String> = mPosition
    val province: State<String> = mProvince
    val town: State<String> = mTown
    val gender: State<String> = mGender
    val level: State<String> = mLevel
    val positionChangePoint: State<Int> = mPositionChangePoint

    fun updateName(name: String) {
        mName.value = name
    }

    fun updatePosition(position: String) {
        mPosition.value = position
    }

    fun updateProvince(province: String) {
        mProvince.value = province
    }

    fun updateTown(town: String) {
        mTown.value = town
    }

    fun updateGender(gender: String) {
        mGender.value = gender
    }

    fun updateLevel(level: String) {
        mLevel.value = level
    }

    fun updatePositionChangePoint(positionChangePoint: Int) {
        mPositionChangePoint.value = positionChangePoint
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
                    mProvince.value = response.body()!!.province
                    mTown.value = response.body()!!.town
                } else {
                    Log.d("debug2", (response.errorBody()?.charStream()).toString())
                }
            }
            override fun onFailure(call: Call<UserUpdateDto>, t: Throwable) {
                Log.i("test", "실패$t")
            }
        })
    }

}
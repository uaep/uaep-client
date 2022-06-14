package com.example.uaep.ui.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
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
    val context: Context
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
                    Log.i("profile", response.raw().toString())
                    mName.value = response.body()!!.name
                    mPosition.value = response.body()!!.position
                    mProvince.value = response.body()!!.province
                    mTown.value = response.body()!!.town
                } else {
                    Log.d("error",response.errorBody()?.string().toString())
//                    val errorResponse: ErrorResponse? =
//                        Gson().fromJson(
//                            response.errorBody()!!.charStream(),
//                            object : TypeToken<ErrorResponse>() {}.type
//                        )

//                    Log.d("error", errorResponse?.message.toString())

                    if (response.errorBody()?.string().toString().contains("points")) {
                        mToast(context, "포지션 변경 포인트가 부족합니다.")
                    } else if (response.errorBody()?.string().toString().contains("Precondition")) {
                        mToast(context, "포지션 변경을 위해선 현재 참여중인 방이 없어야합니다.")
                    }
                }
            }
            override fun onFailure(call: Call<UserUpdateDto>, t: Throwable) {
                Log.i("test", "실패$t")
            }
        })
    }

    private fun mToast(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}
package com.example.uaep.ui.rank

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.uaep.dto.UserDto
import com.example.uaep.dto.UserUpdateDto
import com.example.uaep.network.UserApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingViewModel(
    private val userApiService: UserApiService = UserApiService.getInstance(),
    val context: Context
    ): ViewModel() {

    val mList = mutableStateOf<List<UserDto>>(emptyList())


    init {
        updateRanking()
    }


    fun updateRanking() {
        userApiService.getRanking().enqueue(object:
            Callback<List<UserDto>> {
            override fun onResponse(
                call: Call<List<UserDto>>,
                response: Response<List<UserDto>>
            ) {
                if (response.isSuccessful) {
                    Log.i("ranking_raw", response.raw().toString())
                    Log.i("ranking_body", response.body().toString())
                    mList.value = response.body()!!
                } else {
                    Log.d("error",response.errorBody()?.string().toString())
//                    val errorResponse: ErrorResponse? =
//                        Gson().fromJson(
//                            response.errorBody()!!.charStream(),
//                            object : TypeToken<ErrorResponse>() {}.type
//                        )

//                    Log.d("error", errorResponse?.message.toString())

//                    if (response.errorBody()?.string().toString().contains("points")) {
//                        mToast(context, "포지션 변경 포인트가 부족합니다.")
//                    } else if (response.errorBody()?.string().toString().contains("Precondition")) {
//                        mToast(context, "포지션 변경을 위해선 현재 참여중인 방이 없어야합니다.")
//                    }
                }
            }
            override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                Log.i("test", "실패$t")
            }
        })
    }

    private fun mToast(context: Context, msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}
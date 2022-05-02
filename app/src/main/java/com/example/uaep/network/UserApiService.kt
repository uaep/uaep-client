package com.example.uaep.network

import com.example.uaep.dto.AuthCodeRequestDto
import com.example.uaep.dto.EmailRequestDto
import com.example.uaep.dto.SignUpRequestDto
import com.example.uaep.dto.SignUpResponseDto
import com.example.uaep.dto.UrlResponseDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("email_validity_checks")
    fun checkEmailValidity(@Body emailRequestDto: EmailRequestDto) : Call<UrlResponseDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("email_verify")
    fun verifyEmail(@Body authCodeRequestDto: AuthCodeRequestDto) : Call<UrlResponseDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST(".")
    fun signup(@Body signUpRequestDto: SignUpRequestDto) : Call<UrlResponseDto>

    // TODO: Login

    // 싱글톤 객체로서, 인스턴스 생성 없이 사용할 수 있다.
    companion object{
        private var userApiService:UserApiService? = null
        fun getInstance() : UserApiService {
            if (userApiService == null) {
                userApiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/users/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UserApiService::class.java)
            }
            return userApiService!!
        }
    }
}
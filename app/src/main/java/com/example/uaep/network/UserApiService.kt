package com.example.uaep.network

import com.example.uaep.dto.*
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query


interface UserApiService {
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("email_validity_checks")
    fun checkEmailValidity(@Body emailRequestDto: EmailRequestDto) : Call<UrlResponseDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("email_verify")
    fun verifyEmail(
        @Body authCodeRequestDto: AuthCodeRequestDto,
        @Query("signupVerifyToken") token: String
    ) : Call<UrlResponseDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @POST(".")
    fun signup(
        @Body signUpRequestDto: SignUpRequestDto,
        @Query("signupVerifyToken") token: String
    ) : Call<UrlResponseDto>

    // TODO: Login
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("auth/login")
    fun login(
        @Body longinRequestDto: LoginRequestDto
    ) : Call<LoginResponseDto>

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
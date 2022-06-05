package com.example.uaep.network

import com.example.uaep.dto.AuthCodeRequestDto
import com.example.uaep.dto.EmailRequestDto
import com.example.uaep.dto.SignUpRequestDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.dto.UserDto
import com.example.uaep.dto.UserUpdateDto
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query


interface UserApiService {

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @POST("email_validity_checks")
    fun checkEmailValidity(@Body emailRequestDto: EmailRequestDto) : Call<UrlResponseDto>

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @POST("email_verify")
    fun verifyEmail(
        @Body authCodeRequestDto: AuthCodeRequestDto,
        @Query("signupVerifyToken") token: String
    ) : Call<UrlResponseDto>

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @POST(".")
    fun signup(
        @Body signUpRequestDto: SignUpRequestDto,
        @Query("signupVerifyToken") token: String
    ) : Call<UrlResponseDto>

    @GET(".")
    fun getUser() : Call<UserDto>

    @Headers(
        "accept: application/json",
        "content-type: application/json"
    )
    @PATCH(".")
    fun updateUserInfo(
        @Body userUpdateDto: UserUpdateDto
    ) : Call<UserUpdateDto>

    // 싱글톤 객체로서, 인스턴스 생성 없이 사용할 수 있다.
    companion object{
        private var userApiService:UserApiService? = null
        private val myCookieJar = AuthService.getCookieJar()

        fun getInstance() : UserApiService {
            if (userApiService == null) {
                val httpClient = OkHttpClient.Builder()
                    .cookieJar(myCookieJar).addInterceptor(AddAccessInterceptor())
                    .build()

                userApiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/users/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(UserApiService::class.java)
            }
            return userApiService!!
        }
    }
}
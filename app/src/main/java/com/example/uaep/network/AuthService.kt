package com.example.uaep.network

import com.example.uaep.dto.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface AuthService {

    //Login
    @Headers("accept: application/json",
        "content-type: application/json")
    @POST("users/auth/login")
    fun login(
        @Body longinRequestDto: LoginRequestDto
    ) : Call<LoginResponseDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("games")
    fun rooms(
    ) : Call<RoomsResponseDto>

    // 싱글톤 객체로서, 인스턴스 생성 없이 사용할 수 있다.
    companion object{
        private var authService:AuthService? = null
        private val myCookieJar = MyCookieJar()
        //private val myCookieJar = JavaNetCookieJar(CookieManager())
        fun getInstance() : AuthService {
            if (authService == null) {

                val httpClient = OkHttpClient.Builder()
                    .cookieJar(myCookieJar).addInterceptor(AddAccessInterceptor())
                    .build()
                authService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(AuthService::class.java)
            }
            return authService!!
        }

        fun getCookieJar() : MyCookieJar{
            return myCookieJar
        }
    }
}
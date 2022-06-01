package com.example.uaep.network

import com.example.uaep.dto.DummyResponse
import com.example.uaep.dto.RoomsResponseDto
import com.example.uaep.dto.UrlResponseDto
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

interface ReAuthService {

    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("refresh")
    fun reauth(
    ) : Call<DummyResponse>

    // 싱글톤 객체로서, 인스턴스 생성 없이 사용할 수 있다.
    companion object{
        private var authService:ReAuthService? = null
        private val myCookieJar = AuthService.getCookieJar()
        fun getInstance() : ReAuthService {
            if (authService == null) {

                val httpClient = OkHttpClient.Builder()
                    .cookieJar(myCookieJar).addInterceptor(AddCookiesInterceptor())
                    .build()
                authService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/users/auth/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ReAuthService::class.java)
            }
            return authService!!
        }
    }

}
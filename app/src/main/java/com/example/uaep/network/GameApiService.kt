package com.example.uaep.network

import com.example.uaep.dto.GameCreateDto
import com.example.uaep.dto.UrlResponseDto
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface GameApiService {
    @Headers(
        "content-type: application/json"
    )
    @POST(".")
    fun create(@Body gameCreateDto: GameCreateDto) : Call<UrlResponseDto>

    companion object{
        private var gameApiService:GameApiService? = null
        private val myCookieJar = AuthService.getCookieJar()

        fun getInstance() : GameApiService {
            if (gameApiService == null) {
                val httpClient = OkHttpClient.Builder()
                    .cookieJar(myCookieJar).addInterceptor(AddAccessInterceptor())
                    .build()

                gameApiService = Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:3000/games/")
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(GameApiService::class.java)
            }
            return gameApiService!!
        }
    }
}
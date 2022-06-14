package com.example.uaep.network

import com.example.uaep.dto.GameCreateDto
import com.example.uaep.dto.UrlResponseDto
import com.example.uaep.model.Room
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface GameApiService {
    @Headers(
        "content-type: application/json"
    )
    @POST(".")
    fun create(@Body gameCreateDto: GameCreateDto) : Call<UrlResponseDto>

    @GET(".")
    fun getAllGamesByRegion(@Query("region") region: String) : Call<List<Room>>

    @GET(".")
    fun getAllGamesByGender(@Query("gender") region: String) : Call<List<Room>>

    @GET(".")
    fun getAllGamesByNumPlayer(@Query("number_of_users") region: String) : Call<List<Room>>

    @GET(".")
    fun getAllGamesByStatus(@Query("status") status: String) : Call<List<Room>>

    @GET(".")
    fun getAllGamesByLevel(@Query("level_limit") region: String) : Call<List<Room>>


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
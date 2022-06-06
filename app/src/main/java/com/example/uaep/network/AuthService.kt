package com.example.uaep.network

import com.example.uaep.dto.*
import com.example.uaep.model.Room
import com.google.gson.GsonBuilder
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
    ) : Call<List<Room>>

    @Headers("accept: application/json",
        "content-type: application/json")
    @GET("games/{id}")
    fun select(
        @Path("id") id: Int
    ) : Call<RoomDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @PATCH("games/{id}/{teamType}")
    fun createFormation(
        @Path("id") id: Int,
        @Path("teamType") type: String,
        @Body formationRequestDto: FormationRequestDto
    ) : Call<RoomDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @PATCH("games/{id}/{teamType}/{position}")
    fun setPosition(
        @Path("id") id: Int,
        @Path("teamType") type: String,
        @Path("position") position: String
    ) : Call<RoomDto>

    @Headers("accept: application/json",
        "content-type: application/json")
    @DELETE("games/{id}/{teamType}")
    fun deleteTeam(
        @Path("id") id: Int,
        @Path("teamType") type: String
    ) : Call<DummyResponse>

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
                    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                    .build().create(AuthService::class.java)
            }
            return authService!!
        }

        fun getCookieJar() : MyCookieJar{
            return myCookieJar
        }
    }
}
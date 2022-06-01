package com.example.uaep.network

import android.content.Context
import android.util.Log
import okhttp3.Cookie
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class CookieChanger<T>() {

    fun change(response : Response<T>) : List<Cookie> {
        val cookieList = response.headers().values("Set-Cookie")
        val newCookieList = ArrayList<String>()
        for(cookies in cookieList){
            for(cookie in cookies.split(";")){
                newCookieList.add(cookie.split("=").last())
            }
        }
        for(c in newCookieList){
            Log.i("cookielist", c.toString())
        }
        val tokens = listOf<Cookie>(
            Cookie.Builder()
            .domain(newCookieList[1])
            .path(newCookieList[2])
            .name("access_token")
            .value(newCookieList[0])
            .httpOnly()
            .build(),
            Cookie.Builder()
                .domain(newCookieList[5])
                .path(newCookieList[6])
                .name("refresh_token")
                .value(newCookieList[4])
                .httpOnly()
                .build()
        )
        return tokens
    }

}
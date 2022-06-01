package com.example.uaep.network

import android.util.Log
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

class MyCookieJar : CookieJar {
    private var accessCookie: Cookie? = null
    private var refreshCookie: Cookie? = null

    override fun saveFromResponse(url: HttpUrl?, cookies: List<Cookie>?) {
        Log.i("save-cookies", cookies.toString())
        if (url != null && cookies != null) {
            if (url.encodedPath().endsWith("login")) {
                for (cookie in cookies) {
                    Log.i("cookies_string", cookie.toString())
                    if(cookie.name() == "access_token") {
                        accessCookie = cookie
                    }
                    if(cookie.name() == "refresh_token"){
                        refreshCookie = cookie
                    }
                }
            }
        }
    }

    override fun loadForRequest(url: HttpUrl?): List<Cookie>? { // url = https://www.mangaupdates.com/series.html?id=14829
//        if(url != null && url.pathSegments().size > 0 && url.encodedPath().endsWith("login")) {
//            return emptyList()
//        }
        Log.i("load-cookies", accessCookie.toString())
        //val cookies: List<Cookie> = if(accessCookie==null||refreshCookie==null) emptyList() else listOf(accessCookie!!, refreshCookie!!)
        val cookies: List<Cookie> = if(accessCookie==null||refreshCookie==null) emptyList() else listOf(accessCookie!!)
        //Log.i("load-cookieList", cookies.)
        return cookies // = ["secure_session=601bbc74; expires=Sun, 07 Oct 2018 20:45:24 GMT; domain=www.mangaupdates.com; path=/; secure; httponly"]
    }

    fun saveToken(cookies: List<Cookie>?) {
        Log.i("save-cookies", cookies.toString())
        if (cookies != null) {
            for (cookie in cookies) {
                Log.i("cookies_string", cookie.toString())
                if(cookie.name() == "access_token") {
                    accessCookie = cookie
                }
            }

        }
    }

    fun loadAccess(): Cookie? {

        return accessCookie
    }

    fun loadAllToken(): List<Cookie>{


        return listOf(accessCookie!!, refreshCookie!!)
    }


}
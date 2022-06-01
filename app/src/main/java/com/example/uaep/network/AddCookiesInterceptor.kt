package com.example.uaep.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AddCookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain){

        val newRequest = request().newBuilder()
            .addHeader("Cookie", AuthService.getCookieJar().loadAllToken().toString())
            .build()
        Log.i("interceptor", AuthService.getCookieJar().loadAccess().toString())
        proceed(newRequest)
//        val builder = chain.request().newBuilder()
//
//        // Preference에서 cookies를 가져오는 작업을 수행
//        val pref = getSharedPreferences(APIPreferences.SHARED_PREFERENCE_NAME_COOKIE, new HashSet<String>())
//
//        for (String cookie : preferences) { builder.addHeader("Cookie", cookie); }
//
//        // Web,Android,iOS 구분을 위해 User-Agent세팅
//        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");



    }
}
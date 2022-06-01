package com.example.uaep.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class AddAccessInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain){
        val newRequest = request().newBuilder()
            .addHeader("Cookie", AuthService.getCookieJar().loadAccess().toString())
            .build()
        Log.i("interceptor", AuthService.getCookieJar().loadAccess().toString())
        proceed(newRequest)
    }

}
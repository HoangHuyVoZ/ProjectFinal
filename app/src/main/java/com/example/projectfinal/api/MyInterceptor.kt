package com.example.projectfinal.api

import android.content.Context
import com.example.projectfinal.utils.accessToken
import okhttp3.Interceptor
import okhttp3.Response

class MyInterceptor : Interceptor {
   val context: Context? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request=chain.request()
            .newBuilder()
            .addHeader("Authorization", "Bearer ${accessToken?:""}")
            .build()
        return chain.proceed(request)
    }

}
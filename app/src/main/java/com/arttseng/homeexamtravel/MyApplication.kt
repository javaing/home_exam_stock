package com.arttseng.homeexamtravel

import android.app.Application
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class MyApplication: Application() {
    companion object {
        private var instance: Application? = null
        fun instance() = instance!!
        private var okHttpClient: OkHttpClient? = null
        fun getOkHttpClient(): OkHttpClient {
            if(okHttpClient==null) {
                //if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.level = HttpLoggingInterceptor.Level.HEADERS
                    okHttpClient = OkHttpClient.Builder()
                        .addInterceptor(interceptor)
                        .addInterceptor(Interceptor { chain ->
                            val request: Request =
                                chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .build()
                            chain.proceed(request)
                        })
                        .retryOnConnectionFailure(true) //.addInterceptor(UserAgentInterc)
                        .connectTimeout(80, TimeUnit.SECONDS)
                        .readTimeout(80, TimeUnit.SECONDS)
                        .build()
//                } else {
//                    okHttpClient = OkHttpClient.Builder()
//                        .retryOnConnectionFailure(true) //.addInterceptor(UserAgentInterc)
//                        .connectTimeout(80, TimeUnit.SECONDS)
//                        .readTimeout(80, TimeUnit.SECONDS)
//                        .build()
//                }
                Log.e("", "art created okhttp")
            }
            return okHttpClient as OkHttpClient
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
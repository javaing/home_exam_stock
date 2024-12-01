package com.arttseng.homeexam.airplane.tools

import android.util.Log
import com.arttseng.homeexam.airplane.MyApplication
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitFactory
{
    object WebAccess {

        val API : ApiClient by lazy {
            val retrofit = Retrofit.Builder()
                .client(MyApplication.getOkHttpClient())
                .baseUrl(Const.BaseURL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return@lazy retrofit.create(ApiClient::class.java)
        }
    }
}
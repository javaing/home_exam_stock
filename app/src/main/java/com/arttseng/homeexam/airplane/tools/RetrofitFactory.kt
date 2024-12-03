package com.arttseng.homeexam.airplane.tools

import com.arttseng.homeexam.airplane.MyApplication
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitFactory
{
    object WebAccess {

        val APICurrency : ApiClientCurrency by lazy {
            val retrofit = Retrofit.Builder()
                .client(MyApplication.getOkHttpClient())
                .baseUrl(Const.BaseUrl_Currency)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return@lazy retrofit.create(ApiClientCurrency::class.java)
        }

        val API_Airpane : ApiClientAirplane by lazy {
            val retrofit = Retrofit.Builder()
                .client(MyApplication.getOkHttpClient())
                .baseUrl(Const.BaseUrl_Airplane)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

            return@lazy retrofit.create(ApiClientAirplane::class.java)
        }
    }
}
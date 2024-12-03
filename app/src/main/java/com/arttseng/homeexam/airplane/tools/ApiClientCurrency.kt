package com.arttseng.homeexam.airplane.tools

import com.arttseng.homeexam.airplane.datamodel.Currencies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClientCurrency {

    ////https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_KOsIq7gijrkRbmzw5MfUhXUhzgaeRg3ssG8LF90Z&currencies=CNY%2CEUR%2CJPY%2CKRW%2CHKD%2CUSD&base_currency=CNY
    //

    @GET(Const.lastestCurrency)
    suspend fun getLastestCurrency(
        @Query("apikey") apikey: String, @Query("currencies") currencies: String, @Query("base_currency") base_currency: String,
        ): Response<Currencies>



}
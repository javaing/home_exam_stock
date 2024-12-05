package com.arttseng.homeexam.stock.tools

import com.arttseng.homeexam.stock.datamodel.BWIBBU_ALLItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AVG_AllItem
import com.arttseng.homeexam.stock.datamodel.Stock_Day_AllItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiClient {

    @GET(Const.BWIBBU_ALL)
    suspend fun getBWIBBU(): Response<List<BWIBBU_ALLItem>>

    @GET(Const.STOCK_DAY_AVG_ALL)
    suspend fun getDAYAVGALL(): Response<List<Stock_Day_AVG_AllItem>>

    @GET(Const.STOCK_DAY_ALL)
    suspend fun getDAYALL(): Response<List<Stock_Day_AllItem>>

}
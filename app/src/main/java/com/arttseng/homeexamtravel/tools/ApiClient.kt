package com.arttseng.homeexamtravel.tools

import com.arttseng.homeexamtravel.datamodel.Attractions
import com.arttseng.homeexamtravel.datamodel.News
import com.arttseng.homeexamtravel.tools.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @Headers("Accept: application/json")
    @GET(Const.GetAttractionsAll)
    suspend fun getAttractionsAll(
        @Path("lang") lang: String, @Query("page") page: Int
        ): Response<Attractions>

    @Headers("Accept: application/json")
    //@Headers("Context-Type:text/html")
    @GET(Const.GetEventNews)
    suspend fun getEventNews(
            @Path("lang") lang: String, @Query("page") page: Int
    ): Response<News>

}
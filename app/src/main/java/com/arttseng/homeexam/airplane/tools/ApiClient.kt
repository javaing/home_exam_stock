package com.arttseng.homeexam.airplane.tools

import com.arttseng.homeexam.airplane.datamodel.Attractions
import com.arttseng.homeexam.airplane.datamodel.News
import com.arttseng.homeexam.airplane.tools.Const
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET(Const.GetAttractionsAll)
    suspend fun getAttractionsAll(
        @Path("lang") lang: String, @Query("page") page: Int
        ): Response<Attractions>

    @GET(Const.GetEventNews)
    suspend fun getEventNews(
            @Path("lang") lang: String, @Query("page") page: Int
    ): Response<News>

}
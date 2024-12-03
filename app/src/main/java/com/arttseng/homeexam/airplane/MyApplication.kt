package com.arttseng.homeexam.airplane

import android.app.Application
import android.util.Log
import com.arttseng.homeexam.airplane.tools.Const
import me.relex.circleindicator.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

class MyApplication: Application() {
    companion object {
        private var instance: Application? = null
        var tdx_token = ""
        var currencyApikey = ""
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
                                    .addHeader("Authorization", "Bearer $tdx_token")
                                    .build()
                            chain.proceed(request)
                        })
                        .retryOnConnectionFailure(true) //.addInterceptor(UserAgentInterc)
                        .connectTimeout(80, TimeUnit.SECONDS)
                        .readTimeout(80, TimeUnit.SECONDS)
                        .build()

                Log.e("", "art created okhttp")
            }
            return okHttpClient as OkHttpClient
        }
    }

    interface ApiService {
        @POST(Const.TDX_Token)
        fun postRequest(@Body requestBody: RequestBody?): Call<ResponseBody>
    }

    fun getTDXToken() {
        val retrofit = Retrofit.Builder()
            .client(getOkHttpClient())
            .baseUrl(Const.TDX_Base)
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val body = mapOf(
            "grant_type" to "client_credentials",
            "client_id" to resources.getString(R.string.tdx_client_id),
            "client_secret" to resources.getString(R.string.tdx_client_secret)
        )
        val requestBody = JSONObject(body).toString().toRequestBody("application/json".toMediaTypeOrNull())

        apiService.postRequest(requestBody).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

                println("other message:" + response.body()?.string())
                response.body().let {
                    val res = JSONObject(it.toString())
                    tdx_token = res.getString("access_token")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // handle the failure
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        tdx_token = resources.getString(R.string.tdx_token)
        currencyApikey = resources.getString(R.string.freecurrency_apikey)
        if(tdx_token.isEmpty()) {
            getTDXToken()
        }
    }
}
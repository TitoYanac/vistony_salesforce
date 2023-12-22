package com.vistony.salesforce.kotlin.Utilities.api

import android.util.Log
import com.vistony.salesforce.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy
import java.util.concurrent.TimeUnit

class RetrofitConfig {
    private var retrofit: Retrofit? = null
    private var retrofit2: Retrofit? = null
    private var client: OkHttpClient? = null
    private var client2: OkHttpClient? = null
    fun getClient(): Retrofit? {
        var baseUrl: String? = null
        baseUrl =
            when (BuildConfig.FLAVOR) {
                "chile"         -> "http://190.12.79.132:8083"
                "espania"       -> "http://190.12.79.132:8083"
                "marruecos"     -> "http://190.12.79.132:8083"
                "paraguay"      -> "http://190.12.79.132:8083"               //QA Peru
                "peru"          -> "http://190.12.79.132:8083"
                "perurofalab"   -> "http://190.12.79.132:8083"
                "bolivia"       -> "http://190.12.79.132:8083"
                "ecuador"       -> "http://190.12.79.132:8083"
                else            -> "http://190.12.79.132:8083"
            }
        Log.e("REOS", "-RetrofitConfig-getClientLog-baseUrl$baseUrl")
        try {
            if (client2 == null) {
                client2 = OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .proxy(Proxy.NO_PROXY)
                    .build()
            }
            if (retrofit2 == null) {
                retrofit2 = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).client(client2)
                    .build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("REOS", "Config-getClient-error:$e")
        }
        return retrofit2
    }


    fun getClientLog(): Retrofit? {
        var baseUrl: String? = null
        baseUrl =
            when (BuildConfig.FLAVOR) {
                "chile"         -> "http://190.12.79.132:8083"
                "espania"       -> "http://190.12.79.132:8083"
                "marruecos"     -> "http://190.12.79.132:8083"
                "paraguay"      -> "http://190.12.79.132:8083"               //QA Peru
                "peru"          -> "http://190.12.79.132:8083"
                "perurofalab"   -> "http://190.12.79.132:8083"
                "bolivia"       -> "http://190.12.79.132:8083"
                "ecuador"       -> "http://190.12.79.132:8083"
                else            -> "http://190.12.79.132:8083"
            }
        Log.e("jesusdebug", "-RetrofitConfig-getClientLog-baseUrl$baseUrl")
        try {
            if (client == null) {
                client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .proxy(Proxy.NO_PROXY)
                    .build()
            }
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create()).client(client)
                    .build()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("REOS", "RetrofitConfig-getClientLog-error:$e")
        }

        Log.e("REOS", "-RetrofitConfig-getClientLog-retrofit${retrofit.toString()}")
        return retrofit
    }
}
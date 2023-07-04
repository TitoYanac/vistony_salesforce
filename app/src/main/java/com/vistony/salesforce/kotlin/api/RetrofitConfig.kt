package com.vistony.salesforce.kotlin.api

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
                "chile", "ecuador", "espania", "marruecos" -> "https://salesforce.vistony.pe"
                "perurofalab", "bolivia", "paraguay" -> "https://app.vistony.pe"
                "peru" ->"http://190.12.79.132:8083"
                else -> "http://salesforce.vistony.com"
            }
        var puerto = ""
        when (BuildConfig.FLAVOR) {
            "chile" ->                 //puerto = ":8054";
                //Produccion----------
                //puerto = ":8054";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = ""
            "bolivia" ->                 //puerto = ":8052";
                puerto = ""
            "ecuador" ->                 //puerto = ":8050";
                puerto = ""
            "peru" ->                 //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = ""
            "perurofalab" ->                 //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = ""
            "paraguay" ->                 //puerto = ":8051";
                //Pruebas QA------------- Nueva Produccion
                puerto = ""
            "espania" -> puerto = ""
            "marruecos" -> puerto = ""
        }
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
                    .baseUrl(baseUrl + puerto)
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
                "chile", "ecuador", "espania", "marruecos" -> "https://salesforce.vistony.pe"
                "perurofalab", "bolivia", "paraguay" ->                 //QA Peru
                    //case "peru":
                    "https://app.vistony.pe"
                "peru" ->"http://190.12.79.132:8083"
                else -> "http://salesforce.vistony.com"
            }
        Log.e("REOS", "-RetrofitConfig-getClientLog-baseUrl$baseUrl")
        var puerto = ""
        when (BuildConfig.FLAVOR) {
            "chile" ->                 //puerto = ":8054";
                puerto = ""
            "bolivia" ->                 //puerto = ":8052";
                puerto = ""
            "ecuador" ->                 //puerto = ":8050";
                puerto = ""
            "peru" ->                 //Produccion----------
                puerto = ""
            "perurofalab" ->                 //Produccion----------

                puerto = ""
            "paraguay" ->                 //puerto = ":8051";
                puerto = ""
            "espania" -> puerto = ""
            "marruecos" -> puerto = ""
        }
        Log.e("REOS", "-RetrofitConfig-getClientLog-puerto$puerto")
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
                    .baseUrl(baseUrl + puerto)
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
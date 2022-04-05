
package com.vistony.salesforce.Controller.Retrofit;

import android.util.Log;
import android.view.View;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.SesionEntity;

import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    private static Retrofit retrofit = null;
    private static OkHttpClient client=null;

    public static Retrofit getClient() {

        String baseUrl = "http://salesforce.vistony.com";
        String puerto = "";
        switch (BuildConfig.FLAVOR) {
            case "chile":
                puerto = ":8054";
                break;
            case "bolivia":
                puerto = ":8052";
                break;
            case "ecuador":
                puerto = ":8050";
                break;
            case "peru":
                puerto = ":8001";
                break;
            case "paraguay":
                puerto = ":8051";
                break;
        }
        try{
            if(client==null) {
                client = new OkHttpClient. Builder()
                        .connectTimeout(2, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .readTimeout(2, TimeUnit.MINUTES)
                        .retryOnConnectionFailure(true)
                        .proxy(Proxy.NO_PROXY)
                        .build();
            }

           if(retrofit==null){
                retrofit = new Retrofit.Builder()
                            .baseUrl( baseUrl+puerto)
                            .addConverterFactory(GsonConverterFactory.create()).client(client)
                            .build();

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","Config-getClient-e:"+e.toString());
        }
        return retrofit;
    }

    public static Retrofit getClientLogin() {

        String baseUrl = "http://salesforce.vistony.com";
        String puerto = "";
        switch (BuildConfig.FLAVOR) {
            case "chile":
                puerto = ":8054";
                break;
            case "bolivia":
                puerto = ":8052";
                break;
            case "ecuador":
                puerto = ":8050";
                break;
            case "peru":
                puerto = ":8001";
                break;
            case "paraguay":
                puerto = ":8051";
                break;
        }
        try{
            if(client==null) {
                client = new OkHttpClient. Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .proxy(Proxy.NO_PROXY)
                        .build();
            }

            if(retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl( baseUrl+puerto)
                        .addConverterFactory(GsonConverterFactory.create()).client(client)
                        .build();

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","Config-getClient-e:"+e.toString());
        }
        return retrofit;
    }
}

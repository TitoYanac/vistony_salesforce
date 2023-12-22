
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
    private static Retrofit retrofit2 = null;
    private static OkHttpClient client=null;
    private static OkHttpClient client2=null;
    public static Retrofit getClient() {

        String baseUrl=null;
        switch (BuildConfig.FLAVOR) {
            case "peru":        baseUrl = "http://190.12.79.132:8083";
                break;
            case "paraguay":    baseUrl = "http://190.12.79.132:8083";
                break;
            case "perurofalab": baseUrl = "http://190.12.79.132:8083";
                break;
            case "marruecos":   baseUrl = "http://190.12.79.132:8083";
                break;
            case "chile":       baseUrl = "http://190.12.79.132:8083";
                break;
            case "ecuador":     baseUrl = "http://190.12.79.132:8083";
                break;
            case "espania":     baseUrl = "http://190.12.79.132:8083";
                break;
            case "bolivia":     baseUrl = "http://190.12.79.132:8083";
                break;
            default: baseUrl = "http://190.12.79.132:8083";
                break;
        }
        Log.e("jesusdebug", "-RetrofitConfig-getClientLog-baseUrl" + baseUrl );
        try{
            if(client2==null) {
                client2 = new OkHttpClient. Builder()
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .writeTimeout(5, TimeUnit.MINUTES)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .retryOnConnectionFailure(true)
                        .proxy(Proxy.NO_PROXY)
                        .build();
            }

           if(retrofit2==null){
               retrofit2 = new Retrofit.Builder()
                            .baseUrl( baseUrl)
                            .addConverterFactory(GsonConverterFactory.create()).client(client2)
                            .build();

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","Config-getClient-e:"+e.toString());
        }
        return retrofit2;
    }

    public static Retrofit getClientLogin() {

        String baseUrl=null;

        switch (BuildConfig.FLAVOR) {
            case "peru":        baseUrl = "http://190.12.79.132:8083";
                break;
            case "paraguay":    baseUrl = "http://190.12.79.132:8083";
                break;
            case "perurofalab": baseUrl = "http://190.12.79.132:8083";
                break;
            case "marruecos":   baseUrl = "http://190.12.79.132:8083";
                break;
            case "chile":       baseUrl = "http://190.12.79.132:8083";
                break;
            case "ecuador":     baseUrl = "http://190.12.79.132:8083";
                break;
            case "espania":     baseUrl = "http://190.12.79.132:8083";
                break;
            case "bolivia":     baseUrl = "http://190.12.79.132:8083";
                break;
            default: baseUrl = "http://190.12.79.132:8083";
                break;
        }
        try{
            if(client==null) {
                client = new OkHttpClient. Builder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .writeTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(5, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .proxy(Proxy.NO_PROXY)
                        .build();
            }

            if(retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl( baseUrl)
                        .addConverterFactory(GsonConverterFactory.create()).client(client)
                        .build();

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","Config-getClient-e:"+e.toString());
        }
        return retrofit;
    }

    public static Retrofit getClentSesionStart() {

        String baseUrl=null;
        switch (BuildConfig.FLAVOR) {
            case "peru":        baseUrl = "http://190.12.79.132:8083";
                break;
            case "paraguay":    baseUrl = "http://190.12.79.132:8083";
                break;
            case "perurofalab": baseUrl = "http://190.12.79.132:8083";
                break;
            case "marruecos":   baseUrl = "http://190.12.79.132:8083";
                break;
            case "chile":       baseUrl = "http://190.12.79.132:8083";
                break;
            case "ecuador":     baseUrl = "http://190.12.79.132:8083";
                break;
            case "espania":     baseUrl = "http://190.12.79.132:8083";
                break;
            case "bolivia":     baseUrl = "http://190.12.79.132:8083";
                break;
            default: baseUrl = "http://190.12.79.132:8083";
                break;
        }

        try{
            if(client==null) {
                client = new OkHttpClient. Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .proxy(Proxy.NO_PROXY)
                        .build();
            }

            if(retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl( baseUrl)
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


package com.vistony.salesforce.Controller.Retrofit;

import android.util.Log;

import com.vistony.salesforce.BuildConfig;

import java.lang.reflect.Field;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
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
        }
        try{
            if(client==null) {
                client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .proxy(Proxy.NO_PROXY)
                //.connectionPool(new ConnectionPool(10,15,TimeUnit.SECONDS))
                //.dns(new Ipv4PreferDns())
                .build();
            }

           if(retrofit==null){
                retrofit = new Retrofit.Builder()
                //.baseUrl("http://169.47.196.209")
                        //.baseUrl("http://salesforce.vistony.com")
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

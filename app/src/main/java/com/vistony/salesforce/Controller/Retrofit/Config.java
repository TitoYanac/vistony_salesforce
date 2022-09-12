
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
        //if(BuildConfig.FLAVOR.equals("peru"))
        if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("chile"))
        {
            baseUrl = "https://salesforce.vistony.pe";
        }else {
            baseUrl = "http://salesforce.vistony.com";
        }
        //Produccion-------------------------------
        //String baseUrl = "http://salesforce.vistony.com";
        //------------------------------------------
        //Pruebas QA--------------- Nueva Produccion
        //String baseUrl = "https://salesforce.vistony.pe";
        //------------------
        //String baseUrl = "http://200.107.154.233";

        String puerto = "";
        switch (BuildConfig.FLAVOR) {
            case "chile":
                //puerto = ":8054";
                //Produccion----------
                //puerto = ":8054";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "bolivia":
                puerto = ":8052";
                //puerto = "";
                break;
            case "ecuador":
                puerto = ":8050";
                break;
            case "peru":
                //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "paraguay":
                puerto = ":8051";
                break;
        }
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
                            .baseUrl( baseUrl+puerto)
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
        //if(BuildConfig.FLAVOR.equals("peru"))
        //if(BuildConfig.FLAVOR.equals("peru"))
        if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("chile"))
        {
            baseUrl = "https://salesforce.vistony.pe";
        }else {
            baseUrl = "http://salesforce.vistony.com";
        }
        //Produccion-------------------------------
        //String baseUrl = "http://salesforce.vistony.com";
        //------------------------------------------
        //Pruebas QA--------------- Nueva Produccion
        //String baseUrl = "https://salesforce.vistony.pe";
        //------------------
        //String baseUrl = "http://200.107.154.233";

        String puerto = "";
        switch (BuildConfig.FLAVOR) {
            case "chile":
                //puerto = ":8054";
                //Produccion----------
                //puerto = ":8054";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "bolivia":
                puerto = ":8052";
                //puerto = "";
                break;
            case "ecuador":
                puerto = ":8050";
                break;
            case "peru":
                //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
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

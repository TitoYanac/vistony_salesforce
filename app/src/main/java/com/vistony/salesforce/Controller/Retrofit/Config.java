
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
            //case "chile"://Produccion
            //case "ecuador":
            //QA Aprobaciones 11/07/2023
            case "peru":
                //Produccion
                //case "bolivia":
            //case "espania":
            case "marruecos":
                baseUrl = "https://salesforce.vistony.pe";
                break;
            //case "chile"://QA
            case "perurofalab":
            //case "bolivia":
            //case "paraguay":
                //QA Peru
            //case "peru":
               baseUrl = "https://app.vistony.pe";
                break;
                //Produccion
            /*case "peru":
                baseUrl = "http://190.12.79.132:8083";
            break;*/
            //QA Aprobaciones
            /*case "peru":
                baseUrl = "http://190.12.79.132:8082";
                break;*/
            case "chile":
                baseUrl = "http://190.12.79.132:8084";
                break;
            case "ecuador":
                baseUrl = "http://190.12.79.132:8085";
                break;
            case "espania":
                baseUrl = "http://190.12.79.132:8086";
                break;
            case "bolivia":
                baseUrl = "http://190.12.79.132:8088";
                break;
            case "paraguay":
                baseUrl = "http://190.12.79.132:8087";
                break;
            default:
                baseUrl = "http://salesforce.vistony.com";
                break;
        }
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
                //puerto = ":8052";
                puerto = "";
                break;
            case "ecuador":
                //puerto = ":8050";
                puerto = "";
                break;
            case "peru":
                //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "perurofalab":
                //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "paraguay":
                //puerto = ":8051";
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                break;
            case "espania":
                puerto = "";
                break;
            case "marruecos":
                puerto = "";
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
        switch (BuildConfig.FLAVOR) {
            //case "chile"://Produccion
            //QA Aprobaciones 11/07/2023
            case "peru":
                //Produccion
            //case "ecuador":
            //case "espania":
            case "marruecos":
                baseUrl = "https://salesforce.vistony.pe";
                break;
            case "perurofalab":
            //case "chile"://QA
            //case "bolivia":
            //case "paraguay":
                //QA Peru
            //case "peru":
                baseUrl = "https://app.vistony.pe";
                break;
            //Produccion
            /*case "peru":
                baseUrl = "http://190.12.79.132:8083";
                break;*/
            //QA Aprobaciones
            /*case "peru":
                baseUrl = "http://190.12.79.132:8082";
                break;*/
            case "chile":
                baseUrl = "http://190.12.79.132:8084";
                break;
            case "ecuador":
                baseUrl = "http://190.12.79.132:8085";
                break;
            case "espania":
                baseUrl = "http://190.12.79.132:8086";
                break;
            case "bolivia":
                baseUrl = "http://190.12.79.132:8088";
                break;
            case "paraguay":
                baseUrl = "http://190.12.79.132:8087";
                break;
            default:
                baseUrl = "http://salesforce.vistony.com";
                break;
        }
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
                //puerto = ":8052";
                puerto = "";
                break;
            case "ecuador":
                //puerto = ":8050";
                puerto = "";
                break;
            case "peru":
                //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "perurofalab":
                //Produccion----------
                //puerto = ":8001";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                //-----------------------
                break;
            case "paraguay":
                //puerto = ":8051";
                //--------------------
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
                break;
            case "espania":
                puerto = "";
                break;
            case "marruecos":
                puerto = "";
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

    public static Retrofit getClentSesionStart() {

        String baseUrl=null;
        //if(BuildConfig.FLAVOR.equals("peru"))
        //if(BuildConfig.FLAVOR.equals("peru"))
        /*if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
        {
            baseUrl = "https://salesforce.vistony.pe";
        }else {
            baseUrl = "http://salesforce.vistony.com";
        }*/
        switch (BuildConfig.FLAVOR) {
            //case "chile"://Produccion
            //QA Aprobaciones 11/07/2023
            case "peru":
            //case "ecuador":
            //case "espania":
            case "marruecos":
                baseUrl = "https://salesforce.vistony.pe";
                break;
            case "chile"://QA
            case "perurofalab":
            //case "bolivia":
            //case "paraguay":
            //case "peru":
                baseUrl = "https://app.vistony.pe";
                break;
             //Produccion
            /*case "peru":
                baseUrl = "http://190.12.79.132:8083";
                break;*/
            //QA Aprobaciones
            /*case "peru":
                baseUrl = "http://190.12.79.132:8082";
                break;*/
            case "ecuador":
                baseUrl = "http://190.12.79.132:8085";
                break;
            case "espania":
                baseUrl = "http://190.12.79.132:8086";
                break;
            case "bolivia":
                baseUrl = "http://190.12.79.132:8088";
                break;
            case "paraguay":
                baseUrl = "http://190.12.79.132:8087";
                break;
            default:
                baseUrl = "http://salesforce.vistony.com";
                break;
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
                //puerto = ":8052";
                puerto = "";
                break;
            case "ecuador":
                //puerto = ":8050";
                puerto = "";
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
                //puerto = ":8051";
                //Pruebas QA------------- Nueva Produccion
                puerto = "";
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

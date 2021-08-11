package com.vistony.salesforce.Controller.Retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    private static Retrofit retrofit = null;
    private static OkHttpClient client=null;

    public static Retrofit getClient() {
        try{
            OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

            if(retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://169.47.196.209")// Pruebas SAP
                        .addConverterFactory(GsonConverterFactory.create()).client(client)
                        .build();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        
        return retrofit;
    }

    public static void closeConection(){
        client.dispatcher().executorService().shutdown();
        client.connectionPool().evictAll();
    }

    //DocumentLine


}

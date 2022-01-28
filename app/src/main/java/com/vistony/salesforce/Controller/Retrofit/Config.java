
package com.vistony.salesforce.Controller.Retrofit;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Config {
    private static Retrofit retrofit = null;
    private static OkHttpClient client=null;

    public static Retrofit getClient() {
        try{
            if(client==null) {
                client = new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .proxy(Proxy.NO_PROXY)
                //.connectionPool(new ConnectionPool(10,15,TimeUnit.SECONDS))
                //.dns(new Ipv4PreferDns())
                .build();
            }

           if(retrofit==null){
                retrofit = new Retrofit.Builder()
                .baseUrl("http://169.47.196.209/")
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return retrofit;
    }
    public static Retrofit getClientPost() {
        try{
            if(client==null) {
                client = new OkHttpClient.Builder()
                        .connectTimeout(2, TimeUnit.MINUTES)
                        .writeTimeout(2, TimeUnit.MINUTES)
                        .readTimeout(2, TimeUnit.MINUTES)
                        .retryOnConnectionFailure(true)
                        .proxy(Proxy.NO_PROXY)
                        //.connectionPool(new ConnectionPool(10,15,TimeUnit.SECONDS))
                        //.dns(new Ipv4PreferDns())
                        .build();
            }

            if(retrofit==null){
                retrofit = new Retrofit.Builder()
                        .baseUrl("http://169.47.196.209_2/")
                        .addConverterFactory(GsonConverterFactory.create()).client(client)
                        .build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return retrofit;
    }
}

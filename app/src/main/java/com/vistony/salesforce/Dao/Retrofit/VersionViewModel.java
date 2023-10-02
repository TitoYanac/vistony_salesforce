package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Controller.Retrofit.Config;
import org.json.JSONException;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vistony.salesforce.Controller.Utilitario.CifradoController.md5;

public class VersionViewModel {

    private MutableLiveData<Object> status= new MutableLiveData<>();

    public MutableLiveData<Object> getVs(String imei,String version,Context context){
        SharedPreferences statusImei = context.getSharedPreferences("imeiRegister", Context.MODE_PRIVATE);
        String baseUrl=null;
        //if(BuildConfig.FLAVOR.equals("peru"))
        //if(BuildConfig.FLAVOR.equals("peru"))
        //if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("chile"))
        //if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
        /*if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("chile")||
                BuildConfig.FLAVOR.equals("bolivia")||BuildConfig.FLAVOR.equals("perurofalab")
                ||BuildConfig.FLAVOR.equals("espania")||BuildConfig.FLAVOR.equals("marruecos")
        )
        {
            baseUrl = "https://salesforce.vistony.pe";
        }else {
            baseUrl = "http://salesforce.vistony.com";
        }*/
        switch (BuildConfig.FLAVOR) {
            //case "chile":
            //QA Aprobaciones 11/07/2023
            //case "peru":
                //Produccion
            //case "ecuador":
            //case "espania":
            case "marruecos":
                baseUrl = "https://salesforce.vistony.pe";
                break;
            case "perurofalab":
                //Ultima milla prueba bolivia
            case "bolivia":
            case "paraguay":
            //case "peru":
                baseUrl = "https://app.vistony.pe";
                break;
            //Produccion
            case "peru":
                baseUrl = "http://190.12.79.132:8083";
                break;
            //Aprobaciones
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

        String endPoint=baseUrl
                +puerto
                 +"/"
                ;

        switch(BuildConfig.FLAVOR){
            case "india":
                endPoint=endPoint+"/v1.0/api/version?imei="+imei+"&app="+md5(context.getPackageName()); //India es el lead y esta en SAP DB peru backup
                break;
            case "chile":
            case "ecuador":
            case "bolivia":
            case "peru":
            case "paraguay":
            case "espania":
            case "marruecos":
            case "perurofalab":
                String hashMd5=context.getPackageName()+"."+BuildConfig.BUILD_TYPE;
                Log.e("El hash ess","=>"+hashMd5);
                endPoint=endPoint+BuildConfig.BASE_ENDPOINT+BuildConfig.BASE_ENVIRONMENT+"/version?imei="+imei+"&token="+md5(hashMd5);
                Log.e("REOS","VersionViewModel-getVs-endPoint:"+endPoint);
                break;
        }

        Config.getClientLogin().create(Api.class).getVs(endPoint).enqueue(new Callback<VersionEntity>() {
         @Override
            public void onResponse(Call<VersionEntity> call, Response<VersionEntity> response) {
                if(response.isSuccessful()) {
                    SharedPreferences.Editor editor = statusImei.edit();
                    String versionVs=response.body().getVs();
                    VersionEntity vs=response.body();

                    if(version.equals(versionVs)){
                        editor.putString("status","yes");
                        status.setValue(false);
                    }else{
                        status.setValue(versionVs); //descargara nuevo apk
                        editor.putString("status","not");
                    }

                    editor.apply();

                }else{
                    SharedPreferences.Editor editor = statusImei.edit();
                    editor.putString("status","yes");
                    editor.apply();

                    status.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<VersionEntity> call, Throwable error) {
                SharedPreferences.Editor editor = statusImei.edit();
                editor.putString("status","yes");
                editor.apply();

                String message="Error no definido";

                if(error instanceof SocketTimeoutException){
                    message="El tiempo de respuesta expiro";
                }else if(error instanceof UnknownHostException){
                    message="No tiene conexión a internet";
                }else if (error instanceof ConnectException) {
                    message="El servidor no responde";
                } else if (error instanceof JSONException || error instanceof JsonSyntaxException) {
                    message="Error en el parceo";
                } else if (error instanceof IOException) {
                    message=error.getMessage();
                }

                status.setValue(message);

            }
        });

        return status;
    }
}


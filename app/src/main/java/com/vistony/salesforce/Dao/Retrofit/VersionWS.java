package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.ChekingUpdate;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.VersionEntityResponse;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vistony.salesforce.Entity.SesionEntity.urlFormMicrosft;
import static com.vistony.salesforce.View.LoginView.statusImei;

public class VersionWS {

    private MutableLiveData<Object> status= new MutableLiveData<>();

    public MutableLiveData<Object> getVs(String imei,String version){
        Config.getClient().create(Api.class).getVs("https://graph.vistony.pe/Version?imei="+imei+"&app=SalesForce").enqueue(new Callback<VersionEntity>() {
            @Override
            public void onResponse(Call<VersionEntity> call, Response<VersionEntity> response) {
                if (response.isSuccessful()) {
                    VersionEntity versionEntity=response.body();

                        SharedPreferences.Editor editor = statusImei.edit();
                        if(version.equals(versionEntity.getVs())){
                            editor.putString("status","yes");
                            status.setValue(false);
                        }else{
                            status.setValue(versionEntity.getVs()); //descargara nuevo apk
                            editor.putString("status","not");
                        }

                        editor.apply();

                }else{
                    SharedPreferences.Editor editor = statusImei.edit();
                    editor.putString("status","yes");
                    editor.apply();
                }
            }

            @Override
            public void onFailure(Call<VersionEntity> call, Throwable error) {
                Log.e("Error",""+error.getMessage());

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


package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Controller.Retrofit.Api;
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

        String endPoint="http://169.47.196.209/cl/api/version?imei="+imei+"&token="+md5(context.getPackageName());
        if(BuildConfig.FLAVOR.equals("india")){
            endPoint="http://169.47.196.209/v1.0/api/version?imei="+imei+"&app="+md5(context.getPackageName());
        }

        Config.getClient().create(Api.class).getVs(endPoint).enqueue(new Callback<VersionEntity>() {
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

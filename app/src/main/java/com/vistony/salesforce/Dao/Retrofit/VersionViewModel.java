package com.vistony.salesforce.Dao.Retrofit;

import android.content.SharedPreferences;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.vistony.salesforce.View.LoginView.statusImei;

public class VersionViewModel {

    private MutableLiveData<Object> status= new MutableLiveData<>();

    public MutableLiveData<Object> getVs(String imei,String version){
        Log.e("Error","VersionWS inicio peticion");
        Config.getClient().create(Api.class).getVs(imei,"SalesForce").enqueue(new Callback<VersionEntity>() {
         @Override
            public void onResponse(Call<VersionEntity> call, Response<VersionEntity> response) {
                if (response.isSuccessful()) {
                    Log.e("Error","VersionWS true");
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
                    Log.e("Error","VersionWS else "+response.errorBody());
                    Log.e("JPCM","ENTRO ELSE LoginViewModel "+response.code()+" => "+response.message());
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Log.e("JPCM","ENTRO ELSE VersionWS "+jObjError.getJSONObject("error").getString("message"));
                        Log.e("JPCM","ENTRO ELSE VersionWS "+response.code()+" => "+response.message());

                    } catch (Exception e) {
                        Log.e("JPCM","ENTRO ELSE VersionWS "+e.getMessage());
                    }

                    SharedPreferences.Editor editor = statusImei.edit();
                    editor.putString("status","yes");
                    editor.apply();

                    status.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<VersionEntity> call, Throwable error) {
                Log.e("Error","VersionWS "+error.getMessage());

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


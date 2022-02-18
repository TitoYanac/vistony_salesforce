package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.SesionEntity;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackupRepository  extends ViewModel {
    private MutableLiveData<Object> status= new MutableLiveData<>();

    public LiveData<Object> sendSqlite(Context context){
        String imei= SesionEntity.imei;
        File file = new File(Environment.getDataDirectory(),"/data/"+context.getPackageName()+"/databases/dbcobranzas");
        Api api = Config.getClient().create(Api.class);
        RequestBody requestBody=RequestBody.create(file,MediaType.parse("multipart/form-data"));

        final String endPoint="https://reclamos.vistonyapp.com/backup/sqlite";
        final String nameFile=(imei==null?"sinImei":imei);

        Log.e("jepicame","=>"+nameFile);

        api.sendBackup(endPoint,MultipartBody.Part.createFormData("file",nameFile,requestBody)).enqueue(new Callback<ResponseBody>(){

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("REOS","BackupRepository-sendSqlite-call:"+call.toString());
                Log.e("REOS","BackupRepository-sendSqlite-response:"+response.toString());
                status.setValue(true);
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable error) {

                String message="Error no definido";

                if(error instanceof SocketTimeoutException){
                    message="El tiempo de respuesat expiro";
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
        } );

        return status;
    }

}


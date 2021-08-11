package com.vistony.salesforce.Dao.Retrofit;

import android.os.Environment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;

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

public class BackupWS {
    private MutableLiveData<Object> status= new MutableLiveData<>();

    public LiveData<Object> sendSqlite(String imei){

        File file = new File(Environment.getDataDirectory(),"/data/com.vistony.salesforce/databases/dbcobranzas");
        Api api = Config.getClient().create(Api.class);
        api.sendBackup("https://reclamos.vistonyapp.com/backup/sqlite",
        MultipartBody.Part.createFormData("file",(imei==null?"sinImei":imei),RequestBody.create(MediaType.parse("multipart/form-data"),file))).enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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


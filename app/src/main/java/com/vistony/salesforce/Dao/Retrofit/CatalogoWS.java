package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonSyntaxException;
import com.vistony.salesforce.Controller.Adapters.ListaCatalogoAdapter;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CatalogoWS {
    private MutableLiveData<Object> listaCatalogoAdapter= new MutableLiveData<>();
    private Context context;

    public CatalogoWS(Context context){
        this.context=context;
    }
    public LiveData<Object> send(String imei){

        Api api = Config.getClient().create(Api.class);
        api.getCatalog("https://reclamos.vistonyapp.com/fichas?imei="+imei).enqueue(new Callback<List<CatalogoEntity>>(){
            @Override
            public void onResponse(Call<List<CatalogoEntity>> call, Response<List<CatalogoEntity>> response) {
                if(response.isSuccessful()){
                    listaCatalogoAdapter.setValue(new ListaCatalogoAdapter(context,response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<CatalogoEntity>> call, Throwable error) {

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

                listaCatalogoAdapter.setValue(message);
            }
        } );

        return listaCatalogoAdapter;
    }

}

package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.gson.Gson;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitaEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitaRepository extends ViewModel {
    private VisitaSQLite visitaSQLite;

    public MutableLiveData<String> PostVisitaWS(final VisitaSQLiteEntity visitasEntity,final Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        if(visitaSQLite==null){
            visitaSQLite =new VisitaSQLite(context);
        }

        sendVisit(visitasEntity,context, new VisitCallback(){
            @Override
            public void onResponseSap(VisitaEntity data) {
                if(data!=null){
                    boolean status= visitaSQLite.ActualizaEstadoWSVisita(data);

                    if(status){
                        temp.setValue("El estado de la visita fue actualizada");
                    }else{
                        temp.setValue("El estado de la visita no fue actualizada");
                    }
                }else{
                    temp.setValue("Ocurrio un error en el servidor");
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    public MutableLiveData<String> visitResend(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendVisit(null,context, new VisitCallback(){
            @Override
            public void onResponseSap(VisitaEntity data) {
                if(data!=null){
                    boolean status= visitaSQLite.ActualizaEstadoWSVisita(data);

                    if(status){
                        temp.setValue("El estado de las visitas fue actualizado");
                    }else{
                        temp.setValue("El estado de las visitas no fue actualizado");
                    }
                }else{
                    temp.setValue("Ocurrio un error en el servidor");
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    private void sendVisit(final VisitaSQLiteEntity visitEntity, final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new Gson();

        if(visitaSQLite==null){
            visitaSQLite =new VisitaSQLite(context);
        }

        if(visitEntity==null){
            ArrayList<VisitaSQLiteEntity> listVisit = visitaSQLite.ObtenerVisitas();
            if(listVisit.size()>0){
                json = gson.toJson(listVisit);
                json = "{ \"Visits\":" + json + "}";
            }
        }else{
            json = gson.toJson(visitEntity);
            json = "{ \"Visits\":[" + json + "]}";
        }

        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json,MediaType.parse("application/json; charset=utf-8"));
            Config.getClient().create(Api.class).sendVisit("http://169.47.196.209/cl/api/Visit",jsonRequest).enqueue(new Callback<VisitaEntity>() {
                @Override
                public void onResponse(Call<VisitaEntity> call, Response<VisitaEntity> response) {
                    if(response.isSuccessful()){
                        callback.onResponseSap(response.body());
                    }else{
                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<VisitaEntity> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay visitas pendientes de enviar");
        }
    }
}

interface VisitCallback {
    void onResponseSap(VisitaEntity visitaEntity);
    void onResponseErrorSap(String response);
}
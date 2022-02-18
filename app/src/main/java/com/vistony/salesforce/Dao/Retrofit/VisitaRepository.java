package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

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

    public MutableLiveData<String> visitResend(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendVisit(context, new VisitCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay ordenes de venta pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    private void sendVisit(final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new Gson();

        if(visitaSQLite==null){
            visitaSQLite =new VisitaSQLite(context);
        }

        ArrayList<VisitaSQLiteEntity> listVisit = visitaSQLite.ObtenerVisitas();
        if(listVisit!=null && listVisit.size()>0){
            json = gson.toJson(listVisit);
            json = "{ \"Visits\":" + json + "}";
        }
        Log.e("REOS", "VisitaRepository-sendVisit-json"+json);
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json,MediaType.parse("application/json; charset=utf-8"));
            Config.getClient().create(Api.class).sendVisit(jsonRequest).enqueue(new Callback<VisitaEntity>() {
                @Override
                public void onResponse(Call<VisitaEntity> call, Response<VisitaEntity> response) {

                    VisitaEntity visitas=response.body();

                    if(response.isSuccessful() && visitas!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (VisitaSQLiteEntity respuesta:visitas.getVisitas()) {
                            if(respuesta.getHaveError().equals("0")){//se envio
                               responseData.add("La visita fue aceptado en SAP");
                                visitaSQLite.ActualizaResultadoVisitaEnviada(respuesta.getIdVisit());

                            }else{//tiene error
                                responseData.add("La visita no fue aceptado en SAP");
                            }
                        }

                        callback.onResponseSap(responseData);
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
    void onResponseSap(ArrayList<String> response);
    void onResponseErrorSap(String response);
}
package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DetailStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StatusDispatchEntityResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusDispatchRepository  extends ViewModel {
    private MutableLiveData<List<HistoricStatusDispatchEntity>> ListHistoricStatusDispatchEntity= new MutableLiveData<>();
    StatusDispatchSQLite statusDispatchSQLite;

    public MutableLiveData<List<HistoricStatusDispatchEntity>> getHistoricStatusDispatch(
            String Imei,
            String Date){
        ListHistoricStatusDispatchEntity= new MutableLiveData<>();
        Config.getClient().create(Api.class).geHistoricStatusDispatch(Imei,Date).enqueue(new Callback<HistoricStatusDispatchEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricStatusDispatchEntityResponse> call, Response<HistoricStatusDispatchEntityResponse> response) {
                HistoricStatusDispatchEntityResponse historicStatusDispatchEntityResponse=response.body();
                Log.e("REOS","StatusDispatchRepository-getHistoricStatusDispatch-call: "+call.toString());
                Log.e("REOS","StatusDispatchRepository-getHistoricStatusDispatch-response: "+response.toString());
                if(response.isSuccessful() && historicStatusDispatchEntityResponse.getHistoricStatusDispatch() .size()>0){
                    ListHistoricStatusDispatchEntity.setValue(historicStatusDispatchEntityResponse.getHistoricStatusDispatch());
                }else
                {
                    ListHistoricStatusDispatchEntity.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<HistoricStatusDispatchEntityResponse> call, Throwable t) {
                ListHistoricStatusDispatchEntity=null;
            }
        });
        return ListHistoricStatusDispatchEntity;
    }

    public MutableLiveData<String> statusDispatchSend(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendStatusDispatch(context, new VisitCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay Estados de Despacho pendientes de Enviar");
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

    private void sendStatusDispatch(final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();

        if(statusDispatchSQLite==null){
            statusDispatchSQLite =new StatusDispatchSQLite(context);
        }

        ArrayList<HeaderStatusDispatchEntity> listStatusDispatch = statusDispatchSQLite.getListHeaderStatusDispatch(context);
        if(listStatusDispatch!=null && listStatusDispatch.size()>0){
            json = gson.toJson(listStatusDispatch);
            json = "{ \"Dispatch\":" + json + "}";
        }

        Log.e("REOS", "StatusDispatchRepository-sendStatusDispatch-json"+json);
        /*if(listStatusDispatch!=null && listStatusDispatch.size()>0)
        {
            statusDispatchSQLite.UpdatePruebaJSON(json, listStatusDispatch.get(0).getEntrega_id());
        }*/
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendStatusDispatch(jsonRequest).enqueue(new Callback<HeaderStatusDispatchEntityResponse>() {
                @Override
                public void onResponse(Call<HeaderStatusDispatchEntityResponse> call, Response<HeaderStatusDispatchEntityResponse> response) {

                    HeaderStatusDispatchEntityResponse headerStatusDispatchEntityResponse=response.body();

                    if(response.isSuccessful() && headerStatusDispatchEntityResponse!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (HeaderStatusDispatchEntity respuesta:headerStatusDispatchEntityResponse.getHeaderStatusDispatchEntityResponse()  )
                        {
                            for(DetailStatusDispatchEntity respuestaDetalle:respuesta.getDetails())
                            {
                                if(respuestaDetalle.getHaveError().equals("N")){//se envio
                                    responseData.add("El Estado de Despacho fue aceptado en SAP");
                                    statusDispatchSQLite.UpdateResultStatusDispatch(respuesta.getDocEntry(),respuestaDetalle.getLineId(),respuestaDetalle.getMessage());

                                }else{//tiene error
                                    responseData.add("El Estado de Despacho no fue aceptado en SAP");
                                }
                            }
                        }

                        callback.onResponseSap(responseData);
                        callback.onResponseErrorSap(response.message());
                    }else{

                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<HeaderStatusDispatchEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap("Cayo en Error:"+t.getMessage());

                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay estados de despachos pendientes de enviar");
        }
    }

    public MutableLiveData<String> statusDispatchSendTime(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendStatusDispatchTime(context, new VisitCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay Estados de Despacho con Tiempo pendientes de Enviar");
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

    private void sendStatusDispatchTime(final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();

        if(statusDispatchSQLite==null){
            statusDispatchSQLite =new StatusDispatchSQLite(context);
        }

        ArrayList<HeaderStatusDispatchEntity> listStatusDispatch = statusDispatchSQLite.getHeaderListStatusDispatchTime(context);
        if(listStatusDispatch!=null && listStatusDispatch.size()>0){
            json = gson.toJson(listStatusDispatch);
            json = "{ \"Dispatch\":" + json + "}";
        }

        Log.e("REOS", "StatusDispatchRepository-sendStatusDispatchTime-json"+json);
        /*if(listStatusDispatch!=null && listStatusDispatch.size()>0)
        {
            statusDispatchSQLite.UpdatePruebaJSON(json, listStatusDispatch.get(0).getEntrega_id());
        }*/
        if(json!=null){
            ArrayList<String> responseData=new ArrayList<>();
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendStatusDispatch(jsonRequest).enqueue(new Callback<HeaderStatusDispatchEntityResponse>() {
                @Override
                public void onResponse(Call<HeaderStatusDispatchEntityResponse> call, Response<HeaderStatusDispatchEntityResponse> response) {

                    HeaderStatusDispatchEntityResponse headerStatusDispatchEntityResponse = response.body();
                    for (HeaderStatusDispatchEntity respuesta : headerStatusDispatchEntityResponse.getHeaderStatusDispatchEntityResponse()) {
                        for (DetailStatusDispatchEntity respuestaDetalle : respuesta.getDetails()) {
                            if (respuestaDetalle.getHaveError().equals("N")) {//se envio

                                responseData.add("La Hora de Estado de Despacho fue aceptado en SAP");
                                statusDispatchSQLite.UpdateResultStatusDispatchTime(respuesta.getDocEntry(), respuestaDetalle.getLineId(),respuestaDetalle.getMessage());

                            } else {//tiene error
                                responseData.add("El Estado de Despacho no fue aceptado en SAP");
                            }
                        }
                    }

                    callback.onResponseSap(responseData);
                    callback.onResponseErrorSap(response.message());
                }
                    /*if(response.isSuccessful() && statusDispatchEntityResponse!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (StatusDispatchEntity respuesta:statusDispatchEntityResponse.getStatusDispatch()) {
                            if(respuesta.getHaveError().equals("N")){//se envio
                                responseData.add("La Hora de Estado de Despacho fue aceptado en SAP");
                                statusDispatchSQLite.UpdateResultStatusDispatchTime(respuesta.getDocEntry(),respuesta.getLineId());

                            }else{//tiene error
                                responseData.add("La Hora de Estado de Despacho no fue aceptado en SAP");
                            }
                        }

                        callback.onResponseSap(responseData);
                    }else{
                        callback.onResponseErrorSap(response.message());
                    }*/


                @Override
                public void onFailure(Call<HeaderStatusDispatchEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay hora de estados de despachos pendientes de enviar");
        }
    }

}

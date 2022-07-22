package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SummaryofeffectivenessEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SummaryofeffectivenessEntityResponse;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

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
        Gson gson=new Gson();

        if(statusDispatchSQLite==null){
            statusDispatchSQLite =new StatusDispatchSQLite(context);
        }

        ArrayList<StatusDispatchEntity> listStatusDispatch = statusDispatchSQLite.getListStatusDispatch();
        if(listStatusDispatch!=null && listStatusDispatch.size()>0){
            json = gson.toJson(listStatusDispatch);
            json = "{ \"StatusDispatch\":" + json + "}";
        }
        Log.e("REOS", "StatusDispatchRepository-sendStatusDispatch-json"+json);
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendStatusDispatch(jsonRequest).enqueue(new Callback<StatusDispatchEntityResponse>() {
                @Override
                public void onResponse(Call<StatusDispatchEntityResponse> call, Response<StatusDispatchEntityResponse> response) {

                    StatusDispatchEntityResponse statusDispatchEntityResponse=response.body();

                    if(response.isSuccessful() && statusDispatchEntityResponse!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (StatusDispatchEntity respuesta:statusDispatchEntityResponse.getStatusDispatch()) {
                            if(respuesta.getHaveError().equals("0")){//se envio
                                responseData.add("El Estado de Despacho fue aceptado en SAP");
                                statusDispatchSQLite.UpdateResultStatusDispatch(respuesta.getEntrega_id());

                            }else{//tiene error
                                responseData.add("El Estado de Despacho no fue aceptado en SAP");
                            }
                        }

                        callback.onResponseSap(responseData);
                    }else{
                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<StatusDispatchEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay visitas pendientes de enviar");
        }
    }

}

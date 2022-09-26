package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.LeadSQLite;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DetailStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadAddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.LeadAddressEntityResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadClienteViewModel extends ViewModel {

    private LeadSQLite leadSQLite;

    public MutableLiveData<String> sendLead(final HashMap<String, String> params,final Context context) {
        MutableLiveData<String> temp=new MutableLiveData<String>();

        if(leadSQLite==null){
            leadSQLite = new LeadSQLite(context);
        }

        temp.setValue("init");
        insertLeadSqlite(params,context);
        /*RequestBody requestBody=RequestBody.create(("{ \"Leads\":["+new JSONObject(params).toString()+"]}"),MediaType.parse("application/json; charset=utf-8"));
        Config.getClient().create(Api.class).sendLead("http://169.47.196.209/v1.0/api/customers",requestBody).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.isSuccessful()){

                    leadSQLite.updateStatusSend(params.get("DateTime"),params.get("CardName"));

                    temp.setValue("successful");
                }else{
                    temp.setValue("else " +response.code()+"=>"+response.message()+"=>"+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                temp.setValue("failure");
            }
        });*/

        return temp;
    }

    public MutableLiveData<String> sendLeadNotSend(final Context context){
        if(leadSQLite==null){
            leadSQLite = new LeadSQLite(context);
        }

        MutableLiveData<String> temp=new MutableLiveData<String>();

        ArrayList<LeadEntity> leadEntity=leadSQLite.getLeadNotSend();
        JSONArray jsArray = new JSONArray(leadEntity);

        temp.setValue(jsArray.toString());

        return temp;
    }

    private void insertLeadSqlite(final HashMap<String, String> params,final Context context){
        if(leadSQLite==null){
            leadSQLite = new LeadSQLite(context);
        }

        leadSQLite.addLead(
                params.get("CardName"),
                params.get("TaxNumber"),
                params.get("TradeName"),
                params.get("NumberPhono"),
                params.get("NumberCellPhone"),
                params.get("ContactPerson"),
                params.get("Email"),
                params.get("Latitude"),
                params.get("Longitude"),
                params.get("Comentary"),
                params.get("Category"),
                params.get("photo"),
                params.get("DateTime"),
                params.get("SalesPersonCode"),
                params.get("DocumentsOwner"),
                params.get("Address"),
                params.get("Reference"),
                params.get("CardCode"),
                params.get("domembarque_id"),
                params.get("type"),
                params.get("addresscode")
        );
    }

    public MutableLiveData<String> sendGeolocationClient (Context context, String imei, Executor executor){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        try
        {
        sendGeolocationClient(imei,context, new VisitCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.postValue(("No hay Geolocalizacion de Clientes pendientes de enviar"));
                }else{
                    temp.postValue(data.get(0));
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.postValue(response);
            }
        });

        }catch (Exception e)
        {
            Toast.makeText(context, "Error en Proceso de Envio de Geolocalizacion- Error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        return temp;
    }

    private void sendGeolocationClient(String imei,final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();
        LeadSQLite leadSQLite =new LeadSQLite(context);

        ArrayList<LeadAddressEntity> listLeadAddressEntity= leadSQLite.getGeolocationClient();
        if(listLeadAddressEntity!=null && listLeadAddressEntity.size()>0){
            json = gson.toJson(listLeadAddressEntity);
            //json = "{ \"Addresses\":" + json + "}";
            json = "{ \"Token\":\"" + imei + "\",\"Addresses\":" + json + "}";
        }

        Log.e("REOS", "LeadClienteViewModel-sendGeolocationClient-json"+json);
        /*if(listLeadAddressEntity!=null && listLeadAddressEntity.size()>0)
        {
            leadSQLite.UpdateLeadJSON(json, listLeadAddressEntity.get(0).getCardCode());
        }*/
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendLeadAddress(jsonRequest).enqueue(new Callback<LeadAddressEntityResponse>() {
                @Override
                public void onResponse(Call<LeadAddressEntityResponse> call, Response<LeadAddressEntityResponse> response) {

                    LeadAddressEntityResponse leadAddressEntityResponse=response.body();

                    if(response.isSuccessful() && leadAddressEntityResponse!=null){
                        ArrayList<String> responseData=new ArrayList<>();

                        for (LeadAddressEntity respuesta:leadAddressEntityResponse.getLeadAddressEntity()  )
                        {
                                if(respuesta.getHaveError().equals("N")){//se envio

                                    responseData.add("La Geolocalizacion fue aceptado en SAP");
                                    Log.e("REOS","LeadClienteViewModel.sendGeolocationClient.respuesta.getCardCode():" + respuesta.getCardCode());
                                    Log.e("REOS","LeadClienteViewModel.sendGeolocationClient.respuesta.getAddressCode():" + respuesta.getAddressCode() );
                                    Log.e("REOS","LeadClienteViewModel.sendGeolocationClient.respuesta.getMessage():" + respuesta.getMessage());
                                    leadSQLite.UpdateResultLeadAddress(respuesta.getCardCode(),respuesta.getAddressCode() , respuesta.getMessage());

                                }else{//tiene error
                                    responseData.add("La Geolocalizacion no fue aceptado en SAP");
                                    leadSQLite.UpdateResultLeadAddress(respuesta.getCardCode(),respuesta.getAddressCode() , respuesta.getMessage());
                                }

                        }

                        callback.onResponseSap(responseData);
                        callback.onResponseErrorSap(response.message());
                    }else{

                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<LeadAddressEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap("Cayo en Error:"+t.getMessage());

                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay Geolocalizacion de Clientes pendientes de enviar");
        }
    }
}

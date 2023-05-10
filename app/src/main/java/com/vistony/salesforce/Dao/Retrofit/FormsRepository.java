package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CustomerComplaintFormsSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.JSON.CollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CobranzaItemEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormsRepository extends ViewModel {

    CustomerComplaintFormsSQLiteDao customerComplaintFormsSQLiteDao;

    public MutableLiveData<String> getDataSendForms(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendForms(context, new CollectionCallback(){
            @Override
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay formularios pendientes de enviar");
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

    private void sendForms(final Context context, final CollectionCallback callback){
        String  json=null;
        Gson gson=new Gson();

        if(customerComplaintFormsSQLiteDao==null){
            customerComplaintFormsSQLiteDao =new CustomerComplaintFormsSQLiteDao(context);
        }
        ArrayList<CustomerComplaintFormsEntity> listCustomerComplaintFormsEntity=new ArrayList<>();

        listCustomerComplaintFormsEntity = customerComplaintFormsSQLiteDao.getFormsJSON();
        Log.e("REOS","FormsRepository-sendForms-listCustomerComplaintFormsEntity.size(): "+listCustomerComplaintFormsEntity.size());
        try {
            if (listCustomerComplaintFormsEntity != null && listCustomerComplaintFormsEntity.size() > 0) {
                json = gson.toJson(listCustomerComplaintFormsEntity);
                json = "{ \"Formulario\":" + json + "}";
            }
            Log.e("REOS","FormsRepository-sendForms-json: "+json);
            RequestBody jsonConvert = RequestBody.create(json,MediaType.parse("application/json; charset=utf-8"));
            Log.e("REOS","FormsRepository-sendForms-jsonConvert: "+jsonConvert);
            if (jsonConvert != null) {
                //RequestBody jsonRequest = RequestBody.create(jsonConvert, MediaType.parse("application/json; charset=utf-8"));
                Config.getClient().create(Api.class).sendForms(jsonConvert).enqueue(new Callback<CustomerComplaintFormsEntity>() {
                    @Override
                    public void onResponse(Call<CustomerComplaintFormsEntity> call, Response<CustomerComplaintFormsEntity> response) {

                        CustomerComplaintFormsEntity cobranzaDetalleEntity = response.body();
                        Log.e("REOS","FormsRepository-sendForms-response: "+response.toString());
                        if (response.isSuccessful() && cobranzaDetalleEntity != null) {
                            ArrayList<String> responseData = new ArrayList<>();
                            callback.onResponseSap(responseData);
                        } else {
                            callback.onResponseErrorSap(response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<CustomerComplaintFormsEntity> call, Throwable t) {
                        callback.onResponseErrorSap(t.getMessage());
                        call.cancel();
                        Log.e("REOS","FormsRepository-sendForms-onFailure: "+t.toString());
                    }
                });
            } else {
                callback.onResponseErrorSap("No hay recibos sin depositar pendientes de enviar");
            }
        }catch (Exception e)
        {
            Log.e("REOS","FormsRepository-sendForms-error: "+e.toString());
        }
    }
}

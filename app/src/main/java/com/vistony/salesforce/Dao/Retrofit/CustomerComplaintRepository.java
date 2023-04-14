package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CustomerComplaintFormsEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.CustomerComplaintSectionEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerComplaintRepository  extends ViewModel {
    private MutableLiveData<CustomerComplaintFormsEntity> status= new MutableLiveData<>();

    public MutableLiveData<CustomerComplaintFormsEntity> getCustomerComplaint
            (){
        status= new MutableLiveData<>();
        Config.getClient().create(Api.class).getCustomerComplaint(
                //Imei,CardCode
        ).enqueue(new Callback<CustomerComplaintFormsEntityResponse>() {

            @Override
            public void onResponse(Call<CustomerComplaintFormsEntityResponse> call, Response<CustomerComplaintFormsEntityResponse> response) {
                try {
                    Log.e("REOS", "CustomerComplaintRepository.getCustomerComplaint.call:" + call.toString());
                    CustomerComplaintFormsEntityResponse customerComplaintFormsEntity = response.body();
                    Log.e("REOS", "CustomerComplaintRepository.getCustomerComplaint.response:" + response.toString());
                    if (response.isSuccessful()
                           // && customerComplaintFormsEntity.getListCustomerComplaintSection()
                           // .size() > 0
                    ) {
                        Log.e("REOS", "CustomerComplaintRepository.getCustomerComplaint.Ingreso:");
                        //Log.e("REOS","KardexPagoRepository.getKardexPago.kardexPagoEntityResponse.getKardexPagoEntity():"+customerComplaintSectionEntityResponse.getCustomerComplaintSectionEntityResponse().size());
                        //kardexPagoPDF.generarPdf(context, kardexPagoEntityResponse.getKardexPagoEntity());
                        status.setValue(customerComplaintFormsEntity.getCustomerComplaintFormsEntityResponse());
                    } else {
                        status.setValue(null);
                    }

                }catch (Exception e){
                    Log.e("REOS", "CustomerComplaintRepository.getCustomerComplaint.error:"+e.toString());
                }

            }

            @Override
            public void onFailure(Call<CustomerComplaintFormsEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }

}

package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotationEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SalesOrderEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricSalesAnalysisByRouteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.QuotationEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SalesOrderEntityResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuotationRepository  extends ViewModel {
    private MutableLiveData<List<QuotationEntity>> status= new MutableLiveData<>();
    private MutableLiveData<List<SalesOrderEntity>> statusPost= new MutableLiveData<>();

    public MutableLiveData<List<QuotationEntity>> getHistoricQuotation(String Imei, String Day){
        status= new MutableLiveData<>();
        String  json=null;
        if(Imei!=null && Day!=null){

            json = "{ \"Fecha\":\"" + Day + "\",\"Imei\":\""+Imei+"\" }";
        }
        RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        Config.getClient().create(Api.class).geHistoricQuotation(jsonRequest).enqueue(new Callback<QuotationEntityResponse>() {
            @Override
            public void onResponse(Call<QuotationEntityResponse> call, Response<QuotationEntityResponse> response) {
                Log.e("REOS","QuotationRepository.getHistoricQuotation.call:" + call.toString());
                QuotationEntityResponse quotationEntityResponse=response.body();
                Log.e("REOS","QuotationRepository.getHistoricQuotation.response:" + response.toString());
                if(response.isSuccessful() && quotationEntityResponse.getQuotationEntity() .size()>0){
                    Log.e("REOS","QuotationRepository.getHistoricQuotation.Ingreso:");
                    //Log.e("REOS","KardexPagoRepository.getKardexPago.kardexPagoEntityResponse.getKardexPagoEntity():"+kardexPagoEntityResponse.getKardexPagoEntity().size());
                    //kardexPagoPDF.generarPdf(context, kardexPagoEntityResponse.getKardexPagoEntity());
                    status.setValue(quotationEntityResponse.getQuotationEntity());
                }else
                {
                    status.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<QuotationEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }

    public MutableLiveData<List<SalesOrderEntity>> sendQuotation(
            String Imei,
            String DocEntry,
            Context context

    ){
        statusPost= new MutableLiveData<>();
        String  json=null;
        Log.e("REOS","QuotationRepository.sendQuotation.Imei:" + Imei);
        Log.e("REOS","QuotationRepository.sendQuotation.DocEntry:" + DocEntry);
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        String AndroidVersion = android.os.Build.VERSION.RELEASE;
        if(Imei!=null && DocEntry!=null){
            json = "{ \"OsVersion\":\"" + AndroidVersion + "\",\"AppVersion\":\""+ Utilitario.getVersion(context)+"\" ,\"ModelDevice\":\""+modelo+"\",\"Brand\":\""+fabricante+"\"}";
        }

        RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
        int docentry=Integer.parseInt(DocEntry);
        Config.getClient().create(Api.class).sendQuotation(docentry,jsonRequest).enqueue(new Callback<SalesOrderEntityResponse>() {
            @Override
            public void onResponse(Call<SalesOrderEntityResponse> call, Response<SalesOrderEntityResponse> response) {
                Log.e("REOS","QuotationRepository.sendQuotation.call:" + call.toString());
                SalesOrderEntityResponse salesOrderEntityResponse=response.body();
                Log.e("REOS","QuotationRepository.sendQuotation.response:" + response.toString());
                if(response.code()==200&& salesOrderEntityResponse.getSalesOrderEntity().size()>0){
                    Log.e("REOS","QuotationRepository.sendQuotation.Ingreso:");
                    statusPost.setValue(salesOrderEntityResponse.getSalesOrderEntity());
                }else
                {
                    statusPost.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<SalesOrderEntityResponse> call, Throwable t) {
                statusPost.setValue(null);
            }
        });
        return statusPost;
    }
}

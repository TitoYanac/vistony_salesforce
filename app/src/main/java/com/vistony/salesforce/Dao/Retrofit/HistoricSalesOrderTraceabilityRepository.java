package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.KardexPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricSalesOrderTraceabilityEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricSalesOrderTraceabilityRepository {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<List<HistoricSalesOrderTraceabilityEntity>> status= new MutableLiveData<>();


    public MutableLiveData<List<HistoricSalesOrderTraceabilityEntity>> getHistoricSalesOrderTraceabilityRepository(String Imei, String Date){

        Config.getClient().create(Api.class).geHistoricSalesOrderTraceability(Imei,Date).enqueue(new Callback<HistoricSalesOrderTraceabilityEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricSalesOrderTraceabilityEntityResponse> call, Response<HistoricSalesOrderTraceabilityEntityResponse> response) {
                Log.e("REOS","KardexPagoRepository.getKardexPago.call:" + call.toString());
                KardexPagoPDF kardexPagoPDF=new KardexPagoPDF();
                HistoricSalesOrderTraceabilityEntityResponse historicSalesOrderTraceabilityEntityResponse=response.body();
                Log.e("REOS","KardexPagoRepository.getKardexPago.response:" + response.toString());
                if(response.isSuccessful() && historicSalesOrderTraceabilityEntityResponse.getHistoricSalesOrderTraceability().size()>0){
                    Log.e("REOS","KardexPagoRepository.getKardexPago.Ingreso:");
                    //Log.e("REOS","KardexPagoRepository.getKardexPago.kardexPagoEntityResponse.getKardexPagoEntity():"+kardexPagoEntityResponse.getKardexPagoEntity().size());
                    //kardexPagoPDF.generarPdf(context, kardexPagoEntityResponse.getKardexPagoEntity());
                    status.setValue(historicSalesOrderTraceabilityEntityResponse.getHistoricSalesOrderTraceability());
                }else
                {
                    status.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<HistoricSalesOrderTraceabilityEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }
}

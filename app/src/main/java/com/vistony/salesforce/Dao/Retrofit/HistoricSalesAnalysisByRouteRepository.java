package com.vistony.salesforce.Dao.Retrofit;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricSalesAnalysisByRouteEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricSalesOrderTraceabilityEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricSalesAnalysisByRouteRepository extends ViewModel {
    private MutableLiveData<List<HistoricSalesAnalysisByRouteEntity>> status= new MutableLiveData<>();


    public MutableLiveData<List<HistoricSalesAnalysisByRouteEntity>> getHistoricSalesAnalysisByRoute(String Imei, String Day){

        Config.getClient().create(Api.class).geHistoricSalesAnalysisByRoute(Imei,Day).enqueue(new Callback<HistoricSalesAnalysisByRouteEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricSalesAnalysisByRouteEntityResponse> call, Response<HistoricSalesAnalysisByRouteEntityResponse> response) {
                Log.e("REOS","KardexPagoRepository.getKardexPago.call:" + call.toString());
                KardexPagoPDF kardexPagoPDF=new KardexPagoPDF();
                HistoricSalesAnalysisByRouteEntityResponse historicSalesAnalysisByRouteEntityResponse=response.body();
                Log.e("REOS","KardexPagoRepository.getKardexPago.response:" + response.toString());
                if(response.isSuccessful() && historicSalesAnalysisByRouteEntityResponse.getHistoricSalesAnalysisByRoute().size()>0){
                    Log.e("REOS","KardexPagoRepository.getKardexPago.Ingreso:");
                    //Log.e("REOS","KardexPagoRepository.getKardexPago.kardexPagoEntityResponse.getKardexPagoEntity():"+kardexPagoEntityResponse.getKardexPagoEntity().size());
                    //kardexPagoPDF.generarPdf(context, kardexPagoEntityResponse.getKardexPagoEntity());
                    status.setValue(historicSalesAnalysisByRouteEntityResponse.getHistoricSalesAnalysisByRoute());
                }else
                {
                    status.setValue(null);
                }
            }
            @Override
            public void onFailure(Call<HistoricSalesAnalysisByRouteEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }
}

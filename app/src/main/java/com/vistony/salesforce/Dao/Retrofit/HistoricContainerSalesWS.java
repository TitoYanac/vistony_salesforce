package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricContainerSalesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TerminoPagoEntityResponse;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricContainerSalesWS {
    private List<HistoricContainerSalesEntity> ListHistoricContainerSalesEntity =  new ArrayList<>();
    private Context context;

    public HistoricContainerSalesWS (final Context context){
        this.context=context;
    }

    public List<HistoricContainerSalesEntity> getHistoricContainerSalesEntityws(
            String Imei,
            Context context,
            String CardCode,
            String FechaIni,
            String FechFin,
            String Variable,
            String Tipo

    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricContainerSalesEntityResponse> call = api.getHistoricContainerSales("01",Imei,Variable,CardCode,FechaIni,FechFin);

        try
        {
            Response<HistoricContainerSalesEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricContainerSalesEntityResponse historicContainerSalesEntityResponse=response.body();
                if(response.isSuccessful() && historicContainerSalesEntityResponse.getHistoricContainerSales().size()>0){

                    ListHistoricContainerSalesEntity=(historicContainerSalesEntityResponse.getHistoricContainerSales());

                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return ListHistoricContainerSalesEntity;
    }
}

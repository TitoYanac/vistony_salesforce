package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ComissionsSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PriceListSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ComisionesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricContainerSalesEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComissionsRepository  extends ViewModel {
    private ComissionsSQLiteDao comissionsSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String>  getCommissions(String Imei, Context context, String FuerzaTrabajo_ID, String FechaIni, String FechFino){

        Config.getClient().create(Api.class).getComisiones(
                Imei,
                FuerzaTrabajo_ID,
                FechaIni,
                FechFino
        ).enqueue(new Callback<ComisionesEntityResponse>() {
            @Override
            public void onResponse(Call<ComisionesEntityResponse> call, Response<ComisionesEntityResponse> response) {
                Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-call"+call);
                Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-response"+response);
                ComisionesEntityResponse comisionesEntityResponse=response.body();
                //Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-historicContainerSalesEntityResponse"+historicContainerSalesEntityResponse);
                if(response.isSuccessful() && comisionesEntityResponse.getComisionesEntity().size()>0){
                    comissionsSQLiteDao = new ComissionsSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    comissionsSQLiteDao.ClearCommissions();
                    comissionsSQLiteDao.InsertComissions(comisionesEntityResponse.getComisionesEntity());
                    Integer countBank=comissionsSQLiteDao.getCountCommissions();
                    parametrosSQLite.ActualizaCantidadRegistros("19", "COMISSION", ""+countBank, getDateTime());

                }
                status.setValue("1");
            }

            @Override
            public void onFailure(Call<ComisionesEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

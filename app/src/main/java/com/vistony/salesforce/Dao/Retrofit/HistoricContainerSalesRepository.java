package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricContainerSalesEntityResponse;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.View.HistoricContainerSaleFocoView;
import com.vistony.salesforce.View.HistoricContainerSalesFamilyView;
import com.vistony.salesforce.View.HistoricContainerSalesSemaforoView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoricContainerSalesRepository  extends ViewModel {
    private HistoricContainerSalesEntity historicContainerSalesEntity;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<List<HistoricContainerSalesEntity>> status= new MutableLiveData<>();


    public MutableLiveData<List<HistoricContainerSalesEntity>> getHistoricContainerSales(String Imei, Context context, String CardCode, String FechaIni, String FechFin, String Variable, String Tipo){

        Config.getClient().create(Api.class).getHistoricContainerSales(Imei,Variable,CardCode,FechaIni,FechFin).enqueue(new Callback<HistoricContainerSalesEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricContainerSalesEntityResponse> call, Response<HistoricContainerSalesEntityResponse> response) {
                Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-call"+call);
                Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-response"+response);
                HistoricContainerSalesEntityResponse historicContainerSalesEntityResponse=response.body();
                Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-historicContainerSalesEntityResponse"+historicContainerSalesEntityResponse);
                if(response.isSuccessful() && historicContainerSalesEntityResponse.getHistoricContainerSales().size()>0){
                    /*Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-Variable:"+Variable);
                    Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-Tipo:"+Tipo);
                    if(Tipo.equals("SEMAFORO"))
                    {
                        Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-IngresoIF-SEMAFORO");
                    HistoricContainerSalesSemaforoView.newInstanceListarClienteSemaforo(historicContainerSalesEntityResponse.getHistoricContainerSales());
                    }
                    else if(Tipo.equals("FAMILIA"))
                    {
                        Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-IngresoIF-FAMILIA");
                        HistoricContainerSalesFamilyView.newInstanceListarClienteFamily(historicContainerSalesEntityResponse.getHistoricContainerSales());
                    }
                    else if(Tipo.equals("FOCO"))
                    {
                        Log.e("REOS","HistoricContainerSalesRepository-getHistoricContainerSales-IngresoIF-FOCO");
                        HistoricContainerSaleFocoView.newInstanceListarClienteFoco(historicContainerSalesEntityResponse.getHistoricContainerSales());
                    }*/
                    status.setValue(historicContainerSalesEntityResponse.getHistoricContainerSales());
                }

            }

            @Override
            public void onFailure(Call<HistoricContainerSalesEntityResponse> call, Throwable t) {
                status.setValue(null);
            }
        });
        return status;
    }
}

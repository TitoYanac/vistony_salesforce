package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.ResumenDiarioPDF;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricContainerSalesEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ResumenDiarioEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumenDiarioRepository extends ViewModel {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<List<HistoricContainerSalesEntity>> status= new MutableLiveData<>();


    public MutableLiveData <List<HistoricContainerSalesEntity>> getResumenDiario(
            String company,
            String Imei,
            String fechaini,
            Context context,
            String fechafin

    ){

        Config.getClient().create(Api.class).getDailySummary(Imei,fechaini,fechafin).enqueue(new Callback<HistoricContainerSalesEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricContainerSalesEntityResponse> call, Response<HistoricContainerSalesEntityResponse> response) {

                HistoricContainerSalesEntityResponse historicContainerSalesEntityResponse=response.body();
                Log.e("REOS","ResumenDiarioRepository-getResumenDiario-call"+call);
                Log.e("REOS","ResumenDiarioRepository-getResumenDiario-response"+response);
                if(response.isSuccessful() && historicContainerSalesEntityResponse.getHistoricContainerSales() .size()>0){
                    for(int i=0;i<historicContainerSalesEntityResponse.getHistoricContainerSales().size();i++)
                    {
                        historicContainerSalesEntityResponse.getHistoricContainerSales().get(i).setFechasap(fechaini);

                    }
                    status.setValue(historicContainerSalesEntityResponse.getHistoricContainerSales());
                }else
                    {
                        status.setValue(null);
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

package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.KardexPagoPDF;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KardexPagoRepository  extends ViewModel {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getKardexPago(String Imei,String CardCode, Context context){

        Config.getClient().create(Api.class).getKardexPago(CardCode).enqueue(new Callback<KardexPagoEntityResponse>() {
            @Override
            public void onResponse(Call<KardexPagoEntityResponse> call, Response<KardexPagoEntityResponse> response) {
                Log.e("REOS","KardexPagoRepository.getKardexPago.call:" + call.toString());
                KardexPagoPDF kardexPagoPDF=new KardexPagoPDF();
                KardexPagoEntityResponse kardexPagoEntityResponse=response.body();
                Log.e("REOS","KardexPagoRepository.getKardexPago.response:" + response.toString());
                if(response.isSuccessful() && kardexPagoEntityResponse.getKardexPagoEntity().size()>0){
                    Log.e("REOS","KardexPagoRepository.getKardexPago.Ingreso:");
                    Log.e("REOS","KardexPagoRepository.getKardexPago.kardexPagoEntityResponse.getKardexPagoEntity():"+kardexPagoEntityResponse.getKardexPagoEntity().size());
                    kardexPagoPDF.generarPdf(context, kardexPagoEntityResponse.getKardexPagoEntity());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<KardexPagoEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountBank(Context context){
        return bancoSQLite.ObtenerCantidadBancos();
    }
}

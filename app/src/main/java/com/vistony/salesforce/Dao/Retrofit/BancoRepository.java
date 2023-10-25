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
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.R;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BancoRepository extends ViewModel {
    private BancoSQLite bancoSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getAndInsertBank(String Imei, Context context, Executor executor){

        Config.getClient().create(Api.class).getBanco(Imei).enqueue(new Callback<BancoEntityResponse>() {
            @Override
            public void onResponse(Call<BancoEntityResponse> call, Response<BancoEntityResponse> response) {

                BancoEntityResponse bancosList=response.body();

                if(response.isSuccessful() && bancosList.getBancoEntity()!=null){
                    executor.execute(() -> {
                    bancoSQLite = new BancoSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    bancoSQLite.LimpiarTablaBanco();
                    bancoSQLite.InsertaBanco(bancosList.getBancoEntity());
                    Integer countBank=getCountBank(context);
                    parametrosSQLite.ActualizaCantidadRegistros("2", context.getResources().getString(R.string.banks), ""+countBank, getDateTime());
                    });
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<BancoEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountBank(Context context){
        return bancoSQLite.ObtenerCantidadBancos();
    }
}

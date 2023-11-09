package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PriceListHeadSQLite;
import com.vistony.salesforce.Dao.SQLite.PriceListSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PriceListEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PriceListHeadEntityResponse;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceListHeadRepository extends ViewModel {
    private PriceListHeadSQLite priceListHeadSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getAddAllPriceListHead(String Imei, Context context,Executor executor){

        Config.getClient().create(Api.class).getPriceListHead(Imei).enqueue(new Callback<PriceListHeadEntityResponse>() {
            @Override
            public void onResponse(Call<PriceListHeadEntityResponse> call, Response<PriceListHeadEntityResponse> response) {

                PriceListHeadEntityResponse priceListHeadEntityResponse=response.body();

                if(response.isSuccessful() && priceListHeadEntityResponse.getPriceListHeadEntity()!=null){
                    executor.execute(() -> {
                    priceListHeadSQLite = new PriceListHeadSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    priceListHeadSQLite.ClearPriceListHead();
                    priceListHeadSQLite.addPriceListHead(priceListHeadEntityResponse.getPriceListHeadEntity());
                    Integer countBank=getCountPriceListHead(context);
                    parametrosSQLite.ActualizaCantidadRegistros("27", "LISTA PRECIO CABECERA", ""+countBank, getDateTime());
                    });
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<PriceListHeadEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountPriceListHead(Context context){
        return priceListHeadSQLite.getCountPriceListHead();
    }
}

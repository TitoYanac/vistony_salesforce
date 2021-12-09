package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PriceListSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PriceListEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceListRepository extends ViewModel {
    private PriceListSQLiteDao priceListSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getAddAllPriceList(String Imei, Context context){

        Config.getClient().create(Api.class).getPriceList(Imei).enqueue(new Callback<PriceListEntityResponse>() {
            @Override
            public void onResponse(Call<PriceListEntityResponse> call, Response<PriceListEntityResponse> response) {

                PriceListEntityResponse priceListEntityResponse=response.body();

                if(response.isSuccessful() && priceListEntityResponse.getPriceListEntity().size()>0){

                    priceListSQLiteDao = new PriceListSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    priceListSQLiteDao.ClearPriceList();
                    priceListSQLiteDao.InsertPriceList(priceListEntityResponse.getPriceListEntity());
                    Integer countBank=getCountPriceList(context);
                    parametrosSQLite.ActualizaCantidadRegistros("18", "PRICE LIST", ""+countBank, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<PriceListEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountPriceList(Context context){
        return priceListSQLiteDao.getCountPriceList();
    }
}

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
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.KardexPagoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.WareHousesEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WareHousesRepository   extends ViewModel {
    private MutableLiveData<List<WareHousesEntity>> wareHousesEntitylist= new MutableLiveData<>();


    public MutableLiveData<List<WareHousesEntity>> getWareHouses(String Imei, Context context,String ItemCode){
        Config.getClient().create(Api.class).getWareHouses(Imei,ItemCode).enqueue(new Callback<WareHousesEntityResponse>() {
            @Override
            public void onResponse(Call<WareHousesEntityResponse> call, Response<WareHousesEntityResponse> response) {

                WareHousesEntityResponse wareHousesEntityResponse=response.body();
                if(response.isSuccessful() && wareHousesEntityResponse.getWareHousesEntities().size()>0){
                    wareHousesEntitylist.setValue(wareHousesEntityResponse.getWareHousesEntities());
                }else
                {
                    wareHousesEntitylist.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<WareHousesEntityResponse> call, Throwable t) {
                wareHousesEntitylist=null;
            }
        });
        return wareHousesEntitylist;
    }
}

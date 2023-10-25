package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerDetailSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerHeadSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BusinessLayerEntityResponse;
import com.vistony.salesforce.R;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessLayerRepository extends ViewModel {
    private BusinessLayerHeadSQLiteDao businessLayerHeadSQLiteDao;
    private BusinessLayerDetailSQLiteDao businessLayerDetailSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getBussinessLayer(String Imei, Context context, Executor executor){

        Config.getClient().create(Api.class).getBusinessLayer(Imei).enqueue(new Callback<BusinessLayerEntityResponse>() {
            @Override
            public void onResponse(Call<BusinessLayerEntityResponse> call, Response<BusinessLayerEntityResponse> response) {

                BusinessLayerEntityResponse businessLayerEntityResponse=response.body();

                if(response.isSuccessful() && businessLayerEntityResponse.getBusinessLayerEntityResponse()!=null){
                    executor.execute(() -> {
                    businessLayerHeadSQLiteDao = new BusinessLayerHeadSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    businessLayerDetailSQLiteDao = new BusinessLayerDetailSQLiteDao(context);
                    businessLayerHeadSQLiteDao.clearTableBusinessLayerHead();
                    businessLayerDetailSQLiteDao.clearTableBusinessLayerDetail();
                    businessLayerHeadSQLiteDao.addBusinessLayer(businessLayerEntityResponse.getBusinessLayerEntityResponse());
                    Integer countBussinessLayer=getBussinessLayer(context);
                    parametrosSQLite.ActualizaCantidadRegistros("28", context.getResources().getString(R.string.busines_layer), ""+countBussinessLayer, getDateTime());
                    });
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<BusinessLayerEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getBussinessLayer(Context context){
        return businessLayerHeadSQLiteDao.getCountBusinessLayerHead();
    }
}

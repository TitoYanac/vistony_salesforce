package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ObjectSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.WarehouseSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ObjectEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.WarehouseEntityResponse;
import com.vistony.salesforce.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WarehouseRepository  extends ViewModel {
    private WarehouseSQLiteDao warehouseSQLiteDao;
    private MutableLiveData<String> status= new MutableLiveData<>();
    private ParametrosSQLite parametrosSQLite;

    public MutableLiveData<String> getWarehouse(String Imei, Context context){
        warehouseSQLiteDao=new WarehouseSQLiteDao(context);
        parametrosSQLite=new ParametrosSQLite(context);
        Config.getClient().create(Api.class).getWarehouse(Imei).enqueue(new Callback<WarehouseEntityResponse>() {
            @Override
            public void onResponse(Call<WarehouseEntityResponse> call, Response<WarehouseEntityResponse> response) {

                WarehouseEntityResponse warehouseEntityResponse=response.body();

                if(response.isSuccessful() && warehouseEntityResponse.getWarehouseEntityResponse()!=null){
                    warehouseSQLiteDao.clearTableWarehouse();
                    warehouseSQLiteDao.addWarehouse(warehouseEntityResponse.getWarehouseEntityResponse());
                    Integer countObjects=getCountWarehouse(context);
                    parametrosSQLite.ActualizaCantidadRegistros("31", context.getResources().getString(R.string.warehouse), ""+countObjects, getDateTime());

                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<WarehouseEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountWarehouse(Context context){
        return warehouseSQLiteDao.getCountWareHouse();
    }
}

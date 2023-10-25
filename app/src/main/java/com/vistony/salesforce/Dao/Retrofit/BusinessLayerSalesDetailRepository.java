package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerDetailSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerHeadSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerSalesDetailDetailDao;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerSalesDetailHeaderDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BusinessLayerEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BusinessLayerSalesDetailEntityResponse;
import com.vistony.salesforce.R;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessLayerSalesDetailRepository extends ViewModel {

    private BusinessLayerSalesDetailHeaderDao businessLayerSalesDetailHeaderDao;
    private BusinessLayerSalesDetailDetailDao businessLayerSalesDetailDetailDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getBussinessLayerSalesDetail(String Imei, Context context, Executor executor){

        Config.getClient().create(Api.class).getBusinessLayerSalesDetail(Imei).enqueue(new Callback<BusinessLayerSalesDetailEntityResponse>() {
            @Override
            public void onResponse(Call<BusinessLayerSalesDetailEntityResponse> call, Response<BusinessLayerSalesDetailEntityResponse> response) {

                BusinessLayerSalesDetailEntityResponse businessLayerSalesDetailEntityResponse=response.body();

                if(response.isSuccessful() && businessLayerSalesDetailEntityResponse.getBusinessLayerSalesDetailEntityResponse()!=null){
                    executor.execute(() -> {
                    businessLayerSalesDetailHeaderDao = new BusinessLayerSalesDetailHeaderDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    businessLayerSalesDetailDetailDao = new BusinessLayerSalesDetailDetailDao(context);
                    businessLayerSalesDetailHeaderDao.clearTableBusinessLayerSalesDetailHeader();
                    businessLayerSalesDetailDetailDao.clearTableBusinessLayerSalesDetailDetail();
                    businessLayerSalesDetailHeaderDao.addBusinessLayerSalesOrder(businessLayerSalesDetailEntityResponse.getBusinessLayerSalesDetailEntityResponse());
                    Integer countBussinessLayer=getBussinessLayerSalesDetail(context);
                    parametrosSQLite.ActualizaCantidadRegistros("30", context.getResources().getString(R.string.busines_layer_sales_detail), ""+countBussinessLayer, getDateTime());
                    });
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<BusinessLayerSalesDetailEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getBussinessLayerSalesDetail(Context context){
        return businessLayerSalesDetailHeaderDao.getCountBusinessLayerSalesDetailHeader();
    }
}

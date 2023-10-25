package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.SellerRouteSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SellerRouteEntityResponse;
import com.vistony.salesforce.R;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRouteRepository  extends ViewModel {
    private SellerRouteSQLiteDao sellerRouteSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getAddSellerRoute(String Imei, String fecha, Context context, Executor executor){

        Config.getClient().create(Api.class).getSellerRoute(Imei,fecha).enqueue(new Callback<SellerRouteEntityResponse>() {
            @Override
            public void onResponse(Call<SellerRouteEntityResponse> call, Response<SellerRouteEntityResponse> response) {

                SellerRouteEntityResponse sellerRouteEntityResponse=response.body();

                if(response.isSuccessful() && sellerRouteEntityResponse.getSellerRouteEntity()!=null){
                    executor.execute(() -> {
                    sellerRouteSQLiteDao = new SellerRouteSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    sellerRouteSQLiteDao.ClearTableSellerRoute();
                    sellerRouteSQLiteDao.addSellerRoute(sellerRouteEntityResponse.getSellerRouteEntity());
                    Integer countSellerRoute=getCountSellerRoute(context);
                    parametrosSQLite.ActualizaCantidadRegistros("32", context.getResources().getString(R.string.seller_route), ""+countSellerRoute, getDateTime());
                    });
                }
                status.setValue("1");
            }

            @Override
            public void onFailure(Call<SellerRouteEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountSellerRoute(Context context){
        return sellerRouteSQLiteDao.getCountSellerRoute();
    }
}

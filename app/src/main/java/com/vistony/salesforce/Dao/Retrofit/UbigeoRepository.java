package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.TypeDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UbigeoSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TypeDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.UbigeoEntityResponse;
import com.vistony.salesforce.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UbigeoRepository  extends ViewModel {
    private MutableLiveData<String> status= new MutableLiveData<>();
    UbigeoSQLiteDao ubigeoSQLiteDao;
    ParametrosSQLite parametrosSQLite;

    public MutableLiveData<String> geUbigeo (String Imei, Context context){
        Config.getClient().create(Api.class).getUbigeo(Imei).enqueue(new Callback<UbigeoEntityResponse>() {
            @Override
            public void onResponse(Call<UbigeoEntityResponse> call, Response<UbigeoEntityResponse> response) {

                UbigeoEntityResponse ubigeoEntityResponse=response.body();
                if(response.isSuccessful() && ubigeoEntityResponse.getUbigeoEntity().size()>0){
                    ubigeoSQLiteDao = new UbigeoSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    ubigeoSQLiteDao.LimpiarTablaUbigeo ();
                    ubigeoSQLiteDao.addListUbigeo (ubigeoEntityResponse.getUbigeoEntity());
                    Integer countTypeDispatch=getCountUbigeo(context);
                    parametrosSQLite.ActualizaCantidadRegistros("25", context.getResources().getString(R.string.ubigeous).toUpperCase(), ""+countTypeDispatch, getDateTime());

                    status.setValue("1");
                }else
                {
                    status.setValue("0");
                }
            }

            @Override
            public void onFailure(Call<UbigeoEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountUbigeo(Context context){
        return ubigeoSQLiteDao.ObtenerCantidadUbigeo() ;
    }
}

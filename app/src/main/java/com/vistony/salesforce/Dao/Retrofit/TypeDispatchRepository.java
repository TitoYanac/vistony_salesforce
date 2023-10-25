package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.ReasonDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.TypeDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.TypeDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.WareHousesEntityResponse;

import java.util.List;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypeDispatchRepository  extends ViewModel {
    private MutableLiveData<String> status= new MutableLiveData<>();
    TypeDispatchSQLite typeDispatchSQLite;
    ParametrosSQLite parametrosSQLite;

    public MutableLiveData<String> geTypeDispatch (String Imei, Context context, Executor executor){
        Config.getClient().create(Api.class).getTypeDispatch(Imei).enqueue(new Callback<TypeDispatchEntityResponse>() {
            @Override
            public void onResponse(Call<TypeDispatchEntityResponse> call, Response<TypeDispatchEntityResponse> response) {

                TypeDispatchEntityResponse typeDispatchEntityResponse=response.body();
                if(response.isSuccessful() && typeDispatchEntityResponse.getTypeDispatchEntities().size()>0){
                    executor.execute(() -> {
                    typeDispatchSQLite = new TypeDispatchSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    typeDispatchSQLite.DeleteTableTypeDispatch();
                    typeDispatchSQLite.AddTypeDispatch (typeDispatchEntityResponse.getTypeDispatchEntities());
                    Integer countTypeDispatch=getCountTypeDispatch(context);
                    parametrosSQLite.ActualizaCantidadRegistros("23", "TIPO DESPACHO", ""+countTypeDispatch, getDateTime());
                    status.setValue("1");
                    });
                }else
                {
                    status.setValue("0");
                }
            }

            @Override
            public void onFailure(Call<TypeDispatchEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountTypeDispatch(Context context){
        return typeDispatchSQLite.getCountTypeDispatch() ;
    }
}

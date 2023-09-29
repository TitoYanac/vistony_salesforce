package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerHeadSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ObjectSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BusinessLayerEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ObjectEntityResponse;
import com.vistony.salesforce.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectRepository extends ViewModel {
    private ObjectSQLite objectSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getObjectAPI(String Imei, Context context){
        objectSQLite=new ObjectSQLite(context);
        parametrosSQLite=new ParametrosSQLite(context);
        Config.getClient().create(Api.class).getObject(Imei).enqueue(new Callback<ObjectEntityResponse>() {
            @Override
            public void onResponse(Call<ObjectEntityResponse> call, Response<ObjectEntityResponse> response) {

                ObjectEntityResponse objectEntityResponse=response.body();

                if(response.isSuccessful() && objectEntityResponse.getObjectEntityResponse()!=null){
                    objectSQLite.clearTableObject();
                    objectSQLite.addObject(objectEntityResponse.getObjectEntityResponse());
                    Integer countObjects=getCountObject(context);
                    parametrosSQLite.ActualizaCantidadRegistros("29", context.getResources().getString(R.string.objects), ""+countObjects, getDateTime());

                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<ObjectEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountObject(Context context){
        return objectSQLite.getCountObject();
    }
}

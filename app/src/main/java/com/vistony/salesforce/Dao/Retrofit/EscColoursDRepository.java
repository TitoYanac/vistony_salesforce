package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.EscColoursCSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.EscColoursDSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursCEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursDEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscColoursDRepository {
    private EscColoursDSQLiteDao escColoursDSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getEscColoursD(String Imei, Context context){

        Config.getClient().create(Api.class).getScColoursD(Imei).enqueue(new Callback<EscColoursDEntityResponse>() {
            @Override
            public void onResponse(Call<EscColoursDEntityResponse> call, Response<EscColoursDEntityResponse> response) {

                EscColoursDEntityResponse escColoursDEntityResponse=response.body();

                if(response.isSuccessful() && escColoursDEntityResponse.getEscColoursDEntity() .size()>0){

                    escColoursDSQLiteDao = new EscColoursDSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    escColoursDSQLiteDao.ClearEscColoursD() ;
                    escColoursDSQLiteDao.InsertEscColoursD (escColoursDEntityResponse.getEscColoursDEntity());
                    Integer countBank=escColoursDSQLiteDao.getCountEscColoursD();
                    parametrosSQLite.ActualizaCantidadRegistros("19", "EscColoursC", ""+countBank, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<EscColoursDEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

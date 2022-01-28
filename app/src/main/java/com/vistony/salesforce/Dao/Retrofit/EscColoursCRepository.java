package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.EscColoursCSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PriceListSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.EscColoursCEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PriceListEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EscColoursCRepository extends ViewModel {
    private EscColoursCSQLiteDao escColoursCSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getEscColoursC(String Imei, Context context){

        Config.getClient().create(Api.class).getScColoursC(Imei).enqueue(new Callback<EscColoursCEntityResponse>() {
            @Override
            public void onResponse(Call<EscColoursCEntityResponse> call, Response<EscColoursCEntityResponse> response) {

                EscColoursCEntityResponse escColoursCEntityResponse=response.body();

                if(response.isSuccessful() && escColoursCEntityResponse.getEscColoursEntity() .size()>0){

                    escColoursCSQLiteDao = new EscColoursCSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    escColoursCSQLiteDao.ClearEscColoursC() ;
                    escColoursCSQLiteDao.InsertEscColoursC (escColoursCEntityResponse.getEscColoursEntity());
                    Integer countBank=escColoursCSQLiteDao.getCountEscColoursC();
                    parametrosSQLite.ActualizaCantidadRegistros("19", "EscColoursC", ""+countBank, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<EscColoursCEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

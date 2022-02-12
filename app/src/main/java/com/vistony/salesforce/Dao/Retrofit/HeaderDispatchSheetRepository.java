package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderDispatchSheetEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeaderDispatchSheetRepository extends ViewModel {
    private HeaderDispatchSheetSQLite headerDispatchSheetSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getAndInsertHeaderDispatchSheet(
            String Imei
            , String FechaDespacho
            ,Context context
    ){

        Config.getClient().create(Api.class).getHeaderDispatchSheet(
                Imei
                , FechaDespacho
        ).enqueue(new Callback<HeaderDispatchSheetEntityResponse>() {
            @Override
            public void onResponse(Call<HeaderDispatchSheetEntityResponse> call, Response<HeaderDispatchSheetEntityResponse> response) {

                HeaderDispatchSheetEntityResponse headerDispatchSheetEntityResponse=response.body();

                if(response.isSuccessful() && headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().size()>0){

                    headerDispatchSheetSQLite = new HeaderDispatchSheetSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    headerDispatchSheetSQLite.ClearTableHeaderDispatchDate();
                    headerDispatchSheetSQLite.InsertHeaderDispatchSheet(headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity(),FechaDespacho);
                    Integer countHeadDispatchSheet=headerDispatchSheetSQLite.getCountHeaderDispatchSheet();
                    parametrosSQLite.ActualizaCantidadRegistros("2", "BANCOS", ""+countHeadDispatchSheet, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<HeaderDispatchSheetEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

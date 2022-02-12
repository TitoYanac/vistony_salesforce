package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.DetailDispatchSheetEntityResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailDispatchSheetRepository extends ViewModel {
    private DetailDispatchSheetSQLite detailDispatchSheetSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();


    public MutableLiveData<String> getAndInsertDetailDispatchSheet(
            String Imei
            , String DispatchDate
            , Context context
    ){

        Config.getClient().create(Api.class).getDetailDispatchSheet(
                Imei
                , DispatchDate
        ).enqueue(new Callback<DetailDispatchSheetEntityResponse>() {
            @Override
            public void onResponse(Call<DetailDispatchSheetEntityResponse> call, Response<DetailDispatchSheetEntityResponse> response) {

                DetailDispatchSheetEntityResponse detailDispatchSheetEntityResponse=response.body();

                if(response.isSuccessful() && detailDispatchSheetEntityResponse.getDetailDispatchSheetEntityEntity().size()>0){

                    detailDispatchSheetSQLite = new DetailDispatchSheetSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    detailDispatchSheetSQLite.ClearTableDetailDispatchSheet();
                    detailDispatchSheetSQLite.InsertDetailDispatchSheet(detailDispatchSheetEntityResponse.getDetailDispatchSheetEntityEntity(),DispatchDate);
                    Integer countDetailDispatchSheet=detailDispatchSheetSQLite.getCountDetailDispatchSheet();
                    parametrosSQLite.ActualizaCantidadRegistros("2", "BANCOS", ""+countDetailDispatchSheet, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<DetailDispatchSheetEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

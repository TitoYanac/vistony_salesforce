package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderDispatchSheetEntityResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HeaderDispatchSheetRepository extends ViewModel {
    private HeaderDispatchSheetSQLite headerDispatchSheetSQLite;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();
    private DetailDispatchSheetSQLite detailDispatchSheetSQLite;

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
                Log.e("REOS","HeaderDispatchSheetRepository-getAndInsertHeaderDispatchSheet-call"+call.toString());
                HeaderDispatchSheetEntityResponse headerDispatchSheetEntityResponse=response.body();
                Log.e("REOS","HeaderDispatchSheetRepository-getAndInsertHeaderDispatchSheet-response"+response.toString());
                if(response.isSuccessful() && headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().size()>0){
                    Log.e("REOS","HeaderDispatchSheetRepository-getAndInsertHeaderDispatchSheet-headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity()"+headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().toString());

                    headerDispatchSheetSQLite = new HeaderDispatchSheetSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    headerDispatchSheetSQLite.ClearTableHeaderDispatchDate();
                    detailDispatchSheetSQLite = new DetailDispatchSheetSQLite(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    detailDispatchSheetSQLite.ClearTableDetailDispatchSheet();
                    for(int i=0;i< headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().size();i++ )
                    {

                        headerDispatchSheetSQLite.InsertHeaderDispatchSheet(headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity(), FechaDespacho);


                        //Documentos
                        if (headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().get(i).getListDedailDispatch() == null || headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().get(i).getListDedailDispatch().size() == 0) {
                            //ObjCliente.setListInvoice(null);
                        } else {

                            for(int j=0;j<headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().get(i).getListDedailDispatch().size();j++)
                            {
                                headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().get(i).getListDedailDispatch().get(j).setControlid(headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().get(i).getControl_id());
                            }
                            detailDispatchSheetSQLite.InsertDetailDispatchSheet(headerDispatchSheetEntityResponse.getHeaderDispatchSheetEntity().get(i).getListDedailDispatch(),FechaDespacho);

                        }
                    }
                    Integer countHeadDispatchSheet = headerDispatchSheetSQLite.getCountHeaderDispatchSheet();
                    parametrosSQLite.ActualizaCantidadRegistros("19", "HOJA DESPACHO", "" + countHeadDispatchSheet, getDateTime());
                    Integer countDetailDispatchSheet=detailDispatchSheetSQLite.getCountDetailDispatchSheet();
                    parametrosSQLite.ActualizaCantidadRegistros("20", "HOJA DESPACHO DETALLE", ""+countDetailDispatchSheet, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<HeaderDispatchSheetEntityResponse> call, Throwable t) {
                status.setValue("0");
                Log.e("REOS","HeaderDispatchSheetRepository-getAndInsertHeaderDispatchSheet-error"+t.toString());
            }
        });
        return status;
    }
}

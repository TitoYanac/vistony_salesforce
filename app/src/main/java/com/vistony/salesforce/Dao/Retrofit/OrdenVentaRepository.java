package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SalesOrderEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.SalesOrderEntityResponse;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdenVentaRepository extends ViewModel {

    private  OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite;

    public MutableLiveData<String> sendSalesOrder(final String orderId, final Context context) {
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendSalesOrder(orderId,context, new SalesOrderCallback(){
            @Override
            public void onResponseSap(SalesOrderEntityResponse data) {
                //Solo devolveremos uno, el envio masivo es trasnaprente
                if(data.getSalesOrderEntity().get(0).getDocNum()==null){
                    if(data.getSalesOrderEntity().get(0).getMessage().equals("Documento creado con flujo de aprobaciÃ³n")){
                        temp.setValue(data.getSalesOrderEntity().get(0).getMessage());
                    }else{
                        temp.setValue("Ocurrio un error al enviar la orden de venta");
                    }
                }else{
                    temp.setValue("La orden de venta "+data.getSalesOrderEntity().get(0).getDocNum()+", fue registrada con éxito");
                }
            }

            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });

        return temp;
    }

    public MutableLiveData<String> salesOrderResend(Context context){
        MutableLiveData<String> temp=new MutableLiveData<String>();

        sendSalesOrder(null,context, new SalesOrderCallback(){
            @Override
            public void onResponseSap(SalesOrderEntityResponse data) {
                if(data==null){
                    temp.setValue("No hay ordenes de venta pendientes de enviar");
                }else{
                    Log.e("JPCM","=>"+data.getSalesOrderEntity().get(0).getMessage());
                    if(data.getSalesOrderEntity().get(0).getDocNum()==null){
                        if(data.getSalesOrderEntity().get(0).getMessage().equals("document created with approval flow")){
                            temp.setValue(data.getSalesOrderEntity().get(0).getMessage());
                        }else{
                            temp.setValue("Ocurrio un error al enviar la orden de venta");
                        }
                    }else{
                        //Solo devolveremos uno, el envio masivo es trasnaprente
                        temp.setValue("La orden de venta "+data.getSalesOrderEntity().get(0).getDocNum()+", fue registrada con éxito");
                    }
                }
            }
            @Override
            public void onResponseErrorSap(String response) {
                temp.setValue(response);
            }
        });
        return temp;
    }

    private String EnviarNubeOV(String ordenventa_id,Context context){
        FormulasController formulasController=new FormulasController(context);
        return formulasController.GenerayConvierteaJSONOV(ordenventa_id,context)+",";
    }

    private void sendSalesOrder(final String idSalesOrder, final Context context,final SalesOrderCallback callback){

        String json="";
        if(ordenVentaCabeceraSQLite==null){
            ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(context);
        }

        if(idSalesOrder==null){
            ArrayList<String> listSalesOrders = new ArrayList<>();
            listSalesOrders=ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraPendientesEnvioWS();

            if(listSalesOrders!=null && listSalesOrders.size()>0) {
                for (int z = 0; z < listSalesOrders.size(); z++) {
                    String ordenVentaId = listSalesOrders.get(z);
                    json = json + EnviarNubeOV(ordenVentaId, context);
                }

            }
        }else{
            json = EnviarNubeOV(idSalesOrder, context);
        }

        if(json.isEmpty()){
            callback.onResponseSap(null);
        }else{
            json = "{ \"SalesOrders\":[" + json + "]}";

            RequestBody jsonConvert = RequestBody.create(json,MediaType.parse("application/json; charset=utf-8"));

            Config.getClient().create(Api.class).sendOrder("http://169.47.196.209/cl/api/SalesOrder",jsonConvert).enqueue(new Callback<SalesOrderEntityResponse>() {
                @Override
                public void onResponse(Call<SalesOrderEntityResponse> call, Response<SalesOrderEntityResponse> response) {

                    SalesOrderEntityResponse salesOrderEntityResponse=response.body();

                    if(response.isSuccessful() && salesOrderEntityResponse!=null){

                        for (SalesOrderEntity data:salesOrderEntityResponse.getSalesOrderEntity()) {

                            String statusSend="0";
                            if(data.getDocEntry()==null){
                                if(data.getMessage().equals("document created with approval flow")){
                                    statusSend="1"; //set 1 por que si llego pero al estar en el flujo de aprovacion y no hay docEntry aun
                                }
                            }

                            /*
                            ordenVentaCabeceraSQLite.ActualizaResultadoOVenviada(
                                data.getSalesOrderID(),
                                statusSend,
                                data.getDocNum(),
                                data.getMessage()
                            );
                            */

                        }

                        callback.onResponseSap(salesOrderEntityResponse);
                    }else{
                        callback.onResponseErrorSap("Error "+response.code()+", "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<SalesOrderEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }

    }
}

interface SalesOrderCallback {
    void onResponseSap(SalesOrderEntityResponse response);
    void onResponseErrorSap(String response);
}

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

import java.util.ArrayList;

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
            public void onResponseSap(ArrayList<String> data) {
                if(data==null && data.size()==0){
                    temp.setValue("No hay ordenes de venta pendientes de enviar");
                }else{
                    temp.setValue(data.get(0));
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
            public void onResponseSap(ArrayList<String> data) {
                if(data==null){
                    temp.setValue("No hay ordenes de venta pendientes de enviar");
                }else{

                    for(int i=0;i<data.size();i++)
                    {
                        temp.setValue(data.get(i));
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
                for (int i = 0; i < listSalesOrders.size(); i++) {
                    json = json + EnviarNubeOV(/*OrdenVentaId*/listSalesOrders.get(i), context);
                }

            }
        }else{
            json = EnviarNubeOV(idSalesOrder, context);
        }

        if(json.isEmpty()){
            callback.onResponseSap(null);
        }else{
            json = "{ \"SalesOrders\":[" + json + "]}";
            Log.e("REOS","OrdenVentaRepository-sendSalesOrder-json"+json);
            RequestBody jsonConvert = RequestBody.create(json,MediaType.parse("application/json; charset=utf-8"));

            Config.getClient().create(Api.class).sendOrder(jsonConvert).enqueue(new Callback<SalesOrderEntityResponse>() {
                @Override
                public void onResponse(Call<SalesOrderEntityResponse> call, Response<SalesOrderEntityResponse> response) {

                    SalesOrderEntityResponse salesOrderEntityResponse=response.body();

                    if(response.isSuccessful() && salesOrderEntityResponse!=null){
                        ArrayList<String> responseData=new ArrayList<>();
                        //responseData.add("Sin datos");
                        for (SalesOrderEntity respuesta:salesOrderEntityResponse.getSalesOrderEntity()) {
                            if(respuesta.getErrorCode().equals("0")){//se envio
                                if(respuesta.getDocEntry()==null && respuesta.getDocNum()==null){//pasa por flujo de aprobacion
                                    ordenVentaCabeceraSQLite.ActualizaResultadoOVenviada(
                                            respuesta.getSalesOrderID(),
                                            "1",
                                            respuesta.getDocNum(),
                                            respuesta.getMessage()
                                    );
                                    responseData.add("La orden de venta "+respuesta.getSalesOrderID()+", pasara por un flujo de aprobación");
                                }else{//pasa por flujo automatico
                                    ordenVentaCabeceraSQLite.ActualizaResultadoOVenviada(
                                            respuesta.getSalesOrderID(),
                                        "1",
                                            respuesta.getDocNum(),
                                            respuesta.getMessage()
                                    );

                                    responseData.add("La orden de venta "+respuesta.getDocNum()+", fue aceptado en SAP");
                                }
                            }else{//tiene error
                                ordenVentaCabeceraSQLite.ActualizaResultadoOVenviada(
                                        respuesta.getSalesOrderID(),
                                        "0",
                                        respuesta.getDocNum(),
                                        respuesta.getMessage()
                                );
                                responseData.add("La orden de venta "+respuesta.getSalesOrderID()+", tiene un error");
                            }
                        }

                        callback.onResponseSap(responseData);
                    }else{
                        callback.onResponseErrorSap("Error "+response.code()+", "+response.message());
                    }
                }

                @Override
                public void onFailure(Call<SalesOrderEntityResponse> call, Throwable t) {
                    //callback.onResponseErrorSap(t.getMessage());
                    callback.onResponseErrorSap("En estos momentos no tiene Acceso a Internet. Se reenviara su Orden de Venta en el Proximo inicio de Sesion");
                    call.cancel();
                }
            });
        }

    }
}

interface SalesOrderCallback {
    void onResponseSap(ArrayList<String> response);
    void onResponseErrorSap(String response);
}

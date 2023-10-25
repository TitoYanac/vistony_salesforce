package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DetailStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HeaderStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricStatusDispatchEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StatusDispatchEntityResponse;
import com.vistony.salesforce.View.BuscarClienteView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatusDispatchRepository  extends ViewModel {
    private MutableLiveData<List<HistoricStatusDispatchEntity>> ListHistoricStatusDispatchEntity= new MutableLiveData<>();
    StatusDispatchSQLite statusDispatchSQLite;

    public MutableLiveData<List<HistoricStatusDispatchEntity>> getHistoricStatusDispatch(
            String Imei,
            String Date){
        ListHistoricStatusDispatchEntity= new MutableLiveData<>();
        Config.getClient().create(Api.class).geHistoricStatusDispatch(Imei,Date).enqueue(new Callback<HistoricStatusDispatchEntityResponse>() {
            @Override
            public void onResponse(Call<HistoricStatusDispatchEntityResponse> call, Response<HistoricStatusDispatchEntityResponse> response) {
                HistoricStatusDispatchEntityResponse historicStatusDispatchEntityResponse=response.body();
                Log.e("REOS","StatusDispatchRepository-getHistoricStatusDispatch-call: "+call.toString());
                Log.e("REOS","StatusDispatchRepository-getHistoricStatusDispatch-response: "+response.toString());
                if(response.isSuccessful() && historicStatusDispatchEntityResponse.getHistoricStatusDispatch() .size()>0){
                    ListHistoricStatusDispatchEntity.setValue(historicStatusDispatchEntityResponse.getHistoricStatusDispatch());
                }else
                {
                    ListHistoricStatusDispatchEntity.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<HistoricStatusDispatchEntityResponse> call, Throwable t) {
                ListHistoricStatusDispatchEntity=null;
            }
        });
        return ListHistoricStatusDispatchEntity;
    }


    public MutableLiveData<String> statusDispatchListSend(Context context,Executor executor){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        try {
            sendStatusDispatchList(context, new VisitCallback() {
                @Override
                public void onResponseSap(ArrayList<String> data) {
                    if (data == null) {
                        temp.postValue("No hay Estados de Despacho pendientes de Enviar");
                    } else {
                        temp.postValue(data.get(0));
                    }
                }

                @Override
                public void onResponseErrorSap(String response) {
                    temp.postValue(response);
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(context, "Error en Proceso de Envio de Despacho - Error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return temp;
    }

    private void sendStatusDispatchList(final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();

        if(statusDispatchSQLite==null){
            statusDispatchSQLite =new StatusDispatchSQLite(context);
        }

        ArrayList<HeaderStatusDispatchEntity> listStatusDispatch = statusDispatchSQLite.getHeaderListStatusDispatch(context);

        json = gson.toJson(listStatusDispatch);
        json = "{ \"Dispatch\":" + json + "}";

        Log.e("REOS", "StatusDispatchRepository-sendStatusDispatchList-json"+json);
        /*if(listStatusDispatch!=null && listStatusDispatch.size()>0)
        {
            statusDispatchSQLite.UpdatePruebaJSON(json, listStatusDispatch.get(0).getDetails().get(0).getEntrega_id());
        }*/
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendStatusDispatch(jsonRequest).enqueue(new Callback<HeaderStatusDispatchEntityResponse>() {
                @Override
                public void onResponse(Call<HeaderStatusDispatchEntityResponse> call, Response<HeaderStatusDispatchEntityResponse> response) {

                    HeaderStatusDispatchEntityResponse headerStatusDispatchEntityResponse=response.body();

                    if(response.isSuccessful() && headerStatusDispatchEntityResponse!=null){
                        ArrayList<String> responseData=new ArrayList<>();
                        responseData.add("Cargando Lista de Estados de Despachos");
                        for (HeaderStatusDispatchEntity respuesta:headerStatusDispatchEntityResponse.getHeaderStatusDispatchEntityResponse()  )
                        {
                            for(DetailStatusDispatchEntity respuestaDetalle:respuesta.getDetails())
                            {
                                if(respuestaDetalle.getHaveError().equals("N")){//se envio
                                    responseData.add("La Lista Estado de Despacho fue aceptado en SAP");
                                    statusDispatchSQLite.UpdateResultStatusDispatchList(respuesta.getDocEntry(),respuestaDetalle.getLineId(),respuestaDetalle.getMessage());

                                }else{//tiene error
                                    responseData.add("La Lista Estado de Despacho no fue aceptado en SAP");
                                }
                            }
                        }

                        callback.onResponseSap(responseData);
                        callback.onResponseErrorSap(response.message());
                    }else{

                        callback.onResponseErrorSap(response.message());
                    }
                }
                @Override
                public void onFailure(Call<HeaderStatusDispatchEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap("Cayo en Error La Lista:"+t.getMessage());

                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay Lista estados de despachos pendientes de enviar");
        }
    }

    public MutableLiveData<String> statusDispatchSendTime(Context context,Executor executor){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        try {
        executor.execute(() -> {
            sendStatusDispatchTime(context, new VisitCallback() {
                @Override
                public void onResponseSap(ArrayList<String> data) {
                    if (data == null) {
                        temp.postValue("No hay Estados de Despacho con Tiempo pendientes de Enviar");
                    } else {
                        temp.postValue(data.get(0));
                    }
                }

                @Override
                public void onResponseErrorSap(String response) {
                    temp.postValue(response);
                }
            });
        });
        }catch (Exception e)
        {
            Toast.makeText(context, "Error en Proceso de Envio de Despacho Hora- Error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

        return temp;
    }

    private void sendStatusDispatchTime(final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();

        if(statusDispatchSQLite==null){
            statusDispatchSQLite =new StatusDispatchSQLite(context);
        }

        ArrayList<HeaderStatusDispatchEntity> listStatusDispatch = statusDispatchSQLite.getHeaderListStatusDispatchTime(context);
        if(listStatusDispatch!=null && listStatusDispatch.size()>0){
            json = gson.toJson(listStatusDispatch);
            json = "{ \"Dispatch\":" + json + "}";
        }

        Log.e("REOS", "StatusDispatchRepository-sendStatusDispatchTime-json"+json);
        /*if(listStatusDispatch!=null && listStatusDispatch.size()>0)
        {
            statusDispatchSQLite.UpdatePruebaJSON(json, listStatusDispatch.get(0).getEntrega_id());
        }*/
        if(json!=null){
            ArrayList<String> responseData=new ArrayList<>();
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendStatusDispatch(jsonRequest).enqueue(new Callback<HeaderStatusDispatchEntityResponse>() {
                @Override
                public void onResponse(Call<HeaderStatusDispatchEntityResponse> call, Response<HeaderStatusDispatchEntityResponse> response) {

                    HeaderStatusDispatchEntityResponse headerStatusDispatchEntityResponse = response.body();
                    if(response.isSuccessful() && headerStatusDispatchEntityResponse!=null)
                    {
                        for (HeaderStatusDispatchEntity respuesta : headerStatusDispatchEntityResponse.getHeaderStatusDispatchEntityResponse()) {
                            for (DetailStatusDispatchEntity respuestaDetalle : respuesta.getDetails()) {
                                if (respuestaDetalle.getHaveError().equals("N")) {//se envio

                                    responseData.add("La Hora de Estado de Despacho fue aceptado en SAP");
                                    statusDispatchSQLite.UpdateResultStatusDispatchTime(respuesta.getDocEntry(), respuestaDetalle.getLineId(), respuestaDetalle.getMessage());

                                } else {//tiene error
                                    responseData.add("El Estado de Despacho no fue aceptado en SAP");
                                }
                            }
                        }
                        callback.onResponseSap(responseData);
                        callback.onResponseErrorSap(response.message());
                    }else{

                        callback.onResponseErrorSap(response.message());
                    }

                }

                @Override
                public void onFailure(Call<HeaderStatusDispatchEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap(t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay hora de estados de despachos pendientes de enviar");
        }
    }

    public MutableLiveData<String> statusDispatchSendPhoto(Context context,Executor executor){
        MutableLiveData<String> temp=new MutableLiveData<String>();
        try {
            sendStatusDispatchPhoto(context, new VisitCallback() {
                @Override
                public void onResponseSap(ArrayList<String> data) {
                    if (data == null) {
                        temp.postValue("No hay Estados de Despacho con Fotos pendientes de Enviar");
                    } else {
                        temp.postValue(data.get(0));
                    }
                }

                @Override
                public void onResponseErrorSap(String response) {
                    temp.postValue(response);
                }
            });
        }catch (Exception e)
        {
            Toast.makeText(context, "Error en Proceso de Envio de Despacho Foto- Error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return temp;
    }

    private void sendStatusDispatchPhoto(final Context context,final VisitCallback callback){
        String  json=null;
        Gson gson=new GsonBuilder().disableHtmlEscaping().create();

        if(statusDispatchSQLite==null){
            statusDispatchSQLite =new StatusDispatchSQLite(context);
        }

        ArrayList<HeaderStatusDispatchEntity> listStatusDispatch = statusDispatchSQLite.getListHeaderDispatchPhoto(context);



        if(listStatusDispatch!=null && listStatusDispatch.size()>0){
            for(int i=0;i<listStatusDispatch.size();i++)
            {
                for(int j=0;j<listStatusDispatch.get(i).getDetails().size();j++)
                {
                    String encoded = null,encoded2 = null;
                    ImageCameraController imageCameraController=new ImageCameraController();
                    encoded=imageCameraController.getBASE64(listStatusDispatch.get(i).getDetails().get(j).getPhotoStore());
                    encoded2=imageCameraController.getBASE64(listStatusDispatch.get(i).getDetails().get(j).getPhotoDocument());
                    if(encoded.equals(""))
                    {
                        listStatusDispatch.get(i).getDetails().get(j).setPhotoStore(encoded);
                    }else {
                        String Base64PhotoLocal = encoded.replace("\n", "");
                        String Base64PhotoLocal2 = Base64PhotoLocal.replace("'\u003d'", "=");
                        listStatusDispatch.get(i).getDetails().get(j).setPhotoStore(Base64PhotoLocal2);
                    }
                    if(encoded2.equals(""))
                    {
                        listStatusDispatch.get(i).getDetails().get(j).setPhotoDocument(encoded2);
                    }else {
                        String Base64PhotoGuia = encoded2.replace("\n", "");
                        String Base64PhotoGuia2 = Base64PhotoGuia.replace("'\u003d'", "=");
                        listStatusDispatch.get(i).getDetails().get(j).setPhotoDocument(Base64PhotoGuia2);
                    }
                }
            }
            json = gson.toJson(listStatusDispatch);
            json = "{ \"Dispatch\":" + json + "}";
        }

        Log.e("REOS", "StatusDispatchRepository-sendStatusDispatchPhoto-json"+json);
        /*if(listStatusDispatch!=null && listStatusDispatch.size()>0)
        {
            statusDispatchSQLite.UpdatePruebaJSON(json, listStatusDispatch.get(0).getDetails().get(0).getEntrega_id());
        }*/
        if(json!=null){
            RequestBody jsonRequest = RequestBody.create(json, MediaType.parse("application/json; charset=utf-8"));
            //Log.e("REOS", "VisitaRepository-sendVisit-sendVisit"+Config.getClient().create(Api.class).sendVisit(jsonRequest).toString());
            Config.getClient().create(Api.class).sendStatusDispatch(jsonRequest).enqueue(new Callback<HeaderStatusDispatchEntityResponse>() {
                @Override
                public void onResponse(Call<HeaderStatusDispatchEntityResponse> call, Response<HeaderStatusDispatchEntityResponse> response) {
                    ArrayList<String> responseData=new ArrayList<>();
                    responseData.add("Inicia Envio de Status Photo");
                    HeaderStatusDispatchEntityResponse headerStatusDispatchEntityResponse=response.body();

                    if(response.isSuccessful() && headerStatusDispatchEntityResponse!=null){


                        for (HeaderStatusDispatchEntity respuesta:headerStatusDispatchEntityResponse.getHeaderStatusDispatchEntityResponse()  )
                        {
                            for(DetailStatusDispatchEntity respuestaDetalle:respuesta.getDetails())
                            {
                                if(respuestaDetalle.getHaveError().equals("N")){//se envio
                                    responseData.add("El Estado de Despacho con Fotos fue aceptado en SAP");
                                    statusDispatchSQLite.UpdateResultStatusDispatch(respuesta.getDocEntry(),respuestaDetalle.getLineId(),respuestaDetalle.getMessage());

                                }else{//tiene error
                                    responseData.add("El Estado de Despacho con Fotos no fue aceptado en SAP");
                                }
                            }
                        }

                        callback.onResponseSap(responseData);
                        callback.onResponseErrorSap(response.message());
                    }else{
                        responseData.add("El Estado de Despacho con Fotos no fue aceptado en SAP");
                        callback.onResponseSap(responseData);
                        callback.onResponseErrorSap(response.message());
                    }

                }
                @Override
                public void onFailure(Call<HeaderStatusDispatchEntityResponse> call, Throwable t) {
                    callback.onResponseErrorSap("Cayo en Error sendStatusDispatchPhoto:"+t.getMessage());
                    call.cancel();
                }
            });
        }else{
            callback.onResponseErrorSap("No hay estados de despachos con fotos pendientes  de enviar");
        }
    }



}

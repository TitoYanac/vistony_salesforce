package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasHistorialDespachosEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasHistorialDespachoEntityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoFacturasHistorialDespachosWS {
    private ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> LHFacturasHD =  new ArrayList<>();
    private Context context;

    public HistoricoFacturasHistorialDespachosWS(final Context context){
        this.context=context;
    }
    public ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> getHistoricoFacturasHistorialDespachos(
            String Imei,
            String Compania_id,
            String Fuerzatrabajo_ID,
            String Cliente_ID,
            String NroDocumento
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoFacturasHistorialDespachoEntityResponse> call = api.getHistoricoFacturasHistorialDespachos(
                Imei,
                Compania_id,
                Fuerzatrabajo_ID,
                Cliente_ID,
                NroDocumento
        );
        try
        {
            Response<HistoricoFacturasHistorialDespachoEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricoFacturasHistorialDespachoEntityResponse historicoFacturasHistorialDespachoEntityResponse=response.body();

                for(int i=0;i<historicoFacturasHistorialDespachoEntityResponse.getHistoricoFacturasHistorialDespachoEntity().size();i++){
                    ListaHistoricoFacturasHistorialDespachosEntity ObjLHFacturasHD = new ListaHistoricoFacturasHistorialDespachosEntity();
                    ObjLHFacturasHD.item = historicoFacturasHistorialDespachoEntityResponse.getHistoricoFacturasHistorialDespachoEntity().get(i).getItem();
                    ObjLHFacturasHD.estado = historicoFacturasHistorialDespachoEntityResponse.getHistoricoFacturasHistorialDespachoEntity().get(i).getEstado();
                    ObjLHFacturasHD.fecha   = historicoFacturasHistorialDespachoEntityResponse.getHistoricoFacturasHistorialDespachoEntity().get(i).getFecha();
                    ObjLHFacturasHD.motivo = historicoFacturasHistorialDespachoEntityResponse.getHistoricoFacturasHistorialDespachoEntity().get(i).getMotivo();;
                    LHFacturasHD.add(ObjLHFacturasHD);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","ERROR:HistoricoFacturasHistorialDespachosWS"+String.valueOf(e));
        }
        Log.e("REOS","LISTA:HistoricoFacturasHistorialDespachosWS"+String.valueOf(LHFacturasHD));
        Log.e("REOS","LISTA:HistoricoFacturasHistorialDespachosWS"+String.valueOf(LHFacturasHD.size()));
        return LHFacturasHD;
    }
}

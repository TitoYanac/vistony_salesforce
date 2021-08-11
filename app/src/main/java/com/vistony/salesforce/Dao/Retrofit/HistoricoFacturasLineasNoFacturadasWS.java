package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasLineasNoFacturadasEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasLineasNoFacturadasEntityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoFacturasLineasNoFacturadasWS {
    private ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> LHFacturas =  new ArrayList<>();
    private Context context;

    public HistoricoFacturasLineasNoFacturadasWS(final Context context){
        this.context=context;
    }
    public ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> getHistoricoFacturasLineasNoFacturas(
            String Imei,
            String Compania_id,
            String Fuerzatrabajo_ID,
            String Cliente_ID,
            String NroDocumento
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoFacturasLineasNoFacturadasEntityResponse> call = api.getHistoricoFacturaLineasNoFacturadas(
                 Imei,
                 Compania_id,
                 Fuerzatrabajo_ID,
                 Cliente_ID,
                 NroDocumento
        );
        try
        {
            Response<HistoricoFacturasLineasNoFacturadasEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricoFacturasLineasNoFacturadasEntityResponse historicoFacturasLineasNoFacturadasEntityResponse=response.body();

                for(int i=0;i<historicoFacturasLineasNoFacturadasEntityResponse.getHistoricoFacturasLineasNoFacturadasEntity().size();i++){
                    ListaHistoricoFacturasLineasNoFacturadasEntity ObjLHFacturasLNF = new ListaHistoricoFacturasLineasNoFacturadasEntity();
                    ObjLHFacturasLNF.item_ov = historicoFacturasLineasNoFacturadasEntityResponse.getHistoricoFacturasLineasNoFacturadasEntity().get(i).getItem_ov();
                    ObjLHFacturasLNF.producto = historicoFacturasLineasNoFacturadasEntityResponse.getHistoricoFacturasLineasNoFacturadasEntity().get(i).getProducto();
                    ObjLHFacturasLNF.umd   = historicoFacturasLineasNoFacturadasEntityResponse.getHistoricoFacturasLineasNoFacturadasEntity().get(i).getUmd();
                    ObjLHFacturasLNF.cantidad = historicoFacturasLineasNoFacturadasEntityResponse.getHistoricoFacturasLineasNoFacturadasEntity().get(i).getCantidad();;
                    LHFacturas.add(ObjLHFacturasLNF);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","ERROR:HistoricoFacturasLineasNoFacturadasWS"+String.valueOf(e));
        }
        Log.e("REOS","LISTA:HistoricoFacturasLineasNoFacturadasWS"+String.valueOf(LHFacturas));
        Log.e("REOS","LISTA:HistoricoFacturasLineasNoFacturadasWS"+String.valueOf(LHFacturas.size()));
        return LHFacturas;
    }
}

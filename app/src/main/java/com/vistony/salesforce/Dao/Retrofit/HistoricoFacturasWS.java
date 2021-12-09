package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoFacturasEntityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoFacturasWS {
    private ArrayList<ListaHistoricoFacturasEntity> LHFacturas =  new ArrayList<>();
    private Context context;

    public HistoricoFacturasWS(final Context context){
        this.context=context;
    }
    public ArrayList<ListaHistoricoFacturasEntity> getHistoricoFacturas(
            String Imei,
            String Compania_id,
            String Fuerzatrabajo_ID,
            String FechaOrdenVenta
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoFacturasEntityResponse> call = api.getHistoricoFactura(
                Imei,
                FechaOrdenVenta
        );
        Log.e("REOS", "HistoricoFacturasWS-getHistoricoFacturas-E" + call.toString());
        try
        {
            Response<HistoricoFacturasEntityResponse> response= call.execute();
            Log.e("REOS", "HistoricoFacturasWS-getHistoricoFacturas-response" + response.toString());
            if(response.isSuccessful()) {
                HistoricoFacturasEntityResponse historicoFacturasEntityResponse=response.body();

                for(int i=0;i<historicoFacturasEntityResponse.getHistoricoFacturas().size();i++){
                    ListaHistoricoFacturasEntity ObjLHFacturas = new ListaHistoricoFacturasEntity();
                    ObjLHFacturas.ordenventa_erp_id = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getOrdenventa_erp_id();
                    //ObjLHFacturas.montoimporteordenventa = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getMontoimporteordenventa();
                    ObjLHFacturas.montoimporteordenventa = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getMontoimportefactura();
                    ObjLHFacturas.cliente_id   = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getCliente_id();
                    ObjLHFacturas.rucdni= historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getRucdni();
                    ObjLHFacturas.nombrecliente = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getNombrecliente();
                    ObjLHFacturas.documento_id = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getDocumento_id();                           //ok
                    ObjLHFacturas.nrofactura = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getNrofactura();                               //ok
                    ObjLHFacturas.fechaemisionfactura = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getFechaemisionfactura();             //ok
                    ObjLHFacturas.montoimportefactura = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getMontoimportefactura();             //ok
                    ObjLHFacturas.montosaldofactura = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getMontosaldofactura();                 //ok
                    ObjLHFacturas.nombrechofer = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getNombrechofer();                           //ok
                    ObjLHFacturas.fechaprogramaciondespacho = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getFechaprogramaciondespacho();
                    ObjLHFacturas.estadodespacho = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getEstadodespacho();
                    ObjLHFacturas.motivoestadodespacho = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getMotivoestadodespacho();
                    ObjLHFacturas.terminopago = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getTerminopago();
                    ObjLHFacturas.tipo_factura = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getTipo_factura();
                    ObjLHFacturas.telefonochofer = historicoFacturasEntityResponse.getHistoricoFacturas().get(i).getTelefonochofer();                       //ok
                    LHFacturas.add(ObjLHFacturas);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS", "HistoricoFacturasWS-getHistoricoFacturas-e" + e.toString());
        }
        Log.e("REOS", "HistoricoFacturasWS-getHistoricoFacturas-response" + LHFacturas.size());
        return LHFacturas;
    }
}

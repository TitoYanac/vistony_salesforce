package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoOrdenVentaEntityResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoOrdenVentaWS {
    private ArrayList<ListaHistoricoOrdenVentaEntity> LHOrdenVenta =  new ArrayList<>();
    private Context context;

    public HistoricoOrdenVentaWS(final Context context){
        this.context=context;
    }

    public ArrayList<ListaHistoricoOrdenVentaEntity> getHistoricoOrdenVenta(String Imei){

        Api api = Config.getClient().create(Api.class);
        Call<HistoricoOrdenVentaEntityResponse> call = api.getHistoricoOrdenVenta("http://169.47.196.209/cl/api/SalesOrder",Imei,null);

        try{
            Response<HistoricoOrdenVentaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricoOrdenVentaEntityResponse historicoOrdenVentaEntityResponse=response.body();

                for(int i=0;i<historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().size();i++){
                    ListaHistoricoOrdenVentaEntity ObjLHOrdenVenta = new ListaHistoricoOrdenVentaEntity();
                    ObjLHOrdenVenta.ordenventa_erp_id = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_erp_id();
                    ObjLHOrdenVenta.cliente_id = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getCliente_id();
                    ObjLHOrdenVenta.rucdni   = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getRucdni();
                    ObjLHOrdenVenta.nombrecliente= historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getNombrecliente();
                    ObjLHOrdenVenta.montototalorden = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getMontototalorden();
                    ObjLHOrdenVenta.estadoaprobacion = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getEstadoaprobacion();
                    ObjLHOrdenVenta.comentarioaprobacion="";
                    ObjLHOrdenVenta.comentariows = "Orden Registrada con Exito";
                    ObjLHOrdenVenta.recepcionERPOV = true;
                    ObjLHOrdenVenta.envioERPOV = true;
                    ObjLHOrdenVenta.docnum = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getDocnum();

                        ObjLHOrdenVenta.ordenventa_id=historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_id();

                    LHOrdenVenta.add(ObjLHOrdenVenta);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","HistoricoOrdenVentaWS-Error: "+e.toString());
        }
        Log.e("REOS","HistoricoOrdenVentaWS:LHOrdenVenta.size():"+LHOrdenVenta.size());
        return LHOrdenVenta;
    }
    public ArrayList<ListaHistoricoOrdenVentaEntity> getHistoricoOrdenVentaEstado(
            String Imei,
            String Compania_id,
            String Fuerzatrabajo_ID,
            String FechaOrdenVentaInicio,
            String FechaOrdenVentaFinal
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoOrdenVentaEntityResponse> call = api.getHistoricoOrdenVentaEstado(
                Imei,
                Compania_id,
                Fuerzatrabajo_ID,
                FechaOrdenVentaInicio,
                FechaOrdenVentaFinal
        );
        Log.e("REOS","HistoricoOrdenVentaWS:Call: "+call);
        try
        {
            Response<HistoricoOrdenVentaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricoOrdenVentaEntityResponse historicoOrdenVentaEntityResponse=response.body();
                //Log.e("REOS","ListaInternaWebService"+historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().size());
                for(int i=0;i<historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().size();i++){
                    ListaHistoricoOrdenVentaEntity ObjLHOrdenVenta = new ListaHistoricoOrdenVentaEntity();
                    ObjLHOrdenVenta.ordenventa_erp_id = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_erp_id();
                    ObjLHOrdenVenta.cliente_id = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getCliente_id();
                    ObjLHOrdenVenta.rucdni   = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getRucdni();
                    ObjLHOrdenVenta.nombrecliente= historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getNombrecliente();
                    ObjLHOrdenVenta.montototalorden = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getMontototalorden();
                    ObjLHOrdenVenta.estadoaprobacion = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getEstadoaprobacion();
                    ObjLHOrdenVenta.comentarioaprobacion="";
                    ObjLHOrdenVenta.comentariows = "Orden Registrada con Exito";
                    ObjLHOrdenVenta.recepcionERPOV = true;
                    ObjLHOrdenVenta.envioERPOV = true;
                    //if(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_id().equals(""))
                    //{
                    ObjLHOrdenVenta.ordenventa_id=historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_erp_id();
                    // }
                    Log.e("REOS","OrdenVenta_Id"+historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_erp_id());
                    LHOrdenVenta.add(ObjLHOrdenVenta);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","error "+e);
        }
        Log.e("REOS","ListaWebService"+LHOrdenVenta.size());
        return LHOrdenVenta;
    }
}

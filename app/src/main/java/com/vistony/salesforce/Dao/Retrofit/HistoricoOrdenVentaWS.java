package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.BuildConfig;
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

    public ArrayList<ListaHistoricoOrdenVentaEntity> getHistoricoOrdenVenta(String Imei,String fecha){
        Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-Imei:"+Imei.toString());
        Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-fecha:"+fecha.toString());
        Api api = Config.getClient().create(Api.class);
        Call<HistoricoOrdenVentaEntityResponse> call = api.getHistoricoOrdenVenta(Imei,fecha);
        Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-api:"+api.toString());
        try{
            Response<HistoricoOrdenVentaEntityResponse> response= call.execute();
            Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-call:"+call.toString());
            if(response.isSuccessful()) {
                HistoricoOrdenVentaEntityResponse historicoOrdenVentaEntityResponse=response.body();
                Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-response:"+response.toString());
                for(int i=0;i<historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().size();i++){

                    ListaHistoricoOrdenVentaEntity ObjLHOrdenVenta = new ListaHistoricoOrdenVentaEntity();

                    ObjLHOrdenVenta.setCardCode(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getCliente_id());
                    ObjLHOrdenVenta.setLicTradNum(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getRucdni());
                    ObjLHOrdenVenta.setCardName(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getNombrecliente());
                    ObjLHOrdenVenta.setDocTotal(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getMontototalorden());


                    switch (BuildConfig.FLAVOR){
                        case "peru":
                        case "india":
                            ObjLHOrdenVenta.setApprovalStatus(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getEstadoaprobacion());
                            break;
                        case "bolivia":
                        case "ecuador":
                        case "chile":
                        case "paraguay":
                        case "espania":
                            ObjLHOrdenVenta.setApprovalStatus(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getObject());
                            break;
                    }
                    ObjLHOrdenVenta.setApprovalCommentary(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getComentarioaprobacion());
                    ObjLHOrdenVenta.setDocNum(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getDocnum());
                    ObjLHOrdenVenta.setSalesOrderID(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_id());
                    ObjLHOrdenVenta.setEnvioERPOV(true);
                    ObjLHOrdenVenta.setRecepcionERPOV(true);
                    ObjLHOrdenVenta.setComentariows("Orden de Venta Registrada Correctamente");
                    ObjLHOrdenVenta.setObject(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getObject());
                    LHOrdenVenta.add(ObjLHOrdenVenta);


                }
            }

        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-e:"+e.toString());
        }
        Log.e("REOS","HistoricoOrdenVentaWS-getHistoricoOrdenVenta-LHOrdenVenta:"+LHOrdenVenta.size());
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

                for(int i=0;i<historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().size();i++){
                    ListaHistoricoOrdenVentaEntity ObjLHOrdenVenta = new ListaHistoricoOrdenVentaEntity();
                    ObjLHOrdenVenta.setDocNum(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getDocnum());
                    ObjLHOrdenVenta.setCardCode(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getCliente_id());
                    ObjLHOrdenVenta.setLicTradNum(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getRucdni());
                    ObjLHOrdenVenta.setCardName(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getNombrecliente());
                    ObjLHOrdenVenta.setDocTotal(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getMontototalorden());
                   // ObjLHOrdenVenta.estadoaprobacion = historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getEstadoaprobacion();
                    //ObjLHOrdenVenta.comentarioaprobacion="";
                    //ObjLHOrdenVenta.comentariows = "Orden Registrada con Exito";
                    //ObjLHOrdenVenta.recepcionERPOV = true;
                    //ObjLHOrdenVenta.envioERPOV = true;

                    ObjLHOrdenVenta.setSalesOrderID(historicoOrdenVentaEntityResponse.getHistoricoOrdenVenta().get(i).getOrdenventa_id());

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

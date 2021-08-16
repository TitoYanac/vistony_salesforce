package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoCobranzaWS {
    private ListaHistoricoCobranzaEntity listaHistoricoDepositoEntity;
    private ArrayList<ListaHistoricoCobranzaEntity> LHDCobranza  = new ArrayList<>();
    private Context context;

    public HistoricoCobranzaWS(final Context context){
        this.context=context;
    }

    public ArrayList<ListaHistoricoCobranzaEntity> getHistoricoCobranza(
            String Imei,
            String Fecha,
            String Flag
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoCobranzaEntityResponse> call = api.getHistoricoCobranza("https://graph.vistony.pe/CobranzaD?imei="+Imei+"&fecha="+Fecha+"&flag="+Flag
                /*Imei
                ,
                Compania_ID,
                Banco_ID,
                TipoIngreso,
                Deposito_ID,
                TipoFecha,
                Fecha,
                FuerzaTrabajo_ID*/
        );

        try{
            Response<HistoricoCobranzaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {


                HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse=response.body();

//

                    for(int i=0;i<historicoCobranzaEntityResponse.getHistoricoCobranza().size();i++){
                        ListaHistoricoCobranzaEntity ObjLHCobranza = new ListaHistoricoCobranzaEntity();
                        ObjLHCobranza.bancarizacion = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getBancarizacion();
                        ObjLHCobranza.banco_id = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getBanco_id();
                        ObjLHCobranza.cliente_id   = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCliente_id();
                        ObjLHCobranza.cliente_nombre= historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNombrecliente();
                        ObjLHCobranza.comentario = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getComentario();
                        ObjLHCobranza.compania_id = SesionEntity.compania_id;
                        ObjLHCobranza.depositodirecto = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDepositodirecto();
                        ObjLHCobranza.detalle_item = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDetalle_item();
                        ObjLHCobranza.documento_id = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDocumento_id();
                        ObjLHCobranza.deposito_id = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDeposito_id();
                        ObjLHCobranza.estado= historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstado();
                        ObjLHCobranza.estadoqr = historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstadoqr();
                        Log.e("REOS","historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstadoqr(): "+historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstadoqr().toString());
                        ObjLHCobranza.fechacobranza =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechacobranza();
                        ObjLHCobranza.fechadeposito =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechadeposito();
                        ObjLHCobranza.fuerzatrabajo_id =  SesionEntity.fuerzatrabajo_id;
                        ObjLHCobranza.importedocumento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getImportedocumento();
                        ObjLHCobranza.montocobrado =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getMontocobrado();
                        ObjLHCobranza.nro_documento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNro_documento();
                        ObjLHCobranza.nuevosaldodocumento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNuevosaldodocumento();
                        ObjLHCobranza.recibo =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getRecibo();
                        ObjLHCobranza.saldodocumento =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getSaldodocumento();
                        ObjLHCobranza.tipoingreso =  historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getTipoingreso();
                        ObjLHCobranza.usuario_id =  SesionEntity.usuario_id;
                        ObjLHCobranza.tipoingreso = "0";
                        ObjLHCobranza.chkwsrecibido = "1";
                        ObjLHCobranza.pagopos=historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getPagopos();
                        ObjLHCobranza.motivoanulacion=historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getMotivoanulacion();
                        ObjLHCobranza.chkwsrecibido = "1";
                        LHDCobranza.add(ObjLHCobranza);
                        Log.e("REOS","HistoricoCobranzaWS-Lista: "+LHDCobranza.size());
                    }
                }


        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","Error-HistoricoCobranzaWS: "+e.toString());
        }
        Log.e("REOS","HistoricoCobranzaWS-Lista: "+LHDCobranza.size());

        //Config.closeConection();
        return LHDCobranza;
    }


}

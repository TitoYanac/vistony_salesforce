package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaUnidadEntityResponse;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoCobranzaUnidadWS {
    private ListaHistoricoCobranzaEntity listaHistoricoDepositoEntity;
    private ArrayList<ListaHistoricoCobranzaEntity> LHDCobranza  = new ArrayList<>();
    private Context context;

    public HistoricoCobranzaUnidadWS(final Context context){
        this.context=context;
    }

    public ArrayList<ListaHistoricoCobranzaEntity> getHistoricoCobranzaIndividual(
            String Imei,
            String Compania_ID,
            String Usuario_ID,
            String Recibo

    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoCobranzaUnidadEntityResponse> call = api.getHistoricoCobranzaIndividual(
                Imei,
                Compania_ID,
                Usuario_ID,
                Recibo
        );
        try
        {
            Response<HistoricoCobranzaUnidadEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricoCobranzaUnidadEntityResponse historicoCobranzaUnidadEntityResponse=response.body();
                for(int i=0;i<historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().size();i++){
                    ListaHistoricoCobranzaEntity ObjLHCobranza = new ListaHistoricoCobranzaEntity();
                    ObjLHCobranza.bancarizacion = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getBancarizacion();
                    ObjLHCobranza.banco_id = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getBanco_id();
                    ObjLHCobranza.cliente_id   = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getCliente_id();
                    ObjLHCobranza.cliente_nombre= historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getNombrecliente();
                    ObjLHCobranza.comentario = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getComentario();
                    ObjLHCobranza.compania_id = SesionEntity.compania_id;
                    ObjLHCobranza.deposito_id = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getDeposito_id();
                    ObjLHCobranza.depositodirecto = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getDepositodirecto();
                    ObjLHCobranza.detalle_item = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getDetalle_item();
                    ObjLHCobranza.documento_id = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getDocumento_id();
                    ObjLHCobranza.estado= historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getEstado();
                    ObjLHCobranza.estadoqr = historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getEstadoqr();
                    ObjLHCobranza.fechacobranza =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getFechacobranza();
                    ObjLHCobranza.fechadeposito =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getFechadeposito();
                    ObjLHCobranza.fuerzatrabajo_id =  SesionEntity.fuerzatrabajo_id;
                    ObjLHCobranza.importedocumento =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getImportedocumento();
                    ObjLHCobranza.montocobrado =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getMontocobrado();
                    ObjLHCobranza.nro_documento =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getNro_documento();
                    ObjLHCobranza.nuevosaldodocumento =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getNuevosaldodocumento();
                    ObjLHCobranza.recibo =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getRecibo();
                    ObjLHCobranza.saldodocumento =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getSaldodocumento();
                    ObjLHCobranza.tipoingreso =  historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getTipoingreso();
                    ObjLHCobranza.usuario_id =  SesionEntity.usuario_id;
                    ObjLHCobranza.tipoingreso = "0";
                    ObjLHCobranza.chkwsrecibido = "1";
                    ObjLHCobranza.pagopos=historicoCobranzaUnidadEntityResponse.getHistoricoCobranzaUnidadEntity().get(i).getTipoingreso();
                    LHDCobranza.add(ObjLHCobranza);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LHDCobranza;
    }
}

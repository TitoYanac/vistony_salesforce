package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoUnidadEntityResponse;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoDepositoUnidadWS {
    private ListaHistoricoDepositoEntity listaHistoricoDepositoEntity;
    private ArrayList<CobranzaCabeceraSQLiteEntity> LHDeposito =  new ArrayList<>();
    private Context context;

    public HistoricoDepositoUnidadWS(final Context context){
        this.context=context;
    }

    public ArrayList<CobranzaCabeceraSQLiteEntity> getHistoricoDepositoIndividual(
            String Imei,
            String Compania_ID,
            String Usuario_ID,
            String Banco_ID,
            String Deposito_ID)
    {
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoDepositoUnidadEntityResponse> call = api.getHistoricoDepositoIndividual(
                Imei,
                Compania_ID,
                Usuario_ID,
                Banco_ID,
                Deposito_ID
        );
        try
        {
            Response<HistoricoDepositoUnidadEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                HistoricoDepositoUnidadEntityResponse historicoDepositoUnidadEntityResponse=response.body();

                for(int i=0;i<historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().size();i++){
                    CobranzaCabeceraSQLiteEntity ObjLCCabecera = new CobranzaCabeceraSQLiteEntity();
                    ObjLCCabecera.chkbancarizado = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getBancarizacion();
                    ObjLCCabecera.banco_id = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getBanco_id();
                    ObjLCCabecera.comentarioanulado   = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getComentario();
                    ObjLCCabecera.compania_id= SesionEntity.compania_id;
                    ObjLCCabecera.pagodirecto = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getDepositodirecto();
                    ObjLCCabecera.cobranza_id = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getDeposito_id();
                    ObjLCCabecera.chkanulado = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getEstado();
                    ObjLCCabecera.fechadeposito = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getFechadeposito();
                    ObjLCCabecera.fechadiferido = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getFechadiferida();
                    ObjLCCabecera.fuerzatrabajo_id= SesionEntity.fuerzatrabajo_id;
                    ObjLCCabecera.totalmontocobrado = historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getMontodeposito();
                    ObjLCCabecera.comentarioanulado=  historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getMotivoanulacion();
                    ObjLCCabecera.tipoingreso =  historicoDepositoUnidadEntityResponse.getHistoricoDepositoUnidad().get(i).getTipoingreso();
                    ObjLCCabecera.usuario_id =  SesionEntity.usuario_id;
                    LHDeposito.add(ObjLCCabecera);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        return LHDeposito;
    }
}

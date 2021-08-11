package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoDepositoEntityResponse;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class HistoricoDepositoWS {
    private ListaHistoricoDepositoEntity listaHistoricoDepositoEntity;
    private ArrayList<ListaHistoricoDepositoEntity> LHDeposito =  new ArrayList<>();
    private Context context;

    public HistoricoDepositoWS(final Context context){
        this.context=context;
    }

    public ArrayList<ListaHistoricoDepositoEntity> getHistoricoDeposito(
            String Imei,
            String Compania_id,
            String FechaDepositoIni,
            String FechaDepositoFin,
            String Fuerzatrabajo_ID
    ){
        Api api = Config.getClient().create(Api.class);

        Call<HistoricoDepositoEntityResponse> call = api.getHistoricoDeposito(
                Imei,
                Compania_id,
                FechaDepositoIni,
                FechaDepositoFin,
                Fuerzatrabajo_ID
        );
        try
        {
            Response<HistoricoDepositoEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                    HistoricoDepositoEntityResponse historicoDepositoEntityResponse=response.body();
                    Log.e("REOS","Response-HistoricoDepositoWS: "+response.toString());
                    for(int i=0;i<historicoDepositoEntityResponse.getHistoricoDeposito().size();i++){
                        ListaHistoricoDepositoEntity ObjLHDeposito = new ListaHistoricoDepositoEntity();
                        ObjLHDeposito.bancarizacion = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getBancarizacion();
                        ObjLHDeposito.banco_id = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getBanco_id();
                        ObjLHDeposito.comentario   = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getComentario();
                        ObjLHDeposito.compania_id=SesionEntity.compania_id;
                        ObjLHDeposito.depositodirecto = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getDepositodirecto();
                        ObjLHDeposito.deposito_id = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getDeposito_id();
                        ObjLHDeposito.estado = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getEstado();
                        ObjLHDeposito.fechadeposito = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getFechadeposito();
                        ObjLHDeposito.fechadiferida = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getFechadiferida();
                        ObjLHDeposito.fuerzatrabajo_id= SesionEntity.fuerzatrabajo_id;
                        ObjLHDeposito.montodeposito = historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getMontodeposito();
                        ObjLHDeposito.motivoanulacion =  historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getMotivoanulacion();
                        ObjLHDeposito.tipoingreso =  historicoDepositoEntityResponse.getHistoricoDeposito().get(i).getTipoingreso();
                        ObjLHDeposito.usuario_id =  SesionEntity.usuario_id;
                        LHDeposito.add(ObjLHDeposito);
                    }
                }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","Error-HistoricoDepositoWS: "+e.toString());
        }

        return LHDeposito;
    }


}

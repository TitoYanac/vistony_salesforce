package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.MotivoVisitaEntityResponse;
import com.vistony.salesforce.Entity.SQLite.MotivoVisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class MotivoVisitaWS {
    private ArrayList<MotivoVisitaSQLiteEntity> LMotivoVisita =  new ArrayList<>();
    private Context context;

    public MotivoVisitaWS(final Context context){
        this.context=context;
    }

    public ArrayList<MotivoVisitaSQLiteEntity> getMotivoVisitaWS(
            String Imei,
            String Compania_ID
    ){
        Api api = Config.getClient().create(Api.class);

        Call<MotivoVisitaEntityResponse> call = api.getReasonVisits(
                Imei);
        Log.e("REOS","MotivoVisitaWS.getMotivoVisitaWS-"+Imei+"-"+Compania_ID);
        try
        {
            Response<MotivoVisitaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                MotivoVisitaEntityResponse motivoVisitaEntityResponse=response.body();
                Log.e("REOS","MotivoVisitaWS.getMotivoVisitaWS.motivoVisitaEntityResponse.getMotivoVisitaEntity()"+motivoVisitaEntityResponse.getMotivoVisitaEntity().size());
                for(int i=0;i<motivoVisitaEntityResponse.getMotivoVisitaEntity().size();i++){

                    MotivoVisitaSQLiteEntity ObjMotivoVisita = new MotivoVisitaSQLiteEntity();
                    ObjMotivoVisita.code = motivoVisitaEntityResponse.getMotivoVisitaEntity().get(i).getCode();
                    ObjMotivoVisita.name = motivoVisitaEntityResponse.getMotivoVisitaEntity().get(i).getName();
                    ObjMotivoVisita.type = motivoVisitaEntityResponse.getMotivoVisitaEntity().get(i).getType();
                    ObjMotivoVisita.compania_id = SesionEntity.compania_id;
                    ObjMotivoVisita.fuerzatrabajo_id = SesionEntity.fuerzatrabajo_id;
                    ObjMotivoVisita.usuario_id = SesionEntity.usuario_id;
                    LMotivoVisita.add(ObjMotivoVisita);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","MotivoVisitaWS.getMotivoVisitaWS.e: "+e.toString());
        }
        Log.e("REOS","MotivoVisitaWS.getMotivoVisitaWS.LMotivoVisita: "+LMotivoVisita.size());
        return LMotivoVisita;
    }
}

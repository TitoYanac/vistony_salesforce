package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.RutaFuerzaTrabajoEntityResponse;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class RutaFuerzaTrabajoWS {

    private ArrayList<RutaFuerzaTrabajoSQLiteEntity> LRFTrabajo =  new ArrayList<>();
    private Context context;

    public RutaFuerzaTrabajoWS (final Context context){
        this.context=context;
    }

    public ArrayList<RutaFuerzaTrabajoSQLiteEntity> getRutaFuerzaTrabajoWS(String Imei){
        Api api = Config.getClient().create(Api.class);

        Call<RutaFuerzaTrabajoEntityResponse> call = api.getRutaFuerzaTrabajo("https://graph.vistony.pe/RutaTrabajo?imei="+Imei);

        try
        {
            Response<RutaFuerzaTrabajoEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                RutaFuerzaTrabajoEntityResponse rutaFuerzaTrabajoEntityResponse=response.body();
                for(int i=0;i<rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().size();i++){

                    RutaFuerzaTrabajoSQLiteEntity ObjRFTrabajo = new RutaFuerzaTrabajoSQLiteEntity();
                    ObjRFTrabajo.compania_id = SesionEntity.compania_id;
                    ObjRFTrabajo.zona_id = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getZona_id();
                    ObjRFTrabajo.zona = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getZona();
                    ObjRFTrabajo.dia = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getDia();
                    ObjRFTrabajo.frecuencia = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getFrecuencia();
                    ObjRFTrabajo.fechainicioruta = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getFechainicioruta();
                    ObjRFTrabajo.estado = rutaFuerzaTrabajoEntityResponse.getRutaFuerzaTrabajoEntity().get(i).getEstado();
                    LRFTrabajo.add(ObjRFTrabajo);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LRFTrabajo;
    }
}

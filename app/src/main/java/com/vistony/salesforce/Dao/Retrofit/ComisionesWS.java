package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ComisionesEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class ComisionesWS {

    private ArrayList<ComisionesSQLiteEntity> LComisiones =  new ArrayList<>();
    private Context context;

    public ComisionesWS (final Context context){
        this.context=context;
    }

    public ArrayList<ComisionesSQLiteEntity> getComisiones(
            String Imei,
            String Compania_ID,
            String FuerzaTrabajo_ID,
            String ano,
            String Periodo

    ){
        Api api = Config.getClient().create(Api.class);

        //Call<ComisionesEntityResponse> call = api.getComisiones("https://graph.vistony.pe/Comision?imei="+Imei+"&anio="+ano+"&mes="+Periodo);
        Call<ComisionesEntityResponse> call =Config.getClient().create(Api.class).getComisiones(
                Imei,
                ano,
                Periodo
        );
        try
        {
            Log.e("REOS","ComisionesWS-getComisiones-call"+call.toString());

            Response<ComisionesEntityResponse> response= call.execute();
            Log.e("REOS","ComisionesWS-getComisiones-response"+response.toString());
            if(response.isSuccessful()) {
                ComisionesEntityResponse comisionesEntityResponse=response.body();
                Log.e("REOS","ComisionesWS-getComisiones-comisionesEntityResponse"+comisionesEntityResponse.getComisionesEntity().toString());
                for(int i=0;i<comisionesEntityResponse.getComisionesEntity().size();i++){

                    ComisionesSQLiteEntity ObjComisiones = new ComisionesSQLiteEntity();
                    ObjComisiones.variable = comisionesEntityResponse.getComisionesEntity().get(i).getVariable();
                    ObjComisiones.umd = comisionesEntityResponse.getComisionesEntity().get(i).getUmd();
                    ObjComisiones.avance = comisionesEntityResponse.getComisionesEntity().get(i).getAvance();
                    ObjComisiones.cuota = comisionesEntityResponse.getComisionesEntity().get(i).getCuota();
                    ObjComisiones.porcentajeavance = comisionesEntityResponse.getComisionesEntity().get(i).getPorcentajeavance();
                    ObjComisiones.compania_id = SesionEntity.compania_id;
                    LComisiones.add(ObjComisiones);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS","ComisionesWS"+e.toString());
        }

        Log.e("REOS","ComisionesWS:LComisiones.Size():"+LComisiones.size());
        return LComisiones;
    }
}

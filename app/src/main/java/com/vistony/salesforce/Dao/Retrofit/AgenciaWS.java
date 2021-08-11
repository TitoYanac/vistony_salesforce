package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.AgenciaEntityResponse;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class AgenciaWS {
    private ArrayList<AgenciaSQLiteEntity> LAgencia= new ArrayList<>();
    private Context context;

    public AgenciaWS(final Context context){
        this.context=context;
    }

    public ArrayList<AgenciaSQLiteEntity> getAgenciaWS(String Imei){
        Api api = Config.getClient().create(Api.class);
        Call<AgenciaEntityResponse> call = api.getAgencia("https://graph.vistony.pe/agencia?imei="+Imei);
        try
        {
            Response<AgenciaEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                AgenciaEntityResponse agenciaEntityResponse=response.body();
                for(int i=0;i<agenciaEntityResponse.getAgenciaEntity().size();i++){
                    AgenciaSQLiteEntity ObjAgencia = new AgenciaSQLiteEntity();
                    ObjAgencia.agencia_id = agenciaEntityResponse.getAgenciaEntity().get(i).getAgencia_id();
                    ObjAgencia.agencia = agenciaEntityResponse.getAgenciaEntity().get(i).getAgencia();
                    ObjAgencia.ubigeo_id = agenciaEntityResponse.getAgenciaEntity().get(i).getUbigeo_id();
                    ObjAgencia.compania_id = SesionEntity.compania_id;
                    ObjAgencia.ruc = agenciaEntityResponse.getAgenciaEntity().get(i).getRuc();
                    ObjAgencia.direccion = agenciaEntityResponse.getAgenciaEntity().get(i).getDireccion();
                    LAgencia.add(ObjAgencia);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LAgencia;
    }
}

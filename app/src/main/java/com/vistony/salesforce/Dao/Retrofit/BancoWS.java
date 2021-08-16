package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class BancoWS {
    private ArrayList<BancoSQLiteEntity> LBanco= new ArrayList<>();
    private Context context;

    public BancoWS(final Context context){
        this.context=context;
    }

    public ArrayList<BancoSQLiteEntity> getBancoWS(String Imei){
        Api api = Config.getClient().create(Api.class);
        Call<BancoEntityResponse> call = api.getBanco(Imei);
        try
        {
            Response<BancoEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                    BancoEntityResponse bancoEntityResponse=response.body();
                    for(int i=0;i<bancoEntityResponse.getBancoEntity().size();i++){
                        BancoSQLiteEntity ObjBanco = new BancoSQLiteEntity();
                        ObjBanco.banco_id = bancoEntityResponse.getBancoEntity().get(i).getBanco_ID();
                        ObjBanco.nombrebanco = bancoEntityResponse.getBancoEntity().get(i).getNombre_Banco();
                        ObjBanco.compania_id = SesionEntity.compania_id;
                        LBanco.add(ObjBanco);
                    }
                }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LBanco;
    }
}

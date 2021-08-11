package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.LoginEntity;

import java.util.ArrayList;

public class ValidacionHashWS {
    private UsuarioSQLiteDao usuarioSQLiteDao;
    private ArrayList<String> perfiles;
    private Context context;
    ArrayList<LoginEntity> listaLoginEntity=new ArrayList<>();

    public ValidacionHashWS(final Context context){
        this.context=context;
    }

    public String getValidacionHash(
            String imei,
            String Compania_ID,
            String FuerzaTrabajo_ID,
            String WebService,
            String Hash
    ){
        String resultado="0";
        Api api = Config.getClient().create(Api.class);
        //usuarioSQLiteDao = new UsuarioSQLiteDao(context);

        /*Call<ValidacionHashEntityResponse> call = api.getValidacionHash(
                 imei,
                 Compania_ID,
                 FuerzaTrabajo_ID,
                 WebService,
                 Hash
        );
        try
        {
            Response<ValidacionHashEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                ValidacionHashEntityResponse validacionHashEntityResponse=response.body();
                for(int i=0;i<validacionHashEntityResponse.getValidacionHashEntities().size();i++){
                    ValidacionHashEntity ObjValidacionHash= new ValidacionHashEntity();
                    resultado = validacionHashEntityResponse.getValidacionHashEntities().get(i).getSemejanza();

                }
            }


        }catch (Exception e)
        {
            e.printStackTrace();
            resultado="0";
        }
        */
        return resultado;
    }
}

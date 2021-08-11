package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPromocionEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class ListaPromocionWS {

    private ArrayList<ListaPromocionSQLiteEntity> LPromocion =  new ArrayList<>();
    private Context context;

    public ListaPromocionWS (final Context context){
        this.context=context;
    }

    public ArrayList<ListaPromocionSQLiteEntity> GetListaPromocionWS(String Imei){
        Api api = Config.getClient().create(Api.class);

        Call<ListaPromocionEntityResponse> call = api.getListaPromocion("https://graph.vistony.pe/TipoListaPromo?imei="+Imei);

        try
        {
            Response<ListaPromocionEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                ListaPromocionEntityResponse listaPromocionEntityResponse=response.body();
                for(int i=0;i<listaPromocionEntityResponse.getListaPromocionEntity().size();i++){

                    ListaPromocionSQLiteEntity ObjLPromocion = new ListaPromocionSQLiteEntity();
                    ObjLPromocion.lista_promocion_id = listaPromocionEntityResponse.getListaPromocionEntity().get(i).getLista_promocion_id();
                    ObjLPromocion.lista_promocion = listaPromocionEntityResponse.getListaPromocionEntity().get(i).getLista_promocion();
                    ObjLPromocion.compania_id = SesionEntity.compania_id;
                    //ObjLPromocion.u_vis_cashdscnt = listaPromocionEntityResponse.getListaPromocionEntity().get(i).getU_vis_cashdscnt();
                    LPromocion.add(ObjLPromocion);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LPromocion;
    }
}

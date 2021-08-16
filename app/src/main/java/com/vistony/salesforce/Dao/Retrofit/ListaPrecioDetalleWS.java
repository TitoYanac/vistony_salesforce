package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPrecioDetalleEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class ListaPrecioDetalleWS {

    private ArrayList<ListaPrecioDetalleSQLiteEntity> LPDetalle= new ArrayList<>();
    private Context context;

    public ListaPrecioDetalleWS(final Context context){
        this.context=context;
    }

    public ArrayList<ListaPrecioDetalleSQLiteEntity> getListaPrecioDetalleWS(String Imei){
        Api api = Config.getClient().create(Api.class);
        Call<ListaPrecioDetalleEntityResponse> call = api.getListaPrecioDetalle(Imei);
        try
        {
            Response<ListaPrecioDetalleEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                ListaPrecioDetalleEntityResponse listaPrecioDetalleEntityResponse=response.body();
                for(int i=0;i<listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().size();i++){
                    ListaPrecioDetalleSQLiteEntity ObjLPDetalle = new ListaPrecioDetalleSQLiteEntity();
                    //ObjLPDetalle.listaprecio_id = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getListaprecio_id();
                    ObjLPDetalle.contado = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getContado();
                    ObjLPDetalle.credito = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getCredito();
                    ObjLPDetalle.producto_id = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getProducto_id();
                    ObjLPDetalle.producto = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getProducto();
                    ObjLPDetalle.umd = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getUmd();
                    ObjLPDetalle.gal = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getGal();
                    ObjLPDetalle.compania_id = SesionEntity.compania_id;
                    //ObjLPDetalle.u_vis_cashdscnt = listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getU_vis_cashdscnt();
                    LPDetalle.add(ObjLPDetalle);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LPDetalle;
    }
}

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

public class ListaPrecioRepository {

    private ArrayList<ListaPrecioDetalleSQLiteEntity> LPDetalle= new ArrayList<>();
    private Context context;

    public ListaPrecioRepository(final Context context){
        this.context=context;
    }

    public ArrayList<ListaPrecioDetalleSQLiteEntity> getListaPrecioDetalleWS(String Imei){
        Api api = Config.getClient().create(Api.class);
        Call<ListaPrecioDetalleEntityResponse> call = api.getListaPrecioDetalle("http://169.47.196.209/cl/api/pricelist",Imei);
        try{
            Response<ListaPrecioDetalleEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                ListaPrecioDetalleEntityResponse listaPrecioDetalleEntityResponse=response.body();
                for(int i=0;i<listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().size();i++){

                    ListaPrecioDetalleSQLiteEntity ObjLPDetalle = new ListaPrecioDetalleSQLiteEntity();
                    ObjLPDetalle.setContado(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getContado());
                    ObjLPDetalle.setCredito(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getCredito());
                    ObjLPDetalle.setProducto_id(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getProducto_id());
                    ObjLPDetalle.setProducto(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getProducto());
                    ObjLPDetalle.setUmd(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getUmd());
                    ObjLPDetalle.setGal(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getGal());
                    ObjLPDetalle.setCompania_id(SesionEntity.compania_id);
                    ObjLPDetalle.setPorcentaje_descuento(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getPorcentaje_descuento());
                    ObjLPDetalle.setTypo(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getTipo());
                    ObjLPDetalle.setStock_almacen(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getStock_almacen());
                    ObjLPDetalle.setStock_general(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getStock_general());

                    LPDetalle.add(ObjLPDetalle);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return LPDetalle;
    }
}

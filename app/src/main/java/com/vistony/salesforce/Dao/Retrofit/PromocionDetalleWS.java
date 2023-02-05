package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionDetalleEntityResponse;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class PromocionDetalleWS {

    private ArrayList<PromocionDetalleSQLiteEntity> LPDetalle =  new ArrayList<>();
    private Context context;

    public PromocionDetalleWS (final Context context){
        this.context=context;
    }

    public ArrayList<PromocionDetalleSQLiteEntity> getPromocionDetalleWS(String Imei){
        Api api = Config.getClient().create(Api.class);

        Call<PromocionDetalleEntityResponse> call = api.getPromomocionDetalle(Imei);

        try
        {
            Response<PromocionDetalleEntityResponse> response= call.execute();
            Log.e("REOS","Response-getPromocionDetalleWS: "+response.toString());
            if(response.isSuccessful()) {
                PromocionDetalleEntityResponse promocionDetalleEntityResponse=response.body();
                for(int i=0;i<promocionDetalleEntityResponse.getPromocionDetalleEntity().size();i++){

                    PromocionDetalleSQLiteEntity ObjPDetalle = new PromocionDetalleSQLiteEntity();
                    ObjPDetalle.lista_promocion_id = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getLista_promocion_id();
                    ObjPDetalle.promocion_id = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getPromocion_id();
                    ObjPDetalle.promocion_detalle_id = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getPromocion_detalle_id();
                    ObjPDetalle.producto_id = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getProducto_id();
                    ObjPDetalle.producto = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getProducto();
                    ObjPDetalle.umd = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getUmd();
                    ObjPDetalle.cantidad = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getCantidad();
                    ObjPDetalle.compania_id = SesionEntity.compania_id;
                    ObjPDetalle.fuerzatrabajo_id = SesionEntity.fuerzatrabajo_id;
                    ObjPDetalle.usuario_id = SesionEntity.usuario_id;
                    //ObjPDetalle.preciobase = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getPreciobase();
                    ObjPDetalle.preciobase = "0";
                    //ObjPDetalle.chkdescuento = promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getChkdescuento();
                    ObjPDetalle.descuento =  promocionDetalleEntityResponse.getPromocionDetalleEntity().get(i).getDescuento();
                    LPDetalle.add(ObjPDetalle);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS", "PromocionDetalleWS-Error: " + e.toString());
        }


        return LPDetalle;
    }
}

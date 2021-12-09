package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPromocionEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionCabeceraEntityResponse;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class PromocionCabeceraWS {

    private ArrayList<PromocionCabeceraSQLiteEntity> LPCabecera =  new ArrayList<>();
    private Context context;
    DecimalFormat format = new DecimalFormat ("#0");


    public PromocionCabeceraWS (final Context context){
        this.context=context;
    }

    public ArrayList<PromocionCabeceraSQLiteEntity> getPromocionCabeceraWS(String Imei){
        Api api = Config.getClient().create(Api.class);

        //Call<PromocionCabeceraEntityResponse> call = api.getPromomocionCabecera("https://graph.vistony.pe/ListaPromoC?imei="+Imei);
        Call<PromocionCabeceraEntityResponse> call = api.getPromomocionCabecera(Imei);
        try
        {
            Response<PromocionCabeceraEntityResponse> response= call.execute();
            Log.e("REOS","Response-PromocionCabeceraWS: "+response.toString());
            if(response.isSuccessful()) {
                PromocionCabeceraEntityResponse promocionCabeceraEntityResponse=response.body();
                for(int i=0;i<promocionCabeceraEntityResponse.getPromocionCabeceraEntity().size();i++){

                    PromocionCabeceraSQLiteEntity ObjPCabecera = new PromocionCabeceraSQLiteEntity();
                    ObjPCabecera.lista_promocion_id = promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getLista_promocion_id();
                    ObjPCabecera.promocion_id = promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getPromocion_id();
                    ObjPCabecera.producto_id = promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getProducto_id();
                    ObjPCabecera.producto = promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getProducto();
                    ObjPCabecera.umd = promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getUmd();
                    //ObjPCabecera.cantidad = String.valueOf(Integer.parseInt(promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getCantidad().toString())) ;
                    ObjPCabecera.cantidad = String.valueOf(Integer.parseInt(format.format(Float.parseFloat(promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getCantidad())))) ;
                    ObjPCabecera.descuento = String.valueOf(Integer.parseInt(format.format(Float.parseFloat(promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getDescuento())))) ;
                    //Log.e("REOS", "PromocionCabeceraWS-Cantidad: " + String.valueOf(Integer.parseInt(format.format(Float.parseFloat(promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getCantidad())))));
                    ObjPCabecera.compania_id = SesionEntity.compania_id;
                    ObjPCabecera.fuerzatrabajo_id = SesionEntity.fuerzatrabajo_id;
                    ObjPCabecera.usuario_id = SesionEntity.usuario_id;
                    //ObjPCabecera.total_preciobase = promocionCabeceraEntityResponse.getPromocionCabeceraEntity().get(i).getTotal_preciobase();
                    LPCabecera.add(ObjPCabecera);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            Log.e("REOS", "PromocionCabeceraWS-Error: " + e.toString());
        }


        return LPCabecera;
    }
}

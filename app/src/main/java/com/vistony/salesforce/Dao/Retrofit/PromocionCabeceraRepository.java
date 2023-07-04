package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PromocionCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.BancoEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPromocionEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionCabeceraEntityResponse;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromocionCabeceraRepository extends ViewModel {

    /*private ArrayList<PromocionCabeceraSQLiteEntity> LPCabecera =  new ArrayList<>();
    private Context context;
    DecimalFormat format = new DecimalFormat ("#0");*/
    private PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();

    /*public PromocionCabeceraRepository(final Context context){
        this.context=context;
    }*/

    /*
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
    */


    public MutableLiveData<String> exeClearandAddPromotionHead(String Imei,Context context){

        Config.getClient().create(Api.class).getPromomocionCabecera(Imei).enqueue(new Callback<PromocionCabeceraEntityResponse>() {
            @Override
            public void onResponse(Call<PromocionCabeceraEntityResponse> call, Response<PromocionCabeceraEntityResponse> response) {

                PromocionCabeceraEntityResponse promocionCabeceraEntityResponse=response.body();

                if(response.isSuccessful() && promocionCabeceraEntityResponse.getPromocionCabeceraEntity()
                        !=null){
                    //PromocionCabeceraEntity promocionCabeceraEntity=new PromocionCabeceraEntity();
                    //ArrayList<PromocionCabeceraEntity> promocionCabeceraEntityArrayList=new ArrayList<>();
                    promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);

                    /*promocionCabeceraEntity.setProducto_id("1400026");
                    promocionCabeceraEntity.setProducto("PRODUCTO DE PRUEBA");
                    promocionCabeceraEntity.setCantidad("24");
                    promocionCabeceraEntity.setTipo_malla("E");
                    promocionCabeceraEntity.setLista_promocion_id("0000");
                    promocionCabeceraEntity.setDescuento("0");
                    promocionCabeceraEntity.setCantidad_maxima("49");
                    promocionCabeceraEntity.setUmd("CAJ12");
                    promocionCabeceraEntityArrayList.add(promocionCabeceraEntity);*/

                    promocionCabeceraSQLiteDao.LimpiarTablaPromocionCabecera();
                    promocionCabeceraSQLiteDao.AddListPromotionHead(promocionCabeceraEntityResponse.getPromocionCabeceraEntity());
                    //promocionCabeceraSQLiteDao.AddListPromotionHead(promocionCabeceraEntityArrayList);
                    Integer countPromotionHead=getPromotionHead(context);
                    parametrosSQLite.ActualizaCantidadRegistros("10", context.getResources().getString(R.string.promotion_head).toUpperCase(), ""+countPromotionHead, getDateTime());

                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<PromocionCabeceraEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getPromotionHead(Context context){
        return promocionCabeceraSQLiteDao.ObtenerCantidadPromocionCabecera() ;
    }
}

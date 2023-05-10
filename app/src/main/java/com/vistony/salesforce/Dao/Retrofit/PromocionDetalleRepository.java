package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionCabeceraEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.PromocionDetalleEntityResponse;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromocionDetalleRepository extends ViewModel
{

    /*private ArrayList<PromocionDetalleSQLiteEntity> LPDetalle =  new ArrayList<>();
    private Context context;*/
    private PromocionDetalleSQLiteDao promocionDetalleSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();

    /*
    public PromocionDetalleRepository(final Context context){
        this.context=context;
    }*/

    /*
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
    }*/

    public MutableLiveData<String> exeClearandAddPromotionDetail(String Imei,Context context){

        Config.getClient().create(Api.class).getPromomocionDetalle(Imei).enqueue(new Callback<PromocionDetalleEntityResponse>() {
            @Override
            public void onResponse(Call<PromocionDetalleEntityResponse> call, Response<PromocionDetalleEntityResponse> response) {

                PromocionDetalleEntityResponse promocionDetalleEntityResponse=response.body();

                if(response.isSuccessful() && promocionDetalleEntityResponse.getPromocionDetalleEntity()
                        !=null){

                    promocionDetalleSQLiteDao = new PromocionDetalleSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    promocionDetalleSQLiteDao.LimpiarTablaPromocionDetalle();
                    promocionDetalleSQLiteDao.AddListPromotionDetail(promocionDetalleEntityResponse.getPromocionDetalleEntity());
                    Integer countPromotionDetail=getPromotionDetail(context);
                    parametrosSQLite.ActualizaCantidadRegistros("11", context.getResources().getString(R.string.promotion_detail).toUpperCase(), ""+countPromotionDetail, getDateTime());
                }

                status.setValue("1");
            }

            @Override
            public void onFailure(Call<PromocionDetalleEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getPromotionDetail(Context context){
        return promocionDetalleSQLiteDao.ObtenerCantidadPromocionDetalle() ;
    }

}

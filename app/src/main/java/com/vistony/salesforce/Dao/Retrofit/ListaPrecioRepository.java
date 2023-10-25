package com.vistony.salesforce.Dao.Retrofit;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPrecioDetalleEntityResponse;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.ListaPrecioDetalleWarehouseEntityResponse;
import com.vistony.salesforce.R;

import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaPrecioRepository
        extends ViewModel
{

    //private ArrayList<ListaPrecioDetalleSQLiteEntity> LPDetalle= new ArrayList<>();
    //private Context context;

    private ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao;
    private ParametrosSQLite parametrosSQLite;
    private MutableLiveData<String> status= new MutableLiveData<>();

    /*
    public ListaPrecioRepository(final Context context){
        this.context=context;
    }

    public ArrayList<ListaPrecioDetalleSQLiteEntity> getListaPrecioDetalleWS(String Imei){
        Api api = Config.getClient().create(Api.class);
        Call<ListaPrecioDetalleEntityResponse> call = api.getListaPrecioDetalle(Imei);
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
                    ObjLPDetalle.setU_vis_cashdscnt(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getCashdscnt());
                    ObjLPDetalle.setUnit (listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getUnit());
                    ObjLPDetalle.setOiltax (listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getOiltax());
                    ObjLPDetalle.setLiter (listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getLiter());
                    ObjLPDetalle.setSIGAUS (listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity().get(i).getSIGAUS());


                    LPDetalle.add(ObjLPDetalle);
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return LPDetalle;
    }*/

    public MutableLiveData<String> execClarAndAddPriceList(String Imei, Context context, Executor executor){

        Config.getClient().create(Api.class).getListaPrecioDetalle(Imei).enqueue(new Callback<ListaPrecioDetalleEntityResponse>() {
            @Override
            public void onResponse(Call<ListaPrecioDetalleEntityResponse> call, Response<ListaPrecioDetalleEntityResponse> response) {
                ListaPrecioDetalleEntityResponse listaPrecioDetalleEntityResponse=response.body();
                if(response.isSuccessful() && listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity()!=null)
                {
                    executor.execute(() -> {
                    listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                    listaPrecioDetalleSQLiteDao.AddListPriceList(listaPrecioDetalleEntityResponse.getListaPrecioDetalleEntity());
                    Integer countPriceList=getCountPriceList(context);
                    parametrosSQLite.ActualizaCantidadRegistros("7", context.getResources().getString(R.string.price_list).toUpperCase(), ""+countPriceList, getDateTime());
                    });
                }
                status.setValue("1");
            }

            @Override
            public void onFailure(Call<ListaPrecioDetalleEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }

    private Integer getCountPriceList(Context context){
        return listaPrecioDetalleSQLiteDao.ObtenerCantidadListaPrecioDetalle();
    }

    public MutableLiveData<String> execClarAndAddPriceListWarehouse(String Imei,Context context,String WhsCode,String PriceListCash,String PriceListCredit,Executor executor){

        Log.e("REOS","ListaPrecioRepository-execClarAndAddPriceListWarehouse-WhsCode: "+WhsCode);
        Log.e("REOS","ListaPrecioRepository-execClarAndAddPriceListWarehouse-PriceListCash: "+PriceListCash);
        Log.e("REOS","ListaPrecioRepository-execClarAndAddPriceListWarehouse-PriceListCredit: "+PriceListCredit);
        Config.getClient().create(Api.class).getPriceListWarehouse(Imei,WhsCode,PriceListCash,PriceListCredit).enqueue(new Callback<ListaPrecioDetalleWarehouseEntityResponse>() {
            @Override
            public void onResponse(Call<ListaPrecioDetalleWarehouseEntityResponse> call, Response<ListaPrecioDetalleWarehouseEntityResponse> response) {
                Log.e("REOS", "ParametrosView-listaPrecioRepository-Call: " + call);
                Log.e("REOS", "ParametrosView-listaPrecioRepository-response: " + response);
                ListaPrecioDetalleWarehouseEntityResponse listaPrecioDetalleWarehouseEntityResponse=response.body();
                if(response.isSuccessful() && listaPrecioDetalleWarehouseEntityResponse.getListaPrecioDetalleWarehouseEntity()!=null)
                {
                    executor.execute(() -> {
                    Log.e("REOS", "ParametrosView-listaPrecioRepository-response.isSuccessful(): " + response.isSuccessful());
                    listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(context);
                    parametrosSQLite = new ParametrosSQLite(context);
                    listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                    listaPrecioDetalleSQLiteDao.AddListPriceList(listaPrecioDetalleWarehouseEntityResponse.getListaPrecioDetalleWarehouseEntity());
                    Integer countPriceList=getCountPriceList(context);
                    parametrosSQLite.ActualizaCantidadRegistros("7", context.getResources().getString(R.string.price_list).toUpperCase(), ""+countPriceList, getDateTime());
                    });
                }
                status.setValue("1");
            }

            @Override
            public void onFailure(Call<ListaPrecioDetalleWarehouseEntityResponse> call, Throwable t) {
                status.setValue("0");
            }
        });
        return status;
    }
}

package com.vistony.salesforce.Dao.Retrofit;

import android.content.Context;

import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.StockEntityResponse;
import com.vistony.salesforce.Entity.SQLite.StockSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class StockWS {

    private ArrayList<StockSQLiteEntity> LStock =  new ArrayList<>();
    private Context context;

    public StockWS (final Context context){
        this.context=context;
    }

    public ArrayList<StockSQLiteEntity> getStockWS(
            String Imei
    ){
        Api api = Config.getClient().create(Api.class);

        Call<StockEntityResponse> call = api.getStock(Imei);
        try
        {
            Response<StockEntityResponse> response= call.execute();
            if(response.isSuccessful()) {
                StockEntityResponse stockEntityResponse=response.body();
                for(int i=0;i<stockEntityResponse.getStockEntity().size();i++){

                    StockSQLiteEntity ObjStock = new StockSQLiteEntity();
                    ObjStock.producto_id = stockEntityResponse.getStockEntity().get(i).getProducto_id();
                    ObjStock.producto = stockEntityResponse.getStockEntity().get(i).getProducto();
                    ObjStock.umd = stockEntityResponse.getStockEntity().get(i).getUmd();
                    ObjStock.stock = stockEntityResponse.getStockEntity().get(i).getStock();
                    ObjStock.almacen_id = stockEntityResponse.getStockEntity().get(i).getAlmacen_id();
                    ObjStock.comprometido = stockEntityResponse.getStockEntity().get(i).getCmprometido();
                    ObjStock.enstock = stockEntityResponse.getStockEntity().get(i).getEnstock();
                    ObjStock.pedido = stockEntityResponse.getStockEntity().get(i).getPedido();
                    ObjStock.compania_id = SesionEntity.compania_id;
                    LStock.add(ObjStock);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        return LStock;
    }
}

package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ObjectEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WarehouseEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class WarehouseSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    Context context;

    public WarehouseSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        this.context=context;
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteController.getDatabaseName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteController.getDatabaseName() );
        sqliteController.close();
    }


    //public int InsertaBanco (String banco_id,String compania_id,String nombrebanco)
    public int addWarehouse (List<WarehouseEntity> warehouse)
    {
        abrir();
        for (int i = 0; i < warehouse.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("WhsCode",warehouse.get(i).getWhsCode());
            registro.put("WhsName",warehouse.get(i).getWhsName());
            registro.put("PriceListCash",warehouse.get(i).getPriceListCash());
            registro.put("PriceListCredit",warehouse.get(i).getPriceListCredit());
            registro.put("U_VIST_SUCUSU",warehouse.get(i).getU_VIST_SUCUSU());
            bd.insert("warehouse",null,registro);
        }

        bd.close();
        return 1;
    }

    public int clearTableWarehouse()
    {
        abrir();
        bd.execSQL("delete from warehouse ");
        bd.close();
        return 1;
    }

    public Integer getCountWareHouse ()
    {
        Integer resultado=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(WhsCode) from warehouse",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }
        return resultado;
    }

    public ArrayList<WarehouseEntity> getWarehouse(){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  * FROM warehouse ", null);
            ArrayList<WarehouseEntity> listWarehouseEntity = new  ArrayList<WarehouseEntity>();

            if (fila.moveToFirst()) {
                do {
                    WarehouseEntity warehouseEntity=new WarehouseEntity();
                    warehouseEntity.setWhsCode(fila.getString(fila.getColumnIndex("WhsCode")));
                    warehouseEntity.setWhsName(fila.getString(fila.getColumnIndex("WhsName")));
                    warehouseEntity.setPriceListCash(fila.getString(fila.getColumnIndex("PriceListCash")));
                    warehouseEntity.setPriceListCredit(fila.getString(fila.getColumnIndex("PriceListCredit")));
                    warehouseEntity.setU_VIST_SUCUSU(fila.getString(fila.getColumnIndex("U_VIST_SUCUSU")));
                    listWarehouseEntity.add(warehouseEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listWarehouseEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }

    public ArrayList<WarehouseEntity> getWarehouseCode(String warehouseCode){
        try {
            abrir();

            Cursor fila = bd.rawQuery("SELECT  * FROM warehouse  WHERE WhsCode ='"+warehouseCode+"'", null);
            ArrayList<WarehouseEntity> listWarehouseEntity = new  ArrayList<WarehouseEntity>();

            if (fila.moveToFirst()) {
                do {
                    WarehouseEntity warehouseEntity=new WarehouseEntity();
                    warehouseEntity.setWhsCode(fila.getString(fila.getColumnIndex("WhsCode")));
                    warehouseEntity.setWhsName(fila.getString(fila.getColumnIndex("WhsName")));
                    warehouseEntity.setPriceListCash(fila.getString(fila.getColumnIndex("PriceListCash")));
                    warehouseEntity.setPriceListCredit(fila.getString(fila.getColumnIndex("PriceListCredit")));
                    warehouseEntity.setU_VIST_SUCUSU(fila.getString(fila.getColumnIndex("U_VIST_SUCUSU")));
                    listWarehouseEntity.add(warehouseEntity);
                } while (fila.moveToNext());
            }
            bd.close();
            return listWarehouseEntity;
        }catch (Exception ex){
            Log.e("REOS","LeadSQLite-getGeolocationBlock-ex"+ex.getMessage());
            bd.close();
            return null;
        }
    }
}

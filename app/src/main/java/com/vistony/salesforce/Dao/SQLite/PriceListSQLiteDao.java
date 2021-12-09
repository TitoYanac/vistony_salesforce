package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListEntity;
import com.vistony.salesforce.Entity.SQLite.ParametrosSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PriceListSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class PriceListSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<PriceListSQLiteEntity> listPriceListSQLiteEntity;

    public PriceListSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + this.getClass().getName());
        sqliteController.close();
    }

    public int InsertPriceList (List<PriceListEntity> priceListEntity)
    {
        abrir();

        for (int i = 0; i < priceListEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("pricelist_id",priceListEntity.get(i).getPricelist_id());
            registro.put("pricelist",priceListEntity.get(i).getPricelist());
            bd.insert("pricelist",null,registro);
        }

        bd.close();
        return 1;
    }

    public int ClearPriceList ()
    {
        abrir();
        bd.execSQL("delete from pricelist ");
        bd.close();
        return 1;
    }

    public ArrayList<PriceListSQLiteEntity> GetPriceList ()
    {
        listPriceListSQLiteEntity = new ArrayList<PriceListSQLiteEntity>();
        PriceListSQLiteEntity priceListSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from pricelist ",null);

        while (fila.moveToNext())
        {
            priceListSQLiteEntity= new PriceListSQLiteEntity();
            priceListSQLiteEntity.setCompania_id(fila.getString(0));
            priceListSQLiteEntity.setFuerzatrabajo_id(fila.getString(1));
            priceListSQLiteEntity.setUsuario_id(fila.getString(2));
            priceListSQLiteEntity.setPricelist_id(fila.getString(3));
            priceListSQLiteEntity.setPriceList(fila.getString(4));
            listPriceListSQLiteEntity.add(priceListSQLiteEntity);
        }

        bd.close();
        return listPriceListSQLiteEntity;
    }

    public Integer getCountPriceList ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(pricelist_id) from pricelist",null);

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
}

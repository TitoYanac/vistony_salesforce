package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListHeadEntity;
import com.vistony.salesforce.Entity.SQLite.PriceListSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;


public class PriceListHeadSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<PriceListHeadEntity> listPriceListHeadEntity;

    public PriceListHeadSQLite(Context context)
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

    public int addPriceListHead (List<PriceListHeadEntity> priceListHeadEntity)
    {
        abrir();

        for (int i = 0; i < priceListHeadEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("ListNum",priceListHeadEntity.get(i).getListNum());
            registro.put("ListName",priceListHeadEntity.get(i).getListName());
            registro.put("U_VIS_PercentageIncrease",priceListHeadEntity.get(i).getPrcntIncrease());
            bd.insert("pricelisthead",null,registro);
        }

        bd.close();
        return 1;
    }

    public int ClearPriceListHead ()
    {
        abrir();
        bd.execSQL("delete from pricelisthead ");
        bd.close();
        return 1;
    }

    public ArrayList<PriceListHeadEntity> GetPriceListHead (String ListNum)
    {
        listPriceListHeadEntity = new ArrayList<PriceListHeadEntity>();
        PriceListHeadEntity priceListHeadEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from pricelisthead where ListNum=?",new String[]{ListNum});

        while (fila.moveToNext())
        {
            priceListHeadEntity= new PriceListHeadEntity();
            priceListHeadEntity.setCompania_id(fila.getString(0));
            priceListHeadEntity.setFuerzatrabajo_id(fila.getString(1));
            priceListHeadEntity.setUsuario_id(fila.getString(2));
            priceListHeadEntity.setListNum(fila.getString(3));
            priceListHeadEntity.setListName(fila.getString(4));
            priceListHeadEntity.setPrcntIncrease(fila.getString(5));
            listPriceListHeadEntity.add(priceListHeadEntity);
        }

        bd.close();
        return listPriceListHeadEntity;
    }

    public Integer getCountPriceListHead ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(ListNum) from pricelisthead",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            //Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }

        return resultado;
    }
}

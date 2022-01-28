package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ComisionesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PriceListSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class ComissionsSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ComisionesSQLiteEntity> listComisionesSQLiteEntity;

    public ComissionsSQLiteDao(Context context)
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

    public int InsertComissions (List<ComisionesEntity> comisionesEntity)
    {
        abrir();

        for (int i = 0; i < comisionesEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("variable",comisionesEntity.get(i).getVariable());
            registro.put("uom",comisionesEntity.get(i).getUmd());
            registro.put("advance",comisionesEntity.get(i).getAvance());
            registro.put("quota",comisionesEntity.get(i).getCuota());
            registro.put("percentage",comisionesEntity.get(i).getPorcentajeavance());
            registro.put("esc_colours",comisionesEntity.get(i).getEsc_colours());
            registro.put("hidedata",comisionesEntity.get(i).getHidedata());
            bd.insert("commissions",null,registro);
        }

        bd.close();
        return 1;
    }

    public int ClearCommissions ()
    {
        abrir();
        bd.execSQL("delete from commissions ");
        bd.close();
        return 1;
    }

    public ArrayList<ComisionesSQLiteEntity> GetComissions ()
    {
        listComisionesSQLiteEntity = new ArrayList<ComisionesSQLiteEntity>();
        ComisionesSQLiteEntity comisionesSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from commissions ",null);

        while (fila.moveToNext())
        {
            comisionesSQLiteEntity= new ComisionesSQLiteEntity();
            comisionesSQLiteEntity.setCompania_id (fila.getString(fila.getColumnIndex("compania_id")));
            comisionesSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            comisionesSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            comisionesSQLiteEntity.setVariable(fila.getString(fila.getColumnIndex("variable")));
            comisionesSQLiteEntity.setUmd(fila.getString(fila.getColumnIndex("uom")));
            comisionesSQLiteEntity.setAvance(fila.getString(fila.getColumnIndex("advance")));
            comisionesSQLiteEntity.setCuota(fila.getString(fila.getColumnIndex("quota")));
            comisionesSQLiteEntity.setPorcentajeavance(fila.getString(fila.getColumnIndex("percentage")));
            comisionesSQLiteEntity.setEsc_colours(fila.getString(fila.getColumnIndex("esc_colours")));
            comisionesSQLiteEntity.setHidedata(fila.getString(fila.getColumnIndex("hidedata")));
            listComisionesSQLiteEntity.add(comisionesSQLiteEntity);
        }

        bd.close();
        return listComisionesSQLiteEntity;
    }

    public Integer getCountCommissions ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from commissions",null);

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

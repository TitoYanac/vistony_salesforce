package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ComisionesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursDEntity;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class EscColoursDSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<EscColoursDEntity> listEscColoursDEntity;

    public EscColoursDSQLiteDao(Context context)
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

    public int InsertEscColoursD (List<EscColoursDEntity> escColoursDEntity)
    {
        abrir();

        for (int i = 0; i < escColoursDEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("id_esc_colours_c",escColoursDEntity.get(i).getId_esc_colours_c());
            registro.put("id",escColoursDEntity.get(i).getId());
            registro.put("rangemin",escColoursDEntity.get(i).getRangemin());
            registro.put("rangemax",escColoursDEntity.get(i).getRangemax());
            registro.put("colourmin",escColoursDEntity.get(i).getColourmin());
            registro.put("colourmax",escColoursDEntity.get(i).getColourmax());
            registro.put("degrade",escColoursDEntity.get(i).getDegrade());
            bd.insert("esc_colours_d",null,registro);
        }

        bd.close();
        return 1;
    }

    public int ClearEscColoursD ()
    {
        abrir();
        bd.execSQL("delete from esc_colours_d ");
        bd.close();
        return 1;
    }

    public ArrayList<EscColoursDEntity> GetEscColours (String codeColor,float percent)
    {
        Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-codeColor"+codeColor);
        Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-percent"+percent);
        listEscColoursDEntity = new ArrayList<EscColoursDEntity>();
        EscColoursDEntity escColoursDEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from esc_colours_d where id_esc_colours_c='"+codeColor+"' and CAST(rangemin AS DECIMAL) <='"+percent+"' OR CAST(rangemax AS DECIMAL)<='"+percent+"'"
                    , null);
            Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-bd.rawQuery"+fila.toString());
            while (fila.moveToNext()) {
                escColoursDEntity = new EscColoursDEntity();
                escColoursDEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                escColoursDEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                escColoursDEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
                escColoursDEntity.setId_esc_colours_c(fila.getString(fila.getColumnIndex("id_esc_colours_c")));
                escColoursDEntity.setId(fila.getString(fila.getColumnIndex("id")));
                escColoursDEntity.setRangemin(fila.getString(fila.getColumnIndex("rangemin")));
                escColoursDEntity.setRangemax(fila.getString(fila.getColumnIndex("rangemax")));
                escColoursDEntity.setColourmin(fila.getString(fila.getColumnIndex("colourmin")));
                escColoursDEntity.setColourmax(fila.getString(fila.getColumnIndex("colourmax")));
                escColoursDEntity.setDegrade(fila.getString(fila.getColumnIndex("degrade")));
                listEscColoursDEntity.add(escColoursDEntity);
            }
        }catch (Exception e)
        {
            e.getMessage();
            Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-e:"+e.toString());
        }

        bd.close();
        return listEscColoursDEntity;
    }

    public Integer getCountEscColoursD ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from esc_colours_d",null);

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

    public ArrayList<EscColoursDEntity> GetEscColoursForCode (String codeColor)
    {
        Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-codeColor"+codeColor);
        listEscColoursDEntity = new ArrayList<EscColoursDEntity>();
        EscColoursDEntity escColoursDEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from esc_colours_d where id_esc_colours_c='"+codeColor+"'"
                    , null);
            Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-bd.rawQuery"+fila.toString());
            while (fila.moveToNext()) {
                escColoursDEntity = new EscColoursDEntity();
                escColoursDEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                escColoursDEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                escColoursDEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
                escColoursDEntity.setId_esc_colours_c(fila.getString(fila.getColumnIndex("id_esc_colours_c")));
                escColoursDEntity.setId(fila.getString(fila.getColumnIndex("id")));
                escColoursDEntity.setRangemin(fila.getString(fila.getColumnIndex("rangemin")));
                escColoursDEntity.setRangemax(fila.getString(fila.getColumnIndex("rangemax")));
                escColoursDEntity.setColourmin(fila.getString(fila.getColumnIndex("colourmin")));
                escColoursDEntity.setColourmax(fila.getString(fila.getColumnIndex("colourmax")));
                escColoursDEntity.setDegrade(fila.getString(fila.getColumnIndex("degrade")));
                listEscColoursDEntity.add(escColoursDEntity);
            }
        }catch (Exception e)
        {
            e.getMessage();
            Log.e("REOS","EscColoursDSQLiteDao-GetEscColours-e:"+e.toString());
        }

        bd.close();
        return listEscColoursDEntity;
    }

}

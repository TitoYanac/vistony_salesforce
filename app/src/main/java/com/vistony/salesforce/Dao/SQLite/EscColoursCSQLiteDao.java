package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ComisionesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursCEntity;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;


public class EscColoursCSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<EscColoursCEntity> listEscColoursCEntity;

    public EscColoursCSQLiteDao(Context context)
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

    public int InsertEscColoursC (List<EscColoursCEntity> escColoursCEntity)
    {
        abrir();

        for (int i = 0; i < escColoursCEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("id",escColoursCEntity.get(i).getId());
            registro.put("description",escColoursCEntity.get(i).getScale());
            registro.put("status",escColoursCEntity.get(i).getStatus());
            bd.insert("esc_colours_c",null,registro);
        }

        bd.close();
        return 1;
    }

    public int ClearEscColoursC()
    {
        abrir();
        bd.execSQL("delete from esc_colours_c ");
        bd.close();
        return 1;
    }

    public ArrayList<EscColoursCEntity> GetEscColoursC ()
    {
        listEscColoursCEntity = new ArrayList<EscColoursCEntity>();
        EscColoursCEntity escColoursCEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from esc_colours_c ",null);

        while (fila.moveToNext())
        {
            escColoursCEntity= new EscColoursCEntity();
            escColoursCEntity.setCompania_id (fila.getString(fila.getColumnIndex("compania_id")));
            escColoursCEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            escColoursCEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            escColoursCEntity.setId(fila.getString(fila.getColumnIndex("id")));
            escColoursCEntity.setScale(fila.getString(fila.getColumnIndex("description")));
            escColoursCEntity.setStatus(fila.getString(fila.getColumnIndex("status")));
            listEscColoursCEntity.add(escColoursCEntity);
        }

        bd.close();
        return listEscColoursCEntity;
    }

    public Integer getCountEscColoursC ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from esc_colours_c",null);

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

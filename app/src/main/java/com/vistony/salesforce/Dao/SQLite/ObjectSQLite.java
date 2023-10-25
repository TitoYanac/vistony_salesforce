package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ObjectEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class ObjectSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    Context context;

    public ObjectSQLite(Context context)
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
    public int addObject (List<ObjectEntity> Object)
    {
        abrir();
        for (int i = 0; i < Object.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("Code",Object.get(i).getCode());
            registro.put("Name",Object.get(i).getName());
            bd.insert("object",null,registro);
        }

        bd.close();
        return 1;
    }

    public int clearTableObject()
    {
        abrir();
        bd.execSQL("delete from object ");
        bd.close();
        return 1;
    }

    public Integer getCountObject ()
    {
        Integer resultado=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from object",null);

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

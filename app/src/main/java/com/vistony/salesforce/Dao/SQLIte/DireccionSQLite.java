package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.DireccionClienteSQLiteEntity;

import java.util.ArrayList;

public class DireccionSQLite {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<DireccionClienteSQLiteEntity> listaDireccionClienteSQLiteEntity;

    public DireccionSQLite(Context context)
    {
        sqLiteController = new SQLiteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqLiteController.getDatabaseName() );
        bd = sqLiteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqLiteController.getDatabaseName() );
        sqLiteController.close();
    }


    public int InsertaDireccionCliente (
            String compania_id,
            String cliente_id,
            String domembarque_id,
            String direccion,
            String zona_id,
            String zona,
            String fuerzatrabajo_id,
            String nombrefuerzatrabajo

    )
    {
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("cliente_id",cliente_id);
        registro.put("domembarque_id",domembarque_id);
        registro.put("direccion",direccion);
        registro.put("zona_id",zona_id);
        registro.put("zona",zona);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("nombrefuerzatrabajo",nombrefuerzatrabajo);

        bd.insert("direccioncliente",null,registro);
        bd.close();
        return 1;
    }

    public ArrayList<DireccionClienteSQLiteEntity> ObtenerDireccionCliente (
            String compania_id,
            String cliente_id
    )
    {

        listaDireccionClienteSQLiteEntity = new ArrayList<DireccionClienteSQLiteEntity>();
        DireccionClienteSQLiteEntity direccionClienteSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from direccioncliente where compania_id= '"+compania_id+"' and cliente_id= '"+cliente_id+"'",null);

        while (fila.moveToNext())
        {
            direccionClienteSQLiteEntity= new DireccionClienteSQLiteEntity();
            direccionClienteSQLiteEntity.setCompania_id(fila.getString(0));
            direccionClienteSQLiteEntity.setCliente_id(fila.getString(1));
            direccionClienteSQLiteEntity.setDomembarque_id(fila.getString(2));
            direccionClienteSQLiteEntity.setDireccion(fila.getString(3));
            direccionClienteSQLiteEntity.setZona_id(fila.getString(4));
            direccionClienteSQLiteEntity.setZona(fila.getString(5));
            direccionClienteSQLiteEntity.setFuerzatrabajo_id(fila.getString(6));
            direccionClienteSQLiteEntity.setNombrefuerzatrabajo(fila.getString(7));
            listaDireccionClienteSQLiteEntity.add(direccionClienteSQLiteEntity);
        }

        bd.close();
        return listaDireccionClienteSQLiteEntity;
    }

    public int LimpiarTablaDireccionCliente ()
    {
        abrir();
        bd.execSQL("delete from direccioncliente ");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadDireccionCliente ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from direccioncliente",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();

        return resultado;
    }



}

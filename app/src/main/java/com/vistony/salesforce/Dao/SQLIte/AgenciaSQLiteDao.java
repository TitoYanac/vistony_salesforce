package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;

import java.util.ArrayList;

public class AgenciaSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<AgenciaSQLiteEntity> listaAgenciaSQLiteEntity;

    public AgenciaSQLiteDao(Context context)
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


    public int InsertaAgencia (
            String compania_id,
            String agencia_id,
            String agencia,
            String ubigeo_id,
            String ruc,
            String direccion

    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("agencia_id",agencia_id);
        registro.put("agencia",agencia);
        registro.put("ubigeo_id",ubigeo_id);
        registro.put("ruc",ruc);
        registro.put("direccion",direccion);
        bd.insert("agencia",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<AgenciaSQLiteEntity> ObtenerAgencia ()
    {
        listaAgenciaSQLiteEntity = new ArrayList<AgenciaSQLiteEntity>();
        AgenciaSQLiteEntity agenciaSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from agencia",null);

        while (fila.moveToNext())
        {
            agenciaSQLiteEntity= new AgenciaSQLiteEntity();
            agenciaSQLiteEntity.setCompania_id(fila.getString(0));
            agenciaSQLiteEntity.setAgencia_id(fila.getString(1));
            agenciaSQLiteEntity.setAgencia(fila.getString(2));
            agenciaSQLiteEntity.setUbigeo_id(fila.getString(3));
            agenciaSQLiteEntity.setRuc(fila.getString(4));
            agenciaSQLiteEntity.setDireccion(fila.getString(5));
            listaAgenciaSQLiteEntity.add(agenciaSQLiteEntity);
        }
        bd.close();
        return listaAgenciaSQLiteEntity;
    }

    public ArrayList<AgenciaSQLiteEntity> ObtenerAgencia_porID (String agencia_id)
    {
        listaAgenciaSQLiteEntity = new ArrayList<AgenciaSQLiteEntity>();
        AgenciaSQLiteEntity agenciaSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from agencia where agencia_id='"+agencia_id+"'",null);

        while (fila.moveToNext())
        {
            agenciaSQLiteEntity= new AgenciaSQLiteEntity();
            agenciaSQLiteEntity.setCompania_id(fila.getString(0));
            agenciaSQLiteEntity.setAgencia_id(fila.getString(1));
            agenciaSQLiteEntity.setAgencia(fila.getString(2));
            agenciaSQLiteEntity.setUbigeo_id(fila.getString(3));
            agenciaSQLiteEntity.setRuc(fila.getString(4));
            agenciaSQLiteEntity.setDireccion(fila.getString(5));
            listaAgenciaSQLiteEntity.add(agenciaSQLiteEntity);
        }
        bd.close();
        return listaAgenciaSQLiteEntity;
    }


    public int LimpiarTablaAgencia ()
    {
        abrir();
        bd.execSQL("delete from agencia");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadAgencia ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from agencia",null);

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

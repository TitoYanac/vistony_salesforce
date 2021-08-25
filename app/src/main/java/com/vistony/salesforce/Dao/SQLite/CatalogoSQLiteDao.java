package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.CatalogoSQLiteEntity;

import java.util.ArrayList;

public class CatalogoSQLiteDao {

    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<CatalogoSQLiteEntity> listaCatalogoSQLiteEntity;

    public CatalogoSQLiteDao(Context context)
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


    public int InsertaCatalogo (String compania_id,String catalogo_id,String catalogo,String ruta)
    {
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("catalogo_id",catalogo_id);
        registro.put("catalogo",catalogo);
        registro.put("ruta",ruta);
        bd.insert("catalogo",null,registro);
        bd.close();
        return 1;
    }

    public ArrayList<CatalogoSQLiteEntity> ObtenerCatalogo ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCatalogoSQLiteEntity = new ArrayList<>();
        CatalogoSQLiteEntity catalogoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from catalogo",null);

        while (fila.moveToNext())
        {
            catalogoSQLiteEntity= new CatalogoSQLiteEntity();
            catalogoSQLiteEntity.setCompania_id(fila.getString(0));
            catalogoSQLiteEntity.setCatalogo_id(fila.getString(1));
            catalogoSQLiteEntity.setCatalogo(fila.getString(2));
            catalogoSQLiteEntity.setRuta(fila.getString(3));
            listaCatalogoSQLiteEntity.add(catalogoSQLiteEntity);
        }

        bd.close();
        return listaCatalogoSQLiteEntity;
    }

    public int LimpiarTablaCatalogo ()
    {
        abrir();
        bd.execSQL("delete from catalogo ");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadCatalogos ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from catalogo",null);

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

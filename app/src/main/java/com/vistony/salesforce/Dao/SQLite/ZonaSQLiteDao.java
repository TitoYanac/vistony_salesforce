package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;

import java.util.ArrayList;

public class ZonaSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity;

    public ZonaSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
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


    public int InsertaZona (String zona_id,String compania_id,String fuerzatrabajo_id,String diasemana)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("zona_id",zona_id);
        registro.put("compania_id",compania_id);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("diasemana",diasemana);
        bd.insert("zona",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }
}

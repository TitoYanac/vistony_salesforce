package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;


public class TypeDispatchSQLite {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<TypeDispatchEntity> listTypeDispatchEntity;

    public TypeDispatchSQLite(Context context)
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

    public int AddTypeDispatch (List<TypeDispatchEntity> listTypeDispatchEntity)
    {
        abrir();
        for (int i = 0; i < listTypeDispatchEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("typedispatch_id",listTypeDispatchEntity.get(i).getTypedispatch_id());
            registro.put("typedispatch",listTypeDispatchEntity.get(i).getTypedispatch());
            registro.put("statusupdate",listTypeDispatchEntity.get(i).getStatusupdate());
            bd.insert("typedispatch",null,registro);
        }
        bd.close();
        return 1;
    }

    public ArrayList<TypeDispatchEntity> getTypeDispatch ()
    {
        listTypeDispatchEntity = new ArrayList<TypeDispatchEntity>();
        TypeDispatchEntity typeDispatchEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from typedispatch where statusupdate='Y'",null);

        while (fila.moveToNext())
        {
            typeDispatchEntity= new TypeDispatchEntity();
            typeDispatchEntity.setCompania_id(fila.getString(0));
            typeDispatchEntity.setFuerzatrabajo_id(fila.getString(1));
            typeDispatchEntity.setUsuario_id(fila.getString(2));
            typeDispatchEntity.setTypedispatch_id (fila.getString(3));
            typeDispatchEntity.setTypedispatch (fila.getString(4));
            listTypeDispatchEntity.add(typeDispatchEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listTypeDispatchEntity;
    }

    public int DeleteTableTypeDispatch ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from typedispatch");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public Integer getCountTypeDispatch ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from typedispatch",null);

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

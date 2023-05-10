package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;

public class ReasonFreeTransferSQLiteDao {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ReasonDispatchEntity> listReasonDispatchEntity;

    public ReasonFreeTransferSQLiteDao(Context context)
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

    //public int InsertaBanco (String banco_id,String compania_id,String nombrebanco)
    public int AddReasonFreeTransfer (List<ReasonDispatchEntity> listReasonDispatchEntity)
    {
        abrir();
        for (int i = 0; i < listReasonDispatchEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id", SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id", SesionEntity.usuario_id);
            registro.put("Value",listReasonDispatchEntity.get(i).getReasondispatch_id());
            registro.put("Dscription",listReasonDispatchEntity.get(i).getReasondispatch());
            bd.insert("reasonfreetransfer",null,registro);
        }
        bd.close();
        return 1;
    }

    public ArrayList<ReasonDispatchEntity> getReasonFreeTransfer ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listReasonDispatchEntity = new ArrayList<ReasonDispatchEntity>();
        ReasonDispatchEntity ReasonDispatchEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from reasonfreetransfer",null);

        while (fila.moveToNext())
        {
            ReasonDispatchEntity= new ReasonDispatchEntity();
            ReasonDispatchEntity.setCompania_id(fila.getString(0));
            ReasonDispatchEntity.setFuerzatrabajo_id(fila.getString(1));
            ReasonDispatchEntity.setUsuario_id(fila.getString(2));
            ReasonDispatchEntity.setReasondispatch_id(fila.getString(3));
            ReasonDispatchEntity.setReasondispatch(fila.getString(4));
            listReasonDispatchEntity.add(ReasonDispatchEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listReasonDispatchEntity;
    }
    public int DeleteTableReasonFreeTransfer ()
    {;
        abrir();
        bd.execSQL("delete from reasonfreetransfer");
        bd.close();
        return 1;
    }

    public Integer getCountReasonFreeTransfer ()
    {
        Integer resultado=0;

        try {
            abrir();
            Cursor fila = bd.rawQuery("Select count(compania_id) from reasonfreetransfer",null);

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

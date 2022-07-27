package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;

import java.util.ArrayList;

public class CobranzaDetalleSMSSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    android.content.Context Context;

    public CobranzaDetalleSMSSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        Context=context;
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde" + this.getClass().getName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde" + this.getClass().getName() );
        sqliteController.close();
    }

    public int InsertaCobranzaDetalleSMS (
                                         String recibo
                                        ,String e_signature
                                        ,String chkesignature
                                        ,String phone
                                        ,String compania_id
                                        ,String fuerzatrabajo_id
                                        ,String usuario_id
                                        ,String date
                                        ,String hour
    )
    {
        int resultado;
        abrir();
        try {
            ContentValues registro = new ContentValues();
            registro.put("recibo",recibo);
            registro.put("e_signature",e_signature);
            registro.put("chkesignature",chkesignature);
            registro.put("phone",phone);
            registro.put("compania_id",compania_id);
            registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
            registro.put("usuario_id",usuario_id);
            registro.put("date",date);
            registro.put("hour",hour);
            bd.insert("cobranzadetalleSMS", null, registro);

            resultado=1;
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        bd.close();
        return resultado;
    }
}

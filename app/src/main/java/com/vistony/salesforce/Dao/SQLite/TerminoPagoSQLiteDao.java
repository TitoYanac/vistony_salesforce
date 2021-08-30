package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;

import java.util.ArrayList;

public class TerminoPagoSQLiteDao {

    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<TerminoPagoSQLiteEntity> listaTerminoPagoSQLiteEntity;

    public TerminoPagoSQLiteDao(Context context)
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


    public int InsertaTerminoPago (
            String terminopago_id,
            String compania_id,
            String terminopago,
            //String listaprecio_id
            String contado,
            String dias_vencimiento
    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("terminopago_id",terminopago_id);
        registro.put("compania_id",compania_id);
        registro.put("terminopago",terminopago);
        //registro.put("listaprecio_id",listaprecio_id);
        registro.put("contado",contado);
        registro.put("dias_vencimiento",dias_vencimiento);
        bd.insert("terminopago",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<TerminoPagoSQLiteEntity> ObtenerTerminoPago (String compania_id,String dias_vencimiento)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaTerminoPagoSQLiteEntity = new ArrayList<TerminoPagoSQLiteEntity>();
        TerminoPagoSQLiteEntity terminoPagoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from terminopago " +
                        " where compania_id= '"+compania_id+"' and   (CAST(dias_vencimiento AS INTEGER)) <= (CAST('"+dias_vencimiento+"'AS INTEGER)) "
                        ,null);

        while (fila.moveToNext())
        {
            terminoPagoSQLiteEntity= new TerminoPagoSQLiteEntity();
            terminoPagoSQLiteEntity.setTerminopago_id(fila.getString(1));
            terminoPagoSQLiteEntity.setCompania_id(fila.getString(0));
            terminoPagoSQLiteEntity.setTerminopago(fila.getString(2));
            terminoPagoSQLiteEntity.setContado(fila.getString(3));
            terminoPagoSQLiteEntity.setDias_vencimiento(fila.getString(4));
            listaTerminoPagoSQLiteEntity.add(terminoPagoSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaTerminoPagoSQLiteEntity;
    }

    public int LimpiarTablaTerminoPago ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from terminopago");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int ObtenerCantidadTerminoPago ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from terminopago",null);

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

    public ArrayList<TerminoPagoSQLiteEntity> ObtenerTerminoPagoporID (String terminopago_id,String compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaTerminoPagoSQLiteEntity = new ArrayList<TerminoPagoSQLiteEntity>();
        TerminoPagoSQLiteEntity terminoPagoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery("Select * from terminopago where terminopago_id='"+terminopago_id+"' and compania_id='"+compania_id+"'",null);

        while (fila.moveToNext())
        {
            terminoPagoSQLiteEntity= new TerminoPagoSQLiteEntity();
            terminoPagoSQLiteEntity.setTerminopago_id(fila.getString(1));
            terminoPagoSQLiteEntity.setCompania_id(fila.getString(0));
            terminoPagoSQLiteEntity.setTerminopago(fila.getString(2));
            terminoPagoSQLiteEntity.setContado(fila.getString(3));
            terminoPagoSQLiteEntity.setDias_vencimiento(fila.getString(4));
            listaTerminoPagoSQLiteEntity.add(terminoPagoSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaTerminoPagoSQLiteEntity;
    }
}

package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
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

    private void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqLiteController.getDatabaseName() );
        bd = sqLiteController.getWritableDatabase();
    }

    private void cerrar()
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

    public ArrayList<ListaDireccionClienteEntity> getListAddress(String cliente_id){
        ArrayList<ListaDireccionClienteEntity> LDCliente = new ArrayList<>();

        abrir();
        Cursor fila = bd.rawQuery(
                "SELECT cliente_id,domembarque_id,direccion,zona_id,zona,nombrefuerzatrabajo FROM direccioncliente WHERE cliente_id= '"+cliente_id+"'",null);

        if (fila.moveToFirst()) {
            if (fila.moveToFirst()) {
                do {
                    ListaDireccionClienteEntity ObjDCliente = new ListaDireccionClienteEntity();
                    ObjDCliente.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                    ObjDCliente.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
                    ObjDCliente.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
                    ObjDCliente.setZona_id(fila.getString(fila.getColumnIndex("zona_id")));
                    ObjDCliente.setZona(fila.getString(fila.getColumnIndex("zona")));
                    ObjDCliente.setNombrefuerzatrabajo(fila.getString(fila.getColumnIndex("nombrefuerzatrabajo")));

                    LDCliente.add(ObjDCliente);
                } while (fila.moveToNext());
            }
        }

        bd.close();
        return LDCliente;
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

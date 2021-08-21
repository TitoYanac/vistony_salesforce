package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;

import java.util.ArrayList;

public class ListaPromocionSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<ListaPromocionSQLiteEntity> listaPromocionSQLiteEntity;

    public ListaPromocionSQLiteDao(Context context)
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


    public int InsertaListaPromocion (
            String compania_id,
            String lista_promocion_id,
            String lista_promocion,
            String u_vis_cashdscnt

    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("lista_promocion_id",lista_promocion_id);
        registro.put("lista_promocion",lista_promocion);
        registro.put("U_VIS_CashDscnt",u_vis_cashdscnt);
        bd.insert("listapromocion",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ListaPromocionSQLiteEntity ObtenerListaPromocion (
            String compania_id,
            String lista_promocion_id
    )
    {
        listaPromocionSQLiteEntity = new ArrayList<>();
        ListaPromocionSQLiteEntity ObjListaPromocionSQLiteEntity= new ListaPromocionSQLiteEntity();
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from listapromocion where compania_id= '"+compania_id+"' and lista_promocion_id= '"+lista_promocion_id+"' ",null);

        while (fila.moveToNext())
        {

            ObjListaPromocionSQLiteEntity.setCompania_id(fila.getString(0));
            ObjListaPromocionSQLiteEntity.setLista_promocion_id(fila.getString(1));
            ObjListaPromocionSQLiteEntity.setLista_promocion(fila.getString(2));
            ObjListaPromocionSQLiteEntity.setU_vis_cashdscnt(fila.getString(3));
            //listaPromocionSQLiteEntity.add(ObjListaPromocionSQLiteEntity);
        }
        bd.close();
        //return listaPromocionSQLiteEntity;
        return ObjListaPromocionSQLiteEntity;
    }

    public int LimpiarTablaListaPromocion ()
    {
        abrir();
        bd.execSQL("delete from listapromocion");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadListaPromocion ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from listapromocion",null);

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

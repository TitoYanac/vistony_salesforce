package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderDispatchSheetEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class HeaderDispatchSheetSQLite {
    SqliteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<HojaDespachoCabeceraSQLiteEntity> listaHojaDespachoCabeceraSQLiteEntity;

    public HeaderDispatchSheetSQLite(Context context)
    {
        sqLiteController = new SqliteController(context);
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


    public int InsertHeaderDispatchSheet (
            /*String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String control_id,
            String asistente_id,
            String asistente,
            String placa,
            String marca,
            String pesototal,
            String fechahojadespacho*/
            List<HeaderDispatchSheetEntity> headerDispatchSheetEntity,
            String FechaDespacho

    )
    {
        abrir();
        for (int i = 0; i < headerDispatchSheetEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id",SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("control_id",headerDispatchSheetEntity.get(i).getControl_id());
            registro.put("asistente_id",headerDispatchSheetEntity.get(i).getAsistente_id());
            registro.put("asistente",headerDispatchSheetEntity.get(i).getAsistente());
            registro.put("placa",headerDispatchSheetEntity.get(i).getPlaca());
            registro.put("marca",headerDispatchSheetEntity.get(i).getMarca());
            registro.put("pesototal",headerDispatchSheetEntity.get(i).getPeso_total());
            registro.put("fechahojadespacho",FechaDespacho);
            bd.insert("headerdispatchsheet",null,registro);
        }

        bd.close();
        return 1;
    }

    public ArrayList<HojaDespachoCabeceraSQLiteEntity> getHeaderDispatchSheetforDate (String DispatchDate)
    {

        listaHojaDespachoCabeceraSQLiteEntity = new ArrayList<>();
        HojaDespachoCabeceraSQLiteEntity hojaDespachoCabeceraSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from headerdispatchsheet where fechahojadespacho='"+DispatchDate+"'",null);

        while (fila.moveToNext())
        {
            hojaDespachoCabeceraSQLiteEntity= new HojaDespachoCabeceraSQLiteEntity();
            hojaDespachoCabeceraSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoCabeceraSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoCabeceraSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoCabeceraSQLiteEntity.setAsistente_id(fila.getString(fila.getColumnIndex("asistente_id")));
            hojaDespachoCabeceraSQLiteEntity.setAsistente(fila.getString(fila.getColumnIndex("asistente")));
            hojaDespachoCabeceraSQLiteEntity.setPlaca(fila.getString(fila.getColumnIndex("placa")));
            hojaDespachoCabeceraSQLiteEntity.setMarca(fila.getString(fila.getColumnIndex("marca")));
            hojaDespachoCabeceraSQLiteEntity.setPesototal(fila.getString(fila.getColumnIndex("pesototal")));
            hojaDespachoCabeceraSQLiteEntity.setFechahojadespacho(fila.getString(fila.getColumnIndex("fechahojadespacho")));
            listaHojaDespachoCabeceraSQLiteEntity.add(hojaDespachoCabeceraSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoCabeceraSQLiteEntity;
    }

    public int ClearTableHeaderDispatchDate ()
    {
        abrir();
        bd.execSQL("delete from headerdispatchsheet ");
        bd.close();
        return 1;
    }

    public int getCountHeaderDispatchSheet ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from headerdispatchsheet",null);

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


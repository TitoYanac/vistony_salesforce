package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;

import java.util.ArrayList;

public class RutaFuerzaTrabajoSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<RutaFuerzaTrabajoSQLiteEntity> listaRutaFuerzaTrabajoSQLiteEntity;

    public RutaFuerzaTrabajoSQLiteDao(Context context)
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


    public int InsertaRutaFuerzaTrabajo (
            String compania_id,
            String zona_id,
            String zona,
            String dia,
            String frecuencia,
            String fechainicioruta,
            String estado
    )
    {
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("zona_id",zona_id);
        registro.put("zona",zona);
        registro.put("dia",dia);
        registro.put("frecuencia",frecuencia);
        registro.put("fechainicioruta",fechainicioruta);
        registro.put("estado",estado);
        bd.insert("rutafuerzatrabajo",null,registro);
        bd.close();
        return 1;
    }

    public int LimpiarTablaRutaFuerzaTrabajo ()
    {
        abrir();
        bd.execSQL("delete from rutafuerzatrabajo ");
        bd.close();
        return 1;
    }

    public ArrayList<RutaFuerzaTrabajoSQLiteEntity> ObtenerRutaFuerzaTrabajo ()
    {
        listaRutaFuerzaTrabajoSQLiteEntity = new ArrayList<RutaFuerzaTrabajoSQLiteEntity>();
        RutaFuerzaTrabajoSQLiteEntity rutaFuerzaTrabajoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from rutafuerzatrabajo ",null);

        while (fila.moveToNext())
        {
            rutaFuerzaTrabajoSQLiteEntity= new RutaFuerzaTrabajoSQLiteEntity();
            rutaFuerzaTrabajoSQLiteEntity.setCompania_id(fila.getString(0));
            rutaFuerzaTrabajoSQLiteEntity.setZona_id(fila.getString(1));
            rutaFuerzaTrabajoSQLiteEntity.setZona(fila.getString(2));
            rutaFuerzaTrabajoSQLiteEntity.setDia(fila.getString(3));
            rutaFuerzaTrabajoSQLiteEntity.setFrecuencia(fila.getString(4));
            rutaFuerzaTrabajoSQLiteEntity.setFechainicioruta(fila.getString(5));
            rutaFuerzaTrabajoSQLiteEntity.setEstado(fila.getString(6));
            listaRutaFuerzaTrabajoSQLiteEntity.add(rutaFuerzaTrabajoSQLiteEntity);
        }

        bd.close();
        return listaRutaFuerzaTrabajoSQLiteEntity;
    }

    public int ObtenerCantidadRutaFuerzaTrabajo ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from rutafuerzatrabajo",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

    public ArrayList<RutaFuerzaTrabajoSQLiteEntity> ObtenerRutaFuerzaTrabajoPorFecha (String fechainicioruta)
    {
        listaRutaFuerzaTrabajoSQLiteEntity = new ArrayList<RutaFuerzaTrabajoSQLiteEntity>();
        RutaFuerzaTrabajoSQLiteEntity rutaFuerzaTrabajoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from rutafuerzatrabajo where fechainicioruta='"+fechainicioruta+"'",null);

        while (fila.moveToNext())
        {
            rutaFuerzaTrabajoSQLiteEntity= new RutaFuerzaTrabajoSQLiteEntity();
            rutaFuerzaTrabajoSQLiteEntity.setCompania_id(fila.getString(0));
            rutaFuerzaTrabajoSQLiteEntity.setZona_id(fila.getString(1));
            rutaFuerzaTrabajoSQLiteEntity.setZona(fila.getString(2));
            rutaFuerzaTrabajoSQLiteEntity.setDia(fila.getString(3));
            rutaFuerzaTrabajoSQLiteEntity.setFrecuencia(fila.getString(4));
            rutaFuerzaTrabajoSQLiteEntity.setFechainicioruta(fila.getString(5));
            rutaFuerzaTrabajoSQLiteEntity.setEstado(fila.getString(6));
            listaRutaFuerzaTrabajoSQLiteEntity.add(rutaFuerzaTrabajoSQLiteEntity);
        }

        bd.close();
        return listaRutaFuerzaTrabajoSQLiteEntity;
    }

    public ArrayList<RutaFuerzaTrabajoSQLiteEntity> ObtenerRutaFuerzaTrabajoFechaMenor (String fechainicioruta)
    {
        listaRutaFuerzaTrabajoSQLiteEntity = new ArrayList<RutaFuerzaTrabajoSQLiteEntity>();
        RutaFuerzaTrabajoSQLiteEntity rutaFuerzaTrabajoSQLiteEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from rutafuerzatrabajo  where fechainicioruta<'" + fechainicioruta + "'", null);

            while (fila.moveToNext()) {
                rutaFuerzaTrabajoSQLiteEntity = new RutaFuerzaTrabajoSQLiteEntity();
                rutaFuerzaTrabajoSQLiteEntity.setCompania_id(fila.getString(0));
                rutaFuerzaTrabajoSQLiteEntity.setZona_id(fila.getString(1));
                rutaFuerzaTrabajoSQLiteEntity.setZona(fila.getString(2));
                rutaFuerzaTrabajoSQLiteEntity.setDia(fila.getString(3));
                rutaFuerzaTrabajoSQLiteEntity.setFrecuencia(fila.getString(4));
                rutaFuerzaTrabajoSQLiteEntity.setFechainicioruta(fila.getString(5));
                rutaFuerzaTrabajoSQLiteEntity.setEstado(fila.getString(6));
                listaRutaFuerzaTrabajoSQLiteEntity.add(rutaFuerzaTrabajoSQLiteEntity);
            }
            fila.close();
        }catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaRutaFuerzaTrabajoSQLiteEntity;
    }

    public int ActualizaFechaInicioRuta (String compania_id, String zona_id, String dia,String fechainicioruta)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("fechainicioruta",fechainicioruta);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("rutafuerzatrabajo",registro,"compania_id='"+compania_id+"' and zona_id='"+zona_id+"' and dia='"+dia+"'" ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }
        return resultado;
    }

}

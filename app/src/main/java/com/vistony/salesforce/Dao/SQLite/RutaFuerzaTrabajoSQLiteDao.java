package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.RutaFuerzaTrabajoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.UserEntity;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class RutaFuerzaTrabajoSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<RutaFuerzaTrabajoSQLiteEntity> listaRutaFuerzaTrabajoSQLiteEntity;
    Context context;
    public RutaFuerzaTrabajoSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        this.context=context;
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

    /*
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
    }*/


    public int InsertWorkPath (List<RutaFuerzaTrabajoEntity> rutaFuerzaTrabajoEntity)
    {
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
        UsuarioSQLiteEntity usuarioSQLiteEntity=new UsuarioSQLiteEntity();
        usuarioSQLiteEntity=usuarioSQLite.ObtenerUsuarioSesion();
        abrir();
        try {
            Log.e("REOS", "RutaFuerzaTrabajoSQLiteDao.InsertWorkPath.rutaFuerzaTrabajoEntity.size(): " + rutaFuerzaTrabajoEntity.size());
            ContentValues registro = new ContentValues();
            for (int i = 0; i < rutaFuerzaTrabajoEntity.size(); i++) {
                registro.put("compania_id",usuarioSQLiteEntity.getCompania_id());
                registro.put("zona_id",rutaFuerzaTrabajoEntity.get(i).getZona_id());
                registro.put("zona",rutaFuerzaTrabajoEntity.get(i).getZona());
                registro.put("dia",rutaFuerzaTrabajoEntity.get(i).getDia());
                registro.put("frecuencia",rutaFuerzaTrabajoEntity.get(i).getFrecuencia());
                registro.put("fechainicioruta",rutaFuerzaTrabajoEntity.get(i).getFechainicioruta());
                registro.put("estado",rutaFuerzaTrabajoEntity.get(i).getEstado());
                bd.insert("rutafuerzatrabajo", null, registro);
            }
        }
        catch (Exception e){
            Log.e("REOS", "RutaFuerzaTrabajoSQLiteDao.InsertWorkPath.e: " + e.toString());
        }
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
        Log.e(
                "REOS",
                "RutaFuerzaTrabajoSQLiteDao-ObtenerRutaFuerzaTrabajo-inicia");
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
        Log.e(
                "REOS",
                "RutaFuerzaTrabajoSQLiteDao-ObtenerRutaFuerzaTrabajo-listaRutaFuerzaTrabajoSQLiteEntity.size()"+listaRutaFuerzaTrabajoSQLiteEntity.size());
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
        }catch (Exception e){
            e.printStackTrace();
        }
        bd.close();
        return resultado;
    }

    public ArrayList<RutaFuerzaTrabajoSQLiteEntity> ObtenerRutaFuerzaTrabajoPorFecha ()
    {
        listaRutaFuerzaTrabajoSQLiteEntity = new ArrayList<RutaFuerzaTrabajoSQLiteEntity>();
        RutaFuerzaTrabajoSQLiteEntity rutaFuerzaTrabajoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from rutafuerzatrabajo where fechainicioruta= strftime('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))"
                ,null);
        Log.e("REOS", "RutaFuerzaTrabajoSQLiteDao-ObtenerRutaFuerzaTrabajoPorFecha-fila.toString(): "+fila.toString());
        Log.e("REOS", "RutaFuerzaTrabajoSQLiteDao-ObtenerRutaFuerzaTrabajoPorFecha-rawQuery.size(): "+bd.toString());
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
        Log.e("REOS", "RutaFuerzaTrabajoSQLiteDao-ObtenerRutaFuerzaTrabajoPorFecha-listaRutaFuerzaTrabajoSQLiteEntity.size(): "+listaRutaFuerzaTrabajoSQLiteEntity.size());
        bd.close();
        return listaRutaFuerzaTrabajoSQLiteEntity;
    }

    public ArrayList<RutaFuerzaTrabajoSQLiteEntity> ObtenerRutaFuerzaTrabajoFechaMenor ()
    {
        listaRutaFuerzaTrabajoSQLiteEntity = new ArrayList<RutaFuerzaTrabajoSQLiteEntity>();
        RutaFuerzaTrabajoSQLiteEntity rutaFuerzaTrabajoSQLiteEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from rutafuerzatrabajo  where fechainicioruta<" +
                     //       "date('now','localtime')"
                    " strftime ('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))"
                    , null);

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
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("rutafuerzatrabajo",registro,"compania_id='"+compania_id+"' and zona_id='"+zona_id+"' and dia='"+dia+"'" ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }
        return resultado;
    }

    public String ObtenerZonaRutaFuerzaTrabajo (String fecha)
    {
        String resultado="";

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select zona from rutafuerzatrabajo where fechainicioruta='"+fecha+"'",null);

            while (fila.moveToNext())
            {
                resultado= (fila.getString(0));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        bd.close();
        return resultado;
    }

    public ArrayList<String>  getDayWorkPath ()
    {
        try {
            ArrayList<String> days = new  ArrayList<String>();
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select dia from rutafuerzatrabajo group by dia",null);

            if (fila.moveToFirst()) {
                do {
                    days.add(fila.getString(fila.getColumnIndex("dia")));
                } while (fila.moveToNext());
            }
            bd.close();
            return days;
        }catch (Exception e){
            e.printStackTrace();
            bd.close();
            return null;
        }
    }

    public String getDateWorkPathforZone (String zone_id,String cliente_id,String domembarque_id)
    {
        String resultado="",frecuenciastring="",fechainicioruta="",diaruta="";
        abrir();
        DireccionSQLite direccionSQLite=new DireccionSQLite(context);

        try {
            frecuenciastring=direccionSQLite.getDayAddress(cliente_id,domembarque_id);
            Log.e("REOS","RutaFuerzaTrabajoSQLiteDao-getDateWorkPathforZone-frecuencia"+frecuenciastring.toString());
            Log.e("REOS","RutaFuerzaTrabajoSQLiteDao-getDateWorkPathforZone-zone_id"+zone_id.toString());

            Log.e("REOS","RutaFuerzaTrabajoSQLiteDao-getDateWorkPathforZone-frecuenciaupdate"+frecuenciastring.toString());
            Cursor filafechainicioruta = bd.rawQuery(
                    "Select  fechainicioruta,dia from rutafuerzatrabajo where zona_id='"+zone_id+"'",null);
            while (filafechainicioruta.moveToNext())
            {
                fechainicioruta= (filafechainicioruta.getString(0));
                diaruta= (filafechainicioruta.getString(1));
            }

            if(diaruta.equals("VIERNES")||diaruta.equals("VIERNES2")||diaruta.equals("SABADO")||diaruta.equals("SABAD02"))
            {
                frecuenciastring=String.valueOf(Integer.valueOf(frecuenciastring)+1) ;
            }
            frecuenciastring="'+"+ frecuenciastring+" day'";

            fechainicioruta= Induvis.getDate(BuildConfig.FLAVOR,fechainicioruta);
            fechainicioruta="'"+fechainicioruta+"'";

            Log.e("REOS","RutaFuerzaTrabajoSQLiteDao-getDateWorkPathforZone-fechainicioruta"+fechainicioruta);
            Cursor fila = bd.rawQuery(
                    "Select  DATE("+fechainicioruta+","+frecuenciastring+") AS fecha from rutafuerzatrabajo where zona_id='"+zone_id+"'",null);
            while (fila.moveToNext())
            {
                resultado= (fila.getString(0));

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","RutaFuerzaTrabajoSQLiteDao-getDateWorkPathforZone-error"+e.toString());
        }
        bd.close();
        Log.e("REOS","RutaFuerzaTrabajoSQLiteDao-getDateWorkPathforZone-resultado"+resultado.toString());
        return resultado;
    }

}

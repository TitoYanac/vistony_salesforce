package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.ParametrosSQLiteEntity;

import java.util.ArrayList;

public class ParametrosSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ParametrosSQLiteEntity> listaParametrosSQLiteEntity;

    public ParametrosSQLite(Context context)
    {
        sqliteController = new SqliteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde " + this.getClass().getName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + this.getClass().getName());
        sqliteController.close();
    }


    public int InsertaParametros (
            String id,
            String nombreparametro,
            String cantidadregistros,
            String fechacarga
            //,
            //String hash
    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("parametro_id",id);
        registro.put("nombreparametro",nombreparametro);
        registro.put("cantidadregistros",cantidadregistros);
        registro.put("fechacarga",fechacarga);
        //registro.put("hash",hash);
        bd.insert("parametros",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int LimpiarParametros ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from parametros ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<ParametrosSQLiteEntity> ObtenerParametros ()
    {
        listaParametrosSQLiteEntity = new ArrayList<ParametrosSQLiteEntity>();
        ParametrosSQLiteEntity parametrosSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from parametros ",null);

        while (fila.moveToNext())
        {
            parametrosSQLiteEntity= new ParametrosSQLiteEntity();
            parametrosSQLiteEntity.setId(fila.getString(0));
            parametrosSQLiteEntity.setNombreparametro(fila.getString(1));
            parametrosSQLiteEntity.setCantidadregistros(fila.getString(2));
            parametrosSQLiteEntity.setFechacarga(fila.getString(3));
            listaParametrosSQLiteEntity.add(parametrosSQLiteEntity);
        }

        bd.close();
        return listaParametrosSQLiteEntity;
    }

    public int ActualizaCantidadRegistros (
            String id,
            String nombreparametro,
            String cantidad,
            String fechacarga
            //,
            //String hash
    )
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            //registro.put("id",id);
            registro.put("cantidadregistros",cantidad);
            registro.put("fechacarga",fechacarga);
            //registro.put("hash",hash);
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("parametros",registro,"parametro_id='"+id+"'"//+" and nombreparametro='"+nombreparametro+"'"
                    ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;

        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }


    public int ObtenerCantidadParametroID (String id)
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(parametro_id) from parametros where parametro_id='"+id+"' ",null);

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

    public String getDateParemeterforName (String name)
    {
        String resultado="";
        abrir();
        Cursor fila = bd.rawQuery(
                "Select substr(fechacarga, 1, instr(fechacarga, ' ')-1) as date from parametros  where nombreparametro='"+name+"' and cantidadregistros>0 ",null);

        while (fila.moveToNext())
        {
            resultado= (fila.getString(0));
        }

        bd.close();
        return resultado;
    }

    public String getDateTimeParemeterforName (String name)
    {
        String resultado="";
        abrir();
        Log.e("REOS", "ParametrosSQLite-getDateTimeParemeterforName-name: "+name);
        Cursor fila = bd.rawQuery(
                "Select fechacarga as date from parametros  where nombreparametro='"+name+"' and cantidadregistros>0 ",null);

        while (fila.moveToNext())
        {
            resultado= (fila.getString(0));
        }
        Log.e("REOS", "ParametrosSQLite-getDateTimeParemeterforName-resultado: "+resultado);
        bd.close();
        return resultado;
    }


}

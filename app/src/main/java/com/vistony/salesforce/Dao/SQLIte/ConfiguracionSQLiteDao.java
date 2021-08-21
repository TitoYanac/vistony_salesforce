package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;

import java.util.ArrayList;

public class ConfiguracionSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLiteEntity;

    public ConfiguracionSQLiteDao(Context context)
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


    public int InsertaConfiguracion (String papel,String tamanio,String totalrecibos,String secuenciarecibos,
                                     String modeloimpresora,String direccionimpresora,String tipoimpresora,String vinculaimpresora)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("papel",papel);
        registro.put("tamanio",tamanio);
        registro.put("totalrecibos",totalrecibos);
        registro.put("secuenciarecibos",secuenciarecibos);
        registro.put("modeloimpresora",modeloimpresora);
        registro.put("direccionimpresora",direccionimpresora);
        registro.put("tipoimpresora",tipoimpresora);
        registro.put("vinculaimpresora",vinculaimpresora);
        bd.insert("configuracion",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int LimpiarTablaConfiguracion ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from configuracion");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<ConfiguracionSQLEntity> ObtenerConfiguracion ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaConfiguracionSQLiteEntity = new ArrayList<ConfiguracionSQLEntity>();
        ConfiguracionSQLEntity configuracionSQLEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from configuracion",null);

        while (fila.moveToNext())
        {
            configuracionSQLEntity= new ConfiguracionSQLEntity();
            configuracionSQLEntity.setPapel(fila.getString(0));
            configuracionSQLEntity.setTamanio(fila.getString(1));
            configuracionSQLEntity.setTotalrecibos(fila.getString(2));
            configuracionSQLEntity.setSecuenciarecibos(fila.getString(3));
            configuracionSQLEntity.setModeloimpresora(fila.getString(4));
            configuracionSQLEntity.setDireccionimpresora(fila.getString(5));
            configuracionSQLEntity.setTipoimpresora(fila.getString(6));
            configuracionSQLEntity.setVinculaimpresora(fila.getString(7));
            listaConfiguracionSQLiteEntity.add(configuracionSQLEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaConfiguracionSQLiteEntity;
    }


    public ArrayList<ConfiguracionSQLEntity> ObtenerCorrelativoConfiguracion ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaConfiguracionSQLiteEntity = new ArrayList<ConfiguracionSQLEntity>();
        ConfiguracionSQLEntity configuracionSQLEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select secuenciarecibos from configuracion",null);

        while (fila.moveToNext())
        {
            configuracionSQLEntity= new ConfiguracionSQLEntity();
            configuracionSQLEntity.setSecuenciarecibos(fila.getString(0));
            listaConfiguracionSQLiteEntity.add(configuracionSQLEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaConfiguracionSQLiteEntity;
    }
    public int ActualizaCorrelativo (String correlativo)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("secuenciarecibos",correlativo);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("configuracion",registro,null,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

    public int ActualizaVinculo (String vinculo)
    {
        int resultado=0;
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("vinculaimpresora",vinculo);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("configuracion",registro,null,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

}

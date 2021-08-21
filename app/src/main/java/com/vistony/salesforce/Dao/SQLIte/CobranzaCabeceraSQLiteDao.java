package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.View.MenuView;

import java.util.ArrayList;

public class CobranzaCabeceraSQLiteDao {

    private MenuView menuView;
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity;

    public CobranzaCabeceraSQLiteDao(Context context)
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

    public int InsertaCobranzaCabecera (String cobranza_id,String usuario_id,String cobrador_id,String banco_id,String compania_id
            ,String totalmontocobrado, String tipoingreso,String chkbancarizado,String fechadiferido,String fechadeposito,String depositodirecto,String pagopos)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        int resultado=0;
        try {

            abrir();
            ContentValues registro = new ContentValues();
            registro.put("cobranza_id",cobranza_id);
            registro.put("usuario_id",usuario_id);
            registro.put("banco_id",banco_id);
            registro.put("compania_id",compania_id);
            registro.put("totalmontocobrado",totalmontocobrado);
            registro.put("fuerzatrabajo_id",cobrador_id);
            registro.put("chkdepositado","1");
            registro.put("chkanulado","0");
            registro.put("tipoingreso",tipoingreso);
            registro.put("chkbancarizado",chkbancarizado);
            registro.put("fechadiferido",fechadiferido);
            registro.put("chkwsrecibido","0");
            registro.put("fechadeposito",fechadeposito);
            registro.put("comentarioanulado","");
            registro.put("chkwsanulado","0");
            registro.put("pagodirecto",depositodirecto);
            registro.put("pagopos",pagopos);


            bd.insert("cobranzacabecera",null,registro);
            bd.close();
            resultado=1;

        }catch (Exception e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }

    public int AnularCobranzaCabecera (String cobranza_id,String compania_id,String fuerzatrabajo_id,String comentarioanulado,String chkwsanulado)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkanulado","1");
            registro.put("chkwsanulado",chkwsanulado);
            registro.put("comentarioanulado",comentarioanulado);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
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


    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraporID (String id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera " +
                            " where cobranza_id= '"+id+"'"  //+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  //and chkanulado='0' "// +
                    //"and chkqrvalidado='1'"
                    ,null);

            while (fila.moveToNext())
            {

                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

           // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

    public int ActualizarCobranzaCabeceraWS (String cobranza_id,String compania_id,String fuerzatrabajo_id,String chkwsrecibido)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsrecibido",chkwsrecibido);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
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


    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraPendientesWS (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera where chkwsrecibido= '0' " //+
                    +" and compania_id='"+compania_id+"' and usuario_id='"+usuario_id+"'"// +
                    //"and chkqrvalidado='1'"
                    ,null);

            while (fila.moveToNext())
            {

                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

    public int ActualizarAnuladoCobranzaCabeceraWS (String cobranza_id,String compania_id,String usuario_id,String chkwsanulado)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsanulado",chkwsanulado);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
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

    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraPendientesAnulacionWS (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera where chkwsanulado= '0' " //+
                            +" and compania_id='"+compania_id+"' and usuario_id='"+usuario_id+"'"// +
                    +"and chkanulado='1'"
                    ,null);

            while (fila.moveToNext())
            {

                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }


    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerHistoricoDepostito (String compania_id,String usuario_id,String fechaini,String fechafin)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase()
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;

        try {
            abrir();
            String query="Select * from cobranzacabecera where fechadeposito>='"+fechaini+"'"
                    +" and compania_id='"+compania_id+"' and usuario_id='"+usuario_id+"'"// +
                    +"and fechadeposito<='"+fechafin+"'";
                    Log.e("jpcm",query);
            Cursor fila = bd.rawQuery(query ,null);

            while (fila.moveToNext())
            {

                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            // bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

    public int UpdateCobranzaCabecera (String cobranza_id,String cobranza_id_update,String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        int resultado=0;
        try {

            ContentValues registro = new ContentValues();
            registro.put("cobranza_id",cobranza_id_update);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzacabecera",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'"  ,null);

            /*Cursor fila = bd.rawQuery(
                    "update cobranzadetalle set cobranza_id = '"+cobranza_id+ "'" +
                            //+ ",chkdepositado = '"+chkdepositado+"'"+
                            " where recibo= '"+recibo+"'"
                            //+ " and compania_id= '"+compania_id+"'"
                    ,null);
            */

            //resultado=1;
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

    public ArrayList<CobranzaCabeceraSQLiteEntity> ObtenerCobranzaCabeceraPendientesEnvioTotalWS (String compania_id, String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaCabeceraSQLiteEntity = new ArrayList<CobranzaCabeceraSQLiteEntity>();
        CobranzaCabeceraSQLiteEntity cobcabeceraentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzacabecera" +
                            " where chkwsrecibido='0' " +
                            " and compania_id='"+compania_id+"'" +
                            " and usuario_id='"+usuario_id+"' and chkanulado='0'"
                    ,null);

            while (fila.moveToNext())
            {
                cobcabeceraentity= new CobranzaCabeceraSQLiteEntity();
                cobcabeceraentity.setCobranza_id(fila.getString(0));
                cobcabeceraentity.setUsuario_id(fila.getString(1));
                cobcabeceraentity.setBanco_id(fila.getString(2));
                cobcabeceraentity.setCompania_id(fila.getString(3));
                cobcabeceraentity.setTotalmontocobrado(fila.getString(4));
                cobcabeceraentity.setChkdepositado(fila.getString(5));
                cobcabeceraentity.setChkanulado(fila.getString(6));
                cobcabeceraentity.setFuerzatrabajo_id(fila.getString(7));
                cobcabeceraentity.setTipoingreso(fila.getString(8));
                cobcabeceraentity.setChkbancarizado(fila.getString(9));
                cobcabeceraentity.setFechadiferido(fila.getString(10));
                cobcabeceraentity.setChkwsrecibido(fila.getString(11));
                cobcabeceraentity.setFechadeposito(fila.getString(12));
                cobcabeceraentity.setComentarioanulado(fila.getString(13));
                cobcabeceraentity.setPagodirecto(fila.getString(17));
                cobcabeceraentity.setPagopos(fila.getString(18));
                listaCobranzaCabeceraSQLiteEntity.add(cobcabeceraentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaCobranzaCabeceraSQLiteEntity;
    }

}

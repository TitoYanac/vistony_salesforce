package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.View.MenuView;

import java.util.ArrayList;

public class CobranzaDetalleSQLiteDao {
    private MenuView menuView;
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity;

    public CobranzaDetalleSQLiteDao(Context context)
    {
        sqLiteController = new SQLiteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde" + this.getClass().getName() );
        bd = sqLiteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde" + this.getClass().getName() );
        sqLiteController.close();
    }
    public String ObtenerCantidadCobranzaDetalle (String usuario_id,String Compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        String resultado="";
        abrir();
        try {

            Cursor fila = bd.rawQuery(
                    "Select IFNULL(COUNT(recibo),0) cantidad from cobranzadetalle " ,null);

            while (fila.moveToNext())
            {
                resultado=fila.getString(0);
            }


        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return resultado;
    }
    public int InsertaCobranzaDetalle (String cobranza_id,
                                       String cliente_id,
                                       String documento_id,
                                       String compania_id,
                                       String importedocumento,
                                       String saldodocumento,
                                       String nuevosaldodocumento,
                                       String saldocobrado,
                                       String fechacobranza,
                                       String recibo,
                                       String nrofactura,
                                       String fuerzatrabajo_id,
                                       String bancarizado,
                                       String motivoanulacion,
                                       String usuario_id,
                                       String comentario,
                                       String chkdepositado,
                                       String chkqrvalidado,
                                       String banco_id,
                                       String chkwsrecibido,
                                       String chkwsqrvalidado,
                                       String chkwsdepositorecibido,
                                       String pagodirecto,
                                       String pagopos
    )
    {
        int resultado;
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {
            ContentValues registro = new ContentValues();
            registro.put("cobranza_id", cobranza_id);
            registro.put("cliente_id", cliente_id);
            registro.put("documento_id", documento_id);
            registro.put("compania_id", compania_id);
            registro.put("importedocumento", importedocumento);
            registro.put("saldodocumento", saldodocumento);
            registro.put("nuevosaldodocumento", nuevosaldodocumento);
            registro.put("saldocobrado", saldocobrado);
            registro.put("fechacobranza", fechacobranza);
            registro.put("recibo", recibo);
            registro.put("nrofactura", nrofactura);
            registro.put("chkdepositado",chkdepositado);
            registro.put("chkqrvalidado",chkqrvalidado);
            registro.put("chkanulado","0");
            registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
            registro.put("chkbancarizado",bancarizado);
            registro.put("motivoanulacion",motivoanulacion);
            registro.put("usuario_id",usuario_id);
            registro.put("chkwsrecibido",chkwsrecibido);
            registro.put("banco_id",banco_id);
            registro.put("chkwsdepositorecibido",chkwsdepositorecibido);
            registro.put("chkwsqrvalidado",chkwsqrvalidado);
            registro.put("comentario",comentario);
            registro.put("chkwsupdate","0");
            registro.put("pagodirecto",pagodirecto);
            registro.put("pagopos",pagopos);
            bd.insert("cobranzadetalle", null, registro);

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

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleporFecha (String fecha,String compania_id,String usuario_id,String recibos_agregados,String fuerzatrabajo_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            String query="Select * from cobranzadetalle" +
                    " where fechacobranza LIKE '"+fecha+"%'" +" and chkdepositado='0' and chkanulado='0' and usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"' and recibo not in ("+recibos_agregados+")"
                    + " and chkqrvalidado='1' and fuerzatrabajo_id='"+fuerzatrabajo_id+"'";

            Log.e("jpcm","print query-> "+query);
            Cursor fila = bd.rawQuery(query ,null);

            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public int ActualizaCobranzaDetalle (String cobranza_id, String recibo, String compania_id,String banco_id)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("cobranza_id",cobranza_id);
            registro.put("chkdepositado","1");
            registro.put("banco_id",banco_id);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'" ,null);

            resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        bd.close();
        return resultado;
    }


    public int ObtenerID ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        int id=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select max(id) maximo from cobranzadetalle",null);

            while (fila.moveToNext())
            {
                id = Integer.parseInt(fila.getString(0));

            }


        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }

        bd.close();
        return id;
    }


    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleporRecibo (String Recibo,String Compania_id,String fuerzatrabajo_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where recibo= '"+Recibo+"'" +" and compania_id='"+Compania_id+"' and fuerzatrabajo_id='"+fuerzatrabajo_id+"'",null);

            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setMotivoanulacion(fila.getString(17));
                cobdetalleentity.setUsuario_id(fila.getString(18));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setUsuario_id(fila.getString(18));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public int DesvincularCobranzaDetalleconCabecera (String cobranza_id, String fuerzatrabajo_id, String compania_id)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("cobranza_id","1");
            registro.put("chkdepositado","0");
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"cobranza_id='"+cobranza_id+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'" ,null);

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

        bd.close();
        return resultado;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleporDeposito (String Deposito_id,String Compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        //abrir();
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where cobranza_id= '"+Deposito_id+"'" +" and compania_id='"+Compania_id+"'",null);

            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setMotivoanulacion(fila.getString(17));
                cobdetalleentity.setUsuario_id(fila.getString(18));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public int AnulaCobranzaDetalle (String recibo, String compania_id,String fuerzatrabajo_id)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkanulado","1");
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'" ,null);

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

        bd.close();
        return resultado;
    }

    public int ActualizaValidacionQRCobranzaDetalle (String recibo, String compania_id,String fuerzatrabajo_id)
    {
        int resultado=0;
        String chkdepositado="1";

        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkqrvalidado","1");
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and fuerzatrabajo_id='"+fuerzatrabajo_id+"'" ,null);
            bd.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("error",""+e);
            resultado=0;
        }

        return resultado;
    }

    public int ObtenerUltimoRecibo (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        int recibo=0;
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select  max(CAST(recibo AS INTEGER)) ultimorecibo from cobranzadetalle  where compania_id= '"+compania_id+"' and usuario_id='"+usuario_id+"'",null);

            while (fila.moveToNext())
            {
                recibo = Integer.parseInt(fila.getString(0));

            }



            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }

        bd.close();
        return recibo;
    }

    public int VerificaRecibosPendientesDeposito (String compania_id,String fuerzatrabajo_id,String fecha)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        int recibo=0;
        try {
            /*String[] sourceSplitemision2= fecha.split("-");
            String anioemision= sourceSplitemision2[0];
            String mesemision= sourceSplitemision2[1];
            String diaemision= sourceSplitemision2[2];

            String fechaCobro=diaemision+"/"+mesemision+"/"+anioemision+" 00:00:00";*/
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select  count(recibo) cantidad from cobranzadetalle  where compania_id= '"+compania_id+"' " +
                            "and fuerzatrabajo_id='"+fuerzatrabajo_id+"' and (fechacobranza<'"+fecha+"' or fechacobranza<'"+fecha+"') and chkdepositado='0' and chkanulado='0'"
                             ,null);

            while (fila.moveToNext())
            {
                recibo = Integer.parseInt(fila.getString(0));

            }



            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());

        }

        bd.close();
        return recibo;
    }

    public int EliminarRecibo (String compania_id,String recibo,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from cobranzadetalle where compania_id= '"+compania_id+"' and usuario_id='"+usuario_id+"'  and recibo='"+recibo+"'  ");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int ActualizaConexionWSCobranzaDetalle (String recibo, String compania_id,String usuario_id,String chkwsrecibido)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsrecibido",chkwsrecibido);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

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

        bd.close();
        return resultado;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendientesEnvioTotalWS (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where chkwsrecibido='0' " +
                            //" and chkwsdepositorecibido='0'" +
                            //" and chkwsrecibido='1'" +
                            " and compania_id='"+compania_id+"'" +
                            " and usuario_id='"+usuario_id+"'"
                    ,null);

            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setMotivoanulacion(fila.getString(17));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendientesEnvioParcial (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where chkwsrecibido='1' " +
                            " and chkwsdepositorecibido='0'" +
                            " and cobranza_id<>'1'" +
                            " and compania_id='"+compania_id+"'" +
                            " and usuario_id='"+usuario_id+"'"
                    ,null);

            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }


    public int ActualizaConexionWSDepositoCobranzaDetalle (String recibo, String compania_id,String usuario_id,String chkwsdepositorecibido)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsdepositorecibido",chkwsdepositorecibido);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

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

        bd.close();
        return resultado;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleConsultaCobradoFecha (String fecha,String compania_id,String fuerzatrabajo_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where fechacobranza like '"+fecha+"%'" +" and fuerzatrabajo_id= '"+fuerzatrabajo_id+"'" +" and compania_id= '"+compania_id+"' and chkanulado='0'"
                    ,null);
            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleQRValidadoWS (String compania_id,String usuario_id)
    {
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"' and chkwsqrvalidado='0' and chkqrvalidado='1'"
                    // " and chkqrvalidado='1'"
                    //+  " and chkwsrecibido='1'"
                    ,null);
            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public int ActualizaWSQRValidadoCobranzaDetalle (String recibo, String compania_id,String usuario_id,String chkwsqrvalidado)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsqrvalidado",chkwsqrvalidado);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

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

        bd.close();
        return  resultado;
    }


    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleActualizacionPendiente (String usuario_id,String Compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        //abrir();
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id='"+Compania_id+"' and chkwsupdate='1' " ,null);

            while (fila.moveToNext())
            {

                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkdepositado(fila.getString(12));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setUsuario_id(fila.getString(18));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setChkwsdepositorecibido(fila.getString(21));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public int ActualizaWSCobranzaDetalle (String recibo, String compania_id,String usuario_id,String chkwsupdate,String cobranza_id,String banco_id,String chkdepositado,String chkwsdepositorecibido)
    {
        int resultado=0;
        //String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsupdate",chkwsupdate);
            registro.put("cobranza_id",cobranza_id);
            registro.put("chkdepositado",chkdepositado);
            registro.put("chkwsdepositorecibido",chkwsdepositorecibido);
            registro.put("banco_id",banco_id);
            //registro.put("precio",pre);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

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

        bd.close();
        return  resultado;
    }


    public String ValidarCobranzaDetallePendientePorMonto (String Deposito_id,String Compania_id)
    {
        Log.e("jpcm",Deposito_id+"-"+Compania_id);
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        String resultado="";
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(sum(SaldoCobrado),0) TotalSaldoCobrado from cobranzadetalle" +
                            " where cobranza_id= '"+Deposito_id+"'" +" and compania_id='"+Compania_id+"'" ,null);

            while (fila.moveToNext())
            {

                resultado=(fila.getString(0));
            }

            Log.e("jpcm",""+resultado);

            bd.close();
        }catch (Exception e)
        {
           Log.e("jpcm",""+e);
        }

        bd.close();
        return resultado;
    }


    public int ActualizaEstadoCobranzaDetalle (String recibo, String compania_id,String usuario_id,String chkanulado,String motivoanulacion)
    {
        int resultado=0;
        //String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkanulado",chkanulado);
            registro.put("motivoanulacion",motivoanulacion);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }

        bd.close();
        return  resultado;
    }


    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendientePorDepositar (String usuario_id,String Compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        //abrir();
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id='"+Compania_id+"' and chkdepositado='0' and chkanulado='0'" ,null);

            while (fila.moveToNext())
            {

                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkdepositado(fila.getString(12));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setUsuario_id(fila.getString(18));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setChkwsdepositorecibido(fila.getString(21));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendientePorDepositarCajaChica (String usuario_id,String Compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        //abrir();
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id='"+Compania_id+"' and chkwsrecibido='0' and cobranza_id='1'" ,null);

            while (fila.moveToNext())
            {

                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(0));
                cobdetalleentity.setCobranza_id(fila.getString(1));
                cobdetalleentity.setCliente_id(fila.getString(2));
                cobdetalleentity.setDocumento_id(fila.getString(3));
                cobdetalleentity.setCompania_id(fila.getString(4));
                cobdetalleentity.setImportedocumento(fila.getString(5));
                cobdetalleentity.setSaldodocumento(fila.getString(6));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(7));
                cobdetalleentity.setSaldocobrado(fila.getString(8));
                cobdetalleentity.setFechacobranza(fila.getString(9));
                cobdetalleentity.setRecibo(fila.getString(10));
                cobdetalleentity.setNrofactura(fila.getString(11));
                cobdetalleentity.setChkdepositado(fila.getString(12));
                cobdetalleentity.setChkqrvalidado(fila.getString(13));
                cobdetalleentity.setChkbancarizado(fila.getString(16));
                cobdetalleentity.setUsuario_id(fila.getString(18));
                cobdetalleentity.setChkwsrecibido(fila.getString(19));
                cobdetalleentity.setBanco_id(fila.getString(20));
                cobdetalleentity.setChkwsdepositorecibido(fila.getString(21));
                cobdetalleentity.setComentario(fila.getString(23));
                cobdetalleentity.setPagodirecto(fila.getString(27));
                cobdetalleentity.setPagopos(fila.getString(28));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listaCobranzaDetalleSQLiteEntity;
    }


}

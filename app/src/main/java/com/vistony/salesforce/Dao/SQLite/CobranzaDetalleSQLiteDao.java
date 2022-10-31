package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.JSON.CollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.SignatureEntity;
import com.vistony.salesforce.Entity.Retrofit.Respuesta.HistoricoCobranzaEntityResponse;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.sentry.Sentry;

public class CobranzaDetalleSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity;
    Context Context;

    public CobranzaDetalleSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        Context=context;
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos desde" + this.getClass().getName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos desde" + this.getClass().getName() );
        sqliteController.close();
    }
    public String ObtenerCantidadCobranzaDetalle (String usuario_id,String compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        String resultado="";
        abrir();
        try {

            Cursor fila = bd.rawQuery(
                    "Select IFNULL(COUNT(recibo),0) cantidad from cobranzadetalle  where usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"'" ,null);

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
                                       String pagopos,
                                       String sap_code,
                                       String mensajeWS,
                                       String horacobranza,
                                       String cardname,
                                       String codeSMS,
                                       String docentry,
                                       String collectioncheck
                                        ,String e_signature
                                        ,String chkesignature
                                       ,String phone
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
            registro.put("chkanulado","N");
            registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
            registro.put("chkbancarizado",bancarizado);
            registro.put("motivoanulacion",motivoanulacion);
            registro.put("usuario_id",usuario_id);
            registro.put("chkwsrecibido",chkwsrecibido);
            registro.put("banco_id",banco_id);
            registro.put("chkwsdepositorecibido",chkwsdepositorecibido);
            registro.put("chkwsqrvalidado",chkwsqrvalidado);
            registro.put("comentario",comentario);
            registro.put("chkwsupdate","N");
            registro.put("pagodirecto",pagodirecto);
            registro.put("pagopos",pagopos);
            registro.put("sap_code",sap_code);
            registro.put("mensajeWS",mensajeWS);
            registro.put("horacobranza",horacobranza);
            registro.put("countsend","1");
            registro.put("cardname",cardname);
            registro.put("codeSMS",codeSMS);
            registro.put("docentry",docentry);
            registro.put("collectioncheck",collectioncheck);
            registro.put("e_signature",e_signature);
            registro.put("chkesignature",chkesignature);
            registro.put("phone",phone);
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

    public int addCollection (HistoricoCobranzaEntityResponse historicoCobranzaEntityResponse)
    {
        abrir();

        for (int i = 0; i < historicoCobranzaEntityResponse.getHistoricoCobranza().size(); i++) {
            ContentValues registro = new ContentValues();
            Random numAleatorio = new Random();
            int codeSMS = numAleatorio.nextInt(9999 + 1000 + 1) + 1000;
            registro.put("cobranza_id", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDeposito_id());
            registro.put("cliente_id", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCliente_id());
            registro.put("documento_id", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDocumento_id());
            registro.put("compania_id", SesionEntity.compania_id);
            registro.put("importedocumento", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getImportedocumento());
            registro.put("saldodocumento", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getSaldodocumento());
            registro.put("nuevosaldodocumento", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNuevosaldodocumento());
            registro.put("saldocobrado", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getMontocobrado());
            registro.put("fechacobranza", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechacobranza());
            registro.put("recibo", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getRecibo());
            registro.put("nrofactura", historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNro_documento());
            registro.put("chkdepositado","N");
            registro.put("chkqrvalidado",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstadoqr());
            registro.put("chkanulado","N");
            registro.put("fuerzatrabajo_id",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFuerzatrabajo_id());
            registro.put("chkbancarizado",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getBancarizacion());
            registro.put("motivoanulacion",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getMotivoanulacion());
            registro.put("usuario_id",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getUsuario_id());
            registro.put("chkwsrecibido","Y");
            registro.put("banco_id",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getBanco_id());
            registro.put("chkwsdepositorecibido","N");
            registro.put("chkwsqrvalidado",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getEstadoqr());
            registro.put("comentario",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getComentario());
            registro.put("chkwsupdate","N");
            registro.put("pagodirecto",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDepositodirecto());
            registro.put("pagopos",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getPagopos());
            registro.put("sap_code",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getCodesap());
            registro.put("mensajeWS","Recibo Registrado Correctamente");
            registro.put("horacobranza",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getFechacobranza());
            registro.put("cardname",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getNombrecliente());
            registro.put("docentry",historicoCobranzaEntityResponse.getHistoricoCobranza().get(i).getDocentry());
            registro.put("collectioncheck","N");
            registro.put("e_signature","");
            registro.put("chkesignature","N");
            registro.put("codeSMS",codeSMS);
            bd.insert("cobranzadetalle", null, registro);
        }

        bd.close();
        return 1;
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
                    " where fechacobranza LIKE '"+fecha+"%'" +" and chkdepositado='N' and chkanulado='N' " +
                    "and usuario_id= '"+usuario_id+"'" +"" +
                    " and compania_id= '"+compania_id+"' and recibo not in ("+recibos_agregados+")"
                    + " and chkqrvalidado='Y' and fuerzatrabajo_id='"+fuerzatrabajo_id+"'";

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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
                cobdetalleentity.setCardname (fila.getString(33));
                if(fila.getString(36)==null) {
                    cobdetalleentity.setCollectioncheck("N");
                }
                else {
                    cobdetalleentity.setCollectioncheck(fila.getString(36));
                }
                //cobdetalleentity.setCompania_id(fila.getString(3));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return listaCobranzaDetalleSQLiteEntity;
    }

    public int updateStatusCodeSap(String itemId,String code,String induvis_id,String message,String status,String receip){
        int resultado=0;

        try{

            ContentValues registro = new ContentValues();
            registro.put("sap_code",code);
            registro.put("mensajeWS",message);
            registro.put("chkwsrecibido",status);
            bd = sqliteController.getWritableDatabase();
            resultado=bd.update("cobranzadetalle",registro,"recibo=? and compania_id=?" ,new String[]{receip, induvis_id});
            bd.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return resultado;
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
            registro.put("chkdepositado","Y");
            registro.put("banco_id",banco_id);
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
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


    public int UpdateCollectionDeposit (String cobranza_id, String recibo, String compania_id,String banco_id)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-cobranza_id: "+cobranza_id);
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-recibo: "+recibo);
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-compania_id: "+compania_id);
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-banco_id: "+banco_id);
            ContentValues registro = new ContentValues();
            registro.put("chkwsdepositorecibido","Y");
            registro.put("chkwsqrvalidado","Y");
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'" ,null);
            resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-error: "+e.toString());
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


    public String getSapCode(String Recibo,String Compania_id,String fuerzatrabajo_id){
        String rpta="";
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select sap_code from cobranzadetalle where recibo=? and compania_id=? and fuerzatrabajo_id=?",new String[]{Recibo,Compania_id,fuerzatrabajo_id});

            while (fila.moveToNext()){
                rpta=fila.getString(fila.getColumnIndex("sap_code"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return rpta;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetalleporRecibo (String Recibo,String Compania_id,String fuerzatrabajo_id){
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select c.*,(Select doc_entry FROM documentodeuda WHERE documento_id=c.documento_id) AS documentoentry from cobranzadetalle c where recibo=? and compania_id=? and fuerzatrabajo_id=?",new String[]{Recibo,Compania_id,fuerzatrabajo_id});

            while (fila.moveToNext())
            {
                cobdetalleentity= new CobranzaDetalleSQLiteEntity();
                cobdetalleentity.setId(fila.getString(fila.getColumnIndex("id")));
                cobdetalleentity.setCobranza_id(fila.getString(fila.getColumnIndex("cobranza_id")));
                cobdetalleentity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                cobdetalleentity.setDocumento_id(fila.getString(fila.getColumnIndex("documento_id")));
                cobdetalleentity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                cobdetalleentity.setImportedocumento(fila.getString(fila.getColumnIndex("importedocumento")));
                cobdetalleentity.setSaldodocumento(fila.getString(fila.getColumnIndex("saldodocumento")));
                cobdetalleentity.setNuevosaldodocumento(fila.getString(fila.getColumnIndex("nuevosaldodocumento")));
                cobdetalleentity.setSaldocobrado(fila.getString(fila.getColumnIndex("saldocobrado")));
                cobdetalleentity.setFechacobranza(fila.getString(fila.getColumnIndex("fechacobranza")));
                cobdetalleentity.setRecibo(fila.getString(fila.getColumnIndex("recibo")));
                cobdetalleentity.setNrofactura(fila.getString(fila.getColumnIndex("nrofactura")));
                cobdetalleentity.setChkqrvalidado(fila.getString(fila.getColumnIndex("chkqrvalidado")));
                cobdetalleentity.setChkbancarizado(fila.getString(fila.getColumnIndex("chkbancarizado")));
                cobdetalleentity.setMotivoanulacion(fila.getString(fila.getColumnIndex("motivoanulacion")));
                cobdetalleentity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
                cobdetalleentity.setChkwsrecibido(fila.getString(fila.getColumnIndex("chkwsrecibido")));
                cobdetalleentity.setBanco_id(fila.getString(fila.getColumnIndex("banco_id")));
                cobdetalleentity.setComentario(fila.getString(fila.getColumnIndex("comentario")));
                cobdetalleentity.setPagodirecto(fila.getString(fila.getColumnIndex("pagodirecto")));
                cobdetalleentity.setPagopos(fila.getString(fila.getColumnIndex("pagopos")));
                cobdetalleentity.setDocumento_entry(fila.getString(fila.getColumnIndex("documentoentry")));
                cobdetalleentity.setSap_code(fila.getString(fila.getColumnIndex("sap_code")));
                cobdetalleentity.setMensajews(fila.getString(fila.getColumnIndex("mensajeWS")));
                cobdetalleentity.setHoracobranza(fila.getString(fila.getColumnIndex("horacobranza")));
                cobdetalleentity.setCodeSMS(fila.getString(fila.getColumnIndex("codeSMS")));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-e"+e.toString());
        }

        bd.close();
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-listaCobranzaDetalleSQLiteEntity"+listaCobranzaDetalleSQLiteEntity.size());
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
            registro.put("cobranza_id","Y");
            registro.put("chkdepositado","N");
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
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
            registro.put("chkanulado","Y");
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
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

    public int ActualizaValidacionQRCobranzaDetalle (String recibo, String compania_id,String usuario_id)
    {
        int resultado=0;
        String chkdepositado="1";

        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkqrvalidado","Y");
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);
            bd.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","CobranzaDetalleSQliteDao-ActualizaValidacionQRCobranzaDetalle-e"+e);
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

    public int VerificaRecibosPendientesDeposito (String compania_id,String fuerzatrabajo_id) {

        int recibo=0;
        String maxdatedeposit="";
        maxdatedeposit="'-"+SesionEntity.maxDateDeposit+" day'";
        Log.e("REOS", "CobranzaDetalleSQLiteDao-VerificaRecibosPendientesDeposito-maxdatedeposit:" + maxdatedeposit);
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select  count(recibo) cantidad from cobranzadetalle  where compania_id= '"+compania_id+"' " +
                            //Cambio para Peru
                            //--------------------------------
                            //"and fuerzatrabajo_id='"+fuerzatrabajo_id+"' and (fechacobranza< DATE('now','-10 day')) and chkdepositado='0' and chkanulado='0'"
                            " and fuerzatrabajo_id='"+fuerzatrabajo_id+"' " +
                            //"and (fechacobranza< strftime ('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))-"+Induvis.getMaximoDiasDeposito()+") " +
                            "and (fechacobranza< strftime ('%Y',date('now','localtime',"+maxdatedeposit+"))||strftime ('%m',date('now','localtime',"+maxdatedeposit+"))||strftime ('%d',date('now','localtime',"+maxdatedeposit+"))) " +
                            "and chkdepositado='N' and chkanulado='N'"
                    //--------------------------------
                    ,null);

            while (fila.moveToNext()){
                recibo = Integer.parseInt(fila.getString(0));
            }

            bd.close();
        }catch (Exception e){
            Log.e("REOS", "CobranzaDetalleSQLiteDao-VerificaRecibosPendientesDeposito-e: "+e.toString());
            e.printStackTrace();
        }
        Log.e("REOS", "CobranzaDetalleSQLiteDao-VerificaRecibosPendientesDeposito-recibo:" + recibo);

        bd.close();
        return recibo;
    }

    public List<ListaPendingCollectionEntity> getDateandCollections (String compania_id, String fuerzatrabajo_id) {
        List<ListaPendingCollectionEntity> ListaPendingCollectionEntity=new ArrayList<>();
        ListaPendingCollectionEntity ObjlistaPendingCollectionEntity;
        String maxdatedeposit="'-"+SesionEntity.maxDateDeposit+" day'";
        Log.e("REOS", "CobranzaDetalleSQLiteDao-VerificaRecibosPendientesDeposito-maxdatedeposit:" + maxdatedeposit);
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select  count(fechacobranza) cantidad,fechacobranza from cobranzadetalle  where compania_id= '"+compania_id+"' " +
                            //Cambio para Peru
                            //--------------------------------
                            //"and fuerzatrabajo_id='"+fuerzatrabajo_id+"' and (fechacobranza< DATE('now','-10 day')) and chkdepositado='0' and chkanulado='0'"
                            " and fuerzatrabajo_id='"+fuerzatrabajo_id+"' " +
                            //"and (fechacobranza< strftime ('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))-"+Induvis.getMaximoDiasDeposito()+") " +
                            "and (fechacobranza< strftime ('%Y',date('now','localtime',"+maxdatedeposit+"))||strftime ('%m',date('now','localtime',"+maxdatedeposit+"))||strftime ('%d',date('now','localtime',"+maxdatedeposit+"))) " +
                            "and chkdepositado='N' and chkanulado='N' " +
                            "GROUP BY fechacobranza"
                    //--------------------------------
                    ,null);

            while (fila.moveToNext()){
                ObjlistaPendingCollectionEntity=new ListaPendingCollectionEntity();
                ObjlistaPendingCollectionEntity.setCount(fila.getString(0));
                ObjlistaPendingCollectionEntity.setDate(fila.getString(1));
                ListaPendingCollectionEntity.add(ObjlistaPendingCollectionEntity);
            }

            bd.close();
        }catch (Exception e){
            Log.e("REOS", "CobranzaDetalleSQLiteDao-VerificaRecibosPendientesDeposito-e: "+e.toString());
            e.printStackTrace();
        }
        Log.e("REOS", "CobranzaDetalleSQLiteDao-VerificaRecibosPendientesDeposito-recibo:");

        bd.close();
        return ListaPendingCollectionEntity;
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

            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);



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

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendientesEnvioTotalWS (String compania_id,String usuario_id){
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery("Select * from cobranzadetalle where chkwsrecibido=? and compania_id=? and usuario_id=?",new String[]{"N",compania_id,usuario_id});

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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
                listaCobranzaDetalleSQLiteEntity.add(cobdetalleentity);
            }


        }catch (Exception e){
            Sentry.captureMessage(e.getMessage());
        }finally {
            bd.close();
        }

        return listaCobranzaDetalleSQLiteEntity;
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendienteEnvioEstadoDepositados (String compania_id,String usuario_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where chkdepositado='Y' " +
                            " and chkwsdepositorecibido='N'" +
                            " and cobranza_id<>''" +
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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
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
        abrir();
        try {
            ContentValues registro = new ContentValues();
            registro.put("chkwsdepositorecibido",chkwsdepositorecibido);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);
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
                            " where fechacobranza like '"+fecha+"%'" +" and fuerzatrabajo_id= '"+fuerzatrabajo_id+"'" +" and compania_id= '"+compania_id+"' and (chkanulado='N' or chkanulado='0') "
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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
                cobdetalleentity.setCollectioncheck(fila.getString(36));
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

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerCobranzaDetallePendienteEnvioEstadoQR (String compania_id,String usuario_id)
    {
        listaCobranzaDetalleSQLiteEntity = new ArrayList<CobranzaDetalleSQLiteEntity>();
        CobranzaDetalleSQLiteEntity cobdetalleentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"' and chkwsqrvalidado='N' and chkqrvalidado='Y'"
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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
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
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkwsqrvalidado",chkwsqrvalidado);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-ActualizaWSQRValidadoCobranzaDetalle-error: "+e.toString());
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
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id='"+Compania_id+"' and chkwsupdate='Y' " ,null);

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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
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
            bd = sqliteController.getWritableDatabase();
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
            //registro.put("motivoanulacion",motivoanulacion);
            bd = sqliteController.getWritableDatabase();
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
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id='"+Compania_id+"' and chkdepositado='N' and chkanulado='N'" ,null);

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
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id='"+Compania_id+"' and chkwsrecibido='N' and cobranza_id='Y'" ,null);

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
                cobdetalleentity.setSap_code(fila.getString(29));
                cobdetalleentity.setMensajews(fila.getString(30));
                cobdetalleentity.setHoracobranza(fila.getString(31));
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

    public int SincronizarEstadoAnuladoCobranzaDetalle (List<HistoricoCobranzaEntity> listreceipts)
    {
        int resultado=0;
        abrir();
        try {
            for(int i=0;i<listreceipts.size();i++)
            {
                Log.e("REOS","CobranzaDetalleSQLiteDao-SincronizarEstadoAnuladoCobranzaDetalle-listreceipts.get(i).getMotivoanulacion()-"+listreceipts.get(i).getMotivoanulacion());
                Log.e("REOS","CobranzaDetalleSQLiteDao-SincronizarEstadoAnuladoCobranzaDetalle-listreceipts.get(i).getRecibo()"+listreceipts.get(i).getRecibo());
                Log.e("REOS","CobranzaDetalleSQLiteDao-SincronizarEstadoAnuladoCobranzaDetalle-listreceipts.get(i).getCompania_id()-"+ SesionEntity.compania_id);
                Log.e("REOS","CobranzaDetalleSQLiteDao-SincronizarEstadoAnuladoCobranzaDetalle-listreceipts.get(i).getUsuario_id()-"+listreceipts.get(i).getUsuario_id());
                ContentValues registro = new ContentValues();
                registro.put("chkanulado","Y");
                registro.put("motivoanulacion",listreceipts.get(i).getMotivoanulacion());
                bd = sqliteController.getWritableDatabase();
                resultado = bd.update("cobranzadetalle",registro,"recibo='"+listreceipts.get(i).getRecibo()+"'"+" and compania_id='"+SesionEntity.compania_id+"'"+" and usuario_id='"+listreceipts.get(i).getUsuario_id()+"'" ,null);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-SincronizarEstadoAnuladoCobranzaDetalle-Error:-"+e.toString());

            resultado=0;
        }

        bd.close();
        return  resultado;
    }

    public ArrayList<CollectionEntity> ObtenerCobranzaDetallePendientesFormatoJSON (String compania_id, String usuario_id)
    {
        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        String brand = Build.MANUFACTURER;
        String model = Build.MODEL;
        String osVersion = android.os.Build.VERSION.RELEASE;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select   a.cobranza_id as ItemDetail,"  +
                            "a.cliente_id as CardCode, " +
                            "a.documento_id as DocNum, " +
                            "a.importedocumento as DocTotal, " +
                            "a.saldodocumento as Balance, " +
                            "a.nuevosaldodocumento as NewBalance," +
                            "a.saldocobrado as AmountCharged," +
                            "a.fechacobranza as IncomeDate," +
                            "a.recibo as Receip, " +
                            "a.nrofactura as CancelReason, " +
                            "a.chkqrvalidado as QRStatus, " +
                            "a.fuerzatrabajo_id as SlpCode, " +
                            "a.chkbancarizado as Banking, " +
                            "a.usuario_id as UserID, " +
                            "a.banco_id as BankID, " +
                            "substr(a.comentario, 1, 100 ) as Commentary, " +
                            "a.pagodirecto as DirectDeposit, " +
                            "a.pagopos as POSPay, " +
                            "IFNULL(a.horacobranza,'') as IncomeTime, " +
                            "IFNULL(docentry,0 )  AS documentoentry, " +
                            "IFNULL(a.countsend,'1') as countsend," +
                            "IFNULL(a.collectioncheck,'N') as collectioncheck " +
                            " from cobranzadetalle a " +
                            " where (chkwsrecibido='N' or  chkwsrecibido='0') " +
                            //" and chkwsdepositorecibido='0'" +
                            //" and chkwsrecibido='1'" +
                            " and compania_id='"+compania_id+"'" +
                            //" and CAST(IFNULL( a.countsend,'1') AS INTEGER)=1 " +
                            " and usuario_id='"+usuario_id+"'"
                    ,null);

            while (fila.moveToNext())
            {
                String Banking,DepositDirect,PosPay,QRStatus;
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-IniciaLlenadoQuery");
                collectionEntity= new CollectionEntity();
                collectionEntity.setItemDetail(fila.getString(fila.getColumnIndex("ItemDetail")));
                collectionEntity.setCardCode(fila.getString(fila.getColumnIndex("CardCode")));
                collectionEntity.setDocNum(fila.getString(fila.getColumnIndex("DocNum")));
                collectionEntity.setDocTotal(fila.getString(fila.getColumnIndex("DocTotal")));
                collectionEntity.setBalance(fila.getString(fila.getColumnIndex("Balance")));
                collectionEntity.setNewBalance(fila.getString(fila.getColumnIndex("NewBalance")));
                collectionEntity.setAmountCharged(fila.getString(fila.getColumnIndex("AmountCharged")));
                collectionEntity.setIncomeDate(fila.getString(fila.getColumnIndex("IncomeDate")));
                collectionEntity.setReceip(fila.getString(fila.getColumnIndex("Receip")));

                if(fila.getString(fila.getColumnIndex("QRStatus")).equals("1"))
                {
                    QRStatus="Y";
                }
                else if(fila.getString(fila.getColumnIndex("QRStatus")).equals("0"))
                {
                    QRStatus="N";
                }
                else{
                    QRStatus=fila.getString(fila.getColumnIndex("QRStatus"));
                }

                //collectionEntity.setQRStatus(fila.getString(fila.getColumnIndex("QRStatus")));
                collectionEntity.setQRStatus(QRStatus);
                if(fila.getString(fila.getColumnIndex("Banking")).equals("1"))
                {
                    Banking="Y";
                }
                else if(fila.getString(fila.getColumnIndex("Banking")).equals("0"))
                {
                    Banking="N";
                }
                else{
                    Banking=fila.getString(fila.getColumnIndex("Banking"));
                }
                //collectionEntity.setBanking(fila.getString(fila.getColumnIndex("Banking")));
                collectionEntity.setBanking(Banking);
                collectionEntity.setCancelReason(fila.getString(fila.getColumnIndex("CancelReason")));
                collectionEntity.setBankID(fila.getString(fila.getColumnIndex("BankID")));
                collectionEntity.setCommentary(fila.getString(fila.getColumnIndex("Commentary")));
                if(fila.getString(fila.getColumnIndex("DirectDeposit")).equals("1"))
                {
                    DepositDirect="Y";
                }
                else if(fila.getString(fila.getColumnIndex("DirectDeposit")).equals("0"))
                {
                    DepositDirect="N";
                }
                else{
                    DepositDirect=fila.getString(fila.getColumnIndex("DirectDeposit"));
                }
                //collectionEntity.setDirectDeposit(fila.getString(fila.getColumnIndex("DirectDeposit")));
                collectionEntity.setDirectDeposit(DepositDirect);
                if(fila.getString(fila.getColumnIndex("POSPay")).equals("1"))
                {
                    PosPay="Y";
                }
                else if(fila.getString(fila.getColumnIndex("POSPay")).equals("0"))
                {
                    PosPay="N";
                }
                else{
                    PosPay=fila.getString(fila.getColumnIndex("POSPay"));
                }
                //collectionEntity.setPOSPay(fila.getString(fila.getColumnIndex("POSPay")));
                collectionEntity.setPOSPay(PosPay);
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-Induvis.getTimeSAP-IncomeTime: "+(fila.getString(fila.getColumnIndex("IncomeTime"))));
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-Induvis.getTimeSAP: "+Induvis.getTimeSAP(BuildConfig.FLAVOR,fila.getString(fila.getColumnIndex("IncomeTime"))));
                collectionEntity.setIncomeTime(Induvis.getTimeSAP(BuildConfig.FLAVOR,fila.getString(fila.getColumnIndex("IncomeTime"))));
                collectionEntity.setStatus("P");
                collectionEntity.setDeposit("");
                collectionEntity.setUserID(fila.getString(fila.getColumnIndex("UserID")));
                collectionEntity.setSlpCode(fila.getString(fila.getColumnIndex("SlpCode")));
                collectionEntity.setDocEntryFT(fila.getString(fila.getColumnIndex("documentoentry")));
                collectionEntity.setIntent (fila.getString(fila.getColumnIndex("countsend")));
                collectionEntity.setAppVersion(Utilitario.getVersion(Context));
                if(model.length()>50) {
                    collectionEntity.setModel(model.substring(0,49));
                }
                else {
                    collectionEntity.setModel(model);
                }
                collectionEntity.setBrand(brand);
                collectionEntity.setOSVersion(osVersion);
                //if(BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("peru"))
                //{
                    collectionEntity.setCollectionCheck(fila.getString(fila.getColumnIndex("collectioncheck")));
                //}
                listCollectionEntity.add(collectionEntity);

                UpdateCountSend(fila.getString(fila.getColumnIndex("Receip")),ObjUsuario.compania_id,ObjUsuario.usuario_id,fila.getString(fila.getColumnIndex("countsend")));
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-e: "+e.toString());
        }

        bd.close();
        return listCollectionEntity;
    }

    public ArrayList<CollectionEntity> ObtenerCobranzaDetallePendienteEnvioEstadoDepositadosJSON (String compania_id,String usuario_id)
    {

        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select sap_code,cobranza_id,banco_id,recibo,chkqrvalidado,usuario_id from cobranzadetalle" +
                            " where (chkdepositado='Y' or  chkdepositado='1')  " +
                            " and (chkwsdepositorecibido='N'or  chkwsdepositorecibido='0')" +
                            " and cobranza_id<>''" +
                            " and compania_id='"+compania_id+"'  " +
                            " and usuario_id='"+usuario_id+"'   LIMIT 100  "
                    ,null);

            while (fila.moveToNext())
            {
                String QRStatus="";
                collectionEntity= new CollectionEntity();
                collectionEntity.setCode(fila.getString(0));
                collectionEntity.setDeposit(fila.getString(1));
                collectionEntity.setBankID(fila.getString(2));
                collectionEntity.setReceip(fila.getString(3));
                if((fila.getString(4).equals("1")))
                {
                    QRStatus="Y";
                }
                else {
                    QRStatus=fila.getString(4);
                }
                collectionEntity.setQRStatus(QRStatus);
                collectionEntity.setU_VIS_UserID(fila.getString(5));
                listCollectionEntity.add(collectionEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCollectionEntity;
    }
    public ArrayList<CollectionEntity> ObtenerCobranzaDetallePendienteEnvioEstadoQRJSON (String compania_id,String usuario_id)
    {
        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select sap_code,cobranza_id,banco_id,recibo,chkqrvalidado from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"' and chkwsqrvalidado='N' and chkqrvalidado='Y'"
                    ,null);
            while (fila.moveToNext())
            {
                collectionEntity= new CollectionEntity();
                collectionEntity.setCode(fila.getString(0));
                collectionEntity.setDeposit(fila.getString(1));
                collectionEntity.setBankID(fila.getString(2));
                collectionEntity.setReceip(fila.getString(3));
                collectionEntity.setQRStatus(fila.getString(4));
                listCollectionEntity.add(collectionEntity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCollectionEntity;
    }

    public ArrayList<CollectionEntity> UpdateRecibosDesvinculadosDepositoJSON (String compania_id,String usuario_id)
    {

        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select sap_code,cobranza_id,banco_id,recibo,chkqrvalidado from cobranzadetalle" +
                            " where (chkwsupdate='Y' or chkwsupdate='1') " +
                            " and compania_id='"+compania_id+"'" +
                            " and usuario_id='"+usuario_id+"'"
                    ,null);

            while (fila.moveToNext())
            {
                collectionEntity= new CollectionEntity();
                collectionEntity.setCode(fila.getString(0));
                collectionEntity.setDeposit(fila.getString(1));
                collectionEntity.setBankID(fila.getString(2));
                collectionEntity.setReceip(fila.getString(3));
                collectionEntity.setQRStatus(fila.getString(4));
                listCollectionEntity.add(collectionEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCollectionEntity;
    }

    public int UpdateCollectionDetachedDeposit (String cobranza_id, String recibo, String compania_id,String banco_id)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-cobranza_id: "+cobranza_id);
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-recibo: "+recibo);
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-compania_id: "+compania_id);
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-banco_id: "+banco_id);
            ContentValues registro = new ContentValues();
            registro.put("chkwsupdate","N");
            //registro.put("precio",pre);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'" ,null);
            resultado=1;
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS", "CobranzaDetalleSQLiteDao-ActualizaCobranzaDetalle-error: "+e.toString());
            resultado=0;
        }
        bd.close();
        return resultado;
    }

    public int getCountValidateCodeSMS (
            String compania_id,
            String usuario_id,
            String recibo,
            String codesms
            )
    {
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-compania_id"+String.valueOf(compania_id));
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-usuario_id"+String.valueOf(usuario_id));
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-recibo"+String.valueOf(recibo));
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-codesms"+String.valueOf(codesms));

        abrir();
        int resultado=0;
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(count(recibo),0) as cantidad from cobranzadetalle" +
                            " where compania_id='"+compania_id+"'" +
                            " and usuario_id='"+usuario_id+"'" +
                            " and recibo='"+recibo+"'" +
                            " and codeSMS='"+codesms+"'"
                    ,null);

            while (fila.moveToNext())
            {
                resultado=Integer.parseInt(fila.getString(0));
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-e"+e.toString());
        }

        bd.close();
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-resultado"+String.valueOf(resultado));
        return resultado;
    }

    public int UpdateCountSend (String recibo, String compania_id,String usuario_id,String countsend)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("countsend",String.valueOf(Integer.parseInt(countsend)+1));
            bd = sqliteController.getWritableDatabase();
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

    public ArrayList<CollectionEntity> ObtenerCobranzaDetallePendientesFormatoJSONCountSend (String compania_id, String usuario_id)
    {
        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select   a.cobranza_id as ItemDetail,"  +
                            "a.cliente_id as CardCode, " +
                            "a.documento_id as DocNum, " +
                            "a.importedocumento as DocTotal, " +
                            "a.saldodocumento as Balance, " +
                            "a.nuevosaldodocumento as NewBalance," +
                            "a.saldocobrado as AmountCharged," +
                            "a.fechacobranza as IncomeDate," +
                            "a.recibo as Receip, " +
                            "a.nrofactura as CancelReason, " +
                            "a.chkqrvalidado as QRStatus, " +
                            "a.fuerzatrabajo_id as SlpCode, " +
                            "a.chkbancarizado as Banking, " +
                            "a.usuario_id as UserID, " +
                            "a.banco_id as BankID, " +
                            "a.comentario as Commentary, " +
                            "a.pagodirecto as DirectDeposit, " +
                            "a.pagopos as POSPay, " +
                            "IFNULL(a.horacobranza,'') as IncomeTime, " +
                            "IFNULL((Select IFNULL(doc_entry,0 ) FROM documentodeuda WHERE documento_id=a.documento_id),0) AS documentoentry, " +
                            "IFNULL(a.countsend,'1') as countsend " +
                            " from cobranzadetalle a " +
                            " where (chkwsrecibido='N' or  chkwsrecibido='0') " +
                            //" and chkwsdepositorecibido='0'" +
                            //" and chkwsrecibido='1'" +
                            " and compania_id='"+compania_id+"'" +
                            " and CAST(IFNULL( a.countsend,'1') AS INTEGER)>1 " +
                            " and usuario_id='"+usuario_id+"'"
                    ,null);

            while (fila.moveToNext())
            {
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-IniciaLlenadoQuery");
                collectionEntity= new CollectionEntity();
                collectionEntity.setItemDetail(fila.getString(fila.getColumnIndex("ItemDetail")));
                collectionEntity.setCardCode(fila.getString(fila.getColumnIndex("CardCode")));
                collectionEntity.setDocNum(fila.getString(fila.getColumnIndex("DocNum")));
                collectionEntity.setDocTotal(fila.getString(fila.getColumnIndex("DocTotal")));
                collectionEntity.setBalance(fila.getString(fila.getColumnIndex("Balance")));
                collectionEntity.setNewBalance(fila.getString(fila.getColumnIndex("NewBalance")));
                collectionEntity.setAmountCharged(fila.getString(fila.getColumnIndex("AmountCharged")));
                collectionEntity.setIncomeDate(fila.getString(fila.getColumnIndex("IncomeDate")));
                collectionEntity.setReceip(fila.getString(fila.getColumnIndex("Receip")));
                collectionEntity.setQRStatus(fila.getString(fila.getColumnIndex("QRStatus")));
                collectionEntity.setBanking(fila.getString(fila.getColumnIndex("Banking")));
                collectionEntity.setCancelReason(fila.getString(fila.getColumnIndex("CancelReason")));
                collectionEntity.setBankID(fila.getString(fila.getColumnIndex("BankID")));
                collectionEntity.setCommentary(fila.getString(fila.getColumnIndex("Commentary")));
                collectionEntity.setDirectDeposit(fila.getString(fila.getColumnIndex("DirectDeposit")));
                collectionEntity.setPOSPay(fila.getString(fila.getColumnIndex("POSPay")));
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-Induvis.getTimeSAP-IncomeTime: "+(fila.getString(fila.getColumnIndex("IncomeTime"))));
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-Induvis.getTimeSAP: "+Induvis.getTimeSAP(BuildConfig.FLAVOR,fila.getString(fila.getColumnIndex("IncomeTime"))));
                collectionEntity.setIncomeTime(Induvis.getTimeSAP(BuildConfig.FLAVOR,fila.getString(fila.getColumnIndex("IncomeTime"))));
                collectionEntity.setStatus("P");
                collectionEntity.setDeposit("");
                collectionEntity.setUserID(fila.getString(fila.getColumnIndex("UserID")));
                collectionEntity.setSlpCode(fila.getString(fila.getColumnIndex("SlpCode")));
                collectionEntity.setDocEntryFT(fila.getString(fila.getColumnIndex("documentoentry")));
                collectionEntity.setIntent (fila.getString(fila.getColumnIndex("countsend")));
                collectionEntity.setAppVersion(Utilitario.getVersion(Context));
                listCollectionEntity.add(collectionEntity);

                UpdateCountSend(fila.getString(fila.getColumnIndex("Receip")),SesionEntity.compania_id,SesionEntity.usuario_id,fila.getString(fila.getColumnIndex("countsend")));
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-e: "+e.toString());
        }

        bd.close();
        return listCollectionEntity;
    }

    public int getCountCollectionPendingSend (String usuario_id,String compania_id)
    {
        int resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(COUNT(recibo),0) cantidad from cobranzadetalle  where usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"' AND chkwsrecibido='N' " ,null);
            while (fila.moveToNext())
            {
                resultado=Integer.parseInt(fila.getString(0));
            }
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        bd.close();
        return resultado;
    }

    public int UpdateE_Signature (String compania_id,String usuario_id,String recibo,String e_signature)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("e_signature",e_signature);
            bd = sqliteController.getWritableDatabase();
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

    public ArrayList<CollectionEntity> ObtenerCobranzaDetallePendienteEnvioE_SignatureJSON (String compania_id, String usuario_id,Context context)
    {
        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select * from cobranzadetalle" +
                            " where usuario_id= '"+usuario_id+"'" +" and compania_id= '"+compania_id+"' and e_signature<>'' and chkesignature='N'"
                    ,null);
            while (fila.moveToNext())
            {
                collectionEntity= new CollectionEntity();
                collectionEntity.setCardCode(fila.getString(fila.getColumnIndex("CardCode")));
                ClienteSQlite clienteSQlite=new ClienteSQlite(context);
                ArrayList<ClienteSQLiteEntity> listClientes=new ArrayList<>();

                listClientes=clienteSQlite.ObtenerDatosCliente(collectionEntity.getCardCode(),compania_id);
                for(int i=0;i<listClientes.size();i++)
                {
                    collectionEntity.setCardName(listClientes.get(i).getNombrecliente());
                }
                collectionEntity.setSlpCode(fila.getString(fila.getColumnIndex("SlpCode")));
                collectionEntity.setSlpCode(SesionEntity.nombrefuerzadetrabajo);
                collectionEntity.setIncomeDate(fila.getString(fila.getColumnIndex("IncomeDate")));
                collectionEntity.setReceip(fila.getString(fila.getColumnIndex("Receip")));
                collectionEntity.setLegalNumber(fila.getString(fila.getColumnIndex("motivoanulacion")));
                collectionEntity.setDocTotal(fila.getString(fila.getColumnIndex("DocTotal")));
                collectionEntity.setBalance(fila.getString(fila.getColumnIndex("Balance")));
                collectionEntity.setAmountCharged(fila.getString(fila.getColumnIndex("AmountCharged")));
                collectionEntity.setNewBalance(fila.getString(fila.getColumnIndex("NewBalance")));
                collectionEntity.setCodeSMS(fila.getString(fila.getColumnIndex("codeSMS")));
                collectionEntity.setPhone(fila.getString(fila.getColumnIndex("codeSMS")));

                collectionEntity.setDocNum(fila.getString(fila.getColumnIndex("DocNum")));
                collectionEntity.setQRStatus(fila.getString(fila.getColumnIndex("QRStatus")));
                collectionEntity.setBanking(fila.getString(fila.getColumnIndex("Banking")));
                collectionEntity.setCancelReason(fila.getString(fila.getColumnIndex("CancelReason")));
                collectionEntity.setBankID(fila.getString(fila.getColumnIndex("BankID")));
                collectionEntity.setCommentary(fila.getString(fila.getColumnIndex("Commentary")));
                collectionEntity.setDirectDeposit(fila.getString(fila.getColumnIndex("DirectDeposit")));
                collectionEntity.setPOSPay(fila.getString(fila.getColumnIndex("POSPay")));
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-Induvis.getTimeSAP-IncomeTime: "+(fila.getString(fila.getColumnIndex("IncomeTime"))));
                Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetallePendientesFormatoJSON-Induvis.getTimeSAP: "+Induvis.getTimeSAP(BuildConfig.FLAVOR,fila.getString(fila.getColumnIndex("IncomeTime"))));
                collectionEntity.setIncomeTime(Induvis.getTimeSAP(BuildConfig.FLAVOR,fila.getString(fila.getColumnIndex("IncomeTime"))));
                collectionEntity.setStatus("P");
                collectionEntity.setDeposit("");
                collectionEntity.setUserID(fila.getString(fila.getColumnIndex("UserID")));

                collectionEntity.setDocEntryFT(fila.getString(fila.getColumnIndex("documentoentry")));
                collectionEntity.setIntent (fila.getString(fila.getColumnIndex("countsend")));
                listCollectionEntity.add(collectionEntity);
            }
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCollectionEntity;
    }

    public int UpdateDBCollectionE_Signature (String recibo, String compania_id,String usuario_id,String chkesignature)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkesignature",chkesignature);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("cobranzadetalle",registro,"recibo='"+recibo+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            Log.e("REOS","CobranzaDetalleSQLiteDao-UpdateDBCollectionE_Signature-error: "+e.toString());
            resultado=0;
        }
        bd.close();
        return  resultado;
    }

    public String getE_Signature(String Recibo,String Compania_id,String fuerzatrabajo_id){
        String rpta="";
        try {
            abrir();
            Cursor fila = bd.rawQuery(
                    "Select e_signature from cobranzadetalle where recibo=? and compania_id=? and fuerzatrabajo_id=?",new String[]{Recibo,Compania_id,fuerzatrabajo_id});

            while (fila.moveToNext()){
                rpta=fila.getString(fila.getColumnIndex("e_signature"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return rpta;
    }

    public int getCountCollectionDate (
            String date,
            String fuerzatrabajo_id,
            String cardcode
    )
    {
        abrir();
        int resultado=0;
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(count(recibo),0) as cantidad from cobranzadetalle" +
                            " where fuerzatrabajo_id='"+fuerzatrabajo_id+"'" +
                            " and cliente_id='"+cardcode+"'" +
                            " and fechacobranza='"+date+"'"
                    ,null);

            while (fila.moveToNext())
            {
                resultado=Integer.parseInt(fila.getString(0));
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        Log.e("REOS","CobranzaDetalleSQLiteDao-ObtenerCobranzaDetalleporRecibo-resultado"+String.valueOf(resultado));
        return resultado;
    }

    public ArrayList<CollectionEntity> ObtenerCobranzaDetallePendienteEnvioEstadoDepositadosJSONDrivers (String compania_id,String usuario_id)
    {

        ArrayList<CollectionEntity> listCollectionEntity=new ArrayList<>();
        CollectionEntity collectionEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select sap_code,cobranza_id,IFNULL(banco_id,'103117'),recibo,chkqrvalidado,usuario_id from cobranzadetalle" +
                            " where (chkdepositado='Y' or  chkdepositado='1')  " +
                            " and (chkwsdepositorecibido='N'or  chkwsdepositorecibido='0')" +
                            " and cobranza_id<>''" +
                            " and compania_id='"+compania_id+"' " +
                            " and usuario_id='"+usuario_id+"'   LIMIT 100  "
                    ,null);

            while (fila.moveToNext())
            {
                String QRStatus="";
                collectionEntity= new CollectionEntity();
                collectionEntity.setCode(fila.getString(0));
                collectionEntity.setDeposit(fila.getString(1));
                collectionEntity.setBankID(fila.getString(2));
                collectionEntity.setReceip(fila.getString(3));
                if((fila.getString(4).equals("1")))
                {
                    QRStatus="Y";
                }
                else {
                    QRStatus=fila.getString(4);
                }
                collectionEntity.setQRStatus(QRStatus);
                collectionEntity.setU_VIS_UserID(fila.getString(5));
                listCollectionEntity.add(collectionEntity);
            }

            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

        bd.close();
        return listCollectionEntity;
    }

}

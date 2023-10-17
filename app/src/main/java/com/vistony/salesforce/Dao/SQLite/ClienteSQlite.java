package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.sentry.Sentry;

public class ClienteSQlite {
    private ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity;
    private ArrayList<ListaClienteCabeceraEntity> arraylistaClienteSQLiteEntity;

    public ClienteSQlite(Context context){
        DataBaseManager.initializeInstance(new SqliteController(context));
    }

    public int InsertaCliente (List<ClienteSQLiteEntity> Lista){
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        for(int i=0;i<Lista.size();i++){
            ContentValues registro = new ContentValues();
            registro.put("cliente_id",Lista.get(i).getCliente_id());
            registro.put("domfactura_id",Lista.get(i).getDomfactura_id());
            registro.put("domembarque_id",Lista.get(i).getDomembarque_id());
            registro.put("compania_id",Lista.get(i).getCompania_id());
            registro.put("nombrecliente",Lista.get(i).getNombrecliente());
            registro.put("direccion",Lista.get(i).getDireccion());
            registro.put("zona_id",Lista.get(i).getZona_id());
            registro.put("ordenvisita",Lista.get(i).getOrden());
            registro.put("zona",Lista.get(i).getZona());
            registro.put("rucdni",Lista.get(i).getRucdni());
            registro.put("moneda",Lista.get(i).getMoneda());
            registro.put("telefonofijo",Lista.get(i).getTelefonofijo());
            registro.put("telefonomovil",Lista.get(i).getTelefonomovil());
            registro.put("ubigeo_id",Lista.get(i).getUbigeo_id());
            registro.put("impuesto_id", Lista.get(i).getImpuesto_id());
            registro.put("impuesto",Lista.get(i).getImpuesto());
            registro.put("tipocambio",Lista.get(i).getTipocambio());
            registro.put("categoria",Lista.get(i).getCategoria());
            registro.put("linea_credito",Lista.get(i).getLinea_credito());
            registro.put("linea_credito_usado",Lista.get(i).getLinea_credito_usado());
            registro.put("terminopago_id",Lista.get(i).getTerminopago_id());
            registro.put("lista_precio",Lista.get(i).getLista_precio());
            registro.put("DueDays",Lista.get(i).getDueDays());
            registro.put("lineofbusiness",Lista.get(i).getLineofbusiness());
            registro.put("LastPurchase",Lista.get(i).getLastpurchase());
            registro.put("correo",Lista.get(i).getCorreo());
            registro.put("statuscounted",Lista.get(i).getStatuscounted());
            registro.put("customerwhitelist",Lista.get(i).getCustomerwhitelist());
            sqlite.insert("cliente",null,registro);
        }

        DataBaseManager.getInstance().closeDatabase();

        return 1;
    }

    public static String getPaymentGroupCode(String cardCode){
        String terminoPago=null;

        try {

            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor fila = sqlite.rawQuery("SELECT terminopago_id FROM cliente WHERE cliente_id =? LIMIT 1; ",new String[]{cardCode});

            while (fila.moveToNext())
            {
                terminoPago=fila.getString(0);
            }
        }catch (Exception e){
            Sentry.captureMessage(e.getMessage());
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        return terminoPago;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteDeuda (){

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor fila = sqlite.rawQuery(
                "Select " +
                        "b.cliente_id,b.nombrecliente,b.direccion,SUM(saldo),a.moneda,b.domembarque_id,b.impuesto_id,b.impuesto,b.categoria,b.linea_credito,b.linea_credito_usado,b.terminopago_id " +
                        "from documentodeuda a " +
                        "INNER JOIN (Select compania_id,cliente_id,nombrecliente,direccion,domembarque_id,impuesto_id,impuesto,rucdni,categoria,linea_credito,linea_credito_usado,terminopago_id from cliente GROUP BY compania_id,cliente_id,nombrecliente,direccion,domembarque_id,impuesto_id,impuesto,rucdni,categoria,linea_credito,terminopago_id) b ON" +
                        " a.compania_id=b.compania_id " +
                        "and a.cliente_id=b.cliente_id " +
                        "where a.saldo <> '0' and a.saldo>1 "  +
                        "GROUP BY b.cliente_id,b.nombrecliente " +
                        "ORDER BY SUM(saldo)" ,null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setNombrecliente(fila.getString(1));
                clienteentity.setDireccion(fila.getString(2));
                clienteentity.setSaldo(fila.getString(3));
                clienteentity.setMoneda(fila.getString(4));
                clienteentity.setDomembarque_id(fila.getString(5));
                clienteentity.setImpuesto_id(fila.getString(6));
                clienteentity.setImpuesto(fila.getString(7));
                clienteentity.setRucdni(fila.getString(8));
                clienteentity.setCategoria(fila.getString(9));
                clienteentity.setLinea_credito(fila.getString(10));
                clienteentity.setLinea_credito_usado(fila.getString(11));
                clienteentity.setTerminopago_id(fila.getString(12));
                arraylistaClienteSQLiteEntity.add(clienteentity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaClienteSQLiteEntity;
    }

    public ArrayList<ClienteSQLiteEntity> ObtenerDatosCliente (String cliente_id,String compania_id){
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try{
           listaClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
           ClienteSQLiteEntity clienteentity;
           Cursor fila = sqlite.rawQuery(
                   "Select A.cliente_id,A.compania_id,A.nombrecliente,A.direccion,A.rucdni,A.categoria,A.linea_credito,A.linea_credito_usado,A.terminopago_id,B.domembarque_id,A.zona_id,A.domfactura_id,A.telefonofijo,A.telefonomovil,a.correo  from cliente A" +
                           " left outer join direccioncliente B ON" +
                           " A.cliente_id=B.cliente_id" +
                           " where A.cliente_id= '"+cliente_id+"' and A.compania_id= '"+compania_id+"'",null);

           while (fila.moveToNext())
           {
               clienteentity= new ClienteSQLiteEntity();
               clienteentity.setCliente_id(fila.getString(0));
               clienteentity.setCompania_id(fila.getString(1));
               clienteentity.setNombrecliente(fila.getString(2));
               clienteentity.setDireccion(fila.getString(3));
               clienteentity.setRucdni(fila.getString(4));
               clienteentity.setCategoria(fila.getString(5));
               clienteentity.setLinea_credito(fila.getString(6));
               clienteentity.setLinea_credito_usado(fila.getString(7));
               Log.e("REOS","ClienteSQLite.ObtenerDatosCliente.fila.getString(8): "+fila.getString(8));
               clienteentity.setTerminopago_id(fila.getString(8));
               clienteentity.setDomembarque_id(fila.getString(9));
               clienteentity.setZona_id(fila.getString(10));
               clienteentity.setDomfactura_id(fila.getString(11));
               clienteentity.setTelefonofijo(fila.getString(12));
               clienteentity.setTelefonomovil(fila.getString(13));
               clienteentity.setCorreo(fila.getString(14));
               listaClienteSQLiteEntity.add(clienteentity);
           }
       }catch(Exception e){
           e.printStackTrace();
       }finally {
            DataBaseManager.getInstance().closeDatabase();
       }

        return listaClienteSQLiteEntity;
    }

    public int LimpiarTablasNodos(){
        int status=0;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try{
            sqlite.execSQL("DELETE FROM cliente");
            //sqlite.execSQL("DELETE FROM rutavendedor"); analizar para forzar el recalculo cada vez que actulicen aprametros
            sqlite.execSQL("DELETE FROM direccioncliente");
            sqlite.execSQL("DELETE FROM documentodeuda");
            status=1;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return status;
    }

    public int ObtenerCantidadClientes (){
        int resultado=0;
        listaClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select count(cliente_id) from cliente",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return resultado;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteSinDeuda () {

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select " +
                            "a.cliente_id,a.nombrecliente,'',IFNULL(SUM(b.saldo),0),IFNULL(a.moneda,0),a.rucdni,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id " +
                            "from cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                            "where b.saldo is null "  +
                            "GROUP BY a.cliente_id,a.nombrecliente,a.rucdni,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id ",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setNombrecliente(fila.getString(1));
                clienteentity.setDireccion(fila.getString(2));
                clienteentity.setSaldo(fila.getString(3));
                clienteentity.setMoneda(fila.getString(4));
                clienteentity.setRucdni(fila.getString(6));
                clienteentity.setCategoria(fila.getString(7));
                clienteentity.setLinea_credito(fila.getString(8));
                clienteentity.setLinea_credito_usado(fila.getString(9));
                clienteentity.setTerminopago_id(fila.getString(10));
                arraylistaClienteSQLiteEntity.add(clienteentity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaClienteSQLiteEntity;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientes (){

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        String fecha =dateFormat.format(date);
        ListaClienteCabeceraEntity clienteentity;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select " +
                            "a.cliente_id,a.compania_id,a.nombrecliente,d.domembarque_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,IFNULL(a.moneda,0),a.telefonofijo," +
                            "a.telefonomovil,a.correo,zipcode AS ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,IFNULL(SUM(b.saldo),0),a.lista_precio" +
                            ",a.domfactura_id,a.lastpurchase,a.lineofbusiness,g.terminopago,g.contado,d.latitud,d.longitud,d.addresscode,(case when d.latitud='0' or d.latitud is null then '0' else '1' end) as geolocalizado,IFNULL(a.statuscounted,'N') as statuscount,a.customerwhitelist  FROM cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,SUM(saldo) saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            " and a.cliente_id=b.cliente_id " +
                            " INNER JOIN (SELECT compania_id,cliente_id,zona_id,domembarque_id,direccion,latitud,longitud,addresscode,zipcode FROM direccioncliente GROUP BY compania_id,cliente_id,zona_id,domembarque_id,direccion,latitud,longitud,addresscode,zipcode) d ON a.compania_id=d.compania_id " +
                            " and a.cliente_id=d.cliente_id " +
                            " LEFT JOIN  terminopago g ON" +
                            "  g.terminopago_id=a.terminopago_id " +
                            "where a.cliente_id not in (Select cliente_id from rutavendedor where fecharuta='"+fecha+"') "  +
                            " GROUP BY a.cliente_id,a.compania_id,a.nombrecliente,d.domembarque_id,a.domfactura_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,a.moneda,a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,a.lista_precio,a.lineofbusiness,a.customerwhitelist",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setCompania_id(fila.getString(1));
                clienteentity.setNombrecliente(fila.getString(2));
                clienteentity.setDomembarque_id(fila.getString(3));
                clienteentity.setDomfactura_id(fila.getString(23));

                clienteentity.setDireccion(fila.getString(4));
                clienteentity.setZona_id(fila.getString(5));
                clienteentity.setOrdenvisita(fila.getString(6));
                clienteentity.setZona(fila.getString(7));
                clienteentity.setRucdni(fila.getString(8));
                clienteentity.setMoneda(fila.getString(9));
                clienteentity.setTelefonofijo(fila.getString(10));
                clienteentity.setTelefonomovil(fila.getString(11));
                clienteentity.setCorreo(fila.getString(12));
                clienteentity.setUbigeo_id(fila.getString(13));
                clienteentity.setImpuesto_id(fila.getString(14));
                clienteentity.setImpuesto(fila.getString(15));
                clienteentity.setTipocambio(fila.getString(16));
                clienteentity.setCategoria(fila.getString(17));
                clienteentity.setLinea_credito(fila.getString(18));
                clienteentity.setLinea_credito_usado(fila.getString(19));
                clienteentity.setTerminopago_id(fila.getString(20));
                clienteentity.setSaldo(fila.getString(21));
                clienteentity.setLista_precio(fila.getString(21));
                clienteentity.setLastpurchase(fila.getString(24));
                clienteentity.setLineofbussiness(fila.getString(25));
                clienteentity.setTerminopago(fila.getString(26));
                clienteentity.setContado(fila.getString(27));
                clienteentity.setLatitud(fila.getString(28));
                clienteentity.setLongitud(fila.getString(29));
                clienteentity.setAddresscode(fila.getString(30));
                clienteentity.setChkgeolocation(fila.getString(31));
                clienteentity.setStatuscount (fila.getString(32));
                clienteentity.setCustomerwhitelist (fila.getString(33));
                arraylistaClienteSQLiteEntity.add(clienteentity);
            }
        }catch (Exception e){
           e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaClienteSQLiteEntity;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteporClienteID (String cliente_id){

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select " +
                            "a.cliente_id,a.nombrecliente,a.direccion,IFNULL(SUM(b.saldo),0),IFNULL(a.moneda,0),a.domembarque_id,a.impuesto_id,a.impuesto,a.rucdni," +
                            "a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id " +
                            "from cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                             "where a.cliente_id='"+cliente_id+"' "  +
                            "GROUP BY a.cliente_id,a.nombrecliente,a.direccion,a.domembarque_id,a.impuesto_id,a.impuesto,a.rucdni,a.categoria,a.linea_credito," +
                            "a.linea_credito_usado,a.terminopago_id ",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setNombrecliente(fila.getString(1));
                clienteentity.setDireccion(fila.getString(2));
                clienteentity.setSaldo(fila.getString(3));
                clienteentity.setMoneda(fila.getString(4));
                clienteentity.setDomembarque_id(fila.getString(5));
                clienteentity.setImpuesto_id(fila.getString(6));
                clienteentity.setImpuesto(fila.getString(7));
                clienteentity.setRucdni(fila.getString(8));
                clienteentity.setCategoria(fila.getString(9));
                clienteentity.setLinea_credito(fila.getString(10));
                clienteentity.setLinea_credito_usado(fila.getString(11));
                clienteentity.setTerminopago_id(fila.getString(12));
                arraylistaClienteSQLiteEntity.add(clienteentity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaClienteSQLiteEntity;
    }
/*
    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientePorZona(String zona_id)
    {
        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select " +
                            "a.cliente_id,a.nombrecliente,a.direccion,IFNULL(SUM(b.saldo),0),IFNULL(a.moneda,0),a.domembarque_id,a.impuesto_id,a.impuesto,a.rucdni,a.categoria,a.linea_credito,a.terminopago_id " +
                            "from cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                            "where a.zona_id='"+zona_id+"' "+
                            "GROUP BY a.cliente_id,a.nombrecliente,a.direccion,a.domembarque_id,a.impuesto_id,a.impuesto,a.rucdni,a.categoria,a.linea_credito,a.terminopago_id ",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setNombrecliente(fila.getString(1));
                clienteentity.setDireccion(fila.getString(2));
                clienteentity.setSaldo(fila.getString(3));
                clienteentity.setMoneda(fila.getString(4));
                clienteentity.setDomembarque_id(fila.getString(5));
                clienteentity.setImpuesto_id(fila.getString(6));
                clienteentity.setImpuesto(fila.getString(7));
                clienteentity.setRucdni(fila.getString(8));
                clienteentity.setCategoria(fila.getString(9));
                clienteentity.setLinea_credito(fila.getString(10));
                clienteentity.setTerminopago_id(fila.getString(11));
                arraylistaClienteSQLiteEntity.add(clienteentity);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaClienteSQLiteEntity;
    }
*/
    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientePorZonaCompleto() {
        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        Cursor fila=null;
        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

             fila = sqlite.rawQuery(
                    "SELECT DISTINCT a.cliente_id,a.compania_id,a.nombrecliente,d.domembarque_id,d.direccion,d.zona_id,a.ordenvisita,a.zona," +
                            "a.rucdni,IFNULL(a.moneda,0),a.telefonofijo,a.telefonomovil,a.correo,zipcode as ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio," +
                            "a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,a.linea_credito_usado,a.lastpurchase,IFNULL(SUM(e.saldo),0) as saldonocontados,(case when d.latitud='0' or d.latitud is null then '0' else '1' end) as geolocalizado" +
                            ",(case when f.latitudini is not null and f.latitudini is not null then '1' else '0' end) as duracionvisita,g.terminopago,g.contado,d.latitud,d.longitud,d.addresscode,IFNULL(a.statuscounted,'N') AS statuscounted,a.customerwhitelist from cliente a" +
                            " LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id and a.cliente_id=b.cliente_id " +
                            "INNER JOIN (SELECT compania_id,cliente_id,zona_id,domembarque_id,direccion,latitud,longitud,addresscode,zipcode FROM direccioncliente GROUP BY compania_id,cliente_id,zona_id,domembarque_id,direccion,latitud,longitud,addresscode,zipcode) d ON a.compania_id=d.compania_id " +
                            "and a.cliente_id=d.cliente_id " +
                            " LEFT JOIN (Select saldo,compania_id,cliente_id,domembarque_id,moneda   from documentodeuda where fechaemision<>fechavencimiento GROUP BY compania_id,cliente_id,domembarque_id,saldo,moneda) e ON "+
                            " a.compania_id=e.compania_id and a.cliente_id=e.cliente_id " +
                            " LEFT JOIN visitsection f ON "+
                            " a.compania_id=f.compania_id and a.cliente_id=f.cliente_id and a.domembarque_id=f.domembarque_id and f.dateini=strftime ('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))  " +
                            " LEFT JOIN terminopago g ON "+
                            " a.terminopago_id=g.terminopago_id " +
                            "WHERE d.zona_id IN (SELECT zona_id FROM rutafuerzatrabajo WHERE fechainicioruta=" +
                            //"date('now') " +
                            " strftime ('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))" +
                            "AND estado='ACTIVO') GROUP BY a.cliente_id," +
                            "a.compania_id,a.nombrecliente,d.domembarque_id,d.direccion,d.zona_id,a.ordenvisita,a.zona,a.rucdni,a.moneda,a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id," +
                            "a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,d.latitud,d.longitud,a.customerwhitelist;",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setCompania_id(fila.getString(1));
                clienteentity.setNombrecliente(fila.getString(2));
                clienteentity.setDomembarque_id(fila.getString(3));
                clienteentity.setDireccion(fila.getString(4));
                clienteentity.setZona_id(fila.getString(5));
                clienteentity.setOrdenvisita(fila.getString(6));
                clienteentity.setZona(fila.getString(7));
                clienteentity.setRucdni(fila.getString(8));
                clienteentity.setMoneda(fila.getString(9));
                clienteentity.setTelefonofijo(fila.getString(10));
                clienteentity.setTelefonomovil(fila.getString(11));
                clienteentity.setCorreo(fila.getString(12));
                clienteentity.setUbigeo_id(fila.getString(13));
                clienteentity.setImpuesto_id(fila.getString(14));
                clienteentity.setImpuesto(fila.getString(15));
                clienteentity.setTipocambio(fila.getString(16));
                clienteentity.setCategoria(fila.getString(17));
                clienteentity.setLinea_credito(fila.getString(18));
                clienteentity.setLinea_credito_usado(fila.getString(19));
                clienteentity.setTerminopago_id(fila.getString(20));
                clienteentity.setSaldo(fila.getString(21));
                clienteentity.setLastpurchase(fila.getString(22));
                clienteentity.setSaldosincontados(fila.getString(23));
                clienteentity.setChkgeolocation(fila.getString(24));
                clienteentity.setChkvisitsection(fila.getString(25));
                clienteentity.setTerminopago(fila.getString(26));
                clienteentity.setContado(fila.getString(27));
                clienteentity.setLatitud(fila.getString(28));
                clienteentity.setLongitud(fila.getString(29));
                clienteentity.setAddresscode(fila.getString(30));
                clienteentity.setStatuscount (fila.getString(31));
                clienteentity.setCustomerwhitelist (fila.getString(32));
                Log.e("REOS","ClienteSQlite-ObtenerClientePorZonaCompleto-clienteentity.getSaldoSincontados: "+clienteentity.getSaldosincontados());
                arraylistaClienteSQLiteEntity.add(clienteentity);
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("REOS","ClienteSQlite-ObtenerClientePorZonaCompleto-clienteentity.e: "+e.toString());
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        fila.close();

        return arraylistaClienteSQLiteEntity;
    }


    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientesConsulta (){

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        String fecha =dateFormat.format(date);
        ListaClienteCabeceraEntity clienteentity;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select " +
                            "a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,IFNULL(a.moneda,0),a.telefonofijo," +
                            "a.telefonomovil,a.correo,zipcode AS ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,IFNULL(SUM(b.saldo),0),a.lista_precio" +
                            ",a.domfactura_id,a.lastpurchase,a.lineofbusiness,a.statuscounted,a.customerwhitelist FROM cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,SUM(saldo) saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id "  +
                            "LEFT JOIN (SELECT compania_id,cliente_id,zona_id,domembarque_id,direccion,latitud,longitud,addresscode,zipcode FROM direccioncliente GROUP BY compania_id,cliente_id,zona_id,domembarque_id,direccion,latitud,longitud,addresscode,zipcode) d ON a.compania_id=d.compania_id " +
                            "and a.cliente_id=d.cliente_id " +
                            "GROUP BY a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.domfactura_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,a.moneda,a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,a.lastpurchase,a.lineofbusiness,a.customerwhitelist ",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setCompania_id(fila.getString(1));
                clienteentity.setNombrecliente(fila.getString(2));
                clienteentity.setDomembarque_id(fila.getString(3));
                clienteentity.setDomfactura_id(fila.getString(23));

                clienteentity.setDireccion(fila.getString(4));
                clienteentity.setZona_id(fila.getString(5));
                clienteentity.setOrdenvisita(fila.getString(6));
                clienteentity.setZona(fila.getString(7));
                clienteentity.setRucdni(fila.getString(8));
                clienteentity.setMoneda(fila.getString(9));
                clienteentity.setTelefonofijo(fila.getString(10));
                clienteentity.setTelefonomovil(fila.getString(11));
                clienteentity.setCorreo(fila.getString(12));
                clienteentity.setUbigeo_id(fila.getString(13));
                clienteentity.setImpuesto_id(fila.getString(14));
                clienteentity.setImpuesto(fila.getString(15));
                clienteentity.setTipocambio(fila.getString(16));
                clienteentity.setCategoria(fila.getString(17));
                clienteentity.setLinea_credito(fila.getString(18));
                clienteentity.setLinea_credito_usado(fila.getString(19));
                clienteentity.setTerminopago_id(fila.getString(20));
                clienteentity.setSaldo(fila.getString(21));
                clienteentity.setLista_precio(fila.getString(21));
                clienteentity.setLastpurchase(fila.getString(24));
                clienteentity.setLineofbussiness(fila.getString(25));
                clienteentity.setStatuscount (fila.getString(26));
                clienteentity.setCustomerwhitelist (fila.getString(27));
                Log.e("REOS","ClienteSQLite.ObtenerDatosCliente.clienteentity.getLineofbussiness: "+clienteentity.getLineofbussiness());
                arraylistaClienteSQLiteEntity.add(clienteentity);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaClienteSQLiteEntity;
    }

}

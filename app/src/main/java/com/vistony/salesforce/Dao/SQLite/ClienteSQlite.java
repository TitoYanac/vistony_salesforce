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

            sqlite.insert("cliente",null,registro);
        }

        DataBaseManager.getInstance().closeDatabase();

        return 1;
    }


/*
    public ArrayList<ClienteSQLiteEntity> ObtenerCliente (){
        listaClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        ClienteSQLiteEntity clienteentity;

        Cursor fila = sqlite.rawQuery(
                "Select cliente_id,nombrecliente,direccion from cliente",null);

        while (fila.moveToNext())
        {
            clienteentity= new ClienteSQLiteEntity();
            clienteentity.setCliente_id(fila.getString(0));
            clienteentity.setNombrecliente(fila.getString(1));
            clienteentity.setDireccion(fila.getString(2));
            listaClienteSQLiteEntity.add(clienteentity);
        }

                    DataBaseManager.getInstance().closeDatabase();

        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaClienteSQLiteEntity;
    }*/

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
                   "Select cliente_id,compania_id,nombrecliente,direccion,rucdni,categoria,linea_credito,linea_credito_usado,terminopago_id,domembarque_id,zona_id,domfactura_id from cliente" +
                           " where cliente_id= '"+cliente_id+"' and compania_id= '"+compania_id+"'",null);

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
               clienteentity.setTerminopago_id(fila.getString(8));
               clienteentity.setDomembarque_id(fila.getString(9));
               clienteentity.setZona_id(fila.getString(10));
               clienteentity.setDomfactura_id(fila.getString(11));
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha =dateFormat.format(date);
        ListaClienteCabeceraEntity clienteentity;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select " +
                            "a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,IFNULL(a.moneda,0),a.telefonofijo," +
                            "a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,IFNULL(SUM(b.saldo),0),a.lista_precio" +
                            ",a.domfactura_id FROM cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id where a.cliente_id not in (Select cliente_id from rutavendedor where fecharuta='"+fecha+"') "  +
                            "GROUP BY a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.domfactura_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,a.moneda,a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id",null);

            while (fila.moveToNext())
            {
                clienteentity= new ListaClienteCabeceraEntity();
                clienteentity.setCliente_id(fila.getString(0));
                clienteentity.setCompania_id(fila.getString(1));
                clienteentity.setNombrecliente(fila.getString(2));
                clienteentity.setDomembarque_id(fila.getString(3));
                clienteentity.setDomfactura_id(fila.getString(23));
                Log.e("DomFactura","=>"+fila.getString(23));
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
    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientePorZonaCompleto(String zona_id) {
        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

        try {
            Cursor fila = sqlite.rawQuery(
                    "Select " +
                            "a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,IFNULL(a.moneda,0),a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,IFNULL(SUM(b.saldo),0) " +
                            "from cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                            "where a.zona_id='"+zona_id+"' "+
                            "GROUP BY a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,a.moneda,a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id",null);

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

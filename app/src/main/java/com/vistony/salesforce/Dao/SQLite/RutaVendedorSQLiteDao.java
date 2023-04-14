package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.RutaVendedorSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

public class RutaVendedorSQLiteDao {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<RutaVendedorSQLiteEntity> listaRutaVendedorSQLiteEntity;
    Context context;

    public RutaVendedorSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        this.context=context;
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion sqlite desde "+this.getClass().getName());
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteController.getDatabaseName() );
        sqliteController.close();
    }


    public int InsertaRutaVendedor (
             String cliente_id,
             String compania_id,
             String nombrecliente,
             String domembarque_id,
             String domfactura_id,
             String direccion,
             String zona_id,
             String ordenvisita,
             String zona,
             String rucdni,
             String moneda,
             String telefonofijo,
             String telefonomovil,
             String correo,
             String ubigeo_id,
             String impuesto_id,
             String impuesto,
             String tipocambio,
             String categoria,
             String linea_credito,
             String terminopago_id,
             String saldomn,
             String chk_visita,
             String chk_pedido,
             String chk_cobranza,
             String chk_ruta,
             String fecharuta,
             String slpCode,
             String userCode,
             String lastpurchase,
             String saldosincontado,
             String chkgeolocation,
             String chkvisitsection,
             String terminopago
             ,String contado
             ,String latitud
             ,String longitud
             ,String addresscode
            ,String statuscounted
    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        try {


            abrir();
            ContentValues registro = new ContentValues();
            registro.put("cliente_id", cliente_id);
            registro.put("compania_id", compania_id);
            registro.put("nombrecliente", nombrecliente);
            registro.put("domembarque_id", domembarque_id);
            registro.put("direccion", direccion);
            registro.put("zona_id", zona_id);
            registro.put("ordenvisita", ordenvisita);
            registro.put("zona", zona);
            registro.put("rucdni", rucdni);
            registro.put("moneda", moneda);
            registro.put("telefonofijo", telefonofijo);
            registro.put("telefonomovil", telefonomovil);
            registro.put("correo", correo);
            registro.put("ubigeo_id", ubigeo_id);
            registro.put("impuesto_id", impuesto_id);
            registro.put("impuesto", impuesto);
            registro.put("tipocambio", tipocambio);
            registro.put("categoria", categoria);
            registro.put("linea_credito", linea_credito);
            registro.put("terminopago_id", terminopago_id);
            registro.put("chk_visita", chk_visita);
            registro.put("chk_pedido", chk_pedido);
            registro.put("chk_cobranza", chk_cobranza);
            registro.put("chk_ruta", chk_ruta);
            registro.put("fecharuta", fecharuta);
            registro.put("saldomn", saldomn);
            registro.put("slpCode", slpCode);
            registro.put("userCode", userCode);
            registro.put("lastpurchase", lastpurchase);
            registro.put("saldosincontado", saldosincontado);
            registro.put("chkgeolocation", chkgeolocation);
            registro.put("chkvisitsection", chkvisitsection);
            registro.put("terminopago", terminopago);
            registro.put("contado", contado);
            registro.put("latitud", latitud);
            registro.put("longitud", longitud);
            registro.put("addresscode", addresscode);
            registro.put("statuscounted", statuscounted);

            bd.insert("rutavendedor", null, registro);
        }catch (Exception e)
        {

        }
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int LimpiarTablaRutaVendedor ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from rutavendedor "); //add espesificacion
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<RutaVendedorSQLiteEntity> ObtenerRutaVendedor (String fecharuta)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaRutaVendedorSQLiteEntity = new ArrayList<RutaVendedorSQLiteEntity>();
        RutaVendedorSQLiteEntity rutaVendedorSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from rutavendedor where fecharuta='"+fecharuta+"' ",null);

        while (fila.moveToNext())
        {
            rutaVendedorSQLiteEntity= new RutaVendedorSQLiteEntity();
            rutaVendedorSQLiteEntity.setCliente_id(fila.getString(0));
            rutaVendedorSQLiteEntity.setCompania_id(fila.getString(1));
            rutaVendedorSQLiteEntity.setNombrecliente(fila.getString(2));
            rutaVendedorSQLiteEntity.setDomembarque_id(fila.getString(3));
            rutaVendedorSQLiteEntity.setDireccion(fila.getString(4));
            rutaVendedorSQLiteEntity.setZona_id(fila.getString(5));
            rutaVendedorSQLiteEntity.setOrden(fila.getString(6));
            rutaVendedorSQLiteEntity.setZona(fila.getString(7));
            rutaVendedorSQLiteEntity.setRucdni(fila.getString(8));
            rutaVendedorSQLiteEntity.setMoneda(fila.getString(9));
            rutaVendedorSQLiteEntity.setTelefonofijo(fila.getString(10));
            rutaVendedorSQLiteEntity.setTelefonomovil(fila.getString(11));
            rutaVendedorSQLiteEntity.setCorreo(fila.getString(12));
            rutaVendedorSQLiteEntity.setUbigeo_id(fila.getString(13));
            rutaVendedorSQLiteEntity.setImpuesto_id(fila.getString(14));
            rutaVendedorSQLiteEntity.setImpuesto(fila.getString(15));
            rutaVendedorSQLiteEntity.setTipocambio(fila.getString(16));
            rutaVendedorSQLiteEntity.setCategoria(fila.getString(17));
            rutaVendedorSQLiteEntity.setLinea_credito(fila.getString(18));
            rutaVendedorSQLiteEntity.setTerminopago_id(fila.getString(19));
            rutaVendedorSQLiteEntity.setChk_visita(fila.getString(20));
            rutaVendedorSQLiteEntity.setChk_pedido(fila.getString(21));
            rutaVendedorSQLiteEntity.setChk_cobranza(fila.getString(22));
            rutaVendedorSQLiteEntity.setChk_ruta(fila.getString(23));
            rutaVendedorSQLiteEntity.setFecharuta(fila.getString(24));

            listaRutaVendedorSQLiteEntity.add(rutaVendedorSQLiteEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaRutaVendedorSQLiteEntity;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerRutaVendedorPorFecha (String checkRuta,Context context,String date){

        ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntity=new ArrayList<>();
        ListaClienteCabeceraEntity ObjListaClienteCabeceraEntity;
        abrir();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
        UsuarioSQLiteEntity usuarioSQLiteEntity=new UsuarioSQLiteEntity();
        usuarioSQLiteEntity=usuarioSQLite.ObtenerUsuarioSesion();

        Cursor fila = bd.rawQuery("SELECT * FROM rutavendedor WHERE fecharuta=" +
                //"date('now','localtime') " +
                " strftime ('%Y',date('now','localtime'))||strftime ('%m',date('now','localtime'))||strftime ('%d',date('now','localtime'))" +
                "AND chk_ruta=? AND slpCode=?",new String[]{checkRuta,usuarioSQLiteEntity.getFuerzatrabajo_id()});

        while (fila.moveToNext())
        {
            int countsalesorder=0,countcollection=0,countvisit=0;
            String visitsalesorder="0",visitcollection="0",visit="0";
            ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity=new ArrayList<>();
            ClienteSQlite clienteSQlite =new ClienteSQlite(context);
            VisitaSQLite visitaSQLite=new VisitaSQLite(context);
            String terminopago_id="";
            String linea_credito_usado="";
            String domfactura_id="";
            String ShipToCode="";

            listaClienteSQLiteEntity= clienteSQlite.ObtenerDatosCliente(fila.getString(0),fila.getString(2));
            Log.e("REOS","RutaVendedorSQLiteDao.listaClienteSQLiteEntity.get(i).fila.getString(0): "+fila.getString(0));
            Log.e("REOS","RutaVendedorSQLiteDao.listaClienteSQLiteEntity.get(i).fila.getString(2): "+fila.getString(2));
            Log.e("REOS","RutaVendedorSQLiteDao.listaClienteSQLiteEntity.get(i).listaClienteSQLiteEntity.size(): "+listaClienteSQLiteEntity.size());
            for(int i=0;i<listaClienteSQLiteEntity.size();i++){
                terminopago_id=listaClienteSQLiteEntity.get(i).getTerminopago_id();
                Log.e("REOS","RutaVendedorSQLiteDao.listaClienteSQLiteEntity.get(i).getTerminopago_id(): "+listaClienteSQLiteEntity.get(i).getTerminopago_id());
                linea_credito_usado=listaClienteSQLiteEntity.get(i).getLinea_credito_usado();
                domfactura_id=listaClienteSQLiteEntity.get(i).getDomfactura_id();
                ShipToCode=listaClienteSQLiteEntity.get(i).getDomembarque_id();
            }
            countsalesorder=visitaSQLite.getCountVisitWithOV(date,fila.getString(0),"01",checkRuta,fila.getString(1));
            countcollection=visitaSQLite.getCountVisitWithType(date,fila.getString(0),"02",checkRuta,fila.getString(1));
            countvisit=visitaSQLite.getCountVisitWithDate(date,fila.getString(0),checkRuta,fila.getString(1));
            if(countsalesorder>0)
            {
                visitsalesorder="1";
            }
            if(countcollection>0)
            {
                visitcollection="1";
            }
            if(countvisit>0)
            {
                visit="1";
            }
            ObjListaClienteCabeceraEntity= new ListaClienteCabeceraEntity();
            ObjListaClienteCabeceraEntity.setCliente_id(fila.getString(0));

            ObjListaClienteCabeceraEntity.setDomembarque_id(fila.getString(1));
            ObjListaClienteCabeceraEntity.setDomfactura_id(domfactura_id);

            ObjListaClienteCabeceraEntity.setCompania_id(fila.getString(2));
            ObjListaClienteCabeceraEntity.setNombrecliente(fila.getString(3));
            ObjListaClienteCabeceraEntity.setDireccion(fila.getString(4));
            ObjListaClienteCabeceraEntity.setZona_id(fila.getString(5));
            ObjListaClienteCabeceraEntity.setOrdenvisita(fila.getString(6));
            ObjListaClienteCabeceraEntity.setZona(fila.getString(7));
            ObjListaClienteCabeceraEntity.setRucdni(fila.getString(8));
            ObjListaClienteCabeceraEntity.setMoneda(fila.getString(9));
            ObjListaClienteCabeceraEntity.setTelefonofijo(fila.getString(10));
            ObjListaClienteCabeceraEntity.setTelefonomovil(fila.getString(11));
            ObjListaClienteCabeceraEntity.setCorreo(fila.getString(12));
            ObjListaClienteCabeceraEntity.setUbigeo_id(fila.getString(13));
            ObjListaClienteCabeceraEntity.setImpuesto_id(fila.getString(14));
            ObjListaClienteCabeceraEntity.setImpuesto(fila.getString(15));
            ObjListaClienteCabeceraEntity.setTipocambio(fila.getString(16));
            ObjListaClienteCabeceraEntity.setCategoria(fila.getString(17));
            ObjListaClienteCabeceraEntity.setLinea_credito(fila.getString(18));

            ObjListaClienteCabeceraEntity.setLinea_credito_usado(linea_credito_usado);
            ObjListaClienteCabeceraEntity.setTerminopago_id(terminopago_id);

            ObjListaClienteCabeceraEntity.setChk_visita(visit);
            ObjListaClienteCabeceraEntity.setChk_pedido(visitsalesorder);
            ObjListaClienteCabeceraEntity.setChk_cobranza(visitcollection);
            ObjListaClienteCabeceraEntity.setChk_ruta(checkRuta);
            ObjListaClienteCabeceraEntity.setFecharuta(fila.getString(24));


            if(checkRuta.equals("0"))
            {
                ClienteSQlite clienteSQlite1 = new ClienteSQlite(context);
                ArrayList<ListaClienteCabeceraEntity> listClient = new ArrayList<>();
                listClient = clienteSQlite1.ObtenerClienteporClienteID(fila.getString(0));
                for (int i = 0; i < listClient.size(); i++) {
                    ObjListaClienteCabeceraEntity.setSaldo(listClient.get(i).getSaldo());
                }
            }else {
                ObjListaClienteCabeceraEntity.setSaldo(fila.getString(25));
            }




            ObjListaClienteCabeceraEntity.setLastpurchase(fila.getString(30));
            ObjListaClienteCabeceraEntity.setChkgeolocation(fila.getString(32));
            ObjListaClienteCabeceraEntity.setChkvisitsection(fila.getString(33));
            ObjListaClienteCabeceraEntity.setTerminopago(fila.getString(34));
            ObjListaClienteCabeceraEntity.setContado(fila.getString(35));
            ObjListaClienteCabeceraEntity.setLatitud(fila.getString(36));
            ObjListaClienteCabeceraEntity.setLongitud(fila.getString(37));
            ObjListaClienteCabeceraEntity.setAddresscode(fila.getString(38));
            ObjListaClienteCabeceraEntity.setStatuscount (fila.getString(39));
            Log.e("REOS","RutaVendedorSQLiteDao.ObtenerRutaVendedorPorFecha.getLastpurchase"+ObjListaClienteCabeceraEntity.getLastpurchase());
            Log.e("REOS","RutaVendedorSQLiteDao.ObtenerRutaVendedorPorFecha.getStatuscount"+ObjListaClienteCabeceraEntity.getStatuscount());
            listaClienteCabeceraEntity.add(ObjListaClienteCabeceraEntity);
        }

        bd.close();
        return listaClienteCabeceraEntity;
    }

    public int ObtenerCantidadRutaVendedor (String fecharuta,String chkruta)
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(Cliente_id) from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chkruta+"'",null);

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


    public int ObtenerCantidadVisitadosRutaVendedor (String fecharuta,String chkruta)
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    " Select count(Cliente_id) from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chkruta+"' and chk_visita='1' ",null);

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

    public int ObtenerCantidadCobranzaRutaVendedor (String fecharuta,String chkruta)
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(Cliente_id) from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chkruta+"' and chk_cobranza='1'",null);

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

    public int ObtenerCantidadPedidoRutaVendedor (String fecharuta,String chkruta)
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(Cliente_id) from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chkruta+"' and chk_pedido='1'",null);

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

    public int ActualizaVisitaRutaVendedor (String cliente_id, String domembarque_id, String compania_id,String fecharuta)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chk_visita","1");
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' " +
                    "and  domembarque_id='"+domembarque_id+"' " +
                    "and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }

    public int ActualizaChkPedidoRutaVendedor (
            String cliente_id,
            String domembarque_id,
            String compania_id,
            String fecharuta,
            String salesorderamount
            )
    {
        int resultado=0;
        float monto=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(SUM(salesorderamount),0) from rutavendedor where fecharuta='"+fecharuta+"' and compania_id='"+compania_id+"' and cliente_id='"+cliente_id+"' and domembarque_id='"+domembarque_id+"'   " ,null);
            while (fila.moveToNext())
            {
                monto= Float.parseFloat (fila.getString(0));
            }
            ContentValues registro = new ContentValues();
            registro.put("chk_pedido","1");
            registro.put("chk_visita","1");
            registro.put("salesorderamount",String.valueOf(monto+Float.parseFloat(salesorderamount)));
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"'" +
                    " and  domembarque_id='"+domembarque_id+"'" +
                    " and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }
    public int ActualizaChkCobranzaRutaVendedor (
            String cliente_id,
            String domembarque_id,
            String compania_id,
            String fecharuta,
            String collectionamount

    )
    {
        int resultado=0;
        float monto=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(SUM(collectionamount),0) from rutavendedor where fecharuta='"+fecharuta+"' and compania_id='"+compania_id+"' and cliente_id='"+cliente_id+"' and domembarque_id='"+domembarque_id+"'   " ,null);
            while (fila.moveToNext())
            {
                monto= Float.parseFloat (fila.getString(0));
            }

            ContentValues registro = new ContentValues();
            registro.put("chk_cobranza","1");
            registro.put("chk_visita","1");
            registro.put("collectionamount",String.valueOf(monto+Float.parseFloat(collectionamount)));
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' " +
                    "and  domembarque_id='"+domembarque_id+"' " +
                    "and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }

    public float ObtenerMontoPedidoRutaVendedor (
            String compania_id,
            String fecharuta,
            String chkruta

    )
    {
        float resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(SUM(salesorderamount),0) from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chkruta+"' and chk_pedido='1' and compania_id='"+compania_id+"'" ,null);

            while (fila.moveToNext())
            {
                resultado= Float.parseFloat (fila.getString(0));

            }
        }catch (Exception e)
        {
            Log.e("REOS","RutaVendedorSQLiteDao-ObtenerMontoPedidoRutaVendedor-e"+e.toString());
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public float ObtenerMontoCobranzaRutaVendedor (
            String compania_id,
            String fecharuta,
            String chkruta

    )
    {
        float resultado=0;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select IFNULL(SUM(collectionamount),0) from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chkruta+"' and chk_cobranza='1' and compania_id='"+compania_id+"'" ,null);

            while (fila.moveToNext())
            {
                resultado= Float.parseFloat (fila.getString(0));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int GetCountClientwithBalance (String fecharuta,String chkruta)
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(A.Cliente_id) from rutavendedor A" +
                            " where A.fecharuta='"+fecharuta+"' and A.chk_ruta='"+chkruta+"' and A.saldosincontado>0 ",null);

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

    public int DeleteRouteSalesForDate (String date)
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from rutavendedor where  fecharuta='"+date+"' and chk_ruta='1' "); //add espesificacion
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int UpdateChkGeolocationRouteSales (String cliente_id, String domembarque_id, String compania_id,String fecharuta,String latitud,String longitud)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkgeolocation","1");
            registro.put("latitud", latitud);
            registro.put("longitud", longitud);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' " +
                    "and  domembarque_id='"+domembarque_id+"' " +
                    "and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }

    public int UpdateChkVisitSection (String cliente_id, String domembarque_id, String compania_id,String fecharuta)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chkvisitsection","1");
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' " +
                    "and  domembarque_id='"+domembarque_id+"' " +
                    "and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }
}

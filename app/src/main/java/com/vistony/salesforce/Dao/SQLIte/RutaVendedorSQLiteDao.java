package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.RutaVendedorSQLiteEntity;

import java.util.ArrayList;

public class RutaVendedorSQLiteDao {

    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<RutaVendedorSQLiteEntity> listaRutaVendedorSQLiteEntity;
    Context context;

    public RutaVendedorSQLiteDao(Context context)
    {
        sqLiteController = new SQLiteController(context);
        this.context=context;
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


    public int InsertaRutaVendedor (
             String cliente_id,
             String compania_id,
             String nombrecliente,
             String domembarque_id,
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
             String fecharuta
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

    public ArrayList<ListaClienteCabeceraEntity> ObtenerRutaVendedorPorFecha (String fecharuta,String chk_ruta,Context context)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntity=new ArrayList<>();
        ListaClienteCabeceraEntity ObjListaClienteCabeceraEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from rutavendedor where fecharuta='"+fecharuta+"' and chk_ruta='"+chk_ruta+"'",null);

        while (fila.moveToNext())
        {
            ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity=new ArrayList<>();
            ClienteSQliteDAO clienteSQliteDAO=new ClienteSQliteDAO(context);
            String terminopago_id="";

            listaClienteSQLiteEntity=clienteSQliteDAO.ObtenerDatosCliente(
                    fila.getString(0),
                    fila.getString(2));
            Log.e("REOS",fila.getString(0));
            Log.e("REOS",fila.getString(2));

            for(int i=0;i<listaClienteSQLiteEntity.size();i++)
            {
                terminopago_id=listaClienteSQLiteEntity.get(i).getTerminopago_id();
            }



            ObjListaClienteCabeceraEntity= new ListaClienteCabeceraEntity();
            ObjListaClienteCabeceraEntity.setCliente_id(fila.getString(0));
            ObjListaClienteCabeceraEntity.setDomembarque_id(fila.getString(1));
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
            ObjListaClienteCabeceraEntity.setTerminopago_id(terminopago_id);
            ObjListaClienteCabeceraEntity.setChk_visita(fila.getString(20));
            ObjListaClienteCabeceraEntity.setChk_pedido(fila.getString(21));
            ObjListaClienteCabeceraEntity.setChk_cobranza(fila.getString(22));
            ObjListaClienteCabeceraEntity.setChk_ruta(fila.getString(23));
            ObjListaClienteCabeceraEntity.setFecharuta(fila.getString(24));
            ObjListaClienteCabeceraEntity.setSaldo(fila.getString(25));
            listaClienteCabeceraEntity.add(ObjListaClienteCabeceraEntity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaClienteCabeceraEntity;
    }

    public int ObtenerCantidadRutaVendedor ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(Cliente_id) from rutavendedor",null);

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

    public int ActualizaVisitaRutaVendedor (String cliente_id, String domembarque_id, String compania_id,String fecharuta)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chk_visita","1");
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' and  domembarque_id='"+domembarque_id+"' and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }

    public int ActualizaChkPedidoRutaVendedor (String cliente_id, String domembarque_id, String compania_id,String fecharuta)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chk_pedido","1");
            registro.put("chk_visita","1");
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' and  domembarque_id='"+domembarque_id+"' and fecharuta='"+fecharuta+"' " ,null);
            bd.close();
        }catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
            resultado=0;
        }
        return resultado;
    }
    public int ActualizaChkCobranzaRutaVendedor (String cliente_id, String domembarque_id, String compania_id,String fecharuta)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("chk_cobranza","1");
            registro.put("chk_visita","1");
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("rutavendedor",registro,"cliente_id='"+cliente_id+"'"+" and compania_id='"+compania_id+"' and  domembarque_id='"+domembarque_id+"' and fecharuta='"+fecharuta+"' " ,null);
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

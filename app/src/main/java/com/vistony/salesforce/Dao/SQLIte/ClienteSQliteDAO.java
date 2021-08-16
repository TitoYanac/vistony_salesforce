package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.View.MenuView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ClienteSQliteDAO
{
    private MenuView menuView;
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity;
    ArrayList<ListaClienteCabeceraEntity> arraylistaClienteSQLiteEntity;
    public ClienteSQliteDAO(Context context)
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
        Log.i("SQLite", "Se cierra conexion desde " +this.getClass().getName()  );
        sqLiteController.close();
    }


    public int InsertaCliente (
            String cliente_id,
            String domembarque_id,
            String compania_id,
            String nombrecliente,
            String direccion,
            String zona_id,
            String orden,
            String zona,
            String rucdni,
            String moneda,
            String telefonofijo,
            String telefonomovil,
            String ubigeo_id,
            String impuesto_id,
            String impuesto,
            String tipocambio,
            String categoria,
            String linea_credito,
            String linea_credito_usado,
            String terminopago_id

    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
       // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("cliente_id",cliente_id);
        registro.put("domembarque_id",domembarque_id);
        registro.put("compania_id",compania_id);
        registro.put("nombrecliente",nombrecliente);
        registro.put("direccion",direccion);
        registro.put("zona_id",zona_id);
        registro.put("ordenvisita",orden);
        registro.put("zona",zona);
        registro.put("rucdni",rucdni);
        registro.put("moneda",moneda);
        registro.put("telefonofijo",telefonofijo);
        registro.put("telefonomovil",telefonomovil);
        registro.put("ubigeo_id",ubigeo_id);
        registro.put("impuesto_id",impuesto_id);
        registro.put("impuesto",impuesto);
        registro.put("tipocambio",tipocambio);
        registro.put("categoria",categoria);
        registro.put("linea_credito",linea_credito);
        registro.put("linea_credito_usado",linea_credito_usado);
        registro.put("terminopago_id",terminopago_id);
        bd.insert("cliente",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<ClienteSQLiteEntity> ObtenerCliente ()
    {
       //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        ClienteSQLiteEntity clienteentity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select cliente_id,nombrecliente,direccion from cliente",null);

        while (fila.moveToNext())
        {
            clienteentity= new ClienteSQLiteEntity();
            clienteentity.setCliente_id(fila.getString(0));
            clienteentity.setNombrecliente(fila.getString(1));
            clienteentity.setDireccion(fila.getString(2));
            listaClienteSQLiteEntity.add(clienteentity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaClienteSQLiteEntity;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteDeuda ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
        Cursor fila = bd.rawQuery(
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
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaClienteSQLiteEntity;
    }

    public ArrayList<ClienteSQLiteEntity> ObtenerDatosCliente (String cliente_id,String compania_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        listaClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        ClienteSQLiteEntity clienteentity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select cliente_id,compania_id,nombrecliente,direccion,rucdni,categoria,linea_credito,linea_credito_usado,terminopago_id,domembarque_id,zona_id from cliente" +
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
            listaClienteSQLiteEntity.add(clienteentity);
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return listaClienteSQLiteEntity;
    }

    public int LimpiarTablaCliente ()
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        // bd.insert("documentodeuda",null,);
        bd.execSQL("delete from cliente");
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }


    public int ObtenerCantidadClientes ()
    {
        int resultado=0;
        listaClienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        ClienteSQLiteEntity clienteentity;
        abrir();
        try {
        Cursor fila = bd.rawQuery(
                "Select count(cliente_id) from cliente",null);

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

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteSinDeuda ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
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
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaClienteSQLiteEntity;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteDeudaPorZona(String zona_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select " +
                            "b.cliente_id,b.nombrecliente,b.direccion,SUM(saldo),a.moneda,b.domembarque_id,b.impuesto_id,b.impuesto,b.rucdni,b.categoria,b.linea_credito " +
                            "from documentodeuda a " +
                            "LEFT OUTER JOIN " +
                            "(Select compania_id,cliente_id,nombrecliente,direccion,domembarque_id,zona_id,impuesto_id,impuesto,rucdni,categoria,linea_credito " +
                            "from cliente GROUP BY compania_id,cliente_id,nombrecliente,direccion,domembarque_id,zona_id,impuesto_id,impuesto,rucdni,categoria,linea_credito ) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                            /*"LEFT OUTER JOIN " +
                            "(Select compania_id,cliente_id,SUM(saldo) " +
                            "from documentodeuda" +
                            "WHERE fechavencimiento<date('now');" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id) C " +*/
                            "where " +
                            "a.saldo <> '0' and a.saldo>1 and " +
                            "b.zona_id='"+zona_id+"' "+
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

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientes ()
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String fecha =dateFormat.format(date);
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select " +
                            "a.cliente_id,a.compania_id,a.nombrecliente,a.domembarque_id,a.direccion,a.zona_id,a.ordenvisita,a.zona,a.rucdni,IFNULL(a.moneda,0),a.telefonofijo,a.telefonomovil,a.correo,a.ubigeo_id,a.impuesto_id,a.impuesto,a.tipocambio,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id,IFNULL(SUM(b.saldo),0) " +
                            "from cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                            //"LEFT JOIN (Select * from rutavendedor where fecharuta='"+fecha+"') c ON "+
                            //"a.compania_id=c.compania_id and "+
                            //"a.cliente_id=c.cliente_id "+
                            "where a.cliente_id not in (Select cliente_id from rutavendedor where fecharuta='"+fecha+"') "  +
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
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS","ClienteSQLiteDao.ObtenerClientes-Error: "+e.toString());
        }

        bd.close();

        return arraylistaClienteSQLiteEntity;
    }

    public String ObtenerRucDniCliente (String cliente_id)
    {
        ClienteSQLiteEntity clienteentity;
        String rucdni="";
        abrir();
        Cursor fila = bd.rawQuery(
                "Select rucdni from cliente where cliente_id='"+cliente_id+"'",null);

        while (fila.moveToNext())
        {
            rucdni=fila.getString(0);

        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return rucdni;
    }

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClienteporClienteID (String cliente_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();

        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select " +
                            "a.cliente_id,a.nombrecliente,a.direccion,IFNULL(SUM(b.saldo),0),IFNULL(a.moneda,0),a.domembarque_id,a.impuesto_id,a.impuesto,a.rucdni,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id " +
                            "from cliente a " +
                            "LEFT JOIN (Select compania_id,cliente_id,saldo,moneda from documentodeuda GROUP BY compania_id,cliente_id,saldo,moneda) b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.cliente_id=b.cliente_id " +
                             "where a.cliente_id='"+cliente_id+"' "  +
                            "GROUP BY a.cliente_id,a.nombrecliente,a.direccion,a.domembarque_id,a.impuesto_id,a.impuesto,a.rucdni,a.categoria,a.linea_credito,a.linea_credito_usado,a.terminopago_id ",null);

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
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaClienteSQLiteEntity;
    }

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

    public ArrayList<ListaClienteCabeceraEntity> ObtenerClientePorZonaCompleto(String zona_id)
    {
        arraylistaClienteSQLiteEntity = new ArrayList<ListaClienteCabeceraEntity>();
        ListaClienteCabeceraEntity clienteentity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
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
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaClienteSQLiteEntity;
    }

}

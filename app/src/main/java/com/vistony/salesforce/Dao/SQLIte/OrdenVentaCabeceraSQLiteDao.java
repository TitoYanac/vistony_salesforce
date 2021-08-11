package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;

import java.util.ArrayList;

public class OrdenVentaCabeceraSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabeceraSQLiteEntity;

    public OrdenVentaCabeceraSQLiteDao(Context context)
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


    public int InsertaOrdenVentaCabecera (
            String compania_id,
            String ordenventa_id,
            String cliente_id,
            String domembarque_id,
            String terminopago_id,
            String agencia_id,
            String moneda_id,
            String comentario,
            String almacen_id,
            String impuesto_id,
            String montosubtotal,
            String montodescuento,
            String montoimpuesto,
            String montototal,
            String fuerzatrabajo_id,
            String usuario_id,
            String enviadoERP,
            String recibidoERP,
            String ordenventa_ERP_id,
            String listaprecio_id,
            String planta_id,
            String fecharegistro,
            String tipocambio,
            String fechatipocambio,
            String rucdni,
            String U_SYP_MDTD,
            String U_SYP_MDSD,
            String U_SYP_MDCD,
            String U_SYP_MDMT,
            String U_SYP_STATUS,
            String DocType,
            String mensajeWS,
            String total_gal_acumulado,
            String descuentocontado
    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("ordenventa_id",ordenventa_id);
        registro.put("cliente_id",cliente_id);
        registro.put("domembarque_id",domembarque_id);
        registro.put("terminopago_id",terminopago_id);
        registro.put("agencia_id",agencia_id);
        registro.put("moneda_id",moneda_id);
        registro.put("comentario",comentario);
        registro.put("almacen_id",almacen_id);
        registro.put("impuesto_id",impuesto_id);
        registro.put("montosubtotal",montosubtotal);
        registro.put("montodescuento",montodescuento);
        registro.put("montoimpuesto",montoimpuesto);
        registro.put("montototal",montototal);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("usuario_id",usuario_id);
        registro.put("enviadoERP",enviadoERP);
        registro.put("recibidoERP",recibidoERP);
        registro.put("ordenventa_ERP_id",ordenventa_ERP_id);
        registro.put("listaprecio_id",listaprecio_id);
        registro.put("planta_id",planta_id);
        registro.put("fecharegistro",fecharegistro);
        registro.put("tipocambio",tipocambio);
        registro.put("fechatipocambio",fechatipocambio);
        registro.put("rucdni",rucdni);
        registro.put("U_SYP_MDTD",U_SYP_MDTD);
        registro.put("U_SYP_MDSD",U_SYP_MDSD);
        registro.put("U_SYP_MDCD",U_SYP_MDCD);
        registro.put("U_SYP_MDMT",U_SYP_MDMT);
        registro.put("U_SYP_STATUS",U_SYP_STATUS);
        registro.put("DocType",DocType);
        registro.put("mensajeWS",mensajeWS);
        registro.put("total_gal_acumulado",total_gal_acumulado);
        registro.put("descuentocontado",descuentocontado);
        bd.insert("ordenventacabecera",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<OrdenVentaCabeceraSQLiteEntity> ObtenerOrdenVentaCabeceraporFecha (String fecha,String fuerzatrabajo_id,String compania_id)
    {
        listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<OrdenVentaCabeceraSQLiteEntity>();
        OrdenVentaCabeceraSQLiteEntity ordenVentaCabeceraSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ordenventacabecera where substr(ordenventa_id,1,8)='"+fecha+"' and  fuerzatrabajo_id='"+fuerzatrabajo_id+"' and compania_id='"+compania_id+"' ",null);

        while (fila.moveToNext())
        {
            ordenVentaCabeceraSQLiteEntity= new OrdenVentaCabeceraSQLiteEntity();
            ordenVentaCabeceraSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaCabeceraSQLiteEntity.setCliente_id(fila.getString(2));
            ordenVentaCabeceraSQLiteEntity.setDomembarque_id(fila.getString(3));
            ordenVentaCabeceraSQLiteEntity.setTerminopago_id(fila.getString(4));
            ordenVentaCabeceraSQLiteEntity.setAgencia_id(fila.getString(5));
            ordenVentaCabeceraSQLiteEntity.setMoneda_id(fila.getString(6));
            ordenVentaCabeceraSQLiteEntity.setComentario(fila.getString(7));
            ordenVentaCabeceraSQLiteEntity.setAlmacen_id(fila.getString(8));
            ordenVentaCabeceraSQLiteEntity.setImpuesto_id(fila.getString(9));
            ordenVentaCabeceraSQLiteEntity.setMontosubtotal(fila.getString(10));
            ordenVentaCabeceraSQLiteEntity.setMontodescuento(fila.getString(11));
            ordenVentaCabeceraSQLiteEntity.setMontoimpuesto(fila.getString(12));
            ordenVentaCabeceraSQLiteEntity.setMontototal(fila.getString(13));
            ordenVentaCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(14));
            ordenVentaCabeceraSQLiteEntity.setUsuario_id(fila.getString(15));
            ordenVentaCabeceraSQLiteEntity.setEnviadoERP(fila.getString(16));
            ordenVentaCabeceraSQLiteEntity.setRecibidoERP(fila.getString(17));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_ERP_id(fila.getString(18));
            ordenVentaCabeceraSQLiteEntity.setListaprecio_id(fila.getString(19));
            ordenVentaCabeceraSQLiteEntity.setPlanta_id(fila.getString(20));
            ordenVentaCabeceraSQLiteEntity.setFecharegistro(fila.getString(21));
            ordenVentaCabeceraSQLiteEntity.setTipocambio(fila.getString(22));
            ordenVentaCabeceraSQLiteEntity.setFechatipocambio(fila.getString(23));
            ordenVentaCabeceraSQLiteEntity.setRucdni(fila.getString(24));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDTD(fila.getString(25));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDSD(fila.getString(26));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDCD(fila.getString(27));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDMT(fila.getString(28));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_STATUS(fila.getString(29));
            ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(30));
            ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(31));
            ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(32));
            ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(33));

            listaOrdenVentaCabeceraSQLiteEntity.add(ordenVentaCabeceraSQLiteEntity);
        }
        bd.close();
        return listaOrdenVentaCabeceraSQLiteEntity;
    }
    public ArrayList<OrdenVentaCabeceraSQLiteEntity> ObtenerOrdenVentaCabeceraporFechaInicioyFinal (String fechainicio,String fechafinal)
    {
        listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<OrdenVentaCabeceraSQLiteEntity>();
        OrdenVentaCabeceraSQLiteEntity ordenVentaCabeceraSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ordenventacabecera where substr(ordenventa_id,1,8)>='"+fechainicio+"' and substr(ordenventa_id,1,8)<='"+fechafinal+"'   "  ,null);

        while (fila.moveToNext())
        {
            ordenVentaCabeceraSQLiteEntity= new OrdenVentaCabeceraSQLiteEntity();
            ordenVentaCabeceraSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaCabeceraSQLiteEntity.setCliente_id(fila.getString(2));
            ordenVentaCabeceraSQLiteEntity.setDomembarque_id(fila.getString(3));
            ordenVentaCabeceraSQLiteEntity.setTerminopago_id(fila.getString(4));
            ordenVentaCabeceraSQLiteEntity.setAgencia_id(fila.getString(5));
            ordenVentaCabeceraSQLiteEntity.setMoneda_id(fila.getString(6));
            ordenVentaCabeceraSQLiteEntity.setComentario(fila.getString(7));
            ordenVentaCabeceraSQLiteEntity.setAlmacen_id(fila.getString(8));
            ordenVentaCabeceraSQLiteEntity.setImpuesto_id(fila.getString(9));
            ordenVentaCabeceraSQLiteEntity.setMontosubtotal(fila.getString(10));
            ordenVentaCabeceraSQLiteEntity.setMontodescuento(fila.getString(11));
            ordenVentaCabeceraSQLiteEntity.setMontoimpuesto(fila.getString(12));
            ordenVentaCabeceraSQLiteEntity.setMontototal(fila.getString(13));
            ordenVentaCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(14));
            ordenVentaCabeceraSQLiteEntity.setUsuario_id(fila.getString(15));
            ordenVentaCabeceraSQLiteEntity.setEnviadoERP(fila.getString(16));
            ordenVentaCabeceraSQLiteEntity.setRecibidoERP(fila.getString(17));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_ERP_id(fila.getString(18));
            ordenVentaCabeceraSQLiteEntity.setListaprecio_id(fila.getString(19));
            ordenVentaCabeceraSQLiteEntity.setPlanta_id(fila.getString(20));
            ordenVentaCabeceraSQLiteEntity.setFecharegistro(fila.getString(21));
            ordenVentaCabeceraSQLiteEntity.setTipocambio(fila.getString(22));
            ordenVentaCabeceraSQLiteEntity.setFechatipocambio(fila.getString(23));
            ordenVentaCabeceraSQLiteEntity.setRucdni(fila.getString(24));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDTD(fila.getString(25));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDSD(fila.getString(26));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDCD(fila.getString(27));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDMT(fila.getString(28));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_STATUS(fila.getString(29));
            ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(30));
            ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(31));
            ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(32));
            ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(33));

            listaOrdenVentaCabeceraSQLiteEntity.add(ordenVentaCabeceraSQLiteEntity);
        }
        bd.close();
        return listaOrdenVentaCabeceraSQLiteEntity;
    }
    public int LimpiarTablaOrdenVentaCabecera ()
    {
        abrir();
        bd.execSQL("delete from ordenventacabecera");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadOrdenVentaCabecera()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from ordenventacabecera",null);

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

    public ArrayList<OrdenVentaCabeceraSQLiteEntity> ObtenerOrdenVentaCabeceraporID (String ordenventa_id)
    {
        listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<OrdenVentaCabeceraSQLiteEntity>();
        OrdenVentaCabeceraSQLiteEntity ordenVentaCabeceraSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ordenventacabecera where ordenventa_id='"+ordenventa_id+"'",null);

        while (fila.moveToNext())
        {
            ordenVentaCabeceraSQLiteEntity= new OrdenVentaCabeceraSQLiteEntity();
            ordenVentaCabeceraSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaCabeceraSQLiteEntity.setCliente_id(fila.getString(2));
            ordenVentaCabeceraSQLiteEntity.setDomembarque_id(fila.getString(3));
            ordenVentaCabeceraSQLiteEntity.setTerminopago_id(fila.getString(4));
            ordenVentaCabeceraSQLiteEntity.setAgencia_id(fila.getString(5));
            ordenVentaCabeceraSQLiteEntity.setMoneda_id(fila.getString(6));
            ordenVentaCabeceraSQLiteEntity.setComentario(fila.getString(7));
            ordenVentaCabeceraSQLiteEntity.setAlmacen_id(fila.getString(8));
            ordenVentaCabeceraSQLiteEntity.setImpuesto_id(fila.getString(9));
            ordenVentaCabeceraSQLiteEntity.setMontosubtotal(fila.getString(10));
            ordenVentaCabeceraSQLiteEntity.setMontodescuento(fila.getString(11));
            ordenVentaCabeceraSQLiteEntity.setMontoimpuesto(fila.getString(12));
            ordenVentaCabeceraSQLiteEntity.setMontototal(fila.getString(13));
            ordenVentaCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(14));
            ordenVentaCabeceraSQLiteEntity.setUsuario_id(fila.getString(15));
            ordenVentaCabeceraSQLiteEntity.setEnviadoERP(fila.getString(16));
            ordenVentaCabeceraSQLiteEntity.setRecibidoERP(fila.getString(17));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_ERP_id(fila.getString(18));
            ordenVentaCabeceraSQLiteEntity.setListaprecio_id(fila.getString(19));
            ordenVentaCabeceraSQLiteEntity.setPlanta_id(fila.getString(20));
            ordenVentaCabeceraSQLiteEntity.setFecharegistro(fila.getString(21));
            ordenVentaCabeceraSQLiteEntity.setTipocambio(fila.getString(22));
            ordenVentaCabeceraSQLiteEntity.setFechatipocambio(fila.getString(23));
            ordenVentaCabeceraSQLiteEntity.setRucdni(fila.getString(24));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDTD(fila.getString(25));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDSD(fila.getString(26));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDCD(fila.getString(27));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDMT(fila.getString(28));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_STATUS(fila.getString(29));
            ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(30));
            ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(31));
            ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(32));
            ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(33));

            listaOrdenVentaCabeceraSQLiteEntity.add(ordenVentaCabeceraSQLiteEntity);
        }
        bd.close();
        return listaOrdenVentaCabeceraSQLiteEntity;
    }

    public int ActualizaResultadoOVenviada (String compania_id, String ordenventa_id, String estado,String ordenventa_id_erp,String mensajeWS)
    {
        int resultado=0;
        String chkdepositado="1";
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("enviadoERP","1");
            registro.put("recibidoERP",estado);
            registro.put("ordenventa_ERP_id",ordenventa_id_erp);
            registro.put("mensajeWS",mensajeWS);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("ordenventacabecera",registro,"compania_id='"+compania_id+"'"+" and ordenventa_id='"+ordenventa_id+"'" ,null);
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


    public boolean ObtenerCantidadOrdenVentaCabeceraPorOrdenVentaID(String ordenventa_id)
    {
        int resultado=0;
        boolean estado=false;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from ordenventacabecera where ordenventa_id='"+ordenventa_id+"'",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();

        if(resultado>0)
        {
            estado=true;
        }
        else
            {
                estado=false;
            }
        return estado;
    }

    public ArrayList<OrdenVentaCabeceraSQLiteEntity> ObtenerOrdenVentaCabeceraPendientesEnvioWS ()
    {
        listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<OrdenVentaCabeceraSQLiteEntity>();
        OrdenVentaCabeceraSQLiteEntity ordenVentaCabeceraSQLiteEntity;
        abrir();
        try {
        Cursor fila = bd.rawQuery(
                "Select * from ordenventacabecera where enviadoERP='1' and (recibidoERP is null or recibidoERP='0')  ",null);

        while (fila.moveToNext())
        {
            ordenVentaCabeceraSQLiteEntity= new OrdenVentaCabeceraSQLiteEntity();
            ordenVentaCabeceraSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaCabeceraSQLiteEntity.setCliente_id(fila.getString(2));
            ordenVentaCabeceraSQLiteEntity.setDomembarque_id(fila.getString(3));
            ordenVentaCabeceraSQLiteEntity.setTerminopago_id(fila.getString(4));
            ordenVentaCabeceraSQLiteEntity.setAgencia_id(fila.getString(5));
            ordenVentaCabeceraSQLiteEntity.setMoneda_id(fila.getString(6));
            ordenVentaCabeceraSQLiteEntity.setComentario(fila.getString(7));
            ordenVentaCabeceraSQLiteEntity.setAlmacen_id(fila.getString(8));
            ordenVentaCabeceraSQLiteEntity.setImpuesto_id(fila.getString(9));
            ordenVentaCabeceraSQLiteEntity.setMontosubtotal(fila.getString(10));
            ordenVentaCabeceraSQLiteEntity.setMontodescuento(fila.getString(11));
            ordenVentaCabeceraSQLiteEntity.setMontoimpuesto(fila.getString(12));
            ordenVentaCabeceraSQLiteEntity.setMontototal(fila.getString(13));
            ordenVentaCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(14));
            ordenVentaCabeceraSQLiteEntity.setUsuario_id(fila.getString(15));
            ordenVentaCabeceraSQLiteEntity.setEnviadoERP(fila.getString(16));
            ordenVentaCabeceraSQLiteEntity.setRecibidoERP(fila.getString(17));
            ordenVentaCabeceraSQLiteEntity.setOrdenventa_ERP_id(fila.getString(18));
            ordenVentaCabeceraSQLiteEntity.setListaprecio_id(fila.getString(19));
            ordenVentaCabeceraSQLiteEntity.setPlanta_id(fila.getString(20));
            ordenVentaCabeceraSQLiteEntity.setFecharegistro(fila.getString(21));
            ordenVentaCabeceraSQLiteEntity.setTipocambio(fila.getString(22));
            ordenVentaCabeceraSQLiteEntity.setFechatipocambio(fila.getString(23));
            ordenVentaCabeceraSQLiteEntity.setRucdni(fila.getString(24));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDTD(fila.getString(25));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDSD(fila.getString(26));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDCD(fila.getString(27));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_MDMT(fila.getString(28));
            ordenVentaCabeceraSQLiteEntity.setU_SYP_STATUS(fila.getString(29));
            ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(30));
            ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(31));
            ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(32));
            ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(33));

            listaOrdenVentaCabeceraSQLiteEntity.add(ordenVentaCabeceraSQLiteEntity);
        }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS", "Exception"+e.toString() );
        }
        bd.close();
        Log.e("REOS", "listaOrdenVentaCabeceraSQLiteEntity.size(): "+listaOrdenVentaCabeceraSQLiteEntity.size() );
        return listaOrdenVentaCabeceraSQLiteEntity;
    }

}

package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;

import java.util.ArrayList;

public class OrdenVentaCabeceraSQLite {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabeceraSQLiteEntity;

    public OrdenVentaCabeceraSQLite(Context context)
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
            String descuentocontado){

            abrir();

            String dias_documento_vencido="0";
            String igual_mayor_linea_credito="0";

            Cursor fila = bd.rawQuery("SELECT CASE WHEN DueDays IS NULL THEN '0' ELSE DueDays END AS DueDays, (CAST('"+montototal+"' AS decimal)+CAST(linea_credito_usado AS decimal))>=CAST(linea_credito AS decimal) AS linea_validation FROM cliente WHERE cliente_id=? LIMIT 1",new String[]{cliente_id});

            while (fila.moveToNext()){
                dias_documento_vencido=fila.getString(0);
                igual_mayor_linea_credito=fila.getString(1);
            }

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
        registro.put("dueDays_cliente",dias_documento_vencido);
        registro.put("excede_lineacredito",igual_mayor_linea_credito);

        bd.insert("ordenventacabecera",null,registro);
        bd.close();

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

    public ArrayList<OrdenVentaCabeceraSQLiteEntity> ObtenerOrdenVentaCabeceraporID (String ordenventa_id){
        listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<OrdenVentaCabeceraSQLiteEntity>();
        OrdenVentaCabeceraSQLiteEntity ordenVentaCabeceraSQLiteEntity;
        try{
            abrir();
            Cursor fila = bd.rawQuery("SELECT compania_id,ordenventa_id,cliente_id,domembarque_id,terminopago_id," +
                    "agencia_id,moneda_id,comentario,almacen_id,impuesto_id,montosubtotal,montodescuento,montoimpuesto,montototal,fuerzatrabajo_id," +
                    "usuario_id,enviadoERP,recibidoERP,ordenventa_ERP_id,listaprecio_id,planta_id,fecharegistro,tipocambio,fechatipocambio,rucdni,DocType," +
                    "mensajeWS,total_gal_acumulado,descuentocontado,dueDays_cliente,excede_lineacredito" +
                    " FROM ordenventacabecera WHERE ordenventa_id=? LIMIT 1",new String[]{ordenventa_id});
            if (fila.moveToFirst()) {
                do {
                    ordenVentaCabeceraSQLiteEntity= new OrdenVentaCabeceraSQLiteEntity();

                    ordenVentaCabeceraSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                    ordenVentaCabeceraSQLiteEntity.setOrdenventa_id(fila.getString(fila.getColumnIndex("ordenventa_id")));
                    ordenVentaCabeceraSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                    ordenVentaCabeceraSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
                    ordenVentaCabeceraSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
                    ordenVentaCabeceraSQLiteEntity.setAgencia_id(fila.getString(fila.getColumnIndex("agencia_id")));
                    ordenVentaCabeceraSQLiteEntity.setMoneda_id(fila.getString(fila.getColumnIndex("moneda_id")));
                    ordenVentaCabeceraSQLiteEntity.setComentario(fila.getString(fila.getColumnIndex("comentario")));
                    ordenVentaCabeceraSQLiteEntity.setAlmacen_id(fila.getString(fila.getColumnIndex("almacen_id")));
                    ordenVentaCabeceraSQLiteEntity.setImpuesto_id(fila.getString(fila.getColumnIndex("impuesto_id")));
                    ordenVentaCabeceraSQLiteEntity.setMontosubtotal(fila.getString(fila.getColumnIndex("montosubtotal")));
                    ordenVentaCabeceraSQLiteEntity.setMontodescuento(fila.getString(fila.getColumnIndex("montodescuento")));
                    ordenVentaCabeceraSQLiteEntity.setMontoimpuesto(fila.getString(fila.getColumnIndex("montoimpuesto")));
                    ordenVentaCabeceraSQLiteEntity.setMontototal(fila.getString(fila.getColumnIndex("montototal")));

                    ordenVentaCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
                    ordenVentaCabeceraSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
                    ordenVentaCabeceraSQLiteEntity.setEnviadoERP(fila.getString(fila.getColumnIndex("enviadoERP")));
                    ordenVentaCabeceraSQLiteEntity.setRecibidoERP(fila.getString(fila.getColumnIndex("recibidoERP")));
                    ordenVentaCabeceraSQLiteEntity.setOrdenventa_ERP_id(fila.getString(fila.getColumnIndex("ordenventa_ERP_id")));
                    ordenVentaCabeceraSQLiteEntity.setListaprecio_id(fila.getString(fila.getColumnIndex("listaprecio_id")));
                    ordenVentaCabeceraSQLiteEntity.setPlanta_id(fila.getString(fila.getColumnIndex("planta_id")));
                    ordenVentaCabeceraSQLiteEntity.setFecharegistro(fila.getString(fila.getColumnIndex("fecharegistro")));
                    ordenVentaCabeceraSQLiteEntity.setTipocambio(fila.getString(fila.getColumnIndex("tipocambio")));
                    ordenVentaCabeceraSQLiteEntity.setFechatipocambio(fila.getString(fila.getColumnIndex("fechatipocambio")));
                    ordenVentaCabeceraSQLiteEntity.setRucdni(fila.getString(fila.getColumnIndex("rucdni")));

                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDTD(fila.getString(fila.getColumnIndex("compania_id")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDSD(fila.getString(fila.getColumnIndex("compania_id")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDCD(fila.getString(fila.getColumnIndex("compania_id")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDMT(fila.getString(fila.getColumnIndex("compania_id")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_STATUS(fila.getString(fila.getColumnIndex("compania_id")));

                    ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(fila.getColumnIndex("DocType")));
                    ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(fila.getColumnIndex("mensajeWS")));
                    ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(fila.getColumnIndex("total_gal_acumulado")));
                    ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(fila.getColumnIndex("descuentocontado")));
                    ordenVentaCabeceraSQLiteEntity.setDueDays(fila.getString(fila.getColumnIndex("dueDays_cliente")));
                    ordenVentaCabeceraSQLiteEntity.setExcede_lineacredito(fila.getString(fila.getColumnIndex("excede_lineacredito")));

                    listaOrdenVentaCabeceraSQLiteEntity.add(ordenVentaCabeceraSQLiteEntity);
                } while (fila.moveToNext());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            bd.close();
        }

        return listaOrdenVentaCabeceraSQLiteEntity;
    }

    public int ActualizaResultadoOVenviada (String ordenventa_id, String estado,String ordenventa_id_erp,String mensajeWS)
    {
        int resultado=0;

        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("enviadoERP","1");
            registro.put("recibidoERP",estado);
            registro.put("ordenventa_ERP_id",ordenventa_id_erp);
            registro.put("mensajeWS",mensajeWS);
            bd = sqLiteController.getWritableDatabase();
            resultado = bd.update("ordenventacabecera",registro,"ordenventa_id='"+ordenventa_id+"'" ,null);
            bd.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

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

    public ArrayList<String> ObtenerOrdenVentaCabeceraPendientesEnvioWS ()
    {
        ArrayList<String> listSalesOrder = new ArrayList<String>();
        abrir();
        try {
            Cursor fila = bd.rawQuery("Select ordenventa_id  from ordenventacabecera where recibidoERP is null or recibidoERP='0' ",null);

            while (fila.moveToNext())
            {
                listSalesOrder.add(fila.getString(0));
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("REOS", "Exception "+e.toString() );
        }finally {
            bd.close();
        }

        return listSalesOrder;
    }

}

package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;

public class OrdenVentaCabeceraSQLite {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabeceraSQLiteEntity;

    public OrdenVentaCabeceraSQLite(Context context)
    {
        sqliteController = new SqliteController(context);
    }

    public void abrir(){
        Log.i("SQLite", "Se abre conexion a la base de datos " + sqliteController.getDatabaseName() );
        bd = sqliteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqliteController.getDatabaseName() );
        sqliteController.close();
    }

    public int InsertaOrdenVentaCabecera (
            String compania_id,
            String ordenventa_id,
            String cliente_id,
            String domembarque_id,
            String domfactura_id,
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
            String DocType,
            String mensajeWS,
            String total_gal_acumulado,
            String descuentocontado,
            String quotation,
            String U_SYP_MDTD,
            String U_SYP_MDSD,
            String U_SYP_MDCD,
            String U_SYP_MDMT,
            String U_SYP_STATUS,
            String rate,
            String dispatchdate

    ){

            abrir();
            Log.e("REOS","OrdenVentaCabeceraSQLite.InsertaOrdenVentaCabecera-rate:"+ rate);
            String dias_documento_vencido="0";
            String igual_mayor_linea_credito="0";
            String agencia_ruc="";
            String agencia_name="";
            String agencia_address="";
            String cliente_name="";
            String terminoPago_name="";
            String direccion_name="";

            Cursor fila = bd.rawQuery("SELECT (SELECT ruc from agencia where agencia_id=? LIMIT 1)," +
                    "(SELECT agencia from agencia where agencia_id=? LIMIT 1)," +
                    "(SELECT direccion from agencia where agencia_id=? LIMIT 1)," +
                    " CASE WHEN DueDays IS NULL THEN '0' ELSE DueDays END AS DueDays," +
                    " (CAST('"+montototal+"' AS decimal)+CAST(linea_credito_usado AS decimal))>=CAST(linea_credito AS decimal) AS linea_validation," +
                    "nombrecliente," +
                    " (SELECT terminopago FROM terminopago WHERE terminopago_id=? LIMIT 1) AS xd," +
                    "(SELECT direccion FROM direccioncliente WHERE domembarque_id=? AND cliente_id=? LIMIT 1) AS xp FROM cliente WHERE cliente_id=? LIMIT 1",
                    new String[]{agencia_id,agencia_id,agencia_id,terminopago_id,domembarque_id,cliente_id,cliente_id});



            while (fila.moveToNext()){
                agencia_ruc=fila.getString(0);
                agencia_name=fila.getString(1);
                agencia_address=fila.getString(2);

                dias_documento_vencido=fila.getString(3);
                igual_mayor_linea_credito=fila.getString(4);

                cliente_name=fila.getString(5);
                terminoPago_name=fila.getString(6);
                direccion_name=fila.getString(7);
            }

        ContentValues registro = new ContentValues();

        registro.put("domembarque_text",direccion_name);
        registro.put("cliente_text",cliente_name);
        registro.put("terminopago_text",terminoPago_name);
        registro.put("compania_id",compania_id);
        registro.put("ordenventa_id",ordenventa_id);
        registro.put("cliente_id",cliente_id);
        registro.put("domembarque_id",domembarque_id);
        registro.put("domfactura_id",domfactura_id);
        registro.put("terminopago_id",terminopago_id);
        registro.put("agencia_id",agencia_id);
        registro.put("U_VIS_AgencyRUC",agencia_ruc);
        registro.put("U_VIS_AgencyName",agencia_name);
        registro.put("U_VIS_AgencyDir",agencia_address);
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
        registro.put("tipocambio",rate);
        registro.put("fechatipocambio",fechatipocambio);
        registro.put("rucdni",rucdni);
        registro.put("DocType",DocType);
        registro.put("mensajeWS",mensajeWS);
        registro.put("total_gal_acumulado",total_gal_acumulado);
        registro.put("descuentocontado",descuentocontado);
        registro.put("dueDays_cliente",dias_documento_vencido);
        registro.put("excede_lineacredito",igual_mayor_linea_credito);
        registro.put("quotation",quotation);
        registro.put("U_SYP_STATUS",U_SYP_STATUS);
        registro.put("U_SYP_MDTD",U_SYP_MDTD);
        registro.put("U_SYP_MDSD",U_SYP_MDSD);
        registro.put("U_SYP_MDCD",U_SYP_MDCD);
        registro.put("U_SYP_MDMT",U_SYP_MDMT);
        registro.put("dispatchdate",dispatchdate);
        registro.put("countsend","1");

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
            ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(30));
            ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(31));
            ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(32));
            ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(33));
            ordenVentaCabeceraSQLiteEntity.setQuotation(fila.getString(43));
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
            Cursor fila = bd.rawQuery("SELECT compania_id,ordenventa_id,cliente_id,domembarque_id,domfactura_id,terminopago_id," +
                    "agencia_id,U_VIS_AgencyRUC,U_VIS_AgencyName,U_VIS_AgencyDir,moneda_id,comentario,almacen_id,impuesto_id,montosubtotal,montodescuento,montoimpuesto,montototal,fuerzatrabajo_id," +
                    "usuario_id,enviadoERP,recibidoERP,ordenventa_ERP_id,listaprecio_id,planta_id,fecharegistro,tipocambio,fechatipocambio,rucdni,DocType," +
                    "mensajeWS,total_gal_acumulado,descuentocontado,dueDays_cliente,excede_lineacredito,domembarque_text,cliente_text,terminopago_text,quotation,U_SYP_MDTD,U_SYP_MDSD,U_SYP_MDCD,U_SYP_MDMT,U_SYP_STATUS,dispatchdate,countsend " +
                    " FROM ordenventacabecera WHERE ordenventa_id=? LIMIT 1",new String[]{ordenventa_id});
            if (fila.moveToFirst()) {
                do {
                    ordenVentaCabeceraSQLiteEntity= new OrdenVentaCabeceraSQLiteEntity();

                    ordenVentaCabeceraSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
                    ordenVentaCabeceraSQLiteEntity.setOrdenventa_id(fila.getString(fila.getColumnIndex("ordenventa_id")));
                    ordenVentaCabeceraSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
                    ordenVentaCabeceraSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
                    ordenVentaCabeceraSQLiteEntity.setDomfactura_id(fila.getString(fila.getColumnIndex("domfactura_id")));
                    ordenVentaCabeceraSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
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

                    ordenVentaCabeceraSQLiteEntity.setAgencia_id(fila.getString(fila.getColumnIndex("agencia_id")));
                    ordenVentaCabeceraSQLiteEntity.setU_VIS_AgencyRUC(fila.getString(fila.getColumnIndex("U_VIS_AgencyRUC")));
                    ordenVentaCabeceraSQLiteEntity.setU_VIS_AgencyName(fila.getString(fila.getColumnIndex("U_VIS_AgencyName")));
                    ordenVentaCabeceraSQLiteEntity.setU_VIS_AgencyDir(fila.getString(fila.getColumnIndex("U_VIS_AgencyDir")));

                    ordenVentaCabeceraSQLiteEntity.setDocType(fila.getString(fila.getColumnIndex("DocType")));
                    ordenVentaCabeceraSQLiteEntity.setMensajeWS(fila.getString(fila.getColumnIndex("mensajeWS")));
                    ordenVentaCabeceraSQLiteEntity.setTotal_gal_acumulado(fila.getString(fila.getColumnIndex("total_gal_acumulado")));
                    ordenVentaCabeceraSQLiteEntity.setDescuentocontado(fila.getString(fila.getColumnIndex("descuentocontado")));
                    ordenVentaCabeceraSQLiteEntity.setDueDays(fila.getString(fila.getColumnIndex("dueDays_cliente")));
                    ordenVentaCabeceraSQLiteEntity.setExcede_lineacredito(fila.getString(fila.getColumnIndex("excede_lineacredito")));

                    ordenVentaCabeceraSQLiteEntity.setExcede_lineacredito(fila.getString(fila.getColumnIndex("excede_lineacredito")));

                    ordenVentaCabeceraSQLiteEntity.setDomembarque_text(fila.getString(fila.getColumnIndex("domembarque_text")));
                    ordenVentaCabeceraSQLiteEntity.setCliente_text(fila.getString(fila.getColumnIndex("cliente_text")));
                    ordenVentaCabeceraSQLiteEntity.setTerminopago_text(fila.getString(fila.getColumnIndex("terminopago_text")));
                    ordenVentaCabeceraSQLiteEntity.setQuotation(fila.getString(fila.getColumnIndex("quotation")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDTD(fila.getString(fila.getColumnIndex("U_SYP_MDTD")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDSD(fila.getString(fila.getColumnIndex("U_SYP_MDSD")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDCD(fila.getString(fila.getColumnIndex("U_SYP_MDCD")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_MDMT(fila.getString(fila.getColumnIndex("U_SYP_MDMT")));
                    ordenVentaCabeceraSQLiteEntity.setU_SYP_STATUS(fila.getString(fila.getColumnIndex("U_SYP_STATUS")));
                    ordenVentaCabeceraSQLiteEntity.setDispatchdate(fila.getString(fila.getColumnIndex("dispatchdate")));
                    ordenVentaCabeceraSQLiteEntity.setIntent(fila.getString(fila.getColumnIndex("countsend")));

                    listaOrdenVentaCabeceraSQLiteEntity.add(ordenVentaCabeceraSQLiteEntity);

                    UpdateCountSend(fila.getString(fila.getColumnIndex("ordenventa_id")),SesionEntity.compania_id,SesionEntity.usuario_id,fila.getString(fila.getColumnIndex("countsend")));
                } while (fila.moveToNext());
            }
        }catch(Exception e){
            e.printStackTrace();
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
            //registro.put("enviadoERP","1");
            registro.put("recibidoERP",estado);

            if(!estado.equals("0")){
                registro.put("ordenventa_ERP_id",ordenventa_id_erp);
            }

            registro.put("mensajeWS",mensajeWS);
            bd = sqliteController.getWritableDatabase();
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
            Cursor fila = bd.rawQuery("Select ordenventa_id  from ordenventacabecera where recibidoERP is null or recibidoERP='0' and enviadoERP='1' ",null);

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

    public int UpdateStatusOVenviada (String ordenventa_id)
    {
        int resultado=0;

        try {
            abrir();
            ContentValues registro = new ContentValues();
            registro.put("enviadoERP","1");
            //registro.put("recibidoERP",estado);

            /*if(!estado.equals("0")){
                registro.put("ordenventa_ERP_id",ordenventa_id_erp);
            }*/

            //registro.put("mensajeWS",mensajeWS);
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("ordenventacabecera",registro,"ordenventa_id='"+ordenventa_id+"'" ,null);
            bd.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return resultado;
    }

    public int UpdateCountSend (String ordenventa_id, String compania_id,String usuario_id,String countsend)
    {
        int resultado=0;
        abrir();
        try {

            ContentValues registro = new ContentValues();
            registro.put("countsend",String.valueOf(Integer.parseInt(countsend)+1));
            bd = sqliteController.getWritableDatabase();
            resultado = bd.update("ordenventacabecera",registro,"ordenventa_id='"+ordenventa_id+"'"+" and compania_id='"+compania_id+"'"+" and usuario_id='"+usuario_id+"'" ,null);

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

}

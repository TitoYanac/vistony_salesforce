package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DetailDispatchSheetEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailDispatchSheetSQLite {
    SqliteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<HojaDespachoDetalleSQLiteEntity> listaHojaDespachoSQLiteEntity;
    ArrayList<HistoricStatusDispatchEntity> listaHistoricStatusDispatchEntity;
    public DetailDispatchSheetSQLite(Context context)
    {
        sqLiteController = new SqliteController(context);
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


    public int InsertDetailDispatchSheet (
            /*String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String control_id,
            String item_id,
            String cliente_id,
            String domembarque_id,
            String direccion,
            String factura_id,
            String entrega_id,
            String entrega,
            String factura,
            String saldo,
            String estado,
            String fuerzatrabajo_factura_id,
            String fuerzatrabajo_factura,
            String terminopago_id,
            String terminopago,
            String peso,
            String comentariodespacho*/
            List<DetailDispatchSheetEntity> detailDispatchSheetEntity,
            String FechaDespacho

    )
    {
        abrir();
        for (int i = 0; i < detailDispatchSheetEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("fuerzatrabajo_id",SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("control_id",detailDispatchSheetEntity.get(i).getControlid());
            registro.put("item_id",detailDispatchSheetEntity.get(i).getItemid());
            registro.put("cliente_id",detailDispatchSheetEntity.get(i).getClienteid());
            registro.put("domembarque_id",detailDispatchSheetEntity.get(i).getDomembarque_id());
            registro.put("direccion",detailDispatchSheetEntity.get(i).getDireccion());
            registro.put("factura_id",detailDispatchSheetEntity.get(i).getFactura_id());
            registro.put("entrega_id",detailDispatchSheetEntity.get(i).getEntrega_id());
            registro.put("entrega",detailDispatchSheetEntity.get(i).getEntrega());
            registro.put("factura",detailDispatchSheetEntity.get(i).getFactura());
            //registro.put("factura",detailDispatchSheetEntity.get(i).getEntrega());
            registro.put("saldo",detailDispatchSheetEntity.get(i).getSaldo());
            registro.put("estado",detailDispatchSheetEntity.get(i).getEstado());
            registro.put("fuerzatrabajo_factura_id",detailDispatchSheetEntity.get(i).getFuerzatrabajo_id());
            registro.put("fuerzatrabajo_factura",detailDispatchSheetEntity.get(i).getFuerzatrabajo());
            //registro.put("fuerzatrabajo_factura","3JAVIER VERGARA");
            registro.put("terminopago_id",detailDispatchSheetEntity.get(i).getTerminoPago_id());
            registro.put("terminopago",detailDispatchSheetEntity.get(i).getTerminoPago());
            //registro.put("terminopago","Contado");
            registro.put("peso",detailDispatchSheetEntity.get(i).getPeso());
            registro.put("comentariodespacho",detailDispatchSheetEntity.get(i).getComentario_despacho());
            registro.put("estado_id",detailDispatchSheetEntity.get(i).getEstado_id());
            registro.put("motivo",detailDispatchSheetEntity.get(i).getMotivo());
            registro.put("motivo_id",detailDispatchSheetEntity.get(i).getMotivo_id());
            registro.put("fotoguia",detailDispatchSheetEntity.get(i).getFotoguia());
            registro.put("fotolocal",detailDispatchSheetEntity.get(i).getFotolocal());

            bd.insert("detaildispatchsheet",null,registro);
        }

        bd.close();
        return 1;
    }

    public ArrayList<HojaDespachoDetalleSQLiteEntity>
            getDetailDispatchSheetforCodeControl
            (String codeControl)
    {

        listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.compania_id,'') as chkupdatedispatch " +
                        ", IFNULL(E.timeini,'0') timeini, IFNULL(E.timefin,'0') timefin,IFNULL(F.documento_id,'')   chk_collection,G.typedispatch,H.reasondispatch " +
                        //", '0' timeini, '0' timefin " +
                        "from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id ) D ON  " +
                        "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                        "A.control_id=D.control_id   " +
                        " left outer join (SELECT compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id FROM statusdispatch group by compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id ) C ON  " +
                        "A.cliente_id=C.cliente_id  AND " +
                        "A.entrega_id=C.entrega_id  AND " +
                        "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                        //"C.fecha_registro=D.fechahojadespacho " +
                        "A.control_id=C.control_id AND " +
                        "A.item_id=C.item_id " +
                        " left outer join visitsection E ON  " +
                        "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                        "D.control_id=E.idref AND " +
                        "A.cliente_id=E.cliente_id AND " +
                        "A.domembarque_id=E.domembarque_id  " +
                        "LEFT OUTER JOIN cobranzadetalle F ON " +
                        "A.cliente_id=F.cliente_id  AND " +
                        "F.docentry=A.factura_id" +
                        " LEFT OUTER JOIN typedispatch G ON " +
                        "A.estado_id=G.typedispatch_id  " +
                        " LEFT OUTER JOIN reasondispatch H ON " +
                        "A.motivo_id=H.reasondispatch_id  " +
                        "  where A.control_id='"+codeControl+"'" +
                        " ORDER BY A.item_id",null);

        while (fila.moveToNext())
        {
            boolean chkupdatedispatch,chkvisitsectionstart,chkvisitsectionend,chkcollection;
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            Log.e("REOS", "DetailDispatchSheetSQLite-getDetailDispatchSheetforCodeControl-chkupdatedispatch" + fila.getString(fila.getColumnIndex("chkupdatedispatch")));
            if(fila.getString(fila.getColumnIndex("chkupdatedispatch")).equals(""))
            {
                chkupdatedispatch=false;
            }else {
                chkupdatedispatch=true;
            }
            hojaDespachoSQLiteEntity.setChkupdatedispatch(chkupdatedispatch);
            if(fila.getString(fila.getColumnIndex("timeini")).equals("0"))
            {
                chkvisitsectionstart=false;
            }else {
                chkvisitsectionstart=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionstart (chkvisitsectionstart);
            if(fila.getString(fila.getColumnIndex("timefin")).equals("0"))
            {
                chkvisitsectionend=false;
            }else {
                chkvisitsectionend=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionend (chkvisitsectionend);

            if(fila.getString(fila.getColumnIndex("chk_collection")).equals(""))
            {
                chkcollection=false;
            }else {
                chkcollection=true;
            }
            hojaDespachoSQLiteEntity.setChkcollection (chkcollection);
            hojaDespachoSQLiteEntity.setEstado (fila.getString(fila.getColumnIndex("typedispatch")));
            hojaDespachoSQLiteEntity.setOcurrencies(fila.getString(fila.getColumnIndex("reasondispatch")));


            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

    public int ClearTableDetailDispatchSheet ()
    {
        abrir();
        bd.execSQL("delete from detaildispatchsheet ");
        bd.close();
        return 1;
    }

    public int getCountDetailDispatchSheet ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from detaildispatchsheet",null);

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

    public int UpdateBalanceDetailDispatchSheet (String compania_id, String documento_id, String nuevo_saldo)
    {
        int resultado=0;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

            ContentValues registro = new ContentValues();
            registro.put("saldo",nuevo_saldo);

            resultado = sqlite.update("detaildispatchsheet",registro,"factura_id='"+documento_id+"'"+" and compania_id='"+compania_id+"'" ,null);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return resultado;
    }

    public ArrayList<HojaDespachoDetalleSQLiteEntity>
    getDetailDispatchSheetforClient
            (String cliente_id)
    {

        listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,IFNULL(A.entrega,'') entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,IFNULL(B.nombrecliente,'') nombrecliente from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        "  where A.cliente_id='"+cliente_id+"'",null);

        while (fila.moveToNext())
        {
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

    public ArrayList<HojaDespachoDetalleSQLiteEntity>
    getDetailDispatchSheetforControlID
            (String control_id,String item_id)
    {

        listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,IFNULL(A.entrega,'') entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,IFNULL(B.nombrecliente,'') nombrecliente from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        "  where A.control_id='"+control_id+"' and A.item_id='"+item_id+"' ",null);

        while (fila.moveToNext())
        {
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

    public ArrayList<HistoricStatusDispatchEntity>
    getDetailDispatchSheetforDateDispatch
            (String dateDispatch)
    {

        listaHistoricStatusDispatchEntity = new ArrayList<>();
        HistoricStatusDispatchEntity historicStatusDispatchEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
                        "A.saldo,F.typedispatch,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.chkrecibido,'0') as chkupdatedispatch " +
                        ", IFNULL(E.timeini,'0') timeini, IFNULL(E.timefin,'0') timefin " +
                        ", a.estado_id,g.reasondispatch,a.motivo_id,a.fotoguia,a.fotolocal,c.messageServerDispatch,c.messageServerTimeDispatch,D.drivermobile,D.drivername,A.saldo  " +
                        "from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id,drivermobile,drivername FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id,drivermobile,drivername ) D ON  " +
                        "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                        "A.control_id=D.control_id   " +
                        " left outer join (SELECT cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id,chkrecibido,messageServerDispatch,messageServerTimeDispatch  FROM statusdispatch GROUP BY cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id,chkrecibido,messageServerDispatch,messageServerTimeDispatch ) C ON  " +
                        "A.cliente_id=C.cliente_id  AND " +
                        "A.entrega_id=C.entrega_id  AND " +
                        "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                        "A.control_id=C.control_id AND " +
                        "A.item_id=C.item_id " +
                        " left outer join visitsection E ON  " +
                        "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                        "D.control_id=E.idref AND " +
                        "A.cliente_id=E.cliente_id AND " +
                        "A.domembarque_id=E.domembarque_id" +
                        " LEFT OUTER JOIN typedispatch F ON " +
                        "F.typedispatch_id=A.estado_id " +
                        " LEFT OUTER JOIN reasondispatch G ON " +
                        "A.motivo_id=G.reasondispatch_id" +
                        "  where D.fechahojadespacho='"+dateDispatch+"'",null);

        while (fila.moveToNext())
        {
            historicStatusDispatchEntity= new HistoricStatusDispatchEntity();
            historicStatusDispatchEntity.setFuerzaTrabajo_ID(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            historicStatusDispatchEntity.setUsuario_ID(fila.getString(fila.getColumnIndex("usuario_id")));
            historicStatusDispatchEntity.setTipoDespacho_ID(fila.getString(fila.getColumnIndex("estado_id")));
            historicStatusDispatchEntity.setTipoDespacho(fila.getString(fila.getColumnIndex("typedispatch")));
            historicStatusDispatchEntity.setMotivoDespacho_ID(fila.getString(fila.getColumnIndex("motivo_id")));
            historicStatusDispatchEntity.setMotivoDespacho(fila.getString(fila.getColumnIndex("reasondispatch")));
            historicStatusDispatchEntity.setObservacion(fila.getString(fila.getColumnIndex("comentariodespacho")));
            historicStatusDispatchEntity.setLatitud(fila.getString(fila.getColumnIndex("direccion")));
            historicStatusDispatchEntity.setLongitud(fila.getString(fila.getColumnIndex("factura_id")));
            historicStatusDispatchEntity.setCliente_ID(fila.getString(fila.getColumnIndex("cliente_id")));
            historicStatusDispatchEntity.setCliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            historicStatusDispatchEntity.setFotoGuia(fila.getString(fila.getColumnIndex("fotoguia")));
            historicStatusDispatchEntity.setFotoLocal(fila.getString(fila.getColumnIndex("fotolocal")));
            historicStatusDispatchEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            //historicStatusDispatchEntity.setChk_Recibido ("Y");
            if(fila.getString(fila.getColumnIndex("chkupdatedispatch")).equals("0"))
            {
                historicStatusDispatchEntity.setChk_Recibido ("N");
            }else {
                historicStatusDispatchEntity.setChk_Recibido ("Y");
            }
            historicStatusDispatchEntity.setMessageServerDispatch(fila.getString(fila.getColumnIndex("messageServerDispatch")));
            historicStatusDispatchEntity.setMessageServerTimeDispatch(fila.getString(fila.getColumnIndex("messageServerTimeDispatch")));
            historicStatusDispatchEntity.setDrivermobile(fila.getString(fila.getColumnIndex("drivermobile")));
            historicStatusDispatchEntity.setDrivername(fila.getString(fila.getColumnIndex("drivername")));
            historicStatusDispatchEntity.setAmount(fila.getString(fila.getColumnIndex("saldo")));
            listaHistoricStatusDispatchEntity.add(historicStatusDispatchEntity);
        }

        bd.close();
        return listaHistoricStatusDispatchEntity;
    }


    public ArrayList<HojaDespachoDetalleSQLiteEntity>
    getDetailDispatchSheetforDispatchDate
            (String dateDispatch)
    {

        listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.compania_id,'') as chkupdatedispatch " +
                        ", IFNULL(E.timeini,'0') timeini, IFNULL(A.estado,IFNULL(E.timefin,'0')) timefin,IFNULL(F.documento_id,'')   chk_collection,G.typedispatch,H.reasondispatch,IFNULL(I.latitud,'0') as latitud, IFNULL(I.longitud,'0') as longitud " +
                        //", '0' timeini, '0' timefin " +
                        "from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id ) D ON  " +
                        "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                        "A.control_id=D.control_id   " +
                        " left outer join (SELECT compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id FROM statusdispatch group by compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id ) C ON  " +
                        "A.cliente_id=C.cliente_id  AND " +
                        "A.entrega_id=C.entrega_id  AND " +
                        "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                        //"C.fecha_registro=D.fechahojadespacho " +
                        "A.control_id=C.control_id AND " +
                        "A.item_id=C.item_id " +
                        " left outer join visitsection E ON  " +
                        "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                        "D.control_id=E.idref AND " +
                        "A.cliente_id=E.cliente_id AND " +
                        "A.domembarque_id=E.domembarque_id  " +
                        "LEFT OUTER JOIN cobranzadetalle F ON " +
                        "A.cliente_id=F.cliente_id  AND " +
                        "F.docentry=A.factura_id" +
                        " LEFT OUTER JOIN typedispatch G ON " +
                        "A.estado_id=G.typedispatch_id  " +
                        " LEFT OUTER JOIN reasondispatch H ON " +
                        "A.motivo_id=H.reasondispatch_id  " +
                        " LEFT OUTER JOIN direccioncliente I ON " +
                        "a.cliente_id=I.cliente_id and " +
                        "a.domembarque_id=I.domembarque_id  " +
                        " where D.fechahojadespacho='"+dateDispatch+"'" +
                        " AND G.typedispatch_id in ('S','P')" +
                        " ORDER BY A.item_id",null);

        while (fila.moveToNext())
        {
            boolean chkupdatedispatch,chkvisitsectionstart,chkvisitsectionend,chkcollection;
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            Log.e("REOS", "DetailDispatchSheetSQLite-getDetailDispatchSheetforCodeControl-chkupdatedispatch" + fila.getString(fila.getColumnIndex("chkupdatedispatch")));
            if(fila.getString(fila.getColumnIndex("chkupdatedispatch")).equals(""))
            {
                chkupdatedispatch=false;
            }else {
                chkupdatedispatch=true;
            }
            hojaDespachoSQLiteEntity.setChkupdatedispatch(chkupdatedispatch);
            if(fila.getString(fila.getColumnIndex("timeini")).equals("0"))
            {
                chkvisitsectionstart=false;
            }else {
                chkvisitsectionstart=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionstart (chkvisitsectionstart);
            if(fila.getString(fila.getColumnIndex("timefin")).equals("0"))
            {
                chkvisitsectionend=false;
            }else {
                chkvisitsectionend=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionend (chkvisitsectionend);

            if(fila.getString(fila.getColumnIndex("chk_collection")).equals(""))
            {
                chkcollection=false;
            }else {
                chkcollection=true;
            }
            hojaDespachoSQLiteEntity.setChkcollection (chkcollection);
            hojaDespachoSQLiteEntity.setEstado (fila.getString(fila.getColumnIndex("typedispatch")));
            hojaDespachoSQLiteEntity.setOcurrencies(fila.getString(fila.getColumnIndex("reasondispatch")));
            hojaDespachoSQLiteEntity.setLatitud (fila.getString(fila.getColumnIndex("latitud")));
            hojaDespachoSQLiteEntity.setLongitud (fila.getString(fila.getColumnIndex("longitud")));

            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

    public ArrayList<HojaDespachoDetalleSQLiteEntity>
    getDetailDispatchSheetforDispatchDateSucessful
            (String dateDispatch)
    {

        listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.compania_id,'') as chkupdatedispatch " +
                        ", IFNULL(E.timeini,'0') timeini, IFNULL(A.estado,IFNULL(E.timefin,'0')) timefin,IFNULL(F.documento_id,'')   chk_collection,G.typedispatch,H.reasondispatch,IFNULL(I.latitud,'0') latitud,IFNULL(I.longitud,'0') longitud " +
                        //", '0' timeini, '0' timefin " +
                        "from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id ) D ON  " +
                        "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                        "A.control_id=D.control_id   " +
                        " left outer join (SELECT compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id FROM statusdispatch group by compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id ) C ON  " +
                        "A.cliente_id=C.cliente_id  AND " +
                        "A.entrega_id=C.entrega_id  AND " +
                        "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                        //"C.fecha_registro=D.fechahojadespacho " +
                        "A.control_id=C.control_id AND " +
                        "A.item_id=C.item_id " +
                        " left outer join visitsection E ON  " +
                        "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                        "D.control_id=E.idref AND " +
                        "A.cliente_id=E.cliente_id AND " +
                        "A.domembarque_id=E.domembarque_id  " +
                        "LEFT OUTER JOIN cobranzadetalle F ON " +
                        "A.cliente_id=F.cliente_id  AND " +
                        "F.docentry=A.factura_id" +
                        " LEFT OUTER JOIN typedispatch G ON " +
                        "A.estado_id=G.typedispatch_id  " +
                        " LEFT OUTER JOIN reasondispatch H ON " +
                        "A.motivo_id=H.reasondispatch_id  " +
                        " LEFT OUTER JOIN direccioncliente I ON " +
                        "a.cliente_id=I.cliente_id  AND " +
                        "a.domembarque_id=I.domembarque_id  " +
                        " where D.fechahojadespacho='"+dateDispatch+"'" +
                        " AND G.typedispatch_id in ('E') " +
                        " ORDER BY A.item_id",null);

        while (fila.moveToNext())
        {
            boolean chkupdatedispatch,chkvisitsectionstart,chkvisitsectionend,chkcollection;
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            Log.e("REOS", "DetailDispatchSheetSQLite-getDetailDispatchSheetforCodeControl-chkupdatedispatch" + fila.getString(fila.getColumnIndex("chkupdatedispatch")));
            if(fila.getString(fila.getColumnIndex("chkupdatedispatch")).equals(""))
            {
                chkupdatedispatch=false;
            }else {
                chkupdatedispatch=true;
            }
            hojaDespachoSQLiteEntity.setChkupdatedispatch(chkupdatedispatch);
            if(fila.getString(fila.getColumnIndex("timeini")).equals("0"))
            {
                chkvisitsectionstart=false;
            }else {
                chkvisitsectionstart=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionstart (chkvisitsectionstart);
            if(fila.getString(fila.getColumnIndex("timefin")).equals("0"))
            {
                chkvisitsectionend=false;
            }else {
                chkvisitsectionend=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionend (chkvisitsectionend);

            if(fila.getString(fila.getColumnIndex("chk_collection")).equals(""))
            {
                chkcollection=false;
            }else {
                chkcollection=true;
            }
            hojaDespachoSQLiteEntity.setChkcollection (chkcollection);
            hojaDespachoSQLiteEntity.setEstado (fila.getString(fila.getColumnIndex("typedispatch")));
            hojaDespachoSQLiteEntity.setOcurrencies(fila.getString(fila.getColumnIndex("reasondispatch")));
            hojaDespachoSQLiteEntity.setLatitud (fila.getString(fila.getColumnIndex("latitud")));
            hojaDespachoSQLiteEntity.setLongitud (fila.getString(fila.getColumnIndex("longitud")));

            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

    public ArrayList<HojaDespachoDetalleSQLiteEntity>
    getDetailDispatchSheetforDispatchDateFailed
            (String dateDispatch)
    {

        listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.compania_id,'') as chkupdatedispatch " +
                        ", IFNULL(E.timeini,'0') timeini, IFNULL(A.estado,IFNULL(E.timefin,'0')) timefin,IFNULL(F.documento_id,'')   chk_collection,G.typedispatch,H.reasondispatch,IFNULL(I.latitud,'0') latitud,IFNULL(I.longitud,'0') longitud  " +
                        //", '0' timeini, '0' timefin " +
                        "from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id ) D ON  " +
                        "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                        "A.control_id=D.control_id   " +
                        " left outer join (SELECT compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id FROM statusdispatch group by compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id ) C ON  " +
                        "A.cliente_id=C.cliente_id  AND " +
                        "A.entrega_id=C.entrega_id  AND " +
                        "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                        //"C.fecha_registro=D.fechahojadespacho " +
                        "A.control_id=C.control_id AND " +
                        "A.item_id=C.item_id " +
                        " left outer join visitsection E ON  " +
                        "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                        "D.control_id=E.idref AND " +
                        "A.cliente_id=E.cliente_id AND " +
                        "A.domembarque_id=E.domembarque_id  " +
                        "LEFT OUTER JOIN cobranzadetalle F ON " +
                        "A.cliente_id=F.cliente_id  AND " +
                        "F.docentry=A.factura_id" +
                        " LEFT OUTER JOIN typedispatch G ON " +
                        "A.estado_id=G.typedispatch_id  " +
                        " LEFT OUTER JOIN reasondispatch H ON " +
                        "A.motivo_id=H.reasondispatch_id  " +
                        " LEFT OUTER JOIN direccioncliente I ON " +
                        "a.cliente_id=I.cliente_id AND " +
                        "a.domembarque_id=I.domembarque_id  " +
                        " where D.fechahojadespacho='"+dateDispatch+"'" +
                        " AND G.typedispatch_id in ('A','V') " +
                        " ORDER BY A.item_id",null);

        while (fila.moveToNext())
        {
            boolean chkupdatedispatch,chkvisitsectionstart,chkvisitsectionend,chkcollection;
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            Log.e("REOS", "DetailDispatchSheetSQLite-getDetailDispatchSheetforCodeControl-chkupdatedispatch" + fila.getString(fila.getColumnIndex("chkupdatedispatch")));
            if(fila.getString(fila.getColumnIndex("chkupdatedispatch")).equals(""))
            {
                chkupdatedispatch=false;
            }else {
                chkupdatedispatch=true;
            }
            hojaDespachoSQLiteEntity.setChkupdatedispatch(chkupdatedispatch);
            if(fila.getString(fila.getColumnIndex("timeini")).equals("0"))
            {
                chkvisitsectionstart=false;
            }else {
                chkvisitsectionstart=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionstart (chkvisitsectionstart);
            if(fila.getString(fila.getColumnIndex("timefin")).equals("0"))
            {
                chkvisitsectionend=false;
            }else {
                chkvisitsectionend=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionend (chkvisitsectionend);

            if(fila.getString(fila.getColumnIndex("chk_collection")).equals(""))
            {
                chkcollection=false;
            }else {
                chkcollection=true;
            }
            hojaDespachoSQLiteEntity.setChkcollection (chkcollection);
            hojaDespachoSQLiteEntity.setEstado (fila.getString(fila.getColumnIndex("typedispatch")));
            hojaDespachoSQLiteEntity.setOcurrencies(fila.getString(fila.getColumnIndex("reasondispatch")));
            hojaDespachoSQLiteEntity.setLatitud (fila.getString(fila.getColumnIndex("latitud")));
            hojaDespachoSQLiteEntity.setLongitud (fila.getString(fila.getColumnIndex("longitud")));

            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

    public ArrayList<HojaDespachoDetalleSQLiteEntity>
    getDetailDispatchSheetforCodeControlMap
            (String dateDispatch)
    {

        ArrayList<HojaDespachoDetalleSQLiteEntity> listaHojaDespachoSQLiteEntity = new ArrayList<>();
        HojaDespachoDetalleSQLiteEntity hojaDespachoSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  A.compania_id,A.fuerzatrabajo_id,A.usuario_id,A.control_id,A.item_id,A.cliente_id,A.domembarque_id,A.direccion,A.factura_id,A.entrega_id,A.entrega,A.factura," +
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente,IFNULL(c.compania_id,'') as chkupdatedispatch " +
                        ", IFNULL(E.timeini,'0') timeini, IFNULL(E.timefin,'0') timefin,IFNULL(F.documento_id,'')   chk_collection,G.typedispatch,IFNULL(H.reasondispatch,'') as reasondispatch,IFNULL(I.latitud,'0') latitud,IFNULL(I.longitud,'0') longitud  " +
                        //", '0' timeini, '0' timefin " +
                        "from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        " left outer join (SELECT control_id,fechahojadespacho,fuerzatrabajo_id FROM headerdispatchsheet group by control_id,fechahojadespacho,fuerzatrabajo_id ) D ON  " +
                        "A.fuerzatrabajo_id=D.fuerzatrabajo_id  AND " +
                        "A.control_id=D.control_id   " +
                        " left outer join (SELECT compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id FROM statusdispatch group by compania_id,cliente_id,entrega_id,fuerzatrabajo_id,control_id,item_id ) C ON  " +
                        "A.cliente_id=C.cliente_id  AND " +
                        "A.entrega_id=C.entrega_id  AND " +
                        "A.fuerzatrabajo_id=C.fuerzatrabajo_id AND " +
                        //"C.fecha_registro=D.fechahojadespacho " +
                        "A.control_id=C.control_id AND " +
                        "A.item_id=C.item_id " +
                        " left outer join visitsection E ON  " +
                        "D.fuerzatrabajo_id=E.fuerzatrabajo_id  AND " +
                        "D.control_id=E.idref AND " +
                        "A.cliente_id=E.cliente_id AND " +
                        "A.domembarque_id=E.domembarque_id  " +
                        "LEFT OUTER JOIN cobranzadetalle F ON " +
                        "A.cliente_id=F.cliente_id  AND " +
                        "F.docentry=A.factura_id" +
                        " LEFT OUTER JOIN typedispatch G ON " +
                        "A.estado_id=G.typedispatch_id  " +
                        " LEFT OUTER JOIN reasondispatch H ON " +
                        "A.motivo_id=H.reasondispatch_id  " +
                        " LEFT OUTER JOIN direccioncliente I ON " +
                        "A.cliente_id=I.cliente_id AND " +
                        "A.domembarque_id=I.domembarque_id  " +
                        //"  where A.control_id='"+codeControl+"'" +
                        " where D.fechahojadespacho='"+dateDispatch+"'" +
                        //" AND G.typedispatch_id in ('A','V') " +
                        " ORDER BY A.item_id",null);
                        //" ORDER BY A.item_id",null);

        while (fila.moveToNext())
        {
            boolean chkupdatedispatch,chkvisitsectionstart,chkvisitsectionend,chkcollection;
            hojaDespachoSQLiteEntity= new HojaDespachoDetalleSQLiteEntity();
            hojaDespachoSQLiteEntity.setCompania_id(fila.getString(fila.getColumnIndex("compania_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_id")));
            hojaDespachoSQLiteEntity.setUsuario_id(fila.getString(fila.getColumnIndex("usuario_id")));
            hojaDespachoSQLiteEntity.setControl_id(fila.getString(fila.getColumnIndex("control_id")));
            hojaDespachoSQLiteEntity.setItem_id(fila.getString(fila.getColumnIndex("item_id")));
            hojaDespachoSQLiteEntity.setCliente_id(fila.getString(fila.getColumnIndex("cliente_id")));
            hojaDespachoSQLiteEntity.setDomembarque_id(fila.getString(fila.getColumnIndex("domembarque_id")));
            hojaDespachoSQLiteEntity.setDireccion(fila.getString(fila.getColumnIndex("direccion")));
            hojaDespachoSQLiteEntity.setFactura_id(fila.getString(fila.getColumnIndex("factura_id")));
            hojaDespachoSQLiteEntity.setEntrega_id(fila.getString(fila.getColumnIndex("entrega_id")));
            hojaDespachoSQLiteEntity.setEntrega(fila.getString(fila.getColumnIndex("entrega")));
            hojaDespachoSQLiteEntity.setFactura(fila.getString(fila.getColumnIndex("factura")));
            hojaDespachoSQLiteEntity.setSaldo(fila.getString(fila.getColumnIndex("saldo")));
            hojaDespachoSQLiteEntity.setEstado(fila.getString(fila.getColumnIndex("estado")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura_id(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura_id")));
            hojaDespachoSQLiteEntity.setFuerzatrabajo_factura(fila.getString(fila.getColumnIndex("fuerzatrabajo_factura")));
            hojaDespachoSQLiteEntity.setTerminopago_id(fila.getString(fila.getColumnIndex("terminopago_id")));
            hojaDespachoSQLiteEntity.setTerminopago(fila.getString(fila.getColumnIndex("terminopago")));
            hojaDespachoSQLiteEntity.setPeso(fila.getString(fila.getColumnIndex("peso")));
            hojaDespachoSQLiteEntity.setComentariodespacho(fila.getString(fila.getColumnIndex("comentariodespacho")));
            hojaDespachoSQLiteEntity.setNombrecliente(fila.getString(fila.getColumnIndex("nombrecliente")));
            Log.e("REOS", "DetailDispatchSheetSQLite-getDetailDispatchSheetforCodeControl-chkupdatedispatch" + fila.getString(fila.getColumnIndex("chkupdatedispatch")));
            if(fila.getString(fila.getColumnIndex("chkupdatedispatch")).equals(""))
            {
                chkupdatedispatch=false;
            }else {
                chkupdatedispatch=true;
            }
            hojaDespachoSQLiteEntity.setChkupdatedispatch(chkupdatedispatch);
            if(fila.getString(fila.getColumnIndex("timeini")).equals("0"))
            {
                chkvisitsectionstart=false;
            }else {
                chkvisitsectionstart=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionstart (chkvisitsectionstart);
            if(fila.getString(fila.getColumnIndex("timefin")).equals("0"))
            {
                chkvisitsectionend=false;
            }else {
                chkvisitsectionend=true;
            }
            hojaDespachoSQLiteEntity.setChkvisitsectionend (chkvisitsectionend);

            if(fila.getString(fila.getColumnIndex("chk_collection")).equals(""))
            {
                chkcollection=false;
            }else {
                chkcollection=true;
            }
            hojaDespachoSQLiteEntity.setChkcollection (chkcollection);
            hojaDespachoSQLiteEntity.setEstado (fila.getString(fila.getColumnIndex("typedispatch")));
            hojaDespachoSQLiteEntity.setOcurrencies(fila.getString(fila.getColumnIndex("reasondispatch")));
            hojaDespachoSQLiteEntity.setLatitud(fila.getString(fila.getColumnIndex("latitud")));
            hojaDespachoSQLiteEntity.setLongitud(fila.getString(fila.getColumnIndex("longitud")));
            listaHojaDespachoSQLiteEntity.add(hojaDespachoSQLiteEntity);
        }

        bd.close();
        return listaHojaDespachoSQLiteEntity;
    }

}

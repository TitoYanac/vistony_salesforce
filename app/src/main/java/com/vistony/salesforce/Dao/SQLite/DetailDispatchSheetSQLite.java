package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DetailDispatchSheetEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class DetailDispatchSheetSQLite {
    SqliteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<HojaDespachoDetalleSQLiteEntity> listaHojaDespachoSQLiteEntity;

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
            registro.put("saldo",detailDispatchSheetEntity.get(i).getSaldo());
            registro.put("estado",detailDispatchSheetEntity.get(i).getEstado());
            registro.put("fuerzatrabajo_factura_id",detailDispatchSheetEntity.get(i).getFuerzatrabajo_id());
            registro.put("fuerzatrabajo_factura",detailDispatchSheetEntity.get(i).getFuerzatrabajo());
            registro.put("terminopago_id",detailDispatchSheetEntity.get(i).getTerminoPago_id());
            registro.put("terminopago",detailDispatchSheetEntity.get(i).getTerminoPago());
            registro.put("peso",detailDispatchSheetEntity.get(i).getPeso());
            registro.put("comentariodespacho",detailDispatchSheetEntity.get(i).getComentario_despacho());
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
                        "A.saldo,A.estado,A.fuerzatrabajo_factura_id,A.fuerzatrabajo_factura,A.terminopago_id,A.terminopago,A.peso,A.comentariodespacho,B.nombrecliente from detaildispatchsheet A" +
                        " left outer join cliente B ON  " +
                        "A.cliente_id=B.cliente_id " +
                        "  where A.control_id='"+codeControl+"'",null);

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

}

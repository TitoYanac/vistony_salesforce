package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetalleSQLiteEntity;

import java.util.ArrayList;

public class OrdenVentaDetalleSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<OrdenVentaDetalleSQLiteEntity> listaOrdenVentaDetalleSQLiteEntity;

    public OrdenVentaDetalleSQLiteDao(Context context)
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


    public int InsertaOrdenVentaDetalle (
            String compania_id,
            String ordenventa_id,
            String lineaordenventa_id,
            String producto_id,
            String umd,
            String cantidad,
            String preciounitario,
            String montosubtotal,
            String porcentajedescuento,
            String montodescuento,
            String montoimpuesto,
            String montototallinea,
            String lineareferencia,
            String impuesto_id,
            String producto,
            String AcctCode,
            String almacen_id,
            String promocion_id,
            String gal_unitario,
            String gal_acumulado,
            String U_SYP_FECAT07,
            String montosubtotalcondescuento,
            String chk_descuentocontado
    ){
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("ordenventa_id",ordenventa_id);
        registro.put("lineaordenventa_id",lineaordenventa_id);
        registro.put("producto_id",producto_id);
        registro.put("umd",umd);
        registro.put("cantidad",cantidad);
        registro.put("preciounitario",preciounitario);
        registro.put("montosubtotal",montosubtotal);
        registro.put("porcentajedescuento",porcentajedescuento);
        registro.put("montodescuento",montodescuento);
        registro.put("montoimpuesto",montoimpuesto);
        registro.put("montototallinea",montototallinea);
        registro.put("lineareferencia",lineareferencia);
        registro.put("impuesto_id",impuesto_id);
        registro.put("producto",producto);
        registro.put("AcctCode",AcctCode);
        registro.put("almacen_id",almacen_id);
        registro.put("promocion_id",promocion_id);
        registro.put("gal_unitario",gal_unitario);
        registro.put("gal_acumulado",gal_acumulado);
        registro.put("U_SYP_FECAT07",U_SYP_FECAT07);
        registro.put("montosubtotalcondescuento",montosubtotalcondescuento);
        registro.put("chk_descuentocontado",chk_descuentocontado);

        bd.insert("ordenventadetalle",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<OrdenVentaDetalleSQLiteEntity> ObtenerOrdenVentaCabecera ()
    {
        listaOrdenVentaDetalleSQLiteEntity = new ArrayList<OrdenVentaDetalleSQLiteEntity>();
        OrdenVentaDetalleSQLiteEntity ordenVentaDetalleSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ordenventadetalle",null);

        while (fila.moveToNext())
        {
            ordenVentaDetalleSQLiteEntity= new OrdenVentaDetalleSQLiteEntity();
            ordenVentaDetalleSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaDetalleSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaDetalleSQLiteEntity.setLineaordenventa_id(fila.getString(2));
            ordenVentaDetalleSQLiteEntity.setProducto_id(fila.getString(3));
            ordenVentaDetalleSQLiteEntity.setUmd(fila.getString(4));
            ordenVentaDetalleSQLiteEntity.setCantidad(fila.getString(5));
            ordenVentaDetalleSQLiteEntity.setPreciounitario(fila.getString(6));
            ordenVentaDetalleSQLiteEntity.setMontosubtotal(fila.getString(7));
            ordenVentaDetalleSQLiteEntity.setPorcentajedescuento(fila.getString(8));
            ordenVentaDetalleSQLiteEntity.setMontodescuento(fila.getString(9));
            ordenVentaDetalleSQLiteEntity.setMontoimpuesto(fila.getString(10));
            ordenVentaDetalleSQLiteEntity.setMontototallinea(fila.getString(11));
            ordenVentaDetalleSQLiteEntity.setLineareferencia(fila.getString(12));
            ordenVentaDetalleSQLiteEntity.setImpuesto_id(fila.getString(13));
            ordenVentaDetalleSQLiteEntity.setProducto(fila.getString(14));
            ordenVentaDetalleSQLiteEntity.setAcctCode(fila.getString(15));
            ordenVentaDetalleSQLiteEntity.setAlmacen_id(fila.getString(16));
            ordenVentaDetalleSQLiteEntity.setPromocion_id(fila.getString(17));
            ordenVentaDetalleSQLiteEntity.setGal_unitario(fila.getString(18));
            ordenVentaDetalleSQLiteEntity.setGal_acumulado(fila.getString(19));
            ordenVentaDetalleSQLiteEntity.setU_SYP_FECAT07(fila.getString(20));
            ordenVentaDetalleSQLiteEntity.setMontosubtotalcondescuento(fila.getString(21));
            ordenVentaDetalleSQLiteEntity.setChk_descuentocontado(fila.getString(22));

            listaOrdenVentaDetalleSQLiteEntity.add(ordenVentaDetalleSQLiteEntity);
        }
        bd.close();
        return listaOrdenVentaDetalleSQLiteEntity;
    }

    public int LimpiarTablaOrdenVentaDetalle ()
    {
        abrir();
        bd.execSQL("delete from ordenventadetalle");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadOrdenVentaDetalle()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from ordenventadetalle",null);

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

    public ArrayList<OrdenVentaDetalleSQLiteEntity> ObtenerOrdenVentaDetalleporID (String ordenventa_id)
    {
        listaOrdenVentaDetalleSQLiteEntity = new ArrayList<OrdenVentaDetalleSQLiteEntity>();
        OrdenVentaDetalleSQLiteEntity ordenVentaDetalleSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from ordenventadetalle where ordenventa_id=?",new String[]{ordenventa_id});

        while (fila.moveToNext())
        {
            ordenVentaDetalleSQLiteEntity= new OrdenVentaDetalleSQLiteEntity();
            ordenVentaDetalleSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaDetalleSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaDetalleSQLiteEntity.setLineaordenventa_id(fila.getString(2));
            ordenVentaDetalleSQLiteEntity.setProducto_id(fila.getString(3));
            ordenVentaDetalleSQLiteEntity.setUmd(fila.getString(4));
            ordenVentaDetalleSQLiteEntity.setCantidad(fila.getString(5));
            ordenVentaDetalleSQLiteEntity.setPreciounitario(fila.getString(6));
            ordenVentaDetalleSQLiteEntity.setMontosubtotal(fila.getString(7));
            ordenVentaDetalleSQLiteEntity.setPorcentajedescuento(fila.getString(8));
            ordenVentaDetalleSQLiteEntity.setMontodescuento(fila.getString(9));
            ordenVentaDetalleSQLiteEntity.setMontoimpuesto(fila.getString(10));
            ordenVentaDetalleSQLiteEntity.setMontototallinea(fila.getString(11));
            ordenVentaDetalleSQLiteEntity.setLineareferencia(fila.getString(12));
            ordenVentaDetalleSQLiteEntity.setImpuesto_id(fila.getString(13));
            ordenVentaDetalleSQLiteEntity.setProducto(fila.getString(14));
            ordenVentaDetalleSQLiteEntity.setAcctCode(fila.getString(15));
            ordenVentaDetalleSQLiteEntity.setAlmacen_id(fila.getString(16));
            ordenVentaDetalleSQLiteEntity.setPromocion_id(fila.getString(17));
            ordenVentaDetalleSQLiteEntity.setGal_unitario(fila.getString(18));
            ordenVentaDetalleSQLiteEntity.setGal_acumulado(fila.getString(19));
            ordenVentaDetalleSQLiteEntity.setU_SYP_FECAT07(fila.getString(20));
            ordenVentaDetalleSQLiteEntity.setMontosubtotalcondescuento(fila.getString(21));
            ordenVentaDetalleSQLiteEntity.setChk_descuentocontado(fila.getString(22));

            listaOrdenVentaDetalleSQLiteEntity.add(ordenVentaDetalleSQLiteEntity);
        }
        bd.close();
        return listaOrdenVentaDetalleSQLiteEntity;
    }

    public boolean ObtenerCantidadOrdenVentaDetallePorOrdenVentaID(String ordenventa_id)
    {
        int resultado=0;
        boolean estado=false;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from ordenventadetalle where ordenventa_id='"+ordenventa_id+"'",null);

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

}

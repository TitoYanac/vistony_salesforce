package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetallePromocionSQLiteEntity;

import java.util.ArrayList;

public class OrdenVentaDetallePromocionSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<OrdenVentaDetallePromocionSQLiteEntity> listaOrdenVentaDetallePromocionSQLiteEntity;

    public OrdenVentaDetallePromocionSQLiteDao(Context context)
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


    public int InsertaOrdenVentaDetallePromocion (
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
    )
    {
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

        bd.insert("ordenventadetallepromocion",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<OrdenVentaDetallePromocionSQLiteEntity> ObtenerOrdenVentaDetallePromocionporID (String ordenventa_id)
    {
        listaOrdenVentaDetallePromocionSQLiteEntity = new ArrayList<>();
        OrdenVentaDetallePromocionSQLiteEntity ordenVentaDetallePromocionSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery("Select * from ordenventadetallepromocion where ordenventa_id='"+ordenventa_id+"'",null);

        while (fila.moveToNext())
        {
            ordenVentaDetallePromocionSQLiteEntity= new OrdenVentaDetallePromocionSQLiteEntity();
            ordenVentaDetallePromocionSQLiteEntity.setCompania_id(fila.getString(0));
            ordenVentaDetallePromocionSQLiteEntity.setOrdenventa_id(fila.getString(1));
            ordenVentaDetallePromocionSQLiteEntity.setLineaordenventa_id(fila.getString(2));
            ordenVentaDetallePromocionSQLiteEntity.setProducto_id(fila.getString(3));
            ordenVentaDetallePromocionSQLiteEntity.setUmd(fila.getString(4));
            ordenVentaDetallePromocionSQLiteEntity.setCantidad(fila.getString(5));
            ordenVentaDetallePromocionSQLiteEntity.setPreciounitario(fila.getString(6));
            ordenVentaDetallePromocionSQLiteEntity.setMontosubtotal(fila.getString(7));
            ordenVentaDetallePromocionSQLiteEntity.setPorcentajedescuento(fila.getString(8));
            ordenVentaDetallePromocionSQLiteEntity.setMontodescuento(fila.getString(9));
            ordenVentaDetallePromocionSQLiteEntity.setMontoimpuesto(fila.getString(10));
            ordenVentaDetallePromocionSQLiteEntity.setMontototallinea(fila.getString(11));
            ordenVentaDetallePromocionSQLiteEntity.setLineareferencia(fila.getString(12));
            ordenVentaDetallePromocionSQLiteEntity.setImpuesto_id(fila.getString(13));
            ordenVentaDetallePromocionSQLiteEntity.setProducto(fila.getString(14));
            ordenVentaDetallePromocionSQLiteEntity.setAcctCode(fila.getString(15));
            ordenVentaDetallePromocionSQLiteEntity.setAlmacen_id(fila.getString(16));
            ordenVentaDetallePromocionSQLiteEntity.setPromocion_id(fila.getString(17));
            ordenVentaDetallePromocionSQLiteEntity.setGal_unitario(fila.getString(18));
            ordenVentaDetallePromocionSQLiteEntity.setGal_acumulado(fila.getString(19));
            ordenVentaDetallePromocionSQLiteEntity.setU_SYP_FECAT07(fila.getString(20));
            ordenVentaDetallePromocionSQLiteEntity.setMontosubtotalcondescuento(fila.getString(21));
            ordenVentaDetallePromocionSQLiteEntity.setChk_descuentocontado(fila.getString(22));

            listaOrdenVentaDetallePromocionSQLiteEntity.add(ordenVentaDetallePromocionSQLiteEntity);
        }
        bd.close();
        return listaOrdenVentaDetallePromocionSQLiteEntity;
    }

}

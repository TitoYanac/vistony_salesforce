package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PromocionDetalleSQLiteDao {
    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleSQLiteEntity;
    DecimalFormat format = new DecimalFormat("#0.00");

    public PromocionDetalleSQLiteDao(Context context)
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


    public int InsertaPromocionDetalle (
            String compania_id,
            String lista_promocion_id,
            String promocion_id,
            String promocion_detalle_id,
            String producto_id,
            String producto,
            String umd,
            String cantidad,
            String fuerzatrabajo_id,
            String usuario_id,
            String preciobase,
            String chkdescuento,
            String descuento
    )
    {
        //SQLiteController admin = new SQLiteController(get,"administracion",null,1);
        // SQLiteDatabase bd = admin.getWritableDatabase();
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("lista_promocion_id",lista_promocion_id);
        registro.put("promocion_id",promocion_id);
        registro.put("promocion_detalle_id",promocion_detalle_id);
        registro.put("producto_id",producto_id);
        registro.put("producto",producto);
        registro.put("umd",umd);
        registro.put("cantidad",cantidad);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("usuario_id",usuario_id);
        registro.put("preciobase",preciobase);
        registro.put("chkdescuento",chkdescuento);
        registro.put("descuento",descuento);
        bd.insert("promociondetalle",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public int LimpiarTablaPromocionDetalle ()
    {
        abrir();
        bd.execSQL("delete from promociondetalle "); //add espesificacion
        bd.close();
        return 1;
    }

    public ArrayList<PromocionDetalleSQLiteEntity> ObtenerPromocionDetalle (
            String compania_id,
            String lista_promocion_id,
            String promocion_id,
            String contado,
            Context context
    )
    {
        listaPromocionDetalleSQLiteEntity = new ArrayList<PromocionDetalleSQLiteEntity>();
        PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from promociondetalle  where compania_id= '"+compania_id+"' and lista_promocion_id= '"+lista_promocion_id+"' and promocion_id= '"+promocion_id+"'",null);

        while (fila.moveToNext()) {
            promocionDetalleSQLiteEntity = new PromocionDetalleSQLiteEntity();
            promocionDetalleSQLiteEntity.setCompania_id(fila.getString(0));
            promocionDetalleSQLiteEntity.setLista_promocion_id(fila.getString(1));
            promocionDetalleSQLiteEntity.setPromocion_id(fila.getString(2));
            promocionDetalleSQLiteEntity.setPromocion_detalle_id(fila.getString(3));
            promocionDetalleSQLiteEntity.setProducto_id(fila.getString(4));
            promocionDetalleSQLiteEntity.setProducto(fila.getString(5));
            promocionDetalleSQLiteEntity.setUmd(fila.getString(6));
            promocionDetalleSQLiteEntity.setCantidad(fila.getString(7));
            promocionDetalleSQLiteEntity.setFuerzatrabajo_id(fila.getString(8));
            promocionDetalleSQLiteEntity.setUsuario_id(fila.getString(9));

            ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(context);
            ArrayList<ListaProductoEntity> listaProductoEntities = new ArrayList<>();

            listaProductoEntities = listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporProducto(contado, promocionDetalleSQLiteEntity.getProducto_id());
            for (int i = 0; i < listaProductoEntities.size(); i++) {
                promocionDetalleSQLiteEntity.setPreciobase(String.valueOf(format.format(Float.parseFloat(listaProductoEntities.get(i).getPreciobase()))));
            }
            //promocionDetalleSQLiteEntity.setPreciobase(fila.getString(10));
            promocionDetalleSQLiteEntity.setChkdescuento(fila.getString(11));
            promocionDetalleSQLiteEntity.setDescuento(fila.getString(12));
            listaPromocionDetalleSQLiteEntity.add(promocionDetalleSQLiteEntity);
        }
        bd.close();
        return listaPromocionDetalleSQLiteEntity;
    }

    public ArrayList<ListaPromocionDetalleEntity> ObtenerListaPromocionDetalle (
            String compania_id,
            String lista_promocion_id,
            String promocion_id
    )
    {

        ArrayList<ListaPromocionDetalleEntity> listaPromocionDetalleEntity = new ArrayList<>();
        ListaPromocionDetalleEntity ObjPromocionDetalleEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from promociondetalle  where compania_id= '"+compania_id+"' and lista_promocion_id= '"+lista_promocion_id+"' and promocion_id= '"+promocion_id+"'",null);

        while (fila.moveToNext())
        {
            ObjPromocionDetalleEntity= new ListaPromocionDetalleEntity();
            ObjPromocionDetalleEntity.setId(fila.getString(3));
            ObjPromocionDetalleEntity.setProducto_id(fila.getString(4));
            ObjPromocionDetalleEntity.setProducto(fila.getString(5));
            ObjPromocionDetalleEntity.setUmd(fila.getString(6));
            ObjPromocionDetalleEntity.setCantidad(fila.getString(7));
            listaPromocionDetalleEntity.add(ObjPromocionDetalleEntity);
        }
        bd.close();
        return listaPromocionDetalleEntity;
    }

    public int ObtenerCantidadPromocionDetalle ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from promociondetalle",null);

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
}

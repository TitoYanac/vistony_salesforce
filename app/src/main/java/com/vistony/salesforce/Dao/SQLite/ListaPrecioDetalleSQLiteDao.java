package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;

import java.util.ArrayList;

public class ListaPrecioDetalleSQLiteDao {

    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<ListaProductoEntity> arraylistaProductoEntity;

    public ListaPrecioDetalleSQLiteDao(Context context)
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


    public int InsertaListaPrecioDetalle (
            String compania_id,
            String credito,
            String contado,
            String producto_id,
            String producto,
            String umd,
            String gal,
            String u_vis_cashdscnt,
            String tipo,
            String porcentaje_descuento,
            String stock_almacen,
            String stock_general
    ){
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("porcentaje_dsct",porcentaje_descuento);
        registro.put("credito",credito);
        registro.put("contado",contado);
        registro.put("producto_id",producto_id);
        registro.put("producto",producto);
        registro.put("umd",umd);
        registro.put("gal",gal);
        registro.put("U_VIS_CashDscnt",u_vis_cashdscnt);
        registro.put("Tipo",tipo);
        registro.put("stock_almacen",stock_almacen);
        registro.put("stock_general",stock_general);
        bd.insert("listapreciodetalle",null,registro);
        bd.close();
        return 1;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalle (String cardCode){

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        abrir();
        Cursor fila=null;

        try {
             fila = bd.rawQuery("SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen,IFNULL(stock_general,0) stock_general," +
                     "contado,contado,gal,porcentaje_dsct" +
                  " FROM listapreciodetalle  WHERE Tipo= (SELECT lista_precio FROM cliente WHERE cliente_id=? LIMIT 1);",new String [] {cardCode});

            while (fila.moveToNext()) {
                listaProductoEntity = new ListaProductoEntity();
                listaProductoEntity.setProducto_id(fila.getString(0));
                listaProductoEntity.setProducto(fila.getString(1));
                listaProductoEntity.setUmd(fila.getString(2));
                listaProductoEntity.setStock_almacen(fila.getString(3));
                listaProductoEntity.setStock_general(fila.getString(4));
                listaProductoEntity.setPreciobase(fila.getString(5));
                listaProductoEntity.setPrecioigv(fila.getString(6));
                listaProductoEntity.setGal(fila.getString(7));
                listaProductoEntity.setPorcentaje_descuento_max(fila.getString(8));

                arraylistaProductoEntity.add(listaProductoEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            bd.close();
        }

        return arraylistaProductoEntity;
    }

    public int LimpiarTablaListaPrecioDetalle ()
    {
        abrir();
        bd.execSQL("delete from listapreciodetalle");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadListaPrecioDetalle ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from listapreciodetalle",null);

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
/*
    public ArrayList<ListaProductoEntity> ObtenerProducto ()
    {
        ArrayList<ListaProductoEntity> arraylistaProducto = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity istaProductoEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select " +
                            "a.producto_id,a.producto,a.umd,a.preciobase,a.precioigv,IFNULL(b.stock,0) stock " +
                            " from listapreciodetalle a " +
                            "LEFT JOIN stock b ON" +
                            " a.compania_id=b.compania_id " +
                            "and a.producto_id=b.producto_id " +
                            "and a.umd=b.umd "  ,null);

            while (fila.moveToNext())
            {
                istaProductoEntity= new ListaProductoEntity();
                istaProductoEntity.setProducto_id(fila.getString(0));
                istaProductoEntity.setProducto(fila.getString(1));
                istaProductoEntity.setUmd(fila.getString(2));
                istaProductoEntity.setPreciobase(fila.getString(3));
                istaProductoEntity.setPrecioigv(fila.getString(4));
                istaProductoEntity.setStock(fila.getString(5));
                arraylistaProducto.add(istaProductoEntity);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaProducto;
    }
*/
    public ArrayList<ListaPrecioDetalleSQLiteEntity> ObtenerListaPrecioDetalleporID (String producto_id)
    {
        ArrayList<ListaPrecioDetalleSQLiteEntity> arraylistaPreciodetalle = new ArrayList<>();
        ListaPrecioDetalleSQLiteEntity listaPrecioDetalleEntity;
        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select  * from listapreciodetalle where producto_id='"+producto_id+"'"  ,null);

            while (fila.moveToNext())
            {
                listaPrecioDetalleEntity= new ListaPrecioDetalleSQLiteEntity();
                listaPrecioDetalleEntity.setContado(fila.getString(1));
                listaPrecioDetalleEntity.setCredito(fila.getString(2));
                listaPrecioDetalleEntity.setProducto_id(fila.getString(3));
                listaPrecioDetalleEntity.setProducto(fila.getString(4));
                listaPrecioDetalleEntity.setUmd(fila.getString(5));
                listaPrecioDetalleEntity.setGal(fila.getString(6));
                listaPrecioDetalleEntity.setU_vis_cashdscnt(fila.getString(7));
                arraylistaPreciodetalle.add(listaPrecioDetalleEntity);

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        bd.close();
        return arraylistaPreciodetalle;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalleporProducto (String contado,String producto_id)
    {
        //SQLiteController admin = new SQLiteController(getApplicationContext(),"administracion",null,1);
        //SQLiteDatabase bd = admin.getWritableDatabase();
        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        abrir();
        Cursor fila=null;
        try {
            if(contado.equals("1")) {
                fila = bd.rawQuery(
                        "Select IFNULL(a.contado,0) precio   from listapreciodetalle A" +
                                " left join stock B on " +
                                " A.compania_id=b.compania_id and" +
                                " A.producto_id=b.producto_id and" +
                                " A.umd=b.umd "+
                                " where A.producto_id= '"+producto_id+"' "
                        , null);

            }else
            {
                fila = bd.rawQuery(
                        "Select IFNULL(a.credito,0) precio from listapreciodetalle A" +
                                " left join stock B on " +
                                " A.compania_id=b.compania_id and" +
                                " A.producto_id=b.producto_id and" +
                                " A.umd=b.umd "+
                                " where A.producto_id= '"+producto_id+"' "
                        , null);

            }
            while (fila.moveToNext()) {
                listaProductoEntity = new ListaProductoEntity();
                  listaProductoEntity.setPreciobase(fila.getString(0));
                arraylistaProductoEntity.add(listaProductoEntity);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        bd.close();

        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return arraylistaProductoEntity;
    }
}

package com.vistony.salesforce.Dao.SQLIte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Entity.SQLite.StockSQLiteEntity;

import java.util.ArrayList;

public class StockSQLiteDao {

    SQLiteController sqLiteController;
    SQLiteDatabase bd;
    ArrayList<StockSQLiteEntity> listaStockSQLiteEntity;

    public StockSQLiteDao(Context context)
    {
        sqLiteController = new SQLiteController(context);
    }
    public void abrir(){
        Log.i("SQLite", "Se abre conexion desde " + this.getClass().getName() );
        bd = sqLiteController.getWritableDatabase();
    }

    /** Cierra conexion a la base de datos */
    public void cerrar()
    {
        Log.i("SQLite", "Se cierra conexion a la base de datos " + sqLiteController.getDatabaseName() );
        sqLiteController.close();
    }


    public int InsertaStock (
            String compania_id,
            String producto_id,
            String producto,
            String umd,
            String stock,
            String almacen_id,
            String comprometido,
            String enstock,
            String pedido
    )

    {
        abrir();
        ContentValues registro = new ContentValues();
        registro.put("compania_id",compania_id);
        registro.put("producto_id",producto_id);
        registro.put("producto",producto);
        registro.put("umd",umd);
        registro.put("stock",stock);
        registro.put("almacen_id",almacen_id);
        registro.put("comprometido",comprometido);
        registro.put("enstock",enstock);
        registro.put("pedido",pedido);
        bd.insert("stock",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }

    public ArrayList<StockSQLiteEntity> ObtenerStock ()
    {

        listaStockSQLiteEntity = new ArrayList<StockSQLiteEntity>();
        StockSQLiteEntity stockSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from stock",null);

        while (fila.moveToNext())
        {
            stockSQLiteEntity= new StockSQLiteEntity();
            stockSQLiteEntity.setCompania_id(fila.getString(0));
            stockSQLiteEntity.setProducto_id(fila.getString(1));
            stockSQLiteEntity.setProducto(fila.getString(2));
            stockSQLiteEntity.setUmd(fila.getString(3));
            stockSQLiteEntity.setStock(fila.getString(4));
            stockSQLiteEntity.setAlmacen_id(fila.getString(5));
            stockSQLiteEntity.setComprometido(fila.getString(6));
            stockSQLiteEntity.setEnstock(fila.getString(7));
            stockSQLiteEntity.setPedido(fila.getString(8));
            listaStockSQLiteEntity.add(stockSQLiteEntity);
        }
        bd.close();
        return listaStockSQLiteEntity;
    }

    public int LimpiarTablaStock ()
    {
        abrir();
        bd.execSQL("delete from stock");
        bd.close();
        return 1;
    }

    public int ObtenerCantidadStock ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from stock",null);
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

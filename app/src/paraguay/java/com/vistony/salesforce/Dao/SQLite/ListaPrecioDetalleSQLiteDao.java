package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.ListaConsultaStockEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Enum.TipoDeCompra;

import java.util.ArrayList;

public class ListaPrecioDetalleSQLiteDao {

    ArrayList<ListaProductoEntity> arraylistaProductoEntity;
    ArrayList<ListaConsultaStockEntity> listaConsultaStockEntity;
    public ListaPrecioDetalleSQLiteDao(Context context){
        DataBaseManager.initializeInstance(new SqliteController(context));
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
            String stock_general,
            String units
    ){
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

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
        registro.put("units",units);
        sqlite.insert("listapreciodetalle",null,registro);

        DataBaseManager.getInstance().closeDatabase();
        return 1;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalle (String cardCode, String terminoPago){

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor listaPre=sqlite.rawQuery("SELECT lista_precio,(SELECT contado FROM terminopago WHERE terminopago_id="+terminoPago+" LIMIT 1) AS isCash FROM cliente WHERE cliente_id=? LIMIT 1",new String[]{cardCode});
            String listaArtificio=null,isCash=null;

            while(listaPre.moveToNext()){
                listaArtificio=listaPre.getString(0);
                isCash=listaPre.getString(1);
            }

            if(listaArtificio!=null && isCash!=null){

                TipoDeCompra tipoDeCompra=TipoDeCompra.contado;
                if(isCash.equals("0")){
                    tipoDeCompra=TipoDeCompra.credito;
                }
                SesionEntity.TipoCompra=tipoDeCompra.toString();

                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.tipoDeCompra: "+tipoDeCompra);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.listaArtificio: "+listaArtificio);
                String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                        "IFNULL(stock_general,0) stock_general,"+tipoDeCompra+","+tipoDeCompra+",gal," +
                        "porcentaje_dsct FROM listapreciodetalle  WHERE Tipo IN("+listaArtificio+") AND Tipo like '%"+tipoDeCompra+"%'";

                fila = sqlite.rawQuery(query,null);

                while (fila.moveToNext()) {
                    listaProductoEntity = new ListaProductoEntity();
                    listaProductoEntity.setProducto_id(fila.getString(0));
                    listaProductoEntity.setProducto(fila.getString(1));
                    listaProductoEntity.setUmd(fila.getString(2));
                    listaProductoEntity.setStock(fila.getString(3));
                    listaProductoEntity.setStockgeneral(fila.getString(4));
                    listaProductoEntity.setPreciobase(fila.getString(5));
                    listaProductoEntity.setPrecioigv(fila.getString(6));
                    listaProductoEntity.setGal(fila.getString(7));
                    listaProductoEntity.setPorcentaje_descuento_max(fila.getString(8));
                    Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.getPorcentaje_descuento_max"+listaProductoEntity.getPorcentaje_descuento_max());
                    arraylistaProductoEntity.add(listaProductoEntity);
                }
            }

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaProductoEntity;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetallePriceList (String cardCode, String terminoPago,String pricelist){

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor listaPre=sqlite.rawQuery("SELECT lista_precio,(SELECT contado FROM terminopago WHERE terminopago_id="+terminoPago+" LIMIT 1) AS isCash FROM cliente WHERE cliente_id=? LIMIT 1",new String[]{cardCode});
            String listaArtificio=null,isCash=null;

            while(listaPre.moveToNext()){
                listaArtificio=listaPre.getString(0);
                isCash=listaPre.getString(1);
            }

            if(listaArtificio!=null && isCash!=null){

                TipoDeCompra tipoDeCompra=TipoDeCompra.contado;
                if(isCash.equals("0")){
                    tipoDeCompra=TipoDeCompra.credito;
                }
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.tipoDeCompra: "+tipoDeCompra);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.listaArtificio: "+listaArtificio);
                String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                        "IFNULL(stock_general,0) stock_general,"+tipoDeCompra+","+tipoDeCompra+",gal," +
                        "porcentaje_dsct FROM listapreciodetalle  WHERE Tipo IN("+listaArtificio+") AND Tipo = '"+pricelist+"'";

                fila = sqlite.rawQuery(query,null);

                while (fila.moveToNext()) {
                    listaProductoEntity = new ListaProductoEntity();
                    listaProductoEntity.setProducto_id(fila.getString(0));
                    listaProductoEntity.setProducto(fila.getString(1));
                    listaProductoEntity.setUmd(fila.getString(2));
                    listaProductoEntity.setStock(fila.getString(3));
                    listaProductoEntity.setStockgeneral(fila.getString(4));
                    listaProductoEntity.setPreciobase(fila.getString(5));
                    listaProductoEntity.setPrecioigv(fila.getString(6));
                    listaProductoEntity.setGal(fila.getString(7));
                    listaProductoEntity.setPorcentaje_descuento_max(fila.getString(8));
                    Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.getPorcentaje_descuento_max"+listaProductoEntity.getPorcentaje_descuento_max());
                    arraylistaProductoEntity.add(listaProductoEntity);
                }
            }

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaProductoEntity;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalleporProductoArtificio (String cardCode, String terminoPago, String producto_id){

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor listaPre=sqlite.rawQuery("SELECT lista_precio,(SELECT contado FROM terminopago WHERE terminopago_id="+terminoPago+" LIMIT 1) AS isCash FROM cliente WHERE cliente_id=? LIMIT 1",new String[]{cardCode});
            String listaArtificio=null,isCash=null;

            while(listaPre.moveToNext()){
                listaArtificio=listaPre.getString(0);
                isCash=listaPre.getString(1);
            }

            if(listaArtificio!=null && isCash!=null){

                TipoDeCompra tipoDeCompra=TipoDeCompra.contado;
                if(isCash.equals("0")){
                    tipoDeCompra=TipoDeCompra.credito;
                }
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.tipoDeCompra: "+tipoDeCompra);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.listaArtificio: "+listaArtificio);
                String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                        "IFNULL(stock_general,0) stock_general,"+tipoDeCompra+","+tipoDeCompra+",gal," +
                        "porcentaje_dsct FROM listapreciodetalle  WHERE Tipo IN("+listaArtificio+") and producto_id='"+producto_id+"' AND Tipo like '%"+tipoDeCompra+"%' ";

                fila = sqlite.rawQuery(query,null);

                while (fila.moveToNext()) {
                    listaProductoEntity = new ListaProductoEntity();
                    listaProductoEntity.setProducto_id(fila.getString(0));
                    listaProductoEntity.setProducto(fila.getString(1));
                    listaProductoEntity.setUmd(fila.getString(2));
                    listaProductoEntity.setStock(fila.getString(3));
                    listaProductoEntity.setPreciobase(fila.getString(5));
                    listaProductoEntity.setPrecioigv(fila.getString(6));
                    listaProductoEntity.setGal(fila.getString(7));

                    arraylistaProductoEntity.add(listaProductoEntity);
                }
            }

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaProductoEntity;
    }

    public int LimpiarTablaListaPrecioDetalle ()
    {
        SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
        sqlite.execSQL("delete from listapreciodetalle");
        DataBaseManager.getInstance().closeDatabase();
        return 1;
    }

    public int ObtenerCantidadListaPrecioDetalle ()
    {
        int resultado=0;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor fila = sqlite.rawQuery(
                    "Select count(compania_id) from listapreciodetalle",null);

            while (fila.moveToNext())
            {
                resultado= Integer.parseInt(fila.getString(0));

            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }


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

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

            Cursor fila = sqlite.rawQuery(
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
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaPreciodetalle;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalleporProducto (String contado,String producto_id)
    {

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;

        Cursor fila=null;
        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

            if(contado.equals("1")) {
                fila = sqlite.rawQuery(
                        "Select IFNULL(a.contado,0) precio   from listapreciodetalle A" +
                                " left join stock B on " +
                                " A.compania_id=b.compania_id and" +
                                " A.producto_id=b.producto_id and" +
                                " A.umd=b.umd "+
                                " where A.producto_id= '"+producto_id+"' "
                        , null);

            }else
            {
                fila = sqlite.rawQuery(
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
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return arraylistaProductoEntity;
    }

    public ArrayList<ListaConsultaStockEntity> ObtenerConsultaStock (Context context){

        listaConsultaStockEntity = new ArrayList<ListaConsultaStockEntity>();
        ListaConsultaStockEntity consultaStockEntity;
        PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao=new PromocionCabeceraSQLiteDao(context);
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                    "IFNULL(stock_general,0) stock_general,contado,credito,gal " +
                    " FROM listapreciodetalle GROUP BY producto_id,producto,umd,stock_almacen,stock_general,contado,credito,gal";

            fila = sqlite.rawQuery(query,null);

            while (fila.moveToNext()) {
                consultaStockEntity = new ListaConsultaStockEntity();
                consultaStockEntity.setProducto_id (fila.getString(0));
                consultaStockEntity.setProducto(fila.getString(1));
                consultaStockEntity.setUmd(fila.getString(2));
                consultaStockEntity.setStock(fila.getString(3));
                consultaStockEntity.setPreciocontadoigv(fila.getString(5));
                consultaStockEntity.setPreciocreditoigv(fila.getString(6));
                consultaStockEntity.setGal(fila.getString(7));
                consultaStockEntity.setPromotionenable(promocionCabeceraSQLiteDao.ObtenerEstadoPromocionConsultaStock(
                        SesionEntity.compania_id,
                        SesionEntity.fuerzatrabajo_id,
                        SesionEntity.usuario_id,
                        consultaStockEntity.getProducto_id(),
                        consultaStockEntity.getUmd()
                ));
                listaConsultaStockEntity.add(consultaStockEntity);
            }
            fila.close();

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        //Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.arraylistaProductoEntity: "+arraylistaProductoEntity.size());
        return listaConsultaStockEntity;
    }

    public ArrayList<ListaPrecioDetalleSQLiteEntity> ObtenerConsultaPriceListInduvis (String Product_id,Context context ){

        ArrayList<ListaPrecioDetalleSQLiteEntity>  listaPrecioDetalleSQLiteEntity = new ArrayList<>();
        ListaPrecioDetalleSQLiteEntity objPrecioDetalleSQLiteEntity;
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            String query="SELECT contado,credito,Tipo " +
                    " FROM listapreciodetalle WHERE producto_id = '"+Product_id+"' GROUP BY contado,credito,Tipo";

            fila = sqlite.rawQuery(query,null);

            while (fila.moveToNext()) {
                objPrecioDetalleSQLiteEntity = new ListaPrecioDetalleSQLiteEntity();
                objPrecioDetalleSQLiteEntity.setContado(fila.getString(0));
                objPrecioDetalleSQLiteEntity.setCredito(fila.getString(1));
                objPrecioDetalleSQLiteEntity.setTypo(fila.getString(2));
                listaPrecioDetalleSQLiteEntity.add(objPrecioDetalleSQLiteEntity);
            }
            fila.close();

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        //Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.arraylistaProductoEntity: "+arraylistaProductoEntity.size());
        return listaPrecioDetalleSQLiteEntity;
    }

}

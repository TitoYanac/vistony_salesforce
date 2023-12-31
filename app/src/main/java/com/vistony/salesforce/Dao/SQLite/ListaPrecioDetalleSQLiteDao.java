package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.DataBaseManager;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.ListaConsultaStockEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ListaPrecioEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UbigeoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Enum.TipoDeCompra;

import java.util.ArrayList;
import java.util.List;

public class ListaPrecioDetalleSQLiteDao {

    ArrayList<ListaProductoEntity> arraylistaProductoEntity;
    ArrayList<ListaConsultaStockEntity> listaConsultaStockEntity;
    ArrayList<ListaPrecioDetalleSQLiteEntity> ListaPrecioDetalleSQLiteEntity;
    SqliteController sqliteController;
    SQLiteDatabase bd;

    /*public ListaPrecioDetalleSQLiteDao(Context context){
        DataBaseManager.initializeInstance(new SqliteController(context));
    }*/
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
    /*public int InsertaListaPrecioDetalle (

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
            String units,
            String oiltax,
            String liter,
            String SIGAUS


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
    }*/

    public int AddListPriceList (List<ListaPrecioEntity> listPrice)
    {
        abrir();

        for (int i = 0; i < listPrice.size(); i++) {
            ContentValues registro = new ContentValues();
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("porcentaje_dsct",listPrice.get(i).getPorcentaje_descuento());
            registro.put("credito",listPrice.get(i).getCredito());
            registro.put("contado",listPrice.get(i).getContado());
            registro.put("producto_id",listPrice.get(i).getProducto_id());
            registro.put("producto",listPrice.get(i).getProducto());
            registro.put("umd",listPrice.get(i).getUmd());
            registro.put("gal",listPrice.get(i).getGal());
            registro.put("U_VIS_CashDscnt",listPrice.get(i).getCashdscnt());
            registro.put("Tipo",listPrice.get(i).getTipo());
            registro.put("stock_almacen",listPrice.get(i).getStock_almacen());
            registro.put("stock_general",listPrice.get(i).getStock_general());
            registro.put("units",listPrice.get(i).getUnit());
            registro.put("oiltax",listPrice.get(i).getOiltax());
            registro.put("liter",listPrice.get(i).getLiter());
            registro.put("SIGAUS",listPrice.get(i).getSIGAUS());
            registro.put("MonedaAdicional",listPrice.get(i).getMonedaAdicional());
            registro.put("MonedaAdicionalContado",listPrice.get(i).getMonedaAdicionalContado());
            registro.put("MonedaAdicionalCredito",listPrice.get(i).getMonedaAdicionalCredito());
            registro.put("CodePriceListCash",listPrice.get(i).getCodePriceListCash());
            registro.put("CodePriceListCredit",listPrice.get(i).getCodePriceListCredit());
            registro.put("CodAlmacen",SesionEntity.almacen_id);
            bd.insert("listapreciodetalle",null,registro);
        }

        bd.close();
        return 1;
    }

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalle (
            String cardCode,
            String terminoPago,
            String ubigeo_id,
            Context context,
            String currency_id

    ){

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        Cursor fila=null;
        FormulasController formulasController=new FormulasController(context);

        try {
            String flete="";
            UbigeoSQLiteDao ubigeoSQLiteDao=new UbigeoSQLiteDao(context);
            ArrayList<UbigeoSQLiteEntity> listUbigeoSQLiteEntity=new ArrayList<>();
            listUbigeoSQLiteEntity=ubigeoSQLiteDao.ObtenerUbigeoporID(ubigeo_id);
            for(int i=0;i<listUbigeoSQLiteEntity.size();i++)
            {
                flete=listUbigeoSQLiteEntity.get(i).getU_VIS_Flete();
            }
            //Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ubigeo_id:"+ubigeo_id);
            //Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.flete:"+flete);
            //Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.currency_id:"+currency_id);

            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor listaPre=sqlite.rawQuery("SELECT lista_precio,(SELECT contado FROM terminopago WHERE terminopago_id="+terminoPago+" LIMIT 1) AS isCash FROM cliente WHERE cliente_id=? LIMIT 1",new String[]{cardCode});
            String listaArtificio=null,isCash=null;

            while(listaPre.moveToNext()){
                listaArtificio=listaPre.getString(0);
                isCash=listaPre.getString(1);
            }

            if(listaArtificio!=null && isCash!=null){

                TipoDeCompra tipoDeCompra=TipoDeCompra.contado;
                TipoDeCompra listnum=TipoDeCompra.CodePriceListCash;
                if(isCash.equals("0")){
                    tipoDeCompra=TipoDeCompra.credito;
                    listnum=TipoDeCompra.CodePriceListCredit;
                }
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.tipoDeCompra: "+tipoDeCompra);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.listaArtificio: "+listaArtificio);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.currency_id: "+currency_id);
                String query="";
                switch (BuildConfig.FLAVOR) {
                    case "peru":
                        if (currency_id.equals("US$")) {
                            //Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$ ");
                            String addCurrency;
                            if (isCash.equals("0")) {
                                addCurrency = "MonedaAdicionalCredito";
                            } else {
                                addCurrency = "MonedaAdicionalContado";
                            }

                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$-addCurrency " + addCurrency);

                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + addCurrency + "," + addCurrency + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units  FROM listapreciodetalle  " +
                                    " WHERE Tipo like '%"+tipoDeCompra+"%'"+
                                    " AND " + addCurrency + " NOT IN ('0.00') ";
                        } else {
                            //Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.NO-ENTROIF-US$ ");
                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + tipoDeCompra + "," + tipoDeCompra + ",gal," +
                                    //"porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle  WHERE Tipo IN(" + listaArtificio + ")";
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle   WHERE Tipo like '%"+tipoDeCompra+"%'"+
                                    "";
                        }

                        break;
                    case "bolivia":
                        if (currency_id.equals("US$")) {
                            //Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$ ");
                            String addCurrency;
                            if (isCash.equals("0")) {
                                addCurrency = "MonedaAdicionalCredito";
                            } else {
                                addCurrency = "MonedaAdicionalContado";
                            }

                            //Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$-addCurrency " + addCurrency);

                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + addCurrency + "," + addCurrency + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units  FROM listapreciodetalle  WHERE Tipo IN(" + listaArtificio + ") AND " + addCurrency + " NOT IN ('0.00') ";
                        } else {
                            //Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.NO-ENTROIF-US$ ");
                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + tipoDeCompra + "," + tipoDeCompra + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle " +
                                    " WHERE Tipo like '%"+tipoDeCompra+"%'"+
                                    "";
                        }

                        break;
                    case "ecuador":
                        query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                "IFNULL(stock_general,0) stock_general," + tipoDeCompra + "," + tipoDeCompra + ",gal," +
                                "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle " +
                                " WHERE Tipo like '%"+tipoDeCompra+"%'"+
                                "";
                        break;
                }



                fila = sqlite.rawQuery(query,null);

                while (fila.moveToNext()) {
                    listaProductoEntity = new ListaProductoEntity();
                    listaProductoEntity.setProducto_id(fila.getString(0));
                    listaProductoEntity.setProducto(fila.getString(1));
                    listaProductoEntity.setUmd(fila.getString(2));
                    listaProductoEntity.setStock_almacen(fila.getString(3));
                    //listaProductoEntity.setPreciobase(fila.getString(5));
                    listaProductoEntity.setPreciobase(formulasController.getPriceIncrement(fila.getString(5),flete) );
                    //Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$-listaProductoEntity.getPreciobase "+listaProductoEntity.getPreciobase());
                    //listaProductoEntity.setPrecioigv(fila.getString(6));
                    listaProductoEntity.setPrecioigv(formulasController.getPriceIncrement(fila.getString(6),flete) );
                    listaProductoEntity.setGal(fila.getString(7));
                    listaProductoEntity.setPorcentaje_dsct(fila.getString(8));
                    listaProductoEntity.setPorcentaje_descuento_max(fila.getString(8));
                    listaProductoEntity.setListnum(fila.getString(9));
                    listaProductoEntity.setOiltax(fila.getString(10));
                    listaProductoEntity.setLiter(fila.getString(11));
                    listaProductoEntity.setSIGAUS(fila.getString(12));
                    listaProductoEntity.setUnits(fila.getString(13));
                    arraylistaProductoEntity.add(listaProductoEntity);
                }
            }

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.arraylistaProductoEntity: "+arraylistaProductoEntity.size());
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
                SesionEntity.TipoListaPrecio=tipoDeCompra.toString();
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporProductoArtificio.cardCode: "+cardCode);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporProductoArtificio.tipoDeCompra: "+tipoDeCompra);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporProductoArtificio.listaArtificio: "+listaArtificio);
                String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                        "IFNULL(stock_general,0) stock_general,"+tipoDeCompra+","+tipoDeCompra+",gal," +
                        //"porcentaje_dsct,oiltax,liter,SIGAUS FROM listapreciodetalle  WHERE Tipo IN("+listaArtificio+") and producto_id='"+producto_id+"'";
                        "porcentaje_dsct,oiltax,liter,SIGAUS FROM listapreciodetalle " +
                        " WHERE Tipo like '%"+tipoDeCompra+"%'"+
                        "and producto_id='"+producto_id+"'";

                fila = sqlite.rawQuery(query,null);

                while (fila.moveToNext()) {
                    listaProductoEntity = new ListaProductoEntity();
                    listaProductoEntity.setProducto_id(fila.getString(0));
                    listaProductoEntity.setProducto(fila.getString(1));
                    listaProductoEntity.setUmd(fila.getString(2));
                    listaProductoEntity.setStock_almacen(fila.getString(3));
                    listaProductoEntity.setPreciobase(fila.getString(5));
                    listaProductoEntity.setPrecioigv(fila.getString(6));
                    listaProductoEntity.setGal(fila.getString(7));
                    listaProductoEntity.setPorcentaje_dsct(fila.getString(8));
                    listaProductoEntity.setOiltax(fila.getString(9));
                    listaProductoEntity.setLiter(fila.getString(10));
                    listaProductoEntity.setSIGAUS(fila.getString(11));
                    arraylistaProductoEntity.add(listaProductoEntity);
                }
            }

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.arraylistaProductoEntity: "+arraylistaProductoEntity.size());
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
    public ArrayList<ListaPrecioDetalleSQLiteEntity> ObtenerListaPrecioDetalleporID (String producto_id,String Tipo)
    {
        ArrayList<ListaPrecioDetalleSQLiteEntity> arraylistaPreciodetalle = new ArrayList<>();
        ListaPrecioDetalleSQLiteEntity listaPrecioDetalleEntity;
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID.Tipo: "+Tipo);
        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();

            Cursor fila = sqlite.rawQuery(
                    "Select  * from listapreciodetalle where producto_id='"+producto_id+"' and Tipo like '%"+Tipo+"%' "  ,null);

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
                listaPrecioDetalleEntity.setPorcentaje_descuento(fila.getString(9));
                listaPrecioDetalleEntity.setOiltax(fila.getString(13));
                listaPrecioDetalleEntity.setLiter(fila.getString(14));
                listaPrecioDetalleEntity.setSIGAUS(fila.getString(15));
                arraylistaPreciodetalle.add(listaPrecioDetalleEntity);

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID.arraylistaPreciodetalle: "+arraylistaPreciodetalle.size());
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

    public ArrayList<ListaPrecioDetalleSQLiteEntity> ObtenerListaPrecioPorProducto (Context context, String producto_id){

        ListaPrecioDetalleSQLiteEntity = new ArrayList<ListaPrecioDetalleSQLiteEntity>();
        ListaPrecioDetalleSQLiteEntity listaPrecioDetalleSQLiteEntity=new ListaPrecioDetalleSQLiteEntity();
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                    "IFNULL(stock_general,0) stock_general,contado,credito,gal,units " +
                    " FROM listapreciodetalle " +
                    "where producto_id='"+producto_id+"' "+
                    "GROUP BY producto_id,producto,umd,stock_almacen,stock_general,contado,credito,gal,units";

            fila = sqlite.rawQuery(query,null);

            while (fila.moveToNext()) {
                listaPrecioDetalleSQLiteEntity = new ListaPrecioDetalleSQLiteEntity();
                listaPrecioDetalleSQLiteEntity.setProducto_id (fila.getString(0));
                listaPrecioDetalleSQLiteEntity.setProducto(fila.getString(1));
                listaPrecioDetalleSQLiteEntity.setUmd(fila.getString(2));
                listaPrecioDetalleSQLiteEntity.setStock_almacen(fila.getString(3));
                listaPrecioDetalleSQLiteEntity.setStock_general(fila.getString(4));
                listaPrecioDetalleSQLiteEntity.setContado(fila.getString(5));
                listaPrecioDetalleSQLiteEntity.setCredito(fila.getString(6));
                listaPrecioDetalleSQLiteEntity.setGal(fila.getString(7));
                listaPrecioDetalleSQLiteEntity.setUnit(fila.getString(8));
                ListaPrecioDetalleSQLiteEntity.add(listaPrecioDetalleSQLiteEntity);
            }


        }catch (Exception e){
            Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.e"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ListaPrecioDetalleSQLiteEntity.SIZE(): "+ListaPrecioDetalleSQLiteEntity.size());
        return ListaPrecioDetalleSQLiteEntity;
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

    public ArrayList<ListaPrecioDetalleSQLiteEntity> getProductSIGAUS (){

        ListaPrecioDetalleSQLiteEntity = new ArrayList<ListaPrecioDetalleSQLiteEntity>();
        ListaPrecioDetalleSQLiteEntity listaPrecioDetalleSQLiteEntity=new ListaPrecioDetalleSQLiteEntity();
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            String query="SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                    "IFNULL(stock_general,0) stock_general,contado,credito,gal,units,oiltax,liter,SIGAUS " +
                    " FROM listapreciodetalle " +
                    "where SIGAUS='Y' "+
                    "GROUP BY producto_id,producto,umd,stock_almacen,stock_general,contado,credito,gal,units,oiltax,liter,SIGAUS";

            fila = sqlite.rawQuery(query,null);

            while (fila.moveToNext()) {
                listaPrecioDetalleSQLiteEntity = new ListaPrecioDetalleSQLiteEntity();
                listaPrecioDetalleSQLiteEntity.setProducto_id (fila.getString(0));
                listaPrecioDetalleSQLiteEntity.setProducto(fila.getString(1));
                listaPrecioDetalleSQLiteEntity.setUmd(fila.getString(2));
                listaPrecioDetalleSQLiteEntity.setStock_almacen(fila.getString(3));
                listaPrecioDetalleSQLiteEntity.setStock_general(fila.getString(4));
                listaPrecioDetalleSQLiteEntity.setContado(fila.getString(5));
                listaPrecioDetalleSQLiteEntity.setCredito(fila.getString(6));
                listaPrecioDetalleSQLiteEntity.setGal(fila.getString(7));
                listaPrecioDetalleSQLiteEntity.setUnit(fila.getString(8));
                listaPrecioDetalleSQLiteEntity.setOiltax(fila.getString(9));
                listaPrecioDetalleSQLiteEntity.setLiter(fila.getString(10));
                listaPrecioDetalleSQLiteEntity.setSIGAUS(fila.getString(11));
                ListaPrecioDetalleSQLiteEntity.add(listaPrecioDetalleSQLiteEntity);
            }


        }catch (Exception e){
            Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.e"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ListaPrecioDetalleSQLiteEntity.SIZE(): "+ListaPrecioDetalleSQLiteEntity.size());
        return ListaPrecioDetalleSQLiteEntity;
    }

    public ArrayList<ListaConsultaStockEntity> getQueryStockPromotion (Context context){

        listaConsultaStockEntity = new ArrayList<ListaConsultaStockEntity>();
        ListaConsultaStockEntity consultaStockEntity;
        PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao=new PromocionCabeceraSQLiteDao(context);
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            String query="SELECT A.producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                    "IFNULL(stock_general,0) stock_general,contado,credito,gal,B.producto_id " +
                    " FROM listapreciodetalle A " +
                    " LEFT JOIN (SELECT producto_id FROM promocioncabecera GROUP BY producto_id ) B ON " +
                    " A.producto_id=B.producto_id " +
                    " WHERE B.producto_id is not NULL " +
                    "GROUP BY A.producto_id,producto,umd,stock_almacen,stock_general,contado,credito,gal,B.producto_id";

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

    public ArrayList<ListaProductoEntity> ObtenerListaPrecioDetalleQuotation (
            String cardCode,
            String terminoPago,
            String ubigeo_id,
            Context context,
            String currency_id

    ){

        arraylistaProductoEntity = new ArrayList<ListaProductoEntity>();
        ListaProductoEntity listaProductoEntity;
        Cursor fila=null;
        FormulasController formulasController=new FormulasController(context);

        try {
            String flete="";
            UbigeoSQLiteDao ubigeoSQLiteDao=new UbigeoSQLiteDao(context);
            ArrayList<UbigeoSQLiteEntity> listUbigeoSQLiteEntity=new ArrayList<>();
            listUbigeoSQLiteEntity=ubigeoSQLiteDao.ObtenerUbigeoporID(ubigeo_id);
            for(int i=0;i<listUbigeoSQLiteEntity.size();i++)
            {
                flete=listUbigeoSQLiteEntity.get(i).getU_VIS_Flete();
            }
            Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ubigeo_id:"+ubigeo_id);
            Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.flete:"+flete);
            Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.currency_id:"+currency_id);

            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            Cursor listaPre=sqlite.rawQuery("SELECT lista_precio,(SELECT contado FROM terminopago WHERE terminopago_id="+terminoPago+" LIMIT 1) AS isCash FROM cliente WHERE cliente_id=? LIMIT 1",new String[]{cardCode});
            String listaArtificio=null,isCash=null;

            while(listaPre.moveToNext()){
                listaArtificio=listaPre.getString(0);
                isCash=listaPre.getString(1);
            }

            if(listaArtificio!=null && isCash!=null){

                TipoDeCompra tipoDeCompra=TipoDeCompra.contado;
                TipoDeCompra listnum=TipoDeCompra.CodePriceListCash;
                if(isCash.equals("0")){
                    tipoDeCompra=TipoDeCompra.credito;
                    listnum=TipoDeCompra.CodePriceListCredit;
                }
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.tipoDeCompra: "+tipoDeCompra);
                Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.listaArtificio: "+listaArtificio);

                String query="";
                switch (BuildConfig.FLAVOR)
                {
                    case "peru":
                        if (currency_id.equals("US$")) {
                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$ ");
                            String addCurrency;
                            if (isCash.equals("0")) {
                                addCurrency = "MonedaAdicionalCredito";
                            } else {
                                addCurrency = "MonedaAdicionalContado";
                            }

                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$-addCurrency " + addCurrency);

                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + addCurrency + "," + addCurrency + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units  FROM listapreciodetalle  WHERE " +
                                    //"Tipo IN("+listaArtificio+") AND " +
                                    "" + addCurrency + " NOT IN ('0.00') ";
                        } else {
                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.NO-ENTROIF-US$ ");
                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + tipoDeCompra + "," + tipoDeCompra + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle  " +
                                    //"WHERE Tipo IN("+listaArtificio+")" +
                                    "";
                        }
                        break;
                    case "bolivia":
                        if (currency_id.equals("US$")) {
                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$ ");
                            String addCurrency;
                            if (isCash.equals("0")) {
                                addCurrency = "MonedaAdicionalCredito";
                            } else {
                                addCurrency = "MonedaAdicionalContado";
                            }

                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$-addCurrency " + addCurrency);

                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + addCurrency + "," + addCurrency + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units  FROM listapreciodetalle  WHERE " +
                                    "Tipo IN("+listaArtificio+") AND " +
                                    "" + addCurrency + " NOT IN ('0.00') ";
                        } else {
                            Log.e("REOS", "ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.NO-ENTROIF-US$ ");
                            query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                    "IFNULL(stock_general,0) stock_general," + tipoDeCompra + "," + tipoDeCompra + ",gal," +
                                    "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle  " +
                                    "WHERE Tipo like '%"+tipoDeCompra+"%'"+
                                    "";
                        }
                        break;
                    case "india":
                    case "chile":
                    case "ecuador":
                    case "paraguay":
                    case "perurofalab":
                    case "espania":
                    case "marruecos":
                        query = "SELECT producto_id,producto,umd,IFNULL(stock_almacen,0) stock_almacen," +
                                "IFNULL(stock_general,0) stock_general," + tipoDeCompra + "," + tipoDeCompra + ",gal," +
                                "porcentaje_dsct," + listnum + ",oiltax,liter,SIGAUS,units FROM listapreciodetalle " +
                                " WHERE Tipo like '%"+tipoDeCompra+"%'"+
                                "";
                        break;
                }


                fila = sqlite.rawQuery(query,null);

                while (fila.moveToNext()) {
                    listaProductoEntity = new ListaProductoEntity();
                    listaProductoEntity.setProducto_id(fila.getString(0));
                    listaProductoEntity.setProducto(fila.getString(1));
                    listaProductoEntity.setUmd(fila.getString(2));
                    listaProductoEntity.setStock_almacen(fila.getString(3));
                    //listaProductoEntity.setPreciobase(fila.getString(5));
                    listaProductoEntity.setPreciobase(formulasController.getPriceIncrement(fila.getString(5),flete) );
                    Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.ENTROIF-US$-listaProductoEntity.getPreciobase "+listaProductoEntity.getPreciobase());
                    //listaProductoEntity.setPrecioigv(fila.getString(6));
                    listaProductoEntity.setPrecioigv(formulasController.getPriceIncrement(fila.getString(6),flete) );
                    listaProductoEntity.setGal(fila.getString(7));
                    listaProductoEntity.setPorcentaje_dsct(fila.getString(8));
                    listaProductoEntity.setPorcentaje_descuento_max(fila.getString(8));
                    listaProductoEntity.setListnum(fila.getString(9));
                    listaProductoEntity.setOiltax(fila.getString(10));
                    listaProductoEntity.setLiter(fila.getString(11));
                    listaProductoEntity.setSIGAUS(fila.getString(12));
                    listaProductoEntity.setUnits(fila.getString(13));
                    arraylistaProductoEntity.add(listaProductoEntity);
                }
            }

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }
        Log.e("REOS","ListaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle.arraylistaProductoEntity: "+arraylistaProductoEntity.size());
        return arraylistaProductoEntity;
    }

    public String getWarehousePricelist(){

        String warehouse="";
        Cursor fila=null;

        try {
            SQLiteDatabase sqlite = DataBaseManager.getInstance().openDatabase();
            String query="SELECT IFNULL(CodAlmacen,'') AS CodAlmacen FROM listapreciodetalle GROUP BY CodAlmacen";

            fila = sqlite.rawQuery(query,null);

            while (fila.moveToNext()) {
                warehouse= (fila.getString(0));
            }
            fila.close();

        }catch (Exception e){
            Log.e("ErrorSqlite","=>"+e.getMessage());
            e.printStackTrace();
        }finally {
            DataBaseManager.getInstance().closeDatabase();
        }

        return warehouse;
    }
}


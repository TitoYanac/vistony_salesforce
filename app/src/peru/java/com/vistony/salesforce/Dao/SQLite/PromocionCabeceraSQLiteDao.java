package com.vistony.salesforce.Dao.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BancoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;

import java.util.ArrayList;
import java.util.List;

public class PromocionCabeceraSQLiteDao {
    SqliteController sqliteController;
    SQLiteDatabase bd;
    ArrayList<PromocionCabeceraSQLiteEntity> listaPromocionCabeceraSQLiteEntity;
    Context context;

    public PromocionCabeceraSQLiteDao(Context context)
    {
        sqliteController = new SqliteController(context);
        this.context=context;
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


    /*
    public int InsertaPromocionCabecera (
            String compania_id,
            String lista_promocion_id,
            String promocion_id,
            String producto_id,
            String producto,
            String umd,
            String cantidad,
            String fuerzatrabajo_id,
            String usuario_id,
            String total_preciobase,
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
        registro.put("producto_id",producto_id);
        registro.put("producto",producto);
        registro.put("umd",umd);
        registro.put("cantidad",cantidad);
        registro.put("fuerzatrabajo_id",fuerzatrabajo_id);
        registro.put("usuario_id",usuario_id);
        registro.put("total_preciobase",total_preciobase);
        registro.put("descuento",descuento);
        bd.insert("promocioncabecera",null,registro);
        bd.close();
        //Toast.makeText(this,"Ss cargaron los datos del articulo", Toast.LENGTH_SHORT).show();
        return 1;
    }*/

    public int AddListPromotionHead (List<PromocionCabeceraEntity> promocionCabeceraEntity)
    {
        abrir();

        for (int i = 0; i < promocionCabeceraEntity.size(); i++) {
            ContentValues registro = new ContentValues();
            /*registro.put("banco_id",bancos.get(i).getBanco_ID());
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("nombrebanco",bancos.get(i).getNombre_Banco());
            registro.put("singledeposit",bancos.get(i).getOperacionUnica());
            registro.put("pagopos",bancos.get(i).getPagoPOS());*/
            ///////////////////////////////////////////
            registro.put("compania_id",SesionEntity.compania_id);
            registro.put("lista_promocion_id",promocionCabeceraEntity.get(i).getLista_promocion_id());
            registro.put("promocion_id",promocionCabeceraEntity.get(i).getPromocion_id());
            registro.put("producto_id",promocionCabeceraEntity.get(i).getProducto_id());
            registro.put("producto",promocionCabeceraEntity.get(i).getProducto());
            registro.put("umd",promocionCabeceraEntity.get(i).getUmd());
            registro.put("cantidad",promocionCabeceraEntity.get(i).getCantidad());
            registro.put("fuerzatrabajo_id",SesionEntity.fuerzatrabajo_id);
            registro.put("usuario_id",SesionEntity.usuario_id);
            registro.put("total_preciobase","0");
            registro.put("descuento",promocionCabeceraEntity.get(i).getDescuento());

            bd.insert("promocioncabecera",null,registro);
        }

        bd.close();
        return 1;
    }

    public int LimpiarTablaPromocionCabecera ()
    {
        abrir();
        bd.execSQL("delete from promocioncabecera "); //add espesificacion
        bd.close();
        return 1;
    }

    public ArrayList<ListaPromocionCabeceraEntity> ObtenerPromocionCabecera (
            String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String producto_id,
            String umd,
            String cantidad,
            String contado,
            String terminopago_id,
            String cardcode,
            String currency

    )
    {
        ArrayList<ListaPromocionCabeceraEntity>  listaPromocionCabeceraSQLiteEntity = new ArrayList<>();
        ListaPromocionCabeceraEntity listaPromocionCabeceraEntity;
        ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities=new ArrayList<>();
        PromocionDetalleSQLiteDao promocionDetalleSQLiteDao=new PromocionDetalleSQLiteDao(context);
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from promocioncabecera where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and usuario_id='" + usuario_id + "' " +
                        "and producto_id='" + producto_id + "' and umd='" + umd + "' and CAST(cantidad as INTEGER)<='" + cantidad + "' order by cantidad desc ",null);

        while (fila.moveToNext())
        {
            listaPromocionCabeceraEntity= new ListaPromocionCabeceraEntity();
            listaPromocionCabeceraEntity.setLista_promocion_id(fila.getString(1));
            listaPromocionCabeceraEntity.setPromocion_id(fila.getString(2));
            listaPromocionCabeceraEntity.setProducto_id(fila.getString(3));
            listaPromocionCabeceraEntity.setProducto(fila.getString(4));
            listaPromocionCabeceraEntity.setUmd(fila.getString(5));
            listaPromocionCabeceraEntity.setCantidadcompra(fila.getString(6));
            listaPromocionCabeceraEntity.setPreciobase(fila.getString(9));
            listaPromocionCabeceraEntity.setDescuento(fila.getString(10));

            Log.e("REOS","PromocionCabeceraSQLiteDao.ObtenerPromocionCabecera.contado: "+contado);
            if(Integer.parseInt(listaPromocionCabeceraEntity.getDescuento())>0 ){
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id(),
                        contado,
                        context,
                        cardcode,
                        terminopago_id


                );

                PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity= new PromocionDetalleSQLiteEntity();
                promocionDetalleSQLiteEntity.setCompania_id(SesionEntity.compania_id);
                promocionDetalleSQLiteEntity.setLista_promocion_id(listaPromocionCabeceraEntity.getLista_promocion_id());
                promocionDetalleSQLiteEntity.setPromocion_detalle_id(String.valueOf((listaPromocionDetalleEntities.size())+1));
                promocionDetalleSQLiteEntity.setProducto_id("%");
                promocionDetalleSQLiteEntity.setProducto("DESCUENTO");
                promocionDetalleSQLiteEntity.setUmd("%");
                promocionDetalleSQLiteEntity.setCantidad(listaPromocionCabeceraEntity.getDescuento());
                promocionDetalleSQLiteEntity.setFuerzatrabajo_id(SesionEntity.fuerzatrabajo_id);
                promocionDetalleSQLiteEntity.setUsuario_id(SesionEntity.usuario_id);
                promocionDetalleSQLiteEntity.setPreciobase("0");
                promocionDetalleSQLiteEntity.setChkdescuento("0");
                promocionDetalleSQLiteEntity.setDescuento("0");

                listaPromocionDetalleEntities.add(promocionDetalleSQLiteEntity);

            }else
            {
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id(),
                        contado,
                        context,
                        cardcode,
                        terminopago_id

                );
            }

            Log.e("REOS","PromocionCabeceraSQLiteDao: "+listaPromocionDetalleEntities.size());
            listaPromocionCabeceraEntity.setCardcode(cardcode);
            listaPromocionCabeceraEntity.setTerminopago_id(terminopago_id);
            listaPromocionCabeceraEntity.setUbigeo_id(currency);
            listaPromocionCabeceraEntity.setCurrency_id(currency);
            listaPromocionCabeceraEntity.setListaPromocionDetalleEntities(listaPromocionDetalleEntities);
            listaPromocionCabeceraSQLiteEntity.add(listaPromocionCabeceraEntity);
        }
        bd.close();
        return listaPromocionCabeceraSQLiteEntity;
    }

    public ArrayList<ListaPromocionCabeceraEntity> ObtenerPromocionCabeceraUnidad (
            String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String producto_id,
            String umd,
            String cantidad,
            String contado,
            String terminopago_id,
            String cardcode
    )
    {
        ArrayList<ListaPromocionCabeceraEntity>  listaPromocionCabeceraSQLiteEntity = new ArrayList<>();
        ListaPromocionCabeceraEntity listaPromocionCabeceraEntity;
        ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities=new ArrayList<>();
        PromocionDetalleSQLiteDao promocionDetalleSQLiteDao=new PromocionDetalleSQLiteDao(context);
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from promocioncabecera where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and usuario_id='" + usuario_id + "' " +
                        "and producto_id='" + producto_id + "' and umd='" + umd + "' and CAST(cantidad as INTEGER)='" + cantidad + "'",null);

        while (fila.moveToNext())
        {
            listaPromocionCabeceraEntity= new ListaPromocionCabeceraEntity();
            listaPromocionCabeceraEntity.setLista_promocion_id(fila.getString(1));
            listaPromocionCabeceraEntity.setPromocion_id(fila.getString(2));
            listaPromocionCabeceraEntity.setProducto_id(fila.getString(3));
            listaPromocionCabeceraEntity.setProducto(fila.getString(4));
            listaPromocionCabeceraEntity.setUmd(fila.getString(5));
            listaPromocionCabeceraEntity.setCantidadcompra(fila.getString(6));
            listaPromocionCabeceraEntity.setPreciobase(fila.getString(9));
            listaPromocionCabeceraEntity.setDescuento(fila.getString(10));

            /*listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                    SesionEntity.compania_id,
                    listaPromocionCabeceraEntity.getLista_promocion_id(),
                    listaPromocionCabeceraEntity.getPromocion_id()
            );*/
            if(Integer.parseInt(listaPromocionCabeceraEntity.getDescuento())>0 ){
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id(),
                        contado,
                        context,
                        cardcode,
                        terminopago_id

                );

                PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity= new PromocionDetalleSQLiteEntity();
                promocionDetalleSQLiteEntity.setCompania_id(SesionEntity.compania_id);
                promocionDetalleSQLiteEntity.setLista_promocion_id(listaPromocionCabeceraEntity.getLista_promocion_id());
                promocionDetalleSQLiteEntity.setPromocion_detalle_id(String.valueOf((listaPromocionDetalleEntities.size())+1));
                promocionDetalleSQLiteEntity.setProducto_id("%");
                promocionDetalleSQLiteEntity.setProducto("DESCUENTO");
                promocionDetalleSQLiteEntity.setUmd("%");
                promocionDetalleSQLiteEntity.setCantidad(listaPromocionCabeceraEntity.getDescuento());
                promocionDetalleSQLiteEntity.setFuerzatrabajo_id(SesionEntity.fuerzatrabajo_id);
                promocionDetalleSQLiteEntity.setUsuario_id(SesionEntity.usuario_id);
                promocionDetalleSQLiteEntity.setPreciobase("0");
                promocionDetalleSQLiteEntity.setChkdescuento("0");
                promocionDetalleSQLiteEntity.setDescuento("0");

                listaPromocionDetalleEntities.add(promocionDetalleSQLiteEntity);

            }else
            {
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id(),
                        contado,
                        context,
                        cardcode,
                        terminopago_id
                );
            }
            Log.e("REOS","PromocionCabeceraSQLiteDao: "+listaPromocionDetalleEntities.size());
            listaPromocionCabeceraEntity.setListaPromocionDetalleEntities(listaPromocionDetalleEntities);
            listaPromocionCabeceraSQLiteEntity.add(listaPromocionCabeceraEntity);
        }
        bd.close();
        return listaPromocionCabeceraSQLiteEntity;
    }

    public int ObtenerCantidadPromocionCabecera ()
    {
        int resultado=0;

        abrir();
        try {
            Cursor fila = bd.rawQuery(
                    "Select count(compania_id) from promocioncabecera",null);

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

    public ArrayList<PromocionCabeceraSQLiteEntity> ObtenerPromocionCabeceraPorCodigo (
            String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String lista_promocion_id,
            String promocion_id
    )
    {
        listaPromocionCabeceraSQLiteEntity = new ArrayList<PromocionCabeceraSQLiteEntity>();
        PromocionCabeceraSQLiteEntity promocionCabeceraSQLiteEntity;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from promocioncabecera where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and usuario_id='" + usuario_id + "' " +
                        "and lista_promocion_id='" + lista_promocion_id + "' and promocion_id='" + promocion_id + "'",null);

        while (fila.moveToNext())
        {
            promocionCabeceraSQLiteEntity= new PromocionCabeceraSQLiteEntity();
            promocionCabeceraSQLiteEntity.setCompania_id(fila.getString(0));
            promocionCabeceraSQLiteEntity.setLista_promocion_id(fila.getString(1));
            promocionCabeceraSQLiteEntity.setPromocion_id(fila.getString(2));
            promocionCabeceraSQLiteEntity.setProducto_id(fila.getString(3));
            promocionCabeceraSQLiteEntity.setProducto(fila.getString(4));
            promocionCabeceraSQLiteEntity.setUmd(fila.getString(5));
            promocionCabeceraSQLiteEntity.setCantidad(fila.getString(6));
            promocionCabeceraSQLiteEntity.setFuerzatrabajo_id(fila.getString(7));
            promocionCabeceraSQLiteEntity.setUsuario_id(fila.getString(8));
            promocionCabeceraSQLiteEntity.setTotal_preciobase(fila.getString(9));
            promocionCabeceraSQLiteEntity.setDescuento(fila.getString(10));

            listaPromocionCabeceraSQLiteEntity.add(promocionCabeceraSQLiteEntity);
        }
        bd.close();
        return listaPromocionCabeceraSQLiteEntity;
    }

    public boolean ObtenerEstadoPromocionConsultaStock (
            String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String producto_id,
            String umd

    )
    {
        boolean estado;
        int resultado=0;
        abrir();
        Cursor fila = bd.rawQuery(
                "Select  count(compania_id)  from promocioncabecera where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and usuario_id='" + usuario_id + "' " +
                        "and producto_id='" + producto_id + "' and umd='" + umd + "'",null);

        while (fila.moveToNext())
        {
            resultado= Integer.parseInt(fila.getString(0));

        }
        if(resultado>0)
        {
            estado=true;
        }else
            {
                estado=false;
            }
        fila.close();
        bd.close();
        return estado;
    }

    public ArrayList<ListaPromocionCabeceraEntity> ObtenerPromocionCabeceraConsultaStock (
            String compania_id,
            String fuerzatrabajo_id,
            String usuario_id,
            String producto_id,
            String umd
    )
    {
        ArrayList<ListaPromocionCabeceraEntity>  listaPromocionCabeceraSQLiteEntity = new ArrayList<>();
        ListaPromocionCabeceraEntity listaPromocionCabeceraEntity;
        ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities=new ArrayList<>();
        PromocionDetalleSQLiteDao promocionDetalleSQLiteDao=new PromocionDetalleSQLiteDao(context);
        abrir();
        Cursor fila = bd.rawQuery(
                "Select * from promocioncabecera where compania_id='" + compania_id + "' and fuerzatrabajo_id='" + fuerzatrabajo_id + "' and usuario_id='" + usuario_id + "' " +
                        "and producto_id='" + producto_id + "' and umd='" + umd + "' order by CAST(cantidad AS INTEGER)",null);

        while (fila.moveToNext())
        {
            listaPromocionCabeceraEntity= new ListaPromocionCabeceraEntity();
            listaPromocionCabeceraEntity.setLista_promocion_id(fila.getString(1));
            listaPromocionCabeceraEntity.setPromocion_id(fila.getString(2));
            listaPromocionCabeceraEntity.setProducto_id(fila.getString(3));
            listaPromocionCabeceraEntity.setProducto(fila.getString(4));
            listaPromocionCabeceraEntity.setUmd(fila.getString(5));
            listaPromocionCabeceraEntity.setCantidadcompra(fila.getString(6));
            Log.e("REOS","PromocionCabeceraSQLiteDao-ObtenerPromocionCabeceraConsultaStock-listaPromocionCabeceraEntity.getCantidadCompra: "+listaPromocionCabeceraEntity.getCantidadcompra());
            listaPromocionCabeceraEntity.setPreciobase(fila.getString(9));
            listaPromocionCabeceraEntity.setDescuento(fila.getString(10));


            if(Integer.parseInt(listaPromocionCabeceraEntity.getDescuento())>0 ){
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalleConsultaStock(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id()
                );

                PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity= new PromocionDetalleSQLiteEntity();
                promocionDetalleSQLiteEntity.setCompania_id(SesionEntity.compania_id);
                promocionDetalleSQLiteEntity.setLista_promocion_id(listaPromocionCabeceraEntity.getLista_promocion_id());
                promocionDetalleSQLiteEntity.setPromocion_detalle_id(String.valueOf((listaPromocionDetalleEntities.size())+1));
                promocionDetalleSQLiteEntity.setProducto_id("%");
                promocionDetalleSQLiteEntity.setProducto("DESCUENTO");
                promocionDetalleSQLiteEntity.setUmd("%");
                promocionDetalleSQLiteEntity.setCantidad(listaPromocionCabeceraEntity.getDescuento());
                promocionDetalleSQLiteEntity.setFuerzatrabajo_id(SesionEntity.fuerzatrabajo_id);
                promocionDetalleSQLiteEntity.setUsuario_id(SesionEntity.usuario_id);
                promocionDetalleSQLiteEntity.setPreciobase("0");
                promocionDetalleSQLiteEntity.setChkdescuento("0");
                promocionDetalleSQLiteEntity.setDescuento("0");

                listaPromocionDetalleEntities.add(promocionDetalleSQLiteEntity);

            }else
            {
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalleConsultaStock(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id()
                );
            }

            Log.e("REOS","PromocionCabeceraSQLiteDao: "+listaPromocionDetalleEntities.size());
            listaPromocionCabeceraEntity.setListaPromocionDetalleEntities(listaPromocionDetalleEntities);
            listaPromocionCabeceraSQLiteEntity.add(listaPromocionCabeceraEntity);
        }
        bd.close();
        return listaPromocionCabeceraSQLiteEntity;
    }


    public ArrayList<ListaPromocionCabeceraEntity> getListPromotionVigent (
    )
    {
        ArrayList<ListaPromocionCabeceraEntity>  listaPromocionCabeceraSQLiteEntity = new ArrayList<>();
        ListaPromocionCabeceraEntity listaPromocionCabeceraEntity;
        ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities=new ArrayList<>();
        PromocionDetalleSQLiteDao promocionDetalleSQLiteDao=new PromocionDetalleSQLiteDao(context);
        abrir();
        Cursor fila = bd.rawQuery(
                " Select *,    (SELECT COUNT(*) FROM promocioncabecera AS X " +
                        "    LEFT JOIN listapromocion AS Y ON X.lista_promocion_id = Y.lista_promocion_id " +
                        "    WHERE Y.lista_promocion_id = B.lista_promocion_id and  X.producto_id = A.producto_id " +
                        "    AND (X.producto_id < A.producto_id OR (X.producto_id = A.producto_id AND X.cantidad < CAST(A.cantidad AS INTEGER)))) + 1 AS numerador from promocioncabecera A "+
                " LEFT JOIN listapromocion B ON" +
                " A.lista_promocion_id=B.lista_promocion_id" +
                " order by B.lista_promocion_id,A.producto_id,CAST(cantidad AS INTEGER) "
                ,null);

        while (fila.moveToNext())
        {
            listaPromocionCabeceraEntity= new ListaPromocionCabeceraEntity();
            listaPromocionCabeceraEntity.setLista_promocion_id(fila.getString(1));
            listaPromocionCabeceraEntity.setPromocion_id(fila.getString(2));
            listaPromocionCabeceraEntity.setProducto_id(fila.getString(3));
            listaPromocionCabeceraEntity.setProducto(fila.getString(4));
            listaPromocionCabeceraEntity.setUmd(fila.getString(5));
            listaPromocionCabeceraEntity.setCantidadcompra(fila.getString(6));
            Log.e("REOS","PromocionCabeceraSQLiteDao-ObtenerPromocionCabeceraConsultaStock-listaPromocionCabeceraEntity.getCantidadCompra: "+listaPromocionCabeceraEntity.getCantidadcompra());
            listaPromocionCabeceraEntity.setPreciobase(fila.getString(9));
            listaPromocionCabeceraEntity.setDescuento(fila.getString(10));
            listaPromocionCabeceraEntity.setLista_promocion(fila.getString(13));
            listaPromocionCabeceraEntity.setCount(fila.getString(15));
            if(Integer.parseInt(listaPromocionCabeceraEntity.getDescuento())>0 ){
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalleConsultaStock(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id()
                );

                PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity= new PromocionDetalleSQLiteEntity();
                promocionDetalleSQLiteEntity.setCompania_id(SesionEntity.compania_id);
                promocionDetalleSQLiteEntity.setLista_promocion_id(listaPromocionCabeceraEntity.getLista_promocion_id());
                promocionDetalleSQLiteEntity.setPromocion_detalle_id(String.valueOf((listaPromocionDetalleEntities.size())+1));
                promocionDetalleSQLiteEntity.setProducto_id("%");
                promocionDetalleSQLiteEntity.setProducto("DESCUENTO");
                promocionDetalleSQLiteEntity.setUmd("%");
                promocionDetalleSQLiteEntity.setCantidad(listaPromocionCabeceraEntity.getDescuento());
                promocionDetalleSQLiteEntity.setFuerzatrabajo_id(SesionEntity.fuerzatrabajo_id);
                promocionDetalleSQLiteEntity.setUsuario_id(SesionEntity.usuario_id);
                promocionDetalleSQLiteEntity.setPreciobase("0");
                promocionDetalleSQLiteEntity.setChkdescuento("0");
                promocionDetalleSQLiteEntity.setDescuento("0");

                listaPromocionDetalleEntities.add(promocionDetalleSQLiteEntity);

            }else
            {
                listaPromocionDetalleEntities=promocionDetalleSQLiteDao.ObtenerPromocionDetalleConsultaStock(
                        SesionEntity.compania_id,
                        listaPromocionCabeceraEntity.getLista_promocion_id(),
                        listaPromocionCabeceraEntity.getPromocion_id()
                );
            }

            Log.e("REOS","PromocionCabeceraSQLiteDao: "+listaPromocionDetalleEntities.size());
            listaPromocionCabeceraEntity.setListaPromocionDetalleEntities(listaPromocionDetalleEntities);
            listaPromocionCabeceraSQLiteEntity.add(listaPromocionCabeceraEntity);
        }
        bd.close();
        return listaPromocionCabeceraSQLiteEntity;
    }
}

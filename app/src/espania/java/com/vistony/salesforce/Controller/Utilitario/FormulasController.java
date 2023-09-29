package com.vistony.salesforce.Controller.Utilitario;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.gson.Gson;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaUnidadWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.Retrofit.VisitaRepository;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CustomerComplaintFormsSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ListaPromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaDetallePromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.DocumentHeader;
import com.vistony.salesforce.Entity.DocumentLine;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintFormsEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CustomerComplaintSectionEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetallePromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.View.TotalSalesOrder;
import com.vistony.salesforce.View.OrdenVentaDetalleView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FormulasController {
    DecimalFormat format = new DecimalFormat("#0.00");
    Context Context;
    private VisitaRepository visitaRepository;
    private CobranzaRepository cobranzaRepository;

    public FormulasController(Context context)
    {
        Context=context;
    }

    public String suma(String num1,String num2){
        num1=(num1.equals("")||num1==null)?"0":num1;
        num2=(num2.equals("")||num2==null)?"0":num2;

        BigDecimal temp1=new BigDecimal(num1);
        BigDecimal rpta=temp1.add(new BigDecimal(num2));

        return rpta.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String CalcularMontoTotalconDescuento(String MontoTotalLineaSinDescuento,String MontoDescuento){
        BigDecimal temp1=new BigDecimal(MontoTotalLineaSinDescuento);

        BigDecimal repta=temp1.subtract(new BigDecimal(MontoDescuento)).setScale(2, RoundingMode.HALF_UP);

        return repta.toString();
    }

    public String CalcularMontoTotalporLinea(String MontoSubTotalSinDescuento,String MontoDescuento,String MontoImpuesto) {

        BigDecimal xd1=new BigDecimal(MontoSubTotalSinDescuento).setScale(2, RoundingMode.HALF_UP);
        BigDecimal xd2=new BigDecimal(MontoDescuento).setScale(2, RoundingMode.HALF_UP);

        BigDecimal rpta=xd1.subtract(xd2).add(new BigDecimal(MontoImpuesto));

       return rpta.toString();
    }

    public String CalcularMontoImpuestoPorLinea(String MontoSubtotal,String MontoDescuento,String Impuesto){
        BigDecimal xd1=new BigDecimal(MontoSubtotal).setScale(2, RoundingMode.HALF_UP);
        BigDecimal xd2=new BigDecimal(MontoDescuento).setScale(2, RoundingMode.HALF_UP);
        return ""+xd1.subtract(xd2).multiply((new BigDecimal(Impuesto)).multiply(new BigDecimal("0.01")));
    }

    /**
    *
    *  @param PrecioUnitario xdd
    *  @param Cantidad suamnod
    *  @return  suma
    */
    public String getTotalPerLine(String PrecioUnitario,String Cantidad){

        Cantidad=(Cantidad.equals(""))?"0":Cantidad;

        BigDecimal preUnit = new BigDecimal(PrecioUnitario).setScale(3,RoundingMode.HALF_UP);
        BigDecimal cant = new BigDecimal(Cantidad);

        BigDecimal subTotalLine=preUnit.multiply(cant);
        return subTotalLine.toString();
    }

    public String applyDiscountPercentageForLine(String MontoTotalLineaSinIGV,String PorcentajeDescuento) {
        Log.e("applyDiscountPercentageForLine","%=>"+PorcentajeDescuento);
        PorcentajeDescuento=(PorcentajeDescuento==null || PorcentajeDescuento.equals(""))?"0":PorcentajeDescuento;

        BigDecimal monto = new BigDecimal(MontoTotalLineaSinIGV);
        BigDecimal porcentaje = new BigDecimal(PorcentajeDescuento);

        BigDecimal montoDescuento=monto.multiply(porcentaje).divide(new BigDecimal(100)).setScale(2,RoundingMode.HALF_UP);
        Log.e("applyDiscountPercentageForLine","=>"+montoDescuento);

        return montoDescuento.toString();
    }

    /*
    public String CalcularCantidadLineaPromocionDetalle(String CantidadPromocionCabecera,String CantidadPromocionDetalle)
    {

        float CantidadFinal=0;
        CantidadFinal=(Float.parseFloat(CantidadPromocionCabecera)*Float.parseFloat(CantidadPromocionDetalle));
        return format.format(CantidadFinal);
    }*/

    public String CalcularPorcentajeDescuentoPorLinea(ArrayList<ListaPromocionCabeceraEntity> ListaPromocionCabeceradelaLinea,String descuentocontado){

        ArrayList<ListaPromocionCabeceraEntity> listapromocioncabecera=ListaPromocionCabeceradelaLinea;
        int calculodescuento=0;

        for(int i=0;i<listapromocioncabecera.size();i++){
            if(!(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().isEmpty())){
                for(int j=0;j<listapromocioncabecera.get(i).getListaPromocionDetalleEntities().size();j++){
                    if(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().get(j).getUmd().equals("%")){
                        if(descuentocontado.equals("true")){
                            calculodescuento=(calculodescuento+Integer.parseInt(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().get(j).getCantidad()))+3;
                        }else{
                            calculodescuento=calculodescuento+Integer.parseInt(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().get(j).getCantidad());
                        }
                    }
                }
            }
        }

        return String.valueOf(calculodescuento);
    }

    //CALCULAR PARA MOSTRAR EN CABECERA
    public TotalSalesOrder CalcularMontosPedidoCabeceraDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity, Context context){

        String CalculoTotalDescuento,CalculoTotalGalonaje;

        CalculoTotalDescuento= ObtenerCalculoDescuentoOrdenDetallePromocion(listaOrdenVentaDetalleEntity);
        CalculoTotalGalonaje=ObtenerCalculoTotalGalonesOrdenDetallePromocion(listaOrdenVentaDetalleEntity);

        String CalculoTotalIGV="",CalculoTotal="";
        BigDecimal CalculoSubTotal=new BigDecimal(ObtenerCalculoMontoSubTotalOrdenDetallePromocion(listaOrdenVentaDetalleEntity,context)).setScale(2,RoundingMode.HALF_UP);
        BigDecimal CalculoTotalDsct=new BigDecimal(CalculoTotalDescuento).setScale(2,RoundingMode.HALF_UP);

        CalculoTotalIGV=""+CalculoSubTotal.subtract(CalculoTotalDsct).multiply( new BigDecimal(Induvis.getImpuestoDouble())).setScale(2,RoundingMode.HALF_UP);
        CalculoTotal=CalculoSubTotal.subtract(CalculoTotalDsct).add(new BigDecimal(CalculoTotalIGV)).setScale(2,RoundingMode.HALF_UP).toString();


        Log.e("setSubtotal",""+CalculoSubTotal);
        Log.e("setDescuento",""+CalculoTotalDsct);
        Log.e("setIgv",""+CalculoTotalIGV);
        Log.e("setTotal",""+CalculoTotal);
        Log.e("setGalones",""+CalculoTotalGalonaje);

        TotalSalesOrder totalSalesOrder=new TotalSalesOrder();
        totalSalesOrder.setSubtotal(CalculoSubTotal.toString());
        totalSalesOrder.setDescuento(CalculoTotalDsct.toString());
        totalSalesOrder.setIgv(CalculoTotalIGV);
        totalSalesOrder.setTotal(CalculoTotal.toString());
        totalSalesOrder.setGalones(CalculoTotalGalonaje);

        return (totalSalesOrder);
    }


    public String ObtenerCalculoTotalGalonesOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista) {
        BigDecimal acum=new BigDecimal(0);
        double CalculoIGV=0.0;
        for(int i=0;i<Lista.size();i++){
            acum=acum.add(new BigDecimal(Lista.get(i).getOrden_detalle_gal_acumulado()));
        }
        return acum.toString();
    }

    public String CalcularMontoImpuestoOrdenDetallePromocionLinea(ListaOrdenVentaDetalleEntity lead){
        BigDecimal calculoImpuesto=new BigDecimal(0);

        if(lead.getOrden_detalle_lista_orden_detalle_promocion()!=null){
            for(int i=0;i<lead.getOrden_detalle_lista_orden_detalle_promocion().size();i++){
                Log.e("REOS", "formulascontroller-CalcularMontoImpuestoOrdenDetallePromocionLinea-lead.getOrden_detalle_lista_orden_detalle_promocion().get(i).getOrden_detalle_monto_igv():"+lead.getOrden_detalle_lista_orden_detalle_promocion().get(i).getOrden_detalle_monto_igv());

                calculoImpuesto=calculoImpuesto.add(new BigDecimal(lead.getOrden_detalle_lista_orden_detalle_promocion().get(i).getOrden_detalle_monto_igv()).setScale(2,RoundingMode.HALF_UP));
            }
        }

        return calculoImpuesto.toString();
    }

    /*public String ObtenerCalculoPromocionOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista) {

        BigDecimal cacculoDsct=new BigDecimal(0);

        for(int i=0;i<Lista.size();i++){
            if(Lista.get(i).getOrden_detalle_porcentaje_descuento().equals("100")){
                cacculoDsct=cacculoDsct.add(new BigDecimal(Lista.get(i).getOrden_detalle_monto_descuento()));
            }
        }
        return cacculoDsct.toString();
    }*/

    public String ObtenerCalculoDescuentoOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista){
        BigDecimal cacculoDsct=new BigDecimal(0);
        Log.e("ObtenerCalculoDescuentoOrdenDetallePromocion","=>>"+Lista.size());
        for(int i=0;i<Lista.size();i++){
            Log.e("ObtenerCalculoDescuentoOrdenDetallePromocion","=>>"+Lista.get(i).getOrden_detalle_monto_descuento());
            if(Lista.get(i).getOrden_detalle_porcentaje_descuento()!=null){
                //if(Double.parseDouble(Lista.get(i).getOrden_detalle_porcentaje_descuento())>0&&Double.parseDouble(Lista.get(i).getOrden_detalle_porcentaje_descuento())<100) {
                    Log.e("ObtenerCalculoDescuentoOrdenDetallePromocion","=>"+Lista.get(i).getOrden_detalle_porcentaje_descuento());
                    cacculoDsct=cacculoDsct.add(new BigDecimal(Lista.get(i).getOrden_detalle_monto_descuento()));
                    Log.e("ObtenerCalculoDescuentoOrdenDetallePromocion","=>"+Lista.get(i).getOrden_detalle_monto_descuento());
               // }
            }
        }
        Log.e("ObtenerCalculoDescuentoOrdenDetallePromocion","=>"+cacculoDsct);
        return cacculoDsct.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public String ObtenerCalculoMontoSubTotalOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista, Context context){

        BigDecimal acum = new BigDecimal(0);

        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(context);
        ArrayList<ListaPrecioDetalleSQLiteEntity> listSIGAUS=new ArrayList<>();
        listSIGAUS=listaPrecioDetalleSQLiteDao.getProductSIGAUS();

        String preciobase="";
        int SIGAUS=0;
        for(int j=0;j<listSIGAUS.size();j++)
        {
            preciobase=listSIGAUS.get(j).getContado();
        }
        for(int g=0;g<Lista.size();g++)
        {
            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(g).getOrden_detalle_lista_promocion_cabecera(): "+Lista.get(g).getOrden_detalle_lista_promocion_cabecera());
            if(Lista.get(g).getOrden_detalle_producto_id().equals("SIGAUS"))
            {
                SIGAUS++;
            }
        }

        Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.preciobase: "+preciobase);
        Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.size: "+Lista.size());
        if(SIGAUS==0)
        {
            for(int i=0;i<Lista.size();i++) {
                if(BuildConfig.FLAVOR.equals("espania"))
                {
                    if(Lista.get(i).getOrden_detalle_oil_tax()!=null)
                    {
                        Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).getOrden_detalle_oil_tax()"+ Lista.get(i).getOrden_detalle_oil_tax());
                        if (Lista.get(i).getOrden_detalle_oil_tax().equals("Y")) {
                            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).Lista.get(i).getOrden_detalle_liter())"+Lista.get(i).getOrden_detalle_liter());
                            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).getOrden_detalle_producto_id"+Lista.get(i).getOrden_detalle_producto_id());
                            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).getOrden_detalle_cantidad"+Lista.get(i).getOrden_detalle_cantidad());
                            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).getOrden_detalle_gal"+Lista.get(i).getOrden_detalle_gal());
                            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).getOrden_detalle_montosubtotal"+Lista.get(i).getOrden_detalle_montosubtotal());
                            Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).SIGAUS"+(Convert.convertSIGAUS(
                                    Convert.convertLiterAcum
                                            (
                                                    Lista.get(i).getOrden_detalle_liter(),
                                                    Lista.get(i).getOrden_detalle_cantidad()
                                            ),
                                    preciobase
                            )));
                            acum = acum.add(new BigDecimal(
                                            String.valueOf(
                                            Float.parseFloat(Lista.get(i).getOrden_detalle_montosubtotal())
                                            +
                                            Float.parseFloat(Convert.convertSIGAUS(
                                                    Convert.convertLiterAcum
                                                            (
                                                                    Lista.get(i).getOrden_detalle_liter(),
                                                                    Lista.get(i).getOrden_detalle_cantidad()
                                                            ),
                                                    preciobase
                                            ))
                                            )
                            ));

                        } else {
                            acum = acum.add(new BigDecimal(Lista.get(i).getOrden_detalle_montosubtotal()));
                        }
                    }
                    else {
                        Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.Lista.get(i).getOrden_detalle_oil_tax()"+ Lista.get(i).getOrden_detalle_oil_tax());

                        acum = acum.add(new BigDecimal(Lista.get(i).getOrden_detalle_montosubtotal()));
                    }
                }
                else {
                    Log.e("REOS","FormulasController.ObtenerCalculoMontoSubTotalOrdenDetallePromocion.not BuildConfig.FLAVOR.equals(\"espania\")");

                    acum = acum.add(new BigDecimal(Lista.get(i).getOrden_detalle_montosubtotal()));
                }
            }
        }else {
            for(int i=0;i<Lista.size();i++) {
                acum = acum.add(new BigDecimal(Lista.get(i).getOrden_detalle_montosubtotal()));
            }
        }


        return ""+acum.setScale(2, RoundingMode.HALF_UP);
    }

    /*public Float ObtenerCalculoPromocion(ArrayList<ListaPromocionCabeceraEntity> Lista) {
        Float CalculoPromocion=0f;
        for(int i=0;i<Lista.size();i++)
        {
            Log.e("REOS","FormulasController:ObtenerCalculoPromocion-Lista.Size(): "+Lista.size());
            for(int j=0;j<Lista.get(i).getListaPromocionDetalleEntities().size();j++)
            {
                Log.e("REOS","FormulasController:ObtenerCalculoPromocion-Lista.get(i).getListaPromocionDetalleEntities().size(): "+Lista.get(i).getListaPromocionDetalleEntities().size());
                Log.e("REOS","FormulasController:ObtenerCalculoPromocion-Lista.get(i).getListaPromocionDetalleEntities().get(j).getPreciobase(): "+Lista.get(i).getListaPromocionDetalleEntities().get(j).getPreciobase().toString());
                Log.e("REOS","FormulasController:ObtenerCalculoPromocion-Lista.get(i).getListaPromocionDetalleEntities().get(j).getCantidad(): "+Lista.get(i).getListaPromocionDetalleEntities().get(j).getCantidad().toString());
                Log.e("REOS","FormulasController:ObtenerCalculoPromocion-Lista.get(i).Lista.get(i).getCantidadcompra(): "+Lista.get(i).getCantidadcompra().toString());
                Log.e("REOS","FormulasController:ObtenerCalculoPromocio n-Lista.get(i).getCantidadPromocion: "+Lista.get(i).getCantidadpromocion().toString());
                CalculoPromocion=CalculoPromocion+
                        (((Float.parseFloat(Lista.get(i).getListaPromocionDetalleEntities().get(j).getPreciobase()))*
                                //(Float.parseFloat(Lista.get(i).getCantidadpro n)) ;
                                Float.parseFloat(Lista.get(i).getListaPromocionDetalleEntities().get(j).getCantidad()))
                                *Float.parseFloat(Lista.get(i).getCantidadpromocion()));
                ;
                Log.e("REOS","FormulasController:ObtenerCalculoPromocionporLinea: "+CalculoPromocion.toString());
            }
            Log.e("REOS","FormulasController:ObtenerCalculoPromocion-CalculoPromocion.toString(): "+CalculoPromocion.toString());
            Log.e("REOS","FormulasController:ObtenerCalculoPromocion-Lista.get(i).getCantidadpromocion() "+Lista.get(i).getCantidadpromocion());
            //CalculoPromocion=CalculoPromocion*Float.parseFloat(Lista.get(i).getCantidadpromocion());
        }

        Log.e("REOS","FormulasController:ObtenerCalculoPromocion: "+CalculoPromocion.toString());
        return CalculoPromocion;
    }*/

    public Float ObtenerCalculoPromocionDetalle(ArrayList<ListaPromocionCabeceraEntity> Lista,int posicion)
    {
        Float CalculoPromocion=0f;
        /*
        for(int i=0;i<Lista.size();i++)
        {¨*/
            for(int j=0;j<Lista.get(posicion).getListaPromocionDetalleEntities().size();j++)
            {
                CalculoPromocion=CalculoPromocion+
                        ((Float.parseFloat(Lista.get(posicion).getListaPromocionDetalleEntities().get(j).getPreciobase()))*
                                Float.parseFloat(Lista.get(posicion).getListaPromocionDetalleEntities().get(j).getCantidad())) ;
            }
        CalculoPromocion=CalculoPromocion*Float.parseFloat(Lista.get(posicion).getCantidadpromocion());

        return CalculoPromocion;
    }



    public Float ObtenerCalculoPromocionPorLinea(ArrayList<PromocionDetalleSQLiteEntity> Lista)
    {
        Float CalculoPromocion=0f;
        for(int i=0;i<Lista.size();i++)
        {

                CalculoPromocion=CalculoPromocion+
                        ((Float.parseFloat(Lista.get(i).getPreciobase()))*
                                (Float.parseFloat(Lista.get(i).getCantidad()))) ;

        }
        return CalculoPromocion;
    }

    public String ObtenerComentarioChkDescuentoContado (ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity)
    {
        String Comentario="Comentario Descuento: ";
        for(int i=0;i<listaOrdenVentaDetalleEntity.size();i++)
        {
            Log.e("REOS","formulasController.ObtenerComentarioChkDescuentoContado: "+listaOrdenVentaDetalleEntity.size());
            Log.e("REOS","formulasController.ObtenerComentarioChkDescuentoContado.listaOrdenVentaDetalleEntity.get(i).isOrden_detalle_chk_descuentocontado_cabecera(): "+listaOrdenVentaDetalleEntity.get(i).isOrden_detalle_chk_descuentocontado_cabecera());
            if(listaOrdenVentaDetalleEntity.get(i).isOrden_detalle_chk_descuentocontado_cabecera())
            {
                Log.e("REOS","formulasController.ObtenerComentarioChkDescuentoContado.listaOrdenVentaDetalleEntity.get(i).isOrden_detalle_chk_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(i).isOrden_detalle_chk_descuentocontado());
                if(listaOrdenVentaDetalleEntity.get(i).isOrden_detalle_chk_descuentocontado())
                {
                    Comentario=Comentario+"- Linea("+listaOrdenVentaDetalleEntity.get(i).getOrden_detalle_item()+") Producto: ("+listaOrdenVentaDetalleEntity.get(i).getOrden_detalle_producto_id()+") DescuentoContado: ("+listaOrdenVentaDetalleEntity.get(i).getOrden_detalle_descuentocontado()+")";
                }
            }

        }
        return Comentario;
    }

    //Ya esta
    public void RegistrarPedidoenBD(ArrayList<ListaOrdenVentaCabeceraEntity> listaOrdenVentaCabeceraEntities,ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity) {
        String OrdenVenta_id="",impuesto_id="",producto="",almacen_id="",comentariodescuentocontado="";
        int cantidadlistaOrdenVentaDetallepromocion=0;
        comentariodescuentocontado=ObtenerComentarioChkDescuentoContado(listaOrdenVentaDetalleEntity);
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();


        for(int i=0;i<listaOrdenVentaCabeceraEntities.size();i++){

            OrdenVenta_id=listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_id();
            impuesto_id=listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_impuesto_id();
            almacen_id=listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_almacen_id();

            OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(Context);
            ordenVentaCabeceraSQLite.InsertaOrdenVentaCabecera(
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_compania_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_cliente_id(),
                    ""+listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_domembarque_id(),
                    ""+listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_domfactura_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_terminopago_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_agencia_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_moneda_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_comentario()+"-"+comentariodescuentocontado,
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_almacen_id(),
                    ""+listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_impuesto_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montosubtotal(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montodescuento(),
                    ""+listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montoimpuesto(),
                    ""+listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montototal(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_fuerzatrabajo_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_usuario_id(),
                    "0",
                    "0",
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_lista_precio_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_planta(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_fecha_creacion(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_tipocambio(),
                    Convertirfechahoraafechanumerica(listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_fechatipocambio()),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_rucdni(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_DocType(),
                    "",
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_total_gal_acumulado(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_descuentocontado(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_cotizacion(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDTD(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDSD(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDCD(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDMT(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_STATUS(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_tipocambio(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_dispatch_date(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_route(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_VIT_VENMOS(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_VIS_Flete(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_VIS_CompleteOV(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_VIS_TipTransGrat(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_discount_percent(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_discount_percent_reason(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_VIS_MOTAPLDESC()
            );
        }

        int contador=0;
        for(int j=0;j<listaOrdenVentaDetalleEntity.size();j++)
        {
            OrdenVentaDetalleSQLiteDao ordenVentaDetalleSQLiteDao=new OrdenVentaDetalleSQLiteDao(Context);
            OrdenVentaDetallePromocionSQLiteDao ordenVentaDetallePromocionSQLiteDao=new OrdenVentaDetallePromocionSQLiteDao(Context);
            ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(Context);
            ArrayList<ListaPrecioDetalleSQLiteEntity> listaPrecioDetalleSQLiteEntities=new ArrayList<>();

            listaPrecioDetalleSQLiteEntities=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id(),SesionEntity.TipoListaPrecio);

            for(int g=0;g<listaPrecioDetalleSQLiteEntities.size();g++){
                producto=listaPrecioDetalleSQLiteEntities.get(g).getProducto();
            }

            if(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera()==null){
                contador++;

                ordenVentaDetalleSQLiteDao.InsertaOrdenVentaDetalle(
                        ObjUsuario.compania_id,
                        OrdenVenta_id,
                        ""+contador,
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_umd(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_cantidad(),
                        ""+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_precio_unitario(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_porcentaje_descuento(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_descuento(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_igv(),
                        ""+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montototallinea(),
                        ""+contador,
                        impuesto_id,
                        producto,
                        "",
                        almacen_id,
                        "",
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal_acumulado(),
                        "10",
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotalcondescuento(),
                        ""+listaOrdenVentaDetalleEntity.get(j).isOrden_detalle_chk_descuentocontado()
                );

                //Empieza OrdenVentaDetallePromocion sin Lineas de Promocion
                if(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion()!=null)
                {
                    for(int g=0;g<listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().size();g++)
                    {
                        cantidadlistaOrdenVentaDetallepromocion++;
                        Log.e("REOS", "FormulasController-OrdenVentaDetallePromocionsin Lineas de Promocion-listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre():" + listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre());
                        ordenVentaDetallePromocionSQLiteDao.InsertaOrdenVentaDetallePromocion(
                                ObjUsuario.compania_id,
                                OrdenVenta_id,
                                String.valueOf(cantidadlistaOrdenVentaDetallepromocion),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_producto_id(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_umd(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_cantidad(),
                                "" + Convert.stringToDouble(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_precio_unitario()),
                                //String.valueOf(Float.valueOf(format.format(Float.valueOf()))),

                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_montosubtotal(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_porcentaje_descuento(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_monto_descuento(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_monto_igv(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_montototallinea(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre(),
                                impuesto_id,
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_producto(),
                                "",
                                almacen_id,
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_promocion_id(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_gal(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_gal_acumulado(),
                                "10",
                                //MontoSubTotalporLinea
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_montosubtotalcondescuento(),
                                String.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).isOrden_detalle_chk_descuentocontado())
                        );
                    }

                }


            }else{
                        contador++;
                        int contadorpromocion=0;
                        contadorpromocion=contador;
                        ordenVentaDetalleSQLiteDao.InsertaOrdenVentaDetalle(
                                ObjUsuario.compania_id,
                                OrdenVenta_id,
                                ""+contador,
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_umd(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_cantidad(),
                                ""+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_precio_unitario(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotal(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_porcentaje_descuento(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_descuento(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_igv(),
                                ""+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montototallinea(),
                                ""+contador,
                                impuesto_id,
                                producto,
                                "",
                                almacen_id,
                                "",
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal_acumulado(),
                                "10",
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotalcondescuento(),
                                String.valueOf(listaOrdenVentaDetalleEntity.get(j).isOrden_detalle_chk_descuentocontado())
                        );

                        for(int k=0;k<listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().size();k++)
                        {
                            String promocion_id="";
                            promocion_id=listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getPromocion_id();

                            for(int l=0;l<listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().size();l++)
                            {

                                if(!(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getUmd().equals("%")))
                                {
                                    String MontoSubTotalporLinea;
                                    MontoSubTotalporLinea=getTotalPerLine(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase(),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getCantidad());
                                    contador++;
                                    ordenVentaDetalleSQLiteDao.InsertaOrdenVentaDetalle(
                                            ObjUsuario.compania_id,
                                            OrdenVenta_id,
                                            String.valueOf(contador),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getProducto_id(),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getUmd(),
                                            String.valueOf(Integer.parseInt(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getCantidad())
                                                    *Integer.parseInt(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getCantidadpromocion()))
                                            ,
                                            ""+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase(),
                                            MontoSubTotalporLinea,
                                            "100",
                                            MontoSubTotalporLinea,
                                            "0",
                                            //MontoSubTotalporLinea,
                                            ""+(Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase())

                                                    *(Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getCantidad())
                                                            *Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getCantidadpromocion())))
                                            ,
                                            String.valueOf(contadorpromocion),
                                            impuesto_id,
                                            //producto,
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getProducto(),
                                            "",
                                            almacen_id,
                                            promocion_id,
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal(),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal_acumulado(),
                                            "15",
                                            //MontoSubTotalporLinea
                                            "0",
                                            String.valueOf(listaOrdenVentaDetalleEntity.get(j).isOrden_detalle_chk_descuentocontado())
                                    );
                                }
                            }
                }
                        //Empieza OrdenVentaDetallePromocion con Lineas de Promocion
                    for(int g=0;g<listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().size();g++)
                    {

                        cantidadlistaOrdenVentaDetallepromocion++;
                        Log.e("REOS","FormulasController-OrdenVentaDetallePromocioncon Lineas de Promocion-listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre():"+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre());
                            ordenVentaDetallePromocionSQLiteDao.InsertaOrdenVentaDetallePromocion(
                                    ObjUsuario.compania_id,
                                    OrdenVenta_id,
                                    String.valueOf(cantidadlistaOrdenVentaDetallepromocion),

                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_producto_id(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_umd(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_cantidad(),
                                    ""+listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_precio_unitario(),

                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_montosubtotal(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_porcentaje_descuento(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_monto_descuento(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_monto_igv(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_montototallinea(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre(),

                                    impuesto_id,
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_producto(),
                                    "",
                                    almacen_id,
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_promocion_id(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_gal(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_gal_acumulado(),
                                    "10",
                                    //MontoSubTotalporLinea
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_montosubtotalcondescuento(),
                                    String.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).isOrden_detalle_chk_descuentocontado())
                            );

                    }


        }
        }
    }

    public boolean ValidarMontoEnCero (ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity)
    {

        boolean validacion=true;
        int contador=0;
        for(int i=0;i<listaOrdenVentaDetalleEntity.size();i++)
        {
           if(listaOrdenVentaDetalleEntity.get(i).getOrden_detalle_cantidad().equals("0"))
           {
               contador++;
           }
            if(listaOrdenVentaDetalleEntity.get(i).getOrden_detalle_cantidad().equals(""))
            {
                contador++;
            }
        }

        if(contador==0)
        {
            validacion=false;
        }
        return validacion;
    }

    public String GenerayConvierteaJSONOV (String ordenventa_id,Context context){

        String cadenaJSON="";
        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(context);
        OrdenVentaDetallePromocionSQLiteDao ordenVentaDetallePromocionSQLiteDao=new OrdenVentaDetallePromocionSQLiteDao(context);

        ArrayList<OrdenVentaDetallePromocionSQLiteEntity> listaordenVentaDetalleSQLiteEntity=new ArrayList<>();
        ArrayList<DocumentLine> listadoDocumentLines =new ArrayList<>();
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        String AndroidVersion = android.os.Build.VERSION.RELEASE;


        /**OBTIENE TODAS LAS ORDENES DE VENTA CON EL ID**/
        //SOLO DEVOVLERA LA POSICION UNO POR QUE EL ID SOLO PERTENECE A UN OBJETO NO A UNA LISTA DE OBJETOS
        OrdenVentaCabeceraSQLiteEntity ovCabecera= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraporID(ordenventa_id).get(0);
        /**FIN**/

        String agenciaruc="", agenciadir="", agencianombre="", agenciacode="",U_SYP_MDMT="",U_SYP_TVENTA="",U_SYP_VIST_TG="";


        if(ovCabecera.getTerminopago_id().equals("47"))
        {
            U_SYP_MDMT="08";
            U_SYP_TVENTA="3";
            U_SYP_VIST_TG="Y";
        }
        else
        {
            U_SYP_MDMT=ovCabecera.getU_SYP_MDMT();
            U_SYP_TVENTA="1";
            U_SYP_VIST_TG="N";
        }

        DocumentHeader documentHeader=new DocumentHeader();
        Gson gson=new Gson();
        DocumentLine documentLine;

        documentHeader.setCardCode(ovCabecera.getCliente_id());
        documentHeader.setComments(ovCabecera.getComentario());
        documentHeader.setDocCurrency(ovCabecera.getMoneda_id());
        documentHeader.setDocDate(Convertirfechahoraafechanumerica(ovCabecera.getFecharegistro()));
        documentHeader.setDocDueDate(ovCabecera.getDispatchdate());
        documentHeader.setDocType(ovCabecera.getDocType());
        documentHeader.setU_VIS_SalesOrderID(ovCabecera.getOrdenventa_id());
        documentHeader.setDocumentsOwner(SesionEntity.documentsowner);
        documentHeader.setFederalTaxID(ovCabecera.getRucdni());
        documentHeader.setPaymentGroupCode(ovCabecera.getTerminopago_id());
        documentHeader.setSalesPersonCode(SesionEntity.fuerzatrabajo_id);
        documentHeader.setBranch(SesionEntity.U_VIST_SUCUSU);
        documentHeader.setPayToCode(ovCabecera.getDomfactura_id());
        documentHeader.setShipToCode(ovCabecera.getDomembarque_id());
        documentHeader.setU_VIS_AgencyCode(ovCabecera.getAgencia_id());
        documentHeader.setU_VIS_AgencyRUC(ovCabecera.getU_VIS_AgencyRUC());
        documentHeader.setU_VIS_AgencyName(ovCabecera.getU_VIS_AgencyName());
        documentHeader.setU_VIS_AgencyDir(ovCabecera.getU_VIS_AgencyDir());
        documentHeader.setAppVersion(Utilitario.getVersion(context));
        documentHeader.setQuotation(ovCabecera.getQuotation());
        documentHeader.setTaxDate(Convertirfechahoraafechanumerica(ovCabecera.getFecharegistro()));
        documentHeader.setDocRate(ovCabecera.getTipocambio());
        documentHeader.setU_SYP_MDMT(U_SYP_MDMT);
        documentHeader.setU_SYP_TVENTA(U_SYP_TVENTA);
        documentHeader.setU_SYP_FEEST("PE");
        documentHeader.setU_SYP_FETO("01");
        documentHeader.setU_SYP_FEMEX("1");
        documentHeader.setU_SYP_VIST_TG(U_SYP_VIST_TG);
        documentHeader.setU_SYP_DOCEXPORT("N");
        documentHeader.setIntent(ovCabecera.getIntent());
        documentHeader.setBrand(fabricante);
        documentHeader.setOSVersion(AndroidVersion);
        documentHeader.setModel(modelo);
        documentHeader.setU_VIS_DiscountPercent(ovCabecera.getU_VIS_DiscountPercent());
        documentHeader.setU_VIS_ReasonDiscountPercent(ovCabecera.getU_VIS_ReasonDiscountPercent());
        documentHeader.setU_VIST_SUCUSU(SesionEntity.U_VIST_SUCUSU);
        documentHeader.setU_VIS_MOTAPLDESC(ovCabecera.getU_VIS_MOTAPLDESC());

        ///////////////////////////FLAG PARA ENVIAR LA OV POR EL FLUJO DE  APROBACIÓN O NO//////
        ///ALTO RIESGO ASUMIDO/////////

            /*if(ovCabecera.getExcede_lineacredito().equals("1")){ //NO CUMPLE CON LA VALIDACIÓN DE EXCEDIO LA LINEA DE CREDITO
                documentHeader.setApCredit("Y");
            }else{*/
                documentHeader.setApCredit("N");
            //}

            /*if(Integer.parseInt(ovCabecera.getDueDays())>5){ //NO CUMPLE CON LA VALIDACIÓN DE DOCUMENTOS VENCIDOS
                documentHeader.setApDues("Y");
            }else{*/
                documentHeader.setApDues("N");
            //}

            /*ecuador no tiene modelado aprovaciones por descuento*/
            //if(Integer.parseInt(ovCabecera.getDueDays())>5){ //SE AGREGO UN DESCUENTO FUERA DE LO PERMITIDO
            //    documentHeader.setApPrcnt("Y");
            //}else{
                documentHeader.setApPrcnt("N");
            //}

           // if(documentHeader.getPaymentGroupCode().equals(ClienteSQlite.getPaymentGroupCode(documentHeader.getCardCode()))){
                documentHeader.setApTPag("N");
            /*}else{
                documentHeader.setApTPag("Y");
            }*/




        ////////////////////////////////////////////////////////////////////////////////////////////

        listaordenVentaDetalleSQLiteEntity=ordenVentaDetallePromocionSQLiteDao.ObtenerOrdenVentaDetallePromocionporID(ordenventa_id);
        for(int j=0;j<listaordenVentaDetalleSQLiteEntity.size();j++){
            String COGSAccountCode="",U_SYP_FECAT_07="",taxOnly="",taxCode="",U_VIST_CTAINGDCTO="",montolineatotal="",PercentDescnt="0";

           //Casuistica Bonificacion
            if(listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento().equals("100"))
            {
                /*COGSAccountCode="659420";
                U_SYP_FECAT_07="31";
                taxOnly="Y";
                taxCode="EXE_IGV";
                U_VIST_CTAINGDCTO="741111";*/

                montolineatotal=listaordenVentaDetalleSQLiteEntity.get(j).getMontosubtotal();
                PercentDescnt="100";
                //montolineatotal=listaordenVentaDetalleSQLiteEntity.get(j).getMontosubtotalcondescuento();
            }
            //Casustica Descuento
            /*else if(Float.parseFloat(listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento())>0&&
                    Float.parseFloat(listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento())<100)
            {
                COGSAccountCode="";
                U_SYP_FECAT_07="10";
                taxOnly="N";
                taxCode="R3";
                U_VIST_CTAINGDCTO="741113";
                montolineatotal=listaordenVentaDetalleSQLiteEntity.get(j).getMontosubtotalcondescuento();
                PercentDescnt=listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento();
            }
            //Casuistica Transferencia Gratuita
            else if(ovCabecera.getTerminopago_id().equals("47"))
            {
                COGSAccountCode="659419";
                U_SYP_FECAT_07="11";
                taxOnly="Y";
                taxCode="R3";
                U_VIST_CTAINGDCTO="";
                montolineatotal=listaordenVentaDetalleSQLiteEntity.get(j).getMontosubtotal();
                PercentDescnt=listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento();
            }*/
            //Casuistica Venta
            else
            {
                U_SYP_FECAT_07="10";
                COGSAccountCode="";
                taxOnly="N";
                taxCode="R3";
                U_VIST_CTAINGDCTO="";
                montolineatotal=listaordenVentaDetalleSQLiteEntity.get(j).getMontosubtotalcondescuento();
                PercentDescnt=listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento();
            }

            //Casuistica Venta
            //taxOnly="N";
            //taxCode=Induvis.getTaxCodeString();
            documentLine =new DocumentLine();
            documentLine.setCostingCode( SesionEntity.UnidadNegocio);
            documentLine.setCostingCode2(SesionEntity.CentroCosto);
            documentLine.setCostingCode3(SesionEntity.LineaProduccion);
            //el vendedor puede desde 0 a 99.9%, de 0 a 5% todo ok en adelnate mostrar alerta
            //documentLine.setDiscountPercent(listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento());
            documentLine.setDiscountPercent(PercentDescnt);
            documentLine.setDscription(ObtenerProductoDescripcion(listaordenVentaDetalleSQLiteEntity.get(j).getProducto_id(),context));
            documentLine.setItemCode(listaordenVentaDetalleSQLiteEntity.get(j).getProducto_id());
            documentLine.setPrice(listaordenVentaDetalleSQLiteEntity.get(j).getPreciounitario());
            documentLine.setQuantity(listaordenVentaDetalleSQLiteEntity.get(j).getCantidad());
            documentLine.setTaxCode(taxCode);
            documentLine.setTaxOnly(taxOnly);
            documentLine.setWarehouseCode(listaordenVentaDetalleSQLiteEntity.get(j).getAlmacen_id());
            documentLine.setLineTotal(montolineatotal);
            documentLine.setAcctCode("");
            documentLine.setU_SYP_FECAT07(U_SYP_FECAT_07);
            documentLine.setU_VIS_PromID(listaordenVentaDetalleSQLiteEntity.get(j).getPromocion_id());
            documentLine.setU_VIS_PromLineID(listaordenVentaDetalleSQLiteEntity.get(j).getLineareferencia());
            documentLine.setU_VIST_CTAINGDCTO(U_VIST_CTAINGDCTO);
            documentLine.setU_VIS_CommentText("0");
            documentLine.setCOGSAccountCode(COGSAccountCode);
            listadoDocumentLines.add(documentLine);
        }
        //Ingreso como borrador
        documentHeader.setDraft("Y");

        documentHeader.setDocumentLines(listadoDocumentLines);

        cadenaJSON=gson.toJson(documentHeader);
        return cadenaJSON;
    }


    public static String Convertirfechahoraafechanumerica(String fecha)
    {
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechacadena=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {

            fechacadena = formato.format(df.parse(fecha));
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
        return fechacadena;
    }

    public static String ObtenerProductoDescripcion(String producto_id,Context context)
    {
        String producto="";
        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(context);
        ArrayList<ListaPrecioDetalleSQLiteEntity> arraylistapreciodetalleentity=new ArrayList<>();
        arraylistapreciodetalleentity=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(producto_id,SesionEntity.TipoListaPrecio);
        for(int i=0;i<arraylistapreciodetalleentity.size();i++)
        {
            producto=arraylistapreciodetalleentity.get(i).getProducto();
        }

        return producto;
    }


    public static String ObtenerFechaHoraCadena()
    {
        String ID="";
        GregorianCalendar calendario = new GregorianCalendar();
        String year,mes,dia,hora,min,segs;
        try {
            year = String.valueOf(calendario.get(calendario.YEAR));
            mes = String.valueOf(calendario.get(calendario.MONTH)+1);
            dia = String.valueOf(calendario.get(calendario.DAY_OF_MONTH));
            hora = String.valueOf(calendario.get(calendario.HOUR_OF_DAY));
            min = String.valueOf(calendario.get(calendario.MINUTE));
            segs = String.valueOf(calendario.get(calendario.SECOND));

            if (mes.length() == 1) {
                mes = '0' + mes;
            }
            if (dia.length() == 1) {
                dia = '0' + dia;
            }
            ID = year + mes + dia + hora + min + segs+getRandomNumberString(2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return ID;
    }

    private static String getRandomNumberString(int maxLeght) {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(maxLeght);

        // this will convert any number sequence into 6 character.
        return String.format("%02d", number);
    }

    public static String ObtenerFechaCadena(String Fecha)
    {
        String ID="";
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechacadena=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String year,mes,dia,hora,min,segs;
        try {
            GregorianCalendar calendario = new GregorianCalendar();
            calendario.setTime(df.parse(Fecha));
            year = String.valueOf(calendario.get(calendario.YEAR));
            mes = String.valueOf(calendario.get(calendario.MONTH));
            dia = String.valueOf(calendario.get(calendario.DAY_OF_MONTH));
            hora = String.valueOf(calendario.get(calendario.HOUR_OF_DAY));
            min = String.valueOf(calendario.get(calendario.MINUTE));
            segs = String.valueOf(calendario.get(calendario.SECOND));

            mes=String.valueOf(Integer.parseInt(mes)+1) ;
            if (mes.length() == 1) {
                mes = '0' + mes;
            }
            if (dia.length() == 1) {
                dia = '0' + dia;
            }
            ID = year + mes + dia;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ID;
    }

    public boolean ValidarOrdenVentaIDSQLite (Context context,String OrdenVenta_ID)
    {
        boolean estado=false;
        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(context);
        OrdenVentaDetalleSQLiteDao ordenVentaDetalleSQLiteDao=new OrdenVentaDetalleSQLiteDao(context);

        try {
            if(ordenVentaCabeceraSQLite.ObtenerCantidadOrdenVentaCabeceraPorOrdenVentaID(OrdenVenta_ID)&&ordenVentaDetalleSQLiteDao.ObtenerCantidadOrdenVentaDetallePorOrdenVentaID(OrdenVenta_ID))
            {
                estado=true;
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            estado=false;
        }

        return estado;
    }

    public ArrayList<OrdenVentaCabeceraSQLiteEntity> ObtenerOrdenVentaCaberaSQLiteporID (Context context,String OrdenVenta_ID)
    {
        try {
            return new HiloObtenerVentaCabecera(context,OrdenVenta_ID).execute().get();
            //return new getProfilesAsyncTask(userDao).execute().get();
        } catch (Exception e) {
            Log.e("JPCM",""+e);
            e.printStackTrace();
            return null;
        }
    }

    private class HiloObtenerVentaCabecera extends AsyncTask<String, Void, ArrayList<OrdenVentaCabeceraSQLiteEntity> > {
        Context context;
        String ordenventa_id;

        private HiloObtenerVentaCabecera(Context context,String ordenventa_id)
        {
            this.context=context;
            this.ordenventa_id=ordenventa_id;
        }

        @Override
        public final ArrayList<OrdenVentaCabeceraSQLiteEntity>  doInBackground(String... arg0) {

            ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabecera=new ArrayList<>();
            OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(context);
            try {
                listaOrdenVentaCabecera= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraporID(ordenventa_id);
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return listaOrdenVentaCabecera;
        }
    }

    public ArrayList<OrdenVentaDetalleSQLiteEntity> ObtenerOrdenVentaDetalleSQLiteporID (Context context,String OrdenVenta_ID)
    {
        try {
            return new HiloObtenerVentaDetalle(context,OrdenVenta_ID).execute().get();
            //return new getProfilesAsyncTask(userDao).execute().get();
        } catch (Exception e) {
            Log.e("JPCM",""+e);
            e.printStackTrace();
            return null;
        }
    }

    private class HiloObtenerVentaDetalle extends AsyncTask<String, Void, ArrayList<OrdenVentaDetalleSQLiteEntity> > {
        Context context;
        String ordenventa_id;

        private HiloObtenerVentaDetalle(Context context,String ordenventa_id)
        {
            this.context=context;
            this.ordenventa_id=ordenventa_id;
        }

        @Override
        public final ArrayList<OrdenVentaDetalleSQLiteEntity>  doInBackground(String... arg0) {

            ArrayList<OrdenVentaDetalleSQLiteEntity> listaOrdenVentaDetalle=new ArrayList<>();
            OrdenVentaDetalleSQLiteDao ordenVentaDetalleSQLiteDao=new OrdenVentaDetalleSQLiteDao(context);
            try {
                listaOrdenVentaDetalle=ordenVentaDetalleSQLiteDao.ObtenerOrdenVentaDetalleporID(ordenventa_id);
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return listaOrdenVentaDetalle;
        }
    }


    public  ArrayList<ListaOrdenVentaDetalleEntity> ConvertirOrdenVentaDetalleSQLiteEntityenListaOrdenVentaDetalle (ArrayList<OrdenVentaDetalleSQLiteEntity> ListaSQlite,Context context)
    {
        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalle=new ArrayList<>();
        ListaOrdenVentaDetalleEntity listaOrdenVentaDetalle=new ListaOrdenVentaDetalleEntity();


        for(int i=0;i<ListaSQlite.size();i++)
        {

                listaOrdenVentaDetalle.orden_detalle_item = ListaSQlite.get(i).getLineaordenventa_id();
                listaOrdenVentaDetalle.orden_detalle_producto_id = ListaSQlite.get(i).getProducto_id();
                listaOrdenVentaDetalle.orden_detalle_producto = ListaSQlite.get(i).getProducto();
                listaOrdenVentaDetalle.orden_detalle_umd = ListaSQlite.get(i).getUmd();
                listaOrdenVentaDetalle.orden_detalle_stock_almacen = "";
                listaOrdenVentaDetalle.orden_detalle_cantidad = ListaSQlite.get(i).getCantidad();
                listaOrdenVentaDetalle.orden_detalle_precio_unitario = ListaSQlite.get(i).getPreciounitario();
                listaOrdenVentaDetalle.orden_detalle_montosubtotal = ListaSQlite.get(i).getMontosubtotal();
                listaOrdenVentaDetalle.orden_detalle_porcentaje_descuento = ListaSQlite.get(i).getPorcentajedescuento();
                listaOrdenVentaDetalle.orden_detalle_monto_descuento = ListaSQlite.get(i).getMontodescuento();
                listaOrdenVentaDetalle.orden_detalle_monto_igv = ListaSQlite.get(i).getMontoimpuesto();
                listaOrdenVentaDetalle.orden_detalle_montototallinea = ListaSQlite.get(i).getMontototallinea();

                if(ListaSQlite.get(i).getPorcentajedescuento().equals("0"))
                {
                    listaOrdenVentaDetalle.orden_detalle_lista_promocion_cabecera=null;
                }
                else
                    {

                    }
                listaOrdenVentaDetalle.orden_detalle_promocion_habilitada="0";


        }

        return ListaOrdenVentaDetalle;
    }


    public int RegistrarRutaVendedor(ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntities,String fecharuta,String chk_ruta)
    {
        int resultado=0;
        RutaVendedorSQLiteDao rutaVendedorSQLiteDao=new RutaVendedorSQLiteDao(Context);
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
        Log.e("REOS","FormulasController.RegistrarRutaVendedor.listaClienteCabeceraEntities.size(): "+listaClienteCabeceraEntities.size());
        Log.e("REOS","FormulasController.RegistrarRutaVendedor.fecharuta: "+fecharuta);
        Log.e("REOS","FormulasController.RegistrarRutaVendedor.chk_ruta: "+chk_ruta);
        try
        {
            for (int i=0;i<listaClienteCabeceraEntities.size();i++){

                resultado=rutaVendedorSQLiteDao.InsertaRutaVendedor(
                        listaClienteCabeceraEntities.get(i).getCliente_id(),
                        ObjUsuario.compania_id,
                        listaClienteCabeceraEntities.get(i).getNombrecliente(),
                        listaClienteCabeceraEntities.get(i).getDomembarque_id(),
                        listaClienteCabeceraEntities.get(i).getDomfactura_id(),
                        listaClienteCabeceraEntities.get(i).getDireccion(),
                        listaClienteCabeceraEntities.get(i).getZona_id(),
                        listaClienteCabeceraEntities.get(i).getOrdenvisita(),
                        listaClienteCabeceraEntities.get(i).getZona(),
                        listaClienteCabeceraEntities.get(i).getRucdni(),
                        listaClienteCabeceraEntities.get(i).getMoneda(),
                        listaClienteCabeceraEntities.get(i).getTelefonofijo(),
                        listaClienteCabeceraEntities.get(i).getTelefonomovil(),
                        listaClienteCabeceraEntities.get(i).getCorreo(),
                        listaClienteCabeceraEntities.get(i).getUbigeo_id(),
                        listaClienteCabeceraEntities.get(i).getImpuesto_id(),
                        listaClienteCabeceraEntities.get(i).getImpuesto(),
                        listaClienteCabeceraEntities.get(i).getTipocambio(),
                        listaClienteCabeceraEntities.get(i).getCategoria(),
                        listaClienteCabeceraEntities.get(i).getLinea_credito(),
                        listaClienteCabeceraEntities.get(i).getTerminopago_id(),
                        listaClienteCabeceraEntities.get(i).getSaldo(),
                        "0",
                        "0",
                        "0",
                        chk_ruta,
                        fecharuta,
                        ObjUsuario.fuerzatrabajo_id,
                        ObjUsuario.usuario_id,
                        listaClienteCabeceraEntities.get(i).getLastpurchase(),
                        listaClienteCabeceraEntities.get(i).getSaldosincontados(),
                        listaClienteCabeceraEntities.get(i).getChkgeolocation(),
                        listaClienteCabeceraEntities.get(i).getChkvisitsection(),
                        listaClienteCabeceraEntities.get(i).getTerminopago(),
                        listaClienteCabeceraEntities.get(i).getContado()
                        ,listaClienteCabeceraEntities.get(i).getLatitud()
                        ,listaClienteCabeceraEntities.get(i).getLongitud()
                        ,listaClienteCabeceraEntities.get(i).getAddresscode()
                        , listaClienteCabeceraEntities.get(i).getStatuscount()
                        , listaClienteCabeceraEntities.get(i).getAmountQuotation()
                        , listaClienteCabeceraEntities.get(i).getChk_quotation()
                        , listaClienteCabeceraEntities.get(i).getTypeVisit()
                        , listaClienteCabeceraEntities.get(i).getCustomerwhitelist()
                );
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            Log.e("REOS",String.valueOf(e));
            resultado=0;
        }

        return resultado;
    }

    public void RegistraVisita(VisitaSQLiteEntity visita, Context context,String monto) {
        Log.e("REOS","FormulasController-RegistraVisita-monto:"+monto);
        visitaRepository = new ViewModelProvider((ViewModelStoreOwner) context).get(VisitaRepository.class);

        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
        SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Date date = new Date();

        visita.setCompania_id(ObjUsuario.compania_id);
        visita.setDate(FormatFecha.format(date));
        visita.setHour(dateFormathora.format(date));
        visita.setSlpCode(ObjUsuario.fuerzatrabajo_id);
        visita.setUserId(ObjUsuario.usuario_id);
        visita.setChkenviado("1");
        visita.setChkrecibido("0");
        VisitaSQLite visitaSQLite = new VisitaSQLite(context);
        visita.setHour_Before(visitaSQLite.getHourAfter(FormatFecha.format(date)));

        visitaSQLite.InsertaVisita(visita);

        RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(context);
        Log.e("REOS","FormulasController-RegistraVisita-visita.getCardCode():"+visita.getCardCode());
        Log.e("REOS","FormulasController-RegistraVisita-visita.getAddress():"+visita.getAddress());
        switch(visita.getType()){
            case "01":
                rutaVendedorSQLiteDao.ActualizaChkPedidoRutaVendedor(
                        visita.getCardCode(),
                        visita.getAddress(),
                        ObjUsuario.compania_id,
                        String.valueOf(FormatFecha.format(date)),
                        monto
                );
                break;
            case "02":
                rutaVendedorSQLiteDao.ActualizaChkCobranzaRutaVendedor(
                        visita.getCardCode(),
                        visita.getAddress(),
                        ObjUsuario.compania_id,
                        String.valueOf(FormatFecha.format(date)),
                        monto
                );
                break;
            default:
                rutaVendedorSQLiteDao.ActualizaVisitaRutaVendedor(
                        visita.getCardCode(),
                        visita.getAddress(),
                        ObjUsuario.compania_id,
                        String.valueOf(FormatFecha.format(date))
                );
                break;
        }

        /*********************************/
        /********************************* REENVIO DE VISITAS *********************************/
        /*********************************/
        visitaRepository.visitResend(context).observe((LifecycleOwner) context, data -> {
            Log.e("Jepicame", "=>" + data);
        });


        /*********************************/
        /*********************************/
        /*********************************/
    }

    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerListaConvertidaCobranzaDetalleSQLite (Context context,String Imei,String Compania_id,String usuario_id,String Recibo) {
        ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity=new ArrayList<>();
        ArrayList<ListaHistoricoCobranzaEntity> ListaHistoricoCobranzaEntity=new ArrayList<>();
        HistoricoCobranzaUnidadWS historicoCobranzaUnidadWS=new HistoricoCobranzaUnidadWS(context);
        try {
            ListaHistoricoCobranzaEntity=historicoCobranzaUnidadWS.getHistoricoCobranzaIndividual(
                    Imei,Compania_id,usuario_id,Recibo
            );
            listaCobranzaDetalleSQLiteEntity=ConversionCobranzaDetalleSQLiteEntity(ListaHistoricoCobranzaEntity);
        } catch (Exception e){
            e.printStackTrace();
        }
            return listaCobranzaDetalleSQLiteEntity;
    }


    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerListaConvertidaHistoricoCobranza (
            Context context,
            String Imei,
            String Compania_id,
            String Banco_ID,
            String Deposito_ID,
            String TipoFecha,
            String Fecha,
            String FuerzaTrabajo_ID,
            String TipoIngreso
            )
    {
        ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity=new ArrayList<>();
        ArrayList<ListaHistoricoCobranzaEntity> ListaHistoricoCobranzaEntity=new ArrayList<>();
        HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(context);
        try {
            ListaHistoricoCobranzaEntity = historicoCobranzaWS.getHistoricoCobranza
                    (
                            Imei,
                            //Compania_id,
                            //Banco_ID,
                            //Deposito_ID,
                            Fecha,
                            TipoFecha
                            //FuerzaTrabajo_ID,
                            //TipoIngreso
                    );
            listaCobranzaDetalleSQLiteEntity=ConversionCobranzaDetalleSQLiteEntity(ListaHistoricoCobranzaEntity);
        } catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return listaCobranzaDetalleSQLiteEntity;
    }
    public ArrayList<CobranzaDetalleSQLiteEntity> ConversionCobranzaDetalleSQLiteEntity(ArrayList<ListaHistoricoCobranzaEntity> Lista)
    {
        ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity=new ArrayList<>();
        CobranzaDetalleSQLiteEntity cobranzaDetalleSQLiteEntity;

        for(int i=0;i<Lista.size();i++)
        {
            cobranzaDetalleSQLiteEntity=new CobranzaDetalleSQLiteEntity();
            cobranzaDetalleSQLiteEntity.id=Lista.get(i).getDetalle_item();
            cobranzaDetalleSQLiteEntity.cobranza_id=Lista.get(i).getDeposito_id();
            cobranzaDetalleSQLiteEntity.cliente_id=Lista.get(i).getCliente_id();
            cobranzaDetalleSQLiteEntity.documento_id=Lista.get(i).getDocumento_id();
            cobranzaDetalleSQLiteEntity.compania_id=Lista.get(i).getCompania_id();
            cobranzaDetalleSQLiteEntity.importedocumento=Lista.get(i).getImportedocumento();
            cobranzaDetalleSQLiteEntity.saldodocumento=Lista.get(i).getSaldodocumento();
            cobranzaDetalleSQLiteEntity.nuevosaldodocumento=Lista.get(i).getNuevosaldodocumento();
            cobranzaDetalleSQLiteEntity.saldocobrado=Lista.get(i).getMontocobrado();
            cobranzaDetalleSQLiteEntity.fechacobranza=Lista.get(i).getFechacobranza();
            cobranzaDetalleSQLiteEntity.recibo=Lista.get(i).getRecibo();
            cobranzaDetalleSQLiteEntity.nrofactura=Lista.get(i).getNro_documento();
            cobranzaDetalleSQLiteEntity.chkqrvalidado=Lista.get(i).getEstadoqr();
            cobranzaDetalleSQLiteEntity.chkbancarizado=Lista.get(i).getBancarizacion();
            cobranzaDetalleSQLiteEntity.motivoanulacion=Lista.get(i).getMotivoanulacion();
            cobranzaDetalleSQLiteEntity.usuario_id=Lista.get(i).getUsuario_id();
            cobranzaDetalleSQLiteEntity.chkwsrecibido="1";
            cobranzaDetalleSQLiteEntity.banco_id=Lista.get(i).getBanco_id();
            if (Lista.get(i).getDeposito_id().equals("1")) {
                cobranzaDetalleSQLiteEntity.chkwsdepositorecibido="0";
                cobranzaDetalleSQLiteEntity.chkdepositado="0";
            }else
            {
                cobranzaDetalleSQLiteEntity.chkwsdepositorecibido="1";
                cobranzaDetalleSQLiteEntity.chkdepositado="1";
            }
            //cobranzaDetalleSQLiteEntity.chkwsdepositorecibido=Lista.get(i).getDetalle_item();
            cobranzaDetalleSQLiteEntity.comentario=Lista.get(i).getComentario();
            cobranzaDetalleSQLiteEntity.chkwsupdate="0";
            //cobranzaDetalleSQLiteEntity.chkdepositado=Lista.get(i).getDetalle_item();
            cobranzaDetalleSQLiteEntity.chkwsqrvalidado=Lista.get(i).getEstadoqr();
            if (Lista.get(i).getEstado().equals("Anulado")) {
                cobranzaDetalleSQLiteEntity.chkanulado="1";
            }else
            {
                cobranzaDetalleSQLiteEntity.chkanulado="0";
            }
            //cobranzaDetalleSQLiteEntity.chkanulado=Lista.get(i).getDetalle_item();
            cobranzaDetalleSQLiteEntity.pagodirecto=Lista.get(i).getDepositodirecto();
            cobranzaDetalleSQLiteEntity.pagopos=Lista.get(i).getPagopos();

            listaCobranzaDetalleSQLiteEntity.add(cobranzaDetalleSQLiteEntity);
        }
        return listaCobranzaDetalleSQLiteEntity;
    }

    public ArrayList<ListaOrdenVentaDetalleEntity> ConvertirListaOrdenVentaDetalleEntity(ArrayList<OrdenVentaDetalleSQLiteEntity> ListaRecibida){

        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalleEntity=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();

        for(int i=0;i<ListaRecibida.size();i++){
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=ListaRecibida.get(i).getLineaordenventa_id();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=ListaRecibida.get(i).getProducto_id();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=ListaRecibida.get(i).getProducto();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=ListaRecibida.get(i).getUmd();
            //ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock=ListaRecibida.get(i).get
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=ListaRecibida.get(i).getCantidad();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=ListaRecibida.get(i).getPreciounitario();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=ListaRecibida.get(i).getMontosubtotal();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=ListaRecibida.get(i).getPorcentajedescuento();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=ListaRecibida.get(i).getMontodescuento();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=ListaRecibida.get(i).getMontoimpuesto();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea=ListaRecibida.get(i).getMontototallinea();
            //ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera==ListaRecibida.get(i).get
            //ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada;
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal=ListaRecibida.get(i).getGal_unitario();
            ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado=ListaRecibida.get(i).getGal_acumulado();
            ListaOrdenVentaDetalleEntity.add(ObjlistaOrdenVentaDetalleEntity);
        }

        return ListaOrdenVentaDetalleEntity;
    }

    public int ObtenerCantidadLineasListaOrdenVentaDetalleEntity(ArrayList<ListaOrdenVentaDetalleEntity> ListaRecibida)
    {
        int cantidadlineas=0;
        cantidadlineas=ListaRecibida.size();
        for(int i=0;i<ListaRecibida.size();i++)
        {
            if(ListaRecibida.get(i).getOrden_detalle_lista_promocion_cabecera()!=null)
            {
                for(int j=0;j<ListaRecibida.get(i).getOrden_detalle_lista_promocion_cabecera().size();j++)
                {
                    cantidadlineas=cantidadlineas+ListaRecibida.get(i).getOrden_detalle_lista_promocion_cabecera().get(j).getListaPromocionDetalleEntities().size();
                }
            }
        }
        return cantidadlineas;
    }

    public int ObtenerCantidadLineasPromocionDetalle(ArrayList<ListaPromocionCabeceraEntity> listapromocioncabeceraentity, int Position)
    {
        int cantidadlineas=0;

            for(int j=0;j<listapromocioncabeceraentity.get(Position).getListaPromocionDetalleEntities().size();j++)
            {
                if(!listapromocioncabeceraentity.get(Position).getListaPromocionDetalleEntities().get(j).getUmd().equals("%"))
                {
                    //Log.e("REOS","FoumlasController-listapromocioncabeceraentity.get(i).getListaPromocionDetalleEntities().get(j).getProducto_id"+ listapromocioncabeceraentity.get(Position).getListaPromocionDetalleEntities().get(j).getProducto_id());
                    //Log.e("REOS","FoumlasController-listapromocioncabeceraentity.get(i).getListaPromocionDetalleEntities().get(j).getProducto"+ listapromocioncabeceraentity.get(Position).getListaPromocionDetalleEntities().get(j).getProducto());
                    //Log.e("REOS","FoumlasController-listapromocioncabeceraentity.get(i).getListaPromocionDetalleEntities().get(j).getUMD"+ listapromocioncabeceraentity.get(Position).getListaPromocionDetalleEntities().get(j).getUmd());
                    cantidadlineas++;
                }
            }
        cantidadlineas=Integer.parseInt(listapromocioncabeceraentity.get(Position).getCantidadpromocion())*cantidadlineas;
        //Log.e("REOS","FoumlasController-ObtenerCantidadLineasPromocionDetalle-cantidadlineas:"+String.valueOf(cantidadlineas));

        return cantidadlineas;
    }

    public ArrayList<ListaOrdenVentaDetalleEntity> ConversionListaOrdenDetallepoListaOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity)
    {
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntitycopia=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjlistaOrdenVentaDetalleEntity;

        //Inicia Bucle

        if(listaOrdenVentaDetalleEntity!=null){
            for(int a=0;a<listaOrdenVentaDetalleEntity.size();a++)
            {
                Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-a"+String.valueOf(a));
                //Evalua si Promocion es diferente a vacia
                if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null)
                {
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null-true");
                    //Inicia Bucle que recorre la Lista de Promociones
                    for(int b=0;b<listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().size();b++)
                    {
                        ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_item();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto_id();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_umd();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock_almacen=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_stock();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_cantidad();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_precio_unitario();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotal();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea =listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montototallinea();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_lista_promocion_cabecera();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_promocion_habilitada();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal_acumulado();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotalcondescuento = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_orden_detalle_promocion = null;
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_descuentocontado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_chk_descuentocontado_aplicado = listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado_aplicado();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_oil_tax = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_oil_tax();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_liter = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_liter();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_SIGAUS = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_SIGAUS();
                        listaOrdenVentaDetalleEntitycopia.add(ObjlistaOrdenVentaDetalleEntity);
                    }
                }
            }
        }



        return listaOrdenVentaDetalleEntitycopia;
    }

    /*
    public ArrayList<ListaOrdenVentaDetalleEntity> ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion
            (ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity)
    {
        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-iniciaMetodo");
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetallePromocionEntity> listaOrdenVentaDetalleEntitycopia=new ArrayList<>();
        ListaOrdenVentaDetallePromocionEntity ObjlistaOrdenVentaDetalleEntity;
        //ListaOrdenVentaDetallePromocionEntity ObjlistaOrdenVentaDetallePromocionEntity;
        //Inicia Bucle
        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.size(): "+listaOrdenVentaDetalleEntity.size());
        for(int a=0;a<listaOrdenVentaDetalleEntity.size();a++)
        {
            listaOrdenVentaDetalleEntitycopia=new ArrayList<>();
            Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-a"+String.valueOf(a));
            //Evalua si Promocion es diferente a vacia
            if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null)
            {
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null-true");
                //Inicia Bucle que recorre la Lista de Promociones
                for(int b=0;b<listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().size();b++)
                {
                    boolean chk_descuentocontadoaplicado=true;
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-b"+String.valueOf(b));
                    String contadodescuento="0";


                    if(Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado())>0&&
                            !listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento().equals("100")
                            &&listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado()&&(!listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                    )
                    {
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                        contadodescuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                        //chk_descuentocontadoaplicado=true;

                    }
                    else
                    {
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                        contadodescuento="0";
                        //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                        //{
                            Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones-2dobuclecumplecondiciones");
                            //chk_descuentocontadoaplicado=true;
                        //}

                    }
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-chk_descuentocontadoaplicado"+String.valueOf(chk_descuentocontadoaplicado));
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado()"+String.valueOf(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado()));
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-contadodescuento: "+contadodescuento);
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado());
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado());
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento());
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado());
                    ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetallePromocionEntity();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_item();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto_id();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_umd();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_stock();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_cantidad();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_precio_unitario();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotal();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=String.valueOf(
                            Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento())+
                                    Integer.parseInt(contadodescuento));
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                    //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=
                            applyDiscountPercentageForLine(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                    //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento());
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=
                            CalcularMontoImpuestoPorLinea(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                    SesionEntity.Impuesto
                            );
                    //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-SesionEntity.Impuesto: "+SesionEntity.Impuesto);
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea =
                            CalcularMontoTotalporLinea(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv()
                                    );
                            //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montototallinea();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_lista_promocion_cabecera();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_promocion_habilitada();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal_acumulado();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotalcondescuento =
                            CalcularMontoTotalconDescuento(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento()
                            );
                            //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_orden_detalle_promocion = null;
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_descuentocontado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;


                    listaOrdenVentaDetalleEntitycopia.add(ObjlistaOrdenVentaDetalleEntity);
                }
            }
            else
                {
                    boolean chk_descuentocontadoaplicado=true;

                    String contadodescuento="0";
                    if(Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado())>0&&
                            listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado()&&(
                                    !listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado_aplicado())
                    )
                    {
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                        contadodescuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                        //chk_descuentocontadoaplicado=true;

                    }
                    {
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                        contadodescuento="0";
                        //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                        //{
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones-2dobuclecumplecondiciones");
                        //chk_descuentocontadoaplicado=true;
                        //}
                    }
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null-true");
                    //Inicia Bucle que recorre la Lista de Promociones

                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-chk_descuentocontadoaplicado"+String.valueOf(chk_descuentocontadoaplicado));
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-contadod<escuento: "+contadodescuento);
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado());
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado());
                        ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetallePromocionEntity();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_item();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_producto_id();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_producto();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_umd();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_stock();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_cantidad();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_precio_unitario();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_montosubtotal();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=String.valueOf(
                                Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_porcentaje_descuento())+
                                        Integer.parseInt(contadodescuento));
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                        //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=
                                applyDiscountPercentageForLine(
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                        //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento());
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=
                                CalcularMontoImpuestoPorLinea(
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                        SesionEntity.Impuesto
                                );
                        //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                        Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                        Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-SesionEntity.Impuesto: "+SesionEntity.Impuesto);
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea =
                                CalcularMontoTotalporLinea(
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv()
                                );
                        //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montototallinea();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_promocion_cabecera();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_promocion_habilitada();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_gal();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_gal_acumulado();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotalcondescuento =
                                CalcularMontoTotalconDescuento(
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                        ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento()
                                );
                        //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_orden_detalle_promocion = null;
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_descuentocontado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                        listaOrdenVentaDetalleEntitycopia.add(ObjlistaOrdenVentaDetalleEntity);

                }
            listaOrdenVentaDetalleEntity.get(a).setOrden_detalle_lista_orden_detalle_promocion(listaOrdenVentaDetalleEntitycopia);

        }

        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-FinalizaMetodo");
        return listaOrdenVentaDetalleEntity;
    }*/

    /*
    public ArrayList<ListaOrdenVentaDetalleEntity> ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion
            (ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity)
    {
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntitycopia=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjlistaOrdenVentaDetalleEntity;
        //Inicia Bucle
        for(int a=0;a<listaOrdenVentaDetalleEntity.size();a++)
        {
            ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();
            //listaOrdenVentaDetalleEntitycopia=new ArrayList<>();
            Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-a"+String.valueOf(a));
            //Evalua si Promocion es diferente a vacia
            if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null)
            {

                Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null-true");
                //Inicia Bucle que recorre la Lista de Promociones
                for(int b=0;b<listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().size();b++)
                {
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-b"+String.valueOf(b));
                    String contadodescuento="0";

                   /* if(Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado())>0&&
                            !listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento().equals("100")
                            &&listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado()
                    )
                    {
                        contadodescuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                    }
                    else
                    {
                        contadodescuento="0";
                    }
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-contadodescuento: "+contadodescuento);
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado());
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado());

                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento());
                    ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_item();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto_id();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_umd();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_stock();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_cantidad();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_precio_unitario();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotal();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                            /*String.valueOf(
                            Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento())+
                                    Integer.parseInt(contadodescuento));
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                    //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                          /*  CalcularMontoDescuento(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                    //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento());
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                            /*CalcularMontoImpuestoPorLinea(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                    SesionEntity.Impuesto
                            );
                    //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-SesionEntity.Impuesto: "+SesionEntity.Impuesto);
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea =

                            listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montototallinea();

                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_lista_promocion_cabecera();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_promocion_habilitada();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal_acumulado();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotalcondescuento =listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                            CalcularMontoTotalconDescuento(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento()
                            );
                    //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_orden_detalle_promocion = null;
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_descuentocontado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();

                }
            }
            else
            {
                boolean chk_descuentocontadoaplicado=false;

                String contadodescuento="0";
                if(Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado())>0&&
                        listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado()&&(
                        !listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado_aplicado())
                )
                {
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                    contadodescuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                    chk_descuentocontadoaplicado=true;

                }
                {
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                    contadodescuento="0";
                    //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                    //{
                    Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones-2dobuclecumplecondiciones");
                    chk_descuentocontadoaplicado=true;
                    //}
                }
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion()!=null-true");
                //Inicia Bucle que recorre la Lista de Promociones

                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-chk_descuentocontadoaplicado"+String.valueOf(chk_descuentocontadoaplicado));
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-contadod<escuento: "+contadodescuento);
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado());
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado());
                ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_item();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_producto_id();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_producto();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_umd();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_stock();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_cantidad();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_precio_unitario();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_montosubtotal();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=String.valueOf(
                        Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_porcentaje_descuento())+
                                Integer.parseInt(contadodescuento));
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=
                        applyDiscountPercentageForLine(
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento());
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=
                        CalcularMontoImpuestoPorLinea(
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                SesionEntity.Impuesto
                        );
                //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-SesionEntity.Impuesto: "+SesionEntity.Impuesto);
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea =
                        CalcularMontoTotalporLinea(
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv()
                        );
                //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montototallinea();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_promocion_cabecera();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_promocion_habilitada();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_gal();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_gal_acumulado();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotalcondescuento =
                        CalcularMontoTotalconDescuento(
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento()
                        );
                //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_orden_detalle_promocion = null;
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_descuentocontado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                listaOrdenVentaDetalleEntitycopia.add(ObjlistaOrdenVentaDetalleEntity);

            }
            listaOrdenVentaDetalleEntitycopia.add(ObjlistaOrdenVentaDetalleEntity);
            //listaOrdenVentaDetalleEntity.get(a).setOrden_detalle_lista_orden_detalle_promocion(listaOrdenVentaDetalleEntitycopia);

        }


        return listaOrdenVentaDetalleEntitycopia;
    }*/

    public boolean  obtenerPromociondescuentocontado (String listapromocion_id)
    {
        boolean resultado=false;
        ListaPromocionSQLiteDao listaPromocionSQLiteDao=new ListaPromocionSQLiteDao(Context);
        ListaPromocionSQLiteEntity listaPromocionSQLiteEntity=new ListaPromocionSQLiteEntity();
        ArrayList<ListaPromocionSQLiteEntity> arrayListListaPromocionSQLiteEntity=new ArrayList<>();
        Log.e("REOS","formulascontroller-obtenerPromociondescuentocontado-listapromocion_id-"+listapromocion_id);
        listaPromocionSQLiteEntity=listaPromocionSQLiteDao.ObtenerListaPromocion(SesionEntity.compania_id,listapromocion_id);
        arrayListListaPromocionSQLiteEntity.add(listaPromocionSQLiteEntity);
        Log.e("REOS","formulascontroller-obtenerPromociondescuentocontado-arrayListListaPromocionSQLiteEntity.size()-"+arrayListListaPromocionSQLiteEntity.size());
        if(!arrayListListaPromocionSQLiteEntity.isEmpty())
        {
            Log.e("REOS","formulascontroller-obtenerPromociondescuentocontado-!arrayListListaPromocionSQLiteEntity.isEmpty()-true");
            for(int i=0;i<arrayListListaPromocionSQLiteEntity.size();i++)
            {
                Log.e("REOS","formulascontroller-obtenerPromociondescuentocontado-arrayListListaPromocionSQLiteEntity.get(i).getU_vis_cashdscnt()-"+arrayListListaPromocionSQLiteEntity.get(i).getU_vis_cashdscnt());
                if(arrayListListaPromocionSQLiteEntity.get(i).getU_vis_cashdscnt().equals("Y"))
                   // if(Double.parseDouble (arrayListListaPromocionSQLiteEntity.get(i).getU_vis_cashdscnt())>0)
                {
                    resultado=true;
                }else
                    {
                        resultado=false;
                    }
            }
        }
        else {
            Log.e("REOS","formulascontroller-obtenerPromociondescuentocontado-!arrayListListaPromocionSQLiteEntity.isEmpty()-false");
            resultado=true;
        }
        Log.e("REOS","formulascontroller-obtenerPromociondescuentocontado-resultado-"+resultado);
        return resultado;
    }

    public boolean  obtenerEstadoProductoContadoDescuento (String listapromocion_id)
    {
        boolean resultado=false;
        ArrayList<ListaPrecioDetalleSQLiteEntity> arrayListListaPrecioDetalleSQLiteEntity=new ArrayList<>();
        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(Context);
        arrayListListaPrecioDetalleSQLiteEntity=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(listapromocion_id,SesionEntity.TipoListaPrecio);
        Log.e("REOS", "FormulasController:obtenerEstadoProductoContadoDescuento-SesionEntity.TipoListaPrecio: "+SesionEntity.TipoListaPrecio);
        Log.e("REOS", "FormulasController:obtenerEstadoProductoContadoDescuento-arrayListListaPrecioDetalleSQLiteEntity.size(): "+arrayListListaPrecioDetalleSQLiteEntity.size());
        for(int i=0;i<arrayListListaPrecioDetalleSQLiteEntity.size();i++)
        {
            Log.e("REOS", "FormulasController:obtenerEstadoProductoContadoDescuento-arrayListListaPrecioDetalleSQLiteEntity.get(i).getU_vis_cashdscnt(): "+arrayListListaPrecioDetalleSQLiteEntity.get(i).getU_vis_cashdscnt());
            Log.e("REOS", "FormulasController:obtenerEstadoProductoContadoDescuento-getU_vis_cashdscnt(): "+arrayListListaPrecioDetalleSQLiteEntity.get(i).getU_vis_cashdscnt());
            //if(Double.parseDouble (SesionEntity.U_VIS_CashDscnt )>0)
            if(arrayListListaPrecioDetalleSQLiteEntity.get(i).getU_vis_cashdscnt().equals("N"))
            {
                Log.e("REOS", "FormulasController:obtenerEstadoProductoContadoDescuento-true: ");
                resultado=true;
            }
            else
                {
                    Log.e("REOS", "FormulasController:obtenerEstadoPr        oductoContadoDescuento-false: ");
                    resultado=false;
                }


        }
        Log.e("REOS", "FormulasController:obtenerEstadoProductoContadoDescuento-resultado: "+resultado);
        return resultado;
    }

    public boolean  validadescuentocontado (ListaOrdenVentaDetalleEntity lead)
    {
        boolean resultadofinal=false,resultadoproducto=false,resultadopromocion=false;
        ArrayList<ListaOrdenVentaDetalleEntity> lista=new ArrayList<>();
        lista.add(lead);

        for(int i=0;i<lista.size();i++)
        {
            if(lista.get(i).getOrden_detalle_lista_promocion_cabecera()!=null)
            {
                for(int j=0;j<lista.get(i).getOrden_detalle_lista_promocion_cabecera().size();j++)
                {
                    Log.e("REOS","formulascontroller-validadescuentocontado-lista.get(i).getOrden_detalle_lista_promocion_cabecera().get(j).getLista_promocion_id()"+lista.get(i).getOrden_detalle_lista_promocion_cabecera().get(j).getLista_promocion_id());
                    Log.e("REOS","formulascontroller-validadescuentocontado-lista.get(i).getOrden_detalle_lista_promocion_cabecera().get(j).getPromocion_id()"+lista.get(i).getOrden_detalle_lista_promocion_cabecera().get(j).getPromocion_id());
                    //lista.get(i).getOrden_detalle_lista_promocion_cabecera()
                    resultadopromocion=obtenerPromociondescuentocontado(lista.get(i).getOrden_detalle_lista_promocion_cabecera().get(j).getLista_promocion_id());
                }
                //Log.e("REOS","ListaOrdenVentaDetalleAdapter:holder.chk_descuento_contado.setEnabled(false)");
            }
            else
            {
                resultadopromocion=true;
            }
            resultadoproducto=obtenerEstadoProductoContadoDescuento(lista.get(i).getOrden_detalle_producto_id());
        }
        if(resultadopromocion&&resultadoproducto)
        {
            resultadofinal=true;
        }
        else
        {
            resultadofinal=false;
        }

        Log.e("REOS","formulascontroller-validadescuentocontado-resultadopromocion-"+resultadopromocion);
        Log.e("REOS","formulascontroller-validadescuentocontado-resultadoproducto-"+resultadoproducto);
        Log.e("REOS","formulascontroller-validadescuentocontado-resultadofinal-"+resultadofinal);

        return resultadofinal;
    }

    static public int ObtenerCantidadListaPromocion(ListaOrdenVentaDetalleEntity lead)
    {
        int cantidadLineasPromocion=0;
        if(!OrdenVentaDetalleView.listadoProductosAgregados.isEmpty())
        {
            for (int i = 0; i < OrdenVentaDetalleView.listadoProductosAgregados.size(); i++) {
                if(Integer.parseInt(OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_item())<Integer.parseInt(lead.getOrden_detalle_item()))
                if (OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion()!=null ) {
                    for (int j = 0; j < OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size(); j++) {
                        cantidadLineasPromocion++;
                        Log.e("REOS","FormulasController-ObtenerCantidadListaPromocion-cantidadLineasPromocion-OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size(): "+OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size());
                    }
                }
            }
        }
        Log.e("REOS","FormulasController-ObtenerCantidadListaPromocion-cantidadLineasPromocion-return: "+cantidadLineasPromocion);
        return cantidadLineasPromocion;
    }

    public String  convertirCadenaEnvioSMS (ArrayList<ListaClienteDetalleEntity> Lista, String fuerzatrabajo_id, String nombrefuerzatrabajo, String recibo, String fecharecibo)
    {
        String encRecibo="",encCardCode="",CardCode="",hash="",encHash="",codeSMS="";
        CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(Context);
        ArrayList<CobranzaDetalleSQLiteEntity> lista=new ArrayList<>();
        lista=cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                 recibo,
                 SesionEntity.compania_id,
                 SesionEntity.fuerzatrabajo_id);
        for(int j=0;j<lista.size();j++)
        {
                codeSMS=lista.get(j).getCodeSMS();
        }

        for (int i = 0; i < Lista.size(); i++)
        {
            CardCode=Lista.get(i).getCliente_id();
        }

        hash=CardCode+"-"+recibo;
        try {
            Log.e("REOS","DocumentoCobranzaPDF-generarPdf-EntroalTryCifrado");
            encHash= CifradoController.encrypt("Vistony2019*".getBytes("UTF-16LE"), hash.getBytes("UTF-16LE"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String CadenaSMS="";
        try {
            for (int i = 0; i < Lista.size(); i++) {
                //CadenaSMS="GlaGlaGla2";
                CadenaSMS = "VISTONY\n " +
                        "N.I.F: B45783081 OFICINA: C/Rafael Alberti, 30 Pol. Industrial Fuente Techada ORGAZ  -\n" +
                        "(Toledo) - Apdo. Correos 30, 45100 Sonseca \n" +
                        "Telf: 925 910 177 \n"+
                        Lista.get(i).getCliente_id() + "\n"  +
                        Lista.get(i).getNombrecliente() + "\n" +
                        "*********************DATOS COBRADOR*******************\n"  +
                        fuerzatrabajo_id + " " + nombrefuerzatrabajo + "\n" +
                        "*********************DATOS DOCUMENTO******************" + "\n" +
                        "Fecha:     " + Induvis.getDate(BuildConfig.FLAVOR,fecharecibo)  + "\n"+
                        "Recibo:     " + recibo + "\n" +
                        "Documento:     " +(Lista.get(i).getNrodocumento())  + "\n" +
                        "Importe :     " + Convert.currencyForView(Lista.get(i).getImporte()) + "\n" +
                        "Saldo :.      " + Convert.currencyForView(Lista.get(i).getSaldo()) + "\n" +
                        "Cobrado :.     " + Convert.currencyForView(Lista.get(i).getCobrado()) + "\n" +
                        "Nuevo Saldo:.     " + Convert.currencyForView(Lista.get(i).getNuevo_saldo()) + "\n" +
                        //"www.vistony.com/intranet/hash="+encHash+ "\n" +
                        "Codigo Validacion SMS:"+codeSMS+""
                ;
                ;
               /* SmsManager sm = SmsManager.getDefault();
                ArrayList<String> parts =sm.divideMessage(LONG_TEXT);
                int numParts = parts.size();
                ArrayList<PendingIntent> sentIntents = new ArrayList<PendingIntent>();
                ArrayList<PendingIntent> deliveryIntents = new ArrayList<PendingIntent>();
                for (int i = 0; i < numParts; i++)
                { sentIntents.add(PendingIntent.getBroadcast(getContext(), 0, mSendIntent, 0)); deliveryIntents.add(PendingIntent.getBroadcast(getContext(), 0, mDeliveryIntent, 0)); } sm.sendMultiPartTextMessage(mDestAddr,null, parts, sentIntents, deliveryIntents)*/
            }
        }catch (Exception e)
        {
            Log.e("REOS","formulascontroller-convertirCadenaEnvioSMS-error-"+e.toString());
        }
        Log.e("REOS","formulascontroller-convertirCadenaEnvioSMS-CadenaSMS-"+CadenaSMS);
        return CadenaSMS;
    }

    public String CalcularMontoTotalPromocionconDescuentoyBono(String MontoTotalLineaSinDescuento,String MontoDescuento,String Bono ){
        BigDecimal temp1=new BigDecimal(MontoTotalLineaSinDescuento);

        BigDecimal repta=temp1.subtract(new BigDecimal(MontoDescuento)).subtract(new BigDecimal(Bono).setScale(2, RoundingMode.HALF_UP));

        return repta.toString();
    }

    public  String getPriceReferencePack(String PrecioPackconDescuento,String QuantityPackPromotion){
        String efectividad="";
        Log.e("REOS","FormulasController.getAmountRouteeffectiveness.PrecioPackconDescuento:" + PrecioPackconDescuento);
        Log.e("REOS","FormulasController.getAmountRouteeffectiveness.QuantityPackPromotion:" + QuantityPackPromotion);
        double resultado;
        resultado=(Double.parseDouble(PrecioPackconDescuento)/Double.parseDouble(QuantityPackPromotion));
        Log.e("REOS","FormulasController.getAmountRouteeffectiveness.resultado:" + resultado);
        return String.valueOf(format.format(resultado));
    }

    public String ObtenerCalculoPrecioImpuesto(String preciounitario,String FactorImpuesto){

        BigDecimal acum = new BigDecimal(0);
        Log.e("REOS","formulascontroller-ObtenerCalculoPrecioImpuesto-preciounitario-"+preciounitario);
        Log.e("REOS","formulascontroller-ObtenerCalculoPrecioImpuesto-FactorImpuesto-"+FactorImpuesto);
        preciounitario=(preciounitario.equals(""))?"0":preciounitario;

        BigDecimal preUnit = new BigDecimal(preciounitario).setScale(5,RoundingMode.HALF_UP);
        BigDecimal cant = new BigDecimal(FactorImpuesto);

        BigDecimal subTotalLine=preUnit.multiply(cant.multiply(new BigDecimal(0.01)).add(new BigDecimal(1)));
        return subTotalLine.setScale(5,RoundingMode.DOWN).toString();
    }


    public ArrayList<ListaOrdenVentaDetalleEntity> addSIGAUS
            (ArrayList<ListaOrdenVentaDetalleEntity> listSalesOrder
             //,ArrayList<ListaOrdenVentaDetalleEntity> listadoProductosAgregados
            )
    {
        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(Context);
        ArrayList<ListaPrecioDetalleSQLiteEntity> listSIGAUS=new ArrayList<>();
        listSIGAUS=listaPrecioDetalleSQLiteDao.getProductSIGAUS();
        Log.e("REOS", "FormulasController-addSIGAUS-listSIGAUS.size(): "+listSIGAUS.size());

        String producto_id="",producto="",umd="",stock="",preciobase="";
        Float cantidad=0f,SIGAUS=0f;
        for(int i=0;i<listSIGAUS.size();i++)
        {
            producto_id=listSIGAUS.get(i).getProducto_id();
            producto=listSIGAUS.get(i).getProducto();
            umd=listSIGAUS.get(i).getUmd();
            stock=listSIGAUS.get(i).getStock_almacen();
            preciobase=listSIGAUS.get(i).getContado();
        }

        Log.e("REOS", "FormulasController-addSIGAUS-listSalesOrder.size(): "+listSalesOrder.size());
        Log.e("REOS", "FormulasController-addSIGAUS-preciobase: "+preciobase);
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
        listaOrdenVentaDetalleEntities=ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listSalesOrder);
        for(int j=0;j<listaOrdenVentaDetalleEntities.size();j++)
        {
            Log.e("REOS", "FormulasController-addSIGAUS-listSalesOrder.get(j).getOrden_detalle_oil_tax(): "+listaOrdenVentaDetalleEntities.get(j).getOrden_detalle_liter());
            Log.e("REOS", "FormulasController-addSIGAUS-listSalesOrder.get(j).getOrden_detalle_liter(): "+listaOrdenVentaDetalleEntities.get(j).getOrden_detalle_cantidad());
            if(listaOrdenVentaDetalleEntities.get(j).getOrden_detalle_oil_tax() .equals("Y"))
            {
                cantidad=cantidad+
                        (
                                Float.parseFloat(listaOrdenVentaDetalleEntities.get(j).getOrden_detalle_liter())
                                *
                                Float.parseFloat(listaOrdenVentaDetalleEntities.get(j).getOrden_detalle_cantidad())
                        )
                ;
            }
        }
        ListaOrdenVentaDetalleEntity ObjListaProductosEntity=new ListaOrdenVentaDetalleEntity();
        String indice="0";
        if(listSalesOrder.isEmpty())
        {
            indice="1";
        }
        else
        {
            indice=String.valueOf(listSalesOrder.size()+1);
        }

        for(int j=0;j<listSalesOrder.size();j++)
        {
            if(listSalesOrder.get(j).getOrden_detalle_producto_id().equals("SIGAUS"))
            {
                SIGAUS++;
            }
        }

        if(SIGAUS==0)
        {
            ObjListaProductosEntity.orden_detalle_item = (indice);
            ObjListaProductosEntity.orden_detalle_producto_id = producto_id;
            ObjListaProductosEntity.orden_detalle_producto = producto;
            ObjListaProductosEntity.orden_detalle_umd = umd;
            ObjListaProductosEntity.orden_detalle_stock_almacen = stock;
            ObjListaProductosEntity.orden_detalle_precio_unitario = preciobase;
            ObjListaProductosEntity.orden_detalle_gal = "0";
            ObjListaProductosEntity.orden_detalle_monto_igv = "0";
            //Cambio
            ObjListaProductosEntity.orden_detalle_cantidad = cantidad.toString();
            //
            ObjListaProductosEntity.orden_detalle_monto_descuento = "0";
            ObjListaProductosEntity.orden_detalle_montototallinea = "0";
            ObjListaProductosEntity.orden_detalle_promocion_habilitada = "0";
            ObjListaProductosEntity.orden_detalle_lista_promocion_cabecera = null;
            ObjListaProductosEntity.orden_detalle_porcentaje_descuento = "0";
            ObjListaProductosEntity.orden_detalle_montosubtotal = "0";
            ObjListaProductosEntity.orden_detalle_gal_acumulado = "0";
            ObjListaProductosEntity.orden_detalle_descuentocontado = "0";
            ObjListaProductosEntity.orden_detalle_terminopago_id = "0";
            ObjListaProductosEntity.orden_detalle_chk_descuentocontado_cabecera = true;
            ObjListaProductosEntity.orden_detalle_cardcode = "";
            ObjListaProductosEntity.orden_detalle_porcentaje_descuento_maximo = "";
            ObjListaProductosEntity.orden_detalle_oil_tax = "";
            ObjListaProductosEntity.orden_detalle_liter = "";
            ObjListaProductosEntity.orden_detalle_montosubtotalcondescuento = "0";
            listSalesOrder.add(ObjListaProductosEntity);
        }
        return listSalesOrder;
    }

    public String getPriceIncrement(String PrecioUnitario, String Incremento) {

        //Log.e("REOS","formulascontroller-ObtenerCalculoPrecioImpuesto-preciounitario-"+preciounitario);
        //Log.e("REOS","formulascontroller-ObtenerCalculoPrecioImpuesto-preciounitario-"+preciounitario);

        Incremento = (Incremento.equals("")) ? "0" : Incremento;

        BigDecimal preUnit = new BigDecimal(PrecioUnitario).setScale(2, RoundingMode.HALF_UP);
        BigDecimal incremento = new BigDecimal(Incremento).divide(new BigDecimal("100")).add(new BigDecimal("1"));

        BigDecimal product = preUnit.multiply(incremento);
        return product.toString();
    }

    public void AddForms(List<CustomerComplaintSectionEntity> lead) {
        try {
            SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
            SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date date = new Date();
            CustomerComplaintFormsSQLiteDao customerComplaintFormsSQLiteDao = new CustomerComplaintFormsSQLiteDao(Context);
            ArrayList<CustomerComplaintFormsEntity> ListCustomerComplaintForms = new ArrayList<>();
            CustomerComplaintFormsEntity customerComplaintFormsEntity = new CustomerComplaintFormsEntity();
            customerComplaintFormsEntity.forms = "Reclamo de Cliente";
            customerComplaintFormsEntity.forms_id = "0";
            customerComplaintFormsEntity.forms_date = FormatFecha.format(date);
            customerComplaintFormsEntity.time = dateFormathora.format(date);
            customerComplaintFormsEntity.salesrepcode = SesionEntity.fuerzatrabajo_id;
            customerComplaintFormsEntity.user_id = SesionEntity.usuario_id;
            customerComplaintFormsEntity.setListCustomerComplaintSection(lead);
            ListCustomerComplaintForms.add(customerComplaintFormsEntity);

            customerComplaintFormsSQLiteDao.addCustomerComplaintForms(ListCustomerComplaintForms);

        } catch (Exception e) {
            Log.e("REOS", "FormulasController.AddForms.error : " + e.toString());
        }
    }

}

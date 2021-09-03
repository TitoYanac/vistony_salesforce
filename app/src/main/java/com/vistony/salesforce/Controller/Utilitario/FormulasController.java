package com.vistony.salesforce.Controller.Utilitario;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.google.gson.Gson;
import com.vistony.salesforce.Dao.Retrofit.CobranzaDetalleWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaUnidadWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.Retrofit.VisitaRepository;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ListaPromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaDetallePromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetallePromocionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.ApprovalRequest;
import com.vistony.salesforce.Entity.DocumentHeader;
import com.vistony.salesforce.Entity.DocumentLine;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetallePromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.View.TotalSalesOrder;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class FormulasController {
    DecimalFormat format = new DecimalFormat("#0.00");
    Context Context;
    private VisitaRepository visitaRepository;

    public FormulasController(Context context)
    {
        Context=context;
    }

    public String CalcularMontoDescuento(String MontoTotalLineaSinIGV,String PorcentajeDescuento) {
        MontoTotalLineaSinIGV = MontoTotalLineaSinIGV.replace(",", ".");
        PorcentajeDescuento = PorcentajeDescuento.replace(",", ".");

        double MontoDescuento=0,factorConversion=0.01;
        MontoDescuento=Double.parseDouble(MontoTotalLineaSinIGV)*(Double.parseDouble(PorcentajeDescuento)*factorConversion);
        return format.format(MontoDescuento);
    }

    public String CalcularMontoTotalconDescuento(String MontoTotalLineaSinDescuento,String MontoDescuento){
        MontoTotalLineaSinDescuento = MontoTotalLineaSinDescuento.replace(",", ".");
        MontoDescuento = MontoDescuento.replace(",", ".");

        double MontoTotalconDescuento=Double.parseDouble(MontoTotalLineaSinDescuento)-Double.parseDouble(MontoDescuento);
        return format.format(MontoTotalconDescuento);
    }

    public String CalcularMontoTotalporLinea(String MontoSubTotalSinDescuento,String MontoDescuento,String MontoImpuesto) {

        MontoSubTotalSinDescuento = MontoSubTotalSinDescuento.replace(",", ".");
        MontoDescuento = MontoDescuento.replace(",", ".");
        MontoImpuesto = MontoImpuesto.replace(",", ".");

        double MontoTotalporLinea=0;
        MontoTotalporLinea=Double.parseDouble(MontoSubTotalSinDescuento)-Double.parseDouble(MontoDescuento)+Double.parseDouble(MontoImpuesto);
        return format.format(MontoTotalporLinea);
    }

    public String CalcularMontoImpuestoPorLinea(String MontoSubtotal,String MontoDescuento,String Impuesto){

        MontoSubtotal = MontoSubtotal.replace(",", ".");
        MontoDescuento = MontoDescuento.replace(",", ".");
        Impuesto = Impuesto.replace(",", ".");

        double MontoImpuestoPorLinea=0,factorConversion=0.01;
        MontoImpuestoPorLinea=(Double.parseDouble(MontoSubtotal)-Double.parseDouble(MontoDescuento))*(factorConversion*Double.parseDouble(Impuesto));
        return format.format(MontoImpuestoPorLinea);
    }
    public String CalcularMontoSubTotalporLinea(String PrecioUnitario,String Cantidad){
        double MontoSubTotalPorLinea=0;
        Cantidad=(Cantidad.equals(""))?"0":Cantidad;
        MontoSubTotalPorLinea=Double.parseDouble(PrecioUnitario)*Integer.parseInt(Cantidad);
        return format.format(MontoSubTotalPorLinea);
    }

    public String CalcularCantidadLineaPromocionDetalle(String CantidadPromocionCabecera,String CantidadPromocionDetalle)
    {

        float CantidadFinal=0;
        CantidadFinal=(Float.parseFloat(CantidadPromocionCabecera)*Float.parseFloat(CantidadPromocionDetalle));
        return format.format(CantidadFinal);
    }

    public String CalcularPorcentajeDescuentoPorLinea(ArrayList<ListaPromocionCabeceraEntity> ListaPromocionCabeceradelaLinea,String descuentocontado)
    {
        ArrayList<ListaPromocionCabeceraEntity> listapromocioncabecera=new ArrayList<>();
        listapromocioncabecera=ListaPromocionCabeceradelaLinea;
        int calculodescuento=0;
        for(int i=0;i<listapromocioncabecera.size();i++)
        {
            //ArrayList<PromocionDetalleSQLiteEntity> listapromociondetallesqliteentity=new ArrayList<>();
            //PromocionDetalleSQLiteDao promocionDetalleSQLiteDao= new PromocionDetalleSQLiteDao(Context);
            /*listapromociondetallesqliteentity=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                    SesionEntity.compania_id,
                    listapromocioncabecera.get(i).getLista_promocion_id(),
                    listapromocioncabecera.get(i).getPromocion_id()
            );*/
            //listapromocioncabecera.get(i).getListaPromocionDetalleEntities()
            //if(!(listapromociondetallesqliteentity.isEmpty()))
            if(!(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().isEmpty()))
            {
                for(int j=0;j<listapromocioncabecera.get(i).getListaPromocionDetalleEntities().size();j++)
                {
                    if(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().get(j).getUmd().equals("%"))
                    {
                        if(descuentocontado.equals("true"))
                        {

                            calculodescuento=(calculodescuento+Integer.parseInt(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().get(j).getCantidad()))+3;
                        }
                        else
                            {

                                calculodescuento=calculodescuento+Integer.parseInt(listapromocioncabecera.get(i).getListaPromocionDetalleEntities().get(j).getCantidad());
                            }

                    }
                }
            }
        }
        return String.valueOf(calculodescuento);
    }

    public TotalSalesOrder CalcularMontosPedidoCabeceraDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity){

        double Calculo=0.0,CalculoSubTotal=0.0,CalculoTotalIGV=0.0,CalculoTotal=0.0,CalculoPromocion=0.0,CalculoTotalDescuento=0.0,CalculoTotalPromocion=0.0,CalculoTotalGalonaje=0.0;

        Calculo=ObtenerCalculoMontoSubTotalOrdenDetallePromocion(listaOrdenVentaDetalleEntity);
        CalculoPromocion=ObtenerCalculoPromocionOrdenDetallePromocion(listaOrdenVentaDetalleEntity);
        CalculoTotalDescuento= ObtenerCalculoDescuentoOrdenDetallePromocion(listaOrdenVentaDetalleEntity);
        CalculoTotalGalonaje=ObtenerCalculoTotalGalonesOrdenDetallePromocion(listaOrdenVentaDetalleEntity);

        CalculoSubTotal=Calculo;
        //format.format(numero decimal) =>devuelve string
        CalculoTotalPromocion=CalculoPromocion+CalculoTotalDescuento;
        CalculoTotalIGV=(CalculoSubTotal-CalculoTotalPromocion)*0.19;
        CalculoTotal=(CalculoSubTotal-CalculoTotalPromocion)+CalculoTotalIGV;

        TotalSalesOrder totalSalesOrder=new TotalSalesOrder();
        totalSalesOrder.setSubtotal(CalculoSubTotal);
        totalSalesOrder.setDescuento(CalculoTotalPromocion);
        totalSalesOrder.setIgv(CalculoTotalIGV);
        totalSalesOrder.setTotal(CalculoTotal);
        totalSalesOrder.setGalones(CalculoTotalGalonaje);

        return (totalSalesOrder);
    }
    public double ObtenerCalculoTotalGalonesOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista) {
        double CalculoIGV=0.0;
        for(int i=0;i<Lista.size();i++)
        {
                CalculoIGV=CalculoIGV+Float.parseFloat(Lista.get(i).getOrden_detalle_gal_acumulado());
        }
        return CalculoIGV;
    }

    public String CalcularMontoImpuestoOrdenDetallePromocionLinea(ListaOrdenVentaDetalleEntity lead)
    {

        double CalculoIGV=0;

        if(lead.getOrden_detalle_lista_orden_detalle_promocion()!=null)
        {
            for(int i=0;i<lead.getOrden_detalle_lista_orden_detalle_promocion().size();i++){
                String asdas=lead.getOrden_detalle_lista_orden_detalle_promocion().get(i).getOrden_detalle_monto_igv();
                asdas = asdas.replace(",", ".");

                CalculoIGV=CalculoIGV+Double.parseDouble(asdas);

            }
        }


        return format.format(CalculoIGV);
    }

    public double ObtenerCalculoPromocionOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista)
    {
        double CalculoPromocion=0;
        Log.e("REOS","FormulasController:CalcularMontosPedidoCabeceraDetallePromocion-Lista.size(): "+Lista.size());
        for(int i=0;i<Lista.size();i++)
        {
            Log.e("REOS","FormulasController:CalcularMontosPedidoCabeceraDetallePromocion-Lista.get(i).getOrden_detalle_porcentaje_descuento(): "+Lista.get(i).getOrden_detalle_porcentaje_descuento());
            if(Lista.get(i).getOrden_detalle_porcentaje_descuento().equals("100"))
            {
                Log.e("REOS","FormulasController:CalcularMontosPedidoCabeceraDetallePromocion-ObtenerCalculoPromocionOrdenDetallePromocion-Lista.get(i).getOrden_detalle_monto_descuento(): "+Lista.get(i).getOrden_detalle_monto_descuento());
                CalculoPromocion=CalculoPromocion+Float.parseFloat(Lista.get(i).getOrden_detalle_monto_descuento());
            }

        }
        Log.e("REOS","FormulasController:CalcularMontosPedidoCabeceraDetallePromocion-ObtenerCalculoPromocionOrdenDetallePromocion-CalculoPromocion: "+CalculoPromocion);
        return CalculoPromocion;
    }
    public double ObtenerCalculoDescuentoOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista)
    {
        double CalculoDescuento=0;
        for(int i=0;i<Lista.size();i++)
        {
            if(Double.parseDouble(Lista.get(i).getOrden_detalle_porcentaje_descuento())>0&&Double.parseDouble(Lista.get(i).getOrden_detalle_porcentaje_descuento())<100) {
                String temp23=Lista.get(i).getOrden_detalle_monto_descuento();
                temp23=temp23.replace(",",".");

                CalculoDescuento=CalculoDescuento+Double.parseDouble(temp23);
            }

        }
        return CalculoDescuento;
    }
    public double ObtenerCalculoMontoSubTotalOrdenDetallePromocion(ArrayList<ListaOrdenVentaDetalleEntity> Lista){
        double CalculoMontoSubTotal=0;
        for(int i=0;i<Lista.size();i++){
            String montoSubtotal=Lista.get(i).getOrden_detalle_montosubtotal();
            montoSubtotal = montoSubtotal.replace(",", ".");

            CalculoMontoSubTotal=CalculoMontoSubTotal+Double.parseDouble(montoSubtotal);
        }
        return CalculoMontoSubTotal;
    }

    public Float ObtenerCalculoPromocion(ArrayList<ListaPromocionCabeceraEntity> Lista) {
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
    }

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
                                //(Float.parseFloat(Lista.get(posicion).getCantidadpromocion()))) ;
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

    public void RegistrarPedidoenBD (ArrayList<ListaOrdenVentaCabeceraEntity> listaOrdenVentaCabeceraEntities,ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity) {


        String OrdenVenta_id="",impuesto_id="",producto="",almacen_id="";
        int cantidadlistaOrdenVentaDetallepromocion=0;

        for(int i=0;i<listaOrdenVentaCabeceraEntities.size();i++){

            OrdenVenta_id=listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_id();
            impuesto_id=listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_impuesto_id();
            almacen_id=listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_almacen_id();
            OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(Context);
            ordenVentaCabeceraSQLite.InsertaOrdenVentaCabecera(
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_compania_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_cliente_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_domembarque_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_terminopago_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_agencia_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_moneda_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_comentario(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_almacen_id(),
                    ""+listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_impuesto_id(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montosubtotal(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montodescuento(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montoimpuesto(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_montototal(),
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
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDTD(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDSD(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDCD(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_MDMT(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_U_SYP_STATUS(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_DocType(),
                    "",
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_total_gal_acumulado(),
                    listaOrdenVentaCabeceraEntities.get(i).getOrden_cabecera_descuentocontado()
            );
        }

        int contador=0;
        for(int j=0;j<listaOrdenVentaDetalleEntity.size();j++)
        {
            OrdenVentaDetalleSQLiteDao ordenVentaDetalleSQLiteDao=new OrdenVentaDetalleSQLiteDao(Context);
            OrdenVentaDetallePromocionSQLiteDao ordenVentaDetallePromocionSQLiteDao=new OrdenVentaDetallePromocionSQLiteDao(Context);
            ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(Context);
            ArrayList<ListaPrecioDetalleSQLiteEntity> listaPrecioDetalleSQLiteEntities=new ArrayList<>();
            listaPrecioDetalleSQLiteEntities=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id());

            for(int g=0;g<listaPrecioDetalleSQLiteEntities.size();g++){
                producto=listaPrecioDetalleSQLiteEntities.get(g).getProducto();
            }

            if(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera()==null){
                contador++;

                String temp24=listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_precio_unitario();
                Log.e("=>",temp24);

                String temp25=listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montototallinea();
                Log.e("=>",temp25);

                ordenVentaDetalleSQLiteDao.InsertaOrdenVentaDetalle(
                        SesionEntity.compania_id,
                        OrdenVenta_id,
                        ""+contador,
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_umd(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_cantidad(),
                        ""+temp24,
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_porcentaje_descuento(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_descuento(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_igv(),
                        ""+temp25,
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
                for(int g=0;g<listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().size();g++){
                    cantidadlistaOrdenVentaDetallepromocion++;
                    ordenVentaDetallePromocionSQLiteDao.InsertaOrdenVentaDetallePromocion(
                            SesionEntity.compania_id,
                            OrdenVenta_id,
                            String.valueOf(cantidadlistaOrdenVentaDetallepromocion),
                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_producto_id(),
                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_umd(),
                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_cantidad(),
                            ""+Convert.stringToDouble(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_precio_unitario()),
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

                /*ordenVentaDetallePromocionSQLiteDao.InsertaOrdenVentaDetallePromocion(
                        SesionEntity.compania_id,
                        OrdenVenta_id,
                        String.valueOf(contador),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_umd(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_cantidad(),
                        String.valueOf(Float.valueOf(format.format(Float.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_precio_unitario())))),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_porcentaje_descuento(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_descuento(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_igv(),
                        String.valueOf(format.format(Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montototallinea()))),
                        String.valueOf(contador),
                        impuesto_id,
                        producto,
                        "",
                        almacen_id,
                        "",
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal(),
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_gal_acumulado(),
                        "10",
                        listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotalcondescuento()
                );*/

            }else{
                        contador++;
                        int contadorpromocion=0;
                        contadorpromocion=contador;
                        ordenVentaDetalleSQLiteDao.InsertaOrdenVentaDetalle(
                                SesionEntity.compania_id,
                                OrdenVenta_id,
                                String.valueOf(contador),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_producto_id(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_umd(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_cantidad(),
                                String.valueOf(Float.valueOf(format.format(Float.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_precio_unitario())))),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montosubtotal(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_porcentaje_descuento(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_descuento(),
                                listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_monto_igv(),
                                String.valueOf(format.format(Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_montototallinea()))),
                                String.valueOf(contador),
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
                                    MontoSubTotalporLinea=CalcularMontoSubTotalporLinea(
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase(),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getCantidad()
                                    );
                                    contador++;
                                    ordenVentaDetalleSQLiteDao.InsertaOrdenVentaDetalle(
                                            SesionEntity.compania_id,
                                            OrdenVenta_id,
                                            String.valueOf(contador),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getProducto_id(),
                                            listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getUmd(),
                                            String.valueOf(Integer.parseInt(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getCantidad())
                                                    *Integer.parseInt(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getCantidadpromocion()))
                                            ,
                                            String.valueOf(format.format(Float.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase()))),
                                            MontoSubTotalporLinea,
                                            "100",
                                            MontoSubTotalporLinea,
                                            "0",
                                            //MontoSubTotalporLinea,
                                            String.valueOf(format.format(Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase())
                                                    //*Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getCantidadpromocion()))
                                                    *(Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getCantidad())
                                                            *Float.parseFloat(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getCantidadpromocion()))))
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
                            ordenVentaDetallePromocionSQLiteDao.InsertaOrdenVentaDetallePromocion(
                                    SesionEntity.compania_id,
                                    OrdenVenta_id,
                                    String.valueOf(cantidadlistaOrdenVentaDetallepromocion),
                                    //listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_promocion_item(),
                                    //listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_linea_referencia_padre(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_producto_id(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_umd(),
                                    listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_cantidad(),
                                    String.valueOf(Float.valueOf(format.format(Float.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_orden_detalle_promocion().get(g).getOrden_detalle_precio_unitario())))),
                                    //String.valueOf(Float.valueOf(format.format(Float.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_precio_unitario())))),
                                    //String.valueOf(format.format(Float.valueOf(listaOrdenVentaDetalleEntity.get(j).getOrden_detalle_lista_promocion_cabecera().get(k).getListaPromocionDetalleEntities().get(l).getPreciobase()))),
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

    private ApprovalRequest addNodoApproval(final int code,final String message){
        ApprovalRequest requestDsct=new ApprovalRequest();

        requestDsct.setApprovalTemplatesID(code);
        requestDsct.setRemarks(message);
        return requestDsct;
    }

    public String GenerayConvierteaJSONOV (String ordenventa_id,Context context){

        String cadenaJSON="";
        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(context);
        OrdenVentaDetallePromocionSQLiteDao ordenVentaDetallePromocionSQLiteDao=new OrdenVentaDetallePromocionSQLiteDao(context);

        ArrayList<OrdenVentaDetallePromocionSQLiteEntity> listaordenVentaDetalleSQLiteEntity=new ArrayList<>();
        ArrayList<DocumentLine> listadoDocumentLines =new ArrayList<>();

        /**OBTIENE TODAS LAS ORDENES DE VENTA CON EL ID**/
        //SOLO DEVOVLERA LA POSICION UNO POR QUE EL ID SOLO PERTENECE A UN OBJETO NO A UNA LISTA DE OBJETOS
        OrdenVentaCabeceraSQLiteEntity ovCabecera= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraporID(ordenventa_id).get(0);
        /**FIN**/

        DocumentHeader documentHeader=new DocumentHeader();
        Gson gson=new Gson();
        DocumentLine documentLine;

        documentHeader.setCardCode(ovCabecera.getCliente_id());
        documentHeader.setComments(ovCabecera.getComentario());
        documentHeader.setDocCurrency(ovCabecera.getMoneda_id());
        documentHeader.setDocDate(Convertirfechahoraafechanumerica(ovCabecera.getFecharegistro()));
        documentHeader.setDocDueDate(Convertirfechahoraafechanumerica(ovCabecera.getFecharegistro()));
        documentHeader.setDocType(ovCabecera.getDocType());
        documentHeader.setU_VIS_SalesOrderID(ovCabecera.getOrdenventa_id());
        documentHeader.setDocumentsOwner(SesionEntity.documentsowner);
        documentHeader.setFederalTaxID(ovCabecera.getRucdni());
        documentHeader.setPaymentGroupCode(ovCabecera.getTerminopago_id());
        documentHeader.setSalesPersonCode(SesionEntity.fuerzatrabajo_id);
        documentHeader.setPayToCode(ovCabecera.getDomembarque_id());
        documentHeader.setShipToCode(ovCabecera.getDomembarque_id());

        ///////////////////////////FLAG PARA ENVIAR LA OV POR EL FLUJO DE  APROBACIÓN O NO/////////ALTO RIESGO ASUMIDO/////////
        List<ApprovalRequest> attrFlag=new ArrayList<ApprovalRequest>();

        if(ovCabecera.getExcede_lineacredito().equals("1")){
            ApprovalRequest nodo= addNodoApproval(6,"NO CUMPLE CON LA VALIDACIÓN DE EXCEDIO LA LINEA DE CREDITO");
           attrFlag.add(nodo);
        }

        if(Integer.parseInt(ovCabecera.getDueDays())>5){
            ApprovalRequest nodo= addNodoApproval(4,"NO CUMPLE CON LA VALIDACIÓN DE DOCUMENTOS VENCIDOS");
            attrFlag.add(nodo);
        }

        if(attrFlag.size()>0){
            /******************/
            //Cuando cae en validaciones se envia 0.2 si o si, el  setApprovalTemplatesIDidentifica el tipo de validacion
            documentHeader.setDiscountPercent("0.2");
            /******************/
        }

        documentHeader.setDocument_ApprovalRequests(attrFlag);


        ////////////////////////////////////////////////////////////////////////////////////////////

        listaordenVentaDetalleSQLiteEntity=ordenVentaDetallePromocionSQLiteDao.ObtenerOrdenVentaDetallePromocionporID(ordenventa_id);
        for(int j=0;j<listaordenVentaDetalleSQLiteEntity.size();j++){
            String taxOnly="",taxCode="";


            //Casuistica Venta

            //U_SYP_FECAT_07="10";
            //COGSAccountCode="";
            taxOnly="N";
            taxCode="IVA";
            //U_VIST_CTAINGDCTO="";
           // montolineatotal=listaordenVentaDetalleSQLiteEntity.get(j).getMontosubtotalcondescuento();

                documentLine =new DocumentLine();
                documentLine.setCostingCode( SesionEntity.UnidadNegocio);
                documentLine.setCostingCode2(SesionEntity.CentroCosto);
                documentLine.setDiscountPercent(listaordenVentaDetalleSQLiteEntity.get(j).getPorcentajedescuento()); //el vendedor puede desde 0 a 99.9%, de 0 a 5% todo ok en adelnate mostrar alerta
                documentLine.setDscription(ObtenerProductoDescripcion(listaordenVentaDetalleSQLiteEntity.get(j).getProducto_id(),context));
                documentLine.setItemCode(listaordenVentaDetalleSQLiteEntity.get(j).getProducto_id());
                documentLine.setPrice(listaordenVentaDetalleSQLiteEntity.get(j).getPreciounitario());
                documentLine.setQuantity(listaordenVentaDetalleSQLiteEntity.get(j).getCantidad());
                documentLine.setTaxCode(taxCode);
                documentLine.setTaxOnly(taxOnly);
                documentLine.setWarehouseCode(listaordenVentaDetalleSQLiteEntity.get(j).getAlmacen_id());


            listadoDocumentLines.add(documentLine);
        }
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
        arraylistapreciodetalleentity=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(producto_id);
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
        String año,mes,dia,hora,min,segs;
        try {
            año = String.valueOf(calendario.get(calendario.YEAR));
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
            ID = año + mes + dia + hora + min + segs;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return ID;
    }

    public static String ObtenerFechaCadena(String Fecha)
    {
        String ID="";
        SimpleDateFormat formato = new SimpleDateFormat("yyyyMMdd");
        String fechacadena=null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String año,mes,dia,hora,min,segs;
        try {
            GregorianCalendar calendario = new GregorianCalendar();
            calendario.setTime(df.parse(Fecha));
            año = String.valueOf(calendario.get(calendario.YEAR));
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
            ID = año + mes + dia;
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
                listaOrdenVentaDetalle.orden_detalle_stock_general = "";
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

    public String EnviarReciboWsRetrofit(
            ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity,
            Context context,
            String TipoCRUD,
            String Anulacion,
            String Comentario,
            String Banco,
            String Actualizacion
            )
    {
        String resultado="0";
        CobranzaDetalleWS cobranzaDetalleWS=new CobranzaDetalleWS(context);
        String banco_id="",deposito_id="",comentario="";

        try
        {
            for(int i=0;i<listaCobranzaDetalleSQLiteEntity.size();i++)
            {
                if(Anulacion.equals("1")&&TipoCRUD.equals("UPDATE"))
                {
                    banco_id="0";
                    deposito_id="1";
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                }else if (Actualizacion.equals("1")&&TipoCRUD.equals("UPDATE"))
                {
                    banco_id=Banco;
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                    deposito_id=listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id();
                    Log.e("REOS","UPDATE+1"+banco_id+"-"+comentario+"-"+deposito_id);
                }
                else if(TipoCRUD.equals("CREATE"))
                {
                    comentario=listaCobranzaDetalleSQLiteEntity.get(i).getComentario();
                    banco_id=listaCobranzaDetalleSQLiteEntity.get(i).getBanco_id();
                    deposito_id=listaCobranzaDetalleSQLiteEntity.get(i).getCobranza_id();
                }


                resultado=cobranzaDetalleWS.PostCobranzaDetalleWS(
                        SesionEntity.imei,
                        TipoCRUD,
                        listaCobranzaDetalleSQLiteEntity.get(i).getCompania_id(),
                        banco_id,
                        "0",
                        deposito_id,
                        listaCobranzaDetalleSQLiteEntity.get(i).getId(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getCliente_id(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getDocumento_id(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getImportedocumento(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getSaldodocumento(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getSaldocobrado(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getNuevosaldodocumento(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getFechacobranza(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getRecibo(),
                        "Pendiente",
                        comentario,
                        SesionEntity.usuario_id,
                        SesionEntity.fuerzatrabajo_id,
                        listaCobranzaDetalleSQLiteEntity.get(i).getChkbancarizado(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getChkqrvalidado(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getMotivoanulacion(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getPagodirecto(),
                        listaCobranzaDetalleSQLiteEntity.get(i).getPagopos()
                );

            }

        }catch (Exception e)
        {
            resultado="0";
        }

        return resultado;
    }

    public int RegistrarRutaVendedor(ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntities,String fecharuta,String chk_ruta)
    {
        int resultado=0;
        RutaVendedorSQLiteDao rutaVendedorSQLiteDao=new RutaVendedorSQLiteDao(Context);
        try
        {
            for (int i=0;i<listaClienteCabeceraEntities.size();i++)
            {
                resultado=rutaVendedorSQLiteDao.InsertaRutaVendedor(
                        listaClienteCabeceraEntities.get(i).getCliente_id(),
                        listaClienteCabeceraEntities.get(i).getCompania_id(),
                        listaClienteCabeceraEntities.get(i).getNombrecliente(),
                        listaClienteCabeceraEntities.get(i).getDomembarque_id(),
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
                        fecharuta
                );
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS",String.valueOf(e));
            resultado=0;
        }

        return resultado;
    }

    public void RegistraVisita(VisitaSQLiteEntity visita, Context context) {
        visitaRepository = new ViewModelProvider((ViewModelStoreOwner) context).get(VisitaRepository.class);

        SimpleDateFormat dateFormathora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        Date date = new Date();



        visita.setCompania_id(SesionEntity.compania_id);
        visita.setDate(FormatFecha.format(date));
        visita.setHour(dateFormathora.format(date));
        visita.setSlpCode(SesionEntity.fuerzatrabajo_id);
        visita.setUserId(SesionEntity.usuario_id);
        visita.setChkenviado("1");
        visita.setChkrecibido("0");

        VisitaSQLite visitaSQLite = new VisitaSQLite(context);
        visitaSQLite.InsertaVisita(visita);

        RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(context);

        switch(visita.getType()){
            case "1":
                rutaVendedorSQLiteDao.ActualizaChkPedidoRutaVendedor(
                        visita.getCardCode(),
                        visita.getAddress(),
                        SesionEntity.compania_id,
                        String.valueOf(FormatFecha.format(date))
                );
                break;
            case "2":
                rutaVendedorSQLiteDao.ActualizaChkCobranzaRutaVendedor(
                        visita.getCardCode(),
                        visita.getAddress(),
                        SesionEntity.compania_id,
                        String.valueOf(FormatFecha.format(date))
                );
                break;
            default:
                rutaVendedorSQLiteDao.ActualizaVisitaRutaVendedor(
                        visita.getCardCode(),
                        visita.getAddress(),
                        SesionEntity.compania_id,
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
        } catch (Exception e)
        {
            // TODO: handle exception
            System.out.println(e.getMessage());
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

    /*
    public ArrayList<CobranzaDetalleSQLiteEntity> ObtenerListaConvertidaCobranzaDetalleSQLite (Context context,String Imei,String Compania_id,String usuario_id,String Recibo)
    {
        try {
            return new HiloObtenerListaConvertidaCobranzaDetalleSQLite(context,Imei,Compania_id,usuario_id,Recibo).execute().get();
            //return new getProfilesAsyncTask(userDao).execute().get();
        } catch (Exception e) {
            Log.e("JPCM",""+e);
            e.printStackTrace();
            return null;
        }
    }

    private class HiloObtenerListaConvertidaCobranzaDetalleSQLite extends AsyncTask<String, Void, ArrayList<CobranzaDetalleSQLiteEntity> > {
        Context context;
        String Imei,Compania_id,usuario_id,Recibo;

        private HiloObtenerListaConvertidaCobranzaDetalleSQLite(Context context,String Imei,String Compania_id,String usuario_id,String Recibo)
        {
            this.context=context;
            this.Imei=Imei;
            this.Compania_id=Compania_id;
            this.usuario_id=usuario_id;
            this.Recibo=Recibo;
        }

        @Override
        public final ArrayList<CobranzaDetalleSQLiteEntity>  doInBackground(String... arg0) {

            ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity=new ArrayList<>();
            ArrayList<ListaHistoricoCobranzaEntity> ListaHistoricoCobranzaEntity=new ArrayList<>();
            HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(context);
            OrdenVentaDetalleSQLiteDao ordenVentaDetalleSQLiteDao=new OrdenVentaDetalleSQLiteDao(context);
            try {
                ListaHistoricoCobranzaEntity=historicoCobranzaWS.getHistoricoCobranzaIndividual(
                        Imei,Compania_id,usuario_id,Recibo
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

    }*/

    public ArrayList<ListaOrdenVentaDetalleEntity> ConvertirListaOrdenVentaDetalleEntity(ArrayList<OrdenVentaDetalleSQLiteEntity> ListaRecibida){

        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalleEntity=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();
        for(int i=0;i<ListaRecibida.size();i++)
        {
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

    public ArrayList<ListaOrdenVentaDetalleEntity> ConversionListaOrdenDetallepoListaOrdenDetallePromocion
            (ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity)
    {
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntitycopia=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjlistaOrdenVentaDetalleEntity;

        //Inicia Bucle
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
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-b"+String.valueOf(b));
                    //String contadodescuento="0";
                   // boolean chk_descuentocontadoaplicado=false;

                    /*if(Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado())>0&&
                            !listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento().equals("100")
                            &&listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado()&&(!listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                    )
                    {
                        Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclecumplecondiciones");
                        contadodescuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                        chk_descuentocontadoaplicado=true;
                    }
                    else
                    {
                        Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclenocumplecondiciones");
                        contadodescuento="0";
                        //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                        //{
                            Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-buclenocumplecondiciones-2dobuclecumplecondiciones");
                            chk_descuentocontadoaplicado=true;
                        //}

                    }*/
                    //Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-chk_descuentocontadoaplicado"+String.valueOf(chk_descuentocontadoaplicado));
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado()"+String.valueOf(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado()));
                    //Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-contadodescuento: "+contadodescuento);
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado());
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado());
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento());
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado());
                    ObjlistaOrdenVentaDetalleEntity=new ListaOrdenVentaDetalleEntity();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_item=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_item();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto_id=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto_id();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_producto=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_producto();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_umd=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_umd();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock_almacen=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_stock();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_cantidad();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_precio_unitario();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotal();
                    /*ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=String.valueOf(
                            Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento())+
                            Integer.parseInt(contadodescuento));*/
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                    /*ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=
                            CalcularMontoDescuento(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());*/
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento());
                    /*ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=
                            CalcularMontoImpuestoPorLinea(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                    SesionEntity.Impuesto
                            );*/
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_igv());
                    Log.e("REOS","FormulasController-ConversionListaOrdenDetallepoListaOrdenDetallePromocion-SesionEntity.Impuesto: "+SesionEntity.Impuesto);
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montototallinea =listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montototallinea();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_promocion_cabecera = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_lista_promocion_cabecera();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_promocion_habilitada = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_promocion_habilitada();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_gal_acumulado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_gal_acumulado();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotalcondescuento = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_montosubtotalcondescuento();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_lista_orden_detalle_promocion = null;
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_descuentocontado = listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_descuentocontado();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_chk_descuentocontado_aplicado = listaOrdenVentaDetalleEntity.get(a).isOrden_detalle_chk_descuentocontado_aplicado();
                    listaOrdenVentaDetalleEntitycopia.add(ObjlistaOrdenVentaDetalleEntity);
                }
            }
        }


        return listaOrdenVentaDetalleEntitycopia;
    }

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
                            CalcularMontoDescuento(
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
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_stock_almacen();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_cantidad();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_precio_unitario();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_montosubtotal();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=String.valueOf(
                                Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_porcentaje_descuento())+
                                        Integer.parseInt(contadodescuento));
                        Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                        //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                        ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=
                                CalcularMontoDescuento(
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
    }

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
                    */
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento(): "+listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento());
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
                            /*String.valueOf(
                            Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento())+
                                    Integer.parseInt(contadodescuento));*/
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                    //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                          /*  CalcularMontoDescuento(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());*/
                    //listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_descuento();
                    Log.e("REOS","FormulasController-ActualizaciondeConversionOrdenVentaCabeceraListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento: "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento());
                    ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_igv=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_monto_igv();
                            /*CalcularMontoImpuestoPorLinea(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento(),
                                    SesionEntity.Impuesto
                            );*/
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
                            /*CalcularMontoTotalconDescuento(
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_montosubtotal(),
                                    ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_monto_descuento()
                            );*/
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
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_stock_almacen=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_stock_almacen();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_cantidad=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_cantidad();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_precio_unitario=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_precio_unitario();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_montosubtotal=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_montosubtotal();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=String.valueOf(
                        Integer.parseInt(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_porcentaje_descuento())+
                                Integer.parseInt(contadodescuento));
                Log.e("REOS","FormulasController-ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion-ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento(): "+ObjlistaOrdenVentaDetalleEntity.getOrden_detalle_porcentaje_descuento());
                //ObjlistaOrdenVentaDetalleEntity.orden_detalle_porcentaje_descuento=listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).getOrden_detalle_porcentaje_descuento();
                ObjlistaOrdenVentaDetalleEntity.orden_detalle_monto_descuento=
                        CalcularMontoDescuento(
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
    }

    public void OrdenBurbuja(String[] args) {
        int [] a  = {7,2,4,8,3,9,1,5,10,6};
        int temporal = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 1; j < (a.length - i); j++) {
                if (a[j - 1] > a[j]) {
                    temporal = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temporal;
                }
            }
        }
        //System.out.println(Arrays.toString(a));
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
        arrayListListaPrecioDetalleSQLiteEntity=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(listapromocion_id);

        for(int i=0;i<arrayListListaPrecioDetalleSQLiteEntity.size();i++)
        {
            if(arrayListListaPrecioDetalleSQLiteEntity.get(i).getU_vis_cashdscnt().equals("N"))
            {
                resultado=true;
            }
            else
                {
                    resultado=false;
                }


        }

        return resultado;
    }
}

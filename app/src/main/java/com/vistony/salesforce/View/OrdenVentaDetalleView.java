package com.vistony.salesforce.View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListSalesOrderDetailAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaOrdenVentaDetalleAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListaOrdenVentaDetalleDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.DocumentHeader;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.View.TotalSalesOrder;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;
import com.vistony.salesforce.Sesion.ClienteAtendido;
import com.vistony.salesforce.Sesion.Vendedor;

import java.util.ArrayList;


public class OrdenVentaDetalleView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String TAG_1 = "text";
    View v;
    static OnFragmentInteractionListener mListener;
    public static ArrayList<ListaOrdenVentaDetalleEntity> listadoProductosAgregados;
    FloatingActionButton fab_consulta_productos;
    static HiloAgregarListaProductos hiloAgregarListaProductos;
    static HiloActualizarResumenMontos hiloActualizarResumenMontos;
    static ListaOrdenVentaDetalleAdapter listaOrdenVentaDetalleAdapter;
    ListView lv_ordenventadetalle;
    static TextView tv_orden_venta_detalle_subtotal,tv_orden_venta_detalle_descuento,tv_orden_venta_detalle_igv,tv_orden_venta_detalle_total,tv_orden_detalle_galones;
    public static ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabecera=new ArrayList<>();
    static MenuItem guardar_orden_venta,vincular_orden_venta_cabecera;
    static Menu menu_variable;
    static String listaprecio_id,descuentocontado,terminopago_id,ubigeo_id="0",currency_id,discount_percent_bl;
    static Context context;
    private ProgressDialog pd;
    TableRow tr_summary_flete,tr_detail_gallons;
    static ListSalesOrderDetailAdapter listSalesOrderDetailAdapter;
    public static OrdenVentaDetalleView newInstanceEnviaListaPromocion (Object objeto) {

        ListenerBackPress.setCurrentFragment("OrdenVentaDetalleView");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        String Fragment="OrdenVentaDetalleView";
        String accion="listadopromocion";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstance(Object objeto){


        /*String [] arrayObject= (String[]) objeto;

        for(int i=0;i<arrayObject.length;i++){
            Log.e("JEPICAME","=>"+arrayObject[i]);
        }

        String objetostring=arrayObject[1];
        String[] compuesto = objetostring.split("&");

        if(compuesto[0]!=null||compuesto[1]!=null||compuesto[2]!=null){

            listaprecio_id=arrayObject[0]; //codigocliente
            descuentocontado=compuesto[0];
            terminopago_id=compuesto[1];
            currency_id=compuesto[2];
            discount_percent_bl=compuesto[3];
            ubigeo_id=compuesto[4];

        }else{
            listaprecio_id=arrayObject[0]; //codigocliente
            descuentocontado="false";
        }*/
        DocumentHeader documentHeader= (DocumentHeader) objeto;
        listaprecio_id=documentHeader.getCardCode(); //codigocliente
        descuentocontado=documentHeader.getDiscountCash();
        terminopago_id=documentHeader.getPaymentGroupCode();
        currency_id=documentHeader.getDocCurrency();
        discount_percent_bl= documentHeader.getDiscountPercent_BL();
        ubigeo_id=documentHeader.getUbigeoCode();
        Log.e("REOS","OrdenVentaDetalleView-newInstance-ubigeo_id: "+ubigeo_id);
        Log.e("REOS","OrdenVentaDetalleView-newInstance-listaprecio_id: "+listaprecio_id);
        Log.e("REOS","OrdenVentaDetalleView-newInstance-descuentocontado: "+descuentocontado);
        Log.e("REOS","OrdenVentaDetalleView-newInstance-terminopago_id: "+terminopago_id);
        Log.e("REOS","OrdenVentaDetalleView-newInstance-discount_percent_bl: "+discount_percent_bl);

        ListenerBackPress.setCurrentFragment("OrdenVentaDetalleView");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceAgregarProducto(Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        //ArrayList<ListaProductoEntity> listadoProductosConversion=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjListaProductosEntity=new ListaOrdenVentaDetalleEntity();
        ListaProductoEntity productoAgregado=(ListaProductoEntity)objeto;
        //listadoProductosConversion=(ArrayList<ListaProductoEntity>)objeto;
        String indice="0";
        boolean descuentocontadocabecera=false;
        if(descuentocontado.equals("true"))
        {
            descuentocontadocabecera=true;
        }
        else
        {
            descuentocontadocabecera=false;
        }
        //for (int i=0;i<listadoProductosConversion.size();i++)
        //{
            if(listadoProductosAgregados.isEmpty())
            {

                indice="1";
            }
            else
            {
                indice=String.valueOf(listadoProductosAgregados.size()+1);
            }
            ObjListaProductosEntity.orden_detalle_item=(indice);
            ObjListaProductosEntity.orden_detalle_producto_id=productoAgregado.getProducto_id();
            ObjListaProductosEntity.orden_detalle_producto=productoAgregado.getProducto();
            ObjListaProductosEntity.orden_detalle_umd=productoAgregado.getUmd();
            ObjListaProductosEntity.orden_detalle_stock_almacen=productoAgregado.getStock_almacen();
            ObjListaProductosEntity.orden_detalle_precio_unitario=productoAgregado.getPreciobase();
            ObjListaProductosEntity.orden_detalle_gal=productoAgregado.getGal();
            ObjListaProductosEntity.orden_detalle_monto_igv="0";
            //Cambio 18/10/2023 21:25 solucion error perdida de foco
            ObjListaProductosEntity.orden_detalle_cantidad="";
            //
            ObjListaProductosEntity.orden_detalle_monto_descuento="0";
            ObjListaProductosEntity.orden_detalle_montototallinea="0";
            ObjListaProductosEntity.orden_detalle_promocion_habilitada="0";
            ObjListaProductosEntity.orden_detalle_lista_promocion_cabecera=null;
            ObjListaProductosEntity.orden_detalle_porcentaje_descuento="0";
            ObjListaProductosEntity.orden_detalle_montosubtotal="0";
            ObjListaProductosEntity.orden_detalle_gal_acumulado="0";
            ObjListaProductosEntity.orden_detalle_descuentocontado="0";
            ObjListaProductosEntity.orden_detalle_terminopago_id=terminopago_id;
            //ObjListaProductosEntity.orden_detalle_porcentaje_descuento_maximo=productoAgregado.get
            ObjListaProductosEntity.orden_detalle_chk_descuentocontado_cabecera= descuentocontadocabecera;
            ObjListaProductosEntity.orden_detalle_cardcode= listaprecio_id;
            ObjListaProductosEntity.orden_detalle_porcentaje_descuento_maximo=productoAgregado.getPorcentaje_descuento_max();
            ObjListaProductosEntity.orden_detalle_currency=currency_id;
            ObjListaProductosEntity.orden_detalle_listnum =productoAgregado.getListnum();
            ObjListaProductosEntity.orden_detalle_price_listprice =productoAgregado.getPreciobase();
            ObjListaProductosEntity.orden_detalle_percent_discount_business_layer=discount_percent_bl;
            ObjListaProductosEntity.orden_detalle_units=productoAgregado.getUnits();
            ObjListaProductosEntity.orden_detalle_oil_tax= productoAgregado.getOiltax();
            ObjListaProductosEntity.orden_detalle_liter= productoAgregado.getLiter();
            ObjListaProductosEntity.orden_detalle_SIGAUS= productoAgregado.getSIGAUS();
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-productoAgregado.getOiltax(): "+productoAgregado.getOiltax());
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-productoAgregado.getLiter(): "+productoAgregado.getLiter());
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-productoAgregado.getSIGAUS(): "+productoAgregado.getSIGAUS());
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-productoAgregado.getUnits(): "+productoAgregado.getUnits());
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-discount_percent_bl: "+discount_percent_bl);
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-productoAgregado.getListnum():"+productoAgregado.getListnum());
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-currency_id:"+currency_id);
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-descuentocontado:"+String.valueOf(descuentocontado));
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-terminopago_id:"+String.valueOf(terminopago_id));
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-terminopago_id:"+String.valueOf(descuentocontadocabecera));
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-productoAgregado.getPorcentaje_dsct():"+String.valueOf(productoAgregado.getPorcentaje_dsct()));
            listadoProductosAgregados.add(ObjListaProductosEntity);
        //}
        hiloAgregarListaProductos.execute();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceAgregarListaPromocionCabecera(Object objeto) {
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);

        Object[] listaobjetos=new Object[2];
        listaobjetos=(Object[]) objeto;
        ArrayList<ListaPromocionCabeceraEntity> ListaPromocionCabeceraEntity = (ArrayList<ListaPromocionCabeceraEntity>) listaobjetos[0];
        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalleEntity= (ArrayList<ListaOrdenVentaDetalleEntity>) listaobjetos[1];

        for (int i=0;i<listadoProductosAgregados.size();i++){
            if(listadoProductosAgregados.get(i).getOrden_detalle_item().equals(ListaOrdenVentaDetalleEntity.get(0).getOrden_detalle_item())){
                listadoProductosAgregados.get(i).setOrden_detalle_lista_promocion_cabecera(ListaPromocionCabeceraEntity);
            }
        }
        hiloAgregarListaProductos.execute();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceActualizaLista(Object objeto) {

        ListenerBackPress.setCurrentFragment("OrdenVentaDetalleView");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalleEntity= (ArrayList<ListaOrdenVentaDetalleEntity>) objeto;

        for (int i=0;i<listadoProductosAgregados.size();i++){
            if(listadoProductosAgregados.get(i).getOrden_detalle_item().equals(ListaOrdenVentaDetalleEntity.get(0).getOrden_detalle_item())){
                listadoProductosAgregados.get(i).setOrden_detalle_promocion_habilitada(ListaOrdenVentaDetalleEntity.get(0).getOrden_detalle_promocion_habilitada());
            }
        }

        hiloAgregarListaProductos.execute();
        CargarEstadoItemsMenu();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceEliminaLineaOrdenVenta(Object objeto) {
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");

        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        int Position=(int) objeto;

        listadoProductosAgregados.remove(Position);

        for(int i=0;i<listadoProductosAgregados.size();i++)
        {
            listadoProductosAgregados.get(i).setOrden_detalle_item(String.valueOf(i+1));
        }
        hiloAgregarListaProductos.execute();
        CargarEstadoItemsMenu();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceActualizarResumen() {
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);

        ActualizarResumenMontos(tv_orden_venta_detalle_subtotal,tv_orden_venta_detalle_descuento,tv_orden_venta_detalle_igv,tv_orden_venta_detalle_total,tv_orden_detalle_galones);

        return ordenVentaDetalleView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listadoProductosAgregados =  new ArrayList<>();
        hiloAgregarListaProductos = new HiloAgregarListaProductos();
        hiloActualizarResumenMontos = new HiloActualizarResumenMontos();
        context=getContext();
        getActivity().setTitle(Induvis.getTituloVentaString(getContext()));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        v= inflater.inflate(R.layout.fragment_orden_venta_detalle_view_peru, container, false);
        fab_consulta_productos = v.findViewById(R.id.fab_consulta_productos);
        lv_ordenventadetalle = v.findViewById(R.id.lv_ordenventadetalle);
        tv_orden_venta_detalle_subtotal = v.findViewById(R.id.tv_orden_venta_detalle_subtotal);
        tv_orden_venta_detalle_descuento = v.findViewById(R.id.tv_orden_venta_detalle_descuento);
        tv_orden_venta_detalle_igv = v.findViewById(R.id.tv_orden_venta_detalle_igv);
        tv_orden_venta_detalle_total = v.findViewById(R.id.tv_orden_venta_detalle_total);
        tv_orden_detalle_galones = v.findViewById(R.id.tv_orden_detalle_galones);
        tr_summary_flete = v.findViewById(R.id.tr_summary_flete);
        tr_summary_flete.setVisibility(View.GONE);
        tr_detail_gallons = v.findViewById(R.id.tr_detail_gallons);

        ClienteAtendido cliente=new ClienteAtendido();
        cliente.setCardCode(listaprecio_id);//contiene CardCode
        cliente.setPymntGroup(terminopago_id);
        cliente.setUbigeo_ID(ubigeo_id);
        cliente.setCurrency_ID(currency_id);

        //Para solo induvis
        if(!BuildConfig.FLAVOR.equals("peru"))
        {
            tr_detail_gallons.setVisibility(View.GONE);
        }

        fab_consulta_productos.setOnClickListener(view -> {

            String Fragment="OrdenVentaDetalleView";
            String accion="producto";
            String compuesto=Fragment+"-"+accion;
            Vendedor vendedor=new Vendedor();
            vendedor.setCliente(cliente);
            mListener.onFragmentInteraction(compuesto,vendedor);
        });

        hiloAgregarListaProductos.execute();
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        //Log.e("jpcm","se regresoooo EERTTT");
        //ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
    }

    @Override
    public void onAttach(Context context) {
        //ListenerBackPress.setCurrentFragment("OrdenVentaDetalleView");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private class HiloAgregarListaProductos extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Procesando...", true, false);
        }

        @Override
        protected String doInBackground(String... arg0) {
            try {
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result){
            getActivity().setTitle(Induvis.getTituloVentaString(getContext())+" detalle");

            switch (BuildConfig.FLAVOR)
            {
                case "peru":
                case "bolivia":
                case "ecuador":
                case "chile":
                case "espania":
                case "marruecos":
                case "perurofalab":
                case "paraguay":
                    if(SesionEntity.quotation.equals("N"))
                    {
                        listaOrdenVentaDetalleAdapter = new ListaOrdenVentaDetalleAdapter(getActivity(), ListaOrdenVentaDetalleDao.getInstance().getLeads(listadoProductosAgregados));
                    }
                    else {
                        listSalesOrderDetailAdapter = new ListSalesOrderDetailAdapter(getActivity(), ListaOrdenVentaDetalleDao.getInstance().getLeads(listadoProductosAgregados));
                    }
                    break;
            }

            if(lv_ordenventadetalle!=null)
            {
                switch (BuildConfig.FLAVOR)
                {
                    case "peru":
                    case "bolivia":
                    case "ecuador":
                    case "chile":
                    case "espania":
                    case "marruecos":
                    case "perurofalab":
                    case "paraguay":
                        if (SesionEntity.quotation.equals("N")) {
                            lv_ordenventadetalle.setAdapter(listaOrdenVentaDetalleAdapter);
                        } else {
                            lv_ordenventadetalle.setAdapter(listSalesOrderDetailAdapter);
                        }
                        break;
                }
                hiloAgregarListaProductos = new HiloAgregarListaProductos();

                ActualizarResumenMontos(tv_orden_venta_detalle_subtotal, tv_orden_venta_detalle_descuento, tv_orden_venta_detalle_igv, tv_orden_venta_detalle_total, tv_orden_detalle_galones);

                setHasOptionsMenu(true);
            }
            else {
                Toast.makeText(getContext(), "Consultar nuevamente el Cliente:", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }

    //ESTA FUNCION MUESTRA EL CALCULADO EN ORDEN VENTA DETALLE, NO EN CABECERA
    public static void ActualizarResumenMontos(TextView textSubTotal,TextView textSescuento,TextView textIgv,TextView textTotal,TextView textGalones){
        FormulasController formulasController=new FormulasController(context);
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();

        listaOrdenVentaDetalleEntities=formulasController.ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
        TotalSalesOrder salesOrder=formulasController.CalcularMontosPedidoCabeceraDetallePromocion(listaOrdenVentaDetalleEntities);

        textSubTotal.setText(""+Convert.currencyForView(salesOrder.getSubtotal()));
        textSescuento.setText(""+Convert.currencyForView(salesOrder.getDescuento()));
        textIgv.setText(""+Convert.currencyForView(salesOrder.getIgv()));
        textTotal.setText(""+Convert.currencyForView(salesOrder.getTotal()));
        textGalones.setText(""+ Convert.amountForTwoDecimal(salesOrder.getGalones()));
    }

    private class HiloActualizarResumenMontos extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            return "true";
        }

        protected void onPostExecute(Object result){

            hiloActualizarResumenMontos =  new HiloActualizarResumenMontos();
            ActualizarResumenMontos(tv_orden_venta_detalle_subtotal,tv_orden_venta_detalle_descuento,tv_orden_venta_detalle_igv,tv_orden_venta_detalle_total,tv_orden_detalle_galones);
            setHasOptionsMenu(true);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_orden_venta_detalle, menu);
        vincular_orden_venta_cabecera = menu.findItem(R.id.vincular_orden_venta_cabecera);
        menu_variable=menu;
        CargarEstadoItemsMenu();

        super.onCreateOptionsMenu(menu, inflater);

    }

    static public void CargarEstadoItemsMenu()
    {
        if(listadoProductosAgregados.isEmpty())
        {
            if(!(menu_variable == null))
            {
                Drawable drawable = menu_variable.findItem(R.id.vincular_orden_venta_cabecera).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.vincular_orden_venta_cabecera).setIcon(drawable);
                vincular_orden_venta_cabecera.setEnabled(false);
            }
        }else
        {
            for(int i=0;i<listadoProductosAgregados.size();i++)
            {
                if(!(listadoProductosAgregados.get(i).getOrden_detalle_cantidad().equals("0")))
                {
                    if(!(menu_variable == null)) {
                        if((menu_variable.findItem(R.id.vincular_orden_venta_cabecera).getIcon()) == null)
                        {

                        }else
                        {
                            Drawable drawable = menu_variable.findItem(R.id.vincular_orden_venta_cabecera).getIcon();
                            drawable = DrawableCompat.wrap(drawable);
                            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.white));
                            menu_variable.findItem(R.id.vincular_orden_venta_cabecera).setIcon(drawable);
                            vincular_orden_venta_cabecera.setEnabled(true);
                        }

                    }
                }
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*switch (item.getItemId()) {
            case R.id.vincular_orden_venta_cabecera:
                DialogoOrdenVentaDetalle().show();

                return false;
            default:
                break;
        }
        return false;*/
        switch (item.getItemId()) {
            case R.id.vincular_orden_venta_cabecera:

                switch (BuildConfig.FLAVOR)
                {
                    case "bolivia":
                    case "india":
                    case "chile":
                    case "peru":
                    case "paraguay":
                    case "perurofalab":
                    case "espania":
                    case "marruecos":
                        DialogoOrdenVentaDetalle().show();
                        break;
                    case "ecuador":
                        FormulasController formulasController=new FormulasController(getContext());
                        if (formulasController.ValidarMontoEnCero(listadoProductosAgregados)) {
                            alertaValidacionMontoCero().show();
                        } else {
                            UsuarioSQLite usuarioSQLite = new UsuarioSQLite(getContext());
                            UsuarioSQLiteEntity usuarioSQLiteEntity = new UsuarioSQLiteEntity();
                            int SIGAUS = 0, OilTax = 0;
                            usuarioSQLiteEntity = usuarioSQLite.ObtenerUsuarioSesion();
                            Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-usuarioSQLiteEntity.getOiltaxstatus(): " + usuarioSQLiteEntity.getOiltaxstatus());
                            if (usuarioSQLiteEntity.getOiltaxstatus().equals("Y")) {
                                Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-listadoProductosAgregados.size(): " + listadoProductosAgregados.size());
                                for (int i = 0; i < listadoProductosAgregados.size(); ++i) {
                                    //Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size(): " + listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size());
                                    if (listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion() != null) {
                                        Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size(): " + listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size());
                                        for (int j = 0; j < listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().size(); j++) {
                                            Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().get(j).getOrden_detalle_producto_id(): " + listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().get(j).getOrden_detalle_producto_id());
                                            if (listadoProductosAgregados.get(i).getOrden_detalle_lista_orden_detalle_promocion().get(j).getOrden_detalle_producto_id().equals("ECOVALOR")) {
                                                SIGAUS++;
                                            }
                                        }
                                    }
                                    Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-listadoProductosAgregados.get(i).getOrden_detalle_oil_tax(): " + listadoProductosAgregados.get(i).getOrden_detalle_oil_tax());
                                    if (listadoProductosAgregados.get(i).getOrden_detalle_oil_tax().equals("Y")) {
                                        OilTax++;
                                    }
                                }
                                Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-SIGAUS: " + SIGAUS);
                                Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-OilTax: " + OilTax);
                                if (OilTax == 0) {
                                    DialogoOrdenVentaDetalle().show();
                                } else {
                                    if (SIGAUS > 0) {
                                        Float cantidad=0f;
                                        String cantidadEcovalor="0";
                                        int position=0;
                                        for(int j=0;j<listadoProductosAgregados.size();j++)
                                        {
                                            if(listadoProductosAgregados.get(j).getOrden_detalle_oil_tax() .equals("Y"))
                                            {
                                                cantidad=cantidad+ (Float.parseFloat(listadoProductosAgregados.get(j).getOrden_detalle_gal_acumulado()));
                                            }
                                            if(listadoProductosAgregados.get(j).getOrden_detalle_producto_id().equals("ECOVALOR"))
                                            {
                                                position=j;
                                                cantidadEcovalor=listadoProductosAgregados.get(j).getOrden_detalle_cantidad();
                                            }

                                        }

                                        Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-cantidadGalonesSIGAUSactual: " + cantidad);
                                        Log.e("REOS", "OrdenVentaDetalleView-onOptionsItemSelected-cantidadEcovalor: " + cantidadEcovalor);

                                        if(Float.parseFloat(cantidadEcovalor)==cantidad)
                                        {
                                            DialogoOrdenVentaDetalle().show();
                                        }
                                        else {
                                            newInstanceEliminaLineaOrdenVenta(position);
                                            if(!formulasController.addSIGAUS(listadoProductosAgregados).isEmpty())
                                            {
                                                DialogoOrdenVentaDetalle().show();
                                            }

                                        }

                                    } else {
                                        //calculateSIGAUS();
                                        alertAddSIGAUS().show();
                                    }
                                }

                            } else {

                                DialogoOrdenVentaDetalle().show();
                            }
                        }
                        break;
                }
                return false;
            default:
                break;
        }
        return false;
    }

    public Dialog DialogoOrdenVentaDetalle() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_alert_dialog_orden_venta_detalle);
        TextView textTitle = dialog.findViewById(R.id.text_orden_venta_detalle);
        textTitle.setText("ADVERTENCIA");
        final TextView textMsj = dialog.findViewById(R.id.textViewMsj_orden_venta_detalle);
        textMsj.setText("Seguro de Finalizar el Ingreso de Lineas?");
        ImageView image = (ImageView) dialog.findViewById(R.id.image_orden_venta_detalle);

        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK_orden_venta_detalle);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel_orden_venta_detalle);
        dialogButton.setText("ACEPTAR");
        dialogButtonCancel.setText("CANCELAR");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(v -> {
            FormulasController formulasController=new FormulasController(getContext());
            if (formulasController.ValidarMontoEnCero(listadoProductosAgregados)) {
                alertaValidacionMontoCero().show();
            } else {

                String Fragment = "OrdenVentaDetalleView";
                String accion = "ordenventacabecera";
                String compuesto = Fragment + "-" + accion;
                mListener.onFragmentInteraction(compuesto, listadoProductosAgregados);

                dialog.dismiss();
            }
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  dialog;
    }

    public Dialog alertaValidacionMontoCero() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Tiene una Linea en Monto 0");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText("OK");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  dialog;
    }

    public Dialog alertAddSIGAUS() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Se generara la Linea de ECOVALOR, reintente el vincular para finalizar");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText("OK");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateSIGAUS();
                dialog.dismiss();
            }
        });
        return  dialog;
    }

    public void calculateSIGAUS(){
        FormulasController formulasController=new FormulasController(getContext());
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
        //listaOrdenVentaDetalleEntities=formulasController.ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
        //formulasController.addSIGAUS(listaOrdenVentaDetalleEntities,listadoProductosAgregados);
        if(!formulasController.addSIGAUS(listadoProductosAgregados).isEmpty())
        {
            hiloAgregarListaProductos.execute();
            //DialogoOrdenVentaDetalle().show();
        }

    }


}
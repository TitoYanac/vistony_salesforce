package com.vistony.salesforce.View;

import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Adapters.ListaOrdenVentaDetalleAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.Adapters.ListaOrdenVentaDetalleDao;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdenVentaDetalleView#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    static String listaprecio_id,descuentocontado,terminopago_id;
    static Context context;
    public OrdenVentaDetalleView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment OrdenVentaDetalleView.
     */
    // TODO: Rename and change types and number of parameters
    public static OrdenVentaDetalleView newInstanceEnviaListaPromocion (Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        String Fragment="OrdenVentaDetalleView";
        String accion="listadopromocion";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstance(Object objeto) {
        String objetostring=(String) objeto;

        String[] compuesto = objetostring.split("-");
        if(compuesto[0]!=null||compuesto[1]!=null||compuesto[2]!=null)
        {
            listaprecio_id=compuesto[0];
            descuentocontado=compuesto[1];
            terminopago_id=compuesto[2];
        }
        else
            {
                listaprecio_id=compuesto[0];
                descuentocontado="false";
            }

        Log.e("REOS", "OrdenVentaDetalleView-listaprecio_id:" + listaprecio_id);
        Log.e("REOS", "OrdenVentaDetalleView-descuentocontado:" + descuentocontado);
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
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
        ArrayList<ListaProductoEntity> listadoProductosConversion=new ArrayList<>();
        ListaOrdenVentaDetalleEntity ObjListaProductosEntity=new ListaOrdenVentaDetalleEntity();
        listadoProductosConversion=(ArrayList<ListaProductoEntity>)objeto;
        String indice="0";
        for (int i=0;i<listadoProductosConversion.size();i++)
        {
            if(listadoProductosAgregados.isEmpty())
            {

                indice="1";
            }
            else
                {
                    indice=String.valueOf(listadoProductosAgregados.size()+1);
                }
            ObjListaProductosEntity.orden_detalle_item=(indice);
            ObjListaProductosEntity.orden_detalle_producto_id=listadoProductosConversion.get(i).getProducto_id();
            ObjListaProductosEntity.orden_detalle_producto=listadoProductosConversion.get(i).getProducto();
            ObjListaProductosEntity.orden_detalle_umd=listadoProductosConversion.get(i).getUmd();
            ObjListaProductosEntity.orden_detalle_stock=listadoProductosConversion.get(i).getStock();
            ObjListaProductosEntity.orden_detalle_precio_unitario=listadoProductosConversion.get(i).getPreciobase();
            ObjListaProductosEntity.orden_detalle_gal=listadoProductosConversion.get(i).getGal();
            ObjListaProductosEntity.orden_detalle_monto_igv="0";
            //Cambio
            ObjListaProductosEntity.orden_detalle_cantidad="";
            //
            ObjListaProductosEntity.orden_detalle_monto_descuento="0";
            ObjListaProductosEntity.orden_detalle_montototallinea="0";
            ObjListaProductosEntity.orden_detalle_promocion_habilitada="0";
            ObjListaProductosEntity.orden_detalle_lista_promocion_cabecera=null;
            ObjListaProductosEntity.orden_detalle_porcentaje_descuento="0";
            ObjListaProductosEntity.orden_detalle_montosubtotal="0";
            ObjListaProductosEntity.orden_detalle_gal_acumulado="0";
            ObjListaProductosEntity.orden_detalle_descuentocontado=descuentocontado;
            ObjListaProductosEntity.orden_detalle_terminopago_id=terminopago_id;
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-descuentocontado:"+String.valueOf(descuentocontado));
            Log.e("REOS","OrdenVentaDetalleView-newInstanceAgregarProducto-terminopago_id:"+String.valueOf(terminopago_id));
            listadoProductosAgregados.add(ObjListaProductosEntity);
        }
        hiloAgregarListaProductos.execute();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceAgregarListaPromocionCabecera(Object objeto) {
        //Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);

        Object[] listaobjetos=new Object[2];
        listaobjetos=(Object[]) objeto;
        ArrayList<ListaPromocionCabeceraEntity> ListaPromocionCabeceraEntity = (ArrayList<ListaPromocionCabeceraEntity>) listaobjetos[0];
        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalleEntity= (ArrayList<ListaOrdenVentaDetalleEntity>) listaobjetos[1];

        for (int i=0;i<listadoProductosAgregados.size();i++)
        {
            if(listadoProductosAgregados.get(i).getOrden_detalle_item().equals(ListaOrdenVentaDetalleEntity.get(0).getOrden_detalle_item()))
            {
                listadoProductosAgregados.get(i).setOrden_detalle_lista_promocion_cabecera(ListaPromocionCabeceraEntity);
            }
        }
        hiloAgregarListaProductos.execute();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceActualizaLista(Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        ArrayList<ListaOrdenVentaDetalleEntity> ListaOrdenVentaDetalleEntity= (ArrayList<ListaOrdenVentaDetalleEntity>) objeto;

        for (int i=0;i<listadoProductosAgregados.size();i++)
        {
            if(listadoProductosAgregados.get(i).getOrden_detalle_item().equals(ListaOrdenVentaDetalleEntity.get(0).getOrden_detalle_item()))
            {
                listadoProductosAgregados.get(i).setOrden_detalle_promocion_habilitada(ListaOrdenVentaDetalleEntity.get(0).getOrden_detalle_promocion_habilitada());
            }
        }

        hiloAgregarListaProductos.execute();
        CargarEstadoItemsMenu();
        return ordenVentaDetalleView;
    }

    public static OrdenVentaDetalleView newInstanceEliminaLineaOrdenVenta(Object objeto) {
        OrdenVentaDetalleView ordenVentaDetalleView = new OrdenVentaDetalleView();
        Bundle b = new Bundle();
        ordenVentaDetalleView.setArguments(b);
        int Position=(int) objeto;
        Log.e("REOS", "antes remove listadoProductosAgregados.size():" + listadoProductosAgregados.size());
        listadoProductosAgregados.remove(Position);
        Log.e("REOS", "despues remove listadoProductosAgregados.size():" + listadoProductosAgregados.size());
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
        //int Position=(int) objeto;
        //listadoProductosAgregados.remove(Position);
        ActualizarResumenMontos();
//        hiloActualizarResumenMontos.execute();
        //CargarEstadoItemsMenu();
        return ordenVentaDetalleView;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listadoProductosAgregados =  new ArrayList<>();
        hiloAgregarListaProductos = new HiloAgregarListaProductos();
        hiloActualizarResumenMontos = new HiloActualizarResumenMontos();
        context=getContext();
        getActivity().setTitle("Orden Venta Detalle");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        v= inflater.inflate(R.layout.fragment_orden_venta_detalle_view, container, false);
        fab_consulta_productos = v.findViewById(R.id.fab_consulta_productos);
        lv_ordenventadetalle = v.findViewById(R.id.lv_ordenventadetalle);
        tv_orden_venta_detalle_subtotal = v.findViewById(R.id.tv_orden_venta_detalle_subtotal);
        tv_orden_venta_detalle_descuento = v.findViewById(R.id.tv_orden_venta_detalle_descuento);
        tv_orden_venta_detalle_igv = v.findViewById(R.id.tv_orden_venta_detalle_igv);
        tv_orden_venta_detalle_total = v.findViewById(R.id.tv_orden_venta_detalle_total);
        tv_orden_detalle_galones = v.findViewById(R.id.tv_orden_detalle_galones);
        fab_consulta_productos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


                String Fragment="OrdenVentaDetalleView";
                String accion="producto";
                String compuesto=Fragment+"-"+accion;
                String Objeto=listaprecio_id;
                mListener.onFragmentInteraction(compuesto,Objeto);
            }
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
        protected String doInBackground(String... arg0) {
            try {
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
            getActivity().setTitle("Orden Venta Detalle");
            listaOrdenVentaDetalleAdapter = new ListaOrdenVentaDetalleAdapter(getActivity(), ListaOrdenVentaDetalleDao.getInstance().getLeads(listadoProductosAgregados));
            lv_ordenventadetalle.setAdapter(listaOrdenVentaDetalleAdapter);
            hiloAgregarListaProductos =  new HiloAgregarListaProductos();
            FormulasController formulasController=new FormulasController(getContext());
            String [] listaTotalesPedidosCabecera=new String[4];
            ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
            Log.e("REOS","OrdenVentaDetalleView-HiloAgregarListaProductos-listadoProductosAgregados.size(): "+listadoProductosAgregados.size());
            listaOrdenVentaDetalleEntities=formulasController.ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
            Log.e("REOS","OrdenVentaDetalleView-HiloAgregarListaProductos-listaOrdenVentaDetalleEntities.size(): "+listaOrdenVentaDetalleEntities.size());
            //listadoProductosAgregados=formulasController.ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
            //String [] MontosTotales=formulasController.CalcularMontosPedidoCabecera(listaOrdenVentaDetalleEntities);
            listaTotalesPedidosCabecera=formulasController.CalcularMontosPedidoCabeceraDetallePromocion(listaOrdenVentaDetalleEntities);
            //listaTotalesPedidosCabecera=formulasController.CalcularMontosPedidoCabecera(listadoProductosAgregados);
            tv_orden_venta_detalle_subtotal.setText(String.valueOf(listaTotalesPedidosCabecera[0]));
            tv_orden_venta_detalle_descuento.setText(String.valueOf(listaTotalesPedidosCabecera[1]));
            tv_orden_venta_detalle_igv.setText(String.valueOf(listaTotalesPedidosCabecera[2]));
            tv_orden_venta_detalle_total.setText(String.valueOf(listaTotalesPedidosCabecera[3]));
            tv_orden_detalle_galones.setText(String.valueOf(listaTotalesPedidosCabecera[4]));
            setHasOptionsMenu(true);


        }
    }

    static public void ActualizarResumenMontos()
    {
        FormulasController formulasController=new FormulasController(context);
        String [] listaTotalesPedidosCabecera=new String[4];
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
        Log.e("REOS","OrdenVentaDetalleView-ActualizarResumenMontos-listadoProductosAgregados.size(): "+listadoProductosAgregados.size());
        listaOrdenVentaDetalleEntities=formulasController.ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
        Log.e("REOS","OrdenVentaDetalleView-ActualizarResumenMontos-listaOrdenVentaDetalleEntities.size(): "+listaOrdenVentaDetalleEntities.size());
        //String [] MontosTotales=formulasController.CalcularMontosPedidoCabecera(listaOrdenVentaDetalleEntities);
        listaTotalesPedidosCabecera=formulasController.CalcularMontosPedidoCabeceraDetallePromocion(listaOrdenVentaDetalleEntities);
        //listadoProductosAgregados=formulasController.ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
        //listaTotalesPedidosCabecera=formulasController.CalcularMontosPedidoCabecera(listadoProductosAgregados);
        tv_orden_venta_detalle_subtotal.setText(String.valueOf(listaTotalesPedidosCabecera[0]));
        tv_orden_venta_detalle_descuento.setText(String.valueOf(listaTotalesPedidosCabecera[1]));
        tv_orden_venta_detalle_igv.setText(String.valueOf(listaTotalesPedidosCabecera[2]));
        tv_orden_venta_detalle_total.setText(String.valueOf(listaTotalesPedidosCabecera[3]));
        tv_orden_detalle_galones.setText(String.valueOf(listaTotalesPedidosCabecera[4]));
    }

    private class HiloActualizarResumenMontos extends AsyncTask<String, Void, Object> {

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

        protected void onPostExecute(Object result)
        {

            hiloActualizarResumenMontos =  new HiloActualizarResumenMontos();
            FormulasController formulasController=new FormulasController(getContext());
            String [] listaTotalesPedidosCabecera=new String[4];
            ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
            listaOrdenVentaDetalleEntities=formulasController.ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
            listaTotalesPedidosCabecera=formulasController.CalcularMontosPedidoCabeceraDetallePromocion(listaOrdenVentaDetalleEntities);
            //listadoProductosAgregados=formulasController.ActualizaciondeConversionListaOrdenDetallepoListaOrdenDetallePromocion(listadoProductosAgregados);
            tv_orden_venta_detalle_subtotal.setText(String.valueOf(listaTotalesPedidosCabecera[0]));
            tv_orden_venta_detalle_descuento.setText(String.valueOf(listaTotalesPedidosCabecera[1]));
            tv_orden_venta_detalle_igv.setText(String.valueOf(listaTotalesPedidosCabecera[2]));
            tv_orden_venta_detalle_total.setText(String.valueOf(listaTotalesPedidosCabecera[3]));
            tv_orden_detalle_galones.setText(String.valueOf(listaTotalesPedidosCabecera[4]));
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
        switch (item.getItemId()) {
            case R.id.vincular_orden_venta_cabecera:
                         DialogoOrdenVentaDetalle().show();

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
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK_orden_venta_detalle);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel_orden_venta_detalle);
        dialogButton.setText("ACEPTAR");
        dialogButtonCancel.setText("CANCELAR");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FormulasController formulasController=new FormulasController(getContext());
                if(formulasController.ObtenerCantidadLineasListaOrdenVentaDetalleEntity(listadoProductosAgregados)>15)
                {
                    Toast.makeText(getContext(), "La Cantidad de Lineas, Supera las 15 Lineas!!! Revisar la Cantidad de Lineas", Toast.LENGTH_SHORT).show();
                }else
                    {
                        if (formulasController.ValidarMontoEnCero(listadoProductosAgregados)) {
                            alertaValidacionMontoCero().show();
                        } else {
                            String Fragment = "OrdenVentaDetalleView";
                            String accion = "ordenventacabecera";
                            String compuesto = Fragment + "-" + accion;
                            mListener.onFragmentInteraction(compuesto, listadoProductosAgregados);
                            dialog.dismiss();
                        }
                    }
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


}
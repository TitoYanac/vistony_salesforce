package com.vistony.salesforce.View;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListaPromocionCabeceraAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.Adapters.ListaPromocionCabeceraDao;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromocionCabeceraView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromocionCabeceraView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_1 = "listadopromocionfiltrado";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static ListView lv_listapromociones;
    static OnFragmentInteractionListener mListener;
    MenuItem vincular,deshacer;
    static ArrayList<ListaPromocionCabeceraEntity> Listado= new ArrayList();
    static ArrayList<ListaPromocionCabeceraEntity> ListadoInicial= new ArrayList();
    static HiloObtenerPromocionCabecera hiloObtenerPromocionCabecera;
    ListaPromocionCabeceraAdapter listaPromocionCabeceraAdapter;
    public static ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities= new ArrayList<>();
    static Menu menu_variable;
    static Context context;
    public static TextView tv_cantidad_disponible,tv_cantidad_promocion,tv_cantidad_pendiente;
    private FragmentManager fragmentManager;
    PromocionCabeceraView promocionCabeceraView;
    static FrameLayout listapromocioncabecera;
    static ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntities=new ArrayList<>();
    FloatingActionButton fab_add_promotionhead;
    String cantidad="",currency="",cardcode="",terminopago_id="",producto_id="",producto="";
    static UsuarioSQLiteEntity ObjUsuario;

    public PromocionCabeceraView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PromocionCabeceraView.
     */
    // TODO: Rename and change types and number of parameters
    public static PromocionCabeceraView newInstance(Object objeto) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        Bundle b = new Bundle();
        Object[] listaobjetos=new Object[2];
        listaobjetos=(Object[]) objeto;
        ArrayList<PromocionCabeceraSQLiteEntity> Lista = (ArrayList<PromocionCabeceraSQLiteEntity>) listaobjetos[0];
        listaOrdenVentaDetalleEntities= (ArrayList<ListaOrdenVentaDetalleEntity>) listaobjetos[1];
        Lista.size();
        b.putSerializable(TAG_1,Lista);
        fragment.setArguments(b);
        return fragment;
    }

    public static PromocionCabeceraView newEditarDetallePromocion(Object objeto) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        String Fragment="PromocionCabeceraView";
        String accion="editar_detalle";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);
        return fragment;
    }

    public static PromocionCabeceraView newEditarDetallePromocionDescuento(Object objeto) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        String Fragment="PromocionCabeceraView";
        String accion="editar_descuento";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);
        return fragment;
    }


    public static PromocionCabeceraView newInstanceActivarVincular(String objeto) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Drawable drawable = menu_variable.findItem(R.id.vincular).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.white));
        menu_variable.findItem(R.id.vincular).setIcon(drawable);
        menu_variable.findItem(R.id.vincular).setEnabled(true);
        Drawable drawable2 = menu_variable.findItem(R.id.deshacer).getIcon();
        drawable2 = DrawableCompat.wrap(drawable2);
        DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.white));
        menu_variable.findItem(R.id.deshacer).setIcon(drawable2);
        menu_variable.findItem(R.id.deshacer).setEnabled(true);
        //lv_listapromociones.setClickable(false);
        //lv_listapromociones.setEnabled(false);
        //lv_listapromociones.setFocusable(false);
        //listapromocioncabecera.setEnabled(false);
        //listapromocioncabecera.setClickable(false);
        //listapromocioncabecera.setFocusable(false);
        return fragment;
    }
    public static PromocionCabeceraView newInstanceDesactivarVincular(String objeto) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Drawable drawable = menu_variable.findItem(R.id.vincular).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
        menu_variable.findItem(R.id.vincular).setIcon(drawable);
        menu_variable.findItem(R.id.vincular).setEnabled(false);
        Drawable drawable2 = menu_variable.findItem(R.id.deshacer).getIcon();
        drawable2 = DrawableCompat.wrap(drawable2);
        DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
        menu_variable.findItem(R.id.deshacer).setIcon(drawable2);
        menu_variable.findItem(R.id.deshacer).setEnabled(false);
        return fragment;
    }

    public static PromocionCabeceraView newInstanceIncrementar(
            String cantidadVenta,String CantidadPromocional,String descuento
    ) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        String solicitado=tv_cantidad_promocion.getText().toString();
        solicitado=String.valueOf(Integer.parseInt(solicitado)+Integer.parseInt(cantidadVenta));
        String pendiente= String.valueOf(Integer.parseInt(tv_cantidad_pendiente.getText().toString()) -Integer.parseInt(cantidadVenta));

        Log.e("REOS", "PromocionCabeceraView-newInstanceIncrementar-cantidadVenta:" + cantidadVenta);
        Log.e("REOS", "PromocionCabeceraView-newInstanceIncrementar-CantidadPromocional:" + CantidadPromocional);
        Log.e("REOS", "PromocionCabeceraView-newInstanceIncrementar-descuento:" + descuento);
        /*for(int i=0;i<Listado.size();i++)
        {
            if(Listado.get(i).getCantidadcompra().equals(cantidadVenta))
            {

                Listado.get(i).setDescuento(descuento);
                Listado.get(i).setCantidadpromocion(CantidadPromocional);
            }
        }

        for(int i=0;i<listaPromocionCabeceraEntities.size();i++)
        {
            if(listaPromocionCabeceraEntities.get(i).getCantidadcompra().equals(cantidadVenta))
            {

                listaPromocionCabeceraEntities.get(i).setDescuento(descuento);
                listaPromocionCabeceraEntities.get(i).setCantidadpromocion(CantidadPromocional);
            }
        }*/

        tv_cantidad_promocion.setText(solicitado);
        tv_cantidad_pendiente.setText(pendiente);
        return fragment;
    }
    public static PromocionCabeceraView newInstanceDecrementar(
            String cantidadVenta,String CantidadPromocional,String descuento
    ) {
        PromocionCabeceraView fragment = new PromocionCabeceraView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        Log.e("REOS", "PromocionCabeceraView-newInstanceDecrementar-cantidadVenta:" + cantidadVenta);
        Log.e("REOS", "PromocionCabeceraView-newInstanceDecrementar-CantidadPromocional:" + CantidadPromocional);
        Log.e("REOS", "PromocionCabeceraView-newInstanceDecrementar-descuento:" + descuento);
        /*for(int i=0;i<Listado.size();i++)
        {
            if(Listado.get(i).getCantidadcompra().equals(cantidadVenta))
            {
                Listado.get(i).setDescuento(descuento);
                Listado.get(i).setCantidadpromocion(CantidadPromocional);
            }
        }
        for(int i=0;i<listaPromocionCabeceraEntities.size();i++)
        {
            if(listaPromocionCabeceraEntities.get(i).getCantidadcompra().equals(cantidadVenta))
            {

                listaPromocionCabeceraEntities.get(i).setDescuento(descuento);
                listaPromocionCabeceraEntities.get(i).setCantidadpromocion(CantidadPromocional);
            }
        }*/
        String solicitado=tv_cantidad_promocion.getText().toString();
        solicitado=String.valueOf(Integer.parseInt(solicitado)-Integer.parseInt(cantidadVenta));
        String pendiente= String.valueOf(Integer.parseInt(tv_cantidad_pendiente.getText().toString()) +Integer.parseInt(cantidadVenta));
        tv_cantidad_promocion.setText(solicitado);
        tv_cantidad_pendiente.setText(pendiente);
        return fragment;
    }

    public static PromocionCabeceraView UpdatePromotionHead (Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        PromocionCabeceraView listadoPromocionView = new PromocionCabeceraView();
        Bundle b = new Bundle();
        listadoPromocionView.setArguments(b);
        Log.e("REOS", "PromocionCabeceraView:UpdatePromotionHead.listaPromocionCabeceraEntities.size()-antes " + listaPromocionCabeceraEntities.size());
        listaPromocionCabeceraEntities= (ArrayList<ListaPromocionCabeceraEntity>)  objeto;
        Log.e("REOS", "PromocionCabeceraView:UpdatePromotionHead.listaPromocionCabeceraEntities.size()-despues " + listaPromocionCabeceraEntities.size());

        for(int i=0;i<listaPromocionCabeceraEntities.size();i++)
        {
            Log.e("REOS", "PromocionCabeceraView-UpdatePromotionHead.listaPromocionCabeceraEntities.get(i).getCantidadcompra());: " + listaPromocionCabeceraEntities.get(i).getCantidadcompra());
        }
        return listadoPromocionView;
    }

    public static PromocionCabeceraView newInstanceEditarPromocionDetalle (Object objeto) {
        PromocionCabeceraView listadoPromocionView = new PromocionCabeceraView();
        ArrayList<ListaPromocionCabeceraEntity> listadoPromocionCabeceraEntity=(ArrayList<ListaPromocionCabeceraEntity>)  objeto;
        Log.e("REOS","PromocionCabeceraView:Listado:"+Listado.size());
        Log.e("REOS","PromocionCabeceraView:listadoPromocionCabeceraEntity:"+listadoPromocionCabeceraEntity.size());
        String promocion_id="",descuento="";
        ArrayList<PromocionDetalleSQLiteEntity> listPromocionDetalleSQLite=new ArrayList<>();
        for(int j=0;j<listadoPromocionCabeceraEntity.size();j++)
        {
            promocion_id=listadoPromocionCabeceraEntity.get(j).getPromocion_id();
            descuento=listadoPromocionCabeceraEntity.get(j).getDescuento();
            listPromocionDetalleSQLite=listadoPromocionCabeceraEntity.get(j).getListaPromocionDetalleEntities();
        }
        for(int i=0;i<Listado.size();i++)
        {
            if(Listado.get(i).getPromocion_id().equals(promocion_id))
            {
                Listado.get(i).setDescuento(descuento);
                if(SesionEntity.quotation.equals("Y"))
                {
                    //Listado.get(i).setPromocion_id(promocion_id+"_M");
                }
                Listado.get(i).setListaPromocionDetalleEntities(listPromocionDetalleSQLite);
            }
                /*if(listadoPromocionCabeceraEntity.get(i).getPromocion_id().equals(Listado.get(j).getPromocion_id()))
                {
                    Listado.get(j).setDescuento(listadoPromocionCabeceraEntity.get(i).getDescuento());
                    Listado.get(j).setListaPromocionDetalleEntities(listadoPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities());
                }*/
        }


        hiloObtenerPromocionCabecera.execute();
        //listaPromocionCabeceraEntities= (ArrayList<ListaPromocionCabeceraEntity>)  objeto;

        return listadoPromocionView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        if(BuildConfig.FLAVOR.equals("peru"))
        {
            if(SesionEntity.quotation.equals("Y"))
            {
                if(ObjUsuario.getU_VIS_ManagementType().equals("B2C"))
                {
                    getActivity().setTitle(getActivity().getResources().getString(R.string.exception_request));
                }else {
                    getActivity().setTitle(getActivity().getResources().getString(R.string.menu_promotion));
                }
            }else {
                getActivity().setTitle(getActivity().getResources().getString(R.string.menu_promotion));
            }
        }else {
            getActivity().setTitle(getActivity().getResources().getString(R.string.menu_promotion));
        }


        context=getContext();
        hiloObtenerPromocionCabecera= new HiloObtenerPromocionCabecera();
        if (getArguments() != null) {
            if(!((ArrayList<PromocionCabeceraSQLiteEntity>)getArguments().getSerializable(TAG_1)==null))
            {
                Listado = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_1);
                ListadoInicial = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_1);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_promocion_cabecera_view, container, false);
        tv_cantidad_disponible=(TextView) v.findViewById(R.id.tv_cantidad_disponible);
        tv_cantidad_promocion=(TextView) v.findViewById(R.id.tv_cantidad_promocion);
        tv_cantidad_pendiente=(TextView) v.findViewById(R.id.tv_cantidad_pendiente);
        listapromocioncabecera = (FrameLayout) v.findViewById(R.id.listapromocioncabecera);
        fab_add_promotionhead=v.findViewById(R.id.fab_add_promotionhead);
        ObtenerResumen();
        lv_listapromociones = (ListView) v.findViewById(R.id.lv_listapromociones);
        FormulasController formulasController=new FormulasController(getContext());

        switch (BuildConfig.FLAVOR)
        {
            case "peru":
                if(SesionEntity.quotation.equals("Y"))
                {
                    if(ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
                    {
                        fab_add_promotionhead.setVisibility(View.GONE);
                    }
                }else {
                    fab_add_promotionhead.setVisibility(View.GONE);
                }
                break;
            case "bolivia":
                if(SesionEntity.quotation.equals("Y"))
                {

                }else
                {
                    fab_add_promotionhead.setVisibility(View.GONE);
                }
                break;
            default:
                fab_add_promotionhead.setVisibility(View.GONE);
                break;
        }
        /*if(BuildConfig.FLAVOR.equals("peru"))
        {
            if(SesionEntity.quotation.equals("Y"))
            {
                if(ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
                {
                    fab_add_promotionhead.setVisibility(View.GONE);
                }
            }else {
                fab_add_promotionhead.setVisibility(View.GONE);
            }
        }else {
            fab_add_promotionhead.setVisibility(View.GONE);
        }*/


        fab_add_promotionhead.setOnClickListener(view -> {
            ListaPromocionCabeceraEntity objListaPromocionCabecera = new ListaPromocionCabeceraEntity();
            objListaPromocionCabecera.setLista_promocion_id("0000");
            objListaPromocionCabecera.setPromocion_id(formulasController.ObtenerFechaHoraCadena());
            objListaPromocionCabecera.setDescuento("0");
            objListaPromocionCabecera.setCantidadcompra(cantidad);
            objListaPromocionCabecera.setCurrency_id(currency);
            objListaPromocionCabecera.setCardcode(cardcode);
            objListaPromocionCabecera.setTerminopago_id(terminopago_id);
            objListaPromocionCabecera.setCantidadpromocion("0");
            objListaPromocionCabecera.setProducto(producto);
            objListaPromocionCabecera.setProducto_id(producto_id);
            objListaPromocionCabecera.setCount(String.valueOf(Listado.size()));
            objListaPromocionCabecera.setListaPromocionDetalleEntities(new ArrayList<PromocionDetalleSQLiteEntity>());
            Listado.add(objListaPromocionCabecera);
            hiloObtenerPromocionCabecera.execute();
        });

        hiloObtenerPromocionCabecera.execute();
        return v;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    private void ObtenerResumen()
    {

        for(int i=0;i<listaOrdenVentaDetalleEntities.size();i++)
        {
            cantidad=listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad();
            currency=listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_currency();
            cardcode=listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cardcode();
            terminopago_id=listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_terminopago_id();
            producto=listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_producto();
            producto_id=listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_producto_id();
        }
        tv_cantidad_disponible.setText(cantidad);
        tv_cantidad_pendiente.setText(cantidad);
        tv_cantidad_promocion.setText("0");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("jpcm","se regresoooo EERTTT");
        ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
    }

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("ConfigSistemaView");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    private class HiloObtenerPromocionCabecera extends AsyncTask<String, Void, Object> {

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
            listaPromocionCabeceraAdapter = new ListaPromocionCabeceraAdapter(getActivity(), ListaPromocionCabeceraDao.getInstance().getLeads(Listado));
            lv_listapromociones.setAdapter(listaPromocionCabeceraAdapter);
            //lv_listapromociones.setNestedScrollingEnabled(true);
            hiloObtenerPromocionCabecera = new HiloObtenerPromocionCabecera();
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_promocion_cabecera, menu);
        vincular = menu.findItem(R.id.vincular);
        deshacer = menu.findItem(R.id.deshacer);
        menu_variable=menu;
        fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceDesactivarVincular(""));

        if(SesionEntity.flagquerystock.equals("Y"))
        {
            Drawable drawable = menu_variable.findItem(R.id.vincular).getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.white));
            menu_variable.findItem(R.id.vincular).setIcon(drawable);
            menu_variable.findItem(R.id.vincular).setEnabled(true);
            Drawable drawable2 = menu_variable.findItem(R.id.deshacer).getIcon();
            drawable2 = DrawableCompat.wrap(drawable2);
            DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
            menu_variable.findItem(R.id.deshacer).setIcon(drawable2);
            menu_variable.findItem(R.id.deshacer).setEnabled(false);
        }
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vincular:
                String lista_promocion_id="";
                for(int l=0;l<listaPromocionCabeceraEntities.size();l++)
                {
                    lista_promocion_id=listaPromocionCabeceraEntities.get(l).getLista_promocion_id();
                }

                if(SesionEntity.flagquerystock.equals("N"))
                {
                    if(SesionEntity.quotation.equals("Y"))
                    {
                        if (lista_promocion_id.equals("0000"))
                        {
                            int nocumple = 0;
                            String promocion_id = "",descuento="";
                            for (int i = 0; i < listaPromocionCabeceraEntities.size(); i++) {
                                PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(getContext());
                                promocion_id = promocionCabeceraSQLiteDao.getPromotionHeaderException(
                                        listaPromocionCabeceraEntities.get(i).producto_id,
                                        listaPromocionCabeceraEntities.get(i).getCantidadcompra());
                                if (promocion_id != null && !promocion_id.equals("")) {
                                    for (int j = 0; j < listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().size(); j++) {
                                        int resultadodetail = 0;
                                        PromocionDetalleSQLiteDao promocionDetalleSQLiteDao = new PromocionDetalleSQLiteDao(getContext());
                                        Log.e("REOS", "PromocionCabeceraView-onOptionsItemSelected-listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().getOrden_detalle_producto_id():" + listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().get(j).getProducto_id());
                                        Log.e("REOS", "PromocionCabeceraView-onOptionsItemSelected-listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().getOrden_detalle_cantidad():" + listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().get(j).getCantidad());
                                        Log.e("REOS", "PromocionCabeceraView-onOptionsItemSelected-promocion_id:" + promocion_id);
                                        resultadodetail = promocionDetalleSQLiteDao.getPromotionDetailException(
                                                promocion_id,
                                                listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().get(j).getCantidad(),
                                                listaPromocionCabeceraEntities.get(i).getListaPromocionDetalleEntities().get(j).getProducto_id()
                                        );
                                        if (resultadodetail == 0) {
                                            nocumple++;
                                        }

                                        Log.e("REOS", "PromocionCabeceraView-onOptionsItemSelected-resultadodetail:" + resultadodetail);
                                    }
                                } else {
                                    nocumple++;
                                    //Toast.makeText(getContext(), "No Paso 1era Validacion de Cabecera", Toast.LENGTH_LONG).show();
                                }
                                //Log.e("REOS", "PromocionCabeceraView-onOptionsItemSelected-vincular-resultado:" + resultado);
                            }
                            if (nocumple > 0 && BuildConfig.FLAVOR.equals("peru")) {
                                Toast.makeText(getContext(), "No cumple con las reglas de excepción", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getContext(), "Si cumple con las reglas de excepcion!!!!", Toast.LENGTH_LONG).show();
                                for(int i=0;i<listaPromocionCabeceraEntities.size();i++)
                                {
                                    listaPromocionCabeceraEntities.get(i).setPromocion_id(promocion_id);
                                }

                                String Fragment = "PromocionCabeceraView";
                                String accion = "listapromocioncabecera";
                                String compuesto = Fragment + "-" + accion;
                                Object[] listaobjetos = new Object[2];
                                listaobjetos[0] = listaPromocionCabeceraEntities;
                                Log.e("REOS", "PromocionCabeceraView:listaPromocionCabeceraEntities.size()" + listaPromocionCabeceraEntities.size());
                                listaobjetos[1] = listaOrdenVentaDetalleEntities;
                                Log.e("REOS", "PromocionCabeceraView:listaOrdenVentaDetalleEntities.size()" + listaOrdenVentaDetalleEntities.size());
                                mListener.onFragmentInteraction(compuesto, listaobjetos);
                            }
                        }else {
                            String Fragment = "PromocionCabeceraView";
                            String accion = "listapromocioncabecera";
                            String compuesto = Fragment + "-" + accion;
                            Object[] listaobjetos = new Object[2];
                            listaobjetos[0] = listaPromocionCabeceraEntities;
                            Log.e("REOS", "PromocionCabeceraView:listaPromocionCabeceraEntities.size()" + listaPromocionCabeceraEntities.size());
                            listaobjetos[1] = listaOrdenVentaDetalleEntities;
                            Log.e("REOS", "PromocionCabeceraView:listaOrdenVentaDetalleEntities.size()" + listaOrdenVentaDetalleEntities.size());
                            mListener.onFragmentInteraction(compuesto, listaobjetos);
                        }

                    }else
                    {
                        String Fragment = "PromocionCabeceraView";
                        String accion = "listapromocioncabecera";
                        String compuesto = Fragment + "-" + accion;
                        Object[] listaobjetos = new Object[2];
                        listaobjetos[0] = listaPromocionCabeceraEntities;
                        Log.e("REOS", "PromocionCabeceraView:listaPromocionCabeceraEntities.size()" + listaPromocionCabeceraEntities.size());
                        listaobjetos[1] = listaOrdenVentaDetalleEntities;
                        Log.e("REOS", "PromocionCabeceraView:listaOrdenVentaDetalleEntities.size()" + listaOrdenVentaDetalleEntities.size());
                        mListener.onFragmentInteraction(compuesto, listaobjetos);
                    }
                }
                else if(SesionEntity.flagquerystock.equals("Y"))
                {
                    if(SesionEntity.flagquerystockPromotion.equals("Y"))
                    {
                        String Fragment = "HistoricPromotionView";
                        String accion = "mostrarConsultaStock";
                        String compuesto = Fragment + "-" + accion;
                        String Objeto="";
                        mListener.onFragmentInteraction(compuesto, Objeto);

                    }else {
                        String Fragment = "ConsultaStockView";
                        String accion = "mostrarConsultaStock";
                        String compuesto = Fragment + "-" + accion;
                        String Objeto="";
                        mListener.onFragmentInteraction(compuesto, Objeto);
                    }

                }
                return false;
            case R.id.deshacer:
                Listado=ListadoInicial;
                hiloObtenerPromocionCabecera.execute();
                fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceDesactivarVincular(""));
                ObtenerResumen();
                return false;
            default:
                break;
        }
        return false;
    }
}
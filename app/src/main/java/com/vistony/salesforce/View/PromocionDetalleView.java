package com.vistony.salesforce.View;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Adapters.ListaPromocionDetalleEditarAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaPromocionDetalleEditarDao;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;
import com.vistony.salesforce.Sesion.ClienteAtendido;
import com.vistony.salesforce.Sesion.Vendedor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromocionDetalleView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromocionDetalleView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_PromocionDetalle="Editar";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListaPromocionDetalleEditarAdapter listaPromocionDetalleEditarAdapter;
    static OnFragmentInteractionListener mListener;
    static public ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity;
    //static public ArrayList<ListaPromocionCabeceraEntity> copiaeditablelistaPromocionCabeceraEntity;
    ListView list_promocion_detalle_editar;
    static HiloObtenerPromocionDetalle hiloObtenerPromocionDetalle;
    MenuItem vincular_promocion_detalle_editar,add_promocion_detalle_percent;
    static String cardcode,terminopago_id,currency_id;
    FloatingActionButton fab_add_promotiondetail;
    TextView txtdocumento;

    public PromocionDetalleView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PromocionDetalleView.
     */
    // TODO: Rename and change types and number of parameters
    public static PromocionDetalleView newInstance(Object object) {
        PromocionDetalleView fragment = new PromocionDetalleView();
        Bundle b = new Bundle();
        ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntities=new ArrayList<>();
        ListaPromocionCabeceraEntity ObjPromocionCabeceraEntity=(ListaPromocionCabeceraEntity)   object;
        listaPromocionCabeceraEntities.add(ObjPromocionCabeceraEntity);
        for(int i=0;i<listaPromocionCabeceraEntities.size();i++)
        {
            cardcode=listaPromocionCabeceraEntities.get(i).getCardcode();
            terminopago_id=listaPromocionCabeceraEntities.get(i).getTerminopago_id();
            currency_id=listaPromocionCabeceraEntities.get(i).getCurrency_id();
        }
        b.putSerializable(TAG_PromocionDetalle,listaPromocionCabeceraEntities);
        fragment.setArguments(b);
        return fragment;
    }

    public static PromocionDetalleView SendPromotionProduct() {
        PromocionDetalleView fragment = new PromocionDetalleView();
        Bundle b = new Bundle();
        String Fragment="PromocionDetalleView";
        String accion="producto";
        String compuesto=Fragment+"-"+accion;
        Vendedor vendedor=new Vendedor();
        ClienteAtendido cliente=new ClienteAtendido();
        cliente.setCardCode(cardcode);//contiene CardCode
        cliente.setPymntGroup(terminopago_id);
        cliente.setUbigeo_ID(currency_id);
        cliente.setCurrency_ID(currency_id);
        vendedor.setCliente(cliente);
        mListener.onFragmentInteraction(compuesto,vendedor);
        return fragment;
    }

    public static PromocionDetalleView newInstanceAgregarProducto(Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        PromocionDetalleView promocionDetalleView = new PromocionDetalleView();
        ListaProductoEntity productoAgregado=(ListaProductoEntity)objeto;
        Log.e("REOS","PromocionDetalleView-newInstanceAgregarProducto-productoAgregado.getProducto_id(): "+productoAgregado.getProducto_id());
        Log.e("REOS","PromocionDetalleView-newInstanceAgregarProducto-productoAgregado.getProducto(): "+productoAgregado.getProducto());

        for(int i=0;i<listaPromocionCabeceraEntity.size();i++)
        {
            for(int j=0;j<listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
            {
                if(listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).isStatusEdit())
                {
                    Log.e("REOS","PromocionDetalleView-newInstanceAgregarProducto-listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).isStatusEdit(): "+listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).isStatusEdit());
                    Log.e("REOS","PromocionDetalleView-newInstanceAgregarProducto-listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getProducto(): "+listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getProducto());
                    listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setProducto_id(productoAgregado.getProducto_id());
                    listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setProducto(productoAgregado.getProducto());
                    listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setUmd(productoAgregado.getUmd());
                    listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setStatusEdit(false);
                }

            }

        }
        hiloObtenerPromocionDetalle.execute();
        return promocionDetalleView;
    }

    public static PromocionDetalleView refreshList() {
        PromocionDetalleView fragment = new PromocionDetalleView();
        hiloObtenerPromocionDetalle.execute();
        return fragment;
    }

    public static PromocionDetalleView AddProductPromotionDetail(Object objeto) {
        PromocionDetalleView promocionDetalleView = new PromocionDetalleView();
        ListaProductoEntity productoAgregado=(ListaProductoEntity)objeto;
        Log.e("REOS","PromocionDetalleView-newInstanceAgregarProducto-productoAgregado.getProducto_id(): "+productoAgregado.getProducto_id());
        Log.e("REOS","PromocionDetalleView-newInstanceAgregarProducto-productoAgregado.getProducto(): "+productoAgregado.getProducto());
        int item_id=0;
        for(int i=0;i<listaPromocionCabeceraEntity.size();i++) {
                item_id=listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size()+1;
        }
        PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity=new PromocionDetalleSQLiteEntity();
        promocionDetalleSQLiteEntity.setProducto_id(productoAgregado.getProducto_id());
        promocionDetalleSQLiteEntity.setProducto(productoAgregado.getProducto());
        promocionDetalleSQLiteEntity.setUmd(productoAgregado.getUmd());
        promocionDetalleSQLiteEntity.setStatusEdit(false);
        promocionDetalleSQLiteEntity.setPreciobase(productoAgregado.getPreciobase());
        //promocionDetalleSQLiteEntity.setPromocion_id(String.valueOf(item_id));
        promocionDetalleSQLiteEntity.setPromocion_detalle_id(String.valueOf(item_id));
        promocionDetalleSQLiteEntity.setCantidad("1");
        promocionDetalleSQLiteEntity.setDescuento("100");

        for(int i=0;i<listaPromocionCabeceraEntity.size();i++)
        {
            listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().add(promocionDetalleSQLiteEntity);
        }

        hiloObtenerPromocionDetalle.execute();
        return promocionDetalleView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaPromocionCabeceraEntity=new ArrayList<>();
        hiloObtenerPromocionDetalle=new HiloObtenerPromocionDetalle();
        setHasOptionsMenu(true);
        //getActivity().setTitle("Detalle de Promoción");
        if(SesionEntity.quotation.equals("Y"))
        {
            getActivity().setTitle(getActivity().getResources().getString(R.string.exception_request));
        }else {
            getActivity().setTitle(getActivity().getResources().getString(R.string.menu_promotion));
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(!((ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_PromocionDetalle)==null))
            {
                listaPromocionCabeceraEntity = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_PromocionDetalle);
                //copiaeditablelistaPromocionCabeceraEntity = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_PromocionDetalle);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_promocion_detalle_view, container, false);
        list_promocion_detalle_editar=v.findViewById(R.id.list_promocion_detalle);
        fab_add_promotiondetail=v.findViewById(R.id.fab_add_promotiondetail);
        txtdocumento=v.findViewById(R.id.txtdocumento);
        if(SesionEntity.quotation.equals("Y"))
        {
            txtdocumento.setText("EDITAR SOLICITUD EXCEPCIÓN");
        }else {
            txtdocumento.setText("EDITAR PROMOCIÓN");
        }

        if(SesionEntity.quotation.equals("N"))
        {
            fab_add_promotiondetail.setVisibility(View.GONE);
        }

        fab_add_promotiondetail.setOnClickListener(view -> {
            String Fragment="PromocionDetalleView";
            String accion="producto_add";
            String compuesto=Fragment+"-"+accion;
            Vendedor vendedor=new Vendedor();
            ClienteAtendido cliente=new ClienteAtendido();
            cliente.setCardCode(cardcode);//contiene CardCode
            cliente.setPymntGroup(terminopago_id);
            cliente.setUbigeo_ID(currency_id);
            cliente.setCurrency_ID(currency_id);
            vendedor.setCliente(cliente);
            mListener.onFragmentInteraction(compuesto,vendedor);
        });

        hiloObtenerPromocionDetalle.execute();
        return v;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    private class HiloObtenerPromocionDetalle extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0)
        {
            try {
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
            hiloObtenerPromocionDetalle=new HiloObtenerPromocionDetalle();
            listaPromocionDetalleEditarAdapter = new ListaPromocionDetalleEditarAdapter(getActivity(), ListaPromocionDetalleEditarDao.getInstance().getLeads(listaPromocionCabeceraEntity));
            list_promocion_detalle_editar.setAdapter(listaPromocionDetalleEditarAdapter);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_promocion_detalle, menu);
        vincular_promocion_detalle_editar = menu.findItem(R.id.vincular_promocion_detalle_editar);
        add_promocion_detalle_percent = menu.findItem(R.id.add_promocion_detalle_percent);

        if(SesionEntity.quotation.equals("N"))
        {
            add_promocion_detalle_percent.setVisible(false);
            add_promocion_detalle_percent.setEnabled(false);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vincular_promocion_detalle_editar:
                String Fragment="PromocionDetalleView";
                String accion="editar";
                String compuesto=Fragment+"-"+accion;
                //mListener.onFragmentInteraction(compuesto,ObtenerListaPromocionDetalle());
                mListener.onFragmentInteraction(compuesto,listaPromocionCabeceraEntity);
                return false;
            case R.id.add_promocion_detalle_percent:
                Log.e("REOS","PromocionDetalleView-onOptionsItemSelected-add-ingreso:");
                boolean status=false;
                status=addPercentDescount();
                Log.e("REOS","PromocionDetalleView-onOptionsItemSelected-add-status:"+status);
                return false;
            default:
                break;
        }
        return false;
    }

    /*
    public ArrayList<ListaPromocionCabeceraEntity> ObtenerListaPromocionDetalle()
    {

        for (int i=0;i< listaPromocionCabeceraEntity.size();i++ )
        {
            listaPromocionCabeceraEntity.get(i).setDescuento(

                    copiaeditablelistaPromocionCabeceraEntity.get(i).getDescuento()

            );
            for(int j=0;j<listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
            {
                listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setCantidad(

                        copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getCantidad()

                );
            }

        }
        return  listaPromocionCabeceraEntity;
    }*/

    public boolean addPercentDescount()
    {
        boolean status=false;
        try
        {
            int item_id = 0;
            for (int i = 0; i < listaPromocionCabeceraEntity.size(); i++) {
                item_id = listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size() + 1;
            }
            PromocionDetalleSQLiteEntity promocionDetalleSQLiteEntity = new PromocionDetalleSQLiteEntity();
            promocionDetalleSQLiteEntity.setProducto_id("%");
            promocionDetalleSQLiteEntity.setProducto("DESCUENTO");
            promocionDetalleSQLiteEntity.setUmd("%");
            promocionDetalleSQLiteEntity.setStatusEdit(false);
            promocionDetalleSQLiteEntity.setPreciobase("0");
            //promocionDetalleSQLiteEntity.setPromocion_id(String.valueOf(item_id));
            promocionDetalleSQLiteEntity.setPromocion_detalle_id(String.valueOf(item_id));
            promocionDetalleSQLiteEntity.setCantidad("1");
            promocionDetalleSQLiteEntity.setDescuento("1");
            promocionDetalleSQLiteEntity.setChkdescuento("0");
            for (int i = 0; i < listaPromocionCabeceraEntity.size(); i++)
            {
                listaPromocionCabeceraEntity.get(i).setDescuento("1");
                //listaPromocionCabeceraEntity.get(i).setPromocion_id(listaPromocionCabeceraEntity.get(i).getPromocion_id()+"_M");
                listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().add(promocionDetalleSQLiteEntity);
            }

            hiloObtenerPromocionDetalle.execute();
            status=true;
        }catch (Exception e)
        {
            Log.e("REOS","PromocionDetalleView-addPercentDescount-e:"+e.toString());
        }
        return status;
    }

}
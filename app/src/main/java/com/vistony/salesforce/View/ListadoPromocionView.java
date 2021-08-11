package com.vistony.salesforce.View;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vistony.salesforce.Controller.Adapters.ListaListadoPromocionAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaListadoPromocionDao;
import com.vistony.salesforce.Dao.SQLIte.ListaPromocionSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaListadoPromocionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListadoPromocionView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListadoPromocionView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_1 = "listadopromocion";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static OnFragmentInteractionListener mListener;
    ListView lv_listadopromocion;
    HiloObtenerListadoPromocion hiloObtenerListadoPromocion;
    ListaListadoPromocionAdapter listaListadoPromocionAdapter;
    static ArrayList<ListaPromocionCabeceraEntity> Listado= new ArrayList();
    ArrayList<ListaPromocionSQLiteEntity> listaListadoPromocionSQLiteEntity=new ArrayList<>();
    static ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities= new ArrayList<>();

    public ListadoPromocionView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PromocionView.
     */
    // TODO: Rename and change types and number of parameters
    public static ListadoPromocionView newInstance(Object objeto) {
        ListadoPromocionView fragment = new ListadoPromocionView();
        Bundle b = new Bundle();
        Object[] listaobjetos=new Object[2];
        listaobjetos=(Object[]) objeto;
        ArrayList<PromocionCabeceraSQLiteEntity> Lista = (ArrayList<PromocionCabeceraSQLiteEntity>) listaobjetos[0];
        Log.e("REOS", "ListadoPromocionView:Lista.size(): " + Lista.size());
        listaOrdenVentaDetalleEntities= (ArrayList<ListaOrdenVentaDetalleEntity>) listaobjetos[1];
        Log.e("REOS", "ListadoPromocionView:listaOrdenVentaDetalleEntities.size(): " + listaOrdenVentaDetalleEntities.size());
        //Lista.size();
        b.putSerializable(TAG_1,Lista);
        fragment.setArguments(b);
        return fragment;
    }

    public static ListadoPromocionView newInstanceEnviaListaPromocion (Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());

        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        ListadoPromocionView listadoPromocionView = new ListadoPromocionView();
        Bundle b = new Bundle();
        ArrayList<ListaListadoPromocionEntity> arrayListaListadoPromocionEntity= (ArrayList<ListaListadoPromocionEntity>) objeto;
        String listapromocion_id="";
        for(int i=0;i<arrayListaListadoPromocionEntity.size();i++)
        {
            listapromocion_id=arrayListaListadoPromocionEntity.get(i).getId();
        }
        ArrayList<ListaPromocionCabeceraEntity> arrayListPromocionCabeceraSQliteEntityFiltrado=new ArrayList<>();

        for (int j=0;j<Listado.size();j++)
        {
            if(Listado.get(j).getLista_promocion_id().equals(listapromocion_id))
            {
                ListaPromocionCabeceraEntity listaPromocionCabeceraEntity=new ListaPromocionCabeceraEntity();

                listaPromocionCabeceraEntity.lista_promocion_id=Listado.get(j).getLista_promocion_id();
                listaPromocionCabeceraEntity.promocion_id=Listado.get(j).getPromocion_id();
                listaPromocionCabeceraEntity.producto_id=Listado.get(j).getProducto_id();
                listaPromocionCabeceraEntity.producto=Listado.get(j).getProducto();
                listaPromocionCabeceraEntity.umd=Listado.get(j).getUmd();
                listaPromocionCabeceraEntity.cantidadcompra =Listado.get(j).getCantidadcompra();
                listaPromocionCabeceraEntity.cantidadpromocion="0";
                listaPromocionCabeceraEntity.preciobase=Listado.get(j).getPreciobase();
                listaPromocionCabeceraEntity.descuento=Listado.get(j).getDescuento();
                listaPromocionCabeceraEntity.setListaPromocionDetalleEntities(Listado.get(j).getListaPromocionDetalleEntities());
                arrayListPromocionCabeceraSQliteEntityFiltrado.add(listaPromocionCabeceraEntity);

            }
        }

        listadoPromocionView.setArguments(b);
        String Fragment="ListadoPromocionView";
        String accion="promociondetalle";
        String compuesto=Fragment+"-"+accion;
        Object [] listaobjetos=new Object[2];
        listaobjetos[0]=arrayListPromocionCabeceraSQliteEntityFiltrado;
        listaobjetos[1]=listaOrdenVentaDetalleEntities;
        mListener.onFragmentInteraction(compuesto,listaobjetos);
        return listadoPromocionView;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Listado Promocion");
        hiloObtenerListadoPromocion = new HiloObtenerListadoPromocion();
        if (getArguments() != null) {
            Listado = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_1);
            if(!(Listado==null))
            {
                for(int i=0;i<Listado.size();i++)
                {
                    ListaPromocionSQLiteDao listaPromocionSQLiteDao= new ListaPromocionSQLiteDao(getContext());
                    ListaPromocionSQLiteEntity listaPromocionSQLiteEntity= new ListaPromocionSQLiteEntity();
                    Log.e("REOS", "ListadoPromocionView:Listado.get(i).getLista_promocion_id(): " + Listado.get(i).getLista_promocion_id());
                    listaPromocionSQLiteEntity=listaPromocionSQLiteDao.ObtenerListaPromocion(
                            SesionEntity.compania_id,
                            Listado.get(i).getLista_promocion_id()
                    );

                    listaListadoPromocionSQLiteEntity.add(listaPromocionSQLiteEntity);
                }
                Log.e("REOS", "ListadoPromocionView:listaPromocionSQLiteEntity.size(): " + listaListadoPromocionSQLiteEntity.size());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_promocion_view, container, false);
        lv_listadopromocion = (ListView) v.findViewById(R.id.lv_listadopromocion);
        hiloObtenerListadoPromocion =  new HiloObtenerListadoPromocion();
        hiloObtenerListadoPromocion.execute();
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
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


    private class HiloObtenerListadoPromocion extends AsyncTask<String, Void, Object> {

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
            listaListadoPromocionAdapter = new ListaListadoPromocionAdapter(getActivity(), ListaListadoPromocionDao.getInstance().getLeads(listaListadoPromocionSQLiteEntity));
            lv_listadopromocion.setAdapter(listaListadoPromocionAdapter);
        }
    }


}
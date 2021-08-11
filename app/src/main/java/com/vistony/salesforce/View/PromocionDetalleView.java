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

import com.vistony.salesforce.Controller.Adapters.ListaPromocionDetalleEditarAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaPromocionDetalleEditarDao;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

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
    ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity;
    static public ArrayList<ListaPromocionCabeceraEntity> copiaeditablelistaPromocionCabeceraEntity;
    ListView list_promocion_detalle_editar;
    HiloObtenerPromocionDetalle hiloObtenerPromocionDetalle;
    MenuItem vincular_promocion_detalle_editar;


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
        b.putSerializable(TAG_PromocionDetalle,listaPromocionCabeceraEntities);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        listaPromocionCabeceraEntity=new ArrayList<>();
        hiloObtenerPromocionDetalle=new HiloObtenerPromocionDetalle();
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(!((ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_PromocionDetalle)==null))
            {
                listaPromocionCabeceraEntity = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_PromocionDetalle);
                copiaeditablelistaPromocionCabeceraEntity = (ArrayList<ListaPromocionCabeceraEntity>)getArguments().getSerializable(TAG_PromocionDetalle);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_promocion_detalle_view, container, false);
        list_promocion_detalle_editar=v.findViewById(R.id.list_promocion_detalle);
        hiloObtenerPromocionDetalle.execute();
        return v;
    }

    /*
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
    }*/

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
            listaPromocionDetalleEditarAdapter = new ListaPromocionDetalleEditarAdapter(getActivity(), ListaPromocionDetalleEditarDao.getInstance().getLeads(listaPromocionCabeceraEntity));
            list_promocion_detalle_editar.setAdapter(listaPromocionDetalleEditarAdapter);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_promocion_detalle, menu);
        vincular_promocion_detalle_editar = menu.findItem(R.id.vincular_promocion_detalle_editar);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.vincular_promocion_detalle_editar:
                String Fragment="PromocionDetalleView";
                String accion="editar";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,ObtenerListaPromocionDetalle());
                return false;
            default:
                break;
        }
        return false;
    }

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
    }

}
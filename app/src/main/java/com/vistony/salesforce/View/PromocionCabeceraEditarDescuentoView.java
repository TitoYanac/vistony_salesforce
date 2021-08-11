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

import com.vistony.salesforce.Controller.Adapters.ListaPromocionCabeceraEditarDescuentoAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaPromocionCabeceraEditarDescuentoDao;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PromocionCabeceraEditarDescuentoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PromocionCabeceraEditarDescuentoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView list_promocion_detalle_editar_descuento;
    static OnFragmentInteractionListener mListener;
    private static final String TAG_PromocionDetalle="Editar";
    ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntity;
    static public ArrayList<ListaPromocionCabeceraEntity> copiaeditablelistaPromocionCabeceraEntity;
    ListaPromocionCabeceraEditarDescuentoAdapter listaPromocionCabeceraEditarDescuentoAdapter;
    HiloObtenerPromocionCabeceraEditarDescuento hiloObtenerPromocionCabeceraEditarDescuento;
    public PromocionCabeceraEditarDescuentoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PromocionCabeceraEditarDescuento.
     */
    // TODO: Rename and change types and number of parameters

    public static PromocionCabeceraEditarDescuentoView newInstance(Object object) {
        PromocionCabeceraEditarDescuentoView fragment = new PromocionCabeceraEditarDescuentoView();
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
        super.onCreate(savedInstanceState);
        hiloObtenerPromocionCabeceraEditarDescuento = new HiloObtenerPromocionCabeceraEditarDescuento();
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
        v= inflater.inflate(R.layout.fragment_promocion_cabecera_editar_descuento, container, false);
        list_promocion_detalle_editar_descuento=v.findViewById(R.id.list_promocion_detalle_editar_descuento);
        hiloObtenerPromocionCabeceraEditarDescuento.execute();

        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
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

    private class HiloObtenerPromocionCabeceraEditarDescuento extends AsyncTask<String, Void, Object> {

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
            listaPromocionCabeceraEditarDescuentoAdapter = new ListaPromocionCabeceraEditarDescuentoAdapter(getActivity(), ListaPromocionCabeceraEditarDescuentoDao.getInstance().getLeads(listaPromocionCabeceraEntity));
            list_promocion_detalle_editar_descuento.setAdapter(listaPromocionCabeceraEditarDescuentoAdapter);
        }
    }

}
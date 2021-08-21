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
import android.widget.SearchView;

import com.vistony.salesforce.Controller.Adapters.ListaConsClienteCabeceraAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaConsClienteCabeceraDao;
import com.vistony.salesforce.Dao.SQLIte.ClienteSQlite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsClienteView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsClienteView extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView listconscliente;
    public static OnFragmentInteractionListener mListener;
    //ListaClienteCabeceraAdapter listaClienteCabeceraAdapter;
    ObtenerConsultaCliente obtenerConsultaCliente;
    ListaConsClienteCabeceraAdapter listaConsClienteCabeceraAdapter;
    SearchView mSearchView;
    public ConsClienteView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsCliente.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsClienteView newInstance(String param1, String param2) {
        ConsClienteView fragment = new ConsClienteView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ConsClienteView newInstanciaEnviarCliente(Object param1) {
        ConsClienteView fragment = new ConsClienteView();
        String Fragment="ConsClienteView";
        String accion="agregarClienteNoRuta";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Consulta Clientes");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_cons_cliente, container, false);
        listconscliente=v.findViewById(R.id.listconscliente);
        obtenerConsultaCliente=new ObtenerConsultaCliente();
        obtenerConsultaCliente.execute();

        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public class ObtenerConsultaCliente extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... arg0) {
            ArrayList<ListaClienteCabeceraEntity> listaClienteCabeceraEntities=new ArrayList<>();
            try {
                ClienteSQlite clienteSQlite =new ClienteSQlite(getContext());
                listaClienteCabeceraEntities= clienteSQlite.ObtenerClientes();

            } catch (Exception e) {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return listaClienteCabeceraEntities;
        }

        protected void onPostExecute(Object result) {
            ArrayList<ListaClienteCabeceraEntity>Lista=(ArrayList<ListaClienteCabeceraEntity>) result;

            if(Lista.isEmpty())
            {

            }
            else
            {

                listaConsClienteCabeceraAdapter = new ListaConsClienteCabeceraAdapter(getActivity(), ListaConsClienteCabeceraDao.getInstance().getLeads(Lista));
                listconscliente.setAdapter(listaConsClienteCabeceraAdapter);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historico_cobranza, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search3);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);

    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Cliente");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaConsClienteCabeceraAdapter.filter(text);
        return true;
    }
}
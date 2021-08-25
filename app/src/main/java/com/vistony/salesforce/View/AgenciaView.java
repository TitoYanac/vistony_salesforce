package com.vistony.salesforce.View;

import android.content.Context;
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

import com.vistony.salesforce.Controller.Adapters.ListaAgenciaAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaAgenciaDao;
import com.vistony.salesforce.Dao.SQLite.AgenciaSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgenciaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgenciaView extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView lv_agencia;
    public static OnFragmentInteractionListener mListener;

    //Variables Carga SQLite
    ArrayList<AgenciaSQLiteEntity> listaAgenciaSQLiteEntity;
    AgenciaSQLiteEntity agenciaSQLiteEntity;
    AgenciaSQLiteDao agenciaSQLiteDao;
    ListaAgenciaAdapter listaAgenciaAdapter;
    private SearchView mSearchView;
    public AgenciaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AgenciaView.
     */
    // TODO: Rename and change types and number of parameters
    public static AgenciaView newInstance(Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        AgenciaView agenciaView = new AgenciaView();
        Bundle b = new Bundle();
        agenciaView.setArguments(b);
        return agenciaView;
    }

    public static AgenciaView newInstancia(Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        AgenciaView agenciaView = new AgenciaView();
        Bundle b = new Bundle();
        agenciaView.setArguments(b);

        String Fragment="AgenciaView";
        String accion="inicio";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);

        return agenciaView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Agencias");
        setHasOptionsMenu(true);
        agenciaSQLiteDao= new AgenciaSQLiteDao(getContext());
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_agencia_view, container, false);
        lv_agencia=v.findViewById(R.id.lv_agencia);
        cargarAgenciaSqlite();
        return v;
    }

    private void cargarAgenciaSqlite(){

        listaAgenciaSQLiteEntity=new ArrayList<>();
        listaAgenciaSQLiteEntity=agenciaSQLiteDao.ObtenerAgencia();
        listaAgenciaAdapter = new ListaAgenciaAdapter(getActivity(), ListaAgenciaDao.getInstance().getLeads(listaAgenciaSQLiteEntity));
        lv_agencia.setAdapter(listaAgenciaAdapter);

        //listcliente.setTextFilterEnabled(true);
        //setupSearchView();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_agencia, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_agencia);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Agencia");
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaAgenciaAdapter.filter(text);
        return true;
    }
}
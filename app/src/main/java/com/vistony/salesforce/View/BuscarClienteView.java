package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.Controller.Adapters.ListaConsClienteCabeceraAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaConsClienteCabeceraDao;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.OrdenVentaRepository;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BuscarClienteView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BuscarClienteView extends Fragment implements SearchView.OnQueryTextListener {

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
    private ProgressDialog pd;
    ListaConsClienteCabeceraAdapter listaConsClienteCabeceraAdapter;
    SearchView mSearchView;
    private ClienteRepository clienteRepository;
    static public String Flujo="";

    public BuscarClienteView() {
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
    public static BuscarClienteView newInstance(String param1, String param2) {
        Flujo="";
        BuscarClienteView fragment = new BuscarClienteView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static BuscarClienteView newInstanciaEnviarCliente(Object param1) {
        BuscarClienteView fragment = new BuscarClienteView();
        String Fragment="ConsClienteView";
        String accion="agregarClienteNoRuta";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    public static BuscarClienteView newInstanciaHistoricContainerSale(Object param1) {
        BuscarClienteView fragment = new BuscarClienteView();
        Flujo=(String) param1;
        return fragment;
    }

    public static BuscarClienteView newInstanciaEnviarClienteHistoricContainerSale(Object param1) {
        BuscarClienteView fragment = new BuscarClienteView();
        String Fragment="HistoricContainerSaleView";
        String accion="recibircliente";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }
    public static BuscarClienteView newInstanciaEnviarClienteHistoricContainerSKU(Object param1) {
        BuscarClienteView fragment = new BuscarClienteView();
        String Fragment="HistoricContainerSaleView";
        String accion="recibirclienteSKU";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    public static BuscarClienteView newInstanciaEnviarClienteQuotasPerCustomer(Object param1) {
        BuscarClienteView fragment = new BuscarClienteView();
        String Fragment="ConsClienteView";
        String accion="agregarquotaspercustomer";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    public static BuscarClienteView newInstanciaEnviarClienteQuotasPerCustomerDialog(Object param1) {
        BuscarClienteView fragment = new BuscarClienteView();
        String Fragment="ConsClienteView";
        String accion="agregarquotaspercustomerDialog";
        String compuesto=Fragment+"-"+accion;
        if(mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, param1);
        }
        return fragment;
    }

    public static BuscarClienteView newInstanceFlujoNoRuta(Object object) {
        BuscarClienteView fragment = new BuscarClienteView();
        Bundle args = new Bundle();
        Flujo="";
        fragment.setArguments(args);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_cons_cliente, container, false);
        listconscliente=v.findViewById(R.id.listconscliente);

        clienteRepository = new ViewModelProvider(this).get(ClienteRepository.class);
        AppExecutors executor=new AppExecutors();

        pd = new ProgressDialog(getActivity());
        pd = ProgressDialog.show(getActivity(), "Por favor espere", "Listando clientes", true, false);

        clienteRepository.getCustomerNotRoute(getContext(),executor.diskIO()).observe(getActivity(), data -> {
            if(data!=null){
                listaConsClienteCabeceraAdapter = new ListaConsClienteCabeceraAdapter(getActivity(), ListaConsClienteCabeceraDao.getInstance().getLeads(data));
                listconscliente.setAdapter(listaConsClienteCabeceraAdapter);
            }else{
                Toast.makeText(getContext(), "Actualiza tus parametros, no hay clientes disponibles", Toast.LENGTH_LONG).show();
                getActivity().getSupportFragmentManager().popBackStack();
            }
            pd.dismiss();
        });

        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
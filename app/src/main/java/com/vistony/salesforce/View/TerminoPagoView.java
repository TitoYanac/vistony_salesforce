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
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListaTerminoPagoAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaTerminoPagoDao;
import com.vistony.salesforce.Dao.SQLite.TerminoPagoSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TerminoPagoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TerminoPagoView extends Fragment implements SearchView.OnQueryTextListener   {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static OnFragmentInteractionListener mListener;
    View v;
    String dias_vencimiento;
    ListView listaterminopago;
    ArrayList<TerminoPagoSQLiteEntity> listaTerminoPagoSQLiteEntity;
    TerminoPagoSQLiteEntity terminoPagoSQLiteEntity;
    ListaTerminoPagoAdapter listaTerminoPagoAdapter;
    TerminoPagoSQLiteDao terminoPagoSQLiteDao;
    private SearchView mSearchView;

    public TerminoPagoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TerminoPagoView.
     */
    // TODO: Rename and change types and number of parameters
    public static TerminoPagoView newInstance(Object objeto) {
        String terminopago_id = "";
        terminopago_id = (String) objeto;
        TerminoPagoView fragment = new TerminoPagoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, terminopago_id);
        fragment.setArguments(args);

        return fragment;
    }

    public static TerminoPagoView newInstancia(Object objeto) {
        Log.e("jpcm", "regreso here 1 de " + ListenerBackPress.getCurrentFragment());
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        TerminoPagoView terminoPagoView = new TerminoPagoView();
        Bundle b = new Bundle();
        terminoPagoView.setArguments(b);

        String Fragment = "TerminoPagoView";
        String accion = "inicio";
        String compuesto = Fragment + "-" + accion;
        if (mListener!=null)
        {
            mListener.onFragmentInteraction(compuesto, objeto);
        }
        return terminoPagoView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getResources().getString(R.string.payment_terms));
        setHasOptionsMenu(true);
        terminoPagoSQLiteDao = new TerminoPagoSQLiteDao(getContext());
        if (getArguments() != null) {
            ArrayList<TerminoPagoSQLiteEntity> listaTerminopago = new ArrayList<>();
            TerminoPagoSQLiteDao terminoPagoSQLiteDao = new TerminoPagoSQLiteDao(getContext());
            mParam1 = getArguments().getString(ARG_PARAM1);
            listaTerminopago = terminoPagoSQLiteDao.ObtenerTerminoPagoporID(mParam1, SesionEntity.compania_id);
            Log.e("REOS","TerminoPagoView-onCreate-mParam1"+mParam1);
            for (int i = 0; i < listaTerminopago.size(); i++) {
                dias_vencimiento = listaTerminopago.get(i).getDias_vencimiento();
            }
            Log.e("REOS","TerminoPagoView-onCreate-dias_vencimiento"+dias_vencimiento);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_termino_pago_view, container, false);
        listaterminopago = v.findViewById(R.id.listaterminopago);
        cargarTerminoPagoSqlite();
        return v;
    }

    /*private void cargarTerminoPagoSqlite() {

        listaTerminoPagoSQLiteEntity = new ArrayList<>();
        //Log.e("REOS:DiasVencimiento",dias_vencimiento);
        listaTerminoPagoSQLiteEntity = terminoPagoSQLiteDao.ObtenerTerminoPago(SesionEntity.compania_id, dias_vencimiento);
        listaTerminoPagoAdapter = new ListaTerminoPagoAdapter(getActivity(), ListaTerminoPagoDao.getInstance().getLeads(listaTerminoPagoSQLiteEntity));
        listaterminopago.setAdapter(listaTerminoPagoAdapter);

        //listcliente.setTextFilterEnabled(true);
        //setupSearchView();
    }*/

    private void cargarTerminoPagoSqlite() {
        listaTerminoPagoSQLiteEntity = new ArrayList<>();
//        Log.e("REOS:DiasVencimiento",dias_vencimiento);
        listaTerminoPagoSQLiteEntity = terminoPagoSQLiteDao.ObtenerTerminoPago(SesionEntity.compania_id, dias_vencimiento);
        if (listaTerminoPagoSQLiteEntity.size() != 0)
        {
            listaTerminoPagoAdapter = new ListaTerminoPagoAdapter(getActivity(), ListaTerminoPagoDao.getInstance().getLeads(listaTerminoPagoSQLiteEntity));
            listaterminopago.setAdapter(listaTerminoPagoAdapter);
        }else
        {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.mse_not_data_available), Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
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

        inflater.inflate(R.menu.menu_termino_pago, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search_termino_pago);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint(getActivity().getResources().getString(R.string.find)+" "+getActivity().getResources().getString(R.string.payment_terms));
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(listaTerminoPagoAdapter!=null)
        {
        listaTerminoPagoAdapter.filter(newText);
        }
        return true;
    }
}
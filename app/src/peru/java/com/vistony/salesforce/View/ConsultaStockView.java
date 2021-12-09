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

import com.vistony.salesforce.Controller.Adapters.ListaConsultaStockAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaConsultaStockDao;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaConsultaStockEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultaStockView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaStockView extends Fragment  implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    private SearchView mSearchView;
    ListaConsultaStockAdapter listaConsultaStockAdapter;
    ListView lv_consultaStock;

    public ConsultaStockView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultaStockView.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultaStockView newInstance(String param1, String param2) {
        ConsultaStockView fragment = new ConsultaStockView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ConsultaStockView newInstanceEnviarPromocion(Object objeto) {
        ConsultaStockView fragment = new ConsultaStockView();
        Bundle args = new Bundle();
        String Fragment="ConsultaStockView";
        String accion="listadopromocion";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Consulta de Stock");
        SesionEntity.flagquerystock="Y";
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_consulta_stock_view, container, false);
        lv_consultaStock=(ListView) v.findViewById(R.id.lv_consultaStock);
        cargarConsultaStock();
        return v;
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

        inflater.inflate(R.menu.menu_consultastock, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Producto");
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaConsultaStockAdapter.filter(text);
        return true;
    }

    private void cargarConsultaStock()
    {

        ArrayList<ListaConsultaStockEntity>  ListaConsultaStockEntity=new ArrayList<>();
        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(getContext());
        ListaConsultaStockEntity=listaPrecioDetalleSQLiteDao.ObtenerConsultaStock(getContext());

        if(ListaConsultaStockEntity==null || ListaConsultaStockEntity.size()<0){
            Toast.makeText(getContext(),"Actualiza tus parametros, no hay productos disponibles", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }else{
            listaConsultaStockAdapter = new ListaConsultaStockAdapter(getActivity(), ListaConsultaStockDao.getInstance().getLeads(ListaConsultaStockEntity));
            lv_consultaStock.setAdapter(listaConsultaStockAdapter);
        }
    }
}
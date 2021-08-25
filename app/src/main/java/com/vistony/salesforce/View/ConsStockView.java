package com.vistony.salesforce.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;

import com.vistony.salesforce.Controller.Adapters.ListaStockAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaStockDao;
import com.vistony.salesforce.Dao.SQLite.StockSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.StockSQLiteEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsStockView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsStockView extends Fragment implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<StockSQLiteEntity> listaStockEntity;
    View v;
    StockSQLiteDao stockSQLiteDao;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lv_cons_stock;
    ListaStockAdapter listaStockAdapter;
    private SearchView mSearchView;
    public ConsStockView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsStockView.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsStockView newInstance(String param1, String param2) {
        ConsStockView fragment = new ConsStockView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Consulta Stock");
        setHasOptionsMenu(true);
        stockSQLiteDao=new StockSQLiteDao(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_cons_stock_view, container, false);
        lv_cons_stock=v.findViewById(R.id.lv_cons_stock);
        cargarStockSqlite();
        return v;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaStockAdapter.filter(text);
        return true;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    private void cargarStockSqlite(){

        listaStockEntity=new ArrayList<>();
        listaStockEntity=stockSQLiteDao.ObtenerStock();
        listaStockAdapter = new ListaStockAdapter(getActivity(), ListaStockDao.getInstance().getLeads(listaStockEntity));
        lv_cons_stock.setAdapter(listaStockAdapter);

        //listcliente.setTextFilterEnabled(true);
        //setupSearchView();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_producto, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search6);
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
}
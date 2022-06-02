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

import com.vistony.salesforce.Controller.Adapters.ListaProductoAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaProductoDao;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;
import com.vistony.salesforce.Sesion.Vendedor;

import java.util.ArrayList;


public class ProductoView extends Fragment  implements SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView lv_producto;
    ArrayList<com.vistony.salesforce.Entity.Adapters.ListaProductoEntity> ListaProductoEntity;
    ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao;
    ListaProductoAdapter listaProductoAdapter;
    public static OnFragmentInteractionListener mListener;
    static Vendedor vendedor;
    private SearchView mSearchView;

    public ProductoView() {
        // Required empty public constructor
    }

    public static ProductoView newInstance(Object objeto) {
        vendedor=(Vendedor) objeto;
        ListenerBackPress.setCurrentFragment("ProductoView");
        ProductoView productoView = new ProductoView();
        Bundle b = new Bundle();
        productoView.setArguments(b);
        return productoView;
    }

    public static ProductoView newInstancia(ListaProductoEntity objeto) {
        ListenerBackPress.setCurrentFragment("ProductoView");
        ProductoView productoView = new ProductoView();
        Bundle b = new Bundle();
        productoView.setArguments(b);

        String Fragment="ProductoView";
        String accion="inicio";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,objeto);

        return productoView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Productos");
        setHasOptionsMenu(true);
        listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_producto_view, container, false);
        lv_producto=v.findViewById(R.id.lv_producto);

        cargarProductosSqlite(
                vendedor.getCliente().getCardCode(),
                vendedor.getCliente().getPymntGroup(),
                vendedor.getCliente().getChkpricelist(),
                vendedor.getCliente().getPriceList_id(),
                vendedor.getCliente().getPriceList()
        );
        return v;
    }

    private void cargarProductosSqlite(
            String codigoCliente,
            String terminoPago,
            String chkpricelist,
            String pricelist_id,
            String pricelist)
    {
        Log.e("REOS","ProductoView.cargarProductosSqlite.codigoCliente: "+codigoCliente);
        Log.e("REOS","ProductoView.cargarProductosSqlite.terminoPago:"+terminoPago);
        Log.e("REOS","ProductoView.cargarProductosSqlite.chkpricelist:"+chkpricelist);
        if(chkpricelist.equals("true"))
        {
            ListaProductoEntity=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetallePriceList(codigoCliente,terminoPago,pricelist);
        }else
        {
            ListaProductoEntity=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalle(codigoCliente,terminoPago);
        }


        if(ListaProductoEntity==null || ListaProductoEntity.size()<0){
            Toast.makeText(getContext(),"Actualiza tus parametros, no hay productos disponibles", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }else{
            listaProductoAdapter = new ListaProductoAdapter(getActivity(), ListaProductoDao.getInstance().getLeads(ListaProductoEntity));
            lv_producto.setAdapter(listaProductoAdapter);
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
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        listaProductoAdapter.filter(text);
        return true;
    }

}
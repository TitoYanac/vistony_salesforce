package com.vistony.salesforce.View;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListaPriceListAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaTerminoPagoAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaPriceListDao;
import com.vistony.salesforce.Dao.Adapters.ListaTerminoPagoDao;
import com.vistony.salesforce.Dao.SQLite.PriceListSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.PriceListSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PriceListView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PriceListView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    ArrayList<PriceListSQLiteEntity> listaPriceSQLiteEntity;
    PriceListSQLiteDao priceListSQLiteDao;
    ListaPriceListAdapter listaPriceListAdapter;
    ListView lv_price_list;

    public PriceListView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment PriceListView.
     */
    // TODO: Rename and change types and number of parameters
    public static PriceListView newInstance(Object objeto) {
        PriceListView fragment = new PriceListView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static PriceListView newInstanceEnviarPriceList(Object objeto) {
        PriceListView fragment = new PriceListView();
        String Fragment = "PriceList";
        String accion = "enviarlead";
        String compuesto = Fragment + "-" + accion;
        mListener.onFragmentInteraction(compuesto, objeto);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        priceListSQLiteDao=new PriceListSQLiteDao(getContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_price_list_view, container, false);
        lv_price_list=(ListView) v.findViewById(R.id.lv_price_list);
        cargarPriceListqlite();
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void cargarPriceListqlite() {
        listaPriceSQLiteEntity = new ArrayList<>();
        listaPriceSQLiteEntity = priceListSQLiteDao.GetPriceList();
        if (listaPriceSQLiteEntity.size() != 0)
        {
            listaPriceListAdapter = new ListaPriceListAdapter(getActivity(), ListaPriceListDao.getInstance().getLeads(listaPriceSQLiteEntity));
            lv_price_list.setAdapter(listaPriceListAdapter);
        }else
        {
            Toast.makeText(getActivity(), "No hay Lista de Precios Disponibles", Toast.LENGTH_SHORT).show();
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

}
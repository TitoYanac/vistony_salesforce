package com.vistony.salesforce.View;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.vistony.salesforce.Dao.Adapters.ListaHojaDespachoDao;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoSQLiteEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HojaDespachoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HojaDespachoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static OnFragmentInteractionListener mListener;
    View v;
    ListView lista_despachos;
    Spinner spn_control_id;

    public HojaDespachoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HojaDespachoView.
     */
    // TODO: Rename and change types and number of parameters
    public static HojaDespachoView newInstance(String param1, String param2) {
        HojaDespachoView fragment = new HojaDespachoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        v= inflater.inflate(R.layout.fragment_hoja_despacho_view, container, false);
        lista_despachos = v.findViewById(R.id.lista_despachos);
        spn_control_id = v.findViewById(R.id.spn_control_id);
        //obtenerSQLiteHojaDespacho =new ObtenerSQLiteHojaDespacho();
        //obtenerSQLiteHojaDespacho.execute();
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

    public void CargaSpinnerControl(String [] control_id)
    {
        ArrayAdapter<String> adapter_control_id = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, control_id);
        spn_control_id.setAdapter(adapter_control_id);
    }

    public static boolean esValorUnico(String valor, ArrayList<HojaDespachoSQLiteEntity> arreglo) {
        int contador = 0;
        for (int x = 0; x < arreglo.size(); x++) {
            if (arreglo.get(x).getControl_id().equals(valor))
                contador++;
        }
        return contador == 1;
    }


}
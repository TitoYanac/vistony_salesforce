package com.vistony.salesforce.View;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuConfiguracionView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuConfiguracionView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView cv_configuracion_datos,cv_configuracion_impresora,cv_confiracion_sistema;
    OnFragmentInteractionListener mListener;
    View v;
    public MenuConfiguracionView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuConfiguracion.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuConfiguracionView newInstance(String param1, String param2) {
        MenuConfiguracionView fragment = new MenuConfiguracionView();
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
        switch (BuildConfig.FLAVOR){
            case "chile":
            case "ecuador":
            case "bolivia":
                v= inflater.inflate(R.layout.fragment_menu_configuracion_induvis, container, false);
                break;
            case "peru":
                v= inflater.inflate(R.layout.fragment_menu_configuracion, container, false);
                break;
        }

        cv_configuracion_datos=v.findViewById(R.id.cv_configuracion_datos);
        cv_configuracion_impresora=v.findViewById(R.id.cv_configuracion_impresora);
        cv_confiracion_sistema=v.findViewById(R.id.cv_confiracion_sistema);


        cv_configuracion_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConfiguracionView";
                String accion="parametros";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });

        cv_configuracion_impresora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConfiguracionView";
                String accion="impresora";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });

        cv_confiracion_sistema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConfiguracionView";
                String accion="sistema";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });

        if(BuildConfig.FLAVOR.equals("peru"))
        {
            if(SesionEntity.Print.equals("N"))
            {
                cv_configuracion_impresora.setVisibility(View.GONE);
            }
        }
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
        ListenerBackPress.setTemporaIdentityFragment("configuracion");
        ListenerBackPress.setCurrentFragment("MenuConfiguracionView");
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

}
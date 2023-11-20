package com.vistony.salesforce.View;

import android.content.Context;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuFormulariosView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFormulariosView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private CardView cv_catalogos,cv_agregarcliente,cv_reclamocliente,cv_form_supervisor;
    OnFragmentInteractionListener mListener;
    public MenuFormulariosView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFormularios.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFormulariosView newInstance(String param1, String param2) {
        MenuFormulariosView fragment = new MenuFormulariosView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getResources().getString(R.string.menu_forms));
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_menu_formularios, container, false);
        cv_catalogos=v.findViewById(R.id.cv_catalogos);
        cv_agregarcliente=v.findViewById(R.id.cv_agregarcliente);
        cv_reclamocliente=v.findViewById(R.id.cv_reclamocliente);
        cv_form_supervisor=v.findViewById(R.id.cv_form_supervisor);

        if(!BuildConfig.FLAVOR.equals("peru"))
        {
            cv_reclamocliente.setVisibility(View.GONE);
        }
        //cv_reclamocliente.setVisibility(View.GONE);
        cv_agregarcliente.setVisibility(View.GONE);
        cv_form_supervisor.setVisibility(View.GONE);

        cv_catalogos.setOnClickListener(v -> {
            String Fragment="MenuFormulariosView";
            String accion="catalogos";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,"");
        });

        cv_reclamocliente.setOnClickListener(v -> {
            String Fragment="MenuFormulariosView";
            String accion="reclamocliente";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,"");
        });

        cv_agregarcliente.setOnClickListener(v -> {
            String Fragment="MenuFormulariosView";
            String accion="agregarcliente";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,"");
        });

        cv_form_supervisor.setOnClickListener(v -> {
            String Fragment="MenuFormulariosView";
            String accion="formsupervisor";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,"");
        });

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
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
        ListenerBackPress.setCurrentFragment("MenuFormulariosView");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }else{
            throw new RuntimeException(context.toString()+ " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
        mListener = null;
    }
}
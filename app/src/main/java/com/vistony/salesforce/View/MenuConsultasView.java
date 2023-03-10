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
 * Use the {@link MenuConsultasView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuConsultasView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView cv_ordenventa,cv_documentofacturado,cv_consulta_cobrado,cv_consulta_deposito,cv_consulta_stock,cv_consulta_orden_venta_estado,cv_dispatch,cv_quotation;
    View v;
    OnFragmentInteractionListener mListener;
    public MenuConsultasView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuConsultasView.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuConsultasView newInstance(String param1, String param2) {
        MenuConsultasView fragment = new MenuConsultasView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getActivity().getResources().getString(R.string.menu_query));
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

            case "india":
                v= inflater.inflate(R.layout.fragment_menu_consultas_view_induvis, container, false);
                break;
            case "peru":
            case "paraguay":
            case "chile":
            case "ecuador":
            case "bolivia":
            case "perurofalab":
            case "espania":
            case "marruecos":
                v= inflater.inflate(R.layout.fragment_menu_consultas_view, container, false);
                break;
            default:
                break;
        }

        cv_ordenventa=v.findViewById(R.id.cv_ordenventa);
        cv_documentofacturado=v.findViewById(R.id.cv_documentofacturado);
        cv_consulta_cobrado=v.findViewById(R.id.cv_consulta_cobrado);
        cv_consulta_deposito=v.findViewById(R.id.cv_consulta_deposito);
        cv_dispatch=v.findViewById(R.id.cv_dispatch);
        cv_quotation=v.findViewById(R.id.cv_quotation);


        //cv_dispatch.setVisibility(View.GONE);
        if(SesionEntity.migratequotation.equals("N"))
        {
            cv_quotation.setVisibility(View.GONE);
        }
        else {
            cv_quotation.setVisibility(View.VISIBLE);
        }

        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
        {
            cv_ordenventa.setVisibility(View.GONE);
            cv_documentofacturado.setVisibility(View.GONE);
            //cv_dispatch.setVisibility(View.GONE);
            cv_quotation.setVisibility(View.GONE);
        }

        switch (BuildConfig.FLAVOR) {


            case "chile":

            case "marruecos":
                cv_dispatch.setVisibility(View.GONE);
                break;
            case "peru":
            case "ecuador":
            case "perurofalab":
            case "espania":
            case "bolivia":
            case "paraguay":
                break;
        }

        /*if(!(BuildConfig.FLAVOR.equals("peru")))
        {
            cv_dispatch.setVisibility(View.GONE);
        }
        else {
            if(SesionEntity.perfil_id.equals("VENDEDOR")||SesionEntity.perfil_id.equals("Vendedor"))
            {
                //cv_dispatch.setVisibility(View.GONE);
            }
        }*/

        cv_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConsultasView";
                //String accion="historicofacturas";
                String accion="menuconsultacotizacion";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });

        cv_ordenventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment,accion,compuesto;
                switch (BuildConfig.FLAVOR){
                    case "peru":
                    case "paraguay":
                    case "chile":
                    case "ecuador":
                    case "bolivia":
                    case "perurofalab":
                    case "espania":
                    case "marruecos":
                         Fragment="MenuConsultasView";
                         accion="menuconsultaspedidoview";
                         compuesto=Fragment+"-"+accion;
                        mListener.onFragmentInteraction(compuesto,"");
                        break;
                    //case "ecuador":
                    /*case "bolivia":

                         Fragment="MenuConsultasView";
                         accion="historicoordenventa";
                         compuesto=Fragment+"-"+accion;
                        mListener.onFragmentInteraction(compuesto,"");
                        break;*/
                }

            }
        });

        cv_documentofacturado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConsultasView";
                //String accion="historicofacturas";
                String accion="menuconsultasfacturaview";
                String compuesto=Fragment+"-"+accion;
               mListener.onFragmentInteraction(compuesto,"");
            }
        });
        cv_consulta_cobrado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment,accion,compuesto;
                switch (BuildConfig.FLAVOR){
                    case "peru":
                    case "paraguay":
                    case "chile":
                    case "ecuador":
                    case "bolivia":
                    case "perurofalab":
                    case "espania":
                    case "marruecos":
                        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
                        {
                            Fragment="HistoricoCobranzaView";
                             accion="COBRANZA";
                             compuesto=Fragment+"-"+accion;
                            mListener.onFragmentInteraction(compuesto,"");
                        }
                        else
                            {
                                Fragment="HistoricoCobranzaView";
                                accion="MENUCOBRANZA";
                                compuesto=Fragment+"-"+accion;
                                mListener.onFragmentInteraction(compuesto,"");
                            }

                        break;
                    //case "ecuador":
                    /*case "bolivia":

                         Fragment="HistoricoCobranzaView";
                         accion="COBRANZA";
                         compuesto=Fragment+"-"+accion;
                        mListener.onFragmentInteraction(compuesto,"");*/
                        //break;
                }

            }
        });

        cv_consulta_deposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="HistoricoDepositoView";
                String accion="nuevoinicioHistoricoDepositoView";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });
        /*cv_consulta_deposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="HistoricoDepositoView";
                String accion="nuevoinicioHistoricoDepositoView";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });*/
        cv_dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuConsultasView";
                String accion="dispatch";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,"");
            }
        });
        return v;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    /*
    @Override
    public void onDetach() {
        super.onDetach();

        Log.e("jpcm","se regresoooo EERTTT");
        ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
    }

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("ConfigSistemaView");
        super.onAttach(context);
        if (context instanceof MenuAccionView.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/
    @Override
    public void onDetach() {
        super.onDetach();
        ListenerBackPress.setCurrentFragment("MenuConsultasView");
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
    }

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("MenuConsultasView");
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
        super.onAttach(context);
        if (context instanceof MenuAccionView.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
}
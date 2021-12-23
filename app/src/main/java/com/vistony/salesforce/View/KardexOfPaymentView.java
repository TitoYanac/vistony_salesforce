package com.vistony.salesforce.View;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Adapters.ListKardexOfPaymentAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Adapters.ListKardexOfPaymentDao;
import com.vistony.salesforce.Dao.Adapters.ListaConsultaStockDao;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KardexOfPaymentView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KardexOfPaymentView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    //View
    View v;
    static ListView lv_kardex_of_payment;
    Button btn_find_client;
    static TextView tv_client;
    static KardexPagoRepository kardexPagoRepository;
    static Context context;
    static ListKardexOfPaymentAdapter listKardexOfPaymentAdapter;
    static String CardCode;
    static Activity activity;
    static LifecycleOwner lifecycleOwner;
    public static OnFragmentInteractionListener mListener;
    public KardexOfPaymentView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KardexOfPaymentView.
     */
    // TODO: Rename and change types and number of parameters
    public static KardexOfPaymentView newInstance(String param1, String param2) {
        KardexOfPaymentView fragment = new KardexOfPaymentView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static KardexOfPaymentView newInstanceRecibirLista (Object objeto) {
        KardexOfPaymentView fragment = new KardexOfPaymentView();
        Bundle args = new Bundle();

        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        MenuAccionView menuAccionView = new MenuAccionView();

        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;


        for(int s=0;s<Lista.size();s++){
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDireccion());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDomembarque_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDomfactura_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getCliente_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getZona_id());
            CardCode=Lista.get(s).getCliente_id();
            tv_client.setText(Lista.get(s).getNombrecliente());
        }
        getListKardexOfPayment(CardCode);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Kardex de Pago");
        kardexPagoRepository = new ViewModelProvider(getActivity()).get(KardexPagoRepository.class);
        context=getContext();
        activity=getActivity();
        lifecycleOwner=getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_kardex_of_payment_view, container, false);
        lv_kardex_of_payment=v.findViewById(R.id.lv_kardex_of_payment);
        btn_find_client=v.findViewById(R.id.btn_find_client);
        tv_client=v.findViewById(R.id.tv_client);

        btn_find_client.setOnClickListener(v -> {
            String Fragment="KardexOfPaymentView";
            String accion="findClient";
            String compuesto=Fragment+"-"+accion;
            String objeto="kardexofpayment";
            mListener.onFragmentInteraction(compuesto, objeto);
        });
        return v;
    }


    static private void getListKardexOfPayment(String CardCode)
    {
        kardexPagoRepository.getKardexPago(SesionEntity.imei, CardCode,context).observe(lifecycleOwner, data -> {
        Convert convert=new Convert();
            Log.e("Jepicame","=>"+data);

             listKardexOfPaymentAdapter
                    =new ListKardexOfPaymentAdapter(
                     activity,
                    ListKardexOfPaymentDao.getInstance().getLeads(convert.getConvertListKardexOfPayment(data)));
                    lv_kardex_of_payment.setAdapter(listKardexOfPaymentAdapter);
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }
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
        if (context instanceof MenuAccionView.OnFragmentInteractionListener) {
            super.onAttach(context);
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


}
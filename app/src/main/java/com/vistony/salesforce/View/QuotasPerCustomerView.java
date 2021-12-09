package com.vistony.salesforce.View;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.vistony.salesforce.Controller.Adapters.ListaQuotaPerCustomerDetailAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaQuotaPerCustomerHeadAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaQuotaPerCustomerDao;
import com.vistony.salesforce.Dao.Adapters.ListaQuotaPerCustomerDetailDao;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerDetailRepository;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerHeadRepository;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link QuotasPerCustomerView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuotasPerCustomerView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    public static OnFragmentInteractionListener mListener;
    MenuItem buscar_cliente;
    static public String CardCode="";
    static ListView lv_quotaspercustomerhead,lv_quotaspercustomerdetail;
    static Context context;
    static ListaQuotaPerCustomerHeadAdapter listaQuotasPerCustomerAdapter;
    static ListaQuotaPerCustomerDetailAdapter listaQuotaPerCustomerDetailAdapter;
    static QuotasPerCustomerHeadRepository quotasPerCustomerRepository;
    QuotasPerCustomerDetailRepository quotasPerCustomerDetailRepository;
    public QuotasPerCustomerView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuotasPerCustomer.
     */
    // TODO: Rename and change types and number of parameters
    public static QuotasPerCustomerView newInstance(String param1, String param2) {
        QuotasPerCustomerView fragment = new QuotasPerCustomerView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static QuotasPerCustomerView newInstanceRecibirCliente(Object object) {
        QuotasPerCustomerView fragment = new QuotasPerCustomerView();

        ArrayList<ListaClienteCabeceraEntity> Listado=(ArrayList<ListaClienteCabeceraEntity>) object;
        for(int i=0;i<Listado.size();i++)
        {
            CardCode=Listado.get(i).getCliente_id();
        }
        return fragment;
    }
    public static QuotasPerCustomerView newInstanceRecibirLista(Object object) {
        QuotasPerCustomerView fragment = new QuotasPerCustomerView();
        ListarQuotaPerCustomerHead((List<QuotasPerCustomerHeadEntity>) object);
        return fragment;
    }

    public static QuotasPerCustomerView newInstanceRecibirListaDetail(Object object) {
        QuotasPerCustomerView fragment = new QuotasPerCustomerView();
        ListarQuotaPerCustomerDetail((List<QuotasPerCustomerDetailEntity>) object);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context=getContext();
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
        v=inflater.inflate(R.layout.fragment_quotas_per_customer, container, false);
        lv_quotaspercustomerhead=(ListView) v.findViewById(R.id.lv_quotaspercustomerhead);
        lv_quotaspercustomerdetail=(ListView) v.findViewById(R.id.lv_quotaspercustomerdetail);
        quotasPerCustomerRepository = new ViewModelProvider(getActivity()).get(QuotasPerCustomerHeadRepository.class);
        quotasPerCustomerDetailRepository = new ViewModelProvider(getActivity()).get(QuotasPerCustomerDetailRepository.class);

        ///////////////////////////// BANCO SLITE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        quotasPerCustomerRepository.getQuotasPerCustomer(SesionEntity.fuerzatrabajo_id,getContext(),CardCode).observe(getActivity(), data -> {
            Log.e("Jepicame","=>"+data);
        });
        quotasPerCustomerDetailRepository.getQuotasPerCustomerDetail (SesionEntity.imei,getContext(),CardCode).observe(getActivity(), data -> {
            Log.e("REOS","QuotasPerCustomer.onCreateView.data"+data);
        });
        return v;
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

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_quotas_per_customer, menu);
        buscar_cliente = menu.findItem(R.id.buscar_cliente);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buscar_cliente:
                //alertaGetClient("Seleccione el Cliente:").show();
                String Fragment="QuotasPerCustomer";
                String accion="dialogoagregarcliente";
                String compuesto=Fragment+"-"+accion;
                String objeto="quotaspercustomer";
                mListener.onFragmentInteraction(compuesto, objeto);
                return false;
            default:
                break;
        }
        return false;
    }

    static public void ListarQuotaPerCustomerHead(List<QuotasPerCustomerHeadEntity> lista){

        listaQuotasPerCustomerAdapter = new ListaQuotaPerCustomerHeadAdapter(context,  ListaQuotaPerCustomerDao.getInstance().getLeads(lista));
        lv_quotaspercustomerhead.setAdapter(listaQuotasPerCustomerAdapter);
    }

    static public void ListarQuotaPerCustomerDetail(List<QuotasPerCustomerDetailEntity> lista){

        listaQuotaPerCustomerDetailAdapter = new ListaQuotaPerCustomerDetailAdapter (context,  ListaQuotaPerCustomerDetailDao.getInstance().getLeads(lista));
        lv_quotaspercustomerdetail.setAdapter(listaQuotaPerCustomerDetailAdapter);
    }

}
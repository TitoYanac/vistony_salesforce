package com.vistony.salesforce.View;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.Controller.Adapters.ListKardexOfPaymentAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Retrofit.LeadClienteViewModel;
import com.vistony.salesforce.Dao.SQLite.LeadSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadAddressEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultaGeolocalizacionView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultaGeolocalizacionView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ListView lv_find_geolocation;
    Button btn_find_client;
    static private ProgressDialog pd;
    static Context context;
    static Activity activity;
    static private LeadClienteViewModel leadClienteViewModel;
    static LifecycleOwner lifecycleOwner;

    public ConsultaGeolocalizacionView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuConsultaGeolocalizacionView.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultaGeolocalizacionView newInstance(String param1, String param2) {
        ConsultaGeolocalizacionView fragment = new ConsultaGeolocalizacionView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        activity=getActivity();
        leadClienteViewModel = new ViewModelProvider(this).get(LeadClienteViewModel.class);
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
        v= inflater.inflate(R.layout.fragment_menu_consulta_geolocalizacion_view, container, false);
        lv_find_geolocation=v.findViewById(R.id.lv_kardex_of_payment);
        btn_find_client=v.findViewById(R.id.btn_find_client);
        return v;
    }

    static private void getListSendGeolocation(String CardCode){
        LeadSQLite leadSQLite =new LeadSQLite(context);
        ArrayList<LeadAddressEntity> listLeadAddressEntity= leadSQLite.getGeolocationforClient(CardCode);



    }





    static private void getFindGeolocation(String CardCode)
    {
        /*AppExecutors executor=new AppExecutors();
        pd = new ProgressDialog(activity);
        pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Kardex de Pago de Cliente", true, false);
        leadClienteViewModel.sendGeolocationforClient(context, SesionEntity.imei,executor.diskIO(), CardCode).observe(lifecycleOwner, data -> {
            Convert convert=new Convert();
            Log.e("Jepicame","=>"+data);
            if(data!=null)
            {
                listKardexOfPaymentEntityList=convert.getConvertListKardexOfPayment(data);
                kardexPagoEntityList=data;
                listKardexOfPaymentAdapter
                        =new ListKardexOfPaymentAdapter(
                        activity,
                        listKardexOfPaymentEntityList);
                lv_kardex_of_payment.setAdapter(listKardexOfPaymentAdapter);
                Log.e("REOS","KardexOfPaymentView.getListKardexOfPayment.listKardexOfPaymentEntityList.size()"+listKardexOfPaymentEntityList.size());
                for(int i=0;i<listKardexOfPaymentEntityList.size();i++)
                {
                    docamount=docamount+Double.parseDouble(listKardexOfPaymentEntityList.get(i).getDocAmount()) ;
                    //tv_docamount_kardex_invoice.setText(Convert.currencyForView(String.valueOf(listKardexOfPaymentEntityList.get(i).getDocAmount())));
                }
                tv_quantity_invoice_kardex.setText(String.valueOf(listKardexOfPaymentEntityList.size()));
                tv_docamount_kardex_invoice.setText(Convert.currencyForView(String.valueOf(docamount)));
            }else {
                Toast.makeText(context, "No se encontraron Facturas", Toast.LENGTH_SHORT).show();
                alertdialogInformative(context,"IMPORTANTE!!!","No se encontraron Documentos para realizar el Calculo").show();
            }
            pd.dismiss();
        });*/
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }
}
package com.vistony.salesforce.View;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListHistoricSalesAnalysisByRouteAdapter;
import com.vistony.salesforce.Controller.Adapters.ListHistoricSalesOrderTraceabilityAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Adapters.ListHistoricSalesAnalysisByRouteDao;
import com.vistony.salesforce.Dao.Adapters.ListaClienteDetalleDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesAnalysisByRouteRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesOrderTraceabilityRepository;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricSalesAnalysisByRoute#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricSalesAnalysisByRoute extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View v;
    Button btn_get_historic_sales_analysis_by_route;
    Spinner spn_route_day;
    static ListView listview_historic_sales_analysis_by_route;
    TextView tv_amount_historic_sales_analysis_by_route,tv_count_historic_sales_analysis_by_route;
    static HistoricSalesAnalysisByRouteRepository historicSalesAnalysisByRouteRepository;
    static private ProgressDialog pd;
    static LifecycleOwner lifecycleOwner;
    static Context context;
    static Activity activity;
    static ListHistoricSalesAnalysisByRouteAdapter listHistoricSalesAnalysisByRouteAdapter;
    public static List<HistoricSalesAnalysisByRouteEntity> historicSalesAnalysisByRouteEntityList;
    public HistoricSalesAnalysisByRoute() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricSalesAnalysisByRoute.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricSalesAnalysisByRoute newInstance(String param1, String param2) {
        HistoricSalesAnalysisByRoute fragment = new HistoricSalesAnalysisByRoute();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        context=getContext();
        activity=getActivity();
        lifecycleOwner=getActivity();
        historicSalesAnalysisByRouteEntityList=new ArrayList<>();
        getActivity().setTitle("Analisis de Venta por Ruta");
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
        v= inflater.inflate(R.layout.fragment_historic_sales_analysis_by_route, container, false);
        btn_get_historic_sales_analysis_by_route=v.findViewById(R.id.btn_get_historic_sales_analysis_by_route);
        spn_route_day=v.findViewById(R.id.spn_route_day);
        listview_historic_sales_analysis_by_route=v.findViewById(R.id.listview_historic_sales_analysis_by_route);
        btn_get_historic_sales_analysis_by_route.setOnClickListener(this);
        historicSalesAnalysisByRouteRepository= new ViewModelProvider(getActivity()).get(HistoricSalesAnalysisByRouteRepository.class);

    return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ListenerBackPress.setCurrentFragment("HistoricoFacturasView");
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
    }

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("HistoricoFacturasView");
        ListenerBackPress.setTemporaIdentityFragment("onAttach");
        super.onAttach(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_get_historic_sales_analysis_by_route:
                getListHistoricSales(spn_route_day.getSelectedItem().toString());
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    static private void getListHistoricSales(String Day)
    {
        pd = new ProgressDialog(activity);
        pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Ordenes de Venta", true, false);
        historicSalesAnalysisByRouteRepository.getHistoricSalesAnalysisByRoute  (SesionEntity.imei, Day).observe(lifecycleOwner, data -> {
            historicSalesAnalysisByRouteEntityList=data;
            Log.e("Jepicame","=>"+data);
            if(data!=null)
            {
                Double amount=0.0;
                listHistoricSalesAnalysisByRouteAdapter
                        =new ListHistoricSalesAnalysisByRouteAdapter(
                        activity,
                        ListHistoricSalesAnalysisByRouteDao.getInstance().getLeads(Convert.getConvertListHistoricSalesAnalysisByRoute(data)));
                listview_historic_sales_analysis_by_route.setAdapter((listHistoricSalesAnalysisByRouteAdapter));
                //for(int i=0;i<data.size();i++)
                //{
                //    amount=amount+Double.parseDouble(data.get(i).getMontototalorden());
                //}
                // tv_amount_historic_sales_order_traceability.setText(Convert.currencyForView(String.valueOf(amount)));
            }else {
                Toast.makeText(context, "No se encontraron Facturas", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        });
    }
}
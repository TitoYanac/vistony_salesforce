package com.vistony.salesforce.View;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListHistoricSalesOrderTraceabilityAdapter;
import com.vistony.salesforce.Controller.Adapters.ListHistoricStatusDispatchAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaHistoricoFacturasAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesOrderTraceabilityRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasWS;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricSalesOrderTraceabilityView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricSalesOrderTraceabilityView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ImageButton imb_calendario_historic_salesorder_traceability;
    static TextView tv_fecha_historic_salesorder_traceability,tv_count_historic_sales_order_traceability,tv_amount_historic_sales_order_traceability;
    Button btn_get_historic_salesorder_traceability;
    static ListView listview_historic_salesorder_traceability;
    private static DatePickerDialog oyenteSelectorFecha;
    private  int diahf,meshf,yearF;
    private SearchView mSearchView;
    static ListaHistoricoFacturasAdapter listaHistoricoFacturasAdapter;
    SimpleDateFormat dateFormat,dateFormat2;
    Date date;
    static String fecha,fecha2;
    static private ProgressDialog pd;
    static LifecycleOwner lifecycleOwner;
    static Context context;
    static Activity activity;
    static HistoricSalesOrderTraceabilityRepository historicSalesOrderTraceabilityRepository;
    static ListHistoricSalesOrderTraceabilityAdapter listHistoricSalesOrderTraceabilityAdapter;
    String parametrofecha="";
    public HistoricSalesOrderTraceabilityView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricSalesOrderTraceability.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricSalesOrderTraceabilityView newInstance(String param1, String param2) {
        HistoricSalesOrderTraceabilityView fragment = new HistoricSalesOrderTraceabilityView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        context=getContext();
        activity=getActivity();
        lifecycleOwner=getActivity();
        getActivity().setTitle("Trazabilidad Orden Venta");
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
        v= inflater.inflate(R.layout.fragment_historic_sales_order_traceability, container, false);
        imb_calendario_historic_salesorder_traceability=v.findViewById(R.id.imb_calendario_historic_salesorder_traceability);
        tv_fecha_historic_salesorder_traceability=v.findViewById(R.id.tv_fecha_historic_salesorder_traceability);
        tv_count_historic_sales_order_traceability=v.findViewById(R.id.tv_count_historic_sales_order_traceability);
        tv_amount_historic_sales_order_traceability=v.findViewById(R.id.tv_amount_historic_sales_order_traceability);
        btn_get_historic_salesorder_traceability=v.findViewById(R.id.btn_get_historic_salesorder_traceability);
        listview_historic_salesorder_traceability=v.findViewById(R.id.listview_historic_salesorder_traceability);
        imb_calendario_historic_salesorder_traceability.setOnClickListener(this);
        btn_get_historic_salesorder_traceability.setOnClickListener(this);
        historicSalesOrderTraceabilityRepository= new ViewModelProvider(getActivity()).get(HistoricSalesOrderTraceabilityRepository.class);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        dateFormat2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        fecha2=dateFormat2.format(date);
        tv_fecha_historic_salesorder_traceability.setText(fecha);
        parametrofecha=fecha;
        return v;
    }





    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_historic_salesorder_traceability:
                final Calendar c1 = Calendar.getInstance();
                diahf = c1.get(Calendar.DAY_OF_MONTH);
                meshf = c1.get(Calendar.MONTH);
                yearF = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        yearF,
                        meshf,
                        diahf
                );
                oyenteSelectorFecha.show();


                break;
            case R.id.btn_get_historic_salesorder_traceability:
                Log.e("jpcm","Execute historico");

                getListHistoric(parametrofecha);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String ano="",mes="",dia="";
        mes=String.valueOf(month+1);
        dia=String.valueOf(dayOfMonth);
        if(mes.length()==1)
        {
            mes='0'+mes;
        }
        if(dia.length()==1)
        {
            dia='0'+dia;
        }
        parametrofecha=year + "-" + mes + "-" + dia;
        fecha2=year + "" + mes + "" + dia;
        tv_fecha_historic_salesorder_traceability.setText(year + "-" + mes + "-" + dia);
    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Cliente");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(listHistoricSalesOrderTraceabilityAdapter!=null)
        {
            listHistoricSalesOrderTraceabilityAdapter.filter(newText);
        }
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historico_orden_venta, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    static private void getListHistoric(String Date)
    {
        pd = new ProgressDialog(activity);
        pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Ordenes de Venta", true, false);
        historicSalesOrderTraceabilityRepository.getHistoricSalesOrderTraceabilityRepository  (SesionEntity.imei, Date).observe(lifecycleOwner, data -> {
            Log.e("Jepicame","=>"+data);
            if(data!=null)
            {
                Double amount=0.0;
                listHistoricSalesOrderTraceabilityAdapter
                        =new ListHistoricSalesOrderTraceabilityAdapter(
                        activity,
                        data,
                        fecha2);
                listview_historic_salesorder_traceability.setAdapter(listHistoricSalesOrderTraceabilityAdapter);
                tv_count_historic_sales_order_traceability.setText(String.valueOf(data.size()));
                for(int i=0;i<data.size();i++)
                {
                    amount=amount+Double.parseDouble(data.get(i).getMontototalorden());
                }
                tv_amount_historic_sales_order_traceability.setText(Convert.currencyForView(String.valueOf(amount)));
            }else {
                Toast.makeText(context, "No se encontraron Facturas", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        });
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }
}
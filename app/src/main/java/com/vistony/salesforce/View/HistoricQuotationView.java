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

import com.vistony.salesforce.Controller.Adapters.ListHistoricQuotationAdapter;
import com.vistony.salesforce.Controller.Adapters.ListHistoricSalesAnalysisByRouteAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Adapters.ListHistoricQuotationDao;
import com.vistony.salesforce.Dao.Adapters.ListHistoricSalesAnalysisByRouteDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesOrderTraceabilityRepository;
import com.vistony.salesforce.Dao.Retrofit.QuotationRepository;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricQuotationView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricQuotationView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private static OnFragmentInteractionListener mListener;
    private  int dia,mes,anio;
    private SearchView mSearchView;
    private static DatePickerDialog listenerselectdate;
    private ImageButton imb_calendar_historic_quotation;
    static private TextView tv_date_historic_quotation,tv_line_text,tv_quantity_quotation,tv_amount_quotation;
    private Button btn_get_historic_quotation;
    static private ListView lv_historic_quotation_detail;
    static private ProgressDialog pd;
    static Context context;
    static Activity activity;
    static LifecycleOwner lifecycleOwner;
    static QuotationRepository quotationRepository;
    static ListHistoricQuotationAdapter listHistoricQuotationAdapter;
    String parametrofecha="";
    SimpleDateFormat dateFormat;
    Date date;
    public HistoricQuotationView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricQuotationView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricQuotationView newInstance(String param1, String param2) {
        HistoricQuotationView fragment = new HistoricQuotationView();
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
        lifecycleOwner=getActivity();
        setHasOptionsMenu(true);
        quotationRepository= new ViewModelProvider(getActivity()).get(QuotationRepository.class);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        parametrofecha =dateFormat.format(date);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_historic_quotation_view, container, false);
        imb_calendar_historic_quotation=(ImageButton)v.findViewById(R.id.imb_calendar_historic_quotation);
        btn_get_historic_quotation=(Button) v.findViewById(R.id.btn_get_historic_quotation);
        lv_historic_quotation_detail=(ListView) v.findViewById(R.id.lv_historic_quotation_detail);
        tv_line_text=(TextView) v.findViewById(R.id.tv_line_text);
        tv_date_historic_quotation=(TextView) v.findViewById(R.id.tv_date_historic_quotation);
        tv_quantity_quotation=(TextView) v.findViewById(R.id.tv_quantity_quotation);
        tv_amount_quotation=(TextView) v.findViewById(R.id.tv_amount_quotation);
        tv_date_historic_quotation.setText(parametrofecha);
        imb_calendar_historic_quotation.setOnClickListener(this);
        btn_get_historic_quotation.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendar_historic_quotation:
                final Calendar c1 = Calendar.getInstance();
                dia = c1.get(Calendar.DAY_OF_MONTH);
                mes = c1.get(Calendar.MONTH);
                anio = c1.get(Calendar.YEAR);
                listenerselectdate = new DatePickerDialog(getContext(),this,
                        anio,
                        mes,
                        dia
                );
                listenerselectdate.show();


                break;
            case R.id.btn_get_historic_quotation:
                getListHistoricQuotation(parametrofecha);
                break;
            default:
                break;
        }
    }


    static private void getListHistoricQuotation(String Day)
    {
        pd = new ProgressDialog(activity);
        pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Analisis de Venta", true, false);
        quotationRepository.getHistoricQuotation (SesionEntity.imei, Day).observe(lifecycleOwner, data -> {
            Log.e("REOS","HistoricQuotationView-getListHistoricQuotation-data: "+data);
            if(data!=null)
            {
                Double amount=0.0;
                listHistoricQuotationAdapter
                        =new ListHistoricQuotationAdapter(
                        activity,
                        ListHistoricQuotationDao.getInstance().getLeads(data));
                lv_historic_quotation_detail.setAdapter((listHistoricQuotationAdapter));
                tv_quantity_quotation.setText(String.valueOf(data.size()));
                for(int i=0;i<data.size();i++)
                {
                 //   amount=amount+Double.parseDouble(data.get(i).getAmounttotal());
                }
                //tv_amount_quotation.setText(Convert.currencyForView(String.valueOf(amount)));
            }else {
                Toast.makeText(context, "No se encontraron Cotizaciones", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        });
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
        tv_date_historic_quotation.setText(year + "-" + mes + "-" + dia);
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
        if(listHistoricQuotationAdapter!=null) {
            listHistoricQuotationAdapter.filter(newText);
        }
        return true;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_historic_quotation, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onAttach(Context context) {
        if(ListenerBackPress.getTemporaIdentityFragment() ==null || !ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa")){
            ListenerBackPress.setTemporaIdentityFragment("HistoricoDepositoViewCobranzasDetails");
            ListenerBackPress.setCurrentFragment("FormaAsociatyListCobranzaDeposito");
        }

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
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
    }
}
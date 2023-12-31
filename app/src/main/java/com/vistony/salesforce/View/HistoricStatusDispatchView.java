package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

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

import com.vistony.salesforce.Controller.Adapters.ListHistoricStatusDispatchAdapter;
import com.vistony.salesforce.Controller.Adapters.ListKardexOfPaymentAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.HeaderDispatchSheetRepository;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.Retrofit.TypeDispatchRepository;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricStatusDispatchView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricStatusDispatchView extends Fragment  implements View.OnClickListener, DatePickerDialog.OnDateSetListener,SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton imb_calendario_historic_status_dispatch;
    Button btn_get_status_dispatch;
    static TextView tv_date_historic_status_dispatch,tv_count_historic_status_dispatch,tv_amount_historic_status_dispatch;
    static ListView lv_historic_status_dispatch;
    View v;
    private  int diadespacho,mesdespacho,anodespacho;
    private static DatePickerDialog oyenteSelectorFecha;
    SearchView mSearchView;
    static String fecha,parametrofecha;
    static ListHistoricStatusDispatchAdapter listHistoricStatusDispatchAdapter;
    static private ProgressDialog pd;
    static Activity activity;
    static StatusDispatchRepository statusDispatchRepository;
    static LifecycleOwner lifecycleOwner;
    static Context context;
    static double docamount;
    SimpleDateFormat dateFormat,dateFormat2;
    Date date,date2;
    static HeaderDispatchSheetRepository headerDispatchSheetRepository;
    static List<HistoricStatusDispatchEntity> listHistoricStatusDiapatch;
    static GetAsyncTaskCustomer getAsyncTaskCustomer;
    public HistoricStatusDispatchView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FindStatusDispatch.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricStatusDispatchView newInstance(String param1, String param2) {
        HistoricStatusDispatchView fragment = new HistoricStatusDispatchView();
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
        getActivity().setTitle("Consulta Despachos");
        setHasOptionsMenu(true);
        statusDispatchRepository= new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_find_status_dispatch, container, false);
        btn_get_status_dispatch= v.findViewById(R.id.btn_get_status_dispatch);
        btn_get_status_dispatch.setOnClickListener(this);
        tv_date_historic_status_dispatch=v.findViewById(R.id.tv_date_historic_status_dispatch);
        tv_count_historic_status_dispatch=v.findViewById(R.id.tv_count_historic_status_dispatch);
        imb_calendario_historic_status_dispatch=(ImageButton)v.findViewById(R.id.imb_calendario_historic_status_dispatch);
        imb_calendario_historic_status_dispatch.setOnClickListener(this);
        lv_historic_status_dispatch=(ListView) v.findViewById(R.id.lv_historic_status_dispatch);
        headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);

        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormat2 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        date2 = new Date();
        fecha =dateFormat.format(date);
        parametrofecha =dateFormat2.format(date2);
        tv_date_historic_status_dispatch.setText(fecha);
        getListHistoric(parametrofecha);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_historic_status_dispatch:
                Log.e("REOS","HistoricStatusDispatch-OnClick-imb_calendario_historico_cobranza");
                final Calendar c1 = Calendar.getInstance();
                diadespacho = c1.get(Calendar.DAY_OF_MONTH);
                mesdespacho = c1.get(Calendar.MONTH);
                anodespacho = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                );
                oyenteSelectorFecha.show();


                break;
            case R.id.btn_get_status_dispatch:
                Log.e("REOS","HistoricStatusDispatch-OnClick-btn_consultar_fecha_hisorico_cobranza");
                statusDispatchRepository= new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);
                getListHistoric(parametrofecha);
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
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
        parametrofecha=year+mes+dia;
        tv_date_historic_status_dispatch.setText(dia + "/" + mes + "/" + year);
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

        if(listHistoricStatusDispatchAdapter!=null) {
            listHistoricStatusDispatchAdapter.filter(newText);
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historic_status_dispatch, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static class GetAsyncTaskCustomer extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(activity);
            pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Datos", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            int CantClientes=0;
            ClienteRepository clienteRepository = new ClienteRepository(context);
            ParametrosSQLite parametrosSQLite=new ParametrosSQLite(activity);
            List<ClienteSQLiteEntity> LclientesqlSQLiteEntity;
            LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei ,parametrofecha);
            Log.e("REOS","HojaDespachoView.getMastersDelivery-LclientesqlSQLiteEntity:"+LclientesqlSQLiteEntity.size());
            if (!(LclientesqlSQLiteEntity.isEmpty())) {
                CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                parametrosSQLite.ActualizaCantidadRegistros("1", "CLIENTES", ""+CantClientes, getDateTime());
            }
            return 1;
        }
        protected void onPostExecute(Object result) {
            pd.dismiss();
        }

        public int registrarClienteSQLite(List<ClienteSQLiteEntity> Lista){

            ClienteRepository clienteRepository =new ClienteRepository(context);
            clienteRepository.addCustomer(Lista);

            return clienteRepository.countCustomer();
        }
    }


    static private void getListHistoric(String Date)
    {
        //pd = new ProgressDialog(activity);
        //pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Estados de Despacho", true, false);


        if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
        {
            listHistoricStatusDiapatch=new ArrayList<>();
            Log.e("REOS","HistoricStatusDispatchView-getListHistoric-data==null:");
            Log.e("REOS","HistoricStatusDispatchView-getListHistoric-SesionEntity.perfil_idl:"+SesionEntity.perfil_id);
            Log.e("REOS","HistoricStatusDispatchView-getListHistoric-Date:"+Date);
            getAsyncTaskCustomer=new GetAsyncTaskCustomer();
            getAsyncTaskCustomer.execute();
            headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(SesionEntity.imei, Date,context).observe(lifecycleOwner, data -> {
                //listHistoricStatusDiapatch=statusDispatchSQLite.getListStatusDispatchforDate(Date);
                DetailDispatchSheetSQLite detailDispatchSheetSQLite = new DetailDispatchSheetSQLite(context);
                List<HistoricStatusDispatchEntity> listHistoricStatusDiapatch = new ArrayList<>();
                if (data.equals("1")) {
                    listHistoricStatusDiapatch = detailDispatchSheetSQLite.getDetailDispatchSheetforDateDispatch(Date);
                }
                Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                if(listHistoricStatusDiapatch.isEmpty())
                {
                    Log.e("REOS","HistoricStatusDispatchView-getListHistoric-listHistoricStatusDiapatch.isEmpty():");
                    lv_historic_status_dispatch.setAdapter(null);
                    tv_count_historic_status_dispatch.setText("0");
                    Toast.makeText(context, "No se encontro operaciones en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("REOS","HistoricStatusDispatchView-getListHistoric-!listHistoricStatusDiapatch.isEmpty():");
                    listHistoricStatusDispatchAdapter
                            =new ListHistoricStatusDispatchAdapter(
                            activity,
                            listHistoricStatusDiapatch,
                            activity);
                    lv_historic_status_dispatch.setAdapter(listHistoricStatusDispatchAdapter);
                    tv_count_historic_status_dispatch.setText(String.valueOf(listHistoricStatusDiapatch.size()));
                    Toast.makeText(context, "Consulta Realizada con Exito", Toast.LENGTH_SHORT).show();
                }
            });


            /*if(SesionEntity.perfil_id.equals("chofer")||SesionEntity.perfil_id.equals("CHOFER"))
            {
                listHistoricStatusDiapatch=statusDispatchSQLite.getListStatusDispatchforDate(Date);
            }else {
                listHistoricStatusDiapatch=detailDispatchSheetSQLite.getDetailDispatchSheetforDateDispatch(Date);
            }*/


        }
        else {
            pd = new ProgressDialog(activity);
            pd = ProgressDialog.show(activity, "Por favor espere", "Consultando Estados de Despacho", true, false);
            headerDispatchSheetRepository.getAndInsertHeaderDispatchSheetSalesPerson(SesionEntity.imei, Date, context).observe(lifecycleOwner, data -> {
                Log.e("REOS", "HistoricStatusDispatchView-getListHistoric-date:" + data);
                if (data != null) {
                    DetailDispatchSheetSQLite detailDispatchSheetSQLite = new DetailDispatchSheetSQLite(context);
                    List<HistoricStatusDispatchEntity> listHistoricStatusDiapatch = new ArrayList<>();
                    if (data.equals("1")) {
                        listHistoricStatusDiapatch = detailDispatchSheetSQLite.getDetailDispatchSheetforDateDispatch(Date);
                    }
                    listHistoricStatusDispatchAdapter
                            = new ListHistoricStatusDispatchAdapter(
                            activity,
                            listHistoricStatusDiapatch,
                            activity);
                    lv_historic_status_dispatch.setAdapter(listHistoricStatusDispatchAdapter);
                    tv_count_historic_status_dispatch.setText(String.valueOf(listHistoricStatusDiapatch.size()));
                    Toast.makeText(context, "Consulta Realizada con Exito", Toast.LENGTH_SHORT).show();

                    Log.e("REOS", "HistoricStatusDispatchView-getListHistoric-data!=null:");
                }

            /*else {
                Log.e("REOS","HistoricStatusDispatchView-getListHistoric-data==null:");
                List<HistoricStatusDispatchEntity> listHistoricStatusDiapatch=new ArrayList<>();
                StatusDispatchSQLite statusDispatchSQLite=new StatusDispatchSQLite(context);
                listHistoricStatusDiapatch=statusDispatchSQLite.getListStatusDispatchforDate(Date);
                if(listHistoricStatusDiapatch.isEmpty())
                {
                    Log.e("REOS","HistoricStatusDispatchView-getListHistoric-listHistoricStatusDiapatch.isEmpty():");
                    lv_historic_status_dispatch.setAdapter(null);
                    Toast.makeText(context, "No se encontro operaciones en la fecha seleccionada", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("REOS","HistoricStatusDispatchView-getListHistoric-!listHistoricStatusDiapatch.isEmpty():");
                    listHistoricStatusDispatchAdapter
                            =new ListHistoricStatusDispatchAdapter(
                            activity,
                            listHistoricStatusDiapatch,
                            activity);
                    lv_historic_status_dispatch.setAdapter(listHistoricStatusDispatchAdapter);
                    tv_count_historic_status_dispatch.setText(String.valueOf(listHistoricStatusDiapatch.size()));
                    Toast.makeText(context, "Consulta Realizada con Exito", Toast.LENGTH_SHORT).show();
                }

            }*/
                pd.dismiss();
            });

        }





            //pd.dismiss();
        //});
    }

}
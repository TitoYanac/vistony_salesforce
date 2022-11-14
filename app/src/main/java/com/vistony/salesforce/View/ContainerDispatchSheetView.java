package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.HeaderDispatchSheetRepository;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoCabeceraSQLiteEntity;
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
 * Use the {@link ContainerDispatchSheetView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContainerDispatchSheetView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener,SearchView.OnQueryTextListener  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private ViewPager viewPager;
    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private PageAdapter pageAdapter;
    private ProgressDialog pd;
    GetAsyncTaskCustomer getAsyncTaskCustomer;
    List<ClienteSQLiteEntity> LclientesqlSQLiteEntity;
    SimpleDateFormat dateFormat;
    Date date;
    String parametrofecha;
    private SearchView mSearchView;
    TextView tv_sheet_distpatch_date,tv_date_update;
    ImageButton imb_edit_date_dispatch,imb_get_date_dispatch,imb_update_date_dispatch;
    public static OnFragmentInteractionListener mListener;
    private static DatePickerDialog oyenteSelectorFecha;
    private  int dia,mes,año;

    public ContainerDispatchSheetView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContainerDispatchSheetView.
     */
    // TODO: Rename and change types and number of parameters
    public static ContainerDispatchSheetView newInstance(String param1, String param2) {
        ContainerDispatchSheetView fragment = new ContainerDispatchSheetView();
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
        v= inflater.inflate(R.layout.fragment_container_dispatch_sheet_view, container, false);
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        pageAdapter= new PageAdapter(getChildFragmentManager());
        pageAdapter.addFRagment(new DispatchSheetPendingView(),"PENDIENTES");
        pageAdapter.addFRagment(new DispatchSheetSucessful(),"EXITOSOS");
        pageAdapter.addFRagment(new DispatchSheetFailedView(),"FALLIDOS");
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        getActivity().setTitle("Hoja de Despacho");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        parametrofecha =dateFormat.format(date);

        tv_sheet_distpatch_date=v.findViewById(R.id.tv_sheet_distpatch_date);
        imb_edit_date_dispatch=v.findViewById(R.id.imb_edit_date_dispatch);
        imb_get_date_dispatch=v.findViewById(R.id.imb_get_date_dispatch);
        imb_update_date_dispatch=v.findViewById(R.id.imb_update_date_dispatch);
        tv_date_update=v.findViewById(R.id.tv_date_update);

        tv_sheet_distpatch_date.setText(parametrofecha);
        imb_edit_date_dispatch.setOnClickListener(this);
        imb_get_date_dispatch.setOnClickListener(this);
        imb_update_date_dispatch.setOnClickListener(this);
        getMastersDelivery(SesionEntity.imei,parametrofecha);
        return v;
    }

    public void getMastersDelivery(String Imei, String DispatchDate)
    {
        Log.e("REOS","HojaDespachoView.getMastersDelivery-DispatchDate:"+DispatchDate);
        HeaderDispatchSheetRepository headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ArrayList<HojaDespachoCabeceraSQLiteEntity> listHeaderDispatchSqlite=new ArrayList<>();
        HeaderDispatchSheetSQLite headerDispatchSheetSQLite=new HeaderDispatchSheetSQLite(getContext());
        if(headerDispatchSheetSQLite.getCountHeaderDispatchSheetDate(DispatchDate)>0)
        {
            //getListDispatchSheet(DispatchDate);
        }else {
            if (networkInfo != null) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraif");
                    getAsyncTaskCustomer=new GetAsyncTaskCustomer();
                    getAsyncTaskCustomer.execute();
                    headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(Imei,DispatchDate,getContext()).observe(getActivity(), data -> {
                        Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                        //getListDispatchSheet(DispatchDate);
                    });

                } else {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                }
            }else{
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                //getListDispatchSheet(DispatchDate);
            }
        }

        listHeaderDispatchSqlite=headerDispatchSheetSQLite.getDateRegisterHeaderDispatchSheet(DispatchDate);
        for(int i=0;i<listHeaderDispatchSqlite.size();i++)
        {
            tv_date_update.setText(listHeaderDispatchSqlite.get(i).getDatetimeregister());
        }
    }

    public void getListDispatchSheet(String Dispatchdate){
        DispatchSheetPendingView.newInstanceDateDispatch(Dispatchdate,getContext());
        DispatchSheetFailedView.newInstanceDateDispatch(Dispatchdate,getContext());
        DispatchSheetSucessful.newInstanceDateDispatch(Dispatchdate,getContext());

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //if(listaHojaDespachoAdapter!=null) {
        //    listaHojaDespachoAdapter.filter(newText);
        //}
        return true;
    }


    public class GetAsyncTaskCustomer extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Datos", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            int CantClientes=0;
            ClienteRepository clienteRepository = new ClienteRepository(getContext());
            ParametrosSQLite parametrosSQLite=new ParametrosSQLite(getActivity());
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
    }

    public int registrarClienteSQLite(List<ClienteSQLiteEntity> Lista){

        ClienteRepository clienteRepository =new ClienteRepository(getContext());
        clienteRepository.addCustomer(Lista);

        return clienteRepository.countCustomer();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_hoja_despacho_containner, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        //mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint("Buscar Cliente");
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
        tv_sheet_distpatch_date.setText(year + "-" + mes + "-" + dia);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onAttach(Context context) {
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
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_edit_date_dispatch:
                final Calendar c1 = Calendar.getInstance();
                dia = c1.get(Calendar.DAY_OF_MONTH);
                mes = c1.get(Calendar.MONTH);
                año = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        año,
                        mes,
                        dia
                );
                oyenteSelectorFecha.show();
                break;

            case R.id.imb_get_date_dispatch:
                getListDispatchSheet(parametrofecha);
                //UpdateListView(SesionEntity.imei,parametrofecha);
                break;

            case R.id.imb_update_date_dispatch:
                Log.e("REOS","HojaDespachoView.btnconsultarfecha: Ingreso");
                //getMastersDelivery(SesionEntity.imei,parametrofecha);
                UpdateListView(SesionEntity.imei,parametrofecha);
                break;
            default:
                break;
        }
    }

    public void getMastersDeliveryUpdate(String Imei, String DispatchDate)
    {
        Log.e("REOS","HojaDespachoView.getMastersDelivery-DispatchDate:"+DispatchDate);
        HeaderDispatchSheetRepository headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        ArrayList<HojaDespachoCabeceraSQLiteEntity> listHeaderDispatchSqlite=new ArrayList<>();
        HeaderDispatchSheetSQLite headerDispatchSheetSQLite=new HeaderDispatchSheetSQLite(getContext());
        if(headerDispatchSheetSQLite.getCountHeaderDispatchSheetDate(DispatchDate)>0)
        {
            getListDispatchSheet(DispatchDate);
        }else {
            if (networkInfo != null) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraif");
                    getAsyncTaskCustomer=new GetAsyncTaskCustomer();
                    getAsyncTaskCustomer.execute();
                    headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(Imei,DispatchDate,getContext()).observe(getActivity(), data -> {
                        Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                        getListDispatchSheet(DispatchDate);
                    });

                } else {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                }
            }else{
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
                getListDispatchSheet(DispatchDate);
            }
        }
        listHeaderDispatchSqlite=headerDispatchSheetSQLite.getDateRegisterHeaderDispatchSheet(DispatchDate);
        for(int i=0;i<listHeaderDispatchSqlite.size();i++)
        {
            tv_date_update.setText(listHeaderDispatchSqlite.get(i).getDatetimeregister());
        }
    }

    public void UpdateListView(String Imei,String DispatchDate) {
        Log.e("REOS","HojaDespachoView.getMastersDelivery-DispatchDate:"+DispatchDate);
        HeaderDispatchSheetRepository headerDispatchSheetRepository= new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
        ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        ArrayList<HojaDespachoCabeceraSQLiteEntity> listHeaderDispatchSqlite=new ArrayList<>();
        HeaderDispatchSheetSQLite headerDispatchSheetSQLite=new HeaderDispatchSheetSQLite(getContext());

        if (networkInfo != null) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraif");
                getAsyncTaskCustomer=new GetAsyncTaskCustomer();
                getAsyncTaskCustomer.execute();
                headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(Imei,DispatchDate,getContext()).observe(getActivity(), data -> {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                    getListDispatchSheet(DispatchDate);
                });

            } else {
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
            }
        }else{
            Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
            getListDispatchSheet(DispatchDate);
        }
        listHeaderDispatchSqlite=headerDispatchSheetSQLite.getDateRegisterHeaderDispatchSheet(DispatchDate);
        for(int i=0;i<listHeaderDispatchSqlite.size();i++)
        {
            tv_date_update.setText(listHeaderDispatchSqlite.get(i).getDatetimeregister());
        }
    }


}
package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import com.google.android.gms.maps.GoogleMap;
import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.HeaderDispatchSheetRepository;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.kotlin.compose.DispatchSheetFailedScreen;
import com.vistony.salesforce.kotlin.compose.DispatchSheetMapScreen;
import com.vistony.salesforce.kotlin.compose.DispatchSheetMapScreenKt;
import com.vistony.salesforce.kotlin.compose.DispatchSheetPendingScreen;
import com.vistony.salesforce.kotlin.compose.theme.DispatchSheetSucessfulScreen;

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
public class ContainerDispatchSheetView extends Fragment
        implements SearchView.OnQueryTextListener
{

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
    private SearchView mSearchView;
    public static OnFragmentInteractionListener mListener;
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

        getActivity().setTitle("Hoja de Despacho");

        if(ContainerDispatchView.statusUpdateDispatchSheet)
        {
            UpdateListView(SesionEntity.imei,ContainerDispatchView.parametrofecha);
        }else {
            ArrayList<HojaDespachoCabeceraSQLiteEntity> listHeaderDispatchSqlite=new ArrayList<>();
            HeaderDispatchSheetSQLite headerDispatchSheetSQLite=new HeaderDispatchSheetSQLite(getContext());
            listHeaderDispatchSqlite=headerDispatchSheetSQLite.getDateRegisterHeaderDispatchSheet(ContainerDispatchView.parametrofecha);
            if(listHeaderDispatchSqlite.isEmpty())
            {
                UpdateListView(SesionEntity.imei,ContainerDispatchView.parametrofecha);
            }else
            {
                getListDispatchSheet(ContainerDispatchView.parametrofecha);
            }
        }

        //getMastersDelivery(SesionEntity.imei,parametrofecha);
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
            ContainerDispatchView.tv_date_update.setText(listHeaderDispatchSqlite.get(i).getDatetimeregister());
        }

        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetPending=new ArrayList<>();
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSucesful=new ArrayList<>();
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetFailed=new ArrayList<>();
        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());

        listDetailDispatchSheetPending=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDate(DispatchDate);
        listDetailDispatchSheetSucesful=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDateSucessful(DispatchDate);
        listDetailDispatchSheetFailed=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDateFailed(DispatchDate);

       // tabLayout.removeAllTabs();
        //tabLayout.removeAllViews();
        //tabLayout.removeAllViewsInLayout();
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        pageAdapter= new PageAdapter(getChildFragmentManager());

        //pageAdapter.addFRagment(new DispatchSheetPendingView(),listDetailDispatchSheetPending.size()+"\nPENDIENTES");
        pageAdapter.addFRagment(new DispatchSheetPendingScreen(),listDetailDispatchSheetPending.size()+"\nPENDIENTES");
        pageAdapter.addFRagment(new DispatchSheetSucessful(),listDetailDispatchSheetSucesful.size()+"\nEXITOSOS");
        pageAdapter.addFRagment(new DispatchSheetFailedView(),listDetailDispatchSheetFailed.size()+"\nFALLIDOS");

        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);


        //getListDispatchSheet(DispatchDate);
    }

    public void getListDispatchSheet(String Dispatchdate){

        //DispatchSheetPendingView.newInstanceDateDispatch(Dispatchdate,getContext());
        //DispatchSheetFailedView.newInstanceDateDispatch(Dispatchdate,getContext());
        //DispatchSheetSucessful.newInstanceDateDispatch(Dispatchdate,getContext());
        ArrayList<HojaDespachoCabeceraSQLiteEntity> listHeaderDispatchSqlite=new ArrayList<>();
        HeaderDispatchSheetSQLite headerDispatchSheetSQLite=new HeaderDispatchSheetSQLite(getContext());

        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetPending=new ArrayList<>();
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSucesful=new ArrayList<>();
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetFailed=new ArrayList<>();
        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());

        listDetailDispatchSheetPending=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDate(Dispatchdate);
        listDetailDispatchSheetSucesful=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDateSucessful(Dispatchdate);
        listDetailDispatchSheetFailed=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDateFailed(Dispatchdate);

        //tabLayout.removeAllTabs();
        //tabLayout.removeAllViews();
        //tabLayout.removeAllViewsInLayout();
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        pageAdapter= new PageAdapter(getChildFragmentManager());

        pageAdapter.addFRagment(new DispatchSheetPendingScreen(),"PENDIENTES ("+listDetailDispatchSheetPending.size()+")");
        //pageAdapter.addFRagment(new DispatchSheetPendingView(),listDetailDispatchSheetPending.size()+"\nPENDIENTES");
        //pageAdapter.addFRagment(new DispatchSheetPendingScreen(),listDetailDispatchSheetPending.size()+"\nPENDIENTES");
        pageAdapter.addFRagment(new DispatchSheetSucessfulScreen(),"EXITOSOS ("+listDetailDispatchSheetSucesful.size()+")");
        //pageAdapter.addFRagment(new DispatchSheetFailedView(),"FALLIDOS ("+listDetailDispatchSheetFailed.size()+")");
        pageAdapter.addFRagment(new DispatchSheetFailedScreen(),"FALLIDOS ("+listDetailDispatchSheetFailed.size()+")");

        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        listHeaderDispatchSqlite=headerDispatchSheetSQLite.getDateRegisterHeaderDispatchSheet(Dispatchdate);
        for(int i=0;i<listHeaderDispatchSqlite.size();i++)
        {
            ContainerDispatchView.tv_date_update.setText(listHeaderDispatchSqlite.get(i).getDatetimeregister());
        }

        //DispatchSheetMapScreen dispatchSheetMapScreenKt= new DispatchSheetMapScreen();
        //DispatchSheetMapScreenKt dispatchSheetMapScreenKt=new DispatchSheetMapScreenKt();
        //ContainerDispatchView containerDispatchView=new ContainerDispatchView();
        //containerDispatchView.setContainer();
        //ContainerDispatchView.setContainer2();

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
            LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei ,ContainerDispatchView.parametrofecha);
            Log.e("REOS","HojaDespachoView.getMastersDelivery-LclientesqlSQLiteEntity:"+LclientesqlSQLiteEntity.size());
            if (!(LclientesqlSQLiteEntity.isEmpty())) {
                CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                parametrosSQLite.ActualizaCantidadRegistros("1", "CLIENTES", ""+CantClientes, getDateTime());

            }
            //getHeadder(SesionEntity.imei ,parametrofecha);
            return 1;
        }
        protected void onPostExecute(Object result) {
            getListDispatchSheet(ContainerDispatchView.parametrofecha);
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
            ContainerDispatchView.tv_date_update.setText(listHeaderDispatchSqlite.get(i).getDatetimeregister());
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
                pd = new ProgressDialog(getActivity());
                pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Datos", true, false);
                headerDispatchSheetRepository.getAndInsertHeaderDispatchSheet(Imei,DispatchDate,getContext()).observe(getActivity(), data -> {
                    Log.e("REOS","DispatchSheetView-getMastersDelivery-data"+data.toString());
                    //getListDispatchSheet(DispatchDate);
                    pd.dismiss();
                    getAsyncTaskCustomer=new GetAsyncTaskCustomer();
                    getAsyncTaskCustomer.execute();

                });

            } else {
                Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
            }
        }else{
            Log.e("REOS","DispatchSheetView-getMastersDelivery-entraelse");
            getListDispatchSheet(DispatchDate);
        }
    }


}
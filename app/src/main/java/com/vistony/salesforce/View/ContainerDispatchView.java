package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Dao.SQLite.HeaderDispatchSheetSQLite;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.kotlin.compose.DispatchSheetMapScreen;
import com.vistony.salesforce.kotlin.data.HeaderDispatchSheetViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContainerDispatchView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContainerDispatchView extends Fragment
        implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static private ViewPager viewPager;
    private Toolbar tollbartab;
    static private TabLayout tabLayout;
    static private PageAdapter pageAdapter;
    SimpleDateFormat dateFormat;
    Date date;
    public static String parametrofecha;
    TextView tv_sheet_distpatch_date;
    public static TextView tv_date_update;
    ImageButton imb_edit_date_dispatch,imb_get_date_dispatch,imb_update_date_dispatch;

    private static DatePickerDialog oyenteSelectorFecha;
    private  int dia,mes,año;
    static public boolean statusUpdateDispatchSheet=false;


    public ContainerDispatchView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ContainnerDispatchView.
     */
    // TODO: Rename and change types and number of parameters
    public static ContainerDispatchView newInstance(String param1, String param2) {
        ContainerDispatchView fragment = new ContainerDispatchView();
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
        v= inflater.inflate(R.layout.fragment_containner_dispatch_view, container, false);
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        setContainer();
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
        //pageAdapter.addFRagment(new ContainerDispatchSheetView(),"Despacho");
        //pageAdapter.addFRagment(new RutaVendedorNoRutaView(),"Mapa");
        //viewPager.setAdapter(pageAdapter);






        return v;
    }


    public void setContainer(){
        pageAdapter= new PageAdapter(getChildFragmentManager());
        pageAdapter.addFRagment(new ContainerDispatchSheetView(),"Despacho");
        pageAdapter.addFRagment(new DispatchSheetMapScreen(),"Mapa");

        //tabLayout.getTabAt(0).getCustomView()
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_delete_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_delete_black_24dp));
        viewPager.setAdapter(pageAdapter);
        //viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
                //getListDispatchSheet(parametrofecha);
                //UpdateListView(SesionEntity.imei,parametrofecha);


                statusUpdateDispatchSheet=false;
                setContainer();

                break;

            case R.id.imb_update_date_dispatch:
                Log.e("REOS","HojaDespachoView.btnconsultarfecha: Ingreso");
                //getMastersDelivery(SesionEntity.imei,parametrofecha);

                statusUpdateDispatchSheet=true;
                setContainer();
                break;
            default:
                break;
        }
    }
}
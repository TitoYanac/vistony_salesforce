package com.vistony.salesforce.View;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Adapters.ListaHojaDespachoAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaHojaDespachoDao;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DispatchSheetFailedView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DispatchSheetFailedView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static ListView list_sheet_failed;
    static Context context;
    SimpleDateFormat dateFormat;
    Date date;
    String parametrofecha;
    static TextView tv_count_total;
    static ListaHojaDespachoAdapter listaHojaDespachoAdapter;

    public DispatchSheetFailedView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DispatchSheetFailedView.
     */
    // TODO: Rename and change types and number of parameters
    public static DispatchSheetFailedView newInstance(String param1, String param2) {
        DispatchSheetFailedView fragment = new DispatchSheetFailedView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DispatchSheetFailedView newInstanceDateDispatch(String param1, Context context) {
        DispatchSheetFailedView fragment = new DispatchSheetFailedView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        v= inflater.inflate(R.layout.fragment_dispatch_sheet_failed_view, container, false);
        list_sheet_failed=v.findViewById(R.id.list_sheet_failed);
        tv_count_total=v.findViewById(R.id.tv_count_total);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        parametrofecha =dateFormat.format(date);
        getListDetailDispatchSheet(parametrofecha,getContext());
        return v;
    }

    static public void getListDetailDispatchSheet (String dateDispatch, Context context){
        Log.e("REOS", "DispatchSheetView-getMastersDelivery-headerDispatchSheetRepository-dateDispatch" + dateDispatch);
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(context);
        listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforDispatchDateFailed(dateDispatch);
        Log.e("REOS", "DispatchSheetView-getMastersDelivery-headerDispatchSheetRepository-listDetailDispatchSheetSQLite" + listDetailDispatchSheetSQLite.size());
        listaHojaDespachoAdapter = new ListaHojaDespachoAdapter(context, ListaHojaDespachoDao.getInstance().getLeads(listDetailDispatchSheetSQLite));
        list_sheet_failed.setAdapter(listaHojaDespachoAdapter);
        tv_count_total.setText(String.valueOf(listDetailDispatchSheetSQLite.size()));
    }
}
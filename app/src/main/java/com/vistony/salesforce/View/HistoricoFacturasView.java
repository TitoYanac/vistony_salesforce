package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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

import com.vistony.salesforce.Controller.Adapters.ListaHistoricoFacturasAdapter;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasWS;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricoFacturasView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoFacturasView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ImageButton imb_calendario_historico_facturas;
    TextView tv_fecha_historico_facturas,tv_cantidad_facturas,tv_monto_facturas;
    Button btnconsultarfechafacturas;
    ListView listviewhistoricofacturas;
    private static DatePickerDialog oyenteSelectorFecha;
    private  int diahf,meshf,yearF;
    private SearchView mSearchView;
    HiloObtenerHistoricoFacturas hiloObtenerHistoricoFacturas;
    ListaHistoricoFacturasAdapter listaHistoricoFacturasAdapter;
    SimpleDateFormat dateFormat,dateFormatSAP;
    Date date,dateSAP;
    String fecha,fechaSAP;

    public HistoricoFacturasView() {
        // Required empty public constructor
    }
    public static HistoricoFacturasView newInstance(String param1, String param2) {
        HistoricoFacturasView fragment = new HistoricoFacturasView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        hiloObtenerHistoricoFacturas = new HiloObtenerHistoricoFacturas();
        setHasOptionsMenu(true);
        getActivity().setTitle(getActivity().getResources().getString(R.string.query_invoices));



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
        v= inflater.inflate(R.layout.fragment_historico_facturas, container, false);
        imb_calendario_historico_facturas=v.findViewById(R.id.imb_calendario_historico_facturas);
        tv_fecha_historico_facturas=v.findViewById(R.id.tv_fecha_historico_facturas);
        tv_cantidad_facturas=v.findViewById(R.id.tv_cantidad_facturas);
        tv_monto_facturas=v.findViewById(R.id.tv_monto_facturas);
        btnconsultarfechafacturas=v.findViewById(R.id.btnconsultarfechafacturas);
        listviewhistoricofacturas=v.findViewById(R.id.listviewhistoricofacturas);
        imb_calendario_historico_facturas.setOnClickListener(this);
        btnconsultarfechafacturas.setOnClickListener(this);
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        dateFormatSAP = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        dateSAP = new Date();
        fecha =dateFormat.format(date);
        fechaSAP = dateFormatSAP.format(dateSAP);
        tv_fecha_historico_facturas.setText(fecha);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_historico_facturas:
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
            case R.id.btnconsultarfechafacturas:
                Log.e("jpcm","Execute historico");
                String parametrofecha="";

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        hiloObtenerHistoricoFacturas =  new HiloObtenerHistoricoFacturas();
                        hiloObtenerHistoricoFacturas.execute();
                    }
                });
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
        fechaSAP=year+mes+dia;
        tv_fecha_historico_facturas.setText(dia + "/" + mes + "/" + year);
    }

    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setQueryHint(getActivity().getResources().getString(R.string.find_client));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(listaHistoricoFacturasAdapter!=null)
        {
            listaHistoricoFacturasAdapter.filter(newText);
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
    private class HiloObtenerHistoricoFacturas extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... arg0) {
            HistoricoFacturasWS historicoFacturasWS=new HistoricoFacturasWS(getContext());
            ArrayList<ListaHistoricoFacturasEntity> listaHistoricoFacturasEntities=new ArrayList<>();
            try {
                listaHistoricoFacturasEntities=historicoFacturasWS.getHistoricoFacturas(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        SesionEntity.fuerzatrabajo_id,
                        fechaSAP
                );
                Log.e("REOS", "HistoricoFacturas-HiloObtenerHistoricoFacturas-listaHistoricoFacturasEntities.size()" + listaHistoricoFacturasEntities.size());
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
                Log.e("REOS", "HistoricoFacturas-HiloObtenerHistoricoFacturas-e" + e.toString());
            }

            return listaHistoricoFacturasEntities;
        }

        protected void onPostExecute(Object result)
        {
            ArrayList<ListaHistoricoFacturasEntity> Lista = (ArrayList<ListaHistoricoFacturasEntity>) result;
            if (Lista.isEmpty())
            {
                Toast.makeText(getContext(), getActivity().getResources().getString(R.string.mse_not_data_available), Toast.LENGTH_SHORT).show();
                listviewhistoricofacturas.setAdapter(null);
            }else
            {
                float monto_orden_venta=0f;
                listaHistoricoFacturasAdapter = new ListaHistoricoFacturasAdapter(getActivity(),
                        ListaHistoricoFacturasDao.getInstance().getLeads(Lista));
                listviewhistoricofacturas.setAdapter(listaHistoricoFacturasAdapter);
                Toast.makeText(getContext(), getResources().getString(R.string.msm_data_available), Toast.LENGTH_SHORT).show();
                String montoordenventa;
                for(int i=0;i<Lista.size();i++)
                {
                    if(Lista.get(i).getMontoimporteordenventa()==null)
                    {
                        montoordenventa="0";
                    }
                    else
                        {
                            montoordenventa=Lista.get(i).getMontoimporteordenventa();
                        }
                    monto_orden_venta=monto_orden_venta+Float.parseFloat(
                            montoordenventa
                    );
                }
                tv_cantidad_facturas.setText(String.valueOf(Lista.size()));
                tv_monto_facturas.setText(String.valueOf(monto_orden_venta));
            }

        }
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

}
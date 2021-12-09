package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListaClienteCabeceraAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaHistoricContainerSalesAdapter;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListaClienteCabeceraDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricContainerSalesDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesWS;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricContainerSaleFocoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricContainerSaleFocoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static ListView lv_HistoricContainerSalesFoco;
    static Context context;
    HistoricContainerSalesRepository historicContainerSalesRepository;
    String cardCode;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    static ListaHistoricContainerSalesAdapter listaHistoricContainerSalesAdapter;
    ObtenerHiloHistoricContainerFoco obtenerHiloHistoricContainerFoco;
    ObtenerHiloHistoricContainerFamilia obtenerHiloHistoricContainerFamilia;
    private ProgressDialog pd;
    public HistoricContainerSaleFocoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricContainerSaleFocoView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricContainerSaleFocoView newInstance(String param1, String param2) {
        HistoricContainerSaleFocoView fragment = new HistoricContainerSaleFocoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricContainerSaleFocoView newInstanceListarClienteFoco(Object object) {
        HistoricContainerSaleFocoView fragment = new HistoricContainerSaleFocoView();
        //ListarHistoricContainerSalesFoco((List<HistoricContainerSalesEntity>) object);
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("REOS","HistoricContainerSaleFocoView-onCreate");
        obtenerHiloHistoricContainerFoco=new ObtenerHiloHistoricContainerFoco();
        obtenerHiloHistoricContainerFamilia= new ObtenerHiloHistoricContainerFamilia();
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
        //if(v==null) {
            Log.e("REOS", "HistoricContainerSaleFocoView-onCreateView");
            v = inflater.inflate(R.layout.fragment_historic_container_sale_foco_view, container, false);
            lv_HistoricContainerSalesFoco = (ListView) v.findViewById(R.id.lv_HistoricContainerSalesFoco);
            dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            date = new Date();
            context = getContext();
            fecha = dateFormat.format(date);
            /*historicContainerSalesRepository = new ViewModelProvider(getActivity()).get(HistoricContainerSalesRepository.class);
        try {
            historicContainerSalesRepository.getHistoricContainerSales(
                    SesionEntity.imei,
                    getContext(),
                    HistoricContainerSaleView.CardCode,
                    Induvis.changeMonth(fecha,-6),
                    fecha,
                    "FOCOS",
                    "FOCOS"
            ).observe(getActivity(), data -> {
                Log.e("Jepicame","=>"+data);
                HistoricContainerSaleFocoView.newInstanceListarClienteFoco(data);
            });
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("REOS","HistoricContainerSaleFocoView-onCreateView-e:"+e.toString());
        }*/

        //}
        obtenerHiloHistoricContainerFoco.execute();
        obtenerHiloHistoricContainerFamilia.execute();
        return v;
    }

    /*static public void ListarHistoricContainerSalesFoco(List<HistoricContainerSalesEntity> lista){
        Log.e("REOS","HistoricContainerSaleFocoView-ListarHistoricContainerSalesFoco-lista.SIZE():"+lista.size());
        try
        {
        listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context,  ListaHistoricContainerSalesDao.getInstance().getLeads(lista,"FOCOS"),"FOCOS");
        lv_HistoricContainerSalesFoco.setAdapter(listaHistoricContainerSalesAdapter);
        }catch (Exception e)
        {
            Log.e("REOS","HistoricContainerSaleFocoView-ListarHistoricContainerSalesFoco-e:"+e.toString());
        }
    }*/

    public class ObtenerHiloHistoricContainerFoco extends AsyncTask<String, Void, Object> {

        /*@Override
                protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Descargando Productos Foco", true, false);
        }*/
        @Override
        protected Object doInBackground(String... arg0) {
            List<HistoricContainerSalesEntity> Lista=new ArrayList<>();
            try {
                HistoricContainerSalesWS historicContainerSalesWS=new HistoricContainerSalesWS(getContext());
                Lista=historicContainerSalesWS.getHistoricContainerSalesEntityws(
                        SesionEntity.imei,
                        getContext(),
                        HistoricContainerSaleView.CardCode,
                        Induvis.changeMonth(fecha,-6),
                        fecha,
                        "FOCOS",
                        "FOCOS"
                );

            } catch (Exception e) {
                Log.e("REOS","ObtenerHiloHistoricContainerFoco-E"+e.toString());
                e.printStackTrace();
            }
            return Lista;
        }

        protected void onPostExecute(Object result)
        {
            List<HistoricContainerSalesEntity> Listado=new ArrayList<>();
            Listado=(List<HistoricContainerSalesEntity>) result;
            try {
                listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context,  ListaHistoricContainerSalesDao.getInstance().getLeads( Listado,"FOCOS"),"FOCOS");
                lv_HistoricContainerSalesFoco.setAdapter(listaHistoricContainerSalesAdapter);

            }catch(Exception e){
                Log.e("ASDASD=>","ASDA");
                e.printStackTrace();
            }
            obtenerHiloHistoricContainerFoco=new ObtenerHiloHistoricContainerFoco();
            //pd.dismiss();
            Toast.makeText(getContext(), "Productos Foco Obtenidos Correctamente...", Toast.LENGTH_SHORT).show();
        }
    }

    public class ObtenerHiloHistoricContainerFamilia extends AsyncTask<String, Void, Object> {
        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Descargando Productos Familia", true, false);
        }*/

        @Override
        protected Object doInBackground(String... arg0) {
            List<HistoricContainerSalesEntity> Lista = new ArrayList<>();
            try {
                HistoricContainerSalesWS historicContainerSalesWS = new HistoricContainerSalesWS(getContext());
                Lista = historicContainerSalesWS.getHistoricContainerSalesEntityws(
                        SesionEntity.imei,
                        getContext(),
                        HistoricContainerSaleView.CardCode,
                        Induvis.changeMonth(fecha, -6),
                        fecha,
                        "FAMILIA",
                        "FAMILIA"
                );

            } catch (Exception e) {
                Log.e("REOS", "ObtenerHiloHistoricContainerFAMILIA-E" + e.toString());
                e.printStackTrace();
            }
            return Lista;
        }

        protected void onPostExecute(Object result) {

            List<HistoricContainerSalesEntity> Listado = new ArrayList<>();
            Listado = (List<HistoricContainerSalesEntity>) result;
            Log.e("REOS", "HistoricContainerSalesFamilyView-ListarHistoricContainerSalesFamily-Listado:" + Listado.size());
            try {
                listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context, ListaHistoricContainerSalesDao.getInstance().getLeads(Listado, "FAMILIA"), "FAMILIA");
                HistoricContainerSalesFamilyView.lv_HistoricContainerSalesFamily.setAdapter(listaHistoricContainerSalesAdapter);

            } catch (Exception e) {
                Log.e("ASDASD=>", "ASDA");
                e.printStackTrace();
                Log.e("REOS", "HistoricContainerSalesFamilyView-ListarHistoricContainerSalesFamily-e:" + e.toString());
            }
            obtenerHiloHistoricContainerFamilia = new ObtenerHiloHistoricContainerFamilia();
            //pd.dismiss();
            Toast.makeText(getContext(), "Productos Familia Obtenidos Correctamente...", Toast.LENGTH_SHORT).show();
        }
    }
}
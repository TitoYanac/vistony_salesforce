package com.vistony.salesforce.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.vistony.salesforce.Controller.Adapters.ListaHistoricContainerSalesAdapter;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricContainerSalesDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesWS;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricContainerSalesSemaforoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricContainerSalesSemaforoView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static ListView lv_HistoricContainerSalesSemaforo;
    static ListaHistoricContainerSalesAdapter listaHistoricContainerSalesAdapter;
    static Context context;
    HistoricContainerSalesRepository historicContainerSalesRepository;
    String cardCode;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    private ProgressDialog pd;
    ObtenerHiloHistoricContainerSemaforo obtenerHiloHistoricContainerSemaforo;
    public HistoricContainerSalesSemaforoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricContainerSalesSemaforoView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricContainerSalesSemaforoView newInstance(String param1, String param2) {
        HistoricContainerSalesSemaforoView fragment = new HistoricContainerSalesSemaforoView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricContainerSalesSemaforoView newInstanceListarClienteSemaforo(Object object) {
        HistoricContainerSalesSemaforoView fragment = new HistoricContainerSalesSemaforoView();
        ListarHistoricContainerSalesSemaforo((List<HistoricContainerSalesEntity>) object);
        //fragment.setArguments(args);
        Log.e("REOS", "HistoricContainerSalesSemaforoView-newInstanceListarClienteSemaforo");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        obtenerHiloHistoricContainerSemaforo=new ObtenerHiloHistoricContainerSemaforo();
        Log.e("REOS","HistoricContainerSaleSemaforoView-onCreate");
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
            Log.e("REOS","HistoricContainerSaleSemaforoView-onCreateView");
            v = inflater.inflate(R.layout.fragment_historic_container_sales_semaforo_view, container, false);
            lv_HistoricContainerSalesSemaforo = (ListView) v.findViewById(R.id.lv_HistoricContainerSalesSemaforo);
            dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            date = new Date();
            fecha = dateFormat.format(date);
            historicContainerSalesRepository = new ViewModelProvider(getActivity()).get(HistoricContainerSalesRepository.class);
        /*try {
            historicContainerSalesRepository.getHistoricContainerSales(
                    SesionEntity.imei,
                    getContext(),
                    HistoricContainerSaleView.CardCode,
                    Induvis.changeMonth(fecha,-6),
                    fecha,
                    "SEMAFORO",
                    "SEMAFORO"
                    ).observe(getActivity(), data -> {
                HistoricContainerSalesSemaforoView.newInstanceListarClienteSemaforo(data);
                Log.e("REOS","HistoricContainerSalesSemaforo"+data);
            });

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("REOS","HistoricContainerSalesSemaforo-error"+e);
        }*/
        obtenerHiloHistoricContainerSemaforo.execute();
        //}
        return v;
    }

    static public void ListarHistoricContainerSalesSemaforo(List<HistoricContainerSalesEntity> lista){
        try
        {
            //Induvis Induvis = new Induvis();
            Log.e("REOS", "HistoricContainerSalesSemaforoView-ListarHistoricContainerSalesSemaforo-lista" + lista.size());
            listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context, ListaHistoricContainerSalesDao.getInstance().getLeads(
                    //Induvis.convertHistoricContainerSalesEntityTotalPeriod(
                            lista
            //)
                    , "SEMAFORO"), "SEMAFORO");
            lv_HistoricContainerSalesSemaforo.setAdapter(listaHistoricContainerSalesAdapter);
        }catch (Exception e)
        {
            Log.e("REOS", "HistoricContainerSalesSemaforoView-ListarHistoricContainerSalesSemaforo-Exception" + e.toString());
        }
    }

    public class ObtenerHiloHistoricContainerSemaforo extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Descargando Productos Semaforo", true, false);
        }
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
                        "SEMAFORO",
                        "SEMAFORO"
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
                listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context,  ListaHistoricContainerSalesDao.getInstance().getLeads( Listado,"SEMAFORO"),"SEMAFORO");
                lv_HistoricContainerSalesSemaforo.setAdapter(listaHistoricContainerSalesAdapter);

            }catch(Exception e){
                Log.e("ASDASD=>","ASDA");
                e.printStackTrace();
            }
            obtenerHiloHistoricContainerSemaforo=new ObtenerHiloHistoricContainerSemaforo();
            pd.dismiss();
            Toast.makeText(getContext(), "Productos Semaforo Obtenidos Correctamente...", Toast.LENGTH_SHORT).show();
        }
    }
}
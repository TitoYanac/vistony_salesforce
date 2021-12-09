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
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricContainerSalesFamilyView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricContainerSalesFamilyView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    static public  ListView lv_HistoricContainerSalesFamily;
    static Context context;
    HistoricContainerSalesRepository historicContainerSalesRepository;
    String cardCode;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    static ListaHistoricContainerSalesAdapter listaHistoricContainerSalesAdapter;
    public static OnFragmentInteractionListener mListener;
    private ProgressDialog pd;
    ObtenerHiloHistoricContainerFamilia obtenerHiloHistoricContainerFamilia;

    public HistoricContainerSalesFamilyView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricContainerSaleFamilyView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricContainerSalesFamilyView newInstance(String param1, String param2) {
        HistoricContainerSalesFamilyView fragment = new HistoricContainerSalesFamilyView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricContainerSalesFamilyView newInstanceListarClienteFamily(Object object) {
        HistoricContainerSalesFamilyView fragment = new HistoricContainerSalesFamilyView();
        //ListarHistoricContainerSalesFamily((List<HistoricContainerSalesEntity>) object);
        Log.e("REOS","HistoricContainerSalesFamilyView-newInstanceListarClienteFamily-FAMILIA");
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.e("REOS","HistoricContainerSaleFamilyView-onCreate");
        obtenerHiloHistoricContainerFamilia=new ObtenerHiloHistoricContainerFamilia();
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // if(v==null) {
            Log.e("REOS", "HistoricContainerSaleFamilyView-onCreateView");
            // Inflate the layout for this fragment
            v = inflater.inflate(R.layout.fragment_historic_container_sale_family_view, container, false);
            context = getContext();
            lv_HistoricContainerSalesFamily = (ListView) v.findViewById(R.id.lv_HistoricContainerSalesFamily);
            dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            date = new Date();
            fecha = dateFormat.format(date);
            historicContainerSalesRepository = new ViewModelProvider(getActivity()).get(HistoricContainerSalesRepository.class);
            //obtenerHiloHistoricContainerFamilia.execute();
        /*try {
            historicContainerSalesRepository.getHistoricContainerSales(
                    SesionEntity.imei,
                    getContext(),
                    HistoricContainerSaleView.CardCode,
                    Induvis.changeMonth(fecha,-6),
                    fecha,
                    "FAMILIA",
                    "FAMILIA"
            ).observe(getActivity(), data -> {
                Log.e("REOS","HistoricContainerSalesFamilyView"+data);
                HistoricContainerSalesFamilyView.newInstanceListarClienteFamily(data);
            });
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("REOS","HistoricContainerSalesFamilyView.ERROR: "+e.toString());
        }*/
       // }
        return v;
    }

    /*static public void ListarHistoricContainerSalesFamily(List<HistoricContainerSalesEntity> lista){
        try {
            Log.e("REOS","HistoricContainerSalesFamilyView-listaHistoricContainerSalesAdapter-FAMILIA");
            listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context,  ListaHistoricContainerSalesDao.getInstance().getLeads(lista,"FAMILIA"),"FAMILIA");
            lv_HistoricContainerSalesFamily.setAdapter(listaHistoricContainerSalesAdapter);
        }catch (Exception e)
        {
            Log.e("REOS","HistoricContainerSalesFamilyView-ListarHistoricContainerSalesFamily-e:"+e.toString());
        }

    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("REOS","HistoricContainerSalesFamilyView-onAttach");
        ListenerBackPress.setCurrentFragment("FormParametrosView");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        Log.e("REOS","HistoricContainerSalesFamilyView-onDetach");
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    public class ObtenerHiloHistoricContainerFamilia extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Descargando Parametros", true, false);
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
                        "FAMILIA",
                        "FAMILIA"
                );

            } catch (Exception e) {
                Log.e("REOS","ObtenerHiloHistoricContainerFAMILIA-E"+e.toString());
                e.printStackTrace();
            }
            return Lista;
        }

        protected void onPostExecute(Object result)
        {

            List<HistoricContainerSalesEntity> Listado=new ArrayList<>();
            Listado=(List<HistoricContainerSalesEntity>) result;
            Log.e("REOS","HistoricContainerSalesFamilyView-ListarHistoricContainerSalesFamily-Listado:"+Listado.size());
            try {
                listaHistoricContainerSalesAdapter = new ListaHistoricContainerSalesAdapter(context,  ListaHistoricContainerSalesDao.getInstance().getLeads( Listado,"FAMILIA"),"FAMILIA");
                lv_HistoricContainerSalesFamily.setAdapter(listaHistoricContainerSalesAdapter);

            }catch(Exception e){
                Log.e("ASDASD=>","ASDA");
                e.printStackTrace();
                Log.e("REOS","HistoricContainerSalesFamilyView-ListarHistoricContainerSalesFamily-e:"+e.toString());
            }
            obtenerHiloHistoricContainerFamilia=new ObtenerHiloHistoricContainerFamilia();
            pd.dismiss();
            Toast.makeText(getContext(), "Productos Familia Obtenidos Correctamente...", Toast.LENGTH_SHORT).show();
        }
    }
}
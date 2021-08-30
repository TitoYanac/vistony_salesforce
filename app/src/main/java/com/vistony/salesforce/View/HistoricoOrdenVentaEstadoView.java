package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.vistony.salesforce.Controller.Adapters.PageAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.Retrofit.HistoricoOrdenVentaWS;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
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
 * Use the {@link HistoricoOrdenVentaEstadoView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoOrdenVentaEstadoView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    private Toolbar tollbartab;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    ImageButton imb_calendario_historico_orden_venta_estado_inicio,imb_calendario_historico_orden_venta_estado_final;
    TextView tv_fecha_historico_orden_venta_estado_inicio,tv_fecha_historico_orden_venta_estado_final;
    com.omega_r.libs.OmegaCenterIconButton btnconsultarfechaordenventa;
    private  int diaov1,mesov1,añoov1,diaov2,mesov2,añoov2;
    private static DatePickerDialog oyenteSelectorFecha1,oyenteSelectorFecha2;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    private ProgressDialog pd;
    HiloObtenerHistoricoOrdenVentaEstado hiloObtenerHistoricoOrdenVentaEstado;
    public static ArrayList<ListaHistoricoOrdenVentaEntity> Lista;
    public static RutaVendedorView.OnFragmentInteractionListener mListener;

    public HistoricoOrdenVentaEstadoView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricoOrdenVentaEstado.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoOrdenVentaEstadoView newInstance(String param1, String param2) {
        HistoricoOrdenVentaEstadoView fragment = new HistoricoOrdenVentaEstadoView();
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
        v= inflater.inflate(R.layout.fragment_historico_orden_venta_estado, container, false);
        tollbartab=v.findViewById(R.id.tollbartab);
        viewPager=v.findViewById(R.id.viewpager);
        tabLayout=v.findViewById(R.id.tablayout);
        imb_calendario_historico_orden_venta_estado_inicio=(ImageButton)v.findViewById(R.id.imb_calendario_historico_orden_venta_estado_inicio);
        imb_calendario_historico_orden_venta_estado_final=(ImageButton)v.findViewById(R.id.imb_calendario_historico_orden_venta_estado_final);
        tv_fecha_historico_orden_venta_estado_inicio=(TextView) v.findViewById(R.id.tv_fecha_historico_orden_venta_estado_inicio);
        tv_fecha_historico_orden_venta_estado_final=(TextView) v.findViewById(R.id.tv_fecha_historico_orden_venta_estado_final);
        btnconsultarfechaordenventa=(com.omega_r.libs.OmegaCenterIconButton) v.findViewById(R.id.btnconsultarfechaordenventa);
        imb_calendario_historico_orden_venta_estado_inicio.setOnClickListener(this);
        imb_calendario_historico_orden_venta_estado_final.setOnClickListener(this);
        btnconsultarfechaordenventa.setOnClickListener(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        tv_fecha_historico_orden_venta_estado_inicio.setText(fecha);
        tv_fecha_historico_orden_venta_estado_final.setText(fecha);

        pageAdapter= new PageAdapter(getChildFragmentManager());
        //mAdapter=new TabsPagerAdapter(getActivity().getSupportFragmentManager(), tabLayout.getTabCount());



        return v;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_historico_orden_venta_estado_inicio:
                final Calendar c1 = Calendar.getInstance();
                diaov1 = c1.get(Calendar.DAY_OF_MONTH);
                mesov1 = c1.get(Calendar.MONTH);
                añoov1 = c1.get(Calendar.YEAR);
                oyenteSelectorFecha1 = new DatePickerDialog(getContext(),this,
                        añoov1,
                        mesov1,
                        diaov1
                );
                oyenteSelectorFecha1.show();
                break;
            case R.id.imb_calendario_historico_orden_venta_estado_final:
                final Calendar c2 = Calendar.getInstance();
                diaov2 = c2.get(Calendar.DAY_OF_MONTH);
                mesov2 = c2.get(Calendar.MONTH);
                añoov2 = c2.get(Calendar.YEAR);
                oyenteSelectorFecha2 = new DatePickerDialog(getContext(),this,
                        añoov2,
                        mesov2,
                        diaov2
                );
                oyenteSelectorFecha2.show();
                break;
            case R.id.btnconsultarfechaordenventa:
                Log.e("jpcm","Execute historico");
                String parametrofecha="";

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        hiloObtenerHistoricoOrdenVentaEstado =  new HiloObtenerHistoricoOrdenVentaEstado();
                        hiloObtenerHistoricoOrdenVentaEstado.execute();
                    }
                });
                break;
            default:
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String Dato,Object Lista);
    }

    private class HiloObtenerHistoricoOrdenVentaEstado extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Ordenes de Venta", true, false);
        }
        @Override
        protected Object doInBackground(String... arg0) {
            HistoricoOrdenVentaWS historicoOrdenVentaWS=new HistoricoOrdenVentaWS(getContext());
            ArrayList<ListaHistoricoOrdenVentaEntity> listaHistoricoOrdenVentaEntities=new ArrayList<>();
            ArrayList<ListaHistoricoOrdenVentaEntity> listaHistoricoOrdenVenta = new ArrayList<>();
            try {
                //Declara Variables
                ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaSQLite = new ArrayList<>();
                OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite = new OrdenVentaCabeceraSQLite(getContext());

                ClienteSQLiteEntity clienteSQLiteEntity=new ClienteSQLiteEntity();
                ArrayList<String> listadepuracion1 = new ArrayList<>();
                ArrayList<String> listadepuracion2 = new ArrayList<>();
                FormulasController formulasController=new FormulasController(getContext());

                //Consulta Webservice Historico Orden Venta
                listaHistoricoOrdenVentaEntities=historicoOrdenVentaWS.getHistoricoOrdenVentaEstado(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        SesionEntity.fuerzatrabajo_id,
                        tv_fecha_historico_orden_venta_estado_inicio.getText().toString(),
                        tv_fecha_historico_orden_venta_estado_final.getText().toString());
                Log.e("REOS","TamañoLista"+listaHistoricoOrdenVentaEntities.size());
                //Registra en listadepuracion1
                for (int g = 0; g < listaHistoricoOrdenVentaEntities.size(); g++) {

                    listadepuracion1.add(listaHistoricoOrdenVentaEntities.get(g).getOrdenventa_id());
                }

                //Consulta SQLite Orden Venta Cabecera
                listaOrdenVentaSQLite = ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraporFechaInicioyFinal
                        (formulasController.ObtenerFechaCadena(tv_fecha_historico_orden_venta_estado_inicio.getText().toString()),
                                formulasController.ObtenerFechaCadena(tv_fecha_historico_orden_venta_estado_final.getText().toString())
                                );

                //Registra en listadepuracion2
                for (int i = 0; i < listaOrdenVentaSQLite.size(); i++) {

                    listadepuracion2.add(listaOrdenVentaSQLite.get(i).getOrdenventa_id());
                }

                //Evalua listas
                Log.e("REOS","ListaDepuracion1"+listadepuracion1.size());
                Log.e("REOS","ListaDepuracion2"+listadepuracion2.size());
                if (!(listadepuracion1.size() == listadepuracion2.size())) {

                    listadepuracion2.removeAll(listadepuracion1);

                    for (int k = 0; k < listadepuracion2.size(); k++) {
                        Log.e("REOS","ListaDepuracion2PostRemoveAll"+listadepuracion2.size());
                        for (int l = 0; l < listaOrdenVentaSQLite.size(); l++) {
                            Log.e("REOS","listaOrdenVentaSQLite"+listaOrdenVentaSQLite.size());
                            if (listadepuracion2.get(k).equals(listaOrdenVentaSQLite.get(l).getOrdenventa_id())) {
                                String nombrecliente="";
                                ClienteSQlite clienteSQlite =new ClienteSQlite(getContext());
                                ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity=new ArrayList<>();
                                listaClienteSQLiteEntity= clienteSQlite.ObtenerDatosCliente(listaOrdenVentaSQLite.get(l).getCliente_id(),SesionEntity.compania_id);
                                for(int w=0;w<listaClienteSQLiteEntity.size();w++)
                                {
                                    nombrecliente=listaClienteSQLiteEntity.get(w).getNombrecliente();
                                }

                                ListaHistoricoOrdenVentaEntity listaHOV = new ListaHistoricoOrdenVentaEntity();


                                listaHOV.ordenventa_id = listaOrdenVentaSQLite.get(l).getOrdenventa_id();
                                listaHOV.cliente_id = listaOrdenVentaSQLite.get(l).getCliente_id();
                                listaHOV.rucdni = listaOrdenVentaSQLite.get(l).getRucdni();
                                listaHOV.nombrecliente = nombrecliente;
                                listaHOV.montototalorden = listaOrdenVentaSQLite.get(l).getMontototal();
                                listaHOV.estadoaprobacion = "Pendiente";
                                listaHOV.comentarioaprobacion = "";
                                listaHOV.ordenventa_erp_id = listaOrdenVentaSQLite.get(l).getOrdenventa_id();
                                listaHOV.comentariows = listaOrdenVentaSQLite.get(l).getMensajeWS();
                                if(listaOrdenVentaSQLite.get(l).getRecibidoERP().equals("1"))
                                {
                                    listaHOV.recepcionERPOV = true;
                                    listaHOV.ordenventa_erp_id = listaOrdenVentaSQLite.get(l).getOrdenventa_ERP_id();
                                }
                                else
                                {
                                    listaHOV.recepcionERPOV = false;
                                    listaHOV.ordenventa_erp_id = listaOrdenVentaSQLite.get(l).getOrdenventa_id();
                                }
                                if(listaOrdenVentaSQLite.get(l).getEnviadoERP().equals("1"))
                                {
                                    listaHOV.envioERPOV = true;
                                }
                                else
                                {
                                    listaHOV.envioERPOV = false;
                                }

                                listaHistoricoOrdenVentaEntities.add(listaHOV);
                            }
                        }

                    }
                }

                /*listaHistoricoOrdenVenta=historicoOrdenVentaWS.getHistoricoOrdenVenta(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        SesionEntity.fuerzatrabajo_id,
                        tv_fecha_historico_orden_venta.getText().toString());*/
                Log.e("REOS","TamañoListafinal"+listaHistoricoOrdenVenta.size());
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }

            return listaHistoricoOrdenVentaEntities;
        }

        protected void onPostExecute(Object result)
        {
             Lista = (ArrayList<ListaHistoricoOrdenVentaEntity>) result;
            Log.e("REOS","HistoricoOrdenVentaEstadoView:Lista.size()"+Lista.size());
            pageAdapter.addFRagment(new HistoricoOrdenVentaEstadoPendRevView(),"Pend.Rev");
            pageAdapter.addFRagment(new HistoricoOrdenVentaEstadoPendAprobView(),"Pend.Aprob");
            pageAdapter.addFRagment(new HistoricoOrdenVentaEstadoAprobacionView()," Aprobado");
            pageAdapter.addFRagment(new HistoricoOrdenVentaEstadoRechazadoView()," Rechazado");
            viewPager.setAdapter(pageAdapter);
            //pageAdapter.addFRagment(new RutaVendedorNoRutaView(),"NO RUTA");
            tabLayout.setupWithViewPager(viewPager);
            /*if (Lista.isEmpty())
            {
                Toast.makeText(getContext(), "Error en la Consulta", Toast.LENGTH_SHORT).show();
            }else
            {
                float monto_orden_venta=0f;
                listaHistoricoOrdenVentaAdapter = new ListaHistoricoOrdenVentaAdapter(getActivity(),
                        ListaHistoricoOrdenVentaDao.getInstance().getLeads(Lista));
                listviewhistoricoordenventa.setAdapter(listaHistoricoOrdenVentaAdapter);
                Toast.makeText(getContext(), "Ordenes de Venta Obtenidas Correctamente", Toast.LENGTH_SHORT).show();
                for(int i=0;i<Lista.size();i++)
                {
                    monto_orden_venta=monto_orden_venta+Float.parseFloat(Lista.get(i).getMontototalorden());
                }
                tv_cantidad__orden_venta.setText(String.valueOf(Lista.size()));
                tv_monto_orden_venta.setText(String.valueOf(monto_orden_venta));
            }*/
            pd.dismiss();
        }
    }
/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");

        if (context instanceof RutaVendedorView.OnFragmentInteractionListener) {
            mListener = (RutaVendedorView.OnFragmentInteractionListener) context;
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
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("jpcm","se cargo ruta vendedorrr");
        ListenerBackPress.setTemporaIdentityFragment("rutaVendedor");
        ListenerBackPress.setCurrentFragment("RutaVendedorView");
        if (context instanceof RutaVendedorView.OnFragmentInteractionListener) {
            mListener = (RutaVendedorView.OnFragmentInteractionListener) context;
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

}
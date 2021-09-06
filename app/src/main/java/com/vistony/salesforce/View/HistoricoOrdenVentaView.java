package com.vistony.salesforce.View;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import com.vistony.salesforce.Controller.Adapters.ListaHistoricoOrdenVentaAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoOrdenVentaDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricoOrdenVentaWS;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoricoOrdenVentaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoOrdenVentaView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, SearchView.OnQueryTextListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;
    ImageButton imb_calendario_historico_orden_venta;
    TextView tv_fecha_historico_orden_venta,tv_cantidad__orden_venta,tv_monto_orden_venta;
    Button btnconsultarfecha;
    ListView listviewhistoricoordenventa;
    private static DatePickerDialog oyenteSelectorFecha;
    private  int diaov,mesov,ANOV;
    private SearchView mSearchView;
    HiloObtenerHistoricoOrdenVenta hiloObtenerHistoricoOrdenVenta;
    ListaHistoricoOrdenVentaAdapter listaHistoricoOrdenVentaAdapter;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    private static OnFragmentInteractionListener mListener;
    private ProgressDialog pd;


    public HistoricoOrdenVentaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoricoOrdenVentaView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoOrdenVentaView newInstance(String param1, String param2) {
        HistoricoOrdenVentaView fragment = new HistoricoOrdenVentaView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static HistoricoOrdenVentaView newInstanceEnviarOrdenVentaID(ArrayList<ListaHistoricoOrdenVentaEntity>  Lista) {
        HistoricoOrdenVentaView fragment = new HistoricoOrdenVentaView();
        String Fragment="HistoricoOrdenVentaView";
        String accion="ordenventacabecera";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,Lista);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hiloObtenerHistoricoOrdenVenta = new HiloObtenerHistoricoOrdenVenta();
        setHasOptionsMenu(true);
        getActivity().setTitle("Consulta Orden Venta");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_historico_orden_venta_view, container, false);
        imb_calendario_historico_orden_venta=(ImageButton)v.findViewById(R.id.imb_calendario_historico_orden_venta);
        tv_fecha_historico_orden_venta=(TextView) v.findViewById(R.id.tv_fecha_historico_orden_venta);
        tv_cantidad__orden_venta=(TextView) v.findViewById(R.id.tv_cantidad__orden_venta);
        tv_monto_orden_venta=(TextView) v.findViewById(R.id.tv_monto_orden_venta);
        btnconsultarfecha=(Button) v.findViewById(R.id.btnconsultarfecha);
        listviewhistoricoordenventa=(ListView) v.findViewById(R.id.listviewhistoricoordenventa);
        imb_calendario_historico_orden_venta.setOnClickListener(this);
        btnconsultarfecha.setOnClickListener(this);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        tv_fecha_historico_orden_venta.setText(fecha);
        hiloObtenerHistoricoOrdenVenta =  new HiloObtenerHistoricoOrdenVenta();
        hiloObtenerHistoricoOrdenVenta.execute();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_historico_orden_venta:
                final Calendar c1 = Calendar.getInstance();
                diaov = c1.get(Calendar.DAY_OF_MONTH);
                mesov = c1.get(Calendar.MONTH);
                ANOV = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        ANOV,
                        mesov,
                        diaov
                );
                oyenteSelectorFecha.show();


                break;
            case R.id.btnconsultarfecha:
                Log.e("jpcm","Execute historico");
                String parametrofecha="";

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        hiloObtenerHistoricoOrdenVenta =  new HiloObtenerHistoricoOrdenVenta();
                        hiloObtenerHistoricoOrdenVenta.execute();
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

        tv_fecha_historico_orden_venta.setText(year + "-" + mes + "-" + dia);
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
        String text = newText;
        listaHistoricoOrdenVentaAdapter.filter(text);
        return true;
    }

    private class HiloObtenerHistoricoOrdenVenta extends AsyncTask<String, Void, Object> {
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
                listaHistoricoOrdenVentaEntities=historicoOrdenVentaWS.getHistoricoOrdenVenta( SesionEntity.imei,tv_fecha_historico_orden_venta.getText().toString());

                //Registra en listadepuracion1
                    for (int g = 0; g < listaHistoricoOrdenVentaEntities.size(); g++) {

                        listadepuracion1.add(listaHistoricoOrdenVentaEntities.get(g).getSalesOrderID());
                    }

                //Consulta SQLite Orden Venta Cabecera
                    listaOrdenVentaSQLite = ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraporFecha(formulasController.ObtenerFechaCadena(
                            tv_fecha_historico_orden_venta.getText().toString()),
                            SesionEntity.fuerzatrabajo_id,
                            SesionEntity.compania_id
                    );

                //Registra en listadepuracion2
                    for (int i = 0; i < listaOrdenVentaSQLite.size(); i++) {

                        listadepuracion2.add(listaOrdenVentaSQLite.get(i).getOrdenventa_id());
                    }

                //Evalua listas
                    if (!(listadepuracion1.size() == listadepuracion2.size())) {

                        listadepuracion2.removeAll(listadepuracion1);

                        for (int k = 0; k < listadepuracion2.size(); k++) {
                            for (int l = 0; l < listaOrdenVentaSQLite.size(); l++) {

                                if (listadepuracion2.get(k).equals(listaOrdenVentaSQLite.get(l).getOrdenventa_id())) {
                                    String nombrecliente="";
                                    ClienteSQlite clienteSQlite =new ClienteSQlite(getContext());
                                    ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity=new ArrayList<>();
                                    listaClienteSQLiteEntity= clienteSQlite.ObtenerDatosCliente(listaOrdenVentaSQLite.get(l).getCliente_id(),SesionEntity.compania_id);

                                    for(int w=0;w<listaClienteSQLiteEntity.size();w++){
                                        nombrecliente=listaClienteSQLiteEntity.get(w).getNombrecliente();
                                    }

                                    ListaHistoricoOrdenVentaEntity listaHOV = new ListaHistoricoOrdenVentaEntity();

                                    //listaHOV.ordenventa_id = listaOrdenVentaSQLite.get(l).getOrdenventa_id();
                                    listaHOV.setCardCode(listaOrdenVentaSQLite.get(l).getCliente_id());
                                    listaHOV.setLicTradNum(listaOrdenVentaSQLite.get(l).getRucdni());
                                    listaHOV.setCardName(nombrecliente);
                                    listaHOV.setDocTotal(listaOrdenVentaSQLite.get(l).getMontototal());

                                    Log.e("MontoTotal","=>"+listaOrdenVentaSQLite.get(l).getMontototal());

                                    listaHOV.setApprovalStatus("Pendiente");
                                    //listaHOV.comentarioaprobacion = "";
                                    listaHOV.setSalesOrderID(listaOrdenVentaSQLite.get(l).getOrdenventa_id());
                                    //listaHOV.set = listaOrdenVentaSQLite.get(l).getMensajeWS();

                                    /*if(listaOrdenVentaSQLite.get(l).getRecibidoERP().equals("1")){
                                        listaHOV.recepcionERPOV = true;
                                        listaHOV.ordenventa_erp_id = listaOrdenVentaSQLite.get(l).getOrdenventa_ERP_id();
                                    }else{
                                        listaHOV.recepcionERPOV = false;
                                        listaHOV.ordenventa_erp_id = listaOrdenVentaSQLite.get(l).getOrdenventa_id();
                                    }

                                    if(listaOrdenVentaSQLite.get(l).getEnviadoERP().equals("1")){
                                        listaHOV.envioERPOV = true;
                                    }else{
                                        listaHOV.envioERPOV = false;
                                    }*/

                                    listaHistoricoOrdenVentaEntities.add(listaHOV);
                                }
                            }

                        }
                    }

            } catch (Exception e){
                e.printStackTrace();
            }
            return listaHistoricoOrdenVentaEntities;
        }

        protected void onPostExecute(Object result)
        {
            ArrayList<ListaHistoricoOrdenVentaEntity> Lista = (ArrayList<ListaHistoricoOrdenVentaEntity>) result;
            if (Lista.isEmpty()){
                Toast.makeText(getContext(), "No hay datos disponibles", Toast.LENGTH_SHORT).show();
            }else{
                listaHistoricoOrdenVentaAdapter = new ListaHistoricoOrdenVentaAdapter(getActivity(), ListaHistoricoOrdenVentaDao.getInstance().getLeads(Lista));
                listviewhistoricoordenventa.setAdapter(listaHistoricoOrdenVentaAdapter);

                BigDecimal monto_total_orden_venta=new BigDecimal(0);
                for(int i=0;i<Lista.size();i++){
                    monto_total_orden_venta=monto_total_orden_venta.add(new BigDecimal(Lista.get(i).getDocTotal()));
                }
                tv_cantidad__orden_venta.setText(String.valueOf(Lista.size()));
                tv_monto_orden_venta.setText(Convert.currencyForView(monto_total_orden_venta.setScale(3, RoundingMode.HALF_UP).toString()));

                Toast.makeText(getContext(), "Ordenes de Venta Obtenidas Correctamente", Toast.LENGTH_SHORT).show();

            }
            pd.dismiss();
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historico_orden_venta, menu);
        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView();
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    /*
    @Override
    public void onAttach(Context context) {
        if(ListenerBackPress.getTemporaIdentityFragment() ==null || !ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa")){
            ListenerBackPress.setTemporaIdentityFragment("HistoricoDepositoViewCobranzasDetails");
            ListenerBackPress.setCurrentFragment("FormaAsociatyListCobranzaDeposito");
        }

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/
    @Override
    public void onAttach(Context context) {
        if(ListenerBackPress.getTemporaIdentityFragment() ==null || !ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa")){
            ListenerBackPress.setTemporaIdentityFragment("HistoricoDepositoViewCobranzasDetails");
            ListenerBackPress.setCurrentFragment("FormaAsociatyListCobranzaDeposito");
        }

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
        ListenerBackPress.setTemporaIdentityFragment("onDetach");
    }

}
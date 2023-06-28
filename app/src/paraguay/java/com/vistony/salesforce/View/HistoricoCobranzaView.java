package com.vistony.salesforce.View;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListaHistoricoCobranzaAdapter;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoCobranzaDao;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HistoricoCobranzaView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoricoCobranzaView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoricoCobranzaView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener,SearchView.OnQueryTextListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG_1 = "text";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final int MY_PERMISSIONS_REQUEST_CAMERA=1;
    static String tipofecha="0";
    private static OnFragmentInteractionListener mListener;
    public static ArrayList<ListaHistoricoDepositoEntity> Listado;
    static ObtenerHistoricoCobranza obtenerHistoricoCobranza;
    ToggleButton tglbtipofecha;
    static View v;
    TextView tv_fecha_historico_cobranza;
    ArrayList<ListaHistoricoCobranzaEntity> arraylistahistoricocobranzaentity;
    ListView listviewhistoricocobranzas;
    ListaHistoricoCobranzaAdapter listaHistoricoCobranzaAdapter;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha,parametrofecha;
    TextView tv_cantidad_cobranza,tv_monto_cobranza;
    String cantidad_cobranza,monto_cobranza,deposito_id;
    private  int diadespacho,mesdespacho,anodespacho,hora,minutos,diadespacho2,mesdespacho2,anodespacho2;
    private static DatePickerDialog oyenteSelectorFecha3,oyenteSelectorFecha4;
    String calendario="";
    private static final int TIPO_DIALOGO=0,TIPO_DIALOGO2=1;
    ImageButton imb_calendario_historico_cobranza;
    public static com.omega_r.libs.OmegaCenterIconButton btn_consultar_fecha_hisorico_cobranza;
    SearchView mSearchView;
    ConfigImpresoraView configImpresoraView;
    private ProgressDialog pd;
    Induvis induvis;


    //ListaHistoricoCobranzaAdapter listaClienteCabeceraAdapter;
    public HistoricoCobranzaView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HistoricoCobranzaView.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoricoCobranzaView newInstance(Object param1,String accion) {
        ListenerBackPress.setCurrentFragment("FormaAsociatyListCobranzaDeposito");

        HistoricoCobranzaView fragment = new HistoricoCobranzaView();
        Bundle b = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //fragment.setArguments(args);
        tipofecha=accion;
        ArrayList<ListaHistoricoDepositoEntity> Lista = (ArrayList<ListaHistoricoDepositoEntity>) param1;
        Lista.size();
        b.putSerializable(TAG_1,Lista);
        fragment.setArguments(b);
        return fragment;
    }

    public static HistoricoCobranzaView newInstancia(String param1) {
        HistoricoCobranzaView fragment = new HistoricoCobranzaView();
        Bundle b = new Bundle();
        b.putString(ARG_PARAM1, param1);
        fragment.setArguments(b);

        return fragment;
    }

    public static HistoricoCobranzaView newInstancias (Object param1) {
        HistoricoCobranzaView fragment = new HistoricoCobranzaView();

        String Fragment="ClienteDetalleView";
        String accion="recuperar";
        String compuesto=Fragment+"-"+accion;
        mListener.onFragmentInteraction(compuesto,param1);
        return fragment;
    }

    public  static HistoricoCobranzaView newInstanciaAnulacion(String accion) {
        Log.e("jpcm","Enetra aqui apra que salga error");
        obtenerHistoricoCobranza.execute();
        HistoricoCobranzaView fragment = new HistoricoCobranzaView();
        return fragment;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Consulta Cobrado");
        Listado = new ArrayList<ListaHistoricoDepositoEntity>();
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            //Listado = new  ArrayList<ListaHistoricoDepositoEntity>();
            Listado = (ArrayList<ListaHistoricoDepositoEntity>) getArguments().getSerializable(TAG_1);
            if(Listado.isEmpty())
            {
                mParam1 = getArguments().getString(ARG_PARAM1);
                if (mParam1.equals("COBRANZA")) {

                }

            }else
            {
                for(int i=0;i<Listado.size();i++)
                {
                    deposito_id=Listado.get(i).getDeposito_id();
                }
            }
        }

       /* if (SesionEntity.loginSesion.equals("0")) {
            configImpresoraView = new ConfigImpresoraView();
            configImpresoraView.OpenPrinter(MenuView.indicador, getContext());
            SesionEntity.loginSesion = "1";
        }
        MenuView.indicador="3";*/

        String vinculaimpresora="0";
        ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;

        ConfiguracionSQLiteDao configuracionSQLiteDao2 =  new ConfiguracionSQLiteDao(getContext());
        arraylistConfiguracionentity=configuracionSQLiteDao2.ObtenerConfiguracion();
        for(int i=0;i<arraylistConfiguracionentity.size();i++)
        {
            vinculaimpresora=arraylistConfiguracionentity.get(i).getVinculaimpresora();

        }

        if ((vinculaimpresora.equals("0"))) {
            configImpresoraView = new ConfigImpresoraView();
            configImpresoraView.OpenPrinter(
                    //MenuView.indicador
                    "0"
                    , getContext());
            SesionEntity.loginSesion = "1";
        }
        MenuView.indicador="3";
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        parametrofecha=fecha;
        induvis=new Induvis();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[] {android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
            {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {

                } else
                {
                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED))
                    {

                        requestPermissions(new String[] {android.Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                    //break;
                    //}
                }
                break;
            }
        }

    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_historico_cobranza_view, container, false);
        tglbtipofecha = (ToggleButton)v.findViewById(R.id.tglbtipofecha);
        tv_fecha_historico_cobranza=(TextView) v.findViewById(R.id.tv_fecha_historico_cobranza);
        tv_fecha_historico_cobranza.setText(induvis.getDate(BuildConfig.FLAVOR,fecha));
        listviewhistoricocobranzas = (ListView) v.findViewById(R.id.listviewhistoricocobranzas);
        imb_calendario_historico_cobranza = (ImageButton) v.findViewById(R.id.imb_calendario_historico_cobranza);
        imb_calendario_historico_cobranza.setOnClickListener(this);
        btn_consultar_fecha_hisorico_cobranza = v.findViewById(R.id.btn_consultar_fecha_hisorico_cobranza);
        btn_consultar_fecha_hisorico_cobranza.setOnClickListener(this);
        tv_fecha_historico_cobranza.setText(induvis.getDate(BuildConfig.FLAVOR,fecha));
        tv_cantidad_cobranza=(TextView) v.findViewById(R.id.tv_cantidad_cobranza);
        tv_monto_cobranza=(TextView) v.findViewById(R.id.tv_monto_cobranza);

        if(Listado.isEmpty())
        {
            tglbtipofecha.setChecked(false);
            tipofecha=tglbtipofecha.getTextOff().toString();

        }
        else
        {
            imb_calendario_historico_cobranza.setEnabled(false);
            btn_consultar_fecha_hisorico_cobranza.setEnabled(false);
        }

        if(tipofecha.equals("Deposito"))
        {
            tglbtipofecha.setChecked(true);
        }

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                obtenerHistoricoCobranza =  new ObtenerHistoricoCobranza();
                obtenerHistoricoCobranza.execute();

            }
        });

        return  v;
    }

    // TODO: Rename method, update argument and hook method into UI event
   /* public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/
/*
    @Override
    public void onAttach(Context context) {
        if(ListenerBackPress.getTemporaIdentityFragment() ==null || !ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa")){
            ListenerBackPress.setTemporaIdentityFragment("HistoricoDepositoViewCobranzasDetails");
            ListenerBackPress.setCurrentFragment("FormaAsociatyListCobranzaDeposito");
        }else{
            ListenerBackPress.setTemporaIdentityFragment("HistoricoCobranzaView");
            ListenerBackPress.setCurrentFragment("onAttach");
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
        ListenerBackPress.setTemporaIdentityFragment("HistoricoCobranzaView");
        ListenerBackPress.setCurrentFragment("onDetach");
    }
*/
    @Override
    public void onAttach(Context context) {
        if(ListenerBackPress.getTemporaIdentityFragment() ==null || !ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa")){
            ListenerBackPress.setTemporaIdentityFragment("HistoricoDepositoViewCobranzasDetails");
            ListenerBackPress.setCurrentFragment("FormaAsociatyListCobranzaDeposito");
        }else{
            ListenerBackPress.setTemporaIdentityFragment("HistoricoCobranzaView");
            ListenerBackPress.setCurrentFragment("onAttach");
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
        ListenerBackPress.setTemporaIdentityFragment("HistoricoCobranzaView");
        ListenerBackPress.setCurrentFragment("onDetach");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imb_calendario_historico_cobranza:
                final Calendar c1 = Calendar.getInstance();
                diadespacho = c1.get(Calendar.DAY_OF_MONTH);
                mesdespacho = c1.get(Calendar.MONTH);
                anodespacho = c1.get(Calendar.YEAR);
                oyenteSelectorFecha3 = new DatePickerDialog(getContext(),this,
                        anodespacho,
                        mesdespacho,
                        diadespacho
                );
                oyenteSelectorFecha3.show();


                break;
            case R.id.btn_consultar_fecha_hisorico_cobranza:
                Log.e("jpcm","Execute historico");
                String parametrofecha="";

                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        obtenerHistoricoCobranza =  new ObtenerHistoricoCobranza();
                        obtenerHistoricoCobranza.execute();

                    }
                });




                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    private void setText(final TextView text,final String value){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    private class ObtenerHistoricoCobranza extends AsyncTask<String, Void, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Consultando Recibos", true, false);
        }
        @Override
        protected String doInBackground(String... arg1) {

            try {
                String banco="0",deposito_id="1",fecha="";
                arraylistahistoricocobranzaentity =  new ArrayList<ListaHistoricoCobranzaEntity>();
                CobranzaRepository cobranzaRepository=new CobranzaRepository(getContext());

                Log.e("REOS","HistoricoCobranzaView-Listado: "+Listado.size());
                if(!(Listado.isEmpty())){
                    Log.e("REOS","HistoricoCobranzaView-NoListadoEmpty");
                    for(int i=0;i<Listado.size();i++)
                    {
                        banco=Listado.get(i).getBanco_id();
                        deposito_id=Listado.get(i).getDeposito_id();
                        if(tipofecha.equals("Deposito"))
                        {
                            fecha=Listado.get(i).getFechadeposito();
                        }
                        else if (tipofecha.equals("COBRANZA"))
                        {
                            fecha=tv_fecha_historico_cobranza.getText().toString();
                        }
                    }

                }
                else
                {
                    Log.e("REOS","HistoricoCobranzaView-ListadoEmpty");
                    fecha = tv_fecha_historico_cobranza.getText().toString();

                }

                ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();

                //if (networkInfo != null || ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa")) {
                //    if (ListenerBackPress.getTemporaIdentityFragment().equals("ConsultaHistoricoCobranzaa") || networkInfo.getState() == NetworkInfo.State.CONNECTED ) {

                  /*  arraylistahistoricocobranzaentity = historicoCobranzaWSDao.obtenerHistoricoCobranza(
                            SesionEntity.imei,
                            SesionEntity.compania_id,
                            banco,
                            deposito_id,
                            tipofecha,
                            fecha,
                            SesionEntity.fuerzatrabajo_id,
                            ""
                    );*/
                Log.e("REOS","HistoricoCobranzaView-tipofecha: "+tipofecha);
                Log.e("REOS","HistoricoCobranzaView-ingreso con Internet");
                HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(getContext());
                   /*arraylistahistoricocobranzaentity = historicoCobranzaWS.getHistoricoCobranza
                                   (
                            SesionEntity.imei,
                            //SesionEntity.compania_id,
                            //banco,
                                 //          "''",
                                           //deposito_id,
                                 //          "''",

                            fecha,
                                           tipofecha
                           // SesionEntity.fuerzatrabajo_id,
                            //"''"
                    );*/
                arraylistahistoricocobranzaentity = cobranzaRepository.getHistoricoCobranza
                        (SesionEntity.imei, fecha, tipofecha,deposito_id);
                Log.e("REOS","HistoricoCobranzaView-ObtenerHistoricoCobranza-arraylistahistoricocobranzaentity.size(): "+arraylistahistoricocobranzaentity.size());
                Log.e("REOS","HistoricoCobranzaView-fecha: "+fecha);
                Log.e("REOS","HistoricoCobranzaView-tipofecha"+tipofecha);
                if((Listado.isEmpty())){

                    ArrayList<String> listadepuracion1 = new ArrayList<>();
                    ArrayList<String> listadepuracion2 = new ArrayList<>();

                    for (int g = 0; g < arraylistahistoricocobranzaentity.size(); g++) {

                        listadepuracion1.add(arraylistahistoricocobranzaentity.get(g).getRecibo());
                    }

                    ArrayList<CobranzaDetalleSQLiteEntity> listareciboSQLite = new ArrayList<>();
                    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
                    ArrayList<ListaHistoricoCobranzaEntity> listaHistoricoCobranzaEntities = new ArrayList<>();

                    listareciboSQLite = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleConsultaCobradoFecha(parametrofecha, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id);
                    Log.e("REOS","HistoricoCobranzaView-listareciboSQLite.size(): "+listareciboSQLite.size());
                    Log.e("REOS","HistoricoCobranzaView-listareciboSQLite"+listareciboSQLite.size());
                    for (int i = 0; i < listareciboSQLite.size(); i++) {
                        listadepuracion2.add(listareciboSQLite.get(i).getRecibo());
                    }
                    Log.e("REOS","HistoricoCobranzaView-listadepuracion1"+listadepuracion1.size());
                    Log.e("REOS","HistoricoCobranzaView-listadepuracion2"+listadepuracion2.size());
                    if (!(listadepuracion1.size() == listadepuracion2.size())) {
                        listadepuracion2.removeAll(listadepuracion1);
                        for (int k = 0; k < listadepuracion2.size(); k++) {
                            for (int l = 0; l < listareciboSQLite.size(); l++) {
                                if (listadepuracion2.get(k).equals(listareciboSQLite.get(l).getRecibo())) {
                                    ListaHistoricoCobranzaEntity listaHCE = new ListaHistoricoCobranzaEntity();
                                    listaHCE.bancarizacion = listareciboSQLite.get(l).getChkbancarizado();
                                    listaHCE.banco_id = listareciboSQLite.get(l).getBanco_id();
                                    listaHCE.cliente_id = listareciboSQLite.get(l).getCliente_id();
                                    ArrayList<ClienteSQLiteEntity> listaclienteSQLiteEntity = new ArrayList<>();
                                    ClienteSQlite clienteSQlite = new ClienteSQlite(getContext());
                                    listaclienteSQLiteEntity = clienteSQlite.ObtenerDatosCliente(listareciboSQLite.get(l).getCliente_id(), SesionEntity.compania_id);
                                    for (int m=0;m<listaclienteSQLiteEntity.size();m++)
                                    {
                                        listaHCE.cliente_nombre = listaclienteSQLiteEntity.get(m).getNombrecliente();
                                    }

                                    //listaHCE.comentario=listareciboSQLite.get(l).getComen
                                    listaHCE.compania_id = listareciboSQLite.get(l).getCompania_id();
                                    listaHCE.deposito_id = listareciboSQLite.get(l).getCobranza_id();
                                    listaHCE.detalle_item = listareciboSQLite.get(l).getId();
                                    listaHCE.documento_id = listareciboSQLite.get(l).getDocumento_id();
                                    listaHCE.estado = "PENDIENTE";
                                    Log.e("REOS","HistoricoCobranzaView-QRvalidado: "+listareciboSQLite.get(l).getChkqrvalidado().toString());
                                    if (listareciboSQLite.get(l).getChkqrvalidado().equals("Y")) {
                                        listaHCE.estadoqr = "True";
                                    } else {
                                        listaHCE.estadoqr = "False";
                                    }
                                    String fechaCobro;
                                    if(listareciboSQLite.get(l).getFechacobranza().length()==10)
                                    {
                                           /* String[] sourceSplitemision2= listareciboSQLite.get(l).getFechacobranza().split("-");
                                            String anioemision= sourceSplitemision2[0];
                                            String mesemision= sourceSplitemision2[1];
                                            String diaemision= sourceSplitemision2[2];

                                            fechaCobro=diaemision+"/"+mesemision+"/"+anioemision+" 00:00:00";
                                            */
                                        fechaCobro=listareciboSQLite.get(l).getFechacobranza();
                                    }
                                    else
                                    {
                                        fechaCobro=listareciboSQLite.get(l).getFechacobranza();
                                    }
                                    listaHCE.fechacobranza = fechaCobro;

                                    listaHCE.importedocumento = listareciboSQLite.get(l).getImportedocumento();
                                    listaHCE.montocobrado = listareciboSQLite.get(l).getSaldocobrado();
                                    listaHCE.motivoanulacion = listareciboSQLite.get(l).getMotivoanulacion();
                                    listaHCE.nro_documento = listareciboSQLite.get(l).getNrofactura();
                                    listaHCE.nuevosaldodocumento = listareciboSQLite.get(l).getNuevosaldodocumento();
                                    listaHCE.recibo = listareciboSQLite.get(l).getRecibo();
                                    listaHCE.saldodocumento = listareciboSQLite.get(l).getSaldodocumento();
                                    //listaHCE.tipoingreso=listareciboSQLite.get(l).ge
                                    listaHCE.usuario_id = listareciboSQLite.get(l).getUsuario_id();
                                    listaHCE.chkwsrecibido = listareciboSQLite.get(l).getChkwsrecibido();
                                    listaHCE.depositodirecto = listareciboSQLite.get(l).getPagodirecto();
                                    listaHCE.mensajeWS = listareciboSQLite.get(l).getMensajews();
                                    //listaHCE.chkconciliado=listareciboSQLite.get(l).getC
                                    arraylistahistoricocobranzaentity.add(listaHCE);
                                }
                            }

                        }
                    }

                }

                BigDecimal montocobrado=new BigDecimal("0");
                for(int i=0;i<arraylistahistoricocobranzaentity.size();i++)
                {
                    montocobrado=montocobrado.add(new BigDecimal(arraylistahistoricocobranzaentity.get(i).getMontocobrado()));
                }


                setText(tv_monto_cobranza, Convert.currencyForView(montocobrado.setScale(3, RoundingMode.HALF_UP).toString()));

                cantidad_cobranza=String.valueOf(arraylistahistoricocobranzaentity.size());

                setText(tv_cantidad_cobranza,cantidad_cobranza);



            } catch (Exception e) {


                Log.e("jpcm","error "+e);
                e.printStackTrace();
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
            int validar=0;
            listviewhistoricocobranzas.setAdapter(null);

            if(arraylistahistoricocobranzaentity.size()>0)
            {
                validar=1;
                //listaConsDepositoAdapter.clear();
                listaHistoricoCobranzaAdapter = new ListaHistoricoCobranzaAdapter(getActivity(), ListaHistoricoCobranzaDao.getInstance().getLeads(arraylistahistoricocobranzaentity));
                listviewhistoricocobranzas.setAdapter(listaHistoricoCobranzaAdapter);
            }
            else
            {
                validar=0;
            }

            pd.dismiss();
            if(validar==1)
            {
                Toast.makeText(getContext(), "Cobranzas Obtenidas Exitosamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "No se obtuvieron Cobranzas", Toast.LENGTH_LONG).show();
            }

            obtenerHistoricoCobranza =  new ObtenerHistoricoCobranza();

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
        parametrofecha=year+mes+dia;
        tv_fecha_historico_cobranza.setText(year + "-" + mes + "-" + dia);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_historico_cobranza, menu);
       /* mSearchView=(SearchView) v.findViewById(R.id.);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.app_bar_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        */
        MenuItem searchItem = menu.findItem(R.id.app_bar_search3);
        mSearchView = (SearchView) searchItem.getActionView();
        //mSearchView=(SearchView) menu.findItem(R.id.app_bar_search);
        //v.findViewById(R.id.app_bar_search);

        setupSearchView();
        //obtenerSQLiteCliente.execute();


        super.onCreateOptionsMenu(menu, inflater);

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
        if(listaHistoricoCobranzaAdapter!=null) {
        listaHistoricoCobranzaAdapter.filter(newText);
        }
        return true;
    }


}

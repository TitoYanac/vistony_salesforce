package com.vistony.salesforce.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
/*
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
*/
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Adapters.ListaParametrosAdapter;
import com.vistony.salesforce.Controller.Funcionalidades.FormulasController;
import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Dao.Network.XML.OrdenVentaWSDao;
import com.vistony.salesforce.Dao.Retrofit.AgenciaWS;
import com.vistony.salesforce.Dao.Retrofit.BancoWS;
import com.vistony.salesforce.Dao.Retrofit.CatalogoWS;
import com.vistony.salesforce.Dao.Retrofit.ClienteWS;
import com.vistony.salesforce.Dao.Retrofit.CobranzaCabeceraWS;
import com.vistony.salesforce.Dao.Retrofit.DocumentoDeudaWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.Retrofit.ListaPrecioDetalleWS;
import com.vistony.salesforce.Dao.Retrofit.ListaPromocionWS;
import com.vistony.salesforce.Dao.Retrofit.PromocionCabeceraWS;
import com.vistony.salesforce.Dao.Retrofit.PromocionDetalleWS;
import com.vistony.salesforce.Dao.Retrofit.RutaFuerzaTrabajoWS;
import com.vistony.salesforce.Dao.Retrofit.StockWS;
import com.vistony.salesforce.Dao.Retrofit.TerminoPagoWS;
import com.vistony.salesforce.Dao.SQLIte.AgenciaSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.BancoSQLiteDAO;
import com.vistony.salesforce.Dao.SQLIte.CatalogoSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.ClienteSQliteDAO;
import com.vistony.salesforce.Dao.SQLIte.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.DireccionClienteSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.DocumentoDeudaSQLiteDao;
import com.vistony.salesforce.Dao.Adapters.ListaParametrosDao;
import com.vistony.salesforce.Dao.SQLIte.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.ListaPromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.OrdenVentaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.ParametrosSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.PromocionDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.StockSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.TerminoPagoSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.VisitaSQLiteDAO;
import com.vistony.salesforce.Dao.SQLIte.ZonaSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CatalogoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DireccionClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ParametrosSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.RutaVendedorSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.StockSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ParametrosView extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private View v;
    private ListView listviewparametro;
    public static ClienteSQliteDAO clienteSQliteDAO;
    private BancoSQLiteDAO bancoSQLiteDAO;
    private BancoSQLiteDAO bancoSQLiteDAO2;
    private DocumentoDeudaSQLiteDao documentoDeudaSQLiteDao;
    private CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao;
    private ObtenerWSParametros obtenerWSParametros;
    private SesionEntity sesionEntity;
    private TerminoPagoSQLiteDao terminoPagoSQLiteDao;
    private AgenciaSQLiteDao agenciaSQLiteDao;
    private ListaPromocionSQLiteDao listaPromocionSQLiteDao;
    private ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao;
    private StockSQLiteDao stockSQLiteDao;
    private PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao;
    private PromocionDetalleSQLiteDao promocionDetalleSQLiteDao;
    private RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDao;
    private CatalogoSQLiteDao catalogoSQLiteDao;
    private DireccionClienteSQLiteDao direccionClienteSQLiteDao;
    private ZonaSQLiteDao zonaSQLiteDao;
    private ClienteSQliteDAO clienteSQliteDAO2;
    private ProgressDialog pd;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String imei,compania_id,fuerzatrabajo_id;
    private OnFragmentInteractionListener mListener;
    static List<ClienteSQliteDAO> Llogin;
    static List<ClienteSQLiteEntity> LCliente;
    static List<BancoSQLiteEntity> LBanco;
    static List<DocumentoDeudaSQLiteEntity> LDDeuda;
    static ArrayList<ClienteSQLiteEntity> listaclientesqlSQLiteEntity;
    static List<RutaVendedorSQLiteEntity> LRVendedor;
    static List<TerminoPagoSQLiteEntity> LTPago;
    static List<AgenciaSQLiteEntity> LAgencia;
    static List<ListaPrecioDetalleSQLiteEntity> LPDetalle;
    static List<StockSQLiteEntity> LStock;
    static List<ListaPromocionSQLiteEntity> LPromocion;
    static List<PromocionCabeceraSQLiteEntity> LPCabecera;
    static List<PromocionDetalleSQLiteEntity> LPromocionDetalle;
    static List<RutaFuerzaTrabajoSQLiteEntity> LRutaFuerzaTrabajo;
    static List<CatalogoSQLiteEntity> LCatalogo;
    static List<DireccionClienteSQLiteEntity> LDCliente;
    static List<HojaDespachoSQLiteEntity> LHDespacho;
    List<ClienteSQLiteEntity> LclientesqlSQLiteEntity;
    SQLiteController sqLiteController;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    ListaParametrosAdapter listaParametrosAdapter;
    ListaParametrosEntity listaParametrosEntity;
    ArrayList<ListaParametrosEntity> arraylistaparametrosentity;
    ParametrosSQLiteEntity parametrosSQLiteEntity;
    ParametrosSQLiteDao parametrosSQLiteDao;
    FloatingActionButton fabdescargarparametros;
    ConfigImpresoraView configImpresoraView;
    ArrayList<UsuarioSQLiteEntity> listaUsuarioSQLiteEntity;
    UsuarioSQLiteDao usuarioSQLiteDao;
    String conexion="";
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION=1;

    private OrdenVentaWSDao ordenVentaWSDao;
    private Observer<String []> viewModelOrdenDeVenta = null;

    //HiloEnviarNubeOV hiloEnviarNubeOV;
    public ParametrosView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *


     * @return A new instance of fragment ParametrosView.
     */
    // TODO: Rename and change types and number of parameters
    public static ParametrosView newInstance(String param1) {
        ParametrosView fragment = new ParametrosView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesionEntity =  new SesionEntity();
        clienteSQliteDAO = new ClienteSQliteDAO(getContext());
        listaclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        bancoSQLiteDAO = new BancoSQLiteDAO(getContext());
        documentoDeudaSQLiteDao = new DocumentoDeudaSQLiteDao(getContext());
        cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(getContext());
        parametrosSQLiteDao = new ParametrosSQLiteDao(getContext());
        terminoPagoSQLiteDao =  new TerminoPagoSQLiteDao(getContext());
        agenciaSQLiteDao  = new AgenciaSQLiteDao(getContext());
        listaPrecioDetalleSQLiteDao =  new ListaPrecioDetalleSQLiteDao(getContext());
        stockSQLiteDao = new StockSQLiteDao(getContext());
        listaPromocionSQLiteDao = new ListaPromocionSQLiteDao(getContext());
        promocionCabeceraSQLiteDao =  new PromocionCabeceraSQLiteDao(getContext());
        promocionDetalleSQLiteDao = new PromocionDetalleSQLiteDao(getContext());
        catalogoSQLiteDao=new CatalogoSQLiteDao(getContext());
        direccionClienteSQLiteDao=new DireccionClienteSQLiteDao(getContext());
        obtenerWSParametros =  new ObtenerWSParametros();
        LCliente= new ArrayList<ClienteSQLiteEntity>();
        LBanco = new ArrayList<BancoSQLiteEntity>();
        LDDeuda = new ArrayList<DocumentoDeudaSQLiteEntity>();
        LRVendedor = new ArrayList<RutaVendedorSQLiteEntity>();
        LTPago = new ArrayList<>();
        LAgencia = new ArrayList<>();
        LPDetalle =  new ArrayList<>();
        LPromocion =  new ArrayList<>();
        LPromocionDetalle = new ArrayList<>();
        LCatalogo= new ArrayList<>();
        LDCliente = new ArrayList<>();
        zonaSQLiteDao = new ZonaSQLiteDao(getContext());
        obtenerPametros();
        LclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        //rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        sqLiteController =  new SQLiteController(getContext());
        usuarioSQLiteDao = new UsuarioSQLiteDao(getContext());
        listaUsuarioSQLiteEntity = new ArrayList<UsuarioSQLiteEntity>();
        listaUsuarioSQLiteEntity = usuarioSQLiteDao.ObtenerUsuarioSesion();
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        rutaFuerzaTrabajoSQLiteDao=new RutaFuerzaTrabajoSQLiteDao(getContext());
       // for(int i=0;i<listaUsuarioSQLiteEntity.size();i++)
        //{
       //     conexion=listaUsuarioSQLiteEntity.get(i).getOnline();
        //}
        //sqLiteController.deleteDatabase(getContext());

       // if(conexion.equals("1"))
       // {
            obtenerWSParametros.execute("Todos");
       // }
      //  else if(conexion.equals("0"))
      //  {
      //      obtenerWSParametros.execute("");

      //  }






        //clienteWSDao.obtenerCliente()


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            //mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        getActivity().setTitle("Parametros");
        v = inflater.inflate(R.layout.fragment_parametros_view, container, false);

        ordenVentaWSDao = new ViewModelProvider(this).get(OrdenVentaWSDao.class);
        viewModelOrdenDeVenta = new Observer<String []>() {
            @Override
            public void onChanged(@Nullable final String [] newName) {

                Toast.makeText(getContext(), "Ya respondio ov enviada", Toast.LENGTH_SHORT).show();
            }
        };

        listviewparametro = (ListView) v.findViewById(R.id.listparametro);
        fabdescargarparametros = (FloatingActionButton) v.findViewById(R.id.fabdescargarparametros);

        fabdescargarparametros.setOnClickListener(view -> {
            Object objeto=null,object2=null;
            //listaConsDepositoAdapter = new ListaConsDepositoAdapter();
            objeto=listaParametrosAdapter.ObtenerListaParametros();
            //object2=objeto;
            //listaConsDepositoEntity = new ArrayList<ListaConsDepositoEntity>();
            arraylistaparametrosentity = (ArrayList<ListaParametrosEntity>) objeto;
            String [] valores=new String[]{"","","","","","","","","","","","","","",""};
            int p=0;
            for(int i=0;i<arraylistaparametrosentity.size();i++){
                if(arraylistaparametrosentity.get(i).isChkparametro()){
                    valores[p]=(arraylistaparametrosentity.get(i).getNombreparametro().toString());

                    p++;
                }
            }
            obtenerWSParametros =  new ObtenerWSParametros();
            obtenerWSParametros.execute(valores);

        });


        if(conexion.equals("0"))
        {
            fabdescargarparametros.setEnabled(false);
        }
        return v;
    }

    private class ObtenerWSParametros extends AsyncTask<String, Void, String> {
        String argumento="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Cargando Parametros", true, false);
        }
        @Override
        protected String doInBackground(String... arg0) {
            try {
                int CantClientes=0,CantBancos=0,CantDocumentosDeuda=0,CantRutaVendedor=0,CantTerminoPago=0,
                        CantAgencia=0,CantListaPrecioDetalle=0,CantStock=0,CantListaPromocion=0,CantPromocionCabecera=0,
                CantPromocionDetalle=0,CantRutaFuerzaTrabajo=0,CantCatalogo=0,CantDireccionCliente=0,CantHojaDespacho=0
                ;

                //dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                Date date2 = new Date();
                String fecha2 =dateFormat2.format(date2);


                for(int i=0;i<arg0.length;i++) {
                    argumento = arg0[i];
                    if (argumento.equals("Todos")) {

                        /*SINCRONIZAR AL CARGAR PARAMETROS*/
                        String resultadocantidadcobranzadetalle;
                        CobranzaDetalleSQLiteDao cantidadregistroscobranzadetalle = new CobranzaDetalleSQLiteDao(getContext());
                        resultadocantidadcobranzadetalle = cantidadregistroscobranzadetalle.ObtenerCantidadCobranzaDetalle(SesionEntity.usuario_id, SesionEntity.compania_id);
                        if (resultadocantidadcobranzadetalle.equals("0")) {
                            ObtenerRecibosPendientes();
                        }
                        /*FIN*/


                        listaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                        listaParametrosEntity = new ListaParametrosEntity();
                        arraylistaparametrosentity = new ArrayList<ListaParametrosEntity>();
                        ArrayList<ParametrosSQLiteEntity> listaparametrosSQLiteEntity = new ArrayList<>();
                        ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity = new ArrayList<>();
                        CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(getContext());
                        listaCobranzaCabeceraSQLiteEntity =
                                cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraPendientesWS(SesionEntity.compania_id, SesionEntity.usuario_id);

                        //Envio de Cabecera a WsService
                        for (int j = 0; j < listaCobranzaCabeceraSQLiteEntity.size(); j++) {
                            String resultadoccabeceraenviows = "0";
                            /*resultadoccabeceraenviows =
                                    cobranzaCabeceraWSDao.RegistraDeposito
                                            (
                                                    SesionEntity.imei,
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getCompania_id(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getBanco_id(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getTipoingreso(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getUsuario_id(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFechadeposito(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getTotalmontocobrado(),
                                                    "0",
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFuerzatrabajo_id(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getChkbancarizado(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFechadiferido(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getComentarioanulado(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getPagodirecto()
                                            );*/
                            CobranzaCabeceraWS cobranzaCabeceraWS=new CobranzaCabeceraWS(getContext());
                            //int resultado=0;
                            resultadoccabeceraenviows=
                                    cobranzaCabeceraWS.PostCobranzaCabeceraWS
                                            (
                                                    SesionEntity.imei,
                                                    "CREATE",
                                                    SesionEntity.compania_id,
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getBanco_id(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getTipoingreso(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id(),
                                                    SesionEntity.usuario_id,
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFechadeposito(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getTotalmontocobrado(),
                                                    "Pendiente",
                                                    "0",
                                                    SesionEntity.fuerzatrabajo_id,
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getChkbancarizado(),
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFechadiferido(),
                                                    "0",
                                                    listaCobranzaCabeceraSQLiteEntity.get(j).getPagodirecto(),
                                                    "0"
                                            );


                            //resultadoccabeceraenviows=String.valueOf(resultado);

                            cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id(),
                                    listaCobranzaCabeceraSQLiteEntity.get(j).getCompania_id(),
                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFuerzatrabajo_id(),
                                    resultadoccabeceraenviows);
                        }

                        /*********************************/
                        /********************************* ENVIO DE VISITAS *********************************/
                        /*********************************/

                        VisitaSQLiteDAO visitaSQLiteDAO = new VisitaSQLiteDAO(getActivity());
                        ArrayList<VisitaSQLiteEntity> listaVisitaSQLiteEntity = new ArrayList<>();
                        listaVisitaSQLiteEntity = visitaSQLiteDAO.ObtenerVisitas();
                        //VisitaWSDAO visitaWSDAO = new VisitaWSDAO(getActivity());
                        FormulasController formulasController=new FormulasController(getContext());
                        for (int j = 0; j < listaVisitaSQLiteEntity.size(); j++) {
                            /*Log.e("jpmc fecha=>", "" + listaVisitaSQLiteEntity.get(j).getFecha_registro().toString());
                            visitaWSDAO.RegistraDeposito
                                    (
                                            listaVisitaSQLiteEntity.get(j).getFecha_registro(),
                                            listaVisitaSQLiteEntity.get(j).getMotivo(),
                                            listaVisitaSQLiteEntity.get(j).getTipo(),
                                            listaVisitaSQLiteEntity.get(j).getCompania_id(),
                                            listaVisitaSQLiteEntity.get(j).getUsuario_id(),
                                            listaVisitaSQLiteEntity.get(j).getFuerzatrabajo_id(),
                                            listaVisitaSQLiteEntity.get(j).getCliente_id(),
                                            listaVisitaSQLiteEntity.get(j).getDireccion_id(),
                                            SesionEntity.imei
                                    );*/
                            ConnectivityManager manager= (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);;
                            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

                            if (networkInfo != null) {
                                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {


                                    /*********************************/
                                    /********************************* ENVIO DE VISITAS *********************************/
                                    /*********************************/
                                    VisitaSQLiteEntity visita=new VisitaSQLiteEntity();

                                    visita.setCompania_id(listaVisitaSQLiteEntity.get(j).getCompania_id());
                                    visita.setCliente_id(listaVisitaSQLiteEntity.get(j).getCliente_id());
                                    visita.setDireccion_id(listaVisitaSQLiteEntity.get(j).getDireccion_id());
                                    visita.setFecha_registro(listaVisitaSQLiteEntity.get(j).getFecha_registro());
                                    visita.setHora_registro(listaVisitaSQLiteEntity.get(j).getHora_registro());
                                    visita.setZona_id(listaVisitaSQLiteEntity.get(j).getZona_id());
                                    visita.setFuerzatrabajo_id(listaVisitaSQLiteEntity.get(j).getFuerzatrabajo_id());
                                    visita.setUsuario_id(listaVisitaSQLiteEntity.get(j).getUsuario_id());
                                    visita.setTipo(listaVisitaSQLiteEntity.get(j).getTipo());
                                    visita.setMotivo(listaVisitaSQLiteEntity.get(j).getMotivo());
                                    visita.setObservacion(listaVisitaSQLiteEntity.get(j).getObservacion());
                                    visita.setChkenviado(listaVisitaSQLiteEntity.get(j).getChkenviado());
                                    visita.setChkrecibido(listaVisitaSQLiteEntity.get(j).getChkrecibido());
                                    visita.setLatitud(listaVisitaSQLiteEntity.get(j).getLatitud());
                                    visita.setLongitud(listaVisitaSQLiteEntity.get(j).getLongitud());

                                    new FormulasController.ReenvioDeVisitas(getContext(),visita).execute();

                                    /*********************************/
                                    /*********************************/
                                    /*********************************/


                                }
                            }
                            /*visitaWSDAO.RegistraDeposito
                                    (
                                            listaVisitaSQLiteEntity.get(j).getFecha_registro(),
                                            listaVisitaSQLiteEntity.get(j).getMotivo(),
                                            listaVisitaSQLiteEntity.get(j).getTipo(),
                                            listaVisitaSQLiteEntity.get(j).getCompania_id(),
                                            listaVisitaSQLiteEntity.get(j).getUsuario_id(),
                                            listaVisitaSQLiteEntity.get(j).getFuerzatrabajo_id(),
                                            listaVisitaSQLiteEntity.get(j).getCliente_id(),
                                            listaVisitaSQLiteEntity.get(j).getDireccion_id(),
                                            SesionEntity.imei
                                    );*/

                        }
                        /*********************************/
                        /*********************************/
                        /*********************************/

                        //Envio de Cobranza Detalle Pendientes a WS
                        listaCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendientesEnvioTotalWS(SesionEntity.compania_id, SesionEntity.usuario_id);


                        if (!(listaCobranzaDetalleSQLiteEntity.isEmpty())) {
                            for (int j = 0; j < listaCobranzaDetalleSQLiteEntity.size(); j++) {
                                String resultado = "0";
                                List<CobranzaDetalleSQLiteEntity> listaleercobranza = new ArrayList<>();
                                /*listaleercobranza = cobranzaDetalleWSDao.LeerCobranzaRecibo(SesionEntity.imei, SesionEntity.compania_id
                                        , SesionEntity.usuario_id, listaCobranzaDetalleSQLiteEntity.get(j).getRecibo().toString()
                                );*/
                                listaleercobranza=formulasController.ObtenerListaConvertidaCobranzaDetalleSQLite(
                                        getContext(),
                                        SesionEntity.imei,
                                        SesionEntity.compania_id,
                                        SesionEntity.usuario_id,
                                        listaCobranzaDetalleSQLiteEntity.get(j).getRecibo()
                                );

                                /*HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(getContext());
                                listaleercobranza = historicoCobranzaWS.getHistoricoCobranzaIndividual
                                        (
                                                SesionEntity.imei,
                                                SesionEntity.compania_id,
                                                SesionEntity.usuario_id,
                                                listaCobranzaDetalleSQLiteEntity.get(j).getRecibo().toString()
                                        );*/

                                HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(getContext());
                                if (listaleercobranza.isEmpty()) {
                                    if (listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id().equals("1")) {
                                        String resultadoWS="0";
                                        ArrayList<CobranzaDetalleSQLiteEntity> ListaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                                        CobranzaDetalleSQLiteEntity cobranzaDetalleSQLiteEntity = new CobranzaDetalleSQLiteEntity();
                                        cobranzaDetalleSQLiteEntity.id = listaCobranzaDetalleSQLiteEntity.get(j).getId();
                                        cobranzaDetalleSQLiteEntity.cobranza_id = listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id();
                                        cobranzaDetalleSQLiteEntity.cliente_id = listaCobranzaDetalleSQLiteEntity.get(j).getCliente_id();
                                        cobranzaDetalleSQLiteEntity.compania_id = listaCobranzaDetalleSQLiteEntity.get(j).getCompania_id();
                                        cobranzaDetalleSQLiteEntity.documento_id = listaCobranzaDetalleSQLiteEntity.get(j).getDocumento_id();
                                        cobranzaDetalleSQLiteEntity.fechacobranza = listaCobranzaDetalleSQLiteEntity.get(j).getFechacobranza();
                                        cobranzaDetalleSQLiteEntity.importedocumento = listaCobranzaDetalleSQLiteEntity.get(j).getImportedocumento();
                                        cobranzaDetalleSQLiteEntity.saldocobrado = listaCobranzaDetalleSQLiteEntity.get(j).getSaldocobrado();
                                        cobranzaDetalleSQLiteEntity.nuevosaldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getNuevosaldodocumento();
                                        cobranzaDetalleSQLiteEntity.recibo = listaCobranzaDetalleSQLiteEntity.get(j).getRecibo();
                                        cobranzaDetalleSQLiteEntity.saldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getSaldodocumento();
                                        cobranzaDetalleSQLiteEntity.chkbancarizado = listaCobranzaDetalleSQLiteEntity.get(j).getChkbancarizado();
                                        cobranzaDetalleSQLiteEntity.motivoanulacion = listaCobranzaDetalleSQLiteEntity.get(j).getMotivoanulacion();
                                        cobranzaDetalleSQLiteEntity.chkqrvalidado = listaCobranzaDetalleSQLiteEntity.get(j).getChkqrvalidado();
                                        cobranzaDetalleSQLiteEntity.banco_id = listaCobranzaDetalleSQLiteEntity.get(j).getBanco_id();
                                        cobranzaDetalleSQLiteEntity.comentario = listaCobranzaDetalleSQLiteEntity.get(j).getComentario();
                                        cobranzaDetalleSQLiteEntity.pagodirecto = listaCobranzaDetalleSQLiteEntity.get(j).getPagodirecto();

                                        /*
                                        ListaCobranzaDetalleSQLiteEntity.add(cobranzaDetalleSQLiteEntity);
                                        resultado = cobranzaDetalleWSDao.enviarRecibo
                                                (ListaCobranzaDetalleSQLiteEntity, SesionEntity.imei, SesionEntity.usuario_id,
                                                        listaCobranzaDetalleSQLiteEntity.get(j).getComentario(), SesionEntity.fuerzatrabajo_id);
                                        */

                                        resultado=formulasController.EnviarReciboWsRetrofit(
                                                cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                                        listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                                getContext(),
                                                "CREATE",
                                                "0",
                                                "0",
                                                "0",
                                                "0"
                                        );
                                        //resultado=String.valueOf(resultadoWS);

                                        if (resultado.equals("1")) {

                                            /*listaleercobranza = cobranzaDetalleWSDao.LeerCobranzaRecibo(SesionEntity.imei, SesionEntity.compania_id
                                                    , SesionEntity.usuario_id, listaCobranzaDetalleSQLiteEntity.get(j).getRecibo().toString()
                                            );*/

                                            listaleercobranza=formulasController.ObtenerListaConvertidaCobranzaDetalleSQLite(
                                                    getContext(),
                                                    SesionEntity.imei,
                                                    SesionEntity.compania_id,
                                                    SesionEntity.usuario_id,
                                                    listaCobranzaDetalleSQLiteEntity.get(j).getRecibo()
                                            );

                                            if (listaleercobranza.isEmpty()) {
                                                resultado = "0";

                                            } else {
                                                resultado = "1";
                                            }
                                            cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id, SesionEntity.usuario_id, resultado);
                                        }

                                    } else {
                                        ArrayList<CobranzaDetalleSQLiteEntity> ListaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                                        CobranzaDetalleSQLiteEntity cobranzaDetalleSQLiteEntity = new CobranzaDetalleSQLiteEntity();
                                        cobranzaDetalleSQLiteEntity.id = listaCobranzaDetalleSQLiteEntity.get(j).getId();
                                        cobranzaDetalleSQLiteEntity.cobranza_id = listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id();
                                        cobranzaDetalleSQLiteEntity.cliente_id = listaCobranzaDetalleSQLiteEntity.get(j).getCliente_id();
                                        cobranzaDetalleSQLiteEntity.compania_id = listaCobranzaDetalleSQLiteEntity.get(j).getCompania_id();
                                        cobranzaDetalleSQLiteEntity.documento_id = listaCobranzaDetalleSQLiteEntity.get(j).getDocumento_id();
                                        cobranzaDetalleSQLiteEntity.fechacobranza = listaCobranzaDetalleSQLiteEntity.get(j).getFechacobranza();
                                        cobranzaDetalleSQLiteEntity.importedocumento = listaCobranzaDetalleSQLiteEntity.get(j).getImportedocumento();
                                        cobranzaDetalleSQLiteEntity.saldocobrado = listaCobranzaDetalleSQLiteEntity.get(j).getSaldocobrado();
                                        cobranzaDetalleSQLiteEntity.nuevosaldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getNuevosaldodocumento();
                                        cobranzaDetalleSQLiteEntity.recibo = listaCobranzaDetalleSQLiteEntity.get(j).getRecibo();
                                        cobranzaDetalleSQLiteEntity.saldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getSaldodocumento();
                                        cobranzaDetalleSQLiteEntity.chkbancarizado = listaCobranzaDetalleSQLiteEntity.get(j).getChkbancarizado();
                                        cobranzaDetalleSQLiteEntity.motivoanulacion = listaCobranzaDetalleSQLiteEntity.get(j).getMotivoanulacion();
                                        cobranzaDetalleSQLiteEntity.chkqrvalidado = listaCobranzaDetalleSQLiteEntity.get(j).getChkqrvalidado();
                                        cobranzaDetalleSQLiteEntity.banco_id = listaCobranzaDetalleSQLiteEntity.get(j).getBanco_id();
                                        cobranzaDetalleSQLiteEntity.comentario = listaCobranzaDetalleSQLiteEntity.get(j).getComentario();
                                        cobranzaDetalleSQLiteEntity.pagodirecto = listaCobranzaDetalleSQLiteEntity.get(j).getPagodirecto();

                                        ListaCobranzaDetalleSQLiteEntity.add(cobranzaDetalleSQLiteEntity);
                                        String resultadoWS="0";
                                        /*resultado = cobranzaDetalleWSDao.enviarRecibo(ListaCobranzaDetalleSQLiteEntity, SesionEntity.imei, SesionEntity.usuario_id,
                                                listaCobranzaDetalleSQLiteEntity.get(j).getComentario(), SesionEntity.fuerzatrabajo_id);*/
                                        formulasController=new FormulasController(getContext());
                                        resultado=formulasController.EnviarReciboWsRetrofit(
                                                cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                                        listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                                getContext(),
                                                "CREATE",
                                                "0",
                                                "0",
                                                "0",
                                                "0"
                                        );
                                        //resultado=String.valueOf(resultadoWS);

                                        if (resultado.equals("1")) {
                                            cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id, SesionEntity.usuario_id, resultado);
                                            cobranzaDetalleSQLiteDao.ActualizaConexionWSDepositoCobranzaDetalle(
                                                    listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(),
                                                    listaCobranzaDetalleSQLiteEntity.get(j).getCompania_id(),
                                                    SesionEntity.usuario_id,
                                                    resultado
                                            );
                                        }

                                    }

                                } else {
                                    for (int g = 0; g < listaleercobranza.size(); g++) {
                                        CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteEntity = new CobranzaDetalleSQLiteDao(getContext());
                                        cobranzaDetalleSQLiteEntity.ActualizaConexionWSCobranzaDetalle(
                                                listaleercobranza.get(g).getRecibo(),
                                                SesionEntity.compania_id,
                                                SesionEntity.usuario_id,
                                                "1"
                                        );
                                    }

                                }


                            }
                        }

                        //Envio Cobranza Detalle a WS (Parcial)
                        ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                        listaCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendientesEnvioParcial(SesionEntity.compania_id, SesionEntity.usuario_id);
                        if (!(listaCobranzaDetalleSQLiteEntity.isEmpty())) {

                            for (int j = 0; j < listaCobranzaDetalleSQLiteEntity.size(); j++) {
                                String chkwsdepositorecibido = "0";
                                ArrayList<CobranzaDetalleSQLiteEntity> nuevalista = new ArrayList<>();
                                CobranzaDetalleSQLiteEntity cobranzaDetalleSQLiteEntity = new CobranzaDetalleSQLiteEntity();

                                cobranzaDetalleSQLiteEntity.id = listaCobranzaDetalleSQLiteEntity.get(j).getId();
                                cobranzaDetalleSQLiteEntity.cobranza_id = listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id();
                                cobranzaDetalleSQLiteEntity.cliente_id = listaCobranzaDetalleSQLiteEntity.get(j).getCliente_id();
                                cobranzaDetalleSQLiteEntity.compania_id = listaCobranzaDetalleSQLiteEntity.get(j).getCompania_id();
                                cobranzaDetalleSQLiteEntity.documento_id = listaCobranzaDetalleSQLiteEntity.get(j).getDocumento_id();
                                cobranzaDetalleSQLiteEntity.fechacobranza = listaCobranzaDetalleSQLiteEntity.get(j).getFechacobranza();
                                cobranzaDetalleSQLiteEntity.importedocumento = listaCobranzaDetalleSQLiteEntity.get(j).getImportedocumento();
                                cobranzaDetalleSQLiteEntity.saldocobrado = listaCobranzaDetalleSQLiteEntity.get(j).getSaldocobrado();
                                cobranzaDetalleSQLiteEntity.nuevosaldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getNuevosaldodocumento();
                                cobranzaDetalleSQLiteEntity.recibo = listaCobranzaDetalleSQLiteEntity.get(j).getRecibo();
                                cobranzaDetalleSQLiteEntity.saldodocumento = listaCobranzaDetalleSQLiteEntity.get(j).getSaldodocumento();
                                cobranzaDetalleSQLiteEntity.chkbancarizado = listaCobranzaDetalleSQLiteEntity.get(j).getChkbancarizado();
                                cobranzaDetalleSQLiteEntity.motivoanulacion = listaCobranzaDetalleSQLiteEntity.get(j).getMotivoanulacion();
                                cobranzaDetalleSQLiteEntity.chkqrvalidado = listaCobranzaDetalleSQLiteEntity.get(j).getChkqrvalidado();
                                cobranzaDetalleSQLiteEntity.banco_id = listaCobranzaDetalleSQLiteEntity.get(j).getBanco_id();
                                cobranzaDetalleSQLiteEntity.comentario = listaCobranzaDetalleSQLiteEntity.get(j).getComentario();
                                cobranzaDetalleSQLiteEntity.pagodirecto = listaCobranzaDetalleSQLiteEntity.get(j).getPagodirecto();
                                nuevalista.add(cobranzaDetalleSQLiteEntity);


                                /*chkwsdepositorecibido = cobranzaDetalleWSDao.ActualizarRecibo(
                                        nuevalista,
                                        SesionEntity.imei,
                                        SesionEntity.usuario_id,
                                        listaCobranzaDetalleSQLiteEntity.get(j).getComentario(),
                                        SesionEntity.fuerzatrabajo_id,
                                        listaCobranzaDetalleSQLiteEntity.get(j).getBanco_id()
                                );*/
                                String resultado="0";
                                formulasController=new FormulasController(getContext());
                                chkwsdepositorecibido=formulasController.EnviarReciboWsRetrofit(
                                        cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                        getContext(),
                                        "UPDATE",
                                        "0",
                                        listaCobranzaDetalleSQLiteEntity.get(j).getComentario(),
                                        listaCobranzaDetalleSQLiteEntity.get(j).getBanco_id(),
                                        "1"
                                );
                                //chkwsdepositorecibido=String.valueOf(resultado);

                                if (chkwsdepositorecibido.equals("1")) {
                                    cobranzaDetalleSQLiteDao.ActualizaConexionWSDepositoCobranzaDetalle(
                                            cobranzaDetalleSQLiteEntity.recibo,
                                            SesionEntity.compania_id,
                                            SesionEntity.usuario_id,
                                            chkwsdepositorecibido
                                    );
                                }

                            }


                        }

                        //Envio de QRValidados Pendientes
                        ArrayList<CobranzaDetalleSQLiteEntity> listaQRvalidadosPendientes = new ArrayList<>();
                        listaQRvalidadosPendientes = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleQRValidadoWS(SesionEntity.compania_id, SesionEntity.usuario_id);

                        for (int l = 0; l < listaQRvalidadosPendientes.size(); l++) {
                            String resultadowsqrvalidado = "0";
                            CobranzaDetalleSQLiteEntity cobranzaDetalleSQLiteEntity = new CobranzaDetalleSQLiteEntity();
                            ArrayList<CobranzaDetalleSQLiteEntity> listadoCobranzaDetalleSQLiteEntity = new ArrayList<>();

                            cobranzaDetalleSQLiteEntity.id = listaQRvalidadosPendientes.get(l).getId();
                            cobranzaDetalleSQLiteEntity.cobranza_id = listaQRvalidadosPendientes.get(l).getCobranza_id();
                            cobranzaDetalleSQLiteEntity.cliente_id = listaQRvalidadosPendientes.get(l).getCliente_id();
                            cobranzaDetalleSQLiteEntity.compania_id = listaQRvalidadosPendientes.get(l).getCompania_id();
                            cobranzaDetalleSQLiteEntity.documento_id = listaQRvalidadosPendientes.get(l).getDocumento_id();
                            cobranzaDetalleSQLiteEntity.fechacobranza = listaQRvalidadosPendientes.get(l).getFechacobranza();
                            cobranzaDetalleSQLiteEntity.importedocumento = listaQRvalidadosPendientes.get(l).getImportedocumento();
                            cobranzaDetalleSQLiteEntity.saldocobrado = listaQRvalidadosPendientes.get(l).getSaldocobrado();
                            cobranzaDetalleSQLiteEntity.nuevosaldodocumento = listaQRvalidadosPendientes.get(l).getNuevosaldodocumento();
                            cobranzaDetalleSQLiteEntity.recibo = listaQRvalidadosPendientes.get(l).getRecibo();
                            cobranzaDetalleSQLiteEntity.saldodocumento = listaQRvalidadosPendientes.get(l).getSaldodocumento();
                            cobranzaDetalleSQLiteEntity.chkbancarizado = listaQRvalidadosPendientes.get(l).getChkbancarizado();
                            cobranzaDetalleSQLiteEntity.motivoanulacion = listaQRvalidadosPendientes.get(l).getMotivoanulacion();
                            cobranzaDetalleSQLiteEntity.chkqrvalidado = listaQRvalidadosPendientes.get(l).getChkqrvalidado();
                            cobranzaDetalleSQLiteEntity.banco_id = listaQRvalidadosPendientes.get(l).getBanco_id();
                            cobranzaDetalleSQLiteEntity.comentario = listaQRvalidadosPendientes.get(l).getComentario();
                            cobranzaDetalleSQLiteEntity.pagodirecto = listaQRvalidadosPendientes.get(l).getPagodirecto();

                            listadoCobranzaDetalleSQLiteEntity.add(cobranzaDetalleSQLiteEntity);

                            //resultadowsqrvalidado = cobranzaDetalleWSDao.ActualizarRecibo(listadoCobranzaDetalleSQLiteEntity
                            //        , SesionEntity.imei, SesionEntity.usuario_id, listaQRvalidadosPendientes.get(l).getComentario(), SesionEntity.fuerzatrabajo_id, listaQRvalidadosPendientes.get(l).getBanco_id());
                            formulasController=new FormulasController(getContext());

                            resultadowsqrvalidado=formulasController.EnviarReciboWsRetrofit(
                                    cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(listaQRvalidadosPendientes.get(l).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                    getContext(),
                                    "UPDATE",
                                    "0",
                                    listaQRvalidadosPendientes.get(l).getComentario(),
                                    listaQRvalidadosPendientes.get(l).getBanco_id(),
                                    "1"
                            );


                            if (resultadowsqrvalidado.equals("1")) {
                                cobranzaDetalleSQLiteDao.ActualizaWSQRValidadoCobranzaDetalle(
                                        listaQRvalidadosPendientes.get(l).getRecibo(),
                                        SesionEntity.compania_id,
                                        SesionEntity.usuario_id,
                                        resultadowsqrvalidado
                                );
                            }
                        }

                        //Envio de Documentos Anulados Pendientes
                        ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity2 = new ArrayList<>();
                        listaCobranzaCabeceraSQLiteEntity2 = cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraPendientesAnulacionWS(SesionEntity.compania_id, SesionEntity.usuario_id);
                        String reswsanuladep = "0";
                        int resultadocabecera = 0;
                        for (int y = 0; y < listaCobranzaCabeceraSQLiteEntity2.size(); y++) {
                            String motivoanulacion = listaCobranzaCabeceraSQLiteEntity2.get(y).getComentarioanulado();

                  //          ===========================JPCM REVISAR, AL REMOVER KSOAP QUEDA INABILITADA LAS LINEAS DE ABAJO ==========================================================================
                            /*VALIDAR SI FUNCIONABA O NO

                            reswsanuladep =  cobranzaCabeceraWSDao.AnularDeposito(
                                    SesionEntity.imei,
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getCompania_id(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getBanco_id(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getTipoingreso(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getCobranza_id(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getUsuario_id(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getFechadeposito(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getTotalmontocobrado(),
                                    "",
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getFuerzatrabajo_id(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getChkbancarizado(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getFechadiferido(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getComentarioanulado(),
                                    listaCobranzaCabeceraSQLiteEntity2.get(y).getPagodirecto()
                            );

                            if (reswsanuladep.equals("1")) {
                                resultadocabecera = cobranzaCabeceraSQLiteDao.AnularCobranzaCabecera(
                                        listaCobranzaCabeceraSQLiteEntity2.get(y).getCobranza_id(),
                                        listaCobranzaCabeceraSQLiteEntity2.get(y).getCompania_id(),
                                        listaCobranzaCabeceraSQLiteEntity2.get(y).getFuerzatrabajo_id(),
                                        motivoanulacion,
                                        reswsanuladep);
                            } else {
                                resultadocabecera = cobranzaCabeceraSQLiteDao.AnularCobranzaCabecera(
                                        listaCobranzaCabeceraSQLiteEntity2.get(y).getCobranza_id(),
                                        listaCobranzaCabeceraSQLiteEntity2.get(y).getCompania_id(),
                                        listaCobranzaCabeceraSQLiteEntity2.get(y).getFuerzatrabajo_id(),
                                        motivoanulacion,
                                        reswsanuladep);
                            }*/
                        }
                        ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity2 = new ArrayList<>();
                        listaCobranzaDetalleSQLiteEntity2 = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleActualizacionPendiente(SesionEntity.usuario_id, SesionEntity.compania_id);
                        String reswsliberadetalle = "0";

                        for (int j = 0; j < listaCobranzaDetalleSQLiteEntity2.size(); j++) {
                            reswsliberadetalle=formulasController.EnviarReciboWsRetrofit(
                                    cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                            listaCobranzaDetalleSQLiteEntity2.get(j).getRecibo(),
                                            SesionEntity.compania_id,
                                            SesionEntity.fuerzatrabajo_id),
                                    getContext(),
                                    "UPDATE",
                                    "1",
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getComentario(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getBanco_id(),
                                    "0"
                            );

                            /*reswsliberadetalle = cobranzaDetalleWSDao.DesvinculaReciboconDeposito(
                                    SesionEntity.imei,
                                    "UPDATE",
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getCompania_id().toString(),
                                    "0",
                                    "",
                                    "1",
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getId(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getCliente_id(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getDocumento_id(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getImportedocumento(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getSaldodocumento(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getSaldocobrado(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getNuevosaldodocumento(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getFechacobranza(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getRecibo(),
                                    "Pendiente",
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getComentario()
                                    ,
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getUsuario_id()
                                    ,
                                    SesionEntity.fuerzatrabajo_id,
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getChkbancarizado(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getChkqrvalidado(),
                                    listaCobranzaDetalleSQLiteEntity2.get(j).getMotivoanulacion(),
                                    listaCobranzaDetalleSQLiteEntity.get(j).getPagodirecto()
                            );
                            */
                            if (reswsliberadetalle.equals("1")) {
                                cobranzaDetalleSQLiteDao.ActualizaWSCobranzaDetalle(
                                        listaCobranzaDetalleSQLiteEntity2.get(j).getRecibo(),
                                        listaCobranzaDetalleSQLiteEntity2.get(j).getCompania_id(),
                                        listaCobranzaDetalleSQLiteEntity2.get(j).getUsuario_id(),
                                        "0",
                                        listaCobranzaDetalleSQLiteEntity2.get(j).getCobranza_id(),
                                        listaCobranzaDetalleSQLiteEntity2.get(j).getBanco_id(),
                                        listaCobranzaDetalleSQLiteEntity2.get(j).getChkdepositado(),
                                        //listaCobranzaDetalleSQLiteEntity2.get(j).getChkwsdepositorecibido()
                                        "0"

                                );

                            }
                            /*else
                                {
                                    cobranzaDetalleSQLiteDao.ActualizaWSCobranzaDetalle(
                                            listaCobranzaDetalleSQLiteEntity2.get(j).getRecibo(),
                                            listaCobranzaDetalleSQLiteEntity2.get(j).getCompania_id(),
                                            listaCobranzaDetalleSQLiteEntity2.get(j).getUsuario_id(),
                                            "1",
                                            listaCobranzaDetalleSQLiteEntity2.get(j).getCobranza_id(),
                                            listaCobranzaDetalleSQLiteEntity2.get(j).getBanco_id()
                                    );
                                }*/

                        }

                        //Sincronizacion Automatica de Recibos Anulados
                        ArrayList<CobranzaDetalleSQLiteEntity> ListaCobranzaDetalleSQLiteEntity = new ArrayList<>();
                        /*HistoricoCobranzaWSDao historicoCobranzaWSDao = new HistoricoCobranzaWSDao();
                        ListaCobranzaDetalleSQLiteEntity = historicoCobranzaWSDao.obtenerHistoricoCobranzaAnulado
                                (
                                        SesionEntity.imei,
                                        SesionEntity.compania_id,
                                        "",
                                        "",
                                        "RECIBO_ANULADO",
                                        "01-01-0001",
                                        SesionEntity.usuario_id,
                                        ""
                                );*/
                        //HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(getContext());
                        //formulasController.ObtenerListaConvertidaHistoricoCobranza
                        /*ListaCobranzaDetalleSQLiteEntity = formulasController.ObtenerListaConvertidaHistoricoCobranza
                                (
                                        getContext(),
                                        SesionEntity.imei,
                                        SesionEntity.compania_id,
                                        "0",
                                        "1",
                                        "RECIBO_ANULADO",
                                        "01-01-0001",
                                        SesionEntity.fuerzatrabajo_id,
                                        "0"
                                );*/
                        /*if (!ListaCobranzaDetalleSQLiteEntity.isEmpty()) {

                            // resultado="1";
                            for (int g = 0; g < ListaCobranzaDetalleSQLiteEntity.size(); g++) {
                                //ArrayList<CobranzaDetalleSQLiteEntity> listadoCobranzaDetalleSQLiteEntity = new ArrayList<>();
                                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
                                if (ListaCobranzaDetalleSQLiteEntity.get(g).getChkanulado().equals("1")) {
                                    cobranzaDetalleSQLiteDao.ActualizaEstadoCobranzaDetalle(
                                            ListaCobranzaDetalleSQLiteEntity.get(g).getRecibo(),
                                            SesionEntity.compania_id,
                                            SesionEntity.usuario_id,
                                            "1",
                                            ListaCobranzaDetalleSQLiteEntity.get(g).getMotivoanulacion()
                                    );
                                }
                            }
                        }*/

                        Log.e("REOS", "SQLite-Inicia Envio Automatico OV " );
                        //Envio Automatico de Ordenes de Venta, sin recepcion de SAP
                        OrdenVentaCabeceraSQLiteDao ordenVentaCabeceraSQLiteDao=new OrdenVentaCabeceraSQLiteDao(getContext());
                        ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<>();
                        listaOrdenVentaCabeceraSQLiteEntity=ordenVentaCabeceraSQLiteDao.ObtenerOrdenVentaCabeceraPendientesEnvioWS();
                        Log.e("REOS", "listaOrdenVentaCabeceraSQLiteEntity.size(): "+listaOrdenVentaCabeceraSQLiteEntity.size() );
                        for(int z=0;z<listaOrdenVentaCabeceraSQLiteEntity.size();z++){
                            String ordenVentaId=listaOrdenVentaCabeceraSQLiteEntity.get(z).getOrdenventa_id();
                            String JSON;
                            JSON=EnviarNubeOV(ordenVentaId,getContext());

                            //listaOrdenVentaCabeceraSQLiteEntity.get(z).getOrdenventa_id()

                            ordenVentaWSDao.sendSalesOrder(JSON).observe(getActivity(), data->{
                                if(data!=null){
                                   // OrdenVentaCabeceraSQLiteDao ordenVentaCabeceraSQLiteDao=new OrdenVentaCabeceraSQLiteDao(getContext());
                                    ordenVentaCabeceraSQLiteDao.ActualizaResultadoOVenviada(
                                            SesionEntity.compania_id,
                                            ordenVentaId,
                                            data[0],
                                            data[3],
                                            data[1]
                                    );
                                }else{

                                }
                            });

                            //hiloEnviarNubeOV=new HiloEnviarNubeOV(getContext(),listaOrdenVentaCabeceraSQLiteEntity.get(z).getOrdenventa_id());
                            //hiloEnviarNubeOV.execute(JSON);
                        }
                        //CARGA DE MAESTROS
                        listaparametrosSQLiteEntity = parametrosSQLiteDao.ObtenerParametros();
                        if (listaparametrosSQLiteEntity.isEmpty()) {
                            parametrosSQLiteDao.LimpiarParametros();
                            parametrosSQLiteDao.InsertaParametros("1", "CLIENTES", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("2", "BANCOS", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("3", "DOCUMENTOS", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("4", "RUTA VENDEDOR", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("5", "TERMINO PAGO", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("6", "AGENCIAS", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("7", "LISTA PRECIO", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("8", "STOCK", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("9", "LISTA PROMOCION", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("10", "PROMOCION CABECERA", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("11", "PROMOCION DETALLE", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("12", "RUTA FUERZATRABAJO", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("13", "CATALOGO", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("14", "DIRECCION CLIENTE", "0", fecha2
                                    //, ""
                            );
                            parametrosSQLiteDao.InsertaParametros("15", "HOJA DESPACHO", "0", fecha2
                                    //, ""
                            );
                        }

                        /*ArrayList<ParametrosSQLiteEntity> listaParametros=new ArrayList<>();
                        listaParametros=parametrosSQLiteDao.ObtenerParametros();
                        String resultado="0";
                        //for(int j=0;j<listaParametros.size();j++)
                        //{
                            ValidacionHashWS validacionHashWS=new ValidacionHashWS(getContext());
                        resultado=validacionHashWS.getValidacionHash(
                                    SesionEntity.imei,SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,
                                    listaParametros.get(0).getNombreparametro(),
                                    listaParametros.get(0).getHash()
                                    );
                        //}

                        if(resultado.equals("0")) {

                            ClienteWS clienteWS = new ClienteWS(getContext());
                            Object[] objects = new Object[2];
                            objects = clienteWS.getClienteWS(SesionEntity.imei, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id);
                            ArrayList<SeguridadEntity> lseguridadentity = new ArrayList<>();
                            LclientesqlSQLiteEntity = (ArrayList<ClienteSQLiteEntity>) objects[0];
                            lseguridadentity = (ArrayList<SeguridadEntity>) objects[1];

                            if (!(LclientesqlSQLiteEntity.isEmpty())) {
                                clienteSQliteDAO.LimpiarTablaCliente();
                                CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                                parametrosSQLiteDao.ActualizaCantidadRegistros("1", "CLIENTES", String.valueOf(CantClientes), fecha2, lseguridadentity.get(0).getHash());

                            }
                        }
                        */
                        ClienteWS clienteWS = new ClienteWS(getContext());
                        LclientesqlSQLiteEntity = clienteWS.getClienteWS(SesionEntity.imei);
                        if (!(LclientesqlSQLiteEntity.isEmpty())) {
                            clienteSQliteDAO.LimpiarTablaCliente();
                            CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("1", "CLIENTES", String.valueOf(CantClientes), fecha2
                                    //, lseguridadentity.get(0).getHash()
                            );

                        }
                        BancoWS bancoWS = new BancoWS(getContext());
                        //LBanco = bancoWSDao.obtenerBanco(SesionEntity.imei, SesionEntity.compania_id);
                        //LBanco = bancoWS.getBancoWS(SesionEntity.imei, SesionEntity.compania_id);
                        if (!(LBanco.isEmpty())) {
                            //bancoSQLiteDAO.LimpiarTablaBanco();
                            //CantBancos = registrarBancoSQLite(LBanco);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("2", "BANCOS", String.valueOf(CantBancos), fecha2);

                        }
                        DocumentoDeudaWS documentoDeudaWS = new DocumentoDeudaWS(getContext());
                        LDDeuda = documentoDeudaWS.getDocumentoDeuda(SesionEntity.imei);
                        if (!(LDDeuda.isEmpty())) {
                            documentoDeudaSQLiteDao.LimpiarTablaDocumentoDeuda();
                            CantDocumentosDeuda = registrarDocumentoDeudaSQLite(LDDeuda);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("3", "DOCUMENTOS", String.valueOf(CantDocumentosDeuda), fecha2);

                        }

                        /*RutaVendedorWS rutaVendedorWS = new RutaVendedorWS(getContext());
                        LRVendedor = rutaVendedorWS.getRutaVendedorWS(SesionEntity.imei, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id, fecha);

                        if (!(LRVendedor.isEmpty())) {
                            rutaVendedorSQLiteDao.LimpiarTablaRutaVendedor();
                            CantRutaVendedor = registrarRutaVendedorSQLite(LRVendedor);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("4", "RUTA VENDEDOR", String.valueOf(CantRutaVendedor), fecha2);

                        }*/

                        TerminoPagoWS terminoPagoWS = new TerminoPagoWS(getContext());
                        //LTPago = terminoPagoWS.getTerminoPagoWS(SesionEntity.imei, SesionEntity.compania_id);

                        if (!(LTPago.isEmpty())) {
                            //terminoPagoSQLiteDao.LimpiarTablaTerminoPago();
                            //CantTerminoPago = registrarTerminoPagoSQLite(LTPago);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("5", "TERMINO PAGO", String.valueOf(CantTerminoPago), fecha2);
                        }

                        AgenciaWS agenciaWS = new AgenciaWS(getContext());
                        //LAgencia = agenciaWS.getAgenciaWS(SesionEntity.imei, SesionEntity.compania_id);

                        if (!(LAgencia.isEmpty())) {
                            //agenciaSQLiteDao.LimpiarTablaAgencia();
                            //CantAgencia = registrarAgenciaSQLite(LAgencia);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("6", "AGENCIAS", String.valueOf(CantAgencia), fecha2);
                        }

                        ListaPrecioDetalleWS listaPrecioDetalleWS = new ListaPrecioDetalleWS(getContext());
                        //LPDetalle = listaPrecioDetalleWS.getListaPrecioDetalleWS(SesionEntity.imei, SesionEntity.fuerzatrabajo_id);

                        if (!(LPDetalle.isEmpty())) {
                            //listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                            //CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("7", "LISTA PRECIO", String.valueOf(CantListaPrecioDetalle), fecha2);
                        }

                        StockWS stockWS = new StockWS(getContext());
                        LStock = stockWS.getStockWS(SesionEntity.imei);

                        if (!(LStock.isEmpty())) {
                            stockSQLiteDao.LimpiarTablaStock();
                            CantStock = registrarStockSQLite(LStock);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("8", "STOCK", String.valueOf(CantStock), fecha2);
                        }

                        ListaPromocionWS listaPromocionWS = new ListaPromocionWS(getContext());
                        //LPromocion = listaPromocionWS.GetListaPromocionWS(SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);

                        if (!(LPromocion.isEmpty())) {
                            //istaPromocionSQLiteDao.LimpiarTablaListaPromocion();
                            //CantListaPromocion = registrarListaPromocionSQLite(LPromocion);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("9", "LISTA PROMOCION", String.valueOf(CantListaPromocion), fecha2);
                        }

                        //PromocionCabeceraWS promocionCabeceraWS = new PromocionCabeceraWS(getContext());
                        //LPCabecera = promocionCabeceraWS.getPromocionCabeceraWS(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,SesionEntity.imei);

                        //if (!LPCabecera.isEmpty()) {
                           // promocionCabeceraSQLiteDao.LimpiarTablaPromocionCabecera();
                           // CantPromocionCabecera = registrarPromocionCabeceraSQLite(LPCabecera);
                           // parametrosSQLiteDao.ActualizaCantidadRegistros("10", "PROMOCION CABECERA", String.valueOf(CantPromocionCabecera), fecha2);
                        //}

                        PromocionDetalleWS promocionDetalleWS = new PromocionDetalleWS(getContext());
                        //LPromocionDetalle = promocionDetalleWS.getPromocionDetalleWS(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,SesionEntity.imei);

                        if (!(LPromocionDetalle.isEmpty())) {
                        //    promocionDetalleSQLiteDao.LimpiarTablaPromocionDetalle();
                        //    CantPromocionDetalle = registrarPromocionDetalleSQLite(LPromocionDetalle);
                        //    parametrosSQLiteDao.ActualizaCantidadRegistros("11", "PROMOCION DETALLE", String.valueOf(CantPromocionDetalle), fecha2);
                        }

                        RutaFuerzaTrabajoWS rutaFuerzaTrabajoWS = new RutaFuerzaTrabajoWS(getContext());
                        //LRutaFuerzaTrabajo = rutaFuerzaTrabajoWS.getRutaFuerzaTrabajoWS(SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                        if (LRutaFuerzaTrabajo!=null) {
                            rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo();
                            CantRutaFuerzaTrabajo = registrarRutaFuerzaTrabajoSQLite(LRutaFuerzaTrabajo);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(CantRutaFuerzaTrabajo), fecha2);
                        }

                        CatalogoWS catalogoWS = new CatalogoWS(getContext());
                        //LCatalogo = catalogoWS.getCatalogoWS(SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                        if (!(LCatalogo.isEmpty())) {
                            catalogoSQLiteDao.LimpiarTablaCatalogo();
                            CantCatalogo = registrarCatalogoSQLite(LCatalogo);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("13", "CATALOGO", String.valueOf(CantCatalogo), fecha2);
                        }

                        /*DireccionClienteWS direccionClienteWS = new DireccionClienteWS(getContext());
                        LDCliente = direccionClienteWS.getDClienteWS
                                (SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                        if (!(LDCliente.isEmpty())) {
                            direccionClienteSQLiteDao.LimpiarTablaDireccionCliente();
                            CantDireccionCliente = registrarDireccionClienteSQLite(LDCliente);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("14", "DIRECCION CLIENTE", String.valueOf(CantDireccionCliente), fecha2);
                        }*/

                        //HojaDespachoWS hojaDespachoWS = new HojaDespachoWS(getContext());
                        //LHDespacho = hojaDespachoWS.getHojaDespachoWS(SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,fecha);
                        //if (!(LHDespacho.isEmpty())) {
                            //hojaDespachoSQLiteDao.LimpiarTablaHojaDespacho();
                            //CantHojaDespacho = registrarHojaDespacho(LHDespacho);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("15", "HOJA DESPACHO", String.valueOf(CantHojaDespacho), fecha2);
                       // }

                    } else if (argumento.equals("CLIENTES")) {
                        ClienteWS clienteWS = new ClienteWS(getContext());
                        //LclientesqlSQLiteEntity = clienteWSDao.obtenerCliente(SesionEntity.imei, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id);
                        LclientesqlSQLiteEntity = clienteWS.getClienteWS(SesionEntity.imei);
                        if (!(LclientesqlSQLiteEntity.isEmpty())) {
                            clienteSQliteDAO.LimpiarTablaCliente();
                            CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("1", "CLIENTES", String.valueOf(CantClientes), fecha2);
                        }

                    } else if (argumento.equals("BANCOS")) {
                        BancoWS bancoWS = new BancoWS(getContext());
                        LBanco = bancoWS.getBancoWS(SesionEntity.imei);
                        if (!(LBanco.isEmpty())) {
                            bancoSQLiteDAO.LimpiarTablaBanco();
                            CantBancos = registrarBancoSQLite(LBanco);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("2", "BANCOS", String.valueOf(CantBancos), fecha2);
                        }
                    } else if (argumento.equals("TERMINO PAGO")) {
                        TerminoPagoWS terminoPagoWS = new TerminoPagoWS(getContext());
                        LTPago = terminoPagoWS.getTerminoPagoWS(SesionEntity.imei);

                        if (!(LTPago.isEmpty())) {
                            terminoPagoSQLiteDao.LimpiarTablaTerminoPago();
                            CantTerminoPago = registrarTerminoPagoSQLite(LTPago);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("5", "TERMINO PAGO", String.valueOf(CantTerminoPago), fecha2);

                        }


                    } else if (argumento.equals("AGENCIAS")) {
                        AgenciaWS agenciaWS = new AgenciaWS(getContext());
                        LAgencia = agenciaWS.getAgenciaWS(SesionEntity.imei);

                        if (!(LAgencia.isEmpty())) {
                            agenciaSQLiteDao.LimpiarTablaAgencia();
                            CantAgencia = registrarAgenciaSQLite(LAgencia);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("6", "AGENCIAS", String.valueOf(CantAgencia), fecha2);
                        }
                    }
                    else if (argumento.equals("LISTA PRECIO")) {
                        ListaPrecioDetalleWS listaPrecioDetalleWS = new ListaPrecioDetalleWS(getContext());
                        LPDetalle = listaPrecioDetalleWS.getListaPrecioDetalleWS(SesionEntity.imei);

                        if (!(LPDetalle.isEmpty())) {
                            listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                            CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("7", "LISTA PRECIO", String.valueOf(CantListaPrecioDetalle), fecha2);
                        }
                    }
                    else if (argumento.equals("STOCK")) {
                    StockWS stockWS = new StockWS(getContext());
                    LStock = stockWS.getStockWS
                            (SesionEntity.imei);

                    if (!(LStock.isEmpty())) {
                        stockSQLiteDao.LimpiarTablaStock();
                        CantStock = registrarStockSQLite(LStock);
                        parametrosSQLiteDao.ActualizaCantidadRegistros("8", "STOCK", String.valueOf(CantStock), fecha2);
                    }
                    }
                    else if (argumento.equals("LISTA PROMOCION")) {
                    ListaPromocionWS listaPromocionWS = new ListaPromocionWS(getContext());
                    LPromocion = listaPromocionWS.GetListaPromocionWS(SesionEntity.imei);

                    if (!(LPromocion.isEmpty())) {
                        listaPromocionSQLiteDao.LimpiarTablaListaPromocion();
                        CantListaPromocion = registrarListaPromocionSQLite(LPromocion);
                        parametrosSQLiteDao.ActualizaCantidadRegistros("9", "LISTA PROMOCION", String.valueOf(CantListaPromocion), fecha2);
                    }
                    }
///los de aqui se ejecutan cuando dan click al parametro
                    else if (argumento.equals("PROMOCION CABECERA")) {
                        PromocionCabeceraWS promocionCabeceraWS = new PromocionCabeceraWS(getContext());

                        LPCabecera = promocionCabeceraWS.getPromocionCabeceraWS(SesionEntity.imei);

                        if (!(LPCabecera.isEmpty())) {
                            promocionCabeceraSQLiteDao.LimpiarTablaPromocionCabecera();
                            CantPromocionCabecera = registrarPromocionCabeceraSQLite(LPCabecera);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("10", "PROMOCION CABECERA", String.valueOf(CantPromocionCabecera), fecha2);
                        }
                    }else if (argumento.equals("PROMOCION DETALLE")) {
                        PromocionDetalleWS promocionDetalleWS = new PromocionDetalleWS(getContext());
                        LPromocionDetalle = promocionDetalleWS.getPromocionDetalleWS(SesionEntity.imei);
                        if (!(LPromocionDetalle.isEmpty())) {
                            promocionDetalleSQLiteDao.LimpiarTablaPromocionDetalle();
                            CantPromocionDetalle = registrarPromocionDetalleSQLite(LPromocionDetalle);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("11", "PROMOCION DETALLE", String.valueOf(CantPromocionDetalle), fecha2);
                        }
                    }else if (argumento.equals("RUTA FUERZATRABAJO")) {
                        RutaFuerzaTrabajoWS rutaFuerzaTrabajoWS = new RutaFuerzaTrabajoWS(getContext());
                        LRutaFuerzaTrabajo = rutaFuerzaTrabajoWS.getRutaFuerzaTrabajoWS(SesionEntity.imei);

                        if (!(LRutaFuerzaTrabajo.isEmpty())) {
                            rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo();
                            CantRutaFuerzaTrabajo = registrarRutaFuerzaTrabajoSQLite(LRutaFuerzaTrabajo);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(CantRutaFuerzaTrabajo), fecha2);
                        }
                    }

                   /* else if (argumento.equals("CATALOGO"))
                    {
                    CatalogoWS catalogoWS = new CatalogoWS(getContext());
                    LCatalogo = catalogoWS.getCatalogoWS
                            (SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                    if (!(LCatalogo.isEmpty())) {
                        catalogoSQLiteDao.LimpiarTablaCatalogo();
                        CantCatalogo = registrarCatalogoSQLite(LCatalogo);
                        parametrosSQLiteDao.ActualizaCantidadRegistros("13", "CATALOGO", String.valueOf(CantCatalogo), fecha2);
                    }
                    }*/

                    /*else if (argumento.equals("DIRECCION CLIENTE")) {
                        DireccionClienteWS direccionClienteWS = new DireccionClienteWS(getContext());
                        LDCliente = direccionClienteWS.getDClienteWS
                                (SesionEntity.imei, SesionEntity.compania_id, SesionEntity.fuerzatrabajo_id);
                        if (!(LDCliente.isEmpty())) {
                            direccionClienteSQLiteDao.LimpiarTablaDireccionCliente();
                            CantDireccionCliente = registrarDireccionClienteSQLite(LDCliente);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("14", "DIRECCION CLIENTE", String.valueOf(CantDireccionCliente), fecha2);
                        }
                    }*/
                    else if (argumento.equals("HOJA DESPACHO")) {
                       /* HojaDespachoWS hojaDespachoWS = new HojaDespachoWS(getContext());
                        LHDespacho = hojaDespachoWS.getHojaDespachoWS
                                (SesionEntity.imei, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,fecha);
                        if (!(LHDespacho.isEmpty())) {
                            hojaDespachoSQLiteDao.LimpiarTablaHojaDespacho();
                            CantHojaDespacho = registrarHojaDespacho(LHDespacho);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("15", "HOJA DESPACHO", String.valueOf(CantHojaDespacho), fecha2);
                        }*/

                    }


                    }




            } catch (Exception e){
                e.printStackTrace();
                Log.e("REOS","JEPICAME ParametrosView:ErrorHilo:"+e.getMessage());
            }
            return argumento;
        }

        protected void onPostExecute(String argumento)
        {
            getActivity().setTitle("Parametros");
            ArrayList<ParametrosSQLiteEntity> listaparametrosSQlentity = new ArrayList<ParametrosSQLiteEntity>();
            listaparametrosSQlentity=parametrosSQLiteDao.ObtenerParametros();
            listaParametrosAdapter = new ListaParametrosAdapter(getActivity(), ListaParametrosDao.getInstance().getLeads(listaparametrosSQlentity));
            listviewparametro.setAdapter(listaParametrosAdapter);




            pd.dismiss();
            Toast.makeText(getContext(), "Parametros Actualizados...", Toast.LENGTH_SHORT).show();


        }

        protected void ObtenerRecibosPendientes() {
            ArrayList<ListaHistoricoCobranzaEntity> ListaCobranzaDetalleSQLiteEntity = new ArrayList<>();
            //HistoricoCobranzaWSDao historicoCobranzaWSDao = new HistoricoCobranzaWSDao();

            //ListaCobranzaDetalleSQLiteEntity = historicoCobranzaWSDao.obtenerHistoricoCobranzaPendiente(SesionEntity.imei, SesionEntity.compania_id, "", "", "Pendiente_Deposito", "01-01-0001", SesionEntity.usuario_id, "");
            HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(getContext());
            ListaCobranzaDetalleSQLiteEntity = historicoCobranzaWS.getHistoricoCobranza
                    (
                            SesionEntity.imei,
                            //SesionEntity.compania_id,
                            //banco,
                            //"''",
                            //deposito_id,
                            //"''",

                            fecha,
                            "PENDIENTE_DEPOSITO"
                            //SesionEntity.fuerzatrabajo_id,
                            //"''"
                    );
            if (!ListaCobranzaDetalleSQLiteEntity.isEmpty()) {
                //resultado="1";
                for (int i = 0; i < ListaCobranzaDetalleSQLiteEntity.size(); i++) {
                    ArrayList<CobranzaDetalleSQLiteEntity> listadoCobranzaDetalleSQLiteEntity = new ArrayList<>();
                    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
                    listadoCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                            ListaCobranzaDetalleSQLiteEntity.get(i).getRecibo().toString(),
                            ListaCobranzaDetalleSQLiteEntity.get(i).getCompania_id().toString(),
                            SesionEntity.fuerzatrabajo_id
                    );
                    if (listadoCobranzaDetalleSQLiteEntity.isEmpty()) {
                        CobranzaDetalleSQLiteDao insertarCobranzaDetalle = new CobranzaDetalleSQLiteDao(getContext());
                        insertarCobranzaDetalle.InsertaCobranzaDetalle
                                (
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getDeposito_id(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getCliente_id(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getDocumento_id(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getCompania_id(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getImportedocumento(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getSaldodocumento(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getNuevosaldodocumento(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getMontocobrado(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getFechacobranza(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getRecibo(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getNro_documento(),
                                        SesionEntity.fuerzatrabajo_id,
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getBancarizacion(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getMotivoanulacion(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getUsuario_id(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getComentario(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getDepositodirecto(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getEstadoqr(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getBanco_id(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getChkwsrecibido(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getEstadoqr(),
                                        "0",
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getDepositodirecto(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getPagopos()
                                );
                    }
                }
            }

        }



    }
    public String EnviarNubeOV(String ordenventa_id,Context context)
    {
        String JSON="";
        FormulasController formulasController=new FormulasController(context);
        JSON=formulasController.GenerayConvierteaJSONOV(ordenventa_id,context);

        return  JSON;
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
        super.onAttach(context);
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
        super.onDetach();
        mListener = null;
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        super.onDetach();
        mListener = null;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    public void obtenerPametros()
    {
        imei=sesionEntity.imei;
        compania_id=sesionEntity.compania_id;
        fuerzatrabajo_id=sesionEntity.fuerzatrabajo_id;
    }

    public static ArrayList<ClienteSQLiteEntity> ObtenerDatosCliente(String cliente_id, String compania_id)
    {
        listaclientesqlSQLiteEntity.clear();
        listaclientesqlSQLiteEntity=clienteSQliteDAO.ObtenerDatosCliente(cliente_id,compania_id);

        return listaclientesqlSQLiteEntity;
    }

    public int registrarClienteSQLite(List<ClienteSQLiteEntity> Lista)
    {
        clienteSQliteDAO2 = new ClienteSQliteDAO(getContext());
        int resultado=0;
        try
        {
            for (int i = 0; i < Lista.size(); i++) {
                clienteSQliteDAO2.InsertaCliente(
                        Lista.get(i).getCliente_id(),
                        Lista.get(i).getDomembarque_id(),
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getNombrecliente(),
                        Lista.get(i).getDireccion(),
                        Lista.get(i).getZona_id(),
                        Lista.get(i).getOrden(),
                        Lista.get(i).getZona(),
                        Lista.get(i).getRucdni(),
                        Lista.get(i).getMoneda(),
                        Lista.get(i).getTelefonofijo(),
                        Lista.get(i).getTelefonomovil(),
                        Lista.get(i).getUbigeo_id(),
                        Lista.get(i).getImpuesto_id(),
                        Lista.get(i).getImpuesto(),
                        Lista.get(i).getTipocambio(),
                        Lista.get(i).getCategoria(),
                        Lista.get(i).getLinea_credito(),
                        Lista.get(i).getTerminopago_id()

                );
            }

            resultado=clienteSQliteDAO2.ObtenerCantidadClientes();

        }catch (Exception e)
        {
        // TODO: handle exception
            System.out.println(e.getMessage());

        }
        return resultado;


    }

    public int registrarBancoSQLite(List<BancoSQLiteEntity> Lista)
    {
        bancoSQLiteDAO2 = new BancoSQLiteDAO(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                bancoSQLiteDAO2.InsertaBanco(
                        Lista.get(i).getBanco_id(),
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getNombrebanco());
            }
            resultado=bancoSQLiteDAO2.ObtenerCantidadBancos();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;


    }

    public int registrarDocumentoDeudaSQLite(List<DocumentoDeudaSQLiteEntity> Lista)
    {
        documentoDeudaSQLiteDao = new DocumentoDeudaSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                documentoDeudaSQLiteDao.InsertaDocumentoDeuda(
                        Lista.get(i).getDocumento_id(),
                        Lista.get(i).getDomembaque_id(),
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getCliente_id(),
                        Lista.get(i).getFuerzatrabajo_id(),
                        Lista.get(i).getFechaemision(),
                        Lista.get(i).getFechavencimiento(),
                        Lista.get(i).getNrofactura(),
                        Lista.get(i).getMoneda(),
                        Lista.get(i).getImportefactura(),
                        Lista.get(i).getSaldo(),
                        Lista.get(i).getSaldo_sin_procesar()
                );
            }
            resultado=documentoDeudaSQLiteDao.ObtenerCantidadDocumentosDeuda();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;


    }

    /*public int registrarRutaVendedorSQLite(List<RutaVendedorSQLiteEntity> Lista)
    {
        rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                rutaVendedorSQLiteDao.InsertaRutaVendedor(
                        Lista.get(i).getCliente_id(),
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getDireccion(),
                        Lista.get(i).getDomembarque_id(),
                        Lista.get(i).getNombrecliente(),
                        String.valueOf(Lista.get(i).getOrden()),
                        Lista.get(i).getSaldomn(),
                        Lista.get(i).getZona(),
                        Lista.get(i).getZona_id(),
                        Lista.get(i).getRucdni(),
                        Lista.get(i).getMoneda(),
                        Lista.get(i).getTelefonofijo(),
                        Lista.get(i).getTelefonomovil(),
                        Lista.get(i).getCorreo(),
                        Lista.get(i).getUbigeo_id(),
                        Lista.get(i).getImpuesto_id(),
                        Lista.get(i).getImpuesto(),
                        Lista.get(i).getTipocambio())

                ;
            }
            resultado=rutaVendedorSQLiteDao.ObtenerCantidadRutaVendedor();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;


    }*/

    public int registrarTerminoPagoSQLite(List<TerminoPagoSQLiteEntity> Lista)
    {
        terminoPagoSQLiteDao = new TerminoPagoSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                terminoPagoSQLiteDao.InsertaTerminoPago(
                        Lista.get(i).getTerminopago_id(),
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getTerminopago(),
                        Lista.get(i).getContado(),
                        Lista.get(i).getDias_vencimiento()
                        )
                ;
            }
            resultado=terminoPagoSQLiteDao.ObtenerCantidadTerminoPago();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarAgenciaSQLite(List<AgenciaSQLiteEntity> Lista)
    {
        agenciaSQLiteDao = new AgenciaSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                agenciaSQLiteDao.InsertaAgencia(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getAgencia_id(),
                        Lista.get(i).getAgencia(),
                        Lista.get(i).getUbigeo_id(),
                        Lista.get(i).getRuc(),
                        Lista.get(i).getDireccion()

                )
                ;
            }
            resultado=agenciaSQLiteDao.ObtenerCantidadAgencia();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarListaPrecioDetalleSQLite(List<ListaPrecioDetalleSQLiteEntity> Lista)
    {
        listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                listaPrecioDetalleSQLiteDao.InsertaListaPrecioDetalle(
                        Lista.get(i).getCompania_id(),
                        //Lista.get(i).getListaprecio_id(),
                        Lista.get(i).getCredito(),
                        Lista.get(i).getContado(),
                        Lista.get(i).getProducto_id(),
                        Lista.get(i).getProducto(),
                        Lista.get(i).getUmd(),
                        Lista.get(i).getGal(),
                        Lista.get(i).getU_vis_cashdscnt()
                )
                ;
            }
            resultado=listaPrecioDetalleSQLiteDao.ObtenerCantidadListaPrecioDetalle();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarStockSQLite(List<StockSQLiteEntity> Lista)
    {
        stockSQLiteDao = new StockSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                stockSQLiteDao.InsertaStock(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getProducto_id(),
                        Lista.get(i).getProducto(),
                        Lista.get(i).getUmd(),
                        Lista.get(i).getStock(),
                        Lista.get(i).getAlmacen_id(),
                        Lista.get(i).getComprometido(),
                        Lista.get(i).getEnstock(),
                        Lista.get(i).getPedido()

                )
                ;
            }
            resultado=stockSQLiteDao.ObtenerCantidadStock();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarListaPromocionSQLite(List<ListaPromocionSQLiteEntity> Lista)
    {
        listaPromocionSQLiteDao = new ListaPromocionSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                listaPromocionSQLiteDao.InsertaListaPromocion(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getLista_promocion_id(),
                        Lista.get(i).getLista_promocion(),
                        Lista.get(i).getU_vis_cashdscnt()
                )
                ;
            }
            resultado=listaPromocionSQLiteDao.ObtenerCantidadListaPromocion();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarPromocionCabeceraSQLite(List<PromocionCabeceraSQLiteEntity> Lista)
    {
        promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                promocionCabeceraSQLiteDao.InsertaPromocionCabecera(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getLista_promocion_id(),
                        Lista.get(i).getPromocion_id(),
                        Lista.get(i).getProducto_id(),
                        Lista.get(i).getProducto(),
                        Lista.get(i).getUmd(),
                        Lista.get(i).getCantidad(),
                        Lista.get(i).getFuerzatrabajo_id(),
                        Lista.get(i).getUsuario_id(),
                        Lista.get(i).getTotal_preciobase(),
                        Lista.get(i).getDescuento()
                );
            }
            resultado=promocionCabeceraSQLiteDao.ObtenerCantidadPromocionCabecera();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarPromocionDetalleSQLite(List<PromocionDetalleSQLiteEntity> Lista)
    {
        promocionDetalleSQLiteDao = new PromocionDetalleSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                promocionDetalleSQLiteDao.InsertaPromocionDetalle(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getLista_promocion_id(),
                        Lista.get(i).getPromocion_id(),
                        Lista.get(i).getPromocion_detalle_id(),
                        Lista.get(i).getProducto_id(),
                        Lista.get(i).getProducto(),
                        Lista.get(i).getUmd(),
                        Lista.get(i).getCantidad(),
                        Lista.get(i).getFuerzatrabajo_id(),
                        Lista.get(i).getUsuario_id(),
                        Lista.get(i).getPreciobase(),
                        Lista.get(i).getChkdescuento(),
                        Lista.get(i).getDescuento()
                );
            }
            resultado=promocionDetalleSQLiteDao.ObtenerCantidadPromocionDetalle();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarRutaFuerzaTrabajoSQLite(List<RutaFuerzaTrabajoSQLiteEntity> Lista)
    {
        rutaFuerzaTrabajoSQLiteDao = new RutaFuerzaTrabajoSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                rutaFuerzaTrabajoSQLiteDao.InsertaRutaFuerzaTrabajo(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getZona_id(),
                        Lista.get(i).getZona(),
                        Lista.get(i).getDia(),
                        Lista.get(i).getFrecuencia(),
                        Lista.get(i).getFechainicioruta(),
                        Lista.get(i).getEstado()
                );
            }
            resultado=rutaFuerzaTrabajoSQLiteDao.ObtenerCantidadRutaFuerzaTrabajo();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }

    public int registrarCatalogoSQLite(List<CatalogoSQLiteEntity> Lista)
    {
        catalogoSQLiteDao = new CatalogoSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                catalogoSQLiteDao.InsertaCatalogo(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getCatalogo_id(),
                        Lista.get(i).getCatalogo(),
                        Lista.get(i).getRuta()
                );
            }
            resultado=catalogoSQLiteDao.ObtenerCantidadCatalogos();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }
    public int registrarDireccionClienteSQLite(List<DireccionClienteSQLiteEntity> Lista)
    {
        direccionClienteSQLiteDao = new DireccionClienteSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                direccionClienteSQLiteDao.InsertaDireccionCliente(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getCliente_id(),
                        Lista.get(i).getDomembarque_id(),
                        Lista.get(i).getDireccion(),
                        Lista.get(i).getZona_id(),
                        Lista.get(i).getZona(),
                        Lista.get(i).getFuerzatrabajo_id(),
                        Lista.get(i).getNombrefuerzatrabajo()
                );
            }
            resultado=direccionClienteSQLiteDao.ObtenerCantidadDireccionCliente();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_LOCATION:
            {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {

                } else
                {
                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED))
                    {
                        // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        //        MY_PERMISSIONS_REQUEST_CAMERA
                        //1
                        // );
                        requestPermissions(new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_LOCATION);
                    }
                    //break;
                    //}
                }
                break;
            }
            default:

                break;
        }

    }



}

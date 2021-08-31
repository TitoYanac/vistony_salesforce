package com.vistony.salesforce.View;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListaParametrosAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Dao.Retrofit.OrdenVentaRepository;
import com.vistony.salesforce.Dao.Retrofit.AgenciaWS;
import com.vistony.salesforce.Dao.Retrofit.BancoRepository;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaCabeceraWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.Retrofit.ListaPrecioRepository;
import com.vistony.salesforce.Dao.Retrofit.ListaPromocionWS;
import com.vistony.salesforce.Dao.Retrofit.PromocionCabeceraWS;
import com.vistony.salesforce.Dao.Retrofit.PromocionDetalleWS;
import com.vistony.salesforce.Dao.Retrofit.RutaFuerzaTrabajoRepository;
import com.vistony.salesforce.Dao.Retrofit.StockWS;
import com.vistony.salesforce.Dao.Retrofit.TerminoPagoWS;
import com.vistony.salesforce.Dao.SQLite.AgenciaSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.BancoSQLiteDAO;
import com.vistony.salesforce.Dao.SQLite.CatalogoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Dao.Adapters.ListaParametrosDao;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ListaPromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.StockSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.TerminoPagoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLiteDAO;
import com.vistony.salesforce.Dao.SQLite.ZonaSQLiteDao;
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
    public static ClienteSQlite clienteSQlite;
    private BancoSQLiteDAO bancoSQLiteDAO;
    private BancoSQLiteDAO bancoSQLiteDAO2;
    private DocumentoSQLite documentoSQLite;
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
    private DireccionSQLite direccionSQLite;
    private ZonaSQLiteDao zonaSQLiteDao;
    private ClienteSQlite clienteSQlite2;
    private ProgressDialog pd;
    private String mParam1;
    private String mParam2;
    private String imei,compania_id,fuerzatrabajo_id;
    private OnFragmentInteractionListener mListener;
    static List<ClienteSQlite> Llogin;
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
    UsuarioSQLite usuarioSQLite;
    String conexion="";
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION=1;
    private OrdenVentaRepository ordenVentaRepository;

    public ParametrosView() {
        // Required empty public constructor
    }

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
        clienteSQlite = new ClienteSQlite(getContext());
        listaclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        bancoSQLiteDAO = new BancoSQLiteDAO(getContext());
        documentoSQLite = new DocumentoSQLite(getContext());
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
        direccionSQLite =new DireccionSQLite(getContext());
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

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);
        sqLiteController =  new SQLiteController(getContext());
        usuarioSQLite = new UsuarioSQLite(getContext());
        listaUsuarioSQLiteEntity = new ArrayList<UsuarioSQLiteEntity>();
        listaUsuarioSQLiteEntity = usuarioSQLite.ObtenerUsuarioSesion();
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        rutaFuerzaTrabajoSQLiteDao=new RutaFuerzaTrabajoSQLiteDao(getContext());

        if(BuildConfig.FLAVOR.equals("chile")){
            obtenerWSParametros.execute("Todos");
        }

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        getActivity().setTitle("Parametros");
        v = inflater.inflate(R.layout.fragment_parametros_view, container, false);
        listviewparametro = (ListView) v.findViewById(R.id.listparametro);
        fabdescargarparametros = (FloatingActionButton) v.findViewById(R.id.fabdescargarparametros);

        ordenVentaRepository = new ViewModelProvider(this).get(OrdenVentaRepository.class);

        ///////////////// ENVIAR PEDIDOS
        ordenVentaRepository.salesOrderResend(getContext()).observe(getActivity(), data -> {
            Log.e("jepicame","=>"+data);
        });

        /*
        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(getContext());
        ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabeceraSQLiteEntity = new ArrayList<>();
        listaOrdenVentaCabeceraSQLiteEntity= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraPendientesEnvioWS();

        for(int z=0;z<listaOrdenVentaCabeceraSQLiteEntity.size();z++){
            String ordenVentaId=listaOrdenVentaCabeceraSQLiteEntity.get(z).getOrdenventa_id();
            String JSON=EnviarNubeOV(ordenVentaId,getContext());

            ordenVentaViewModel.sendSalesOrder(JSON).observe(getActivity(), data->{
                if(data!=null){
                    ordenVentaCabeceraSQLite.ActualizaResultadoOVenviada(
                            SesionEntity.compania_id,
                            ordenVentaId,
                            data[0],
                            data[3],
                            data[1]
                    );
                }else{

                }
            });
        }*/


        fabdescargarparametros.setOnClickListener(view -> {
            Object objeto=null,object2=null;

            objeto=listaParametrosAdapter.ObtenerListaParametros();

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

    private String getDateTime(){
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date2 = new Date();
        return dateFormat2.format(date2);
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
                CantPromocionDetalle=0,CantRutaFuerzaTrabajo=0,CantCatalogo=0,CantDireccionCliente=0,CantHojaDespacho=0;


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

                            CobranzaCabeceraWS cobranzaCabeceraWS=new CobranzaCabeceraWS(getContext());

                            //int resultado=0;

                            Log.e("JEPICAME","SE EEJCUTA EL PostCobranzaCabeceraWS EN PARAMETROS");

                            resultadoccabeceraenviows=cobranzaCabeceraWS.PostCobranzaCabeceraWS
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
                                                getContext(),"CREATE","0","0","0","0");
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

                        }





                        //CARGA DE MAESTROS
                        listaparametrosSQLiteEntity = parametrosSQLiteDao.ObtenerParametros();
                        if (listaparametrosSQLiteEntity.isEmpty()) {
                            parametrosSQLiteDao.LimpiarParametros();
                            parametrosSQLiteDao.InsertaParametros("1", "CLIENTES", "0", getDateTime());
                            parametrosSQLiteDao.InsertaParametros("2", "BANCOS", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("3", "DOCUMENTOS", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("4", "RUTA VENDEDOR", "0", getDateTime());
                            parametrosSQLiteDao.InsertaParametros("5", "TERMINO PAGO", "0", getDateTime());
                            parametrosSQLiteDao.InsertaParametros("6", "AGENCIAS", "0", getDateTime());
                            parametrosSQLiteDao.InsertaParametros("7", "LISTA PRECIO", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("8", "STOCK", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("9", "LISTA PROMOCION", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("10", "PROMOCION CABECERA", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("11", "PROMOCION DETALLE", "0", getDateTime());
                            parametrosSQLiteDao.InsertaParametros("12", "RUTA FUERZATRABAJO", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("13", "CATALOGO", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("14", "DIRECCION CLIENTE", "0", getDateTime());
                            //parametrosSQLiteDao.InsertaParametros("15", "HOJA DESPACHO", "0", getDateTime());
                        }

                        BancoRepository bancoRepository = new BancoRepository(getContext());
                        LBanco = bancoRepository.getBancoWS(SesionEntity.imei);
                        if (!(LBanco.isEmpty())) {
                            bancoSQLiteDAO.LimpiarTablaBanco();
                            CantBancos = registrarBancoSQLite(LBanco);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("2", "BANCOS", String.valueOf(CantBancos), getDateTime());
                        }

                        ListaPrecioRepository listaPrecioRepository = new ListaPrecioRepository(getContext());
                        LPDetalle = listaPrecioRepository.getListaPrecioDetalleWS(SesionEntity.imei);

                        if (!(LPDetalle.isEmpty())) {
                            listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                            CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("7", "LISTA PRECIO", String.valueOf(CantListaPrecioDetalle), getDateTime());
                        }

                        ClienteRepository clienteRepository = new ClienteRepository();
                        LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei);

                        if (!LclientesqlSQLiteEntity.isEmpty()) {
                            clienteSQlite.LimpiarTablaCliente();
                            CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("1", "CLIENTES", String.valueOf(CantClientes), getDateTime());
                        }

                        TerminoPagoWS terminoPagoWS = new TerminoPagoWS(getContext());
                        LTPago = terminoPagoWS.getTerminoPagoWS(SesionEntity.imei);

                        if (!(LTPago.isEmpty())) {
                            terminoPagoSQLiteDao.LimpiarTablaTerminoPago();
                            CantTerminoPago = registrarTerminoPagoSQLite(LTPago);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("5", "TERMINO PAGO", String.valueOf(CantTerminoPago), getDateTime());

                        }

                        AgenciaWS agenciaWS = new AgenciaWS(getContext());
                        LAgencia = agenciaWS.getAgenciaWS(SesionEntity.imei);

                        if (!(LAgencia.isEmpty())) {
                            agenciaSQLiteDao.LimpiarTablaAgencia();
                            CantAgencia = registrarAgenciaSQLite(LAgencia);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("6", "AGENCIAS", String.valueOf(CantAgencia), getDateTime());
                        }


                        RutaFuerzaTrabajoRepository rutaFuerzaTrabajoRepository = new RutaFuerzaTrabajoRepository(getContext());
                        LRutaFuerzaTrabajo = rutaFuerzaTrabajoRepository.getRutaFuerzaTrabajoWS(SesionEntity.imei);
                        if (LRutaFuerzaTrabajo!=null) {
                            rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo();
                            CantRutaFuerzaTrabajo = registrarRutaFuerzaTrabajoSQLite(LRutaFuerzaTrabajo);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(CantRutaFuerzaTrabajo), getDateTime());
                        }

                        ////////////////////////////YA ESTA EN EL MAESTRO DE DOCUMENTOS/////////////
                        /*
                        DocumentoRepository documentoRepository = new DocumentoRepository();
                        LDDeuda = documentoRepository.getDocumentoDeuda(getActivity(),SesionEntity.imei);
                        if (!(LDDeuda.isEmpty())) {
                            documentoDeudaSQLiteDao.LimpiarTablaDocumentoDeuda();
                            CantDocumentosDeuda = registrarDocumentoDeudaSQLite(LDDeuda);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("3", "DOCUMENTOS", String.valueOf(CantDocumentosDeuda), getDateTime());

                        }*/

                    }

                    ///////////////////////FIN DE TODOS
                    else if (argumento.equals("CLIENTES")) {
                        ClienteRepository clienteRepository = new ClienteRepository();
                        LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei);
                        if (!(LclientesqlSQLiteEntity.isEmpty())) {
                            clienteSQlite.LimpiarTablaCliente();
                            //CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                            //parametrosSQLiteDao.ActualizaCantidadRegistros("1", "CLIENTES", ""+CantClientes, fecha2);
                        }

                    }
                    else if (argumento.equals("BANCOS")) {
                        BancoRepository bancoRepository = new BancoRepository(getContext());
                        LBanco = bancoRepository.getBancoWS(SesionEntity.imei);
                        if (!(LBanco.isEmpty())) {
                            bancoSQLiteDAO.LimpiarTablaBanco();
                            CantBancos = registrarBancoSQLite(LBanco);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("2", "BANCOS", String.valueOf(CantBancos), getDateTime());
                        }
                    }
                    else if (argumento.equals("TERMINO PAGO")) {
                        TerminoPagoWS terminoPagoWS = new TerminoPagoWS(getContext());
                        LTPago = terminoPagoWS.getTerminoPagoWS(SesionEntity.imei);

                        if (!(LTPago.isEmpty())) {
                            terminoPagoSQLiteDao.LimpiarTablaTerminoPago();
                            CantTerminoPago = registrarTerminoPagoSQLite(LTPago);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("5", "TERMINO PAGO", String.valueOf(CantTerminoPago), getDateTime());

                        }


                    }
                    else if (argumento.equals("AGENCIAS")) {
                        AgenciaWS agenciaWS = new AgenciaWS(getContext());
                        LAgencia = agenciaWS.getAgenciaWS(SesionEntity.imei);

                        if (!(LAgencia.isEmpty())) {
                            agenciaSQLiteDao.LimpiarTablaAgencia();
                            CantAgencia = registrarAgenciaSQLite(LAgencia);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("6", "AGENCIAS", String.valueOf(CantAgencia), getDateTime());
                        }
                    }
                    else if (argumento.equals("LISTA PRECIO")) {
                        ListaPrecioRepository listaPrecioRepository = new ListaPrecioRepository(getContext());
                        LPDetalle = listaPrecioRepository.getListaPrecioDetalleWS(SesionEntity.imei);

                        if (!(LPDetalle.isEmpty())) {
                            listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                            CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("7", "LISTA PRECIO", String.valueOf(CantListaPrecioDetalle), getDateTime());
                        }
                    }
                    else if (argumento.equals("STOCK")) {
                        StockWS stockWS = new StockWS(getContext());
                        LStock = stockWS.getStockWS(SesionEntity.imei);

                        if (!(LStock.isEmpty())) {
                            stockSQLiteDao.LimpiarTablaStock();
                            CantStock = registrarStockSQLite(LStock);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("8", "STOCK", String.valueOf(CantStock), getDateTime());
                        }
                    }
                    else if (argumento.equals("LISTA PROMOCION")) {
                    ListaPromocionWS listaPromocionWS = new ListaPromocionWS(getContext());
                    LPromocion = listaPromocionWS.GetListaPromocionWS(SesionEntity.imei);

                    if (!(LPromocion.isEmpty())) {
                        listaPromocionSQLiteDao.LimpiarTablaListaPromocion();
                        CantListaPromocion = registrarListaPromocionSQLite(LPromocion);
                        parametrosSQLiteDao.ActualizaCantidadRegistros("9", "LISTA PROMOCION", String.valueOf(CantListaPromocion), getDateTime());
                    }
                    }
                    else if (argumento.equals("PROMOCION CABECERA")) {
                        PromocionCabeceraWS promocionCabeceraWS = new PromocionCabeceraWS(getContext());

                        LPCabecera = promocionCabeceraWS.getPromocionCabeceraWS(SesionEntity.imei);

                        if (!(LPCabecera.isEmpty())) {
                            promocionCabeceraSQLiteDao.LimpiarTablaPromocionCabecera();
                            CantPromocionCabecera = registrarPromocionCabeceraSQLite(LPCabecera);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("10", "PROMOCION CABECERA", String.valueOf(CantPromocionCabecera), getDateTime());
                        }
                    }
                    else if (argumento.equals("PROMOCION DETALLE")) {
                        PromocionDetalleWS promocionDetalleWS = new PromocionDetalleWS(getContext());
                        LPromocionDetalle = promocionDetalleWS.getPromocionDetalleWS(SesionEntity.imei);
                        if (!(LPromocionDetalle.isEmpty())) {
                            promocionDetalleSQLiteDao.LimpiarTablaPromocionDetalle();
                            CantPromocionDetalle = registrarPromocionDetalleSQLite(LPromocionDetalle);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("11", "PROMOCION DETALLE", String.valueOf(CantPromocionDetalle), getDateTime());
                        }
                    }
                    else if (argumento.equals("RUTA FUERZATRABAJO")) {

                        Log.e("JPCM","FUERZA DE TRABAJO DESCARGANDO");
                        RutaFuerzaTrabajoRepository rutaFuerzaTrabajoRepository = new RutaFuerzaTrabajoRepository(getContext());
                        LRutaFuerzaTrabajo = rutaFuerzaTrabajoRepository.getRutaFuerzaTrabajoWS(SesionEntity.imei);

                        if (!(LRutaFuerzaTrabajo.isEmpty())) {
                            rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo();
                            CantRutaFuerzaTrabajo = registrarRutaFuerzaTrabajoSQLite(LRutaFuerzaTrabajo);
                            parametrosSQLiteDao.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(CantRutaFuerzaTrabajo), getDateTime());
                        }
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
        listaclientesqlSQLiteEntity= clienteSQlite.ObtenerDatosCliente(cliente_id,compania_id);

        return listaclientesqlSQLiteEntity;
    }

    public int registrarClienteSQLite(List<ClienteSQLiteEntity> Lista){

        ClienteRepository clienteRepository =new ClienteRepository(getContext());
        clienteRepository.addCustomer(Lista);

        return clienteRepository.countCustomer();
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
        documentoSQLite = new DocumentoSQLite(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                documentoSQLite.InsertaDocumentoDeuda(
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
            resultado= documentoSQLite.ObtenerCantidadDocumentosDeuda();
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
                        Lista.get(i).getCredito(),
                        Lista.get(i).getContado(),
                        Lista.get(i).getProducto_id(),
                        Lista.get(i).getProducto(),
                        Lista.get(i).getUmd(),
                        Lista.get(i).getGal(),
                        Lista.get(i).getU_vis_cashdscnt(),
                        Lista.get(i).getTypo(),
                        Lista.get(i).getPorcentaje_descuento()
                );
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
        direccionSQLite = new DireccionSQLite(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                direccionSQLite.InsertaDireccionCliente(
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
            resultado= direccionSQLite.ObtenerCantidadDireccionCliente();
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

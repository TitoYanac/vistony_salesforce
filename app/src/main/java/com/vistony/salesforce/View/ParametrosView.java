package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.getDateTime;
import static com.vistony.salesforce.Entity.SesionEntity.FLAG_BACKUP;
import static com.vistony.salesforce.kotlin.Utilities.NotificationAppKt.areNotificationsEnabled;
import static com.vistony.salesforce.kotlin.Utilities.NotificationAppKt.showNotificationDisabledDialog;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.ListaParametrosAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Dao.Retrofit.BackupRepository;
import com.vistony.salesforce.Dao.Retrofit.BusinessLayerRepository;
import com.vistony.salesforce.Dao.Retrofit.BusinessLayerSalesDetailRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.EscColoursCRepository;
import com.vistony.salesforce.Dao.Retrofit.HeaderDispatchSheetRepository;
import com.vistony.salesforce.Dao.Retrofit.LeadClienteViewModel;
import com.vistony.salesforce.Dao.Retrofit.MotivoVisitaWS;
import com.vistony.salesforce.Dao.Retrofit.ObjectRepository;
import com.vistony.salesforce.Dao.Retrofit.OrdenVentaRepository;
import com.vistony.salesforce.Dao.Retrofit.AgenciaWS;
import com.vistony.salesforce.Dao.Retrofit.BancoRepository;
import com.vistony.salesforce.Dao.Retrofit.ClienteRepository;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricoCobranzaWS;
import com.vistony.salesforce.Dao.Retrofit.ListaPrecioRepository;
import com.vistony.salesforce.Dao.Retrofit.ListaPromocionWS;
import com.vistony.salesforce.Dao.Retrofit.PriceListHeadRepository;
import com.vistony.salesforce.Dao.Retrofit.PriceListRepository;
import com.vistony.salesforce.Dao.Retrofit.PromocionCabeceraRepository;
import com.vistony.salesforce.Dao.Retrofit.PromocionDetalleRepository;
import com.vistony.salesforce.Dao.Retrofit.QuoteEffectivenessRepository;
import com.vistony.salesforce.Dao.Retrofit.ReasonDispatchRepository;
import com.vistony.salesforce.Dao.Retrofit.ReasonFreeTransferRepository;
import com.vistony.salesforce.Dao.Retrofit.RutaFuerzaTrabajoRepository;
import com.vistony.salesforce.Dao.Retrofit.SellerRouteRepository;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.Retrofit.StockWS;
import com.vistony.salesforce.Dao.Retrofit.TerminoPagoWS;
import com.vistony.salesforce.Dao.Retrofit.TypeDispatchRepository;
import com.vistony.salesforce.Dao.Retrofit.UbigeoRepository;
import com.vistony.salesforce.Dao.Retrofit.VisitaRepository;
import com.vistony.salesforce.Dao.Retrofit.WarehouseRepository;
import com.vistony.salesforce.Dao.SQLite.AgenciaSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.CatalogoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Dao.Adapters.ListaParametrosDao;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ListaPromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.MotivoVisitaSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Dao.SQLite.ParametrosSQLite;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.StockSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.TerminoPagoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.ZonaSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CatalogoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DireccionClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.MotivoVisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ParametrosSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.RutaFuerzaTrabajoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.RutaVendedorSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.StockSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;
import com.vistony.salesforce.kotlin.Model.SalesCalendarRepository;
import com.vistony.salesforce.kotlin.Model.SalesCalendarViewModel;
import com.vistony.salesforce.kotlin.Model.ServiceAppRepository;
import com.vistony.salesforce.kotlin.Model.ServiceAppViewModel;
import com.vistony.salesforce.kotlin.Utilities.ServiceApp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class
ParametrosView extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private View v;
    private ListView listviewparametro;
    public static ClienteSQlite clienteSQlite;
    private BancoSQLite bancoSQLite;
    private BancoSQLite bancoSQLite2;
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
    private MotivoVisitaSQLiteDao motivoVisitaSQLiteDao;
    private CatalogoSQLiteDao catalogoSQLiteDao;
    private DireccionSQLite direccionSQLite;
    private ZonaSQLiteDao zonaSQLiteDao;
    private ClienteSQlite clienteSQlite2;
    private ProgressDialog pd;
    private String mParam1;
    private String mParam2;
    private String imei, compania_id, fuerzatrabajo_id;
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
    static List<MotivoVisitaSQLiteEntity> LMVisita;
    List<ClienteSQLiteEntity> LclientesqlSQLiteEntity;
    SqliteController sqliteController;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    ListaParametrosAdapter listaParametrosAdapter;
    ListaParametrosEntity listaParametrosEntity;
    ArrayList<ListaParametrosEntity> arraylistaparametrosentity;
    ParametrosSQLite parametrosSQLite;
    FloatingActionButton fabdescargarparametros;
    UsuarioSQLite usuarioSQLite;
    String conexion = "";
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity;
    private final int MY_PERMISSIONS_REQUEST_ACCESS_LOCATION = 1;
    private OrdenVentaRepository ordenVentaRepository;
    private VisitaRepository visitaRepository;
    private DepositoRepository depositoRepository;
    private CobranzaRepository cobranzaRepository;
    private BackupRepository backupRepository;
    private BancoRepository bancoRepository;
    private PriceListRepository priceListRepository;
    private HeaderDispatchSheetRepository headerDispatchSheetRepository;
    private EscColoursCRepository escColoursCRepository;
    MenuItem seleccionar_todo;
    private TypeDispatchRepository typeDispatchRepository;
    private ReasonDispatchRepository reasonDispatchRepository;
    private RutaFuerzaTrabajoRepository rutaFuerzaTrabajoRepository;
    private StatusDispatchRepository statusDispatchRepository;
    private QuoteEffectivenessRepository quoteEffectivenessRepository;
    private LeadClienteViewModel leadClienteViewModel;
    private UbigeoRepository ubigeoRepository;
    private ListaPrecioRepository listaPrecioRepository;
    private PromocionCabeceraRepository promocionCabeceraRepository;
    private PromocionDetalleRepository promocionDetalleRepository;
    private ReasonFreeTransferRepository reasonFreeTransferRepository;
    private PriceListHeadRepository priceListHeadRepository;
    private ObjectRepository objectRepository;
    private BusinessLayerRepository businessLayerRepository;
    private BusinessLayerSalesDetailRepository businessLayerSalesDetailRepository;
    private WarehouseRepository warehouseRepository;
    UsuarioSQLiteEntity usuarioSQLiteEntity;
    private SellerRouteRepository sellerRouteRepository;
    AppExecutors executor;

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

        /*StrictModeController.enableStrictMode();
        StrictModeController.allowDiskReads(
                clienteSQlite = new ClienteSQlite(getContext())
        );*/
        super.onCreate(savedInstanceState);
        sesionEntity = new SesionEntity();
        clienteSQlite = new ClienteSQlite(getContext());
        listaclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        bancoSQLite = new BancoSQLite(getContext());
        documentoSQLite = new DocumentoSQLite(getContext());
        cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(getContext());
        parametrosSQLite = new ParametrosSQLite(getContext());
        terminoPagoSQLiteDao = new TerminoPagoSQLiteDao(getContext());
        agenciaSQLiteDao = new AgenciaSQLiteDao(getContext());
        listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(getContext());
        stockSQLiteDao = new StockSQLiteDao(getContext());
        listaPromocionSQLiteDao = new ListaPromocionSQLiteDao(getContext());
        promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(getContext());
        promocionDetalleSQLiteDao = new PromocionDetalleSQLiteDao(getContext());
        catalogoSQLiteDao = new CatalogoSQLiteDao(getContext());
        motivoVisitaSQLiteDao = new MotivoVisitaSQLiteDao(getContext());
        direccionSQLite = new DireccionSQLite(getContext());
        obtenerWSParametros = new ObtenerWSParametros();
        LCliente = new ArrayList<ClienteSQLiteEntity>();
        LBanco = new ArrayList<BancoSQLiteEntity>();
        LDDeuda = new ArrayList<DocumentoDeudaSQLiteEntity>();
        LRVendedor = new ArrayList<RutaVendedorSQLiteEntity>();
        LTPago = new ArrayList<>();
        LAgencia = new ArrayList<>();
        LPDetalle = new ArrayList<>();
        LPromocion = new ArrayList<>();
        LPromocionDetalle = new ArrayList<>();
        LCatalogo = new ArrayList<>();
        LDCliente = new ArrayList<>();
        LMVisita = new ArrayList<>();
        zonaSQLiteDao = new ZonaSQLiteDao(getContext());
        obtenerPametros();
        LclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();

        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = new Date();
        fecha = dateFormat.format(date);

        sqliteController = new SqliteController(getContext());
        usuarioSQLite = new UsuarioSQLite(getContext());

        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        rutaFuerzaTrabajoSQLiteDao = new RutaFuerzaTrabajoSQLiteDao(getContext());
        executor = new AppExecutors();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().setTitle(getResources().getString(R.string.app_fragment_Parametro));
        setHasOptionsMenu(true);
        v = inflater.inflate(R.layout.fragment_parametros_view, container, false);
        listviewparametro = v.findViewById(R.id.listparametro);
        fabdescargarparametros = v.findViewById(R.id.fabdescargarparametros);

        ordenVentaRepository = new ViewModelProvider(getActivity()).get(OrdenVentaRepository.class);
        visitaRepository = new ViewModelProvider(getActivity()).get(VisitaRepository.class);
        depositoRepository = new ViewModelProvider(getActivity()).get(DepositoRepository.class);
        cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
        backupRepository = new ViewModelProvider(getActivity()).get(BackupRepository.class);
        bancoRepository = new ViewModelProvider(getActivity()).get(BancoRepository.class);
        priceListRepository = new ViewModelProvider(getActivity()).get(PriceListRepository.class);
        headerDispatchSheetRepository = new ViewModelProvider(getActivity()).get(HeaderDispatchSheetRepository.class);
        escColoursCRepository = new ViewModelProvider(getActivity()).get(EscColoursCRepository.class);
        listaParametrosEntity = new ListaParametrosEntity();
        arraylistaparametrosentity = new ArrayList<ListaParametrosEntity>();
        ArrayList<ParametrosSQLiteEntity> listaparametrosSQLiteEntity = new ArrayList<>();
        FormulasController formulasController = new FormulasController(getContext());
        typeDispatchRepository = new ViewModelProvider(getActivity()).get(TypeDispatchRepository.class);
        reasonDispatchRepository = new ViewModelProvider(getActivity()).get(ReasonDispatchRepository.class);
        rutaFuerzaTrabajoRepository = new ViewModelProvider(getActivity()).get(RutaFuerzaTrabajoRepository.class);
        statusDispatchRepository = new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);
        quoteEffectivenessRepository = new ViewModelProvider(getActivity()).get(QuoteEffectivenessRepository.class);
        leadClienteViewModel = new ViewModelProvider(getActivity()).get(LeadClienteViewModel.class);
        ubigeoRepository = new ViewModelProvider(getActivity()).get(UbigeoRepository.class);
        listaPrecioRepository = new ViewModelProvider(getActivity()).get(ListaPrecioRepository.class);
        promocionCabeceraRepository = new ViewModelProvider(getActivity()).get(PromocionCabeceraRepository.class);
        promocionDetalleRepository = new ViewModelProvider(getActivity()).get(PromocionDetalleRepository.class);
        reasonFreeTransferRepository = new ViewModelProvider(getActivity()).get(ReasonFreeTransferRepository.class);
        priceListHeadRepository = new ViewModelProvider(getActivity()).get(PriceListHeadRepository.class);
        objectRepository = new ViewModelProvider(getActivity()).get(ObjectRepository.class);
        businessLayerRepository = new ViewModelProvider(getActivity()).get(BusinessLayerRepository.class);
        businessLayerSalesDetailRepository = new ViewModelProvider(getActivity()).get(BusinessLayerSalesDetailRepository.class);
        warehouseRepository = new ViewModelProvider(getActivity()).get(WarehouseRepository.class);
        ServiceAppRepository serviceAppRepository = new ServiceAppRepository();
        ServiceAppViewModel serviceAppViewModel = new ViewModelProvider(this, new ServiceAppViewModel.ServiceAppViewModelFactory(
                serviceAppRepository,
                getContext(),
                SesionEntity.imei
        )).get(ServiceAppViewModel.class);

        SalesCalendarRepository salesCalendarRepository = new SalesCalendarRepository();
        SalesCalendarViewModel salesCalendarViewModel = new ViewModelProvider(this, new SalesCalendarViewModel.SalesCalendarViewModelFactory(
                salesCalendarRepository,
                getContext()
        )).get(SalesCalendarViewModel.class);
        UsuarioSQLite usuarioSQLite = new UsuarioSQLite(getContext());
        usuarioSQLiteEntity = new UsuarioSQLiteEntity();
        usuarioSQLiteEntity = usuarioSQLite.ObtenerUsuarioSesion();
        sellerRouteRepository = new ViewModelProvider(getActivity()).get(SellerRouteRepository.class);


        //CARGA DE MAESTROS
        listaparametrosSQLiteEntity = parametrosSQLite.ObtenerParametros();
        switch (BuildConfig.FLAVOR) {
            //case "ecuador":
            case "india":
                if (listaparametrosSQLiteEntity.isEmpty()) {
                    parametrosSQLite.LimpiarParametros();
                    parametrosSQLite.InsertaParametros("1", this.getResources().getString(R.string.clients).toLowerCase(), "0", getDateTime());
                    parametrosSQLite.InsertaParametros("2", this.getResources().getString(R.string.banks).toUpperCase(), "0", getDateTime());
                    parametrosSQLite.InsertaParametros("5", this.getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase(), "0", getDateTime());
                    parametrosSQLite.InsertaParametros("6", this.getResources().getString(R.string.Agencies).toUpperCase(), "0", getDateTime());
                    parametrosSQLite.InsertaParametros("7", this.getResources().getString(R.string.price_list).toUpperCase(), "0", getDateTime());
                    parametrosSQLite.InsertaParametros("12", this.getResources().getString(R.string.route_workforce).toUpperCase(), "0", getDateTime());
                }
                if (parametrosSQLite.ObtenerCantidadParametroID("17") == 0) {
                    parametrosSQLite.InsertaParametros("17", this.getResources().getString(R.string.reasons_visit).toUpperCase(), "0", getDateTime());
                }
                break;
            case "chile":
            case "peru":
            case "bolivia":
            case "paraguay":
            case "perurofalab":
            case "espania":
            case "marruecos":
            case "ecuador":
                if (listaparametrosSQLiteEntity.isEmpty()) {
                    if (SesionEntity.perfil_id.equals("Chofer") || SesionEntity.perfil_id.equals("CHOFER")) {
                        //if (listaparametrosSQLiteEntity.isEmpty()) {
                        parametrosSQLite.LimpiarParametros();
                        parametrosSQLite.InsertaParametros("1", this.getResources().getString(R.string.clients).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("2", this.getResources().getString(R.string.banks).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("19", this.getResources().getString(R.string.dispatch_sheet_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("20", this.getResources().getString(R.string.dispatch_sheet_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("23", this.getResources().getString(R.string.dispatch_type).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("24", this.getResources().getString(R.string.dispatch_reason).toUpperCase(), "0", getDateTime());
                        // }
                    } else {
                        if (listaparametrosSQLiteEntity.isEmpty()) {
                            parametrosSQLite.LimpiarParametros();
                            parametrosSQLite.InsertaParametros("1", this.getResources().getString(R.string.clients).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("2", this.getResources().getString(R.string.banks).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("5", this.getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("6", this.getResources().getString(R.string.Agencies).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("7", this.getResources().getString(R.string.price_list).toUpperCase(), "0", getDateTime());
                            //parametrosSQLite.InsertaParametros("8", "STOCK", "0", getDateTime());
                            parametrosSQLite.InsertaParametros("9", this.getResources().getString(R.string.list_promotion).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("10", this.getResources().getString(R.string.promotion_head).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("11", this.getResources().getString(R.string.promotion_detail).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("12", this.getResources().getString(R.string.route_workforce).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("17", this.getResources().getString(R.string.reasons_visit).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("21", this.getResources().getString(R.string.colors_head).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("22", this.getResources().getString(R.string.colors_detail).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("25", this.getResources().getString(R.string.ubigeous).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("26", this.getResources().getString(R.string.reason_free_transfer).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("27", this.getResources().getString(R.string.price_list_head).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("28", this.getResources().getString(R.string.busines_layer).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("29", this.getResources().getString(R.string.objects).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("30", this.getResources().getString(R.string.busines_layer_sales_detail).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("31", this.getResources().getString(R.string.warehouse).toUpperCase(), "0", getDateTime());
                            parametrosSQLite.InsertaParametros("32", this.getResources().getString(R.string.seller_route).toUpperCase(), "0", getDateTime());
                        }
                        /*if (parametrosSQLite.ObtenerCantidadParametroID("18") == 0) {
                            parametrosSQLite.InsertaParametros("18", this.getResources().getString(R.string.price_list), "0", getDateTime());
                        }*/
                    }
                } else {
                    if (listaparametrosSQLiteEntity.size() == 7) {
                        parametrosSQLite.LimpiarParametros();
                        parametrosSQLite.InsertaParametros("1", this.getResources().getString(R.string.clients).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("2", this.getResources().getString(R.string.banks).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("5", this.getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("6", this.getResources().getString(R.string.Agencies).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("7", this.getResources().getString(R.string.price_list).toUpperCase(), "0", getDateTime());
                        //parametrosSQLite.InsertaParametros("8", "STOCK", "0", getDateTime());
                        parametrosSQLite.InsertaParametros("9", this.getResources().getString(R.string.list_promotion).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("10", this.getResources().getString(R.string.promotion_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("11", this.getResources().getString(R.string.promotion_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("12", this.getResources().getString(R.string.route_workforce).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("17", this.getResources().getString(R.string.reasons_visit).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("21", this.getResources().getString(R.string.colors_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("22", this.getResources().getString(R.string.colors_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("25", this.getResources().getString(R.string.ubigeous).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("26", this.getResources().getString(R.string.reason_free_transfer).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("27", this.getResources().getString(R.string.price_list_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("28", this.getResources().getString(R.string.busines_layer).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("29", this.getResources().getString(R.string.objects).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("30", this.getResources().getString(R.string.busines_layer_sales_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("31", this.getResources().getString(R.string.warehouse).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("32", this.getResources().getString(R.string.seller_route).toUpperCase(), "0", getDateTime());
                    }
                    else if(listaparametrosSQLiteEntity.size() == 19)
                    {
                        parametrosSQLite.LimpiarParametros();
                        parametrosSQLite.InsertaParametros("1", this.getResources().getString(R.string.clients).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("2", this.getResources().getString(R.string.banks).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("5", this.getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("6", this.getResources().getString(R.string.Agencies).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("7", this.getResources().getString(R.string.price_list).toUpperCase(), "0", getDateTime());
                        //parametrosSQLite.InsertaParametros("8", "STOCK", "0", getDateTime());
                        parametrosSQLite.InsertaParametros("9", this.getResources().getString(R.string.list_promotion).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("10", this.getResources().getString(R.string.promotion_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("11", this.getResources().getString(R.string.promotion_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("12", this.getResources().getString(R.string.route_workforce).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("17", this.getResources().getString(R.string.reasons_visit).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("21", this.getResources().getString(R.string.colors_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("22", this.getResources().getString(R.string.colors_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("25", this.getResources().getString(R.string.ubigeous).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("26", this.getResources().getString(R.string.reason_free_transfer).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("27", this.getResources().getString(R.string.price_list_head).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("28", this.getResources().getString(R.string.busines_layer).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("29", this.getResources().getString(R.string.objects).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("30", this.getResources().getString(R.string.busines_layer_sales_detail).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("31", this.getResources().getString(R.string.warehouse).toUpperCase(), "0", getDateTime());
                        parametrosSQLite.InsertaParametros("32", this.getResources().getString(R.string.seller_route).toUpperCase(), "0", getDateTime());
                    }
                }
                break;
        }


        if (SesionEntity.imei == null || SesionEntity.imei.equals("")) {

        } else {
            /////////////////////Sincronizar Recibos Pendientes de Depositar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.SynchronizedepositedPendingCollection(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });

            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.UndepositedPendingCollection(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });

            ///////////////  /ENVIAR RECIBOS PENDIENTE CON DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.depositedPendingCollection(getContext()).observe(getActivity(), data -> {
                Log.e("REOS-ParametrosView-depositedPendingCollection", "=>" + data);
            });


            ///////////////////////////// ENVIAR VISITAS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            if (SesionEntity.sendvisits.equals("Y")) {
                visitaRepository.visitResend(getContext()).observe(getActivity(), data -> {
                    Log.e("REOS-ParametrosView-visitResend", "=>" + data);
                });
            }

            ///////////////////////////// ENVIAR DEPOSITOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            depositoRepository.depositResend(getContext()).observe(getActivity(), data -> {
                Log.e("REOS-ParametrosView-depositResend", "=>" + data);
            });

            ///////////////////////////// BACKUP SLITE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            if (FLAG_BACKUP.equals("")) {
                backupRepository.sendSqlite(getContext()).observe(getActivity(), data -> {
                    Log.e("Jepicame", "=>" + data);
                });
            }
            ////////////////////////SINCRONIZACION ANULADOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.Synchronizevoidedreceipts(getContext(), SesionEntity.imei).observe(getActivity(), data -> {
                Log.e("REOS-ParametrosView-Synchronizevoidedreceipts-", "=>" + data);
            });
            ///////////////////////////// BANCO SLITE \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            bancoRepository.getAndInsertBank(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                Log.e("REOS-ParametrosView-getAndInsertBank-", "=>" + data);
            });

            ///////////////////////////// ENVIAR DEPOSITOS ANULADOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            depositoRepository.UpdatedepositStatus(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });
            ////////////////////////ENVIAR RECIBOS DESVINCULADOS DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            cobranzaRepository.ReceiptDetachedDeposit(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });


            if (SesionEntity.perfil_id.equals("CHOFER") || SesionEntity.perfil_id.equals("Chofer")) {
                //////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                //statusDispatchRepository.statusDispatchSendTime(getContext()).observe(getActivity(), data -> {
                statusDispatchRepository.statusDispatchSendTime(getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS", "statusDispatchRepository-->statusDispatchSendTime-->resultdata" + data);
                });

                /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                //statusDispatchRepository.statusDispatchSend(getContext()).observe(getActivity(), data -> {
                statusDispatchRepository.statusDispatchSendPhoto(getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS", "statusDispatchRepository-->statusDispatchSendPhoto-->resultdata" + data);
                });

                /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                //statusDispatchRepository.statusDispatchSend(getContext()).observe(getActivity(), data -> {
                statusDispatchRepository.statusDispatchListSend(getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS", "statusDispatchRepository-->statusDispatchListSend-->resultdata" + data);
                });

                ///////////////////////////Motivos de Despacho/////////////////////////////////////////////////
                reasonDispatchRepository.geReasonDispatch(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("Jepicame", "=>" + data);
                });

                ///////////////////////////COLORES/////////////////////////////////////////////////
                typeDispatchRepository.geTypeDispatch(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("Jepicame", "=>" + data);
                });
            } else {

                reasonFreeTransferRepository.getReasonFreeTransfer(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                        , data -> {
                            Log.e("REOS", "ParametrosView-getReasonFreeTransfer-data: " + data);
                        }
                );
                //////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                //statusDispatchRepository.statusDispatchSendTime(getContext()).observe(getActivity(), data -> {
                priceListHeadRepository.getAddAllPriceListHead(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS", "PriceListHeadRepository-->getAddAllPriceListHead-->resultdata" + data);
                });

                ///////////////  /ENVIAR RECIBOS PENDIENTE CON DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\
                sellerRouteRepository.getAddSellerRoute(SesionEntity.imei, fecha, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS-ParametrosView-sellerRouteRepository-getAddSellerRoute", "=>" + data);
                });

                String datepricelist = "", datepromotionhead = "", datepromotiondetail = "";
                ParametrosSQLite parametrosSQLite = new ParametrosSQLite(getContext());
                datepricelist = parametrosSQLite.getDateParemeterforName(getContext().getResources().getString(R.string.price_list).toUpperCase());
                Log.e("REOS", "ParametrosView-onCreate-fecha: " + fecha);
                Log.e("REOS", "ParametrosView-onCreate-datepricelist: " + datepricelist);
                if (!fecha.equals(datepricelist)) {
                    listaPrecioRepository.execClarAndAddPriceList(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                            , data -> {
                                Log.e("REOS", "ParametrosView-listaPrecioRepository-data: " + data);
                            }
                    );
                }

                datepromotionhead = parametrosSQLite.getDateParemeterforName(getContext().getResources().getString(R.string.promotion_head).toUpperCase());
                Log.e("REOS", "ParametrosView-onCreate-fecha: " + fecha);
                Log.e("REOS", "ParametrosView-onCreate-datepromotionhead: " + datepromotionhead);
                if (!fecha.equals(datepromotionhead)) {
                    promocionCabeceraRepository.exeClearandAddPromotionHead(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                            , data -> {
                                Log.e("REOS", "ParametrosView-promocionCabeceraRepository-data: " + data);
                            }
                    );
                }

                datepromotiondetail = parametrosSQLite.getDateParemeterforName(getContext().getResources().getString(R.string.promotion_detail).toUpperCase());
                Log.e("REOS", "ParametrosView-onCreate-fecha: " + fecha);
                Log.e("REOS", "ParametrosView-onCreate-datepromotiondetail: " + datepromotiondetail);
                if (!fecha.equals(datepromotiondetail)) {
                    promocionDetalleRepository.exeClearandAddPromotionDetail(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                            , data -> {
                                Log.e("REOS", "ParametrosView-promocionDetalleRepository-data: " + data);
                            }
                    );
                }

                //Envio de Geolocalizacion sin Foto en Bloque
                leadClienteViewModel.sendGeolocationBlock(getContext(), SesionEntity.imei, executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS", "sendGeolocationBlock" + data);
                });

                ubigeoRepository.geUbigeo(SesionEntity.imei, getContext()).observe(getActivity(), data -> {
                    Log.e("REOS", "ParametrosView-ubigeoRepository-data: " + data);
                });

                //Envio de Geolocalizacion con Foto
                leadClienteViewModel.sendGeolocationClient(getContext(), SesionEntity.imei, executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("REOS", "sendGeolocationClient" + data);
                });

                ///////////////////////////Cuota de Efectividad/////////////////////////////////////////////////
                quoteEffectivenessRepository.getQuoteEffectiveness(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("Jepicame", "=>" + data);
                });

                ///////////////////////////COLORES/////////////////////////////////////////////////
                escColoursCRepository.getEscColours(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                    Log.e("Jepicame", "=>" + data);
                });

                //Se desactivo para identificar el motivo de reinicio 14/06/2023 14:22
                //Validar Pedidos
                if (SesionEntity.sendvalidations.equals("Y")) {
                    OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite = new OrdenVentaCabeceraSQLite(getContext());
                    ArrayList<OrdenVentaCabeceraSQLiteEntity> listSalesOrders = new ArrayList<>();
                    listSalesOrders = ordenVentaCabeceraSQLite.getSalesOrderPendingSAP();

                    for (int i = 0; i < listSalesOrders.size(); i++) {
                        ordenVentaRepository.validateSalesOrder(
                                listSalesOrders.get(i).getCliente_id(),
                                FormulasController.Convertirfechahoraafechanumerica(listSalesOrders.get(i).getFecharegistro()),
                                listSalesOrders.get(i).getOrdenventa_id(),
                                usuarioSQLiteEntity.fuerzatrabajo_id,
                                getContext()
                        ).observe(getActivity(), data -> {
                            Log.e("REOS", "ParametrosView-validateSalesOrder-data: " + data);
                        });
                    }
                } else {

                }
            }
        }


        fabdescargarparametros.setOnClickListener(view -> {
            Object objeto = null, object2 = null;
            ///////////////////////////// ENVIAR PEDIDOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            ordenVentaRepository.salesOrderResend(getContext()).observe(getActivity(), data -> {
                Log.e("REOS-ParametrosView-salesOrderResend", "=>" + data);
            });
            ///////////////////////////Ruta de Trabajo/////////////////////////////////////////////////
            rutaFuerzaTrabajoRepository.getInsertDBWorkPath(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });

            listaPrecioRepository.execClarAndAddPriceList(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                    , data -> {
                        Log.e("REOS", "ParametrosView-listaPrecioRepository-data: " + data);
                    }
            );

            promocionCabeceraRepository.exeClearandAddPromotionHead(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                    , data -> {
                        Log.e("REOS", "ParametrosView-promocionCabeceraRepository-data: " + data);
                    }
            );

            promocionDetalleRepository.exeClearandAddPromotionDetail(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity()
                    , data -> {
                        Log.e("REOS", "ParametrosView-promocionDetalleRepository-data: " + data);
                    }
            );

            objectRepository.getObjectAPI(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                Log.e("REOS", "ObjectRepository-->getObjectAPI-->resultdata" + data);
            });

            businessLayerRepository.getBussinessLayer(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                Log.e("REOS", "BusinessLayerRepository->getBussinessLayer-->resultdata" + data);
            });

            businessLayerSalesDetailRepository.getBussinessLayerSalesDetail(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                Log.e("REOS", "BusinessLayerSalesDetailRepository->getBussinessLayerSalesDetail-->resultdata" + data);
            });

            warehouseRepository.getWarehouse(SesionEntity.imei, getContext(), executor.diskIO()).observe(getActivity(), data -> {
                Log.e("REOS", "WarehouseRepository->getWarehouse-->resultdata" + data);
            });


            objeto = listaParametrosAdapter.ObtenerListaParametros();

            arraylistaparametrosentity = (ArrayList<ListaParametrosEntity>) objeto;
            String[] valores = new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            int p = 0;
            for (int i = 0; i < arraylistaparametrosentity.size(); i++) {
                if (arraylistaparametrosentity.get(i).isChkparametro()) {
                    valores[p] = (arraylistaparametrosentity.get(i).getNombreparametro().toString());
                    p++;
                }
            }

            obtenerWSParametros = new ObtenerWSParametros();
            obtenerWSParametros.execute(valores);
            /*if (usuarioSQLiteEntity.getU_VIS_ManagementType().equals("B2B")) {
                LocalDate today = LocalDate.now();

                // Obtener el primer día del mes anterior
                LocalDate firstDayOfLastMonth = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());

                // Formatear el primer día del mes anterior
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                String formattedFirstDay = firstDayOfLastMonth.format(formatter);
                Log.e("REOS", "ParametrosView->salesCalendarViewModel.addSalesCalendar-formattedFirstDay: " + formattedFirstDay);
                System.out.println("Primer día del mes anterior: " + formattedFirstDay);

                // Obtener el último día de este mes
                LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());

                // Formatear el último día de este mes
                String formattedLastDay = lastDayOfThisMonth.format(formatter);
                Log.e("REOS", "ParametrosView->salesCalendarViewModel.addSalesCalendar-formattedLastDay: " + formattedLastDay);
                System.out.println("Último día de este mes: " + formattedLastDay);
                //Log.e("REOS", "WarehouseRepository->getWarehouse-->resultdata" + formattedLastDay);

                serviceAppViewModel.addServiceApp();
                salesCalendarViewModel.addSalesCalendar(SesionEntity.imei, formattedFirstDay, formattedLastDay);
            }*/
            //Ejecuta la carga de parametros necesarios para la notificacion
            switch (BuildConfig.FLAVOR)
            {
                case "peru":
                    if (!SesionEntity.perfil_id.equals("chofer")) {
                        if (usuarioSQLiteEntity.getU_VIS_ManagementType().equals("B2B")) {
                            getParametersNotification(serviceAppViewModel, salesCalendarViewModel);
                        }
                    }
                    break;
                case "bolivia":
                    /*if (!SesionEntity.perfil_id.equals("chofer")) {
                            getParametersNotification(serviceAppViewModel, salesCalendarViewModel);
                    }*/
                    break;
            }

        });


        if (conexion.equals("0")) {
            fabdescargarparametros.setEnabled(false);
        }

        if (SesionEntity.imei == null || SesionEntity.imei.equals("")) {

        } else {
            if (!BuildConfig.FLAVOR.equals("india")) { //la india por el app de lead no carga parametros
                obtenerWSParametros.execute("Todos");
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {

            } else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }

        //Manejo de servicios de Notificaciones
        switch (BuildConfig.FLAVOR) {
            case "peru":
                if (!SesionEntity.perfil_id.equals("chofer")) {

                    Log.e("REOS", "ParametrosView-oncreate-ServiceNotificationApp.isServiceRunning: " + ServiceApp.isServiceRunning);
                    if (usuarioSQLiteEntity.getU_VIS_ManagementType().equals("B2B")) {


                        if (!ServiceApp.isServiceRunning) {
                            //Inicio de Servicio y Envio de Notificacion
                            if (areNotificationsEnabled(getContext())) {
                                // Las notificaciones están habilitadas
                                try {
                                    //Intent intent = new Intent(this, MenuView.class);
                                    Intent intent = new Intent(getContext(), ServiceApp.class);
                                    //Log.e("REOS", "Composables-StatusIcons-contexto: "+context1)
                                    //Log.e("REOS", "Composables-StatusIcons-intent: "+intent)
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        //Log.e("REOS", "Composables-StatusIcons-startForegroundService")
                                        ContextCompat.startForegroundService(getActivity(), intent);
                                    } else {
                                        //Log.e("REOS", "Composables-StatusIcons-startService ")
                                        //ServiceNotificationApp serviceNotificationApp=new ServiceNotificationApp();
                                        //serviceNotificationApp.startService(intent);
                                        ContextCompat.startForegroundService(getActivity(), intent);
                                    }
                                } catch (Exception e) {
                                    Log.e("REOS", "Composables-StatusIcons-error: " + e.toString());
                                }
                            } else {
                                // Las notificaciones están deshabilitadas
                                showNotificationDisabledDialog(getContext());
                            }
                            Toast.makeText(getContext(), "El servicio de notificaciones, esta en proceso de inicio.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "El servicio ya se encuentra activo, no es necesario volverlo a iniciar.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case "bolivia":
                /*if (!SesionEntity.perfil_id.equals("chofer")) {
                    if (!ServiceApp.isServiceRunning) {
                        //Inicio de Servicio y Envio de Notificacion
                        if (areNotificationsEnabled(getContext())) {
                            // Las notificaciones están habilitadas
                            try {
                                //Intent intent = new Intent(this, MenuView.class);
                                Intent intent = new Intent(getContext(), ServiceApp.class);
                                //Log.e("REOS", "Composables-StatusIcons-contexto: "+context1)
                                //Log.e("REOS", "Composables-StatusIcons-intent: "+intent)
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    //Log.e("REOS", "Composables-StatusIcons-startForegroundService")
                                    ContextCompat.startForegroundService(getActivity(), intent);
                                } else {
                                    //Log.e("REOS", "Composables-StatusIcons-startService ")
                                    //ServiceNotificationApp serviceNotificationApp=new ServiceNotificationApp();
                                    //serviceNotificationApp.startService(intent);
                                    ContextCompat.startForegroundService(getActivity(), intent);
                                }
                            } catch (Exception e) {
                                Log.e("REOS", "Composables-StatusIcons-error: " + e.toString());
                            }
                        } else {
                            // Las notificaciones están deshabilitadas
                            showNotificationDisabledDialog(getContext());
                        }
                        Toast.makeText(getContext(), "El servicio de notificaciones, esta en proceso de inicio.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "El servicio ya se encuentra activo, no es necesario volverlo a iniciar.", Toast.LENGTH_SHORT).show();
                    }
                }*/
                break;
            default:
                break;
        }
        return v;
    }

    public void getParametersNotification(ServiceAppViewModel serviceAppViewModel,SalesCalendarViewModel salesCalendarViewModel)
    {
        LocalDate today = LocalDate.now();

        // Obtener el primer día del mes anterior
        LocalDate firstDayOfLastMonth = today.minusMonths(1).with(TemporalAdjusters.firstDayOfMonth());

        // Formatear el primer día del mes anterior
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedFirstDay = firstDayOfLastMonth.format(formatter);
        Log.e("REOS", "ParametrosView->salesCalendarViewModel.addSalesCalendar-formattedFirstDay: " + formattedFirstDay);
        System.out.println("Primer día del mes anterior: " + formattedFirstDay);

        // Obtener el último día de este mes
        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth());

        // Formatear el último día de este mes
        String formattedLastDay = lastDayOfThisMonth.format(formatter);
        Log.e("REOS", "ParametrosView->salesCalendarViewModel.addSalesCalendar-formattedLastDay: " + formattedLastDay);
        System.out.println("Último día de este mes: " + formattedLastDay);
        //Log.e("REOS", "WarehouseRepository->getWarehouse-->resultdata" + formattedLastDay);

        serviceAppViewModel.addServiceApp();
        salesCalendarViewModel.addSalesCalendar(SesionEntity.imei, formattedFirstDay, formattedLastDay);
    }


    private class ObtenerWSParametros extends AsyncTask<String, Void, String> {
        String argumento="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.download_parameters), true, true);
        }
        @Override
        protected String doInBackground(String... arg0) {
            try {
                int CantClientes=0,CantBancos=0,CantDocumentosDeuda=0,CantRutaVendedor=0,CantTerminoPago=0,
                        CantAgencia=0,CantListaPrecioDetalle=0,CantStock=0,CantListaPromocion=0,CantPromocionCabecera=0,
                CantPromocionDetalle=0,CantRutaFuerzaTrabajo=0,CantCatalogo=0,CantDireccionCliente=0,CantHojaDespacho=0,CantMotivoVisita=0;


                for(int i=0;i<arg0.length;i++) {
                    argumento = arg0[i];
                    if (argumento.equals("Todos")) {

                        ClienteRepository clienteRepository;
                        //ListaPrecioRepository listaPrecioRepository;
                        // bancoRepository.getAndInsertBank(SesionEntity.imei,getContext());
                        switch (BuildConfig.FLAVOR){
                            case "india":
                            case "ecuador":

                                 clienteRepository = new ClienteRepository(getContext());
                                LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei,"");

                                if (!LclientesqlSQLiteEntity.isEmpty()) {
                                    CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                                    parametrosSQLite.ActualizaCantidadRegistros("1",  getActivity().getResources().getString(R.string.clients).toUpperCase(), String.valueOf(CantClientes), getDateTime());
                                }

                                TerminoPagoWS terminoPagoWS = new TerminoPagoWS(getContext());
                                LTPago = terminoPagoWS.getTerminoPagoWS(SesionEntity.imei);

                                if (!(LTPago.isEmpty())) {
                                    terminoPagoSQLiteDao.LimpiarTablaTerminoPago();
                                    CantTerminoPago = registrarTerminoPagoSQLite(LTPago);
                                    parametrosSQLite.ActualizaCantidadRegistros("5", getActivity().getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase(), String.valueOf(CantTerminoPago), getDateTime());

                                }

                                AgenciaWS agenciaWS = new AgenciaWS(getContext());
                                LAgencia = agenciaWS.getAgenciaWS(SesionEntity.imei);

                                if (!(LAgencia.isEmpty())) {
                                    agenciaSQLiteDao.LimpiarTablaAgencia();
                                    CantAgencia = registrarAgenciaSQLite(LAgencia);
                                    parametrosSQLite.ActualizaCantidadRegistros("6", getActivity().getResources().getString(R.string.Agencies).toUpperCase(), String.valueOf(CantAgencia), getDateTime());
                                }

                                /*listaPrecioRepository = new ListaPrecioRepository(getContext());
                                LPDetalle = listaPrecioRepository.getListaPrecioDetalleWS(SesionEntity.imei);

                                if (!(LPDetalle.isEmpty())) {
                                    listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                                    CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                                    parametrosSQLite.ActualizaCantidadRegistros("7",  getActivity().getResources().getString(R.string.price_list).toUpperCase(), String.valueOf(CantListaPrecioDetalle), getDateTime());
                                }*/

                                /*RutaFuerzaTrabajoRepository rutaFuerzaTrabajoRepository = new RutaFuerzaTrabajoRepository(getContext());
                                LRutaFuerzaTrabajo = rutaFuerzaTrabajoRepository.getRutaFuerzaTrabajoWS(SesionEntity.imei);
                                if (LRutaFuerzaTrabajo!=null) {
                                    rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo();
                                    CantRutaFuerzaTrabajo = registrarRutaFuerzaTrabajoSQLite(LRutaFuerzaTrabajo);
                                    parametrosSQLite.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(CantRutaFuerzaTrabajo), getDateTime());
                                }*/


                                MotivoVisitaWS motivoVisitaWS = new MotivoVisitaWS(getContext());
                                LMVisita = motivoVisitaWS.getMotivoVisitaWS (SesionEntity.imei, SesionEntity.compania_id);
                                if (!(LMVisita.isEmpty())) {
                                    motivoVisitaSQLiteDao.LimpiarTablaMotivoVisita();
                                    CantMotivoVisita = registrarMotivoVisita(LMVisita);
                                    parametrosSQLite.ActualizaCantidadRegistros("17",  getActivity() .getResources().getString(R.string.reasons_visit).toUpperCase(), String.valueOf(CantMotivoVisita), getDateTime());
                                }


                                break;
                            case "peru":
                            case "chile":
                            case "perurofalab":
                            case "espania":
                            case "marruecos":
                                if(!SesionEntity.perfil_id.equals("CHOFER"))
                                {
                                     clienteRepository = new ClienteRepository(getContext());
                                    LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei,"");

                                    if (!LclientesqlSQLiteEntity.isEmpty()) {
                                        CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                                        parametrosSQLite.ActualizaCantidadRegistros("1",  getActivity().getResources().getString(R.string.clients).toUpperCase(),String.valueOf(CantClientes), getDateTime());
                                    }
                                }

                                break;
                            case "bolivia":
                            case "paraguay":
                                 /*clienteRepository = new ClienteRepository(getContext());
                                LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei,"");

                                if (!LclientesqlSQLiteEntity.isEmpty()) {
                                    CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                                    parametrosSQLite.ActualizaCantidadRegistros("1", "CLIENTES", String.valueOf(CantClientes), getDateTime());
                                }
                                listaPrecioRepository = new ListaPrecioRepository(getContext());
                                LPDetalle = listaPrecioRepository.getListaPrecioDetalleWS(SesionEntity.imei);

                                if (!(LPDetalle.isEmpty())) {
                                    listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                                    CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                                    parametrosSQLite.ActualizaCantidadRegistros("7", "LISTA PRECIO", String.valueOf(CantListaPrecioDetalle), getDateTime());
                                }*/
                                break;
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
                    else if (argumento.equals(getActivity().getResources().getString(R.string.clients).toUpperCase())) {
                        ClienteRepository clienteRepository = new ClienteRepository(getContext());
                        LclientesqlSQLiteEntity = clienteRepository.getCustomers(SesionEntity.imei,"");
                        if (!(LclientesqlSQLiteEntity.isEmpty())) {
                            CantClientes = registrarClienteSQLite(LclientesqlSQLiteEntity);
                            parametrosSQLite.ActualizaCantidadRegistros("1",  getActivity().getResources().getString(R.string.clients).toUpperCase(), ""+CantClientes, getDateTime());
                        }

                    }

                    else if (argumento.equals(getActivity().getResources().getString(R.string.banks).toUpperCase())) {
                        bancoRepository.getAndInsertBank(SesionEntity.imei,getContext(),executor.diskIO());
                    }
                    else if (argumento.equals(getActivity().getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase())) {
                        TerminoPagoWS terminoPagoWS = new TerminoPagoWS(getContext());
                        LTPago = terminoPagoWS.getTerminoPagoWS(SesionEntity.imei);

                        if (!(LTPago.isEmpty())) {
                            terminoPagoSQLiteDao.LimpiarTablaTerminoPago();
                            CantTerminoPago = registrarTerminoPagoSQLite(LTPago);
                            parametrosSQLite.ActualizaCantidadRegistros("5", getActivity().getResources().getString(R.string.lbl_orderhed_payterms).toUpperCase(), String.valueOf(CantTerminoPago), getDateTime());

                        }


                    }
                    else if (argumento.equals(getActivity().getResources().getString(R.string.Agencies).toUpperCase().toUpperCase())) {
                        AgenciaWS agenciaWS = new AgenciaWS(getContext());
                        LAgencia = agenciaWS.getAgenciaWS(SesionEntity.imei);

                        if (!(LAgencia.isEmpty())) {
                            agenciaSQLiteDao.LimpiarTablaAgencia();
                            CantAgencia = registrarAgenciaSQLite(LAgencia);
                            parametrosSQLite.ActualizaCantidadRegistros("6", getActivity().getResources().getString(R.string.Agencies).toUpperCase(), String.valueOf(CantAgencia), getDateTime());
                        }
                    }
                    /*else if (argumento.equals(getActivity().getResources().getString(R.string.price_list).toUpperCase())) {
                        ListaPrecioRepository listaPrecioRepository = new ListaPrecioRepository(getContext());
                        LPDetalle = listaPrecioRepository.getListaPrecioDetalleWS(SesionEntity.imei);

                        if (!(LPDetalle.isEmpty())) {
                            listaPrecioDetalleSQLiteDao.LimpiarTablaListaPrecioDetalle();
                            CantListaPrecioDetalle = registrarListaPrecioDetalleSQLite(LPDetalle);
                            parametrosSQLite.ActualizaCantidadRegistros("7", getActivity().getResources().getString(R.string.price_list).toUpperCase(), String.valueOf(CantListaPrecioDetalle), getDateTime());
                        }
                    }*/
                    else if (argumento.equals("STOCK")) {
                        StockWS stockWS = new StockWS(getContext());
                        LStock = stockWS.getStockWS(SesionEntity.imei);

                        if (!(LStock.isEmpty())) {
                            stockSQLiteDao.LimpiarTablaStock();
                            CantStock = registrarStockSQLite(LStock);
                            parametrosSQLite.ActualizaCantidadRegistros("8", "STOCK", String.valueOf(CantStock), getDateTime());
                        }
                    }
                    else if (argumento.equals(getActivity() .getResources().getString(R.string.list_promotion).toUpperCase())) {
                    ListaPromocionWS listaPromocionWS = new ListaPromocionWS(getContext());
                    LPromocion = listaPromocionWS.GetListaPromocionWS(SesionEntity.imei);

                    if (!(LPromocion.isEmpty())) {
                        listaPromocionSQLiteDao.LimpiarTablaListaPromocion();
                        CantListaPromocion = registrarListaPromocionSQLite(LPromocion);
                        parametrosSQLite.ActualizaCantidadRegistros("9", getActivity().getResources().getString(R.string.list_promotion).toUpperCase(), String.valueOf(CantListaPromocion), getDateTime());
                    }
                    }
                    /*else if (argumento.equals(getActivity().getResources().getString(R.string.promotion_head).toUpperCase())) {
                        PromocionCabeceraRepository promocionCabeceraWS = new PromocionCabeceraRepository(getContext());

                        LPCabecera = promocionCabeceraWS.getPromocionCabeceraWS(SesionEntity.imei);

                        if (!(LPCabecera.isEmpty())) {
                            promocionCabeceraSQLiteDao.LimpiarTablaPromocionCabecera();
                            CantPromocionCabecera = registrarPromocionCabeceraSQLite(LPCabecera);
                            parametrosSQLite.ActualizaCantidadRegistros("10", getActivity().getResources().getString(R.string.promotion_head).toUpperCase(), String.valueOf(CantPromocionCabecera), getDateTime());
                        }
                    }*/
                    /*else if (argumento.equals(getActivity().getResources().getString(R.string.promotion_detail).toUpperCase())) {
                        PromocionDetalleRepository promocionDetalleWS = new PromocionDetalleRepository(getContext());
                        LPromocionDetalle = promocionDetalleWS.getPromocionDetalleWS(SesionEntity.imei);
                        if (!(LPromocionDetalle.isEmpty())) {
                            promocionDetalleSQLiteDao.LimpiarTablaPromocionDetalle();
                            CantPromocionDetalle = registrarPromocionDetalleSQLite(LPromocionDetalle);
                            parametrosSQLite.ActualizaCantidadRegistros("11", getActivity().getResources().getString(R.string.promotion_detail).toUpperCase(), String.valueOf(CantPromocionDetalle), getDateTime());
                        }
                    }*/
                    else if (argumento.equals("RUTA FUERZATRABAJO")) {

                        Log.e("JPCM","FUERZA DE TRABAJO DESCARGANDO");
                        /*RutaFuerzaTrabajoRepository rutaFuerzaTrabajoRepository = new RutaFuerzaTrabajoRepository(getContext());
                        LRutaFuerzaTrabajo = rutaFuerzaTrabajoRepository.getRutaFuerzaTrabajoWS(SesionEntity.imei);

                        if (!(LRutaFuerzaTrabajo.isEmpty())) {
                            rutaFuerzaTrabajoSQLiteDao.LimpiarTablaRutaFuerzaTrabajo();
                            CantRutaFuerzaTrabajo = registrarRutaFuerzaTrabajoSQLite(LRutaFuerzaTrabajo);
                            parametrosSQLite.ActualizaCantidadRegistros("12", "RUTA FUERZATRABAJO", String.valueOf(CantRutaFuerzaTrabajo), getDateTime());
                        }*/

                    }else if (argumento.equals(getActivity().getResources().getString(R.string.reasons_visit).toUpperCase())) {
                        MotivoVisitaWS motivoVisitaWS = new MotivoVisitaWS(getContext());
                        LMVisita = motivoVisitaWS.getMotivoVisitaWS (SesionEntity.imei, SesionEntity.compania_id);
                        Log.e("REOS","ParametrosView.CargaParametroIndividual-LMVisita.size(): "+LMVisita.size());
                        if (!(LMVisita.isEmpty())) {
                            motivoVisitaSQLiteDao.LimpiarTablaMotivoVisita();
                            CantMotivoVisita = registrarMotivoVisita(LMVisita);
                            parametrosSQLite.ActualizaCantidadRegistros("17", getActivity().getResources().getString(R.string.reasons_visit).toUpperCase(), String.valueOf(CantMotivoVisita), getDateTime());
                        }

                }else if (argumento.equals(getActivity().getResources().getString(R.string.reasons_visit).toUpperCase())) {
                    MotivoVisitaWS motivoVisitaWS = new MotivoVisitaWS(getContext());
                    LMVisita = motivoVisitaWS.getMotivoVisitaWS (SesionEntity.imei, SesionEntity.compania_id);
                    Log.e("REOS","ParametrosView.CargaParametroIndividual-LMVisita.size(): "+LMVisita.size());
                    if (!(LMVisita.isEmpty())) {
                        motivoVisitaSQLiteDao.LimpiarTablaMotivoVisita();
                        CantMotivoVisita = registrarMotivoVisita(LMVisita);
                        parametrosSQLite.ActualizaCantidadRegistros("17", getActivity().getResources().getString(R.string.reasons_visit).toUpperCase(), String.valueOf(CantMotivoVisita), getDateTime());
                    }
                }else if (argumento.equals("PRICE LIST")) {


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
            ArrayList<ParametrosSQLiteEntity> listaparametrosSQlentity = new ArrayList<ParametrosSQLiteEntity>();
            listaparametrosSQlentity= parametrosSQLite.ObtenerParametros();

            if(getActivity()!=null)
            {
                listaParametrosAdapter = new ListaParametrosAdapter(getActivity(), ListaParametrosDao.getInstance().getLeads(listaparametrosSQlentity, false));
                listviewparametro.setAdapter(listaParametrosAdapter);
            }
            pd.dismiss();
            Toast.makeText(getContext(), getActivity().getResources().getString(R.string.parameters_updated), Toast.LENGTH_SHORT).show();

        }

        protected void ObtenerRecibosPendientes() {
            ArrayList<ListaHistoricoCobranzaEntity> ListaCobranzaDetalleSQLiteEntity = new ArrayList<>();
            CobranzaRepository cobranzaRepository=new CobranzaRepository(getContext());
            //            //ListaCobranzaDetalleSQLiteEntity = historicoCobranzaWSDao.obtenerHistoricoCobranzaPendiente(SesionEntity.imei, SesionEntity.compania_id, "", "", "Pendiente_Deposito", "01-01-0001", SesionEntity.usuario_id, "");
            HistoricoCobranzaWS historicoCobranzaWS=new HistoricoCobranzaWS(getContext());
            ListaCobranzaDetalleSQLiteEntity = cobranzaRepository.getHistoricoCobranza
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
                            ,""
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
                                        "N",
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getDepositodirecto(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getPagopos(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getCodesap(),
                                        "",
                                        "",
                                        "",
                                        "",
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getDocentry(),
                                        ListaCobranzaDetalleSQLiteEntity.get(i).getCollectioncheck()
                                        ,""
                                        ,"0"
                                        ,""
                                        ,ListaCobranzaDetalleSQLiteEntity.get(i).getCollection_salesperson()
                                        ,ListaCobranzaDetalleSQLiteEntity.get(i).getType()
                                );
                    }
                }
            }
        }
    }

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
    public int registrarMotivoVisita(List<MotivoVisitaSQLiteEntity> Lista)
    {
        motivoVisitaSQLiteDao = new MotivoVisitaSQLiteDao(getContext());
        int resultado=0;
        try {

            for (int i = 0; i < Lista.size(); i++) {
                motivoVisitaSQLiteDao.InsertaMotivoVisita(
                        Lista.get(i).getCompania_id(),
                        Lista.get(i).getFuerzatrabajo_id(),
                        Lista.get(i).getUsuario_id(),
                        Lista.get(i).getCode(),
                        Lista.get(i).getName(),
                        Lista.get(i).getType()

                );
            }
            resultado=motivoVisitaSQLiteDao.ObtenerCantidadMotivoVisita();
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return resultado;
    }


/*  COMENTADO 05/09/21 08:23AM
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
                        Lista.get(i).getSaldo_sin_procesar(),
                        Lista.get(i).getD
                );
            }
            resultado= documentoSQLite.ObtenerCantidadDocumentosDeuda();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;


    }
*/
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

    /*
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
                        Lista.get(i).getPorcentaje_descuento(),
                        Lista.get(i).getStock_almacen(),
                        Lista.get(i).getStock_general(),
                        Lista.get(i).getUnit(),
                        Lista.get(i).getOiltax(),
                        Lista.get(i).getLiter(),
                        Lista.get(i).getSIGAUS()
                );
            }
            resultado=listaPrecioDetalleSQLiteDao.ObtenerCantidadListaPrecioDetalle();
        }catch (Exception e) {
            Toast.makeText(getContext(), "Ocurrio un error al guardar la lista de precios", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return resultado;
    }*/

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

    /*
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
    }*/

    /*
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
    }*/

    /*public int registrarRutaFuerzaTrabajoSQLite(List<RutaFuerzaTrabajoSQLiteEntity> Lista)
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
    }*/

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

    /*
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

    }*/


    public void ObtenerParametrosCheck()
    {
        ArrayList<ParametrosSQLiteEntity> listaparametrosSQlentity = new ArrayList<ParametrosSQLiteEntity>();
        listaparametrosSQlentity= parametrosSQLite.ObtenerParametros();

        if(getActivity()!=null)
        {
            listaParametrosAdapter = new ListaParametrosAdapter(getActivity(), ListaParametrosDao.getInstance().getLeads(listaparametrosSQlentity, true));
            listviewparametro.setAdapter(listaParametrosAdapter);
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_parametros, menu);
        seleccionar_todo = menu.findItem(R.id.seleccionar_todo);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.seleccionar_todo:
                alertaSeleccionarTodo(getActivity().getResources().getString(R.string.mse_selection_all_parameters)).show();


                return false;
            default:
                break;
        }
        return false;
    }

    private Dialog alertaSeleccionarTodo(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            ObtenerParametrosCheck();
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


}

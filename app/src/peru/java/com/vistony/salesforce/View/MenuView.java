package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.CifradoController.decrypt;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.BixolonPrinterController;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricContainerSalesRepository;
import com.vistony.salesforce.Dao.Retrofit.HistoricSalesOrderTraceabilityRepository;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerDetailRepository;
import com.vistony.salesforce.Dao.Retrofit.QuotasPerCustomerHeadRepository;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MenuView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ClienteCabeceraView.OnFragmentInteractionListener,
        ClienteDetalleView.OnFragmentInteractionListener,
        LeadClientesView.OnFragmentInteractionListener,
        ParametrosView.OnFragmentInteractionListener,
        CobranzaCabeceraView.OnFragmentInteractionListener,
        CobranzaDetalleView.OnFragmentInteractionListener,
        ConsDepositoView.OnFragmentInteractionListener,
        RutaVendedorView.OnFragmentInteractionListener,
        HistoricoDepositoView.OnFragmentInteractionListener,
        HistoricoCobranzaView.OnFragmentInteractionListener,
        ConfigImpresoraView.OnFragmentInteractionListener,
        ConfigSistemaView.OnFragmentInteractionListener,
        ContenedorComisionesView.OnFragmentInteractionListener,
        ComisionesView.OnFragmentInteractionListener,
        PronosticoComisionesView.OnFragmentInteractionListener,
        MantenimientoClienteView.OnFragmentInteractionListener,
        MapaClienteView.OnFragmentInteractionListener,
        ReclamoClientesView.OnFragmentInteractionListener,
        SeguimientoFacturasView.OnFragmentInteractionListener,
        MenuAccionView.OnFragmentInteractionListener,
        OrdenVentaCabeceraView.OnFragmentInteractionListener,
        OrdenVentaDetalleView.OnFragmentInteractionListener,
        TerminoPagoView.OnFragmentInteractionListener,
        AgenciaView.OnFragmentInteractionListener,
        ProductoView.OnFragmentInteractionListener,
        ListadoPromocionView.OnFragmentInteractionListener,
        PromocionCabeceraView.OnFragmentInteractionListener,
        MenuConsultasView.OnFragmentInteractionListener,
        MenuFormulariosView.OnFragmentInteractionListener,
        CatalogoView.OnFragmentInteractionListener,
        MenuConfiguracionView.OnFragmentInteractionListener,
        BuscarClienteView.OnFragmentInteractionListener,
        RutaVendedorNoRutaView.OnFragmentInteractionListener,
        DireccionClienteView.OnFragmentInteractionListener,
        HistoricoOrdenVentaView.OnFragmentInteractionListener,
        PromocionDetalleView.OnFragmentInteractionListener,
        ConsStockView.OnFragmentInteractionListener,
        PromocionCabeceraEditarDescuentoView.OnFragmentInteractionListener,
        MenuConsultaPedidosView.OnFragmentInteractionListener,
        MenuConsultasFacturasView.OnFragmentInteractionListener,
        HistoricContainerSalesFamilyView.OnFragmentInteractionListener,
        MenuConsultaCobradoView.OnFragmentInteractionListener,
        QuotasPerCustomerView.OnFragmentInteractionListener,
        ConsultaStockView.OnFragmentInteractionListener,
        HistoricContainerSKU.OnFragmentInteractionListener,
        KardexOfPaymentView.OnFragmentInteractionListener,
        DispatchSheetView.OnFragmentInteractionListener,
        HistoricStatusDispatchView.OnFragmentInteractionListener,
        HistoricSalesOrderTraceabilityView.OnFragmentInteractionListener
{
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    Fragment contentFragment,contentHistoryFragment;
    ClienteCabeceraView clienteCabeceraView;
    ClienteDetalleView consultarCobranzaView;
    ParametrosView parametrosView;
    CobranzaCabeceraView cobranzaCabeceraView;
    MenuItem view;
    NavigationView navigationView;
    Fragment CobranzaCabeceraFragment;
    Fragment ConsDepositoFragment;
    Fragment ConsDepositoFragment2;
    Fragment ClienteCabeceraView;
    Fragment RutaVendedorView;
    Fragment HistoricoDepositoFragment;
    Fragment HistoricoCobranzaFragment;
    Fragment MenuAccionViewFragment;
    Fragment OrdenVentaCabeceraFragment;
    Fragment OrdenVentaDetalleFragment;
    Fragment ListadoPromocionFragment;
    Fragment ConsClienteFragment;
    Fragment DireccionClienteFragment;
    Fragment historicoOrdenVentaFragment;
    Fragment PromocionCabeceraFragment;
    Fragment PromocionDetalleFragment;
    Fragment MenuConsultasPedidosFragment;
    Fragment HistoricContainerSaleFragment;
    Fragment BuscarClienteFragment;
    Fragment QuotasPerCustomerFragment;
    Fragment ConsultaStockFragment;
    Fragment MenuConsultaCobranzaFragment;
    Fragment KardexOfPaymentFragment;
    Fragment HojaDespachoView;
    Fragment HojaDespachoFragment;
    static QuotasPerCustomerHeadRepository quotasPerCustomerRepository;
    private static int TAKE_PICTURE = 1888;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    TextView tv_fuerzatrabajo_id_navheader,tv_nombrefuerzatrabajo_navheader,tv_correlativo_navheader,tv_conexion_navheader,tv_zona;
    FragmentManager fragmentManager;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionEntity;
    ArrayList<ConfiguracionSQLEntity> arraylistConfiguracionentity;
    String correlativo="";
    ConfiguracionSQLiteDao configuracionSQLiteDao;
    BluetoothAdapter bluetoothAdapter;
    public static Context context;
    ImageView imv_compania_menu;
    public static  String indicador="0";
    UsuarioSQLite usuarioSQLite;
    String Conexion="";
    private final int MY_PERMISSIONS_REQUEST_CAMERA=1;
    private String NameOfFolder = "/RECIBOS";
    private String NameOfFile = "imagen";
    public static String mCurrentPhotoPath="";
    private ValueCallback<Uri> uploadMessage;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDao;

    /*VARIABLES TEMPORALESS*/
    final int COD_FOTO=20;
    private TextView textViewStatus;
    String path;
    private CobranzaRepository cobranzaRepository;

    private  String recibovalidado = "";
    private ConnectivityManager manager;
    private static BixolonPrinterController bxlPrinter = null;
    QuotasPerCustomerDetailRepository quotasPerCustomerDetailRepository;
    HistoricContainerSalesRepository historicContainerSalesRepository;
    TableRow tablerowzona;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        manager = (ConnectivityManager) getApplication().getSystemService(getApplication().CONNECTIVITY_SERVICE);
        super.onCreate(savedInstanceState);
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        setContentView(R.layout.activity_menu_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        date = new Date();
        fecha =dateFormat.format(date);

        view= (MenuItem) findViewById(R.id.nav_cobranzas);
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(this);
        CobranzaCabeceraFragment= new Fragment();
        ConsDepositoFragment=new Fragment();
        ConsDepositoFragment2=new Fragment();
        ClienteCabeceraView= new Fragment();
        RutaVendedorView = new Fragment();
        MenuAccionViewFragment = new Fragment();
        HistoricoCobranzaFragment = new Fragment();
        HistoricoDepositoFragment = new Fragment();
        OrdenVentaCabeceraFragment = new Fragment();
        DireccionClienteFragment = new Fragment();
        ConsClienteFragment =new Fragment();
        historicoOrdenVentaFragment = new Fragment();
        MenuConsultasPedidosFragment = new Fragment();
        HistoricContainerSaleFragment = new Fragment();
        BuscarClienteFragment = new Fragment();
        QuotasPerCustomerFragment = new Fragment();
        ConsultaStockFragment= new Fragment();
        MenuConsultaCobranzaFragment = new Fragment();
        KardexOfPaymentFragment = new Fragment();
        HojaDespachoView = new Fragment();
        HojaDespachoFragment = new Fragment();

        arraylistConfiguracionentity= new ArrayList<ConfiguracionSQLEntity>();
        configuracionSQLiteDao =  new ConfiguracionSQLiteDao(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        context=this;
        usuarioSQLite = new UsuarioSQLite(this);
        quotasPerCustomerRepository = new ViewModelProvider(this).get(QuotasPerCustomerHeadRepository.class);
        quotasPerCustomerDetailRepository = new ViewModelProvider(this).get(QuotasPerCustomerDetailRepository.class);
        cobranzaRepository = new ViewModelProvider(this).get(CobranzaRepository.class);
        cobranzaRepository.PendingCollectionQR(this).observe(MenuView.this, data -> {
            Log.e("Jepicame","=>"+data);
        });
        historicContainerSalesRepository = new ViewModelProvider(this).get(HistoricContainerSalesRepository.class);
        Conexion=usuarioSQLite.ObtenerUsuarioSesion().getOnline();
        rutaFuerzaTrabajoSQLiteDao=new RutaFuerzaTrabajoSQLiteDao(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        clienteCabeceraView = new ClienteCabeceraView();
        consultarCobranzaView= new ClienteDetalleView();
        parametrosView = new ParametrosView();

        cobranzaCabeceraView = new CobranzaCabeceraView();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        bxlPrinter = new BixolonPrinterController(this);
        tv_fuerzatrabajo_id_navheader=(TextView)
                navigationView.getHeaderView(0).findViewById(R.id.tv_fuerzatrabajo_id_navheader);
        //findViewById( R.id.tv_fuerzatrabajo_id_navheader);
        tv_nombrefuerzatrabajo_navheader=(TextView)
                navigationView.getHeaderView(0).findViewById(R.id.tv_nombrefuerzatrabajo_navheader);

        tv_zona=(TextView)navigationView.getHeaderView(0).findViewById(R.id.tv_zona);
        tv_zona.setText(rutaFuerzaTrabajoSQLiteDao.ObtenerZonaRutaFuerzaTrabajo(fecha));
        //tv_conexion_navheader=(TextView)
        //        navigationView.getHeaderView(0).findViewById(R.id.tv_Conexion_navheader);
        imv_compania_menu=(ImageView)
                navigationView.getHeaderView(0).findViewById(R.id.imv_compania_menu);
        textViewStatus=navigationView.getHeaderView(0).findViewById(R.id.textViewStatus);
        listaConfiguracionEntity= new ArrayList<ConfiguracionSQLEntity>();
        listaConfiguracionEntity=configuracionSQLiteDao.ObtenerCorrelativoConfiguracion();
        tablerowzona= navigationView.getHeaderView(0).findViewById(R.id.tablerowzona);




        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
        {
            tv_zona.setVisibility(View.GONE);
            tablerowzona.setVisibility(View.GONE);
        }
        if(listaConfiguracionEntity.isEmpty())
        {
            configuracionSQLiteDao.InsertaConfiguracion(
                    "80mm",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0"
            );
        }

        arraylistConfiguracionentity = configuracionSQLiteDao.ObtenerCorrelativoConfiguracion();

        for (int j=0;j<arraylistConfiguracionentity.size();j++)
        {
            correlativo=arraylistConfiguracionentity.get(j).getSecuenciarecibos();
        }

        tv_fuerzatrabajo_id_navheader.setText(String.valueOf(SesionEntity.fuerzatrabajo_id));
        tv_nombrefuerzatrabajo_navheader.setText(String.valueOf(SesionEntity.nombrefuerzadetrabajo));
//      tv_correlativo_navheader.setText(String.valueOf(correlativo));

        if(SesionEntity.compania_id.equals("C011"))
        {
            imv_compania_menu.setImageResource(R.mipmap.logo_bluker_2);
        }
        else if(SesionEntity.compania_id.equals("C013"))
        {
            imv_compania_menu.setImageResource(R.mipmap.rofalab304x90_2);
        }


        if(SesionEntity.Print.equals("Y"))
        {
            SesionEntity.formhabilPrint=("Y");
        }



        //Manejo de Perfiles
        //Modificado rotarola 29-10-2021 12:39 Optimizacion de codigo de Perfiles
                /*if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
        {
            onNavigationItemSelected(navigationView.getMenu().getItem(1).setVisible(false));
            onNavigationItemSelected(navigationView.getMenu().getItem(3).setVisible(false));
            onNavigationItemSelected(navigationView.getMenu().getItem(5).setVisible(false));
            onNavigationItemSelected(navigationView.getMenu().getItem(6).setVisible(false));

        }
        else if(SesionEntity.perfil_id.equals("Vendedor")||SesionEntity.perfil_id.equals("VENDEDOR") || SesionEntity.perfil_id.equals("SALES PERSON"))
        {
            onNavigationItemSelected(navigationView.getMenu().getItem(0).setVisible(false));
            onNavigationItemSelected(navigationView.getMenu().getItem(6).setVisible(false));
            onNavigationItemSelected(navigationView.getMenu().getItem(7).setVisible(false));
        }*/
        switch (SesionEntity.perfil_id){
            case "CHOFER":
            case "Chofer":
                onNavigationItemSelected(navigationView.getMenu().getItem(1).setVisible(false));
                onNavigationItemSelected(navigationView.getMenu().getItem(4).setVisible(false));
                onNavigationItemSelected(navigationView.getMenu().getItem(5).setVisible(false));
                onNavigationItemSelected(navigationView.getMenu().getItem(6).setVisible(false));
                break;
            case "Vendedor":
            case "VENDEDOR":
            case "Supervisor":
            case "SUPERVISOR":
            case "SALES PERSON":
                onNavigationItemSelected(navigationView.getMenu().getItem(0).setVisible(false));
                onNavigationItemSelected(navigationView.getMenu().getItem(6).setVisible(false));
                onNavigationItemSelected(navigationView.getMenu().getItem(7).setVisible(false));
                break;
        }
        ////////////////////////////////////////////////////////////////////////////////////////////



        switch (BuildConfig.FLAVOR){
            case "bolivia":
            case "ecuador":
            case "chile":
                navigationView.getMenu().findItem(R.id.nav_hoja_despacho).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_ruta_vendedor).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_cobranzas).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_consultas).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_comisiones).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_formularios).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_dinero_cobrado).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_asistencia_chofer).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_configuracion_general).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_salir).setEnabled(true);
                break;
            case "india":
                navigationView.getMenu().findItem(R.id.nav_hoja_despacho).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_ruta_vendedor).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_cobranzas).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_consultas).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_comisiones).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_formularios).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_dinero_cobrado).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_asistencia_chofer).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_configuracion_general).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_salir).setEnabled(true);
                break;
            case "peru":
                navigationView.getMenu().findItem(R.id.nav_hoja_despacho).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_ruta_vendedor).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_cobranzas).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_consultas).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_comisiones).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_formularios).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_dinero_cobrado).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_asistencia_chofer).setEnabled(false);
                navigationView.getMenu().findItem(R.id.nav_configuracion_general).setEnabled(true);
                navigationView.getMenu().findItem(R.id.nav_salir).setEnabled(true);
                break;
            default:
                break;
        }

        //Envia a Parametros

        //String Fragment="MenuFormulariosView";
        //String accion="agregarcliente";

        String Fragment="ParametrosView";
        String accion="Todos";

        if(BuildConfig.FLAVOR.equals("india")){
            Fragment="MenuFormulariosView";
            accion="agregarcliente";
       }

        String compuesto=Fragment+"-"+accion;
        onFragmentInteraction(compuesto,"");
    }

    @Override
    public void onBackPressed() {

        Log.e("jpcm","se esta intentando retroceder "+ListenerBackPress.getCurrentFragment()+" "+ListenerBackPress.getTemporaIdentityFragment());

        if(ListenerBackPress.getCurrentFragment()!=null){
            switch (ListenerBackPress.getCurrentFragment()){
                case "HistoricoFacturasView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("onAttach")) {
                        super.onBackPressed();
                        this.setTitle("Menu Consultas");
                    }
                    break;

                case "MenuConsultasView":
                    //fragmento base
                    break;
                case "ComisionesView":
                    //fragmento base
                    break;
                case "Webview":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("onDetach")){
                        //super.onBackPressed();
                        //fragmento base
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("onAttach")){
                        super.onBackPressed();
                    }
                    break;
                case "FormListClienteDetalleRutaVendedor":

                    super.onBackPressed();
                    /*
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("pagoDirecto")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(3).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("cliente")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));
                    }else{
                        onNavigationItemSelected(navigationView.getMenu().getItem(2).setChecked(true));
                    }*/

                    break;
                case "FormaAsociatyListCobranzaDeposito":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("Deposito")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(5).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("HistoricoDepositoViewCobranzasDetails")){
                        //fragmento base de consultas
                        super.onBackPressed();
                        //onNavigationItemSelected(navigationView.getMenu().getItem(3).setChecked(true));
                    }else{
                        onNavigationItemSelected(navigationView.getMenu().getItem(3).setChecked(true));
                    }
                    break;
                case "FormCobranzaDetalleFromHistoryCobranza":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("HistoricoDepositoViewCobranzasDetails")){
                        super.onBackPressed();
                    }else{
                        onNavigationItemSelected(navigationView.getMenu().getItem(5).setChecked(true));
                    }
                    break;
                case "FormListaDeudaCliente":
                case "MenuAccionView":
                    //no necesita ir mas atras ya que es un fragmento base
                    if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
                    {
                        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));
                    }else
                        {
                            onNavigationItemSelected(navigationView.getMenu().getItem(1).setChecked(true));
                        }

                    /*
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("rutaVendedor")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(2).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("pagoDirecto")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(3).setChecked(true));
                    }else{
                        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));
                    }*/

                    break;
                case "FormDetalleCobranzaCliente":
                    Log.e("jpcm","se regreso al tab layout");
                    super.onBackPressed();
                    break;
                case "catalogoView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("onAttach")) {
                        super.onBackPressed();
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("onDetach")){
                        //fragmento base
                    }
                    break;
                case "ConfigSistemaView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("menuConfig")){
                        // fragmento base

                        super.onBackPressed();
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("configuracion")){
                        super.onBackPressed();
                    }
                    //onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "MenuConfiguracionView":
                    // onNavigationItemSelected(navigationView.getMenu().getItem(8).setChecked(true));
                    break;
                case "ConfigImpresoraView":

                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("fragmentoConfigImpresora")) {
                        //getFragmentManager().popBackStack();
                        super.onBackPressed();
                        //onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null  && ListenerBackPress.getTemporaIdentityFragment().equals("menuConfig")){
                        //es fragmento base
                        //ListenerBackPress.setCurrentFragment(null);
                        //getFragmentManager().popBackStack();
                        // onNavigationItemSelected(navigationView.getMenu().getItem(8).setChecked(true));
                    }

                    // getFragmentManager().popBackStack();
                    //  contentFragment=new ConfigImpresoraView();
                    //super.onBackPressed();
                    /// Log.e("jpcm","ConfigImpresoraView IMPRESORA "+ListenerBackPress.getTemporaIdentityFragment());
                    //super.onBackPressed();
                    //  onNavigationItemSelected(navigationView.getMenu().getItem(6).setChecked(true));
                    break;
                case "RutaVendedorView":
                    //Como es un fragmento base no hay necesidad de ir más atras
                    //onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "HistoricoDepositoView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("onAttach")){
                        super.onBackPressed();
                    }
                    break;
                case "HistoricoCobranzaView":
                    Log.e("jpcm","historico cobrnaza");
                    onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "CLIENTE ASDASD":
                    Log.e("jpcm","cliente  XDDD");
                    onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "CLIENTEDEUDA":
                    Log.e("jpcm","cliente  deuda");
                    onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "CLIENTESINDEUDA":
                    Log.e("jpcm","cliente  sin deuda");
                    onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "DepositoViewXd":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("Deposito")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(2).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("redirectToDepositoItem1")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(1).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("temporaAddCorbaznaToListDeposito")){
                        super.onBackPressed();
                    }
                    break;
                case "PagoDirecto":
                    Log.e("jpcm","pago directoooo");
                    onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    break;
                case "CObranzaDetalleView":
                    Log.e("jpcm","cobranza detalle view");
                    break;
                case "ConsultaCobranzaParaDepositarView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("Deposito")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(7).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("redirectToDepositoItem1")){
                        onNavigationItemSelected(navigationView.getMenu().getItem(2).setChecked(true));
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("temporaAddCorbaznaToListDeposito")){
                        super.onBackPressed();
                    }else{
                        super.onBackPressed();
                    }
                    break;
               /* case "FormParametrosView":
                    onNavigationItemSelected(navigationView.getMenu().getItem(9).setChecked(true));
                    break;
                //super.onBackPressed();*/
                case "OrdenVentaCabeceraView":
                    super.onBackPressed();
                    break;
                case "OrdenVentaDetalleView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("NoTieneLineas")){
                        super.onBackPressed();
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("TieneLineas")){
                        Toast.makeText(getApplicationContext(), "No es posible retroceder por que tiene productos agregados", Toast.LENGTH_LONG).show();
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("cestoCompra")){
                        //Toast.makeText(context, "segura que desea salir y eliminar los productos agregados, mostrar dialog", Toast.LENGTH_SHORT).show();
                        //super.onBackPressed();
                    }else if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("rutaVendedor")){
                        super.onBackPressed(); //carro de compras vacio y puede retroceder fresh
                    }
                    break;
                case "ProductoView":
                    if(ListenerBackPress.getTemporaIdentityFragment()!=null && ListenerBackPress.getTemporaIdentityFragment().equals("TieneLineas")) {
                        Toast.makeText(getApplicationContext(), "No es posible retroceder por que tiene productos agregados", Toast.LENGTH_LONG).show();
                    }else{
                        super.onBackPressed();
                    }
                    break;
                //super.onBackPressed();
                /*default:
                    ListenerBackPress.setCurrentFragment(null);
                    getFragmentManager().popBackStack();
                    break;*/
            }


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();
        contentFragment=null;
        boolean fragmentSeleccionado=false;
        String TAG_FRAGMENT=null;
        String fragment=null;
        String accion=null;
        String compuesto=null;
        Object object=null;
        String validarblockpay=null;
        int validar=0;
        UsuarioSQLite usuarioSQLite =null;
        ArrayList<UsuarioSQLiteEntity> listausuariosqliteentity=null;

        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        switch(id){
            /*case R.id.nav_clientes:
                item.setEnabled(false);

                if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    AsyncTask<Void, Void, Void> retorno=new Preguntar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                }
                // Handle the camera action
                validarblockpay="";
                validar=0;
                dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                date = new Date();
                fecha =dateFormat.format(date);
                //fecha="2019-08-04";
                usuarioSQLiteDao=new UsuarioSQLiteDao(this);
                listausuariosqliteentity = new ArrayList<>();
                listausuariosqliteentity=usuarioSQLiteDao.ObtenerUsuarioBlockPay();

                for(int i=0;i<listausuariosqliteentity.size();i++)
                {
                    validarblockpay=listausuariosqliteentity.get(i).getChkbloqueopago();
                }
                if(validarblockpay==null)
                {
                    validarblockpay="0";
                }

                validar=cobranzaDetalleSQLiteDao.VerificaRecibosPendientesDeposito(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,fecha);

                if(validar>0)
                {
                   alertarecibospendientes().show();
                }

                else
                {
                    SesionEntity.pagodirecto="0";
                    contentFragment=new ClienteCabeceraView();
                    fragment="ClienteCabeceraView";
                    accion="nuevoinicioClienteCabeceraView";
                    compuesto=fragment+"-"+accion;
                    ListenerBackPress.setTemporaIdentityFragment("cliente");
                    onFragmentInteraction(compuesto,object);
                }

                break;*/
            case R.id.nav_hoja_despacho:
                //contentFragment=new DispatchSheetView();
                //fragmentSeleccionado=true;
                //TAG_FRAGMENT="config_print";

                HojaDespachoFragment = new DispatchSheetView();
                fragment="HojaDespachoView";
                accion="inicio";
                compuesto=fragment+"-"+accion;
                object=null;
                onFragmentInteraction(compuesto,object);
                break;
            case R.id.nav_cobranzas:
                //contentFragment=new CobranzaCabeceraView();
                //fragmentSeleccionado=true;
                CobranzaCabeceraFragment = new CobranzaCabeceraView();
                fragment="CobranzaCabeceraView";
                accion="inicio";
                compuesto=fragment+"-"+accion;
                object=null;
                onFragmentInteraction(compuesto,object);


                /*if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    AsyncTask<Void, Void, Void> retorno=new Preguntar().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
                }*/
                break;
            case R.id.nav_ruta_vendedor:

                SesionEntity.pagodirecto="0";
                contentFragment = new RutaVendedorView();
                fragment = "RutaVendedorView";
                accion = "nuevoinicioRutaVendedorView";
                compuesto = fragment + "-" + accion;
                object = null;
                onFragmentInteraction(compuesto, object);
                Log.e("jpcm","Entramos aqui");

                break;
            case R.id.nav_consultas:
                contentFragment=new MenuConsultasView();
                fragmentSeleccionado=true;
                TAG_FRAGMENT="config_print";
                break;
            case R.id.nav_comisiones:
                contentFragment=new ContenedorComisionesView();
                fragmentSeleccionado=true;
                TAG_FRAGMENT="config_print";
                break;
            case R.id.nav_formularios:
                contentFragment=new MenuFormulariosView();
                fragmentSeleccionado=true;
                TAG_FRAGMENT="config_print";
                break;
            case R.id.nav_configuracion_general:
                contentFragment=new MenuConfiguracionView();
                fragmentSeleccionado=true;
                TAG_FRAGMENT="config_print";
                break;
            case R.id.nav_dinero_cobrado:
                //contentFragment=new HojaDespachoView();
                break;
            case R.id.nav_asistencia_chofer:
                //contentFragment=new HojaDespachoView();
                break;
            default:
                createSimpleDialog().show();
                break;
        }



        if (fragmentSeleccionado==true){
            getSupportFragmentManager().beginTransaction().replace(R.id.content_menu_view,contentFragment,contentFragment.getClass().getName()).commit();
        }

        DrawerLayout drawer =findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        item.setEnabled(true);

        return true;


    }

    public Dialog createSimpleDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_alert_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(getResources().getString(R.string.dialog_advertencia));

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText(getResources().getString(R.string.dialog_text));

        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);


        dialogButton.setText(getResources().getString(R.string.dialog_btn_salir));
        dialogButtonExit.setText(getResources().getString(R.string.dialog_btn_cancelar));

        dialogButton.setOnClickListener(v -> {
            dialog.dismiss();
            if (bluetoothAdapter.isEnabled()) {
                bluetoothAdapter.disable();
            } else {
                bluetoothAdapter.enable();
            }

            System.exit(0);
        });

        dialogButtonExit.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
}

    @Override
    public void onFragmentInteraction(String Tag, Object Lista) {

        String tag="",tag2="";

        String[] separada = Tag.split("-");
        if(separada.length>1)
        {
            tag=separada[0];
            tag2=separada[1];
        }else{
                tag=separada[0];
            }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        contentFragment=null;

        if(tag.equals("ClienteCabeceraView")){

            if(tag2.equals("nuevoinicioClienteCabeceraView"))
            {
                contentFragment=new ClienteCabeceraView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popx");
                ft.commit();
            }
            if(tag2.equals("inicioClienteCabeceraView"))
            {
                String tagClienteCabeceraView="nuevoinicioClienteCabeceraView";
                ClienteCabeceraView = getSupportFragmentManager().findFragmentByTag(tagClienteCabeceraView);
                ft.remove(ClienteCabeceraView);

                Log.e("JEPICAMEE","=> tag 2 es "+tagClienteCabeceraView);
                ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }

        }

        if(tag.equals("RutaVendedorView"))
        {
            if(tag2.equals("nuevoinicioRutaVendedorView"))
            {
                contentFragment=new RutaVendedorView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("pop1");
                ft.commit();
            }
            if(tag2.equals("inicioRutaVendedorView"))
            {
                String tagRutaVendedorView="nuevoinicioRutaVendedorView";
                RutaVendedorView = getSupportFragmentManager().findFragmentByTag(tagRutaVendedorView);
                ft.remove(RutaVendedorView);

                Log.e("JEPICAMEE","=> tag 2 es "+tagRutaVendedorView);

                ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("2pop");

                ft.commit();
            }

        }
        else if(tag.equals("CobranzaCabeceraView")){

            //ListenerBackPress.setTemporaIdentityFragment("Deposito");
                if(tag2.equals("nuevoinicio"))
                {
                    Log.e("jpcm","nuevo inicio====");
                    String taginicio="inicio";
                    onNavigationItemSelected(navigationView.getMenu().getItem(2).setChecked(true));
                }
                else if(tag2.equals("inicio"))
                {
                    Log.e("jpcm","inicio====");
                    ListenerBackPress.setTemporaIdentityFragment("Deposito");
                    String taginicio=tag2;
                    ft.replace(R.id.content_menu_view,CobranzaCabeceraFragment,taginicio);
                    ft.addToBackStack("popqqqqqq");
                    ft.commit();
                }
                else if (tag2.equals("nuevalista"))
                    {
                        Log.e("jpcm","nueva lista open");
                        String tagnuevalista=tag2;
                        ft.hide(CobranzaCabeceraFragment);
                        ft.add(R.id.content_menu_view,ConsDepositoView.newInstanciada(tag2),tagnuevalista);
                        ft.addToBackStack("popssss");
                    ft.commit();
                    }
                else if (tag2.equals("agregarlista"))
                {
                    Log.e("jpcm","agregar lista open");
                    String tagagregarlista="";
                    tagagregarlista=tag2;
                    ft.hide(CobranzaCabeceraFragment);
                    ft.add(R.id.content_menu_view,ConsDepositoView.newInstance(Lista,tagagregarlista),tagagregarlista);
                    ft.addToBackStack("popaaa");
                    ft.commit();
                }
            }

        else if(tag.equals("ClienteDetalleView"))
        {
            Log.e("jpcm","se intenta regresar 434444444444");
            if(tag2.equals("inicio"))
            {
               /////////////////////////////////////////////////////*/*/
                contentFragment=new CobranzaDetalleView();
                String tagClienteDetalleView="inicioClienteCabeceraView";
                Fragment clienteDetalleView;
                clienteDetalleView = getSupportFragmentManager().findFragmentByTag(tagClienteDetalleView);
                ft.remove(clienteDetalleView);
                ft.add(R.id.content_menu_view,CobranzaDetalleView.newInstance(Lista),tag2);
                ft.addToBackStack("popX");
                ft.commit();
            }
            if(tag2.equals("recuperar"))
            {

                contentFragment=new CobranzaDetalleView();
                String tagCobranzaDetalleView="COBRANZA";
                Fragment cobranzaDetalleView;
                cobranzaDetalleView = getSupportFragmentManager().findFragmentByTag("popX");
                ft.replace(R.id.content_menu_view,CobranzaDetalleView.newInstancia(Lista),tag2);
                ft.addToBackStack("popssdddd");
                ft.commit();
            }
            if(tag2.equals("regresar"))
            {
                contentFragment=new ClienteCabeceraView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("pop23344");
                ft.commit();
            }
            if(tag2.equals("editarcliente"))
            {
                String tagClienteDetalleView="inicioClienteCabeceraView";
                Fragment clienteDetalleView;
                clienteDetalleView = getSupportFragmentManager().findFragmentByTag(tagClienteDetalleView);
                ft.remove(clienteDetalleView);
                ft.add(R.id.content_menu_view,MantenimientoClienteView.newInstance(Lista),tag2);
                ft.addToBackStack("kkkiool");
                ft.commit();
            }
        }

        else if(tag.equals("ConsDepositoView"))
        {
            contentFragment=new CobranzaCabeceraView();

            if(tag2.equals("nuevalista"))
            {
                ConsDepositoFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                ft.remove(ConsDepositoFragment);

                ft.show(CobranzaCabeceraFragment);

                CobranzaCabeceraView.newInstancia(Lista);
                ft.addToBackStack("popiuytu");
                ft.commit();

            }
            else if(tag2.equals("agregarlista"))
            {
                ConsDepositoFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                ft.remove(ConsDepositoFragment);
                ft.show(CobranzaCabeceraFragment);
                ft.addToBackStack("pop");
                ft.commit();
                CobranzaCabeceraView.newInstanciaAdd(Lista);
            }

            else if(tag.equals("MenuView"))
            {

                if(tag2.equals("Todos"))
                {
                    String taginicio="Todos";
                    contentFragment=new ParametrosView();

                    ft.replace(R.id.content_menu_view,contentFragment,taginicio);
                    ParametrosView.newInstance(taginicio);
                    ft.addToBackStack("popuuuploo");
                    ft.commit();
                }
            }

        }
        else if(tag.equals("HistoricoDepositoView")){

            Log.e("jpvm","ingreso al interation brother");
            if(tag2.equals("nuevoinicioHistoricoDepositoView"))
            {

                contentFragment=new HistoricoDepositoView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popllloppp");
                ft.commit();

                Log.e("jpcm","------"+contentFragment);
            }
            if(tag2.equals("Deposito"))
            {
                Log.e("jpcm","el tag es Deposito");
                String accion="nuevoinicioHistoricoDepositoView";
                HistoricoCobranzaView historicoCobranzaView =  new HistoricoCobranzaView();
                HistoricoDepositoFragment = getSupportFragmentManager().findFragmentByTag(accion);
                ft.hide(HistoricoDepositoFragment);
                ft.add(R.id.content_menu_view,historicoCobranzaView.newInstance(Lista,tag2));
                ft.addToBackStack("popdhgjuy");
                ft.commit();
            }

        }
        else if(tag.equals("HistoricoCobranzaView")){
            if(tag2.equals("COBRANZA"))
            {
                contentFragment=new HistoricoCobranzaView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            else if(tag2.equals("MENUCOBRANZA"))
            {
                contentFragment=new MenuConsultaCobradoView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
        }else if(tag.equals("MantenimientoClienteView")){
                tag2="MantenimientoClienteView";
                contentFragment=new MapaClienteView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
            ft.addToBackStack("popcccceeer");
            ft.commit();

        }
        if(tag.equals("MenuAccionView")){

            if(tag2.equals("pedido"))
            {

                String tagRutaVendedorView="inicioRutaVendedorView";
                MenuAccionViewFragment = getSupportFragmentManager().findFragmentByTag(tagRutaVendedorView);
                ft.remove(MenuAccionViewFragment);
                ft.add(R.id.content_menu_view, OrdenVentaCabeceraView.newInstance(Lista),tag2);

                ft.addToBackStack("po1p");
                ft.commit();
            }
            if(tag2.equals("cobranza"))
            {
                //String tagClienteCabeceraView="inicioClienteCabeceraView";
                String tagRutaVendedorView="inicioRutaVendedorView";
                MenuAccionViewFragment = getSupportFragmentManager().findFragmentByTag(tagRutaVendedorView);
                ft.remove(MenuAccionViewFragment);
                tag2="inicioClienteCabeceraView";
                ft.add(R.id.content_menu_view,ClienteDetalleView.newInstance(Lista),tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
            if(tag2.equals("lead"))
            {
                //String tagClienteCabeceraView="inicioClienteCabeceraView";
                /*String tagRutaVendedorView="inicioRutaVendedorView";
                MenuAccionViewFragment = getSupportFragmentManager().findFragmentByTag(tagRutaVendedorView);
                ft.remove(MenuAccionViewFragment);
                tag2="inicioClienteCabeceraView";
                ft.add(R.id.content_menu_view,ClienteDetalleView.newInstance(Lista),tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();*/
                contentFragment=new LeadClientesView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();

            }

        }

        if(tag.equals("OrdenVentaCabeceraView")){

            if(tag2.equals("detalle")){

                String tagOrdenVentaCabeceraView="pedido";
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaCabeceraView);
                ft.hide(OrdenVentaCabeceraFragment);

                ft.add(R.id.content_menu_view, OrdenVentaDetalleView.newInstance(Lista),tag2);

                ft.addToBackStack("po1p");
                ft.commit();

            }

            if(tag2.equals("terminopago")){
                String tagOrdenVentaCabeceraView="pedido";
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaCabeceraView);
                ft.hide(OrdenVentaCabeceraFragment);
                ft.add(R.id.content_menu_view, TerminoPagoView.newInstance(Lista),tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
            if(tag2.equals("agencia"))
            {
                String tagOrdenVentaCabeceraView="pedido";
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaCabeceraView);
                ft.hide(OrdenVentaCabeceraFragment);
                ft.add(R.id.content_menu_view, AgenciaView.newInstance(Lista),tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
            if(tag2.equals("direccion"))
            {
                String tagOrdenVentaCabeceraView="pedido";
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaCabeceraView);
                ft.hide(OrdenVentaCabeceraFragment);
                ft.add(R.id.content_menu_view, DireccionClienteView.newInstance(Lista),tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
        }

        else if(tag.equals("TerminoPagoView"))
        {
            if(tag2.equals("inicio"))
            {
                tag2="pedido";
                String tag3="terminopago";
                Fragment TerminoPagoViewFragment=new Fragment();
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                TerminoPagoViewFragment=getSupportFragmentManager().findFragmentByTag(tag3);
                ft.remove(TerminoPagoViewFragment);
                ft.show(OrdenVentaCabeceraFragment);
                ft.addToBackStack("pop");
                ft.commit();
                OrdenVentaCabeceraView.newInstanciaTerminoPago(Lista);
            }
        }

        else if(tag.equals("AgenciaView"))
        {
            if(tag2.equals("inicio"))
            {
                tag2="pedido";
                String tag3="agencia";
                Fragment AgenciaViewFragment=new Fragment();
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                AgenciaViewFragment=getSupportFragmentManager().findFragmentByTag(tag3);
                ft.remove(AgenciaViewFragment);
                ft.show(OrdenVentaCabeceraFragment);
                ft.addToBackStack("pop");
                ft.commit();

                OrdenVentaCabeceraView.newInstanciaAgencia(Lista);
            }
        }

        if(tag.equals("OrdenVentaDetalleView")){
            String tagOrdenVentaDetalleView="detalle";
            switch(tag2){
                case "producto":
                    OrdenVentaDetalleFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaDetalleView);
                    ft.hide(OrdenVentaDetalleFragment);
                    ft.add(R.id.content_menu_view, ProductoView.newInstance(Lista),tag2);
                    ft.addToBackStack("po1p");
                    ft.commit();
                    break;
                case "listadopromocion":
                    OrdenVentaDetalleFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaDetalleView);
                    ft.hide(OrdenVentaDetalleFragment);
                    ft.add(R.id.content_menu_view, ListadoPromocionView.newInstance(Lista),tag2);
                    //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                    ft.addToBackStack("po1p");
                    ft.commit();
                    break;
                case "ordenventacabecera":
                    tag2="pedido";

                    OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tag2);

                    OrdenVentaDetalleFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaDetalleView);
                    ft.hide(OrdenVentaDetalleFragment);

                    ft.show(OrdenVentaCabeceraFragment);
                    ft.addToBackStack("po1p");
                    ft.commit();

                    OrdenVentaCabeceraView.newInstanciaAgregarOrdenVentaDetalle(Lista);
                    break;
            }
        }

        else if(tag.equals("ProductoView"))
        {
            if(tag2.equals("inicio"))
            {
                tag2="detalle";
                String tag3="producto";
                Fragment ProductoViewFragment=new Fragment();
                OrdenVentaDetalleFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                ProductoViewFragment=getSupportFragmentManager().findFragmentByTag(tag3);

                ft.remove(ProductoViewFragment);
                ft.show(OrdenVentaDetalleFragment);
               // ft.addToBackStack("pop");
                ft.commit();
                OrdenVentaDetalleView.newInstanceAgregarProducto(Lista);
            }
        }
        if(tag.equals("ListadoPromocionView")){
            if(tag2.equals("promociondetalle"))
            {
                String tagListadoPromocionView="listadopromocion";
                ListadoPromocionFragment = getSupportFragmentManager().findFragmentByTag(tagListadoPromocionView);
                ft.hide(ListadoPromocionFragment);
                ft.add(R.id.content_menu_view, PromocionCabeceraView.newInstance(Lista),tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
        }
        if(tag.equals("PromocionCabeceraView")){
            if(tag2.equals("listapromocioncabecera"))
            {
                String tagOrdenVentaDetalleView="detalle";
                String tagPromocionCabeceraView="promociondetalle";

                Fragment PromocionCabeceraView=new Fragment();
                OrdenVentaDetalleFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaDetalleView);
                PromocionCabeceraView=getSupportFragmentManager().findFragmentByTag(tagPromocionCabeceraView);
                ft.remove(PromocionCabeceraView);
                ft.show(OrdenVentaDetalleFragment);
                ft.addToBackStack("pop");
                ft.commit();

                OrdenVentaDetalleView.newInstanceAgregarListaPromocionCabecera(Lista);
            }
            if(tag2.equals("editar_detalle"))
            {
                String tagPromocionCabeceraView="promociondetalle";
                String tagPromocionDetalleView="promociondetalleeditar";
                PromocionCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagPromocionCabeceraView);
                //PromocionDetalleView=getSupportFragmentManager().findFragmentByTag(tagPromocionDetalleView);
                ft.hide(PromocionCabeceraFragment);
                ft.add(R.id.content_menu_view, PromocionDetalleView.newInstance(Lista),tagPromocionDetalleView);
                //OrdenVentaDetalleView.newInstanceAgregarListaPromocionCabecera(Lista);
                //ft.show(PromocionDetalleView);
                ft.addToBackStack("pop");
                ft.commit();
                //OrdenVentaDetalleView.newInstanceAgregarListaPromocionCabecera(Lista);
            }
            if(tag2.equals("editar_descuento"))
            {
                String tagPromocionCabeceraView="promociondetalle";
                String tagPromocionDetalleView="editar_descuento";
                PromocionCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagPromocionCabeceraView);
                ft.hide(PromocionCabeceraFragment);
                ft.add(R.id.content_menu_view, PromocionCabeceraEditarDescuentoView.newInstance(Lista),tagPromocionDetalleView);
                ft.addToBackStack("pop");
                ft.commit();
            }
        }
        if(tag.equals("PromocionDetalleView"))
        {

            if(tag2.equals("editar"))
            {
                String tagPromocionCabeceraView="promociondetalle";
                String tagPromocionDetalleView="promociondetalleeditar";
                PromocionCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagPromocionCabeceraView);
                PromocionDetalleFragment = getSupportFragmentManager().findFragmentByTag(tagPromocionDetalleView);
                ft.remove(PromocionDetalleFragment);
                ft.show(PromocionCabeceraFragment);

                PromocionCabeceraView.newInstanceEditarPromocionDetalle(Lista);
                ft.addToBackStack("po1p");
                ft.commit();
            }
        }
        if(tag.equals("MenuConsultasView")){
            if(tag2.equals("historicoordenventa"))
            {
                contentFragment=new HistoricoOrdenVentaView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("menuconsultaspedidoview"))
            {
                contentFragment=new MenuConsultaPedidosView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("historicofacturas"))
            {
                contentFragment=new HistoricoFacturasView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("stock"))
            {
                contentFragment=new ConsStockView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("Consulta_Orden_Venta_Estado"))
            {
                //contentFragment=new HistoricoOrdenVentaEstadoView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("menuconsultasfacturaview"))
            {
                contentFragment=new MenuConsultasFacturasView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("dispatch"))
            {
                contentFragment=new HistoricStatusDispatchView() ;
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
        }

        if(tag.equals("MenuFormulariosView")){
            switch(tag2){
                case "catalogos":
                    contentFragment=new CatalogoView();
                    break;
                case "reclamocliente":
                    contentFragment=new ReclamoClientesView();
                    break;
                case "agregarcliente":
                    contentFragment=new LeadClientesView();
                    break;
            }

            ft.replace(R.id.content_menu_view,contentFragment,tag2);
            ft.addToBackStack("popsssggggersa");
            ft.commit();
        }

        if(tag.equals("MenuConfiguracionView")){
            if(tag2.equals("parametros"))
            {
                contentFragment=new ParametrosView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("impresora"))
            {
                contentFragment=new ConfigImpresoraView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("sistema"))
            {
                contentFragment=new ConfigSistemaView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
        }
        if(tag.equals("ParametrosView"))
        {

            if(tag2.equals("Todos"))
            {
                String taginicio="Todos";
                contentFragment=new ParametrosView();

                ft.replace(R.id.content_menu_view,contentFragment,taginicio);
                ParametrosView.newInstance(taginicio);
                ft.addToBackStack("popuuuploo");
                ft.commit();
            }
        }
        if(tag.equals("ConsClienteView"))
        {
            if(tag2.equals("agregarClienteNoRuta"))
            {
                String tagRutaVendedor="nuevoinicioRutaVendedorView";
                contentFragment=new RutaVendedorNoRutaView();
                ConsClienteFragment=getSupportFragmentManager().findFragmentByTag(tag2);
                RutaVendedorView=getSupportFragmentManager().findFragmentByTag(tagRutaVendedor);
                ft.remove(ConsClienteFragment);
                ft.show(RutaVendedorView);
                RutaVendedorNoRutaView.newInstanceRecibeCliente(Lista);
                ft.addToBackStack("popuuuploo");
                ft.commit();
            }
            else if(tag2.equals("agregarquotaspercustomer"))
            {
                contentFragment=new BuscarClienteView();
                String tagBuscarClienteView="dialogoagregarcliente";
                String tagQuotasPerCustomer="QUOTAS";
                BuscarClienteFragment=getSupportFragmentManager().findFragmentByTag(tagBuscarClienteView);
                ft.hide(BuscarClienteFragment);
                QuotasPerCustomerFragment  =getSupportFragmentManager().findFragmentByTag(tagQuotasPerCustomer);
                //ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.show(QuotasPerCustomerFragment);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
                String nomblecliente="",cliente_id="";
                if(Lista!=null)
                {

                    ArrayList<ListaClienteCabeceraEntity>Listado=(ArrayList<ListaClienteCabeceraEntity>) Lista;
                    for(int i=0;i<Listado.size();i++)
                    {
                        nomblecliente=Listado.get(i).getNombrecliente();
                        cliente_id=Listado.get(i).getCliente_id();
                    }
                    Induvis.setTituloContenedor( nomblecliente,this);

                }
                QuotasPerCustomerView.newInstanceRecibirCliente(Lista);
                quotasPerCustomerRepository.getQuotasPerCustomer(SesionEntity.fuerzatrabajo_id,this,cliente_id).observe(this, data -> {
                    Log.e("Jepicame","=>"+data);
                });
                quotasPerCustomerDetailRepository.getQuotasPerCustomerDetail (SesionEntity.fuerzatrabajo_id,this,cliente_id).observe(this, data -> {
                    Log.e("Jepicame","=>"+data);
                });

            }
            else if(tag2.equals("agregarquotaspercustomerDialog"))
            {
                contentFragment=new BuscarClienteView();
                String tagBuscarClienteView="dialogoagregarclienteMenu";
                String tagQuotasPerCustomer="MENUCOBRANZA";
                BuscarClienteFragment=getSupportFragmentManager().findFragmentByTag(tagBuscarClienteView);
                ft.remove(BuscarClienteFragment);
                MenuConsultaCobranzaFragment  =getSupportFragmentManager().findFragmentByTag(tagQuotasPerCustomer);
                //ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.show(MenuConsultaCobranzaFragment);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
                MenuConsultaCobradoView.newInstanceRecibirCliente(Lista);

            }

        }
        if(tag.equals("RutaVendedorNorutaView"))
        {
            if(tag2.equals("agregarClienteNoRuta"))
            {
                contentFragment=new BuscarClienteView();
                String tagRutaVendedorView="nuevoinicioRutaVendedorView";
                RutaVendedorView=getSupportFragmentManager().findFragmentByTag(tagRutaVendedorView);
                ft.hide(RutaVendedorView);
                ft.add(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
                BuscarClienteView.newInstanceFlujoNoRuta(Lista);

            }
        }
        if(tag.equals("DireccionClienteView"))
        {

            if(tag2.equals("nuevadireccion"))
            {
                String tagOrdenVentaDetalleView="direccion";
                tag2="pedido";
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                DireccionClienteFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaDetalleView);
                ft.hide(DireccionClienteFragment);
                ft.show(OrdenVentaCabeceraFragment);
                ft.addToBackStack("po1p");
                ft.commit();
                OrdenVentaCabeceraView.newInstanciaNuevaDireccion(Lista);
            }
        }
        if(tag.equals("HistoricoOrdenVentaView"))
        {

            if(tag2.equals("ordenventacabecera"))
            {
                tag2="historicoordenventa";
                String tagOrdenVentaCabeceraView="consultaordenventa";
                historicoOrdenVentaFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaCabeceraView);
                ft.hide(historicoOrdenVentaFragment);
                ft.add(R.id.content_menu_view, OrdenVentaCabeceraView.newInstanceHistoricoOrdenVenta(Lista,tag2),tagOrdenVentaCabeceraView);
                ft.addToBackStack("po1p");
                ft.commit();
            }
        }
        if(tag.equals("MenuConsultasPedidosView"))
        {

            if(tag2.equals("iniciar"))
            {
                tag2="historicoordenventa";
                String tagOrdenVentaCabeceraView="consultaordenventa";
                historicoOrdenVentaFragment = getSupportFragmentManager().findFragmentByTag(tag2);
                OrdenVentaCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tagOrdenVentaCabeceraView);
                ft.hide(historicoOrdenVentaFragment);
                ft.add(R.id.content_menu_view, OrdenVentaCabeceraView.newInstanceHistoricoOrdenVenta(Lista,tag2),tagOrdenVentaCabeceraView);
                ft.addToBackStack("po1p");
                ft.commit();
            }
            if(tag2.equals("consulta_stock"))
            {
                    contentFragment=new ConsultaStockView();
                    ft.replace(R.id.content_menu_view,contentFragment,tag2);
                    ft.addToBackStack("popsssggggersa");
                    ft.commit();

            }
            if(tag2.equals("seguimiento"))
            {
                contentFragment=new HistoricSalesOrderTraceabilityView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();

            }
        }
        if(tag.equals("MenuConsultasFacturasView"))
        {
            if(tag2.equals("historiccontainersaleview"))
            {
                contentFragment=new HistoricContainerSaleView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("historiccontainersaleviewSKU"))
            {
                contentFragment=new HistoricContainerSKU();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
        }
        if(tag.equals("HistoricContainerSaleView"))
        {
            if(tag2.equals("dialogoagregarclienteSKU"))
            {
                contentFragment=new BuscarClienteView();
                String tagHistoricContainerSale="historiccontainersaleviewSKU";
                HistoricContainerSaleFragment=getSupportFragmentManager().findFragmentByTag(tagHistoricContainerSale);
                ft.hide(HistoricContainerSaleFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("dialogoagregarcliente"))
            {
                contentFragment=new BuscarClienteView();
                String tagHistoricContainerSale="historiccontainersaleview";
                HistoricContainerSaleFragment=getSupportFragmentManager().findFragmentByTag(tagHistoricContainerSale);
                ft.hide(HistoricContainerSaleFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("recibircliente"))
            {

                contentFragment=new BuscarClienteView();
                String tagBuscarClienteView="dialogoagregarcliente";
                String tagHistoricContainerSale="historiccontainersaleview";
                BuscarClienteFragment=getSupportFragmentManager().findFragmentByTag(tagBuscarClienteView);
                ft.hide(BuscarClienteFragment);
                HistoricContainerSaleFragment=getSupportFragmentManager().findFragmentByTag(tagHistoricContainerSale);
                //ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.show(HistoricContainerSaleFragment);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
                HistoricContainerSaleView.newInstanceRecibirCliente(Lista);
                if(Lista!=null)
                {
                    String nomblecliente="";
                    ArrayList<ListaClienteCabeceraEntity>Listado=(ArrayList<ListaClienteCabeceraEntity>) Lista;
                    for(int i=0;i<Listado.size();i++)
                    {
                        nomblecliente=Listado.get(i).getNombrecliente();
                    }
                    Induvis.setTituloContenedor( nomblecliente,this);

                }
                /*dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                date = new Date();
                fecha =dateFormat.format(date);

                HistoricContainerSaleView.newInstanceRecibirCliente(Lista);
                historicContainerSalesRepository = new ViewModelProvider(this).get(HistoricContainerSalesRepository.class);
                try {
                    historicContainerSalesRepository.getHistoricContainerSales(
                            SesionEntity.imei,
                            this,
                            HistoricContainerSaleView.CardCode,
                            Induvis.changeMonth(fecha,-6),
                            fecha,
                            "FOCOS",
                            "FOCOS"
                    ).observe(this, data -> {
                        HistoricContainerSaleFocoView.newInstanceListarClienteFoco(data);
                        Log.e("REOS","RESPUESTAFOCO"+data);
                    });
                    historicContainerSalesRepository.getHistoricContainerSales(
                            SesionEntity.imei,
                            this,
                            HistoricContainerSaleView.CardCode,
                            Induvis.changeMonth(fecha,-6),
                            fecha,
                            "FAMILIA",
                            "FAMILIA"
                    ).observe(this, data -> {
                        HistoricContainerSalesFamilyView.newInstanceListarClienteFamily(data);
                        Log.e("REOS","RESPUESTAFAMILIA"+data);
                    });
                    historicContainerSalesRepository.getHistoricContainerSales(
                            SesionEntity.imei,
                            this,
                            HistoricContainerSaleView.CardCode,
                            Induvis.changeMonth(fecha,-6),
                            fecha,
                            "SEMAFORO",
                            "SEMAFORO"
                    ).observe(this, data -> {
                        Log.e("REOS","RESPUESTASEMAFORO"+data);
                        HistoricContainerSalesSemaforoView.newInstanceListarClienteSemaforo(data);
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                */
            }
            if(tag2.equals("recibirclienteSKU"))
            {
                contentFragment=new BuscarClienteView();
                String tagBuscarClienteView="dialogoagregarclienteSKU";
                String tagHistoricContainerSale="historiccontainersaleviewSKU";
                BuscarClienteFragment=getSupportFragmentManager().findFragmentByTag(tagBuscarClienteView);
                ft.hide(BuscarClienteFragment);
                HistoricContainerSaleFragment=getSupportFragmentManager().findFragmentByTag(tagHistoricContainerSale);
                //ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.show(HistoricContainerSaleFragment);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
                HistoricContainerSKU.newInstanceRecibirCliente(Lista);
            }
        }

        if(tag.equals("HistoricoMenuCobranzaView"))
        {
            if(tag2.equals("QUOTAS"))
            {
                contentFragment=new QuotasPerCustomerView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
        }
        if(tag.equals("QuotasPerCustomer"))
        {
            if(tag2.equals("dialogoagregarcliente"))
            {
                contentFragment=new BuscarClienteView();
                String tagQUOTAS="QUOTAS";
                QuotasPerCustomerFragment  =getSupportFragmentManager().findFragmentByTag(tagQUOTAS);
                ft.hide(QuotasPerCustomerFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals("dialogoagregarclienteMenu"))
            {
                contentFragment=new BuscarClienteView();
                String tagMenuCobranza="MENUCOBRANZA";
                MenuConsultaCobranzaFragment  =getSupportFragmentManager().findFragmentByTag(tagMenuCobranza);
                ft.hide(MenuConsultaCobranzaFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
        }

        if(tag.equals("ConsultaStockView"))
        {
            if(tag2.equals( "listadopromocion"))
            {
                String tag3="consulta_stock";
                ConsultaStockFragment = getSupportFragmentManager().findFragmentByTag(tag3);
                ft.hide(ConsultaStockFragment);
                ft.add(R.id.content_menu_view, ListadoPromocionView.newInstanceRecibePromocionConsultaStock(Lista), tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
            else if(tag2.equals( "mostrarConsultaStock"))
            {
                Induvis.setTituloContenedor("Consulta Stock",this);
                String tag3="consulta_stock",tag4="promociondetalle";
                PromocionCabeceraFragment = getSupportFragmentManager().findFragmentByTag(tag4);
                ConsultaStockFragment = getSupportFragmentManager().findFragmentByTag(tag3);

                ft.remove(PromocionCabeceraFragment);
                ft.show(ConsultaStockFragment);
                //ft.add(R.id.content_menu_view, ListadoPromocionView.newInstanceRecibePromocionConsultaStock(Lista), tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
            else if(tag2.equals( "warehouses"))
            {
                String tag3="consulta_stock";
                ConsultaStockFragment = getSupportFragmentManager().findFragmentByTag(tag3);
                ft.hide(ConsultaStockFragment);
                ft.add(R.id.content_menu_view, WareHousesView
                        .newInstanceGetItemCode(Lista), tag2);
                //ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("po1p");
                ft.commit();
            }
        }
        if(tag.equals("KardexOfPaymentView"))
        {
            if(tag2.equals( "start"))
            {
                contentFragment=new KardexOfPaymentView();
                ft.replace(R.id.content_menu_view,contentFragment,tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals( "findClient"))
            {
                contentFragment=new BuscarClienteView();
                String taKardexOfPaymentView="start";
                KardexOfPaymentFragment  =getSupportFragmentManager().findFragmentByTag(taKardexOfPaymentView);
                ft.hide(KardexOfPaymentFragment);
                //ft.add(R.id.content_menu_view,contentFragment,tag2);
                ft.add(R.id.content_menu_view, BuscarClienteView.newInstanciaHistoricContainerSale(Lista),tag2);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
            }
            if(tag2.equals( "sendClient"))
            {
                String tagBuscarClienteView="findClient";
                String tagKardexOfPaymentView="start";
                BuscarClienteFragment=getSupportFragmentManager().findFragmentByTag(tagBuscarClienteView);
                KardexOfPaymentFragment  =getSupportFragmentManager().findFragmentByTag(tagKardexOfPaymentView);
                ft.remove(BuscarClienteFragment);
                ft.show(KardexOfPaymentFragment);
                ft.addToBackStack("popsssggggersa");
                ft.commit();
                KardexOfPaymentView.newInstanceRecibirLista(Lista);
                this.setTitle("Kardex de Pago");
            }
        }
        if(tag.equals("HojaDespachoView"))
        {

            if(tag2.equals("inicio"))
            {
                //Log.e("jpcm","inicio====");
                //ListenerBackPress.setTemporaIdentityFragment("Deposito");
                String taginicio=tag;
                ft.replace(R.id.content_menu_view,HojaDespachoFragment,taginicio);
                ft.addToBackStack("popqqqqqq");
                ft.commit();
            }
            if(tag2.equals("inicioHojaDespachoViewView"))
            {
                String tagHojaDespachoView="HojaDespachoView";
                tag2="inicioRutaVendedorView";
                HojaDespachoView = getSupportFragmentManager().findFragmentByTag(tagHojaDespachoView);
                ft.remove(HojaDespachoView);
                //ft.add(R.id.content_menu_view,ClienteDetalleView.newInstance(Lista),tag3);
                ft.add(R.id.content_menu_view,MenuAccionView.newInstance(Lista),tag2);
                ft.addToBackStack("2pop");
                ft.commit();
            }
        }
    }

    public void EnviarFragmentCobranza(int i)
    {
        if(i>0)
        {
            contentFragment=null;
            contentFragment=new CobranzaCabeceraView();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_menu_view,contentFragment).commit();
        }
    }

    private void checkExternalStoragePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
        } else {
            Log.d("JPCM", "Se tiene permiso para leer!");
        }
    }

    public String getPathFromGooglePhotosUri(Uri uriPhoto) {
        if (uriPhoto == null)
            return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uriPhoto, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFilename = getTempFilename(this);
            output = new FileOutputStream(tempFilename);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return tempFilename;
        } catch (IOException ignored) {
            // Nothing we can do
        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    public static void closeSilently(Closeable c) {
        if (c == null)
            return;
        try {
            c.close();
        } catch (Throwable t) {
            // Do nothing
        }
    }

    private static String getTempFilename(Context context) throws IOException {
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        String QRScaneado = "";

        final CobranzaDetalleView cobranzaDetalleView = new CobranzaDetalleView();
        //final CobranzaCabeceraView cobranzaCabeceraView = new CobranzaCabeceraView();

        CobranzaDetalleSQLiteDao cobranzaCabeceraSQLiteDao = new CobranzaDetalleSQLiteDao(this);

        if (resultCode == RESULT_OK) {
           switch (requestCode) {
                case COD_FOTO:

                    File file = new File(mCurrentPhotoPath);
                    Bitmap bitmap2=null;
                    try {
                        bitmap2 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(file));
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                    ImageCameraController imageCameraController = new ImageCameraController();
                    imageCameraController.SaveImage (this,bitmap2);

                    if(cobranzaCabeceraSQLiteDao.ActualizaValidacionQRCobranzaDetalle(recibovalidado,SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id)==1){
                        fragmentManager = this.getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.add(R.id.content_menu_view, cobranzaDetalleView.nuevainstancia("0"));
                        cobranzaRepository.PendingCollectionQR(getApplicationContext());

                    }
                    break;
                case 21:  //deposito tomar foto


                    File file1 = new File(mCurrentPhotoPath);
                    Bitmap bitmap21=null;
                    try {
                        bitmap21 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(file1));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ImageCameraController imageCameraController2 = new ImageCameraController();
                    imageCameraController2.SaveImage (this,bitmap21);



                    CobranzaCabeceraView.estado = 1;
                    CobranzaCabeceraView.etgrupo.setSelection(0);


                    CobranzaCabeceraView.etgrupo.setEnabled(true);

                    CobranzaCabeceraView.abrir.setEnabled(false);
                    CobranzaCabeceraView.guardar_deposito.setEnabled(true);
                    CobranzaCabeceraView.agregar_foto_deposito.setEnabled(false);


                    Drawable drawable = CobranzaCabeceraView.menu_variable.findItem(R.id.guardar_deposito).getIcon();
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, getResources().getColor(R.color.white));


                    Drawable drawable2 = CobranzaCabeceraView.menu_variable.findItem(R.id.agregar_foto_deposito).getIcon();
                    drawable2 = DrawableCompat.wrap(drawable2);
                    DrawableCompat.setTint(drawable2, getResources().getColor(R.color.Black));

                    SesionEntity.imagen = "DEP";

                    Toast.makeText(this, "Imagen guardada correctamente...", Toast.LENGTH_SHORT).show();
                    break;
                case 155:
                    Log.e("jpcm", "ingreso 155 add foto");

                    Uri uriPhoto = data.getData();

                    File sourceFile = new File(getPathFromGooglePhotosUri(uriPhoto));

                    //////////////////////////////////////////
                    Bitmap bitmap211=null;
                    try {
                        bitmap211 = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.fromFile(sourceFile));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    SesionEntity.imagen = "DEP";

                    ImageCameraController imageCameraController22 = new ImageCameraController();
                    imageCameraController22.SaveImage (this,bitmap211);
                    //////////////////////////////////////////


                    CobranzaCabeceraView.estado = 1;
                    CobranzaCabeceraView.etgrupo.setSelection(0);
                    CobranzaCabeceraView.etgrupo.setEnabled(true);

                    CobranzaCabeceraView.abrir.setEnabled(false);
                    CobranzaCabeceraView.guardar_deposito.setEnabled(true);
                    CobranzaCabeceraView.agregar_foto_deposito.setEnabled(false);

                    Drawable drawable3 = CobranzaCabeceraView.menu_variable.findItem(R.id.guardar_deposito).getIcon();
                    drawable = DrawableCompat.wrap(drawable3);
                    DrawableCompat.setTint(drawable, getResources().getColor(R.color.white));

                    Drawable drawable4 = CobranzaCabeceraView.menu_variable.findItem(R.id.agregar_foto_deposito).getIcon();
                    drawable2 = DrawableCompat.wrap(drawable4);
                    DrawableCompat.setTint(drawable2, getResources().getColor(R.color.Black));

                    Toast.makeText(this, "Imagen adjuntada...", Toast.LENGTH_SHORT).show();


                    break;
               case 10000:

                   Log.e("JPCM","LLEGO DESDE EL WEB VIEW");
                   if (null == uploadMessage && null == ReclamoClientesView.uploadMessageAboveL) return;
                   Uri resultx = data == null || resultCode != RESULT_OK ? null : data.getData();

                   if (ReclamoClientesView.uploadMessageAboveL != null) {
                       onActivityResultAboveL(requestCode, resultCode, data);
                   } else if (uploadMessage != null) {
                       uploadMessage.onReceiveValue(resultx);
                       uploadMessage = null;
                   }
                   break;
                default:
                    Log.d("jpcm", " entro al default con este requestCode " + requestCode);
                    break;
            }
        }

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Tu has canceladp el Scaneo", Toast.LENGTH_LONG).show();
            } else {
                String decData = "";
                try {
                    decData = decrypt("Vistony2019*", Base64.decode(result.getContents().getBytes("UTF-16LE"), Base64.DEFAULT));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //QRScaneado=result.getContents();
                QRScaneado = decData;
                //int resultado=0;

                if (QRScaneado.equals(cobranzaDetalleView.recibo)) {
                    recibovalidado = QRScaneado;
                    Toast.makeText(this, "QR Validado Correctamente", Toast.LENGTH_LONG).show();

/////////////////////////////////////////////////////////////////////////////////////////////////////
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Crea el File
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.e("log,",""+ex);
                    }
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),"com.vistony.salesforce.peru" , photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent,20);
                    }
/////////////////////////////////////////////////////////////////////////////////////////////////////
                } else {
                    Toast.makeText(this, "El QR Scaneado no coindice con el Generado", Toast.LENGTH_LONG).show();
                }
            }
        }
    }



    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();

        return image;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void onActivityResultAboveL(int requestCode, int resultCode, Intent intent) {
        if (requestCode != 10000 || ReclamoClientesView.uploadMessageAboveL == null)
            return;
        Uri[] results = null;
        if (resultCode == Activity.RESULT_OK) {

            if (intent != null) {
                String dataString = intent.getDataString();
                ClipData clipData = intent.getClipData();
                if (clipData != null) {
                    results = new Uri[clipData.getItemCount()];
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        results[i] = item.getUri();
                    }
                }
                if (dataString != null)
                    results = new Uri[]{Uri.parse(dataString)};
            }

        }
        ReclamoClientesView.uploadMessageAboveL.onReceiveValue(results);
        ReclamoClientesView.uploadMessageAboveL = null;
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static BixolonPrinterController getPrinterInstance()
    {
        Log.e("REOS","MenuView-getPrinterInstance-Inicio");
        return bxlPrinter;
    }

    @SuppressLint("SimpleDateFormat")
     private String getCode()
     {
     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
    String date = dateFormat.format(new Date() );
    String photoCode = "pic_" + date;
    return photoCode;
    }

    public void RegresarDeuda()
    {
        onNavigationItemSelected(navigationView.getMenu().getItem(0).setChecked(true));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:

                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)){

                } else{
                    if ((ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)){
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                    }
                }
                break;
        }
    }

    private BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            onNetworkChange(networkInfo);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(networkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        unregisterReceiver(networkStateReceiver);
        super.onPause();
    }

    private void onNetworkChange(NetworkInfo networkInfo) {
        if (networkInfo != null) {
            if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                textViewStatus.setTextColor(Color.parseColor("#FFFFFF"));
                textViewStatus.setText("CONECTADO");
            } else {
                textViewStatus.setTextColor(Color.parseColor("#FFFFFF"));
                textViewStatus.setText("DESCONECTADO");
            }
        }else{
            textViewStatus.setTextColor(Color.parseColor("#FFFFFF"));
            textViewStatus.setText("DESCONECTADO");
        }
    }

    @Override
    public void onRestart() {
        Log.e("REOS","MenuView-onRestart");
        super.onRestart();
        Induvis.refreshGlobalVariables(this);
        //registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
    }
}

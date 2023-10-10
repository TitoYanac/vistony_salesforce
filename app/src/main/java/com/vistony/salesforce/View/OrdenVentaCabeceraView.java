package com.vistony.salesforce.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.perf.metrics.Trace;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.DocumentoPedidoPDF;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Retrofit.ListaPrecioRepository;
import com.vistony.salesforce.Dao.Retrofit.OrdenVentaRepository;
import com.vistony.salesforce.Dao.SQLite.AgenciaSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.BusinessLayerHeadSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaDetallePromocionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ReasonFreeTransferSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.RutaFuerzaTrabajoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.TerminoPagoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UbigeoSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.WarehouseSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.DireccionCliente;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.DocumentHeader;
import com.vistony.salesforce.Entity.Retrofit.Modelo.BusinessLayerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WarehouseEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetallePromocionSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UbigeoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.View.TotalSalesOrder;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.vistony.salesforce.View.OrdenVentaDetalleView.ActualizarResumenMontos;

public class OrdenVentaCabeceraView extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String TAG_1 = "text";
    private static String TAG="HistoricoOrdenVenta";

    private String mParam1;
    private String mParam2;
    View v;
    static Button btn_detalle_orden_venta;
    OnFragmentInteractionListener mListener;
    String nombrecliente,codigocliente,direccioncliente,moneda,rucdni,comentario,galonesAcum,subtotalAcum,descuentoAcum,impuestosAcum,totalAcum,Flag,dispatchdate,chkruta,ubigeo_id="0",statuscount="N",customerwhitelist="N";
    static String cliente_terminopago,cliente_terminopago_id,cliente_domembarque_id,discountpercentreason="";
    static String terminopago_id="",terminopago,listaprecio_id,agencia,agencia_id,historicoordenventa_agencia,impuesto_id,impuesto,contado,ordenventa_id,zona_id;
    TextView tv_ruc,tv_cliente,tv_moneda,tv_orden_cabecera_subtotal,tv_orden_cabecera_descuento,tv_orden_cabecera_igv,tv_orden_cabecera_total,tv_orden_cabecera_galones,tv_dispatch_date;
    static EditText et_comentario;
    static TextView tv_terminopago,tv_orden_venta_agencia,tv_direccion,tv_increment_flete,tv_discount_percent,lbl_tittle_discount_percent;
    public static ArrayList<ListaClienteCabeceraEntity> Listado;
    public static ArrayList<ListaHistoricoOrdenVentaEntity> listaHistoricoOrdenVentaEntity=new ArrayList<>();
    static ImageButton btn_orden_venta_consultar_agencia,btn_consultar_termino_pago,btn_consultar_direccion,btn_dispatch_date,imb_descount_bl;
    static ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
    static HiloObtenerResumenOrdenVenta hiloObtenerResumenOrdenVenta;
    static MenuItem guardar_orden_venta,enviar_erp,generarpdf;
    static Menu menu_variable;
    static Context context;
    static ArrayList<AgenciaSQLiteEntity> ListaAgencia=new ArrayList<>();
    static ArrayList<TerminoPagoSQLiteEntity> ListaTerminoPago=new ArrayList<>();
    static Activity activity;
    /*
    static ArrayList<ListaDireccionClienteEntity> ListaDireccionCliente=new ArrayList<>();

    private DireccionCliente direccionSelecionada;*/

    HiloObtenerAgencia hiloObtenerAgencia=new HiloObtenerAgencia();
    static Spinner sp_cantidaddescuento,spnmoneda,spn_reason_freetransfer,spn_warehouse;
    private ProgressDialog pd;
    ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabecera;
    ArrayList<OrdenVentaDetalleSQLiteEntity> listaOrdenVentaDetalle;
    ArrayList<TerminoPagoSQLiteEntity> listaTerminopago;
    TerminoPagoSQLiteDao terminoPagoSQLiteDao;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    static boolean chk_inicioOrdenVentaCabeceraView=false;
    static CheckBox chk_descuento_contado,chk_sale_counter,chk_add_flete,chk_ovcomplete;
    ArrayList<ListaOrdenVentaCabeceraEntity> listaOrdenVentaCabeceraEntities;
    ArrayAdapter<String> comboAdapterdescuento;
    Trace myTrace;
    Boolean confirmationRequestErp=false;
    List<String> values;
    String cantidaddescuento,historicoOVcantidaddescuento;
    private OrdenVentaRepository ordenVentaRepository;
    private static DireccionCliente direccionSelecionada;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha,parametrofecha,fechacomparativa;
    private  int day_dispatch_date,mes_dispatch_date,ano_dispatch_date;
    private static DatePickerDialog oyenteSelectorFecha;
    Induvis induvis;
    static TableRow tr_dsct_cont,tr_summary_flete,tr_lbl_reason_free_transfer,tr_spn_reason_free_transfer,tr_descount_bl,tr_lbl_dispatch_date,tr_tv_dispatch_date,tr_lbl_spn_warehouse,tr_spn_warehouse,tr_sale_counter,tr_add_flete,tr_ovcomplete,tr_gallons;
    static TableLayout tl_tittle_discount_percent,tl_discount_percent;
    Float discountPercent=0f;
    //TaskGetPriceList taskGetPriceList=new TaskGetPriceList();
    private ListaPrecioRepository listaPrecioRepository;
    UsuarioSQLiteEntity ObjUsuario;
    public OrdenVentaCabeceraView() {
        // Required empty public constructor
    }

    public static OrdenVentaCabeceraView newInstanceHistoricoOrdenVenta(Object objeto,String flag) {
        OrdenVentaCabeceraView ordenVentaView = new OrdenVentaCabeceraView();

        Bundle b = new Bundle();
        ArrayList<ListaHistoricoOrdenVentaEntity> Lista = (ArrayList<ListaHistoricoOrdenVentaEntity>) objeto;
        b.putSerializable(TAG,Lista);
        b.putString("FLAG",flag);
        ordenVentaView.setArguments(b);

        return ordenVentaView;
    }

    public static OrdenVentaCabeceraView newInstance(Object objeto) {

        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaCabeceraView ordenVentaView = new OrdenVentaCabeceraView();

        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        b.putSerializable(TAG_1,Lista);
        ordenVentaView.setArguments(b);
        Listado = Lista;
        chk_inicioOrdenVentaCabeceraView=true;
        return ordenVentaView;
    }

    public static OrdenVentaCabeceraView newInstanciaTerminoPago(Object objeto) {
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaCabeceraView ordenVentaView = new OrdenVentaCabeceraView();
        obtenerTituloFormulario();
        Bundle b = new Bundle();
        ListaTerminoPago = (ArrayList<TerminoPagoSQLiteEntity>) objeto;
        for(int i=0;i<ListaTerminoPago.size();i++){

            terminopago_id=ListaTerminoPago.get(i).getTerminopago_id().toString();
            Log.e("REOS","OrdenVentaCabeceraView.newInstanciaTerminoPago.terminopago_id: "+terminopago_id);
            terminopago=ListaTerminoPago.get(i).getTerminopago().toString();
            Log.e("REOS","OrdenVentaCabeceraView.newInstanciaTerminoPago.terminopago: "+terminopago);
            contado=ListaTerminoPago.get(i).getContado().toString();
            Log.e("REOS","OrdenVentaCabeceraView.newInstanciaTerminoPago.contado: "+contado);
            SesionEntity.contado=contado;
            cliente_terminopago_id=terminopago_id;
            Log.e("REOS","OrdenVentaCabeceraView.newInstanciaTerminoPago.cliente_terminopago_id: "+cliente_terminopago_id);
            if(terminopago_id.equals("-1")){
                chk_descuento_contado.setEnabled(true);
            }else{
                chk_descuento_contado.setEnabled(false);
            }
        }
        tv_terminopago.setText(terminopago);

        if(terminopago_id.equals("47"))
        {
            tr_lbl_reason_free_transfer.setVisibility(View.VISIBLE);
            tr_spn_reason_free_transfer.setVisibility(View.VISIBLE);
        }else {
            tr_lbl_reason_free_transfer.setVisibility(View.GONE);
            tr_spn_reason_free_transfer.setVisibility(View.GONE);
        }

        if(terminopago_id.equals("44"))
        {
            tr_descount_bl.setVisibility(View.VISIBLE);
            tl_tittle_discount_percent.setVisibility(View.VISIBLE);
            tl_discount_percent.setVisibility(View.VISIBLE);
        }else {
            tr_descount_bl.setVisibility(View.GONE);
            tl_tittle_discount_percent.setVisibility(View.GONE);
            tl_discount_percent.setVisibility(View.GONE);
        }

        return ordenVentaView;
    }

    public static OrdenVentaCabeceraView newInstanciaNuevaDireccion(Object objeto) {

        Log.e("XASD","paso por aqui direccion");
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaCabeceraView ordenVentaView = new OrdenVentaCabeceraView();
        obtenerTituloFormulario();
        direccionSelecionada=(DireccionCliente)objeto;

        tv_direccion.setText(direccionSelecionada.getDireccion());
        cliente_domembarque_id=direccionSelecionada.getDomembarque_id();
        Log.e("REOS", "OrdenVentaCabeceraView-newInstanciaNuevaDireccion-cliente_domembarque_id:"+cliente_domembarque_id);
        return ordenVentaView;
    }

    public static OrdenVentaCabeceraView newInstanciaAgencia(Object objeto) {

        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        OrdenVentaCabeceraView ordenVentaView = new OrdenVentaCabeceraView();
        obtenerTituloFormulario();
        Bundle b = new Bundle();
        ListaAgencia = (ArrayList<AgenciaSQLiteEntity>) objeto;
        for(int i=0;i<ListaAgencia.size();i++)
        {
            agencia=ListaAgencia.get(i).getAgencia().toString();
            agencia_id=ListaAgencia.get(i).getAgencia_id().toString();

        }
        tv_orden_venta_agencia.setText(agencia);

        return ordenVentaView;
    }

    public static OrdenVentaCabeceraView newInstanciaAgregarOrdenVentaDetalle(Object objeto) {
        OrdenVentaCabeceraView ordenVentaView = new OrdenVentaCabeceraView();

        listaOrdenVentaDetalleEntities=(ArrayList<ListaOrdenVentaDetalleEntity>) objeto;
        hiloObtenerResumenOrdenVenta.execute();

        /*SE DEBE PODER EDITAR AUN, POR QUE NO SE GUARDA*/


        tv_terminopago.setEnabled(false);
        btn_consultar_termino_pago.setEnabled(false);
        chk_descuento_contado.setEnabled(false);
        spnmoneda.setEnabled (false);
        spnmoneda.setClickable (false);
        //sp_cantidaddescuento.setEnabled(false);
        //sp_cantidaddescuento.setClickable (false);


        Utilitario.disabledButtton(btn_detalle_orden_venta);
        Utilitario.disabledImageButtton(btn_consultar_direccion,context);
        Utilitario.disabledImageButtton(btn_consultar_termino_pago,context);
        Utilitario.disabledImageButtton(btn_orden_venta_consultar_agencia,context);
        Utilitario.disabledSpinner(spnmoneda);
        Utilitario.disabledImageButtton(imb_descount_bl,context);
        Utilitario.disabledSpinner(spn_warehouse);

        //Utilitario.disabledImageButtton(btn_dispatch_date);
        //Utilitario.disabledEditText(et_comentario);
        Utilitario.disabledCheckBox(chk_descuento_contado);


        return ordenVentaView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        context=getContext();
        SesionEntity.flagquerystock="N";
        hiloObtenerResumenOrdenVenta=new HiloObtenerResumenOrdenVenta();
        activity=getActivity();
        obtenerTituloFormulario();
        hiloObtenerAgencia=new HiloObtenerAgencia();
        listaTerminopago=new ArrayList<>();
        terminoPagoSQLiteDao=new TerminoPagoSQLiteDao(getContext());
        listaOrdenVentaCabeceraEntities=new ArrayList<>();
        listaOrdenVentaDetalleEntities=new ArrayList<>();
        values=new ArrayList<String>();
        ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            listaHistoricoOrdenVentaEntity = (ArrayList<ListaHistoricoOrdenVentaEntity>)(getArguments().getSerializable(TAG));
            Listado= (ArrayList<ListaClienteCabeceraEntity>)getArguments().getSerializable(TAG_1);


            Flag=getArguments().getString("FLAG");

            if(listaHistoricoOrdenVentaEntity!=null){

                listaOrdenVentaCabecera=new ArrayList<>();
                listaOrdenVentaDetalle=new ArrayList<>();
                ArrayList<ListaClienteCabeceraEntity> listaClienteCabecera=new ArrayList<>();

                ClienteSQlite clienteSQlite =new ClienteSQlite(getContext());
                AgenciaSQLiteDao agenciaSQLiteDao=new AgenciaSQLiteDao(getContext());
                ArrayList<AgenciaSQLiteEntity> listaAgenciasqliteentity=new ArrayList<>();

                for(int j=0;j<listaHistoricoOrdenVentaEntity.size();j++){
                    ordenventa_id=listaHistoricoOrdenVentaEntity.get(j).getSalesOrderID();
                }

                FormulasController formulasController=new FormulasController(getContext());
                listaOrdenVentaCabecera=formulasController.ObtenerOrdenVentaCaberaSQLiteporID(getContext(),ordenventa_id);
                listaOrdenVentaDetalle=formulasController.ObtenerOrdenVentaDetalleSQLiteporID(getContext(),ordenventa_id);

                listaOrdenVentaDetalleEntities=formulasController.ConvertirListaOrdenVentaDetalleEntity(listaOrdenVentaDetalle);

                //////////////////////////////////////
                //////////////////////////////////////
                //////////////////////////////////////
                ArrayList<DireccionCliente> listaDireccionCliente=new ArrayList<>();
                DireccionSQLite direccionSQLite=new DireccionSQLite(getContext());

                for(int g=0;g<listaOrdenVentaCabecera.size();g++){
                    listaClienteCabecera= clienteSQlite.ObtenerClienteporClienteID(listaOrdenVentaCabecera.get(g).getCliente_id());

                    comentario=listaOrdenVentaCabecera.get(g).getComentario();
                    galonesAcum=listaOrdenVentaCabecera.get(g).getTotal_gal_acumulado();
                    subtotalAcum=listaOrdenVentaCabecera.get(g).getMontosubtotal();
                    descuentoAcum=listaOrdenVentaCabecera.get(g).getMontodescuento();
                    impuestosAcum=listaOrdenVentaCabecera.get(g).getMontoimpuesto();
                    totalAcum=listaOrdenVentaCabecera.get(g).getMontototal();
                    confirmationRequestErp=listaOrdenVentaCabecera.get(g).getRecibidoERP().equals("1")?true:false;
                    cliente_terminopago_id=listaOrdenVentaCabecera.get(g).getTerminopago_id();
                    cliente_domembarque_id=listaOrdenVentaCabecera.get(g).getDomembarque_id();
                    dispatchdate=listaOrdenVentaCabecera.get(g).getDispatchdate();
                    listaAgenciasqliteentity= agenciaSQLiteDao.ObtenerAgencia_porID(
                            listaOrdenVentaCabecera.get(g).getAgencia_id()
                    );
                    listaDireccionCliente=direccionSQLite.getListAddressOV(listaOrdenVentaCabecera.get(g).getCliente_id(),listaOrdenVentaCabecera.get(g).getDomembarque_id());
                    historicoOVcantidaddescuento=listaOrdenVentaCabecera.get(g).getDescuentocontado();
                }

                for(int l=0;l<listaClienteCabecera.size();l++)
                {
                    nombrecliente=listaClienteCabecera.get(l).getNombrecliente();
                    codigocliente=listaClienteCabecera.get(l).getCliente_id();
                    //direccioncliente=listaClienteCabecera.get(l).getDireccion();

                    moneda=listaClienteCabecera.get(l).getMoneda();
                    impuesto_id=listaClienteCabecera.get(l).getImpuesto_id();
                    impuesto=listaClienteCabecera.get(l).getImpuesto();
                    rucdni= listaClienteCabecera.get(l).getRucdni();
                    //cliente_terminopago_id=listaClienteCabecera.get(l).getTerminopago_id();
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.listaClienteCabecera.listaClienteCabecera.get(l).getTerminopago_id(): "+listaClienteCabecera.get(l).getTerminopago_id());
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.listaClienteCabecera.cliente_terminopago_id: "+cliente_terminopago_id);



                }

                for(int k=0;k<listaDireccionCliente.size();k++)
                {
                    direccioncliente=listaDireccionCliente.get(k).getDireccion();
                }

                for(int m=0;m<listaAgenciasqliteentity.size();m++){
                    historicoordenventa_agencia= listaAgenciasqliteentity.get(m).getAgencia();
                }
                obtenerTituloFormulario();
            }

            if(Listado !=null){

                for(int i=0;i<Listado.size();i++){
                    nombrecliente=Listado.get(i).getNombrecliente();
                    codigocliente=Listado.get(i).getCliente_id();
                    direccioncliente=Listado.get(i).getDireccion();
                    moneda=Listado.get(i).getMoneda();
                    impuesto_id=Listado.get(i).getImpuesto_id();
                    impuesto=Listado.get(i).getImpuesto();
                    rucdni= Listado.get(i).getRucdni();
                    cliente_terminopago_id=Listado.get(i).getTerminopago_id();
                    cliente_domembarque_id=Listado.get(i).getDomembarque_id();
                    chkruta=Listado.get(i).getChk_ruta();
                    zona_id=Listado.get(i).getZona_id();
                    ubigeo_id=Listado.get(i).getUbigeo_id();
                    if(ubigeo_id==null||ubigeo_id==""){
                        ubigeo_id="0";
                    }
                    if(Listado.get(i).getStatuscount()!=null)
                    {
                        statuscount=Listado.get(i).getStatuscount();
                    }
                    customerwhitelist=Listado.get(i).getCustomerwhitelist();
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.Listado.customerwhitelist: "+customerwhitelist);
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.Listado.ubigeo_id: "+ubigeo_id);
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.Listado.zona_id: "+zona_id);
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.Listado.Listado.get(i).getTerminopago_id(): "+Listado.get(i).getTerminopago_id());
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.Listado.cliente_terminopago_id: "+cliente_terminopago_id);
                    Log.e("REOS","OrdenVentaCabeceraView.OnCreate.Listado.statuscount: "+statuscount);

                }
            }else{
                Log.e("jpcm","ESTA NULO lISTADO");
            }
        }



        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            Log.e("REOS","MenuAccionView: No tiene ACCESS_FINE_LOCATION ");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            Log.e("REOS","MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            try {
                gpsController =  new GPSController(getContext());
                mLocation = gpsController.getLocation(mLocation);
                latitude = mLocation.getLatitude();
                longitude= mLocation.getLongitude();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        ArrayList<BusinessLayerHeadEntity> businessLayerHeadEntityArrayList=new ArrayList<>();
        BusinessLayerHeadSQLiteDao businessLayerHeadSQLiteDao=new BusinessLayerHeadSQLiteDao(getContext());
        businessLayerHeadEntityArrayList=businessLayerHeadSQLiteDao.getBusinessLayer("27");

        for(int i=0;i<businessLayerHeadEntityArrayList.size();i++)
        {
            discountPercent=Float.parseFloat(businessLayerHeadEntityArrayList.get(i).getU_VIS_Variable());
            discountpercentreason=businessLayerHeadEntityArrayList.get(i).getName();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);

        /*if(SesionEntity.quotation.equals("N"))
        {
            v= inflater.inflate(R.layout.fragment_orden_venta_cabecera_peru, container, false);
        }else {
            v= inflater.inflate(R.layout.fragment_orden_venta_cabecera, container, false);
        }*/
        v= inflater.inflate(R.layout.fragment_orden_venta_cabecera_peru, container, false);

        ordenVentaRepository = new ViewModelProvider(getActivity()).get(OrdenVentaRepository.class);


        btn_detalle_orden_venta=v.findViewById(R.id.btn_detalle_orden_venta);
        btn_consultar_direccion=v.findViewById(R.id.btn_consultar_direccion);
        tv_ruc=v.findViewById(R.id.tv_ruc);
        tv_cliente=v.findViewById(R.id.tv_cliente);
        tv_direccion=v.findViewById(R.id.tv_direccion);
        spnmoneda=v.findViewById(R.id.spnmoneda);
        sp_cantidaddescuento= (Spinner) v.findViewById(R.id.sp_cantidaddescuento);
        //tv_moneda=v.findViewById(R.id.tv_moneda);
        tv_terminopago=(TextView) v.findViewById(R.id.tv_orden_venta_terminopago);
        tv_orden_venta_agencia=v.findViewById(R.id.tv_orden_venta_agencia);
        et_comentario=v.findViewById(R.id.et_comentario);
        btn_consultar_termino_pago=v.findViewById(R.id.btn_consultar_termino_pago);
        tv_orden_cabecera_subtotal=v.findViewById(R.id.tv_orden_cabecera_subtotal);
        tv_orden_cabecera_descuento=v.findViewById(R.id.tv_orden_cabecera_descuento);
        tv_orden_cabecera_igv=v.findViewById(R.id.tv_orden_cabecera_igv);
        tv_orden_cabecera_total=v.findViewById(R.id.tv_orden_cabecera_total);
        tv_orden_cabecera_galones=v.findViewById(R.id.tv_orden_cabecera_galones);
        tv_dispatch_date=v.findViewById(R.id.tv_dispatch_date);
        btn_orden_venta_consultar_agencia=v.findViewById(R.id.btn_orden_venta_consultar_agencia);
        chk_descuento_contado=(CheckBox) v.findViewById(R.id.chk_descuento_contado);
        tv_ruc.setText(rucdni);
        tv_cliente.setText(nombrecliente);
        tv_direccion.setText(direccioncliente);
        listaTerminopago=terminoPagoSQLiteDao.ObtenerTerminoPagoporID(cliente_terminopago_id,SesionEntity.compania_id);
        btn_dispatch_date = (ImageButton) v.findViewById(R.id.btn_dispatch_date);
        btn_dispatch_date.setOnClickListener(this);
        tr_dsct_cont=v.findViewById(R.id.tr_dsct_cont);
        chk_sale_counter=v.findViewById(R.id.chk_sale_counter);
        chk_add_flete=v.findViewById(R.id.chk_add_flete);
        tv_increment_flete=v.findViewById(R.id.tv_increment_flete);
        tr_summary_flete=v.findViewById(R.id.tr_summary_flete);
        tr_summary_flete.setVisibility(View.GONE);
        chk_ovcomplete=v.findViewById(R.id.chk_ovcomplete);
        tr_lbl_reason_free_transfer=v.findViewById(R.id.tr_lbl_reason_free_transfer);
        tr_spn_reason_free_transfer=v.findViewById(R.id.tr_spn_reason_free_transfer);
        spn_reason_freetransfer=v.findViewById(R.id.spn_reason_freetransfer);
        tr_descount_bl=v.findViewById(R.id.tr_descount_bl);
        tr_descount_bl.setVisibility(View.GONE);
        imb_descount_bl=v.findViewById(R.id.imb_descount_bl);
        tv_discount_percent=v.findViewById(R.id.tv_discount_percent);
        tl_tittle_discount_percent=v.findViewById(R.id.tl_tittle_discount_percent);
        tl_discount_percent=v.findViewById(R.id.tl_discount_percent);
        tl_tittle_discount_percent.setVisibility(View.GONE);
        tl_discount_percent.setVisibility(View.GONE);
        lbl_tittle_discount_percent=v.findViewById(R.id.lbl_tittle_discount_percent);
        tr_lbl_dispatch_date=v.findViewById(R.id.tr_lbl_dispatch_date);
        tr_tv_dispatch_date=v.findViewById(R.id.tr_tv_dispatch_date);
        spn_warehouse=v.findViewById(R.id.spn_warehouse);
        tr_lbl_spn_warehouse=v.findViewById(R.id.tr_lbl_spn_warehouse);
        tr_spn_warehouse=v.findViewById(R.id.tr_spn_warehouse);
        tr_sale_counter=v.findViewById(R.id.tr_sale_counter);
        tr_ovcomplete=v.findViewById(R.id.tr_ovcomplete);
        tr_add_flete=v.findViewById(R.id.tr_add_flete);
        tr_gallons=v.findViewById(R.id.tr_gallons);
        if(!ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
        {
            tr_lbl_spn_warehouse.setVisibility(View.GONE);
            tr_spn_warehouse.setVisibility(View.GONE);
        }

        //Ocultar Funcionalidades
        //Descuento por contado
        tr_dsct_cont.setVisibility(View.GONE);
        //Para solo induvis
        if(!BuildConfig.FLAVOR.equals("peru"))
        {
            tr_sale_counter.setVisibility(View.GONE);
            tr_add_flete.setVisibility(View.GONE);
            tr_ovcomplete.setVisibility(View.GONE);
            tr_gallons.setVisibility(View.GONE);
        }

        ArrayList<String> currencyList = Induvis.getCurrency();
        ArrayAdapter<String> adapterCurrency = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,currencyList);
        spnmoneda.setAdapter(adapterCurrency);
        adapterCurrency.notifyDataSetChanged();

        //Pruebas de Fecha de entrega
        if(SesionEntity.deliverydateauto.equals("Y"))
        {
            tv_dispatch_date.setText(Induvis.getDateFormataDateUser(getDateWorkPathforZone()));
            Utilitario.disabledImageButtton(btn_dispatch_date, getContext());
        }
        else {
            //Produccion
            dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            date = new Date();
            fecha =dateFormat.format(date);
            parametrofecha=fecha;
            tv_dispatch_date.setText(induvis.getDate(BuildConfig.FLAVOR,fecha));
        }



        for(int i=0;i<listaTerminopago.size();i++)
        {
            cliente_terminopago=listaTerminopago.get(i).getTerminopago();
            contado=listaTerminopago.get(i).getContado();
            SesionEntity.contado=contado;
            if(listaTerminopago.get(i).getTerminopago_id().equals("-1"))
            {
                chk_descuento_contado.setEnabled(true);
            }else
            {
                chk_descuento_contado.setEnabled(false);
            }
            Log.e("REOS","OrdenVentaCabeceraView-contado:"+SesionEntity.contado);

        }

        if(SesionEntity.deliveryrefusedmoney.equals("Y"))
        {
            if(statuscount.equals("Y")&&!customerwhitelist.equals("Y"))
            {
                tv_terminopago.setText("--ELEGIR TERMINO DE PAGO--");
            }
            else {
                tv_terminopago.setText(cliente_terminopago);
            }
        }else {
            tv_terminopago.setText(cliente_terminopago);
        }

        ArrayList<String> ListResponseWarehouse= new  ArrayList<String>();
        ListResponseWarehouse.add("--SELECCIONAR--");
        WarehouseSQLiteDao warehouseSQLiteDao=new WarehouseSQLiteDao(getContext());
        ArrayList<WarehouseEntity> listWarehouseEntity=new ArrayList<>();
        listWarehouseEntity=warehouseSQLiteDao.getWarehouse();
        for(int i=0;i<listWarehouseEntity.size();i++)
        {
            ListResponseWarehouse.add(listWarehouseEntity.get(i).getWhsCode()+"-"+listWarehouseEntity.get(i).getWhsName()) ;
        }
        ArrayAdapter<String> adapterResponseWarehouse = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, ListResponseWarehouse);
        spn_warehouse.setAdapter(adapterResponseWarehouse);
        adapterResponseWarehouse.notifyDataSetChanged();

        ///Eventos
        btn_detalle_orden_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tv_terminopago.getText().toString().equals("--ELEGIR TERMINO DE PAGO--"))
                {
                    Toast.makeText(getContext(), "Debe Elegir un termino de pago de lista", Toast.LENGTH_SHORT).show();
                }else
                {
                    if(cliente_terminopago_id.equals("-1")&&!agencia_id.equals("P20102306598"))
                    {
                        alertdialogInformative(getContext(),"Advertencia!!!","Motivo: \n Termino de Pago (Contado) no debe ir vinculado con agencia.\n Sugerencia: \n Cambiar a termino de pago (Pago adelantado) si se desea seguir con agencia.").show();
                    }else {
                        //if(ObjUsuario.getU_VIS_ManagementType().equals("B2B"))

                        if(spn_warehouse.getSelectedItem().toString().equals("--SELECCIONAR--")&&ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
                        {
                            Toast.makeText(getContext(), "Debe Elegir un Almacen para poder continuar...", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            btn_detalle_orden_venta.setEnabled(false);
                            btn_detalle_orden_venta.setClickable(false);
                            alertaCrearOrdenVenta("Esta Seguro de Abrir una "+Induvis.getTituloVentaString(getContext())+" Nueva?").show();
                        }
                    }
                }
            }
        });

        btn_consultar_termino_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("REOS","OrdenVentaCabeceraView.btn_consultar_termino_pago.Listado.statuscount: "+statuscount);
                Log.e("REOS","OrdenVentaCabeceraView.btn_consultar_termino_pago.Listado.cliente_terminopago_id: "+cliente_terminopago_id);
                String Fragment="OrdenVentaCabeceraView";
                String accion="terminopago";
                String compuesto=Fragment+"-"+accion;
                String Objeto=cliente_terminopago_id+"&&"+statuscount+"&&"+customerwhitelist;
                mListener.onFragmentInteraction(compuesto,Objeto);
            }
        });

        btn_orden_venta_consultar_agencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="OrdenVentaCabeceraView";
                String accion="agencia";
                String compuesto=Fragment+"-"+accion;
                String Objeto="";
                mListener.onFragmentInteraction(compuesto,Objeto);
            }
        });

        btn_consultar_direccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="OrdenVentaCabeceraView";
                String accion="direccion";
                String compuesto=Fragment+"-"+accion;
                String Objeto=codigocliente;
                mListener.onFragmentInteraction(compuesto,Objeto);
            }
        });

        chk_add_flete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(chk_add_flete.isChecked()){
                    Log.e("REOS","OrdenVentaCabeceraView-ubigeo_id:"+ubigeo_id);
                    UbigeoSQLiteDao ubigeoSQLiteDao=new UbigeoSQLiteDao(getContext());
                    ArrayList<UbigeoSQLiteEntity> listUbigeoSQLiteEntity=new ArrayList<>();
                    listUbigeoSQLiteEntity=ubigeoSQLiteDao.ObtenerUbigeoporID(ubigeo_id);
                    Log.e("REOS","OrdenVentaCabeceraView-listUbigeoSQLiteEntity.size():"+listUbigeoSQLiteEntity.size());
                    for(int i=0;i<listUbigeoSQLiteEntity.size();i++){
                        tv_increment_flete.setText(listUbigeoSQLiteEntity.get(i).getU_VIS_Flete());
                    }
                }else {
                    tv_increment_flete.setText("0");
                }
            }
        });

        chk_descuento_contado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
/*                if(chk_descuento_contado.isChecked())
                {
                    sp_cantidaddescuento.setEnabled(true);
                    sp_cantidaddescuento.setClickable(true);
                    ObtenerDataSpinnerDescuentoContado();

                }else
                {
                    sp_cantidaddescuento.setEnabled(false);
                    sp_cantidaddescuento.setClickable(false);
                }
*/
            }
        });


        /*if (getArguments() != null) {
        if(!listaHistoricoOrdenVentaEntity.isEmpty())
        {

        }
        }*/

        if (getArguments() != null) {
            if(listaHistoricoOrdenVentaEntity!=null)
            {
                tv_dispatch_date.setText(Induvis.getDate(BuildConfig.FLAVOR,dispatchdate) );
                et_comentario.setText(comentario);
                //sp_cantidaddescuento.setText
                values=new ArrayList<>();
                /*Log.e("REOS","OrdenVentaCabeceraView-OnCreateView-SesionEntity.U_VIS_CashDscnt:"+SesionEntity.U_VIS_CashDscnt);
                for(int j=0;j<Integer.parseInt(SesionEntity.U_VIS_CashDscnt);j++)
                {
                    Log.e("REOS","OrdenVentaCabeceraView-OnCreateView-U_VIS_CashDscnt-i:"+j);
                    //valores[i]=String.valueOf(i+1);
                    values.add(String.valueOf(j+1));
                }*/
                values.add(historicoOVcantidaddescuento);
                /*comboAdapterdescuento=new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner,values);
                sp_cantidaddescuento.setAdapter(comboAdapterdescuento);
                sp_cantidaddescuento.setEnabled(false);
                sp_cantidaddescuento.setClickable(false);*/
                obtenerTituloFormulario();
                hiloObtenerResumenOrdenVenta.execute();
            }
        }
        if(SesionEntity.activecurrency.equals("N"))
        {
            spnmoneda.setEnabled(false);
        }
        hiloObtenerAgencia.execute();

        Utilitario.disabledCheckBox(chk_add_flete);
        if(StatusIncrementFlete()){
            chk_add_flete.setChecked(true);
        }

        ArrayList<String> ListResponse= new  ArrayList<String>();
        ListResponse.add("--SELECCIONAR--");
        ReasonFreeTransferSQLiteDao reasonFreeTransferSQLiteDao=new ReasonFreeTransferSQLiteDao(getContext());
        ArrayList<ReasonDispatchEntity> listReasonFreeTransfer=new ArrayList<>();
        listReasonFreeTransfer=reasonFreeTransferSQLiteDao.getReasonFreeTransfer();
        for(int i=0;i<listReasonFreeTransfer.size();i++)
        {
            ListResponse.add(listReasonFreeTransfer.get(i).getReasondispatch_id()+"-"+listReasonFreeTransfer.get(i).getReasondispatch()) ;
        }
        ArrayAdapter<String> adapterResponse = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, ListResponse);
        spn_reason_freetransfer.setAdapter(adapterResponse);
        adapterResponse.notifyDataSetChanged();

        spnmoneda.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String monedatotal="",codigomoneda="";
                        monedatotal=spnmoneda.getSelectedItem().toString();
                        String[] palabra = monedatotal.split("-");
                        SesionEntity.currency_id=palabra[0];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        imb_descount_bl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInputEditext(getContext(),discountPercent.floatValue()).show();
            }
        });

        lbl_tittle_discount_percent.setText(discountpercentreason);
        lbl_tittle_discount_percent.setAllCaps(true);



        spn_warehouse.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(!spn_warehouse.getSelectedItem().toString().equals("--SELECCIONAR--"))
                        {

                            String wareHousePriceList;

                            String warehouseCode="",warehouseName="",priceListCash="",priceListCredit="",U_VIST_SUCUSU="";
                            String[] palabra = spn_warehouse.getSelectedItem().toString().split("-");
                            warehouseCode=palabra[0];
                            warehouseName=palabra[1];
                            ArrayList<WarehouseEntity> listWarehouseEntity=new ArrayList<>();
                            listWarehouseEntity=warehouseSQLiteDao.getWarehouseCode(warehouseCode);
                            for(int j=0;j<listWarehouseEntity.size();j++)
                            {
                                priceListCash=listWarehouseEntity.get(j).getPriceListCash();
                                priceListCredit=listWarehouseEntity.get(j).getPriceListCredit();
                                U_VIST_SUCUSU=listWarehouseEntity.get(j).getU_VIST_SUCUSU();
                            }
                            SesionEntity.almacen_id=warehouseCode;
                            SesionEntity.U_VIST_SUCUSU=U_VIST_SUCUSU;

                            ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(getContext());
                            wareHousePriceList= listaPrecioDetalleSQLiteDao.getWarehousePricelist();
                            Log.e("REOS", "OrdenVentaCabeceraView-onCreateView-wareHousePriceList: " + wareHousePriceList);
                            Log.e("REOS", "OrdenVentaCabeceraView-onCreateView-SesionEntity.almacen_id: " + SesionEntity.almacen_id);

                            if(!SesionEntity.almacen_id.replaceAll(" ","").equals(wareHousePriceList.replaceAll(" ","")))
                            {
                                ProgressDialog pd1;
                                pd1 = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.download_parameters), true, false);
                                listaPrecioRepository = new ViewModelProvider(getActivity()).get(ListaPrecioRepository.class);
                                listaPrecioRepository.execClarAndAddPriceListWarehouse(SesionEntity.imei, getContext(), warehouseCode, priceListCash, priceListCredit).observe(getActivity()
                                        , data -> {

                                            Toast.makeText(getContext(), "Maestro de Precios Actualizado", Toast.LENGTH_SHORT).show();
                                            Log.e("REOS", "ParametrosView-listaPrecioRepository-data: " + data);
                                            pd1.dismiss();
                                        }
                                );
                            }

                        }
                        else {
                            Toast.makeText(getContext(), "Debe elegir un Almacen", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        return v;
    }


    public boolean StatusIncrementFlete(){
        boolean status=false;
        Float flete=0f;
        Log.e("REOS","OrdenVentaCabeceraView-ubigeo_id:"+ubigeo_id);
        UbigeoSQLiteDao ubigeoSQLiteDao=new UbigeoSQLiteDao(getContext());
        ArrayList<UbigeoSQLiteEntity> listUbigeoSQLiteEntity=new ArrayList<>();
        listUbigeoSQLiteEntity=ubigeoSQLiteDao.ObtenerUbigeoporID(ubigeo_id);
        Log.e("REOS","OrdenVentaCabeceraView-listUbigeoSQLiteEntity.size():"+listUbigeoSQLiteEntity.size());
        for(int i=0;i<listUbigeoSQLiteEntity.size();i++){
            tv_increment_flete.setText(listUbigeoSQLiteEntity.get(i).getU_VIS_Flete());
            tv_increment_flete.setVisibility(View.GONE);
            chk_add_flete.setText("INCREMENTO POR FLETE "+listUbigeoSQLiteEntity.get(i).getU_VIS_Flete()+"%");
            flete=Float.parseFloat(listUbigeoSQLiteEntity.get(i).getU_VIS_Flete());
        }

        if(flete>0){
            status=true;
        }else{
            status=false;
        }


        return status;

    }

    public String getDateWorkPathforZone()
    {
        String date="";
        RutaFuerzaTrabajoSQLiteDao rutaFuerzaTrabajoSQLiteDao=new RutaFuerzaTrabajoSQLiteDao(getContext());
        date=rutaFuerzaTrabajoSQLiteDao.getDateWorkPathforZone(zona_id,codigocliente,cliente_domembarque_id);
        parametrofecha=date;
        parametrofecha=Induvis.ConvertdatefordateSAP(parametrofecha);
        return date;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.e("jpcm","se regresoooo EERTTT");
        //ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");
        mListener = null;
    }

    @Override
    public void onAttach(Context context) {
        //ListenerBackPress.setCurrentFragment("OrdenVentaCabeceraView");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private class HiloObtenerResumenOrdenVenta extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            try {
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result){
            obtenerTituloFormulario();

            ActualizarResumenMontos(tv_orden_cabecera_subtotal,tv_orden_cabecera_descuento,tv_orden_cabecera_igv,tv_orden_cabecera_total,tv_orden_cabecera_galones);

            hiloObtenerResumenOrdenVenta= new HiloObtenerResumenOrdenVenta();
            setHasOptionsMenu(true);
            if (getArguments().getString("FLAG") != null) {
                callbackFlag(getArguments().getString("FLAG"));
            }
        }
    }

    private void callbackFlag(String flag){
        switch(flag){
            case "historicoordenventa":

                if(confirmationRequestErp){
                    Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
                    drawable2 = DrawableCompat.wrap(drawable2);
                    DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
                    menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
                    enviar_erp.setEnabled(false);
                }else{

                }

                tv_orden_cabecera_galones.setText(galonesAcum);
                tv_orden_cabecera_subtotal.setText(Convert.currencyForView(subtotalAcum));
                tv_orden_cabecera_descuento.setText(Convert.currencyForView(descuentoAcum));
                tv_orden_cabecera_igv.setText(Convert.currencyForView(impuestosAcum));
                tv_orden_cabecera_total.setText(Convert.currencyForView(totalAcum));


                Utilitario.disabledButtton(btn_detalle_orden_venta);
                Utilitario.disabledImageButtton(btn_consultar_direccion,context);
                Utilitario.disabledImageButtton(btn_consultar_termino_pago,context);
                Utilitario.disabledImageButtton(btn_orden_venta_consultar_agencia,context);
                Utilitario.disabledImageButtton(btn_dispatch_date,context);
                Utilitario.disabledSpinner(spnmoneda);

                if(et_comentario.getText().length()==0){
                    et_comentario.setHint("Sin comentario");
                }

                Utilitario.disabledEditText(et_comentario);

                Utilitario.disabledCheckBox(chk_descuento_contado);
                break;
            default:
                break;
        }
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        switch (BuildConfig.FLAVOR){
            case "bolivia":
            case "india":
            case "ecuador":
            case "chile":
                inflater.inflate(R.menu.menu_orden_venta_cabecera, menu);
                break;
            case "peru":
                inflater.inflate(R.menu.menu_orden_venta_cabecera_peru, menu);
                break;
            default:
                break;
        }

        guardar_orden_venta = menu.findItem(R.id.guardar_orden_venta);
        enviar_erp = menu.findItem(R.id.enviar_erp);
        generarpdf = menu.findItem(R.id.generarpdf);
        menu_variable=menu;

        if(listaOrdenVentaDetalleEntities.isEmpty())
        {
            Drawable drawable = menu_variable.findItem(R.id.guardar_orden_venta).getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
            menu_variable.findItem(R.id.guardar_orden_venta).setIcon(drawable);
            guardar_orden_venta.setEnabled(false);

            Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
            drawable2 = DrawableCompat.wrap(drawable2);
            DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
            menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
            enviar_erp.setEnabled(false);

        }else{

            Drawable drawable = menu_variable.findItem(R.id.guardar_orden_venta).getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.white));
            menu_variable.findItem(R.id.guardar_orden_venta).setIcon(drawable);
            guardar_orden_venta.setEnabled(true);

        }

        if (getArguments() != null) {
            if(listaHistoricoOrdenVentaEntity!=null)
            {
                Drawable drawable = menu_variable.findItem(R.id.guardar_orden_venta).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.guardar_orden_venta).setIcon(drawable);
                guardar_orden_venta.setEnabled(false);

                for(int i=0;i<listaHistoricoOrdenVentaEntity.size();i++){
                    Log.e("REOS", "OrdenVentaCabeceraView-onCreateOptionsMenu-listaHistoricoOrdenVentaEntity.get(i).isEnvioERPOV()"+listaHistoricoOrdenVentaEntity.get(i).isEnvioERPOV());
                    if(//listaHistoricoOrdenVentaEntity!=null || !
                            listaHistoricoOrdenVentaEntity.get(i).isRecepcionERPOV()

                    ){
                        Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
                        drawable2 = DrawableCompat.wrap(drawable2);
                        DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
                        menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
                        enviar_erp.setEnabled(false);


                    }else{
                        Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
                        drawable2 = DrawableCompat.wrap(drawable2);
                        DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.white));
                        menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
                        enviar_erp.setEnabled(true);
                    }
                }
                /*Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.white));
                menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
                enviar_erp.setEnabled(true);*/

                Drawable drawable3 = menu_variable.findItem(R.id.generarpdf).getIcon();
                drawable3 = DrawableCompat.wrap(drawable3);
                DrawableCompat.setTint(drawable3, ContextCompat.getColor(context, R.color.white));
                menu_variable.findItem(R.id.generarpdf).setIcon(drawable3);
                generarpdf.setEnabled(true);

                tv_terminopago.setEnabled(false);
                btn_consultar_termino_pago.setEnabled(false);
            }

            if(chk_inicioOrdenVentaCabeceraView)
            {
                Drawable drawable = menu_variable.findItem(R.id.guardar_orden_venta).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.guardar_orden_venta).setIcon(drawable);
                guardar_orden_venta.setEnabled(false);

                Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
                enviar_erp.setEnabled(false);

                Drawable drawable3 = menu_variable.findItem(R.id.generarpdf).getIcon();
                drawable3 = DrawableCompat.wrap(drawable3);
                DrawableCompat.setTint(drawable3, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.generarpdf).setIcon(drawable3);
                generarpdf.setEnabled(false);
                chk_inicioOrdenVentaCabeceraView=false;
            }
            /*else
                {

                }*/
        }

        super.onCreateOptionsMenu(menu, inflater);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.guardar_orden_venta:
                if(spn_reason_freetransfer==null)
                {
                    ArrayList<String> ListResponse= new  ArrayList<String>();
                    ListResponse.add("");
                    ArrayAdapter<String> adapterResponse = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, ListResponse);
                    spn_reason_freetransfer.setAdapter(adapterResponse);
                }

                Log.e("REOS","ListSalesOrderDetailAdapter-onOptionsItemSelected-guardar_orden_venta-terminopago_id:"+terminopago_id);
                Log.e("REOS","ListSalesOrderDetailAdapter-onOptionsItemSelected-guardar_orden_venta-spn_reason_freetransfer.getSelectedItem().toString():"+spn_reason_freetransfer.getSelectedItem().toString());
                if( terminopago_id.equals("47")
                    &&(spn_reason_freetransfer.getSelectedItem().toString().equals("--SELECCIONAR--"))
                )
                {
                    alertdialogInformative(getContext(), "ADVERTENCIA", "El termino de pago TRANSFERENCIA GRATUITA, debe ir vinculando a un motivo valido favor de elegir").show();
                }else {
                    if (SesionEntity.deliverydateauto.equals("Y")) {
                        alertaGuardarOrdenVenta("Esta Seguro de Guardar la Orden de Venta?").show();
                    } else {
                        dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                        date = new Date();
                        fechacomparativa = dateFormat.format(date);
                        if (!parametrofecha.equals(fechacomparativa)) {
                            alertaGuardarOrdenVenta("Esta Seguro de Guardar la Orden de Venta?").show();
                        } else {

                            alertdialogInformative(getContext(), "ADVERTENCIA", "La Fecha de entrega de la Orden de Venta, debe ser distinta a la fecha actual, se sugiere 2 dias en adelante...").show();
                        }
                    }
                }
                return false;
            case R.id.enviar_erp:
                alertaEnviarERP("Esta Seguro de Enviar a la Nube la Orden de Venta?").show();
                return true;
            case R.id.generarpdf:
                alertaGenerarPDF("Esta Seguro de Generar el Archivo PDF?").show();
                return true;
            default:
                break;
        }
        return false;
    }

    private Dialog alertaGuardarOrdenVenta(String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);
        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);
        ImageView image = dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);



        dialogButtonOK.setOnClickListener(v -> {

                Utilitario.disabledImageButtton(btn_dispatch_date, context);
                RegistrarOrdenVentaBD();
                Toast.makeText(getContext(), "Se Guardo Correctamente la Orden de Venta", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


    public AlertDialog alertaGuardarOrdenVenta2() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Guardar la Orden de Venta?")
                .setPositiveButton("OK",

                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RegistrarOrdenVentaBD ();
                                Toast.makeText(getContext(), "Se Guardo Correctamente la Orden de Venta", Toast.LENGTH_SHORT).show();

                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

        return builder.create();
    }
    private Dialog alertaGenerarPDF(String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            GenerarArchivoPDF ();
            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    public void GenerarArchivoPDF() {

        ArrayList<OrdenVentaCabeceraSQLiteEntity> listaOrdenVentaCabecera=new ArrayList<>();
        ArrayList<OrdenVentaDetallePromocionSQLiteEntity> listaOrdenVentaDetallePromocion=new ArrayList<>();

        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(getContext());
        OrdenVentaDetallePromocionSQLiteDao ordenVentaDetallePromocionSQLiteDao=new OrdenVentaDetallePromocionSQLiteDao(getContext());

        listaOrdenVentaCabecera= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraporID(ordenventa_id);
        listaOrdenVentaDetallePromocion=ordenVentaDetallePromocionSQLiteDao.ObtenerOrdenVentaDetallePromocionporID(ordenventa_id);

        DocumentoPedidoPDF documentoPedidoPdf =new DocumentoPedidoPDF();
        documentoPedidoPdf.generarPdf(getContext(),listaOrdenVentaCabecera,listaOrdenVentaDetallePromocion);
        //TOAST DE ARCHIVOC READO ESTA EN EL generarPdf

        switch (BuildConfig.FLAVOR){
            case "bolivia":
            case "india":
            case "ecuador":
            case "chile":
                Drawable drawable = menu_variable.findItem(R.id.enviar_erp).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.enviar_erp).setIcon(drawable);
                enviar_erp.setEnabled(false);
                Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                generarpdf.setEnabled(true);
                break;
            case "peru":

                break;
            default:
                break;
        }



    }

    public void RegistrarOrdenVentaBD (){
        listaOrdenVentaCabeceraEntities=new ArrayList<>();
        FormulasController formulasController=new FormulasController(getContext());
        ListaOrdenVentaCabeceraEntity listaOrdenVentaCabeceraEntity=new ListaOrdenVentaCabeceraEntity();
        GregorianCalendar calendario = new GregorianCalendar();
        String monedatotal,codigomoneda,descripcionmoneda;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        UsuarioSQLite usuarioSQLite =new UsuarioSQLite(getContext());
        listaprecio_id= usuarioSQLite.ObtenerListaPrecioUsuario(contado);
        SesionEntity.contado=contado;

        ordenventa_id=formulasController.ObtenerFechaHoraCadena();
        monedatotal=spnmoneda.getSelectedItem().toString();
        String[] palabra = monedatotal.split("-");
        codigomoneda=palabra[0];
        descripcionmoneda=palabra[1];

        for(int i=0;i<Listado.size();i++)
        {

            listaOrdenVentaCabeceraEntity.orden_cabecera_compania_id= SesionEntity.compania_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_id=ordenventa_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_cliente_id=codigocliente;
            Log.e("REOS", "OrdenVentaCabeceraView-RegistrarPedido-cliente_domembarque_id:"+cliente_domembarque_id);
            listaOrdenVentaCabeceraEntity.orden_cabecera_domembarque_id=//(direccionSelecionada==null)?Listado.get(i).getDomembarque_id():direccionSelecionada.getDomembarque_id();//  Listado.get(i).getDomembarque_id();
                    cliente_domembarque_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_domfactura_id=Listado.get(i).getDomfactura_id();

            listaOrdenVentaCabeceraEntity.orden_cabecera_fecha_creacion=String.valueOf(sdf.format(calendario.getTime()));
            listaOrdenVentaCabeceraEntity.orden_cabecera_terminopago_id=cliente_terminopago_id;
            if(!agencia_id.equals("P20102306598"))
            {
                listaOrdenVentaCabeceraEntity.orden_cabecera_agencia_id=agencia_id;
            }else {
                listaOrdenVentaCabeceraEntity.orden_cabecera_agencia_id="";
            }
            listaOrdenVentaCabeceraEntity.orden_cabecera_moneda_id=codigomoneda;
            listaOrdenVentaCabeceraEntity.orden_cabecera_comentario=et_comentario.getText().toString();
            listaOrdenVentaCabeceraEntity.orden_cabecera_almacen_id=SesionEntity.almacen_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_impuesto_id=impuesto_id;

            formulasController=new FormulasController(getContext());
            ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntyCopia=new ArrayList<>();

            listaOrdenVentaDetalleEntyCopia=formulasController.ConversionListaOrdenDetallepoListaOrdenDetallePromocion(listaOrdenVentaDetalleEntities);

            TotalSalesOrder totalSalesOrder=formulasController.CalcularMontosPedidoCabeceraDetallePromocion(listaOrdenVentaDetalleEntyCopia);

            /*AQUI SE ASIGNA LOSDATOS PARA LA CABECERA*/
            listaOrdenVentaCabeceraEntity.orden_cabecera_montosubtotal=""+totalSalesOrder.getSubtotal();
            listaOrdenVentaCabeceraEntity.orden_cabecera_montodescuento=""+totalSalesOrder.getDescuento();
            listaOrdenVentaCabeceraEntity.orden_cabecera_montoimpuesto=""+totalSalesOrder.getIgv();
            listaOrdenVentaCabeceraEntity.orden_cabecera_montototal=""+totalSalesOrder.getTotal();

            listaOrdenVentaCabeceraEntity.orden_cabecera_fuerzatrabajo_id=SesionEntity.fuerzatrabajo_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_usuario_id=SesionEntity.usuario_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_lista_orden_detalle=listaOrdenVentaDetalleEntities;
            listaOrdenVentaCabeceraEntity.orden_cabecera_planta=SesionEntity.planta_id;
            listaOrdenVentaCabeceraEntity.orden_cabecera_lista_precio_id=listaprecio_id;

            switch (BuildConfig.FLAVOR)
            {
                case "peru":
                    if(codigomoneda.equals("S/")){
                        listaOrdenVentaCabeceraEntity.orden_cabecera_tipocambio="1";
                    }else{
                        //Esperando que el Tipo de Cambio viaje hacia el Usuario
                        listaOrdenVentaCabeceraEntity.orden_cabecera_tipocambio=SesionEntity.rate;
                    }
                    break;
                case "bolivia":
                    listaOrdenVentaCabeceraEntity.orden_cabecera_tipocambio="1";
                    break;
            }


            listaOrdenVentaCabeceraEntity.orden_cabecera_fechatipocambio=String.valueOf(sdf.format(calendario.getTime()));
            listaOrdenVentaCabeceraEntity.orden_cabecera_rucdni=rucdni;
            listaOrdenVentaCabeceraEntity.orden_cabecera_DocType="I";
            listaOrdenVentaCabeceraEntity.orden_cabecera_total_gal_acumulado=""+totalSalesOrder.getGalones();
            listaOrdenVentaCabeceraEntity.orden_cabecera_descuentocontado=cantidaddescuento;
            listaOrdenVentaCabeceraEntity.orden_cabecera_cotizacion=SesionEntity.quotation;
            listaOrdenVentaCabeceraEntity.orden_cabecera_U_SYP_MDTD="";
            listaOrdenVentaCabeceraEntity.orden_cabecera_U_SYP_MDSD="";
            listaOrdenVentaCabeceraEntity.orden_cabecera_U_SYP_MDCD="";
            listaOrdenVentaCabeceraEntity.orden_cabecera_U_SYP_MDMT="01";
            listaOrdenVentaCabeceraEntity.orden_cabecera_U_SYP_STATUS="V";
            listaOrdenVentaCabeceraEntity.orden_cabecera_dispatch_date= parametrofecha;
            listaOrdenVentaCabeceraEntity.orden_cabecera_route=chkruta;

            Log.e("REOS","OrdenVentaCabeceraView.RegistrarOrdenVentaBD.chk_sale_counter.isChecked():"+chk_sale_counter.isChecked());
            if (chk_sale_counter.isChecked())
            {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIT_VENMOS ="S";
            }else {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIT_VENMOS ="N";
            }
            Log.e("REOS","OrdenVentaCabeceraView.RegistrarOrdenVentaBD.listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIT_VENMOS:"+listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIT_VENMOS);

            Log.e("REOS","OrdenVentaCabeceraView.RegistrarOrdenVentaBD.chk_add_flete.isChecked():"+chk_add_flete.isChecked());
            if (chk_add_flete.isChecked())
            {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_Flete=tv_increment_flete.getText().toString();
                //listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_Flete ="S";
            }else {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_Flete ="0";
            }
            if (chk_ovcomplete.isChecked())
            {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_CompleteOV ="S";
            }else {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_CompleteOV ="N";
            }

            if(!spn_reason_freetransfer.getSelectedItem().toString().equals("--SELECCIONAR--"))
            {
                String U_VIS_TipTransGrat=spn_reason_freetransfer.getSelectedItem().toString();
                String[] arrayU_VIS_TipTransGrat = U_VIS_TipTransGrat.split("-");
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_TipTransGrat =arrayU_VIS_TipTransGrat[0];
            }

            listaOrdenVentaCabeceraEntity.orden_cabecera_discount_percent=tv_discount_percent.getText().toString();
            listaOrdenVentaCabeceraEntity.orden_cabecera_discount_percent_reason=discountpercentreason;

            if(!tv_discount_percent.getText().toString().equals("0"))
            {
                listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_MOTAPLDESC="01";
            }
            listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIST_SUCUSU=SesionEntity.U_VIST_SUCUSU;


            Log.e("REOS","OrdenVentaCabeceraView.RegistrarOrdenVentaBD.listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_Flete:"+listaOrdenVentaCabeceraEntity.orden_cabecera_U_VIS_Flete);

            VisitaSQLiteEntity visita=new VisitaSQLiteEntity();
            visita.setCardCode(codigocliente);
            visita.setAddress(cliente_domembarque_id);

            if(SesionEntity.quotation.equals("Y"))
            {
                visita.setType("12");
            }else {
                visita.setType("01");
            }
            visita.setObservation("Se genero el pedido "+listaOrdenVentaCabeceraEntity.getOrden_cabecera_id()+" para la dirección "+Listado.get(i).getDireccion());
            visita.setLatitude(""+latitude);
            visita.setLongitude(""+longitude);
            visita.setMobileID(ordenventa_id);
            visita.setStatusRoute(chkruta);
            visita.setAmount(""+totalSalesOrder.getTotal());
            visita.setTerminoPago_ID(contado);
            formulasController.RegistraVisita(visita,getActivity(),totalSalesOrder.getTotal());

        }

        listaOrdenVentaCabeceraEntities.add(listaOrdenVentaCabeceraEntity);

        formulasController = new FormulasController(getContext());

        ///////////GUARDA LA ORDEN DE VENTA\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        formulasController.RegistrarPedidoenBD(listaOrdenVentaCabeceraEntities,listaOrdenVentaDetalleEntities);
        /////////////////////////////////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        Drawable drawable = menu_variable.findItem(R.id.guardar_orden_venta).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
        menu_variable.findItem(R.id.guardar_orden_venta).setIcon(drawable);
        guardar_orden_venta.setEnabled(false);

        Drawable drawable2 = menu_variable.findItem(R.id.enviar_erp).getIcon();
        drawable2 = DrawableCompat.wrap(drawable2);
        DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.White));
        menu_variable.findItem(R.id.enviar_erp).setIcon(drawable2);
        enviar_erp.setEnabled(true);

        Drawable drawable3 = menu_variable.findItem(R.id.generarpdf).getIcon();
        drawable3 = DrawableCompat.wrap(drawable3);
        DrawableCompat.setTint(drawable3, ContextCompat.getColor(context, R.color.White));
        menu_variable.findItem(R.id.generarpdf).setIcon(drawable3);
        generarpdf.setEnabled(true);


    }

    private Dialog alertaEnviarERP(String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {

            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Enviando "+Induvis.getTituloVentaString(context), true, false);
            OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite=new OrdenVentaCabeceraSQLite(getContext());
            ordenVentaCabeceraSQLite.UpdateStatusOVenviada(ordenventa_id);
            ordenVentaRepository.sendSalesOrder(ordenventa_id,getContext()).observe(getActivity(), data->{

                Toast.makeText(getContext(),data, Toast.LENGTH_LONG).show();

                Drawable drawable = menu_variable.findItem(R.id.enviar_erp).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(context, R.color.Black));
                menu_variable.findItem(R.id.enviar_erp).setIcon(drawable);
                enviar_erp.setEnabled(false);

                Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(context, R.color.white));
                menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                generarpdf.setEnabled(true);

                pd.dismiss();
            });
//            myTrace.stop();

            //HiloEnviarNubeOV hiloEnviarNubeOV = new HiloEnviarNubeOV();
            //hiloEnviarNubeOV.execute(Jsonx);

            dialog.dismiss();
        });
        dialogButtonCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
    private class HiloObtenerAgencia extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... arg0) {
            ArrayList<AgenciaSQLiteEntity> listaAgenciaSQLiteEntity=new ArrayList<>();
            try {
                AgenciaSQLiteDao agenciaSQLiteDao=new AgenciaSQLiteDao(getContext());
                listaAgenciaSQLiteEntity=agenciaSQLiteDao.ObtenerAgencia();
            } catch (Exception e){
                e.printStackTrace();
            }
            return listaAgenciaSQLiteEntity;
        }

        protected void onPostExecute(Object result)
        {
            ArrayList<AgenciaSQLiteEntity> agencias= (ArrayList<AgenciaSQLiteEntity>) result;
            for(int i=0;i<agencias.size();i++)
            {
                if(agencias.get(i).getAgencia_id().equals(Induvis.getAgenciaInduvis())){
                    agencia_id=agencias.get(i).getAgencia_id();
                    agencia=agencias.get(i).getAgencia();
                    tv_orden_venta_agencia.setText(agencia);
                    break;
                }else{
                    agencia_id=agencias.get(i).getAgencia_id();
                    agencia=agencias.get(i).getAgencia();
                    tv_orden_venta_agencia.setText(agencia);
                }
            }

            if(listaHistoricoOrdenVentaEntity!=null)
            {
                tv_orden_venta_agencia.setText(historicoordenventa_agencia);
            }
            hiloObtenerAgencia=new HiloObtenerAgencia();
        }
    }

    private Dialog alertaCrearOrdenVenta(String texto) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);
        dialog.setCanceledOnTouchOutside(false);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK =dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel =  dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            String Fragment="OrdenVentaCabeceraView";
            String accion="detalle";
            String compuesto=Fragment+"-"+accion;
            cantidaddescuento=String.valueOf(chk_descuento_contado.isChecked());
            ///////////AQui//////////////
            String monedatotal="",codigomoneda="";
            monedatotal=spnmoneda.getSelectedItem().toString();
            String[] palabra = monedatotal.split("-");
            codigomoneda=palabra[0];

            if(ubigeo_id==null||ubigeo_id==""||ubigeo_id.isEmpty()||ubigeo_id.isBlank()){
                ubigeo_id="0";
            }
            if(codigomoneda==null||codigomoneda==""||codigomoneda.isEmpty()||codigomoneda.isBlank()){
                codigomoneda="0";
            }
            String Objeto=
                    //contado+"-"+
                    cantidaddescuento+"&"+cliente_terminopago_id+"&"+codigomoneda+"&"+tv_discount_percent.getText().toString()+"&"+ubigeo_id;
            //String Objeto=cantidaddescuento+"-"+cliente_terminopago_id;
            Log.e("REOS","OrdenVentaCabeceraView-alertaCrearOrdenVenta-cantidaddescuento: "+cantidaddescuento);
            Log.e("REOS","OrdenVentaCabeceraView-alertaCrearOrdenVenta-cliente_terminopago_id: "+cliente_terminopago_id);
            Log.e("REOS","OrdenVentaCabeceraView-alertaCrearOrdenVenta-codigocliente: "+codigocliente);
            Log.e("REOS","OrdenVentaCabeceraView-alertaCrearOrdenVenta-Objeto: "+Objeto);
            Log.e("REOS","OrdenVentaCabeceraView-alertaCrearOrdenVenta-ubigeo_id: "+ubigeo_id);
            Log.e("REOS","OrdenVentaCabeceraView-alertaCrearOrdenVenta-currency_id: "+codigomoneda);
            //SalesOrder salesOrder=new SalesOrder();
            //salesOrder.discountpercent=tv_discount_percent.getText().toString();
            //salesOrder.payment=cliente_terminopago_id;
            String [] arrayObject={codigocliente,Objeto};

            DocumentHeader documentHeader=new DocumentHeader();
            documentHeader.setCardCode(codigocliente);
            documentHeader.setDiscountCash(cantidaddescuento);
            documentHeader.setDocCurrency(codigomoneda);
            documentHeader.setDiscountPercent_BL(tv_discount_percent.getText().toString());
            documentHeader.setUbigeoCode(ubigeo_id);
            documentHeader.setPaymentGroupCode(cliente_terminopago_id);

            dialog.dismiss();
            //mListener.onFragmentInteraction(compuesto,arrayObject);
            mListener.onFragmentInteraction(compuesto,documentHeader);
            btn_detalle_orden_venta.setEnabled(true);
            btn_detalle_orden_venta.setClickable(true);
        });

        dialogButtonCancel.setOnClickListener(v -> {
            dialog.dismiss();
            btn_detalle_orden_venta.setEnabled(true);
            btn_detalle_orden_venta.setClickable(true);
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    public void ObtenerDataSpinnerDescuentoContado()
    {
        values=new ArrayList<>();
        Log.e("REOS","OrdenVentaCabeceraView-OnCreateView-SesionEntity.U_VIS_CashDscnt:"+SesionEntity.U_VIS_CashDscnt);
        for(int j=0;j<Integer.parseInt(SesionEntity.U_VIS_CashDscnt);j++)
        {
            Log.e("REOS","OrdenVentaCabeceraView-OnCreateView-U_VIS_CashDscnt-i:"+j);
            //valores[i]=String.valueOf(i+1);
            values.add(String.valueOf(j+1));
        }
        comboAdapterdescuento=new ArrayAdapter<>(getContext(),R.layout.layout_custom_spinner,values);
        sp_cantidaddescuento.setAdapter(comboAdapterdescuento);
        //sp_cantidaddescuen=sp_cantidaddescuento.getSelectedItem().toString();
        Log.e("REOS","OrdenVentaCabeceraView-OnCreateView-sp_cantidaddescuento.getSelectedItem().toString():"+sp_cantidaddescuento.getSelectedItem().toString());
    }

    static private void obtenerTituloFormulario()
    {
        activity.setTitle(Induvis.getTituloVentaString(context));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_dispatch_date:
                final Calendar c1 = Calendar.getInstance();
                day_dispatch_date = c1.get(Calendar.DAY_OF_MONTH);
                mes_dispatch_date = c1.get(Calendar.MONTH);
                ano_dispatch_date = c1.get(Calendar.YEAR);
                oyenteSelectorFecha = new DatePickerDialog(getContext(),this,
                        ano_dispatch_date,
                        mes_dispatch_date,
                        day_dispatch_date
                );
                oyenteSelectorFecha.show();
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
        parametrofecha=year+mes+dia;
        tv_dispatch_date.setText(dia + "/" + mes + "/" + year);
    }

    static private Dialog alertdialogInformative(Context context, String titulo, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text=(TextView) dialog.findViewById(R.id.text);
        text.setText(titulo);
        textViewMsj.setText(message);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


    private Dialog DialogInputEditext(
            Context context,
            Float discountPercent

    ) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_input_numeric);
        EditText et_ViewMsj=(EditText) dialog.findViewById(R.id.textEditViewMsj);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText(discountpercentreason);
        text.setAllCaps(true);
        text.setTextSize(15);
        et_ViewMsj.setHint("Hasta el "+discountPercent+"%");
        //et_ViewMsj.setInputType(InputType.TYPE_CLASS_NUMBER);
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(et_ViewMsj.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(), "Ingrese un valor valido", Toast.LENGTH_SHORT).show();
                }else {
                    Log.e("REOS","OrdenVentaCabeceraView-DialogInputEditext-discount: "+discountPercent);
                    Log.e("REOS","OrdenVentaCabeceraView-DialogInputEditext.et_ViewMsj.getText().toString(): "+et_ViewMsj.getText().toString());
                    if(Float.parseFloat (et_ViewMsj.getText().toString())<=discountPercent)
                    {
                        tv_discount_percent.setText(et_ViewMsj.getText().toString());
                        dialog.dismiss();
                    }else
                    {
                        Toast.makeText(getContext(), "El % de descuento excede lo permitido", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


    /*private class TaskGetPriceList extends AsyncTask<String, Void, String> {
        String WhsCode="",WhsName="",PriceListCash="",PriceListCredit="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), getActivity().getResources().getString(R.string.please_wait), getActivity().getResources().getString(R.string.download_parameters), true, false);
        }
        @Override
        protected String doInBackground(String... arg0) {
            try {
                for(int i=0;i<arg0.length;i++) {
                    switch (i)
                    {
                        case 0: {
                            WhsCode=arg0[i];
                            break;
                        }
                        case 1: {
                            WhsName=arg0[i];
                            break;
                        }
                        case 2: {
                            PriceListCash=arg0[i];
                            break;
                        }
                        case 3: {
                            PriceListCredit=arg0[i];
                            break;
                        }
                    }
                }


            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result){
            obtenerTituloFormulario();

            ActualizarResumenMontos(tv_orden_cabecera_subtotal,tv_orden_cabecera_descuento,tv_orden_cabecera_igv,tv_orden_cabecera_total,tv_orden_cabecera_galones);

            hiloObtenerResumenOrdenVenta= new HiloObtenerResumenOrdenVenta();
            setHasOptionsMenu(true);
            if (getArguments().getString("FLAG") != null) {
                callbackFlag(getArguments().getString("FLAG"));
            }
        }
    }*/
}
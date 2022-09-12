package com.vistony.salesforce.View;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

/*
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
*/

import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hitomi.cmlibrary.CircleMenu;
import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.CobranzaDetalleDialogController;
import com.vistony.salesforce.Controller.Utilitario.DocumentoCobranzaPDF;
import com.vistony.salesforce.Controller.Adapters.ListaClienteDetalleAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaCobranzaDetalleAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.SQLite.BancoSQLite;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Dao.Adapters.ListaCobranzaDetalleDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.SQLite.BancoSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.sentry.Sentry;

public class CobranzaDetalleView extends Fragment {
    //ListaClienteDetalleAdapter listaClienteDetalleAdapter;
    ArrayList<ListaClienteDetalleEntity> listaClienteDetalleAdapterFragment;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleEntities;
    ListaCobranzaDetalleAdapter listaCobranzaDetalleAdapter;
    ListView listViewCobranzaDetalle;
    ImageButton imbplus,imbminus,imbaceptar,imbcancelar,imbcancelar2;
    OmegaCenterIconButton imbcomentariorecibo;

    EditText et_cobrado_edit;
    View v;
    ObtenerWSCobranzaDetalle obtenerWSCobranzaDetalle;

    BigDecimal cobrado,nuevo_saldo,saldo;

    // float cobrado=0,nuevo_saldo=0,saldo=0;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;

    String fecha,et_cobrado,chkqrvalidado,recibo_generado;
    static public String recibo;
    DocumentoCobranzaPDF documentoCobranzaPDF;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";

    private String mParam1;
    private String mParam2;
    static String TAG_1 = "text";
    String texto= null;
    DocumentoSQLite documentoSQLite;
    int resultado=0,correlativorecibo=0,ultimocorrelativorecibo=0,correlativorecibows=0;
    private OnFragmentInteractionListener mListener;
    static public ImageView imvprueba;
    CheckBox chkpagoadelantado,chk_bancarizado,chk_pago_directo,chk_pago_pos,chk_collectioncheck,chk_E_signature;
    MenuItem generarpdf,validarqr,guardar,edit_signature;
    TextView tv_recibo;
    public static CheckBox chk_validacionqr;
    ListaClienteDetalleEntity listaClienteDetalleEntity;
    ClienteSQlite clienteSQlite;
    ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity;
    private boolean linternaOn;
    private boolean tieneFlash;
    private Camera objCamara;
    Camera.Parameters parametrosCamara;
    private final static String NOMBRE_DIRECTORIO = "Cobranza";
    public static String comentario="";
    int resultadows=0;
    String resultadoenviows="0";
    Menu menu_variable;
    ConfiguracionSQLiteDao configuracionSQLiteDao;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLEntity;
    String vinculaimpresora;
    UsuarioSQLite usuarioSQLite;
    String Conexion="",tipocobranza="";
    ArrayList<UsuarioSQLiteEntity> listaUsuarioSQLiteEntity;
    ArrayList<ListaHistoricoCobranzaEntity> Listado = new ArrayList<>();
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=2;
    String Estadochkbancarizado="",Estadodepositodirecto="",Estadopagopos="",Estadopagoadelantado="",StatusCollectionCheck="N";
    private CobranzaRepository cobranzaRepository;
    private DepositoRepository depositoRepository;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    String cliente_id_visita,domembarque_id_visita,zona_id_visita;
    FloatingActionButton fab_invoice_cancelation,fab_edit_signature;
    private ProgressDialog pd;
    ImageView imv_prueba_mostrarfirma;
    TableRow tablerow_e_signature;

    public static Fragment newInstanciaComentario(String param1) {
        Log.e("jpcm","Este es NUEVA ISNTANCIA 1");
        CobranzaDetalleView fragment = new CobranzaDetalleView();
        comentario=param1;
        return fragment;
    }
    public static CobranzaDetalleView newInstance(String param1) {
        Log.e("jpcm","Este es NUEVA ISNTANCIA 2 CONSULTA COBRADO");
        CobranzaDetalleView fragment = new CobranzaDetalleView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public static CobranzaDetalleView newInstancia(Object param1) {

        ListenerBackPress.setCurrentFragment("FormCobranzaDetalleFromHistoryCobranza");

        CobranzaDetalleView fragment = new CobranzaDetalleView();
        Bundle args = new Bundle();
        ArrayList<ListaHistoricoCobranzaEntity> Lista = new ArrayList<>();
        Lista = (ArrayList<ListaHistoricoCobranzaEntity>) param1;
        args.putSerializable(ARG_PARAM1,Lista);
        fragment.setArguments(args);
        return fragment;
    }

    public static CobranzaDetalleView newInstance(Object objeto){
        //Log.e("jpcm","Este es NUEVA ISNTANCIA 3 ESTO VA PARA CLIENTE VERIFICADO");

        ListenerBackPress.setCurrentFragment("FormDetalleCobranzaCliente");

        CobranzaDetalleView fragment = new CobranzaDetalleView();
        ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaClienteDetalleEntity> Lista = (ArrayList<ListaClienteDetalleEntity>) objeto;
        Lista.size();
        b.putSerializable(TAG_1,Lista);
        fragment.setArguments(b);
        return fragment;

    }

    public static CobranzaDetalleView nuevainstancia (Object objeto){
        Log.e("jpcm","Este es NUEVA ISNTANCIA 4");
        CobranzaDetalleView fragment = new CobranzaDetalleView();
        chk_validacionqr.setChecked(true);
        //hiloVlidarQR.execute();
        return fragment;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String obtenerFechaActual() {
        String formato = "yyyyMMdd";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime ahora = LocalDateTime.now();
        return formateador.format(ahora);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String obtenerHoraActual() {
        String formato = "HHmmss";
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern(formato);
        LocalDateTime ahora = LocalDateTime.now();
        return formateador.format(ahora);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listaClienteDetalleAdapterFragment =  new ArrayList<ListaClienteDetalleEntity>();
        documentoCobranzaPDF = new DocumentoCobranzaPDF();
        documentoSQLite = new DocumentoSQLite(getContext());
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        listaCobranzaDetalleEntities = new ArrayList<CobranzaDetalleSQLiteEntity>();
        clienteSQlite = new ClienteSQlite(getContext());
        cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
        depositoRepository = new ViewModelProvider(getActivity()).get(DepositoRepository.class);
        StatusCollectionCheck=SesionEntity.collectioncheck;
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            try {
                GPSController gpsController = new GPSController(getActivity());
                Location mLocation = gpsController.getLocation(null);
                latitude = mLocation.getLatitude();
                longitude = mLocation.getLongitude();
            }catch(Exception e){
                Sentry.captureMessage(e.getMessage());
            }
        }
        //fecha =obtenerFechaYHoraActual();
        //objCamara = Camera.open();

        //parametrosCamara = objCamara.getParameters();
        tieneFlash = getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
        listaConfiguracionSQLEntity =  new ArrayList<>();
        usuarioSQLite = new UsuarioSQLite(getContext());
        if (getArguments() != null) {

            Listado = (ArrayList<ListaHistoricoCobranzaEntity>)getArguments().getSerializable(ARG_PARAM1);
            if(!(Listado==null)){
                if(!(Listado.isEmpty()))
                    for (int j = 0; j < Listado.size(); j++)
                    {
                        String Bancarizado="";

                        recibo_generado=Listado.get(j).getRecibo();
                        recibo=recibo_generado;
                        chkqrvalidado=Listado.get(j).getEstadoqr();
                        listaClienteDetalleEntity =  new ListaClienteDetalleEntity();
                        //cobrado = Float.parseFloat(listaCobranzaDetalleEntities.get(j).getSaldocobrado());
                        //istaClienteDetalleEntity.reci
                        listaClienteDetalleEntity.cliente_id=Listado.get(j).getCliente_id();
                        listaClienteDetalleEntity.nombrecliente=Listado.get(j).getCliente_nombre();
                        listaClienteDetalleEntity.cobrado=Listado.get(j).getMontocobrado();
                        listaClienteDetalleEntity.saldo=Listado.get(j).getSaldodocumento();
                        listaClienteDetalleEntity.nuevo_saldo=Listado.get(j).getNuevosaldodocumento();
                        listaClienteDetalleEntity.importe=Listado.get(j).getImportedocumento();
                        if(Listado.get(j).getBancarizacion().equals("Y"))
                        {
                            Estadochkbancarizado="1";
                        }
                        else if(Listado.get(j).getBancarizacion().equals("N"))
                        {
                            Estadochkbancarizado="0";
                        }
                        Log.e("REOS","CobranzaDetalleView-Listado.get(j).getDepositodirecto(): "+Listado.get(j).getDepositodirecto());
                        if(Listado.get(j).getDepositodirecto().equals("Y"))
                        {
                            Estadodepositodirecto="Y";
                        }
                        else if(Listado.get(j).getDepositodirecto().equals("N"))
                        {
                            Estadodepositodirecto="N";
                        }

                        Estadopagopos=SesionEntity.pagopos;
                        Log.e("REOS","CobranzaDetalleView:-HistoricoCobranzaView: "+Listado.size());

                        //String[] fechaCobrado= Listado.get(j).getFechacobranza().split(" ");
                        if(!Listado.get(j).getFechacobranza().isEmpty()){
                            fecha=Listado.get(j).getFechacobranza();
                        }else{
                            Log.e("jpcm","FEHCA LLENA");
                        }
                        if(Listado.get(j).getDocumento_id().equals("")||Listado.get(j).getDocumento_id().equals("0"))
                        {
                            Estadopagoadelantado="1";
                        }else
                        {
                            Estadopagoadelantado="0";
                        }

                       /* String  FechaDiferida="";
                        if(sourceSplitefechadiferida.length>1)
                        {
                            FechaDiferida=sourceSplitefechadiferida[0];
                            String HoraDiferida=sourceSplitefechadiferida[1];
                        }else
                        {
                            FechaDiferida=sourceSplitefechadiferida[0];
                        }

                Log.e("jcpm","sin splip->"+FechaDiferida+" co hora"+Listado.get(j).getFechacobranza());
                        String[] sourceSplitemision2= FechaDiferida.split("-");
                        String anioemision= sourceSplitemision2[0];
                        String mesemision= sourceSplitemision2[1];
                        String diaemision= sourceSplitemision2[2];

                        String fechadiferida=diaemision+"-"+mesemision+"-"+anioemision;

                        //REVISAR HERE line code*/

                        Log.e("jpcm","fecha->"+fecha);
                        //fecha= Listado.get(j).getFechacobranza();//+" "+Listado.get(j).getFechacobranza();
                        listaClienteDetalleEntity.fechaemision="0";
                        listaClienteDetalleEntity.fechavencimiento="0";
                        listaClienteDetalleEntity.direccion="0";
                        listaClienteDetalleEntity.nrodocumento=Listado.get(j).getNro_documento();
                        listaClienteDetalleEntity.documento_id=Listado.get(j).getDocumento_id();
                        StatusCollectionCheck=Listado.get(j).getCollectioncheck();
                        Log.e("REOS","CobranzaDetalleView-ListaHistoricoCobranzaEntity-getCollection:"+Listado.get(j).getCollectioncheck());
                        //listaClienteDetalleEntity.fechaemision=Listado.get(j).getFechacobranza();

                        listaClienteDetalleAdapterFragment.add(listaClienteDetalleEntity);
                        //fecha= Listado.get(j).getFechacobranza();

                    }
            }else{
                Log.e("jpcm","se debio asignar");
                //fecha =obtenerFechaYHoraActual();
                listaClienteDetalleAdapterFragment= (ArrayList<ListaClienteDetalleEntity>)getArguments().getSerializable(TAG_1);
                for(int g=0;g>listaClienteDetalleAdapterFragment.size();g++)
                {
                    cliente_id_visita=listaClienteDetalleAdapterFragment.get(g).getCliente_id();
                    domembarque_id_visita=listaClienteDetalleAdapterFragment.get(g).getDomembarque();
                    zona_id_visita=listaClienteDetalleAdapterFragment.get(g).getZona_id();
                }
            }

            //listaClienteDetalleAdapterFragment.size();
            setHasOptionsMenu(true);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_cobranza_detalle_view, container, false);
        et_cobrado_edit=(EditText)v.findViewById(R.id.et_cobrado_edit);
        //imbplus=(ImageButton)v.findViewById(R.id.imbplus);
        //imbminus=(ImageButton) v.findViewById(R.id.imbminus);
        imbaceptar=(ImageButton)v.findViewById(R.id.imbaceptar);
        imbcancelar=(ImageButton) v.findViewById(R.id.imbcancelar);
        chkpagoadelantado = (CheckBox) v.findViewById(R.id.chkpagoadelantado);
        chk_pago_pos=(CheckBox) v.findViewById(R.id.chk_pago_pos);
        chk_validacionqr = (CheckBox) v.findViewById(R.id.chk_validacionqr);
        tv_recibo = (TextView) v.findViewById(R.id.tv_recibo);
        chk_bancarizado = (CheckBox) v.findViewById(R.id.chk_bancarizado);
        chk_pago_directo = (CheckBox) v.findViewById(R.id.chk_pago_directo);
        chk_E_signature = (CheckBox) v.findViewById(R.id.chk_E_signature);
        tablerow_e_signature=v.findViewById(R.id.tablerow_e_signature);
        chk_collectioncheck  = (CheckBox) v.findViewById(R.id.chk_collectioncheck);
        fab_invoice_cancelation = v.findViewById(R.id.fab_invoice_cancelation);
        //imvprueba = (ImageView) v.findViewById(R.id.imvprueba);
        imbcomentariorecibo = (OmegaCenterIconButton) v.findViewById(R.id.imbcomentariorecibo);
        fab_invoice_cancelation =  (FloatingActionButton) v.findViewById(R.id.fab_invoice_cancelation);
        CircleMenu circleMenu = v.findViewById(R.id.circleMenu);
        fab_edit_signature =  (FloatingActionButton) v.findViewById(R.id.fab_edit_signature);
        imv_prueba_mostrarfirma =  (ImageView) v.findViewById(R.id.imv_prueba_mostrarfirma);
        circleMenu.setVisibility(View.GONE);
        imv_prueba_mostrarfirma.setVisibility(View.GONE);
        fab_edit_signature.setVisibility(View.GONE);
        tablerow_e_signature.setVisibility(View.GONE);
        chk_bancarizado.setEnabled(false);
        fab_invoice_cancelation.setVisibility(View.GONE);

        switch (BuildConfig.FLAVOR){
            case "chile":
                et_cobrado_edit.setInputType(InputType.TYPE_CLASS_NUMBER);
                et_cobrado_edit.setHint ("0");
                break;
        }

        if(!BuildConfig.FLAVOR.equals("chile"))
        {
            fab_invoice_cancelation.setVisibility(View.GONE);
        }

        fab_invoice_cancelation.setOnClickListener(view -> {
                    alertCollectionTotal("Esta Seguro de Realizar el Cobro total del documento?").show();


        });


        imbcomentariorecibo= (OmegaCenterIconButton) v.findViewById(R.id.imbcomentariorecibo);
        imbcomentariorecibo.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       DialogFragment dialogFragment = new CobranzaDetalleDialogController();
                                                       dialogFragment.show(getActivity().getSupportFragmentManager(),"un dialogo");
                                                   }
                                               }
        );

        getActivity().setTitle("Cobranza");
        listViewCobranzaDetalle = (ListView) v.findViewById(R.id.listViewCobranzaCabecera);
        obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();


        if(!(listaClienteDetalleAdapterFragment.isEmpty()))
        {

            for(int i=0;i<listaClienteDetalleAdapterFragment.size();i++)
            {
                if(listaClienteDetalleAdapterFragment.get(i).getNrodocumento()==null)
                {
                    chkpagoadelantado.setChecked(true);
                }
            }

        }

        imbaceptar.setOnClickListener(view -> {
                    String saldo_evaluar="0";
                    if (!chkpagoadelantado.isChecked())
                    {
                        for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {
                            saldo_evaluar = listaClienteDetalleAdapterFragment.get(i).saldo;
                        }
                    }

                    String temporal="";

                    if(et_cobrado_edit.getText()==null || et_cobrado_edit.getText().toString().length()==0){
                        temporal="0";
                    }else{
                        temporal=et_cobrado_edit.getText().toString();
                    }

                    Log.e("Monto=>",""+temporal);
                    BigDecimal montoIngresado=new BigDecimal(temporal);

                    if(!(montoIngresado.compareTo(BigDecimal.ZERO)>0)){
                        et_cobrado_edit.setText(null);
                        Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
                    }else{


                        BigDecimal montoCobrado=new BigDecimal(et_cobrado_edit.getText().toString());

                        Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                        drawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                        menu_variable.findItem(R.id.guardar).setIcon(drawable);
                        guardar.setEnabled(true);
                        Log.e("Monto=>",">"+montoCobrado.toString());

                        if (chkpagoadelantado.isChecked()) {
                            for (int j = 0; j < listaClienteDetalleAdapterFragment.size(); j++) {

                                cobrado=montoCobrado;

                                listaClienteDetalleAdapterFragment.get(j).setCobrado(cobrado.setScale(0,RoundingMode.HALF_UP).toString());
                                listaClienteDetalleAdapterFragment.get(j).setSaldo(String.valueOf("0"));
                                listaClienteDetalleAdapterFragment.get(j).setNuevo_saldo(String.valueOf("0"));
                                listaClienteDetalleAdapterFragment.get(j).setImporte("0");
                                listaClienteDetalleAdapterFragment.get(j).setFechaemision("0");
                                listaClienteDetalleAdapterFragment.get(j).setFechavencimiento("0");
                                listaClienteDetalleAdapterFragment.get(j).setDireccion("0");
                                listaClienteDetalleAdapterFragment.get(j).setNrodocumento("0");
                                listaClienteDetalleAdapterFragment.get(j).setDocumento_id("0");
                                cliente_id_visita=listaClienteDetalleAdapterFragment.get(j).getCliente_id();
                                domembarque_id_visita=listaClienteDetalleAdapterFragment.get(j).getDomembarque();
                                zona_id_visita=listaClienteDetalleAdapterFragment.get(j).getZona_id();

                            }
                            obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                            obtenerWSCobranzaDetalle.execute();


                        } else {
                            Log.e("Monto=>","> NO E SPAGO ADELANTADO");
                            for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {

                                String cobranza = "";

                                if (et_cobrado_edit.getText().toString().equals("")) {
                                    cobranza = "0";
                                } else if (et_cobrado_edit.getText().toString().equals(".")) {
                                    cobranza = "0";
                                } else {
                                    cobranza = et_cobrado_edit.getText().toString();
                                }

                                cobrado=new BigDecimal(cobranza);
                                saldo=new BigDecimal(listaClienteDetalleAdapterFragment.get(i).getSaldo());

                                listaClienteDetalleAdapterFragment.get(i).setSaldo(saldo.setScale(0,RoundingMode.HALF_UP).toString());
                                if (cobrado.compareTo(saldo) <= 0) {
                                    listaClienteDetalleAdapterFragment.get(i).setCobrado(cobrado.setScale(0,RoundingMode.HALF_UP).toString());
                                    nuevo_saldo = saldo.subtract(cobrado);

                                    listaClienteDetalleAdapterFragment.get(i).setNuevo_saldo(nuevo_saldo.setScale(0,RoundingMode.HALF_UP).toString());

                                } else {
                                    Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();

                                }
                                obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                                obtenerWSCobranzaDetalle.execute();
                            }
                        }

                    }
                }
        );
        imbcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++){
                    et_cobrado_edit.setText(null);
                    listaClienteDetalleAdapterFragment.get(i).setCobrado(String.valueOf("0"));
                    listaClienteDetalleAdapterFragment.get(i).setNuevo_saldo(String.valueOf("0"));
                }

                Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
                menu_variable.findItem(R.id.guardar).setIcon(drawable);
                guardar.setEnabled(false);

                chk_bancarizado.setChecked(false);
                obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                obtenerWSCobranzaDetalle.execute();
            }});




        if (getArguments() != null) {
            if(!(Listado==null)) {
                tv_recibo.setText(recibo_generado);
                if (chkqrvalidado.equals("False")) {
                    chk_validacionqr.setChecked(false);


                } else if (chkqrvalidado.equals("True")) {
                    chk_validacionqr.setChecked(true);
                }

                if(Estadopagopos!=null && Estadopagopos.equals("Y")){
                    chk_pago_pos.setChecked(true);
                }else{
                    chk_pago_pos.setChecked(false);
                }

                if(Estadopagoadelantado.equals("1"))
                {
                    chkpagoadelantado.setChecked(true);
                }


            }else
            {

                if(SesionEntity.pagodirecto.equals("Y"))
                {
                    chk_pago_directo.setChecked(true);
                    chk_bancarizado.setEnabled(false);
                    chk_bancarizado.setChecked(false);
                    chk_bancarizado.setFocusable(false);
                    chk_bancarizado.setClickable(false);

                }
                else
                {
                    chk_pago_directo.setChecked(false);
                }
            }
        }

        if((Listado==null))
        {
            if(SesionEntity.pagopos.equals("Y"))
            {
                chk_pago_pos.setChecked(true);
            }
            else
            {
                chk_pago_pos.setChecked(false);
            }
        }
        Conexion="1";

        chk_bancarizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean estado=chk_bancarizado.isChecked();
                if(chk_bancarizado.isChecked()){
                    createSimpleDialog(estado).show();
                }else{
                    createSimpleDialog(estado).show();
                }

            }});

        if(Estadochkbancarizado.equals("1"))
        {
            chk_bancarizado.setChecked(true);
            chk_bancarizado.setFocusable(false);
            chk_bancarizado.setClickable(false);
            //chk_bancarizado.setEnabled(false);

        }
        else if(Estadochkbancarizado.equals("0"))
        {
            chk_bancarizado.setChecked(false);
            chk_bancarizado.setFocusable(false);
            chk_bancarizado.setClickable(false);
        }
        if(Estadodepositodirecto.equals("Y"))
        {
            chk_pago_directo.setChecked(true);
            //chk_bancarizado.setFocusable(false);
            //chk_bancarizado.setClickable(false);
            //chk_bancarizado.setEnabled(false);

        }
        else if(Estadodepositodirecto.equals("N"))
        {
            chk_pago_directo.setChecked(false);
            //chk_bancarizado.setFocusable(false);
            //chk_bancarizado.setClickable(false);
        }
        Log.e("REOS","CobranzaDetalleView-StatusCollectionCheck:"+StatusCollectionCheck);
        if(StatusCollectionCheck.equals("Y"))
        {
            chk_collectioncheck.setChecked(true);
        }else {
            chk_collectioncheck.setChecked(false);
        }
        obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
        obtenerWSCobranzaDetalle.execute();


        return v;
    }

    public Dialog changeFormatKeyboard() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("URGENTE!");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("El lenguaje del equipo debe ser\nEspañol Perú o EE.UU., configurelo y\n vuelva a intentarlo...");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setText("CONFIGURAR AHORA");
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS);
                startActivity(i);
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    public AlertDialog createSimpleDialog(final boolean estado) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        if(estado)
        {
            builder.setTitle("Advertencia")
                    .setMessage("Esta Seguro de Marcar el Recibo como Bancarizado")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                    .setNegativeButton("CANCELAR",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    chk_bancarizado.setChecked(false);
                                }
                            });
        }
        else
        {
            builder.setTitle("Advertencia")
                    .setMessage("Esta Seguro de DesMarcar el Recibo como Bancarizado")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                    .setNegativeButton("CANCELAR",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    chk_bancarizado.setChecked(true);
                                }
                            });
        }
        return builder.create();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.menu_cobranza_detalle, menu);
        guardar = menu.findItem(R.id.guardar);
        generarpdf = menu.findItem(R.id.generarpdf);
        validarqr = menu.findItem(R.id.validarqr);
        edit_signature = menu.findItem(R.id.edit_signature);

        edit_signature.setVisible(false);
        menu_variable=menu;




        if (getArguments() != null) {
            if(!(Listado==null)){
                guardar.setEnabled(false);
                generarpdf.setEnabled(true);
                validarqr.setEnabled(true);
                Drawable drawable = menu.findItem(R.id.guardar).getIcon();
                drawable = DrawableCompat.wrap(drawable);

                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
                menu.findItem(R.id.guardar).setIcon(drawable);
                Drawable drawable2 = menu.findItem(R.id.generarpdf).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.white));
                menu.findItem(R.id.generarpdf).setIcon(drawable2);
                Drawable drawable3 = menu.findItem(R.id.validarqr).getIcon();
                drawable3 = DrawableCompat.wrap(drawable3);
                DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.white));
                menu.findItem(R.id.validarqr).setIcon(drawable3);
                imbaceptar.setEnabled(false);
                imbcancelar.setEnabled(false);
                et_cobrado_edit.setEnabled(false);
                imbcomentariorecibo.setEnabled(false);

            }else{
                comentario="";
                Drawable drawable = menu.findItem(R.id.guardar).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
                menu.findItem(R.id.guardar).setIcon(drawable);
                Drawable drawable2 = menu.findItem(R.id.generarpdf).getIcon();
                drawable2 = DrawableCompat.wrap(drawable2);
                DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.Black));
                menu.findItem(R.id.generarpdf).setIcon(drawable2);
                Drawable drawable3 = menu.findItem(R.id.validarqr).getIcon();
                drawable3 = DrawableCompat.wrap(drawable3);
                DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.Black));
                menu.findItem(R.id.validarqr).setIcon(drawable3);
                //imbcomentariorecibo.setColorFilter(Color.BLUE);

            }
        }

        vinculaimpresora="1"; ///para chile no se requiere impresora

        if(vinculaimpresora.equals("0"))
        {
            generarpdf.setIcon(R.drawable.ic_warning_yellow_24dp);
            Toast.makeText(getContext(), "Advertencia: Impresora No Vinculada - Revisar en Configuracion"+comentario, Toast.LENGTH_SHORT).show();
        }

        super.onCreateOptionsMenu(menu, inflater);

    }



    private class ObtenerWSCobranzaDetalle extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            return "1";
        }

        protected void onPostExecute(Object result){
            listaCobranzaDetalleAdapter = new ListaCobranzaDetalleAdapter(getActivity(), ListaCobranzaDetalleDao.getInstance().getLeads(listaClienteDetalleAdapterFragment));
            listViewCobranzaDetalle.setAdapter(listaCobranzaDetalleAdapter);

        }

        protected ArrayList<ListaClienteDetalleEntity>  llenarlista(ArrayList<ListaClienteDetalleEntity> listaClienteDetalleAdapter)
        {

            for(int i=0; i<listaClienteDetalleAdapter.size();i++)
            {

            }

            return listaClienteDetalleAdapter;
        }
    }

    @Override
    public void onAttach(Context context) {
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
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.guardar:
                if(SesionEntity.fuerzatrabajo_id==null && SesionEntity.nombrefuerzadetrabajo==null && SesionEntity.compania_id==null){
                    //if(true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Advertencia")
                            .setMessage("Tu sesión a expirado")
                            .setPositiveButton("INICIAR SESIóN",
                                    (dialog, which) -> {
                                        Intent i = getContext().getPackageManager().getLaunchIntentForPackage( getContext().getPackageName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        return;
                                    });

                    builder.show();
                }else{
                    fecha =obtenerFechaActual();
                    guardar.setEnabled(false);
                    alertaGuardarCobranza("Esta seguro de guardar la cobranza?").show();
                }
                return false;
            case R.id.generarpdf:
                alertaGenerarPDF("Esta seguro de generar el archivo PDF?").show();
                return true;
            case R.id.validarqr:
                alertaValidarQR("Iniciar validacion QR?").show();
                return false;
            default:
                break;
        }

        return false;
    }

    public AlertDialog alertaGuardarCobranza_() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Guardar la Cobranza?")
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            if(et_cobrado_edit.getText().toString().equals("0")){
                                Toast.makeText(getContext(), "No puedes Guardar un Recibo con valor 0 ", Toast.LENGTH_SHORT).show();
                            }else{

                                if(vinculaimpresora.equals("0")){
                                    //generarpdf.setEnabled(true);
                                    Toast.makeText(getContext(), "Impresora No Vinculada - Favor de Vincular para proseguir", Toast.LENGTH_SHORT).show();
                                }else{
                                    switch(SesionEntity.pagopos) {
                                        case "Y":
                                            tipocobranza = "Cobranza/Deposito";
                                            Log.e("REOS", "CobranzaDetalleView-alertaGuardarCobranza-SesionEntity.pagopos:Cobranza/Deposito" + SesionEntity.pagopos);
                                            resultado = GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment, tipocobranza);
                                            break;
                                        case "N":
                                            tipocobranza = "Cobranza";
                                            Log.e("REOS", "CobranzaDetalleView-alertaGuardarCobranza-SesionEntity.pagopos:Cobranza" + SesionEntity.pagopos);
                                            resultado = GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment, tipocobranza);
                                            break;
                                    }
                                    if(resultado>0){
                                        guardar.setEnabled(false);
                                        //cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id,result.toString());
                                        Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                                        drawable = DrawableCompat.wrap(drawable);
                                        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
                                        menu_variable.findItem(R.id.guardar).setIcon(drawable);
                                        Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                        drawable2 = DrawableCompat.wrap(drawable2);
                                        DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.white));
                                        menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                                        //guardar.setEnabled(false);
                                        //imbcomentariorecibo.setColorFilter(Color.BLACK);
                                        imbcomentariorecibo.setEnabled(false);

                                        if(vinculaimpresora.equals("1"))
                                        {
                                            generarpdf.setEnabled(true);
                                        }

                                        tv_recibo.setText(recibo);
                                        imbaceptar.setEnabled(false);
                                        imbcancelar.setEnabled(false);
                                        et_cobrado_edit.setEnabled(false);

                                        Toast.makeText(getContext(), "Se Guardo Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(getContext(), "Se Guardo Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
                                        //enviarWSCobranzaDetalle =  new EnviarWSCobranzaDetalle();
                                        //enviarWSCobranzaDetalle.execute();


                                    }else
                                    {
                                        Toast.makeText(getContext(), "No se Guardo la Cobranza Comunicarse con El Administrador", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }


                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                guardar.setEnabled(true);
                            }
                        });

        return builder.create();
    }

    public AlertDialog alertaGenerarPDF_() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Generar el Archivo PDF?")
                .setPositiveButton("OK",

                        (dialog, which) -> {

                            if(listaClienteDetalleAdapterFragment.size()>0)
                            {
                                // SesionEntity.imagen="R"+recibo;
                                // fecha =obtenerFechaYHoraActual();




                                //documentPDFController.showPdfFile(recibo+".pdf",getContext());
                                File file = new File(Environment
                                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                        NOMBRE_DIRECTORIO);
                                String Cadenafile="";
                                Cadenafile=String.valueOf(file);
                                String ruta=Cadenafile+"/"+recibo+".pdf";


                                // MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                                String correlativo="";
                                ConfiguracionSQLiteDao configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
                                ArrayList<ConfiguracionSQLEntity> arraylistconfiguracion =  new ArrayList<ConfiguracionSQLEntity>();
                                arraylistconfiguracion=configuracionSQLiteDao.ObtenerCorrelativoConfiguracion();

                                for(int i=0;i<arraylistconfiguracion.size();i++)
                                {
                                    correlativo=arraylistconfiguracion.get(i).getSecuenciarecibos();
                                }

                                configuracionSQLiteDao.ActualizaCorrelativo(String.valueOf(Integer.parseInt(correlativo)-1));

                                //btn item validar QR se queda en disabled
                                /*Drawable drawable = menu_variable.findItem(R.id.validarqr).getIcon();
                                drawable = DrawableCompat.wrap(drawable);
                                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.white));
                                menu_variable.findItem(R.id.validarqr).setIcon(drawable);

                                Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                drawable2 = DrawableCompat.wrap(drawable2);
                                DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.Black));
                                menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);

                                validarqr.setEnabled(true);

                                 //no aplica validar qr //peru si
                                 */


                                documentoCobranzaPDF.generarPdf(getContext(), listaClienteDetalleAdapterFragment,SesionEntity.fuerzatrabajo_id,SesionEntity.nombrefuerzadetrabajo,recibo,fecha,obtenerHoraActual());
                                ///////////////  /ENVIAR RECIBOS PENDIENTE CON DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\
                                cobranzaRepository.depositedPendingCollection(getContext()).observe(getActivity(), data -> {
                                    Log.e("REOS-ParametrosView-depositedPendingCollection","=>"+data);
                                });
                                //MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                                Toast.makeText(getContext(), "Se creo tu archivo pdf", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), "No se creo el archivo pdf", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        (dialog, which) -> {
                            //listener.onNegativeButtonClick();
                        });

        return builder.create();
    }

    public AlertDialog alertaValidarQR_() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Iniciar validacion QR?")
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            SesionEntity.imagen="R"+recibo;
                            final Activity activity = getActivity();
                            //encenderFlash();
                            IntentIntegrator integrator = new IntentIntegrator(activity);
                            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

                            integrator.setPrompt("Scan");
                            integrator.setCameraId(0);
                            integrator.setBeepEnabled(true);
                            integrator.setBarcodeImageEnabled(false);
                            integrator.setOrientationLocked(true);
                            integrator.initiateScan();

                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //listener.onNegativeButtonClick();
                            }
                        });

        return builder.create();
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
        void onFragmentInteraction(String TAG, Object data);
    }


    public int GuardarCobranzaSQLite(ArrayList<ListaClienteDetalleEntity> Lista, String tipoCobranza)
    {
        int resultado=0,recibows=0;
        String tag = "", tag2 = "", cliente_id = "", shipto = "", montocobrado = "", qrvalidado = "N", telefono = "",cardname="",valorcobranza="0",chkruta="",contado="0";
        FormulasController formulasController=new FormulasController(getContext());
        Random numAleatorio = new Random();
        int n = numAleatorio.nextInt(9999 + 1000 + 1) + 1000;
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        for (int k = 0; k < Lista.size(); k++) {
            valorcobranza=Lista.get(k).getCobrado();
            chkruta=Lista.get(k).getChkruta();

            if(Lista.get(k).getFechaemision().equals(Lista.get(k).getFechavencimiento()))
            {
                contado="1";
            }
            else
            {
                contado="0";
            }
        }

        if(Float.parseFloat(valorcobranza)>=1) {
        cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
        correlativorecibo=cobranzaDetalleSQLiteDao.ObtenerUltimoRecibo(ObjUsuario.compania_id, ObjUsuario.usuario_id);


        String[] separada = SesionEntity.recibo.split("R");
        if(SesionEntity.recibo.equals("0"))
        {
            tag2=SesionEntity.recibo;
        }
        else
        {
            if(separada.length>1)
            {
                tag=separada[0];
                tag2=separada[1];
            }
            else
            {
                tag=separada[0];
            }
        }

        if(tag.equals(""))
        {
            tag="0";
        }


        recibows=Integer.parseInt(tag);
        if(correlativorecibo>=recibows)
        {
            ultimocorrelativorecibo=correlativorecibo;
        }
        else
        {
            ultimocorrelativorecibo=recibows;
        }

        String bancarizado="";
        if(chk_bancarizado.isChecked())
        {
            bancarizado="Y";
        }
        else
        {
            bancarizado="N";
        }

        if(tipoCobranza.equals("Cobranza"))
        {
            for (int i = 0; i < Lista.size(); i++) {
                montocobrado=Lista.get(i).getCobrado();
                cliente_id=String.valueOf(Lista.get(i).getCliente_id());
                cardname = Lista.get(i).getNombrecliente();
                shipto=Lista.get(i).getDomembarque();
                recibo = String.valueOf(ultimocorrelativorecibo + 1);
                resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                        FormulasController.ObtenerFechaHoraCadena(),
                        String.valueOf(Lista.get(i).getCliente_id()),
                        String.valueOf(Lista.get(i).getDocumento_id()),
                        SesionEntity.compania_id,
                        String.valueOf(Lista.get(i).getImporte()),
                        String.valueOf(Lista.get(i).getSaldo()),
                        String.valueOf(Lista.get(i).getNuevo_saldo()),
                        String.valueOf(Lista.get(i).getCobrado()),
                        //fechaCobro
                        fecha,
                        recibo,
                        String.valueOf(Lista.get(i).getNrodocumento()),
                        SesionEntity.fuerzatrabajo_id,
                        bancarizado,
                        //Peru - Cambio necesario para letras
                        //------------------
                        //"0",
                        String.valueOf(Lista.get(i).getNrodocumento()),
                        //------------------
                        SesionEntity.usuario_id,
                        comentario,
                        "N",
                        "Y",
                        "",
                        "N",
                        "N",
                        "N",
                        SesionEntity.pagodirecto,
                        SesionEntity.pagopos,
                        "",
                        "",
                        obtenerHoraActual(),
                        cardname,
                        String.valueOf(n),
                        Lista.get(i).getDocentry(),
                        SesionEntity.collectioncheck
                        ,""
                        ,"N"
                        ,""
                );

                        if(SesionEntity.perfil_id.equals("CHOFER")){
                        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());
                        detailDispatchSheetSQLite.UpdateBalanceDetailDispatchSheet(ObjUsuario.compania_id,
                                String.valueOf(Lista.get(i).getDocumento_id()),
                                String.valueOf(Lista.get(i).getNuevo_saldo()));

                        ActualizaDocumentoDeuda(ObjUsuario.compania_id,
                                String.valueOf(Lista.get(i).getDocumento_id()),
                                String.valueOf(Lista.get(i).getNuevo_saldo()),
                                Lista.get(i).getNrodocumento()
                                );

                    }else {
                        ActualizaDocumentoDeuda(ObjUsuario.compania_id,
                                String.valueOf(Lista.get(i).getDocumento_id()),
                                String.valueOf(Lista.get(i).getNuevo_saldo()),
                                Lista.get(i).getNrodocumento()
                        );
                    }

            }
        }
        else if(tipoCobranza.equals("Cobranza/Deposito"))
        {
            String bank_id="";
            ArrayList<BancoSQLiteEntity> listBancoSQLiteEntity=new ArrayList<>();
            BancoSQLite bancoSQLite=new BancoSQLite(getContext());
            listBancoSQLiteEntity = bancoSQLite.ObtenerBancoPOS();
            for(int j=0;j<listBancoSQLiteEntity.size();j++)
            {
                bank_id=listBancoSQLiteEntity.get(j).getBanco_id();
            }
            String sumacobrado="";
            for (int i = 0; i < Lista.size(); i++) {
                montocobrado=Lista.get(i).getCobrado();
                cliente_id=String.valueOf(Lista.get(i).getCliente_id());
                cardname = Lista.get(i).getNombrecliente();
                shipto=Lista.get(i).getDomembarque();
                recibo = String.valueOf(ultimocorrelativorecibo + 1);
                sumacobrado=String.valueOf(Lista.get(i).getCobrado());
                resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                        FormulasController.ObtenerFechaHoraCadena(),
                        String.valueOf(Lista.get(i).getCliente_id()),
                        String.valueOf(Lista.get(i).getDocumento_id()),
                        SesionEntity.compania_id,
                        String.valueOf(Lista.get(i).getImporte()),
                        String.valueOf(Lista.get(i).getSaldo()),
                        String.valueOf(Lista.get(i).getNuevo_saldo()),
                        sumacobrado,
                        fecha,
                        recibo,
                        String.valueOf(Lista.get(i).getNrodocumento()),
                        SesionEntity.fuerzatrabajo_id,
                        bancarizado,
                        "",
                        SesionEntity.usuario_id,
                        comentario,
                        "Y",
                        "Y",
                        bank_id,
                        "N",
                        "N",
                        "N",
                        SesionEntity.pagodirecto,
                        SesionEntity.pagopos,
                        "",
                        "",
                        obtenerHoraActual(),
                        cardname,
                        String.valueOf(n),
                        Lista.get(i).getDocentry(),
                        SesionEntity.collectioncheck
                        ,""
                        ,"N"
                        ,""
                );
                        if(SesionEntity.perfil_id.equals("CHOFER")){
                        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());
                        detailDispatchSheetSQLite.UpdateBalanceDetailDispatchSheet(ObjUsuario.compania_id,
                                String.valueOf(Lista.get(i).getDocumento_id()),
                                String.valueOf(Lista.get(i).getNuevo_saldo())

                        );

                        ActualizaDocumentoDeuda(ObjUsuario.compania_id,
                                String.valueOf(Lista.get(i).getDocumento_id()),
                                String.valueOf(Lista.get(i).getNuevo_saldo()),
                                Lista.get(i).getNrodocumento()
                        );

                    }else {
                        ActualizaDocumentoDeuda(ObjUsuario.compania_id,
                                String.valueOf(Lista.get(i).getDocumento_id()),
                                String.valueOf(Lista.get(i).getNuevo_saldo()),
                                Lista.get(i).getNrodocumento()
                        );
                    }
            }
            addDepositPOS(sumacobrado,bank_id);
        }
        chk_bancarizado.setFocusable(false);
        chk_bancarizado.setClickable(false);


        VisitaSQLiteEntity visita=new VisitaSQLiteEntity();
        visita.setCardCode(cliente_id);
        visita.setAddress(shipto);
        visita.setType("02");
        visita.setObservation("Se genero el recibo "+recibo+" para el cliente: "+cliente_id);
        visita.setLatitude(""+latitude);
        visita.setLongitude(""+longitude);
        visita.setStatusRoute(chkruta);
        visita.setAmount(valorcobranza);
        visita.setTerminoPago_ID(contado);
        formulasController.RegistraVisita(visita,getActivity(),montocobrado);

        /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        //UpdateSendReceipt();
        cobranzaRepository.UndepositedPendingCollection(getContext()).observe(getActivity(), data -> {
            Log.e("Jepicame","=>"+data);
        });
        ///////////////////////////// ENVIAR DEPOSITOS \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        depositoRepository.depositResend(getContext()).observe(getActivity(), data -> {
            Log.e("Jepicame", "=>" + data);
        });
        }
        else {
            et_cobrado_edit.setText(null);
            Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido, verifique su cobranza por:  "+valorcobranza, Toast.LENGTH_SHORT).show();
            Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
            menu_variable.findItem(R.id.guardar).setIcon(drawable);
            guardar.setEnabled(false);
        }
        return resultado;

    }

    public int ActualizaDocumentoDeuda(String compania_id,String documento_id,String nuevo_saldo,String nrofactura)
    {
        int resultado=0;
        resultado= documentoSQLite.ActualizaNuevoSaldo(compania_id,documento_id,nuevo_saldo,nrofactura);
        return resultado;
    }


    private void UpdateSendReceipt()
    {
        Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
        menu_variable.findItem(R.id.guardar).setIcon(drawable);
        Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
        drawable2 = DrawableCompat.wrap(drawable2);
        DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.white));
        menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
        imbcomentariorecibo.setEnabled(false);
        if(vinculaimpresora.equals("1"))
        {
            generarpdf.setEnabled(true);
        }
        tv_recibo.setText(recibo);
        imbaceptar.setEnabled(false);
        imbcancelar.setEnabled(false);
        et_cobrado_edit.setEnabled(false);
        Toast.makeText(getContext(), "Se Guardo Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
    }



    private void addDepositPOS(String montocobrado,String bank_id)
    {
        CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao=new CobranzaCabeceraSQLiteDao(getContext());
        // BigDecimal bigDecimal=new BigDecimal(et_cobrado.toString());

        String Validacion="";
        int ValidaSQLite=0;
        String bancarizado="";
        if(chk_bancarizado.isChecked())
        {
            bancarizado="Y";
        }
        else
        {
            bancarizado="N";
        }
        ValidaSQLite=cobranzaCabeceraSQLiteDao.InsertaCobranzaCabecera(
                SesionEntity.fuerzatrabajo_id+recibo,
                SesionEntity.usuario_id,
                SesionEntity.fuerzatrabajo_id,
                bank_id,
                SesionEntity.compania_id,
                montocobrado,
                "Deposito",
                bancarizado,
                "19000101",
                fecha,
                SesionEntity.pagodirecto,
                "Y"
        );

        if(ValidaSQLite==1)
        {
            Validacion=String.valueOf(ValidaSQLite);

            cobranzaDetalleSQLiteDao.ActualizaCobranzaDetalle(
                    SesionEntity.fuerzatrabajo_id+recibo,
                    recibo,
                    SesionEntity.compania_id,
                    bank_id
            );

        }

        Toast.makeText(getContext(), "Deposito Registrado Correctamente", Toast.LENGTH_SHORT).show();
    }

    public int aceptarQR()
    {
        int resultado=1;
        chk_validacionqr.setChecked(true);
        return resultado;

    }

    private Dialog alertCollectionTotal(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_advertencia);
        dialog.setCanceledOnTouchOutside(false);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK =dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel =  dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            String saldo_evaluar="0";
            if (!chkpagoadelantado.isChecked())
            {
                for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {
                    saldo_evaluar = listaClienteDetalleAdapterFragment.get(i).getSaldo();
                }
            }

            String temporal=saldo_evaluar;

                    /*if(et_cobrado_edit.getText()==null || et_cobrado_edit.getText().toString().length()==0){
                        temporal="0";
                    }else{
                        temporal=et_cobrado_edit.getText().toString();
                    }*/

            Log.e("Monto=>",""+temporal);
            BigDecimal montoIngresado=new BigDecimal(temporal).setScale(0,RoundingMode.HALF_UP);

            if(!(montoIngresado.compareTo(BigDecimal.ZERO)>0)){
                et_cobrado_edit.setText(null);
                Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
            }else{


                BigDecimal montoCobrado=new BigDecimal(temporal).setScale(0,RoundingMode.HALF_UP);

                Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                menu_variable.findItem(R.id.guardar).setIcon(drawable);
                guardar.setEnabled(true);
                Log.e("Monto=>",">"+montoCobrado.toString());

                if (chkpagoadelantado.isChecked()) {
                    for (int j = 0; j < listaClienteDetalleAdapterFragment.size(); j++) {

                        cobrado=montoCobrado;

                        listaClienteDetalleAdapterFragment.get(j).setCobrado(cobrado.setScale(0,RoundingMode.HALF_UP).toString());
                        listaClienteDetalleAdapterFragment.get(j).setSaldo(String.valueOf("0"));
                        listaClienteDetalleAdapterFragment.get(j).setNuevo_saldo(String.valueOf("0"));
                        listaClienteDetalleAdapterFragment.get(j).setImporte("0");
                        listaClienteDetalleAdapterFragment.get(j).setFechaemision("0");
                        listaClienteDetalleAdapterFragment.get(j).setFechavencimiento("0");
                        listaClienteDetalleAdapterFragment.get(j).setDireccion("0");
                        listaClienteDetalleAdapterFragment.get(j).setNrodocumento("0");
                        listaClienteDetalleAdapterFragment.get(j).setDocumento_id("0");
                        cliente_id_visita=listaClienteDetalleAdapterFragment.get(j).getCliente_id();
                        domembarque_id_visita=listaClienteDetalleAdapterFragment.get(j).getDomembarque();
                        zona_id_visita=listaClienteDetalleAdapterFragment.get(j).getZona_id();

                    }
                    obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                    obtenerWSCobranzaDetalle.execute();


                } else {
                    Log.e("Monto=>","> NO E SPAGO ADELANTADO");
                    for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {

                        String cobranza = "";

                        if (temporal.toString().equals("")) {
                            cobranza = "0";
                        } else if (temporal.toString().equals(".")) {
                            cobranza = "0";
                        } else {
                            cobranza = temporal.toString();
                        }

                        cobrado=new BigDecimal(cobranza);
                        saldo=new BigDecimal(listaClienteDetalleAdapterFragment.get(i).getSaldo());

                        listaClienteDetalleAdapterFragment.get(i).setSaldo(saldo.setScale(0,RoundingMode.HALF_UP).toString());
                        if (cobrado.compareTo(saldo) <= 0) {
                            listaClienteDetalleAdapterFragment.get(i).setCobrado(cobrado.setScale(0,RoundingMode.HALF_UP).toString());
                            nuevo_saldo = saldo.subtract(cobrado);

                            listaClienteDetalleAdapterFragment.get(i).setNuevo_saldo(nuevo_saldo.setScale(0,RoundingMode.HALF_UP).toString());

                        } else {
                            Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();

                        }
                        obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                        obtenerWSCobranzaDetalle.execute();
                    }
                }

            }

            et_cobrado_edit.setText(montoIngresado.toString());
            dialog.dismiss();
        });

        dialogButtonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertaGuardarCobranza(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_advertencia);
        dialog.setCanceledOnTouchOutside(false);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK =dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel =  dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            if(et_cobrado_edit.getText().toString().equals("0")){
                Toast.makeText(getContext(), "No puedes Guardar un Recibo con valor 0 ", Toast.LENGTH_SHORT).show();
            }else{

                if(vinculaimpresora.equals("0")){
                    //generarpdf.setEnabled(true);
                    Toast.makeText(getContext(), "Impresora No Vinculada - Favor de Vincular para proseguir", Toast.LENGTH_SHORT).show();
                }else{
                    switch(SesionEntity.pagopos) {
                        case "Y":
                            tipocobranza = "Cobranza/Deposito";
                            Log.e("REOS", "CobranzaDetalleView-alertaGuardarCobranza-SesionEntity.pagopos:Cobranza/Deposito" + SesionEntity.pagopos);
                            resultado = GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment, tipocobranza);
                            break;
                        case "N":
                            tipocobranza = "Cobranza";
                            Log.e("REOS", "CobranzaDetalleView-alertaGuardarCobranza-SesionEntity.pagopos:Cobranza" + SesionEntity.pagopos);
                            resultado = GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment, tipocobranza);
                            break;
                    }
                    if(resultado>0){
                        guardar.setEnabled(false);
                        //cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id,result.toString());
                        Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                        drawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
                        menu_variable.findItem(R.id.guardar).setIcon(drawable);
                        Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                        drawable2 = DrawableCompat.wrap(drawable2);
                        DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.white));
                        menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                        //guardar.setEnabled(false);
                        //imbcomentariorecibo.setColorFilter(Color.BLACK);
                        imbcomentariorecibo.setEnabled(false);

                        if(vinculaimpresora.equals("1"))
                        {
                            generarpdf.setEnabled(true);
                        }

                        tv_recibo.setText(recibo);
                        imbaceptar.setEnabled(false);
                        imbcancelar.setEnabled(false);
                        et_cobrado_edit.setEnabled(false);

                        Toast.makeText(getContext(), "Se Guardo Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
                    }else
                    {
                        Toast.makeText(getContext(), "No se Guardo la Cobranza Comunicarse con El Administrador", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            dialog.dismiss();
        });

        dialogButtonCancel.setOnClickListener(v -> {
            guardar.setEnabled(true);
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertaGenerarPDF(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_advertencia);
        dialog.setCanceledOnTouchOutside(false);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK =dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel =  dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {

            if(listaClienteDetalleAdapterFragment.size()>0)
            {
                File file = new File(Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        NOMBRE_DIRECTORIO);
                String Cadenafile="";
                Cadenafile=String.valueOf(file);
                String ruta=Cadenafile+"/"+recibo+".pdf";


                // MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                String correlativo="";
                ConfiguracionSQLiteDao configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
                ArrayList<ConfiguracionSQLEntity> arraylistconfiguracion =  new ArrayList<ConfiguracionSQLEntity>();
                arraylistconfiguracion=configuracionSQLiteDao.ObtenerCorrelativoConfiguracion();

                for(int i=0;i<arraylistconfiguracion.size();i++)
                {
                    correlativo=arraylistconfiguracion.get(i).getSecuenciarecibos();
                }

                configuracionSQLiteDao.ActualizaCorrelativo(String.valueOf(Integer.parseInt(correlativo)-1));
                documentoCobranzaPDF.generarPdf(getContext(), listaClienteDetalleAdapterFragment,SesionEntity.fuerzatrabajo_id,SesionEntity.nombrefuerzadetrabajo,recibo,fecha,obtenerHoraActual());
                ///////////////  /ENVIAR RECIBOS PENDIENTE CON DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\
                cobranzaRepository.depositedPendingCollection(getContext()).observe(getActivity(), data -> {
                    Log.e("REOS-ParametrosView-depositedPendingCollection","=>"+data);
                });
                //MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                Toast.makeText(getContext(), "Se creo tu archivo pdf", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "No se creo el archivo pdf", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });

        dialogButtonCancel.setOnClickListener(v -> {
            guardar.setEnabled(true);
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertaValidarQR(String texto) {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_advertencia);
        dialog.setCanceledOnTouchOutside(false);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK =dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel =  dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            SesionEntity.imagen="R"+recibo;
            final Activity activity = getActivity();
            //encenderFlash();
            IntentIntegrator integrator = new IntentIntegrator(activity);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(false);
            integrator.setOrientationLocked(true);
            integrator.initiateScan();
            dialog.dismiss();
        });

        dialogButtonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
}

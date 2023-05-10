package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.CifradoController.md5;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

/*
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
*/

import android.os.Environment;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Base64;
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
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonSyntaxException;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.CobranzaDetalleDialogController;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;
import com.vistony.salesforce.Controller.Utilitario.Canvas;
import com.vistony.salesforce.Controller.Utilitario.DocumentoCobranzaPDF;
import com.vistony.salesforce.Controller.Adapters.ListaCobranzaDetalleAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSMSSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Dao.Adapters.ListaCobranzaDetalleDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
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

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import io.sentry.Sentry;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CobranzaDetalleView extends Fragment {
    //ListaClienteDetalleAdapter listaClienteDetalleAdapter;
    ArrayList<ListaClienteDetalleEntity> listaClienteDetalleAdapterFragment;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleEntities;
    ListaCobranzaDetalleAdapter listaCobranzaDetalleAdapter;
    ListView listViewCobranzaDetalle;
    ImageButton imbplus, imbminus, imbaceptar, imbcancelar, imbcancelar2;
    OmegaCenterIconButton imbcomentariorecibo;

    EditText et_cobrado_edit;
    View v;
    ObtenerWSCobranzaDetalle obtenerWSCobranzaDetalle;

    BigDecimal cobrado, nuevo_saldo, saldo;

    // float cobrado=0,nuevo_saldo=0,saldo=0;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;

    String fecha, et_cobrado, chkqrvalidado, recibo_generado;
    static public String recibo;
    DocumentoCobranzaPDF documentoCobranzaPDF;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";

    private String mParam1;
    private String mParam2;
    static String TAG_1 = "text";
    String texto = null;
    DocumentoSQLite documentoSQLite;
    int resultado = 0, correlativorecibo = 0, ultimocorrelativorecibo = 0, correlativorecibows = 0;
    private OnFragmentInteractionListener mListener;
    static public ImageView imvprueba;
    CheckBox chkpagoadelantado, chk_bancarizado, chk_pago_directo, chk_pago_pos,chk_E_signature;
    MenuItem generarpdf, validarqr, guardar,edit_signature;
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
    public static String comentario = "";
    int resultadows = 0;
    String resultadoenviows = "0";
    Menu menu_variable;
    ConfiguracionSQLiteDao configuracionSQLiteDao;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLEntity;
    String vinculaimpresora;
    UsuarioSQLite usuarioSQLite;
    String Conexion = "", tipocobranza = "";
    ArrayList<UsuarioSQLiteEntity> listaUsuarioSQLiteEntity;
    ArrayList<ListaHistoricoCobranzaEntity> Listado = new ArrayList<>();
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 2;
    String Estadochkbancarizado = "", Estadodepositodirecto = "", Estadopagopos = "", Estadopagoadelantado = "";

    static private CobranzaRepository cobranzaRepository;
    private DepositoRepository depositoRepository;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    String cliente_id_visita, domembarque_id_visita, zona_id_visita;
    private ProgressDialog pd;
    static Context context;
    static FloatingActionButton fab_invoice_cancelation,fab_edit_signature;
    String arrayCircle[] = {"Guardar","Generar","Validar","Firma"};
    ImageView imv_prueba_mostrarfirma;
    TableRow tablerow_e_signature;
    String type_description;

    public static Fragment newInstanciaComentario(String param1) {
        Log.e("jpcm", "Este es NUEVA ISNTANCIA 1");
        CobranzaDetalleView fragment = new CobranzaDetalleView();
        comentario = param1;
        return fragment;
    }

    public static CobranzaDetalleView newInstance(String param1) {
        Log.e("jpcm", "Este es NUEVA ISNTANCIA 2 CONSULTA COBRADO");
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
        args.putSerializable(ARG_PARAM1, Lista);
        fragment.setArguments(args);
        return fragment;
    }

    public static CobranzaDetalleView newInstance(Object objeto) {
        //Log.e("jpcm","Este es NUEVA ISNTANCIA 3 ESTO VA PARA CLIENTE VERIFICADO");

        ListenerBackPress.setCurrentFragment("FormDetalleCobranzaCliente");

        CobranzaDetalleView fragment = new CobranzaDetalleView();
        ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaClienteDetalleEntity> Lista = (ArrayList<ListaClienteDetalleEntity>) objeto;
        Lista.size();
        b.putSerializable(TAG_1, Lista);
        fragment.setArguments(b);
        return fragment;

    }

    public static CobranzaDetalleView nuevainstancia(Object objeto) {
        Log.e("jpcm", "Este es NUEVA ISNTANCIA 4");
        CobranzaDetalleView fragment = new CobranzaDetalleView();
        chk_validacionqr.setChecked(true);
        cobranzaRepository.PendingCollectionQR(context);
        fab_edit_signature.setEnabled(true);
        fab_edit_signature.setClickable(true);
        //fab_edit_signature.setBackgroundDrawable(new ColorDrawable(Color.RED));
        //fab_edit_signature.setBackgroundColor(Color.RED);
        fab_edit_signature.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D6001C")));
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
        listaClienteDetalleAdapterFragment = new ArrayList<ListaClienteDetalleEntity>();
        documentoCobranzaPDF = new DocumentoCobranzaPDF();
        documentoSQLite = new DocumentoSQLite(getContext());
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        listaCobranzaDetalleEntities = new ArrayList<CobranzaDetalleSQLiteEntity>();
        clienteSQlite = new ClienteSQlite(getContext());
        cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
        depositoRepository = new ViewModelProvider(getActivity()).get(DepositoRepository.class);
        context=getContext();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            try {
                GPSController gpsController = new GPSController(getActivity());
                Location mLocation = gpsController.getLocation(null);
                latitude = mLocation.getLatitude();
                longitude = mLocation.getLongitude();
            } catch (Exception e) {
                Sentry.captureMessage(e.getMessage());
            }
        }
        //fecha =obtenerFechaYHoraActual();
        /*try {
            objCamara = Camera.open();
        }catch (Exception e)
        {
            Toast.makeText(getContext(), "Error en Comunicacio Camara:"+e.toString() , Toast.LENGTH_SHORT).show();
        }
        parametrosCamara = objCamara.getParameters();*/
        tieneFlash = getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
        listaConfiguracionSQLEntity = new ArrayList<>();
        usuarioSQLite = new UsuarioSQLite(getContext());
        if (getArguments() != null) {

            Listado = (ArrayList<ListaHistoricoCobranzaEntity>) getArguments().getSerializable(ARG_PARAM1);
            if (!(Listado == null)) {
                if (!(Listado.isEmpty()))
                    for (int j = 0; j < Listado.size(); j++) {
                        String Bancarizado = "";

                        recibo_generado = Listado.get(j).getRecibo();
                        recibo = recibo_generado;
                        chkqrvalidado = Listado.get(j).getEstadoqr();
                        listaClienteDetalleEntity = new ListaClienteDetalleEntity();
                        //cobrado = Float.parseFloat(listaCobranzaDetalleEntities.get(j).getSaldocobrado());
                        //istaClienteDetalleEntity.reci
                        listaClienteDetalleEntity.cliente_id = Listado.get(j).getCliente_id();
                        listaClienteDetalleEntity.nombrecliente = Listado.get(j).getCliente_nombre();
                        listaClienteDetalleEntity.cobrado = Listado.get(j).getMontocobrado();
                        listaClienteDetalleEntity.saldo = Listado.get(j).getSaldodocumento();
                        listaClienteDetalleEntity.nuevo_saldo = Listado.get(j).getNuevosaldodocumento();
                        listaClienteDetalleEntity.importe = Listado.get(j).getImportedocumento();
                        if (Listado.get(j).getBancarizacion().equals("Y")) {
                            Estadochkbancarizado = "1";
                        } else if (Listado.get(j).getBancarizacion().equals("N")) {
                            Estadochkbancarizado = "0";
                        }
                        Log.e("REOS", "CobranzaDetalleView-Listado.get(j).getDepositodirecto(): " + Listado.get(j).getDepositodirecto());
                        if (Listado.get(j).getDepositodirecto().equals("Y")) {
                            Estadodepositodirecto = "1";
                        } else if (Listado.get(j).getDepositodirecto().equals("N")) {
                            Estadodepositodirecto = "0";
                        }

                        Estadopagopos = SesionEntity.pagopos;
                        Log.e("REOS", "CobranzaDetalleView:-HistoricoCobranzaView: " + Listado.size());

                        //String[] fechaCobrado= Listado.get(j).getFechacobranza().split(" ");
                        if (!Listado.get(j).getFechacobranza().isEmpty()) {
                            fecha = Listado.get(j).getFechacobranza();
                        } else {
                            Log.e("jpcm", "FEHCA LLENA");
                        }
                        if (Listado.get(j).getDocumento_id().equals("")) {
                            Estadopagoadelantado = "1";
                        } else {
                            Estadopagoadelantado = "0";
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

                        Log.e("jpcm", "fecha->" + fecha);
                        //fecha= Listado.get(j).getFechacobranza();//+" "+Listado.get(j).getFechacobranza();
                        listaClienteDetalleEntity.fechaemision = "0";
                        listaClienteDetalleEntity.fechavencimiento = "0";
                        listaClienteDetalleEntity.direccion = "0";
                        listaClienteDetalleEntity.nrodocumento = Listado.get(j).getNro_documento();
                        listaClienteDetalleEntity.documento_id = Listado.get(j).getDocumento_id();
                        type_description=Listado.get(j).getType();
                        //listaClienteDetalleEntity.fechaemision=Listado.get(j).getFechacobranza();

                        listaClienteDetalleAdapterFragment.add(listaClienteDetalleEntity);
                        //fecha= Listado.get(j).getFechacobranza();

                    }
            } else {
                Log.e("jpcm", "se debio asignar");
                //fecha =obtenerFechaYHoraActual();
                listaClienteDetalleAdapterFragment = (ArrayList<ListaClienteDetalleEntity>) getArguments().getSerializable(TAG_1);
                Log.e("REOS", "CobranzaDetalleView-onCreate-listaClienteDetalleAdapterFragment.size()"+listaClienteDetalleAdapterFragment.size());
                try {


                    for (int g = 0; g < listaClienteDetalleAdapterFragment.size(); g++) {
                        cliente_id_visita = listaClienteDetalleAdapterFragment.get(g).getCliente_id();
                        domembarque_id_visita = listaClienteDetalleAdapterFragment.get(g).getDomembarque();
                        zona_id_visita = listaClienteDetalleAdapterFragment.get(g).getZona_id();
                        Log.e("REOS", "CobranzaDetalleView-onCreate-chkruta"+listaClienteDetalleAdapterFragment.get(g).getChkruta());
                    }
                }catch (Exception e)
                {
                    Log.e("REOS", "CobranzaDetalleView-onCreate-pago.e"+e.toString());
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
        v = inflater.inflate(R.layout.fragment_cobranza_detalle_view, container, false);
        et_cobrado_edit = (EditText) v.findViewById(R.id.et_cobrado_edit);
        //imbplus=(ImageButton)v.findViewById(R.id.imbplus);
        //imbminus=(ImageButton) v.findViewById(R.id.imbminus);
        imbaceptar = (ImageButton) v.findViewById(R.id.imbaceptar);
        imbcancelar = (ImageButton) v.findViewById(R.id.imbcancelar);
        chkpagoadelantado = (CheckBox) v.findViewById(R.id.chkpagoadelantado);
        chk_pago_pos = (CheckBox) v.findViewById(R.id.chk_pago_pos);
        chk_validacionqr = (CheckBox) v.findViewById(R.id.chk_validacionqr);
        tv_recibo = (TextView) v.findViewById(R.id.tv_recibo);
        chk_bancarizado = (CheckBox) v.findViewById(R.id.chk_bancarizado);
        chk_pago_directo = (CheckBox) v.findViewById(R.id.chk_pago_directo);
        chk_E_signature = (CheckBox) v.findViewById(R.id.chk_E_signature);
        tablerow_e_signature=v.findViewById(R.id.tablerow_e_signature);

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
        if(!BuildConfig.FLAVOR.equals("peru"))
        {
            fab_edit_signature.setVisibility(View.GONE);
            tablerow_e_signature.setVisibility(View.GONE);

        }
        else{
            if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
            {
                fab_edit_signature.setVisibility(View.GONE);
            }
            else {

            }

        }
        circleMenu.setMainMenu(Color.parseColor("#0957C3"),R.drawable.ic_baseline_add_24,R.drawable.ic_baseline_cancel_24_white)
                .addSubMenu(Color.parseColor("#0957C3"),R.drawable.ic_save_black_24dp)
                .addSubMenu(Color.parseColor("#0957C3"),R.drawable.ic_print_black_24dp)
                .addSubMenu(Color.parseColor("#0957C3"),R.drawable.ic_menu_camera)
                .addSubMenu(Color.parseColor("#0957C3"),R.drawable.ic_edit_black_24dp)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        Toast.makeText(getContext(),"You Selected"+arrayCircle[index],Toast.LENGTH_LONG).show();
                    }
                })
        ;
        fab_edit_signature.setOnClickListener(new View.OnClickListener() {
                                                  @Override
                                                  public void onClick(View view) {
                                                      //getAlertEditSignature(getContext()).show();
                                                       /*if (!(Listado == null))
                                                       {
                                                           getAlertEditSignatureRead(getContext()).show();
                                                       }else {
                                                           getAlertEditSignature(getContext()).show();
                                                       }*/
                                                      getAlertEditSignature(getContext()).show();
                                                  }
                                              }
        );
        fab_invoice_cancelation.setVisibility(View.GONE);
        imbcomentariorecibo.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View view) {
                                                       DialogFragment dialogFragment = new CobranzaDetalleDialogController();
                                                       dialogFragment.show(getActivity().getSupportFragmentManager(), "un dialogo");
                                                   }
                                               }
        );


        getActivity().setTitle("Cobranza");
        listViewCobranzaDetalle = (ListView) v.findViewById(R.id.listViewCobranzaCabecera);
        obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();


        if (!(listaClienteDetalleAdapterFragment.isEmpty())) {

            for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {
                if (listaClienteDetalleAdapterFragment.get(i).getNrodocumento() == null) {
                    chkpagoadelantado.setChecked(true);
                }
            }

        }

        imbaceptar.setOnClickListener(view -> {
                    int total = 0, p = 0, q = 0;
                    for (int i = 0; i < et_cobrado_edit.getText().toString().length(); i++) {
                        char letra;

                        letra = et_cobrado_edit.getText().toString().charAt(i);
                        if (letra == '.') {
                            p++;

                        } else {
                            q++;
                        }
                        Log.e("REOS", "CobranzaDetalleView.onCreateView.letra: " + letra);
                        Log.e("REOS", "CobranzaDetalleView.onCreateView.p: " + p);
                /*else
                {
                    total=total+Integer.parseInt(String.valueOf(""+letra));
                }*/
                    }

                    if (p <= 1 && q >= 1) {
                        String saldo_evaluar = "0";
                        if (!chkpagoadelantado.isChecked()) {
                            for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {
                                saldo_evaluar = listaClienteDetalleAdapterFragment.get(i).saldo;
                            }
                        }

                        String temporal = "";

                        if (et_cobrado_edit.getText() == null || et_cobrado_edit.getText().toString().length() == 0) {
                            temporal = "0";
                        } else {
                            temporal = et_cobrado_edit.getText().toString();
                        }

                        Log.e("Monto Antes Ingresar a Compare =>", "" + temporal);
                        BigDecimal montoIngresado = new BigDecimal(temporal);
                        Log.e("Monto montoIngresado.compareTo(BigDecimal.ZERO) =>", "" + montoIngresado.compareTo(BigDecimal.ZERO));
                        if (!(montoIngresado.compareTo(BigDecimal.ZERO) > 0)|| Float.parseFloat(et_cobrado_edit.getText().toString()) < 1 ) {
                            et_cobrado_edit.setText(null);
                            Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
                        } else {


                            BigDecimal montoCobrado = new BigDecimal(et_cobrado_edit.getText().toString());

                            Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                            drawable = DrawableCompat.wrap(drawable);
                            DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                            menu_variable.findItem(R.id.guardar).setIcon(drawable);
                            guardar.setEnabled(true);
                            Log.e("Monto=>", " Despues de ingresado Compare >" + montoCobrado.toString());

                            if (chkpagoadelantado.isChecked()) {
                                for (int j = 0; j < listaClienteDetalleAdapterFragment.size(); j++) {

                                    cobrado = montoCobrado;

                                    listaClienteDetalleAdapterFragment.get(j).setCobrado(cobrado.setScale(3, RoundingMode.HALF_UP).toString());
                                    listaClienteDetalleAdapterFragment.get(j).setSaldo(String.valueOf("0"));
                                    listaClienteDetalleAdapterFragment.get(j).setNuevo_saldo(String.valueOf("0"));
                                    listaClienteDetalleAdapterFragment.get(j).setImporte("0");
                                    listaClienteDetalleAdapterFragment.get(j).setFechaemision("0");
                                    listaClienteDetalleAdapterFragment.get(j).setFechavencimiento("0");
                                    listaClienteDetalleAdapterFragment.get(j).setDireccion("0");
                                    listaClienteDetalleAdapterFragment.get(j).setNrodocumento("0");
                                    listaClienteDetalleAdapterFragment.get(j).setDocumento_id("0");
                                    cliente_id_visita = listaClienteDetalleAdapterFragment.get(j).getCliente_id();
                                    domembarque_id_visita = listaClienteDetalleAdapterFragment.get(j).getDomembarque();
                                    zona_id_visita = listaClienteDetalleAdapterFragment.get(j).getZona_id();

                                }
                                obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                                obtenerWSCobranzaDetalle.execute();


                            } else {
                                Log.e("Monto=>", "> NO E SPAGO ADELANTADO");
                                for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {

                                    String cobranza = "";

                                    if (et_cobrado_edit.getText().toString().equals("")) {
                                        cobranza = "0";
                                    } else if (et_cobrado_edit.getText().toString().equals(".")) {
                                        cobranza = "0";
                                    } else {
                                        cobranza = et_cobrado_edit.getText().toString();
                                    }

                                    cobrado = new BigDecimal(cobranza);
                                    saldo = new BigDecimal(listaClienteDetalleAdapterFragment.get(i).getSaldo());

                                    listaClienteDetalleAdapterFragment.get(i).setSaldo(saldo.setScale(3, RoundingMode.HALF_UP).toString());
                                    if (cobrado.compareTo(saldo) <= 0) {
                                        listaClienteDetalleAdapterFragment.get(i).setCobrado(cobrado.setScale(3, RoundingMode.HALF_UP).toString());
                                        nuevo_saldo = saldo.subtract(cobrado);

                                        listaClienteDetalleAdapterFragment.get(i).setNuevo_saldo(nuevo_saldo.setScale(3, RoundingMode.HALF_UP).toString());

                                    } else {
                                        Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();

                                    }
                                    obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                                    obtenerWSCobranzaDetalle.execute();
                                }
                            }

                        }
                    } else {
                        Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        imbcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {
                    et_cobrado_edit.setText(null);
                    listaClienteDetalleAdapterFragment.get(i).setCobrado(String.valueOf("0"));
                    listaClienteDetalleAdapterFragment.get(i).setNuevo_saldo(String.valueOf("0"));
                }

                Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
                menu_variable.findItem(R.id.guardar).setIcon(drawable);
                guardar.setEnabled(false);

                chk_bancarizado.setChecked(false);
                obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                obtenerWSCobranzaDetalle.execute();
            }
        });

        try {
            if (getArguments() != null) {
                if (!(Listado == null)) {
                    tv_recibo.setText(recibo_generado);
                    if (chkqrvalidado.equals("False") || chkqrvalidado.equals("N")) {
                        chk_validacionqr.setChecked(false);


                    } else if (chkqrvalidado.equals("True") || chkqrvalidado.equals("Y")) {
                        chk_validacionqr.setChecked(true);
                    }

                    if (Estadopagopos != null && Estadopagopos.equals("Y")) {
                        chk_pago_pos.setChecked(true);
                    } else {
                        chk_pago_pos.setChecked(false);
                    }

                    if (Estadopagoadelantado.equals("1")) {
                        chkpagoadelantado.setChecked(true);
                    }


                } else {

                    if (SesionEntity.pagodirecto.equals("Y")) {
                        chk_pago_directo.setChecked(true);
                        chk_bancarizado.setEnabled(false);
                        chk_bancarizado.setChecked(false);
                        chk_bancarizado.setFocusable(false);
                        chk_bancarizado.setClickable(false);

                    } else {
                        chk_pago_directo.setChecked(false);
                    }
                }
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        if ((Listado == null)) {
            if (SesionEntity.pagopos.equals("Y")) {
                chk_pago_pos.setChecked(true);
            } else {
                chk_pago_pos.setChecked(false);
            }
        }
        Conexion = "1";

        chk_bancarizado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean estado = chk_bancarizado.isChecked();
                if (chk_bancarizado.isChecked()) {
                    createSimpleDialog(estado).show();
                } else {
                    createSimpleDialog(estado).show();
                }

            }
        });

        if (Estadochkbancarizado.equals("1")) {
            chk_bancarizado.setChecked(true);
            chk_bancarizado.setFocusable(false);
            chk_bancarizado.setClickable(false);
            //chk_bancarizado.setEnabled(false);

        } else if (Estadochkbancarizado.equals("0")) {
            chk_bancarizado.setChecked(false);
            chk_bancarizado.setFocusable(false);
            chk_bancarizado.setClickable(false);
        }
        if (Estadodepositodirecto.equals("Y")) {
            chk_pago_directo.setChecked(true);
            //chk_bancarizado.setFocusable(false);
            //chk_bancarizado.setClickable(false);
            //chk_bancarizado.setEnabled(false);

        } else if (Estadodepositodirecto.equals("N")) {
            chk_pago_directo.setChecked(false);
            //chk_bancarizado.setFocusable(false);
            //chk_bancarizado.setClickable(false);
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

        return dialog;
    }

    public AlertDialog createSimpleDialog(final boolean estado) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        if (estado) {
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
        } else {
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
        menu_variable = menu;


        if (getArguments() != null) {
            if (!(Listado == null)) {
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

            } else {
                comentario = "";
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

        if (SesionEntity.Print.equals("Y")) {
            //vinculaimpresora="1"; ///para chile no se requiere impresora
            listaConfiguracionSQLEntity = configuracionSQLiteDao.ObtenerConfiguracion();
            for (int i = 0; i < listaConfiguracionSQLEntity.size(); i++) {
                vinculaimpresora = listaConfiguracionSQLEntity.get(i).getVinculaimpresora();
            }
            if (vinculaimpresora.equals("0")) {
                generarpdf.setIcon(R.drawable.ic_warning_yellow_24dp);
                Toast.makeText(getContext(), "Advertencia: Impresora No Vinculada - Revisar en Configuracion" + comentario, Toast.LENGTH_SHORT).show();
            }

        } else {
            vinculaimpresora = "1";
        }
        super.onCreateOptionsMenu(menu, inflater);

    }


    private class ObtenerWSCobranzaDetalle extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg0) {
            return "1";
        }

        protected void onPostExecute(Object result) {
            listaCobranzaDetalleAdapter = new ListaCobranzaDetalleAdapter(getActivity(), ListaCobranzaDetalleDao.getInstance().getLeads(listaClienteDetalleAdapterFragment));
            listViewCobranzaDetalle.setAdapter(listaCobranzaDetalleAdapter);

        }

        protected ArrayList<ListaClienteDetalleEntity> llenarlista(ArrayList<ListaClienteDetalleEntity> listaClienteDetalleAdapter) {

            for (int i = 0; i < listaClienteDetalleAdapter.size(); i++) {

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
                if (SesionEntity.fuerzatrabajo_id == null && SesionEntity.nombrefuerzadetrabajo == null && SesionEntity.compania_id == null) {
                    //if(true){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Advertencia")
                            .setMessage("Tu sesión a expirado")
                            .setPositiveButton("INICIAR SESIóN",
                                    (dialog, which) -> {
                                        Intent i = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        return;
                                    });

                    builder.show();
                } else {

                    fecha = obtenerFechaActual();
                    guardar.setEnabled(false);
                    alertaGuardarCobranza().show();


                }
                return false;
            case R.id.generarpdf:
                //alertaGenerarPDF().show();
                if (SesionEntity.Print.equals("Y")) {
                    alertaGenerarPDF().show();
                } else
                {
                    if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
                    {
                        alertaGenerarPDF().show();
                        Drawable drawable3 = menu_variable.findItem(R.id.validarqr).getIcon();
                        drawable3 = DrawableCompat.wrap(drawable3);
                        DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.white ));
                        menu_variable.findItem(R.id.validarqr).setIcon(drawable3);
                        validarqr.setEnabled(true);
                        chk_validacionqr.setChecked(true);
                    }else{
                        alertatypegeneratedocumentcollection().show();
                    }

                }


                return true;
            case R.id.validarqr:
                if (SesionEntity.Print.equals("Y")) {

                    alertaValidarQR().show();

                } else {
                    if(SesionEntity.perfil_id.equals("CHOFER"))
                    {
                        chk_validacionqr.setChecked(true);
                        alertaEnviarSMS(getContext()).show();
                    }
                    else
                    {
                        alertatypevalidatedocumentcollection().show();
                    }

                }
                return false;
            default:
                break;
        }

        return false;
    }

    private Dialog alertaEnviarSMS(Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_enviar_sms);
        //TextView textMsj = dialog.findViewById(R.id.tv_texto);
        EditText et_numero_telefonico = (EditText) dialog.findViewById(R.id.et_numero_telefonico);
        //textMsj.setText(texto);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TelephonyManager tMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
                String mPhoneNumber="";
                mPhoneNumber=SesionEntity.phone;
                sendSMS(et_numero_telefonico.getText().toString());

                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                UsuarioSQLiteEntity objUsuario=new UsuarioSQLiteEntity();
                objUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                CobranzaDetalleSMSSQLiteDao cobranzaDetalleSMSSQLiteDao=new CobranzaDetalleSMSSQLiteDao(getContext());

                SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
                SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date date = new Date();

                cobranzaDetalleSMSSQLiteDao.InsertaCobranzaDetalleSMS(
                        recibo
                        , et_numero_telefonico.getText().toString()
                        ,objUsuario.compania_id
                        ,objUsuario.fuerzatrabajo_id
                        ,objUsuario.usuario_id
                        ,FormatFecha.format(date)
                        ,dateFormathora.format(date)
                        ,"N"
                );

                dialog.dismiss();

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

        return dialog;
    }

    public AlertDialog alertaGuardarCobranza() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Guardar la Cobranza?")
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            if (et_cobrado_edit.getText().toString().equals("0")) {
                                Toast.makeText(getContext(), "No puedes Guardar un Recibo con valor 0 ", Toast.LENGTH_SHORT).show();
                            } else {

                                if (vinculaimpresora.equals("0")) {
                                    //generarpdf.setEnabled(true);
                                    Toast.makeText(getContext(), "Impresora No Vinculada - Favor de Vincular para proseguir", Toast.LENGTH_SHORT).show();
                                } else {
                                    switch (SesionEntity.pagopos) {
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
                                    if (resultado > 0) {
                                        guardar.setEnabled(false);
                                        //cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id,result.toString());
                                        Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                                        drawable = DrawableCompat.wrap(drawable);
                                        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.Black));
                                        menu_variable.findItem(R.id.guardar).setIcon(drawable);
                                        Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                        drawable2 = DrawableCompat.wrap(drawable2);
                                        DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.white));
                                        menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                                        //guardar.setEnabled(false);
                                        //imbcomentariorecibo.setColorFilter(Color.BLACK);
                                        if (!SesionEntity.Print.equals("Y")) {
                                            if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
                                            {
                                                Drawable drawable3 = menu_variable.findItem(R.id.validarqr).getIcon();
                                                drawable3 = DrawableCompat.wrap(drawable3);
                                                DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.white));
                                                menu_variable.findItem(R.id.validarqr).setIcon(drawable3);
                                                validarqr.setEnabled(true);
                                            }else
                                            {
                                                Drawable drawable3 = menu_variable.findItem(R.id.validarqr).getIcon();
                                                drawable3 = DrawableCompat.wrap(drawable3);
                                                DrawableCompat.setTint(drawable3, ContextCompat.getColor(getContext(), R.color.white));
                                                menu_variable.findItem(R.id.validarqr).setIcon(drawable3);
                                                validarqr.setEnabled(true);
                                            }


                                        }
                                        imbcomentariorecibo.setEnabled(false);

                                        if (vinculaimpresora.equals("1")) {
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


                                    } else {
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

    public AlertDialog alertaGenerarPDF() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Generar el Archivo PDF?")
                .setPositiveButton("OK",

                        (dialog, which) -> {

                            if (listaClienteDetalleAdapterFragment.size() > 0) {
                                // SesionEntity.imagen="R"+recibo;
                                // fecha =obtenerFechaYHoraActual();


                                //documentPDFController.showPdfFile(recibo+".pdf",getContext());
                                File file = new File(Environment
                                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                        NOMBRE_DIRECTORIO);
                                String Cadenafile = "";
                                Cadenafile = String.valueOf(file);
                                String ruta = Cadenafile + "/" + recibo + ".pdf";


                                // MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                                String correlativo = "";
                                ConfiguracionSQLiteDao configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
                                ArrayList<ConfiguracionSQLEntity> arraylistconfiguracion = new ArrayList<ConfiguracionSQLEntity>();
                                arraylistconfiguracion = configuracionSQLiteDao.ObtenerCorrelativoConfiguracion();

                                for (int i = 0; i < arraylistconfiguracion.size(); i++) {
                                    correlativo = arraylistconfiguracion.get(i).getSecuenciarecibos();
                                }

                                configuracionSQLiteDao.ActualizaCorrelativo(String.valueOf(Integer.parseInt(correlativo) - 1));

                                //btn item validar QR se queda en disabled
                                if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
                                {
                                    Drawable drawable = menu_variable.findItem(R.id.validarqr).getIcon();
                                    drawable = DrawableCompat.wrap(drawable);
                                    DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                                    menu_variable.findItem(R.id.validarqr).setIcon(drawable);
                                    validarqr.setEnabled(true); //no aplica validar qr //peru si

                                    Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                    drawable2 = DrawableCompat.wrap(drawable2);
                                    DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.white));
                                    menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                                }
                                else
                                {
                                    Drawable drawable = menu_variable.findItem(R.id.validarqr).getIcon();
                                    drawable = DrawableCompat.wrap(drawable);
                                    DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                                    menu_variable.findItem(R.id.validarqr).setIcon(drawable);
                                    validarqr.setEnabled(true); //no aplica validar qr //peru si

                                    Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                    drawable2 = DrawableCompat.wrap(drawable2);
                                    DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(), R.color.Black));
                                    menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                                }
                                ArrayList<CobranzaDetalleSQLiteEntity> listCobranzaDetalle=new ArrayList<>();
                                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(context);
                                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
                                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                                String type="";
                                listCobranzaDetalle=cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                        recibo,
                                        ObjUsuario.compania_id,
                                        ObjUsuario.fuerzatrabajo_id
                                );
                                if(listCobranzaDetalle.isEmpty())
                                {
                                    type=type_description;
                                }else {
                                    for(int i=0;i<listCobranzaDetalle.size();i++)
                                    {
                                        type=listCobranzaDetalle.get(i).getTypedescription();
                                    }
                                }
                                Log.e("REOS","CobranzaDetalleView-alertaGenerarPDF-type: "+type);


                                // If you have access to the external storage, do whatever you need
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                    if (Environment.isExternalStorageManager()){

                                        // If you don't have access, launch a new activity to show the user the system's dialog
                                        // to allow access to the external storage
                                        documentoCobranzaPDF.generarPdf(getContext(), listaClienteDetalleAdapterFragment, SesionEntity.fuerzatrabajo_id, SesionEntity.nombrefuerzadetrabajo, recibo, fecha, obtenerHoraActual(),type);
                                    }else{
                                        Intent intent = new Intent();
                                        intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
                                        intent.setData(uri);
                                        startActivity(intent);
                                    }
                                }
                                else
                                {
                                    documentoCobranzaPDF.generarPdf(getContext(), listaClienteDetalleAdapterFragment, SesionEntity.fuerzatrabajo_id, SesionEntity.nombrefuerzadetrabajo, recibo, fecha, obtenerHoraActual(),type);
                                }
                                if (SesionEntity.Print.equals("Y")) {
                                    try
                                    {
                                       //MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                                    }catch (Exception e)
                                    {
                                        Log.e("REOS","CobranzaDetalleView-alertaGenerarPDF-MenuView.getPrinterInstance().printPdf-e: "+e.toString());
                                    }
                                }
                                Toast.makeText(getContext(), "Se creo tu archivo pdf", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "No se creo el archivo pdf", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setNegativeButton("CANCELAR",
                        (dialog, which) -> {
                            //listener.onNegativeButtonClick();
                        });

        return builder.create();
    }

    public AlertDialog alertaValidarQR() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Iniciar validacion QR?")
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            SesionEntity.imagen = "R" + recibo;
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


    public int GuardarCobranzaSQLite(ArrayList<ListaClienteDetalleEntity> Lista, String tipoCobranza) {
        int resultado = 0, recibows = 0;
        String tag = "", tag2 = "", cliente_id = "", shipto = "", montocobrado = "", qrvalidado = "N", telefono = "",cardname="",valorcobranza="0",chkruta="",contado="0",type="";
        FormulasController formulasController = new FormulasController(getContext());
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
        String bancarizado = "";
        if (chk_bancarizado.isChecked()) {
            bancarizado = "Y";
        } else {
            bancarizado = "N";
        }

        if(bancarizado.equals("Y"))
        {
            type ="Bancarizado";
        }
        else if(SesionEntity.pagodirecto.equals("Y"))
        {
            type="Pago Directo";
        }
        else if(SesionEntity.pagopos.equals("Y"))
        {
            type="Pago POS";
        }
        else if(SesionEntity.collection_salesperson.equals("Y"))
        {
            type="Cobró Vendedor";
        }
        else {
            type="Cobranza Ordinaria";
        }
        if(Float.parseFloat(valorcobranza)>=1) {

            cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
            correlativorecibo = cobranzaDetalleSQLiteDao.ObtenerUltimoRecibo(ObjUsuario.compania_id, ObjUsuario.usuario_id);


            String[] separada = SesionEntity.recibo.split("R");
            if (SesionEntity.recibo.equals("0")) {
                tag2 = SesionEntity.recibo;
            } else {
                if (separada.length > 1) {
                    tag = separada[0];
                    tag2 = separada[1];
                } else {
                    tag = separada[0];
                }
            }

            if (tag.equals("")) {
                tag = "0";
            }


            recibows = Integer.parseInt(tag);
            if (correlativorecibo >= recibows) {
                ultimocorrelativorecibo = correlativorecibo;
            } else {
                ultimocorrelativorecibo = recibows;
            }


            if (SesionEntity.perfil_id.equals("CHOFER") || SesionEntity.perfil_id.equals("Chofer")) {

                qrvalidado = "Y";
                Log.e("REOS", "CobranzaDetalleView.GuardarCobranzaSQLite.qrvalidado:" + qrvalidado);
            }

            if (tipoCobranza.equals("Cobranza")) {
                for (int i = 0; i < Lista.size(); i++) {

                    montocobrado = Lista.get(i).getCobrado();
                    cliente_id = String.valueOf(Lista.get(i).getCliente_id());
                    cardname = Lista.get(i).getNombrecliente();
                    shipto = Lista.get(i).getDomembarque();
                    recibo = String.valueOf(ultimocorrelativorecibo + 1);
                    resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                            FormulasController.ObtenerFechaHoraCadena(),
                            String.valueOf(Lista.get(i).getCliente_id()),
                            String.valueOf(Lista.get(i).getDocumento_id()),
                            ObjUsuario.compania_id,
                            String.valueOf(Lista.get(i).getImporte()),
                            String.valueOf(Lista.get(i).getSaldo()),
                            String.valueOf(Lista.get(i).getNuevo_saldo()),
                            String.valueOf(Lista.get(i).getCobrado()),
                            //fechaCobro
                            fecha,
                            recibo,
                            String.valueOf(Lista.get(i).getNrodocumento()),
                            ObjUsuario.fuerzatrabajo_id,
                            bancarizado,
                            //Peru - Cambio necesario para letras
                            //------------------
                            //"0",
                            String.valueOf(Lista.get(i).getNrodocumento()),
                            //------------------
                            ObjUsuario.usuario_id,
                            comentario,
                            "N",
                            qrvalidado,
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
                            ,"N"
                            ,type
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
            } else if (tipoCobranza.equals("Cobranza/Deposito")) {
                String sumacobrado = "";
                for (int i = 0; i < Lista.size(); i++) {
                    cliente_id = String.valueOf(Lista.get(i).getCliente_id());
                    cardname = Lista.get(i).getNombrecliente();
                    shipto = Lista.get(i).getDomembarque();
                    recibo = String.valueOf(ultimocorrelativorecibo + 1);
                    sumacobrado = String.valueOf(Lista.get(i).getCobrado());
                    montocobrado = sumacobrado;
                    resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                            FormulasController.ObtenerFechaHoraCadena(),
                            //SesionEntity.fuerzatrabajo_id+recibo,
                            String.valueOf(Lista.get(i).getCliente_id()),
                            String.valueOf(Lista.get(i).getDocumento_id()),
                            ObjUsuario.compania_id,
                            String.valueOf(Lista.get(i).getImporte()),
                            String.valueOf(Lista.get(i).getSaldo()),
                            String.valueOf(Lista.get(i).getNuevo_saldo()),
                            sumacobrado,
                            fecha,
                            recibo,
                            String.valueOf(Lista.get(i).getNrodocumento()),
                            ObjUsuario.fuerzatrabajo_id,
                            bancarizado,
                            //"",
                            String.valueOf(n),
                            ObjUsuario.usuario_id,
                            comentario,
                            "Y",
                            "Y",
                            "104111",
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
                            ,"N"
                            ,type
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
                addDepositPOS(sumacobrado);
            /*hiloEnviarWSCobranzaCabecera = new CobranzaDetalleView.HiloEnviarWSCobranzaCabecera();
            hiloEnviarWSCobranzaCabecera.execute(
                    SesionEntity.fuerzatrabajo_id+recibo,
                    "BCPMN",
                    sumacobrado,
                    "Deposito",
                    bancarizado,
                    "1900-01-01",
                    fecha,
                    SesionEntity.pagodirecto);*/
            }

            /*GENERAR UN DEPOSITO GENERA UNA VISITA AL CLIENTE?????? */
        /*
        formulasController.RegistraVisita(
                cliente_id_visita,
                domembarque_id_visita,
                zona_id_visita,
                "02",
                "02-MOTIVO 02",
                "Registro Cobranza",
                getActivity(),
                String.valueOf(latitude),
                String.valueOf(longitude)
        );*/

            chk_bancarizado.setFocusable(false);
            chk_bancarizado.setClickable(false);

            VisitaSQLiteEntity visita = new VisitaSQLiteEntity();
            visita.setCardCode(cliente_id);
            visita.setAddress(shipto);
            visita.setType("02");
            visita.setObservation("Se genero el recibo " + recibo + " para el cliente: " + cliente_id);
            visita.setLatitude("" + latitude);
            visita.setLongitude("" + longitude);
            visita.setMobileID(recibo);
            Log.e("REOS", "CobranzaDetalleView-Guardar-chkruta:" + chkruta);
            visita.setStatusRoute(chkruta);
            visita.setAmount(valorcobranza);
            visita.setTerminoPago_ID(contado);
            formulasController.RegistraVisita(visita, getActivity(), montocobrado);
            //Enviar SMS
            ArrayList<ClienteSQLiteEntity> listClienteSQlite = new ArrayList<>();
            ClienteSQlite clienteSQlite = new ClienteSQlite(getContext());
            listClienteSQlite = clienteSQlite.ObtenerDatosCliente(cliente_id, ObjUsuario.compania_id);
            for (int i = 0; i < listClienteSQlite.size(); i++) {
                telefono = listClienteSQlite.get(i).getTelefonofijo();
            }
            String mPhoneNumber = "";
            TelephonyManager tMgr = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_PHONE_NUMBERS)
                            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                mPhoneNumber = tMgr.getLine1Number();
            }
            if(!SesionEntity.perfil_id.equals("CHOFER"))
            {
                mPhoneNumber = SesionEntity.phone;
                Log.e("REOS", "CobranzaDetalleView-GuardarCobranzaSQLite-Guardar-mPhoneNumber:" + mPhoneNumber);
                Log.e("REOS", "CobranzaDetalleView-GuardarCobranzaSQLite-Guardar-telefono:" + telefono);
                if (telefono.equals(mPhoneNumber)) {
                    Toast.makeText(getContext(), "El Numero Telefonico pertenece al Vendedor", Toast.LENGTH_SHORT).show();
                } else {
                    //telefono="990249315";
                    //sendSMS(telefono);
                    //Toast.makeText(getContext(), "SMS enviado al N° del Cliente: " + telefono, Toast.LENGTH_SHORT).show();
                }
            }

            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
            //UpdateSendReceipt();
            cobranzaRepository.UndepositedPendingCollection(getContext()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
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

    private void addDepositPOS(String montocobrado)
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
                "104111",
                SesionEntity.compania_id,
                montocobrado,
                "Deposito",
                bancarizado,
                "19000101",
                fecha,
                SesionEntity.pagodirecto,
                "Y"
                ,"N"
        );

        if(ValidaSQLite==1)
        {
            Validacion=String.valueOf(ValidaSQLite);

            cobranzaDetalleSQLiteDao.ActualizaCobranzaDetalle(
                    SesionEntity.fuerzatrabajo_id+recibo,
                    recibo,
                    SesionEntity.compania_id,
                    "104111"
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

    private void encenderFlash()
    {
        parametrosCamara.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        objCamara.setParameters(parametrosCamara);
        objCamara.startPreview();
        linternaOn = true;
    }

    private Dialog alertatypegeneratedocumentcollection() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_type_generate_document_collection);


        CardView cv_generate_pdf,cv_send_sms;
        cv_generate_pdf=dialog.findViewById(R.id.cv_generate_pdf);
        cv_send_sms=dialog.findViewById(R.id.cv_send_sms);


        //kardexPagoRepository = new ViewModelProvider(getActivity()).get(KardexPagoRepository.class);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("Elija Tipo de Cobranza:");


        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        cv_generate_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                /*String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="N";
                SesionEntity.pagopos="N";
                dialog.dismiss();*/
                alertaGenerarPDF().show();
                dialog.dismiss();
            }
        });
        cv_send_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                /*String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="Y";
                SesionEntity.pagopos="N";
                dialog.dismiss();*/
                alertaEnviarSMS(getContext()).show();
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertatypevalidatedocumentcollection() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_type_validate_document_collection);


        CardView cv_validate_pdf,cv_validate_sms;
        cv_validate_pdf=dialog.findViewById(R.id.cv_validate_pdf);
        cv_validate_sms=dialog.findViewById(R.id.cv_validate_sms);
        //kardexPagoRepository = new ViewModelProvider(getActivity()).get(KardexPagoRepository.class);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("Elija Tipo de Cobranza:");


        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        cv_validate_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                /*String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="N";
                SesionEntity.pagopos="N";
                dialog.dismiss();*/
                //alertaGenerarPDF().show();
                alertaValidarQR().show();
                dialog.dismiss();
            }
        });
        cv_validate_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                /*String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="Y";
                SesionEntity.pagopos="N";
                dialog.dismiss();*/
                //alertaEnviarSMS(getContext()).show();
                alertValidateSMS(getContext()).show();
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertValidateSMS(Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_validate_sms);
        EditText et_code_sms=(EditText) dialog.findViewById(R.id.et_code_sms);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int resultado=0;
                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
                resultado=cobranzaDetalleSQLiteDao.getCountValidateCodeSMS(
                        SesionEntity.compania_id,SesionEntity.usuario_id,recibo,et_code_sms.getText().toString()
                );
                if(resultado>0)
                {
                    cobranzaDetalleSQLiteDao.ActualizaValidacionQRCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id);
                    chk_validacionqr.setChecked(true);
                    cobranzaRepository.PendingCollectionQR(context);
                    Toast.makeText(getContext(), "Codigo Actualizado Correctamente!!!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else
                {
                    Toast.makeText(getContext(), "El codigo Ingresado no es el Correcto, Intente Nuevamente!!!", Toast.LENGTH_SHORT).show();
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

    public void sendSMS(String telefono)
    {
        FormulasController formulasController=new FormulasController(getContext());
        if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.SEND_SMS},1);
        }
        SmsManager smsManager=SmsManager.getDefault();
        try
        {
            //SmsManager sms = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(formulasController.convertirCadenaEnvioSMS(listaClienteDetalleAdapterFragment, SesionEntity.fuerzatrabajo_id, SesionEntity.nombrefuerzadetrabajo, recibo, fecha));
            //sms.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
            smsManager.sendMultipartTextMessage(
                    telefono
                    , null
                    //, formulasController.convertirCadenaEnvioSMS(listaClienteDetalleAdapterFragment, SesionEntity.fuerzatrabajo_id, SesionEntity.nombrefuerzadetrabajo, recibo, fecha)
                    ,parts
                    , null
                    , null);
            Toast.makeText(getContext(), "Mensaje de Texto Enviado Correctamente", Toast.LENGTH_SHORT).show();
            alertdialogInformative(getContext()).show();
        }
        catch (Exception e)
        {
            Log.e("REOS","CobranzaDetalleView-alertaEnviarSMS-Erroe-"+e.toString());
        }
    }

    private Dialog alertdialogInformative(Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text=(TextView) dialog.findViewById(R.id.text);
        text.setText("IMPORTANTE!!!");
        if(SesionEntity.perfil_id.equals("CHOFER"))
        {
            textViewMsj.setText("El SMS fue enviado Correctamente!!!");
        }
        else
        {
            textViewMsj.setText("El SMS fue enviado Correctamente,solicitar al Cliente el codigo de SMS!!!");
        }
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

    private Dialog getAlertEditSignature(Context context) {
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
        String E_signature="";
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_edit_signature);
        RelativeLayout rl_edit_signature=(RelativeLayout) dialog.findViewById(R.id.rl_edit_signature);
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);
        rl_edit_signature.addView(new Canvas(getActivity(),mPaint));


        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
                String encoded = null;
                Bitmap imgBitmap;
                byte[] byteArray;
                rl_edit_signature.setDrawingCacheEnabled(true);
                imgBitmap = Bitmap.createBitmap(rl_edit_signature.getDrawingCache());
                rl_edit_signature.setDrawingCacheEnabled(false);

                try {


                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byteArray = stream.toByteArray();
                    //encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    /////////////////////////

                /*ImageIO.write(img, "png", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();
                baos.close();*/
                    ///////////////////////
                    String imagenString = new String(byteArray);
                    String url="";
                    MutableLiveData <Object> status= new MutableLiveData<>();
                    cobranzaDetalleSQLiteDao.UpdateE_Signature(
                            ObjUsuario.compania_id
                            ,ObjUsuario.usuario_id
                            ,recibo
                            //,encoded);
                            ,imagenString);
                    chk_E_signature.setChecked(true);
                    cobranzaRepository.PendingCollectionSignatureList(getContext()).observe(getActivity(), data -> {
                        Log.e("REOS", "CobranzaDetalleView-getAlertEditSignature-PendingCollectionSignatureList-data" + data);
                    });

                    //url="https://reclamos.vistonyapp.com/upload/image?"+imagenString;
                    //Log.e("REOS","CobranzaDetalleView-onReponse-url-"+url);
                    /*RequestBody binaryConvert = RequestBody.create(imagenString, MediaType.parse("application/binary; charset=utf-8"));
                    Log.e("REOS","CobranzaDetalleView-onReponse-binaryConvert-"+binaryConvert.toString());
                    Config.getClient().create(Api.class).postPrueba(binaryConvert).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if(response.isSuccessful()) {
                                Log.e("REOS","CobranzaDetalleView-onReponse-response-"+response.toString());
                            }else{

                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable error) {

                            String message="Error no definido";

                            if(error instanceof SocketTimeoutException){
                                message="El tiempo de respuesta expiro";
                            }else if(error instanceof UnknownHostException){
                                message="No tiene conexión a internet";
                            }else if (error instanceof ConnectException) {
                                message="El servidor no responde";
                            } else if (error instanceof JSONException || error instanceof JsonSyntaxException) {
                                message="Error en el parceo";
                            } else if (error instanceof IOException) {
                                message=error.getMessage();
                            }

                            status.setValue(message);

                        }
                    });*/
                }catch (Exception e){
                    Log.e("REOS","CobranzaDetalleView-getAlertEditSignature-error-"+e.toString());
                }
                //imv_prueba_mostrarfirma.setImageBitmap(imgBitmap);
                Toast.makeText(getContext(), "Firma Guardada Correctamente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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

    private Dialog getAlertEditSignatureRead(Context context) {
        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
        String E_signature="";
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_edit_signature_read);
        ImageView iv_edit_signature=(ImageView) dialog.findViewById(R.id.iv_edit_signature);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
        E_signature=cobranzaDetalleSQLiteDao.getE_Signature(
                recibo,
                ObjUsuario.compania_id,
                ObjUsuario.fuerzatrabajo_id
        );

        byte[] byteArray;
        byteArray = Base64.decode(E_signature, Base64.DEFAULT);
        Log.e("REOS","ListHistoricStatusDispatchAdapter.byteArray.tostring"+byteArray.toString());
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        iv_edit_signature.setImageBitmap(bitmap);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
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

}

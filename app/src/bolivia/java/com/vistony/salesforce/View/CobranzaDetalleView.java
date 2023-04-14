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
import com.vistony.salesforce.Controller.Adapters.CobranzaDetalleDialogController;
import com.vistony.salesforce.Controller.Utilitario.DocumentoCobranzaPDF;
import com.vistony.salesforce.Controller.Adapters.ListaCobranzaDetalleAdapter;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DocumentoSQLite;
import com.vistony.salesforce.Dao.Adapters.ListaCobranzaDetalleDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    EnviarWSCobranzaDetalle enviarWSCobranzaDetalle;
    static public ImageView imvprueba;
    CheckBox chkpagoadelantado,chk_bancarizado,chk_pago_directo,chk_pago_pos;;
    MenuItem generarpdf,validarqr,guardar,edit_signature;
    TextView tv_recibo;
    public static CheckBox chk_validacionqr;
    static HiloVlidarQR hiloVlidarQR;
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
    String Estadochkbancarizado="",Estadodepositodirecto="",Estadopagopos="",Estadopagoadelantado="";
    HiloCobranzaPOS hiloCobranzaPOS;
    private CobranzaRepository cobranzaRepository;
    private DepositoRepository depositoRepository;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    String cliente_id_visita,domembarque_id_visita,zona_id_visita;
    private ProgressDialog pd;
    HiloEnviarWSCobranzaCabecera hiloEnviarWSCobranzaCabecera;
    FloatingActionButton fab_invoice_cancelation,fab_edit_signature;
    ImageView imv_prueba_mostrarfirma;
    CircleMenu circleMenu;
    TableRow tablerow_e_signature;
    String type_description;

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
        hiloVlidarQR = new HiloVlidarQR();
        listaClienteDetalleAdapterFragment =  new ArrayList<ListaClienteDetalleEntity>();
        documentoCobranzaPDF = new DocumentoCobranzaPDF();
        documentoSQLite = new DocumentoSQLite(getContext());
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        listaCobranzaDetalleEntities = new ArrayList<CobranzaDetalleSQLiteEntity>();
        clienteSQlite = new ClienteSQlite(getContext());
        hiloCobranzaPOS = new HiloCobranzaPOS();
        cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
        depositoRepository = new ViewModelProvider(getActivity()).get(DepositoRepository.class);

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
        objCamara = Camera.open();

        parametrosCamara = objCamara.getParameters();
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
                            Estadodepositodirecto="1";
                        }
                        else if(Listado.get(j).getDepositodirecto().equals("N"))
                        {
                            Estadodepositodirecto="0";
                        }

                        Estadopagopos=SesionEntity.pagopos;
                        Log.e("REOS","CobranzaDetalleView:-HistoricoCobranzaView: "+Listado.size());

                        //String[] fechaCobrado= Listado.get(j).getFechacobranza().split(" ");
                        if(!Listado.get(j).getFechacobranza().isEmpty()){
                            fecha=Listado.get(j).getFechacobranza();
                        }else{
                            Log.e("jpcm","FEHCA LLENA");
                        }
                        if(Listado.get(j).getDocumento_id().equals(""))
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
                        type_description=Listado.get(j).getType();
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
        //imvprueba = (ImageView) v.findViewById(R.id.imvprueba);
        imbcomentariorecibo= (OmegaCenterIconButton) v.findViewById(R.id.imbcomentariorecibo);
        fab_invoice_cancelation =  (FloatingActionButton) v.findViewById(R.id.fab_invoice_cancelation);
        circleMenu = v.findViewById(R.id.circleMenu);
        fab_edit_signature =  (FloatingActionButton) v.findViewById(R.id.fab_edit_signature);
        imv_prueba_mostrarfirma =  (ImageView) v.findViewById(R.id.imv_prueba_mostrarfirma);
        tablerow_e_signature =  (TableRow) v.findViewById(R.id.tablerow_e_signature);

        circleMenu.setVisibility(View.GONE);
        imv_prueba_mostrarfirma.setVisibility(View.GONE);
        fab_edit_signature.setVisibility(View.GONE);
        fab_invoice_cancelation.setVisibility(View.GONE);
        tablerow_e_signature.setVisibility(View.GONE);

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
                    int total = 0, p = 0,q=0;
                    for (int i = 0; i < et_cobrado_edit.getText().toString().length(); i++) {
                        char letra;

                        letra = et_cobrado_edit.getText().toString().charAt(i);
                        if (letra == '.') {
                            p++;

                        }else
                            {
                                q++;
                            }
                        Log.e("REOS", "CobranzaDetalleView.onCreateView.letra: " + letra);
                        Log.e("REOS", "CobranzaDetalleView.onCreateView.p: " + p);
                /*else
                {
                    total=total+Integer.parseInt(String.valueOf(""+letra));
                }*/
                    }

                    if (p <= 1&&q>=1) {
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

                        Log.e("Monto=>", "" + temporal);
                        BigDecimal montoIngresado = new BigDecimal(temporal);

                        if (!(montoIngresado.compareTo(BigDecimal.ZERO) > 0)) {
                            et_cobrado_edit.setText(null);
                            Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
                        } else {


                            BigDecimal montoCobrado = new BigDecimal(et_cobrado_edit.getText().toString());

                            Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                            drawable = DrawableCompat.wrap(drawable);
                            DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                            menu_variable.findItem(R.id.guardar).setIcon(drawable);
                            guardar.setEnabled(true);
                            Log.e("Monto=>", ">" + montoCobrado.toString());

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
                    }
                    else
                        {
                            Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
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
                if (chkqrvalidado.equals("False")||chkqrvalidado.equals("N")) {
                    chk_validacionqr.setChecked(false);


                } else if (chkqrvalidado.equals("True")||chkqrvalidado.equals("Y")) {
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
        menu_variable=menu;

        edit_signature.setVisible(false);



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

        //vinculaimpresora="1"; ///para chile no se requiere impresora
        listaConfiguracionSQLEntity=configuracionSQLiteDao.ObtenerConfiguracion();
        for(int i=0;i<listaConfiguracionSQLEntity.size();i++)
        {
            vinculaimpresora=listaConfiguracionSQLEntity.get(i).getVinculaimpresora();
        }
        /*if(vinculaimpresora.equals("0"))
        {
            generarpdf.setIcon(R.drawable.ic_warning_yellow_24dp);
            Toast.makeText(getContext(), "Advertencia: Impresora No Vinculada - Revisar en Configuracion"+comentario, Toast.LENGTH_SHORT).show();
        }*/

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
                    alertaGuardarCobranza().show();
                }
                return false;
            case R.id.generarpdf:
                alertaGenerarPDF().show();
                return true;
            case R.id.validarqr:
                alertaValidarQR().show();
                return false;
            default:
                break;
        }

        return false;
    }

    public AlertDialog alertaGuardarCobranza() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Guardar la Cobranza?")
                .setPositiveButton("OK",
                        (dialog, which) -> {
                            if(et_cobrado_edit.getText().toString().equals("0")){
                                Toast.makeText(getContext(), "No puedes Guardar un Recibo con valor 0 ", Toast.LENGTH_SHORT).show();
                            }else{

                                /*if(vinculaimpresora.equals("0")){
                                    //generarpdf.setEnabled(true);
                                    Toast.makeText(getContext(), "Impresora No Vinculada - Favor de Vincular para proseguir", Toast.LENGTH_SHORT).show();
                                }else{*/
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

                                        //if(vinculaimpresora.equals("1"))
                                        //{
                                            generarpdf.setEnabled(true);
                                        //}

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

                            //}


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
                                Drawable drawable = menu_variable.findItem(R.id.validarqr).getIcon();
                                drawable = DrawableCompat.wrap(drawable);
                                DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
                                menu_variable.findItem(R.id.validarqr).setIcon(drawable);

                                Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                drawable2 = DrawableCompat.wrap(drawable2);
                                DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.Black));
                                menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);

                                validarqr.setEnabled(false); //no aplica validar qr //peru si

                                ArrayList<CobranzaDetalleSQLiteEntity> listCobranzaDetalle=new ArrayList<>();
                                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
                                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
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

                                documentoCobranzaPDF.generarPdf(getContext(), listaClienteDetalleAdapterFragment,SesionEntity.fuerzatrabajo_id,SesionEntity.nombrefuerzadetrabajo,recibo,fecha,obtenerHoraActual(),type);
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

    public AlertDialog alertaValidarQR() {

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


    public int GuardarCobranzaSQLite(ArrayList<ListaClienteDetalleEntity> Lista, String tipoCobranza) {
        int resultado = 0, recibows = 0;
        String tag = "", tag2 = "", cliente_id = "", shipto = "", montocobrado = ""
                , qrvalidado = "N", telefono = "",cardname="",valorcobranza="0",chkruta="",contado="0",type="";
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
                            "Y",
                            "",
                            "N",
                            "Y",
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
                            ,SesionEntity.collection_salesperson
                            ,type
                    );

                    ActualizaDocumentoDeuda(ObjUsuario.compania_id,
                            String.valueOf(Lista.get(i).getDocumento_id()),
                            String.valueOf(Lista.get(i).getNuevo_saldo()),
                            Lista.get(i).getNrodocumento()

                    );

                }
            } else if (tipoCobranza.equals("Cobranza/Deposito")) {
                String sumacobrado = "";
                for (int i = 0; i < Lista.size(); i++) {
                    cliente_id = String.valueOf(Lista.get(i).getCliente_id());
                    shipto = Lista.get(i).getDomembarque();
                    recibo = String.valueOf(ultimocorrelativorecibo + 1);
                    sumacobrado = String.valueOf(Lista.get(i).getCobrado());
                    montocobrado = sumacobrado;
                    resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                            FormulasController.ObtenerFechaHoraCadena(),
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
                            "",
                            ObjUsuario.usuario_id,
                            comentario,
                            "Y",
                            "N",
                            "11",
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
                            ,SesionEntity.collection_salesperson
                            ,type
                    );


                    ActualizaDocumentoDeuda(ObjUsuario.compania_id,
                            String.valueOf(Lista.get(i).getDocumento_id()),
                            String.valueOf(Lista.get(i).getNuevo_saldo()),
                            Lista.get(i).getNrodocumento()

                    );

                }
                addDepositPOS(sumacobrado);
            }
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

   /* public void UpdateQRStatus (){
        /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        CobranzaRepository cobranzaRepository = new ViewModelProvider(getActivity()).get(CobranzaRepository.class);
        cobranzaRepository.PendingCollectionQR(getContext()).observe(getActivity(), data -> {
            Log.e("Jepicame","=>"+data);
        });
    }*/

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



    private class EnviarWSCobranzaDetalle extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg1) {
            String resultadoenviows="0";
            try {

                if(Conexion.equals("1")){
                    resultadoenviows= CobranzaRepository.EnviarReciboWsRetrofit(
                            cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                            getContext(),
                            "CREATE",
                            "N",
                            "N",
                            "",
                            "N"
                    );

                }

            } catch (Exception e){
                e.printStackTrace();
            }
            return String.valueOf(resultadoenviows);
        }

        protected void onPostExecute(Object result)
        {
            try {
                if(result.equals("1")){
                    //actualzia el estado a uno pero ni bien se envia a la web service se actualiza
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

                    if(Conexion.equals("1"))
                    {
                        //cobranzaDetalleSQLiteDao.EliminarRecibo(SesionEntity.compania_id,recibo,SesionEntity.usuario_id);
                        //Toast.makeText(getContext(), "No Se Envio la Cobranza, Revisar Conexion a Internet", Toast.LENGTH_SHORT).show();
                    }
                    else if(Conexion.equals("0"))
                    {
                        //cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id,result.toString());
                        Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                        drawable = DrawableCompat.wrap(drawable);
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
                        menu_variable.findItem(R.id.guardar).setIcon(drawable);
                        Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                        drawable2 = DrawableCompat.wrap(drawable2);
                        DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.white));
                        menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                        guardar.setEnabled(false);
                        //imbcomentariorecibo.setColorFilter(Color.BLACK);
                        imbcomentariorecibo.setEnabled(false);
                        generarpdf.setEnabled(true);
                        tv_recibo.setText(recibo);
                        Toast.makeText(getContext(), "Se guardo Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            /*String resultadoenviows="0";
            //recibo="R0"+String.valueOf(correlativorecibo);
            int resultado=0,recibows=0;
            String tag="",tag2="";
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
            //correlativorecibo=correlativorecibo+1;
            recibo=String.valueOf(ultimocorrelativorecibo+1);
            resultadoenviows=cobranzaDetalleWSDao.enviarRecibo(cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id)
                    ,SesionEntity.imei,SesionEntity.usuario_id,comentario,SesionEntity.fuerzatrabajo_id);
            if(resultadoenviows.equals("1"))
            {

                //Toast.makeText(getContext(), "Se Envio al Sistema Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
                resultado=GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment);
                if(resultado>0)
                {
                    Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.Black));
                    menu_variable.findItem(R.id.guardar).setIcon(drawable);
                    Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                    drawable2 = DrawableCompat.wrap(drawable2);
                    DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.white));
                    menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);
                    guardar.setEnabled(false);
                    imbcomentariorecibo.setColorFilter(Color.BLACK);
                    imbcomentariorecibo.setEnabled(false);
                    //Toast.makeText(getContext(), "Se Guardo Correctamente la Cobranza", Toast.LENGTH_SHORT).show();

                }else
                {
                   // Toast.makeText(getContext(), "No se Guardo la Cobranza", Toast.LENGTH_SHORT).show();
                }

            }else
            {

                Toast.makeText(getContext(), "No Se Envio la Cobranza, Revisar Conexion a Internet", Toast.LENGTH_SHORT).show();
            }
*/
            if(tipocobranza.equals("Cobranza/Deposito"))
            {
                hiloCobranzaPOS = new HiloCobranzaPOS();
                hiloCobranzaPOS.execute();
            }

        }
    }

    private  class HiloVlidarQR extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg1) {
            String resultadowsqrvalidado="0";
            String resultado="0";
            try {

                /*resultado=CobranzaRepository.EnviarReciboWsRetrofit(
                        cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                        getContext(),
                        "UPDATE",
                        "0",
                        comentario,
                        "0",
                        "1"
                );
                resultadowsqrvalidado=String.valueOf(resultado);*/
                resultado= CobranzaRepository.sendPatchQR(cobranzaDetalleSQLiteDao.getSapCode(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),"Y");
                resultadowsqrvalidado=String.valueOf(resultado);
            } catch (Exception e){
                e.printStackTrace();
            }
            return resultadowsqrvalidado;
        }

        protected void onPostExecute(String result)
        {
            int resultadowsSQLiteQRvalidado=0;
            CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
            resultadowsSQLiteQRvalidado=cobranzaDetalleSQLiteDao.ActualizaWSQRValidadoCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id,result);



            hiloVlidarQR = new HiloVlidarQR();
            //chk_validacionqr.setChecked(true);
            //Toast.makeText(getContext(), "Acabo la Secuencia de Camara", Toast.LENGTH_SHORT).show();

        }
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
                "11",
                SesionEntity.compania_id,
                montocobrado,
                "Deposito",
                bancarizado,
                "19000101",
                fecha,
                SesionEntity.pagodirecto,
                "Y",
                "N"
        );

        if(ValidaSQLite==1)
        {
            Validacion=String.valueOf(ValidaSQLite);

            cobranzaDetalleSQLiteDao.ActualizaCobranzaDetalle(
                    SesionEntity.fuerzatrabajo_id+recibo,
                    recibo,
                    SesionEntity.compania_id,
                    "11"
            );
        }
        Toast.makeText(getContext(), "Deposito Registrado Correctamente", Toast.LENGTH_SHORT).show();
    }

    private class HiloCobranzaPOS extends AsyncTask<String, Void, Object>
    {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Enviando Datos de Cobranza POS", true, false);
        }
        @Override
        protected String doInBackground(String... arg1) {
            String resultadoccabeceraenviows="0";
            CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao=new CobranzaCabeceraSQLiteDao(getContext());
            // BigDecimal bigDecimal=new BigDecimal(et_cobrado.toString());

            String Validacion="";
            try {
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
                        "11",
                        SesionEntity.compania_id,
                        et_cobrado_edit.toString(),
                        "Deposito",
                        bancarizado,
                        "19000101",
                        fecha,
                        SesionEntity.pagodirecto,
                        "N",
                        "N"
                );

                if(ValidaSQLite==1)
                {
                    Validacion=String.valueOf(ValidaSQLite);

                    cobranzaDetalleSQLiteDao.ActualizaCobranzaDetalle(
                            SesionEntity.fuerzatrabajo_id+recibo,
                            recibo,
                            SesionEntity.compania_id,
                            "11"
                    );
                    DepositoRepository depositoRepository =new DepositoRepository(getContext());



                    resultadoccabeceraenviows=
                            depositoRepository.PostCobranzaCabeceraWS
                                    (
                                            SesionEntity.imei,
                                            "CREATE",
                                            SesionEntity.compania_id,
                                            "11",
                                            "Deposito",
                                            SesionEntity.fuerzatrabajo_id+recibo,
                                            SesionEntity.usuario_id,
                                            fecha,
                                            cobrado.setScale(3,RoundingMode.HALF_UP).toString(),
                                            "Pendiente",
                                            "",
                                            SesionEntity.fuerzatrabajo_id,
                                            bancarizado,
                                            "19000101",
                                            "",
                                            SesionEntity.pagodirecto,
                                            "N"
                                    );


                    //resultadoccabeceraenviows=String.valueOf(resultado);
                    //for(int i=0;i<listaConsDepositoAdapterFragment.size();i++)
                    //{
                    String chkwsdepositorecibido="0";
                    //  listaCobranzaDetalleEntity=new ArrayList<CobranzaDetalleSQLiteEntity>();

                    //SE REENVIA EL RECIBO AHI VA EL PATCH
                    chkwsdepositorecibido= CobranzaRepository.sendPatch(cobranzaDetalleSQLiteDao.getSapCode(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),SesionEntity.fuerzatrabajo_id+recibo,"11",recibo);

                        /*chkwsdepositorecibido= CobranzaRepository.EnviarReciboWsRetrofit(
                                cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(listaConsDepositoAdapterFragment.get(i).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                getContext(),
                                "UPDATE",
                                "0",
                                "0",
                                Banco,
                                "1"
                        );*/
                    //resultadowsqrvalidado=String.valueOf(resultado);

                    //}


                    //cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(Grupo,SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,resultadoccabeceraenviows);
                }
                //Log.e("jpcm","------->"+fechadiferida);
            } catch (Exception e)
            {
                e.printStackTrace();
                // TODO: handle exception
                System.out.println(e.getMessage());
                Log.e("REOS","COBRANZACABECERA:error"+e);
            }
            return Validacion;
        }

        protected void onPostExecute(Object result)
        {
            //getActivity().setTitle("Deposito");
            if(result.equals("1"))
            {
                /*etgrupo.setEnabled(false);
                txtfecha.setEnabled(false);
                spnbanco.setEnabled(false);
                spnbanco.setClickable(false);*/

                Toast.makeText(getContext(), "Deposito Registrado Correctamente", Toast.LENGTH_SHORT).show();
                /*String fragment = "", accion = "", compuesto = "";
                fragment = "CobranzaCabeceraView";
                accion = "nuevoinicio";
                compuesto = fragment + "-" + accion;
                Object object = null;
                mListener.onFragmentInteraction(compuesto, object);*/
            }
            else
            {
                Toast.makeText(getContext(), "Deposito No se Pudo registrar revisar Acceso a Internet", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
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


/*
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE:
            {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED))
                {

                } else
                {
                       /* while (ContextCompat.checkSelfPermission(this,
                                Manifest.permission.READ_PHONE_STATE)
                                != PackageManager.PERMISSION_GRANTED )

                    if ((ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED))
                    {
                        // ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},
                        //        MY_PERMISSIONS_REQUEST_CAMERA
                        //1
                        // );
                        requestPermissions(new String[] {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
                    }
                    //break;
                    //}
                }
                break;
            }
        }

    }
*/

    private class HiloEnviarWSCobranzaCabecera extends AsyncTask<String, Void, Object>
    {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd = ProgressDialog.show(getActivity(), "Por favor espere", "Guardando Datos de Deposito", true, false);
        }
        @Override
        protected String doInBackground(String... arg0) {
            String Validacion="";
            try {
                String comentario="",resultadoccabeceraenviows="";
                int ValidaSQLite=0;
                CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao=new CobranzaCabeceraSQLiteDao(getContext());
                DepositoRepository depositoRepository =new DepositoRepository(getContext());
                ValidaSQLite=cobranzaCabeceraSQLiteDao.InsertaCobranzaCabecera(
                        arg0[0],
                        SesionEntity.usuario_id,
                        SesionEntity.fuerzatrabajo_id,
                        arg0[1],
                        SesionEntity.compania_id,
                        arg0[2],
                        arg0[3],
                        arg0[4],
                        arg0[5],
                        arg0[6],
                        arg0[7],
                        "Y",
                        "N"
                );

                resultadoccabeceraenviows=
                        depositoRepository.PostCobranzaCabeceraWS
                                (
                                        SesionEntity.imei,
                                        SesionEntity.compania_id,
                                        arg0[1],
                                        arg0[3],
                                        arg0[0],
                                        SesionEntity.usuario_id,
                                        arg0[6],
                                        arg0[2],
                                        "0",
                                        SesionEntity.fuerzatrabajo_id,
                                        arg0[4],
                                        arg0[5],
                                        "N",
                                        arg0[7],
                                        "",
                                        "N",
                                        "N"

                                );
                cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(arg0[0],SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,resultadoccabeceraenviows,"","");
                cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
                cobranzaDetalleSQLiteDao.ActualizaConexionWSDepositoCobranzaDetalle(
                        recibo,SesionEntity.compania_id,SesionEntity.usuario_id,resultadoccabeceraenviows
                );
                Validacion=String.valueOf(ValidaSQLite);
            } catch (Exception e)
            {
                e.printStackTrace();
                // TODO: handle exception
                System.out.println(e.getMessage());
                Validacion="0";
            }
            return Validacion;
        }

        protected void onPostExecute(Object result)
        {
            if(result.equals("1"))
            {
                Toast.makeText(getContext(), "Deposito Registrado Correctamente", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getContext(), "Deposito No se Pudo registrar revisar Acceso a Internet", Toast.LENGTH_SHORT).show();
            }
            pd.dismiss();
        }
    }
}

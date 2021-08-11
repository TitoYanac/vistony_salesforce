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
import android.os.Environment;

/*
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
*/

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.Controller.Adapters.CobranzaDetalleDialogController;
import com.vistony.salesforce.Controller.Funcionalidades.DocumentPDFController;
import com.vistony.salesforce.Controller.Adapters.ListaClienteDetalleAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaCobranzaDetalleAdapter;
import com.vistony.salesforce.Controller.Funcionalidades.FormulasController;
import com.vistony.salesforce.Controller.Funcionalidades.GPSController;
import com.vistony.salesforce.Dao.Retrofit.CobranzaCabeceraWS;
import com.vistony.salesforce.Dao.SQLIte.ClienteSQliteDAO;
import com.vistony.salesforce.Dao.SQLIte.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.DocumentoDeudaSQLiteDao;
import com.vistony.salesforce.Dao.Adapters.ListaCobranzaDetalleDao;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ConfiguracionSQLEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CobranzaDetalleView extends Fragment {
    ListaClienteDetalleAdapter listaClienteDetalleAdapter;
    ArrayList<ListaClienteDetalleEntity> listaClienteDetalleAdapterFragment;
    ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleEntities;
    ListaCobranzaDetalleAdapter listaCobranzaDetalleAdapter;
    ListView listViewCobranzaDetalle;
    ImageButton imbplus,imbminus,imbaceptar,imbcancelar,imbcancelar2;
    OmegaCenterIconButton imbcomentariorecibo;

    EditText et_cobrado_edit;
    View v;
    ObtenerWSCobranzaDetalle obtenerWSCobranzaDetalle;
    float cobrado=0,nuevo_saldo=0,saldo=0;
    CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha,et_cobrado,chkqrvalidado,recibo_generado;
    static public String recibo;
    DocumentPDFController documentPDFController;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static String NOMBRE_DOCUMENTO = "prueba.pdf";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static String TAG_1 = "text";
    String texto= null;
    DocumentoDeudaSQLiteDao documentoDeudaSQLiteDao;
    int resultado=0,correlativorecibo=0,ultimocorrelativorecibo=0,correlativorecibows=0;
    private OnFragmentInteractionListener mListener;
    EnviarWSCobranzaDetalle enviarWSCobranzaDetalle;
    static public ImageView imvprueba;
    CheckBox chkpagoadelantado,chk_bancarizado,chk_pago_directo,chk_pago_pos;;
    MenuItem generarpdf,validarqr,guardar;
    TextView tv_recibo;
    public static CheckBox chk_validacionqr;
    static HiloVlidarQR hiloVlidarQR;
    ListaClienteDetalleEntity listaClienteDetalleEntity;
    ClienteSQliteDAO clienteSQliteDAO;
    ArrayList<ClienteSQLiteEntity> listaClienteSQLiteEntity;
    private boolean linternaOn;
    private boolean tieneFlash;
    private Camera objCamara;
    Camera.Parameters parametrosCamara;
    private final static String NOMBRE_DIRECTORIO = "MiPdf";
    public static String comentario="";
    int resultadows=0;
    String resultadoenviows="0";
    Menu menu_variable;
    ConfiguracionSQLiteDao configuracionSQLiteDao;
    ArrayList<ConfiguracionSQLEntity> listaConfiguracionSQLEntity;
    String vinculaimpresora;
    UsuarioSQLiteDao usuarioSQLiteDao;
    String Conexion="";
    ArrayList<UsuarioSQLiteEntity> listaUsuarioSQLiteEntity;
    ArrayList<ListaHistoricoCobranzaEntity> Listado = new ArrayList<>();
    private final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE=2;
    String Estadochkbancarizado="",Estadodepositodirecto="",Estadopagopos="",Estadopagoadelantado="";
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    String cliente_id_visita,domembarque_id_visita,zona_id_visita;
    private ProgressDialog pd;
    HiloEnviarWSCobranzaCabecera hiloEnviarWSCobranzaCabecera;
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
        hiloVlidarQR.execute();
        return fragment;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String obtenerFechaYHoraActual() {
        String formato = "yyyy-MM-dd HH:mm";
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
        documentPDFController = new DocumentPDFController();
        documentoDeudaSQLiteDao = new DocumentoDeudaSQLiteDao(getContext());
        cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        listaCobranzaDetalleEntities = new ArrayList<CobranzaDetalleSQLiteEntity>();
        clienteSQliteDAO = new ClienteSQliteDAO(getContext());

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {

            gpsController =  new GPSController(getContext());
            mLocation = gpsController.getLocation(mLocation);
            latitude = mLocation.getLatitude();
            longitude= mLocation.getLongitude();
        }
        //fecha =obtenerFechaYHoraActual();
        objCamara = Camera.open();

        parametrosCamara = objCamara.getParameters();
        tieneFlash = getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
        listaConfiguracionSQLEntity =  new ArrayList<>();
        usuarioSQLiteDao = new UsuarioSQLiteDao(getContext());
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
                        if(Listado.get(j).getBancarizacion().equals("True"))
                        {
                            Estadochkbancarizado="1";
                        }
                        else if(Listado.get(j).getBancarizacion().equals("False"))
                        {
                            Estadochkbancarizado="0";
                        }

                        else if(Listado.get(j).getBancarizacion().equals("1"))
                        {
                            Estadochkbancarizado="1";
                        }
                        else if(Listado.get(j).getBancarizacion().equals("0"))
                            {
                                Estadochkbancarizado="0";
                            }
                        Log.e("REOS","CobranzaDetalleView-Listado.get(j).getDepositodirecto(): "+Listado.get(j).getDepositodirecto());
                        if(Listado.get(j).getDepositodirecto().equals("True"))
                        {
                            Estadodepositodirecto="1";
                        }
                        else if(Listado.get(j).getDepositodirecto().equals("False"))
                        {
                            Estadodepositodirecto="0";
                        }

                        else if(Listado.get(j).getDepositodirecto().equals("1"))
                        {
                            Estadodepositodirecto="1";
                        }
                        else if(Listado.get(j).getDepositodirecto().equals("0"))
                        {
                            Estadodepositodirecto="0";
                        }

                        Estadopagopos=Listado.get(j).getPagopos();
                        Log.e("REOS","CobranzaDetalleView:-HistoricoCobranzaView: "+Listado.size());

                        //String[] fechaCobrado= Listado.get(j).getFechacobranza().split(" ");
                        if(!Listado.get(j).getFechacobranza().isEmpty()){
                            fecha=Listado.get(j).getFechacobranza();
                        }else{
                            Log.e("jpcm","FEHCA LLENA");
                        }
                        if(Listado.get(j).getDocumento_id().equals("0"))
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

        imbaceptar.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View view) {
                                                int p=0;
                                              int total=0;
                                              for (int i = 0; i < et_cobrado_edit.getText().toString().length(); i++){
                                                  char  letra;

                                                  letra=et_cobrado_edit.getText().toString().charAt(i);
                                                  if(letra=='.')
                                                  {
                                                    p++;

                                                  }
                                                  else
                                                      {
                                                          total=total+Integer.parseInt(String.valueOf(""+letra));
                                                      }

                                                  //Tratamiento del caracter
                                              }

                                              String saldo_evaluar="0";
                                              if (!chkpagoadelantado.isChecked())
                                              {
                                                  for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {
                                                      saldo_evaluar = listaClienteDetalleAdapterFragment.get(i).saldo;
                                                  }
                                              }

                                              if(et_cobrado_edit.getText().toString().equals("")||et_cobrado_edit.getText().toString().equals(".")
                                              ||et_cobrado_edit.getText().toString().equals(" ")||(p>1)||(Float.parseFloat(et_cobrado_edit.getText().toString())>Float.parseFloat(saldo_evaluar)&&!chkpagoadelantado.isChecked())
                                              )
                                              {
                                                  et_cobrado_edit.setText("0");
                                                  Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
                                                }
                                              else
                                                  {


                                                  DecimalFormat format = new DecimalFormat("#0.00");
                                                  if (Float.parseFloat(et_cobrado_edit.getText().toString()) >= 1) {
//150169


                                                      String newSaldo=String.valueOf(format.format(Float.parseFloat(et_cobrado_edit.getText().toString())));
                                                      String loQueQuieroBuscar = ",";
                                                      String[] palabras = loQueQuieroBuscar.split("\\s+");

                                                      boolean notHaveComa=true;
                                                      for (String palabra : palabras) {
                                                          if (newSaldo.contains(palabra)) {
                                                              notHaveComa=false;
                                                          }
                                                      }

                                                      if(notHaveComa){

                                                          Drawable drawable = menu_variable.findItem(R.id.guardar).getIcon();
                                                          drawable = DrawableCompat.wrap(drawable);
                                                          DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(), R.color.white));
                                                          menu_variable.findItem(R.id.guardar).setIcon(drawable);
                                                          guardar.setEnabled(true);

                                                          if (chkpagoadelantado.isChecked()) {
                                                              for (int j = 0; j < listaClienteDetalleAdapterFragment.size(); j++) {
                                                                  cobrado = Float.parseFloat(et_cobrado_edit.getText().toString());
                                                                  listaClienteDetalleAdapterFragment.get(j).setCobrado(String.valueOf(cobrado));
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

                                                              for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++) {

                                                                  String cobranza = "";
                                                                  //cobrado=Integer.parseInt(listaClienteDetalleAdapterFragment.get(i).getCobrado());
                                                                  if (et_cobrado_edit.getText().toString().equals("")) {
                                                                      cobranza = "0";
                                                                  } else if (et_cobrado_edit.getText().toString().equals(".")) {
                                                                      cobranza = "0";
                                                                  } else {
                                                                      cobranza = et_cobrado_edit.getText().toString();
                                                                  }
                                                                  cobrado = Float.parseFloat(cobranza);
                                                                  saldo = Float.parseFloat(listaClienteDetalleAdapterFragment.get(i).getSaldo());
                                                                  listaClienteDetalleAdapterFragment.get(i).setSaldo(format.format(saldo));
                                                                  if (cobrado <= saldo && cobrado > 0) {
                                                                      listaClienteDetalleAdapterFragment.get(i).setCobrado(String.valueOf(format.format(cobrado)));
                                                                      nuevo_saldo = saldo - cobrado;
                                                                      //Object object=format.format(nuevo_saldo);
                                                                      //nuevo_saldo=Float.valueOf() ;
                                                                      if (nuevo_saldo == .00) {
                                                                          nuevo_saldo = 0;
                                                                      }



                                                                      listaClienteDetalleAdapterFragment.get(i).setNuevo_saldo(String.valueOf(format.format(nuevo_saldo)));
                                                                  /*if (cobrado > 3500) {
                                                                      chk_bancarizado.setChecked(true);
                                                                  }*/

                                                                  } else {
                                                                      Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();

                                                                  }
                                                                  obtenerWSCobranzaDetalle = new ObtenerWSCobranzaDetalle();
                                                                  obtenerWSCobranzaDetalle.execute();
                                                              }
                                                          }
                                                      }else{
                                                          changeFormatKeyboard().show();
                                                      }




                                                  } else {
                                                      Toast.makeText(getContext(), "Ingrese un Monto de cobranza Valido", Toast.LENGTH_SHORT).show();
                                                  }

                                              }
                                          }

                                      }
        );
        imbcancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                for (int i = 0; i < listaClienteDetalleAdapterFragment.size(); i++)
                {
                    et_cobrado_edit.setText(String.valueOf("0"));
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
                if(Estadopagopos.equals("1")){
                    chk_pago_pos.setChecked(true);
                }
                else
                {
                    chk_pago_pos.setChecked(false);
                }

                if(Estadopagoadelantado.equals("1"))
                {
                    chkpagoadelantado.setChecked(true);
                }


            }else
            {

                if(SesionEntity.pagodirecto.equals("1"))
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
            if(SesionEntity.pagopos.equals("1"))
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
                 if(chk_bancarizado.isChecked())
                 {

                     createSimpleDialog(estado).show();
                 }
                 else
                     {

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
        if(Estadodepositodirecto.equals("1"))
        {
            chk_pago_directo.setChecked(true);
            //chk_bancarizado.setFocusable(false);
            //chk_bancarizado.setClickable(false);
            //chk_bancarizado.setEnabled(false);

        }
        else if(Estadodepositodirecto.equals("0"))
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
        menu_variable=menu;




        if (getArguments() != null) {
            if(!(Listado==null))
            {
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

            }
            else
                {
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


       listaConfiguracionSQLEntity=configuracionSQLiteDao.ObtenerConfiguracion();
        for(int i=0;i<listaConfiguracionSQLEntity.size();i++)
        {
            vinculaimpresora=listaConfiguracionSQLEntity.get(i).getVinculaimpresora();
        }

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
            try {

                //llenarlista(listaClienteDetalleAdapter.ArraylistaClienteDetalleEntity);

            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return "1";
        }

        protected void onPostExecute(Object result)
        {
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
                //fecha =obtenerFechaYHoraActual();
                //alertaGuardarCobranza().show();
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
                    fecha =obtenerFechaYHoraActual();
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

                                    if(vinculaimpresora.equals("0")){
                                        //generarpdf.setEnabled(true);
                                        Toast.makeText(getContext(), "Impresora No Vinculada - Favor de Vincular para proseguir", Toast.LENGTH_SHORT).show();
                                    }else{


                                            //resultado=GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment);
                                            if(SesionEntity.pagopos.equals("1"))
                                            {
                                                resultado=GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment,"Cobranza/Deposito");
                                            }
                                            if(SesionEntity.pagopos.equals("0"))
                                            {
                                                resultado=GuardarCobranzaSQLite(listaClienteDetalleAdapterFragment,"Cobranza");
                                            }

                                            if(resultado>0)
                                            {
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
                                                enviarWSCobranzaDetalle =  new EnviarWSCobranzaDetalle();
                                                enviarWSCobranzaDetalle.execute();

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
                                //listener.onNegativeButtonClick();
                            }
                        });

        return builder.create();
    }

    public AlertDialog alertaGenerarPDF() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Generar el Archivo PDF?")
                .setPositiveButton("OK",

                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if(listaClienteDetalleAdapterFragment.size()>0)
                                {
                                    SesionEntity.imagen="R"+recibo;
                                   // fecha =obtenerFechaYHoraActual();
                                    Log.e("jpcm","seimrpe se ejecuta esto antes de");
                                    documentPDFController.generarPdf(getContext(), listaClienteDetalleAdapterFragment,SesionEntity.fuerzatrabajo_id,SesionEntity.nombrefuerzadetrabajo,recibo,fecha);
                                    Toast.makeText(getContext(), "Se creo tu archivo pdf", Toast.LENGTH_SHORT).show();

                                    //documentPDFController.showPdfFile(recibo+".pdf",getContext());
                                    File file = new File(Environment
                                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                            NOMBRE_DIRECTORIO);
                                   String Cadenafile="";
                                    Cadenafile=String.valueOf(file);
                                    String ruta=Cadenafile+"/"+recibo+".pdf";
                                   MenuView.getPrinterInstance().printPdf(ruta, 500, 0, 0, 0, 20);
                                   String correlativo="";
                                    ConfiguracionSQLiteDao configuracionSQLiteDao = new ConfiguracionSQLiteDao(getContext());
                                    ArrayList<ConfiguracionSQLEntity> arraylistconfiguracion =  new ArrayList<ConfiguracionSQLEntity>();
                                   arraylistconfiguracion=configuracionSQLiteDao.ObtenerCorrelativoConfiguracion();

                                    for(int i=0;i<arraylistconfiguracion.size();i++)
                                    {
                                       correlativo=arraylistconfiguracion.get(i).getSecuenciarecibos();
                                    }


                                    configuracionSQLiteDao.ActualizaCorrelativo(String.valueOf(Integer.parseInt(correlativo)-1));
                                    Drawable drawable = menu_variable.findItem(R.id.validarqr).getIcon();
                                    drawable = DrawableCompat.wrap(drawable);
                                    DrawableCompat.setTint(drawable, ContextCompat.getColor(getContext(),R.color.white));
                                    menu_variable.findItem(R.id.validarqr).setIcon(drawable);
                                    Drawable drawable2 = menu_variable.findItem(R.id.generarpdf).getIcon();
                                    drawable2 = DrawableCompat.wrap(drawable2);
                                    DrawableCompat.setTint(drawable2, ContextCompat.getColor(getContext(),R.color.Black));
                                    menu_variable.findItem(R.id.generarpdf).setIcon(drawable2);

                                    validarqr.setEnabled(true);
                                }
                                else
                                    {
                                        Toast.makeText(getContext(), "No se creo el archivo pdf", Toast.LENGTH_SHORT).show();
                                    }


                               //

                            }
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

    public AlertDialog alertaValidarQR() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Iniciar validacion QR?")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SesionEntity.imagen="R"+recibo;
                                final Activity activity = getActivity();
                                //encenderFlash();
                                IntentIntegrator integrator = new IntentIntegrator(activity);
                                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);

                                //integrator.setScanningRectangle(450, 450);
                                integrator.setPrompt("Scan");
                                integrator.setCameraId(0);
                                integrator.setBeepEnabled(true);
                                integrator.setBarcodeImageEnabled(false);
                                integrator.setOrientationLocked(true);
                                integrator.initiateScan();

                            }
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

    public int GuardarCobranzaSQLite_(ArrayList<ListaClienteDetalleEntity> Lista)
    {
        int resultado=0,recibows=0;
        String tag="",tag2="";


        cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
        correlativorecibo=cobranzaDetalleSQLiteDao.ObtenerUltimoRecibo(SesionEntity.compania_id,SesionEntity.usuario_id);


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
            bancarizado="1";
        }
        else
            {
                bancarizado="0";
            }

        for(int i=0;i<Lista.size();i++)
        {
            recibo=String.valueOf(ultimocorrelativorecibo+1);
            resultado=cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                    "1",
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
                    "0",
                    SesionEntity.usuario_id,
                  //  comentario+"-"+SesionEntity.serialnumber,
                    SesionEntity.serialnumber,
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    "0",
                    SesionEntity.pagodirecto,
                    SesionEntity.pagopos
                    );


            ActualizaDocumentoDeuda(SesionEntity.compania_id,
                    String.valueOf(Lista.get(i).getDocumento_id()),
                    String.valueOf(Lista.get(i).getNuevo_saldo()));

            //SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            //Date date = new Date();

            FormulasController formulasController=new FormulasController(getContext());

            if (!chkpagoadelantado.isChecked())
            {
                ArrayList<ClienteSQLiteEntity> listaclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
                ClienteSQliteDAO clienteSQliteDAO=new ClienteSQliteDAO(getContext());
                listaclientesqlSQLiteEntity=clienteSQliteDAO.ObtenerDatosCliente(String.valueOf(Lista.get(i).getCliente_id()),
                        String.valueOf(SesionEntity.compania_id));
                for(int j=0;j<listaclientesqlSQLiteEntity.size();j++)
                {
                    zona_id_visita=listaclientesqlSQLiteEntity.get(j).getZona_id();
                }
                Log.e("REOS","CobranzaDetalleView: "+zona_id_visita);
                        cliente_id_visita=Lista.get(i).getCliente_id();
                        domembarque_id_visita=Lista.get(i).getDomembarque();
                        //zona_id_visita=Lista.get(i).getZona_id();

            }

            formulasController.RegistraVisita(
                    cliente_id_visita,
                    domembarque_id_visita,
                    zona_id_visita,
                    "02",
                    "02-MOTIVO 02",
                    "Registro Cobranza",
                    getContext(),
                    String.valueOf(latitude),
                    String.valueOf(longitude)
            );

            Log.e("REOS","DomEmbarque_ID: "+Lista.get(i).getDomembarque());

        }

        chk_bancarizado.setFocusable(false);
        chk_bancarizado.setClickable(false);


        return resultado;

    }

    public int GuardarCobranzaSQLite(ArrayList<ListaClienteDetalleEntity> Lista,String tipoCobranza)
    {
        int resultado=0,recibows=0;
        String tag="",tag2="";
        FormulasController formulasController=new FormulasController(getContext());
        cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
        correlativorecibo=cobranzaDetalleSQLiteDao.ObtenerUltimoRecibo(SesionEntity.compania_id,SesionEntity.usuario_id);


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
            bancarizado="1";
        }
        else
        {
            bancarizado="0";
        }

        if(tipoCobranza.equals("Cobranza"))
        {
            for (int i = 0; i < Lista.size(); i++) {
                recibo = String.valueOf(ultimocorrelativorecibo + 1);
                resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                        "1",
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
                        "0",
                        SesionEntity.usuario_id,
                        comentario + "-" + SesionEntity.serialnumber,
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0",
                        SesionEntity.pagodirecto,
                        SesionEntity.pagopos
                );

                ActualizaDocumentoDeuda(SesionEntity.compania_id,
                        String.valueOf(Lista.get(i).getDocumento_id()),
                        String.valueOf(Lista.get(i).getNuevo_saldo()));

            }
        }
        else if(tipoCobranza.equals("Cobranza/Deposito"))
        {
            String sumacobrado="";
            for (int i = 0; i < Lista.size(); i++) {
                recibo = String.valueOf(ultimocorrelativorecibo + 1);
                sumacobrado=String.valueOf(Lista.get(i).getCobrado());
                resultado = cobranzaDetalleSQLiteDao.InsertaCobranzaDetalle(
                        SesionEntity.fuerzatrabajo_id+recibo,
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
                        "0",
                        SesionEntity.usuario_id,
                        comentario + "-" + SesionEntity.serialnumber,
                        "1",
                        "0",
                        "BCPMN",
                        "0",
                        "0",
                        "0",
                        SesionEntity.pagodirecto,
                        SesionEntity.pagopos
                );

                ActualizaDocumentoDeuda(SesionEntity.compania_id,
                        String.valueOf(Lista.get(i).getDocumento_id()),
                        String.valueOf(Lista.get(i).getNuevo_saldo()));

            }
            hiloEnviarWSCobranzaCabecera = new HiloEnviarWSCobranzaCabecera();
            hiloEnviarWSCobranzaCabecera.execute(
                    SesionEntity.fuerzatrabajo_id+recibo,
                    "BCPMN",
                    sumacobrado,
                    "Deposito",
                    bancarizado,
                    "1900-01-01",
                    fecha,
                    SesionEntity.pagodirecto);
        }

        formulasController.RegistraVisita(
                cliente_id_visita,
                domembarque_id_visita,
                zona_id_visita,
                "02",
                "02-MOTIVO 02",
                "Registro Cobranza",
                getContext(),
                String.valueOf(latitude),
                String.valueOf(longitude)
        );
        chk_bancarizado.setFocusable(false);
        chk_bancarizado.setClickable(false);
        return resultado;

    }

    public int ActualizaDocumentoDeuda(String compania_id,String documento_id,String nuevo_saldo)
    {
        int resultado=0;
        resultado=documentoDeudaSQLiteDao.ActualizaNuevoSaldo(compania_id,documento_id,nuevo_saldo);
        return resultado;
    }

    private class EnviarWSCobranzaDetalle extends AsyncTask<String, Void, Object> {

        @Override
        protected String doInBackground(String... arg1) {
            //String argumento1=arg1[0];
            String resultadoenviows="0";
            try {

                if(Conexion.equals("1"))
                {
                    FormulasController formulasController=new FormulasController(getContext());

                    //Antiguo Envio de Cobranza Detalle WS - SOAP
                    //resultadoenviows=cobranzaDetalleWSDao.enviarRecibo(cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id)
                    //      ,SesionEntity.imei,SesionEntity.usuario_id,comentario+"-"+SesionEntity.serialnumber,SesionEntity.fuerzatrabajo_id);
                    //Nuevo Envio de Cobranza De

                    resultadoenviows=formulasController.EnviarReciboWsRetrofit(
                            cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                            getContext(),
                            "CREATE",
                            "0",
                            "0",
                            "0",
                            "0"
                    );

                }
               /* else if(Conexion.equals("0"))
                {
                    //Toast.makeText(getContext(), "Se guardo Correctamente la Cobranza, pero no se Envio a la Nube", Toast.LENGTH_SHORT).show();
                }*/



            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            return String.valueOf(resultadoenviows);
        }

        protected void onPostExecute(Object result)
        {
            try {
                    if(result.equals("1"))
                    {
                        cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(recibo,SesionEntity.compania_id,SesionEntity.usuario_id,result.toString());
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


        }
    }

    private  class HiloVlidarQR extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... arg1) {
            String resultadowsqrvalidado="0";
            String resultado="0";
            try {

                FormulasController formulasController=new FormulasController(getContext());
                resultado=formulasController.EnviarReciboWsRetrofit(
                        cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(recibo, SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                        getContext(),
                        "UPDATE",
                        "0",
                        comentario,
                        "0",
                        "1"
                );
                resultadowsqrvalidado=String.valueOf(resultado);





                //if((!resultadoenviows.equals("0")))
               // {
                //    Toast.makeText(getContext(), "Se Envio al Sistema Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
               // }else
               // {
                //    Toast.makeText(getContext(), "No Se Envio al Sistema Correctamente la Cobranza", Toast.LENGTH_SHORT).show();
               // }
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
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
                CobranzaCabeceraWS cobranzaCabeceraWS=new CobranzaCabeceraWS(getContext());
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
                        "1"
                );

                resultadoccabeceraenviows=
                        cobranzaCabeceraWS.PostCobranzaCabeceraWS
                                (
                                        SesionEntity.imei,
                                        SesionEntity.compania_id,
                                        arg0[1],
                                        arg0[3],
                                        arg0[0],
                                        SesionEntity.usuario_id,
                                        arg0[6],
                                        arg0[2],
                                        " ",
                                        SesionEntity.fuerzatrabajo_id,
                                        arg0[4],
                                        arg0[5],
                                        "",
                                        arg0[7],
                                        "1",
                                        "0",
                                        "0"

                                );
                cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(arg0[0],SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,resultadoccabeceraenviows);
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

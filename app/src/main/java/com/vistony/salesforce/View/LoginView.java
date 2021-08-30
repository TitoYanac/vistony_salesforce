package com.vistony.salesforce.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SQLiteController;
import com.vistony.salesforce.Controller.Utilitario.UpdateApp;
import com.vistony.salesforce.Dao.Retrofit.HistoricoDepositoUnidadWS;
import com.vistony.salesforce.Dao.Retrofit.VersionViewModel;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.LoginEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import io.sentry.Sentry;
import io.sentry.protocol.User;
import com.vistony.salesforce.Dao.Retrofit.LoginViewModel;

public class LoginView extends AppCompatActivity{
    public OmegaCenterIconButton btnlogin;
    private Spinner spncompania, spnperfil, spnnombre;
    private ProgressDialog pd;
    private Context context;
    private String Imei;
    private TelephonyManager manager;
    private TextView resultado2;
    private SesionEntity Sesion;
    private String perfil, Companiatext, vendedortext;
    static List<LoginEntity> Llogin;
    static List<LoginEntity> Llogin2;
    private BluetoothAdapter bluetoothAdapter;
    private ImageView imv_compania_login;
    private String result;
    private ArrayList<UsuarioSQLiteEntity> listaUsuariosqliteEntity;
    private UsuarioSQLite usuarioSQLite;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private VideoView videoBG;
    private int temp = 0;
    private LocationManager locationManager;
    private AlertDialog alert = null;
    private String version = "";
    private LoginViewModel loginViewModel;
    private SharedPreferences statusImei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale("EN", "US");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        this.setContentView(R.layout.activity_login);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        statusImei = getSharedPreferences("imeiRegister", Context.MODE_PRIVATE);

        context = this;
        result = "";
        Sesion = new SesionEntity();
        spncompania =findViewById(R.id.spncompania);
        spnperfil =findViewById(R.id.spnperfil);
        spnnombre =findViewById(R.id.spnnombre);
        resultado2 =findViewById(R.id.txtimei);
        Llogin = new ArrayList<LoginEntity>();
        Llogin2 = new ArrayList<LoginEntity>();
        btnlogin =findViewById(R.id.btnlogin);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        imv_compania_login =findViewById(R.id.imv_compania_login);
        listaUsuariosqliteEntity = new ArrayList<UsuarioSQLiteEntity>();
        usuarioSQLite = new UsuarioSQLite(this);
        SesionEntity.loginSesion="0";
        SesionEntity.listaConsDeposito="0";
        ConfiguracionSQLiteDao configuracionSQLiteDao5=  new ConfiguracionSQLiteDao(getBaseContext());
        configuracionSQLiteDao5.ActualizaVinculo("0");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        loginViewModel =  new ViewModelProvider(this).get(LoginViewModel.class);
        obtenerImei();

        loginViewModel.getAndLoadUsers(Imei,getApplicationContext()).observe(LoginView.this, data -> {
            if(data==null){
                Toast.makeText(context, "Ocurrio un error en la red", Toast.LENGTH_LONG).show();
            }else if(data.isEmpty()){
                btnlogin.setEnabled(false);
                btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_onclick));
                btnlogin.setText(getResources().getString(R.string.sinInfo));
                btnlogin.setClickable(false);

                Toast.makeText(context, "Sin información local", Toast.LENGTH_LONG).show();
            }else{
                //loginModalView.getAndLoadUsers(Imei,getApplicationContext()).removeObservers(LoginView.this);

                ArrayAdapter<String> adapterProfile = new ArrayAdapter<String>(this,R.layout.layout_custom_spinner,data);
                spnperfil.setAdapter(adapterProfile);
                adapterProfile.notifyDataSetChanged();
            }
        });

        /****Mejora****/
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
           // AlertNoGps();
            androidx.fragment.app.DialogFragment dialogFragment = new AlertGPSDialogController();
            dialogFragment.show(((FragmentActivity) context). getSupportFragmentManager (),"un dialogo");
        }
        /********/

        ObtenerVideo();
        bluetoothAdapter.enable();
        turnGPSOn();

        //String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        //Toast.makeText(getApplicationContext(),android_id,Toast.LENGTH_SHORT).show();

        spnperfil.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    perfil = parent.getItemAtPosition(position).toString();

                    ArrayList<String> companiaList = loginViewModel.getCompanies(perfil);

                    ArrayAdapter<String> adapterCompania = new ArrayAdapter<String>(LoginView.this,R.layout.layout_custom_spinner,companiaList);
                    spncompania.setAdapter(adapterCompania);
                    adapterCompania.notifyDataSetChanged();
                }@Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            }
        );

        spncompania.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Companiatext = spncompania.getSelectedItem().toString();
                    ArrayList<String> usuarioList = loginViewModel.getUsers(perfil,Companiatext);

                    ArrayAdapter<String> adapterUser = new ArrayAdapter<String>(LoginView.this,R.layout.layout_custom_spinner,usuarioList);
                    spnnombre.setAdapter(adapterUser);
                    adapterUser.notifyDataSetChanged();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            }
        );

        spnnombre.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        vendedortext = spnnombre.getSelectedItem().toString();

                        btnlogin.setEnabled(true);
                        btnlogin.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.custom_border_button_red));
                        btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
                        btnlogin.setClickable(true);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );

        manager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        
        verifyPermission();

        TextView viewVersion=findViewById(R.id.txt_internet);
        PackageInfo pInfo = null;

        try{
            pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            SQLiteController db = new SQLiteController(getApplicationContext());
            viewVersion.setText("vs:"+version+" db:"+db.getWritableDatabase().getVersion());

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume(){
        super.onResume();
        ObtenerVideo();
    }

    private void obtenerImei() {

        Imei=statusImei.getString("imei", BuildConfig.IMEI_DEFAULT);

        if(Imei.equals("")){
            final Dialog dialog = new Dialog(LoginView.this);
            dialog.setContentView(R.layout.layout_imei_dialog);

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            final EditText textDImei = dialog.findViewById(R.id.textEditImei);

            ImageView image =dialog.findViewById(R.id.image);
            image.setImageResource(R.mipmap.logo_circulo);

            final Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            final Button dialogButtonExit =  dialog.findViewById(R.id.dialogButtonCancel);

            dialogButton.setText("GUARDAR");
            dialogButtonExit.setText("VER IMEI");

          //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            image.setBackground(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();

            dialogButtonExit.setOnClickListener(v -> {
                Intent i = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
                startActivity(i);
            });

            dialogButton.setOnClickListener(v -> {
                if(textDImei.getText().length()==15){
                    if(temp==1){
                        SharedPreferences.Editor editor = statusImei.edit();
                        editor.putString("imei",textDImei.getText().toString());
                        editor.apply();

                        dialog.dismiss();
                        Toast.makeText(context, "IMEI registrado", Toast.LENGTH_LONG).show();
                        Imei=textDImei.getText().toString();

                        loginViewModel.getAndLoadUsers(Imei,getApplicationContext());

                    }else{
                        Toast.makeText(context, "Vuelva a presionar guardar para configurar su IMEI...", Toast.LENGTH_SHORT).show();
                        temp=1;
                    }
                }else{
                    Toast.makeText(context, "Error al escribir IMEI...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void ObtenerVideo(){
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+ "/" + R.raw.plane);
        videoBG  = findViewById(R.id.videoView);
        videoBG.setVideoURI(uri);
        videoBG.requestFocus();
        videoBG.start();

        videoBG.setOnPreparedListener(mp -> mp.setLooping(true));
    }

    private void verifyPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int permsRequestCode = 100;

            String[] perms = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            };

            int accessReadPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int accessWriteExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);
            int accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessReadExternalStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);

            if (accessReadPhoneState == PackageManager.PERMISSION_GRANTED && accessWriteExternalStorage == PackageManager.PERMISSION_GRANTED && accessCamera == PackageManager.PERMISSION_GRANTED &&  accessCoarseLocation == PackageManager.PERMISSION_GRANTED && accessReadExternalStorage==PackageManager.PERMISSION_GRANTED) {
                //se realiza metodo si es necesario...
            } else {
                requestPermissions(perms, permsRequestCode);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            for (int i=0;i<grantResults.length;i++) {
                if(grantResults[i]==-1){
                    verifyPermission();
                    break;
                }
            }
        }
    }

    public void btnLogin(View v){
        btnlogin.setEnabled(false);
        btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_onclick));
        btnlogin.setText(getResources().getString(R.string.Ingresando));

        btnlogin.setClickable(false);

        listaUsuariosqliteEntity.clear();
        listaUsuariosqliteEntity= loginViewModel.loginUser(Companiatext,vendedortext);

        if(listaUsuariosqliteEntity!=null) {
            for(int g=0;g<listaUsuariosqliteEntity.size();g++){

                Sesion.compania_id = listaUsuariosqliteEntity.get(g).getCompania_id();
                Sesion.fuerzatrabajo_id = listaUsuariosqliteEntity.get(g).getFuerzatrabajo_id();
                Sesion.nombrecompania = listaUsuariosqliteEntity.get(g).getNombrecompania();
                Sesion.nombrefuerzadetrabajo = listaUsuariosqliteEntity.get(g).getNombrefuerzatrabajo();
                Sesion.nombreusuario = listaUsuariosqliteEntity.get(g).getNombreUsuario();
                Sesion.usuario_id = listaUsuariosqliteEntity.get(g).getUsuario_id();
                Sesion.imei = Imei;
                Sesion.recibo = listaUsuariosqliteEntity.get(g).getRecibo();
                Sesion.almacen_id=listaUsuariosqliteEntity.get(g).getAlmacen_id();
                Sesion.planta_id=listaUsuariosqliteEntity.get(g).getPlanta();
                Sesion.perfil_id=listaUsuariosqliteEntity.get(g).getPerfil();
                Sesion.cogsacct=listaUsuariosqliteEntity.get(g).getCogsacct();
                Sesion.u_vist_ctaingdcto=listaUsuariosqliteEntity.get(g).getU_vist_ctaingdcto();
                Sesion.documentsowner=listaUsuariosqliteEntity.get(g).getDocumentsowner();
                Sesion.U_VIST_SUCUSU=listaUsuariosqliteEntity.get(g).getU_VIST_SUCUSU();
                Sesion.CentroCosto=listaUsuariosqliteEntity.get(g).getCentroCosto();
                Sesion.UnidadNegocio=listaUsuariosqliteEntity.get(g).getUnidadNegocio();
                Sesion.LineaProduccion=listaUsuariosqliteEntity.get(g).getLineaProduccion();
                Sesion.Impuesto_ID=listaUsuariosqliteEntity.get(g).getImpuesto_ID();
                Sesion.Impuesto=listaUsuariosqliteEntity.get(g).getImpuesto();
                Sesion.TipoCambio=listaUsuariosqliteEntity.get(g).getTipoCambio();
                Sesion.U_VIS_CashDscnt=listaUsuariosqliteEntity.get(g).getU_VIS_CashDscnt();
            }

            verificationVersion();
        }else{
            Toast.makeText(context, "Equipo No Autorizado, Comunicarse a Helpdesk@vistony.com", Toast.LENGTH_SHORT).show();
            btnlogin.setEnabled(true);

            btnlogin.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.custom_border_button_red));
            btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
            btnlogin.setClickable(true);
        }
    }

    private void verificationVersion(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Intent intent = new Intent(getApplicationContext(), MenuView.class);

        if(networkInfo != null && networkInfo.isConnected()){
            boolean userUnlinked=(SesionEntity.compania_id==null)?true:false;
            //verificamos que el usuario elegido para la sesion tenga codigo de compania

            if(!userUnlinked){
                new VersionViewModel().getVs(SesionEntity.imei,version,getApplicationContext()).observe(this, data -> {
                    if(data!=null){
                        if(data.getClass().getName().equals("java.lang.String")){
                            if(data.toString().length()>6){
                                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                                readUserAndLogin(intent);
                            }else{
                                btnlogin.setText("Validando Recibos...");
                                ObtenerPendientesEnvioWS();
                                new UpdateApp(btnlogin,data.toString(),getApplicationContext());
                            }
                        }else if(data.getClass().getName().equals("java.lang.Boolean")){
                            readUserAndLogin(intent);
                        }
                    }else{
                        Toast.makeText(context, "Error en la respuesta del servidor...", Toast.LENGTH_SHORT).show();
                        readUserAndLogin(intent);
                    }
                });
            }else{
                btnlogin.setText("Usuario Desvinculado");
                Toast.makeText(context, "El usuario selecionado no esta vinculado, SELECIONE OTRO USUARIO", Toast.LENGTH_LONG).show();
            }
        }else{
            if(statusImei.getString("status", "not").equals("yes")){
                startActivity(intent);
                finish();
            }else{
                btnlogin.setText("Actualiza la App movil");
                Toast.makeText(context, "Es necesario conectarse a internet para actualizar esta version de la App", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void readUserAndLogin(Intent intent){
        Map<String, String> vendedor = new HashMap<>();
        vendedor.put("Imei", SesionEntity.imei);
        vendedor.put("Compañia",SesionEntity.compania_id);
        vendedor.put("Fuerza de Venta", SesionEntity.fuerzatrabajo_id);

        User user = new User();
        user.setOthers(vendedor);
        Sentry.setUser(user);
        startActivity(intent);

        finish();
    }

    @Override
    public void onBackPressed() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        } else {
            bluetoothAdapter.enable();
        }
        System.exit(0);
    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(),Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(!provider.contains("gps")){ //if gps is disabled
             final Intent poke = new Intent();
             poke.setClassName("com.android.settings","com.android.settings.widget.SettingsAppWidgetProvider");
             poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
             poke.setData(Uri.parse("3")); sendBroadcast(poke);
        } }

    private void turnGPSOff(){
        String provider = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if(provider.contains("gps")){
            //if gps is enabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings",
                    "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3")); sendBroadcast(poke);
        }
    }

    private String ObtenerPendientesEnvioWS(){

        String resultadoPendientesEnvioWS="",resultadoPendientesEnvioCabeceraWS="0",resultadoPendientesEnvioDetalleWS="0",resultadoRegistroCabecera="0",resultadoRegistroDetalle="0";
        String tipo="0",compuesto="0";

        //Envia Cabecera Pendiente de Envio
        CobranzaCabeceraSQLiteDao cobranzaCabeceraSQLiteDao = new CobranzaCabeceraSQLiteDao(this);
        ArrayList<CobranzaCabeceraSQLiteEntity> listaCobranzaCabeceraSQLiteEntity = new ArrayList<>();

        listaCobranzaCabeceraSQLiteEntity = cobranzaCabeceraSQLiteDao.ObtenerCobranzaCabeceraPendientesEnvioTotalWS(SesionEntity.compania_id, SesionEntity.usuario_id);
        if (!(listaCobranzaCabeceraSQLiteEntity.isEmpty())){

            resultadoPendientesEnvioCabeceraWS="1";
            HistoricoDepositoUnidadWS historicoDepositoUnidadWS=new HistoricoDepositoUnidadWS(this);
            for (int j = 0; j < listaCobranzaCabeceraSQLiteEntity.size(); j++)
            {
                List<CobranzaCabeceraSQLiteEntity> listaleercobranzacabecera = new ArrayList<>();

                listaleercobranzacabecera = historicoDepositoUnidadWS.getHistoricoDepositoIndividual(
                        SesionEntity.imei,
                        SesionEntity.compania_id,
                        SesionEntity.usuario_id,
                        //listaCobranzaDetalleSQLiteEntity.get(j).getRecibo().toString()
                        listaCobranzaCabeceraSQLiteEntity.get(j).getBanco_id(),
                        listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id()
                );

                if (listaleercobranzacabecera.isEmpty()) {

                    //Alerta("5").show();
                    //MOSTRAR ALERT DE REINGRESO DE APP
/*
                        CobranzaCabeceraWS cobranzaCabeceraWS=new CobranzaCabeceraWS(getContext());
                        resultadoRegistroCabecera=
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
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getComentarioanulado(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getPagodirecto(),
                                            listaCobranzaCabeceraSQLiteEntity.get(j).getPagodirecto()
                                        );


                        if (resultadoRegistroCabecera.equals("1")) {
                            cobranzaCabeceraSQLiteDao.ActualizarCobranzaCabeceraWS(
                                    listaCobranzaCabeceraSQLiteEntity.get(j).getCobranza_id(),
                                    SesionEntity.compania_id,
                                    listaCobranzaCabeceraSQLiteEntity.get(j).getFuerzatrabajo_id(),
                                    resultadoRegistroCabecera);

                        }*/

                }
            }
        }

        if(resultadoPendientesEnvioCabeceraWS.equals("0")) {

            CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(this);

            ArrayList<CobranzaDetalleSQLiteEntity> listaCobranzaDetalleSQLiteEntity = new ArrayList<>();
            listaCobranzaDetalleSQLiteEntity = cobranzaDetalleSQLiteDao.ObtenerCobranzaDetallePendientesEnvioTotalWS(SesionEntity.compania_id, SesionEntity.usuario_id);
            //Envio de Cobranza Detalle Pendientes a WS
            if (!(listaCobranzaDetalleSQLiteEntity.isEmpty())) {
                tipo="3";
                FormulasController formulasController=new FormulasController(this);
                for (int j = 0; j < listaCobranzaDetalleSQLiteEntity.size(); j++) {

                    List<CobranzaDetalleSQLiteEntity> listaleercobranza = new ArrayList<>();

                    listaleercobranza=formulasController.ObtenerListaConvertidaCobranzaDetalleSQLite(
                            this,
                            SesionEntity.imei,
                            SesionEntity.compania_id,
                            SesionEntity.usuario_id,
                            listaCobranzaDetalleSQLiteEntity.get(j).getRecibo()
                    );

                    if (listaleercobranza.isEmpty()) {
                        if (listaCobranzaDetalleSQLiteEntity.get(j).getCobranza_id().equals("1")) {

                           // Alerta("5").show();
                                    /*
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

                                    ListaCobranzaDetalleSQLiteEntity.add(cobranzaDetalleSQLiteEntity);
                                    //resultadoRegistroDetalle = cobranzaDetalleWSDao.enviarRecibo(ListaCobranzaDetalleSQLiteEntity, SesionEntity.imei, SesionEntity.usuario_id, listaCobranzaDetalleSQLiteEntity.get(j).getComentario(), SesionEntity.fuerzatrabajo_id);
                                    resultadoRegistroDetalle=formulasController.EnviarReciboWsRetrofit(
                                            cobranzaDetalleSQLiteDao.ObtenerCobranzaDetalleporRecibo(
                                                    ListaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id),
                                            getContext(),
                                            "CREATE",
                                            "0",
                                            "0",
                                            "0",
                                            "0"
                                    );
                                    if (resultadoRegistroDetalle.equals("1")) {
                                        cobranzaDetalleSQLiteDao.ActualizaConexionWSCobranzaDetalle(listaCobranzaDetalleSQLiteEntity.get(j).getRecibo(), SesionEntity.compania_id, SesionEntity.usuario_id, resultadoRegistroDetalle);
                                    }
                                    else{
                                        resultadoPendientesEnvioWS = "1";
                                    }*/

                        }
                    }


                }


            } else {
                resultadoPendientesEnvioWS = "0";
            }

                /*}else
                {

                    resultadoPendientesEnvioWS = "1";
                    tipo="2";
                }*/

        }
        else{
            resultadoPendientesEnvioWS="1";
            tipo="1";
        }
        //Validacion de Orden de Venta
        ArrayList<String> listaordenventacabecerasqliteentity=new ArrayList<>();
        OrdenVentaCabeceraSQLite ordenVentaCabeceraSQLite =new OrdenVentaCabeceraSQLite(this);
        listaordenventacabecerasqliteentity= ordenVentaCabeceraSQLite.ObtenerOrdenVentaCabeceraPendientesEnvioWS();
        if(!listaordenventacabecerasqliteentity.isEmpty()) {
            resultadoPendientesEnvioWS="1";
            tipo="4";
        }

        compuesto=resultadoPendientesEnvioWS+"-"+tipo;

        return compuesto;

    }

}

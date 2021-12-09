package com.vistony.salesforce.View;

import static com.vistony.salesforce.Controller.Utilitario.Utilitario.obtenerImei;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.SqliteController;
import com.vistony.salesforce.Controller.Utilitario.UpdateApp;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.Retrofit.HistoricoDepositoUnidadWS;
import com.vistony.salesforce.Dao.Retrofit.LoginRepository;
import com.vistony.salesforce.Dao.Retrofit.VersionViewModel;
import com.vistony.salesforce.Dao.SQLite.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.OrdenVentaCabeceraSQLite;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
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

public class LoginView extends AppCompatActivity{
    public OmegaCenterIconButton btnlogin;
    private Spinner spncompania, spnperfil, spnnombre;
    private static String Imei;
    private SesionEntity Sesion= new SesionEntity();
    private String perfil, Companiatext, vendedortext;
    private BluetoothAdapter bluetoothAdapter;
    private VideoView videoBG;
    private LoginRepository loginRepository;
    private SharedPreferences statusImei;
    private String version;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        statusImei = getSharedPreferences("imeiRegister", Context.MODE_PRIVATE);
        setLocale(statusImei.getString("language", BuildConfig.LANGUAGE_DEFAULT),statusImei.getString("country", BuildConfig.COUNTRY_DEFAULT));

        spncompania =findViewById(R.id.spncompania);
        spnperfil =findViewById(R.id.spnperfil);
        spnnombre =findViewById(R.id.spnnombre);
        btnlogin =findViewById(R.id.btnlogin);

        final TextView viewVersion=findViewById(R.id.txt_internet);
        SqliteController db = new SqliteController(this);
        String env=BuildConfig.BASE_ENVIRONMENT.equals("/api")?"Producción":"Test";
        version=Utilitario.getVersion(getApplication());

        viewVersion.setText(env+": "+version+" db: "+db.getWritableDatabase().getVersion());

       // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        loginRepository =  new ViewModelProvider(this).get(LoginRepository.class);
        Imei=obtenerImei(statusImei,this,loginRepository);

        loadConfigurationPrinter();
        ObtenerVideo();
        verifyPermission();

        loginRepository.getAndLoadUsers(Imei,this).observe(LoginView.this, data -> {
            if(data==null){
                Toast.makeText(this, "Ocurrio un error en la red", Toast.LENGTH_LONG).show();
            }else if(data.isEmpty()){
                btnlogin.setEnabled(false);
                btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_onclick));
                btnlogin.setText(getResources().getString(R.string.sinInfo));
                btnlogin.setClickable(false);

                Toast.makeText(this, "Sin información local", Toast.LENGTH_LONG).show();
            }else{
                ArrayAdapter<String> adapterProfile = new ArrayAdapter<String>(this,R.layout.layout_custom_spinner,data);
                spnperfil.setAdapter(adapterProfile);
                adapterProfile.notifyDataSetChanged();
            }
        });

        /****Mejora**
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            androidx.fragment.app.DialogFragment dialogFragment = new AlertGPSDialogController();
            dialogFragment.show(((FragmentActivity) this). getSupportFragmentManager (),"un dialogo");
        }
        ******/

        //turnGPSOn();

        spnperfil.setOnItemSelectedListener(
            new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                    perfil = parent.getItemAtPosition(position).toString();

                    ArrayList<String> companiaList = loginRepository.getCompanies(perfil);

                    ArrayAdapter<String> adapterCompania = new ArrayAdapter<String>(LoginView.this,R.layout.layout_custom_spinner,companiaList);
                    spncompania.setAdapter(adapterCompania);
                    adapterCompania.notifyDataSetChanged();
                }@Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            }
        );

        spncompania.setOnItemSelectedListener(
            new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Companiatext = spncompania.getSelectedItem().toString();
                    ArrayList<String> usuarioList = loginRepository.getUsers(perfil,Companiatext);

                    ArrayAdapter<String> adapterUser = new ArrayAdapter<String>(LoginView.this,R.layout.layout_custom_spinner,usuarioList);
                    spnnombre.setAdapter(adapterUser);
                    adapterUser.notifyDataSetChanged();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            }
        );

        spnnombre.setOnItemSelectedListener(
                new OnItemSelectedListener() {
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
        

    }

    @Override
    public void onResume(){
        super.onResume();
        ObtenerVideo();
    }

    private void loadConfigurationPrinter(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.enable();
        ConfiguracionSQLiteDao configuracionSQLiteDao5=  new ConfiguracionSQLiteDao(getBaseContext());
        configuracionSQLiteDao5.ActualizaVinculo("0");
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

        UsuarioSQLiteEntity userEntity= loginRepository.loginUser(Companiatext,vendedortext);

        if(userEntity!=null) {

            Sesion.compania_id = userEntity.getCompania_id();
            Sesion.fuerzatrabajo_id = userEntity.getFuerzatrabajo_id();
            Sesion.nombrecompania = userEntity.getNombrecompania();
            Sesion.nombrefuerzadetrabajo = userEntity.getNombrefuerzatrabajo();
            Sesion.nombreusuario = userEntity.getNombreUsuario();
            Sesion.usuario_id = userEntity.getUsuario_id();
            Sesion.imei = userEntity.getImei();
            Sesion.recibo = userEntity.getRecibo();
            Sesion.almacen_id=userEntity.getAlmacen_id();
            Sesion.planta_id=userEntity.getPlanta();
            Sesion.perfil_id=userEntity.getPerfil();
            Sesion.cogsacct=userEntity.getCogsacct();
            Sesion.u_vist_ctaingdcto=userEntity.getU_vist_ctaingdcto();
            Sesion.documentsowner=userEntity.getDocumentsowner();
            Sesion.U_VIST_SUCUSU=userEntity.getU_VIST_SUCUSU();
            Sesion.CentroCosto=userEntity.getCentroCosto();
            Sesion.UnidadNegocio=userEntity.getUnidadNegocio();
            Sesion.LineaProduccion=userEntity.getLineaProduccion();
            Sesion.Impuesto_ID=userEntity.getImpuesto_ID();
            Sesion.Impuesto=userEntity.getImpuesto();
            Sesion.U_VIS_CashDscnt=userEntity.getU_VIS_CashDscnt();
            Sesion.FLAG_STOCK=userEntity.getFLAG_STOCK();
            Sesion.FLAG_BACKUP=userEntity.getFLAG_BACKUP();
            Sesion.rate=userEntity.getRate();
            Sesion.Print=userEntity.getPrint();
            Sesion.activecurrency=userEntity.getActivecurrency();
            Log.e("REOS","LoginView.Sesion.rate: "+Sesion.rate);
            String country=userEntity.getCountry();
            String language=userEntity.getLenguage();
            Log.e("REOS","LoginView-SesionEntity.Print" + Sesion.Print);
            if( country==null  && language==null){
                Toast.makeText(this, "El sistema no pudo indentificar su País y Lenguaje configurado...", Toast.LENGTH_LONG).show();
                btnlogin.setEnabled(true);

                btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_red));
                btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
                btnlogin.setClickable(true);
            }else{

                SharedPreferences.Editor editor = statusImei.edit();
                editor.putString("country",userEntity.getCountry());
                editor.putString("language",userEntity.getLenguage());
                editor.apply();


                verificationVersion();
            }
        }else{
            Toast.makeText(this, "Equipo No Autorizado, Comunicarse a Helpdesk@vistony.com", Toast.LENGTH_SHORT).show();
            btnlogin.setEnabled(true);

            btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_red));
            btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
            btnlogin.setClickable(true);
        }
    }

    public void setLocale(String lang,String country) {

        Locale myLocale = new Locale(lang,country);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        //Location.getTime();
    }

    private void verificationVersion(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Intent intent = new Intent(this, MenuView.class);

        if(networkInfo != null && networkInfo.isConnected()){
            boolean userUnlinked=(SesionEntity.compania_id==null)?true:false;
            //verificamos que el usuario elegido para la sesion tenga codigo de compania

            if(!userUnlinked){
                new VersionViewModel().getVs(SesionEntity.imei,version,this).observe(this, data -> {
                    if(data!=null){
                        if(data.getClass().getName().equals("java.lang.String")){
                            if(data.toString().length()>6){
                                Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
                                readUserAndLogin(intent);
                            }else{
                                btnlogin.setText("Validando Recibos...");
                                ObtenerPendientesEnvioWS();
                                new UpdateApp(btnlogin,data.toString(),this);
                            }
                        }else if(data.getClass().getName().equals("java.lang.Boolean")){
                            readUserAndLogin(intent);
                        }
                        //readUserAndLogin(intent);
                    }else{
                        Toast.makeText(this, "Error en la respuesta del servidor...", Toast.LENGTH_SHORT).show();
                        readUserAndLogin(intent);
                    }
                });
            }else{
                btnlogin.setText("Usuario Desvinculado");
                Toast.makeText(this, "El usuario selecionado no esta vinculado, SELECIONE OTRO USUARIO", Toast.LENGTH_LONG).show();
            }
        }else{
            if(statusImei.getString("status", "not").equals("yes")){
                startActivity(intent);
                finish();
            }else{
                btnlogin.setText("Actualiza la App movil");
                Toast.makeText(this, "Es necesario conectarse a internet para actualizar esta version de la App", Toast.LENGTH_LONG).show();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
       // turnGPSOff();
    }

   /*
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
    */

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

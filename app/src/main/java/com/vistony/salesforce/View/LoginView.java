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
import android.location.Location;
import android.location.LocationManager;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
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

import com.google.firebase.analytics.FirebaseAnalytics;

public class LoginView extends AppCompatActivity {
    public OmegaCenterIconButton btnlogin;
    private Spinner spncompania, spnperfil, spnnombre;
    private static String Imei;
    private SesionEntity Sesion = new SesionEntity();
    private String perfil, Companiatext, vendedortext;
    private BluetoothAdapter bluetoothAdapter;
    private VideoView videoBG;
    private LoginRepository loginRepository;
    private SharedPreferences statusImei;
    private String version;
    private FirebaseAnalytics mFirebaseAnalytics;
    LocationManager locationManager;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private static final int REQUEST_PERMISSION_BLUETOOH = 255;
    ImageView imv_compania_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        statusImei = getSharedPreferences("imeiRegister", Context.MODE_PRIVATE);
        setLocale(statusImei.getString("language", BuildConfig.LANGUAGE_DEFAULT), statusImei.getString("country", BuildConfig.COUNTRY_DEFAULT));

        spncompania = findViewById(R.id.spncompania);
        spnperfil = findViewById(R.id.spnperfil);
        spnnombre = findViewById(R.id.spnnombre);
        btnlogin = findViewById(R.id.btnlogin);
        imv_compania_login = findViewById(R.id.imv_compania_login);

        //language
        //Locale.setDefault(Locale.FRENCH);
        if (BuildConfig.FLAVOR.equals("marruecos")) {
            Locale locale = new Locale("FR");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            this.getResources().updateConfiguration(config, null);
        }

        final TextView viewVersion = findViewById(R.id.txt_internet);
        SqliteController db = new SqliteController(this);
        String env = BuildConfig.BASE_ENVIRONMENT.equals("/api") ? "Producción" : "Test";
        version = Utilitario.getVersion(getApplication());

        viewVersion.setText(env + ": " + version + " db: " + db.getWritableDatabase().getVersion()+"_");

        // locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        loginRepository = new ViewModelProvider(this).get(LoginRepository.class);
        Imei = obtenerImei(statusImei, this, loginRepository);
        //Imei="355531114595247";
        if (BuildConfig.FLAVOR.equals("espania") || BuildConfig.FLAVOR.equals("marruecos")) {

        } else {
            loadConfigurationPrinter();
        }

        ObtenerVideo();
        //verifyPermission();
        verifyPermission2();
        /*if(BuildConfig.FLAVOR.equals("perurofalab"))
        {
            imv_compania_login.setImageResource(R.mipmap.rofalab304x90_2);
        }*/

        //set image company
        switch (BuildConfig.FLAVOR) {
            case "perurofalab":
                imv_compania_login.setImageResource(R.mipmap.logo_rofalab_788_229);
                break;
            default:
                imv_compania_login.setImageResource(R.mipmap.logo_factura);
                break;
        }
        //Toast.makeText(this,  BuildConfig.COUNTRY_DEFAULT, Toast.LENGTH_LONG).show();
        //Toast.makeText(this,  BuildConfig.LANGUAGE_DEFAULT, Toast.LENGTH_LONG).show();
        //Toast.makeText(this,  this.getResources().getString(R.string.sinInfo), Toast.LENGTH_LONG).show();
        //if(Imei==null||Imei.equals(""))
        //{

        //}else {
            loginRepository.getAndLoadUsers(Imei, this).observe(LoginView.this, data -> {
                if (data == null) {
                    Toast.makeText(this, this.getResources().getString(R.string.Error_and_network), Toast.LENGTH_LONG).show();
                } else if (data.isEmpty()) {
                    btnlogin.setEnabled(false);
                    btnlogin.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_border_button_onclick));
                    btnlogin.setText(this.getResources().getString(R.string.sinInfo));
                    btnlogin.setClickable(false);

                    Toast.makeText(this, this.getResources().getString(R.string.locale_information_none), Toast.LENGTH_LONG).show();
                } else {
                    ArrayAdapter<String> adapterProfile = new ArrayAdapter<String>(this, R.layout.layout_custom_spinner, data);
                    spnperfil.setAdapter(adapterProfile);
                    adapterProfile.notifyDataSetChanged();
                }
            });
        //}

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
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        perfil = parent.getItemAtPosition(position).toString();

                        ArrayList<String> companiaList = loginRepository.getCompanies(perfil);

                        ArrayAdapter<String> adapterCompania = new ArrayAdapter<String>(LoginView.this, R.layout.layout_custom_spinner, companiaList);
                        spncompania.setAdapter(adapterCompania);
                        adapterCompania.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        spncompania.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Companiatext = spncompania.getSelectedItem().toString();
                        ArrayList<String> usuarioList = loginRepository.getUsers(perfil, Companiatext);

                        ArrayAdapter<String> adapterUser = new ArrayAdapter<String>(LoginView.this, R.layout.layout_custom_spinner, usuarioList);
                        spnnombre.setAdapter(adapterUser);
                        adapterUser.notifyDataSetChanged();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        spnnombre.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        vendedortext = spnnombre.getSelectedItem().toString();

                        btnlogin.setEnabled(true);
                        btnlogin.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.custom_border_button_red));
                        btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
                        btnlogin.setClickable(true);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        getLocation();
        /*RoomDatabase roomDatabase=new RoomDatabase() {
            @NonNull
            @Override
            protected SupportSQLiteOpenHelper createOpenHelper(@NonNull DatabaseConfiguration databaseConfiguration) {
                return null;
            }

            @NonNull
            @Override
            protected InvalidationTracker createInvalidationTracker() {
                return null;
            }

            @Override
            public void clearAllTables() {

            }
        };
        RoomDatabase.Companion.buildDatabase(getBaseContext());*/
        //RoomDatabase.Companion.getInstance(this);
        //Room.databaseBuilder(this, TasksDatabase::class.java, "tasks-db").build();



    }


    private void getLocation() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // AlertNoGps();
            androidx.fragment.app.DialogFragment dialogFragment = new AlertGPSDialogController();
            dialogFragment.show(((FragmentActivity) this).getSupportFragmentManager(), "un dialogo");
        }


        // When you need the permission, e.g. onCreate, OnClick etc.
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("REOS", "MenuAccionView: No tiene ACCESS_FINE_LOCATION ");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            Log.e("REOS", "MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            // We have already permission to use the location
            try {
                gpsController = new GPSController(this);
                mLocation = gpsController.getLocation(mLocation);
                latitude = mLocation.getLatitude();
                longitude = mLocation.getLongitude();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
    private void getCrash() {
        Induvis.getCrashLytics();
    }

    @Override
    public void onResume() {
        super.onResume();
        ObtenerVideo();
    }

    private void loadConfigurationPrinter() {

        if (
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_PERMISSION_BLUETOOH);
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH}, REQUEST_PERMISSION_BLUETOOH);
            requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_PERMISSION_BLUETOOH);
            Log.e("REOS", "LoginView-loadConfigurationPrinter.SinPermisobluetooh");
            return;
        }else {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            bluetoothAdapter.enable();
            Log.e("REOS", "LoginView-loadConfigurationPrinter.conPermisobluetooh");
        }

        ConfiguracionSQLiteDao configuracionSQLiteDao5 = new ConfiguracionSQLiteDao(getBaseContext());
        configuracionSQLiteDao5.ActualizaVinculo("0");
    }

    private void ObtenerVideo() {
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.plane);
        videoBG = findViewById(R.id.videoView);
        videoBG.setVideoURI(uri);
        videoBG.requestFocus();
        videoBG.start();
        videoBG.setOnPreparedListener(mp -> mp.setLooping(true));
    }

    private void verifyPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permsRequestCode = 255;

            String[] perms = {
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN
            };

            int accessReadPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int accessWriteExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);
            int accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessReadExternalStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int accessBluetoothConnect = checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT);
            int accessBluetooth = checkSelfPermission(Manifest.permission.BLUETOOTH);
            int accessBluetoothScan = checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN);

            if (accessReadPhoneState == PackageManager.PERMISSION_GRANTED
                    && accessWriteExternalStorage == PackageManager.PERMISSION_GRANTED
                    && accessCamera == PackageManager.PERMISSION_GRANTED
                    && accessCoarseLocation == PackageManager.PERMISSION_GRANTED
                    && accessReadExternalStorage == PackageManager.PERMISSION_GRANTED
                    && accessBluetoothConnect == PackageManager.PERMISSION_GRANTED
                    && accessBluetooth == PackageManager.PERMISSION_GRANTED
                    && accessBluetoothScan == PackageManager.PERMISSION_GRANTED
            ) {
                //se realiza metodo si es necesario...
            } else {
                requestPermissions(perms, permsRequestCode);
            }
        }
    }

    private void verifyPermission2() {

        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int permsRequestCode = 255;

            String[] perms = {
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_SCAN
            };

            /*int accessReadPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int accessWriteExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);
            int accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            int accessReadExternalStorage = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int accessBluetoothConnect = checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT);
            int accessBluetooth = checkSelfPermission(Manifest.permission.BLUETOOTH);
            int accessBluetoothScan = checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN);*/

        if (
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            requestPermissions(perms, permsRequestCode);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 255) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    //verifyPermission();
                    break;
                }
            }
        }
    }

    public void btnLogin(View v) {
        btnlogin.setEnabled(false);
        btnlogin.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_border_button_onclick));
        btnlogin.setText(this.getResources().getString(R.string.Ingresando));

        btnlogin.setClickable(false);

        UsuarioSQLiteEntity userEntity = loginRepository.loginUser(Companiatext, vendedortext);

        if (userEntity != null) {
            Sesion.compania_id = userEntity.getCompania_id();
            Sesion.fuerzatrabajo_id = userEntity.getFuerzatrabajo_id();
            Sesion.nombrecompania = userEntity.getNombrecompania();
            Sesion.nombrefuerzadetrabajo = userEntity.getNombrefuerzatrabajo();
            Sesion.nombreusuario = userEntity.getNombreUsuario();
            Sesion.usuario_id = userEntity.getUsuario_id();
            Sesion.imei = userEntity.getImei();
            Sesion.recibo = userEntity.getRecibo();
            Sesion.almacen_id = userEntity.getAlmacen_id();
            Sesion.planta_id = userEntity.getPlanta();
            Sesion.perfil_id = userEntity.getPerfil();
            Sesion.cogsacct = userEntity.getCogsacct();
            Sesion.u_vist_ctaingdcto = userEntity.getU_vist_ctaingdcto();
            Sesion.documentsowner = userEntity.getDocumentsowner();
            Sesion.U_VIST_SUCUSU = userEntity.getU_VIST_SUCUSU();
            Sesion.CentroCosto = userEntity.getCentroCosto();
            Sesion.UnidadNegocio = userEntity.getUnidadNegocio();
            Sesion.LineaProduccion = userEntity.getLineaProduccion();
            Sesion.Impuesto_ID = userEntity.getImpuesto_ID();
            Sesion.Impuesto = userEntity.getImpuesto();
            Sesion.U_VIS_CashDscnt = userEntity.getU_VIS_CashDscnt();
            Sesion.FLAG_STOCK = userEntity.getFLAG_STOCK();
            Sesion.FLAG_BACKUP = userEntity.getFLAG_BACKUP();
            Sesion.rate = userEntity.getRate();
            Sesion.Print = userEntity.getPrint();
            Sesion.activecurrency = userEntity.getActivecurrency();
            Sesion.phone = userEntity.getPlanta();
            Sesion.maxDateDeposit = userEntity.getChkbloqueopago();
            if (userEntity.getMigratequotation() == null) {
                Sesion.migratequotation = "N";
            } else {
                Sesion.migratequotation = userEntity.getMigratequotation();
            }
            if (userEntity.getCensus() == null) {
                Sesion.census = "N";
            } else {
                Sesion.census = userEntity.getCensus();
            }
            Sesion.deliverydateauto= userEntity.getDeliverydateauto();
            Sesion.deliveryrefusedmoney = userEntity.getDeliveryrefusedmoney();
            Sesion.status = userEntity.getStatus();
            Sesion.sendvisits = userEntity.getSendvisits();
            Sesion.sendvalidations = userEntity.getSendvalidations();

            Log.e("REOS", "LoginView.Sesion.rate: " + Sesion.rate);
            Log.e("REOS", "LoginView.Sesion.U_VIS_CashDscnt: " + Sesion.U_VIS_CashDscnt);
            Log.e("REOS", "LoginView.Sesion.maxDateDeposit: " + Sesion.maxDateDeposit);
            String country = userEntity.getCountry();
            String language = userEntity.getLenguage();

            Log.e("REOS", "LoginView-SesionEntity.Print" + Sesion.Print);
            try {
                //Crashlitycs
                getCrash();
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }

            if (country == null && language == null) {
                Toast.makeText(this, this.getResources().getString(R.string.mse_system_no_identification_country), Toast.LENGTH_LONG).show();
                btnlogin.setEnabled(true);
                btnlogin.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_border_button_red));
                btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
                btnlogin.setClickable(true);
            } else {
                if(SesionEntity.status.equals("Y"))
                {
                    SharedPreferences.Editor editor = statusImei.edit();
                    editor.putString("country", userEntity.getCountry());
                    editor.putString("language", userEntity.getLenguage());
                    editor.apply();
                    verificationVersion();
                }
                else {
                    Toast.makeText(this, this.getResources().getString(R.string.mse_user_inactive), Toast.LENGTH_LONG).show();
                    btnlogin.setEnabled(false);
                    btnlogin.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_border_button_grey));
                    btnlogin.setText(getResources().getString(R.string.user_inactive));
                    btnlogin.setClickable(false);
                }
                /*SharedPreferences.Editor editor = statusImei.edit();
                editor.putString("country", userEntity.getCountry());
                editor.putString("language", userEntity.getLenguage());
                editor.apply();
                verificationVersion();*/
            }
        } else {
            Toast.makeText(this, this.getResources().getString(R.string.unauthorized_equipment), Toast.LENGTH_SHORT).show();
            btnlogin.setEnabled(true);

            btnlogin.setBackground(ContextCompat.getDrawable(this, R.drawable.custom_border_button_red));
            btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
            btnlogin.setClickable(true);
        }
    }

    public void setLocale(String lang, String country) {

        Locale myLocale = new Locale(lang, country);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();

        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

        //Location.getTime();
    }

    private void verificationVersion() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        Intent intent = new Intent(this, MenuView.class);

        if (networkInfo != null && networkInfo.isConnected()) {
            boolean userUnlinked = (SesionEntity.compania_id == null) ? true : false;
            //verificamos que el usuario elegido para la sesion tenga codigo de compania

            if (!userUnlinked) {
                new VersionViewModel().getVs(SesionEntity.imei, version, this).observe(this, data -> {
                    if (data != null) {
                        if (data.getClass().getName().equals("java.lang.String")) {
                            if (data.toString().length() > 6) {
                                Toast.makeText(this, data.toString(), Toast.LENGTH_LONG).show();
                                readUserAndLogin(intent);
                            } else {
                                btnlogin.setText(this.getResources().getString(R.string.validation_receips));
                                ObtenerPendientesEnvioWS();
                                new UpdateApp(btnlogin, data.toString(), this);
                            }
                        } else if (data.getClass().getName().equals("java.lang.Boolean")) {
                            readUserAndLogin(intent);
                        }
                        //readUserAndLogin(intent);
                    } else {
                        Toast.makeText(this, this.getResources().getString(R.string.mse_mistake_response_server), Toast.LENGTH_SHORT).show();
                        readUserAndLogin(intent);
                    }
                });
            } else {
                btnlogin.setText(this.getResources().getString(R.string.unlinked_user));
                Toast.makeText(this, this.getResources().getString(R.string.mse_unlinked_user), Toast.LENGTH_LONG).show();
            }
        } else {
            if (statusImei.getString("status", "not").equals("yes")) {
                startActivity(intent);
                finish();
            } else {
                btnlogin.setText(this.getResources().getString(R.string.update_mobile_app));
                Toast.makeText(this, this.getResources().getString(R.string.mse_necessary_connecting_internet), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void readUserAndLogin(Intent intent) {
        Map<String, String> vendedor = new HashMap<>();
        vendedor.put("Imei", SesionEntity.imei);
        vendedor.put("Compañia", SesionEntity.compania_id);
        vendedor.put("Fuerza de Venta", SesionEntity.fuerzatrabajo_id);

        User user = new User();
        user.setOthers(vendedor);
        Sentry.setUser(user);

        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (BuildConfig.FLAVOR.equals("espania") || BuildConfig.FLAVOR.equals("marruecos")) {

        } else {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Log.e("REOS", "LoginView-onBackPressed.sinPermisobluetooh");
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_CONNECT}, REQUEST_PERMISSION_BLUETOOH);
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH}, REQUEST_PERMISSION_BLUETOOH);
                    requestPermissions(new String[]{Manifest.permission.BLUETOOTH_SCAN}, REQUEST_PERMISSION_BLUETOOH);
                    return;
                }
                else    {
                    Log.e("REOS", "LoginView-onBackPressed.conPermisobluetooh");
                    if(bluetoothAdapter.isEnabled())
                    {
                        //bluetoothAdapter.disable();
                    }
                    else {
                        bluetoothAdapter.enable();
                    }
                }
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

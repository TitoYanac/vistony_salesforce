package com.vistony.salesforce.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
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
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Funcionalidades.FormulasController;
import com.vistony.salesforce.Controller.Funcionalidades.SQLiteController;
import com.vistony.salesforce.Controller.Funcionalidades.UpdateApp;
import com.vistony.salesforce.Dao.Retrofit.HistoricoDepositoUnidadWS;
import com.vistony.salesforce.Dao.Retrofit.LoginModalView;
import com.vistony.salesforce.Dao.Retrofit.VersionWS;
import com.vistony.salesforce.Dao.SQLIte.OrdenVentaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.CobranzaCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.ConfiguracionSQLiteDao;
import com.vistony.salesforce.Dao.SQLIte.UsuarioSQLiteDao;
import com.vistony.salesforce.Entity.SQLite.CobranzaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.LoginEntity;
import com.vistony.salesforce.Entity.SQLite.OrdenVentaCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.R;
//import org.apache.commons.net.io.ToNetASCIIInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.sentry.Sentry;
import io.sentry.protocol.User;
//import static android.Manifest.permission.READ_PHONE_STATE;


public class LoginView extends AppCompatActivity{
    public Button btnlogin;
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
    private UsuarioSQLiteDao usuarioSQLiteDao;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    private VideoView videoBG;
    private int temp = 0;
    private LocationManager locationManager;
    private AlertDialog alert = null;
    private String version = "";
    private LoginModalView loginModalView;
    static public SharedPreferences statusImei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale("en", "US");
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
        usuarioSQLiteDao = new UsuarioSQLiteDao(this);
        SesionEntity.loginSesion="0";
        SesionEntity.listaConsDeposito="0";
        ConfiguracionSQLiteDao configuracionSQLiteDao5=  new ConfiguracionSQLiteDao(getBaseContext());
        configuracionSQLiteDao5.ActualizaVinculo("0");
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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


        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),Settings.Secure.ANDROID_ID);
        Toast.makeText(getApplicationContext(), android_id, Toast.LENGTH_SHORT).show();

        loginModalView =  new ViewModelProvider(this).get(LoginModalView.class);

        obtenerImei();

        loginModalView.getAndLoadUsers(Imei,getApplicationContext()).observe(LoginView.this, data -> {
            if(data==null){
                Toast.makeText(context, "Ocurrio un error en la red", Toast.LENGTH_LONG).show();
            }else if(data.isEmpty()){
                btnlogin.setEnabled(false);
                btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_onclick));
                btnlogin.setText(getResources().getString(R.string.sinInfo));
                btnlogin.setClickable(false);

                Toast.makeText(context, "Sin información local", Toast.LENGTH_LONG).show();
            }else{
                ArrayAdapter<String> adapterProfile = new ArrayAdapter<String>(this,R.layout.layout_custom_spinner,data);
                spnperfil.setAdapter(adapterProfile);
                adapterProfile.notifyDataSetChanged();
            }
        });

        spnperfil.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                   //   TextView textView = (TextView)parent.getChildAt(position);
//                    textView.setTextColor(getResources().getColor(R.color.white));

                    perfil = parent.getItemAtPosition(position).toString();

                    ArrayList<String> companiaList = loginModalView.getCompanies(perfil);

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
                   /* TextView textView = (TextView)parent.getChildAt(0);
                    textView.setTextColor(getResources().getColor(R.color.white));*/
                    Companiatext = spncompania.getSelectedItem().toString();

                    /*if(Companiatext.equals("BLUKER SAC")){
                        imv_compania_login.setImageResource(R.mipmap.bluker);
                    }
                    if(Companiatext.equals("ROFALAB SAC")){
                        imv_compania_login.setImageResource(R.mipmap.rofalab);
                    }*/

                    ArrayList<String> usuarioList = loginModalView.getUsers(perfil,Companiatext);

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
                        //TextView textView = (TextView)parent.getChildAt(0);
                        //textView.setTextColor(getResources().getColor(R.color.white));
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

    public void obtenerImei() {
        Imei=statusImei.getString("imei", "");

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

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

                        //Se llama a la obtencion de usuarios y se queda a la escucha
                        loginModalView.getAndLoadUsers(Imei,getApplicationContext());

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

    private void AlertNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea activarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton("No", (dialog, id) -> dialog.cancel());
        alert = builder.create();
        alert.show();
    }


    private void verifyPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            int permsRequestCode = 100;

            /*
             <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
            <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
            <uses-permission android:name="android.permission.BLUETOOTH" />
            <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />

            <uses-permission android:name="android.permission.READ_PHONE_STATE" />
            <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
            <uses-permission android:name="android.permission.CAMERA" />
            <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
            <uses-permission android:name="android.permission.INTERNET" />
             <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />




            <uses-permission android:name="android.permission.WRITE_INTERNAL_STORAGE" />
            <uses-permission android:name="android.permission.READ_INTERNAL_STORAGE" />
            <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
            <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
            <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
            <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES" />
            * */

            String[] perms = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
               // Manifest.permission.WRITE_INTERNAL_STORAGE,

            };

            int accessReadPhoneState = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int accessWriteExternalStorage = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int accessCamera = checkSelfPermission(Manifest.permission.CAMERA);
            int accessCoarseLocation = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);

            if (accessReadPhoneState == PackageManager.PERMISSION_GRANTED && accessWriteExternalStorage == PackageManager.PERMISSION_GRANTED && accessCamera == PackageManager.PERMISSION_GRANTED &&  accessCoarseLocation == PackageManager.PERMISSION_GRANTED ) {
                //se realiza metodo si es necesario...
            } else {
                requestPermissions(perms, permsRequestCode);
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        ObtenerVideo();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != 0) {
            if (requestCode == 1) {
                if ((grantResults.length <= 0 || grantResults[0] != 0) && ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                    ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, 0);
                }
            }
        } else if ((grantResults.length <= 0 || grantResults[0] != 0) && ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_PHONE_STATE"}, 0);
        }
    }

    public void btnLogin(View v){
        btnlogin.setEnabled(false);
        btnlogin.setBackground(ContextCompat.getDrawable(this,R.drawable.custom_border_button_onclick));
        btnlogin.setText(getResources().getString(R.string.conectado));

        btnlogin.setClickable(false);

        ConsultarLogin consultar = new ConsultarLogin();
        consultar.execute();
    }

    private class ConsultarLogin extends AsyncTask<String, Void, Object> {

       /* protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(LoginView.this);
            pd = ProgressDialog.show(LoginView.this, "Por favor espere", "Consultando Acceso", true, false);
        }*/

        @Override
        protected String doInBackground(String... arg0) {

            String resultado="";

            return resultado;
        }
        protected void onPostExecute(Object result){
           // int Autorizacion=0;

            //if (Llogin.isEmpty()&&Llogin2.isEmpty()){

            Log.e("JEPICAME","SE EJECUTA AsyncTask");
                Integer statusQuery=usuarioSQLiteDao.ActualizaUsuario(Companiatext,vendedortext);

                listaUsuariosqliteEntity.clear();
                listaUsuariosqliteEntity=usuarioSQLiteDao.ObtenerUsuarioSesion();
                if(!listaUsuariosqliteEntity.isEmpty() && statusQuery==1){

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
                    //Autorizacion=1;

                    verificationVersion();
                }else{
                    Toast.makeText(context, "Equipo No Autorizado, Comunicarse a Helpdesk@vistony.com", Toast.LENGTH_SHORT).show();
                    btnlogin.setEnabled(true);

                    btnlogin.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.custom_border_button_red));
                    btnlogin.setText(getResources().getString(R.string.BotonPrincipalLogin));
                    btnlogin.setClickable(true);
                }
        /*    }else{

                String Conexion="";
                UsuarioSQLiteDao usuarioSQLiteDao = new UsuarioSQLiteDao(getApplicationContext());
//                    if(spnconexion.getSelectedItem().toString().equals("ONLINE"))
//                    {
                usuarioSQLiteDao.LimpiarTablaUsuario();
                for(int i=0;i<Llogin.size();i++)
                {
                    usuarioSQLiteDao.InsertaUsuario(
                            Llogin.get(i).getCompania_id(),
                            Llogin.get(i).getFuerzatrabajo_id(),
                            Llogin.get(i).getNombrecompania(),
                            Llogin.get(i).getNombrefuerzadetrabajo(),
                            Llogin.get(i).getNombreusuario(),
                            Llogin.get(i).getUsuario_id(),
                            Llogin.get(i).getRecibo(),
                            "0",
                            spnperfil.getSelectedItem().toString(),
                            Llogin.get(i).getBloqueopago().toString(),
                            Llogin.get(i).getListaprecio_id_1().toString(),
                            Llogin.get(i).getListaprecio_id_2().toString(),
                            Llogin.get(i).getAlmacen_id().toString(),
                            Llogin.get(i).getCogsacct(),
                            Llogin.get(i).getU_vist_ctaingdcto(),
                            Llogin.get(i).getDocumentsowner(),
                            Llogin.get(i).getU_VIST_SUCUSU(),
                            Llogin.get(i).getCentroCosto(),
                            Llogin.get(i).getUnidadNegocio(),
                            Llogin.get(i).getLineaProduccion(),
                            Llogin.get(i).getImpuesto_ID(),
                            Llogin.get(i).getImpuesto(),
                            Llogin.get(i).getTipoCambio(),
                            Llogin.get(i).getU_VIS_CashDscnt()
                    );
                }

                for (int i = 0; i < Llogin.size(); i++) {
                    String nombrevendedortext = "";
                    nombrevendedortext = String.valueOf(Llogin.get(i).getNombrefuerzadetrabajo());

                    if (nombrevendedortext.equals(vendedortext) && Llogin.get(i).getNombrecompania().equals(Companiatext)) {
                        Sesion.compania_id = Llogin.get(i).getCompania_id();
                        Sesion.fuerzatrabajo_id = Llogin.get(i).getFuerzatrabajo_id();
                        Sesion.nombrecompania = Llogin.get(i).getNombrecompania();
                        Sesion.nombrefuerzadetrabajo = Llogin.get(i).getNombrefuerzadetrabajo();
                        Sesion.nombreusuario = Llogin.get(i).getNombreusuario();
                        String nombreusuario = "";
                        nombreusuario = Llogin.get(i).getNombreusuario();
                        Sesion.usuario_id = Llogin.get(i).getUsuario_id();
                        Sesion.imei = Imei;
                        Sesion.recibo=Llogin.get(i).getRecibo();
                        Sesion.perfil_id=Llogin.get(i).getPerfil();
                        Sesion.almacen_id=Llogin.get(i).getAlmacen_id();
                        Sesion.planta_id=Llogin.get(i).getPlanta();
                        Sesion.perfil_id=Llogin.get(i).getPerfil();
                        Sesion.cogsacct=Llogin.get(i).getCogsacct();
                        Sesion.u_vist_ctaingdcto=Llogin.get(i).getU_vist_ctaingdcto();
                        Sesion.documentsowner=Llogin.get(i).getDocumentsowner();
                        Sesion.U_VIST_SUCUSU=Llogin.get(i).getU_VIST_SUCUSU();
                        Sesion.CentroCosto=Llogin.get(i).getCentroCosto();
                        Sesion.UnidadNegocio=Llogin.get(i).getUnidadNegocio();
                        Sesion.LineaProduccion=Llogin.get(i).getLineaProduccion();
                        Sesion.Impuesto_ID=Llogin.get(i).getImpuesto_ID();
                        Sesion.Impuesto=Llogin.get(i).getImpuesto();
                        Sesion.TipoCambio=Llogin.get(i).getTipoCambio();
                        Sesion.U_VIS_CashDscnt=Llogin.get(i).getU_VIS_CashDscnt();
                        Sesion.recibo=Llogin.get(i).getRecibo();


                    }
                }

                Log.e("jpcm","tamao login2 "+Llogin2.size());

                for (int j = 0; j < Llogin2.size(); j++) {

                    usuarioSQLiteDao.ActualizaRecibo(Llogin2.get(j).getRecibo());
                    ArrayList<UsuarioSQLiteEntity> listadoUsuarioSQLiteEntity = new ArrayList<>();
                    listadoUsuarioSQLiteEntity=usuarioSQLiteDao.ObtenerUsuario();
                    try {
                        Sesion.recibo = listadoUsuarioSQLiteEntity.get(j).getRecibo();
                    }catch(Exception e){
                        Toast.makeText(context, "VERIFICA TU PERFIL", Toast.LENGTH_LONG).show();
                        finish();
                        startActivity(getIntent());
                    }
                }

                Conexion="1";
                usuarioSQLiteDao.ActualizaUsuario(Sesion.compania_id,vendedortext,Conexion);
                Autorizacion=1;*/
//            }
////////////////////////////////////////////////////////////////////////////////////////////////////

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
                new VersionWS().getVs(SesionEntity.imei,version).observe(this, data -> {
                    if(data.getClass().getName().equals("java.lang.String")){
                        if(data.toString().length()>6){
                            Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_LONG).show();
                        }else{
                            btnlogin.setText("Validando Recibos...");
                            ObtenerPendientesEnvioWS();
                            new UpdateApp(btnlogin,data.toString(),getApplicationContext());
                        }
                    }else if(data.getClass().getName().equals("java.lang.Boolean")){
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
        ArrayList<OrdenVentaCabeceraSQLiteEntity> listaordenventacabecerasqliteentity=new ArrayList<>();
        OrdenVentaCabeceraSQLiteDao ordenVentaCabeceraSQLiteDao=new OrdenVentaCabeceraSQLiteDao(this);
        listaordenventacabecerasqliteentity=ordenVentaCabeceraSQLiteDao.ObtenerOrdenVentaCabeceraPendientesEnvioWS();
        if(!listaordenventacabecerasqliteentity.isEmpty())
        {
            resultadoPendientesEnvioWS="1";
            tipo="4";
        }

        compuesto=resultadoPendientesEnvioWS+"-"+tipo;

        return compuesto;

    }

}

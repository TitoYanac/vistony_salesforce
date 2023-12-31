package com.vistony.salesforce.View;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Dao.Retrofit.LeadClienteViewModel;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

public class LeadClientesView extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View v;
    private String mParam1;
    private String mParam2;
    private FloatingActionButton floatingButtonTakePhoto;
    public static ImageView imageViewPhoto;
    private LeadClienteViewModel leadClienteViewModel;
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private static LeadClientesView.OnFragmentInteractionListener mListener;
    private Bitmap imgBitmap;
    private byte[] byteArray;
    private MapView mapView;
    private OmegaCenterIconButton btnUpload;
    //private Double latitud = null, longitud = null;
    private Spinner spinner;
    private EditText editTextComentary, editTextCardCode, editTextCardName, editTextCardNameComercial,
            editTextPhone, editTextCellPhone, editTextContactPerson, editTextEmail, editTextWeb, editTextCoordenates
            ,editTextStreet,et_telfmovilcliente,et_telfhouseclient;
    private Button buttonSendGPS;
    static private String direccion = "Sin dirección", referencia = "Sin referencias",type;
    final String message = "Este campo no puede ir vacio";
    private boolean status = true;
    private GoogleMap myGoogleMap;
    private Dialog dialog;
    com.google.android.material.textfield.TextInputLayout ti_commercial_name,ti_card_name,
            ti_textphone,ti_TextContactPerson,ti_TextEmail,ti_TextWeb,ti_TextCardCode,ti_editTextComments,ti_textcellphone;
    static String cliente_id,nombrecliente,domebarque_id,zona_id,domebarque,correo,chkgeolocation,telefonofijo,telefonomovil,rucdni,addresscode,chk_ruta;
    static Object object;
    LinearLayout linearLayoutGps;
    ImageView imv_callmobilephone,imv_calltelfhouseclient;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    static String latitudeClient,longitudeClient;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    LocationManager locationManager;
    File file;
    public static String mCurrentPhotoPath="";
    File fileCliente;
    private static final int MAX_BITMAP_SIZE = 100 * 1024 * 1024; // 100 MB
    AppExecutors executor;
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String tag, Object dato);
    }

    public static Fragment newInstancia (Object param1,String id) {
        LeadClientesView fragment = new LeadClientesView();
        object=param1;
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) param1;
        latitudeClient=null;
        longitudeClient=null;

        for(int i=0;i<Lista.size();i++)
        {
            cliente_id=Lista.get(i).getCliente_id();
            nombrecliente=Lista.get(i).getNombrecliente();
            domebarque_id=Lista.get(i).getDomembarque_id();
            domebarque=Lista.get(i).getDireccion();
            zona_id=Lista.get(i).getZona_id();
            correo=Lista.get(i).getCorreo();
            chkgeolocation=Lista.get(i).getChkgeolocation();
            telefonofijo=Lista.get(i).getTelefonofijo();
            telefonomovil=Lista.get(i).getTelefonomovil();
            rucdni=Lista.get(i).getRucdni();
            addresscode=Lista.get(i).getAddresscode();
            chk_ruta=Lista.get(i).getChk_ruta();
            latitudeClient=Lista.get(i).getLatitud();
            longitudeClient=Lista.get(i).getLongitud();
            Log.e("REOS","LeadClientesView-newInstancia-cliente_id:"+Lista.get(i).getCliente_id());
            Log.e("REOS","LeadClientesView-newInstancia-nombrecliente:"+Lista.get(i).getNombrecliente());
            Log.e("REOS","LeadClientesView-newInstancia-domebarque_id:"+Lista.get(i).getDomembarque_id());
            Log.e("REOS","LeadClientesView-newInstancia-domebarque:"+Lista.get(i).getDireccion());
            Log.e("REOS","LeadClientesView-newInstancia-zona_id:"+Lista.get(i).getZona_id());
            Log.e("REOS","LeadClientesView-newInstancia-contado"+Lista.get(i).getContado());
            Log.e("REOS","LeadClientesView-newInstancia-correo"+Lista.get(i).getCorreo());
            Log.e("REOS","LeadClientesView-newInstancia-chkgeolocation"+Lista.get(i).getChkgeolocation());
            Log.e("REOS","LeadClientesView-newInstancia-telefonofijo"+Lista.get(i).getTelefonofijo());
            Log.e("REOS","LeadClientesView-newInstancia-telefonomovil"+Lista.get(i).getTelefonomovil());
            Log.e("REOS","LeadClientesView-newInstancia-latitud"+Lista.get(i).getLatitud());
            Log.e("REOS","LeadClientesView-newInstancia-longitud"+Lista.get(i).getLongitud());
            Log.e("REOS","LeadClientesView-newInstancia-getChk_ruta"+Lista.get(i).getChk_ruta());
        }
        Log.e("REOS","LeadClientesView-newInstancia-type "+type);
        type=id;

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        latitude=0;
        longitude=0;
        verifyPermission();
        executor=new AppExecutors();
        if(type!=null)
        {
            if (type.equals("leadUpdateClient")) {
                getActivity().setTitle("Consulta Cliente");
            }
        }
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        dialog = new Dialog(getActivity());
        getLocation();
        v = inflater.inflate(R.layout.fragment_lead_cliente_view, container, false);

        floatingButtonTakePhoto = v.findViewById(R.id.floatingButtonTakePhoto);
        imageViewPhoto = v.findViewById(R.id.imageViewPhoto);
        editTextCardCode = v.findViewById(R.id.editTextCardCode);
        editTextCardName = v.findViewById(R.id.editTextCardName);
        editTextCardNameComercial = v.findViewById(R.id.editTextCardNameComercial);
        editTextPhone = v.findViewById(R.id.editTextPhone);
        editTextCellPhone = v.findViewById(R.id.editTextCellPhone);
        editTextContactPerson = v.findViewById(R.id.editTextContactPerson);
        editTextEmail = v.findViewById(R.id.editTextEmail);
        editTextWeb = v.findViewById(R.id.editTextWeb);
        editTextCoordenates = v.findViewById(R.id.editTextCoordenates);
        editTextComentary = v.findViewById(R.id.editTextComent);
        spinner = v.findViewById(R.id.spinner);
        buttonSendGPS = v.findViewById(R.id.buttonSendGPS);
        btnUpload = v.findViewById(R.id.btnSubtmit);
        imageViewPhoto.setBackgroundResource(R.drawable.portail);
        leadClienteViewModel = new ViewModelProvider(this).get(LeadClienteViewModel.class);
        ti_commercial_name = v.findViewById(R.id.ti_commercial_name);
        ti_textphone = v.findViewById(R.id.ti_textphone);
        ti_TextContactPerson = v.findViewById(R.id.ti_TextContactPerson);
        ti_TextEmail = v.findViewById(R.id.ti_TextEmail);
        ti_TextWeb = v.findViewById(R.id.ti_TextWeb);
        ti_TextCardCode = v.findViewById(R.id.ti_TextCardCode);
        editTextStreet = v.findViewById(R.id.editTextStreet);
        linearLayoutGps = v.findViewById(R.id.linearLayoutGps);
        ti_editTextComments = v.findViewById(R.id.ti_editTextComments);
        et_telfmovilcliente=v.findViewById(R.id.et_telfmovilcliente);
        imv_callmobilephone=v.findViewById(R.id.imv_callmobilephone);
        imv_calltelfhouseclient=v.findViewById(R.id.imv_calltelfhouseclient);
        ti_textcellphone=v.findViewById(R.id.ti_textcellphone);

        et_telfhouseclient=v.findViewById(R.id.et_telfhouseclient);
        if(BuildConfig.FLAVOR.equals("peru") ||
           BuildConfig.FLAVOR.equals("espania")||
           BuildConfig.FLAVOR.equals("bolivia")||
           BuildConfig.FLAVOR.equals("ecuador")||
           BuildConfig.FLAVOR.equals("marruecos")||
           BuildConfig.FLAVOR.equals("chile")||
           BuildConfig.FLAVOR.equals("paraguay")
        )
        {
            getActivity().setTitle(getResources().getString(R.string.lead_cliente));
            ti_commercial_name.setVisibility(View.GONE);
            //ti_textphone.setVisibility(View.GONE);
            ti_TextContactPerson.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            ti_TextWeb.setVisibility(View.GONE);
            //ti_TextCardCode.setVisibility(View.GONE);
            editTextCardName.setEnabled (false);
            editTextCardCode.setEnabled(false);
            editTextStreet.setEnabled(false);
            editTextCellPhone.setEnabled(false);
            ti_textphone.setEnabled(false);
            editTextEmail.setEnabled(false);
            //linearLayoutGps.setVisibility(View.GONE);
            ti_editTextComments.setVisibility(View.GONE);
            //ti_TextEmail.setVisibility(View.GONE);
            ti_textphone.setVisibility(View.GONE);
            editTextCellPhone.setVisibility(View.GONE);
            editTextPhone.setVisibility(View.GONE);
            //floatingButtonTakePhoto.setVisibility(View.GONE);
            ti_textcellphone.setVisibility(View.GONE);
            if(type!=null)
            {
                if (type.equals("leadUpdateClient")) {
                    getActivity().setTitle("Consultar Cliente");
                } else if (type.equals("leadUpdateClientCensus")) {
                    btnUpload.setText("ACTUALIZAR");
                } else {
                    //getActivity().setTitle(getResources().getString(R.string.lead_cliente));
                }
            }
            if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER")||BuildConfig.FLAVOR.equals("espania"))
            {
                btnUpload.setText("ACEPTAR");
                getActivity().setTitle("Consultar Cliente");
                floatingButtonTakePhoto.setVisibility(View.GONE);
                linearLayoutGps.setVisibility(View.GONE);
            }


        }
        /*else if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("marruecos")||
                BuildConfig.FLAVOR.equals("chile")
        )
        {
            ti_commercial_name.setVisibility(View.GONE);
            //ti_textphone.setVisibility(View.GONE);
            ti_TextContactPerson.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            ti_TextWeb.setVisibility(View.GONE);
            //ti_TextCardCode.setVisibility(View.GONE);
            linearLayoutGps.setVisibility(View.GONE);
            ti_editTextComments.setVisibility(View.GONE);
            floatingButtonTakePhoto.setVisibility(View.GONE);
            editTextCardName.setEnabled (false);
            editTextCardCode.setEnabled(false);
            editTextStreet.setEnabled(false);
            editTextCellPhone.setEnabled(false);
            ti_textphone.setEnabled(false);
            editTextEmail.setEnabled(false);
            btnUpload.setText(getActivity().getResources().getString(R.string.accept).toUpperCase());
            ti_textcellphone.setVisibility(View.GONE);
        }*/
        editTextCardCode.setText(rucdni);
        editTextCardName.setText(nombrecliente);
        editTextStreet.setText(domebarque);
        editTextEmail.setText(correo);
        //editTextCellPhone.setText(telefonomovil);
        //editTextPhone.setText(telefonofijo);
        et_telfmovilcliente.setText(telefonomovil);
        et_telfhouseclient.setText(telefonofijo);


        imv_callmobilephone.setOnClickListener(e -> {
            String phone = et_telfmovilcliente.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", phone, null));
            startActivity(intent);
        });
        imv_calltelfhouseclient.setOnClickListener(e -> {
            String phone = et_telfhouseclient.getText().toString();
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", phone, null));
            startActivity(intent);
        });
        floatingButtonTakePhoto.setOnClickListener(data -> {

            try {
                //executor.diskIO().execute(() -> {
                    Log.e("REOS","statusDispatchRepository-->FotoLocal-->Inicia");
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Crea el File
                    File photoFile = null;
                        try {
                            photoFile = createImageFile(cliente_id+domebarque_id+"_"+getDate(),"C");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        if (photoFile != null) {
                        Uri photoURI=null;
                        Log.e("REOS","statusDispatchRepository-->FotoLocal-->photoFile != null");
                        switch (BuildConfig.FLAVOR)
                        {
                            case "peru":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                                break;
                            case "bolivia":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.bolivia" , photoFile);
                                break;
                            case "ecuador":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.ecuador" , photoFile);
                                break;
                            case "paraguay":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.paraguay" , photoFile);
                                break;
                            case "chile":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.chile" , photoFile);
                                break;
                            case "espania":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.espania" , photoFile);
                                break;
                            case "marruecos":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.marruecos" , photoFile);
                                break;
                            case "perurofalab":
                                photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.perurofalab" , photoFile);
                                break;
                        }

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        someActivityResultLauncher.launch(intent);
                    }
                    //someActivityResultLauncher.launch(intent);
               // });
            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getActivity(), "No se pudo grabar la Geolocalizacion error: "+e.toString(), Toast.LENGTH_SHORT).show();
            }
            Log.e("REOS","statusDispatchRepository-->FotoLocal-->Fin");

        });

        LeadClientesView.this.someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == RESULT_OK) {
                Bitmap bitmap2=null;
                try {
                        File file = new File(mCurrentPhotoPath);
                            try {
                                bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            ImageCameraController imageCameraController = new ImageCameraController();
                            try {
                                fileCliente=imageCameraController.SaveImageStatusDispatch (getContext(),bitmap2,cliente_id+domebarque_id+"_"+getDate(),"C");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                    //Bundle extras = result.getData().getExtras();
                    //Bitmap imageBitmap = (Bitmap) extras.get("data");
                    //ImageView imageView = findViewById(R.id.imageviewTest);
                    //imageView.setImageBitmap(imageBitmap);
                    //imgBitmap=bitmap2;
                   // imgBitmap=imageBitmap;
                        /*int bitmapSize = imgBitmap.getByteCount();
                        if (bitmapSize > MAX_BITMAP_SIZE) {
                            throw new RuntimeException(
                                    "Canvas: trying to draw too large(" + bitmapSize + "bytes) bitmap.");
                        }else {
                            //imageViewPhoto.setImageBitmap(bitmap2);
                            Convert.resizeImage(imageViewPhoto,bitmap2,getActivity());
                        }*/
                    imageViewPhoto.setImageBitmap(bitmap2);
                    bitmap2=null;
                    //imageViewPhoto.setImageBitmap(imgBitmap);
                    executor.diskIO().execute(() -> {
                        boolean isDeleted = deleteImageFile(mCurrentPhotoPath);
                        if (isDeleted) {
                            System.out.println("La foto fue eliminada exitosamente");
                        } else {
                            System.out.println("No se pudo eliminar la foto");
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });

        btnUpload.setOnClickListener(e -> {
            /*String Fragment="RutaVendedorView";
            String accion="inicioRutaVendedorViewLead";
            String compuesto=Fragment+"-"+accion;
            if(mListener!=null) {
                mListener.onFragmentInteraction(compuesto, object
                );
            }*/
            /*if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("bolivia"))
            {*/
                if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
                {
                    String Fragment="RutaVendedorView";
                    String accion="inicioRutaVendedorViewLead";
                    String compuesto=Fragment+"-"+accion;
                    if(mListener!=null) {
                        mListener.onFragmentInteraction(compuesto, object
                        );
                    }
                }else {
                    sendLeadApi();
                }

            //}
            /*else if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("espania")
                    ||BuildConfig.FLAVOR.equals("marruecos")||BuildConfig.FLAVOR.equals("chile"))
            {
                String Fragment="RutaVendedorView";
                String accion="inicioRutaVendedorViewLead";
                String compuesto=Fragment+"-"+accion;
                if(mListener!=null) {
                    mListener.onFragmentInteraction(compuesto, object
                    );
                }
            }*/
        });
        try {
        buttonSendGPS.setOnClickListener(e -> {
            /*if(latitude!=0&&longitude!=0) {
                editTextCoordenates.setText(latitude + "," + longitude);
            }
            else {
                    Toast.makeText(getContext(), "Un Momento Por favor Calculando Coordenadas... Intente Guardar Nuevamente!!!", Toast.LENGTH_SHORT).show();
                    getLocation();
                }*/
           displayDialogMap();
            MapsInitializer.initialize(getActivity());
            mapView.onCreate(dialog.onSaveInstanceState());
            mapView.onResume();

            /*dialog = new Dialog(getActivity());
            mapView.onCreate(dialog.onSaveInstanceState());

            if(mapView!=null){
                MapsInitializer.initialize(getActivity());

                mapView.onResume();
                displayDialogMap();
            }*/


        });
        }catch (Exception e){
            Toast.makeText(getContext(), "No se puedo mostrar el Mapa - error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        try {
            if(type!=null)
            {
                if (type.equals("leadUpdateClient")) {
                    getActivity().setTitle("Consultar Cliente");

                } else if (type.equals("leadUpdateClientCensus")) {
                    if(BuildConfig.FLAVOR.equals("espania")||BuildConfig.FLAVOR.equals("marruecos")||BuildConfig.FLAVOR.equals("chile"))
                    {
                        getActivity().setTitle(getActivity().getResources().getString(R.string.data_client));
                    }else {
                        getActivity().setTitle("Geolocalización Cliente");
                    }

                } else {
                    getActivity().setTitle(getResources().getString(R.string.lead_cliente));
                }
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "No se puedo mostrar el titulo - error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
        return v;
    }


    private void sendLeadApi() {
        try {
            AppExecutors executor=new AppExecutors();
            UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
            UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
            ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
        Integer acount = 0;

        if (editTextCardName.getText().length() == 0) {
            editTextCardName.setError(message);
            acount = acount + 1;
        /*} else if (editTextCardCode.getText().length() == 0) {
            editTextCardCode.setError(message);
            acount = acount + 1;
        } else if (editTextCardNameComercial.getText().length() == 0) {
            editTextCardNameComercial.setError(message);
            acount = acount + 1;*/
        } else if (editTextCellPhone.getText().length() == 0) {
            editTextCellPhone.setError(message);
            acount = acount + 1;
        /*} else if (editTextContactPerson.getText().length() == 0) {
            editTextContactPerson.setError(message);
            acount = acount + 1;*/
        } else if (editTextEmail.getText().length() == 0) {
            editTextEmail.setError(message);
            acount = acount + 1;
        }else if (editTextCoordenates.getText().length() == 0) {
            editTextCoordenates.setError(message);
            acount = acount + 1;
        }else if(fileCliente!=null)
        {
            acount = acount + 1;
        }


        String encoded = null;

       /* if (imgBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
            byteArray = stream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }
        else {
            acount = acount + 1;


        }*/



        //if(fileCliente!=null&&latitud!=null&&longitud!=null)
            if(fileCliente!=null&&latitude!=0&&longitude!=0)
        {
            Log.e("REOS","LeadClientesView.displayDialogMap.SendLeadApiif-encoded!=null&&latitud!=null&&longitud!=null");
            if (acount> 0) {
                Log.e("REOS","LeadClientesView.displayDialogMap.SendLeadApi-ifacount == 0");
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
                String formattedDate = df.format(c.getTime());

                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("DocumentsOwner", SesionEntity.usuario_id);
                parametros.put("SalesPersonCode", SesionEntity.fuerzatrabajo_id);
                parametros.put("Comentary", editTextComentary.getText().toString());
                parametros.put("CardName", editTextCardName.getText().toString());
                parametros.put("TaxNumber", editTextCardCode.getText().toString());
                parametros.put("TradeName", editTextCardNameComercial.getText().toString());
                parametros.put("NumberPhono", editTextPhone.getText().toString());
                parametros.put("NumberCellPhone", editTextCellPhone.getText().toString());
                parametros.put("ContactPerson", editTextContactPerson.getText().toString());
                parametros.put("Email", editTextEmail.getText().toString());
                parametros.put("Web", editTextWeb.getText().toString());
                parametros.put("Address", direccion);
                parametros.put("Reference", referencia);
                parametros.put("Category", spinner.getSelectedItem().toString());
                parametros.put("Latitude", "" + latitude);
                parametros.put("Longitude", "" + longitude);
                parametros.put("DateTime", "" + formattedDate);
                //parametros.put("photo", encoded);
                parametros.put("photo", fileCliente.toString());
                //if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("bolivia"))
                //{
                    if(SesionEntity.perfil_id.equals("Chofer")||SesionEntity.perfil_id.equals("CHOFER"))
                    {

                    }else {
                        type="leadUpdateClientCensus";
                    }

                //}
                parametros.put("type", type);
                parametros.put("CardCode", cliente_id);
                parametros.put("domembarque_id", domebarque_id);
                parametros.put("addresscode", addresscode);


                DireccionSQLite direccionSQLite= new DireccionSQLite(getContext());
                RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
                rutaVendedorSQLiteDao.UpdateChkGeolocationRouteSales(
                        cliente_id,
                        domebarque_id,
                        ObjUsuario.compania_id,
                        formattedDate,
                        String.valueOf(latitude),
                        String.valueOf(longitude)
                );
                direccionSQLite.updateCoordenatesAddress(cliente_id,
                        domebarque_id,String.valueOf(latitude),
                        String.valueOf(longitude));

                FormulasController formulasController=new FormulasController(getContext());

                VisitaSQLiteEntity visitaNativa=new VisitaSQLiteEntity();
                visitaNativa.setCardCode(cliente_id);
                visitaNativa.setAddress(domebarque_id);
                visitaNativa.setTerritory(zona_id);
                visitaNativa.setType("14");
                visitaNativa.setObservation("Geolocalizacion de Cliente Actualizada...");
                visitaNativa.setLatitude(""+latitude);
                visitaNativa.setLongitude(""+longitude);
                visitaNativa.setStatusRoute(chk_ruta);
                visitaNativa.setMobileID("0");
                visitaNativa.setAmount("0");
                formulasController.RegistraVisita(visitaNativa,getActivity(),"0");
                Toast.makeText(getContext(), "Visita registrada...", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
                leadClienteViewModel.sendLead(parametros, getContext()).observe(this.getActivity(), data -> {
                    if (data.equals("init")) {
                        btnUpload.setEnabled(false);
                        btnUpload.setClickable(false);
                        btnUpload.setText("Enviado Lead...");
                        btnUpload.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_border_button_onclick));
                        String Fragment = "RutaVendedorView";
                        String accion = "inicioRutaVendedorViewLead";
                        String compuesto = Fragment + "-" + accion;
                        if (mListener != null) {
                            mListener.onFragmentInteraction(compuesto, object
                            );
                        }
                        SesionEntity.updateclient = "N";
                        Toast.makeText(getActivity(), "Gelocalizacion Enviada Correctamente...", Toast.LENGTH_SHORT).show();

                    } else if (data.equals("successful")) {
                        btnUpload.setEnabled(true);
                        btnUpload.setClickable(true);
                        btnUpload.setText("Enviar Lead");
                        btnUpload.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_border_button_red));
                        Toast.makeText(getActivity(), "Lead enviado", Toast.LENGTH_SHORT).show();

                        clearEditText();

                    } else {
                        btnUpload.setEnabled(true);
                        btnUpload.setClickable(true);
                        btnUpload.setText("Enviar Lead");
                        btnUpload.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_border_button_red));
                        Toast.makeText(getActivity(), "Ocurrio un error al enviar el lead...", Toast.LENGTH_SHORT).show();
                    }

                });

            }

            /*leadClienteViewModel.sendLeadNotSend(getContext()).observe(this.getActivity(), data -> {
                Log.e("JEPICAMEE", "=>" + data);
            });*/

            leadClienteViewModel.sendGeolocationClient(getContext(),ObjUsuario.Imei,executor.diskIO()).observe(getActivity(), data -> {
                Log.e("Jepicame", "=>" + data);
            });
        }
        else {
            if(encoded==null){
                Toast.makeText(getActivity(), "Se tiene que Tomar la Foto para actualizar los datos del Cliente...", Toast.LENGTH_SHORT).show();
            }else if(latitude!=0&&longitude!=0)
            {
                Toast.makeText(getActivity(), "Se tiene que Actualizar la Geolocalizacion del Cliente para actualizar...", Toast.LENGTH_SHORT).show();
            }
        }
        } catch (Exception e){

        e.printStackTrace();

        Toast.makeText(getActivity(), "No se pudo grabar la Geolocalizacion error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }


}

    private void clearEditText() {
        editTextCardCode.setText("");
        editTextCardName.setText("");
        editTextCardNameComercial.setText("");
        editTextPhone.setText("");
        editTextCellPhone.setText("");
        editTextContactPerson.setText("");
        editTextEmail.setText("");
        editTextWeb.setText("");
        editTextCoordenates.setText("");
        editTextComentary.setText("");
    }

    private void displayDialogMap() {
        try {



            dialog.setContentView(R.layout.layout_mapa_dialog);

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final TextView editTextAddress = dialog.findViewById(R.id.editTextAddressDialog);
            final TextView editTextAddressReference = dialog.findViewById(R.id.editTextAddressReferenceDialog);

            //editTextAddress.setText((direccion.equals("Sin dirección") ? "" : direccion));
            //editTextAddressReference.setText((referencia.equals("Sin referencias") ? "" : referencia));
            editTextAddress.setText(nombrecliente);
            editTextAddressReference.setText(domebarque);

            ImageView image = dialog.findViewById(R.id.image);
            image.setImageResource(R.mipmap.logo_circulo);
            image.setBackground(new ColorDrawable(Color.TRANSPARENT));

            final Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            final Button dialogButtonExit = dialog.findViewById(R.id.dialogButtonCancel);
            mapView = dialog.findViewById(R.id.mapView);
            //getLocation();
            dialogButton.setText("Add");
            dialogButtonExit.setText("Cancel");

            ////////////////////////////////////////////////////////////////////////////////////////////
            if (mapView != null) {

                LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

                if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                {

                    mapView.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(final GoogleMap googleMap) {

                            myGoogleMap = googleMap;

                            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                    ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                                myGoogleMap.setMyLocationEnabled(true);
                                myGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
                                //latitud = location.getLatitude();
                                //longitud = location.getLongitude();
                                //latitud = latitude;
                                //longitud = longitude;
                                if(latitudeClient!=null)
                                {
                                    latitude=Float.parseFloat(latitudeClient);
                                    longitude=Float.parseFloat(longitudeClient);
                                }
                                Log.e("REOS", "LeadClientesView.displayDialogMap.latitudeClient:" + latitudeClient);
                                Log.e("REOS", "LeadClientesView.displayDialogMap.longitudeClient:" + longitudeClient);
                                Log.e("REOS", "LeadClientesView.displayDialogMap.latitud:" + latitude);
                                Log.e("REOS", "LeadClientesView.displayDialogMap.longitud:" + longitude);
                                LatLng latLng = new LatLng(latitude, longitude);
                                myGoogleMap.clear();
                                myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Potential client").draggable(true));


                                if (status) {
                                    myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                                    status = false;
                                } else {
                                    myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    //myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                }

                                dialog.show();

                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), "R.string.error_permission_map", Toast.LENGTH_LONG).show();
                                verifyPermission();
                                //getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 255);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "R.string.error_permission_map", Toast.LENGTH_LONG).show();
                    verifyPermission();
                    //getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 255);
                }

            }
            ////////////////////////////////////////////////////////////////////////////////////////////

            dialogButtonExit.setOnClickListener(v -> {
                // mapView=null;
                dialog.dismiss();
            });

            dialogButton.setOnClickListener(v -> {
                getLocation();
                editTextCoordenates.setText(latitude + "," + longitude);
                direccion = editTextAddress.getText().toString();
                referencia = editTextAddressReference.getText().toString();
                dialog.dismiss();

            });
            /*MapsInitializer.initialize(getActivity());
            mapView.onCreate(dialog.onSaveInstanceState());
            mapView.onResume();*/
        }catch (Exception e)
        {
            Toast.makeText(getContext(), "No se pudo abrir el Mapa error: "+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //ListenerBackPress.setCurrentFragment("Webview");
        //ListenerBackPress.setTemporaIdentityFragment("onAttach");
        //mapView.onStart();
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //
        // mapView.onResume();
        //ListenerBackPress.setTemporaIdentityFragment("onDetach");
        mListener = null;
    }

    private void getLocation()
    {
        locationManager = (LocationManager) getActivity(). getSystemService(LOCATION_SERVICE);
        //****Mejora****
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            // AlertNoGps();
            androidx.fragment.app.DialogFragment dialogFragment = new AlertGPSDialogController();
            dialogFragment.show(((FragmentActivity) getContext()). getSupportFragmentManager (),"un dialogo");
        }
        // When you need the permission, e.g. onCreate, OnClick etc.
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("REOS","MenuAccionView: No tiene ACCESS_FINE_LOCATION ");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            Log.e("REOS","MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            // We have already permission to use the location
            try {
                gpsController =  new GPSController(getContext());
                mLocation = gpsController.getLocation(mLocation);
                latitude = mLocation.getLatitude();
                longitude= mLocation.getLongitude();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        }
    }

    private String getDate() throws IOException {

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        return date;
    }

    private File createImageFile(String entrega_id,String type) throws IOException {
        String imageFileName = entrega_id+"_"+type;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        mCurrentPhotoPath=image.getAbsolutePath();
        return image;
    }


    private void verifyPermission() {

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
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        if (
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                        ||ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            requestPermissions(perms, permsRequestCode);
        } else {

        }
    }

    private boolean deleteImageFile(String filePath) {
        File file = new File(filePath);
        return file.delete();
    }
}


package com.vistony.salesforce.View;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.Dao.Retrofit.LeadClienteViewModel;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

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
    private Double latitud = null, longitud = null;
    private Spinner spinner;
    private EditText editTextComentary, editTextCardCode, editTextCardName, editTextCardNameComercial,
            editTextPhone, editTextCellPhone, editTextContactPerson, editTextEmail, editTextWeb, editTextCoordenates;
    private Button buttonSendGPS;
    private String direccion = "Sin dirección", referencia = "Sin referencias";
    final String message = "Este campo no puede ir vacio";
    private GoogleMap googleMap = null;
    private Dialog dialog;
    private boolean status=true;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String tag, Object dato);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle(getResources().getString(R.string.lead_cliente));
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        v = inflater.inflate(R.layout.fragment_agregar_cliente_view, container, false);

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
        dialog = new Dialog(getActivity());

        floatingButtonTakePhoto.setOnClickListener(data -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                someActivityResultLauncher.launch(intent);
            }
        });

        LeadClientesView.this.someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                imgBitmap = (Bitmap) extras.get("data");
                imageViewPhoto.setImageBitmap(imgBitmap);
            }
        });

        btnUpload.setOnClickListener(e -> {
            sendLeadApi();
        });

        buttonSendGPS.setOnClickListener(e -> {


            displayDialog();

            MapsInitializer.initialize(getActivity());

            mapView.onCreate(dialog.onSaveInstanceState());
            mapView.onResume();

        });
        return v;
    }

    private void sendLeadApi() {
        Integer acount = 0;

        if (editTextCardName.getText().length() == 0) {
            editTextCardName.setError(message);
            acount = acount + 1;
        } else if (editTextCardCode.getText().length() == 0) {
            editTextCardCode.setError(message);
            acount = acount + 1;
        } else if (editTextCardNameComercial.getText().length() == 0) {
            editTextCardNameComercial.setError(message);
            acount = acount + 1;
        } else if (editTextCellPhone.getText().length() == 0) {
            editTextCellPhone.setError(message);
            acount = acount + 1;
        } else if (editTextContactPerson.getText().length() == 0) {
            editTextContactPerson.setError(message);
            acount = acount + 1;
        }


        String encoded = null;

        if (imgBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imgBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
            byteArray = stream.toByteArray();
            encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        if (acount == 0) {

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
            parametros.put("Latitude", ""+latitud);
            parametros.put("Longitude", ""+longitud);
            parametros.put("DateTime", ""+formattedDate);
            parametros.put("photo", encoded);

            leadClienteViewModel.sendLead(parametros).observe(this.getActivity(), data -> {
                if (data.equals("init")) {
                    btnUpload.setEnabled(false);
                    btnUpload.setClickable(false);
                    btnUpload.setText("Enviado Lead...");
                    btnUpload.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_border_button_onclick));
                } else if (data.equals("successful")) {
                    btnUpload.setEnabled(true);
                    btnUpload.setClickable(true);
                    btnUpload.setText("Enviar Lead");
                    btnUpload.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_border_button_red));
                    Toast.makeText(getActivity(), "Lead enviado...", Toast.LENGTH_SHORT).show();
                } else {
                    btnUpload.setEnabled(true);
                    btnUpload.setClickable(true);
                    btnUpload.setText("Enviar Lead");
                    btnUpload.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.custom_border_button_red));
                    Toast.makeText(getActivity(), "Ocurrio un error al enviar el lead...", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void displayDialog() {
        dialog.setContentView(R.layout.layout_mapa_dialog);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        final EditText editTextAddress = dialog.findViewById(R.id.editTextAddressDialog);
        final EditText editTextAddressReference = dialog.findViewById(R.id.editTextAddressReferenceDialog);

        ImageView image = dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);

        final Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        final Button dialogButtonExit = dialog.findViewById(R.id.dialogButtonCancel);
        mapView = dialog.findViewById(R.id.mapView);

        ////////////////////////////////////////////////////////////////////////////////////////////

        if (mapView != null) {

            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    ////////////////////////////////////////////////////////////////////////////////////
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }

                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);

                    googleMap.setOnMyLocationChangeListener(location -> {
                        if(location!=null && googleMap!=null){
                            latitud=location.getLatitude();
                            longitud=location.getLongitude();

                            LatLng latLng=new LatLng(latitud,longitud);
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Potential client").draggable(true));

                            editTextCoordenates.setText(latitud + "," + longitud);

                            if(status){
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                status=false;
                            }else{
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                    });


                    googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker location) {
                            // TODO Auto-generated method stub

                            latitud = location.getPosition().latitude;
                            longitud = location.getPosition().longitude;
                            editTextCoordenates.setText(latitud + "," + longitud);

                            Log.d("System out", "onMarkerDragStart..." + location.getPosition().latitude + "..." + location.getPosition().longitude);
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        public void onMarkerDragEnd(Marker location) {
                            Log.d("System out", "onMarkerDragEnd..." + location.getPosition().latitude + "..." + location.getPosition().longitude);

                            latitud = location.getPosition().latitude;
                            longitud = location.getPosition().longitude;
                            editTextCoordenates.setText(latitud + "," + longitud);

                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(location.getPosition()));
                        }

                        @Override
                        public void onMarkerDrag(Marker arg0) {
                            // TODO Auto-generated method stub
                            Log.i("System out", "onMarkerDrag...");
                        }
                    });


                }
                ////////////////////////////////////////////////////////////////////////////////////
            });


        }
        ////////////////////////////////////////////////////////////////////////////////////////////
        dialogButton.setText("Add");
        dialogButtonExit.setText("Cancel");

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackground(new ColorDrawable(Color.TRANSPARENT));

        dialog.show();

        dialogButtonExit.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialogButton.setOnClickListener(v -> {

            direccion=editTextAddress.getText().toString();
            referencia=editTextAddressReference.getText().toString();

            if(direccion.length()>0){
                if(referencia.length()>0){
                    dialog.dismiss();
                }else{
                    editTextAddressReference.setError(message);
                }
            }else{
                editTextAddress.setError(message);
            }
        });
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

}


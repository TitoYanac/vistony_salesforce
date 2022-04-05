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
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.vistony.salesforce.Dao.Retrofit.LeadClienteViewModel;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

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
    private boolean status = true;
    private GoogleMap myGoogleMap;
    private Dialog dialog;

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

        dialog = new Dialog(getActivity());

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
            displayDialogMap();

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
        } /*else if (editTextCardCode.getText().length() == 0) {
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
        }*/ else if (editTextCoordenates.getText().length() == 0) {
            editTextCoordenates.setError(message);
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
            parametros.put("Latitude", "" + latitud);
            parametros.put("Longitude", "" + longitud);
            parametros.put("DateTime", "" + formattedDate);
            parametros.put("photo", encoded);

            leadClienteViewModel.sendLead(parametros, getContext()).observe(this.getActivity(), data -> {
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

        leadClienteViewModel.sendLeadNotSend(getContext()).observe(this.getActivity(), data -> {
            Log.e("JEPICAMEE", "=>" + data);
        });
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
        dialog.setContentView(R.layout.layout_mapa_dialog);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText editTextAddress = dialog.findViewById(R.id.editTextAddressDialog);
        final EditText editTextAddressReference = dialog.findViewById(R.id.editTextAddressReferenceDialog);

        editTextAddress.setText((direccion.equals("Sin dirección") ? "" : direccion));
        editTextAddressReference.setText((referencia.equals("Sin referencias") ? "" : referencia));

        ImageView image = dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);
        image.setBackground(new ColorDrawable(Color.TRANSPARENT));

        final Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        final Button dialogButtonExit = dialog.findViewById(R.id.dialogButtonCancel);
        mapView = dialog.findViewById(R.id.mapView);

        dialogButton.setText("Add");
        dialogButtonExit.setText("Cancel");

        ////////////////////////////////////////////////////////////////////////////////////////////
        if (mapView != null) {

            LocationManager locationManager = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mapView.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {

                        myGoogleMap=googleMap;

                        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                                ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                            myGoogleMap.setMyLocationEnabled(true);
                            myGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);

                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
                                    new LocationListener() {
                                        @Override
                                        public void onLocationChanged(Location location) {
                                            Toast.makeText(getActivity(), "onLocationChanged", Toast.LENGTH_SHORT).show();

                                            latitud = location.getLatitude();
                                            longitud = location.getLongitude();
                                            Log.e("REOS","LeadClientesView.displayDialogMap.latitud:"+latitud);
                                            Log.e("REOS","LeadClientesView.displayDialogMap.longitud:"+longitud);
                                            LatLng latLng = new LatLng(latitud, longitud);
                                            myGoogleMap.clear();
                                            myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Potential client").draggable(true));

                                            editTextCoordenates.setText(latitud + "," + longitud);

                                            if (status) {
                                                myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                                status = false;
                                            } else {
                                                myGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                            }

                                        }

                                        @Override
                                        public void onStatusChanged(String provider, int status, Bundle extras) {
                                            Toast.makeText(getActivity(), "onStatusChanged", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onProviderEnabled(String provider) {
                                            Toast.makeText(getActivity(), "onProviderEnabled", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onProviderDisabled(String provider) {
                                            Toast.makeText(getActivity(), "onProviderDisabled", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                            );



                            dialog.show();

                        }else{
                            Toast.makeText( getActivity().getApplicationContext(), "R.string.error_permission_map", Toast.LENGTH_LONG).show();
                            getActivity().requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION },100);
                        }

                        ////////////////////////////////////////////////////////////////////////////////////


                /*
                if(googleMap!=null) {

                    googleMap.setOnMyLocationChangeListener(location -> {
                        if (location != null) {

                            latitud = location.getLatitude();
                            longitud = location.getLongitude();

                            LatLng latLng = new LatLng(latitud, longitud);
                            googleMap.clear();
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Potential client").draggable(true));

                            editTextCoordenates.setText(latitud + "," + longitud);

                            if (status) {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
                                status = false;
                            } else {
                                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            }
                        }
                    });

                    googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                        @Override
                        public void onMarkerDragStart(Marker location) {
                            // TODO Auto-generated method stub
                        }

                        @Override
                        public void onMarkerDragEnd(Marker location) {
                            latitud = location.getPosition().latitude;
                            longitud = location.getPosition().longitude;
                            editTextCoordenates.setText(latitud + "," + longitud);

                            googleMap.animateCamera(CameraUpdateFactory.newLatLng(location.getPosition()));
                        }

                        @Override
                        public void onMarkerDrag(Marker arg0) {
                            // TODO Auto-generated method stub
                        }
                    });

                    googleMap.setMyLocationEnabled(true);
                    googleMap.getUiSettings().setZoomControlsEnabled(true);
                }
                */
                    }
                });


            } else {
                Toast.makeText( getActivity(), "R.string.error_permission_map", Toast.LENGTH_LONG).show();
                getActivity().requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION },100);
            }

        }
        ////////////////////////////////////////////////////////////////////////////////////////////

        dialogButtonExit.setOnClickListener(v -> {
           // mapView=null;
            dialog.dismiss();
        });

        dialogButton.setOnClickListener(v -> {

            direccion=editTextAddress.getText().toString();
            referencia=editTextAddressReference.getText().toString();

//            mapView=null;
            dialog.dismiss();

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


package com.vistony.salesforce.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Adapters.ListHistoricSalesOrderTraceabilityInvoiceAdapter;
import com.vistony.salesforce.Controller.Adapters.ListVisitUnClosedAdapter;
import com.vistony.salesforce.Controller.Adapters.ListWareHousesAdapter;
import com.vistony.salesforce.Controller.Adapters.ListaPendingCollectionAdapter;
import com.vistony.salesforce.Controller.Adapters.StatusDispatchDialog;
import com.vistony.salesforce.Controller.Adapters.VisitaDialogController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.Adapters.ListWareHouseDao;
import com.vistony.salesforce.Dao.Adapters.ListaPendingCollectionDao;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Dao.Retrofit.PriceListRepository;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.LeadSQLite;
import com.vistony.salesforce.Dao.SQLite.ReasonDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.TypeDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitSectionSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitSectionEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class MenuAccionView extends Fragment {

    private static final String ARG_PARAM = "ListaClienteCabeceraEntity";

    private String mParam1;
    private String mParam2;
    int validar=0;
    private static String TAG_1 = "text";
    View v;
    private CardView cv_pedido,cv_cobranza,cv_visita,cv_lead,cv_visit_section,cv_dispatch,cv_canvas;
    public static ArrayList<ListaClienteCabeceraEntity> Listado;

    static OnFragmentInteractionListener mListener;
    public static Object objetoMenuAccionView=new Object();

    private GPSController gpsController;
    private Location mLocation;
    static double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    LocationManager locationManager;
    static String CardCode,CardName,Address,DomEmbarque_ID,Contado,Control_id,Item_id,AddressCode,chk_ruta;
    AlertDialog alert = null;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    CobranzaDetalleSQLiteDao CobranzaDetalleSQLiteDao;
    KardexPagoRepository kardexPagoRepository;
    private MapView mapView;
    private GoogleMap myGoogleMap;
    private Double latitud = null, longitud = null;
    static private String latitudesap,longitudesap;
    private boolean status = true;
    private String direccion = "Sin dirección", referencia = "Sin referencias";
    private Dialog dialog;
    String fechainicio="",timeini="";
    private StatusDispatchRepository statusDispatchRepository;
    private ImageView imv_cobranza,imv_entrega;
    public MenuAccionView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MenuAccionView.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuAccionView newInstance(Object objeto) {

        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        MenuAccionView menuAccionView = new MenuAccionView();

        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;

        Log.e("REOS","MenuAccionView.newInstance: Lista.size()"+Lista.size());
        for(int s=0;s<Lista.size();s++){
            Log.e("REOS","MenuAccionView-Lista.get(s).getDireccion()=>"+Lista.get(s).getDireccion());
            Log.e("REOS","MenuAccionView-Lista.get(s).getDomembarque_id()=>"+Lista.get(s).getDomembarque_id());
            Log.e("REOS","MenuAccionView-Lista.get(s).getDomfactura_id()=>"+Lista.get(s).getDomfactura_id());
            Log.e("REOS","MenuAccionView-Lista.get(s).getCliente_id()=>"+Lista.get(s).getCliente_id());
            Log.e("REOS","MenuAccionView-Lista.get(s).getZona_id()=>"+Lista.get(s).getZona_id());
            Log.e("REOS","MenuAccionView-Lista.get(s).getTerminopago()=>"+Lista.get(s).getTerminopago());
            Log.e("REOS","MenuAccionView-Lista.get(s).getContado()=>"+Lista.get(s).getContado());
            Log.e("REOS","MenuAccionView-Lista.get(s).getCorreo()=>"+Lista.get(s).getCorreo());
            Log.e("REOS","MenuAccionView-Lista.get(s).getChkgeolocation()=>"+Lista.get(s).getChkgeolocation());
            Log.e("REOS","MenuAccionView-Lista.get(s).getTelefonofijo()=>"+Lista.get(s).getTelefonofijo());
            Log.e("REOS","MenuAccionView-Lista.get(s).getTelefonomovil()=>"+Lista.get(s).getTelefonomovil());
            Log.e("REOS","MenuAccionView-Lista.get(s).Control_id()=>"+Lista.get(s).getControl_id());
            Log.e("REOS","MenuAccionView-Lista.get(s).getAddresscode()=>"+Lista.get(s).getAddresscode());
            Log.e("REOS","MenuAccionView-Lista.get(s).getLatitud()=>"+Lista.get(s).getLatitud());
            Log.e("REOS","MenuAccionView-Lista.get(s).getLongitud()=>"+Lista.get(s).getLongitud());
            Log.e("REOS","MenuAccionView-Lista.get(s).getChkRuta=>"+Lista.get(s).getChk_ruta());
            Log.e("REOS","MenuAccionView-Lista.get(s).getTerminopago_id=>"+Lista.get(s).getTerminopago_id());
            CardCode=Lista.get(s).getCliente_id();
            CardName=Lista.get(s).getNombrecliente();
            Address=Lista.get(s).getDireccion();
            DomEmbarque_ID=Lista.get(s).getDomembarque_id();
            Contado=Lista.get(s).getContado();
            Control_id=Lista.get(s).getControl_id();
            Item_id=Lista.get(s).getItem_id();
            AddressCode=Lista.get(s).getAddresscode();
            latitudesap=(Lista.get(s).getLatitud());
            longitudesap=(Lista.get(s).getLongitud());
            if(Lista.get(s).getTelefonofijo()==null||Lista.get(s).getCorreo()==null||Lista.get(s).getChkgeolocation()==null)
            {
                SesionEntity.updateclient="Y";
            }
            chk_ruta=Lista.get(s).getChk_ruta();
        }

        b.putSerializable(ARG_PARAM,Lista);

        menuAccionView.setArguments(b);
        Listado = Lista;
        objetoMenuAccionView=objeto;

        return menuAccionView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getLocation();
        ////COMENTADO EL 26/08/2021




        /*if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
            latitude = mLocation.getLatitude();
        } else {
            //Toast.makeText(this, R.string.error_permission_map, Toast.LENGTH_LONG).show();
        }*/

        //latitude = mLocation.getLatitude();



        /*if (getArguments() != null) {
            getActivity().setTitle(Listado.get(0).getNombrecliente());
        }*/
        getActivity().setTitle(CardName);
    }

    private void getLocation()
    {
        locationManager = (LocationManager) getActivity(). getSystemService(LOCATION_SERVICE);
        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        v= inflater.inflate(R.layout.fragment_menu_accion_view, container, false);
        cv_pedido=v.findViewById(R.id.cv_pedido);
        cv_cobranza=v.findViewById(R.id.cv_cobranza);
        cv_visita=v.findViewById(R.id.cv_visita);
        cv_lead=v.findViewById(R.id.cv_lead);
        cv_visit_section=v.findViewById(R.id.cv_visit_section);
        cv_dispatch=v.findViewById(R.id.cv_dispatch);
        cv_canvas=v.findViewById(R.id.cv_canvas);
        imv_cobranza=v.findViewById(R.id.imv_cobranza);
        imv_entrega=v.findViewById(R.id.imv_entrega);


        dialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        cv_canvas.setVisibility(View.GONE);


        cv_canvas.setOnClickListener(v -> {
            /*String Fragment="MenuAccionView";
            String accion="canvas";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);*/
            Log.e("REOS","MenuAccion-alertDialogVisitSection-cv_canvas.latitude"+latitude);
            Log.e("REOS","MenuAccion-alertDialogVisitSection-cv_canvas.longitude"+longitude);
            Log.e("REOS","MenuAccion-alertDialogVisitSection-cv_canvas.url"+"http://maps.google.com/maps/place/"+latitude+","+longitude);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps/place/"+latitude+","+longitude)); //o la direccion/consulta que quiera "http://maps.google.com/maps?q="+ myLatitude  +"," + myLongitude +"("+ labLocation + ")&iwloc=A&hl=es"
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
        });
        if(!BuildConfig.FLAVOR.equals("peru")&&BuildConfig.FLAVOR.equals("rofalab"))
        {
            cv_lead.setVisibility(View.GONE);
            cv_visit_section.setVisibility(View.GONE);
            cv_dispatch.setVisibility(View.GONE);
        }
        else {
            if(SesionEntity.perfil_id.equals("VENDEDOR")||SesionEntity.perfil_id.equals("Vendedor"))
            {
                //cv_lead.setVisibility(View.GONE);
                cv_visit_section.setVisibility(View.GONE);
                cv_dispatch.setVisibility(View.GONE);
                if(SesionEntity.census.equals("N")){
                    cv_lead.setVisibility(View.GONE);
                }
                else {

                    if(chk_ruta.equals("Y")||chk_ruta.equals("1"))
                    {
                        cv_lead.setVisibility(View.VISIBLE);
                        if(latitudesap==null&&longitudesap==null)
                        {
                            alertdialogInformative(getContext(),"IMPORTANTE","El Cliente tiene no tiene la Geolocalizacion Actualizada, debe actualizar la ubicacion y tomar la Foto del Local...").show();

                        }
                    }
                }
            }
        }
        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
        {
            cv_pedido.setVisibility(View.GONE);
            cv_visita.setVisibility(View.GONE);
            //cv_lead.setVisibility(View.GONE);
            //cv_visit_section.setVisibility(View.GONE);
            //cv_dispatch.setVisibility(View.GONE);
            //cv_canvas.setVisibility(View.GONE);
        }

        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        /********/

        cv_pedido.setOnClickListener(v -> {

            switch (BuildConfig.FLAVOR){

                case "peru":
                case "paraguay":
                case "perurofalab":
                case "espania":

                    String Fragment="MenuAccionView";
                    String accion="pedido";
                    String compuesto=Fragment+"-"+accion;
                    mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                    SesionEntity.quotation="N";
                    break;
                case "ecuador":
                case "bolivia":
                case "chile":

                    alertatipoventa().show();
                    break;
            }
        });

        cv_cobranza.setOnClickListener(v -> {
            Log.e("REOS","MenuAccion-alertDialogVisitSection-cv_cobranza.setOnClickListener:Click");
            if(!SesionEntity.perfil_id.equals("CHOFER"))
            {
            //Prueba Patricia Avila

               validar=CobranzaDetalleSQLiteDao.VerificaRecibosPendientesDeposito(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                if(validar>0){
                    alertarecibospendientes().show();
                }else{
                    alertatiporecibos().show();
                }
            }else
            {
                if(cv_cobranza.isFocusable())
                {
                    validar=CobranzaDetalleSQLiteDao.VerificaRecibosPendientesDeposito(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                    if(validar>0){
                        alertarecibospendientes().show();
                    }else{
                        alertatiporecibos().show();
                    }
                   //alertatiporecibos().show();
                }
                else {
                    Toast.makeText(getActivity(), "Registre el inicio de la visita, para continuar!!!", Toast.LENGTH_LONG).show();
                }
                //alertatiporecibos().show();
            }
        });

        cv_visita.setOnClickListener(v -> {
            ConnectivityManager manager= (ConnectivityManager) getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);;
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    DialogFragment dialogFragment = new VisitaDialogController(objetoMenuAccionView);
                    dialogFragment.show(getActivity().getSupportFragmentManager(),"un dialogo");
                } else {
                    Toast.makeText(getActivity(), "Este modulo solo esta disponible con INTERNET!", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getActivity(), "Este modulo solo esta disponible con INTERNET!", Toast.LENGTH_LONG).show();
            }
            //DialogFragment dialogFragment = new VisitaDialogController(objetoMenuAccionView);
            //dialogFragment.show(getActivity().getSupportFragmentManager(),"un dialogo");
            //alertaAdvertencia("La Opcion Aun no Esta Habilitada",getContext()).show();
        });
        cv_lead.setOnClickListener(v -> {
                String Fragment="MenuAccionView";

                //String accion="leadUpdateClient";
                String accion="leadUpdateClientCensus";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.quotation="N";
            //displayDialogMap();
            //MapsInitializer.initialize(getActivity());

            //mapView.onCreate(dialog.onSaveInstanceState());
            //mapView.onResume();
        });
        cv_visit_section.setOnClickListener(v -> {
            alertDialogVisitSection().show();
        });
        cv_dispatch.setOnClickListener(v -> {
            if(cv_dispatch.isFocusable())
            {
                Log.e("REOS", "MenuAccion-alertDialogVisitSection-cv_dispatch.setOnClickListener:Click");
                androidx.fragment.app.DialogFragment dialogFragment = new StatusDispatchDialog(CardCode, CardName, Control_id, Item_id, DomEmbarque_ID);
                dialogFragment.show(((FragmentActivity) getContext()).getSupportFragmentManager(), "un dialogo");
            }else {
                Toast.makeText(getActivity(), "Registre el inicio de la visita, para continuar!!!", Toast.LENGTH_LONG).show();
            }
            ///Intent i= new Intent(getContext(),   MapaView.class);
            //startActivity(i);
        });

        if(SesionEntity.perfil_id.equals("chofer")||SesionEntity.perfil_id.equals("CHOFER"))
        {
            getStatusRouteDriver();
        }

        return v;
    }

    private void displayDialogMap() {
        //final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_mapa_dialog);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //final EditText editTextAddress = dialog.findViewById(R.id.editTextAddressDialog);
        //final EditText editTextAddressReference = dialog.findViewById(R.id.editTextAddressReferenceDialog);
        final TextView editTextAddress = dialog.findViewById(R.id.editTextAddressDialog);
        final TextView editTextAddressReference = dialog.findViewById(R.id.editTextAddressReferenceDialog);
        editTextAddress.setHint("Client");
        editTextAddressReference.setHint("Address");
        editTextAddress.setEnabled(false);
        editTextAddressReference.setEnabled(false);
        //editTextAddress.setText((direccion.equals("Sin dirección") ? "" : direccion));
        //editTextAddressReference.setText((referencia.equals("Sin referencias") ? "" : referencia));
        editTextAddress.setText(CardName);
        editTextAddressReference.setText(Address);

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
                                            //Toast.makeText(getActivity(), "onLocationChanged", Toast.LENGTH_SHORT).show();

                                            latitud = location.getLatitude();
                                            longitud = location.getLongitude();
                                            Log.e("REOS","LeadClientesView.displayDialogMap.latitud:"+latitud);
                                            Log.e("REOS","LeadClientesView.displayDialogMap.longitud:"+longitud);
                                            LatLng latLng = new LatLng(latitud, longitud);
                                            myGoogleMap.clear();
                                            myGoogleMap.addMarker(new MarkerOptions().position(latLng).title("Potential client").draggable(true));

                                            //editTextCoordenates.setText(latitud + "," + longitud);

                                            if (status) {
                                                myGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
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
            UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
            UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
            ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
            DireccionSQLite direccionSQLite= new DireccionSQLite(getContext());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date date = new Date();
             fecha =dateFormat.format(date);
            //direccion=editTextAddress.getText().toString();
            //referencia=editTextAddressReference.getText().toString();

//            mapView=null;

            if(latitude==0||longitude==0)
            {
                Toast.makeText(getActivity(), "Calculando Ubicacion..., Guarde Ubicacion Nuevamente!!!", Toast.LENGTH_LONG).show();
                getLocation();
            }else
                {
                LeadSQLite leadSQLite = new LeadSQLite(getContext());
                leadSQLite.addLead(
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        String.valueOf(latitude),
                        String.valueOf(longitude),
                        "",
                        "",
                        "",
                        fecha,
                        SesionEntity.fuerzatrabajo_id,
                        SesionEntity.usuario_id,
                        "",
                        "",
                        CardCode,
                        DomEmbarque_ID,
                        "01",
                        AddressCode
                );
                RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
                rutaVendedorSQLiteDao.UpdateChkGeolocationRouteSales(
                        CardCode,
                        DomEmbarque_ID,
                        ObjUsuario.compania_id,
                        fecha,
                        String.valueOf(latitude) ,
                        String.valueOf(longitude)
                );
                direccionSQLite.updateCoordenatesAddress(CardCode,
                        DomEmbarque_ID,String.valueOf(latitude),
                        String.valueOf(longitude));
                dialog.dismiss();
            }
        });
    }

    private Dialog alertarecibospendientes() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_pendient_collection);
        CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
        List<ListaPendingCollectionEntity> listaPendingCollectionEntity=new ArrayList<>();
        listaPendingCollectionEntity=cobranzaDetalleSQLiteDao.getDateandCollections(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Ud. cuenta con Recibos pendientes de Depositar, por favor verificar en CONSULTA COBRADO y realizar el deposito!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);
        //ListaPendingCollectionAdapter ListaPendingCollectionAdapter=new ListaPendingCollectionAdapter(getContext(), ListaPendingCollectionDao.getInstance().getLeads(listaPendingCollectionEntity));
        ListaPendingCollectionAdapter ListaPendingCollectionAdapter=new ListaPendingCollectionAdapter(getContext(),
                //ListaPendingCollectionDao.getInstance().getLeads(listaPendingCollectionEntity)
                listaPendingCollectionEntity
        );
        lv_pending_collection.setAdapter(ListaPendingCollectionAdapter);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }


    private Dialog alertatiporecibos() {

        final Dialog dialog = new Dialog(getContext());
        switch (BuildConfig.FLAVOR) {
            case "bolivia":
            case "paraguay":
            case "ecuador":
                dialog.setContentView(R.layout.layout_dialog_tipo_cobranza_induvis);
                break;
            case "peru":
            case "chile":
            case "perurofalab":
            case "espania":
            //case "ecuador":
                dialog.setContentView(R.layout.layout_dialog_tipo_cobranza);
                break;
        }


        CardView cv_cobranza_ordinaria,cv_cobranza_deposito_directo,cv_cobranza_pago_pos,cv_cobranza_kardex_pago,cv_collection_check;
        cv_cobranza_ordinaria=dialog.findViewById(R.id.cv_cobranza_ordinaria);
        cv_cobranza_deposito_directo=dialog.findViewById(R.id.cv_cobranza_deposito_directo);
        cv_cobranza_pago_pos=dialog.findViewById(R.id.cv_cobranza_pago_pos);
        cv_collection_check=dialog.findViewById(R.id.cv_collection_check);
        //cv_cobranza_kardex_pago=dialog.findViewById(R.id.cv_cobranza_kardex_pago);
        kardexPagoRepository = new ViewModelProvider(getActivity()).get(KardexPagoRepository.class);

        if(SesionEntity.perfil_id.equals("CHOFER"))
        {
            //cv_cobranza_deposito_directo.setVisibility(View.GONE);
        }
        if(!BuildConfig.FLAVOR.equals("chile"))
        {
            cv_collection_check.setVisibility(View.GONE);
        }
        else {
            /*if(Contado.equals("1"))
            {
                cv_collection_check.setVisibility(View.GONE);
            }*/
            cv_collection_check.setVisibility(View.GONE);
        }

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("Elija Tipo de Cobranza:");


        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        cv_cobranza_ordinaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="N";
                SesionEntity.pagopos="N";
                SesionEntity.collectioncheck="N";
                dialog.dismiss();
            }
        });
        cv_cobranza_deposito_directo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="Y";
                SesionEntity.pagopos="N";
                SesionEntity.collectioncheck="N";
                dialog.dismiss();
            }
        });
        cv_cobranza_pago_pos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="N";
                SesionEntity.pagopos="Y";
                SesionEntity.collectioncheck="N";
                dialog.dismiss();
            }
        });
        cv_collection_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertatiporecibos().show();
                String Fragment="MenuAccionView";
                String accion="cobranza";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.pagodirecto="N";
                SesionEntity.pagopos="N";
                SesionEntity.collectioncheck="Y";
                dialog.dismiss();
            }
        });
        /*cv_cobranza_kardex_pago.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /////////////////////Sincronizar Recibos Pendientes de Depositar\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                kardexPagoRepository.getKardexPago(SesionEntity.imei, CardCode,getContext()).observe(getActivity(), data -> {
                    Log.e("Jepicame","=>"+data);
                });

            }
        });*/
        /*Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_menu_accion, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String tag,Object dato);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.e("jpcm","se regresoooo EERTTT");
        ListenerBackPress.setCurrentFragment("FormListaDeudaCliente");

    }

    @Override
    public void onAttach(Context context) {
        ListenerBackPress.setCurrentFragment("MenuAccionView");
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    private Dialog alertaAdvertencia(String texto,Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_advertencia);

        TextView textMsj = dialog.findViewById(R.id.tv_texto);
        textMsj.setText(texto);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);

        dialogButtonOK.setOnClickListener(v -> {
            String Fragment="MenuAccionView";
            String accion="leadUpdateClient";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
            SesionEntity.quotation="N";
            dialog.dismiss();
        });

        dialogButtonCancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertatipoventa() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_tipo_venta);
        CardView cv_tipo_cotizacion,cv_tipo_ordenventa;
        cv_tipo_cotizacion=dialog.findViewById(R.id.cv_tipo_cotizacion);
        cv_tipo_ordenventa=dialog.findViewById(R.id.cv_tipo_ordenventa);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("Elija Tipo de Venta:");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        cv_tipo_cotizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuAccionView";
                String accion="pedido";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.quotation="Y";
                dialog.dismiss();
            }
        });
        cv_tipo_ordenventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuAccionView";
                String accion="pedido";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                SesionEntity.quotation="N";
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
    ArrayList<VisitSectionEntity> listVisitSection=new ArrayList<>();
    private Dialog alertDialogVisitSection() {

        final Dialog dialog = new Dialog(getContext());
        fechainicio="";timeini="";
        dialog.setContentView(R.layout.layout_dialog_visit_section);
        CheckBox chk_start_visitsection,chk_finish_visitsection;
        Button btn_start_visitsection,btn_finish_visitsection,dialogButtonOK;
        chk_start_visitsection=dialog.findViewById(R.id.chk_start_visitsection);
        chk_finish_visitsection=dialog.findViewById(R.id.chk_finish_visitsection);
        btn_start_visitsection=dialog.findViewById(R.id.btn_start_visitsection);
        btn_finish_visitsection=dialog.findViewById(R.id.btn_finish_visitsection);
        dialogButtonOK=dialog.findViewById(R.id.dialogButtonOK);

        SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
        SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        //TextView textTitle = dialog.findViewById(R.id.tv_mensaje);
        //textTitle.setText("Elija Tipo de Venta:");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        VisitSectionEntity visitSectionEntity=new VisitSectionEntity();

        VisitSectionSQLite visitSectionSQLite=new VisitSectionSQLite(getContext());
        listVisitSection=visitSectionSQLite.getVisitSection(CardCode,DomEmbarque_ID,FormatFecha.format(date),Control_id);
        statusDispatchRepository = new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);
        for(int i=0;i<listVisitSection.size();i++)
        {
            if(listVisitSection.get(i).getLatitudini()!=null)
            {
                chk_start_visitsection.setChecked(true);
                btn_start_visitsection.setEnabled(false);
                btn_start_visitsection.setClickable(false);
                Utilitario.disabledButtton(btn_start_visitsection);
            }
            if(listVisitSection.get(i).getLatitudfin()!=null)
            {
                if(!listVisitSection.get(i).getLatitudfin().equals("0"))
                {
                    chk_finish_visitsection.setChecked(true);
                    btn_finish_visitsection.setEnabled(false);
                    btn_finish_visitsection.setClickable(false);
                    Utilitario.disabledButtton(btn_finish_visitsection);
                }
            }
            fechainicio=listVisitSection.get(i).getDateini();
            timeini=listVisitSection.get(i).getTimeini();
        }


        btn_start_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<VisitSectionEntity> listVisitSectionini=new ArrayList<>();
                String Entrega="";
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());
                ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                ArrayList<VisitSectionEntity> listVisitSectionValidation=new ArrayList<>();
                listVisitSectionValidation=visitSectionSQLite.getVisitSectionforDate(FormatFecha.format(date));
                listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforControlID(Control_id,Item_id);
                String direccion_id,zona_id;
                Log.e("REOS","StatusDispatchDialog.onCreateDialog.listDetailDispatchSheetSQLite.size():"+listDetailDispatchSheetSQLite.size());
                for(int i=0;i<listDetailDispatchSheetSQLite.size();i++)
                {
                    Entrega=listDetailDispatchSheetSQLite.get(i).getEntrega();
                }
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-longitude: "+longitude);
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-latitude: "+latitude);
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-FormatFecha.format(date): "+FormatFecha.format(date));
                Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_start_visitsection-listVisitSectionValidation.SIZE(): "+listVisitSectionValidation.size());
                if(latitude!=0&&longitude!=0)
                {
                    if(listVisitSectionValidation.isEmpty())
                    {
                        visitSectionEntity.setCompania_id(ObjUsuario.compania_id);
                        visitSectionEntity.setFuerzatrabajo_id(ObjUsuario.fuerzatrabajo_id);
                        visitSectionEntity.setUsuario_id(ObjUsuario.usuario_id);
                        visitSectionEntity.setCliente_id(CardCode);
                        visitSectionEntity.setDomembarque_id(DomEmbarque_ID);
                        visitSectionEntity.setLatitudini(String.valueOf(latitude));
                        visitSectionEntity.setLongitudini(String.valueOf(longitude));
                        visitSectionEntity.setDateini(FormatFecha.format(date));
                        visitSectionEntity.setTimeini(dateFormathora.format(date));
                        visitSectionEntity.setLatitudfin("0");
                        visitSectionEntity.setLongitudfin("0");
                        visitSectionEntity.setDatefin("0");
                        visitSectionEntity.setTimefin("0");
                        visitSectionEntity.setChkrecibido("0");
                        visitSectionEntity.setIdref(Control_id);
                        visitSectionEntity.setIdrefitemid(Item_id);
                        visitSectionEntity.setLegalnumberref(Entrega);
                        listVisitSectionini.add(visitSectionEntity);
                        visitSectionSQLite.addVisitSection(listVisitSectionini);
                        chk_start_visitsection.setChecked(true);
                        btn_start_visitsection.setEnabled(false);
                        btn_start_visitsection.setClickable(false);

                        Utilitario.disabledButtton(btn_start_visitsection);
                        Toast.makeText(getActivity(), "Visita Iniciada!!!", Toast.LENGTH_LONG).show();
                        Utilitario.enableCardView(cv_dispatch, getContext(), imv_entrega);
                        Utilitario.enableCardView(cv_cobranza, getContext(), imv_cobranza);
                    }
                    else {
                        Toast.makeText(getContext(), "Se tiene Visitas iniciadas debe finalizarlas para poder continuar...", Toast.LENGTH_SHORT).show();
                        getalertListVisitUnClosed(listVisitSectionValidation,"VISITA ABIERTA","Debe cerrar las siguientes visitas para poder continuar...").show();
                    }
                }
                else {
                    Toast.makeText(getContext(), "Un Momento Por favor Calculando Coordenadas... Intente Guardar Nuevamente!!!", Toast.LENGTH_SHORT).show();
                    getLocation();
                }
            }

        });
        btn_finish_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Latitudini="0",Longitudini="0";
                CobranzaDetalleSQLiteDao  cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(getContext());
                StatusDispatchSQLite statusDispatchSQLite=new StatusDispatchSQLite(getContext());
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                cobranzaDetalleSQLiteDao.getCountCollectionDate(FormatFecha.format(date),ObjUsuario.fuerzatrabajo_id,CardCode);


                if(
                        cobranzaDetalleSQLiteDao.getCountCollectionDate(FormatFecha.format(date),ObjUsuario.fuerzatrabajo_id,CardCode)>0
                        || statusDispatchSQLite.getCountStatusDispatchforDate(ObjUsuario.fuerzatrabajo_id,CardCode,Control_id,Item_id)   >0
                )
                {
                    Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_finish_visitsection-longitude: "+longitude);
                    Log.e("REOS","MenuAccion-alertDialogVisitSection-btn_finish_visitsection-latitude: "+latitude);
                    if(latitude!=0&&longitude!=0)
                    {
                        listVisitSection = visitSectionSQLite.getVisitSection(CardCode, DomEmbarque_ID, FormatFecha.format(date),Control_id);
                        statusDispatchRepository = new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);
                        for (int i = 0; i < listVisitSection.size(); i++) {
                            fechainicio = listVisitSection.get(i).getDateini();
                            timeini = listVisitSection.get(i).getTimeini();
                            Latitudini = listVisitSection.get(i).getLatitudini();
                            Longitudini = listVisitSection.get(i).getLongitudini();
                        }
                        visitSectionSQLite.UpdateVisitSectionForClient(
                                CardCode, DomEmbarque_ID, FormatFecha.format(date), String.valueOf(latitude), String.valueOf(longitude), FormatFecha.format(date), dateFormathora.format(date)
                        );
                        chk_finish_visitsection.setChecked(true);
                        btn_finish_visitsection.setEnabled(false);
                        btn_finish_visitsection.setClickable(false);
                        Utilitario.disabledButtton(btn_finish_visitsection);

                        if (SesionEntity.perfil_id.equals("CHOFER") || SesionEntity.perfil_id.equals("chofer")) {

                            statusDispatchSQLite.UpdateTimeStatusDispatch(
                                    CardCode,
                                    DomEmbarque_ID, timeini, dateFormathora.format(date), Latitudini, Longitudini
                            );
                            AppExecutors executor=new AppExecutors();
                            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                            //statusDispatchRepository.statusDispatchSendTime(getContext()).observe(getActivity(), data -> {
                            statusDispatchRepository.statusDispatchSendTime(getContext(),executor.diskIO()).observe(getActivity(), data -> {
                                Log.e("REOS", "statusDispatchRepository-->statusDispatchSend-->resultdata" + data);
                            });

                        } else {
                            RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
                            rutaVendedorSQLiteDao.UpdateChkVisitSection(
                                    CardCode,
                                    DomEmbarque_ID,
                                    ObjUsuario.compania_id,
                                    FormatFecha.format(date)
                            );
                        }
                        Toast.makeText(getActivity(), "Visita Finalizada!!!", Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(getContext(), "Un Momento Por favor Calculando Coordenadas... Intente Guardar Nuevamente!!!", Toast.LENGTH_SHORT).show();
                        getLocation();
                    }
                }else {
                    Toast.makeText(getActivity(), "No ah generado cobranza o despacho a este cliente, sirvase a actualizar su despacho o generar su cobranza!!!", Toast.LENGTH_LONG).show();
                    //alertdialogInformative(getContext(),"","No ah generado cobranza o despacho a este cliente,Seguro que desea Finalizar la Visita?").show();
                }

            }
        });
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    public void savefinishvisit()
    {

    }

    private void getStatusRouteDriver()
    {
        SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Date date = new Date();
        VisitSectionSQLite visitSectionSQLite=new VisitSectionSQLite(getContext());
        ArrayList<VisitSectionEntity> listVisitSection=new ArrayList<>();
        listVisitSection=visitSectionSQLite.getVisitSection(CardCode,DomEmbarque_ID,FormatFecha.format(date),Control_id);
        Log.e("REOS","MenuAccionView-getStatusRouteDriver-CardCode"+CardCode);
        Log.e("REOS","MenuAccionView-getStatusRouteDriver-DomEmbarque_ID"+DomEmbarque_ID);
        Log.e("REOS","MenuAccionView-getStatusRouteDriver-FormatFecha.format(date)"+FormatFecha.format(date));
        Log.e("REOS","MenuAccionView-getStatusRouteDriver-listVisitSection"+listVisitSection);
        if(listVisitSection==null||listVisitSection.isEmpty())
        {
            Log.e("REOS","MenuAccionView-getStatusRouteDriver-listVisitSection==null");
            Utilitario.disabledCardView(cv_dispatch,getContext(),imv_entrega,true);
            Utilitario.disabledCardView(cv_cobranza,getContext(),imv_cobranza,true);
        }
        /*for(int i=0;i<listVisitSection.size();i++)
        {
            if(listVisitSection.get(i).getLatitudini()==null)
            {
                Log.e("REOS","MenuAccionView-getStatusRouteDriver-listVisitSection.get(i).getLatitudini()==null");
                Utilitario.disabledCardView(cv_dispatch,getContext());
                Utilitario.disabledCardView(cv_cobranza,getContext());
            }
        }*/
    }

    static private Dialog alertdialogInformative(Context context, String titulo, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text=(TextView) dialog.findViewById(R.id.text);
        text.setText(titulo);
        textViewMsj.setText(message);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                        String Fragment="MenuAccionView";
                        String accion="leadUpdateClientCensus";
                        String compuesto=Fragment+"-"+accion;
                        mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
                        SesionEntity.quotation="N";
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog getalertListVisitUnClosed (List<VisitSectionEntity> visitSectionEntityList, String Type,String Message) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_list_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText(Message);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        TextView txtdocumento = dialog.findViewById(R.id.txtdocumento);
        txtdocumento.setText(Type);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);

        ListVisitUnClosedAdapter listVisitUnClosedAdapter=new ListVisitUnClosedAdapter(getContext(), visitSectionEntityList);

        lv_pending_collection.setAdapter(listVisitUnClosedAdapter);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog


        dialogButton.setOnClickListener(new View.OnClickListener() {
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
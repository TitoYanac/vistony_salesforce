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
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
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
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DireccionSQLite;
import com.vistony.salesforce.Dao.SQLite.LeadSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitSectionSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitSectionEntity;
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

    OnFragmentInteractionListener mListener;
    public static Object objetoMenuAccionView=new Object();

    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    LocationManager locationManager;
    static String CardCode,CardName,Address,DomEmbarque_ID,Contado;
    AlertDialog alert = null;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    CobranzaDetalleSQLiteDao CobranzaDetalleSQLiteDao;
    KardexPagoRepository kardexPagoRepository;
    private MapView mapView;
    private GoogleMap myGoogleMap;
    private Double latitud = null, longitud = null;
    private boolean status = true;
    private String direccion = "Sin dirección", referencia = "Sin referencias";
    private Dialog dialog;

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
            CardCode=Lista.get(s).getCliente_id();
            CardName=Lista.get(s).getNombrecliente();
            Address=Lista.get(s).getDireccion();
            DomEmbarque_ID=Lista.get(s).getDomembarque_id();
            Contado=Lista.get(s).getContado();

            if(Lista.get(s).getTelefonofijo()==null||Lista.get(s).getCorreo()==null||Lista.get(s).getChkgeolocation()==null)
            {
                SesionEntity.updateclient="Y";
            }
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


        ////COMENTADO EL 26/08/2021

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

        dialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        cv_canvas.setVisibility(View.GONE);
        cv_visit_section.setVisibility(View.GONE);
        //cv_lead.setVisibility(View.GONE);
        if(BuildConfig.FLAVOR.equals("peru"))
        {
            if(SesionEntity.perfil_id.equals("VENDEDOR")||SesionEntity.perfil_id.equals("Vendedor"))
            {

                if(SesionEntity.updateclient.equals("Y")) {
                    alertaAdvertencia("Tiene Datos de Clientes pendientes de Actualizar, desea actualizarlo ahora?", getContext()).show();
                }
            }
        }
        cv_canvas.setOnClickListener(v -> {
            String Fragment="MenuAccionView";
            String accion="canvas";
            String compuesto=Fragment+"-"+accion;
            mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
        });
        if(!BuildConfig.FLAVOR.equals("peru"))
        {
            cv_lead.setVisibility(View.GONE);
            cv_visit_section.setVisibility(View.GONE);
            cv_dispatch.setVisibility(View.GONE);
        }
        else {
            if(SesionEntity.perfil_id.equals("VENDEDOR")||SesionEntity.perfil_id.equals("Vendedor"))
            {
                //cv_lead.setVisibility(View.GONE);
                //cv_visit_section.setVisibility(View.GONE);
                cv_dispatch.setVisibility(View.GONE);
            }
        }
        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
        {
            cv_pedido.setVisibility(View.GONE);
            cv_visita.setVisibility(View.GONE);
            cv_lead.setVisibility(View.GONE);
            cv_visit_section.setVisibility(View.GONE);
            //cv_dispatch.setVisibility(View.GONE);
        }

        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        /********/

        cv_pedido.setOnClickListener(v -> {

            switch (BuildConfig.FLAVOR){

                case "peru":
                case "paraguay":
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

            if(!SesionEntity.perfil_id.equals("CHOFER"))
            {
            //Prueba Patricia Avila
               validar=CobranzaDetalleSQLiteDao.VerificaRecibosPendientesDeposito(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
                if(validar>0){
                    alertarecibospendientes().show();
                }else{
                    alertatiporecibos().show();
                }
            }else {
               alertatiporecibos().show();
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
                String accion="leadUpdateClient";
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
            androidx.fragment.app.DialogFragment dialogFragment = new StatusDispatchDialog(CardCode,CardName);
            dialogFragment.show(((FragmentActivity) getContext ()). getSupportFragmentManager (),"un dialogo");
            ///Intent i= new Intent(getContext(),   MapaView.class);
            //startActivity(i);
        });

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
                        "01"
                );
                RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
                rutaVendedorSQLiteDao.UpdateChkGeolocationRouteSales(
                        CardCode,
                        DomEmbarque_ID,
                        ObjUsuario.compania_id,
                        fecha
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
        ListaPendingCollectionAdapter ListaPendingCollectionAdapter=new ListaPendingCollectionAdapter(getContext(), ListaPendingCollectionDao.getInstance().getLeads(listaPendingCollectionEntity));
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
            cv_cobranza_deposito_directo.setVisibility(View.GONE);
        }
        if(!BuildConfig.FLAVOR.equals("chile"))
        {
            cv_collection_check.setVisibility(View.GONE);
        }
        else {
            if(Contado.equals("1"))
            {
                cv_collection_check.setVisibility(View.GONE);
            }
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

    private Dialog alertDialogVisitSection() {

        final Dialog dialog = new Dialog(getContext());
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
        ArrayList<VisitSectionEntity> listVisitSection=new ArrayList<>();
        VisitSectionSQLite visitSectionSQLite=new VisitSectionSQLite(getContext());
        listVisitSection=visitSectionSQLite.getVisitSection(CardCode,DomEmbarque_ID,FormatFecha.format(date));

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
        }


        btn_start_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<VisitSectionEntity> listVisitSectionini=new ArrayList<>();
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

                visitSectionEntity.setCompania_id(ObjUsuario.compania_id);
                visitSectionEntity.setFuerzatrabajo_id(ObjUsuario.fuerzatrabajo_id);
                visitSectionEntity.setUsuario_id(ObjUsuario.usuario_id);
                visitSectionEntity.setCliente_id(CardCode);
                visitSectionEntity.setDomembarque_id(DomEmbarque_ID);
                visitSectionEntity.setLatitudini(String.valueOf(latitude));
                visitSectionEntity.setLongitudini(String.valueOf(longitude));
                visitSectionEntity.setDateini(FormatFecha.format(date));
                visitSectionEntity.setTimeini(dateFormathora.format(date));
                visitSectionEntity.setLatitudfin( "0");
                visitSectionEntity.setLongitudfin( "0");
                visitSectionEntity.setDatefin("0");
                visitSectionEntity.setTimefin("0");
                visitSectionEntity.setChkrecibido("0");
                listVisitSectionini.add(visitSectionEntity);
                visitSectionSQLite.addVisitSection(listVisitSectionini);
                chk_start_visitsection.setChecked(true);
                btn_start_visitsection.setEnabled(false);
                btn_start_visitsection.setClickable(false);
                Utilitario.disabledButtton(btn_start_visitsection);
                Toast.makeText(getActivity(), "Visita Iniciada!!!", Toast.LENGTH_LONG).show();
            }

        });
        btn_finish_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

                visitSectionSQLite.UpdateVisitSectionForClient(
                        CardCode,DomEmbarque_ID,FormatFecha.format(date),String.valueOf(latitude),String.valueOf(longitude),FormatFecha.format(date),dateFormathora.format(date)
                );
                chk_finish_visitsection.setChecked(true);
                btn_finish_visitsection.setEnabled(false);
                btn_finish_visitsection.setClickable(false);
                Utilitario.disabledButtton(btn_finish_visitsection);

                RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(getContext());
                rutaVendedorSQLiteDao.UpdateChkVisitSection(
                        CardCode,
                        DomEmbarque_ID,
                        ObjUsuario.compania_id,
                        FormatFecha.format(date)
                );
                Toast.makeText(getActivity(), "Visita Finalizada!!!", Toast.LENGTH_LONG).show();
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




}
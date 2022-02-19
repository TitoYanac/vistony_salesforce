package com.vistony.salesforce.View;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Adapters.VisitaDialogController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Dao.Retrofit.KardexPagoRepository;
import com.vistony.salesforce.Dao.Retrofit.PriceListRepository;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;

public class MenuAccionView extends Fragment {

    private static final String ARG_PARAM = "ListaClienteCabeceraEntity";

    private String mParam1;
    private String mParam2;
    int validar=0;
    private static String TAG_1 = "text";
    View v;
    private CardView cv_pedido,cv_cobranza,cv_visita;
    public static ArrayList<ListaClienteCabeceraEntity> Listado;

    OnFragmentInteractionListener mListener;
    public static Object objetoMenuAccionView=new Object();

    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    LocationManager locationManager;
    static String CardCode,CardName;
    AlertDialog alert = null;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    CobranzaDetalleSQLiteDao CobranzaDetalleSQLiteDao;
    KardexPagoRepository kardexPagoRepository;
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
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDireccion());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDomembarque_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getDomfactura_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getCliente_id());
            Log.e("JEPICAMEE","=>"+Lista.get(s).getZona_id());
            CardCode=Lista.get(s).getCliente_id();
            CardName=Lista.get(s).getNombrecliente();
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
        setHasOptionsMenu(true);

        if(SesionEntity.perfil_id.equals("CHOFER")||SesionEntity.perfil_id.equals("Chofer"))
        {
            cv_pedido.setVisibility(View.GONE);
            cv_visita.setVisibility(View.GONE);
        }

        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        /********/

        cv_pedido.setOnClickListener(v -> {

            switch (BuildConfig.FLAVOR){

                case "peru":
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

            validar=CobranzaDetalleSQLiteDao.VerificaRecibosPendientesDeposito(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
            if(validar>0){
                alertarecibospendientes().show();
            }else{
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
            //alertaAdvertencia("La Opcion Aun no Esta Habilitada",getContext()).show();
        });
        return v;
    }

    private Dialog alertarecibospendientes() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Ud. cuenta con recibos de cobranzas pendientes, por favor verificar en CONSULTA COBRADO y realizar el deposito!");

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


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

            case "chile":
            case "ecuador":
            case "bolivia":
                dialog.setContentView(R.layout.layout_dialog_tipo_cobranza_induvis);
                break;
            case "peru":
                dialog.setContentView(R.layout.layout_dialog_tipo_cobranza);
                break;
        }


        CardView cv_cobranza_ordinaria,cv_cobranza_deposito_directo,cv_cobranza_pago_pos,cv_cobranza_kardex_pago;
        cv_cobranza_ordinaria=dialog.findViewById(R.id.cv_cobranza_ordinaria);
        cv_cobranza_deposito_directo=dialog.findViewById(R.id.cv_cobranza_deposito_directo);
        cv_cobranza_pago_pos=dialog.findViewById(R.id.cv_cobranza_pago_pos);
        //cv_cobranza_kardex_pago=dialog.findViewById(R.id.cv_cobranza_kardex_pago);
        kardexPagoRepository = new ViewModelProvider(getActivity()).get(KardexPagoRepository.class);

        if(SesionEntity.perfil_id.equals("CHOFER"))
        {
            cv_cobranza_deposito_directo.setVisibility(View.GONE);
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


}
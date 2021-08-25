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

import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Adapters.VisitaDialogController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuAccionView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuAccionView extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
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
    AlertDialog alert = null;
    SimpleDateFormat dateFormat;
    Date date;
    String fecha;
    CobranzaDetalleSQLiteDao CobranzaDetalleSQLiteDao;
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
        //Log.e("jpcm","regreso here 1 de "+ ListenerBackPress.getCurrentFragment());
        //ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        Log.e("jpcm","regreso here 1 de "+ ListenerBackPress.getCurrentFragment());
        ListenerBackPress.setCurrentFragment("FormListClienteDetalleRutaVendedor");
        MenuAccionView menuAccionView = new MenuAccionView();
        //ArrayList<String> Listado = new ArrayList<String>();
        Bundle b = new Bundle();
        ArrayList<ListaClienteCabeceraEntity> Lista = (ArrayList<ListaClienteCabeceraEntity>) objeto;
        Lista.size();
        b.putSerializable(TAG_1,Lista);
        menuAccionView.setArguments(b);
        Listado = Lista;
        //Listado.size();
        objetoMenuAccionView=objeto;

        return menuAccionView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getActivity(). getSystemService(LOCATION_SERVICE);
        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        /****Mejora****/
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
        Log.e("REOS","MenuaccionView: Latidud: "+String.valueOf(latitude)+" "+"Longitud: "+String.valueOf(longitude));
        //latitude = mLocation.getLatitude();
        getActivity().setTitle("Menu Accion");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_menu_accion_view, container, false);
        cv_pedido=v.findViewById(R.id.cv_pedido);
        cv_cobranza=v.findViewById(R.id.cv_cobranza);
        cv_visita=v.findViewById(R.id.cv_visita);
        setHasOptionsMenu(true);
        CobranzaDetalleSQLiteDao = new CobranzaDetalleSQLiteDao(getContext());
        /********/
        cv_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Fragment="MenuAccionView";
                String accion="pedido";
                String compuesto=Fragment+"-"+accion;
                mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);

                //alertaAdvertencia("La Opcion Aun no Esta Habilitada",getContext()).show();
            }
        });

        cv_cobranza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                date = new Date();
                fecha =dateFormat.format(date);

                validar=CobranzaDetalleSQLiteDao.VerificaRecibosPendientesDeposito(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id,fecha);
                if(validar>0){
                    //Toast.makeText(this, "Ud. cuenta con Recibos Pendientes de Deposito, favor de completar el Proceso de Deposito", Toast.LENGTH_LONG).show();
                    alertarecibospendientes().show();
                }else{
                    /*SesionEntity.pagodirecto="0";
                    contentFragment = new RutaVendedorView();
                    fragment = "RutaVendedorView";
                    accion = "nuevoinicioRutaVendedorView";
                    compuesto = fragment + "-" + accion;
                    object = null;
                    onFragmentInteraction(compuesto, object);
                    Log.e("jpcm","Entramos aqui");*/
                    alertatiporecibos().show();
                }

                //String Fragment="MenuAccionView";
                //String accion="cobranza";
                //String compuesto=Fragment+"-"+accion;
                //mListener.onFragmentInteraction(compuesto,objetoMenuAccionView);
            }
        });

        cv_visita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
            }
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
        dialog.setContentView(R.layout.layout_dialog_tipo_cobranza);
        CardView cv_cobranza_ordinaria,cv_cobranza_deposito_directo,cv_cobranza_pago_pos;
        cv_cobranza_ordinaria=dialog.findViewById(R.id.cv_cobranza_ordinaria);
        cv_cobranza_deposito_directo=dialog.findViewById(R.id.cv_cobranza_deposito_directo);
        cv_cobranza_pago_pos=dialog.findViewById(R.id.cv_cobranza_pago_pos);


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
                SesionEntity.pagodirecto="0";
                SesionEntity.pagopos="0";
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
                SesionEntity.pagodirecto="1";
                SesionEntity.pagopos="0";
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
                SesionEntity.pagodirecto="0";
                SesionEntity.pagopos="1";
                dialog.dismiss();
            }
        });
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
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String Fragment="OrdenVentaCabeceraView";
                String accion="detalle";
                String compuesto=Fragment+"-"+accion;
                String Objeto=contado;
                mListener.onFragmentInteraction(compuesto,Objeto);*/

                dialog.dismiss();
            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
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
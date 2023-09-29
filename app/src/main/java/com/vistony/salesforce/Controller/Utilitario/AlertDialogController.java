package com.vistony.salesforce.Controller.Utilitario;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.Controller.Adapters.AlertGPSDialogController;
import com.vistony.salesforce.Controller.Adapters.ListVisitUnClosedAdapter;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.RutaVendedorSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Dao.SQLite.VisitSectionSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitSectionEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlertDialogController extends DialogFragment {
    static ArrayList<VisitSectionEntity> listVisitSection=new ArrayList<>();
    static String fechainicio="",timeini="";
    static private StatusDispatchRepository statusDispatchRepository;
    static LocationManager locationManager;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    static private GPSController gpsController;
    static private Location mLocation;
    static double latitude, longitude;
    Activity activity;
    static ViewModelStoreOwner viewModelStoreOwner;
    static LifecycleOwner lifecycleOwner;

    public AlertDialogController(Activity activity){
        this.activity=activity;
        viewModelStoreOwner=this;
        lifecycleOwner=this;
    }

    static public Dialog alertDialogVisitSection(
            Context context,
            String CardCode,
            String DomEmbarque_ID,
            String Control_id,
            String Item_id,
            Activity activity
    ) {

        final Dialog dialog = new Dialog(context);
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

        VisitSectionSQLite visitSectionSQLite=new VisitSectionSQLite(context);
        listVisitSection=visitSectionSQLite.getVisitSection(CardCode,DomEmbarque_ID,FormatFecha.format(date),Control_id);

        statusDispatchRepository = new ViewModelProvider(viewModelStoreOwner).get(StatusDispatchRepository.class);

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
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
                DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(context);
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
                        Toast.makeText(context, context.getResources().getString(R.string.visit_start), Toast.LENGTH_LONG).show();
                        //Utilitario.enableCardView(cv_dispatch, getContext(), imv_entrega);
                        //Utilitario.enableCardView(cv_cobranza, getContext(), imv_cobranza);
                    }
                    else {
                        Toast.makeText(context, context.getResources().getString(R.string.mse_visits_no_finish), Toast.LENGTH_SHORT).show();
                        getalertListVisitUnClosed(listVisitSectionValidation,context.getResources().getString(R.string.visit_open),context.getResources().getString(R.string.mse_finish_visit_next),context).show();
                    }
                }
                else {
                    Toast.makeText(context, context.getResources().getString(R.string.mse_wait_moment_calculing_coordenates), Toast.LENGTH_SHORT).show();
                    getLocation( context, activity);
                }
            }

        });
        btn_finish_visitsection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Latitudini="0",Longitudini="0";
                CobranzaDetalleSQLiteDao cobranzaDetalleSQLiteDao=new CobranzaDetalleSQLiteDao(context);
                StatusDispatchSQLite statusDispatchSQLite=new StatusDispatchSQLite(context);
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
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
                        statusDispatchRepository = new ViewModelProvider(viewModelStoreOwner).get(StatusDispatchRepository.class);
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
                            statusDispatchRepository.statusDispatchSendTime(context,executor.diskIO()).observe(lifecycleOwner, data -> {
                                Log.e("REOS", "statusDispatchRepository-->statusDispatchSend-->resultdata" + data);
                            });

                        } else {
                            RutaVendedorSQLiteDao rutaVendedorSQLiteDao = new RutaVendedorSQLiteDao(context);
                            rutaVendedorSQLiteDao.UpdateChkVisitSection(
                                    CardCode,
                                    DomEmbarque_ID,
                                    ObjUsuario.compania_id,
                                    FormatFecha.format(date)
                            );
                        }
                        Toast.makeText(context, context.getResources().getString(R.string.visit_finish), Toast.LENGTH_LONG).show();
                    }else
                    {
                        Toast.makeText(context, context.getResources().getString(R.string.mse_wait_moment_calculing_coordenates), Toast.LENGTH_SHORT).show();
                        getLocation( context, activity);
                    }
                }else {
                    Toast.makeText(context, context.getResources().getString(R.string.mse_no_generation_collection), Toast.LENGTH_LONG).show();
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

    static private void getLocation(Context context,Activity activity)
    {
        locationManager = (LocationManager) context. getSystemService(LOCATION_SERVICE);
        //****Mejora****
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            // AlertNoGps();
            DialogFragment dialogFragment = new AlertGPSDialogController();
            dialogFragment.show(((FragmentActivity) context). getSupportFragmentManager (),"un dialogo");
        }
        // When you need the permission, e.g. onCreate, OnClick etc.
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("REOS","MenuAccionView: No tiene ACCESS_FINE_LOCATION ");
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            Log.e("REOS","MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            // We have already permission to use the location
            try {
                gpsController =  new GPSController(context);
                mLocation = gpsController.getLocation(mLocation);
                latitude = mLocation.getLatitude();
                longitude= mLocation.getLongitude();
            }catch (Exception e)
            {
                System.out.println(e.getMessage());
            }

        }
    }
    static private Dialog getalertListVisitUnClosed
            (List<VisitSectionEntity> visitSectionEntityList, String Type, String Message,Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_list_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(context.getResources().getString(R.string.dialog_advertencia));
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText(Message);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        TextView txtdocumento = dialog.findViewById(R.id.txtdocumento);
        txtdocumento.setText(Type);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);

        ListVisitUnClosedAdapter listVisitUnClosedAdapter=new ListVisitUnClosedAdapter(context, visitSectionEntityList);

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

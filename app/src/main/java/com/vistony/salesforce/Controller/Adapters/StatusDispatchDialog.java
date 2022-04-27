package com.vistony.salesforce.Controller.Adapters;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Dao.Retrofit.CobranzaRepository;
import com.vistony.salesforce.Dao.Retrofit.DepositoRepository;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteDetalleView;
import com.vistony.salesforce.View.HistoricoDepositoView;
import com.vistony.salesforce.View.LoginView;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatusDispatchDialog extends DialogFragment {
    private ActivityResultLauncher<Intent> someActivityResultLauncher;
    private ActivityResultLauncher<Intent> someActivityResultLauncherGuia;
    private Bitmap imgBitmap,imgBitmap2;
    private byte[] byteArray,byteArray2;
    String cliente_id,cliente;
    public StatusDispatchDialog(String Client_id,String cliente){
    this.cliente_id=Client_id;
        this.cliente=cliente;
    }
    LocationManager locationManager;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //final View view = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_status_dispatch, null);
        locationManager = (LocationManager) getActivity(). getSystemService(LOCATION_SERVICE);
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

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_dialog_status_dispatch);
        Spinner spn_referral_guide,spn_type_dispatch,spn_reason_dispatch;
        FloatingActionButton floatingButtonTakePhoto;
        TextView textMsj = dialog.findViewById(R.id.tv_titulo);
        ImageView imageViewPhoto,imageViewPhoto2,imv_historic_status_dispatch_delivery,imv_historic_status_dispatch_photo;
        textMsj.setText("Advertencia!!!");
        EditText et_comentario;
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        spn_referral_guide = dialog.findViewById(R.id.spn_referral_guide);
        spn_type_dispatch = dialog.findViewById(R.id.spn_type_dispatch);
        spn_reason_dispatch = dialog.findViewById(R.id.spn_reason_dispatch);
        et_comentario = dialog.findViewById(R.id.et_comentario);
        imageViewPhoto = dialog.findViewById(R.id.imageViewPhoto);
        imageViewPhoto.setBackgroundResource(R.drawable.portail);
        imageViewPhoto2 = dialog.findViewById(R.id.imageViewPhoto2);
        imageViewPhoto2.setBackgroundResource(R.drawable.portail);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        floatingButtonTakePhoto = (FloatingActionButton) dialog.findViewById(R.id.floatingButtonTakePhoto);
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());

        imv_historic_status_dispatch_delivery = dialog.findViewById(R.id.imv_historic_status_dispatch_delivery);
        imv_historic_status_dispatch_photo = dialog.findViewById(R.id.imv_historic_status_dispatch_photo);
        ArrayList<String> sppdelivery=new ArrayList<>();
        listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforClient(cliente_id);
        for(int i=0;i<listDetailDispatchSheetSQLite.size();i++)
        {
            sppdelivery.add(listDetailDispatchSheetSQLite.get(i).getEntrega());
        }

        ArrayAdapter<String> adapterdelivery = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,sppdelivery);
        spn_referral_guide.setAdapter(adapterdelivery);
        adapterdelivery.notifyDataSetChanged();

        this.someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                imgBitmap = (Bitmap) extras.get("data");
                imageViewPhoto.setImageBitmap(imgBitmap);
            }
        });

        this.someActivityResultLauncherGuia = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Bundle extras = result.getData().getExtras();
                imgBitmap2 = (Bitmap) extras.get("data");
                imageViewPhoto2.setImageBitmap(imgBitmap2);
            }
        });
       /* floatingButtonTakePhoto.setOnClickListener(data -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                someActivityResultLauncher.launch(intent);
            }
        });*/
        imv_historic_status_dispatch_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    someActivityResultLauncherGuia.launch(intent);
                }
        }});
        imv_historic_status_dispatch_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    someActivityResultLauncher.launch(intent);
                }
            }});

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encoded = null,encoded2 = null;
                if (imgBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imgBitmap.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byteArray = stream.toByteArray();
                    encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                }
                if (imgBitmap2 != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imgBitmap2.compress(Bitmap.CompressFormat.PNG, 80, stream);
                    byteArray2 = stream.toByteArray();
                    encoded2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);
                }
                SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
                SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                Date date = new Date();
                List<StatusDispatchEntity> listStatusDispatchEntity=new ArrayList<>();
                UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                UsuarioSQLite usuarioSQLite=new UsuarioSQLite(getContext());
                ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
                StatusDispatchSQLite statusDispatchSQLite=new StatusDispatchSQLite(getContext());
                ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
                DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());
                listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforClient(cliente_id);
                String entrega_id="",factura_id="",entrega="",factura="",typedispatch_id="",typedispatch="",reasondispatch_id="",reasondispatch="";
                for(int i=0;i<listDetailDispatchSheetSQLite.size();i++)
                {
                    if(spn_referral_guide.getSelectedItem().toString().equals(listDetailDispatchSheetSQLite.get(i).getEntrega()))
                    {
                        entrega_id=listDetailDispatchSheetSQLite.get(i).getEntrega_id();
                        factura_id=listDetailDispatchSheetSQLite.get(i).getFactura_id();
                        factura=listDetailDispatchSheetSQLite.get(i).getFactura();
                        entrega=listDetailDispatchSheetSQLite.get(i).getEntrega();
                    }
                }

                String[] tipodespacho= spn_type_dispatch.getSelectedItem().toString().split("-");
                String[] motivodespacho= spn_reason_dispatch.getSelectedItem().toString().split("-");
                typedispatch_id=tipodespacho[0];
                typedispatch=tipodespacho[1];
                reasondispatch_id=motivodespacho[0];
                reasondispatch=motivodespacho[1];

                StatusDispatchEntity statusDispatchEntity=new StatusDispatchEntity();
                statusDispatchEntity.compania_id=ObjUsuario.compania_id;
                statusDispatchEntity.fuerzatrabajo_id=ObjUsuario.fuerzatrabajo_id;
                statusDispatchEntity.usuario_id=ObjUsuario.usuario_id;
                statusDispatchEntity.typedispatch_id=typedispatch_id;
                statusDispatchEntity.reasondispatch_id=reasondispatch_id;
                statusDispatchEntity.entrega_id =entrega_id;
                statusDispatchEntity.cliente_id =cliente_id;
                statusDispatchEntity.factura_id =factura_id;
                statusDispatchEntity.chkrecibido ="0";
                statusDispatchEntity.observation =et_comentario.getText().toString();
                statusDispatchEntity.foto =encoded;
                statusDispatchEntity.fecha_registro =FormatFecha.format(date);
                statusDispatchEntity.hora_registro =dateFormathora.format(date);
                statusDispatchEntity.fotoGuia = encoded2;
                statusDispatchEntity.latitud = String.valueOf(latitude) ;
                statusDispatchEntity.longitud =String.valueOf(longitude);
                statusDispatchEntity.cliente =cliente;
                statusDispatchEntity.entrega =String.valueOf(entrega);
                statusDispatchEntity.factura =String.valueOf(factura);
                statusDispatchEntity.typedispatch =typedispatch;
                statusDispatchEntity.reasondispatch =reasondispatch;
                listStatusDispatchEntity.add(statusDispatchEntity);
                statusDispatchSQLite.addStatusDispatch(listStatusDispatchEntity);
                Toast.makeText(getContext(), "Despacho Actualizado Correctamente", Toast.LENGTH_SHORT).show();
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

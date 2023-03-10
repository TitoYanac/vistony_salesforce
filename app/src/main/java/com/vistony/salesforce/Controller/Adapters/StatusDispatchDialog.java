package com.vistony.salesforce.Controller.Adapters;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lowagie.text.DocumentException;
import com.vistony.salesforce.AppExecutors;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Controller.Utilitario.ImageCameraController;
import com.vistony.salesforce.Dao.Retrofit.StatusDispatchRepository;
import com.vistony.salesforce.Dao.SQLite.DetailDispatchSheetSQLite;
import com.vistony.salesforce.Dao.SQLite.ReasonDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.StatusDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.TypeDispatchSQLite;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.LoginView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
    String cliente_id,cliente,control_id,item_id,address;
    LocationManager locationManager;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    StatusDispatchRepository statusDispatchRepository;
    public static String mCurrentPhotoPathG="",mCurrentPhotoPathL="";
    String Entrega_id="",Entrega="";
    File filelocal,fileguia;
    private static final int MAX_BITMAP_SIZE = 100 * 1024 * 1024; // 100 MB

    public StatusDispatchDialog(String Client_id,String cliente,String control_id,String item_id,String address){
    this.cliente_id=Client_id;
    this.cliente=cliente;
        this.control_id=control_id;
        this.item_id=item_id;
        this.address=address;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //final View view = getActivity().getLayoutInflater().inflate(R.layout.layout_dialog_status_dispatch, null);
        locationManager = (LocationManager) getActivity(). getSystemService(LOCATION_SERVICE);
        statusDispatchRepository = new ViewModelProvider(getActivity()).get(StatusDispatchRepository.class);

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
        TextView lbl_referral_guide=dialog.findViewById(R.id.lbl_referral_guide);
        TextView lbl_delivery=dialog.findViewById(R.id.lbl_delivery);
        TextView tv_description=dialog.findViewById(R.id.tv_description);
        if(BuildConfig.FLAVOR.equals("bolivia"))
        {
            lbl_referral_guide.setText("Documento");
            lbl_delivery.setText("Documento");
            tv_description.setText("Elija el documento y seleccione el estado,motivo y ponga observacion");
        }
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        ImageView dialogButtonCancel = (ImageView) dialog.findViewById(R.id.dialogButtonCancel);
        floatingButtonTakePhoto = (FloatingActionButton) dialog.findViewById(R.id.floatingButtonTakePhoto);
        ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite=new ArrayList<>();
        ArrayList<TypeDispatchEntity> listTypeDispatch=new ArrayList<>();
        ArrayList<ReasonDispatchEntity> listReasonDispatch=new ArrayList<>();
        DetailDispatchSheetSQLite detailDispatchSheetSQLite=new DetailDispatchSheetSQLite(getContext());
        TypeDispatchSQLite typeDispatchSQLite=new TypeDispatchSQLite(getContext());
        ReasonDispatchSQLite reasonDispatchSQLite=new ReasonDispatchSQLite(getContext());
        imv_historic_status_dispatch_delivery = dialog.findViewById(R.id.imv_historic_status_dispatch_delivery);
        imv_historic_status_dispatch_photo = dialog.findViewById(R.id.imv_historic_status_dispatch_photo);
        ArrayList<String> sppdelivery=new ArrayList<>();
        ArrayList<String> spptypeDispatch=new ArrayList<>();
        ArrayList<String> sppOcurrencies=new ArrayList<>();
        Log.e("REOS","StatusDispatchDialog.onCreateDialog.cliente_id:"+cliente_id);
        listDetailDispatchSheetSQLite=detailDispatchSheetSQLite.getDetailDispatchSheetforControlID(control_id,item_id);
        String direccion_id,zona_id;
        Log.e("REOS","StatusDispatchDialog.onCreateDialog.listDetailDispatchSheetSQLite.size():"+listDetailDispatchSheetSQLite.size());
        for(int i=0;i<listDetailDispatchSheetSQLite.size();i++)
        {
            sppdelivery.add(listDetailDispatchSheetSQLite.get(i).getEntrega());
            Entrega_id=listDetailDispatchSheetSQLite.get(i).getEntrega_id();
            Entrega=listDetailDispatchSheetSQLite.get(i).getEntrega();
        }
        ArrayAdapter<String> adapterdelivery = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,sppdelivery);
        spn_referral_guide.setAdapter(adapterdelivery);
        adapterdelivery.notifyDataSetChanged();

        listTypeDispatch=typeDispatchSQLite.getTypeDispatch();
        for(int i=0;i<listTypeDispatch.size();i++)
        {
            if(listTypeDispatch.get(i).getTypedispatch_id().equals("E"))
            {
                spptypeDispatch.add(listTypeDispatch.get(i).getTypedispatch_id()+"-"+listTypeDispatch.get(i).getTypedispatch());
            }
        }
        for(int j=0;j<listTypeDispatch.size();j++)
        {
            if(!listTypeDispatch.get(j).getTypedispatch_id().equals("E"))
            {
                spptypeDispatch.add(listTypeDispatch.get(j).getTypedispatch_id()+"-"+listTypeDispatch.get(j).getTypedispatch());
            }
        }
        ArrayAdapter<String> adapterTypeDispatch = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,spptypeDispatch);
        spn_type_dispatch.setAdapter(adapterTypeDispatch);
        adapterTypeDispatch.notifyDataSetChanged();

        listReasonDispatch=reasonDispatchSQLite.getReasonDispatch();
        for(int i=0;i<listReasonDispatch.size();i++)
        {
            sppOcurrencies.add(listReasonDispatch.get(i).getReasondispatch_id()+"-"+listReasonDispatch.get(i).getReasondispatch());
        }
        ArrayAdapter<String> adapterReasonDispatch = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,sppOcurrencies);
        spn_reason_dispatch.setAdapter(adapterReasonDispatch);
        adapterReasonDispatch.notifyDataSetChanged();

        this.someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {

                Bitmap bitmap2=null;
                try {
                    File file = new File(mCurrentPhotoPathL);
                    bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                    ImageCameraController imageCameraController = new ImageCameraController();
                    filelocal= imageCameraController.SaveImageStatusDispatch (getContext(),bitmap2,Entrega+"_"+getDate(),"L");
                    imgBitmap=bitmap2;
                    int bitmapSize = imgBitmap.getByteCount();
                    if (bitmapSize > MAX_BITMAP_SIZE) {
                        throw new RuntimeException(
                                "Canvas: trying to draw too large(" + bitmapSize + "bytes) bitmap.");
                    }else {
                        //imageViewPhoto.setImageBitmap(imgBitmap);
                        Convert.resizeImage(imageViewPhoto,imgBitmap,getActivity());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                }


                Resources res = getContext().getResources(); // need this to fetch the drawable
                Drawable draw = res.getDrawable( R.drawable.ic_baseline_flip_camera_ios_24);
                imv_historic_status_dispatch_photo.setImageDrawable(draw);
                imv_historic_status_dispatch_photo.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                //Bundle extras = result.getData().getExtras();
                //imgBitmap = (Bitmap) extras.get("data");
                //imageViewPhoto.setImageBitmap(imgBitmap);
            }
        });

        this.someActivityResultLauncherGuia = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult()
                , result -> {

            if (result.getResultCode() == RESULT_OK) {
                Bitmap bitmap2=null;
                try {
                File file = new File(mCurrentPhotoPathG);


                    bitmap2 = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), Uri.fromFile(file));
                    ImageCameraController imageCameraController = new ImageCameraController();
                    fileguia=imageCameraController.SaveImageStatusDispatch (getContext(),bitmap2,Entrega+"_"+getDate(),"G");


                    imgBitmap2=bitmap2;
                    int bitmapSize = imgBitmap.getByteCount();
                    if (bitmapSize > MAX_BITMAP_SIZE) {
                        throw new RuntimeException(
                                "Canvas: trying to draw too large(" + bitmapSize + "bytes) bitmap.");
                    }else {
                        //imageViewPhoto2.setImageBitmap(imgBitmap2);
                        Convert.resizeImage(imageViewPhoto2,imgBitmap2,getActivity());
                    }
                    /*byte[] byteArray,byteArray2;
                    byteArray=imageCameraController.getImageSDtoByte(getContext(),fileguia.toString());
                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    imageViewPhoto2.setImageBitmap(bitmap);*/
                    /*BitmapFactory.decodeByteArray()
                    getImageSDtoByte.
                    imageViewPhoto2.setImageBitmap(
                            //decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
                            decodeSampledBitmapFromResource(getResources(), imgBitmap2, 100, 100));*/

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "No se pudo mostrar la imagen en miniatura - error: "+e.toString(), Toast.LENGTH_SHORT).show();
                }


                Resources res = getContext().getResources(); // need this to fetch the drawable
                Drawable draw = res.getDrawable( R.drawable.ic_baseline_flip_camera_ios_24);
                imv_historic_status_dispatch_delivery.setImageDrawable(draw);
                imv_historic_status_dispatch_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));

                //Bundle extras = result.getData().getExtras();
                //imgBitmap2 = (Bitmap) extras.get("data");
                //imageViewPhoto2.setImageBitmap(imgBitmap2);
            }

        });
        spn_type_dispatch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String typedispatch_id,typedispatch;
                ArrayList<ReasonDispatchEntity> listReasonDispatch=new ArrayList<>();
                ArrayList<String> sppOcurrencies=new ArrayList<>();
                String[] tipodespacho = spn_type_dispatch.getSelectedItem().toString().split("-");
                typedispatch_id = tipodespacho[0];
                typedispatch = tipodespacho[1];
                if(typedispatch_id.equals("E"))
                {
                    spn_reason_dispatch.setAdapter(null);
                    spn_reason_dispatch.setEnabled(false);
                }
                else {
                    spn_reason_dispatch.setAdapter(null);
                    spn_reason_dispatch.setEnabled(true);
                    listReasonDispatch=reasonDispatchSQLite.getReasonDispatch();
                    for(int i=0;i<listReasonDispatch.size();i++)
                    {
                        sppOcurrencies.add(listReasonDispatch.get(i).getReasondispatch_id()+"-"+listReasonDispatch.get(i).getReasondispatch());
                    }
                    ArrayAdapter<String> adapterReasonDispatch = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,sppOcurrencies);
                    spn_reason_dispatch.setAdapter(adapterReasonDispatch);
                    adapterReasonDispatch.notifyDataSetChanged();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


/*
        imv_historic_status_dispatch_delivery.setOnClickListener(new View.OnClickListener() {
        //imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("REOS","statusDispatchRepository-->FotoGuia-->Inicia");
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Crea el File
                        File photoFile = null;
                            photoFile = createImageFile(Entrega+"_"+getDate(),"G");
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            someActivityResultLauncherGuia.launch(intent);
                        }
                } catch (IOException ex) {
                    Log.e("REOS,","StatusDispatchDialog-onCreateDialog-imageViewPhoto-error:"+ex);
                }

        }});
*/
        imageViewPhoto2.setOnClickListener(new View.OnClickListener() {
            //imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("REOS","statusDispatchRepository-->FotoGuia-->Inicia");
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                int permsRequestCode = 255;

                String[] perms = {
                        Manifest.permission.CAMERA
                };
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    requestPermissions(perms, permsRequestCode);
                }else{
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Crea el File
                        File photoFile = null;
                        //startActivityForResult(intent,0);
                        photoFile = createImageFile(Entrega+"_"+getDate(),"G");

                        if (photoFile != null) {
                            //Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                            Uri photoURI=null;
                            switch (BuildConfig.FLAVOR){
                                case "ecuador":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.ecuador" , photoFile);
                                    break;
                                case "chile":
                                case "peru":
                                case "marruecos":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                                    break;
                                case "espania":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.espania" , photoFile);
                                    break;
                                case "perurofalab":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.perurofalab" , photoFile);
                                    break;
                                case "bolivia":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.bolivia" , photoFile);
                                    break;
                                case "paraguay":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.paraguay" , photoFile);
                                    break;
                            }
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            //startActivityForResult(intent,20);
                            someActivityResultLauncherGuia.launch(intent);
                            /*if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                someActivityResultLauncherGuia.launch(intent);
                            }*/

                        }
                    } catch (IOException ex) {
                        Log.e("REOS,","StatusDispatchDialog-onCreateDialog-imageViewPhoto-error:"+ex);
                    }
                }


            }});

        /*
        imv_historic_status_dispatch_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        Log.e("REOS","statusDispatchRepository-->FotoLocal-->Inicia");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Crea el File
                        File photoFile = null;
                            photoFile = createImageFile(Entrega+"_"+getDate(),"L");
                        if (photoFile != null) {
                            Log.e("REOS","statusDispatchRepository-->FotoLocal-->photoFile != null");
                            Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            someActivityResultLauncher.launch(intent);
                        }
                } catch (IOException ex) {
                    Log.e("REOS,","StatusDispatchDialog-onCreateDialog-imageViewPhoto2-error:"+ex);
                }
                Log.e("REOS","statusDispatchRepository-->FotoLocal-->Fin");
            }});*/

        imageViewPhoto.setOnClickListener(new View.OnClickListener() {
            //imageViewPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent = new Intent(MediaStore.EXTRA);
                //if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                //   someActivityResultLauncher.launch(intent);
                //}
                int permsRequestCode = 255;

                String[] perms = {
                        Manifest.permission.CAMERA
                };
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    requestPermissions(perms, permsRequestCode);
                }else{
                    try {
                        Log.e("REOS","statusDispatchRepository-->FotoLocal-->Inicia");

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Crea el File
                        File photoFile = null;

                        photoFile = createImageFile(Entrega+"_"+getDate(),"L");

                        if (photoFile != null) {
                            Log.e("REOS","statusDispatchRepository-->FotoLocal-->photoFile != null");
                            Uri photoURI =null;
                            switch (BuildConfig.FLAVOR){
                                case "ecuador":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.ecuador" , photoFile);
                                    break;
                                case "chile":
                                case "peru":
                                case "marruecos":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                                    break;
                                case "espania":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.espania" , photoFile);
                                    break;
                                case "perurofalab":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.perurofalab" , photoFile);
                                    break;
                                case "bolivia":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.bolivia" , photoFile);
                                    break;
                                case "paraguay":
                                    photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.paraguay" , photoFile);
                                    break;

                            }
                            //Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            //startActivityForResult(intent,20);
                            someActivityResultLauncher.launch(intent);
                            /*if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                Log.e("REOS","statusDispatchRepository-->FotoLocal-->intent.resolveActivity(getActivity().getPackageManager()) != null");
                                someActivityResultLauncher.launch(intent);
                            }*/

                        }
                    } catch (IOException ex) {
                        Log.e("REOS,","StatusDispatchDialog-onCreateDialog-imageViewPhoto2-error:"+ex);
                    }
                    Log.e("REOS","statusDispatchRepository-->FotoLocal-->Fin");

                }

            }});

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    /*String encoded = null, encoded2 = null;
                    if (imgBitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                        byteArray = stream.toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                        //encoded = new String(byteArray);
                    }
                    if (imgBitmap2 != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        imgBitmap2.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                        byteArray2 = stream.toByteArray();
                        encoded2 = Base64.encodeToString(byteArray2, Base64.DEFAULT);
                        //encoded2 = new String(byteArray2);
                    }*/

                    if (imgBitmap != null && imgBitmap2 != null) {

                        if (latitude != 0 && longitude != 0) {
                            SimpleDateFormat dateFormathora = new SimpleDateFormat("HHmmss", Locale.getDefault());
                            SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
                            Date date = new Date();
                            List<StatusDispatchEntity> listStatusDispatchEntity = new ArrayList<>();
                            UsuarioSQLiteEntity ObjUsuario = new UsuarioSQLiteEntity();
                            UsuarioSQLite usuarioSQLite = new UsuarioSQLite(getContext());
                            ObjUsuario = usuarioSQLite.ObtenerUsuarioSesion();
                            StatusDispatchSQLite statusDispatchSQLite = new StatusDispatchSQLite(getContext());
                            ArrayList<HojaDespachoDetalleSQLiteEntity> listDetailDispatchSheetSQLite = new ArrayList<>();
                            DetailDispatchSheetSQLite detailDispatchSheetSQLite = new DetailDispatchSheetSQLite(getContext());
                            listDetailDispatchSheetSQLite = detailDispatchSheetSQLite.getDetailDispatchSheetforClient(cliente_id);
                            String entrega_id = "", factura_id = "", entrega = "", factura = "", typedispatch_id = "", typedispatch = "", reasondispatch_id = "", reasondispatch = "", direccion_id = "";
                            for (int i = 0; i < listDetailDispatchSheetSQLite.size(); i++) {
                                if (spn_referral_guide.getSelectedItem().toString().equals(listDetailDispatchSheetSQLite.get(i).getEntrega())) {
                                    entrega_id = listDetailDispatchSheetSQLite.get(i).getEntrega_id();
                                    factura_id = listDetailDispatchSheetSQLite.get(i).getFactura_id();
                                    factura = listDetailDispatchSheetSQLite.get(i).getFactura();
                                    entrega = listDetailDispatchSheetSQLite.get(i).getEntrega();
                                    direccion_id = listDetailDispatchSheetSQLite.get(i).getDomembarque_id();
                                }
                            }

                            String[] tipodespacho = spn_type_dispatch.getSelectedItem().toString().split("-");
                            typedispatch_id = tipodespacho[0];
                            typedispatch = tipodespacho[1];
                            if (spn_reason_dispatch.getAdapter() != null) {
                                String[] motivodespacho = spn_reason_dispatch.getSelectedItem().toString().split("-");
                                reasondispatch_id = motivodespacho[0];
                                reasondispatch = motivodespacho[1];
                            } else {
                                reasondispatch_id = "";
                                reasondispatch = "";
                            }
                            StatusDispatchEntity statusDispatchEntity = new StatusDispatchEntity();
                            statusDispatchEntity.compania_id = ObjUsuario.compania_id;
                            statusDispatchEntity.fuerzatrabajo_id = ObjUsuario.fuerzatrabajo_id;
                            statusDispatchEntity.usuario_id = ObjUsuario.usuario_id;
                            statusDispatchEntity.Delivered = typedispatch_id;
                            statusDispatchEntity.ReturnReason = reasondispatch_id;
                            statusDispatchEntity.entrega_id = entrega_id;
                            statusDispatchEntity.cliente_id = cliente_id;
                            statusDispatchEntity.factura_id = factura_id;
                            statusDispatchEntity.chkrecibido = "0";
                            statusDispatchEntity.Comments = et_comentario.getText().toString();
                            //statusDispatchEntity.foto = encoded;
                            //statusDispatchEntity.foto = entrega_id+"_L.JPG";
                            statusDispatchEntity.foto = filelocal.toString();
                            statusDispatchEntity.fecha_registro = FormatFecha.format(date);
                            statusDispatchEntity.hora_registro = dateFormathora.format(date);
                            //statusDispatchEntity.fotoGuia = encoded2;
                            //statusDispatchEntity.fotoGuia = entrega_id+"_G.JPG";
                            statusDispatchEntity.PhotoDocument = fileguia.toString();
                            statusDispatchEntity.latitud = String.valueOf(latitude);
                            statusDispatchEntity.longitud = String.valueOf(longitude);
                            //statusDispatchEntity.latitud = encoded;
                            //statusDispatchEntity.longitud = encoded2;
                            statusDispatchEntity.cliente = cliente;
                            statusDispatchEntity.entrega = String.valueOf(entrega);
                            statusDispatchEntity.factura = String.valueOf(factura);
                            statusDispatchEntity.typedispatch = typedispatch;
                            statusDispatchEntity.reasondispatch = reasondispatch;
                            statusDispatchEntity.DocEntry = control_id;
                            statusDispatchEntity.LineId = item_id;
                            statusDispatchEntity.Address = address;
                            statusDispatchEntity.checkintime = "0";
                            statusDispatchEntity.checkouttime = "0";
                            statusDispatchEntity.chk_timestatus = "0";
                            statusDispatchEntity.fuerzatrabajo = SesionEntity.nombrefuerzadetrabajo;
                            listStatusDispatchEntity.add(statusDispatchEntity);
                            statusDispatchSQLite.addStatusDispatch(listStatusDispatchEntity);
                            AppExecutors executor=new AppExecutors();
                            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                            //statusDispatchRepository.statusDispatchSend(getContext()).observe(getActivity(), data -> {
                            statusDispatchRepository.statusDispatchListSend(getContext(),executor.diskIO()).observe(getActivity(), data -> {
                                Log.e("REOS", "statusDispatchRepository-->statusDispatchSend-->resultdata" + data);
                            });
                            /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                            //statusDispatchRepository.statusDispatchSend(getContext()).observe(getActivity(), data -> {
                            statusDispatchRepository.statusDispatchSendPhoto(getContext(),executor.diskIO()).observe(getActivity(), data -> {
                                Log.e("REOS", "statusDispatchRepository-->statusDispatchSend-->resultdata" + data);
                            });


                            ////Visitas
                            FormulasController formulasController = new FormulasController(getContext());
                            VisitaSQLiteEntity visitaNativa = new VisitaSQLiteEntity();

                            visitaNativa.setCardCode(cliente_id);
                            visitaNativa.setAddress(direccion_id);
                            visitaNativa.setTerritory("");
                            visitaNativa.setType("13");
                            visitaNativa.setObservation("Actualizacion Despacho realizada a la entrega: " + entrega_id);
                            visitaNativa.setLatitude("" + latitude);
                            visitaNativa.setLongitude("" + longitude);
                            visitaNativa.setStatusRoute("N");
                            visitaNativa.setMobileID(entrega_id);
                            visitaNativa.setAmount("0");
                            formulasController.RegistraVisita(visitaNativa, getActivity(), "0");

                            Toast.makeText(getContext(), "Despacho Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Un Momento Por favor Calculando Coordenadas... Intente Guardar Nuevamente!!!", Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        if (imgBitmap == null && imgBitmap2 == null) {
                            Toast.makeText(getContext(), "Debe tomar Foto de la Guia y el Local!!!", Toast.LENGTH_SHORT).show();
                        } else if (imgBitmap == null && imgBitmap2 != null) {
                            Toast.makeText(getContext(), "Debe tomar Foto del Local!!!", Toast.LENGTH_SHORT).show();
                        } else if (imgBitmap != null && imgBitmap2 == null) {
                            Toast.makeText(getContext(), "Debe tomar Foto de la Guia!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }catch (Exception e)
                {
                    Toast.makeText(getContext(), "No se pudo grabar el despacho error:"+e.toString(), Toast.LENGTH_SHORT).show();
                }
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

    private File createImageFile(String entrega_id,String type) throws IOException {

        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = entrega_id+"_"+type;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg",storageDir);
        if(type.equals("G"))
        {
            mCurrentPhotoPathG=image.getAbsolutePath();
        }
        else if(type.equals("L"))
        {
            mCurrentPhotoPathL = image.getAbsolutePath();
        }
        return image;
    }

    private String getDate() throws IOException {

        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());

        return date;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}

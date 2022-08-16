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
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.vistony.salesforce.R;

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
    String Entrega_id="";
    File filelocal,fileguia;
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
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
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
        }
        ArrayAdapter<String> adapterdelivery = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,sppdelivery);
        spn_referral_guide.setAdapter(adapterdelivery);
        adapterdelivery.notifyDataSetChanged();

        listTypeDispatch=typeDispatchSQLite.getTypeDispatch();
        for(int i=0;i<listTypeDispatch.size();i++)
        {
            spptypeDispatch.add(listTypeDispatch.get(i).getTypedispatch_id()+"-"+listTypeDispatch.get(i).getTypedispatch());
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
                    filelocal= imageCameraController.SaveImageStatusDispatch (getContext(),bitmap2,Entrega_id,"L");

                } catch (IOException e){
                    e.printStackTrace();
                }
                imgBitmap=bitmap2;
                imageViewPhoto.setImageBitmap(bitmap2);

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
                    fileguia=imageCameraController.SaveImageStatusDispatch (getContext(),bitmap2,Entrega_id,"G");

                } catch (IOException e){
                    e.printStackTrace();
                }
                imgBitmap2=bitmap2;
                imageViewPhoto2.setImageBitmap(bitmap2);


                //Bundle extras = result.getData().getExtras();
                //imgBitmap2 = (Bitmap) extras.get("data");
                //imageViewPhoto2.setImageBitmap(imgBitmap2);
            }

        });
       /* floatingButtonTakePhoto.setOnClickListener(data -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                someActivityResultLauncher.launch(intent);
            }
        });*/
        //imv_historic_status_dispatch_delivery.setEnabled(true);
        //imv_historic_status_dispatch_photo.setEnabled(true);
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
                        //startActivityForResult(intent,0);
                            photoFile = createImageFile(Entrega_id,"G");

                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
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

        }});
        imv_historic_status_dispatch_photo.setOnClickListener(new View.OnClickListener() {
        //imageViewPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Intent intent = new Intent(MediaStore.EXTRA);
                //if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                 //   someActivityResultLauncher.launch(intent);
                //}
                try {
                        Log.e("REOS","statusDispatchRepository-->FotoLocal-->Inicia");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // Crea el File
                        File photoFile = null;

                            photoFile = createImageFile(Entrega_id,"L");

                        if (photoFile != null) {
                            Log.e("REOS","statusDispatchRepository-->FotoLocal-->photoFile != null");
                            Uri photoURI = FileProvider.getUriForFile(getContext(),"com.vistony.salesforce.peru" , photoFile);
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
            }});

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encoded = null,encoded2 = null;
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
                }
                if(imgBitmap != null||imgBitmap2 != null) {
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
                    String entrega_id = "", factura_id = "", entrega = "", factura = "", typedispatch_id = "", typedispatch = "", reasondispatch_id = "", reasondispatch = ""
                            ,direccion_id="";
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
                    if(spn_reason_dispatch.getAdapter()!=null)
                    {
                        String[] motivodespacho = spn_reason_dispatch.getSelectedItem().toString().split("-");
                        reasondispatch_id = motivodespacho[0];
                        reasondispatch = motivodespacho[1];
                    }
                    else {
                        reasondispatch_id="";
                        reasondispatch="";
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
                    statusDispatchEntity.DocEntry=control_id;
                    statusDispatchEntity.LineId=item_id;
                    statusDispatchEntity.Address=address;
                    statusDispatchEntity.checkintime="0";
                    statusDispatchEntity.checkouttime="0";
                    statusDispatchEntity.chk_timestatus="0";
                    listStatusDispatchEntity.add(statusDispatchEntity);
                    statusDispatchSQLite.addStatusDispatch(listStatusDispatchEntity);

                    /////////////////////ENVIAR RECIBOS PENDIENTES SIN DEPOSITO\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
                    statusDispatchRepository.statusDispatchSend(getContext()).observe(getActivity(), data -> {
                        Log.e("REOS","statusDispatchRepository-->statusDispatchSend-->resultdata"+data);
                    });


                    ////Visitas
                    FormulasController formulasController=new FormulasController(getContext());
                    VisitaSQLiteEntity visitaNativa=new VisitaSQLiteEntity();

                    visitaNativa.setCardCode(cliente_id);
                    visitaNativa.setAddress(direccion_id);
                    visitaNativa.setTerritory("");
                    visitaNativa.setType("04");
                    visitaNativa.setObservation("Actualizacion Despacho realizada a la entrega: "+entrega_id);
                    visitaNativa.setLatitude(""+latitude);
                    visitaNativa.setLongitude(""+longitude);
                    visitaNativa.setStatusRoute("N");
                    visitaNativa.setMobileID(entrega_id);
                    visitaNativa.setAmount("0");
                    formulasController.RegistraVisita(visitaNativa,getActivity(),"0");

                    Toast.makeText(getContext(), "Despacho Actualizado Correctamente", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else
                    {
                        Toast.makeText(getContext(), "Debe Tomar las Imagenes Solicitadas", Toast.LENGTH_SHORT).show();
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
}

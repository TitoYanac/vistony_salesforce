package com.vistony.salesforce.Controller.Utilitario;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.SesionEntity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ImageCameraController {
    private Context TheThis;
    private String NameOfFolder = "/RECIBOS";

    public void SaveImage(Context context, Bitmap ImageToSave) {
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir,SesionEntity.imagen+SesionEntity.compania_id+SesionEntity.fuerzatrabajo_id+CurrentDateAndTime + ".JPG");

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 35, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }

        catch(FileNotFoundException e) {
            UnableToSave();
        }
        catch(IOException e) {
            UnableToSave();
        }
    }

    public File SaveImageStatusDispatch(Context context, Bitmap ImageToSave,String Entrega_id,String type) {
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir,Entrega_id+"_"+type+".JPG");

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 35, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }

        catch(FileNotFoundException e) {
            UnableToSave();
        }
        catch(IOException e) {
            UnableToSave();
        }

        return file;
    }

    private void MakeSureFileWasCreatedThenMakeAvabile(File file){
        MediaScannerConnection.scanFile(TheThis,
            new String[] { file.toString() } ,null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                  //  Toast.makeText(TheThis, "Album actualizado", Toast.LENGTH_SHORT).show();
                }
            });
    }

    private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd" +"-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    private void UnableToSave() {
        Toast.makeText(TheThis, "¡No se ha podido guardar la imagen!", Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, "Imagen guardada en la galería.", Toast.LENGTH_SHORT).show();
    }

    public byte[] getImageSDtoByte(Context context,String Entrega_id) {
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + "/"+Entrega_id;


        Bitmap bitmap = BitmapFactory.decodeFile(file_path);
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        byte[] bitmapdata = blob.toByteArray();

        Log.e("REOS","ImageCameraController-getImageSDtoByte-file_path-"+file_path);

        return bitmapdata;
    }

    public void OpenImageDispacth(Context context,String Entrega_id)
    {
        try {
            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
            //        +NOMBRE_DIRECTORIO+File.separator+recibo+".pdf");
            TheThis = context;
            //String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder + "/"+Entrega_id;
            ///File file = new File(Entrega_id);
            /*File file = new File("file://storage/emulated/0/RECIBOS/220156850_G.jpg");
            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + File.separator
            //      +NameOfFolder+File.separator+Entrega_id);
            //File file = new File(Environment.getExternalStorageDirectory()+File.separator+ NameOfFolder +File.separator+Entrega_id);
            Uri  excelPath = FileProvider.getUriForFile(context, context.getPackageName(), file);

            Intent target = new Intent(Intent.ACTION_VIEW);
            //Intent target = new Intent(Intent.ACTION_GET_CONTENT);
            target.setDataAndType(excelPath,"image/*");
            target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(target);*/


            //File file=new File(Environment.getExternalStorageDirectory()+"/directoryname/"+filename);
            //---------------------------Generacion de Foto sin Botones de Galeria---------------------------------
            File file = new File(Entrega_id);
            //Uri path= FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID ,file);
            Uri  path = FileProvider.getUriForFile(context, context.getPackageName(), file);
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path,"image/*");
            intent.setFlags(FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION); //must for reading data from directory
            context.startActivity(intent);
            //-------------------------------------------------------------------
            Log.e("REOS","ImageCameraController-OpenImageDispacth-e-"+file.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("REOS","ImageCameraController-OpenImageDispacth-e-"+e.toString());
            Toast.makeText(context, "Es necesario que instales algun visor de PDF", Toast.LENGTH_SHORT).show();
        }

    }
}


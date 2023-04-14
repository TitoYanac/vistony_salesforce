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
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ImageCameraController {
    private Context TheThis;
    static private String NameOfFolder = "/RECIBOS";

    /*
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
            //ImageToSave.compress(Bitmap.CompressFormat.JPEG, 10, fOut);
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 35, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }
        catch(FileNotFoundException e) {
            UnableToSave();
            Log.e("REOS","ImageCameraController-SaveImage-FileNotFoundException-e:"+e.toString());
        }
        catch(IOException e) {
            UnableToSave();
            Log.e("REOS","ImageCameraController-SaveImage-IOException-e:"+e.toString());
        }
    }*/
    public File SaveImage(Context context, Bitmap ImageToSave) {
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        //File dir = new File(file_path);
        File file=null;
        try {
            file = crearFichero(SesionEntity.imagen+SesionEntity.compania_id+SesionEntity.fuerzatrabajo_id+CurrentDateAndTime + ".JPG");
            Log.e("REOS","ImageCameraController-->SaveImage-->file"+file.toString());
            //File f = crearFichero(recibo+".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            //El elegir el PNG no obliga a trabajar con filtro, en cambio el JPEG si obliga
            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 10, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }
        catch(FileNotFoundException e) {
            UnableToSave(e.toString());
        }
        catch(IOException ioexception) {
            UnableToSave(ioexception.toString());
        }
        catch(Exception exception) {
            UnableToSave(exception.toString());
        }
        return file;
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
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

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 10, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }

        catch(FileNotFoundException e) {
            UnableToSave(e.toString());
        }
        catch(IOException ioexception) {
            UnableToSave(ioexception.toString());
        }
        catch(Exception exception) {
            UnableToSave(exception.toString());
        }

        return file;
    }

    public File SaveImage(Context context, Bitmap ImageToSave,String namefile) {
        TheThis = context;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + NameOfFolder;
        String CurrentDateAndTime = getCurrentDateAndTime();
        File dir = new File(file_path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir,namefile+".JPG");

        try {
            FileOutputStream fOut = new FileOutputStream(file);

            ImageToSave.compress(Bitmap.CompressFormat.JPEG, 10, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();
        }

        catch(FileNotFoundException e) {
            UnableToSave(e.toString());
        }
        catch(IOException ioexception) {
            UnableToSave(ioexception.toString());
        }
        catch(Exception exception) {
            UnableToSave(exception.toString());
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

    private void UnableToSave(String error) {
        Toast.makeText(TheThis, TheThis.getResources().getString(R.string.dont_sava_image) +error, Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, TheThis.getResources().getString(R.string.image_save_galery), Toast.LENGTH_SHORT).show();
    }

    public byte[] getImageSDtoByte(Context context,String file_path) {
        TheThis = context;
        Log.e("REOS","ImageCameraController-getImageSDtoByte-file_path-Ingreso"+file_path);
        byte[] bitmapdata=null;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file_path);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10 /* Ignored for PNGs */, blob);
            bitmapdata = blob.toByteArray();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Log.e("REOS","ImageCameraController-getImageSDtoByte-file_path-Salio"+file_path);

        return bitmapdata;
    }

    public String getBASE64(String file_path) {
        String encoded="";
        Log.e("REOS","ImageCameraController-getImageSDtoByte-file_path-Ingreso"+file_path);
        byte[] bitmapdata=null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap bitmap = BitmapFactory.decodeFile(file_path,options);
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            //bitmap.compress(Bitmap.CompressFormat.JPEG, 10/* Ignored for PNGs */, blob);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30/* Ignored for PNGs */, blob);
            bitmapdata = blob.toByteArray();
            encoded = Base64.encodeToString(bitmapdata, Base64.DEFAULT);
            //encoded = Base64.encodeToString(convertBitmapToByteArray(bitmap), Base64.DEFAULT);
        }catch (Exception e){
            System.out.println(e.getMessage());
            encoded="";
        }
        Log.e("REOS","ImageCameraController-getImageSDtoByte-file_path-Salio"+file_path);

        return encoded;
    }
    public static byte[] convertBitmapToByteArray(Bitmap bitmap){
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byteBuffer.rewind();
        return byteBuffer.array();
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

    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            //ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NOMBRE_DIRECTORIO);
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),NameOfFolder);
            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        } else {
        }
        return ruta;
    }


}


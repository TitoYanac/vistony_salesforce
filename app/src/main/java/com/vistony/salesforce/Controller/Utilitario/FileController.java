package com.vistony.salesforce.Controller.Utilitario;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.Toast;

import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileController {
    private Context TheThis;
    static private String NameOfFolder = "/DB";

    public void CopyDbcobranzas(Context context) throws IOException {
        TheThis=context;
        try {
            /*FileOutputStream fOut = new FileOutputStream(file);
            //ImageToSave.compress(Bitmap.CompressFormat.JPEG, 10, fOut);
            fOut.flush();
            fOut.close();
            MakeSureFileWasCreatedThenMakeAvabile(file);
            AbleToSave();*/
            File file = new File(Environment.getDataDirectory(),"/data/"+context.getPackageName()+"/databases/dbcobranzas");
            File fileout = crearFichero("dbcobranzas");
            FileInputStream in = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream(fileout);

            int n=0,c;
            System.out.print ("\nCopiando ...");
            while( (c = in.read()) != -1){
                out.write(c);
                n++;
            }
            in.close();
            out.close();
            System.out.print ("\nSe han copiado "+n+" caracteres\n");
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
    }

    private void UnableToSave(String error) {
        Toast.makeText(TheThis,TheThis.getResources().getText(R.string.dont_save_file)+  error, Toast.LENGTH_SHORT).show();
    }

    private void AbleToSave() {
        Toast.makeText(TheThis, TheThis.getResources().getString(R.string.file_register_sucessful), Toast.LENGTH_SHORT).show();
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null)
            fichero = new File(ruta, nombreFichero);
        return fichero;
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

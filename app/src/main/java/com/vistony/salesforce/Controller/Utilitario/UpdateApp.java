package com.vistony.salesforce.Controller.Utilitario;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.omega_r.libs.OmegaCenterIconButton;
import com.vistony.salesforce.Controller.Retrofit.Api;
import com.vistony.salesforce.Controller.Retrofit.Config;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateApp {
    public UpdateApp(OmegaCenterIconButton btnlogin, String nameFile, Context context){
        btnlogin.setText("Descargando...");

        File  ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"SalesForce");
        if(!ruta.exists()) {
            ruta.mkdirs();
        }

        Config.getClient().create(Api.class).getNewApk(nameFile).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    InputStream apkFile =  response.body().byteStream();
                    btnlogin.setText("Actualizando...");
                    try {

                        creaArchivo(Environment.getExternalStorageDirectory()+ File.separator+"Download"+File.separator+"SalesForce"+File.separator+nameFile+".apk",apkFile);
                        intallApk(nameFile,context);

                        } catch (Exception e) {
                             e.printStackTrace();
                            btnlogin.setText("Error al instalar...");
                            btnlogin.setEnabled(true);
                            btnlogin.setClickable(true);
                        }

                }else{
                     btnlogin.setText("Error Desconocido "+response.code());
                     btnlogin.setEnabled(true);
                     btnlogin.setClickable(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                btnlogin.setText("Error en red");
                btnlogin.setEnabled(true);
                btnlogin.setClickable(true);
            }
        });
    }

    private void creaArchivo(String ruta, InputStream is)throws IOException {
        OutputStream os = new BufferedOutputStream(new FileOutputStream(new File(ruta)));
        byte[] chunk = new byte[1024 * 4];
        int bytesLeidos = 0;
        while ( (bytesLeidos = is.read(chunk)) > 0) {
            os.write(chunk, 0, bytesLeidos);
        }
        os.close();
    }

    private void intallApk(String nameFile,Context context){
        File imagen = new File(Environment.getExternalStorageDirectory() + "/download/SalesForce/" +nameFile+".apk");

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
        Uri data;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            data = FileProvider.getUriForFile(context, context.getPackageName(),imagen );
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                context.grantUriPermission(packageName, data, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }else {
            data = Uri.fromFile(imagen);
        }

        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }
}

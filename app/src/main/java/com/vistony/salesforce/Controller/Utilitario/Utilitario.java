package com.vistony.salesforce.Controller.Utilitario;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.Retrofit.LoginRepository;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.LoginView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Utilitario {
    private static int temp = 0;
    public static void disabledButtton(Button btn){
        btn.setClickable(false);
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#FF686A6F"));
    }
    public static void disabledImageButtton(ImageButton btn,Context context){
        Drawable borderdrawable;
        borderdrawable= ResourcesCompat.getDrawable(context.getResources() , R.drawable.custom_border_button_grey, null);
        btn.setClickable(false);
        btn.setEnabled(false);
        btn.setBackground (borderdrawable);

    }
    public static void enableImageButtton(ImageButton btn,Context context){
        Drawable borderdrawable;
        borderdrawable= ResourcesCompat.getDrawable(context.getResources() , R.drawable.custom_border_button_red, null);
        btn.setClickable(true);
        btn.setEnabled(true);
        btn.setBackground (borderdrawable);
    }
    public static void enableTextView(TextView tv){
        tv.setClickable(true);
        tv.setEnabled(true);
        tv.setTextColor(Color.parseColor("#000000"));
    }
    public static void disabledSpinner(Spinner spn){
        spn.setEnabled(false);
        spn.setClickable(false);
    }

    public static void disabledEditText(EditText editText){
        editText.setEnabled(false);
        editText.setClickable(false);
    }
    public static void disabledTextView(TextView tv){
        tv.setClickable(false);
        tv.setEnabled(false);
        tv.setTextColor(Color.parseColor("#FF686A6F"));
    }
    public static void disabledCheckBox(CheckBox chk){
        chk.setEnabled(false);
        chk.setClickable(false);
    }

    public static String getVersion(Context context){
       try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pInfo.versionName;

        }catch(Exception e){
            return "NA";
        }
    }

    public static String getDateTime(){
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        Date date2 = new Date();
        return dateFormat2.format(date2);
    }

    public static String obtenerImei(SharedPreferences statusImei, Context context, LoginRepository loginRepository) {

        String Imei=statusImei.getString("imei", BuildConfig.IMEI_DEFAULT);

        if(Imei.equals("")){

            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_imei_dialog);

            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);

            final EditText textDImei = dialog.findViewById(R.id.textEditImei);

            ImageView image =dialog.findViewById(R.id.image);
            image.setImageResource(R.mipmap.logo_circulo);

            final Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
            final Button dialogButtonExit =  dialog.findViewById(R.id.dialogButtonCancel);

            dialogButton.setText("INGRESAR");
            dialogButtonExit.setText("VER CÓDIGO");

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            image.setBackground(new ColorDrawable(Color.TRANSPARENT));

            dialog.show();

            dialogButtonExit.setOnClickListener(v -> {
                Intent i = new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            });

            dialogButton.setOnClickListener(v -> {
                if(textDImei.getText().length()==15){
                    if(temp==1){
                        SharedPreferences.Editor editor = statusImei.edit();
                        editor.putString("imei",textDImei.getText().toString());
                        editor.apply();

                        dialog.dismiss();
                        Toast.makeText(context, "Código registrado", Toast.LENGTH_LONG).show();
                        loginRepository.getAndLoadUsers(textDImei.getText().toString(),context);

                    }else{
                        Toast.makeText(context, "Vuelva a presionar guardar para ingresar...", Toast.LENGTH_SHORT).show();
                        temp=1;
                    }
                }else{
                    Toast.makeText(context, "Error al escribir el código de activación...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        return Imei;
    }

    /* como se convierte el mapa a json, los nulos no se envian y no se necita esto
    public static boolean validationNotNull(HashMap hashMap){
        boolean status=true;

        for ( Object key : hashMap.keySet() ) {
            if(hashMap.get(key)==null || hashMap.get(key).toString().isEmpty()){
                status=false;
                break;
            }
        }
        return status;
    }
*/

}

package com.vistony.salesforce;

import android.graphics.Color;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.HashMap;

public class Utilitario {

    public static void disabledButtton(Button btn){
        btn.setClickable(false);
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#FF686A6F"));
    }
    public static void disabledImageButtton(ImageButton btn){
        btn.setClickable(false);
        btn.setEnabled(false);
        btn.setBackgroundColor(Color.parseColor("#FF686A6F"));
    }

    public static void disabledSpinner(Spinner spn){
        spn.setEnabled(false);
        spn.setClickable(false);
    }

    public static void disabledEditText(EditText editText){
        editText.setEnabled(false);
        editText.setClickable(false);
    }

    public static void disabledCheckBox(CheckBox chk){
        chk.setEnabled(false);
        chk.setClickable(false);
    }

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


}

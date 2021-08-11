package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
/*import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;*/
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.R;
import com.vistony.salesforce.View.CobranzaDetalleView;

public class CobranzaDetalleDialogController extends DialogFragment {

    TextView et_comentario_recibo;
    String comentario;
    CobranzaDetalleView cobranzaDetalleView;
    private FragmentManager fragmentManager;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_input_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("INGRESA UN COMENTARIO");

        final EditText textMsj = dialog.findViewById(R.id.textEditViewMsj);

        textMsj.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL;
            }
            @Override
            protected char[] getAcceptedChars() {
                char[] ac = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ -%".toCharArray();
                return ac;
            }
        });


        textMsj.setSingleLine(true);  //add this
        textMsj.setLines(4);
        textMsj.setMaxLines(5);
        textMsj.setGravity(Gravity.LEFT | Gravity.TOP);
        textMsj.setHorizontalScrollBarEnabled(false);

        //textMsj.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonExit = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        // if button is clicked, close the custom dialog
        dialogButton.setText("GUARDAR");
        dialogButtonExit.setText("CANCELAR");


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                comentario=textMsj.getText().toString();


                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //transaction.replace(android.R.id.content, fragment);
                //transaction.addToBackStack("DiscoverPage");


                transaction.add(R.id.content_menu_view, cobranzaDetalleView.newInstanciaComentario(comentario));

                Toast.makeText(getContext(), "Comentario agregado Exitosamente", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        return  dialog;
    }
    public int boolToInt(boolean b)
    {
        return b ? 1 : 0;
    }

}

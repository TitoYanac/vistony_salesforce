package com.vistony.salesforce.Controller.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.vistony.salesforce.R;


public class SincronizarBDDialogController
        extends DialogFragment
{

    ImageView imvsincronizar;
    TextView tv_fechainiciosincronizar;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_sincronizar_bd, null);

        imvsincronizar = (ImageView) view.findViewById(R.id.imvsincronizar);
        tv_fechainiciosincronizar = (TextView) view.findViewById(R.id.tv_fechainiciosincronizar);



        builder.setView(view)
                .setTitle("Modificar Datos de Deposito:")
                //.setIcon(android.R.drawable.btn_dialog)
                .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }
                );
        return builder.create();
    }
}

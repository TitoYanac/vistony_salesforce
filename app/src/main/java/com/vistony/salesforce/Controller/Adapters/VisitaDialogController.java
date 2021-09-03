package com.vistony.salesforce.Controller.Adapters;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.GPSController;
import com.vistony.salesforce.Dao.SQLite.VisitaSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;

public class VisitaDialogController extends DialogFragment {
    String cliente_id,direccion_id,zona_id;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;

    public VisitaDialogController(Object Objetos)
    {
        ArrayList<ListaClienteCabeceraEntity> listaObjetos=(ArrayList<ListaClienteCabeceraEntity>) Objetos;
        for(int i=0;i<listaObjetos.size();i++)
        {
            cliente_id=listaObjetos.get(i).getCliente_id();
            direccion_id=listaObjetos.get(i).getDomembarque_id();
            zona_id=listaObjetos.get(i).getZona_id();
        }

    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_visita_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("DETALLA TU VISITA");
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            Log.e("REOS","MenuAccionView: No tiene ACCESS_FINE_LOCATION ");
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_LOCATION);

        } else {
            Log.e("REOS","MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            // We have already permission to use the location
            gpsController =  new GPSController(getContext());
            mLocation = gpsController.getLocation(mLocation);
            latitude = mLocation.getLatitude();
            longitude= mLocation.getLongitude();
        }
        //final EditText textMotivo = dialog.findViewById(R.id.textEditMotivo);
        //textMotivo.setSingleLine(false);
        //textMotivo.setGravity(Gravity.LEFT | Gravity.TOP);
        //textMotivo.setHorizontalScrollBarEnabled(false);

        final EditText textDescargo = dialog.findViewById(R.id.et_observaciones_visita);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        //Spinner spn_tipo_visita= dialog.findViewById(R.id.spn_tipo_visita);
        Spinner spn_motivo_visita= dialog.findViewById(R.id.spn_motivo_visita);

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

        dialogButton.setOnClickListener(v -> {
           if(textDescargo.getText().length()>0){

               FormulasController formulasController=new FormulasController(getContext());

               VisitaSQLiteEntity visitaNativa=new VisitaSQLiteEntity();

               visitaNativa.setCardCode(cliente_id);
               visitaNativa.setAddress(direccion_id);
               visitaNativa.setTerritory(zona_id);
               visitaNativa.setType(""+(spn_motivo_visita.getSelectedItemPosition()+1));
               visitaNativa.setObservation(textDescargo.getText().toString());
               visitaNativa.setLatitude(""+latitude);
               visitaNativa.setLongitude(""+longitude);

               formulasController.RegistraVisita(visitaNativa,getActivity());

                Toast.makeText(getContext(), "Visita registrada...", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }else{
               textDescargo.setError("Es necesario llenar este campo");
            }
        });

        return  dialog;
    }


}

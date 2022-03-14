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
import android.widget.ArrayAdapter;
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
import com.vistony.salesforce.Dao.SQLite.MotivoVisitaSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.MotivoVisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.VisitaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class VisitaDialogController extends DialogFragment {
    String cliente_id,direccion_id,zona_id,chkruta;
    private GPSController gpsController;
    private Location mLocation;
    double latitude, longitude;
    private static final int REQUEST_PERMISSION_LOCATION = 255;
    List<String> values;
    ArrayAdapter<String> combomotivovisita;

    public VisitaDialogController(Object Objetos)
    {
        ArrayList<ListaClienteCabeceraEntity> listaObjetos=(ArrayList<ListaClienteCabeceraEntity>) Objetos;
        for(int i=0;i<listaObjetos.size();i++)
        {
            cliente_id=listaObjetos.get(i).getCliente_id();
            direccion_id=listaObjetos.get(i).getDomembarque_id();
            zona_id=listaObjetos.get(i).getZona_id();
            chkruta=listaObjetos.get(i).getChk_ruta();
        }

    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        ArrayList<MotivoVisitaSQLiteEntity> listamotivovisitasqliteentity=new ArrayList<>();
        MotivoVisitaSQLiteDao motivoVisitaSQLiteDao=new MotivoVisitaSQLiteDao(getContext());
        values=new ArrayList<>();

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
            try {


                // We have already permission to use the location
                gpsController = new GPSController(getContext());
                mLocation = gpsController.getLocation(mLocation);
                latitude = mLocation.getLatitude();
                longitude = mLocation.getLongitude();
            }catch (Exception e)
            {
                Log.e("REOS","MenuAccionView: si tiene ACCESS_FINE_LOCATION ");
            }
        }
        //final EditText textMotivo = dialog.findViewById(R.id.textEditMotivo);
        //textMotivo.setSingleLine(false);
        //textMotivo.setGravity(Gravity.LEFT | Gravity.TOP);
        //textMotivo.setHorizontalScrollBarEnabled(false);
        listamotivovisitasqliteentity=motivoVisitaSQLiteDao.ObtenerMotivoVisita(SesionEntity.compania_id,SesionEntity.fuerzatrabajo_id);
        for(int j=0;j<listamotivovisitasqliteentity.size();j++)
        {
            Log.e("REOS","VisitaDialogController-onCreateDialog-listamotivovisitasqliteentity.get(j).getMotivovisita_id():"+listamotivovisitasqliteentity.get(j).getType());
            //valores[i]=String.valueOf(i+1);
            if(!listamotivovisitasqliteentity.get(j).getCode().equals("01")&&!listamotivovisitasqliteentity.get(j).getCode().equals("02"))
            {
                Log.e("REOS","VisitaDialogController-onCreateDialog-listamotivovisitasqliteentity.get(j).getMotivovisita_id()-dentrodelif:"+listamotivovisitasqliteentity.get(j).getType());
                values.add(listamotivovisitasqliteentity.get(j).getCode() + "-" + listamotivovisitasqliteentity.get(j).getName());
            }
        }
        combomotivovisita=new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,values);

        final EditText textDescargo = dialog.findViewById(R.id.et_observaciones_visita);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);

        //Spinner spn_tipo_visita= dialog.findViewById(R.id.spn_tipo_visita);
        Spinner spn_motivo_visita= dialog.findViewById(R.id.spn_motivo_visita);
        spn_motivo_visita.setAdapter(combomotivovisita);
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
               String motivo=spn_motivo_visita.getSelectedItem().toString();
               String[] motivos = motivo.split("-");
               String type,name;
               type=motivos[0];
               name=motivos[1];
               FormulasController formulasController=new FormulasController(getContext());

               VisitaSQLiteEntity visitaNativa=new VisitaSQLiteEntity();

               visitaNativa.setCardCode(cliente_id);
               visitaNativa.setAddress(direccion_id);
               visitaNativa.setTerritory(zona_id);
               visitaNativa.setType(type);
               visitaNativa.setObservation(textDescargo.getText().toString());
               visitaNativa.setLatitude(""+latitude);
               visitaNativa.setLongitude(""+longitude);
               visitaNativa.setStatusRoute(chkruta);
               visitaNativa.setMobileID("0");
               visitaNativa.setAmount("0");
               formulasController.RegistraVisita(visitaNativa,getActivity(),"0");

                Toast.makeText(getContext(), "Visita registrada...", Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }else{
               textDescargo.setError("Es necesario llenar este campo");
            }
        });

        return  dialog;
    }


}

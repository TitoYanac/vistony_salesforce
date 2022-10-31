package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.LeadAddressEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.AgenciaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaConsultaGeolocalizacionAdapter  extends ArrayAdapter<LeadAddressEntity> {

    public static ArrayList<ListaAgenciaEntity> araylistaAgenciaEntity;
    private android.content.Context Context;
    LayoutInflater inflater;
    ArrayList<AgenciaSQLiteEntity> listaAgenciaSQLiteEntity=new ArrayList<>();
    private FragmentManager fragmentManager;
    public AgenciaView agenciaView;

    public ListaConsultaGeolocalizacionAdapter(android.content.Context context, List<LeadAddressEntity> objects) {

        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaAgenciaEntity= new ArrayList <ListaAgenciaEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaAgenciaAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_agencia,
                    parent,
                    false);

            holder = new ListaAgenciaAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_agencia_id = (TextView) convertView.findViewById(R.id.tv_agencia_id);
            holder.tv_agencia = (TextView) convertView.findViewById(R.id.tv_agencia);
//            holder.tv_ubigeo = (TextView) convertView.findViewById(R.id.tv_ubigeo);
            holder.relativeListaAgencia=convertView.findViewById(R.id.relativeListaAgencia);
            convertView.setTag(holder);
        } else {
            holder = (ListaAgenciaAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final LeadAddressEntity lead = getItem(position);

        // Setup.
       /* holder.tv_agencia_id.setText(lead.getAgencia_id());
        holder.tv_agencia.setText(lead.getAgencia());
        //holder.tv_ubigeo.setText(lead.getUbigeo());
        holder.relativeListaAgencia.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {

                                                               AgenciaSQLiteEntity agenciaSQLiteEntity=new AgenciaSQLiteEntity();
                                                               agenciaSQLiteEntity.agencia_id = lead.getAgencia_id().toString();
                                                               agenciaSQLiteEntity.agencia = lead.getAgencia().toString();
                                                               agenciaSQLiteEntity.ubigeo_id = lead.getUbigeo().toString();
                                                               listaAgenciaSQLiteEntity.add(agenciaSQLiteEntity);

                                                               fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                               FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                               transaction.add(R.id.content_menu_view, agenciaView.newInstancia(listaAgenciaSQLiteEntity));
                                                           }
                                                       }

        );*/
        return convertView;
    }




    static class ViewHolder {
        TextView tv_agencia_id;
        TextView tv_agencia;
        //TextView tv_ubigeo;
        RelativeLayout relativeListaAgencia;


    }

}

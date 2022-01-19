package com.vistony.salesforce.Controller.Adapters;

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
import com.vistony.salesforce.Entity.Adapters.ListaListadoPromocionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ListadoPromocionView;

import java.util.ArrayList;
import java.util.List;

public class ListaListadoPromocionAdapter extends ArrayAdapter<ListaListadoPromocionEntity> {

    public static ArrayList<ListaAgenciaEntity> araylistaAgenciaEntity;
    private android.content.Context Context;
    private List<ListaAgenciaEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaAgenciaEntity> arrayList;
    ArrayList<ListaListadoPromocionEntity> arraylistaListadoPromocionEntity=new ArrayList<>();
    private FragmentManager fragmentManager;
    public ListadoPromocionView listadoPromocionView;

    public ListaListadoPromocionAdapter(android.content.Context context, List<ListaListadoPromocionEntity> objects) {

        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaAgenciaEntity>();

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaAgenciaEntity= new ArrayList <ListaAgenciaEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaListadoPromocionAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_listado_promocion,
                    parent,
                    false);

            holder = new ListaListadoPromocionAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_lista_precio = (TextView) convertView.findViewById(R.id.tv_lista_precio);
            holder.relativeListaListadoPromocion=convertView.findViewById(R.id.relativeListaListadoPromocion);
            convertView.setTag(holder);
        } else {
            holder = (ListaListadoPromocionAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaListadoPromocionEntity lead = getItem(position);

        // Setup.
        holder.tv_id.setText(lead.getId());
        holder.tv_lista_precio.setText(lead.getLista_precio());
        holder.relativeListaListadoPromocion.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               try {

                                                                   ListaListadoPromocionEntity listaListadoPromocionEntity = new ListaListadoPromocionEntity();
                                                                   listaListadoPromocionEntity.id = lead.getId().toString();
                                                                   listaListadoPromocionEntity.lista_precio = lead.getLista_precio().toString();
                                                                   arraylistaListadoPromocionEntity.add(listaListadoPromocionEntity);

                                                                   fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                                   FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                                   transaction.add(R.id.content_menu_view, listadoPromocionView.newInstanceEnviaListaPromocion(arraylistaListadoPromocionEntity));
                                                               }catch (Exception e)
                                                               {
                                                                   e.printStackTrace();
                                                               }
                                                               }
                                                       }

        );
        return convertView;
    }




    static class ViewHolder {
        TextView tv_id;
        TextView tv_lista_precio;
        RelativeLayout relativeListaListadoPromocion;


    }

}

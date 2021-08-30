package com.vistony.salesforce.Controller.Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.DireccionClienteView;

import java.util.ArrayList;
import java.util.List;

public class ListaDireccionClienteAdapter extends ArrayAdapter<ListaDireccionClienteEntity>  {

    public static ArrayList<ListaDireccionClienteEntity> araylistaDireccionClienteEntity;
    private android.content.Context Context;
    private List<ListaDireccionClienteEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaDireccionClienteEntity> arrayList;
    private FragmentManager fragmentManager;
    public DireccionClienteView direccionClienteView;

    public ListaDireccionClienteAdapter(android.content.Context context, List<ListaDireccionClienteEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaDireccionClienteEntity>();
        this.arrayList.addAll(objects);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaDireccionClienteAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(R.layout.layout_lista_direccion_cliente,parent,false);

            holder = new ListaDireccionClienteAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_domembarque_id = (TextView) convertView.findViewById(R.id.tv_domembarque_id);
            holder.tv_zona = (TextView) convertView.findViewById(R.id.tv_zona);
            holder.tv_direccion = (TextView) convertView.findViewById(R.id.tv_direccion);
            holder.tv_nombrefuerzatrabajo = (TextView) convertView.findViewById(R.id.tv_nombrefuerzatrabajo);
            holder.relativeListaDireccionCliente=convertView.findViewById(R.id.relativeListaDireccionCliente);
            convertView.setTag(holder);
        } else {
            holder = (ListaDireccionClienteAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaDireccionClienteEntity lead = getItem(position);

        // Setup.
        holder.tv_domembarque_id.setText(lead.getDomembarque_id());
        holder.tv_zona.setText(lead.getZona());
        holder.tv_direccion.setText(lead.getDireccion());
        holder.tv_nombrefuerzatrabajo.setText(lead.getNombrefuerzatrabajo());
        holder.relativeListaDireccionCliente.setOnClickListener(new View.OnClickListener() {
                                                           @Override
                                                           public void onClick(View v) {
                                                               alertaHabilitarDireccionCliente(lead).show();
                                                           }
                                                       }

        );
        return convertView;
    }
    static class ViewHolder {
        TextView tv_domembarque_id;
        TextView tv_zona;
        TextView tv_direccion;
        TextView tv_nombrefuerzatrabajo;
        RelativeLayout relativeListaDireccionCliente;


    }
    public AlertDialog alertaHabilitarDireccionCliente(ListaDireccionClienteEntity lead) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Advertencia")
                .setMessage("Esta Seguro de Elegir la Direccion?")
                .setPositiveButton("OK",

                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                ListaDireccionClienteEntity listaDireccionClienteEntity=new ListaDireccionClienteEntity();
                                araylistaDireccionClienteEntity= new ArrayList <>();
                                listaDireccionClienteEntity.cliente_id = lead.getCliente_id().toString();
                                listaDireccionClienteEntity.domembarque_id = lead.getDomembarque_id().toString();
                                listaDireccionClienteEntity.direccion = lead.getDireccion().toString();
                                listaDireccionClienteEntity.zona_id = lead.getZona_id().toString();
                                //listaDireccionClienteEntity.zona = lead.getZona().toString();
                                araylistaDireccionClienteEntity.add(listaDireccionClienteEntity);
                                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.add(R.id.content_menu_view, direccionClienteView.newInstanceDevuelveDireccion(araylistaDireccionClienteEntity));

                            }
                        })
                .setNegativeButton("CANCELAR",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

        return builder.create();
    }
}

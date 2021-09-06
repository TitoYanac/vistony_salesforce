package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Entity.Adapters.ListaCobranzaCabeceraEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaCobranzaCabeceraAdapter extends ArrayAdapter<ListaCobranzaCabeceraEntity> {
    public static ArrayList<ListaCobranzaCabeceraEntity> ArraylistaCobranzaCabeceraEntity;
    ListaCobranzaCabeceraEntity listaCobranzaCabeceraEntity;
    public ListaCobranzaCabeceraAdapter(Context context, List<ListaCobranzaCabeceraEntity> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArraylistaCobranzaCabeceraEntity= new ArrayList <ListaCobranzaCabeceraEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaCobranzaCabeceraAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_cobranza_cabecera,
                    parent,
                    false);

            holder = new ListaCobranzaCabeceraAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_cliente = (TextView) convertView.findViewById(R.id.tv_cliente);
            holder.tv_documento = (TextView) convertView.findViewById(R.id.tv_documento);
            holder.tv_importe = (TextView) convertView.findViewById(R.id.tv_importe);
            holder.imvdetalle = (ImageView) convertView.findViewById(R.id.imvdetalle);
            convertView.setTag(holder);
        } else {
            holder = (ListaCobranzaCabeceraAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaCobranzaCabeceraEntity lead = getItem(position);

        // Setup.
        holder.tv_cliente.setText(lead.getCliente_id());
        holder.tv_documento.setText(lead.getNrodocumento());
        holder.tv_importe.setText((lead!=null && !lead.equals(""))?Convert.currencyForView(lead.getSaldocobrado()):"$0");


        holder.imvdetalle.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String Texto="VALERIA";
                 String Texto1=Texto;
             }
         }

        );



        //Glide.with(getContext()).load(lead.getImage()).into(holder.avatar);

        return convertView;
    }

    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_cliente;
        //TextView lbl_fecha_emision;
        TextView tv_documento;
        // TextView lbl_fecha_vencimiento;
        TextView tv_importe;
        // TextView lbl_importe;
        ImageView imvdetalle;
    }
}

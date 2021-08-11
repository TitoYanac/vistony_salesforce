package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasHistorialDespachosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaHistoricoFacturasHistorialDespachoAdapter  extends ArrayAdapter<ListaHistoricoFacturasHistorialDespachosEntity> {
    public static List<ListaHistoricoFacturasHistorialDespachosEntity> ArraylistaParametrosEntity = new ArrayList<>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;


    public ListaHistoricoFacturasHistorialDespachoAdapter(Context context, List<ListaHistoricoFacturasHistorialDespachosEntity> objects) {

        super(context, 0, objects);
        ArraylistaParametrosEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaHistoricoFacturasHistorialDespachoAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.lista_layout_dialog_historico_despacho_historial,
                    parent,
                    false);

            holder = new ListaHistoricoFacturasHistorialDespachoAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            holder.tv_estado = (TextView) convertView.findViewById(R.id.tv_estado);
            holder.tv_fecha = (TextView) convertView.findViewById(R.id.tv_fecha);
            holder.tv_motivo = (TextView) convertView.findViewById(R.id.tv_motivo);
            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricoFacturasHistorialDespachoAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHistoricoFacturasHistorialDespachosEntity lead = getItem(position);

        // Setup.
        holder.tv_item.setText(lead.getItem());
        holder.tv_estado.setText(lead.getEstado());
        holder.tv_fecha.setText(lead.getFecha());
        holder.tv_motivo.setText(lead.getMotivo());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_item;
        TextView tv_estado;
        TextView tv_fecha;
        TextView tv_motivo;
    }
}

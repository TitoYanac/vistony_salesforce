package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaSeguimientoFacturasEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaSeguimientFacturaAdapter extends ArrayAdapter<ListaSeguimientoFacturasEntity>  {
    public static List<ListaSeguimientoFacturasEntity> ArraylistaSeguimientoFacturaEntity = new ArrayList<ListaSeguimientoFacturasEntity>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;


    public ListaSeguimientFacturaAdapter(Context context, List<ListaSeguimientoFacturasEntity> objects) {

        super(context, 0, objects);
        ArraylistaSeguimientoFacturaEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ListaSeguimientFacturaAdapter.ViewHolder holder;
        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_seguimiento_facturas,
                    parent,
                    false);

            holder = new ListaSeguimientFacturaAdapter.ViewHolder();
            holder.tv_numero_factura = (TextView) convertView.findViewById(R.id.tv_numero_factura);
            holder.tv_numero_orden = (TextView) convertView.findViewById(R.id.tv_numero_orden);
            holder.tv_monto_factura = (TextView) convertView.findViewById(R.id.tv_monto_factura);
            holder.tv_ruc_cliente = (TextView) convertView.findViewById(R.id.tv_ruc_cliente);
            holder.tv_nombre_cliente = (TextView) convertView.findViewById(R.id.tv_nombre_cliente);
            holder.tv_nombre_conductor = (TextView) convertView.findViewById(R.id.tv_nombre_conductor);
            holder.tv_fecha_programacion = (TextView) convertView.findViewById(R.id.tv_fecha_programacion);
            holder.tv_estado_entrega_factura = (TextView) convertView.findViewById(R.id.tv_estado_entrega_factura);
            convertView.setTag(holder);
        } else {
            holder = (ListaSeguimientFacturaAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaSeguimientoFacturasEntity lead = getItem(position);

        // Setup.
        holder.tv_numero_factura.setText(lead.getLegalnumber());
        holder.tv_numero_orden.setText(lead.getOrdenERP_id());
        holder.tv_monto_factura.setText(lead.getMontodocumento());
        holder.tv_ruc_cliente.setText(lead.getRucdni());
        holder.tv_nombre_cliente.setText(lead.getNombrecliente());
        holder.tv_nombre_conductor.setText(lead.getNombrechofer());
        holder.tv_fecha_programacion.setText(lead.getFechaprogramacion());
        holder.tv_estado_entrega_factura.setText(lead.getEstadodespacho());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_numero_factura;
        TextView tv_numero_orden;
        TextView tv_monto_factura;
        TextView tv_ruc_cliente;
        TextView tv_nombre_cliente;
        TextView tv_nombre_conductor;
        TextView tv_fecha_programacion;
        TextView tv_estado_entrega_factura;



    }


}

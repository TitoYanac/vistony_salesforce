package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaPromocionDetalleAdapter  extends ArrayAdapter<ListaPromocionDetalleEntity> {
    public static List<ListaPromocionDetalleEntity> ArraylistaPromocionDetalleEntity = new ArrayList<>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;

    ListaPromocionDetalleAdapter listaPromocionDetalleAdapter;

    public ListaPromocionDetalleAdapter(Context context, List<ListaPromocionDetalleEntity> objects) {

        super(context, 0, objects);
        ArraylistaPromocionDetalleEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ListaPromocionDetalleAdapter.ViewHolder holder;
        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_promocion_detalle,
                    parent,
                    false);

            holder = new ListaPromocionDetalleAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_id_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_id_promocion_detalle);
            holder.tv_producto_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_producto_promocion_detalle);
            holder.tv_cant_producto_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_cant_producto_promocion_detalle);
            holder.tv_cant_producto_promocion_umd = (TextView) convertView.findViewById(R.id.tv_cant_producto_promocion_umd);
            convertView.setTag(holder);
        } else {
            holder = (ListaPromocionDetalleAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaPromocionDetalleEntity lead = getItem(position);

        // Setup.
        holder.tv_id_promocion_detalle.setText(lead.getId());
        holder.tv_producto_promocion_detalle.setText(lead.getProducto());
        holder.tv_cant_producto_promocion_detalle.setText(lead.getCantidad());
        holder.tv_cant_producto_promocion_umd.setText(lead.getUmd());
        return convertView;
    }

    static class ViewHolder {
        TextView tv_id_promocion_detalle;
        TextView tv_producto_promocion_detalle;
        TextView tv_cant_producto_promocion_detalle;
        TextView tv_cant_producto_promocion_umd;
    }

}

package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasLineasNoFacturadasEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaHistoricoFacturasLineasNoFacturadasAdapter extends ArrayAdapter<ListaHistoricoFacturasLineasNoFacturadasEntity> {
    public static List<ListaHistoricoFacturasLineasNoFacturadasEntity> ArraylistaParametrosEntity = new ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;


    public ListaHistoricoFacturasLineasNoFacturadasAdapter(Context context, List<ListaHistoricoFacturasLineasNoFacturadasEntity> objects) {

        super(context, 0, objects);
        ArraylistaParametrosEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaHistoricoFacturasLineasNoFacturadasAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_historico_facturas_no_facturadas,
                    parent,
                    false);

            holder = new ListaHistoricoFacturasLineasNoFacturadasAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            holder.tv_producto = (TextView) convertView.findViewById(R.id.tv_producto);
            holder.tv_umd = (TextView) convertView.findViewById(R.id.tv_umd);
            holder.tv_cantidad = (TextView) convertView.findViewById(R.id.tv_cantidad);
            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricoFacturasLineasNoFacturadasAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHistoricoFacturasLineasNoFacturadasEntity lead = getItem(position);

        // Setup.
        holder.tv_item.setText(lead.getItem_ov());
        holder.tv_producto.setText(lead.getProducto());
        holder.tv_umd.setText(lead.getUmd());
        holder.tv_cantidad.setText(lead.getCantidad());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_item;
        TextView tv_producto;
        TextView tv_umd;
        TextView tv_cantidad;
    }
}

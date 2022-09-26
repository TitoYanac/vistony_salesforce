package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VisitSectionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListVisitUnClosedAdapter extends ArrayAdapter<VisitSectionEntity> {
    public static List<VisitSectionEntity> ArraylistVisitSectionEntity= new ArrayList<>();;
    ListaPendingCollectionEntity listaPendingCollectionEntity;
    private Context context;

    public ListVisitUnClosedAdapter(Context context, List<VisitSectionEntity> objects) {

        super(context, 0, objects);
        ArraylistVisitSectionEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListVisitUnClosedAdapter.ViewHolder holder;
        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_dialog_list_visit_unclosed,
                    parent,
                    false);
            holder = new ListVisitUnClosedAdapter.ViewHolder();
            holder.tv_cliente = (TextView) convertView.findViewById(R.id.tv_cliente);
            holder.tv_delivery = (TextView) convertView.findViewById(R.id.tv_delivery);
            holder.tv_codigo_control = (TextView) convertView.findViewById(R.id.tv_codigo_control);
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ListVisitUnClosedAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final VisitSectionEntity lead = getItem(position);

        // Setup.
        holder.tv_cliente.setText(lead.getCardname());
        holder.tv_delivery.setText(lead.getLegalnumberref());
        holder.tv_codigo_control.setText(lead.getIdref());
        holder.tv_item.setText(lead.getIdrefitemid());



        return convertView;
    }

    static class ViewHolder {
        TextView tv_cliente;
        TextView tv_delivery;
        TextView tv_codigo_control;
        TextView tv_item;
    }
}

package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListHistoricSalesOrderTraceabilityDeliveryAdapter extends ArrayAdapter<InvoicesEntity> {
    public static List<InvoicesEntity> ArraylistaParametrosEntity = new ArrayList<>();;
    ListaPendingCollectionEntity listaPendingCollectionEntity;
    private Context context;

    public ListHistoricSalesOrderTraceabilityDeliveryAdapter(Context context, List<InvoicesEntity> objects) {

        super(context, 0, objects);
        ArraylistaParametrosEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListHistoricSalesOrderTraceabilityDeliveryAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_dialog_list_historic_sales_order_traceability_delivery,
                    parent,
                    false);

            holder = new ListHistoricSalesOrderTraceabilityDeliveryAdapter.ViewHolder();
            holder.tv_legalnumber = (TextView) convertView.findViewById(R.id.tv_legalnumber);
            holder.tv_driver = (TextView) convertView.findViewById(R.id.tv_driver);
            holder.tv_date_dispatch = (TextView) convertView.findViewById(R.id.tv_date_dispatch);
            holder.tv_status_dispatch = (TextView) convertView.findViewById(R.id.tv_status_dispatch);
            holder.tv_ocur_dispatch = (TextView) convertView.findViewById(R.id.tv_ocur_dispatch);
            holder.tv_mobile = (TextView) convertView.findViewById(R.id.tv_mobile);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricSalesOrderTraceabilityDeliveryAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final InvoicesEntity lead = getItem(position);

        // Setup.
        holder.tv_legalnumber.setText(lead.getLegalnumberdelivery() );
        holder.tv_driver.setText(lead.getIddriver());
        holder.tv_date_dispatch.setText(Induvis.getDate(BuildConfig.FLAVOR,lead.getFechadespacho()) );
        holder.tv_status_dispatch.setText(lead.getEstadodespacho());
        holder.tv_ocur_dispatch.setText(lead.getU_SYP_DT_OCUR());
        holder.tv_mobile.setText(lead.getMobile());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_legalnumber;
        TextView tv_driver;
        TextView tv_date_dispatch;
        TextView tv_status_dispatch;
        TextView tv_ocur_dispatch;
        TextView tv_mobile;
    }
}

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
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListHistoricSalesOrderTraceabilityInvoiceAdapter extends ArrayAdapter<InvoicesEntity> {
    public static List<InvoicesEntity> ArraylistaParametrosEntity = new ArrayList<>();;
    ListaPendingCollectionEntity listaPendingCollectionEntity;
    private Context context;

    public ListHistoricSalesOrderTraceabilityInvoiceAdapter(Context context, List<InvoicesEntity> objects) {

        super(context, 0, objects);
        ArraylistaParametrosEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListHistoricSalesOrderTraceabilityInvoiceAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_dialog_list_historic_sales_order_traceability_invoice,
                    parent,
                    false);
            holder = new ListHistoricSalesOrderTraceabilityInvoiceAdapter.ViewHolder();
            holder.tv_documento_id = (TextView) convertView.findViewById(R.id.tv_documento_id);
            holder.tv_legalnumber = (TextView) convertView.findViewById(R.id.tv_legalnumber);
            holder.tv_dateemition = (TextView) convertView.findViewById(R.id.tv_dateemition);
            holder.tv_amount_invoice = (TextView) convertView.findViewById(R.id.tv_amount_invoice);
            holder.tv_balance = (TextView) convertView.findViewById(R.id.tv_balance);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricSalesOrderTraceabilityInvoiceAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final InvoicesEntity lead = getItem(position);

        // Setup.
        holder.tv_documento_id.setText(lead.getDocumentoId());
        holder.tv_legalnumber.setText(lead.getNroFactura());
        holder.tv_dateemition.setText(Induvis.getDate(BuildConfig.FLAVOR,lead.getFechaEmision()));
        holder.tv_amount_invoice.setText(Convert.currencyForView(lead.getImporteFactura()));
        holder.tv_balance.setText(Convert.currencyForView(lead.getSaldo()));


        return convertView;
    }

    static class ViewHolder {
        TextView tv_documento_id;
        TextView tv_legalnumber;
        TextView tv_dateemition;
        TextView tv_amount_invoice;
        TextView tv_balance;
        TextView tv_type;
    }
}

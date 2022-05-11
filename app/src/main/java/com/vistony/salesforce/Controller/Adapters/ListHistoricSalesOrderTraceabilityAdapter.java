package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasHistorialDespachosDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasLineasNoFacturadasDao;
import com.vistony.salesforce.Dao.Adapters.ListaPendingCollectionDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasHistorialDespachosWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasLineasNoFacturadasWS;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasHistorialDespachosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasLineasNoFacturadasEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricSalesOrderTraceabilityAdapter  extends ArrayAdapter<HistoricSalesOrderTraceabilityEntity>  {
    private android.content.Context Context;
    private List<HistoricSalesOrderTraceabilityEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<HistoricSalesOrderTraceabilityEntity> arrayList;
    ListaHistoricoFacturasLineasNoFacturadasAdapter listaHistoricoFacturasLineasNoFacturadasAdapter;
    private ProgressDialog pd;
    ListaHistoricoFacturasHistorialDespachoAdapter listaHistoricoFacturasHistorialDespachoAdapter;

    public ListHistoricSalesOrderTraceabilityAdapter(android.content.Context context, List<HistoricSalesOrderTraceabilityEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<HistoricSalesOrderTraceabilityEntity>();
        this.arrayList.addAll(objects);

    }


    public void filter(String charText)
    {
        charText = charText.toLowerCase(Locale.getDefault());
        Listanombres.clear();
        if(charText.length()==0)
        {
            Listanombres.addAll(arrayList);
        }else
        {
            for(HistoricSalesOrderTraceabilityEntity wp: arrayList)
            {
                if(wp.getNombrecliente().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getCliente_id().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }

            }
        }
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return Listanombres.size();
    }


    @Override
    public HistoricSalesOrderTraceabilityEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListHistoricSalesOrderTraceabilityAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_list_historic_sales_order_traceability,
                    parent,
                    false);

            holder = new ListHistoricSalesOrderTraceabilityAdapter.ViewHolder();
            holder.tv_salesorderid = (TextView) convertView.findViewById(R.id.tv_salesorderid);
            holder.tv_rucdni = (TextView) convertView.findViewById(R.id.tv_rucdni);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.imv_historic_invoices = (ImageView) convertView.findViewById(R.id.imv_historic_invoices);
            holder.imv_historic_delivery = (ImageView) convertView.findViewById(R.id.imv_historic_delivery);
            holder.tv_cond_venta = (TextView) convertView.findViewById(R.id.tv_cond_venta);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricSalesOrderTraceabilityAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricSalesOrderTraceabilityEntity lead = getItem(position);

        // Setup.
        holder.tv_salesorderid.setText(lead.getOrdenventa_id());
        holder.tv_rucdni.setText(lead.getRucdni());
        holder.tv_name.setText(lead.getNombrecliente());
        holder.tv_amount.setText(lead.getMontototalorden());
        holder.tv_cond_venta.setText(lead.getPymntgroup());

        holder.imv_historic_invoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* alertamostrarfactura(
                        "Factura",
                        lead.getDocumento_id(),
                        lead.getNrofactura(),
                        lead.getFechaemisionfactura(),
                        lead.getMontoimportefactura(),
                        lead.getMontosaldofactura(),
                        lead.getTipo_factura()
                ).show();*/
            }
        });
        holder.imv_historic_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getalertDelivery(lead.getInvoices()).show();
            }
        });

        return convertView;
    }

    private Dialog getalertDelivery(List<InvoicesEntity> invoicesEntityList) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_list_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Entregas Vinculadas a la Orden de Venta");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);

        ListHistoricSalesOrderTraceabilityDeliveryAdapter listHistoricSalesOrderTraceabilityDeliveryAdapter=new ListHistoricSalesOrderTraceabilityDeliveryAdapter(getContext(), invoicesEntityList);

        lv_pending_collection.setAdapter(listHistoricSalesOrderTraceabilityDeliveryAdapter);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }

    private Dialog getalertInvoices(List<InvoicesEntity> invoicesEntityList) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_list_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Entregas Vinculadas a la Orden de Venta");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);

        ListHistoricSalesOrderTraceabilityDeliveryAdapter listHistoricSalesOrderTraceabilityDeliveryAdapter=new ListHistoricSalesOrderTraceabilityDeliveryAdapter(getContext(), invoicesEntityList);

        lv_pending_collection.setAdapter(listHistoricSalesOrderTraceabilityDeliveryAdapter);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }

    static class ViewHolder {
        TextView tv_salesorderid;
        TextView tv_rucdni;
        TextView tv_name;
        TextView tv_cond_venta;
        TextView tv_amount;
        ImageView imv_historic_invoices;
        ImageView imv_historic_delivery;

    }

}

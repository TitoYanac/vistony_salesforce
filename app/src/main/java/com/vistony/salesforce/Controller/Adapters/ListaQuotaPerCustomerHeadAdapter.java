package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaQuotasPerCustomerHeadEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.PriceListView;

import java.util.ArrayList;
import java.util.List;

public class ListaQuotaPerCustomerHeadAdapter extends ArrayAdapter<ListaQuotasPerCustomerHeadEntity> {
    public static List<ListaQuotasPerCustomerHeadEntity> ArrayListaQuotasPerCustomerEntity = new ArrayList<>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context=getContext();
    private FragmentManager fragmentManager;
    PriceListView priceListView;
    String tipo;

    public ListaQuotaPerCustomerHeadAdapter(Context context, List<ListaQuotasPerCustomerHeadEntity> objects) {

        super( context,0, objects);
        ArrayListaQuotasPerCustomerEntity=objects;
        this.context=context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaQuotaPerCustomerHeadAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml

                convertView = inflater.inflate(
                        R.layout.lista_layout_quotas_per_customer,
                        parent,
                        false);

            holder = new ListaQuotaPerCustomerHeadAdapter.ViewHolder();
            holder.tv_tramo = (TextView) convertView.findViewById(R.id.tv_tramo);
            holder.tv_condicionpago = (TextView) convertView.findViewById(R.id.tv_condicionpago);
            holder.tv_cuotas = (TextView) convertView.findViewById(R.id.tv_cuotas);
            holder.tv_saldo = (TextView) convertView.findViewById(R.id.tv_saldo);
            convertView.setTag(holder);

        } else {
            holder = (ListaQuotaPerCustomerHeadAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaQuotasPerCustomerHeadEntity lead = getItem(position);

        // Setup.

        holder.tv_tramo.setText(lead.getTramo());
        holder.tv_condicionpago.setText(lead.getCondicionpago());
        holder.tv_cuotas.setText(lead.getCuotas());
        holder.tv_saldo.setText(lead.getSaldo());

        return convertView;
    }

    static class ViewHolder {

        TextView tv_tramo;
        TextView tv_condicionpago;
        TextView tv_cuotas;
        TextView tv_saldo;
    }

}

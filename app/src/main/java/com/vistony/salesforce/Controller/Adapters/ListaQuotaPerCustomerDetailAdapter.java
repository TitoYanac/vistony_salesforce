package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaQuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Adapters.ListaQuotasPerCustomerHeadEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.PriceListView;

import java.util.ArrayList;
import java.util.List;

public class ListaQuotaPerCustomerDetailAdapter  extends ArrayAdapter<ListaQuotasPerCustomerDetailEntity> {
    public static List<ListaQuotasPerCustomerDetailEntity> ArrayListaQuotasPerCustomerEntity = new ArrayList<>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context=getContext();
    private FragmentManager fragmentManager;
    PriceListView priceListView;
    String tipo;

    public ListaQuotaPerCustomerDetailAdapter(Context context, List<ListaQuotasPerCustomerDetailEntity> objects) {

        super( context,0, objects);
        ArrayListaQuotasPerCustomerEntity=objects;
        this.context=context;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaQuotaPerCustomerDetailAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml

            convertView = inflater.inflate(
                    R.layout.lista_layout_quotas_per_customer_detail,
                    parent,
                    false);
            holder = new ListaQuotaPerCustomerDetailAdapter.ViewHolder();
            holder.tv_cuotas = (TextView) convertView.findViewById(R.id.tv_cuotas);
            holder.tv_vencido = (TextView) convertView.findViewById(R.id.tv_vencido);
            holder.tv_corriente = (TextView) convertView.findViewById(R.id.tv_corriente);
            holder.tv_pedido = (TextView) convertView.findViewById(R.id.tv_pedido);
            holder.tv_total = (TextView) convertView.findViewById(R.id.tv_total);
            holder.tv_fecha = (TextView) convertView.findViewById(R.id.tv_fecha);
            convertView.setTag(holder);

        } else {
            holder = (ListaQuotaPerCustomerDetailAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaQuotasPerCustomerDetailEntity lead = getItem(position);

        // Setup.
        holder.tv_cuotas.setText(lead.getCuotas());
        holder.tv_vencido.setText(lead.getVencido());
        holder.tv_corriente.setText(lead.getCorriente());
        holder.tv_pedido.setText(lead.getPedido());
        holder.tv_total.setText(lead.getTotal());
        String fecha,año,mes,dia;
        String[] sourcefechacuota= lead.getFecha().split(" ");
        fecha= sourcefechacuota[0];

        String[] sourcefechadesordenada= fecha.split("/");
        año=sourcefechadesordenada[2];
        mes=sourcefechadesordenada[0];
        dia=sourcefechadesordenada[1];

        holder.tv_fecha.setText(año+"/"+mes+"/"+dia);
        return convertView;
    }

    static class ViewHolder {
        TextView tv_cuotas;
        TextView tv_vencido;
        TextView tv_corriente;
        TextView tv_pedido;
        TextView tv_total;
        TextView tv_fecha;
    }
}

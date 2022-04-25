package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricStatusDispatchAdapter extends ArrayAdapter<HistoricStatusDispatchEntity> {
    LayoutInflater inflater;
    private android.content.Context Context;
    private List<HistoricStatusDispatchEntity> Listanombres =null;
    private ArrayList<HistoricStatusDispatchEntity> arrayList;

    public ListHistoricStatusDispatchAdapter(Context context, List<HistoricStatusDispatchEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(context);
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
            for(HistoricStatusDispatchEntity wp: arrayList)
            {
                if(wp.getCliente() .toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getCliente_ID()  .toLowerCase(Locale.getDefault()).contains(charText))
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
    public HistoricStatusDispatchEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListHistoricStatusDispatchAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_historic_statuc_dispatch,
                    parent,
                    false);

            holder = new ListHistoricStatusDispatchAdapter.ViewHolder();
            holder.tv_client = (TextView) convertView.findViewById(R.id.tv_client);
            holder.tv_referral_guide = (TextView) convertView.findViewById(R.id.tv_referral_guide);
            holder.tv_type_dispatch = (TextView) convertView.findViewById(R.id.tv_type_dispatch);
            holder.tv_reason_dispatch= (TextView) convertView.findViewById(R.id.tv_reason_dispatch);
            holder.imv_historic_status_dispatch_photo= (ImageView) convertView.findViewById(R.id.imv_historic_status_dispatch_photo);
            holder.chk_wsrecibido= (CheckBox) convertView.findViewById(R.id.chk_wsrecibido);
            holder.imv_historico_cobranza_respuesta_ws= (ImageView) convertView.findViewById(R.id.imv_historico_cobranza_respuesta_ws);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricStatusDispatchAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricStatusDispatchEntity lead = getItem(position);

        holder.tv_client.setText(lead.getCliente());
        holder.tv_referral_guide.setText(lead.getEntrega());
        holder.tv_type_dispatch.setText(lead.getTipoDespacho());
        holder.tv_reason_dispatch.setText(lead.getMotivoDespacho());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_client;
        TextView tv_referral_guide;
        TextView tv_type_dispatch;
        TextView tv_reason_dispatch;
        ImageView imv_historic_status_dispatch_photo;
        CheckBox chk_wsrecibido;
        ImageView imv_historico_cobranza_respuesta_ws;
    }
}

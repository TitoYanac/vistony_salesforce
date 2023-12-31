package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Entity.Adapters.ListaCobranzaDetalleEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaCobranzaDetalleAdapter extends ArrayAdapter<ListaCobranzaDetalleEntity> {

    public static ArrayList<ListaCobranzaDetalleEntity> ArraylistaCobranzaDetalleEntity;
    ListaCobranzaDetalleAdapter listaCobranzaDetalleAdapter;

    public ListaCobranzaDetalleAdapter(Context context, List<ListaCobranzaDetalleEntity> objects) {

        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArraylistaCobranzaDetalleEntity= new ArrayList <ListaCobranzaDetalleEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaCobranzaDetalleAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        // ¿Ya se infló este view?
        if (null == convertView) {

            convertView = inflater.inflate(R.layout.layout_lista_cobranza_detalle,parent,false);

            holder = new ListaCobranzaDetalleAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_cliente);
            holder.tv_nrodocumento = (TextView) convertView.findViewById(R.id.tv_documento);
            holder.et_saldo = (TextView) convertView.findViewById(R.id.et_saldo);
            holder.et_cobrado = (TextView) convertView.findViewById(R.id.et_cobrado);
            holder.et_nuevosaldo = (TextView) convertView.findViewById(R.id.et_nuevo_saldo);
            holder.imbcomentariorecibo = (ImageView) convertView.findViewById(R.id.imbcomentariorecibo);

            convertView.setTag(holder);
        } else {
            holder = (ListaCobranzaDetalleAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaCobranzaDetalleEntity lead = getItem(position);

        // Setup.
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_nrodocumento.setText(lead.getNrodocumento());
        holder.et_saldo.setText((lead.getSaldo()==null)?Convert.currencyForView("0"):Convert.currencyForView(lead.getSaldo()));
        holder.et_cobrado.setText((lead.getCobrado()==null)?Convert.currencyForView("0"):Convert.currencyForView(lead.getCobrado()));
        holder.et_nuevosaldo.setText((lead.getNuevosaldo()==null)?Convert.currencyForView("0"):Convert.currencyForView(lead.getNuevosaldo()));

        return convertView;
    }

    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_nombrecliente;
        //TextView lbl_fecha_emision;
        TextView tv_nrodocumento;
        // TextView lbl_fecha_vencimiento;
        TextView et_saldo;
        TextView et_cobrado;
        TextView et_nuevosaldo;
        ImageView imbcomentariorecibo;

    }
}

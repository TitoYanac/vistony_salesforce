package com.vistony.salesforce.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEditarEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.PromocionCabeceraEditarDescuentoView;

import java.util.List;

public class ListaPromocionCabeceraEditarDescuentoAdapter extends ArrayAdapter<ListaPromocionDetalleEditarEntity>  {
    public static List<ListaPromocionDetalleEditarEntity> listaPromocionDetalleEditarEntity;
    private android.content.Context Context;
    LayoutInflater inflater;
    public ListaPromocionCabeceraEditarDescuentoAdapter(android.content.Context context, List<ListaPromocionDetalleEditarEntity> objects) {

        super(context, 0, objects);
        Context=context;
        listaPromocionDetalleEditarEntity=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaPromocionCabeceraEditarDescuentoAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_promocion_cabecera_editar_descuento,
                    parent,
                    false);

            holder = new ListaPromocionCabeceraEditarDescuentoAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_id_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_id_promocion_detalle);
            holder.tv_producto_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_producto_promocion_detalle);
            holder.tv_cant_producto_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_cant_producto_promocion_detalle);
            holder.tv_cant_producto_promocion_umd = (TextView) convertView.findViewById(R.id.tv_cant_producto_promocion_umd);
            holder.tv_cant_promocion_detalle_editable = (TextView) convertView.findViewById(R.id.tv_cant_promocion_detalle_editable);
            holder.imv_incrementar = (ImageView) convertView.findViewById(R.id.imv_incrementar);
            holder.imv_decrementar = (ImageView) convertView.findViewById(R.id.imv_decrementar);
            convertView.setTag(holder);
        } else {
            holder = (ListaPromocionCabeceraEditarDescuentoAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        ListaPromocionDetalleEditarEntity lead = getItem(position);

        // Setup.
        holder.tv_id_promocion_detalle.setText(lead.getId());
        holder.tv_producto_promocion_detalle.setText(lead.getProducto());
        holder.tv_cant_producto_promocion_detalle.setText(lead.getCantidad());
        holder.tv_cant_producto_promocion_umd.setText(lead.getUmd());
        holder.tv_id_promocion_detalle.setText(lead.getId());
        holder.tv_cant_promocion_detalle_editable.setText(lead.getCantidad_editada());
        Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-listaPromocionDetalleEditarEntity.Size(): "+listaPromocionDetalleEditarEntity.size());
        Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-lead.getCantidad_editada(): "+lead.getCantidad_editada());
        //Log.e("jpcm","Primer cagar tv_cant_promocion_detalle: "+lead.getCantidad_editada() );
        holder.imv_incrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //if(listaPromocionDetalleEditarEntity.get(position).isEstadoitems())
                //{
                Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-lead.getCantidad_editada(): "+lead.getCantidad_editada());
                Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-lead.getCantidad(): "+lead.getCantidad());
                Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-holder.tv_cant_promocion_detalle_editable.getText: "+holder.tv_cant_promocion_detalle_editable.getText());
                if(Integer.parseInt(lead.getCantidad_editada())>=Integer.parseInt(lead.getCantidad()))
                {
                    Toast.makeText(getContext(), "La Cantidad a Editar es mayor a la Cantidad de Promocion", Toast.LENGTH_LONG).show();
                }else
                {
                    lead.setCantidad_editada(String.valueOf(Integer.parseInt(lead.getCantidad_editada())+1));
                    holder.tv_cant_promocion_detalle_editable.setText(lead.getCantidad_editada());
                    Log.e("jpcm","Incrementa tv_cant_promocion_detalle: "+lead.getCantidad_editada() );
                    Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-lead.getCantidad_editada()-tv_cant_promocion_detalle: "+lead.getCantidad_editada());
                                /*for(int j=0;j<listaPromocionDetalleEditarEntity.size();j++)
                                {
                                    listaPromocionDetalleEditarEntity.get(j).setEstadoitems(false);
                                }*/
                    for (int i = 0; i< PromocionCabeceraEditarDescuentoView.copiaeditablelistaPromocionCabeceraEntity.size(); i++ )
                    {
                        //for(int j=0;j<PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
                        //{
                           if(PromocionCabeceraEditarDescuentoView.copiaeditablelistaPromocionCabeceraEntity.get(i).getPromocion_id().equals(lead.getId()))
                           {
                        PromocionCabeceraEditarDescuentoView.copiaeditablelistaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada());
                           }
                        //0222222222222222222222224563
                        // 0}

                    }
                }


                //}


            }});
        holder.imv_decrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //if(listaPromocionDetalleEditarEntity.get(position).isEstadoitems())
                //{
                Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-lead.getCantidad_editada(): "+lead.getCantidad_editada());
                Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-lead.getCantidad(): "+lead.getCantidad());
                Log.e("REOS","ListaPromocionCabeceraEditarDescuentoAdapter-holder.tv_cant_promocion_detalle_editable.getText: "+holder.tv_cant_promocion_detalle_editable.getText());
                if (Integer.parseInt(lead.getCantidad_editada()) <= Integer.parseInt(lead.getCantidad())) {

                    lead.setCantidad_editada(String.valueOf(
                            Integer.parseInt(lead.getCantidad_editada()) - 1));
                    holder.tv_cant_promocion_detalle_editable.setText(lead.getCantidad_editada());
                    Log.e("jpcm", "Decrementa tv_cant_promocion_detalle: " + lead.getCantidad_editada());
                    for (int i=0;i< PromocionCabeceraEditarDescuentoView.copiaeditablelistaPromocionCabeceraEntity.size();i++ )
                    {
                        //for(int j=0;j<PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
                        //{
                            //if(PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getPromocion_detalle_id().equals(lead.getId()))
                            //{
                        PromocionCabeceraEditarDescuentoView.copiaeditablelistaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada());
                            //}
                        //}
                    }

                       /* for(int j=0;j<listaPromocionDetalleEditarEntity.size();j++)
                        {
                            if(listaPromocionDetalleEditarEntity.get(j))
                            listaPromocionDetalleEditarEntity.get(j).setEstadoitems(false);
                        }*/
                } else {
                    Toast.makeText(getContext(), "La Cantidad No puede ser Menor a 0", Toast.LENGTH_LONG).show();
                }

                // }
            }});

        return convertView;
    }




    static class ViewHolder {
        TextView tv_id_promocion_detalle;
        TextView tv_producto_promocion_detalle;
        TextView tv_cant_producto_promocion_detalle;
        TextView tv_cant_producto_promocion_umd;
        TextView tv_cant_promocion_detalle_editable;
        ImageView imv_incrementar;
        ImageView imv_decrementar;


    }
}

package com.vistony.salesforce.Controller.Adapters;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEditarEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.Sesion.ClienteAtendido;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.PromocionDetalleView;

import java.util.List;

public class ListaPromocionDetalleEditarAdapter  extends ArrayAdapter<ListaPromocionDetalleEditarEntity> {
    public static List<ListaPromocionDetalleEditarEntity> listaPromocionDetalleEditarEntity;
    private android.content.Context Context;
    LayoutInflater inflater;
    private FragmentManager fragmentManager;

    public ListaPromocionDetalleEditarAdapter(android.content.Context context, List<ListaPromocionDetalleEditarEntity> objects) {

        super(context, 0, objects);
        Context=context;
        listaPromocionDetalleEditarEntity=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaPromocionDetalleEditarAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_promocion_detalle_editar,
                    parent,
                    false);

            holder = new ListaPromocionDetalleEditarAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_id_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_id_promocion_detalle);
            holder.tv_producto_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_producto_promocion_detalle);
            //holder.tv_cant_producto_promocion_detalle = (TextView) convertView.findViewById(R.id.tv_cant_producto_promocion_detalle);
            holder.tv_cant_producto_promocion_umd = (TextView) convertView.findViewById(R.id.tv_cant_producto_promocion_umd);
            holder.tv_cant_promocion_detalle_editable = (TextView) convertView.findViewById(R.id.tv_cant_promocion_detalle_editable);
            holder.imv_incrementar = (ImageView) convertView.findViewById(R.id.imv_incrementar);
            holder.imv_decrementar = (ImageView) convertView.findViewById(R.id.imv_decrementar);
            holder.imv_editar_promocion_detalle = (ImageView) convertView.findViewById(R.id.imv_editar_promocion_detalle);
            holder.imv_delete_promotion_detail = (ImageView) convertView.findViewById(R.id.imv_delete_promotion_detail);

            convertView.setTag(holder);
        } else {
            holder = (ListaPromocionDetalleEditarAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        ListaPromocionDetalleEditarEntity lead = getItem(position);

        // Setup.
        holder.tv_id_promocion_detalle.setText(lead.getId());
        holder.tv_producto_promocion_detalle.setText(lead.getProducto_id()+" "+lead.getProducto());
        //holder.tv_cant_producto_promocion_detalle.setText(lead.getCantidad());
        holder.tv_cant_producto_promocion_umd.setText(lead.getUmd());
        holder.tv_id_promocion_detalle.setText(lead.getId());
        holder.tv_cant_promocion_detalle_editable.setText(lead.getCantidad_editada());
        Log.e("jpcm","listaPromocionDetalleEditarEntity.Size(): "+listaPromocionDetalleEditarEntity.size());
        Log.e("jpcm","Primer cagar tv_cant_promocion_detalle: "+lead.getCantidad_editada() );
        Log.e("REOS","listaPromocionDetalleEditarAdapter-lead.isEstadoitems(): "+lead.isEstadoitems());
        Log.e("REOS","listaPromocionDetalleEditarAdapter-lead.isStatusEdit(): "+lead.isStatusEdit());
        if(lead.getProducto_id().equals("%"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_edit_gray_24);
            holder.imv_editar_promocion_detalle.setImageDrawable(draw);
            holder.imv_editar_promocion_detalle.setEnabled(false);

        }
        else {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_edit_24_red);
            holder.imv_editar_promocion_detalle.setImageDrawable(draw);
            holder.imv_editar_promocion_detalle.setEnabled(true);
        }
        holder.imv_incrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("REOS","ListaPromocionCabeceraEditar-imv_incrementar-lead.getCantidad_editada(): "+lead.getCantidad_editada());
                Log.e("REOS","ListaPromocionCabeceraEditar-imv_incrementar-lead.getCantidad(): "+lead.getCantidad());
                Log.e("REOS","ListaPromocionCabeceraEditar-imv_incrementar-holder.tv_cant_promocion_detalle_editable.getText: "+holder.tv_cant_promocion_detalle_editable.getText());
                        /*if(Integer.parseInt(lead.getCantidad_editada())>=Integer.parseInt(lead.getCantidad()))
                        {
                            Toast.makeText(getContext(), "La Cantidad a Editar es mayor a la Cantidad de Promocion", Toast.LENGTH_LONG).show();
                        }else
                            {*/
                                lead.setCantidad_editada(String.valueOf(Integer.parseInt(lead.getCantidad_editada())+1));
                                holder.tv_cant_promocion_detalle_editable.setText(lead.getCantidad_editada());
                                Log.e("jpcm","Incrementa tv_cant_promocion_detalle: "+lead.getCantidad_editada() );
                                for (int i=0;i< PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.size();i++ )
                                {
                                    PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada().toString());
                                    for(int j=0;j<PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
                                    {
                                        if(PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getPromocion_detalle_id().equals(lead.getId()))
                                        {

                                            //PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada().toString());
                                            PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setCantidad(lead.getCantidad_editada());
                                        }
                                    }

                                }
                           // }
            }});
        holder.imv_decrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("REOS","ListaPromocionCabeceraEditar-imv_decrementar-lead.getCantidad_editada(): "+lead.getCantidad_editada());
                Log.e("REOS","ListaPromocionCabeceraEditar-imv_decrementar-lead.getCantidad(): "+lead.getCantidad());
                Log.e("REOS","ListaPromocionCabeceraEditar-imv_decrementar-holder.tv_cant_promocion_detalle_editable.getText: "+holder.tv_cant_promocion_detalle_editable.getText());
                //if(listaPromocionDetalleEditarEntity.get(position).isEstadoitems())
                //{
                    //if (Integer.parseInt(lead.getCantidad_editada()) <= Integer.parseInt(lead.getCantidad())) {
                        if ((Integer.parseInt(lead.getCantidad_editada()) - 1)>0) {
                        lead.setCantidad_editada(String.valueOf(
                                Integer.parseInt(lead.getCantidad_editada()) - 1));
                        holder.tv_cant_promocion_detalle_editable.setText(lead.getCantidad_editada());
                        Log.e("jpcm", "Decrementa tv_cant_promocion_detalle: " + lead.getCantidad_editada());
                            for (int i=0;i< PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.size();i++ )
                            {
                                //PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada().toString());
                                for(int j=0;j<PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
                                {
                                    if(PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getPromocion_detalle_id().equals(lead.getId()))
                                    {
                                        PromocionDetalleView.copiaeditablelistaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setCantidad(lead.getCantidad_editada());
                                    }
                                }
                            }

                       /* for(int j=0;j<listaPromocionDetalleEditarEntity.size();j++)
                        {
                            if(listaPromocionDetalleEditarEntity.get(j))
                            listaPromocionDetalleEditarEntity.get(j).setEstadoitems(false);
                        }*/
                    } else {
                        Toast.makeText(getContext(), "La cantidad no puede ser menor a igual a 0", Toast.LENGTH_LONG).show();
                    }

               // }
            }});

        holder.imv_editar_promocion_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.content_menu_view, PromocionDetalleView.SendPromotionProduct());
                for (int i=0;i< PromocionDetalleView.listaPromocionCabeceraEntity.size();i++ )
                {
                    //PromocionDetalleView.listaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada().toString());
                    for(int j=0;j<PromocionDetalleView.listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
                    {
                        if(PromocionDetalleView.listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getPromocion_detalle_id().equals(lead.getId()))
                        {
                            PromocionDetalleView.listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).setStatusEdit (true);
                        }
                    }
                }
                lead.setStatusEdit(true);

            }});

        holder.imv_delete_promotion_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.content_menu_view, PromocionDetalleView.SendPromotionProduct());*/
                for (int i=0;i< PromocionDetalleView.listaPromocionCabeceraEntity.size();i++ )
                {
                    PromocionDetalleView.listaPromocionCabeceraEntity.get(i).setDescuento(lead.getCantidad_editada().toString());
                    for(int j=0;j<PromocionDetalleView.listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().size();j++)
                    {
                        if(PromocionDetalleView.listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().get(j).getPromocion_detalle_id().equals(lead.getId()))
                        {
                            PromocionDetalleView.listaPromocionCabeceraEntity.get(i).getListaPromocionDetalleEntities().remove(j);
                        }
                    }
                }
                PromocionDetalleView.refreshList();

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
        ImageView imv_editar_promocion_detalle;
        ImageView imv_delete_promotion_detail;


    }

}

package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.PromocionCabeceraView;

import java.util.ArrayList;
import java.util.List;

public class ListaPromocionCabeceraAdapter extends ArrayAdapter<ListaPromocionCabeceraEntity>  {
    public static List<ListaPromocionCabeceraEntity> ArraylistaPromocionCabeceraEntity = new ArrayList<ListaPromocionCabeceraEntity>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;
    ListaPromocionDetalleAdapter listaPromocionDetalleAdapter;
    private FragmentManager fragmentManager;
    PromocionCabeceraView promocionCabeceraView;
    ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabeceraEntities=new ArrayList<>();
    public ListaPromocionCabeceraAdapter(Context context, List<ListaPromocionCabeceraEntity> objects) {

        super(context, 0, objects);
        ArraylistaPromocionCabeceraEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ListaPromocionCabeceraAdapter.ViewHolder holder;
        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_promocion_cabecera,
                    parent,
                    false);

            holder = new ListaPromocionCabeceraAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_promocion_id = (TextView) convertView.findViewById(R.id.tv_promocion_id);
            holder.tv_promocion_producto = (TextView) convertView.findViewById(R.id.tv_promocion_producto);
            holder.tv_umd = (TextView) convertView.findViewById(R.id.tv_umd);
            holder.tv_cantidadcompra = (TextView) convertView.findViewById(R.id.tv_cantidadcompra);
            holder.tv_cant_promocion = (TextView) convertView.findViewById(R.id.tv_cant_promocion);
            holder.tv_porcentajedescuentocabecera = (TextView) convertView.findViewById(R.id.tv_porcentajedescuentocabecera);
            //holder.lv_promocion_detalle = (ListView) convertView.findViewById(R.id.lv_promocion_detalle);
            holder.imv_incrementar = (ImageView) convertView.findViewById(R.id.imv_incrementar);
            holder.imv_decrementar = (ImageView) convertView.findViewById(R.id.imv_decrementar);
            holder.imv_editar_promocion_detalle = (ImageView) convertView.findViewById(R.id.imv_editar_promocion_detalle);
            holder.imv_editar_promocion_cabecera_descuento = (ImageView) convertView.findViewById(R.id.imv_editar_promocion_cabecera_descuento);
            holder.relativeListaPromocionCabecera =convertView.findViewById(R.id.relativeListaPromocionCabecera);
            holder.contentpromociondetalle=(ViewGroup) convertView.findViewById(R.id.contentpromociondetalle);
            holder.imv_valorizar = (ImageView) convertView.findViewById(R.id.imv_valorizar);
            holder.lbl_valorizacion = (TextView) convertView.findViewById(R.id.lbl_valorizacion);
            convertView.setTag(holder);
        } else {
            holder = (ListaPromocionCabeceraAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaPromocionCabeceraEntity lead = getItem(position);

        // Setup.
        holder.tv_promocion_id.setText(lead.getPromocion_id());
        holder.tv_promocion_producto.setText(lead.getProducto());
        holder.tv_umd.setText(lead.getUmd());
        holder.tv_cantidadcompra.setText(lead.getCantidadcompra());
        holder.tv_cant_promocion.setText(lead.getCantidadpromocion());
        holder.tv_porcentajedescuentocabecera.setText(lead.getDescuento());
        switch (BuildConfig.FLAVOR){
            case "bolivia":
            case "ecuador":
            case "chile":
            case "india":
            case "paraguay":
                holder.imv_valorizar.setVisibility(View.GONE);
                holder.lbl_valorizacion.setVisibility(View.GONE);
                break;
            default:
                break;
        }

        holder.imv_editar_promocion_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PromocionCabeceraView promocionCabeceraView=new PromocionCabeceraView();
                fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.content_menu_view, promocionCabeceraView.newEditarDetallePromocion(lead));
            }});
        holder.imv_editar_promocion_cabecera_descuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PromocionCabeceraView promocionCabeceraView=new PromocionCabeceraView();
                fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.content_menu_view, promocionCabeceraView.newEditarDetallePromocionDescuento(lead));
            }});

        holder.imv_incrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Evalua Si las Acciones en ImageView estan Habilitadas
                if(ArraylistaPromocionCabeceraEntity.get(position).isEstadoitems())
                {
                    //Declara variable de Caja de Texto influenciada por los botones + y -
                    int valorCaja = 0;
                    String cantidadPromocion;
                    //Incrementa en 1 el Valor de Caja y lo multiplica por la cantidad en la Cabecera de Promocion
                    valorCaja =Integer.parseInt(lead.cantidadcompra);
                    //Evalua si la Cantidad Pendiente en el Fragment PromocionCabeceraView - la cantidad en el valorCaja es = a 0
                    if (Integer.parseInt(PromocionCabeceraView.tv_cantidad_pendiente.getText().toString()) - valorCaja == 0) {
                        int x=0;
                        //Inserta sobre la Caja de Texto la Cantidad Actual + 1
                        cantidadPromocion=String.valueOf(Integer.parseInt(holder.tv_cant_promocion.getText().toString()) + 1);
                        holder.tv_cant_promocion.setText(cantidadPromocion);
                        lead.setCantidadpromocion(cantidadPromocion);
                        //Declara FragmentManager
                        fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        //Declara Transaction
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        //Solicita a Promocion Cabecera View Incrementar el Valor en el Resumen
                        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceIncrementar(String.valueOf(valorCaja)));
                        //Solicita a Promocion Cabecera View Activas Botons de Menu
                        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceActivarVincular(String.valueOf(valorCaja)));
                        //Envia Lista de Promociones
                        //listaPromocionCabeceraEntities.clear();
                        if(!listaPromocionCabeceraEntities.isEmpty()){
                            for(int j=0;j<listaPromocionCabeceraEntities.size();j++)
                            {
                                if(listaPromocionCabeceraEntities.get(j).getCantidadcompra().equals(lead.cantidadcompra)){
                                    listaPromocionCabeceraEntities.get(j).setCantidadpromocion(lead.getCantidadpromocion());
                                    x++;
                                }
                            }
                            if(x==0)
                            {
                                listaPromocionCabeceraEntities.add(lead);
                            }
                        }else
                        {
                            listaPromocionCabeceraEntities.add(lead);
                        }
                        Log.e("REOS", "ListaPromocionCabeceraAdapter:listaPromocionCabeceraEntities: " + listaPromocionCabeceraEntities.size());
                        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceObtenerListaPromocion(listaPromocionCabeceraEntities));

                        //Solicita cargar las ImageView esten desHabilitadas
                        for(int j=0;j<ArraylistaPromocionCabeceraEntity.size();j++)
                        {
                            ArraylistaPromocionCabeceraEntity.get(j).setEstadoitems(false);
                        }

                        if(!holder.tv_cant_promocion.getText().equals("0"))
                        {
                            holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_gray_24);
                            holder.imv_editar_promocion_detalle.setEnabled(false);
                        }
                        else
                        {
                            holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_24);
                            holder.imv_editar_promocion_detalle.setEnabled(true);
                        }


                    } else {
                        //Evalua Si la cantidad Pendiente en PromocionCabeceraView es mayor al Valor Caja
                        if (Integer.parseInt(PromocionCabeceraView.tv_cantidad_pendiente.getText().toString()) > valorCaja) {
                            int x=0;
                            //Inserta sobre la Caja de Texto la Cantidad Actual + 1
                            cantidadPromocion=String.valueOf(Integer.parseInt(holder.tv_cant_promocion.getText().toString()) + 1);
                            holder.tv_cant_promocion.setText(cantidadPromocion);
                            lead.setCantidadpromocion(cantidadPromocion);
                            //Declara Transaction
                            fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                            //Solicita a Promocion Cabecera View Incrementar el Valor en el Resumen
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            //Solicita a Promocion Cabecera View Incrementar el Valor en el Resumen
                            transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceIncrementar(String.valueOf(valorCaja)));
                            transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceActivarVincular(String.valueOf(valorCaja)));
                            if(!listaPromocionCabeceraEntities.isEmpty()){
                                for(int j=0;j<listaPromocionCabeceraEntities.size();j++)
                                {
                                    if(listaPromocionCabeceraEntities.get(j).getCantidadcompra().equals(lead.cantidadcompra)){
                                        listaPromocionCabeceraEntities.get(j).setCantidadpromocion(lead.getCantidadpromocion());
                                        x++;
                                    }
                                }
                                if(x==0)
                                {
                                    listaPromocionCabeceraEntities.add(lead);
                                }
                            }else
                            {
                                listaPromocionCabeceraEntities.add(lead);
                            }
                            Log.e("REOS", "ListaPromocionCabeceraAdapter:listaPromocionCabeceraEntities: " + listaPromocionCabeceraEntities.size());
                            transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceObtenerListaPromocion(listaPromocionCabeceraEntities));

                            if(!holder.tv_cant_promocion.getText().equals("0"))
                            {
                                holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_gray_24);
                                holder.imv_editar_promocion_detalle.setEnabled(false);
                            }
                            else
                            {
                                holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_24);
                                holder.imv_editar_promocion_detalle.setEnabled(true);
                            }

                        } else {
                            Toast.makeText(getContext(), "Cantidad No Permitida", Toast.LENGTH_SHORT).show();
                            if(!holder.tv_cant_promocion.getText().equals("0"))
                            {
                                holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_gray_24);
                                holder.imv_editar_promocion_detalle.setEnabled(false);
                            }
                            else
                            {
                                holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_24);
                                holder.imv_editar_promocion_detalle.setEnabled(true);
                            }
                        }
                    }
                }
            }});

        holder.imv_decrementar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Evalua Si las Acciones en ImageView estan Habilitadas
                if(ArraylistaPromocionCabeceraEntity.get(position).isEstadoitems())
                {
                    //Declara variable de Caja de Texto influenciada por los botones + y -
                    int valorCaja = 0;
                    String cantidadPromocion;
                    //Incrementa en 1 el Valor de Caja y lo multiplica por la cantidad en la Cabecera de Promocion
                    valorCaja =Integer.parseInt(lead.cantidadcompra);
                    //Evalua si la Cantidad Pendiente en el Fragment PromocionCabeceraView - la cantidad en el valorCaja es = a 0
                    if (Integer.parseInt(PromocionCabeceraView.tv_cantidad_pendiente.getText().toString()) - valorCaja == 0) {
                        //Inserta sobre la Caja de Texto la Cantidad Actual - 1
                        cantidadPromocion=String.valueOf(Integer.parseInt(holder.tv_cant_promocion.getText().toString()) - 1);
                        holder.tv_cant_promocion.setText(cantidadPromocion);
                        lead.setCantidadpromocion(cantidadPromocion);
                        //holder.tv_cant_promocion.setText(String.valueOf(Integer.parseInt(holder.tv_cant_promocion.getText().toString()) - 1));
                        //Declara FragmentManager
                        fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                        //Declara Transaction
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        //Solicita a Promocion Cabecera View Incrementar el Valor en el Resumen
                        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceDecrementar(String.valueOf(valorCaja)));
                        //Solicita a Promocion Cabecera View Activas Botons de Menu
                        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceActivarVincular(String.valueOf(valorCaja)));
                        //Envia Lista de Promociones
                        if(!listaPromocionCabeceraEntities.isEmpty()){
                            for(int j=0;j<listaPromocionCabeceraEntities.size();j++)
                            {
                                if(listaPromocionCabeceraEntities.get(j).getCantidadcompra().equals(lead.cantidadcompra)){
                                    listaPromocionCabeceraEntities.get(j).setCantidadpromocion(lead.getCantidadpromocion());
                                }
                            }
                        }else
                        {
                            listaPromocionCabeceraEntities.add(lead);
                        }
                        transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceObtenerListaPromocion(listaPromocionCabeceraEntities));
                        //Solicita cargar las ImageView esten desHabilitadas

                        for(int j=0;j<ArraylistaPromocionCabeceraEntity.size();j++)
                        {
                            ArraylistaPromocionCabeceraEntity.get(j).setEstadoitems(false);
                        }

                        if(!holder.tv_cant_promocion.getText().equals("0"))
                        {
                            holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_gray_24);
                            holder.imv_editar_promocion_detalle.setEnabled(false);
                        }
                        else
                        {
                            holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_24);
                            holder.imv_editar_promocion_detalle.setEnabled(true);
                        }

                    } else {
                        //Evalua Si la cantidad Pendiente en PromocionCabeceraView es mayor al Valor Caja
                        //if (Integer.parseInt(PromocionCabeceraView.tv_cantidad_pendiente.getText().toString()) > valorCaja) {
                        if (Integer.parseInt(PromocionCabeceraView.tv_cantidad_promocion.getText().toString()) - valorCaja >= 0) {
                            //Inserta sobre la Caja de Texto la Cantidad Actual + 1
                            cantidadPromocion=String.valueOf(Integer.parseInt(holder.tv_cant_promocion.getText().toString()) - 1);
                            holder.tv_cant_promocion.setText(cantidadPromocion);
                            lead.setCantidadpromocion(cantidadPromocion);
                            //Declara Transaction
                            fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                            //Solicita a Promocion Cabecera View Incrementar el Valor en el Resumen
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            //Solicita a Promocion Cabecera View Incrementar el Valor en el Resumen
                            transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceDecrementar(String.valueOf(valorCaja)));
                            if(!listaPromocionCabeceraEntities.isEmpty()){
                                for(int j=0;j<listaPromocionCabeceraEntities.size();j++)
                                {
                                    if(listaPromocionCabeceraEntities.get(j).getCantidadcompra().equals(lead.cantidadcompra)){
                                        listaPromocionCabeceraEntities.get(j).setCantidadpromocion(lead.getCantidadpromocion());
                                    }
                                    if(listaPromocionCabeceraEntities.get(j).getCantidadpromocion().equals("0"))
                                    {
                                        listaPromocionCabeceraEntities.remove(j);
                                    }
                                }
                            }else
                            {
                                listaPromocionCabeceraEntities.add(lead);
                            }
                        } else {

                            Toast.makeText(getContext(), "Cantidad No Permitida", Toast.LENGTH_SHORT).show();
                        }

                        if(!holder.tv_cant_promocion.getText().equals("0"))
                        {
                            holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_gray_24);
                            holder.imv_editar_promocion_detalle.setEnabled(false);
                        }
                        else
                        {
                            holder.imv_editar_promocion_detalle.setImageResource(R.drawable.ic_baseline_edit_24);
                            holder.imv_editar_promocion_detalle.setEnabled(true);
                        }
                    }
                }
            }});

        if((!(lead.listaPromocionDetalleEntities.isEmpty()))&&holder.contentpromociondetalle.getChildCount()==0)
        {

            for(int i=0;i<lead.listaPromocionDetalleEntities.size();i++)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                int id = R.layout.layout_lista_promocion_detalle;
                RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(id, null, false);
                TextView tv_id_promocion_detalle = (TextView) relativeLayout.findViewById(R.id.tv_id_promocion_detalle);
                TextView tv_producto_promocion_detalle = (TextView) relativeLayout.findViewById(R.id.tv_producto_promocion_detalle);
                TextView tv_cant_producto_promocion_detalle = (TextView) relativeLayout.findViewById(R.id.tv_cant_producto_promocion_detalle);
                TextView tv_cant_producto_promocion_umd = (TextView) relativeLayout.findViewById(R.id.tv_cant_producto_promocion_umd);
                tv_id_promocion_detalle.setText(lead.listaPromocionDetalleEntities.get(i).getPromocion_detalle_id());
                tv_producto_promocion_detalle.setText(lead.listaPromocionDetalleEntities.get(i).getProducto());
                tv_cant_producto_promocion_detalle.setText(lead.listaPromocionDetalleEntities.get(i).getCantidad());
                tv_cant_producto_promocion_umd.setText(lead.listaPromocionDetalleEntities.get(i).getUmd());
                holder.contentpromociondetalle.addView(relativeLayout);
            }
        }

        return convertView;

    }



    static class ViewHolder {
        TextView tv_promocion_id;
        TextView tv_promocion_producto;
        TextView tv_umd;
        TextView tv_cantidadcompra;
        TextView tv_cant_promocion;
        TextView tv_porcentajedescuentocabecera;
        ImageView imv_incrementar;
        ImageView imv_decrementar;
        ImageView imv_editar_promocion_detalle;
        ImageView imv_editar_promocion_cabecera_descuento;
        RelativeLayout relativeListaPromocionCabecera;
        ImageView imv_valorizar;
        TextView lbl_valorizacion;
        ViewGroup contentpromociondetalle;
    }

}

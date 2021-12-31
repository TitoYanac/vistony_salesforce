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
    PromocionDetalleSQLiteDao promocionDetalleSQLiteDao;
    ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao;
    FormulasController formulasController;
    public ListaPromocionCabeceraAdapter(Context context, List<ListaPromocionCabeceraEntity> objects) {

        super(context, 0, objects);
        ArraylistaPromocionCabeceraEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        promocionDetalleSQLiteDao=new PromocionDetalleSQLiteDao(getContext());
        listaPrecioDetalleSQLiteDao = new ListaPrecioDetalleSQLiteDao(getContext());
        formulasController=new FormulasController(getContext());
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
        /*
        final PromocionDetalleSQLiteDao promocionDetalleSQLiteDao=new PromocionDetalleSQLiteDao(getContext());
        ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleSQLiteEntity=new ArrayList<>();
        listaPromocionDetalleSQLiteEntity=promocionDetalleSQLiteDao.ObtenerPromocionDetalle(
                SesionEntity.compania_id,
                lead.getLista_promocion_id(),
                lead.getPromocion_id()
        );*/
        String contado="",credito="";
        ArrayList<ListaPrecioDetalleSQLiteEntity> listaPrecioDetalleSQLiteEntities=new ArrayList<>();
        listaPrecioDetalleSQLiteEntities=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioPorProducto(
                getContext(),
                lead.producto_id
        );
        for(int i=0;i<listaPrecioDetalleSQLiteEntities.size();i++)
        {
            contado=listaPrecioDetalleSQLiteEntities.get(i).getContado();
            credito=listaPrecioDetalleSQLiteEntities.get(i).getCredito();
        }

        if(SesionEntity.flagquerystock.equals("Y"))
        {
            holder.tv_cant_promocion.setVisibility(View.GONE);
            holder.imv_incrementar.setVisibility(View.GONE);
            holder.imv_decrementar.setVisibility(View.GONE);
            holder.imv_editar_promocion_detalle.setVisibility(View.GONE);
            Log.e("REOS","ListaPromocionCabeceraAdapter.getView.e: "+contado);
            /*holder.tv_cash.setText(promocionDetalleSQLiteDao.ObtenerPromocionDetalleSumContado(
                    SesionEntity.compania_id,
                    lead.getLista_promocion_id(),
                    lead.getPromocion_id()));
            holder.tv_credit.setText(promocionDetalleSQLiteDao.ObtenerPromocionDetalleSumCredito(
                    SesionEntity.compania_id,
                    lead.getLista_promocion_id(),
                    lead.getPromocion_id()));*/
            Log.e("REOS","ListaPromocionCabeceraAdapter.getView.contado: "+contado);
            Log.e("REOS","ListaPromocionCabeceraAdapter.getView.credito: "+credito);
            Log.e("REOS","ListaPromocionCabeceraAdapter.getView.formulasController.applyDiscountPercentageForLine): "+formulasController.applyDiscountPercentageForLine(
                    formulasController.getTotalPerLine(
                            contado,lead.getCantidadcompra()
                    ),lead.getDescuento()
            ));
            Log.e("REOS","ListaPromocionCabeceraAdapter.getView.formulasController.applyDiscountPercentageForLine): "+formulasController.applyDiscountPercentageForLine(
                    formulasController.getTotalPerLine(
                            credito,lead.getCantidadcompra()
                    ),lead.getDescuento()
            ));

            /*holder.tv_price_cash_pack.setText(
                    formulasController.CalcularMontoTotalconDescuento(
                            formulasController.getTotalPerLine(
                                    contado,lead.getCantidadcompra()
                            ),
                            formulasController.applyDiscountPercentageForLine(
                                    formulasController.getTotalPerLine(
                                            contado,lead.getCantidadcompra()
                                    ),lead.getDescuento()
                            )
                    ));
            holder.tv_price_credit_pack.setText(
                    formulasController.CalcularMontoTotalconDescuento(
                            formulasController.getTotalPerLine(
                                    credito,lead.getCantidadcompra()
                            ),
                            formulasController.applyDiscountPercentageForLine(
                                    formulasController.getTotalPerLine(
                                            credito,lead.getCantidadcompra()
                                    ),lead.getDescuento()
                            )
                    ));*/
        }

        holder.imv_valorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoObtenerPromocionValorizada(lead).show();
            }});

        //lead.setListaPromocionDetalleEntities(listaPromocionDetalleSQLiteEntity);
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
        /*holder.et_cant_promocion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String valorCaja;
                if (String.valueOf(s).equals("")) {
                    valorCaja = "0";
                } else {
                    valorCaja = String.valueOf(Integer.valueOf(String.valueOf(s)) * Integer.valueOf(lead.getCantidadcompra()));
                }

                if (listaPromocionCabeceraEntities.size() > 1)
                {
                    for (int i = 0; i < PromocionCabeceraView.listaOrdenVentaDetalleEntities.size(); i++)
                    {
                            if (Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja) >  0) {
                                String calculo;
                                calculo = String.valueOf(Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja));
                                PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).setOrden_detalle_cantidad(calculo);
                                lead.setCantidadpromocion(String.valueOf(s));
                            }
                            else if (IntegerOf(Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja));
                                PromocionCabeceraView..parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja)==0)
                            {
                                String calculo;Of(Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja));
                                PromocionCabeceraView.
                                calculo = String.valuelistaOrdenVentaDetalleEntities.get(i).setOrden_detalle_cantidad(calculo);
                                lead.setCantidadpromocion(String.valueOf(s));
                                //holder.et_cant_promocion.setKeyListener(null);
                                holder.relativeListaPromocionCabecera.setEnabled(false);
                                fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceActivarVincular(""));
                            }
                            else {
                                if (String.valueOf(s).equals("")) {
                                } else {
                                    Toast.makeText(getContext(), "Cantidad No Permitida", Toast.LENGTH_SHORT).show();
                                    fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                                    transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceDesactivarVincular(""));
                                }
                            }

                            }
                        //}
                    }

                else
                    {
                    for (int i = 0; i < PromocionCabeceraView.listaOrdenVentaDetalleEntities.size(); i++)
                    {
                        if (PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad().equals("0"))
                        {
                            holder.et_cant_promocion.setKeyListener(null);
                            fragmentManager = ((AppCompatActivity) getContext()).getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.add(R.id.content_menu_view, promocionCabeceraView.newInstanceActivarVincular(""));


                        } else {
                            //if (Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) <= Integer.parseInt(valorCaja)) {
                            if (Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja) == 0) {
                                String calculo;
                                calculo = String.valueOf(Integer.parseInt(PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).getOrden_detalle_cantidad()) - Integer.parseInt(valorCaja));
                                PromocionCabeceraView.listaOrdenVentaDetalleEntities.get(i).setOrden_detalle_cantidad(calculo);
                                lead.setCantidadpromocion(String.valueOf(s));
                            } else {
                                if (String.valueOf(s).equals("")) {
                                } else {
                                    Toast.makeText(getContext(), "Cantidad No Permitida", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                    //fin for
                    }
            }
        });
        */


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

                //tv_orden_venta_detalle_lista_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getLista_promocion_id());

                tv_id_promocion_detalle.setText(lead.listaPromocionDetalleEntities.get(i).getPromocion_detalle_id());
                tv_producto_promocion_detalle.setText(lead.listaPromocionDetalleEntities.get(i).getProducto());
                //tv_producto_promocion_detalle.setText(lead.getCantidadcompra());
                tv_cant_producto_promocion_detalle.setText(lead.listaPromocionDetalleEntities.get(i).getCantidad());
                tv_cant_producto_promocion_umd.setText(lead.listaPromocionDetalleEntities.get(i).getUmd());
                holder.contentpromociondetalle.addView(relativeLayout);
            }
            /*if(Integer.parseInt(lead.getDescuento().toString())>0)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                int id = R.layout.layout_lista_promocion_detalle;
                RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(id, null, false);
                TextView tv_id_promocion_detalle = (TextView) relativeLayout.findViewById(R.id.tv_id_promocion_detalle);
                TextView tv_producto_promocion_detalle = (TextView) relativeLayout.findViewById(R.id.tv_producto_promocion_detalle);
                TextView tv_cant_producto_promocion_detalle = (TextView) relativeLayout.findViewById(R.id.tv_cant_producto_promocion_detalle);
                TextView tv_cant_producto_promocion_umd = (TextView) relativeLayout.findViewById(R.id.tv_cant_producto_promocion_umd);

                //tv_orden_venta_detalle_lista_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getLista_promocion_id());

                tv_id_promocion_detalle.setText("DESC");
                tv_producto_promocion_detalle.setText("% DESCUENTO");
                //tv_producto_promocion_detalle.setText(lead.getCantidadcompra());
                tv_cant_producto_promocion_detalle.setText(lead.getDescuento());
                tv_cant_producto_promocion_umd.setText("%");
                holder.contentpromociondetalle.addView(relativeLayout);
            }*/

        }

        //ViewGroup.LayoutParams layoutParams = holder.lv_promocion_detalle.getLayoutParams();
        //layoutParams.height = (int) context.getResources().getDimension(R.dimen.rowheight) * holder.lv_promocion_detalle.getCount();
        //holder.lv_promocion_detalle.setLayoutParams(layoutParams);
        //listaPromocionDetalleAdapter = new ListaPromocionDetalleAdapter(getContext(), ListaPromocionDetalleDao.getInstance().getLeads(listaPromocionDetalleSQLiteEntity));
        //holder.lv_promocion_detalle.setAdapter(listaPromocionDetalleAdapter);
        //holder.lv_promocion_detalle.setEnabled(false);
        //holder.lv_promocion_detalle.setClickable(false);
        // holder.lv_promocion_detalle.setFocusable(false);
        //ScrollDisabledListView scrollDisabledListView=new ScrollDisabledListView(context);
        //scrollDisabledListView.setAdapter(listaPromocionDetalleAdapter);
        //holder.lv_promocion_detalle.setNestedScrollingEnabled(true);
        return convertView;


    }



    static class ViewHolder {
        TextView tv_promocion_id;
        TextView tv_promocion_producto;
        TextView tv_umd;
        TextView tv_cantidadcompra;
        TextView tv_cant_promocion;
        TextView tv_porcentajedescuentocabecera;
        //ListView lv_promocion_detalle;
        ImageView imv_incrementar;
        ImageView imv_decrementar;
        ImageView imv_editar_promocion_detalle;
        ImageView imv_editar_promocion_cabecera_descuento;
        RelativeLayout relativeListaPromocionCabecera;
        TextView tv_cash;
        TextView tv_credit;
        TextView tv_price_cash_pack;
        TextView tv_price_credit_pack;
        ImageView imv_valorizar;
        ViewGroup contentpromociondetalle;
    }

    public Dialog DialogoObtenerPromocionValorizada(ListaPromocionCabeceraEntity lead) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_lista_promocion_valorizada);
        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        textTitle.setText("IMPORTANTE!!!");
        final TextView tv_pack_contado,tv_pack_credito,tv_descuento_pack_contado,tv_descuento_pack_credito
                ,tv_bono_pack_contado,tv_bono_pack_credito,tv_total_pack_contado,tv_total_pack_credito,
                tv_precio_referencial_pack_contado,tv_precio_referencial_pack_credito,tv_precio_contado,tv_precio_credito
                ;

        tv_pack_contado= dialog.findViewById(R.id.tv_pack_contado);
        tv_pack_credito= dialog.findViewById(R.id.tv_pack_credito);
        tv_descuento_pack_contado= dialog.findViewById(R.id.tv_descuento_pack_contado);
        tv_descuento_pack_credito= dialog.findViewById(R.id.tv_descuento_pack_credito);
        tv_bono_pack_contado= dialog.findViewById(R.id.tv_bono_pack_contado);
        tv_bono_pack_credito= dialog.findViewById(R.id.tv_bono_pack_credito);
        tv_total_pack_contado= dialog.findViewById(R.id.tv_total_pack_contado);
        tv_total_pack_credito= dialog.findViewById(R.id.tv_total_pack_credito);
        tv_precio_referencial_pack_contado= dialog.findViewById(R.id.tv_precio_referencial_pack_contado);
        tv_precio_referencial_pack_credito= dialog.findViewById(R.id.tv_precio_referencial_pack_credito);
        tv_precio_contado= dialog.findViewById(R.id.tv_precio_contado);
        tv_precio_credito= dialog.findViewById(R.id.tv_precio_credito);

        String contado="",credito="";
        ArrayList<ListaPrecioDetalleSQLiteEntity> listaPrecioDetalleSQLiteEntities=new ArrayList<>();
        listaPrecioDetalleSQLiteEntities=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioPorProducto(
                getContext(),
                lead.producto_id
        );
        for(int i=0;i<listaPrecioDetalleSQLiteEntities.size();i++)
        {
            contado=listaPrecioDetalleSQLiteEntities.get(i).getContado();
            credito=listaPrecioDetalleSQLiteEntities.get(i).getCredito();
        }

        tv_precio_contado.setText(Convert.currencyForView(contado));
        tv_precio_credito.setText(Convert.currencyForView(credito));

        tv_pack_contado.setText(Convert.currencyForView(formulasController.getTotalPerLine(
                contado,lead.getCantidadcompra()
        )));
        tv_pack_credito.setText(Convert.currencyForView(formulasController.getTotalPerLine(
                credito,lead.getCantidadcompra()
        )));

        tv_bono_pack_contado.setText(Convert.currencyForView(
                promocionDetalleSQLiteDao.ObtenerPromocionDetalleSumContado(
                        SesionEntity.compania_id,
                        lead.getLista_promocion_id(),
                        lead.getPromocion_id())));
        tv_bono_pack_credito.setText(Convert.currencyForView(
                promocionDetalleSQLiteDao.ObtenerPromocionDetalleSumCredito(
                        SesionEntity.compania_id,
                        lead.getLista_promocion_id(),
                        lead.getPromocion_id())));
        tv_descuento_pack_contado.setText(Convert.currencyForView(String.valueOf(
                Double.parseDouble (formulasController.applyDiscountPercentageForLine(
                        formulasController.getTotalPerLine(
                                contado,lead.getCantidadcompra()
                        ),lead.getDescuento()
                ))*-1)
        ));
        tv_descuento_pack_credito.setText(Convert.currencyForView(String.valueOf(
                Double.parseDouble (formulasController.applyDiscountPercentageForLine(
                        formulasController.getTotalPerLine(
                                credito,lead.getCantidadcompra()
                        ),lead.getDescuento()
                ))*-1)
        ));
        tv_total_pack_contado.setText(Convert.currencyForView(
                formulasController.CalcularMontoTotalPromocionconDescuentoyBono(
                        formulasController.getTotalPerLine(
                                contado,lead.getCantidadcompra()
                        ),
                        formulasController.applyDiscountPercentageForLine(
                                formulasController.getTotalPerLine(
                                        contado,lead.getCantidadcompra()
                                ),lead.getDescuento()
                        ),
                        "0"
                )
        ));
        tv_total_pack_credito.setText(Convert.currencyForView(
                formulasController.CalcularMontoTotalPromocionconDescuentoyBono(
                        formulasController.getTotalPerLine(
                                credito,lead.getCantidadcompra()
                        ),
                        formulasController.applyDiscountPercentageForLine(
                                formulasController.getTotalPerLine(
                                        credito,lead.getCantidadcompra()
                                ),lead.getDescuento()
                        ),
                        "0"
                )
        ));
        tv_precio_referencial_pack_contado.setText(Convert.currencyForView(
                formulasController.getPriceReferencePack(
                        formulasController.CalcularMontoTotalPromocionconDescuentoyBono(
                                formulasController.getTotalPerLine(
                                        contado,lead.getCantidadcompra()
                                ),
                                formulasController.applyDiscountPercentageForLine(
                                        formulasController.getTotalPerLine(
                                                contado,lead.getCantidadcompra()
                                        ),lead.getDescuento()
                                ),
                                promocionDetalleSQLiteDao.ObtenerPromocionDetalleSumContado(
                                        SesionEntity.compania_id,
                                        lead.getLista_promocion_id(),
                                        lead.getPromocion_id())
                        ),
                        lead.getCantidadcompra()
                )
        ));
        tv_precio_referencial_pack_credito.setText(Convert.currencyForView(
                formulasController.getPriceReferencePack(
                        formulasController.CalcularMontoTotalPromocionconDescuentoyBono(
                                formulasController.getTotalPerLine(
                                        credito,lead.getCantidadcompra()
                                ),
                                formulasController.applyDiscountPercentageForLine(
                                        formulasController.getTotalPerLine(
                                                credito,lead.getCantidadcompra()
                                        ),lead.getDescuento()
                                ),
                                promocionDetalleSQLiteDao.ObtenerPromocionDetalleSumCredito(
                                        SesionEntity.compania_id,
                                        lead.getLista_promocion_id(),
                                        lead.getPromocion_id())
                        ),
                        lead.getCantidadcompra()
                )
        ));



        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        //Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel_orden_venta_detalle);
        dialogButton.setText("ACEPTAR");
        //dialogButtonCancel.setText("CANCELAR");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(v -> {
            dialog.dismiss();

        });

        /*dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        return  dialog;
    }

}

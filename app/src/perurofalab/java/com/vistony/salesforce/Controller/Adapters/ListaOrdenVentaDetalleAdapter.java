package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetallePromocionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.OrdenVentaDetalleView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaOrdenVentaDetalleAdapter extends ArrayAdapter<ListaOrdenVentaDetalleEntity> {

    private android.content.Context Context;
    LayoutInflater inflater;
    private FragmentManager fragmentManager;
    public OrdenVentaDetalleView ordenVentaDetalleView;
    PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(getContext());
    ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabecera=new ArrayList<>();
    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
    FormulasController formulasController=new FormulasController(getContext());
    boolean[] itemChecked;

    public ListaOrdenVentaDetalleAdapter(android.content.Context context, List<ListaOrdenVentaDetalleEntity> objects) {

        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);
        this.itemChecked=new boolean[objects.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("REOS","ListaOrdenVentaDetalleAdapter: Inicio");
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaOrdenVentaDetalleAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_orden_venta_detalle_peru,
                    parent,
                    false);

            holder = new ListaOrdenVentaDetalleAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_orden_detalle_item = (TextView) convertView.findViewById(R.id.tv_orden_detalle_item);
            holder.tv_orden_detalle_producto = (TextView) convertView.findViewById(R.id.tv_orden_detalle_producto);
            holder.tv_orden_detalle_umd = (TextView) convertView.findViewById(R.id.tv_orden_detalle_umd);
            holder.tv_orden_detalle_stock = (TextView) convertView.findViewById(R.id.tv_orden_detalle_stock);
            holder.et_orden_detalle_cantidad = (EditText) convertView.findViewById(R.id.et_orden_detalle_cantidad);
            holder.tv_orden_detalle_precio = (TextView) convertView.findViewById(R.id.tv_orden_detalle_precio);
            holder.tv_orden_detalle_porcentaje_descuento = (TextView) convertView.findViewById(R.id.tv_orden_detalle_porcentaje_descuento);
            holder.tv_orden_detalle_igv = (TextView) convertView.findViewById(R.id.tv_orden_detalle_igv);
            holder.tv_orden_detalle_total = (TextView) convertView.findViewById(R.id.tv_orden_detalle_total);
            holder.tv_orden_detalle_galon_unitario = (TextView) convertView.findViewById(R.id.tv_orden_detalle_galon_unitario);
            holder.tv_orden_detalle_galon_acumulado = (TextView) convertView.findViewById(R.id.tv_orden_detalle_galon_acumulado);
            // holder.lv_promociondetalle=(ListView) convertView.findViewById(R.id.lv_promociondetalle);
            holder.imv_consultar_promocion_cabecera=(ImageView) convertView.findViewById(R.id.imv_consultar_promocion_cabecera);
            holder.imv_eliminar_orden_venta_detalle=(ImageView) convertView.findViewById(R.id.imv_eliminar_orden_venta_detalle);
            holder.chk_descuento_contado=(CheckBox) convertView.findViewById(R.id.chk_descuento_contado);
            holder.et_porcentaje_descuento_contado=(EditText) convertView.findViewById(R.id.et_porcentaje_descuento_contado);
            holder.tr_dsct_cont_detail=(TableRow) convertView.findViewById(R.id.tr_dsct_cont_detail);
            holder.layout=(ViewGroup) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ListaOrdenVentaDetalleAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaOrdenVentaDetalleEntity lead = getItem(position);
        holder.layout.removeAllViews();
        listaOrdenVentaDetalleEntities.add(lead);
        // Setup.
        //DecimalFormat format = new DecimalFormat("#0.00");
        holder.tv_orden_detalle_item.setText(lead.getOrden_detalle_item()+")");
        holder.tv_orden_detalle_producto.setText(lead.getOrden_detalle_producto());
        holder.tv_orden_detalle_umd.setText(lead.getOrden_detalle_producto_id());
        //holder.tv_orden_detalle_stock.setText(String.valueOf(format.format(Float.parseFloat(lead.getOrden_detalle_stock()))));
        //holder.tv_orden_detalle_precio.setText(String.valueOf(format.format(Float.parseFloat(lead.getOrden_detalle_precio_unitario()))));
        holder.tv_orden_detalle_stock.setText(Convert.numberForViewDecimals(lead.getOrden_detalle_stock(),2));
        holder.tv_orden_detalle_precio.setText(Convert.numberForViewDecimals(lead.getOrden_detalle_precio_unitario(),2));
        holder.et_orden_detalle_cantidad.setText(lead.getOrden_detalle_cantidad());
        holder.tv_orden_detalle_galon_unitario.setText(Convert.numberForViewDecimals(lead.getOrden_detalle_gal(),2));

        if(BuildConfig.FLAVOR.equals("perurofalab"))
        {
            holder.tr_dsct_cont_detail.setVisibility(View.GONE);
        }

        if(lead.isOrden_detalle_chk_descuentocontado_cabecera()&&!(lead.getOrden_detalle_cantidad().equals(""))&&!lead.getOrden_detalle_terminopago_id().equals("47"))
        {
            holder.chk_descuento_contado.setEnabled(true);
            Log.e("REOS","ListaOrdenVentaDetalleAdapter:holder.chk_descuento_contado.setEnabled(true)");
        }
        else
        {
            Log.e("REOS","ListaOrdenVentaDetalleAdapter:holder.chk_descuento_contado.setEnabled(false)");
            holder.chk_descuento_contado.setEnabled(false);
        }



        if(lead.isOrden_detalle_chk_descuentocontado())
        {
            holder.chk_descuento_contado.setChecked(true);
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.borde_editext_ov_negro);
            //Drawable borde_editext_orden_venta_detalle_inhabilitado=(Drawable) R.drawable.borde_editext_orden_venta_detalle_inhabilitado;
            holder.et_porcentaje_descuento_contado.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
            holder.et_porcentaje_descuento_contado.setTextColor(Color.parseColor("#D6001C"));
            holder.et_porcentaje_descuento_contado.setEnabled(true);
            holder.et_porcentaje_descuento_contado.setText(lead.getOrden_detalle_descuentocontado());
            holder.et_porcentaje_descuento_contado.setSelection(0);

        }
        else
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.borde_editext_ov_negro);
            //Drawable borde_editext_orden_venta_detalle_inhabilitado=(Drawable) R.drawable.borde_editext_orden_venta_detalle_inhabilitado;
            holder.et_porcentaje_descuento_contado.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
            holder.et_porcentaje_descuento_contado.setTextColor(Color.parseColor("#808080"));
            holder.et_porcentaje_descuento_contado.setEnabled(false);
            holder.chk_descuento_contado.setChecked(false);
            Resources res2 = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res2.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
            holder.imv_consultar_promocion_cabecera.setEnabled(true);
            //holder.chk_descuento_contado.setChecked(true);
            holder.et_porcentaje_descuento_contado.setText("0");
        }
        Log.e("REOS","ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_cantidad():" + lead.getOrden_detalle_cantidad());
        Log.e("REOS","ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_descuentocontado():" + lead.getOrden_detalle_descuentocontado());

        holder.chk_descuento_contado.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FormulasController formulasController=new FormulasController(getContext());
                boolean resultado=false;
                resultado=formulasController.validadescuentocontado(lead);
                if(resultado)
                {
                    if(holder.chk_descuento_contado.isChecked())
                    {
                        //holder.et_porcentaje_descuento_contado.setEnabled(false);
                        Resources res = getContext().getResources(); // need this to fetch the drawable
                        Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.borde_editext_ov_negro);
                        //Drawable borde_editext_orden_venta_detalle_inhabilitado=(Drawable) R.drawable.borde_editext_orden_venta_detalle_inhabilitado;
                        holder.et_porcentaje_descuento_contado.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
                        holder.et_porcentaje_descuento_contado.setTextColor(Color.parseColor("#D6001C"));
                        holder.et_porcentaje_descuento_contado.setEnabled(true);
                        lead.setOrden_detalle_chk_descuentocontado(true);
                        Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_24);
                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                        holder.imv_consultar_promocion_cabecera.setEnabled(false);
                    }
                    else
                    {
                        Resources res = getContext().getResources(); // need this to fetch the drawable
                        Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.borde_editext_ov_negro);
                        //Drawable borde_editext_orden_venta_detalle_inhabilitado=(Drawable) R.drawable.borde_editext_orden_venta_detalle_inhabilitado;
                        holder.et_porcentaje_descuento_contado.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
                        holder.et_porcentaje_descuento_contado.setTextColor(Color.parseColor("#808080"));
                        holder.et_porcentaje_descuento_contado.setEnabled(false);
                        holder.et_porcentaje_descuento_contado.setText("0");
                        lead.setOrden_detalle_chk_descuentocontado(false);

                        if(lead.getOrden_detalle_promocion_habilitada().equals("0")||lead.getOrden_detalle_terminopago_id().equals("47")||holder.chk_descuento_contado.isChecked())
                        {

                            Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_24);
                            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                            holder.imv_consultar_promocion_cabecera.setEnabled(false);
                            //holder.chk_descuento_contado.setChecked(false);
                        }
                        else
                        {

                            Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                            holder.imv_consultar_promocion_cabecera.setEnabled(true);
                            //holder.chk_descuento_contado.setChecked(true);
                        }

                        lead.setOrden_detalle_descuentocontado(holder.et_porcentaje_descuento_contado.getText().toString());
                        lead.setOrden_detalle_porcentaje_descuento(
                                String.valueOf(
                                        Double.parseDouble (lead.getOrden_detalle_porcentaje_descuento())
                                        // -
                                        // Integer.parseInt(lead.getOrden_detalle_descuentocontado())
                                ));
                        lead.setOrden_detalle_monto_descuento(
                                //Formula Para Calcular el Monto de Descuento
                                formulasController.applyDiscountPercentageForLine(
                                        //Variable 1 Monto Total Linea Sin IGV
                                        //lead.getOrden_detalle_montototallinea(),
                                        lead.getOrden_detalle_montosubtotal(),
                                        //Variable 2  Porcentaje Descuento
                                        (lead.getOrden_detalle_porcentaje_descuento())
                                ));
                        lead.setOrden_detalle_montosubtotal(
                                formulasController.getTotalPerLine
                                        (lead.getOrden_detalle_precio_unitario(),
                                                lead.getOrden_detalle_cantidad()));
                        //Monto subtotal con descuento
                        lead.setOrden_detalle_montosubtotalcondescuento(
                                //Formula para Calcular el Monto Sub Total Con Descuento
                                formulasController.CalcularMontoTotalconDescuento(
                                        //Variable 1 Monto Total Linea Sin IGV
                                        lead.getOrden_detalle_montosubtotal(),
                                        //Variable 2  Monto Descuento
                                        lead.getOrden_detalle_monto_descuento()
                                ));
                        holder.tv_orden_detalle_total.setText(lead.getOrden_detalle_montosubtotal());

                        for (int i = 0; i < OrdenVentaDetalleView.listadoProductosAgregados.size(); i++) {
                            if (i == (Integer.parseInt(lead.getOrden_detalle_item()) - 1)) {
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:chk_descuentocontado-ingresoabucle");
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_porcentaje_descuento((lead.getOrden_detalle_porcentaje_descuento()));
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_descuento((lead.getOrden_detalle_monto_descuento()));
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_chk_descuentocontado(lead.isOrden_detalle_chk_descuentocontado());
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_descuentocontado(lead.getOrden_detalle_descuentocontado());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_porcentaje_descuento: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_porcentaje_descuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_monto_descuento: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_monto_descuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotal: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotal());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotalcondescuento: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotalcondescuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_chk_descuentocontado: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).isOrden_detalle_chk_descuentocontado());
                            }
                        }
                        ActualizaListaOrdenDetallePromocion(lead);
                        ActualizarResumenOrdenVenta();
                    }

                    Log.e("REOS","ListaOrdenVentaDetalleAdapter:holder.resultado-true");

                }else
                {
                    Log.e("REOS","ListaOrdenVentaDetalleAdapter:holder.resultado-false");
                    alertaValidacionContadoDescuento().show();
                    holder.chk_descuento_contado.setChecked(false);
                    //holder.et_porcentaje_descuento_contado.setText("0");
                }
            }
        });

        holder.et_porcentaje_descuento_contado.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    if (ValidarPorcentajeDescuentoContado(holder.et_porcentaje_descuento_contado.getText().toString(),lead.orden_detalle_producto_id))
                    {
                        FormulasController formulasController = new FormulasController(getContext());
                        boolean resultado = false;
                        resultado = formulasController.validadescuentocontado(lead);
                        if (resultado) {
                            if (holder.chk_descuento_contado.isChecked()) {
                                //holder.et_porcentaje_descuento_contado.setEnabled(false);
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable(R.drawable.borde_editext_ov_negro);
                                //Drawable borde_editext_orden_venta_detalle_inhabilitado=(Drawable) R.drawable.borde_editext_orden_venta_detalle_inhabilitado;


                                holder.et_porcentaje_descuento_contado.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
                                holder.et_porcentaje_descuento_contado.setTextColor(Color.parseColor("#D6001C"));
                                holder.et_porcentaje_descuento_contado.setEnabled(true);

                                lead.setOrden_detalle_descuentocontado(holder.et_porcentaje_descuento_contado.getText().toString());
                                lead.setOrden_detalle_chk_descuentocontado(true);
                                //holder.et_porcentaje_descuento_contado.setText(lead.getOrden_detalle_descuentocontado());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:holder.et_porcentaje_descuento_contado.getText(): " + holder.et_porcentaje_descuento_contado.getText());

                                //lead.setOrden_detalle_descuentocontado(holder.et_porcentaje_descuento_contado.getText().toString());
                                lead.setOrden_detalle_porcentaje_descuento(
                                        String.valueOf(
                                                Double.parseDouble (lead.getOrden_detalle_porcentaje_descuento())
                                               // +
                                               //         Double.parseDouble(lead.getOrden_detalle_descuentocontado()))
                                        ));
                                lead.setOrden_detalle_monto_descuento(
                                        //Formula Para Calcular el Monto de Descuento
                                        formulasController.applyDiscountPercentageForLine(
                                                //Variable 1 Monto Total Linea Sin IGV
                                                //lead.getOrden_detalle_montototallinea(),
                                                lead.getOrden_detalle_montosubtotal(),
                                                //Variable 2  Porcentaje Descuento
                                                (lead.getOrden_detalle_porcentaje_descuento())
                                        ));
                                lead.setOrden_detalle_montosubtotal(
                                        formulasController.getTotalPerLine
                                                (lead.getOrden_detalle_precio_unitario(),
                                                        lead.getOrden_detalle_cantidad()));
                                //Monto subtotal con descuento
                                lead.setOrden_detalle_montosubtotalcondescuento(
                                        //Formula para Calcular el Monto Sub Total Con Descuento
                                        formulasController.CalcularMontoTotalconDescuento(
                                                //Variable 1 Monto Total Linea Sin IGV
                                                lead.getOrden_detalle_montosubtotal(),
                                                //Variable 2  Monto Descuento
                                                lead.getOrden_detalle_monto_descuento()
                                        ));

                                lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoPorLinea(
                                        lead.getOrden_detalle_montosubtotal(),
                                        formulasController.applyDiscountPercentageForLine(
                                                //Variable 1 Monto Total Linea Sin IGV
                                                //lead.getOrden_detalle_montototallinea(),
                                                lead.getOrden_detalle_montosubtotal(),
                                                //Variable 2  Porcentaje Descuento
                                                (lead.getOrden_detalle_descuentocontado())
                                        ),
                                        SesionEntity.Impuesto
                                ));
                                //holder.tv_orden_detalle_igv.setText(format.format(Double.parseDouble(lead.getOrden_detalle_monto_igv())));
                                holder.tv_orden_detalle_igv.setText(Convert.numberForViewDecimals(lead.getOrden_detalle_monto_igv(),2));
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter-lead.getOrden_detalle_descuentocontado()" + lead.getOrden_detalle_descuentocontado());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter-lead.getOrden_detalle_montosubtotal(): " + lead.getOrden_detalle_montosubtotal());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter-lead.getOrden_detalle_monto_descuento(): " + lead.getOrden_detalle_monto_descuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter-SesionEntity.Impuesto: " + SesionEntity.Impuesto);
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter-lead.getOrden_detalle_monto_igv()" + lead.getOrden_detalle_monto_igv());

                            } else {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable(R.drawable.borde_editext_ov_negro);
                                //Drawable borde_editext_orden_venta_detalle_inhabilitado=(Drawable) R.drawable.borde_editext_orden_venta_detalle_inhabilitado;
                                holder.et_porcentaje_descuento_contado.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
                                holder.et_porcentaje_descuento_contado.setTextColor(Color.parseColor("#808080"));
                                holder.et_porcentaje_descuento_contado.setEnabled(false);
                                lead.setOrden_detalle_chk_descuentocontado(false);
                                //holder.et_porcentaje_descuento_contado.setText("0");
                                lead.setOrden_detalle_descuentocontado(holder.et_porcentaje_descuento_contado.getText().toString());
                                lead.setOrden_detalle_porcentaje_descuento(
                                        String.valueOf(
                                                Integer.parseInt(lead.getOrden_detalle_porcentaje_descuento())
                                                // -
                                                // Integer.parseInt(lead.getOrden_detalle_descuentocontado())
                                        ));
                                lead.setOrden_detalle_monto_descuento(
                                        //Formula Para Calcular el Monto de Descuento
                                        formulasController.applyDiscountPercentageForLine(
                                                //Variable 1 Monto Total Linea Sin IGV
                                                //lead.getOrden_detalle_montototallinea(),
                                                lead.getOrden_detalle_montosubtotal(),
                                                //Variable 2  Porcentaje Descuento
                                                (lead.getOrden_detalle_porcentaje_descuento())
                                        ));
                                lead.setOrden_detalle_montosubtotal(
                                        formulasController.getTotalPerLine
                                                (lead.getOrden_detalle_precio_unitario(),
                                                        lead.getOrden_detalle_cantidad()));
                                //Monto subtotal con descuento
                                lead.setOrden_detalle_montosubtotalcondescuento(
                                        //Formula para Calcular el Monto Sub Total Con Descuento
                                        formulasController.CalcularMontoTotalconDescuento(
                                                //Variable 1 Monto Total Linea Sin IGV
                                                lead.getOrden_detalle_montosubtotal(),
                                                //Variable 2  Monto Descuento
                                                lead.getOrden_detalle_monto_descuento()
                                        ));
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_porcentaje_descuento-true" + lead.getOrden_detalle_porcentaje_descuento());
                            }
                            Log.e("REOS", "ListaOrdenVentaDetalleAdapter:holder.resultado-true");

                        } else {
                            Log.e("REOS", "ListaOrdenVentaDetalleAdapter:holder.resultado-false");
                            alertaValidacionContadoDescuento().show();
                            holder.chk_descuento_contado.setChecked(false);
                            //holder.et_porcentaje_descuento_contado.setText("0");
                        }

                        //
                        holder.tv_orden_detalle_total.setText(lead.getOrden_detalle_montosubtotal());
                        Log.e("REOS", "ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_porcentaje_descuento-true" + lead.getOrden_detalle_porcentaje_descuento());
                        Log.e("REOS", "ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_monto_descuento-true" + lead.getOrden_detalle_monto_descuento());
                        Log.e("REOS", "ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_montosubtotal-true" + lead.getOrden_detalle_montosubtotal());
                        Log.e("REOS", "ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_montosubtotalcondescuento-true" + lead.getOrden_detalle_montosubtotalcondescuento());

                        for (int i = 0; i < OrdenVentaDetalleView.listadoProductosAgregados.size(); i++) {
                            if (i == (Integer.parseInt(lead.getOrden_detalle_item()) - 1)) {
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:chk_descuentocontado-ingresoabucle");
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_porcentaje_descuento((lead.getOrden_detalle_porcentaje_descuento()));
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_descuento((lead.getOrden_detalle_monto_descuento()));
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_chk_descuentocontado(lead.isOrden_detalle_chk_descuentocontado());
                                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_descuentocontado(lead.getOrden_detalle_descuentocontado());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_porcentaje_descuento: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_porcentaje_descuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_monto_descuento: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_monto_descuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotal: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotal());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotalcondescuento: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).getOrden_detalle_montosubtotalcondescuento());
                                Log.e("REOS", "ListaOrdenVentaDetalleAdapter:OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_chk_descuentocontado: " + OrdenVentaDetalleView.listadoProductosAgregados.get(i).isOrden_detalle_chk_descuentocontado());
                            }
                        }
                        ActualizaListaOrdenDetallePromocion(lead);
                        ActualizarResumenOrdenVenta();
                        if ((lead.getOrden_detalle_promocion_habilitada().equals("0") || lead.getOrden_detalle_terminopago_id().equals("47")) || (holder.chk_descuento_contado.isChecked() && lead.getOrden_detalle_lista_promocion_cabecera() == null)) {
                            Resources res = getContext().getResources(); // need this to fetch the drawable
                            Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_24);
                            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                            holder.imv_consultar_promocion_cabecera.setEnabled(false);
                            //holder.chk_descuento_contado.setChecked(false);
                        } else {
                            Resources res = getContext().getResources(); // need this to fetch the drawable
                            Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                            holder.imv_consultar_promocion_cabecera.setEnabled(true);
                            //holder.chk_descuento_contado.setChecked(true);
                        }
                        Log.e("REOS", "ListaOrdenVentaDetalleAdapter:formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead): " + formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                        //holder.tv_orden_detalle_igv.setText(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                    }
                    else
                    {
                        alertaValidacionEditextMayorContadoDescuento().show();
                    }
                }
                return procesado;
            }
        });


        if(lead.getOrden_detalle_cantidad().isEmpty())
        {
            holder.tv_orden_detalle_galon_acumulado.setText(String.valueOf(Float.parseFloat("0")*Float.parseFloat(lead.getOrden_detalle_gal())));
        }
        else
        {
            holder.tv_orden_detalle_galon_acumulado.setText(String.valueOf(Float.parseFloat(lead.getOrden_detalle_cantidad())*Float.parseFloat(lead.getOrden_detalle_gal())));
        }
        if(lead.getOrden_detalle_promocion_habilitada().equals("0")||lead.getOrden_detalle_terminopago_id().equals("47")||holder.chk_descuento_contado.isChecked())
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_24);
            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
            holder.imv_consultar_promocion_cabecera.setEnabled(false);
            //holder.chk_descuento_contado.setChecked(false);
        }
        else
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
            holder.imv_consultar_promocion_cabecera.setEnabled(true);
            //holder.chk_descuento_contado.setChecked(true);
        }
        if(!(lead.getOrden_detalle_lista_promocion_cabecera()==null))
        {

            String calculodescuento=formulasController.CalcularPorcentajeDescuentoPorLinea(
                    lead.getOrden_detalle_lista_promocion_cabecera(),
                    lead.getOrden_detalle_descuentocontado()

            );
            Log.e("REOS","ListaOrdenVentaDetalleAdapter:calculodescuento:" + calculodescuento);
            holder.tv_orden_detalle_porcentaje_descuento.setText((calculodescuento));
            lead.setOrden_detalle_porcentaje_descuento(calculodescuento);
            lead.setOrden_detalle_monto_descuento(
                    //Formula Para Calcular el Monto de Descuento
                    formulasController.applyDiscountPercentageForLine(
                            //Variable 1 Monto Total Linea Sin IGV
                            //lead.getOrden_detalle_montototallinea(),
                            lead.getOrden_detalle_montosubtotal(),
                            //Variable 2  Porcentaje Descuento
                            (calculodescuento)
                    ));
            Log.e("REOS","ListaOrdenVentaDetalleAdapter:lead.getOrden_detalle_monto_descuento:" + lead.getOrden_detalle_monto_descuento());
            //Monto subtotal sin descuento
            lead.setOrden_detalle_montosubtotal(
                    formulasController.getTotalPerLine
                            (lead.getOrden_detalle_precio_unitario(),
                                    lead.getOrden_detalle_cantidad()));
            //Monto subtotal con descuento
            lead.setOrden_detalle_montosubtotalcondescuento(
                    //Formula para Calcular el Monto Sub Total Con Descuento
                    formulasController.CalcularMontoTotalconDescuento(
                            //Variable 1 Monto Total Linea Sin IGV
                            lead.getOrden_detalle_montosubtotal(),
                            //Variable 2  Monto Descuento
                            lead.getOrden_detalle_monto_descuento()
                    ));

            holder.tv_orden_detalle_total.setText(lead.getOrden_detalle_montosubtotal());
            //Actualiza Descuentos en Linea

            for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++)
            {
                if (i == (Integer.parseInt(lead.getOrden_detalle_item()) - 1))
                {
                    OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_porcentaje_descuento((lead.getOrden_detalle_porcentaje_descuento()));
                    OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_descuento((lead.getOrden_detalle_monto_descuento()));
                    OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                    OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                    OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_descuentocontado (lead.getOrden_detalle_descuentocontado());
                }
            }
        }else
        {

            Log.e("REOS", "ListaOrdenVentaDetalleAdapter-LineaSinPromocion-Inicio");
            holder.tv_orden_detalle_porcentaje_descuento.setText(lead.getOrden_detalle_porcentaje_descuento());
            holder.tv_orden_detalle_total.setText(lead.getOrden_detalle_montosubtotal());
            lead.setOrden_detalle_montosubtotalcondescuento(
                    //Formula para Calcular el Monto Total Con Descuento
                    formulasController.CalcularMontoTotalconDescuento(
                            //Variable 1 Monto Total Linea Sin IGV
                            lead.getOrden_detalle_montosubtotal(),
                            //Variable 2  Monto Descuento
                            lead.getOrden_detalle_monto_descuento()
                    ));
        }
        ActualizaListaOrdenDetallePromocion(lead);
        ActualizarResumenOrdenVenta();
        //Actualiza Datos al Adjuntar Promocion
        for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++)
        {
            if(i==(Integer.parseInt(lead.getOrden_detalle_item())-1))
            {
                lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                lead.setOrden_detalle_montototallinea(
                        String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                lead.getOrden_detalle_montosubtotal(),
                                lead.getOrden_detalle_monto_descuento()
                                ))
                                        +
                                        Float.parseFloat( lead.getOrden_detalle_monto_igv())
                        ));
                //prueba
                //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_item(lead.getOrden_detalle_item());
                //
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(
                        //String.valueOf (Float.parseFloat (holder.tv_orden_detalle_total.getText().toString())+Float.parseFloat(lead.getOrden_detalle_monto_igv()))
                        lead.getOrden_detalle_montototallinea()
                );
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_cantidad(valor);
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
            }
        }

        holder.tv_orden_detalle_igv.setText(lead.getOrden_detalle_monto_igv());

        holder.et_orden_detalle_cantidad.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    String valor="0";
                    if(v.length()==0)
                    {
                        valor="0";
                    }
                    else {
                        valor=String.valueOf(Integer.parseInt(v.getText().toString()));
                    }
                    lead.setOrden_detalle_cantidad(valor);

                    //holder.tv_orden_detalle_total.setText(
                    lead.setOrden_detalle_montosubtotal(formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(),lead.getOrden_detalle_cantidad())
                            //String.valueOf (
                            //Float.valueOf ((lead.getOrden_detalle_cantidad()))*Float.valueOf(String.valueOf(lead.getOrden_detalle_precio_unitario())))
                    );
                    //holder.tv_orden_detalle_total.setText(lead.getOrden_detalle_montototallinea());
                    lead.setOrden_detalle_montototallinea(
                            String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                    lead.getOrden_detalle_montosubtotal(),
                                    lead.getOrden_detalle_monto_descuento()
                                    ))
                                            +
                                            Float.parseFloat( lead.getOrden_detalle_monto_igv())
                            ));
                    holder.tv_orden_detalle_total.setText(
                            lead.getOrden_detalle_montosubtotal()
                    );
                    holder.tv_orden_detalle_galon_acumulado.setText(
                            String.valueOf(Float.parseFloat(lead.getOrden_detalle_cantidad())*Float.parseFloat(lead.getOrden_detalle_gal()))
                    );

                    for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++)
                    {
                        if(i==(Integer.parseInt(lead.getOrden_detalle_item())-1))
                        {
                            lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                            lead.setOrden_detalle_montototallinea(
                                    String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                            lead.getOrden_detalle_montosubtotal(),
                                            lead.getOrden_detalle_monto_descuento()
                                            ))
                                                    +
                                                    Float.parseFloat( lead.getOrden_detalle_monto_igv())
                                    ));

                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(
                                    //String.valueOf (Float.parseFloat (holder.tv_orden_detalle_total.getText().toString())+Float.parseFloat(lead.getOrden_detalle_monto_igv()))
                                    lead.getOrden_detalle_montototallinea()
                            );
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_cantidad(valor);
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
                        }
                    }
                    if (listaPromocionCabecera.isEmpty()){

                        listaPromocionCabecera=promocionCabeceraSQLiteDao.ObtenerPromocionCabecera(
                                SesionEntity.compania_id,
                                SesionEntity.fuerzatrabajo_id,
                                SesionEntity.usuario_id,
                                lead.getOrden_detalle_producto_id(),
                                lead.getOrden_detalle_umd(),
                                lead.getOrden_detalle_cantidad(),
                                SesionEntity.contado,
                                lead.getOrden_detalle_terminopago_id(),
                                lead.getOrden_detalle_cardcode()
                        );

                    }
                    actualizarlistapromocioncabecera(lead);
                    if (!listaPromocionCabecera.isEmpty()){
                        Resources res = getContext().getResources(); // need this to fetch the drawable
                        Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_blue_24);
                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                        holder.imv_consultar_promocion_cabecera.setEnabled(true);
                        lead.setOrden_detalle_promocion_habilitada("1");

                    }
                    else {
                        Resources res = getContext().getResources(); // need this to fetch the drawable
                        Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_24);
                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                        holder.imv_consultar_promocion_cabecera.setEnabled(false);
                        lead.setOrden_detalle_promocion_habilitada("0");
                    }
                    // Ocultar teclado virtual
                    InputMethodManager imm =
                            (InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    procesado = true;

                    //OrdenVentaDetalleView.listaPromocionCabecera=listaPromocionCabecera;
                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity= new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);
                    fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));
                    ActualizaListaOrdenDetallePromocion(lead);

                }
                return procesado;
            }
        });

        holder.imv_consultar_promocion_cabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities= new ArrayList<>();
                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Log.e("REOS","ListaOrdenVentaAdapter-holder.imv_consultar_promocion_cabecera.lead.getOrden_detalle_producto_id():"+lead.getOrden_detalle_producto_id());
                listaOrdenVentaDetalleEntities.add(lead);
                Object [] listaobjetos=new Object[2];
                //OrdenVentaDetalleView.listaPromocionCabecera=new ArrayList<>();
                actualizarlistapromocioncabecera(lead);
                listaobjetos[0]=OrdenVentaDetalleView.listaPromocionCabecera;
                listaobjetos[1]=listaOrdenVentaDetalleEntities;
                transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceEnviaListaPromocion(listaobjetos));

            }});

        holder.imv_eliminar_orden_venta_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEliminarLina( (position)).show();
            }
        });
        if((!(lead.getOrden_detalle_lista_promocion_cabecera()==null))&&holder.layout.getChildCount()==0)
        {
            itemChecked[position] = true;
            FormulasController formulasController=new FormulasController(getContext());

            for(int i=0;i<lead.getOrden_detalle_lista_promocion_cabecera().size();i++)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                int id = R.layout.layout_lista_orden_venta_detalle_lista_promocion;
                RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(id, null, false);
                TextView tv_orden_venta_detalle_lista_promocion_id = (TextView) relativeLayout.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_id);
                TextView tv_orden_venta_detalle_lista_promocion_promocion_id = (TextView) relativeLayout.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_promocion_id);
                TextView tv_orden_venta_detalle_lista_promocion_cantidad_promocion = (TextView) relativeLayout.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_cantidad_promocion);
                TextView tv_orden_venta_detalle_lista_promocion_preciobase = (TextView) relativeLayout.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_preciobase);
                TextView tv_orden_venta_detalle_lista_promocion_cantidad_lineas = (TextView) relativeLayout.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_cantidad_linas);
                TextView tv_orden_venta_detalle_lista_promocion_porcentaje_descuento = (TextView) relativeLayout.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_porcentaje_descuento);

                //tv_orden_venta_detalle_lista_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getLista_promocion_id());
                tv_orden_venta_detalle_lista_promocion_id.setText(String.valueOf(i+1));
                //tv_orden_venta_detalle_lista_promocion_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getPromocion_id());
                tv_orden_venta_detalle_lista_promocion_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getCantidadcompra());
                tv_orden_venta_detalle_lista_promocion_cantidad_promocion.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getCantidadpromocion());
                //holder.tv_orden_detalle_precio.setText(new BigDecimal(lead.getOrden_detalle_stock()).setScale(2, RoundingMode.HALF_UP).toString();
                tv_orden_venta_detalle_lista_promocion_preciobase.setText(
                        Convert.numberForViewDecimals(
                        (formulasController.ObtenerCalculoPromocionDetalle(
                        lead.getOrden_detalle_lista_promocion_cabecera(), i
                        )).toString(),2));
                tv_orden_venta_detalle_lista_promocion_cantidad_lineas.setText(String.valueOf(
                        formulasController.ObtenerCantidadLineasPromocionDetalle(lead.getOrden_detalle_lista_promocion_cabecera(),
                                i
                        )));
                tv_orden_venta_detalle_lista_promocion_porcentaje_descuento.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getDescuento());
                holder.layout.addView(relativeLayout);
            }
        }else
        {
            itemChecked[position] = false;

        }
        return convertView;
    }


    private Dialog alertaEliminarLina(int Linea) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_alert_dialog_orden_venta_detalle);
        TextView textTitle = dialog.findViewById(R.id.text_orden_venta_detalle);
        textTitle.setText("Advertencia!!!");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj_orden_venta_detalle);
        textMsj.setText("Seguro que desea eliminar la Linea "+String.valueOf(Linea+1)+" de la Orden de Venta?");
        ImageView image = (ImageView) dialog.findViewById(R.id.image_orden_venta_detalle);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK_orden_venta_detalle);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel_orden_venta_detalle);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceEliminaLineaOrdenVenta(Linea));
                dialog.dismiss();
            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
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
        TextView tv_orden_detalle_item;
        TextView tv_orden_detalle_producto;
        TextView tv_orden_detalle_umd;
        TextView tv_orden_detalle_stock;
        EditText et_orden_detalle_cantidad;
        TextView tv_orden_detalle_precio;
        TextView tv_orden_detalle_porcentaje_descuento;
        TextView tv_orden_detalle_igv;
        TextView tv_orden_detalle_total;
        TextView tv_orden_detalle_galon_unitario;
        TextView tv_orden_detalle_galon_acumulado;
        //ListView lv_promociondetalle;
        ImageView imv_consultar_promocion_cabecera;
        ImageView imv_eliminar_orden_venta_detalle;
        CheckBox chk_descuento_contado;
        EditText et_porcentaje_descuento_contado;
        TableRow tr_dsct_cont_detail;
        ViewGroup layout;

    }
    public boolean ValidarPorcentajeDescuentoContado(String valoreditext,String producto_id)
    {
        String porcdesccont="";
        float editextDescount;
        if(valoreditext.isEmpty()||valoreditext=="")
        {
            editextDescount=0;
        }
        else
            {
                editextDescount=Float.parseFloat(valoreditext);
            }
        ArrayList<ListaPrecioDetalleSQLiteEntity> listaPrecioDetalleSQLiteEntities=new ArrayList<>();
        ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(getContext());
        listaPrecioDetalleSQLiteEntities=listaPrecioDetalleSQLiteDao.ObtenerListaPrecioDetalleporID(producto_id,SesionEntity.TipoListaPrecio);

        for(int i=0;i<listaPrecioDetalleSQLiteEntities.size();i++)
        {
            porcdesccont=listaPrecioDetalleSQLiteEntities.get(i).getPorcentaje_descuento();
        }
        boolean resultado=false;
        if(editextDescount>Float.parseFloat(SesionEntity.U_VIS_CashDscnt ))
        {
            resultado=false;
        }else
        {
            resultado=true;
        }
        return resultado;
    }

    public void ActualizaListaOrdenDetallePromocion(ListaOrdenVentaDetalleEntity lead)
    {
        Log.e("REOS","ListaOrdenVentaAdapter-Inicia-ActualizaListaOrdenDetallePromocion");
        Log.e("REOS","ListaOrdenVentaAdapter-lead.getOrden_detalle_item:"+lead.getOrden_detalle_item());
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetallePromocionEntity> arraylistListaOrdenVentaDetalleEntityPromocion = new ArrayList<>();
        ListaOrdenVentaDetallePromocionEntity listaOrdenVentaDetallePromocionEntity;
        int orden_detalle_promocion_item=0,orden_detalle_promocion_cantidad=0,orden_detalle_promocion_linea_referencia=0, orden_detalle_promocion_referencia_item=FormulasController.ObtenerCantidadListaPromocion(lead);


        boolean chk_descuentocontadoaplicado=false;
        String contadodescuento="0";
        //Evalua si la promocion de la cabecera no esta vacia


        if(Float.parseFloat(lead.getOrden_detalle_descuentocontado())>0
                &&lead.isOrden_detalle_chk_descuentocontado()&&
                (!lead.isOrden_detalle_chk_descuentocontado_aplicado())
        )
        {
            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
            contadodescuento=lead.getOrden_detalle_descuentocontado();
            chk_descuentocontadoaplicado=true;

        }
        else
        {
            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
            contadodescuento="0";
            chk_descuentocontadoaplicado=false;
            //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
            //{
            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones-2dobuclecumplecondiciones");
            //chk_descuentocontadoaplicado=true;
            //}

        }

        if(!lead.isOrden_detalle_chk_descuentocontado_aplicado()) {

            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Lead-sinChkdescuentoaplicado");
            if (!(lead.getOrden_detalle_lista_promocion_cabecera() == null)) {
                //Recorre la Lista de Promociones
                for (int a = 0; a < lead.getOrden_detalle_lista_promocion_cabecera().size(); a++) {
                    orden_detalle_promocion_item++;
                    orden_detalle_promocion_referencia_item++;

                    //Inserta Linea Padre
                    orden_detalle_promocion_cantidad = orden_detalle_promocion_cantidad + (
                            Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadcompra()) *
                                    Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadpromocion()));
                    orden_detalle_promocion_linea_referencia = orden_detalle_promocion_item;
                    listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getProducto_id();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getProducto();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getUmd();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad =
                            String.valueOf(Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadcompra()) *
                                    Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadpromocion()));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                            (listaOrdenVentaDetallePromocionEntity.getOrden_detalle_precio_unitario(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                    //listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                            Float.parseFloat(
                                    // lead.getOrden_detalle_porcentaje_descuento()
                                    lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento()
                            ) +
                                    Float.parseFloat(contadodescuento));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_descuento =
                            //Formula Para Calcular el Monto de Descuento
                            formulasController.applyDiscountPercentageForLine(
                                    //Variable 1 Monto Total Linea Sin IGV
                                    //lead.getOrden_detalle_montototallinea(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    //Variable 2  Porcentaje Descuento
                                    (listaOrdenVentaDetallePromocionEntity.getOrden_detalle_porcentaje_descuento()));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_igv = formulasController.CalcularMontoImpuestoPorLinea(
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento(),
                            SesionEntity.Impuesto
                    );
                    //listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf(format.format(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea =
                            Convert.numberForViewDecimals(
                                                            String.valueOf(
                                                            (Float.parseFloat(
                                                            formulasController.CalcularMontoTotalconDescuento(
                                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                                                            ))
                                                                    +
                                                                    Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv())
                                                            ))
                                                            ,2)
                                                            ;

                    listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_promocion_cabecera = null;
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_habilitada = "0";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_gal = lead.getOrden_detalle_gal();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = String.valueOf(Float.parseFloat(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadcompra()) * Float.parseFloat(lead.getOrden_detalle_gal()));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotalcondescuento = formulasController.CalcularMontoTotalconDescuento(
                            //Variable 1 Monto Total Linea Sin IGV
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                            //Variable 2  Monto Descuento
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                    );
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_orden_detalle_promocion = null;
                    Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Lead-orden_detalle_promocion_item"+orden_detalle_promocion_item);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_item = String.valueOf(orden_detalle_promocion_item);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_padre = String.valueOf(orden_detalle_promocion_item);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = String.valueOf(orden_detalle_promocion_referencia_item);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id();
                    Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Lead-lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id()"+lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id());
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;


                    //Registra Detalle de Linea Padre
                    arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                    Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Fin Registro Linea Padre");
                    //Inserta Lineas de Detalle
                    for (int b = 0; b < lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().size(); b++) {
                        if (!lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getProducto_id().equals("DESCUENTO")) {
                            //orden_detalle_promocion_item++;
                            listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getProducto_id();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getProducto();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getUmd();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = "0";
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad =
                                    String.valueOf(Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getCantidad()) *
                                            Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadpromocion()));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase();
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPrODUCTO()"+lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getProducto_id());
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase()"+lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase());
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad()"+listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                                    (lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase(),
                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = "100";
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_descuento =
                                    //Formula Para Calcular el Monto de Descuento
                                    formulasController.applyDiscountPercentageForLine(
                                            //Variable 1 Monto Total Linea Sin IGV
                                            //lead.getOrden_detalle_montototallinea(),
                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                            //Variable 2  Porcentaje Descuento
                                            (listaOrdenVentaDetallePromocionEntity.getOrden_detalle_porcentaje_descuento()));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_igv = formulasController.CalcularMontoImpuestoPorLinea(
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento(),
                                    SesionEntity.Impuesto
                            );
                            //listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf(format.format(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf((Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                                    ))
                                            +
                                            Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv())
                            ));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_promocion_cabecera = null;
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_habilitada = "0";
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_gal = "0";
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = "0";
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotalcondescuento = formulasController.CalcularMontoTotalconDescuento(
                                    //Variable 1 Monto Total Linea Sin IGV
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    //Variable 2  Monto Descuento
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                            );
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_orden_detalle_promocion = null;
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_item = String.valueOf(orden_detalle_promocion_item);
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-orden_detalle_promocion_item"+String.valueOf(orden_detalle_promocion_item));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_padre = String.valueOf(orden_detalle_promocion_item) ;
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-orden_detalle_linea_padre"+String.valueOf(orden_detalle_promocion_item));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = String.valueOf(orden_detalle_promocion_referencia_item);
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-orden_detalle_linea_referencia_padre"+String.valueOf(orden_detalle_promocion_item));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                            //Registra Linea de Promocion
                            arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                            orden_detalle_promocion_referencia_item++;
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Fin Registro Linea DESCUENTO");
                        }
                    }
                }

                //Inserta cantidad Restante de la Linea Padre
                //Si la cantidad de Promocion es menor a la cantidad de la Linea
                if (orden_detalle_promocion_cantidad < Integer.parseInt(lead.getOrden_detalle_cantidad())) {

                    orden_detalle_promocion_referencia_item++;
                    int cantidadlineaordendetalle = 0, cantidadlineaordendetallepromocion = 0, cantidadrestantelineaordendetalle = 0;
                    cantidadlineaordendetalle = Integer.parseInt(lead.getOrden_detalle_cantidad());
                    //cantidadlineaordendetallepromocion=Integer.parseInt(arraylistListaOrdenVentaDetalleEntityPromocion.get(c).getOrden_detalle_cantidad());
                    cantidadrestantelineaordendetalle = cantidadlineaordendetalle - orden_detalle_promocion_cantidad;
                    listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_producto_id();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_producto();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_umd();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad = String.valueOf(cantidadrestantelineaordendetalle);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                            (lead.getOrden_detalle_precio_unitario(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                    //listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = "0";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                            //Integer.parseInt(lead.getOrden_detalle_porcentaje_descuento())+
                            Float.parseFloat(contadodescuento));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_descuento =
                            //Formula Para Calcular el Monto de Descuento
                            formulasController.applyDiscountPercentageForLine(
                                    //Variable 1 Monto Total Linea Sin IGV
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    //Variable 2  Porcentaje Descuento
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_porcentaje_descuento());
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_igv = formulasController.CalcularMontoImpuestoPorLinea(
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento(),
                            SesionEntity.Impuesto
                    );
                    //listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf(format.format(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf((Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                            ))
                                    +
                                    Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv())
                    ));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_promocion_cabecera = null;
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_habilitada = "0";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_gal = lead.getOrden_detalle_gal();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = String.valueOf(Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad()) * Float.parseFloat(lead.getOrden_detalle_gal()));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotalcondescuento = formulasController.CalcularMontoTotalconDescuento(
                            //Variable 1 Monto Total Linea Sin IGV
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                            //Variable 2  Monto Descuento
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                    );
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_orden_detalle_promocion = null;
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_item = String.valueOf(orden_detalle_promocion_item);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_padre = "0";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = "0";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = "0";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                    arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                    Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Fin Linea DESCUENTO");

                }
            }
            //Si la cantidad de Promocion es mayor a la cantidad de la Linea
            else {
                if (lead.getOrden_detalle_cantidad().equals("")) {
                    lead.setOrden_detalle_cantidad("");
                }
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Inicia Si la cantidad de Promocion es mayor a la cantidad de la Linea");
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-chk_descuentocontadoaplicado" + String.valueOf(chk_descuentocontadoaplicado));
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado_aplicado()" + String.valueOf(lead.isOrden_detalle_chk_descuentocontado_aplicado()));
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-contadodescuento: " + contadodescuento);
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado(): " + lead.isOrden_detalle_chk_descuentocontado());
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_descuentocontado(): " + lead.getOrden_detalle_descuentocontado());
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_porcentaje_descuento(): " + lead.getOrden_detalle_porcentaje_descuento());
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado_aplicado(): " + lead.isOrden_detalle_chk_descuentocontado_aplicado());
                orden_detalle_promocion_item++;
                orden_detalle_promocion_referencia_item++;
                listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_producto_id();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_producto();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_umd();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad = lead.getOrden_detalle_cantidad();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                        (lead.getOrden_detalle_precio_unitario(),
                                lead.getOrden_detalle_cantidad());
                //listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = lead.getOrden_detalle_porcentaje_descuento();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                        Float.parseFloat(lead.getOrden_detalle_porcentaje_descuento()) +
                                Float.parseFloat(contadodescuento));
                listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_descuento =
                        //Formula Para Calcular el Monto de Descuento
                        formulasController.applyDiscountPercentageForLine(
                                //Variable 1 Monto Total Linea Sin IGV
                                listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                //Variable 2  Porcentaje Descuento
                                listaOrdenVentaDetallePromocionEntity.getOrden_detalle_porcentaje_descuento());
                listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_igv = formulasController.CalcularMontoImpuestoPorLinea(
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento(),
                        SesionEntity.Impuesto
                );
                //listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf(format.format(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = String.valueOf((Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                        ))
                                +
                                Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv())
                ));
                listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_promocion_cabecera = null;
                listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_habilitada = "0";
                listaOrdenVentaDetallePromocionEntity.orden_detalle_gal = lead.getOrden_detalle_gal();

                if (lead.getOrden_detalle_cantidad().equals("")) {
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = String.valueOf(Float.parseFloat("0") * Float.parseFloat(lead.getOrden_detalle_gal()));
                } else {
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = String.valueOf(Float.parseFloat(lead.getOrden_detalle_cantidad()) * Float.parseFloat(lead.getOrden_detalle_gal()));
                }


                listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotalcondescuento = formulasController.CalcularMontoTotalconDescuento(
                        //Variable 1 Monto Total Linea Sin IGV
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                        //Variable 2  Monto Descuento
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                );
                listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_orden_detalle_promocion = null;
                listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_item = String.valueOf(orden_detalle_promocion_item);
                listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_padre = "0";
                listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = "0";
                listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = "0";
                listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-FIN Si la cantidad de Promocion es mayor a la cantidad de la Linea");
            }

            for (int d = 0; d < OrdenVentaDetalleView.listadoProductosAgregados.size(); d++) {
                Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-OrdenVentaDetalleView.listadoProductosAgregados.size():" + OrdenVentaDetalleView.listadoProductosAgregados.size());
                if (d == (Integer.parseInt(lead.getOrden_detalle_item()) - 1)) {
                    Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-CoincideLinea");
                    //Actualiza LineaOrdenVentaDetalle
                    if (OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion() != null) {
                        Log.e("REOS", "ListaOrdenVentaAdapter-OrdenVentaDetalleView.ActualizaListaOrdenDetallePromocion-OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size()-Antes:" + OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size());
                    }
                    OrdenVentaDetalleView.listadoProductosAgregados.get(d).setOrden_detalle_lista_orden_detalle_promocion(
                            arraylistListaOrdenVentaDetalleEntityPromocion
                    );
                    //OrdenVentaDetalleView.listadoProductosAgregados.get(d).setOrden_detalle_chk_descuentocontado(lead.isOrden_detalle_chk_descuentocontado());
                    OrdenVentaDetalleView.listadoProductosAgregados.get(d).setOrden_detalle_chk_descuentocontado_aplicado(chk_descuentocontadoaplicado);

                    Log.e("REOS", "ListaOrdenVentaAdapter-OrdenVentaDetalleView.ActualizaListaOrdenDetallePromocion-OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size()-Despues:" + OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size());
                }
            }
        }
        else
        {
            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Lead-conChkdescuentoaplicado");
        }
        Log.e("REOS","ListaOrdenVentaAdapter-Finaliza-ActualizaListaOrdenDetallePromocion");
    }

    public void actualizarlistapromocioncabecera(ListaOrdenVentaDetalleEntity lead)
    {
        listaPromocionCabecera=promocionCabeceraSQLiteDao.ObtenerPromocionCabeceraUnidad(

                SesionEntity.compania_id,
                SesionEntity.fuerzatrabajo_id,
                SesionEntity.usuario_id,
                lead.getOrden_detalle_producto_id(),
                lead.getOrden_detalle_umd(),
                lead.getOrden_detalle_cantidad(),
                SesionEntity.contado,
                lead.getOrden_detalle_terminopago_id(),
                lead.getOrden_detalle_cardcode()
        );

        if (listaPromocionCabecera.isEmpty()){

            listaPromocionCabecera=promocionCabeceraSQLiteDao.ObtenerPromocionCabecera(
                    SesionEntity.compania_id,
                    SesionEntity.fuerzatrabajo_id,
                    SesionEntity.usuario_id,
                    lead.getOrden_detalle_producto_id(),
                    lead.getOrden_detalle_umd(),
                    lead.getOrden_detalle_cantidad(),
                    SesionEntity.contado,
                    lead.getOrden_detalle_terminopago_id(),
                    lead.getOrden_detalle_cardcode()
            );

        }

        OrdenVentaDetalleView.listaPromocionCabecera=listaPromocionCabecera;
    }

    public Dialog alertaValidacionContadoDescuento() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("La Linea tiene adjunta una Promocion o Producto no permitido para el Descuento");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText("OK");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  dialog;
    }
    public Dialog alertaValidacionEditextMayorContadoDescuento() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog);
        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        final TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("El % de descuento ingresado, es mayor al Permitido de: %"+SesionEntity.U_VIS_CashDscnt);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText("OK");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  dialog;
    }
    public void ActualizarResumenOrdenVenta()
    {
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizarResumen());
    }


}

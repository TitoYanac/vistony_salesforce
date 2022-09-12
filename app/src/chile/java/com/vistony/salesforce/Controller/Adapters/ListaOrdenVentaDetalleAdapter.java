package com.vistony.salesforce.Controller.Adapters;

import static com.vistony.salesforce.Controller.Utilitario.Convert.getTotaLine;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetallePromocionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.OrdenVentaDetalleView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.sentry.Sentry;


public class ListaOrdenVentaDetalleAdapter extends ArrayAdapter<ListaOrdenVentaDetalleEntity> {

    private android.content.Context Context;
    private LayoutInflater inflater;
    private FragmentManager fragmentManager;
    public OrdenVentaDetalleView ordenVentaDetalleView;
    private PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(getContext());
    private ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabecera=new ArrayList<>();
    private ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();

    private FormulasController formulasController=new FormulasController(getContext());
    private boolean[] itemChecked;
    private DecimalFormat format = new DecimalFormat("#0.00");

    public ListaOrdenVentaDetalleAdapter(android.content.Context context, List<ListaOrdenVentaDetalleEntity> objects) {

        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);
        this.itemChecked=new boolean[objects.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ListaOrdenVentaDetalleAdapter.ViewHolder holder;

        if (null == convertView) {

            convertView = inflater.inflate(R.layout.layout_lista_orden_venta_detalle_chile,parent,false);

            holder = new ListaOrdenVentaDetalleAdapter.ViewHolder();
            holder.tv_orden_detalle_item = (TextView) convertView.findViewById(R.id.tv_orden_detalle_item);
            holder.tv_orden_detalle_producto = (TextView) convertView.findViewById(R.id.tv_orden_detalle_producto);
            holder.tv_orden_detalle_stock = (TextView) convertView.findViewById(R.id.tv_orden_detalle_stock);
            holder.et_orden_detalle_cantidad = (EditText) convertView.findViewById(R.id.et_orden_detalle_cantidad);
            holder.editDspPorcentaje = (EditText) convertView.findViewById(R.id.editDspPorcentaje);
            holder.tv_orden_detalle_precio = (TextView) convertView.findViewById(R.id.tv_orden_detalle_precio);
            holder.tv_orden_detalle_porcentaje_descuento = (TextView) convertView.findViewById(R.id.tv_orden_detalle_porcentaje_descuento);
            holder.tv_orden_detalle_igv = (TextView) convertView.findViewById(R.id.tv_orden_detalle_igv);
            holder.tv_orden_detalle_total = (TextView) convertView.findViewById(R.id.tv_orden_detalle_total);
            holder.tv_orden_detalle_galon_unitario = (TextView) convertView.findViewById(R.id.tv_orden_detalle_galon_unitario);
            holder.tv_orden_detalle_galon_acumulado = (TextView) convertView.findViewById(R.id.tv_orden_detalle_galon_acumulado);
            holder.imv_consultar_promocion_cabecera=(ImageView) convertView.findViewById(R.id.imv_consultar_promocion_cabecera);
            holder.imv_eliminar_orden_venta_detalle=(ImageView) convertView.findViewById(R.id.imv_eliminar_orden_venta_detalle);
            holder.chk_descuento_contado=(CheckBox) convertView.findViewById(R.id.chk_descuento_contado);
            holder.tv_porcentaje_descuento_contado=(TextView) convertView.findViewById(R.id.tv_porcentaje_descuento_contado);

            holder.layout=(ViewGroup) convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaOrdenVentaDetalleEntity lead = getItem(position);
        holder.layout.removeAllViews();
        listaOrdenVentaDetalleEntities.add(lead);
        // Setup.

        //Utilitario utilitario=new Utilitario();
        //Utilitario.disabledEditText(holder.editDspPorcentaje);
        //holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_gray);
        holder.tv_orden_detalle_item.setText(lead.getOrden_detalle_item()+")");
        holder.tv_orden_detalle_producto.setText(lead.getOrden_detalle_producto_id()+" "+lead.getOrden_detalle_producto());

        //Mostrar precio unitario en moneda de acuerdo a la region
        ///////////////////////////////////////////////////////////////////////////////////////
        holder.tv_orden_detalle_stock.setText(Convert.doubleToTextInteger(lead.getOrden_detalle_stock_almacen()));
        ///////////////////////////////////////////////////////////////////////////////////////

        holder.tv_orden_detalle_porcentaje_descuento.setText(String.valueOf(format.format(Float.parseFloat(lead.getOrden_detalle_stock_general()))));

        //Mostrar precio unitario en moneda de acuerdo a la region
        ///////////////////////////////////////////////////////////////////////////////////////
        holder.tv_orden_detalle_precio.setText(Convert.currencyForView(lead.getOrden_detalle_precio_unitario()));
        ///////////////////////////////////////////////////////////////////////////////////////



        holder.et_orden_detalle_cantidad.setText(lead.getOrden_detalle_cantidad());

        ////////////////////RECUPERAR PINTADO DE EDITtEXT DE DESCUENTO//////////////////////////////

        try{
            Double tempDsct = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento());
            Double tempDsctMax = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento_maximo());

            if (tempDsct > 0 && tempDsct <= tempDsctMax) {
                holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
            } else if (tempDsct >= (tempDsctMax+0.1)) {
                holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
            }

            holder.editDspPorcentaje.setText(Convert.numberForView(lead.getOrden_detalle_porcentaje_descuento()));
        }catch(Exception e){
            Sentry.captureMessage(e.getMessage());
            e.printStackTrace();
        }
        /////////////////

        holder.tv_orden_detalle_galon_unitario.setText(lead.getOrden_detalle_gal());

        if(lead.isOrden_detalle_chk_descuentocontado()){
            holder.tv_porcentaje_descuento_contado.setText(lead.getOrden_detalle_descuentocontado());
        }else{
            holder.tv_porcentaje_descuento_contado.setText("0");
        }

        if(lead.isOrden_detalle_chk_descuentocontado()){
            holder.chk_descuento_contado.setChecked(true);
        }else{
            holder.chk_descuento_contado.setChecked(false);
            Drawable draw =  ContextCompat.getDrawable(getContext(),R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
            holder.imv_consultar_promocion_cabecera.setEnabled(true);
            //holder.chk_descuento_contado.setChecked(true);
        }


        if(lead.getOrden_detalle_cantidad().isEmpty()){
            holder.tv_orden_detalle_galon_acumulado.setText("0");
        } else{
            holder.tv_orden_detalle_galon_acumulado.setText(Convert.multiplicacion(lead.getOrden_detalle_cantidad(),lead.getOrden_detalle_gal()));
        }

        if(!(lead.getOrden_detalle_lista_promocion_cabecera()==null)){

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
            lead.setOrden_detalle_montosubtotal(formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(),lead.getOrden_detalle_cantidad()));
            //Monto subtotal con descuento
            lead.setOrden_detalle_montosubtotalcondescuento(
                    //Formula para Calcular el Monto Sub Total Con Descuento
                    formulasController.CalcularMontoTotalconDescuento(
                            //Variable 1 Monto Total Linea Sin IGV
                            lead.getOrden_detalle_montosubtotal(),
                            //Variable 2  Monto Descuento
                            lead.getOrden_detalle_monto_descuento()
                    ));

            //Mostrar total en moneda de acuerdo a la region
            ///////////////////////////////////////////////////////////////////////////////////////
            Log.e("xdd","============> Desct");
            holder.tv_orden_detalle_total.setText(Convert.currencyForView(lead.getOrden_detalle_montosubtotal()));
            ///////////////////////////////////////////////////////////////////////////////////////

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
        }else{
            holder.tv_orden_detalle_porcentaje_descuento.setText(lead.getOrden_detalle_porcentaje_descuento());

            //Mostrar total en moneda de acuerdo a la region
            ///////////////////////////////////////////////////////////////////////////////////////


            try {
                holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
            }catch(Exception e){
                Sentry.captureMessage(e.getMessage());
                holder.tv_orden_detalle_total.setText("ERR");
            }
            ///////////////////////////////////////////////////////////////////////////////////////

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
        for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++){

            if(i==(Integer.parseInt(lead.getOrden_detalle_item())-1)){

                lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));

                BigDecimal montoTotalconDescuento12=new BigDecimal(formulasController.CalcularMontoTotalconDescuento(lead.getOrden_detalle_montosubtotal(),lead.getOrden_detalle_monto_descuento()));
                montoTotalconDescuento12.add(new BigDecimal(lead.getOrden_detalle_monto_igv())).setScale(0,RoundingMode.HALF_UP);

                lead.setOrden_detalle_montototallinea(montoTotalconDescuento12.toString());

                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(
                        lead.getOrden_detalle_montototallinea()
                );

                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_cantidad(valor);
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
            }
        }

        //Mostrar impuesto calculado por linea en moneda de acuerdo a la region
        ///////////////////////////////////////////////////////////////////////////////////////
        holder.tv_orden_detalle_igv.setText(Convert.currencyForView(lead.getOrden_detalle_monto_igv()));
        ///////////////////////////////////////////////////////////////////////////////////////

        holder.editDspPorcentaje.setOnEditorActionListener((v, actionId, event) -> {

            boolean procesado = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                procesado=true;

                if(lead.getOrden_detalle_cantidad().isEmpty()){
                    v.setText(null);
                    Toast.makeText(Context, "Es necesario ingresar primero la cantidad", Toast.LENGTH_LONG).show();
                }else{
                    Double descuento = 0.00;
                    Double descuentoMax = 0.00;

                    if (v.length() != 0) {
                        try{
                            descuento = Double.valueOf(v.getText().toString());
                            descuentoMax = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento_maximo());
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    if (descuento <= 99.9) {

                        if (descuento > 0 && descuento <= descuentoMax) {
                            holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                            holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
                        } else if (descuento >= (descuentoMax+0.1)) {
                            holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                            holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                            Toast.makeText(Context, "El porcentaje de descuento mayor al "+descuentoMax+"% esta sujeto a evaluación", Toast.LENGTH_LONG).show();
                        }

                        lead.setOrden_detalle_porcentaje_descuento(""+descuento);

                        v.setText(Convert.numberForView(String.valueOf(descuento)));
                    } else {
                        Toast.makeText(Context, "El porcentaje de descuento no puede ser mayor al 99.9%", Toast.LENGTH_LONG).show();
                        holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                        holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                        v.setText(null);
                    }

                    ////////////////RECALCULAMOS TODO Y GUARDAMOS EN PERSITENCIA////////////////////
                    for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++){

                        if(i==(Integer.parseInt(lead.getOrden_detalle_item())-1)){

                            lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));

                            String xd1=formulasController.CalcularMontoTotalconDescuento(
                                    lead.getOrden_detalle_montosubtotal(),
                                    lead.getOrden_detalle_monto_descuento()
                            );


                            lead.setOrden_detalle_montototallinea(formulasController.suma(xd1,lead.getOrden_detalle_monto_igv()));

                            ////////////////////////////////////////////////////////////////////////
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());

                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(lead.getOrden_detalle_montototallinea());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());

                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_porcentaje_descuento(lead.getOrden_detalle_porcentaje_descuento());

                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
                        }
                    }
                    /////////////////////////////////////////////////////////////CALCULO MONTOS TOTALES//////////////////////////////////////
                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity= new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);

                    // fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                    // FragmentTransaction transaction = fragmentManager.beginTransaction();
                    // transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));
                    ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity);
                    ActualizaListaOrdenDetallePromocion(lead);
                }
            }

            // Ocultar teclado virtual
            v.clearFocus();//retirar focus
            ocultarTeclado(v);

            return procesado;
        });

        holder.et_orden_detalle_cantidad.setOnEditorActionListener((v, actionId, event) -> {

            boolean procesado = false;

            // Ocultar teclado virtual
            ocultarTeclado(v);

            boolean FLAG_STOCK=(SesionEntity.FLAG_STOCK.equals("Y"))?true:false;
            boolean result_flag=true;

            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String valor="0";

                if(v.length()>0){
                    valor=v.getText().toString();

                    Double cantidadNum=0.0,stockNum=0.0;

                    try{
                        cantidadNum=Double.parseDouble(valor);
                        stockNum=Double.parseDouble(lead.getOrden_detalle_stock_almacen());
                    }catch(Exception e){
                        e.printStackTrace();
                    }

                    if(cantidadNum==0){
                        Toast.makeText(Context, "La cantidad no puede ser 0", Toast.LENGTH_SHORT).show();
                        holder.et_orden_detalle_cantidad.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                        holder.et_orden_detalle_cantidad.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                    }else{
                        Log.e("JEPICMA","Stock flag=>"+FLAG_STOCK);
                        Log.e("JEPICMA","Stock flag=>"+cantidadNum+"-"+stockNum);
                        if(FLAG_STOCK){ //informativo
                            if(cantidadNum>stockNum){
                                Toast.makeText(Context, "La cantidad ingresada excede el stock actual", Toast.LENGTH_SHORT).show();
                                holder.et_orden_detalle_cantidad.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                                holder.et_orden_detalle_cantidad.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                            }else{
                                holder.et_orden_detalle_cantidad.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                                holder.et_orden_detalle_cantidad.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
                            }
                        }else{ //restrictivo
                            if(cantidadNum>stockNum){
                                Toast.makeText(Context, "La cantidad ingresada excede el stock actual", Toast.LENGTH_SHORT).show();
                                holder.et_orden_detalle_cantidad.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                                holder.et_orden_detalle_cantidad.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                                holder.et_orden_detalle_cantidad.setText(null);
                                result_flag=false;
                            }else{
                                holder.et_orden_detalle_cantidad.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                                holder.et_orden_detalle_cantidad.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
                            }
                        }
                    }
                }

                //ejecuta calculo dependiendo el resultado del flag
                if(result_flag) {

                    lead.setOrden_detalle_cantidad(valor);
                    lead.setOrden_detalle_montosubtotal(formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(), lead.getOrden_detalle_cantidad()));

                    String montoToalDsct = formulasController.CalcularMontoTotalconDescuento(
                            lead.getOrden_detalle_montosubtotal(),
                            lead.getOrden_detalle_monto_descuento()
                    );

                    BigDecimal acumImpuesto=new BigDecimal(lead.getOrden_detalle_monto_igv());

                    //lead.setOrden_detalle_montototallinea(acumImpuesto.add(new BigDecimal(montoToalDsct)).setScale(6,RoundingMode.HALF_UP).toString() );
                    lead.setOrden_detalle_montototallinea(acumImpuesto.add(new BigDecimal(montoToalDsct)).setScale(0,RoundingMode.HALF_UP).toString() );

                    //Mostrar total en moneda de acuerdo a la region
                    ///////////////////////////////////////////////////////////////////////////////////////
                    holder.tv_orden_detalle_total.setText(Convert.currencyForView(lead.getOrden_detalle_montosubtotal()));
                    ///////////////////////////////////////////////////////////////////////////////////////
                    holder.tv_orden_detalle_galon_acumulado.setText(Convert.multiplicacion(lead.getOrden_detalle_cantidad(),lead.getOrden_detalle_gal()));

                    for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++){
                        if(i==(Integer.parseInt(lead.getOrden_detalle_item())-1)){

                            String montoLineaCUMOXD=formulasController.CalcularMontoTotalconDescuento(
                                    lead.getOrden_detalle_montosubtotal(),
                                    lead.getOrden_detalle_monto_descuento()
                            );

                            BigDecimal montoPorLinea123=new BigDecimal(montoLineaCUMOXD);

                            lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                            lead.setOrden_detalle_montototallinea(montoPorLinea123.add(new BigDecimal(lead.getOrden_detalle_monto_igv())).setScale(0,RoundingMode.HALF_UP).toString());

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

                    procesado = true;

                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity= new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);

                    fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));

                    ActualizaListaOrdenDetallePromocion(lead);

                }
            }
            return procesado;
        });


        holder.imv_consultar_promocion_cabecera.setOnClickListener(v -> {
            ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities= new ArrayList<>();
            fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            listaOrdenVentaDetalleEntities.add(lead);
            Object [] listaobjetos=new Object[2];
            //OrdenVentaDetalleView.listaPromocionCabecera=new ArrayList<>();
            actualizarlistapromocioncabecera(lead);
            listaobjetos[0]=OrdenVentaDetalleView.listaPromocionCabecera;
            listaobjetos[1]=listaOrdenVentaDetalleEntities;
            transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceEnviaListaPromocion(listaobjetos));

        });

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

                //tv_orden_venta_detalle_lista_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getLista_promocion_id());
                tv_orden_venta_detalle_lista_promocion_id.setText(String.valueOf(i+1));
                //tv_orden_venta_detalle_lista_promocion_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getPromocion_id());
                tv_orden_venta_detalle_lista_promocion_promocion_id.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getCantidadcompra());
                tv_orden_venta_detalle_lista_promocion_cantidad_promocion.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getCantidadpromocion());
                tv_orden_venta_detalle_lista_promocion_preciobase.setText(format.format(Float.parseFloat(String.valueOf(formulasController.ObtenerCalculoPromocionDetalle(
                        lead.getOrden_detalle_lista_promocion_cabecera(), i
                )))));
                tv_orden_venta_detalle_lista_promocion_cantidad_lineas.setText(String.valueOf(
                        formulasController.ObtenerCantidadLineasPromocionDetalle(lead.getOrden_detalle_lista_promocion_cabecera(),
                                i
                        )));
                holder.layout.addView(relativeLayout);
            }
        }else
        {
            itemChecked[position] = false;

        }
        return convertView;
    }

    private void ocultarTeclado(TextView v){
        InputMethodManager imm =(InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
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
        TextView tv_orden_detalle_stock;
        EditText et_orden_detalle_cantidad;
        EditText editDspPorcentaje;
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
        TextView tv_porcentaje_descuento_contado;
        ViewGroup layout;


    }



    /*AQUI SE CALCULA MONTO PARA ORDEN DE VENTA DETALLES*/
    public void ActualizaListaOrdenDetallePromocion(ListaOrdenVentaDetalleEntity lead){
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetallePromocionEntity> arraylistListaOrdenVentaDetalleEntityPromocion = new ArrayList<>();
        ListaOrdenVentaDetallePromocionEntity listaOrdenVentaDetallePromocionEntity;
        int orden_detalle_promocion_item=0,orden_detalle_promocion_cantidad=0,orden_detalle_promocion_linea_referencia=0;
        boolean chk_descuentocontadoaplicado=false;
        String contadodescuento="0";

        //Evalua si la promocion de la cabecera no esta vacia

        Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_descuentocontado(): "+lead.getOrden_detalle_descuentocontado());
        Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado()"+lead.isOrden_detalle_chk_descuentocontado());
        Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado_aplicado()"+lead.isOrden_detalle_chk_descuentocontado_aplicado());

        if(Integer.parseInt(lead.getOrden_detalle_descuentocontado())>0
                &&lead.isOrden_detalle_chk_descuentocontado()&&
                (!lead.isOrden_detalle_chk_descuentocontado_aplicado())
        ){
            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
            contadodescuento=lead.getOrden_detalle_descuentocontado();
            chk_descuentocontadoaplicado=true;

        }
        else
        {
            contadodescuento="0";
            chk_descuentocontadoaplicado=false;

        }

        if(!lead.isOrden_detalle_chk_descuentocontado_aplicado()) {
            Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Lead-sinChkdescuentoaplicado");
            if (!(lead.getOrden_detalle_lista_promocion_cabecera() == null)) {
                //Recorre la Lista de Promociones
                for (int a = 0; a < lead.getOrden_detalle_lista_promocion_cabecera().size(); a++) {

                    orden_detalle_promocion_item++;
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
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock_almacen();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad =
                            String.valueOf(Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadcompra()) *
                                    Integer.parseInt(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadpromocion()));
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                            (listaOrdenVentaDetallePromocionEntity.getOrden_detalle_precio_unitario(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());

                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                            Integer.parseInt(

                                    lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento()
                            ) +
                                    Integer.parseInt(contadodescuento));


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
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento(), Induvis.getImpuestoString());

                    ///////////////////////TOTAL X LINEA//////////////////////////////////////////////
                    BigDecimal montoTotalconDescuento= new BigDecimal(formulasController.CalcularMontoTotalconDescuento(
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                    ));

                    BigDecimal detalle_monto_igv= new BigDecimal(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv());

                    String valueeee=montoTotalconDescuento.add(detalle_monto_igv).setScale(0, RoundingMode.HALF_UP).toString();
                    ///////////////////////TOTAL X LINEA//////////////////////////////////////////////

                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = valueeee;

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
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_item = String.valueOf(orden_detalle_promocion_item);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_padre = "1";
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = String.valueOf(orden_detalle_promocion_linea_referencia);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;


                    //Registra Detalle de Linea Padre
                    arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                    Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Fin Registro Linea Padre");
                    //Inserta Lineas de Detalle
                    for (int b = 0; b < lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().size(); b++) {
                        if (!lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getProducto_id().equals("DESCUENTO")) {

                            orden_detalle_promocion_item++;

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
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                                    (lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase(),
                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = "100";

                            Log.e("applyDiscountPercentageForLine","location 1");
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
                                    Induvis.getImpuestoString()
                            );

                            BigDecimal montoTotalconDescuento1=new BigDecimal(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                            ))).setScale(0, RoundingMode.HALF_UP);

                            BigDecimal detalle_monto_igv1=new BigDecimal(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv()).setScale(0, RoundingMode.HALF_UP);

                            String asdasdas=montoTotalconDescuento1.add(detalle_monto_igv1).setScale(0, RoundingMode.HALF_UP).toString();

                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea =asdasdas ;
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
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_padre = "0";
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = String.valueOf(orden_detalle_promocion_linea_referencia);
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                            //Registra Linea de Promocion
                            arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                            Log.e("REOS", "ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-Fin Registro Linea DESCUENTO");
                        }
                    }
                }

            }
            //Si la cantidad de Promocion es mayor a la cantidad de la Linea
            else {
            /*if(Integer.parseInt(lead.getOrden_detalle_descuentocontado())>0&&
                    !lead.getOrden_detalle_porcentaje_descuento().equals("100")
                    &&lead.isOrden_detalle_chk_descuentocontado()&&(!lead.isOrden_detalle_chk_descuentocontado_aplicado())
            )
            {
                Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
                contadodescuento=lead.getOrden_detalle_descuentocontado();
                //chk_descuentocontadoaplicado=true;

            }
            else
            {
                Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
                contadodescuento="0";
                //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
                //{
                Log.e("REOS","ListaOrdenVentaAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones-2dobuclecumplecondiciones");
                //chk_descuentocontadoaplicado=true;
                //}

            }*/

                if (lead.getOrden_detalle_cantidad().equals("")) {
                    lead.setOrden_detalle_cantidad("");
                }
/*
                if (lead.getPorcentajeDesceunto().equals("")) {
                    lead.setPorcentajeDesceunto("");
                }*/

                orden_detalle_promocion_item++;
                listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_producto_id();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_producto();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_umd();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock_almacen();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad = lead.getOrden_detalle_cantidad();

                listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(),lead.getOrden_detalle_cantidad());

                /* COMENTADO 3/09/2021 00:34*/
                try{
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(Double.parseDouble(lead.getOrden_detalle_porcentaje_descuento()) +Integer.parseInt(contadodescuento));

                }catch(Exception e){
                    Toast.makeText(Context, "Ocurrio un error al asignar porcentaje de descuento", Toast.LENGTH_SHORT).show();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento="0";
                }


                Log.e("applyDiscountPercentageForLine","location 2");
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
                        Induvis.getImpuestoString()
                );

                BigDecimal totalIgv=new BigDecimal(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv()).setScale(0, RoundingMode.HALF_UP);
                BigDecimal montoTotalconDescuento3=new BigDecimal(formulasController.CalcularMontoTotalconDescuento(
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                )).setScale(0, RoundingMode.HALF_UP);


                String wetwr=totalIgv.add(montoTotalconDescuento3).setScale(0, RoundingMode.HALF_UP).toString();

                listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea =wetwr;



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
                SesionEntity.contado
                ,""
                ,""
        );

        if (listaPromocionCabecera.isEmpty()){

            listaPromocionCabecera=promocionCabeceraSQLiteDao.ObtenerPromocionCabecera(
                    SesionEntity.compania_id,
                    SesionEntity.fuerzatrabajo_id,
                    SesionEntity.usuario_id,
                    lead.getOrden_detalle_producto_id(),
                    lead.getOrden_detalle_umd(),
                    lead.getOrden_detalle_cantidad(),
                    SesionEntity.contado
                    ,""
                    ,""
            );

        }

        OrdenVentaDetalleView.listaPromocionCabecera=listaPromocionCabecera;
    }

    public void ActualizarResumenOrdenVenta()
    {
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizarResumen());
    }


}

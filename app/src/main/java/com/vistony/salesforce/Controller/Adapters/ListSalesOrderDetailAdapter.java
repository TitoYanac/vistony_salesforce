package com.vistony.salesforce.Controller.Adapters;

import static com.vistony.salesforce.Controller.Utilitario.Convert.getTotaLine;
import static com.vistony.salesforce.Controller.Utilitario.Induvis.getDiscountPercentDecimals;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vistony.salesforce.BuildConfig;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Controller.Utilitario.Utilitario;
import com.vistony.salesforce.Dao.SQLite.ListaPrecioDetalleSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.PriceListHeadSQLite;
import com.vistony.salesforce.Dao.SQLite.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Dao.SQLite.UsuarioSQLite;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetallePromocionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.PriceListHeadEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPrecioDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.UsuarioSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.OrdenVentaDetalleView;

import java.util.ArrayList;
import java.util.List;


public class ListSalesOrderDetailAdapter  extends ArrayAdapter<ListaOrdenVentaDetalleEntity>  {

    private android.content.Context Context;
    LayoutInflater inflater;
    private FragmentManager fragmentManager;
    public OrdenVentaDetalleView ordenVentaDetalleView;
    PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao = new PromocionCabeceraSQLiteDao(getContext());
    ArrayList<ListaPromocionCabeceraEntity> listaPromocionCabecera=new ArrayList<>();
    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities=new ArrayList<>();
    ListaOrdenVentaDetalleListaPromocionAdapter listaOrdenVentaDetalleListaPromocionAdapter;
    FormulasController formulasController=new FormulasController(getContext());
    boolean[] itemChecked;
    UsuarioSQLiteEntity ObjUsuario;

    public ListSalesOrderDetailAdapter(android.content.Context context, List<ListaOrdenVentaDetalleEntity> objects) {
        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);
        this.itemChecked=new boolean[objects.size()];
        ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e("REOS","ListSalesOrderDetailAdapter: Inicio");
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_orden_venta_detalle_general,
                    parent,
                    false);

            holder = new ViewHolder();
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
            holder.editDspPorcentaje = (EditText) convertView.findViewById(R.id.editDspPorcentaje);
            // holder.lv_promociondetalle=(ListView) convertView.findViewById(R.id.lv_promociondetalle);
            holder.imv_consultar_promocion_cabecera=(ImageView) convertView.findViewById(R.id.imv_consultar_promocion_cabecera);
            holder.imv_eliminar_orden_venta_detalle=(ImageView) convertView.findViewById(R.id.imv_eliminar_orden_venta_detalle);
            holder.chk_descuento_contado=(CheckBox) convertView.findViewById(R.id.chk_descuento_contado);
            holder.et_porcentaje_descuento_contado=(EditText) convertView.findViewById(R.id.et_porcentaje_descuento_contado);
            holder.tv_orden_detalle_precio_igv = (TextView) convertView.findViewById(R.id.tv_orden_detalle_precio_igv);
            holder.tv_orden_detalle_total_igv = (TextView) convertView.findViewById(R.id.tv_orden_detalle_total_igv);
            holder.tv_orden_liter = (TextView) convertView.findViewById(R.id.tv_orden_liter);
            holder.tv_orden_detalle_sigaus = (TextView) convertView.findViewById(R.id.tv_orden_detalle_sigaus);
            holder.tr_sigaus = (TableRow) convertView.findViewById(R.id.tr_sigaus);
            //holder.lbl_orden_detalle_precio = (TextView) convertView.findViewById(R.id.lbl_orden_detalle_precio);
            holder.layout=(ViewGroup) convertView.findViewById(R.id.content);
            holder.btn_edit_price = (Button) convertView.findViewById(R.id.btn_edit_price);
            holder.btn_editDspPorcentaje = (Button) convertView.findViewById(R.id.btn_editDspPorcentaje);
            holder.btn_edit_orden_detalle_cantidad = (Button) convertView.findViewById(R.id.btn_edit_orden_detalle_cantidad);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        // Lead actual.
        final ListaOrdenVentaDetalleEntity lead = getItem(position);
        holder.layout.removeAllViews();
        listaOrdenVentaDetalleEntities.add(lead);
        // Setup.
        //DecimalFormat format = new DecimalFormat("#0.00");
        holder.tv_orden_detalle_item.setText(lead.getOrden_detalle_item()+")");
        holder.tv_orden_detalle_producto.setText(lead.getOrden_detalle_producto());
        holder.tv_orden_detalle_umd.setText(lead.getOrden_detalle_producto_id());
        holder.tv_orden_detalle_stock.setText(Convert.numberForView (lead.getOrden_detalle_stock_almacen()));
        holder.et_orden_detalle_cantidad.setText(lead.getOrden_detalle_cantidad());
        holder.tv_orden_detalle_galon_unitario.setText(Convert.numberForView2(lead.getOrden_detalle_gal()));
        holder.tv_orden_detalle_precio_igv.setText(Convert.numberForView2 (formulasController.ObtenerCalculoPrecioImpuesto(lead.getOrden_detalle_precio_unitario(), ObjUsuario.Impuesto)));

        /*lead.setOrden_detalle_montosubtotal(
                formulasController.getTotalPerLine
                        (lead.getOrden_detalle_precio_unitario(),
                                lead.getOrden_detalle_cantidad()));
        Log.e("REOS","ListSalesOrderDetailAdapter-lead.getOrden_detalle_montosubtotal():"+lead.getOrden_detalle_montosubtotal());
        lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));*/
        holder.tv_orden_detalle_igv.setText(Convert.currencyForView(lead.getOrden_detalle_monto_igv()));
        //Asignar el total
        switch (BuildConfig.FLAVOR)
        {
            case "ecuador":
            case "chile":
            case "bolivia":
                holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
                break;
            default:
                holder.tv_orden_detalle_total.setText(Convert.currencyForView (lead.getOrden_detalle_montosubtotal()));
                break;
        }
        /*if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
        {
            holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
        }else {

            holder.tv_orden_detalle_total.setText(Convert.currencyForView (lead.getOrden_detalle_montosubtotal()));
        }*/

        //Asignar precio
        switch (BuildConfig.FLAVOR)
        {
            case "marrruecos":
                holder.tv_orden_detalle_precio.setText(Convert.numberForView2 (formulasController.ObtenerCalculoPrecioImpuesto(lead.getOrden_detalle_precio_unitario(), ObjUsuario.Impuesto)));
                break;
            default:
                holder.tv_orden_detalle_precio.setText(Convert.currencyForView(lead.getOrden_detalle_precio_unitario()));
                break;
        }
        /*if(BuildConfig.FLAVOR.equals("marrruecos"))
        {
            holder.tv_orden_detalle_precio.setText(Convert.numberForView2 (formulasController.ObtenerCalculoPrecioImpuesto(lead.getOrden_detalle_precio_unitario(), ObjUsuario.Impuesto)));
        }
        else {
            holder.tv_orden_detalle_precio.setText(Convert.currencyForView(lead.getOrden_detalle_precio_unitario()));
        }*/
        //Deshabilitar boton editar precio
        switch (BuildConfig.FLAVOR)
        {
            case "peru":
                //holder.tv_orden_detalle_precio.setText(Convert.numberForView2 (formulasController.ObtenerCalculoPrecioImpuesto(lead.getOrden_detalle_precio_unitario(), ObjUsuario.Impuesto)));
                break;
            default:
                Utilitario.disabledButtton(holder.btn_edit_price);
                break;
        }
        //Deshabilitar Porcentaje de descuento
        Utilitario.disabledEditText(holder.editDspPorcentaje);
        /*if(!BuildConfig.FLAVOR.equals("peru"))
        {
            Utilitario.disabledButtton(holder.btn_edit_price);
            //Utilitario.disabledEditText(holder.editDspPorcentaje);
        }*/

        /*Resources res3 = getContext().getResources(); // need this to fetch the drawable
        Drawable borde_editext_orden_venta_detalle_inhabilitado3 = res3.getDrawable( R.drawable.custom_border_view_grey_light);
        holder.btn_edit_price.setEnabled(false);
        holder.btn_edit_price.setBackground(borde_editext_orden_venta_detalle_inhabilitado3);
        holder.btn_edit_price.setTextColor(Color.parseColor("#FFFFFF"));*/
        //holder.btn_edit_price.


        Log.e("REOS","ListSalesOrderDetailAdapter.BuildConfig.FLAVOR:"+BuildConfig.FLAVOR.toString());
        Log.e("REOS","ListSalesOrderDetailAdapter.BuildConfig.lead.getOrden_detalle_oil_tax():"+lead.getOrden_detalle_oil_tax());
        //Aignar SIGAUS
        switch (BuildConfig.FLAVOR)
        {
            case "espania":
                if(lead.getOrden_detalle_oil_tax().equals("Y"))
                {
                    ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(Context);
                    ArrayList<ListaPrecioDetalleSQLiteEntity> listSIGAUS=new ArrayList<>();
                    listSIGAUS=listaPrecioDetalleSQLiteDao.getProductSIGAUS();

                    String preciobase="";
                    for(int i=0;i<listSIGAUS.size();i++)
                    {
                        preciobase=listSIGAUS.get(i).getContado();
                    }
                    holder.tv_orden_liter.setText(Convert.convertLiterAcum(lead.getOrden_detalle_liter(),lead.getOrden_detalle_cantidad()));
                    holder.tv_orden_detalle_sigaus.setText(Convert.convertSIGAUS(Convert.convertLiterAcum(lead.getOrden_detalle_liter(),lead.getOrden_detalle_cantidad())
                            ,preciobase
                    ));
                }
                else {
                    holder.tr_sigaus.setVisibility(View.GONE);
                }
                break;
            default:
                holder.tr_sigaus.setVisibility(View.GONE);
                break;
        }
        /*if(BuildConfig.FLAVOR.equals("espania"))
        {
            if(lead.getOrden_detalle_oil_tax().equals("Y"))
            {
                ListaPrecioDetalleSQLiteDao listaPrecioDetalleSQLiteDao=new ListaPrecioDetalleSQLiteDao(Context);
                ArrayList<ListaPrecioDetalleSQLiteEntity> listSIGAUS=new ArrayList<>();
                listSIGAUS=listaPrecioDetalleSQLiteDao.getProductSIGAUS();

                String preciobase="";
                for(int i=0;i<listSIGAUS.size();i++)
                {
                    preciobase=listSIGAUS.get(i).getContado();
                }
                holder.tv_orden_liter.setText(Convert.convertLiterAcum(lead.getOrden_detalle_liter(),lead.getOrden_detalle_cantidad()));
                holder.tv_orden_detalle_sigaus.setText(Convert.convertSIGAUS(Convert.convertLiterAcum(lead.getOrden_detalle_liter(),lead.getOrden_detalle_cantidad())
                        ,preciobase
                ));
            }
            else {
                holder.tr_sigaus.setVisibility(View.GONE);
            }
        }
        else{
            holder.tr_sigaus.setVisibility(View.GONE);
        }*/

        //Check promocion cabecera
        switch (BuildConfig.FLAVOR)
        {
            case "chile":
                if (SesionEntity.quotation.equals("Y"))
                {
                    holder.imv_consultar_promocion_cabecera.setVisibility(View.INVISIBLE);
                    holder.imv_consultar_promocion_cabecera.setEnabled(false);
                    holder.imv_consultar_promocion_cabecera.setClickable(false);
                }
                break;
        }
        /*if((BuildConfig.FLAVOR.equals("chile")&&SesionEntity.quotation.equals("Y"))){
            holder.imv_consultar_promocion_cabecera.setVisibility(View.INVISIBLE);
            holder.imv_consultar_promocion_cabecera.setEnabled(false);
            holder.imv_consultar_promocion_cabecera.setClickable(false);
        }*/

        /*if(BuildConfig.FLAVOR.equals("espania")||BuildConfig.FLAVOR.equals("marruecos")||BuildConfig.FLAVOR.equals("ecuador")
                ||(BuildConfig.FLAVOR.equals("chile")&&!SesionEntity.quotation.equals("Y")))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.borde_editext_ov_gris);
            holder.editDspPorcentaje.setEnabled(false);
            holder.editDspPorcentaje.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
            holder.editDspPorcentaje.setTextColor(Color.parseColor("#808080"));
        }else {

        }*/

        //inhabilitar porcentaje de descuento
        switch (BuildConfig.FLAVOR)
        {
            case "espania":
            case "marruecos":
            case "ecuador":
            case "chile":
                if(!SesionEntity.quotation.equals("Y"))
                {
                    Resources res = getContext().getResources(); // need this to fetch the drawable
                    Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.borde_editext_ov_gris);
                    holder.editDspPorcentaje.setEnabled(false);
                    holder.editDspPorcentaje.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
                    holder.editDspPorcentaje.setTextColor(Color.parseColor("#808080"));
                }
                break;
        }

        ////////////////////RECUPERAR PINTADO DE EDITtEXT DE DESCUENTO//////////////////////////////
        Log.e("REOS","ListSalesOrderDetailAdapter PINTADO DE EDITtEXT DE DESCUENTO:lead.getOrden_detalle_porcentaje_descuento():"+lead.getOrden_detalle_porcentaje_descuento());
        Log.e("REOS","ListSalesOrderDetailAdapter PINTADO DE EDITtEXT DE DESCUENTO:lead.getOrden_detalle_porcentaje_descuento_maximo():"+lead.getOrden_detalle_porcentaje_descuento_maximo());
        try{
            Double tempDsct = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento());
            Double tempDsctMax = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento_maximo());

            /*if (tempDsct > 0 && tempDsct <= tempDsctMax) {
                holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
            } else if (tempDsct >= (tempDsctMax+0.1)) {
                holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
            }*/

            Log.e("REOS","ListSalesOrderDetailAdapter:holder.editDspPorcentaje.setText-lead.getOrden_detalle_porcentaje_descuento():"+lead.getOrden_detalle_porcentaje_descuento());
            //Log.e("REOS","ListaOrdenVentaDetalleAdapter:holder.editDspPorcentaje.setText-Integer.parseInt(lead.getOrden_detalle_porcentaje_descuento():"+Integer.parseInt(lead.getOrden_detalle_porcentaje_descuento()));
            if((!(lead.getOrden_detalle_lista_promocion_cabecera()==null))&&holder.layout.getChildCount()==0)
            {
                //Prueba
                if(BuildConfig.FLAVOR.equals("peru"))
                {
                    if(SesionEntity.quotation.equals("Y"))
                    {
                        if (ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
                        {
                            holder.editDspPorcentaje.setText(String.valueOf(Convert.amountForTwoDecimal(lead.getOrden_detalle_porcentaje_descuento())));
                        }
                        else {
                            holder.editDspPorcentaje.setText((Convert.numberForView(lead.getOrden_detalle_porcentaje_descuento())));
                        }
                    }
                    else {
                        holder.editDspPorcentaje.setText((Convert.numberForView(lead.getOrden_detalle_porcentaje_descuento())));
                    }

                }

            }
            else {
                if(BuildConfig.FLAVOR.equals("peru"))
                {
                    if (SesionEntity.quotation.equals("Y")) {
                        if (ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
                        {
                            holder.editDspPorcentaje.setText((Convert.numberForViewDecimals(lead.getOrden_detalle_porcentaje_descuento(),getDiscountPercentDecimals())));
                        }else {
                            holder.editDspPorcentaje.setText((Convert.numberForViewDecimals(lead.getOrden_detalle_porcentaje_descuento(),getDiscountPercentDecimals())));
                        }
                    }
                }else {
                    holder.editDspPorcentaje.setText((Convert.numberForViewDecimals(lead.getOrden_detalle_porcentaje_descuento(),getDiscountPercentDecimals())));
                }

            }

            //holder.editDspPorcentaje.setText(lead.getOrden_detalle_descuentocontado());
            //holder.editDspPorcentaje.setText((Convert.numberForView(lead.getOrden_detalle_porcentaje_descuento())));
            /*ActualizaListaOrdenDetallePromocion(lead);
            ActualizarResumenOrdenVenta();*/

        }catch(Exception e){
            //Sentry.captureMessage(e.getMessage());
            e.printStackTrace();
            Log.e("REOS","ListSalesOrderDetailAdapter PINTADO DE EDITtEXT DE DESCUENTO:e:"+e.toString());
        }
        /////////////////

        if(lead.isOrden_detalle_chk_descuentocontado_cabecera()&&!(lead.getOrden_detalle_cantidad().equals(""))&&!lead.getOrden_detalle_terminopago_id().equals("47"))
        {
            holder.chk_descuento_contado.setEnabled(true);
            //holder.chk_descuento_contado.setChecked(true);

            Log.e("REOS","ListSalesOrderDetailAdapter:holder.chk_descuento_contado.setEnabled(true)");

        }
        else
        {
            Log.e("REOS","ListSalesOrderDetailAdapter:holder.chk_descuento_contado.setEnabled(false)");
            holder.chk_descuento_contado.setEnabled(false);
            //holder.chk_descuento_contado.setChecked(false);
            //holder.et_porcentaje_descuento_contado.setText("0");
        }

        if(SesionEntity.quotation.equals("Y"))
        {
            if (ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
            {
                //holder.lbl_orden_detalle_precio.setBackgroundColor(Color.RED);
                //holder.lbl_orden_detalle_precio.setTextColor(Color.WHITE);
                holder.btn_edit_price.setVisibility(View.VISIBLE);

            }
            else {
                /*Resources res = getContext().getResources(); // need this to fetch the drawable
                Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable( R.drawable.but);
                holder.btn_edit_price.setEnabled(true);
                holder.btn_edit_price.setBackground(borde_editext_orden_venta_detalle_inhabilitado);*/
                Utilitario.disabledButtton(holder.btn_edit_price);
            }
        }

        holder.btn_edit_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUpdatePrice(lead,holder).show();
            }
        });

        Log.e("REOS","ListSalesOrderDetailAdapter:lead.getOrden_detalle_cantidad():" + lead.getOrden_detalle_cantidad());
        Log.e("REOS","ListSalesOrderDetailAdapter:lead.getOrden_detalle_descuentocontado():" + lead.getOrden_detalle_descuentocontado());

        holder.editDspPorcentaje.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.e("REOS", "ListSalesOrderDetailAdapter-getView-holder.editDspPorcentaje-Gained focus");
                } else {
                    Log.e("REOS", "ListSalesOrderDetailAdapter-getView-holder.editDspPorcentaje-Lost focus");
                    //Log.e("REOS", "Lost focus");
                }
            }
        });
       /* holder.editDspPorcentaje.setOnEditorActionListener((v, actionId, event) -> {

            boolean procesado = false;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                procesado=true;

                if(lead.getOrden_detalle_cantidad().isEmpty()){
                    //v.setText(null);
                    Toast.makeText(Context, "Es necesario ingresar primero la cantidad", Toast.LENGTH_LONG).show();
                }else{
                    Double descuento = 0.000;
                    Double descuentoMax = 0.000;

                    if (v.length() != 0) {
                        try{
                            descuento = Double.valueOf(v.getText().toString());
                            descuentoMax = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento_maximo());
                            Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.descuento:"+descuento);
                            Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.descuentoMax:"+descuentoMax);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    Double descountCountry=0.0;
                    if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("peru"))
                    {
                        descountCountry=99.9;
                    }
                    else {
                        descountCountry=100.0;
                    }

                    if (descuento <= descountCountry) {

                        if (descuento > 0 && descuento <= descuentoMax) {
                            holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                            holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));

                        } else if (descuento >= (descuentoMax+0.1)&&descuento!=100) {
                            holder.editDspPorcentaje.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                            holder.editDspPorcentaje.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                            Toast.makeText(Context, "El porcentaje de descuento mayor al "+descuentoMax+"% esta sujeto a evaluación", Toast.LENGTH_LONG).show();
                        }

                        lead.setOrden_detalle_porcentaje_descuento(""+(descuento));
                        //lead.setOrden_detalle_descuentocontado(""+descuento);
                        Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.getOrden_detalle_porcentaje_descuento:"+lead.getOrden_detalle_porcentaje_descuento());
                        Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.descuento:"+descuento);
                        v.setText(Convert.numberForViewDecimals(String.valueOf(descuento),getDiscountPercentDecimals()));
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

                            lead.setOrden_detalle_monto_descuento(
                                    //Formula Para Calcular el Monto de Descuento
                                    formulasController.applyDiscountPercentageForLine(
                                            //Variable 1 Monto Total Linea Sin IGV
                                            //lead.getOrden_detalle_montototallinea(),
                                            lead.getOrden_detalle_montosubtotal(),
                                            //Variable 2  Porcentaje Descuento
                                            (lead.getOrden_detalle_porcentaje_descuento())
                                    ));
                            Log.e("REOS", "ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.setOrden_detalle_monto_descuento() " + lead.getOrden_detalle_monto_descuento());
                            lead.setOrden_detalle_montototallinea(formulasController.suma(xd1,lead.getOrden_detalle_monto_igv()));
                            ////////////////////////////////////////////////////////////////////////
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(lead.getOrden_detalle_montototallinea());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());

                            //////////
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_descuento (lead.getOrden_detalle_monto_descuento());
                            Log.e("REOS", "ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.getOrden_detalle_montosubtotalcondescuento() " + lead.getOrden_detalle_montosubtotalcondescuento());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_porcentaje_descuento(lead.getOrden_detalle_porcentaje_descuento());

                            Log.e("REOS", "ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.getOrden_detalle_porcentaje_descuento() " + lead.getOrden_detalle_porcentaje_descuento());
                            //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_descuentocontado(lead.getOrden_detalle_descuentocontado());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
                        }
                    }
                    /////////////////////////////////////////////////////////////CALCULO MONTOS TOTALES//////////////////////////////////////
                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity= new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);
                    ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity);
                    ActualizaListaOrdenDetallePromocion(lead);
                    ActualizarResumenOrdenVenta();

                }
                // Ocultar teclado virtual
                v.clearFocus();//retirar focus
                ocultarTeclado(v);
            }

            return procesado;
        });*/

        if(lead.getOrden_detalle_cantidad().isEmpty())
        {
            holder.tv_orden_detalle_galon_acumulado.setText (Convert.numberForView2(String.valueOf( Float.parseFloat("0")*Float.parseFloat(lead.getOrden_detalle_gal()))));
        }
        else
        {
            holder.tv_orden_detalle_galon_acumulado.setText( (Convert.numberForView2(String.valueOf(Float.parseFloat(lead.getOrden_detalle_cantidad())*Float.parseFloat(lead.getOrden_detalle_gal())))));
        }
       /* listaOrdenVentaDetalleListaPromocionAdapter =
                new ListaOrdenVentaDetalleListaPromocionAdapter(getContext(),
                        ListaOrdenVentaDetalleListaPromocionDao.getInstance().getLeads(lead.getOrden_detalle_lista_promocion_cabecera()));
        holder.lv_promociondetalle.setAdapter(listaOrdenVentaDetalleListaPromocionAdapter);*/
        Log.e("REOS","ListSalesOrderDetailAdapter.lead.getOrden_detalle_promocion_habilitada(): "+lead.getOrden_detalle_promocion_habilitada());
        Log.e("REOS","ListSalesOrderDetailAdapter.lead.getOrden_detalle_terminopago_id(): "+lead.getOrden_detalle_terminopago_id());
        Log.e("REOS","ListSalesOrderDetailAdapter.holder.chk_descuento_contado.isChecked(): "+holder.chk_descuento_contado.isChecked());
        //Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.getText(): "+holder.editDspPorcentaje.getText());
        Log.e("REOS","ListSalesOrderDetailAdapter.SesionEntity.quotation: "+SesionEntity.quotation);
        Log.e("REOS","ListSalesOrderDetailAdapter.U_VIS_ManagementType: "+ObjUsuario.getU_VIS_ManagementType());
        if(lead.getOrden_detalle_promocion_habilitada().equals("0") ||holder.chk_descuento_contado.isChecked()||Double.parseDouble(lead.getOrden_detalle_porcentaje_descuento())>=100)
        {
            switch (BuildConfig.FLAVOR)
            {
                case "peru":
                    Resources res = getContext().getResources(); // need this to fetch the drawable
                    Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_24);
                    holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                    holder.imv_consultar_promocion_cabecera.setEnabled(false);
                    break;
                case "bolivia":
                    if(SesionEntity.quotation.equals("Y"))
                    {
                        Resources res2 = getContext().getResources(); // need this to fetch the drawable
                        Drawable draw2 = res2.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw2);
                        holder.imv_consultar_promocion_cabecera.setEnabled(true);
                    }else {
                        Resources res1 = getContext().getResources(); // need this to fetch the drawable
                        Drawable draw1 = res1.getDrawable( R.drawable.ic_baseline_card_giftcard_24);
                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw1);
                        holder.imv_consultar_promocion_cabecera.setEnabled(false);
                    }
                    break;
            }
        }
        else
        {
            if(SesionEntity.quotation.equals("Y"))
            {
                if(ObjUsuario.getU_VIS_ManagementType().equals("B2B"))
                {
                    /*Resources res4 = getContext().getResources(); // need this to fetch the drawable
                    Drawable borde_editext_orden_venta_detalle_habilitado4 = res4.getDrawable( R.drawable.custom_border_button_red);
                    holder.btn_edit_price.setEnabled(true);
                    holder.btn_edit_price.setBackground(borde_editext_orden_venta_detalle_habilitado4);
                    holder.btn_edit_price.setTextColor(Color.parseColor("#FFFFFF"));*/
                    Log.e("REOS","ListSalesOrderDetailAdapter.oncreate.entroif.quotation(Y).U_VIS_ManagementType: "+ObjUsuario.getU_VIS_ManagementType());
                }else {
                    Resources res1 = getContext().getResources(); // need this to fetch the drawable
                    Drawable borde_editext_orden_venta_detalle_inhabilitado = res1.getDrawable( R.drawable.borde_editext_ov_gris);
                    Resources res = getContext().getResources(); // need this to fetch the drawable
                    Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                    holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                    holder.imv_consultar_promocion_cabecera.setEnabled(true);
                }
            }else {
                Resources res1 = getContext().getResources(); // need this to fetch the drawable
                Drawable borde_editext_orden_venta_detalle_inhabilitado = res1.getDrawable( R.drawable.borde_editext_ov_gris);
                Resources res = getContext().getResources(); // need this to fetch the drawable
                Drawable draw = res.getDrawable( R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                holder.imv_consultar_promocion_cabecera.setEnabled(true);
            }

        }
        if(!(lead.getOrden_detalle_lista_promocion_cabecera()==null))
        {

            String calculodescuento="";
            if(BuildConfig.FLAVOR.equals("peru"))
            {
                calculodescuento=formulasController.CalcularPorcentajeDescuentoPorLinea(
                        lead.getOrden_detalle_lista_promocion_cabecera(),
                        lead.getOrden_detalle_porcentaje_descuento()
                );

            }else {
                calculodescuento=formulasController.CalcularPorcentajeDescuentoPorLinea(
                        lead.getOrden_detalle_lista_promocion_cabecera(),
                        lead.getOrden_detalle_descuentocontado()

                );
            }

            Log.e("REOS","ListSalesOrderDetailAdapter.!(lead.getOrden_detalle_lista_promocion_cabecera()==null.lead.getOrden_detalle_lista_promocion_cabecera():" + calculodescuento);
            Log.e("REOS","ListSalesOrderDetailAdapter.!(lead.getOrden_detalle_lista_promocion_cabecera()==null.lead.getOrden_detalle_descuentocontado():" + lead.getOrden_detalle_descuentocontado());
            Log.e("REOS","ListSalesOrderDetailAdapter.!(lead.getOrden_detalle_lista_promocion_cabecera()==null.lead.getOrden_detalle_porcentaje_descuento():" + lead.getOrden_detalle_porcentaje_descuento());
            Log.e("REOS","ListSalesOrderDetailAdapter.!(lead.getOrden_detalle_lista_promocion_cabecera()==null.calculodescuento:" + calculodescuento);
            holder.tv_orden_detalle_porcentaje_descuento.setText((calculodescuento));
            //lead.setOrden_detalle_porcentaje_descuento(String.valueOf(Double.parseDouble(calculodescuento)+Double.parseDouble(lead.getOrden_detalle_porcentaje_descuento())));
            lead.setOrden_detalle_monto_descuento(
                    //Formula Para Calcular el Monto de Descuento
                    formulasController.applyDiscountPercentageForLine(
                            //Variable 1 Monto Total Linea Sin IGV
                            //lead.getOrden_detalle_montototallinea(),
                            lead.getOrden_detalle_montosubtotal(),
                            //Variable 2  Porcentaje Descuento
                            (lead.getOrden_detalle_porcentaje_descuento())
                    ));
            Log.e("REOS","ListSalesOrderDetailAdapter:lead.getOrden_detalle_monto_descuento:" + lead.getOrden_detalle_monto_descuento());
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

            //setText(Convert.numberForView2 (lead.getOrden_detalle_montosubtotal()));
            if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
            {
                holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
            }else {
                holder.tv_orden_detalle_total.setText(Convert.currencyForView (lead.getOrden_detalle_montosubtotal()));
            }
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

            Log.e("REOS", "ListSalesOrderDetailAdapter-LineaSinPromocion-Inicio");
            holder.tv_orden_detalle_porcentaje_descuento.setText(lead.getOrden_detalle_porcentaje_descuento());
            //holder.tv_orden_detalle_total.setText(Convert.numberForView2 (lead.getOrden_detalle_montosubtotal()));
            if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
            {
                holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
            }else {
                holder.tv_orden_detalle_total.setText(Convert.currencyForView (lead.getOrden_detalle_montosubtotal()));
            }
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
        /*fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizarResumen());*/
        ActualizarResumenOrdenVenta();
        /*if(!lead.getOrden_detalle_cantidad().equals(""))
        {
            lead.setOrden_detalle_montosubtotal(formulasController.CalcularMontoSubTotalporLinea(lead.getOrden_detalle_precio_unitario(),lead.getOrden_detalle_cantidad()));
        }*/
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


        Log.e("REOS", "ListSalesOrderDetailAdapter-getView-recorreadapter");
        holder.et_orden_detalle_cantidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.e("REOS", "ListSalesOrderDetailAdapter-getView-holder.et_orden_detalle_cantidad-Gained focus");
                } else {
                    Log.e("REOS", "ListSalesOrderDetailAdapter-getView-holder.et_orden_detalle_cantidad-Lost focus");
                    //Log.e("REOS", "Lost focus");
                }
            }
        });
        holder.et_orden_detalle_cantidad.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean procesado = false;
                boolean FLAG_STOCK=(SesionEntity.FLAG_STOCK.equals("Y"))?true:false;
                boolean result_flag=true;
                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    String valor="0";
                    if(v.length()==0)
                    {
                        valor="0";
                    }
                    else {
                        valor=String.valueOf(Integer.parseInt(v.getText().toString()));
                    }

                    //Aqui
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
                            Log.e("JEPICMA","ListSalesOrderDetailAdapter-Stock flag=>"+FLAG_STOCK);
                            Log.e("JEPICMA","ListSalesOrderDetailAdapter-Stock flag=>"+cantidadNum+"-"+stockNum);
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
                    //Hasta aqui

                    //ejecuta calculo dependiendo el resultado del flag
                    if(result_flag) {
                        lead.setOrden_detalle_cantidad(valor);

                        //holder.tv_orden_detalle_total.setText(
                        lead.setOrden_detalle_montosubtotal(formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(), lead.getOrden_detalle_cantidad())
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
                                                Float.parseFloat(lead.getOrden_detalle_monto_igv())
                                ));
                        if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
                        {
                            holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
                        }else {
                            holder.tv_orden_detalle_total.setText(Convert.currencyForView (lead.getOrden_detalle_montosubtotal()));
                        }
                        holder.tv_orden_detalle_galon_acumulado.setText(
                                Convert.numberForView2(String.valueOf(Float.parseFloat(lead.getOrden_detalle_cantidad()) * Float.parseFloat(lead.getOrden_detalle_gal())))
                        );

                        for (int i = 0; i < OrdenVentaDetalleView.listadoProductosAgregados.size(); i++) {
                            if (i == (Integer.parseInt(lead.getOrden_detalle_item()) - 1)) {

                                lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                                lead.setOrden_detalle_montototallinea(
                                        String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                                        lead.getOrden_detalle_montosubtotal(),
                                                        lead.getOrden_detalle_monto_descuento()
                                                ))
                                                        +
                                                        Float.parseFloat(lead.getOrden_detalle_monto_igv())
                                        ));
                                //prueba
                                //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_item(lead.getOrden_detalle_item());
                                //
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
                        Log.e("REOS", "ListSalesOrderDetailAdapter.listaPromocionCabecera:" + listaPromocionCabecera.size());
                        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
                        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

                        if (listaPromocionCabecera.isEmpty()) {

                            listaPromocionCabecera = promocionCabeceraSQLiteDao.ObtenerPromocionCabecera(
                                    ObjUsuario.compania_id,
                                    ObjUsuario.fuerzatrabajo_id,
                                    ObjUsuario.usuario_id,
                                    lead.getOrden_detalle_producto_id(),
                                    lead.getOrden_detalle_umd(),
                                    lead.getOrden_detalle_cantidad(),
                                    SesionEntity.contado,
                                    lead.getOrden_detalle_terminopago_id(),
                                    lead.getOrden_detalle_cardcode(),
                                    lead.getOrden_detalle_currency()
                            );
                            Log.e("REOS", "ListSalesOrderDetailAdapter.listaPromocionCabecera:" + listaPromocionCabecera.size());
                            Log.e("REOS", "ListSalesOrderDetailAdapter.lead.getOrden_detalle_terminopago_id()listaPromocionCabecera:" + lead.getOrden_detalle_terminopago_id());
                            Log.e("REOS", "ListSalesOrderDetailAdapter.lead.getOrden_detalle_cardcode():" + lead.getOrden_detalle_cardcode());
                        }
                        actualizarlistapromocioncabecera(lead);
                        if (!listaPromocionCabecera.isEmpty()) {
                            if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("bolivia"))
                            {
                                if(SesionEntity.quotation.equals("Y"))
                                {
                                    if(ObjUsuario.getU_VIS_ManagementType().equals("B2C"))
                                    {
                                        Resources res = getContext().getResources(); // need this to fetch the drawable
                                        Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                                        holder.imv_consultar_promocion_cabecera.setEnabled(true);
                                        lead.setOrden_detalle_promocion_habilitada("1");
                                    }
                                    else {

                                    }
                                }else {
                                    Resources res = getContext().getResources(); // need this to fetch the drawable
                                    Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                                    holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                                    holder.imv_consultar_promocion_cabecera.setEnabled(true);
                                    lead.setOrden_detalle_promocion_habilitada("1");
                                }
                            }else {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                                holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                                holder.imv_consultar_promocion_cabecera.setEnabled(true);
                                lead.setOrden_detalle_promocion_habilitada("1");
                            }
                        } else {
                            Resources res = getContext().getResources(); // need this to fetch the drawable
                            Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_24);
                            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                            holder.imv_consultar_promocion_cabecera.setEnabled(false);
                            lead.setOrden_detalle_promocion_habilitada("0");
                        }

                        procesado = true;

                        //OrdenVentaDetalleView.listaPromocionCabecera=listaPromocionCabecera;
                        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity = new ArrayList<>();
                        listaOrdenVentaDetalleEntity.add(lead);
                        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));
                        ActualizaListaOrdenDetallePromocion(lead);
                        //result_flag=false;
                    }
                    InputMethodManager imm =
                            (InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }
                /*else {
                    // Ocultar teclado virtual
                    InputMethodManager imm =
                            (InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    v.clearFocus();
                }*/

                // Ocultar teclado virtual
                /*InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                v.clearFocus();*/
                /*boolean procesado = false;
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
                    UpdateLine(lead,holder);
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
                                lead.getOrden_detalle_cardcode(),
                                lead.getOrden_detalle_currency()
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
                */
                return procesado;
            }
        });

        holder.imv_consultar_promocion_cabecera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (BuildConfig.FLAVOR){
                    case "ecuador":
                    case "peru":
                    case "chile":
                    case "bolivia":
                        GeneratePromotion(lead);
                        break;
                    default:
                        alertaAsignarPromocion(lead).show();
                        break;
                }

            }});

        holder.imv_eliminar_orden_venta_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaEliminarLina( (position)).show();
            }
        });
        if((!(lead.getOrden_detalle_lista_promocion_cabecera()==null))&&holder.layout.getChildCount()==0)
        {
            Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.setEnabled(false)-Linea:"+lead.getOrden_detalle_item());
            if(!BuildConfig.FLAVOR.equals("peru"))
            {
                Resources res = getContext().getResources(); // need this to fetch the drawable
                Drawable borde_editext_orden_venta_detalle_inhabilitado = res.getDrawable(R.drawable.borde_editext_ov_gris);
                holder.editDspPorcentaje.setEnabled(false);
                holder.editDspPorcentaje.setBackground(borde_editext_orden_venta_detalle_inhabilitado);
                holder.editDspPorcentaje.setTextColor(Color.parseColor("#808080"));
            }


            itemChecked[position] = true;
            FormulasController formulasController=new FormulasController(getContext());

            for(int i=0;i<lead.getOrden_detalle_lista_promocion_cabecera().size();i++)
            {
                LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                int id = R.layout.layout_lista_orden_venta_detalle_lista_promocion_bolivia;
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
                tv_orden_venta_detalle_lista_promocion_preciobase.setText(Convert.currencyForView(String.valueOf(Float.parseFloat(String.valueOf(formulasController.ObtenerCalculoPromocionDetalle(
                        lead.getOrden_detalle_lista_promocion_cabecera(), i
                ))))));
                tv_orden_venta_detalle_lista_promocion_cantidad_lineas.setText(String.valueOf(
                        formulasController.ObtenerCantidadLineasPromocionDetalle(lead.getOrden_detalle_lista_promocion_cabecera(),
                                i
                        )));
                tv_orden_venta_detalle_lista_promocion_porcentaje_descuento.setText(lead.getOrden_detalle_lista_promocion_cabecera().get(i).getDescuento());
                holder.layout.addView(relativeLayout);
            }
        }else
        {
            Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.setEnabled(true)-Linea:"+lead.getOrden_detalle_item());
            itemChecked[position] = false;
        }

        if(BuildConfig.FLAVOR.equals("peru"))
        {
            if(ObjUsuario.getU_VIS_ManagementType().equals("B2C"))
            {
                holder.chk_descuento_contado.setChecked(false);
                Resources res2 = getContext().getResources(); // need this to fetch the drawable
                Drawable draw = res2.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                holder.imv_consultar_promocion_cabecera.setEnabled(true);
                holder.et_porcentaje_descuento_contado.setText("0");

            }

        }
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-lead.getOrden_detalle_montototallinea():"+lead.getOrden_detalle_montototallinea());
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-lead.getOrden_detalle_monto_descuento():"+lead.getOrden_detalle_monto_descuento());
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-lead.getOrden_detalle_porcentaje_descuento():"+lead.getOrden_detalle_porcentaje_descuento());
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-lead.getOrden_detalle_porcentaje_descuento_maximo():"+lead.getOrden_detalle_porcentaje_descuento_maximo());
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-formulasController.applyDiscountPercentageForLine(lead.getOrden_detalle_montototallinea(),lead.getOrden_detalle_porcentaje_descuento()):"+formulasController.applyDiscountPercentageForLine(lead.getOrden_detalle_montototallinea(),lead.getOrden_detalle_porcentaje_descuento()));
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-lead.getOrden_detalle_montosubtotal():"+lead.getOrden_detalle_montosubtotal());
        Log.e("REOS","ListSalesOrderDetailAdapter.tv_orden_detalle_total_igv-lead.getOrden_detalle_montosubtotalcondescuento():"+lead.getOrden_detalle_montosubtotalcondescuento());

        holder.btn_editDspPorcentaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDiscountPercent(lead,holder).show();
            }
        });

        holder.btn_edit_orden_detalle_cantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEditCountLine(lead,holder).show();
            }
        });


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


    private Dialog alertaAsignarPromocion(ListaOrdenVentaDetalleEntity lead) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_alert_dialog_orden_venta_detalle);

        TextView textTitle = dialog.findViewById(R.id.text_orden_venta_detalle);
        textTitle.setText(Context.getResources().getString(R.string.warning).toUpperCase());

        TextView textMsj = dialog.findViewById(R.id.textViewMsj_orden_venta_detalle);
        textMsj.setText(Context.getResources().getString(R.string.disabled_descount_for_the_promotion));

        ImageView image = (ImageView) dialog.findViewById(R.id.image_orden_venta_detalle);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK_orden_venta_detalle);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel_orden_venta_detalle);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GeneratePromotion(lead);
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

    public void GeneratePromotion(ListaOrdenVentaDetalleEntity lead)
    {
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntities= new ArrayList<>();
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Log.e("REOS","ListSalesOrderDetailAdapter-holder.imv_consultar_promocion_cabecera.lead.getOrden_detalle_producto_id():"+lead.getOrden_detalle_producto_id());
        listaOrdenVentaDetalleEntities.add(lead);
        Object [] listaobjetos=new Object[2];
        //OrdenVentaDetalleView.listaPromocionCabecera=new ArrayList<>();
        actualizarlistapromocioncabecera(lead);
        Log.e("REOS","ListSalesOrderDetailAdapter-GeneratePromotion-OrdenVentaDetalleView.listaPromocionCabecera.size():"+OrdenVentaDetalleView.listaPromocionCabecera.size());
        listaobjetos[0]=OrdenVentaDetalleView.listaPromocionCabecera;
        listaobjetos[1]=listaOrdenVentaDetalleEntities;
        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceEnviaListaPromocion(listaobjetos));
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
        EditText editDspPorcentaje;
        TextView tv_orden_detalle_precio_igv;
        TextView tv_orden_detalle_total_igv;
        TextView tv_orden_liter;
        TextView tv_orden_detalle_sigaus;
        TableRow tr_sigaus;
        ViewGroup layout;
        //TextView lbl_orden_detalle_precio;
        Button btn_edit_price;
        Button btn_editDspPorcentaje;
        Button btn_edit_orden_detalle_cantidad;

    }
    public boolean ValidarPorcentajeDescuentoContado(String valoreditext)
    {
        boolean resultado=false;
        if(Float.parseFloat(valoreditext)>Float.parseFloat(SesionEntity.U_VIS_CashDscnt))
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
        Log.e("REOS","ListSalesOrderDetailAdapter-Inicia-ActualizaListaOrdenDetallePromocion");
        Log.e("REOS","ListSalesOrderDetailAdapter-lead.getOrden_detalle_item:"+lead.getOrden_detalle_item());
        //Declaracion de Variables
        ArrayList<ListaOrdenVentaDetallePromocionEntity> arraylistListaOrdenVentaDetalleEntityPromocion = new ArrayList<>();
        ListaOrdenVentaDetallePromocionEntity listaOrdenVentaDetallePromocionEntity;
        int orden_detalle_promocion_item=0,orden_detalle_promocion_cantidad=0,orden_detalle_promocion_linea_referencia=0;
        boolean chk_descuentocontadoaplicado=false;
        String contadodescuento="0";
        //Evalua si la promocion de la cabecera no esta vacia

        //Si descuentocontado es mayor a 0 y check descuento contado es verdadero y check aplicado descuento contado es falso
        if(Float.parseFloat(lead.getOrden_detalle_descuentocontado())>0
                &&lead.isOrden_detalle_chk_descuentocontado()&&
                (!lead.isOrden_detalle_chk_descuentocontado_aplicado())
        )
        {
            Log.e("REOS","ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
            contadodescuento=lead.getOrden_detalle_descuentocontado();
            chk_descuentocontadoaplicado=true;

        }
        else
        {
            Log.e("REOS","ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones");
            contadodescuento="0";
            chk_descuentocontadoaplicado=false;
            //if(listaOrdenVentaDetalleEntity.get(a).getOrden_detalle_lista_orden_detalle_promocion().get(b).isOrden_detalle_chk_descuentocontado_aplicado())
            //{
            Log.e("REOS","ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-buclecumplecondiciones-2dobuclecumplecondiciones");
            //chk_descuentocontadoaplicado=true;
            //}

        }
        //Si check descuento contado aplicado es falso
        if(!lead.isOrden_detalle_chk_descuentocontado_aplicado()) {
            Log.e("REOS","ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Lead-sinChkdescuentoaplicado");
            //Si la promocion es distinto a nulo
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
                    //listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento();
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-LineaPadre.lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento():"+lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento());
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-LineaPadre.lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadpromocion():"+lead.getOrden_detalle_lista_promocion_cabecera().get(a).getCantidadpromocion());

                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                            Float.parseFloat(lead.getOrden_detalle_porcentaje_descuento())
                            +
                            Float.parseFloat(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento())
                    );
                    //Float.parseFloat(lead.getOrden_detalle_lista_promocion_cabecera().get(a).getDescuento()
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-LineaPadre.listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento:"+listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento);
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
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = Convert.numberForView2(String.valueOf (Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                            ))
                                    +
                                    Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv())
                    ));
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
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Fin Registro Linea Padre");
                    //Inserta Lineas de Detalle
                    for (int b = 0; b < lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().size(); b++) {
                        if (!lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getProducto_id().equals("%" +
                                "")) {
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inicia Linea DESCUENTO");
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-chk_descuentocontadoaplicado" + String.valueOf(chk_descuentocontadoaplicado));
                            //Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).isOrden_detalle_chk_descuentocontado_aplicado()" + String.valueOf(lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).isOrden_detalle_chk_descuentocontado_aplicado()));
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-contadodescuento: " + contadodescuento);
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado(): " + lead.isOrden_detalle_chk_descuentocontado());
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_descuentocontado(): " + lead.getOrden_detalle_descuentocontado());
                            //Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).getOrden_detalle_porcentaje_descuento(): " + lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).getOrden_detalle_porcentaje_descuento());
                            //Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).isOrden_detalle_chk_descuentocontado_aplicado(): " + lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).isOrden_detalle_chk_descuentocontado_aplicado());

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
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-RegistraDetalleLineaPadre.getOrden_detalle_lista_promocion.get(a).getListaPromocionDetalleEntities().get(b).getPreciobase():"+lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase());
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                                    (lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getPreciobase(),
                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getListaPromocionDetalleEntities().get(b).getDescuento();;
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-RegistraDetalleLineaPadre.listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento:"+listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento);
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
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = Convert.numberForView2(String.valueOf  (Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                            listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento()
                                    ))
                                            +
                                            Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_igv())
                            ));
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_lista_promocion_cabecera = null;
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_habilitada = "0";

                            if(BuildConfig.FLAVOR.equals("ecuador"))
                            {
                                listaOrdenVentaDetallePromocionEntity.orden_detalle_gal = lead.getOrden_detalle_gal();
                                listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = String.valueOf(Float.parseFloat(listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad) * Float.parseFloat(lead.getOrden_detalle_gal()));
                            }else {
                                listaOrdenVentaDetallePromocionEntity.orden_detalle_gal = "0";
                                listaOrdenVentaDetallePromocionEntity.orden_detalle_gal_acumulado = "0";
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
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_linea_referencia_padre = String.valueOf(orden_detalle_promocion_linea_referencia);
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_promocion_id = lead.getOrden_detalle_lista_promocion_cabecera().get(a).getPromocion_id();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_descuentocontado = lead.getOrden_detalle_descuentocontado();
                            listaOrdenVentaDetallePromocionEntity.orden_detalle_chk_descuentocontado_aplicado = chk_descuentocontadoaplicado;
                            //Registra Linea de Promocion
                            arraylistListaOrdenVentaDetalleEntityPromocion.add(listaOrdenVentaDetallePromocionEntity);
                            Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Fin Registro Linea DESCUENTO");
                        }
                    }
                }

                //Inserta cantidad Restante de la Linea Padre
                //Si la cantidad de Promocion es menor a la cantidad de la Linea
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inserta cantidad Restante de la Linea Padre");
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-orden_detalle_promocion_cantidad"+orden_detalle_promocion_cantidad);
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_cantidad()"+lead.getOrden_detalle_cantidad());
                if (orden_detalle_promocion_cantidad < Integer.parseInt(lead.getOrden_detalle_cantidad())) {
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inserta cantidad Restante de la Linea Padre-Ingreso");
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inicia Linea DESCUENTO");
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-chk_descuentocontadoaplicado" + String.valueOf(chk_descuentocontadoaplicado));
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).isOrden_detalle_chk_descuentocontado_aplicado()" + String.valueOf(lead.isOrden_detalle_chk_descuentocontado_aplicado()));
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-contadodescuento: " + contadodescuento);
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado(): " + lead.isOrden_detalle_chk_descuentocontado());
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_descuentocontado(): " + lead.getOrden_detalle_descuentocontado());
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).getOrden_detalle_porcentaje_descuento(): " + lead.getOrden_detalle_porcentaje_descuento());
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_lista_orden_detalle_promocion().get(a).isOrden_detalle_chk_descuentocontado_aplicado(): " + lead.isOrden_detalle_chk_descuentocontado_aplicado());

                    orden_detalle_promocion_item++;

                    int cantidadlineaordendetalle = 0, cantidadlineaordendetallepromocion = 0, cantidadrestantelineaordendetalle = 0;
                    cantidadlineaordendetalle = Integer.parseInt(lead.getOrden_detalle_cantidad());
                    //cantidadlineaordendetallepromocion=Integer.parseInt(arraylistListaOrdenVentaDetalleEntityPromocion.get(c).getOrden_detalle_cantidad());
                    cantidadrestantelineaordendetalle = cantidadlineaordendetalle - orden_detalle_promocion_cantidad;
                    listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_producto_id();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_producto();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_umd();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock_almacen();
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad = String.valueOf(cantidadrestantelineaordendetalle);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inserta cantidad Restante de la Linea Padre-Ingreso-lead.getOrden_detalle_precio_unitario()"+lead.getOrden_detalle_precio_unitario());
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inserta cantidad Restante de la Linea Padre-Ingreso-cantidadrestantelineaordendetalle"+cantidadrestantelineaordendetalle);
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                            (lead.getOrden_detalle_precio_unitario(),
                                    listaOrdenVentaDetallePromocionEntity.getOrden_detalle_cantidad());
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inserta cantidad Restante de la Linea Padre-Ingreso-listaOrdenVentaDetallePromocionEntity"+listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal());
                    //listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = "0";
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-CantidadRestanteDetalleLineaPadre.listaOrdenVentaDetallePromocionEntity.contadodescuento:"+contadodescuento);
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-CantidadRestanteDetalleLineaPadre.listaOrdenVentaDetallePromocionEntity.lead.getOrden_detalle_porcentaje_descuento():"+lead.getOrden_detalle_porcentaje_descuento());
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                            //Integer.parseInt(lead.getOrden_detalle_porcentaje_descuento())+
                            Float.parseFloat(contadodescuento)+
                            Float.parseFloat(lead.getOrden_detalle_porcentaje_descuento())
                            //"0"
                    );
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-CantidadRestanteDetalleLineaPadre.listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento:"+listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento);
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
                    listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = Convert.numberForView2(String.valueOf (Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
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
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Fin Linea DESCUENTO");

                }
            }
            //Si la cantidad de Promocion es mayor a la cantidad de la Linea
            else {
                if (lead.getOrden_detalle_cantidad().equals("")) {
                    lead.setOrden_detalle_cantidad("");
                }
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Inicia Si la cantidad de Promocion es mayor a la cantidad de la Linea");
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-chk_descuentocontadoaplicado" + String.valueOf(chk_descuentocontadoaplicado));
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado_aplicado()" + String.valueOf(lead.isOrden_detalle_chk_descuentocontado_aplicado()));
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-contadodescuento: " + contadodescuento);
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado(): " + lead.isOrden_detalle_chk_descuentocontado());
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_descuentocontado(): " + lead.getOrden_detalle_descuentocontado());
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.getOrden_detalle_porcentaje_descuento(): " + lead.getOrden_detalle_porcentaje_descuento());
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-lead.isOrden_detalle_chk_descuentocontado_aplicado(): " + lead.isOrden_detalle_chk_descuentocontado_aplicado());
                orden_detalle_promocion_item++;
                listaOrdenVentaDetallePromocionEntity = new ListaOrdenVentaDetallePromocionEntity();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_item = lead.getOrden_detalle_item();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_producto_id = lead.getOrden_detalle_producto_id();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_producto = lead.getOrden_detalle_producto();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_umd = lead.getOrden_detalle_umd();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_stock = lead.getOrden_detalle_stock_almacen();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_cantidad = lead.getOrden_detalle_cantidad();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_precio_unitario = lead.getOrden_detalle_precio_unitario();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_montosubtotal = formulasController.getTotalPerLine
                        (lead.getOrden_detalle_precio_unitario(),
                                lead.getOrden_detalle_cantidad());
                //listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = lead.getOrden_detalle_porcentaje_descuento();
                listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento = String.valueOf(
                        Float.parseFloat(lead.getOrden_detalle_porcentaje_descuento())
                        //+
                        //Float.parseFloat(contadodescuento)

                );
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-cantidaddePromocionesmayoralacantidaddelaLinea.listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento:"+listaOrdenVentaDetallePromocionEntity.orden_detalle_porcentaje_descuento);
                listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_descuento =
                        //Formula Para Calcular el Monto de Descuento
                        formulasController.applyDiscountPercentageForLine(
                                //Variable 1 Monto Total Linea Sin IGV
                                listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                                //Variable 2  Porcentaje Descuento
                                String.valueOf(Float.parseFloat(listaOrdenVentaDetallePromocionEntity.getOrden_detalle_porcentaje_descuento()))
                                // + Float.parseFloat(lead.getOrden_detalle_porcentaje_descuento()))
                        );
                listaOrdenVentaDetallePromocionEntity.orden_detalle_monto_igv = formulasController.CalcularMontoImpuestoPorLinea(
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_montosubtotal(),
                        listaOrdenVentaDetallePromocionEntity.getOrden_detalle_monto_descuento(),
                        Induvis.getImpuestoString()
                );
                listaOrdenVentaDetallePromocionEntity.orden_detalle_montototallinea = Convert.numberForView2(String.valueOf (Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
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
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-FIN Si la cantidad de Promocion es mayor a la cantidad de la Linea");
            }

            for (int d = 0; d < OrdenVentaDetalleView.listadoProductosAgregados.size(); d++) {
                Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-OrdenVentaDetalleView.listadoProductosAgregados.size():" + OrdenVentaDetalleView.listadoProductosAgregados.size());
                if (d == (Integer.parseInt(lead.getOrden_detalle_item()) - 1)) {
                    Log.e("REOS", "ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-CoincideLinea");
                    //Actualiza LineaOrdenVentaDetalle
                    if (OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion() != null) {
                        Log.e("REOS", "ListSalesOrderDetailAdapter-OrdenVentaDetalleView.ActualizaListaOrdenDetallePromocion-OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size()-Antes:" + OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size());
                    }
                    OrdenVentaDetalleView.listadoProductosAgregados.get(d).setOrden_detalle_lista_orden_detalle_promocion(
                            arraylistListaOrdenVentaDetalleEntityPromocion
                    );
                    //OrdenVentaDetalleView.listadoProductosAgregados.get(d).setOrden_detalle_chk_descuentocontado(lead.isOrden_detalle_chk_descuentocontado());
                    OrdenVentaDetalleView.listadoProductosAgregados.get(d).setOrden_detalle_chk_descuentocontado_aplicado(chk_descuentocontadoaplicado);

                    Log.e("REOS", "ListSalesOrderDetailAdapter-OrdenVentaDetalleView.ActualizaListaOrdenDetallePromocion-OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size()-Despues:" + OrdenVentaDetalleView.listadoProductosAgregados.get(d).getOrden_detalle_lista_orden_detalle_promocion().size());
                }
            }
        }
        else
        {
            Log.e("REOS","ListSalesOrderDetailAdapter-ActualizaListaOrdenDetallePromocion-Lead-conChkdescuentoaplicado");
        }


        Log.e("REOS","ListSalesOrderDetailAdapter-Finaliza-ActualizaListaOrdenDetallePromocion");
    }

    public void actualizarlistapromocioncabecera(ListaOrdenVentaDetalleEntity lead)
    {

        UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
        UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
        ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

        listaPromocionCabecera=promocionCabeceraSQLiteDao.ObtenerPromocionCabeceraUnidad(

                ObjUsuario.compania_id,
                ObjUsuario.fuerzatrabajo_id,
                ObjUsuario.usuario_id,
                lead.getOrden_detalle_producto_id(),
                lead.getOrden_detalle_umd(),
                lead.getOrden_detalle_cantidad(),
                SesionEntity.contado,
                lead.getOrden_detalle_terminopago_id(),
                lead.getOrden_detalle_cardcode()
        );

        if (listaPromocionCabecera.isEmpty()){

            listaPromocionCabecera=promocionCabeceraSQLiteDao.ObtenerPromocionCabecera(
                    ObjUsuario.compania_id,
                    ObjUsuario.fuerzatrabajo_id,
                    ObjUsuario.usuario_id,
                    lead.getOrden_detalle_producto_id(),
                    lead.getOrden_detalle_umd(),
                    lead.getOrden_detalle_cantidad(),
                    SesionEntity.contado,
                    lead.getOrden_detalle_terminopago_id(),
                    lead.getOrden_detalle_cardcode(),
                    lead.getOrden_detalle_currency()
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
        textMsj.setText("La Línea tiene adjunta una Promoción o Producto no permitido para el Descuento");
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
        Log.e("REOS","ListSalesOrderDetailAdapter-ActualizarResumenOrdenVenta");
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizarResumen());
    }
    private void ocultarTeclado(TextView v){
        InputMethodManager imm =(InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    public Dialog DialogUpdatePrice(ListaOrdenVentaDetalleEntity lead,ListSalesOrderDetailAdapter.ViewHolder holder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_edit_price);
        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        TextView subtitle = dialog.findViewById(R.id.tv_subtitle);
        TextView description = dialog.findViewById(R.id.tv_description);
        TextView price_list = dialog.findViewById(R.id.tv_price_list);
        TextView tv_error = dialog.findViewById(R.id.tv_error);

        Button dialogButtonUpdate = (Button) dialog.findViewById(R.id.dialogButtonOK);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        EditText et_edit_price_list = (EditText) dialog.findViewById(R.id.et_edit_price);
        TextView tv_price_unit_discount = dialog.findViewById(R.id.tv_price_unit_discount);
        TextView tv_price_discount = dialog.findViewById(R.id.tv_price_discount);



        tv_price_unit_discount.setText( Convert.currencyForView(
                //formulasController.getPriceReferencePack(lead.getOrden_detalle_price_listprice(),lead.getOrden_detalle_units())
                formulasController.getPriceReferencePack(
                        formulasController.getPriceReferencePack(lead.getOrden_detalle_montosubtotalcondescuento(),lead.getOrden_detalle_cantidad()),
                        lead.getOrden_detalle_units())
        ));

        tv_price_discount.setText ( Convert.currencyForView(
                formulasController.getPriceReferencePack(lead.getOrden_detalle_montosubtotalcondescuento(),lead.getOrden_detalle_cantidad())
        ));

        ArrayList<PriceListHeadEntity> listPriceListHeadEntity=new ArrayList<>();
        PriceListHeadSQLite priceListHeadSQLite=new PriceListHeadSQLite(getContext());
        String PrcntIncrease="";
        //Charge Date
        //dialogButton.setText("OK");
        textTitle.setText("IMPORTANTE");
        //subtitle.setText("Puede Incrementar el precio a un maximo del "+""+"%");
        description.setText(lead.getOrden_detalle_producto());
        listPriceListHeadEntity=priceListHeadSQLite.GetPriceListHead(lead.getOrden_detalle_listnum());

        for (int i=0;i<listPriceListHeadEntity.size();i++)
        {
            subtitle.setText("Puede Incrementar el precio a un maximo del "+Convert.amountForTwoDecimal(listPriceListHeadEntity.get(i).getPrcntIncrease())+" % del precio de lista");
            PrcntIncrease=listPriceListHeadEntity.get(i).getPrcntIncrease();
        }
        description.setText(lead.getOrden_detalle_producto());
        price_list.setText(Convert.currencyForView(lead.getOrden_detalle_price_listprice())+" ");
        et_edit_price_list.setText(lead.getOrden_detalle_precio_unitario()+" ");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        et_edit_price_list.setOnEditorActionListener((v, actionId, event) -> {

            boolean procesado = false;
            if (actionId == EditorInfo.IME_ACTION_SEND)
            {
                if(Convert.getStatusEditPrice(lead.getOrden_detalle_price_listprice(),v.getText().toString()))
                {

                }
                else {
                    //v.setText(lead.getOrden_detalle_price_listprice());
                    //Toast.makeText(Context, "El valor ingresado es menor a la lista de precio actual", Toast.LENGTH_LONG).show();
                    Toast.makeText(Context, "El porcentaje de diferencia es de: "+Convert.getDiscountPercentage(lead.getOrden_detalle_price_listprice(),et_edit_price_list.getText().toString()),Toast.LENGTH_LONG).show();
                }
            }
            v.clearFocus();//retirar focus
            ocultarTeclado(v);

            return procesado;
        });

        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        String finalPrcntIncrease = PrcntIncrease;
        dialogButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////////////////RECALCULAMOS TODO Y GUARDAMOS EN PERSITENCIA////////////////////

                Double percent=Convert.getPercentEditPrice(lead.getOrden_detalle_price_listprice(),et_edit_price_list.getText().toString());
                Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-percent: "+percent);
                Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-finalPrcntIncrease: "+finalPrcntIncrease);
                Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-lead.getOrden_detalle_price_listprice(): "+lead.getOrden_detalle_price_listprice());
                Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-et_edit_price_list.getText().toString(): "+et_edit_price_list.getText().toString());

                if(Float.parseFloat(et_edit_price_list.getText().toString())==Float.parseFloat(lead.getOrden_detalle_price_listprice()))
                {
                    Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-el precio editado es igual al precio de lista");
                }
                else if(Float.parseFloat(et_edit_price_list.getText().toString())>Float.parseFloat(lead.getOrden_detalle_price_listprice()))
                {
                    lead.setOrden_detalle_precio_unitario(et_edit_price_list.getText().toString());
                    UpdateLine(lead,holder);
                    dialog.dismiss();
                    Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-el precio editado es mayor al precio de lista");
                }
                else if(Float.parseFloat(et_edit_price_list.getText().toString())<Float.parseFloat(lead.getOrden_detalle_price_listprice()))
                {

                    Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-el precio editado es menor al precio de lista");
                    Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-porcentaje de descuento: "+Convert.getDiscountPercentage(lead.getOrden_detalle_price_listprice(),et_edit_price_list.getText().toString()));
                    lead.setOrden_detalle_porcentaje_descuento(Convert.getDiscountPercentage(lead.getOrden_detalle_price_listprice(),et_edit_price_list.getText().toString()));
                    UpdateLine(lead,holder);
                    dialog.dismiss();
                }
                /*if(Convert.getStatusEditPrice(finalPrcntIncrease,percent.toString()))
                {
                    Toast.makeText(Context, "El limite maximo de incremento de precio es del "+Convert.amountForTwoDecimal(finalPrcntIncrease)+"%,y el precio editado excede en un "  +Convert.amountForTwoDecimal(percent.toString())+ "% al precio actual ", Toast.LENGTH_LONG).show();
                    tv_error.setText("El limite maximo de incremento de precio es del "+Convert.amountForTwoDecimal(finalPrcntIncrease)+"%,y el precio editado excede en un "  +Convert.amountForTwoDecimal(percent.toString())+ "% al precio actual ");
                }else {

                    for(int i=0;i<OrdenVentaDetalleView.listadoProductosAgregados.size();i++){
                        Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-lead.getOrden_detalle_item(): "+lead.getOrden_detalle_item());
                        if(i==(Integer.parseInt(lead.getOrden_detalle_item())-1)){
                            Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-lead.getOrden_detalle_precio_unitario(): "+lead.getOrden_detalle_precio_unitario());
                            Log.e("REOS","ListSalesOrderDetailAdapter-DialogUpdatePrice-et_edit_price_list.getText().toString(): "+et_edit_price_list.getText().toString());
                            lead.setOrden_detalle_montosubtotal(formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(), lead.getOrden_detalle_cantidad())
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
                                                    Float.parseFloat(lead.getOrden_detalle_monto_igv())
                                    ));
                            lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                            lead.setOrden_detalle_montototallinea(
                                    String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                                    lead.getOrden_detalle_montosubtotal(),
                                                    lead.getOrden_detalle_monto_descuento()
                                            ))
                                                    +
                                                    Float.parseFloat(lead.getOrden_detalle_monto_igv())
                                    ));
                            //prueba
                            //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_item(lead.getOrden_detalle_item());
                            //
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(
                                    //String.valueOf (Float.parseFloat (holder.tv_orden_detalle_total.getText().toString())+Float.parseFloat(lead.getOrden_detalle_monto_igv()))
                                    lead.getOrden_detalle_montototallinea()
                            );
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_precio_unitario(et_edit_price_list.getText().toString());
                        }
                    }

                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity = new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);
                    fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));
                    ActualizaListaOrdenDetallePromocion(lead);

                    dialog.dismiss();
                }*/

                //UpdateLine(lead,holder);

            }
        });
        return  dialog;
    }


    public void UpdateLine(ListaOrdenVentaDetalleEntity lead,ListSalesOrderDetailAdapter.ViewHolder holder)
    {
        //lead.setOrden_detalle_porcentaje_descuento(calculodescuento);
        Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-lead.getOrden_detalle_porcentaje_descuento:" + lead.getOrden_detalle_porcentaje_descuento());
        lead.setOrden_detalle_monto_descuento(
                //Formula Para Calcular el Monto de Descuento
                formulasController.applyDiscountPercentageForLine(
                        //Variable 1 Monto Total Linea Sin IGV
                        //lead.getOrden_detalle_montototallinea(),
                        lead.getOrden_detalle_montosubtotal(),
                        //Variable 2  Porcentaje Descuento
                        (lead.getOrden_detalle_porcentaje_descuento())
                ));
        Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-lead.getOrden_detalle_monto_descuento:" + lead.getOrden_detalle_monto_descuento());
        //Monto subtotal sin descuento
        lead.setOrden_detalle_montosubtotal(
                formulasController.getTotalPerLine
                        (lead.getOrden_detalle_precio_unitario(),
                                lead.getOrden_detalle_cantidad()));
        //Monto subtotal con descuento
        Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-lead.setOrden_detalle_montosubtotal:" + lead.getOrden_detalle_montosubtotal());
        lead.setOrden_detalle_montosubtotalcondescuento(
                //Formula para Calcular el Monto Sub Total Con Descuento
                formulasController.CalcularMontoTotalconDescuento(
                        //Variable 1 Monto Total Linea Sin IGV
                        lead.getOrden_detalle_montosubtotal(),
                        //Variable 2  Monto Descuento
                        lead.getOrden_detalle_monto_descuento()
                ));
        //ActualizaListaOrdenDetallePromocion(lead);
        //ActualizarResumenOrdenVenta();
        //lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
        lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoPorLinea(
                lead.getOrden_detalle_montosubtotal(),
                lead.getOrden_detalle_monto_descuento(),
                Induvis.getImpuestoString()
        ));
        Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-lead.getOrden_detalle_monto_igv():" + lead.getOrden_detalle_monto_igv());
        lead.setOrden_detalle_montototallinea(
                String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                lead.getOrden_detalle_montosubtotal(),
                                lead.getOrden_detalle_monto_descuento()
                        ))
                                +
                                Float.parseFloat( lead.getOrden_detalle_monto_igv())
                ));
        Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-lead.getOrden_detalle_montosubtotalcondescuento:" + lead.getOrden_detalle_montosubtotalcondescuento());
        //holder.tv_orden_detalle_total.setText(Convert.currencyForView(lead.getOrden_detalle_montosubtotal()));
        switch (BuildConfig.FLAVOR)
        {
            case "bolivia":
                Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-bolivia-lead.getOrden_detalle_montototallinea:" + lead.getOrden_detalle_montototallinea());
                holder.tv_orden_detalle_total.setText(Convert.currencyForView(lead.getOrden_detalle_montototallinea()));
                break;
            default:
                Log.e("REOS","ListaOrdenVentaDetalleAdapter-UpdateLine-default-lead.getOrden_detalle_montosubtotal:" + lead.getOrden_detalle_montosubtotal());
                holder.tv_orden_detalle_total.setText(Convert.currencyForView(lead.getOrden_detalle_montosubtotal()));
                break;
        }

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
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(lead.getOrden_detalle_montototallinea());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_cantidad(lead.getOrden_detalle_cantidad());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_precio_unitario(lead.getOrden_detalle_precio_unitario());
            }
        }
        ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity = new ArrayList<>();
        listaOrdenVentaDetalleEntity.add(lead);
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));
        ActualizaListaOrdenDetallePromocion(lead);
        ActualizarResumenOrdenVenta();



        //ActualizaListaOrdenDetallePromocion(lead);
    }

    public Dialog DialogDiscountPercent(ListaOrdenVentaDetalleEntity lead,ListSalesOrderDetailAdapter.ViewHolder holder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_edit_discount_percent);
        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        TextView subtitle = dialog.findViewById(R.id.tv_subtitle);
        TextView description = dialog.findViewById(R.id.tv_description);
        //TextView tv_discount_percent = dialog.findViewById(R.id.tv_discount_percent);
        TextView et_edit_discount_percent = dialog.findViewById(R.id.et_edit_discount_percent);

        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        //Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        textTitle.setText("IMPORTANTE");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        subtitle.setText("Edicion de porcentaje de descuento");
        description.setText("Edite la cantidad en la caja de texto y calcule con el boton enviar en el teclado numerico");
        //tv_discount_percent.setText(lead.getOrden_detalle_porcentaje_descuento());

        et_edit_discount_percent.setText (lead.getOrden_detalle_porcentaje_descuento());

        et_edit_discount_percent.setOnEditorActionListener((v, actionId, event) -> {

            boolean procesado = false;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                procesado=true;

                if(lead.getOrden_detalle_cantidad().isEmpty()){
                    //v.setText(null);
                    Toast.makeText(Context, "Es necesario ingresar primero la cantidad", Toast.LENGTH_LONG).show();
                }else{
                    Double descuento = 0.000;
                    Double descuentoMax = 0.000;

                    if (v.length() != 0) {
                        try{
                            descuento = Double.valueOf(v.getText().toString());
                            descuentoMax = Double.valueOf(lead.getOrden_detalle_porcentaje_descuento_maximo());
                            Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.descuento:"+descuento);
                            Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.descuentoMax:"+descuentoMax);
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }

                    Double descountCountry=0.0;
                    if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("peru"))
                    {
                        descountCountry=99.9;
                    }
                    else {
                        descountCountry=100.0;
                    }

                    if (descuento <= descountCountry) {

                        if (descuento > 0 && descuento <= descuentoMax) {
                            et_edit_discount_percent.setBackgroundResource(R.drawable.borde_editext_ov_negro);
                            et_edit_discount_percent.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));

                        } else if (descuento >= (descuentoMax+0.1)&&descuento!=100) {
                            et_edit_discount_percent.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                            et_edit_discount_percent.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
                            Toast.makeText(Context, "El porcentaje de descuento mayor al "+descuentoMax+"% esta sujeto a evaluación", Toast.LENGTH_LONG).show();
                        }

                        lead.setOrden_detalle_porcentaje_descuento(""+(descuento));
                        //lead.setOrden_detalle_descuentocontado(""+descuento);
                        Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.getOrden_detalle_porcentaje_descuento:"+lead.getOrden_detalle_porcentaje_descuento());
                        Log.e("REOS","ListSalesOrderDetailAdapter.holder.editDspPorcentaje.descuento:"+descuento);
                        v.setText(Convert.numberForViewDecimals(String.valueOf(descuento),getDiscountPercentDecimals()));
                    } else {
                        Toast.makeText(Context, "El porcentaje de descuento no puede ser mayor al 99.9%", Toast.LENGTH_LONG).show();
                        et_edit_discount_percent.setBackgroundResource(R.drawable.borde_editext_ov_rojo);
                        et_edit_discount_percent.setTextColor(ContextCompat.getColor(getContext(), R.color.Rojo_Vistony));
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

                            lead.setOrden_detalle_monto_descuento(
                                    //Formula Para Calcular el Monto de Descuento
                                    formulasController.applyDiscountPercentageForLine(
                                            //Variable 1 Monto Total Linea Sin IGV
                                            //lead.getOrden_detalle_montototallinea(),
                                            lead.getOrden_detalle_montosubtotal(),
                                            //Variable 2  Porcentaje Descuento
                                            (lead.getOrden_detalle_porcentaje_descuento())
                                    ));
                            Log.e("REOS", "ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.setOrden_detalle_monto_descuento() " + lead.getOrden_detalle_monto_descuento());
                            lead.setOrden_detalle_montototallinea(formulasController.suma(xd1,lead.getOrden_detalle_monto_igv()));
                            ////////////////////////////////////////////////////////////////////////
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotal(lead.getOrden_detalle_montosubtotal());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_igv(lead.getOrden_detalle_monto_igv());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montototallinea(lead.getOrden_detalle_montototallinea());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_montosubtotalcondescuento(lead.getOrden_detalle_montosubtotalcondescuento());

                            //////////
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_monto_descuento (lead.getOrden_detalle_monto_descuento());
                            Log.e("REOS", "ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.getOrden_detalle_montosubtotalcondescuento() " + lead.getOrden_detalle_montosubtotalcondescuento());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_porcentaje_descuento(lead.getOrden_detalle_porcentaje_descuento());

                            Log.e("REOS", "ListSalesOrderDetailAdapter.holder.editDspPorcentaje.lead.getOrden_detalle_porcentaje_descuento() " + lead.getOrden_detalle_porcentaje_descuento());
                            //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_descuentocontado(lead.getOrden_detalle_descuentocontado());
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal(String.valueOf(holder.tv_orden_detalle_galon_unitario.getText()));
                            OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_gal_acumulado(String.valueOf(holder.tv_orden_detalle_galon_acumulado.getText()));
                        }
                    }
                    /////////////////////////////////////////////////////////////CALCULO MONTOS TOTALES//////////////////////////////////////
                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity= new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);
                    ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity);
                    ActualizaListaOrdenDetallePromocion(lead);
                    ActualizarResumenOrdenVenta();

                }
                // Ocultar teclado virtual
                //v.clearFocus();//retirar focus
                ocultarTeclado(v);
                dialog.dismiss();
            }

            return procesado;
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  dialog;
    }


    public Dialog DialogEditCountLine(ListaOrdenVentaDetalleEntity lead,ListSalesOrderDetailAdapter.ViewHolder holder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_edit_count_line);
        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        TextView subtitle = dialog.findViewById(R.id.tv_subtitle);
        TextView description = dialog.findViewById(R.id.tv_description);

        TextView et_edit_count_line = dialog.findViewById(R.id.et_edit_count_line);
        subtitle.setText("Edicion de cantidad de linea");
        description.setText("Edite la cantidad en la caja de texto y calcule con el boton enviar en el teclado numerico");
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        //Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        textTitle.setText("IMPORTANTE");
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        et_edit_count_line.setText (lead.getOrden_detalle_cantidad());

        et_edit_count_line.setOnEditorActionListener((v, actionId, event) -> {
            boolean procesado = false;
            boolean FLAG_STOCK=(SesionEntity.FLAG_STOCK.equals("Y"))?true:false;
            boolean result_flag=true;
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                String valor="0";
                if(v.length()==0)
                {
                    valor="0";
                }
                else {
                    valor=String.valueOf(Integer.parseInt(v.getText().toString()));
                }

                //Aqui
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
                        Log.e("JEPICMA","ListSalesOrderDetailAdapter-Stock flag=>"+FLAG_STOCK);
                        Log.e("JEPICMA","ListSalesOrderDetailAdapter-Stock flag=>"+cantidadNum+"-"+stockNum);
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
                //Hasta aqui

                //ejecuta calculo dependiendo el resultado del flag
                if(result_flag) {
                    lead.setOrden_detalle_cantidad(valor);

                    //holder.tv_orden_detalle_total.setText(
                    lead.setOrden_detalle_montosubtotal(formulasController.getTotalPerLine(lead.getOrden_detalle_precio_unitario(), lead.getOrden_detalle_cantidad())
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
                                            Float.parseFloat(lead.getOrden_detalle_monto_igv())
                            ));
                    if(BuildConfig.FLAVOR.equals("ecuador")||BuildConfig.FLAVOR.equals("chile")||BuildConfig.FLAVOR.equals("bolivia"))
                    {
                        holder.tv_orden_detalle_total.setText(Convert.currencyForView(getTotaLine(lead.getOrden_detalle_montosubtotal(), lead.getOrden_detalle_porcentaje_descuento(), Induvis.getImpuestoString())));
                    }else {
                        holder.tv_orden_detalle_total.setText(Convert.currencyForView (lead.getOrden_detalle_montosubtotal()));
                    }
                    holder.tv_orden_detalle_galon_acumulado.setText(
                            Convert.numberForView2(String.valueOf(Float.parseFloat(lead.getOrden_detalle_cantidad()) * Float.parseFloat(lead.getOrden_detalle_gal())))
                    );

                    for (int i = 0; i < OrdenVentaDetalleView.listadoProductosAgregados.size(); i++) {
                        if (i == (Integer.parseInt(lead.getOrden_detalle_item()) - 1)) {

                            lead.setOrden_detalle_monto_igv(formulasController.CalcularMontoImpuestoOrdenDetallePromocionLinea(lead));
                            lead.setOrden_detalle_montototallinea(
                                    String.valueOf(Float.parseFloat(formulasController.CalcularMontoTotalconDescuento(
                                                    lead.getOrden_detalle_montosubtotal(),
                                                    lead.getOrden_detalle_monto_descuento()
                                            ))
                                                    +
                                                    Float.parseFloat(lead.getOrden_detalle_monto_igv())
                                    ));
                            //prueba
                            //OrdenVentaDetalleView.listadoProductosAgregados.get(i).setOrden_detalle_item(lead.getOrden_detalle_item());
                            //
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
                    Log.e("REOS", "ListSalesOrderDetailAdapter.listaPromocionCabecera:" + listaPromocionCabecera.size());
                    UsuarioSQLiteEntity ObjUsuario=new UsuarioSQLiteEntity();
                    UsuarioSQLite usuarioSQLite=new UsuarioSQLite(Context);
                    ObjUsuario=usuarioSQLite.ObtenerUsuarioSesion();

                    if (listaPromocionCabecera.isEmpty()) {

                        listaPromocionCabecera = promocionCabeceraSQLiteDao.ObtenerPromocionCabecera(
                                ObjUsuario.compania_id,
                                ObjUsuario.fuerzatrabajo_id,
                                ObjUsuario.usuario_id,
                                lead.getOrden_detalle_producto_id(),
                                lead.getOrden_detalle_umd(),
                                lead.getOrden_detalle_cantidad(),
                                SesionEntity.contado,
                                lead.getOrden_detalle_terminopago_id(),
                                lead.getOrden_detalle_cardcode(),
                                lead.getOrden_detalle_currency()
                        );
                        Log.e("REOS", "ListSalesOrderDetailAdapter.listaPromocionCabecera:" + listaPromocionCabecera.size());
                        Log.e("REOS", "ListSalesOrderDetailAdapter.lead.getOrden_detalle_terminopago_id()listaPromocionCabecera:" + lead.getOrden_detalle_terminopago_id());
                        Log.e("REOS", "ListSalesOrderDetailAdapter.lead.getOrden_detalle_cardcode():" + lead.getOrden_detalle_cardcode());
                    }
                    actualizarlistapromocioncabecera(lead);
                    if (!listaPromocionCabecera.isEmpty()) {
                        if(BuildConfig.FLAVOR.equals("peru")||BuildConfig.FLAVOR.equals("bolivia"))
                        {
                            if(SesionEntity.quotation.equals("Y"))
                            {
                                if(ObjUsuario.getU_VIS_ManagementType().equals("B2C"))
                                {
                                    Resources res = getContext().getResources(); // need this to fetch the drawable
                                    Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                                    holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                                    holder.imv_consultar_promocion_cabecera.setEnabled(true);
                                    lead.setOrden_detalle_promocion_habilitada("1");
                                }
                                else {

                                }
                            }else {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                                holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                                holder.imv_consultar_promocion_cabecera.setEnabled(true);
                                lead.setOrden_detalle_promocion_habilitada("1");
                            }
                        }else {
                            Resources res = getContext().getResources(); // need this to fetch the drawable
                            Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_rojo_vistony_24);
                            holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                            holder.imv_consultar_promocion_cabecera.setEnabled(true);
                            lead.setOrden_detalle_promocion_habilitada("1");
                        }
                    } else {
                        Resources res = getContext().getResources(); // need this to fetch the drawable
                        Drawable draw = res.getDrawable(R.drawable.ic_baseline_card_giftcard_24);
                        holder.imv_consultar_promocion_cabecera.setImageDrawable(draw);
                        holder.imv_consultar_promocion_cabecera.setEnabled(false);
                        lead.setOrden_detalle_promocion_habilitada("0");
                    }

                    procesado = true;

                    //OrdenVentaDetalleView.listaPromocionCabecera=listaPromocionCabecera;
                    ArrayList<ListaOrdenVentaDetalleEntity> listaOrdenVentaDetalleEntity = new ArrayList<>();
                    listaOrdenVentaDetalleEntity.add(lead);
                    fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.content_menu_view, ordenVentaDetalleView.newInstanceActualizaLista(listaOrdenVentaDetalleEntity));
                    ActualizaListaOrdenDetallePromocion(lead);
                    //result_flag=false;
                }
                InputMethodManager imm =
                        (InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //v.clearFocus();
                dialog.dismiss();
            }
            return procesado;
        });

        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return  dialog;
    }

}

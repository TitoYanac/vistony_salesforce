package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasHistorialDespachosDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasLineasNoFacturadasDao;
import com.vistony.salesforce.Dao.Adapters.ListaPendingCollectionDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasHistorialDespachosWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasLineasNoFacturadasWS;
import com.vistony.salesforce.Dao.SQLite.CobranzaDetalleSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasHistorialDespachosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasLineasNoFacturadasEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricSalesOrderTraceabilityAdapter  extends ArrayAdapter<HistoricSalesOrderTraceabilityEntity>  {
    private android.content.Context Context;
    private List<HistoricSalesOrderTraceabilityEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<HistoricSalesOrderTraceabilityEntity> arrayList;
    ListaHistoricoFacturasLineasNoFacturadasAdapter listaHistoricoFacturasLineasNoFacturadasAdapter;
    private ProgressDialog pd;
    ListaHistoricoFacturasHistorialDespachoAdapter listaHistoricoFacturasHistorialDespachoAdapter;
    String date;
    public ListHistoricSalesOrderTraceabilityAdapter(
            android.content.Context context,
            List<HistoricSalesOrderTraceabilityEntity> objects,
            String Date) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<HistoricSalesOrderTraceabilityEntity>();
        this.arrayList.addAll(objects);
        this.date=Date;
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
            for(HistoricSalesOrderTraceabilityEntity wp: arrayList)
            {
                if(wp.getNombrecliente().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getCliente_id().toLowerCase(Locale.getDefault()).contains(charText))
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
    public HistoricSalesOrderTraceabilityEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListHistoricSalesOrderTraceabilityAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_list_historic_sales_order_traceability,
                    parent,
                    false);

            holder = new ListHistoricSalesOrderTraceabilityAdapter.ViewHolder();
            holder.tv_salesorderid = (TextView) convertView.findViewById(R.id.tv_salesorderid);
            holder.tv_rucdni = (TextView) convertView.findViewById(R.id.tv_rucdni);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_amount = (TextView) convertView.findViewById(R.id.tv_amount);
            holder.imv_historic_invoices = (ImageView) convertView.findViewById(R.id.imv_historic_invoices);
            holder.imv_historic_delivery = (ImageView) convertView.findViewById(R.id.imv_historic_delivery);
            holder.imv_historic_order_aprob = (ImageView) convertView.findViewById(R.id.imv_historic_order_aprob);
            holder.imv_historic_orders = (ImageView) convertView.findViewById(R.id.imv_historic_orders);
            holder.imv_historic_pend_rev = (ImageView) convertView.findViewById(R.id.imv_historic_pend_rev);
            holder.tv_cond_venta = (TextView) convertView.findViewById(R.id.tv_cond_venta);
            holder.tv_status_aprob = (TextView) convertView.findViewById(R.id.tv_status_aprob);
            holder.your_state_progress_bar_id  = (StateProgressBar) convertView.findViewById(R.id.your_state_progress_bar_id);
            holder.tv_date_order_aprob = (TextView) convertView.findViewById(R.id.tv_date_order_aprob);
            holder.tv_date_orders = (TextView) convertView.findViewById(R.id.tv_date_orders);
            holder.tv_date_pend_rev = (TextView) convertView.findViewById(R.id.tv_date_pend_rev);
            holder.tv_date_invoice = (TextView) convertView.findViewById(R.id.tv_date_invoice);
            holder.tv_date_delivery = (TextView) convertView.findViewById(R.id.tv_date_delivery);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricSalesOrderTraceabilityAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricSalesOrderTraceabilityEntity lead = getItem(position);

        // Setup.
        holder.tv_salesorderid.setText(lead.getDocnum());
        holder.tv_rucdni.setText(lead.getRucdni());
        holder.tv_name.setText(lead.getNombrecliente());
        holder.tv_amount.setText(Convert.currencyForView(lead.getMontototalorden()));
        holder.tv_cond_venta.setText(lead.getPymntgroup());
        holder.tv_status_aprob.setText(lead.getComentarioaprobacion());

        String statusDispatch="";
        if(lead.getInvoices()!=null)
        {
            for(int i=0;i<lead.getInvoices().size();i++){
                statusDispatch=lead.getInvoices().get(i).getEstadodespacho();
            }
        }

        holder.tv_date_order_aprob.setVisibility(View.GONE);
        holder.tv_date_orders.setVisibility(View.GONE);
        holder.tv_date_pend_rev.setVisibility(View.GONE);
        holder.tv_date_invoice.setVisibility(View.GONE);
        holder.tv_date_delivery.setVisibility(View.GONE);

        if(BuildConfig.FLAVOR.equals("chile"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable eraser,orders,invoices;
            holder.your_state_progress_bar_id.setMaxStateNumber(StateProgressBar.StateNumber.THREE);
            String[] descriptionData = {"Borrador", "Orden\nVenta", "Facturacion"};
            holder.your_state_progress_bar_id.setStateDescriptionData(descriptionData);
            holder.imv_historic_delivery.setVisibility(View.GONE);
            holder.imv_historic_pend_rev.setVisibility(View.GONE);
            Convert.setMarginsView(holder.imv_historic_orders,120, 400, 200, 145);
            Convert.setMarginsView(holder.imv_historic_order_aprob,450, 400, 200, 145);
            Convert.setMarginsView(holder.imv_historic_invoices,780, 400, 0, 145);

            eraser = res.getDrawable( R.drawable.ic_baseline_plagiarism_24);
            holder.imv_historic_orders.setImageDrawable(eraser);
            orders = res.getDrawable( R.drawable.ic_baseline_receipt_24);
            holder.imv_historic_order_aprob.setImageDrawable(orders);

            /*if (holder.imv_historic_orders.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.imv_historic_orders.getLayoutParams();
                p.setMargins(30, 145, 145, 145);
                holder.imv_historic_orders.requestLayout();

            }*/
            Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.statusDispatch:"+statusDispatch);
            if(lead.getObject().equals("Borrador"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta sin Aprobacion");
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
            }
            else if(lead.getInvoices()==null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()!=null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(true);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                //holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho");
                holder.tv_status_aprob.setText("Facturado");
            }
        }
        else if(BuildConfig.FLAVOR.equals("paraguay"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable eraser,orders,invoices;
            holder.your_state_progress_bar_id.setMaxStateNumber(StateProgressBar.StateNumber.FOUR);
            String[] descriptionData = {"Borrador", "Orden\nVenta", "Facturacion","Despacho"};
            holder.your_state_progress_bar_id.setStateDescriptionData(descriptionData);
            //holder.imv_historic_delivery.setVisibility(View.GONE);

            Convert.setMarginsView(holder.imv_historic_orders,80, 400, 200, 145);
            Convert.setMarginsView(holder.imv_historic_order_aprob,330, 400, 200, 145);
            Convert.setMarginsView(holder.imv_historic_invoices,580, 400, 0, 145);

            eraser = res.getDrawable( R.drawable.ic_baseline_plagiarism_24);
            holder.imv_historic_orders.setImageDrawable(eraser);
            orders = res.getDrawable( R.drawable.ic_baseline_receipt_24);
            holder.imv_historic_order_aprob.setImageDrawable(orders);

            /*if (holder.imv_historic_orders.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.imv_historic_orders.getLayoutParams();
                p.setMargins(30, 145, 145, 145);
                holder.imv_historic_orders.requestLayout();

            }*/
            Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.statusDispatch:"+statusDispatch);
            if(lead.getObject().equals("Borrador"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta sin Aprobacion");
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
            }
            else if(lead.getInvoices()==null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()!=null&&!statusDispatch.equals("Entregado"))
            {
                holder.tv_status_aprob.setText("Facturado");
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho");
            }
            else if(lead.getInvoices()!=null&&statusDispatch.equals("Entregado"))
            {
                holder.tv_status_aprob.setText("Facturado");
                //holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                holder.your_state_progress_bar_id.setAllStatesCompleted(true);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                //holder.imv_historic_delivery.setColorFilter(0957C3);
                //holder.imv_historic_delivery.setBackgroundColor(Color.RED);
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho y Entregado");
            }
        }
        else if(BuildConfig.FLAVOR.equals("peru")) {
            String[] descriptionData = {"Orden\nVenta","Pend.\nRevision", "Aprobacion\nOrden", "Factura", "Entrega\nMercaderia"};
            holder.your_state_progress_bar_id.setStateDescriptionData(descriptionData);
            Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.statusDispatch:"+statusDispatch);
            if(lead.getInvoices()==null&&lead.getComentarioaprobacion().equals("Cancelado"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                /*holder.your_state_progress_bar_id.setStateNumberForegroundColor(
                        ContextCompat.getColor(getContext(),R.color.Rojo_Vistony)
                );*/
                holder.your_state_progress_bar_id.setCurrentStateDescriptionColor
                        (
                        ContextCompat.getColor(getContext(),R.color.Rojo_Vistony)
                );
                holder.your_state_progress_bar_id.setForegroundColor
                        (
                                ContextCompat.getColor(getContext(),R.color.Rojo_Vistony)
                        );

                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.Rojo_Vistony));
                holder.imv_historic_pend_rev.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()==null&&lead.getComentarioaprobacion().equals("Pendiente Revisión"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                holder.your_state_progress_bar_id.setCurrentStateDescriptionColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.your_state_progress_bar_id.setForegroundColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_pend_rev.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()==null&&!lead.getComentarioaprobacion().equals("Aprobado"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta sin Aprobacion");
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                holder.your_state_progress_bar_id.setCurrentStateDescriptionColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.your_state_progress_bar_id.setForegroundColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_pend_rev.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
            }
            else if(lead.getInvoices()==null&&lead.getComentarioaprobacion().equals("Aprobado"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                holder.your_state_progress_bar_id.setCurrentStateDescriptionColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.your_state_progress_bar_id.setForegroundColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_pend_rev.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()!=null&&lead.getComentarioaprobacion().equals("Aprobado")&&!statusDispatch.equals("Entregado"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                holder.your_state_progress_bar_id.setCurrentStateDescriptionColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.your_state_progress_bar_id.setForegroundColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_pend_rev.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho");

            }
            else if(lead.getInvoices()!=null&&lead.getComentarioaprobacion().equals("Aprobado")&&statusDispatch.equals("Entregado"))
            {
                //holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                holder.your_state_progress_bar_id.setAllStatesCompleted(true);
                holder.your_state_progress_bar_id.setCurrentStateDescriptionColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.your_state_progress_bar_id.setForegroundColor
                        (
                                ContextCompat.getColor(getContext(),R.color.colorPrimary)
                        );
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_pend_rev.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));


                holder.tv_date_orders.setText(Induvis.getDateShort(date));
                for(int i=0;i<lead.getInvoices().size();i++)
                {

                    holder.tv_date_delivery.setText(Induvis.getDateShort(lead.getInvoices().get(i).getFechadespacho()));
                    holder.tv_date_invoice.setText(Induvis.getDateShort(lead.getInvoices().get(i).getFechaEmision()));
                }


                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho y Entregado");
            }
        }
        else if(BuildConfig.FLAVOR.equals("ecuador"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable eraser,orders,invoices;
            holder.your_state_progress_bar_id.setMaxStateNumber(StateProgressBar.StateNumber.FOUR);
            String[] descriptionData = {"Borrador", "Orden\nVenta", "Facturacion","Entrega"};
            holder.your_state_progress_bar_id.setStateDescriptionData(descriptionData);
            holder.imv_historic_pend_rev.setVisibility(View.GONE);
            holder.tv_date_orders.setVisibility(View.GONE);
            holder.tv_date_pend_rev.setVisibility(View.GONE);
            holder.tv_date_invoice.setVisibility(View.GONE);
            holder.tv_date_delivery.setVisibility(View.GONE);
            holder.tv_date_order_aprob.setVisibility(View.GONE);

            Convert.setMarginsView(holder.imv_historic_orders,80, 450, 200, 145);
            Convert.setMarginsView(holder.imv_historic_order_aprob,325, 450, 200, 145);
            Convert.setMarginsView(holder.imv_historic_invoices,580, 450, 0, 145);
            Convert.setMarginsView(holder.imv_historic_delivery,830, 450, 0, 145);

            eraser = res.getDrawable( R.drawable.ic_baseline_plagiarism_24);
            holder.imv_historic_orders.setImageDrawable(eraser);
            orders = res.getDrawable( R.drawable.ic_baseline_receipt_24);
            holder.imv_historic_order_aprob.setImageDrawable(orders);

            /*if (holder.imv_historic_orders.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.imv_historic_orders.getLayoutParams();
                p.setMargins(30, 145, 145, 145);
                holder.imv_historic_orders.requestLayout();

            }*/
            Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.statusDispatch:"+statusDispatch);
            if(lead.getObject().equals("Borrador"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta sin Aprobacion");
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
            }
            else if(lead.getInvoices()==null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()!=null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(true);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho");
                holder.tv_status_aprob.setText("Facturado");
            }
        }
        else if(BuildConfig.FLAVOR.equals("bolivia"))
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable eraser,orders,invoices;
            holder.your_state_progress_bar_id.setMaxStateNumber(StateProgressBar.StateNumber.THREE);
            String[] descriptionData = {"Borrador", "Orden\nVenta", "Facturacion"};
            holder.your_state_progress_bar_id.setStateDescriptionData(descriptionData);
            holder.imv_historic_delivery.setVisibility(View.GONE);
            holder.imv_historic_pend_rev.setVisibility(View.GONE);
            Convert.setMarginsView(holder.imv_historic_orders,120, 400, 200, 145);
            Convert.setMarginsView(holder.imv_historic_order_aprob,450, 400, 200, 145);
            Convert.setMarginsView(holder.imv_historic_invoices,780, 400, 0, 145);

            eraser = res.getDrawable( R.drawable.ic_baseline_plagiarism_24);
            holder.imv_historic_orders.setImageDrawable(eraser);
            orders = res.getDrawable( R.drawable.ic_baseline_receipt_24);
            holder.imv_historic_order_aprob.setImageDrawable(orders);

            /*if (holder.imv_historic_orders.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) holder.imv_historic_orders.getLayoutParams();
                p.setMargins(30, 145, 145, 145);
                holder.imv_historic_orders.requestLayout();

            }*/
            Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.statusDispatch:"+statusDispatch);
            if(lead.getObject().equals("Borrador"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta sin Aprobacion");
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
            }
            else if(lead.getInvoices()==null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(false);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Aprobacion");
            }
            else if(lead.getInvoices()!=null&&lead.getObject().equals("Orden de Venta"))
            {
                holder.your_state_progress_bar_id.setAllStatesCompleted(true);
                holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                holder.imv_historic_orders.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_order_aprob.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.imv_historic_invoices.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                //holder.imv_historic_delivery.setColorFilter(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.gray));
                //holder.your_state_progress_bar_id.setStateDescriptionColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
                Log.e("REOS","ListHistoricSalesOrderTraceabilityAdapter.estado:"+lead.getNombrecliente()+"-"+"Orden Venta con Facturado y Proceso Despacho");
                holder.tv_status_aprob.setText("Facturado");
            }
        }


        //String[] descriptionData = {"Orden\nVenta", "Aprobacion\nOrden", "Facturacion", "Entrega\nMercaderia"};
        //holder.your_state_progress_bar_id.setStateDescriptionData(descriptionData);



        holder.imv_historic_invoices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* alertamostrarfactura(
                        "Factura",
                        lead.getDocumento_id(),
                        lead.getNrofactura(),
                        lead.getFechaemisionfactura(),
                        lead.getMontoimportefactura(),
                        lead.getMontosaldofactura(),
                        lead.getTipo_factura()
                ).show();*/
                if(lead.getInvoices()!=null)
                {
                    getalertInvoices(lead.getInvoices(),"FACTURAS").show();
                }
                else {
                    Toast.makeText(getContext(), "No se encontraron Facturas", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.imv_historic_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lead.getInvoices()!=null)
                {
                getalertDelivery(lead.getInvoices(),"ENTREGAS").show();
                }
                else {
                    Toast.makeText(getContext(), "No se encontraron Entregas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.your_state_progress_bar_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (holder.your_state_progress_bar_id.getCurrentStateNumber()) {
                    case 1:
                        //holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        break;
                    case 2:
                        //holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        break;
                    case 3:
                        //holder.your_state_progress_bar_id.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        getalertInvoices(lead.getInvoices(),"FACTURAS").show();
                        break;
                    case 4:
                        //holder.your_state_progress_bar_id.setAllStatesCompleted(true);
                        getalertDelivery(lead.getInvoices(),"ENTREGAS").show();
                        break;
                }
            }
        });

        return convertView;
    }

    /*private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }*/

    private Dialog getalertDelivery(List<InvoicesEntity> invoicesEntityList,String Type) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_list_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Entregas Vinculadas a la Orden de Venta");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);
        TextView txtdocumento = dialog.findViewById(R.id.txtdocumento);
        txtdocumento.setText(Type);
        ListHistoricSalesOrderTraceabilityDeliveryAdapter listHistoricSalesOrderTraceabilityDeliveryAdapter=new ListHistoricSalesOrderTraceabilityDeliveryAdapter(getContext(), invoicesEntityList);

        lv_pending_collection.setAdapter(listHistoricSalesOrderTraceabilityDeliveryAdapter);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog


        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return  dialog;
    }

    private Dialog getalertInvoices(List<InvoicesEntity> invoicesEntityList,String Type) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_list_informative);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText("ADVERTENCIA");
        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText("Facturas Vinculadas a la Orden de Venta");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        TextView txtdocumento = dialog.findViewById(R.id.txtdocumento);
        txtdocumento.setText(Type);
        ListView lv_pending_collection = (ListView) dialog.findViewById(R.id.lv_pending_collection);

        ListHistoricSalesOrderTraceabilityInvoiceAdapter listHistoricSalesOrderTraceabilityInvoiceAdapter=new ListHistoricSalesOrderTraceabilityInvoiceAdapter(getContext(), invoicesEntityList);

        lv_pending_collection.setAdapter(listHistoricSalesOrderTraceabilityInvoiceAdapter);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog


        dialogButton.setOnClickListener(new View.OnClickListener() {
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
        TextView tv_salesorderid;
        TextView tv_rucdni;
        TextView tv_name;
        TextView tv_cond_venta;
        TextView tv_amount;
        TextView tv_status_aprob;
        StateProgressBar your_state_progress_bar_id;
        ImageView imv_historic_invoices;
        ImageView imv_historic_delivery;
        ImageView imv_historic_order_aprob;
        ImageView imv_historic_orders;
        ImageView imv_historic_pend_rev;
        TextView tv_date_order_aprob;
        TextView tv_date_orders;
        TextView tv_date_pend_rev;
        TextView tv_date_invoice;
        TextView tv_date_delivery;

    }

}

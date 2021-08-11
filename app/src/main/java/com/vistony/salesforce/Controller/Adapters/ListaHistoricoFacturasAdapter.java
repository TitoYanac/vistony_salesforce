package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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

import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasHistorialDespachosDao;
import com.vistony.salesforce.Dao.Adapters.ListaHistoricoFacturasLineasNoFacturadasDao;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasHistorialDespachosWS;
import com.vistony.salesforce.Dao.Retrofit.HistoricoFacturasLineasNoFacturadasWS;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasHistorialDespachosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasLineasNoFacturadasEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaHistoricoFacturasAdapter extends ArrayAdapter<ListaHistoricoFacturasEntity> {

    public static ArrayList<ListaHistoricoFacturasEntity> listaHistoricoFacturasEntity;
    private FragmentManager fragmentManager;
    private android.content.Context Context;
    private List<ListaHistoricoFacturasEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaHistoricoFacturasEntity> arrayList;
    ListaHistoricoFacturasLineasNoFacturadasAdapter listaHistoricoFacturasLineasNoFacturadasAdapter;
    Activity activity;
    private ProgressDialog pd;
    ListaHistoricoFacturasHistorialDespachoAdapter listaHistoricoFacturasHistorialDespachoAdapter;
    public ListaHistoricoFacturasAdapter(android.content.Context context, List<ListaHistoricoFacturasEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaHistoricoFacturasEntity>();
        this.arrayList.addAll(objects);

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
            for(ListaHistoricoFacturasEntity wp: arrayList)
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
    public ListaHistoricoFacturasEntity getItem(int position) {
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

        final ListaHistoricoFacturasAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_historico_facturas,
                    parent,
                    false);

            holder = new ListaHistoricoFacturasAdapter.ViewHolder();
            holder.tv_historico_facturas_orden_venta_ERP_id = (TextView) convertView.findViewById(R.id.tv_historico_facturas_orden_venta_ERP_id);
            holder.tv_historico_facturas_rucdnni = (TextView) convertView.findViewById(R.id.tv_historico_facturas_rucdnni);
            holder.tv_historico_facturas_nombrecliente = (TextView) convertView.findViewById(R.id.tv_historico_facturas_nombrecliente);
            holder.tv_historico_facturas_monto = (TextView) convertView.findViewById(R.id.tv_historico_facturas_monto);
            holder.imv_historico_orden_venta_facturacion = (ImageView) convertView.findViewById(R.id.imv_historico_orden_venta_facturacion);
            holder.imv_historico_facturas_despacho = (ImageView) convertView.findViewById(R.id.imv_historico_facturas_despacho);
            holder.tv_cond_venta = (TextView) convertView.findViewById(R.id.tv_cond_venta);
            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricoFacturasAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHistoricoFacturasEntity lead = getItem(position);

        // Setup.
        holder.tv_historico_facturas_orden_venta_ERP_id.setText(lead.getOrdenventa_erp_id());
        holder.tv_historico_facturas_rucdnni.setText(lead.getRucdni());
        holder.tv_historico_facturas_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_historico_facturas_monto.setText(lead.getMontoimporteordenventa());
        holder.tv_cond_venta.setText(lead.getTerminopago());

        holder.imv_historico_orden_venta_facturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertamostrarfactura(
                        "Factura",
                        lead.getDocumento_id(),
                        lead.getNrofactura(),
                        lead.getFechaemisionfactura(),
                        lead.getMontoimportefactura(),
                        lead.getMontosaldofactura(),
                        lead.getTipo_factura()
                ).show();
            }
        });
        holder.imv_historico_facturas_despacho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertamostrardespacho(
                        "Despacho",
                        lead.getNrofactura(),
                        lead.getNombrechofer(),
                        lead.getFechaprogramaciondespacho(),
                        lead.getEstadodespacho(),
                        lead.getMotivoestadodespacho()
                ).show();
            }
        });

        return convertView;
    }

    private Dialog alertamostrarfactura(String titulo,String documento_id,String nrofactura,String fechaemision,String importe,String saldo,String tipo_factura) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_dialog_historico_facturas_factura);

        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        TextView tv_historico_facturas_facturacion_documento_id = dialog.findViewById(R.id.tv_historico_facturas_facturacion_documento_id);
        TextView tv_historico_facturas_facturacion_nrofactura = dialog.findViewById(R.id.tv_historico_facturas_facturacion_nrofactura);
        TextView tv_historico_facturas_facturacion_fechaemision = dialog.findViewById(R.id.tv_historico_facturas_facturacion_fechaemision);
        TextView tv_historico_facturas_facturacion_montoimporte = dialog.findViewById(R.id.tv_historico_facturas_facturacion_montoimporte);
        TextView tv_historico_facturas_facturacion_saldo = dialog.findViewById(R.id.tv_historico_facturas_facturacion_saldo);
        TextView tv_historico_facturas_facturacion_lineas_no_facturadas = dialog.findViewById(R.id.tv_historico_facturas_facturacion_lineas_no_facturadas);
        ImageView imv_historico_orden_venta_facturacion = dialog.findViewById(R.id.imv_historico_orden_venta_facturacion);


        textTitle.setText(titulo);
        tv_historico_facturas_facturacion_documento_id.setText(documento_id);
        tv_historico_facturas_facturacion_nrofactura.setText(nrofactura);
        tv_historico_facturas_facturacion_fechaemision.setText(fechaemision);
        tv_historico_facturas_facturacion_montoimporte.setText(importe);
        tv_historico_facturas_facturacion_saldo.setText(saldo);
        tv_historico_facturas_facturacion_lineas_no_facturadas.setText(tipo_factura);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


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
        imv_historico_orden_venta_facturacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dialog.dismiss();
                alertamostrarfacturanofacturadas().show();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    private Dialog alertamostrardespacho(String titulo,String nrofactura,String nombrechofer,String fechaprogramaciondespacho,String estadodespacho,String motivoestado) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_dialog_historico_facturas_despacho);

        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        TextView tv_historico_facturas_despacho_nrofactura = dialog.findViewById(R.id.tv_historico_facturas_despacho_nrofactura);
        TextView tv_historico_facturas_despacho_nombrechofer = dialog.findViewById(R.id.tv_historico_facturas_despacho_nombrechofer);
        TextView tv_historico_facturas_despacho_fechaprogramaciondespacho = dialog.findViewById(R.id.tv_historico_facturas_despacho_fechaprogramaciondespacho);
        TextView tv_historico_facturas_despacho_estadodespacho = dialog.findViewById(R.id.tv_historico_facturas_despacho_estadodespacho);
        TextView tv_historico_facturas_despacho_motivoestado = dialog.findViewById(R.id.tv_historico_facturas_despacho_motivoestado);
        ImageView imv_historico_facturacion_despacho_historial = dialog.findViewById(R.id.imv_historico_facturacion_despacho_historial);


        textTitle.setText(titulo);
        tv_historico_facturas_despacho_nrofactura.setText(nrofactura);
        tv_historico_facturas_despacho_nombrechofer.setText(nombrechofer);
        tv_historico_facturas_despacho_fechaprogramaciondespacho.setText(fechaprogramaciondespacho);
        tv_historico_facturas_despacho_estadodespacho.setText(estadodespacho);
        tv_historico_facturas_despacho_motivoestado.setText(motivoestado);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


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

        imv_historico_facturacion_despacho_historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertamostrarhistorialdespachos().show();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }

    static class ViewHolder {
        TextView tv_historico_facturas_orden_venta_ERP_id;
        TextView tv_historico_facturas_rucdnni;
        TextView tv_historico_facturas_nombrecliente;
        TextView tv_historico_facturas_monto;
        ImageView imv_historico_orden_venta_facturacion;
        ImageView imv_historico_facturas_despacho;
        TextView tv_cond_venta;
    }

    private Dialog alertamostrarfacturanofacturadas() {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_historico_facturas_factura_no_facturadas);

        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        ListView lv_historico_factura_lineas_no_facturadas = dialog.findViewById(R.id.lv_historico_factura_lineas_no_facturadas);
        String titulo="LINEAS NO FACTURADAS";
        textTitle.setText(titulo);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> listaHistoricoFacturasLNF=new ArrayList<>();
        //HistoricoFacturasLineasNoFacturadasWS historicoFacturasLineasNoFacturadasWS=new HistoricoFacturasLineasNoFacturadasWS(getContext());
        listaHistoricoFacturasLNF=ObtenerHistoricoFacturasLineasNoFacturadas(
                getContext(),
                SesionEntity.imei,
                SesionEntity.compania_id,
                SesionEntity.fuerzatrabajo_id,
                SesionEntity.usuario_id,
                SesionEntity.almacen_id
        );
        listaHistoricoFacturasLineasNoFacturadasAdapter = new ListaHistoricoFacturasLineasNoFacturadasAdapter(getContext(),
                ListaHistoricoFacturasLineasNoFacturadasDao.getInstance().getLeads(listaHistoricoFacturasLNF));
        lv_historico_factura_lineas_no_facturadas.setAdapter(listaHistoricoFacturasLineasNoFacturadasAdapter);

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



    public ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> ObtenerHistoricoFacturasLineasNoFacturadas (
            Context context,
            String Imei,
            String Compania_ID,
            String FuerzaTrabajo_ID,
            String Cliente_ID,
            String NroDocumento

    )
    {
        try {
            return new HiloObtenerHistoricoFacturasLineasNoFacturadas(
                    context
                    ,Imei
                    ,Compania_ID
                    ,FuerzaTrabajo_ID
                    ,Cliente_ID
                    ,NroDocumento
            ).execute().get();
            //return new getProfilesAsyncTask(userDao).execute().get();
        } catch (Exception e) {
            Log.e("JPCM",""+e);
            e.printStackTrace();
            return null;
        }
    }

    private class HiloObtenerHistoricoFacturasLineasNoFacturadas extends AsyncTask<String, Void, ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> > {
        Context context;
        String Imei,Compania_ID,FuerzaTrabajo_ID,Cliente_ID,NroDocumento;

        private HiloObtenerHistoricoFacturasLineasNoFacturadas(
                Context context,
                String Imei,
                String Compania_ID,
                String FuerzaTrabajo_ID,
                String Cliente_ID,
                String NroDocumento
        )
        {
            this.context=context;
            this.Imei=Imei;
            this.Compania_ID=Compania_ID;
            this.FuerzaTrabajo_ID=FuerzaTrabajo_ID;
            this.Cliente_ID=Cliente_ID;
            this.NroDocumento=NroDocumento;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Context);
            pd = ProgressDialog.show(Context, "Por favor espere", "Consultando Lineas No Facturadas", true, false);
        }

        @Override
        public final ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity>  doInBackground(String... arg0) {

            ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> listaHistoricoFacturasLineasNoFacturadasEntity=new ArrayList<>();
            HistoricoFacturasLineasNoFacturadasWS historicoFacturasLineasNoFacturadasWS=new HistoricoFacturasLineasNoFacturadasWS(getContext());
            try {
                listaHistoricoFacturasLineasNoFacturadasEntity=historicoFacturasLineasNoFacturadasWS.getHistoricoFacturasLineasNoFacturas(
                         Imei,
                         Compania_ID,
                         FuerzaTrabajo_ID,
                         Cliente_ID,
                         NroDocumento
                );
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            pd.dismiss();
            return listaHistoricoFacturasLineasNoFacturadasEntity;
        }
    }

    private Dialog alertamostrarhistorialdespachos() {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.lista_layout_historico_facturas_despacho_historial);

        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        ListView lv_historico_despacho_historial = dialog.findViewById(R.id.lv_historico_despacho_historial);
        String titulo="HISTORIAL DESPACHOS";
        textTitle.setText(titulo);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);

        ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> listaHistoricoFacturasHD=new ArrayList<>();
        //HistoricoFacturasLineasNoFacturadasWS historicoFacturasLineasNoFacturadasWS=new HistoricoFacturasLineasNoFacturadasWS(getContext());
        listaHistoricoFacturasHD=ObtenerHistoricoFacturasHistorialDespachos(
                getContext(),
                SesionEntity.imei,
                SesionEntity.compania_id,
                SesionEntity.fuerzatrabajo_id,
                SesionEntity.usuario_id,
                SesionEntity.almacen_id
        );
        listaHistoricoFacturasHistorialDespachoAdapter = new ListaHistoricoFacturasHistorialDespachoAdapter(getContext(),
                ListaHistoricoFacturasHistorialDespachosDao.getInstance().getLeads(listaHistoricoFacturasHD));
        lv_historico_despacho_historial.setAdapter(listaHistoricoFacturasHistorialDespachoAdapter);

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



    public ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> ObtenerHistoricoFacturasHistorialDespachos (
            Context context,
            String Imei,
            String Compania_ID,
            String FuerzaTrabajo_ID,
            String Cliente_ID,
            String NroDocumento

    )
    {
        try {
            return new HiloObtenerHistoricoFacturasHistorialDespachos(
                    context
                    ,Imei
                    ,Compania_ID
                    ,FuerzaTrabajo_ID
                    ,Cliente_ID
                    ,NroDocumento
            ).execute().get();
            //return new getProfilesAsyncTask(userDao).execute().get();
        } catch (Exception e) {
            Log.e("JPCM",""+e);
            e.printStackTrace();
            return null;
        }
    }

    private class HiloObtenerHistoricoFacturasHistorialDespachos extends AsyncTask<String, Void, ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> > {
        Context context;
        String Imei,Compania_ID,FuerzaTrabajo_ID,Cliente_ID,NroDocumento;

        private HiloObtenerHistoricoFacturasHistorialDespachos(
                Context context,
                String Imei,
                String Compania_ID,
                String FuerzaTrabajo_ID,
                String Cliente_ID,
                String NroDocumento
        )
        {
            this.context=context;
            this.Imei=Imei;
            this.Compania_ID=Compania_ID;
            this.FuerzaTrabajo_ID=FuerzaTrabajo_ID;
            this.Cliente_ID=Cliente_ID;
            this.NroDocumento=NroDocumento;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(Context);
            pd = ProgressDialog.show(Context, "Por favor espere", "Consultando Lineas No Facturadas", true, false);
        }

        @Override
        public final ArrayList<ListaHistoricoFacturasHistorialDespachosEntity>  doInBackground(String... arg0) {

            ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> listaHistoricoFacturasHistorialDespachosEntity=new ArrayList<>();
            HistoricoFacturasHistorialDespachosWS historicoFacturasHistorialDespachosWS=new HistoricoFacturasHistorialDespachosWS(getContext());
            try {
                listaHistoricoFacturasHistorialDespachosEntity=historicoFacturasHistorialDespachosWS.getHistoricoFacturasHistorialDespachos(
                        Imei,
                        Compania_ID,
                        FuerzaTrabajo_ID,
                        Cliente_ID,
                        NroDocumento
                );
            } catch (Exception e)
            {
                // TODO: handle exception
                System.out.println(e.getMessage());
            }
            pd.dismiss();
            return listaHistoricoFacturasHistorialDespachosEntity;
        }
    }
}

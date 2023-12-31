package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.zxing.integration.android.IntentIntegrator;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.FormulasController;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.HistoricoOrdenVentaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaHistoricoOrdenVentaAdapter extends ArrayAdapter<ListaHistoricoOrdenVentaEntity> {

    public static ArrayList<ListaHistoricoOrdenVentaEntity> listaHistoricoOrdenVentaEntity;
    private FragmentManager fragmentManager;
    private android.content.Context Context;
    private List<ListaHistoricoOrdenVentaEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaHistoricoOrdenVentaEntity> arrayList;
    FormulasController formulasController;
    HistoricoOrdenVentaView historicoOrdenVentaView;
    Activity activity;
    private static final int REQUEST_CODE_QR_SCAN = 101;

    public ListaHistoricoOrdenVentaAdapter(Context context, List<ListaHistoricoOrdenVentaEntity> objects, Activity activity) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaHistoricoOrdenVentaEntity>();
        this.arrayList.addAll(objects);
        formulasController=new FormulasController(context);
        this.activity=activity;

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
            for(ListaHistoricoOrdenVentaEntity wp: arrayList)
            {
                if(wp.getCardName().toLowerCase(Locale.getDefault()).contains(charText)){
                    Listanombres.add(wp);
                }else if(wp.getCardName().toLowerCase(Locale.getDefault()).contains(charText)){
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
    public ListaHistoricoOrdenVentaEntity getItem(int position) {
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

        final ListaHistoricoOrdenVentaAdapter.ViewHolder holder;
        historicoOrdenVentaView=new HistoricoOrdenVentaView();
        // ¿Ya se infló este view?
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.layout_lista_historico_orden_venta,parent,false);

            holder = new ListaHistoricoOrdenVentaAdapter.ViewHolder();
            holder.tv_orden_venta_ERP_id = (TextView) convertView.findViewById(R.id.tv_orden_venta_ERP_id);
            holder.tv_rucdnni = (TextView) convertView.findViewById(R.id.tv_rucdnni);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_nombrecliente);
            holder.tv_monto_historico_orden_venta = (TextView) convertView.findViewById(R.id.tv_monto_historico_orden_venta);
            holder.tv_estado_historico_orden_venta = (TextView) convertView.findViewById(R.id.tv_estado_historico_orden_venta);
            holder.imv_historico_orden_venta_cometario_aprob = (ImageView) convertView.findViewById(R.id.imv_historico_orden_venta_cometario_aprob);
            holder.imv_historico_orden_venta_cometario_ws = (ImageView) convertView.findViewById(R.id.imv_historico_orden_venta_cometario_ws);
            holder.imv_flecha_historico_orden_venta = (ImageView) convertView.findViewById(R.id.imv_flecha_historico_orden_venta);
            holder.chk_envio_orden_venta_ERP = (CheckBox) convertView.findViewById(R.id.chk_envio_orden_venta_ERP);
            holder.chk_recibido_orden_venta_ERP = (CheckBox) convertView.findViewById(R.id.chk_recibido_orden_venta_ERP);
            holder.imv_salesorder_qr = (ImageView) convertView.findViewById(R.id.imv_salesorder_qr);
            holder.lbl_salesorder_qr = (TextView) convertView.findViewById(R.id.lbl_salesorder_qr);

            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricoOrdenVentaAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHistoricoOrdenVentaEntity lead = getItem(position);

        // Setup.
        holder.tv_orden_venta_ERP_id.setText((lead.getDocNum()==null||lead.getDocNum().equals(""))?lead.getSalesOrderID():lead.getDocNum());
        holder.tv_rucdnni.setText(lead.getLicTradNum());
        holder.tv_nombrecliente.setText(lead.getCardName());
        holder.tv_estado_historico_orden_venta.setText(lead.getApprovalStatus());
        holder.tv_monto_historico_orden_venta.setText(Convert.currencyForView(lead.getDocTotal()));
        holder.imv_salesorder_qr.setVisibility(View.GONE);
        holder.lbl_salesorder_qr.setVisibility(View.GONE);
        if(lead.isRecepcionERPOV())
        {
            holder.chk_recibido_orden_venta_ERP.setChecked(true);
        }
        else
        {
            holder.chk_recibido_orden_venta_ERP.setChecked(false);
        }
        if(lead.isEnvioERPOV())
        {
            holder.chk_envio_orden_venta_ERP.setChecked(true);
        }
        else
        {
            holder.chk_envio_orden_venta_ERP.setChecked(false);
        }

        if(lead.getApprovalCommentary()==null||lead.getApprovalCommentary().isEmpty()){
            holder.imv_historico_orden_venta_cometario_aprob.setEnabled(false);
        }else{
            holder.imv_historico_orden_venta_cometario_aprob.setEnabled(true);
        }

        holder.imv_historico_orden_venta_cometario_aprob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertamostrarcomentario("Comentario Aprobacion",lead.getApprovalCommentary()).show();
            }
        });

        if(lead.getApprovalCommentary()==null||lead.getApprovalCommentary().isEmpty())
        {
            holder.imv_historico_orden_venta_cometario_aprob.setEnabled(false);
            //holder.imv_historico_orden_venta_cometario_aprob.setVisibility(View.INVISIBLE);
        }
        else
        {
            holder.imv_historico_orden_venta_cometario_aprob.setEnabled(true);
            //holder.imv_historico_orden_venta_cometario_aprob.setVisibility(View.VISIBLE);

        }

        holder.imv_historico_orden_venta_cometario_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertamostrarcomentario("Comentario Ws",lead.getComentariows()).show();
            }
        });

        /*if(lead.getDocNum()!=null){
            holder.chk_recibido_orden_venta_ERP.setChecked(true);
            holder.chk_envio_orden_venta_ERP.setChecked(true);
        }else{
            holder.chk_recibido_orden_venta_ERP.setChecked(false);
            holder.chk_envio_orden_venta_ERP.setChecked(false);
        }*/


        holder.imv_flecha_historico_orden_venta.setOnClickListener(v -> {
            //Toast.makeText(getContext(), "ListaHistoricoOrdenVentaAdapter-getView-Lead.getObject():" + lead.getObject(), Toast.LENGTH_SHORT).show();
            if(formulasController.ValidarOrdenVentaIDSQLite(getContext() ,lead.getSalesOrderID())){
                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                ArrayList<ListaHistoricoOrdenVentaEntity> Lista = new ArrayList<>();
                Lista.add(lead);

                transaction.add(R.id.content_menu_view, historicoOrdenVentaView.newInstanceEnviarOrdenVentaID(Lista));
            }else{
                alertamostrarcomentario("Advertencia","La Orden de Venta, No existe en la Base de Datos Local").show();
            }
        });

        holder.imv_salesorder_qr.setOnClickListener(v -> {
            try {
                Log.e("REOS","statusDispatchRepository-->FotoGuia-->Inicia");
                //Intent i = new Intent(activity, QrCodeActivity.class);
               // MenuView menuView=new MenuView();
                //historicoOrdenVentaView.validateQR(activity);
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(true);
                integrator.setRequestCode(REQUEST_CODE_QR_SCAN);
                integrator.initiateScan();
            }catch (Exception e)
            {
                Toast.makeText(getContext(), "ListaHistoricoOrdenVentaAdapter-Error:" + e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;


    }


    private Dialog alertamostrarcomentario(String Titulo, String Comentario) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(Titulo);

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText((Comentario==null)?"Sin Comentario":Comentario);

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

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


    static class ViewHolder {
        TextView tv_orden_venta_ERP_id;
        TextView tv_rucdnni;
        TextView tv_nombrecliente;
        TextView tv_monto_historico_orden_venta;
        TextView tv_estado_historico_orden_venta;
        ImageView imv_historico_orden_venta_cometario_aprob;
        ImageView imv_historico_orden_venta_cometario_ws;
        ImageView imv_flecha_historico_orden_venta;
        CheckBox chk_envio_orden_venta_ERP;
        CheckBox chk_recibido_orden_venta_ERP;
        ImageView imv_salesorder_qr;
        TextView lbl_salesorder_qr;

    }

    private Dialog alertdialogSalesOrderQR(Context context, String data,String salesorder_id) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog_salesorder_qr);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView tv_salesorder_id=(TextView) dialog.findViewById(R.id.tv_salesorder_id);
        TextView tv_cliente=(TextView) dialog.findViewById(R.id.tv_cliente);
        TextView tv_seller=(TextView) dialog.findViewById(R.id.tv_seller);
        TextView tv_date=(TextView) dialog.findViewById(R.id.tv_date);
        TextView tv_amount_salesorder=(TextView) dialog.findViewById(R.id.tv_amount_salesorder);
        TextView text=(TextView) dialog.findViewById(R.id.text);
        text.setText("ORDEN DE VENTA");
        String salesorderid="",orderdate="",client="",amount="",seller="";

        try{
            String[] salesorder_data= data.split("&&&");
            salesorderid=salesorder_data[0];
            orderdate=salesorder_data[1];
            client=salesorder_data[2];
            amount=salesorder_data[3];
            seller=salesorder_data[4];
        }catch (Exception e)
        {
            Toast.makeText(getContext(), "alertdialogSalesOrderQR-Error:" + e.toString(), Toast.LENGTH_SHORT).show();
        }

        tv_salesorder_id.setText(salesorderid);
        tv_cliente.setText(client);
        tv_seller.setText(seller);
        tv_date.setText(orderdate);
        tv_amount_salesorder.setText(amount);


        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }


}

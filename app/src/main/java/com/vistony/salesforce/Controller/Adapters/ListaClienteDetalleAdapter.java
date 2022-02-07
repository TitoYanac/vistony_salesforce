package com.vistony.salesforce.Controller.Adapters;
import android.content.Context;
import android.os.Bundle;

/*
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
*/

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.vistony.salesforce.BuildConfig;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.R;

import com.vistony.salesforce.View.ClienteDetalleView;
import com.vistony.salesforce.View.CobranzaDetalleView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListaClienteDetalleAdapter extends ArrayAdapter<ListaClienteDetalleEntity> {
     public static ArrayList <ListaClienteDetalleEntity> ArraylistaClienteDetalleEntity;
    ListaClienteDetalleEntity listaClienteDetalleEntity;
    CobranzaDetalleView cobranzaDetalleView;
    ClienteDetalleView clienteDetalleView;
    FragmentManager fragmentManager;
    private Context context;
    SimpleDateFormat dateFormat;
    String fechaemision,fechavencimiento;
    java.util.Date date;
    Date datefechaemision,datefechavencimiento;
    public ListaClienteDetalleAdapter(Context context, List<ListaClienteDetalleEntity> objects) {

        super(context, 0, objects);
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArraylistaClienteDetalleEntity= new ArrayList <ListaClienteDetalleEntity>();

        //Toast.makeText(context, "la position xdd "+position, Toast.LENGTH_SHORT).show();
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_cliente_detalle,
                    parent,
                    false);

            holder = new ViewHolder();
            holder.tv_documento = convertView.findViewById(R.id.tv_documento);
            holder.tv_fecha_emision = convertView.findViewById(R.id.tv_fecha_emision);
            holder.tv_fecha_vencimiento = convertView.findViewById(R.id.tv_fecha_vencimiento);
            holder.tv_importe =  convertView.findViewById(R.id.tv_importe);
            holder.tv_saldo =  convertView.findViewById(R.id.tv_saldo);
            holder.imv_clientedetalle =  convertView.findViewById(R.id.imv_cliente_detalle);
            holder.tv_moneda_cliente_detalle =convertView.findViewById(R.id.tv_moneda_cliente_detalle);
            holder.xd =convertView.findViewById(R.id.layoutXD);

            convertView.setTag(holder);


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ListaClienteDetalleEntity lead = getItem(position);
        /*
        String[] sourceSplitemision= lead.getFechaemision().split(" ");
        String  Fechaemision=sourceSplitemision[0];
        String Horaemision=sourceSplitemision[1];
        String[] sourceSplitemision2= Fechaemision.split("/");
        String anioemision= sourceSplitemision2[0];
        String mesemision= sourceSplitemision2[1];
        String diaemision= sourceSplitemision2[2];*/

        String fechaemision=lead.getFechaemision();
        /*
        String[] sourceSplit= lead.getFechavencimiento().split(" ");
        String  Fecha=sourceSplit[0];
        String Hora=sourceSplit[1];
        String[] sourceSplit2= Fecha.split("/");
        String anio= sourceSplit2[0];
        String mes= sourceSplit2[1];
        String dia= sourceSplit2[2];*/

        String fechavencimiento=lead.getFechavencimiento();//anio+"-"+mes+"-"+dia;
        Log.e("REOS","ListaClienteDetalleAdapter-fechaemision: "+fechaemision);
        holder.tv_documento.setText(lead.getNrodocumento());
        holder.tv_fecha_emision.setText(
                //lead.getFechaemision()
                fechaemision
                //Induvis.getDate(BuildConfig.FLAVOR,fechaemision)
        );
        Log.e("REOS","ListaClienteDetalleAdapter-fechavencimiento: "+fechavencimiento);
        holder.tv_fecha_vencimiento.setText(
                //lead.getFechavencimiento()
                //Induvis.getDate(BuildConfig.FLAVOR,fechavencimiento)
                fechavencimiento
        );
        holder.tv_importe.setText(Convert.currencyForView(lead.getImporte()));
        holder.tv_saldo.setText(Convert.currencyForView(lead.getSaldo()));
        holder.tv_moneda_cliente_detalle.setText(lead.getMoneda());

        holder.xd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cliente_id,nombrecliente,domembarque,direccion,documento_id,nrodocumento,fechaemision,fechavencimiento,importe,saldo,cobrado,nuevo_saldo;
                cliente_id=lead.getCliente_id().toString();
                nombrecliente=lead.nombrecliente.toString();
                domembarque=lead.domembarque.toString();

                Log.e("REOS","ListaClienteDetalleAdapter: "+lead.domembarque);
                direccion=lead.direccion.toString();
                documento_id=lead.documento_id.toString();
                nrodocumento=lead.getNrodocumento().toString();
                fechaemision=lead.getFechaemision().toString();
                fechavencimiento=lead.getFechavencimiento().toString();
                importe=lead.getImporte().toString();
                saldo=lead.getSaldo().toString();

                listaClienteDetalleEntity =  new ListaClienteDetalleEntity();

                listaClienteDetalleEntity.cliente_id=cliente_id;
                listaClienteDetalleEntity.nombrecliente=nombrecliente;
                listaClienteDetalleEntity.domembarque=domembarque;
                listaClienteDetalleEntity.direccion=direccion;
                listaClienteDetalleEntity.documento_id=documento_id;
                listaClienteDetalleEntity.nrodocumento=nrodocumento;
                listaClienteDetalleEntity.fechaemision=(fechaemision);
                listaClienteDetalleEntity.fechavencimiento=(fechavencimiento);
                listaClienteDetalleEntity.importe=(importe);
                listaClienteDetalleEntity.saldo=(saldo);
                listaClienteDetalleEntity.cobrado="0";
                listaClienteDetalleEntity.nuevo_saldo="0";
                ArraylistaClienteDetalleEntity.add(listaClienteDetalleEntity);
                ArraylistaClienteDetalleEntity.size();

                String Cliente="";
                Cliente="CL01";


                Bundle bundle = new Bundle();
                bundle.putString("valor",Cliente);

                fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_menu_view, clienteDetalleView.newInstancia(ArraylistaClienteDetalleEntity));
               // transaction.addToBackStack("param1");
               // transaction.commit();
            }
        });

        return convertView;
    }


    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_documento;
        //TextView lbl_fecha_emision;
        TextView tv_fecha_emision;
       // TextView lbl_fecha_vencimiento;
        TextView tv_fecha_vencimiento;
       // TextView lbl_importe;
        TextView tv_importe;
       // TextView lbl_saldo;
        TextView tv_saldo;
        TextView tv_moneda_cliente_detalle;
        ImageView imv_clientedetalle;
        RelativeLayout xd;
    }


}

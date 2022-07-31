package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.os.Bundle;

/*
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
*/

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.RutaVendedorView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaClienteCabeceraAdapter extends ArrayAdapter<ListaClienteCabeceraEntity>   {

    public static ArrayList<ListaClienteCabeceraEntity> ArraylistaClienteCabeceraEntity;
    public ArrayList<ListaClienteCabeceraEntity> employeeArrayList;
    public ArrayList<ListaClienteCabeceraEntity> orig;
    ListaClienteCabeceraEntity listaClienteCabeceraEntity;

    public ClienteCabeceraView clienteCabeceraView;
    public RutaVendedorView rutaVendedorView;

    //ClienteCabeceraView.OnFragmentInteractionListener
    ClienteCabeceraView fragment;
    Fragment fragmento;
    private FragmentManager fragmentManager;
    private Context Context;
    private List<ListaClienteCabeceraEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaClienteCabeceraEntity> arrayList;


    public ListaClienteCabeceraAdapter(Context context, List<ListaClienteCabeceraEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaClienteCabeceraEntity>();
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
                for(ListaClienteCabeceraEntity wp: arrayList)
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
    public ListaClienteCabeceraEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArraylistaClienteCabeceraEntity= new ArrayList <ListaClienteCabeceraEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaClienteCabeceraAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_cliente_cabecera,
                    parent,
                    false);

            holder = new ListaClienteCabeceraAdapter.ViewHolder();

           
            holder.tv_clienteid = (TextView) convertView.findViewById(R.id.tv_cliente_id);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_nombrecliente);
            holder.tv_saldo_cliente_cabecera = (TextView) convertView.findViewById(R.id.tv_saldo_cliente_cabecera);

            holder.tv_linea_credito_usado=convertView.findViewById(R.id.tv_linea_credito_usado);
            holder.tv_linea_credito = (TextView) convertView.findViewById(R.id.tv_linea_credito);

            holder.imv_clientecabecera = (ImageView) convertView.findViewById(R.id.imv_cliente_cabecera);
            holder.tv_moneda = (TextView) convertView.findViewById(R.id.tv_moneda);
            holder.tv_categoria = (TextView) convertView.findViewById(R.id.tv_categoria);
            holder.chk_pedido = (CheckBox) convertView.findViewById(R.id.chk_pedido);
            holder.chk_cobranza = (CheckBox) convertView.findViewById(R.id.chk_cobranza);
            holder.chk_visita = (CheckBox) convertView.findViewById(R.id.chk_visita);
            holder.relativeListaCabezeraCns=convertView.findViewById(R.id.relativeListaCabezera);
            holder.progressLineCredit=convertView.findViewById(R.id.progressBar);
            holder.tv_lastpurchase = (TextView) convertView.findViewById(R.id.tv_lastpurchase);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
            holder.chk_geolocation = (CheckBox) convertView.findViewById(R.id.chk_geolocation);
            holder.chk_visitsection = (CheckBox) convertView.findViewById(R.id.chk_visitsection);
            holder.tablerowbalance = (TableRow) convertView.findViewById(R.id.tablerowbalance);
            holder.linearlayoutlblcredit = (LinearLayout) convertView.findViewById(R.id.linearlayoutlblcredit);
            holder.linearlayouttvcredit = (LinearLayout) convertView.findViewById(R.id.linearlayouttvcredit);
            holder.tv_paymentterms = (TextView) convertView.findViewById(R.id.tv_paymentterms);
            holder.tablerowpaymentterms = (TableRow) convertView.findViewById(R.id.tablerowpaymentterms);
            holder.ll_geolocation = (LinearLayout) convertView.findViewById(R.id.ll_geolocation);


            convertView.setTag(holder);
        } else {
            holder = (ListaClienteCabeceraAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        holder.chk_pedido.setChecked(false);
        holder.chk_visita.setChecked(false);
        holder.chk_cobranza.setChecked(false);
        final ListaClienteCabeceraEntity lead = getItem(position);

        // Setup.

        Log.e("Percona=>","====================");
        Log.e("Percona=>",""+lead.getCliente_id());
        Log.e("Percona=>","====================");

        holder.tv_clienteid.setText(lead.getCliente_id());
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_saldo_cliente_cabecera.setText(Convert.currencyForView(lead.getSaldo()));
        holder.tv_address.setText(lead.getDomembarque_id()+"-"+lead.getDireccion());
        holder.tv_paymentterms.setText(lead.getTerminopago());
        //holder.chk_geolocation.setVisibility(View.GONE);
        holder.ll_geolocation.setVisibility(View.GONE);
        switch(lead.getMoneda()){
            case "S/":
                holder.tv_moneda.setText("Soles");
                break;
            case "##":
                holder.tv_moneda.setText("Multimoneda");
                break;
            case "$":
                holder.tv_moneda.setText("Dolares");
                break;
            default:
                holder.tv_moneda.setText(lead.getMoneda());
                break;
        }
        SesionEntity.currency=lead.getMoneda();
        Log.e("REOS","ListaClienteCabeceraAdapter-getView-SesionEntity.currency: "+SesionEntity.currency);
        if(BuildConfig.FLAVOR.equals("ecuador"))
        {
            //holder.tablerowbalance.setVisibility(View.GONE);
            //holder.progressLineCredit.setVisibility(View.GONE);
            //holder.linearlayoutlblcredit.setVisibility(View.GONE);
            //holder.linearlayouttvcredit.setVisibility(View.GONE);
        }
        else if(BuildConfig.FLAVOR.equals("paraguay"))
        {
            holder.tablerowpaymentterms.setVisibility(View.GONE);
        }


        holder.tv_categoria.setText(lead.getCategoria());
        Log.e("REOS","ListaClienteCabeceraAdapter.getView.Lista.get(i).getLastpurchase())"+lead.getLastpurchase());

        holder.tv_lastpurchase.setText(Induvis.getDate(BuildConfig.FLAVOR,lead.getLastpurchase()));

        //Mostrar linea de credito en moneda de acuerdo a la region
        ///////////////////////////////////////////////////////////////////////////////////////
        holder.tv_linea_credito.setText(Convert.currencyForView(lead.getLinea_credito()));
        holder.tv_linea_credito_usado.setText(Convert.currencyForView(lead.getLinea_credito_usado()));
        ///////////////////////////////////////////////////////////////////////////////////////

        if(lead.getLinea_credito().isEmpty() || lead.getLinea_credito()==null){
            holder.progressLineCredit.setMax(0);
            holder.progressLineCredit.setProgress(0);
        }else{
            holder.progressLineCredit.setMax(Math.round(Float.parseFloat(""+lead.getLinea_credito())));

            if(lead.getLinea_credito_usado().isEmpty() || lead.getLinea_credito_usado()==null){
                holder.progressLineCredit.setProgress(0);
            }else{
                holder.progressLineCredit.setProgress(Math.round(Float.parseFloat(""+lead.getLinea_credito_usado())));
            }
        }


        if(!(lead.getChk_visita()==null||lead.getChk_pedido()==null||lead.getChk_pedido()==null))
        {
            if (lead.getChk_pedido().equals("1")) {
                holder.chk_pedido.setChecked(true);
                holder.chk_visita.setChecked(true);
            }
            if (lead.getChk_cobranza().equals("1")) {
                holder.chk_cobranza.setChecked(true);
                holder.chk_visita.setChecked(true);
            }
            if (lead.getChk_visita().equals("1")) {
                holder.chk_visita.setChecked(true);
            }
        }

        if(lead.getChkgeolocation()!=null) {
            if (lead.getChkgeolocation().equals("1")) {
                holder.chk_geolocation.setChecked(true);
            } else {
                holder.chk_geolocation.setChecked(false);
            }
        }
        else {
            holder.chk_geolocation.setChecked(false);
        }

        if(lead.getChkvisitsection()!=null) {
            if (lead.getChkvisitsection().equals("1")) {
                holder.chk_visitsection.setChecked(true);
            } else {
                holder.chk_visitsection.setChecked(false);
            }
        }
        else {
            holder.chk_visitsection.setChecked(false);
        }

        if(!BuildConfig.FLAVOR.equals("peru"))
       {
            holder.chk_geolocation.setVisibility(View.GONE);
            holder.chk_visitsection.setVisibility(View.GONE);
        }

        holder.relativeListaCabezeraCns.setOnClickListener(v -> {


               clienteCabeceraView = new ClienteCabeceraView();
               rutaVendedorView = new RutaVendedorView();
               listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();

               listaClienteCabeceraEntity.setCliente_id( lead.getCliente_id());
               listaClienteCabeceraEntity.setNombrecliente ( lead.getNombrecliente());
               listaClienteCabeceraEntity.setDireccion ( lead.getDireccion());
               listaClienteCabeceraEntity.setSaldo (lead.getSaldo());
               listaClienteCabeceraEntity.setImvclientecabecera ( lead.getImvclientecabecera());
               listaClienteCabeceraEntity.setMoneda(lead.getMoneda());
               listaClienteCabeceraEntity.setDomembarque_id(lead.getDomembarque_id());
               listaClienteCabeceraEntity.setDomfactura_id(lead.getDomfactura_id());

                    Log.e("JEPICAMEE","=> embarque "+lead.getDomembarque_id());
                    Log.e("JEPICAMEE","=> factura "+lead.getDomfactura_id());

               listaClienteCabeceraEntity.setImpuesto_id(lead.getImpuesto_id());
               listaClienteCabeceraEntity.setImpuesto_id(lead.getImpuesto_id());
               listaClienteCabeceraEntity.setRucdni(lead.getRucdni());
               listaClienteCabeceraEntity.setCategoria(lead.getCategoria());
               listaClienteCabeceraEntity.setLinea_credito(lead.getLinea_credito());
               listaClienteCabeceraEntity.setLinea_credito_usado(lead.getLinea_credito_usado());
                    Log.e("REOS","ListaClienteCabeceraAdapter.lead.getTerminopago_id() "+lead.getTerminopago_id());
               listaClienteCabeceraEntity.setTerminopago_id(lead.getTerminopago_id());
               listaClienteCabeceraEntity.setZona_id(lead.getZona_id());
               listaClienteCabeceraEntity.setCompania_id(lead.getCompania_id());
               listaClienteCabeceraEntity.setOrdenvisita(lead.getOrdenvisita());
               listaClienteCabeceraEntity.setZona(lead.getZona());
               listaClienteCabeceraEntity.setTelefonofijo(lead.getTelefonofijo());
               listaClienteCabeceraEntity.setTelefonomovil(lead.getTelefonomovil());
               listaClienteCabeceraEntity.setCorreo(lead.getCorreo());
               listaClienteCabeceraEntity.setTelefonomovil(lead.getTelefonomovil());
               listaClienteCabeceraEntity.setUbigeo_id(lead.getUbigeo_id());
               listaClienteCabeceraEntity.setTipocambio(lead.getTipocambio());
               listaClienteCabeceraEntity.setChk_visita(lead.getChk_visita());
               listaClienteCabeceraEntity.setChk_pedido(lead.getChk_pedido());
               listaClienteCabeceraEntity.setChk_cobranza(lead.getChk_cobranza());
               listaClienteCabeceraEntity.setChk_ruta(lead.getChk_ruta());
                    Log.e("REOS","ListaClienteCabeceraAdapter.lead.getChk_ruta() "+lead.getChk_ruta());
               listaClienteCabeceraEntity.setChk_cobranza(lead.getFecharuta());
               listaClienteCabeceraEntity.setDocentry(lead.getDocentry());
               listaClienteCabeceraEntity.setLastpurchase(lead.getLastpurchase());
                    listaClienteCabeceraEntity.setTerminopago(lead.getTerminopago());
                    listaClienteCabeceraEntity.setContado( lead.getContado());
               //ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                    ArraylistaClienteCabeceraEntity.add(lead);
               String Cliente = "";
               Cliente = "CL01";
               fragment = new ClienteCabeceraView();
               Bundle bundle = new Bundle();
               bundle.putString("valor", Cliente);

               fragment.setArguments(bundle);
               fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
               FragmentTransaction transaction = fragmentManager.beginTransaction();
               transaction.add(R.id.content_menu_view, rutaVendedorView.newInstanciaMenu(ArraylistaClienteCabeceraEntity));                                                 //String Texto1=Texto;

        }

        );

        Log.e("REOS","Finaliza ListaClienteCabecera");
        //Glide.with(getContext()).load(lead.getImage()).into(holder.avatar);

        return convertView;
    }


    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_clienteid;
        //TextView lbl_fecha_emision;
        TextView tv_nombrecliente;
        // TextView lbl_fecha_vencimiento;
        TextView tv_saldo_cliente_cabecera;
        TextView tv_moneda;
        TextView tv_categoria;
        TextView tv_linea_credito;
         TextView tv_linea_credito_usado;
        ImageView imv_clientecabecera;
        CheckBox chk_pedido;
        CheckBox chk_cobranza;
        CheckBox chk_visita;
        RelativeLayout relativeListaCabezeraCns;
        ProgressBar progressLineCredit;
        TextView tv_lastpurchase;
        TextView tv_address;
        CheckBox chk_geolocation;
        CheckBox chk_visitsection;
        TableRow tablerowbalance;
        LinearLayout linearlayoutlblcredit;
        LinearLayout linearlayouttvcredit;
        TextView tv_paymentterms;
        TableRow tablerowpaymentterms;
        LinearLayout ll_geolocation;
    }



}


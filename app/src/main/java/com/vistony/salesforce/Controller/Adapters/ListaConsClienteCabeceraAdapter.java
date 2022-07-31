package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.BuscarClienteView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaConsClienteCabeceraAdapter extends ArrayAdapter<ListaConsClienteCabeceraEntity>  {

    public static ArrayList<ListaClienteCabeceraEntity> ArraylistaClienteCabeceraEntity;
    ClienteCabeceraView fragment;
    private FragmentManager fragmentManager;
    private android.content.Context Context;
    private List<ListaConsClienteCabeceraEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaConsClienteCabeceraEntity> arrayList;
    ListaClienteCabeceraEntity listaClienteCabeceraEntity;

    public ListaConsClienteCabeceraAdapter(Context context, List<ListaConsClienteCabeceraEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaConsClienteCabeceraEntity>();
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
            for(ListaConsClienteCabeceraEntity wp: arrayList)
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
    public ListaConsClienteCabeceraEntity getItem(int position) {
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

        final ListaConsClienteCabeceraAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(R.layout.layout_lista_cliente_cabecera,parent,false);

            holder = new ListaConsClienteCabeceraAdapter.ViewHolder();
            holder.tv_clienteid = (TextView) convertView.findViewById(R.id.tv_cliente_id);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_nombrecliente);
            holder.tv_saldo_cliente_cabecera = (TextView) convertView.findViewById(R.id.tv_saldo_cliente_cabecera);
            holder.imv_clientecabecera = (ImageView) convertView.findViewById(R.id.imv_cliente_cabecera);
            holder.tv_moneda = (TextView) convertView.findViewById(R.id.tv_moneda);

            holder.tv_linea_credito = (TextView) convertView.findViewById(R.id.tv_linea_credito);
            holder.tv_linea_credito_usado= (TextView) convertView.findViewById(R.id.tv_linea_credito_usado);

            holder.tv_categoria = (TextView) convertView.findViewById(R.id.tv_categoria);
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
            holder = (ListaConsClienteCabeceraAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaConsClienteCabeceraEntity lead = getItem(position);

        // Setup.
        holder.tv_clienteid.setText(lead.getCliente_id());
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_saldo_cliente_cabecera.setText(Convert.currencyForView(lead.getSaldo()));
        holder.tv_address.setText(lead.getDomembarque_id()+"-"+lead.getDireccion());
        holder.tv_moneda.setText(lead.getMoneda());
        holder.tv_categoria.setText(lead.getCategoria());
        holder.tv_lastpurchase.setText(Induvis.getDate(BuildConfig.FLAVOR,lead.getLastpurchase()));
        //Mostrar linea de credito en moneda de acuerdo a la region
        ///////////////////////////////////////////////////////////////////////////////////////
        holder.tv_linea_credito.setText(Convert.currencyForView(lead.getLinea_credito()));
        holder.tv_linea_credito_usado.setText(Convert.currencyForView(lead.getLinea_credito_usado()));
        ///////////////////////////////////////////////////////////////////////////////////////
        holder.tv_paymentterms.setText(lead.getTerminopago());
        holder.ll_geolocation.setVisibility(View.GONE);
        if(!BuildConfig.FLAVOR.equals("peru"))
        {
            holder.chk_geolocation.setVisibility(View.GONE);
            holder.chk_visitsection.setVisibility(View.GONE);
        }
        if(BuildConfig.FLAVOR.equals("ecuador"))
        {
            //holder.tablerowbalance.setVisibility(View.GONE);
            //holder.progressLineCredit.setVisibility(View.GONE);
            //holder.linearlayoutlblcredit.setVisibility(View.GONE);
            //holder.linearlayouttvcredit.setVisibility(View.GONE);
        }
        else if(BuildConfig.FLAVOR.equals("paraguay")){
            holder.tablerowpaymentterms.setVisibility(View.GONE);

        }

        holder.progressLineCredit.setMax(Math.round(Float.parseFloat(""+lead.getLinea_credito())));

        holder.progressLineCredit.setProgress(Math.round(Float.parseFloat(""+lead.getLinea_credito_usado())));

        holder.relativeListaCabezeraCns.setOnClickListener(v -> {
            BuscarClienteView buscarClienteView = new BuscarClienteView();
            listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
            listaClienteCabeceraEntity.setCliente_id ( lead.getCliente_id());
            listaClienteCabeceraEntity.setNombrecliente ( lead.getNombrecliente());
            listaClienteCabeceraEntity.setDireccion ( lead.getDireccion());
            listaClienteCabeceraEntity.setMoneda ( lead.getMoneda());
            listaClienteCabeceraEntity.setSaldo ( lead.getSaldo());
            listaClienteCabeceraEntity.setDomembarque_id(lead.getDomembarque_id());
            listaClienteCabeceraEntity.setImpuesto_id(lead.getImpuesto_id());
            listaClienteCabeceraEntity.setImpuesto(lead.getImpuesto());
            listaClienteCabeceraEntity.setRucdni(lead.getRucdni());
            listaClienteCabeceraEntity.setCategoria(lead.getCategoria());
            listaClienteCabeceraEntity.setLinea_credito(lead.getLinea_credito());
            listaClienteCabeceraEntity.setLinea_credito_usado(lead.getLinea_credito_usado());
            listaClienteCabeceraEntity.setTerminopago_id(lead.getTerminopago_id());
            listaClienteCabeceraEntity.setZona_id(lead.getZona_id());
            listaClienteCabeceraEntity.setCompania_id( SesionEntity.compania_id);
            listaClienteCabeceraEntity.setOrdenvisita(lead.getOrdenvisita());
            listaClienteCabeceraEntity.setZona(lead.getZona());
            listaClienteCabeceraEntity.setTelefonofijo(lead.getTelefonofijo());
            listaClienteCabeceraEntity.setTelefonomovil(lead.getTelefonomovil());
            listaClienteCabeceraEntity.setCorreo(lead.getCorreo());
            listaClienteCabeceraEntity.setUbigeo_id(lead.getUbigeo_id());
            listaClienteCabeceraEntity.setTipocambio(lead.getTipocambio());
            listaClienteCabeceraEntity.setLastpurchase (lead.getLastpurchase());
            listaClienteCabeceraEntity.setLineofbussiness (lead.getLineofbussiness());
            listaClienteCabeceraEntity.setTerminopago (lead.getTerminopago());
            listaClienteCabeceraEntity.setContado (lead.getContado());
                    Log.e("REOS","ListaConsClienteCabeceraAdapter.getView.lead.getLineofbussiness(): "+lead.getLineofbussiness());
            ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);

            fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            ////////////////OCULTAR TECLADO///////////
            InputMethodManager imm =(InputMethodManager) getContext().getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            ////////////////OCULTAR TECLADO///////////
            if(BuscarClienteView.Flujo.equals("historicContainerSaleView"))
            {
                transaction.add(R.id.content_menu_view, buscarClienteView.newInstanciaEnviarClienteHistoricContainerSale(ArraylistaClienteCabeceraEntity));
                Log.e("REOS","ListaConsClienteCabeceraAdapter.BuscarClienteView.Flujo:"+BuscarClienteView.Flujo+"EntroFlujo");
            }else if(BuscarClienteView.Flujo.equals("quotaspercustomer"))
                {
                    Log.e("REOS","ListaConsClienteCabeceraAdapter.BuscarClienteView.Flujo:"+BuscarClienteView.Flujo+"NoEntroFlujo");
                    transaction.add(R.id.content_menu_view, buscarClienteView.newInstanciaEnviarClienteQuotasPerCustomer(ArraylistaClienteCabeceraEntity));
                }
            else if(BuscarClienteView.Flujo.equals("historicContainerSaleViewSKU"))
            {
                Log.e("REOS","ListaConsClienteCabeceraAdapter.BuscarClienteView.Flujo:"+BuscarClienteView.Flujo+"NoEntroFlujo");
                transaction.add(R.id.content_menu_view, buscarClienteView.newInstanciaEnviarClienteHistoricContainerSKU(ArraylistaClienteCabeceraEntity));
            }else if(BuscarClienteView.Flujo.equals("dialogoagregarclienteMenu"))
            {
                Log.e("REOS","ListaConsClienteCabeceraAdapter.BuscarClienteView.Flujo:"+BuscarClienteView.Flujo+"NoEntroFlujo");
                transaction.add(R.id.content_menu_view, buscarClienteView.newInstanciaEnviarClienteQuotasPerCustomerDialog(ArraylistaClienteCabeceraEntity));
            }else if(BuscarClienteView.Flujo.equals("kardexofpayment"))
            {
                Log.e("REOS","ListaConsClienteCabeceraAdapter.BuscarClienteView.Flujo-kardex:"+BuscarClienteView.Flujo+"NoEntroFlujo");
                transaction.add(R.id.content_menu_view, buscarClienteView.newInstanciaEnviarClienteKardexOfPayment(ArraylistaClienteCabeceraEntity));
            }
            else
                    {
                        Log.e("REOS","ListaConsClienteCabeceraAdapter.BuscarClienteView.Flujo:"+BuscarClienteView.Flujo+"NoEntroFlujo");
                        transaction.add(R.id.content_menu_view, buscarClienteView.newInstanciaEnviarCliente(ArraylistaClienteCabeceraEntity));
                    }

        }

        );
        return convertView;
    }
    static class ViewHolder {
        TextView tv_clienteid;
        TextView tv_nombrecliente;
        TextView tv_saldo_cliente_cabecera;
        TextView tv_moneda;
        TextView tv_categoria;
        TextView tv_linea_credito;
        TextView tv_linea_credito_usado;
        ImageView imv_clientecabecera;
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

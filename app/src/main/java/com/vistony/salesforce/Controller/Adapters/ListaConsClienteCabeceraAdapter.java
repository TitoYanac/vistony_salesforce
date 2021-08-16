package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsClienteCabeceraEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.ConsClienteView;

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
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
            convertView.setTag(holder);
        } else {
            holder = (ListaConsClienteCabeceraAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaConsClienteCabeceraEntity lead = getItem(position);

        // Setup.
        holder.tv_clienteid.setText(lead.getCliente_id());
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_saldo_cliente_cabecera.setText(lead.getSaldo());
        holder.tv_moneda.setText(lead.getMoneda());
        holder.tv_categoria.setText(lead.getCategoria());
        holder.tv_linea_credito.setText(lead.getLinea_credito());
        holder.tv_linea_credito_usado.setText(lead.getLinea_credito_usado());

        holder.progressLineCredit.setMax(Math.round(Float.parseFloat(""+lead.getLinea_credito())));

        holder.progressLineCredit.setProgress(Math.round(Float.parseFloat(""+lead.getLinea_credito_usado())));

        holder.relativeListaCabezeraCns.setOnClickListener(new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View v) {
                                                                   ConsClienteView consClienteView= new ConsClienteView();
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




                                                                   ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);

                                                                   fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                                   FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                                   transaction.add(R.id.content_menu_view, consClienteView.newInstanciaEnviarCliente(ArraylistaClienteCabeceraEntity));
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
    }
}

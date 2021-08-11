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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.RutaVendedorView;

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
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaClienteCabeceraAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_cliente_cabecera,
                    parent,
                    false);

            holder = new ListaClienteCabeceraAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_clienteid = (TextView) convertView.findViewById(R.id.tv_cliente_id);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_nombrecliente);
            holder.tv_saldo_cliente_cabecera = (TextView) convertView.findViewById(R.id.tv_saldo_cliente_cabecera);
            holder.imv_clientecabecera = (ImageView) convertView.findViewById(R.id.imv_cliente_cabecera);
            holder.tv_moneda = (TextView) convertView.findViewById(R.id.tv_moneda);
            holder.tv_categoria = (TextView) convertView.findViewById(R.id.tv_categoria);
            holder.tv_linea_credito = (TextView) convertView.findViewById(R.id.tv_linea_credito);
            holder.chk_pedido = (CheckBox) convertView.findViewById(R.id.chk_pedido);
            holder.chk_cobranza = (CheckBox) convertView.findViewById(R.id.chk_cobranza);
            holder.chk_visita = (CheckBox) convertView.findViewById(R.id.chk_visita);
            holder.relativeListaCabezeraCns=convertView.findViewById(R.id.relativeListaCabezera);

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
        holder.tv_clienteid.setText(lead.getCliente_id());
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_saldo_cliente_cabecera.setText(lead.getSaldo());
        holder.tv_moneda.setText(lead.getMoneda());
        holder.tv_categoria.setText(lead.getCategoria());
        holder.tv_linea_credito.setText(lead.getLinea_credito());

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
        holder.relativeListaCabezeraCns.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                        clienteCabeceraView = new ClienteCabeceraView();
                                                        rutaVendedorView = new RutaVendedorView();
                                                        listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
                                                        //String cliente_id, nombrecliente, direccion,moneda,domembarque_id;
                                                        //cliente_id = lead.getCliente_id().toString();
                                                        //nombrecliente = lead.getNombrecliente().toString();
                                                        //direccion = lead.getDireccion().toString();
                                                        //moneda = lead.getMoneda().toString();
                                                        listaClienteCabeceraEntity.cliente_id = lead.getCliente_id();
                                                        listaClienteCabeceraEntity.nombrecliente = lead.getNombrecliente();
                                                        listaClienteCabeceraEntity.direccion = lead.getDireccion();
                                                        listaClienteCabeceraEntity.saldo = lead.getSaldo();
                                                        listaClienteCabeceraEntity.imvclientecabecera = lead.getImvclientecabecera();
                                                        listaClienteCabeceraEntity.moneda = lead.getMoneda();
                                                        listaClienteCabeceraEntity.domembarque_id=lead.getDomembarque_id();
                                                        listaClienteCabeceraEntity.impuesto_id=lead.getImpuesto_id();
                                                        listaClienteCabeceraEntity.impuesto_id=lead.getImpuesto_id();
                                                        listaClienteCabeceraEntity.rucdni=lead.getRucdni();
                                                        listaClienteCabeceraEntity.categoria=lead.getCategoria();
                                                        listaClienteCabeceraEntity.linea_credito=lead.getLinea_credito();
                                                        listaClienteCabeceraEntity.terminopago_id=lead.getTerminopago_id();
                                                        listaClienteCabeceraEntity.zona_id=lead.getZona_id();
                                                        listaClienteCabeceraEntity.compania_id=lead.getCompania_id();
                                                        listaClienteCabeceraEntity.ordenvisita=lead.getOrdenvisita();
                                                        listaClienteCabeceraEntity.zona=lead.getZona();
                                                        listaClienteCabeceraEntity.telefonofijo=lead.getTelefonofijo();
                                                        listaClienteCabeceraEntity.telefonomovil=lead.getTelefonomovil();
                                                        listaClienteCabeceraEntity.correo=lead.getCorreo();
                                                        listaClienteCabeceraEntity.telefonomovil=lead.getTelefonomovil();
                                                        listaClienteCabeceraEntity.ubigeo_id=lead.getUbigeo_id();
                                                        listaClienteCabeceraEntity.tipocambio=lead.getTipocambio();
                                                        listaClienteCabeceraEntity.chk_visita=lead.getChk_visita();
                                                        listaClienteCabeceraEntity.chk_pedido=lead.getChk_pedido();
                                                        listaClienteCabeceraEntity.chk_cobranza=lead.getChk_cobranza();
                                                        listaClienteCabeceraEntity.chk_ruta=lead.getChk_ruta();
                                                        listaClienteCabeceraEntity.chk_cobranza=lead.getFecharuta();

                                                        ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                                                        //ArraylistaClienteCabeceraEntity.size();
                                                        String Cliente = "";
                                                        Cliente = "CL01";
                                                        fragment = new ClienteCabeceraView();
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("valor", Cliente);

                                                        fragment.setArguments(bundle);
                                                        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                        //transaction.replace(android.R.id.content, fragment);
                                                        //transaction.addToBackStack("DiscoverPage");


                                                       //transaction.add(R.id.content_menu_view, clienteCabeceraView.newInstancia(ArraylistaClienteCabeceraEntity));
                                                       transaction.add(R.id.content_menu_view, rutaVendedorView.newInstanciaMenu(ArraylistaClienteCabeceraEntity));

                                                     //transaction.commit();


                                                     //clienteCabeceraView.ObtenerLista(Cliente);

                                                     //mListener.onFragmentInteraction(ArraylistaClienteCabeceraEntity);
                                                     //                                                     //String Texto="VALERIA";
                                                     //                                                     //String Texto1=Texto;

                                                 }
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
        // TextView lbl_importe;
        ImageView imv_clientecabecera;
        CheckBox chk_pedido;
        CheckBox chk_cobranza;
        CheckBox chk_visita;
        RelativeLayout relativeListaCabezeraCns;


    }



}


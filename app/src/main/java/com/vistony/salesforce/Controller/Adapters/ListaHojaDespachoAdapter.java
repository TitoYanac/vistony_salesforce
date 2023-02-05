package com.vistony.salesforce.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHojaDespachoEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.DireccionClienteView;
import com.vistony.salesforce.View.DispatchSheetView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaHojaDespachoAdapter extends ArrayAdapter<ListaHojaDespachoEntity> {

    public static ArrayList<ListaHojaDespachoEntity> araylistaHojaDespachoEntity;
    private android.content.Context Context;
    private List<ListaHojaDespachoEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaHojaDespachoEntity> arrayList;
    private FragmentManager fragmentManager;
    public DireccionClienteView direccionClienteView;
    ListaClienteCabeceraEntity listaClienteCabeceraEntity;
    public ClienteCabeceraView clienteCabeceraView;
    public DispatchSheetView hojaDespachoView;
    public static ArrayList<ListaClienteCabeceraEntity> ArraylistaClienteCabeceraEntity;

    public ListaHojaDespachoAdapter(android.content.Context context, List<ListaHojaDespachoEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaHojaDespachoEntity>();
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
            for(ListaHojaDespachoEntity wp: arrayList)
            {
                if(wp.getNombrecliente().toLowerCase(Locale.getDefault()).contains(charText))
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
    public ListaHojaDespachoEntity getItem(int position) {
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

        final ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_hoja_despacho,
                    parent,
                    false);

            holder = new ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_cliente = (TextView) convertView.findViewById(R.id.tv_cliente);
            holder.tv_direccion = (TextView) convertView.findViewById(R.id.tv_direccion);
            holder.tv_factura = (TextView) convertView.findViewById(R.id.tv_factura);
            holder.tv_saldo = (TextView) convertView.findViewById(R.id.tv_saldo);
            holder.tv_nombrefuerzatrabajo = (TextView) convertView.findViewById(R.id.tv_nombrefuerzatrabajo);
            holder.relativeListaHojaDespacho=convertView.findViewById(R.id.relativeListaHojaDespacho);
            holder.tv_terminopago=convertView.findViewById(R.id.tv_terminopago);
            holder.chk_updatedispatch=convertView.findViewById(R.id.chk_updatedispatch);
            holder.chk_visit_start=convertView.findViewById(R.id.chk_visit_start);
            holder.chk_visit_end=convertView.findViewById(R.id.chk_visit_end);
            holder.tv_orden_detalle_item=convertView.findViewById(R.id.tv_orden_detalle_item);
            holder.tv_delivery=convertView.findViewById(R.id.tv_delivery);
            holder.chk_collection=convertView.findViewById(R.id.chk_collection);
            holder.tv_status_dispatch=convertView.findViewById(R.id.tv_status_dispatch);
            holder.tv_ocurrencies=convertView.findViewById(R.id.tv_ocurrencies);
            holder.tr_entrega=convertView.findViewById(R.id.tr_entrega);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHojaDespachoEntity lead = getItem(position);

        // Setup.

        holder.tv_direccion.setText(lead.getDireccion());
        holder.tv_factura.setText(lead.getNrofactura());
        holder.tv_saldo.setText(lead.getSaldo());
        holder.tv_nombrefuerzatrabajo.setText(lead.getNombrefuerzatrabajo());
        holder.tv_terminopago.setText(lead.getTerminopago());
        holder.chk_updatedispatch.setChecked(lead.isChkupdatedispatch());
        holder.chk_visit_start.setChecked(lead.isChkvisitsectionstart());
        holder.chk_visit_end.setChecked(lead.isChkvisitsectionend());
        holder.chk_collection.setChecked(lead.isChkcollection());
        holder.tv_delivery.setText(lead.getEntrega());
        holder.tv_status_dispatch.setText(lead.getEstado());
        holder.tv_ocurrencies.setText(lead.getOcurrencia());
        ArraylistaClienteCabeceraEntity=new ArrayList<>();

        if(BuildConfig.FLAVOR.equals("bolivia"))
        {
            holder.tr_entrega.setVisibility(View.GONE);
        }
        /*ArrayList<ClienteSQLiteEntity> listaClienteEnvio=new ArrayList<>();
        ClienteSQliteDAO clienteSQliteDAO=new ClienteSQliteDAO(getContext());
        listaClienteEnvio=clienteSQliteDAO.ObtenerDatosCliente(lead.nombrecliente);

        ArraylistaClienteCabeceraEntity=new ArrayList<>();
        for(int i=0;i<listaClienteEnvio.size();i++)
        {
            holder.tv_cliente.setText(listaClienteEnvio.get(i).getNombrecliente());
        }*/
        //.holder.chk_updatedispatch.setVisibility(View.GONE);
        holder.tv_orden_detalle_item.setText(lead.getItem_id()+")");
        holder.tv_cliente.setText(lead.getNombrecliente());
        //holder.tv_cliente.setText(lead.getNombrecliente());
        holder.relativeListaHojaDespacho.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        hojaDespachoView = new DispatchSheetView();
                                                                        listaClienteCabeceraEntity=new ListaClienteCabeceraEntity();
                                                                        ClienteSQlite clienteSQliteDAO=new ClienteSQlite(getContext());
                                                                        ArrayList<ClienteSQLiteEntity> listaClienteEnvio=new ArrayList<>();
                                                                        Log.e("REOS","ListaHojaDespachoAdapter.lead.nombrecliente:"+lead.nombrecliente);
                                                                        Log.e("REOS","ListaHojaDespachoAdapter.SesionEntity.compania_id:"+SesionEntity.compania_id);
                                                                        listaClienteEnvio=clienteSQliteDAO.ObtenerDatosCliente(lead.cliente_id,SesionEntity.compania_id);
                                                                        Log.e("REOS","ListaHojaDespachoAdapter.listaClienteEnvio.size:"+listaClienteEnvio.size());
                                                                        Log.e("REOS","ListaHojaDespachoAdapter.lead.cliente_id():"+lead.getCliente_id());
                                                                        Log.e("REOS","ListaHojaDespachoAdapter.lead.getDomembarque_id():"+lead.getDomembarque_id());
                                                                        if(!listaClienteEnvio.isEmpty()) {
                                                                            for (int i = 0; i < listaClienteEnvio.size(); i++) {
                                                                                listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
                                                                                holder.tv_cliente.setText(listaClienteEnvio.get(i).getNombrecliente());
                                                                                listaClienteCabeceraEntity.setCliente_id(lead.getCliente_id());
                                                                                listaClienteCabeceraEntity.setNombrecliente(listaClienteEnvio.get(i).getNombrecliente());
                                                                                listaClienteCabeceraEntity.setDireccion(lead.getDireccion());
                                                                                listaClienteCabeceraEntity.setSaldo(lead.getSaldo());
                                                                                listaClienteCabeceraEntity.setImvclientecabecera(0);
                                                                                listaClienteCabeceraEntity.setMoneda(listaClienteEnvio.get(i).getMoneda());
                                                                                listaClienteCabeceraEntity.setDomembarque_id(lead.getDomembarque_id());
                                                                                listaClienteCabeceraEntity.setImpuesto_id(listaClienteEnvio.get(i).getImpuesto_id());
                                                                                listaClienteCabeceraEntity.setImpuesto(listaClienteEnvio.get(i).getImpuesto());
                                                                                listaClienteCabeceraEntity.setRucdni(listaClienteEnvio.get(i).getRucdni());
                                                                                listaClienteCabeceraEntity.setCategoria(listaClienteEnvio.get(i).getCategoria());
                                                                                listaClienteCabeceraEntity.setLinea_credito(listaClienteEnvio.get(i).getLinea_credito());
                                                                                listaClienteCabeceraEntity.setTerminopago_id(listaClienteEnvio.get(i).getLinea_credito());
                                                                                listaClienteCabeceraEntity.setZona_id(listaClienteEnvio.get(i).getZona_id());
                                                                                listaClienteCabeceraEntity.setCompania_id(SesionEntity.compania_id);
                                                                                listaClienteCabeceraEntity.setOrdenvisita(listaClienteEnvio.get(i).getOrden());
                                                                                listaClienteCabeceraEntity.setZona(listaClienteEnvio.get(i).getZona());
                                                                                listaClienteCabeceraEntity.setTelefonofijo(listaClienteEnvio.get(i).getTelefonofijo());
                                                                                listaClienteCabeceraEntity.setTelefonomovil(listaClienteEnvio.get(i).getTelefonomovil());
                                                                                listaClienteCabeceraEntity.setCorreo(listaClienteEnvio.get(i).getCorreo());
                                                                                listaClienteCabeceraEntity.setUbigeo_id(listaClienteEnvio.get(i).getUbigeo_id());
                                                                                listaClienteCabeceraEntity.setTipocambio(listaClienteEnvio.get(i).getTipocambio());
                                                                                listaClienteCabeceraEntity.setChk_visita("0");
                                                                                listaClienteCabeceraEntity.setChk_pedido("0");
                                                                                listaClienteCabeceraEntity.setChk_cobranza("0");
                                                                                listaClienteCabeceraEntity.setChk_ruta("0");
                                                                                listaClienteCabeceraEntity.setChk_cobranza("0");
                                                                                listaClienteCabeceraEntity.setControl_id(lead.getControl_id());
                                                                                listaClienteCabeceraEntity.setItem_id(lead.getItem_id());
                                                                                ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                                                                            }
                                                                        }
                                                                        else {
                                                                            listaClienteCabeceraEntity = new ListaClienteCabeceraEntity();
                                                                            listaClienteCabeceraEntity.setCliente_id(lead.getCliente_id());
                                                                            listaClienteCabeceraEntity.setControl_id(lead.getControl_id());
                                                                            listaClienteCabeceraEntity.setItem_id(lead.getItem_id());
                                                                            listaClienteCabeceraEntity.setDomembarque_id(lead.getDomembarque_id());
                                                                            listaClienteCabeceraEntity.setDireccion(lead.getDireccion());
                                                                            listaClienteCabeceraEntity.setSaldo(lead.getSaldo());
                                                                            listaClienteCabeceraEntity.setNombrecliente("Cliente");
                                                                            ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                                                                        }
                                                                        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                                        transaction.add(R.id.content_menu_view, hojaDespachoView.newInstanciaMenu(ArraylistaClienteCabeceraEntity));
                                                                    }
                                                                }

        );
        return convertView;
    }
    static class ViewHolder {
        TextView tv_cliente;
        TextView tv_direccion;
        TextView tv_factura;
        TextView tv_saldo;
        TextView tv_nombrefuerzatrabajo;
        RelativeLayout relativeListaHojaDespacho;
        TextView tv_terminopago;
        CheckBox chk_updatedispatch;
        CheckBox chk_visit_start;
        CheckBox chk_visit_end;
        TextView tv_orden_detalle_item;
        TextView tv_delivery;
        CheckBox chk_collection;
        TextView tv_status_dispatch;
        TextView tv_ocurrencies;
        TableRow tr_entrega;

    }

}

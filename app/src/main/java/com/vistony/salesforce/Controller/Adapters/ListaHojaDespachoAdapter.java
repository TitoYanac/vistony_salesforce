package com.vistony.salesforce.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
        ArraylistaClienteCabeceraEntity=new ArrayList<>();
        /*ArrayList<ClienteSQLiteEntity> listaClienteEnvio=new ArrayList<>();
        ClienteSQliteDAO clienteSQliteDAO=new ClienteSQliteDAO(getContext());
        listaClienteEnvio=clienteSQliteDAO.ObtenerDatosCliente(lead.nombrecliente);

        ArraylistaClienteCabeceraEntity=new ArrayList<>();
        for(int i=0;i<listaClienteEnvio.size();i++)
        {
            holder.tv_cliente.setText(listaClienteEnvio.get(i).getNombrecliente());
        }*/
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
                                                                        for(int i=0;i<listaClienteEnvio.size();i++)
                                                                        {
                                                                            listaClienteCabeceraEntity=new ListaClienteCabeceraEntity();
                                                                            holder.tv_cliente.setText(listaClienteEnvio.get(i).getNombrecliente());
                                                                            listaClienteCabeceraEntity.setCliente_id(listaClienteEnvio.get(i).getCliente_id());
                                                                            listaClienteCabeceraEntity.setNombrecliente(listaClienteEnvio.get(i).getNombrecliente());
                                                                            listaClienteCabeceraEntity.setDireccion(lead.getDireccion());
                                                                            listaClienteCabeceraEntity.setSaldo(lead.getSaldo());
                                                                            listaClienteCabeceraEntity.setImvclientecabecera(0);
                                                                            listaClienteCabeceraEntity.setMoneda(listaClienteEnvio.get(i).getMoneda());
                                                                            listaClienteCabeceraEntity.setDomembarque_id(listaClienteEnvio.get(i).getDomembarque_id());
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


    }

}
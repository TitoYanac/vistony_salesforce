package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.os.Bundle;
/*
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
*/
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaRutaVendedorEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ClienteCabeceraView;
import com.vistony.salesforce.View.RutaVendedorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaRutaVendedorAdapter  extends ArrayAdapter<ListaRutaVendedorEntity> {
    public static ArrayList<ListaClienteCabeceraEntity> ArraylistaClienteCabeceraEntity;
    public ArrayList<ListaRutaVendedorEntity> employeeArrayList;
    public ArrayList<ListaRutaVendedorEntity> orig;
    ListaClienteCabeceraEntity listaClienteCabeceraEntity;
    public ClienteCabeceraView clienteCabeceraView;
    public RutaVendedorView rutaVendedorView;
    //ClienteCabeceraView.OnFragmentInteractionListener
    ClienteCabeceraView fragment;
    Fragment fragmento;
    private FragmentManager fragmentManager;
    private Context Context;
    private List<ListaRutaVendedorEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaRutaVendedorEntity> arrayList;


    public ListaRutaVendedorAdapter(Context context, List<ListaRutaVendedorEntity> objects) {
        super(context, 0, objects);
        this.Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaRutaVendedorEntity>();
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
            for(ListaRutaVendedorEntity wp: arrayList)
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
    public ListaRutaVendedorEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArraylistaClienteCabeceraEntity= new ArrayList <ListaClienteCabeceraEntity>();
        rutaVendedorView = new RutaVendedorView();
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaRutaVendedorAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_ruta_vendedor,
                    parent,
                    false);

            holder = new ListaRutaVendedorAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_clienteid = (TextView) convertView.findViewById(R.id.tv_cliente_id);
            holder.tv_nombrecliente = (TextView) convertView.findViewById(R.id.tv_nombrecliente);
            holder.tv_direccion = (TextView) convertView.findViewById(R.id.tv_direccion);
//            holder.tv_ordenvisita = (TextView) convertView.findViewById(R.id.tv_ordenvisita);
            holder.imv_clientecabecera = (ImageView) convertView.findViewById(R.id.imv_cliente_cabecera);
  //          holder.realtive_ruta=convertView.findViewById(R.id.layout_rutaVendedor);
            convertView.setTag(holder);
        } else {
            holder = (ListaRutaVendedorAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaRutaVendedorEntity lead = getItem(position);

        // Setup.
        holder.tv_clienteid.setText(lead.getCliente_id());
        holder.tv_nombrecliente.setText(lead.getNombrecliente());
        holder.tv_direccion.setText(lead.getSaldomn());
        //holder.tv_ordenvisita.setText(String.valueOf(lead.getOrdenvisita()));
        holder.realtive_ruta.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              clienteCabeceraView = new ClienteCabeceraView();
                                                              listaClienteCabeceraEntity =  new ListaClienteCabeceraEntity();
                                                              String cliente_id="",nombrecliente="",direccion="",zona_id="";
                                                              cliente_id=lead.getCliente_id().toString();
                                                              nombrecliente=lead.getNombrecliente().toString();
                                                              direccion=lead.getDireccion().toString();
                                                              listaClienteCabeceraEntity.setCliente_id(cliente_id);
                                                              listaClienteCabeceraEntity.setNombrecliente(nombrecliente);
                                                              listaClienteCabeceraEntity.setDireccion(direccion);


                                                              ArraylistaClienteCabeceraEntity.add(listaClienteCabeceraEntity);
                                                              ArraylistaClienteCabeceraEntity.size();
                                                              String Cliente="";
                                                              Cliente="CL01";
                                                              fragment = new ClienteCabeceraView();
                                                              Bundle bundle = new Bundle();
                                                              bundle.putString("valor",Cliente);

                                                              fragment.setArguments(bundle);
                                                              fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                              FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                              //transaction.replace(android.R.id.content, fragment);
                                                              //transaction.addToBackStack("DiscoverPage");

                                                              //(ArrayList<ListaClienteCabeceraEntity>)ArraylistaClienteCabeceraEntity;
                                                              transaction.add(R.id.content_menu_view, rutaVendedorView.newInstancia(ArraylistaClienteCabeceraEntity));
                                                              //transaction.commit();


                                                              //clienteCabeceraView.ObtenerLista(Cliente);

                                                              //mListener.onFragmentInteraction(ArraylistaClienteCabeceraEntity);
                                                              //                                                     //String Texto="VALERIA";
                                                              //                                                     //String Texto1=Texto;
                                                          }
                                                      }

        );


        //Glide.with(getContext()).load(lead.getImage()).into(holder.avatar);

        return convertView;
    }




    static class ViewHolder {
        //TextView lbl_documento;
        TextView tv_clienteid;
        //TextView lbl_fecha_emision;
        TextView tv_nombrecliente;
        // TextView lbl_fecha_vencimiento;
        TextView tv_direccion;
        TextView tv_ordenvisita;
        // TextView lbl_importe;
        ImageView imv_clientecabecera;
        //RelativeLayout
        RelativeLayout realtive_ruta;
    }

}

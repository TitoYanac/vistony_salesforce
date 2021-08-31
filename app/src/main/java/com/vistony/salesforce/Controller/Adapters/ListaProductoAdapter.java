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

import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.ProductoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaProductoAdapter extends ArrayAdapter<ListaProductoEntity> {

    public static ArrayList<ListaProductoEntity> araylistaProductoEntity;
    private android.content.Context Context;
    private List<ListaProductoEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaProductoEntity> arrayList;
    private FragmentManager fragmentManager;

    public ListaProductoAdapter(android.content.Context context, List<ListaProductoEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaProductoEntity>();
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
            for(ListaProductoEntity wp: arrayList)
            {
                if(wp.getProducto_id().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getProducto().toLowerCase(Locale.getDefault()).contains(charText))
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
    public ListaProductoEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaProductoEntity= new ArrayList <ListaProductoEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaProductoAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_producto,
                    parent,
                    false);

            holder = new ListaProductoAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_productoid = (TextView) convertView.findViewById(R.id.tv_productoid);
            holder.tv_umd = (TextView) convertView.findViewById(R.id.tv_umd);
            holder.tv_producto = (TextView) convertView.findViewById(R.id.tv_producto);
            holder.tv_stock = (TextView) convertView.findViewById(R.id.tv_stock);
            holder.tv_precio = (TextView) convertView.findViewById(R.id.tv_precio);
            holder.tv_igv = (TextView) convertView.findViewById(R.id.tv_igv);
            holder.tv_gal = (TextView) convertView.findViewById(R.id.tv_gal);

            holder.relativeListaProducto=convertView.findViewById(R.id.relativeListaProducto);
            convertView.setTag(holder);
        } else {
            holder = (ListaProductoAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaProductoEntity lead = getItem(position);

        // Setup.
        holder.tv_productoid.setText(lead.getProducto_id());
        holder.tv_producto.setText(lead.getProducto());
        holder.tv_umd.setText(lead.getUmd());
        holder.tv_stock.setText(lead.getStock());
        holder.tv_precio.setText(lead.getPreciobase());
        holder.tv_igv.setText(lead.getPrecioigv());
        holder.tv_gal.setText(lead.getGal());

        holder.relativeListaProducto.setOnClickListener(v -> {
            ArrayList<ListaProductoEntity> arrayListaProductoEntity= new ArrayList<>();
            ListaProductoEntity listaProductoEntity=new ListaProductoEntity();
            listaProductoEntity.producto_id = lead.getProducto_id();
            listaProductoEntity.producto = lead.getProducto();
            listaProductoEntity.umd = lead.getUmd();
            listaProductoEntity.stock = lead.getStock();
            listaProductoEntity.preciobase = lead.getPreciobase();
            listaProductoEntity.precioigv = lead.getPrecioigv();
            listaProductoEntity.gal = lead.getGal();
            listaProductoEntity.porcentaje_descuento_max = lead.getPorcentaje_descuento_max();

            arrayListaProductoEntity.add(listaProductoEntity);

            fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.content_menu_view, ProductoView.newInstancia(arrayListaProductoEntity));
        }

        );
        return convertView;
    }
    static class ViewHolder {
        TextView tv_productoid;
        TextView tv_umd;
        TextView tv_producto;
        TextView tv_stock;
        TextView tv_precio;
        TextView tv_igv;
        TextView tv_gal;

        RelativeLayout relativeListaProducto;
    }


}

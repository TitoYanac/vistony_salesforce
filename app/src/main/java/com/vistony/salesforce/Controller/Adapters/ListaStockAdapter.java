package com.vistony.salesforce.Controller.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Entity.Adapters.ListaStockEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaStockAdapter extends ArrayAdapter<ListaStockEntity>
{
    public static ArrayList<ListaStockEntity> araylistaStockEntity;
    private android.content.Context Context;
    private List<ListaStockEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaStockEntity> arrayList;
    private FragmentManager fragmentManager;

    public ListaStockAdapter(android.content.Context context, List<ListaStockEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaStockEntity>();
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
            for(ListaStockEntity wp: arrayList)
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
    public ListaStockEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaStockEntity= new ArrayList <ListaStockEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaStockAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_stock,
                    parent,
                    false);

            holder = new ListaStockAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_productoid = (TextView) convertView.findViewById(R.id.tv_productoid);
            holder.tv_umd = (TextView) convertView.findViewById(R.id.tv_umd);
            holder.tv_producto = (TextView) convertView.findViewById(R.id.tv_producto);
            holder.tv_stock = (TextView) convertView.findViewById(R.id.tv_stock);

            convertView.setTag(holder);
        } else {
            holder = (ListaStockAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaStockEntity lead = getItem(position);

        // Setup.
        holder.tv_productoid.setText(lead.getProducto_id());
        holder.tv_producto.setText(lead.getProducto());
        holder.tv_umd.setText(lead.getUmd());
        holder.tv_stock.setText(lead.getStock());

        return convertView;
    }
    static class ViewHolder {
        TextView tv_productoid;
        TextView tv_umd;
        TextView tv_producto;
        TextView tv_stock;
    }

}

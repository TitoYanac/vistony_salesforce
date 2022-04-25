package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListWareHousesAdapter extends ArrayAdapter<WareHousesEntity>  {
    LayoutInflater inflater;
    private Context Context;
    private List<WareHousesEntity> Listanombres =null;
    private ArrayList<WareHousesEntity> arrayList;

    public ListWareHousesAdapter(Context context, List<WareHousesEntity> objects) {
        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(context);
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
            for(WareHousesEntity wp: arrayList)
            {
                if(wp.getWhsName()  .toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getWhsName()  .toLowerCase(Locale.getDefault()).contains(charText))
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
    public WareHousesEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListWareHousesAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_cliente_cabecera,
                    parent,
                    false);

            holder = new ListWareHousesAdapter.ViewHolder();
            holder.tv_whscode = (TextView) convertView.findViewById(R.id.tv_whscode);
            holder.tv_whsname = (TextView) convertView.findViewById(R.id.tv_whsname);
            holder.tv_stock = (TextView) convertView.findViewById(R.id.tv_stock);
            holder.tv_branch= (TextView) convertView.findViewById(R.id.tv_branch);
            convertView.setTag(holder);
        } else {
            holder = (ListWareHousesAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final WareHousesEntity lead = getItem(position);

        holder.tv_whscode.setText(lead.getCodAlmacen());
        holder.tv_whsname.setText(lead.getWhsName());
        holder.tv_stock.setText(lead.getEnStock());
        holder.tv_branch.setText(lead.getU_VIST_SUCUSU());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_whscode;
        TextView tv_whsname;
        TextView tv_stock;
        TextView tv_branch;
    }
}

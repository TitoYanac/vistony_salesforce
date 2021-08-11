package com.vistony.salesforce.Controller.Adapters;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vistony.salesforce.Entity.Adapters.ListaCatalogoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.CatalogoEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaCatalogoAdapter  extends ArrayAdapter<CatalogoEntity> {
    public static ArrayList<CatalogoEntity> arayCatalogoEntity;
    private android.content.Context Context;
    private List<CatalogoEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<CatalogoEntity> arrayList;

    public ListaCatalogoAdapter(android.content.Context context, List<CatalogoEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<CatalogoEntity>();
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
            for(CatalogoEntity wp: arrayList)
            {
                if(wp.getDescripcion().toLowerCase(Locale.getDefault()).contains(charText))
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
    public CatalogoEntity getItem(int position) {
        return Listanombres.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        arayCatalogoEntity= new ArrayList <>();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaCatalogoAdapter.ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.layout_lista_catalogo,parent,false);

            holder = new ListaCatalogoAdapter.ViewHolder();

            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_catalogo = (TextView) convertView.findViewById(R.id.tv_catalogo);
            holder.fabdescargarcatalogo = (FloatingActionButton) convertView.findViewById(R.id.fabdescargarcatalogo);
            convertView.setTag(holder);
        } else {
            holder = (ListaCatalogoAdapter.ViewHolder) convertView.getTag();
        }

        final CatalogoEntity lead = getItem(position);

        holder.tv_id.setText(lead.getCategoria());
        holder.tv_catalogo.setText(lead.getDescripcion());

        holder.fabdescargarcatalogo.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setDataAndType(Uri.parse(lead.getRuta()), lead.getTipo());
            getContext().startActivity(intent);
        });

        return convertView;
    }




    static class ViewHolder {
        TextView tv_id;
        TextView tv_catalogo;
        FloatingActionButton fabdescargarcatalogo;
    }
}

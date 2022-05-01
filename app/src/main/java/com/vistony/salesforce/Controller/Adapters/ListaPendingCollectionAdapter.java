package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaPendingCollectionAdapter  extends ArrayAdapter<ListaPendingCollectionEntity> {
    public static List<ListaPendingCollectionEntity> ArraylistaParametrosEntity = new ArrayList<>();;
    ListaPendingCollectionEntity listaPendingCollectionEntity;
    private Context context;


    public ListaPendingCollectionAdapter(Context context, List<ListaPendingCollectionEntity> objects) {

        super(context, 0, objects);
        ArraylistaParametrosEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaPendingCollectionAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_pending_collection,
                    parent,
                    false);

            holder = new ListaPendingCollectionAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_fecha_pending_collection = (TextView) convertView.findViewById(R.id.tv_fecha_pending_collection);
            holder.tv_count_pending_collection = (TextView) convertView.findViewById(R.id.tv_count_pending_collection);
            convertView.setTag(holder);
        } else {
            holder = (ListaPendingCollectionAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaPendingCollectionEntity lead = getItem(position);

        // Setup.
        holder.tv_fecha_pending_collection.setText(Induvis.getDate(BuildConfig.FLAVOR,lead.getDate()) );
        holder.tv_count_pending_collection.setText(lead.getCount());

        return convertView;
    }

    static class ViewHolder {
        TextView tv_fecha_pending_collection;
        TextView tv_count_pending_collection;
    }
}

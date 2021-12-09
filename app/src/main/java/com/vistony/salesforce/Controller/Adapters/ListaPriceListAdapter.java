package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPriceListEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.PriceListView;

import java.util.ArrayList;
import java.util.List;

public class ListaPriceListAdapter extends ArrayAdapter<ListaPriceListEntity> {
    public static List<ListaPriceListEntity> ArraylistaPriceListEntity = new ArrayList<ListaPriceListEntity>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context;
    private FragmentManager fragmentManager;
    PriceListView priceListView;

    public ListaPriceListAdapter(Context context, List<ListaPriceListEntity> objects) {

        super(context, 0, objects);
        ArraylistaPriceListEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaPriceListAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_price_list,
                    parent,
                    false);

            holder = new ListaPriceListAdapter.ViewHolder();
            holder.tv_pricelist_id = (TextView) convertView.findViewById(R.id.tv_pricelist_id);
            holder.tv_pricelist = (TextView) convertView.findViewById(R.id.tv_pricelist);
            holder.relativeLayout=convertView.findViewById(R.id.relativeListaPriceList);
            convertView.setTag(holder);
        } else {
            holder = (ListaPriceListAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaPriceListEntity lead = getItem(position);

        // Setup.
        holder.tv_pricelist_id.setText(lead.getPricelist_id());
        holder.tv_pricelist.setText(lead.getPricelist());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                        transaction.add(R.id.content_menu_view, priceListView.newInstanceEnviarPriceList(lead));
                                                    }
                                                }
        );
        return convertView;
    }

    static class ViewHolder {
        TextView tv_pricelist_id;
        TextView tv_pricelist;
        RelativeLayout relativeLayout;
    }

}

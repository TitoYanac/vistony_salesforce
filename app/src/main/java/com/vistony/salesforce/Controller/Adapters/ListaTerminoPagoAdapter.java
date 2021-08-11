package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaTerminoPagoEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.TerminoPagoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaTerminoPagoAdapter extends ArrayAdapter<ListaTerminoPagoEntity> {
    private android.content.Context Context;
    LayoutInflater inflater;
    private ArrayList<ListaTerminoPagoEntity> arrayList;
    private FragmentManager fragmentManager;
    public TerminoPagoView terminoPagoView;
    private List<ListaTerminoPagoEntity> Listanombres =null;

    public ListaTerminoPagoAdapter(Context context, List<ListaTerminoPagoEntity> objects) {
        super(context, 0, objects);
        this.Context=context;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaTerminoPagoEntity>();
        this.arrayList.addAll(objects);
        this.Listanombres= objects;
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
            for(ListaTerminoPagoEntity wp: arrayList)
            {
                if(wp.getTerminopago_id().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getTerminopago().toLowerCase(Locale.getDefault()).contains(charText))
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
    public ListaTerminoPagoEntity getItem(int position) {
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

        final ListaTerminoPagoAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_termino_pago,
                    parent,
                    false);

            holder = new ListaTerminoPagoAdapter.ViewHolder();
            holder.tv_terminopago = (TextView) convertView.findViewById(R.id.tv_terminopago);
            holder.tv_terminopago_id = (TextView) convertView.findViewById(R.id.tv_terminopago_id);
            holder.realtive_ruta=convertView.findViewById(R.id.realtive_ruta);
            convertView.setTag(holder);
        } else {
            holder = (ListaTerminoPagoAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaTerminoPagoEntity lead = getItem(position);

        // Setup.
        holder.tv_terminopago.setText(lead.getTerminopago());
        holder.tv_terminopago_id.setText(lead.getTerminopago_id());
        holder.realtive_ruta.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        ArrayList<TerminoPagoSQLiteEntity> listaTerminoPagoSQLiteEntity= new ArrayList<>();
                                                        TerminoPagoSQLiteEntity terminoPagoSQLiteEntity=new TerminoPagoSQLiteEntity();
                                                        terminoPagoSQLiteEntity.terminopago = lead.getTerminopago().toString();
                                                        terminoPagoSQLiteEntity.terminopago_id = lead.getTerminopago_id().toString();
                                                        terminoPagoSQLiteEntity.contado = lead.getContado();
                                                        listaTerminoPagoSQLiteEntity.add(terminoPagoSQLiteEntity);

                                                        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                                                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                                                        transaction.add(R.id.content_menu_view, terminoPagoView.newInstancia(listaTerminoPagoSQLiteEntity));







        }
                                                }

        );
        return convertView;
    }




    static class ViewHolder {
        TextView tv_terminopago;
        TextView tv_terminopago_id;
        RelativeLayout realtive_ruta;
    }
}

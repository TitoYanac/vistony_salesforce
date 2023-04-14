package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    private String statuscounted;
    public ListaTerminoPagoAdapter(
            Context context,
            List<ListaTerminoPagoEntity> objects,
            String statuscounted

    ) {
        super(context, 0, objects);
        this.Context=context;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaTerminoPagoEntity>();
        this.arrayList.addAll(objects);
        this.Listanombres= objects;
        this.statuscounted=statuscounted;
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
                                                        Log.e("REOS","ListaTerminoPagoAdapter-holder.realtive_ruta.setOnClickListener-statuscounted"+statuscounted);
                                                        Log.e("REOS","ListaTerminoPagoAdapter-holder.realtive_ruta.setOnClickListener-lead.getTerminopago()"+lead.getTerminopago());
                                                        if(statuscounted.equals("Y")&&lead.getTerminopago().equals("CONTADO"))
                                                        {
                                                            alertdialogInformative(getContext(),"Advertencia!!!","Motivo: \n No es posible elegir termino de pago (Contado), porque la ultima factura fue devuelta con motivo - (NO TIENE DINERO).\n Sugerencia: \n Elegir el termino, Pago Adelantado.").show();
                                                            //Toast.makeText(getContext(), "No es posible elegir termino de pago contado, porque la ultima factura fue devuelta por motivo - No tiene Dinero", Toast.LENGTH_SHORT).show();
                                                        }else {
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
                                                }

        );
        return convertView;
    }




    static class ViewHolder {
        TextView tv_terminopago;
        TextView tv_terminopago_id;
        RelativeLayout realtive_ruta;
    }

    static private Dialog alertdialogInformative(Context context, String titulo, String message) {

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_dialog);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj);
        TextView text=(TextView) dialog.findViewById(R.id.text);
        text.setText(titulo);
        textViewMsj.setText(message);
        // if button is clicked, close the custom dialog
        dialogButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
}

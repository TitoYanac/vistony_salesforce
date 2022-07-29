package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListaTerminoPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotationEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.TerminoPagoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricQuotationAdapter  extends ArrayAdapter<QuotationEntity> {
    private android.content.Context Context;
    LayoutInflater inflater;
    private ArrayList<QuotationEntity> arrayList;
    private FragmentManager fragmentManager;
    public TerminoPagoView terminoPagoView;
    private List<QuotationEntity> Listanombres =null;

    public ListHistoricQuotationAdapter (android.content.Context context, List<QuotationEntity> objects) {
        super(context, 0, objects);
        this.Context=context;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<QuotationEntity>();
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
            for(QuotationEntity wp: arrayList)
            {
                if(wp.getCardname().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getCardcode().toLowerCase(Locale.getDefault()).contains(charText))
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
    public QuotationEntity getItem(int position) {
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

        final ListHistoricQuotationAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_list_historic_quotation,
                    parent,
                    false);

            holder = new ListHistoricQuotationAdapter.ViewHolder();
            holder.tv_docnum = (TextView) convertView.findViewById(R.id.tv_docnum);
            holder.tv_ruc = (TextView) convertView.findViewById(R.id.tv_ruc);
            holder.tv_cardname=convertView.findViewById(R.id.tv_cardname);
            holder.tv_amount_quotation=convertView.findViewById(R.id.tv_amount_quotation);
            holder.tv_status_aprovval=convertView.findViewById(R.id.tv_status_aprovval);
            holder.imv_historic_quotation_send=convertView.findViewById(R.id.imv_historic_quotation_send);
            holder.imv_historic_quotation_response=convertView.findViewById(R.id.imv_historic_quotation_response);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricQuotationAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final QuotationEntity lead = getItem(position);

        // Setup.
        holder.tv_docnum.setText(lead.getDocnum());
        holder.tv_ruc.setText(lead.getLictradnum());
        holder.tv_cardname.setText(lead.getCardname());
        holder.tv_amount_quotation.setText(lead.getAmounttotal());
        holder.tv_status_aprovval.setText(lead.getApprovalstatus());

        holder.imv_historic_quotation_send.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {


                                                    }
                                                }

        );
        return convertView;
    }
    static class ViewHolder {
        TextView tv_docnum;
        TextView tv_ruc;
        TextView tv_cardname;
        TextView tv_amount_quotation;
        TextView tv_status_aprovval;
        ImageView imv_historic_quotation_send;
        ImageView imv_historic_quotation_response;
    }
}

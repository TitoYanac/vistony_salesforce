package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Entity.Adapters.ListKardexOfPaymentEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaSeguimientoFacturasEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.KardexOfPaymentView;

import java.util.ArrayList;
import java.util.List;

public class ListKardexOfPaymentAdapter extends ArrayAdapter<ListKardexOfPaymentEntity>  {
    public  List<ListKardexOfPaymentEntity> ArrayListKardexOfPaymentEntity = new ArrayList<>();;
    private Context context;


    public ListKardexOfPaymentAdapter(Context context, List<ListKardexOfPaymentEntity> objects) {

        super(context, 0, objects);
        ArrayListKardexOfPaymentEntity=objects;
        this.context=context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ListKardexOfPaymentAdapter.ViewHolder holder;
        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_kardex_of_payment,
                    parent,
                    false);

            holder = new ListKardexOfPaymentAdapter.ViewHolder();
            holder.tv_legalnumber = (TextView) convertView.findViewById(R.id.tv_legalnumber);
            holder.tv_invoicedate = (TextView) convertView.findViewById(R.id.tv_invoicedate);
            holder.tv_duedate = (TextView) convertView.findViewById(R.id.tv_duedate);
            holder.tv_DocAmount = (TextView) convertView.findViewById(R.id.tv_DocAmount);
            holder.tv_balance = (TextView) convertView.findViewById(R.id.tv_balance);
            holder.chk_invoice = (CheckBox) convertView.findViewById(R.id.chk_invoice);
            holder.tv_paymentterms = (TextView) convertView.findViewById(R.id.tv_paymentterms);

            convertView.setTag(holder);
        } else {
            holder = (ListKardexOfPaymentAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListKardexOfPaymentEntity lead = getItem(position);

        // Setup.
        holder.tv_legalnumber.setText(lead.getLegalnumber());
        holder.tv_invoicedate.setText(lead.getInvoicedate());
        holder.tv_duedate.setText(lead.getDuedate());
        holder.tv_DocAmount.setText(lead.getDocAmount());
        holder.tv_balance.setText(lead.getBalance());
        holder.chk_invoice.setChecked(lead.isInvoice());
        holder.tv_paymentterms.setText (lead.getPaymentterms());
        holder.chk_invoice.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   for(int i=0;i< KardexOfPaymentView.listKardexOfPaymentEntityList.size();i++)
                                                   {
                                                       if(lead.getLegalnumber().equals(KardexOfPaymentView.listKardexOfPaymentEntityList.get(i).getLegalnumber()))
                                                       {
                                                           Log.e("REOS","ListKardexOfPaymentAdapter.getView.holder.chk_invoice.isChecked():" + holder.chk_invoice.isChecked());
                                                           Log.e("REOS","ListKardexOfPaymentAdapter.getView.holder.KardexOfPaymentView.listKardexOfPaymentEntityList.get(i).getLegalnumber():" + KardexOfPaymentView.listKardexOfPaymentEntityList.get(i).getLegalnumber());
                                                           Log.e("REOS","ListKardexOfPaymentAdapter.getView.holder.lead.getLegalnumber():" + lead.getLegalnumber());
                                                           KardexOfPaymentView.listKardexOfPaymentEntityList.get(i).setInvoice(holder.chk_invoice.isChecked());
                                                           Log.e("REOS","ListKardexOfPaymentAdapter.getView.holder.KardexOfPaymentView.listKardexOfPaymentEntityList.get(i).isInvoice:" + KardexOfPaymentView.listKardexOfPaymentEntityList.get(i).isInvoice());
                                                       }
                                                   }
                                               }
                                           }

        );

        return convertView;
    }

    static class ViewHolder {
        TextView tv_legalnumber;
        TextView tv_invoicedate;
        TextView tv_duedate;
        TextView tv_DocAmount;
        TextView tv_balance;
        TextView tv_paymentterms;
        CheckBox chk_invoice;
    }

}

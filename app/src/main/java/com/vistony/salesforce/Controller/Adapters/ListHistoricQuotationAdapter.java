package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Adapters.ListHistoricQuotationDao;
import com.vistony.salesforce.Dao.Retrofit.QuotationRepository;
import com.vistony.salesforce.Entity.Adapters.ListaTerminoPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotationEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
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
    private ViewModelStoreOwner viewModelStoreOwner;
    static private ProgressDialog pd;
    private LifecycleOwner lifecycleOwner;
    private static String docentry,docnum,message,error;
    public ListHistoricQuotationAdapter (
            android.content.Context context,
            List<QuotationEntity> objects,
            ViewModelStoreOwner viewModelStoreOwner,
            LifecycleOwner lifecycleOwner
    ) {
        super(context, 0, objects);
        this.Context=context;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<QuotationEntity>();
        this.arrayList.addAll(objects);
        this.Listanombres= objects;
        this.viewModelStoreOwner=viewModelStoreOwner;
        this.lifecycleOwner=lifecycleOwner;
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
            holder.tv_historic_salesorder=convertView.findViewById(R.id.tv_historic_salesorder);

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
        holder.tv_amount_quotation.setText(Convert.currencyForView(lead.getAmounttotal()));
        holder.tv_status_aprovval.setText(lead.getApprovalstatus());
        holder.tv_historic_salesorder.setText(lead.getSalesorder());

        if(lead.getReadygeneration().equals("Y"))
        {
            holder.imv_historic_quotation_send.setColorFilter(ContextCompat.getColor(getContext(),R.color.Black));
            holder.imv_historic_quotation_send.setEnabled(true);
            holder.imv_historic_quotation_response.setColorFilter(ContextCompat.getColor(getContext(),R.color.Black));
            holder.imv_historic_quotation_response.setEnabled(true);

        }else {
            holder.imv_historic_quotation_send.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
            holder.imv_historic_quotation_send.setEnabled(false);
            holder.imv_historic_quotation_response.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
            holder.imv_historic_quotation_response.setEnabled(false);
        }

        holder.imv_historic_quotation_send.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        QuotationRepository quotationRepository=null;
                                                        quotationRepository= new ViewModelProvider(viewModelStoreOwner).get(QuotationRepository.class);
                                                        pd = new ProgressDialog(Context);
                                                        pd = ProgressDialog.show(Context, "Por favor espere", "Migrando Cotizacion a Orden de Venta", true, false);
                                                        quotationRepository.sendQuotation(SesionEntity.imei, lead.getDocentry()).observe(lifecycleOwner, data -> {
                                                            Log.e("REOS","HistoricQuotationView-getListHistoricQuotation-data: "+data);
                                                            if(data!=null)
                                                            {
                                                                for(int i=0;i<data.size();i++)
                                                                {
                                                                    holder.tv_historic_salesorder.setText(data.get(i).getDocNum());
                                                                    docentry=data.get(i).getDocEntry();
                                                                    docnum=data.get(i).getDocEntry();
                                                                    message=data.get(i).getMessage();
                                                                    error=data.get(i).getErrorCode();
                                                                }
                                                                if(error.equals("0"))
                                                                {

                                                                }
                                                                Toast.makeText(Context, "Cotizacion Migrada Correctamente", Toast.LENGTH_SHORT).show();
                                                                holder.imv_historic_quotation_send.setColorFilter(ContextCompat.getColor(getContext(),R.color.gray));
                                                                holder.imv_historic_quotation_send.setEnabled(false);
                                                            }else {
                                                                Toast.makeText(Context, "No se pudo Migrar la Cotizacion", Toast.LENGTH_SHORT).show();
                                                            }
                                                            pd.dismiss();
                                                        });

                                                    }
                                                }

        );

        holder.imv_historic_quotation_response.setOnClickListener(new View.OnClickListener() {
                                                                  @Override
                                                                  public void onClick(View v) {
                                                                      alertShowCommentary("Orden Venta",message).show();
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
        TextView tv_historic_salesorder;
    }

    private Dialog alertShowCommentary(String Titulo, String Comentario) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_dialog);

        TextView textTitle = dialog.findViewById(R.id.text);
        textTitle.setText(Titulo);

        TextView textMsj = dialog.findViewById(R.id.textViewMsj);
        textMsj.setText((Comentario==null)?"Sin Comentario":Comentario);

        ImageView image = (ImageView) dialog.findViewById(R.id.image);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        image.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return  dialog;
    }
}

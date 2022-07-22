package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Entity.Adapters.ListCurrencyChargedEntity;
import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.AgenciaView;
import com.vistony.salesforce.View.CurrencyChargedView;
import com.vistony.salesforce.View.LoginView;
import com.vistony.salesforce.View.OrdenVentaDetalleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListCurrencyChargedAdapter extends ArrayAdapter<ListCurrencyChargedEntity>  {
    private android.content.Context Context;
    LayoutInflater inflater;
    private FragmentManager fragmentManager;
    CurrencyChargedView currencyChargedView;
    public ListCurrencyChargedAdapter(android.content.Context context, List<ListCurrencyChargedEntity> objects) {
        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListCurrencyChargedAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_list_currency_charged,
                    parent,
                    false);

            holder = new ListCurrencyChargedAdapter.ViewHolder();
            holder.spn_currency_type =convertView.findViewById(R.id.spn_currency_type);
            holder.spn_currency_unit = convertView.findViewById(R.id.spn_currency_unit);
            holder.et_currency_quantity=convertView.findViewById(R.id.et_currency_quantity);
            holder.cv_currency=convertView.findViewById(R.id.cv_currency);
            holder.tv_currency_id =convertView.findViewById(R.id.tv_currency_id);
            holder.tv_currency_amount =convertView.findViewById(R.id.tv_currency_amount);
            holder.imv_clear_currency_detail =convertView.findViewById(R.id.imv_clear_currency_detail);

            convertView.setTag(holder);
        } else {
            holder = (ListCurrencyChargedAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListCurrencyChargedEntity lead = getItem(position);

        holder.tv_currency_id.setText(lead.getId());
        holder.et_currency_quantity.setText(lead.getQuantity());
        holder.tv_currency_amount.setText(Convert.currencyForView(lead.getAmount()) );
        currencyChargedView=new CurrencyChargedView();
        //Coloreado CardView
        GradientDrawable layer3 = new GradientDrawable();
        layer3.setShape(GradientDrawable.RECTANGLE);
        layer3.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        //layer3.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(k).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(k).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
        layer3.setColors(new int[] { Color.GRAY,Color.GRAY}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
        holder.cv_currency.setBackgroundDrawable(layer3);
        GradientDrawable layer1 = new GradientDrawable();
        layer1.setShape(GradientDrawable.RECTANGLE);
        layer1.setSize(33,33);
        layer1.setColor(Color.WHITE);
        layer1.setCornerRadius(20);

        GradientDrawable layer2 = new GradientDrawable();
        layer2.setCornerRadius(20);
        //layer1.setShape(GradientDrawable.RECTANGLE);
        //layer2.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(k).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(k).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
        layer2.setColors(new int[] { Color.GRAY,Color.GRAY}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
        InsetDrawable insetLayer2 = new InsetDrawable(layer1, 8, 8, 8, 8);

        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]
                {layer2,insetLayer2});
        holder.cv_currency.setBackground(layerDrawable);
        holder.spn_currency_type.setEnabled(lead.isState_sp_typecurrency());
        holder.spn_currency_unit.setEnabled(lead.isState_sp_unitcurrency());
        holder.spn_currency_type.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        String type = holder.spn_currency_type.getSelectedItem().toString();
                        if(type.equals("BILLETES"))
                        {
                            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.currency_type_billete , android.R.layout.simple_spinner_item);
                            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.currency_type_billete , android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(R.layout.layout_custom_spinner);
                            holder.spn_currency_unit.setAdapter(adapter);
                        }
                        else if(type.equals("MONEDAS"))
                        {
                            //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.currency_type_moneda, android.R.layout.simple_spinner_item);
                            //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.currency_type_moneda, android.R.layout.simple_spinner_item);
                            adapter.setDropDownViewResource(R.layout.layout_custom_spinner);
                            holder.spn_currency_unit.setAdapter(adapter);
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );
        holder.et_currency_quantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean procesado = false;

                if (actionId == EditorInfo.IME_ACTION_SEND) {

                    String valor="0";
                    if(v.length()==0)
                    {
                        valor="0";
                    }
                    else {
                        valor=String.valueOf(Integer.parseInt(v.getText().toString()));
                    }
                    for( int i=0;i<currencyChargedView.listCurrencyChargedEntities.size();i++)
                    {
                        //Log.e("REOS","ListCurrencyChargedAdapter-holder.et_currency_quantity.setOnEditorActionListener.currencyChargedView.listCurrencyChargedEntities.get(i).getId():"+currencyChargedView.listCurrencyChargedEntities.get(i).getId());
                        //Log.e("REOS","ListCurrencyChargedAdapter-holder.et_currency_quantity.setOnEditorActionListener.lead.getId():"+lead.getId());
                        if(currencyChargedView.listCurrencyChargedEntities.get(i).getId().equals(lead.getId()))
                        {
                            currencyChargedView.listCurrencyChargedEntities.get(i).setQuantity(valor);
                            currencyChargedView.listCurrencyChargedEntities.get(i).setAmount(
                                    String.valueOf(
                                    Float.parseFloat(currencyChargedView.listCurrencyChargedEntities.get(i).getQuantity())
                                            *
                                    Float.parseFloat(holder.spn_currency_unit.getSelectedItem().toString())
                                    )
                            );
                            holder.tv_currency_amount.setText(
                                    Convert.currencyForView(
                                    currencyChargedView.listCurrencyChargedEntities.get(i).getAmount()
                                    )
                            );
                            Log.e("REOS","ListCurrencyChargedAdapter-holder.et_currency_quantity.setOnEditorActionListener.currencyChargedView.listCurrencyChargedEntities.get(i).getId():"+currencyChargedView.listCurrencyChargedEntities.get(i).getId());
                            Log.e("REOS","ListCurrencyChargedAdapter-holder.et_currency_quantity.setOnEditorActionListener.lead.getId():"+lead.getId());
                            Log.e("REOS","ListCurrencyChargedAdapter-holder.et_currency_quantity.setOnEditorActionListener.currencyChargedView.listCurrencyChargedEntities.get(i).setQuantity:"+currencyChargedView.listCurrencyChargedEntities.get(i).getQuantity());
                            currencyChargedView.listCurrencyChargedEntities.get(i).setState_sp_typecurrency(false);
                            currencyChargedView.listCurrencyChargedEntities.get(i).setState_sp_unitcurrency(false);
                            UpdateListCurrencyCharged();
                            UpdateListSummaryCurrencyCharged();
                        }
                    }

                }
                return procesado;
            }
        });



        holder.imv_clear_currency_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertClearDetailCurrency( (position)).show();
            }
        });

        return convertView;
    }




    static class ViewHolder {
        Spinner spn_currency_type;
        Spinner spn_currency_unit;
        EditText et_currency_quantity;
        CardView cv_currency;
        TextView tv_currency_id;
        TextView tv_currency_amount;
        ImageView imv_clear_currency_detail;
        RelativeLayout relativeListaAgencia;


    }

    public void UpdateListSummaryCurrencyCharged()
    {
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, currencyChargedView.newInstanceActualizarResumen());
    }

    public void UpdateListCurrencyCharged()
    {
        fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_menu_view, currencyChargedView.newInstanceUpdateList());
    }

    private Dialog alertClearDetailCurrency(int Linea) {

        final Dialog dialog = new Dialog(Context);
        dialog.setContentView(R.layout.layout_alert_dialog_orden_venta_detalle);

        TextView textTitle = dialog.findViewById(R.id.text_orden_venta_detalle);
        textTitle.setText("Advertencia!!!");

        TextView textMsj = dialog.findViewById(R.id.textViewMsj_orden_venta_detalle);
        textMsj.setText("Seguro que desea eliminar la Linea "+String.valueOf(Linea+1)+" de la Lista de Dinero Cobrado?");

        ImageView image = (ImageView) dialog.findViewById(R.id.image_orden_venta_detalle);


        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);


        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK_orden_venta_detalle);
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel_orden_venta_detalle);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManager = ((AppCompatActivity) Context).getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_menu_view, currencyChargedView.newInstanceClarDetailCurrency(Linea));
                dialog.dismiss();
            }
        });
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
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

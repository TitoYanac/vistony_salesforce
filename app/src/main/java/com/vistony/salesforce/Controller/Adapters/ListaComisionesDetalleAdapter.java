package com.vistony.salesforce.Controller.Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.Adapters.ListaHojaDespachoDao;
import com.vistony.salesforce.Dao.Adapters.ListaLegendComissionsDao;
import com.vistony.salesforce.Dao.SQLite.EscColoursDSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaComisionesDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursDEntity;
import com.vistony.salesforce.ListenerBackPress;
import com.vistony.salesforce.R;

import java.util.ArrayList;
import java.util.List;

public class ListaComisionesDetalleAdapter  extends
        ArrayAdapter<ListaComisionesDetalleEntity> {
    public static List<ListaComisionesDetalleEntity> ArraylistaComisionesDetalleEntity;
    private Context context;
    //private String [] Indices=new String []{"Total","Re-programado"};
    private String[] Indices;
    //private int [] colors = new int []{Color.BLUE};
    private int[] colors;
    boolean[] itemChecked;
    ListaLegendComissionsAdapter listaLegendComissionsAdapter;
    Activity activity;
    public ListaComisionesDetalleAdapter(Context context, List<ListaComisionesDetalleEntity> objects,Activity activity) {
        super(context, 0, objects);
        ArraylistaComisionesDetalleEntity = objects;
        this.context = context;
        this.itemChecked=new boolean[objects.size()];
        this.activity=activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ListaComisionesDetalleAdapter.ViewHolder holder;
        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_comisiones_detalle,
                    parent,
                    false);

            holder = new ListaComisionesDetalleAdapter.ViewHolder();
            holder.tv_comisionesdetalle_variable = (TextView) convertView.findViewById(R.id.tv_comisionesdetalle_variable);
            holder.tv_comisionesdetalle_cuota = (TextView) convertView.findViewById(R.id.tv_comisionesdetalle_cuota);
            holder.tv_comisionesdetalle_avance = (TextView) convertView.findViewById(R.id.tv_comisionesdetalle_avance);
            holder.tv_comisionesdetalle_porcentaje = (TextView) convertView.findViewById(R.id.tv_comisionesdetalle_porcentaje);
            holder.tv_comisionesdetalle_umd = (TextView) convertView.findViewById(R.id.tv_comisionesdetalle_umd);
            holder.v_gradiente_comisiones_detalle = (View) convertView.findViewById(R.id.v_gradiente_comisiones_detalle);
            holder.cv_comision_detalle = (CardView) convertView.findViewById(R.id.cv_comision_detalle);
            holder.lbl_comisionesdetalle_cuota = (TextView) convertView.findViewById(R.id.lbl_comisionesdetalle_cuota);
            holder.lbl_comisionesdetalle_avance = (TextView) convertView.findViewById(R.id.lbl_comisionesdetalle_avance);
            holder.imv_get_legend = (ImageView) convertView.findViewById(R.id.imv_get_legend);
            //holder.barChartHorizonalComisionesDetalle = (BarChart) convertView.findViewById(R.id.barChartHorizonalComisionesDetalle);
            convertView.setTag(holder);
        } else {
            holder = (ListaComisionesDetalleAdapter.ViewHolder) convertView.getTag();
        }
        // Lead actual.
        final ListaComisionesDetalleEntity lead = getItem(position);
        // Setup.
        if(lead.getUmd().equals("SO"))

        {
            holder.tv_comisionesdetalle_cuota.setText(Convert.currencyForView(lead.getCuota()));
            holder.tv_comisionesdetalle_avance.setText(Convert.currencyForView(lead.getAvance()));
        }else
            {
                holder.tv_comisionesdetalle_cuota.setText(lead.getCuota());
                holder.tv_comisionesdetalle_avance.setText(lead.getAvance());
            }
        holder.tv_comisionesdetalle_variable.setText(lead.getVariable());
        holder.tv_comisionesdetalle_umd.setText(lead.getUmd());
        holder.tv_comisionesdetalle_porcentaje.setText(lead.getPorcentajeavance());
        ArrayList<ListaComisionesDetalleEntity> listaComisionesDetalleEntities = new ArrayList<>();
        listaComisionesDetalleEntities.add(lead);
        ArrayList<EscColoursDEntity> escColoursDEntityArrayList=new ArrayList<>();
        EscColoursDSQLiteDao escColoursDSQLiteDao=new EscColoursDSQLiteDao(getContext());
        escColoursDEntityArrayList=escColoursDSQLiteDao.GetEscColours(
                lead.getCodecolor(),
                Float.parseFloat(lead.getPorcentajeavance()
                )
        );
        for(int i=0;i<escColoursDEntityArrayList.size();i++)
        {

            GradientDrawable layer3 = new GradientDrawable();
            layer3.setShape(GradientDrawable.RECTANGLE);
            layer3.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            
            layer3.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(i).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(i).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),

            holder.v_gradiente_comisiones_detalle.setBackgroundDrawable(layer3);
            GradientDrawable layer1 = new GradientDrawable();
            layer1.setShape(GradientDrawable.RECTANGLE);
            layer1.setSize(33,33);
            layer1.setColor(Color.WHITE);
            layer1.setCornerRadius(20);

            GradientDrawable layer2 = new GradientDrawable();
            layer2.setCornerRadius(20);
            //layer1.setShape(GradientDrawable.RECTANGLE);
            layer2.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(i).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(i).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),

            InsetDrawable insetLayer2 = new InsetDrawable(layer1, 8, 8, 8, 8);

            LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]
                    {layer2,insetLayer2});
            holder.cv_comision_detalle.setBackground(layerDrawable);


        }

        if(lead.getHidedate().equals("Y"))
        {
            itemChecked[position] = false;
        }
        else {
            itemChecked[position] = true;
        }

        if(itemChecked[position])
        {
            holder.tv_comisionesdetalle_cuota.setVisibility(View.VISIBLE);
            holder.tv_comisionesdetalle_avance.setVisibility(View.VISIBLE);
            holder.lbl_comisionesdetalle_cuota.setVisibility(View.VISIBLE);
            holder.lbl_comisionesdetalle_avance.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.tv_comisionesdetalle_cuota.setVisibility(View.INVISIBLE);
            holder.tv_comisionesdetalle_avance.setVisibility(View.INVISIBLE);
            holder.lbl_comisionesdetalle_cuota.setVisibility(View.INVISIBLE);
            holder.lbl_comisionesdetalle_avance.setVisibility(View.INVISIBLE);
        }

        holder.imv_get_legend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertgetLegend(getContext(),lead).show();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tv_comisionesdetalle_variable;
        TextView tv_comisionesdetalle_cuota;
        TextView tv_comisionesdetalle_avance;
        TextView tv_comisionesdetalle_porcentaje;
        TextView tv_comisionesdetalle_umd;
        CardView cv_comision_detalle;
        View v_gradiente_comisiones_detalle;
        TextView lbl_comisionesdetalle_cuota;
        TextView lbl_comisionesdetalle_avance;
        ImageView imv_get_legend;
        //BarChart barChartHorizonalComisionesDetalle;

    }



    private Dialog alertgetLegend(Context context,ListaComisionesDetalleEntity lead) {

        final Dialog dialog = new Dialog(context);
        ArrayList<EscColoursDEntity> escColoursDEntityArrayList=new ArrayList<>();
        EscColoursDSQLiteDao escColoursDSQLiteDao=new EscColoursDSQLiteDao(getContext());
        dialog.setContentView(R.layout.layout_alert_dialog_legend_comissions);
        ImageView image = (ImageView) dialog.findViewById(R.id.image_alert_dialog_info);
        Drawable background = image.getBackground();
        image.setImageResource(R.mipmap.logo_circulo);
        Button dialogButtonOK = (Button) dialog.findViewById(R.id.dialogButtonOK_alert_dialog_info);
        ListView lv_legend_comissions=dialog.findViewById(R.id.lv_legend_comissions);
        TextView textViewMsj=(TextView) dialog.findViewById(R.id.textViewMsj_alert_dialog_info);
        TextView text=(TextView) dialog.findViewById(R.id.text_alert_dialog_info);
        text.setText("LEYENDA");
        textViewMsj.setText("Los Rangos estan minimo y maximo, y estan expresados en %:");
        //textViewMsj.setText("El SMS fue enviado Correctamente,solicitar al Cliente el codigo de SMS!!!");
        // if button is clicked, close the custom dialog
        Log.e("REOS", "ListaComisionesDetalleAdaptar-alertgetLegend-lead.getCodecolor():" + lead.getCodecolor());
        escColoursDEntityArrayList=escColoursDSQLiteDao.GetEscColoursForCode(lead.getCodecolor());
        Log.e("REOS", "ListaComisionesDetalleAdaptar-alertgetLegend-escColoursDEntityArrayList:" + escColoursDEntityArrayList.size());
        listaLegendComissionsAdapter = new ListaLegendComissionsAdapter(activity, ListaLegendComissionsDao.getInstance().getLeads(escColoursDEntityArrayList));
        lv_legend_comissions.setAdapter(listaLegendComissionsAdapter);

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

/*

    private void legend2 (Chart chart)
    {
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setMaxSizePercent(0.95f );
        ArrayList<LegendEntry>entries=new ArrayList<>();
        for(int i=0;i<Indices.length;i++)
        {
            LegendEntry entry=new LegendEntry();
//            entry.formColor=colors[i];
            entry.label=Indices[i];

            entries.add(entry);
        }
        legend.setWordWrapEnabled(true);
        legend.setEnabled(false);
        legend.setCustom(entries);

    }
    private void axisX(XAxis axis)
    {
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //axis.setValueFormatter(new IndexAxisValueFormatter(Indices));
    }
    private void axisLeft(YAxis axis)
    {
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private void axisRight(YAxis axis)
    {
        axis.setEnabled(false);
    }

    private ArrayList<BarEntry> getBarEntries(ArrayList<ListaComisionesDetalleEntity> Lista)
    {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i=0;i<Lista.size();i++)
        {
            float valor=0;
            valor=(Float.parseFloat (Lista.get(i).getAvance()));
            entries.add(new BarEntry(i,valor));
        }
        return entries;

    }
    private Chart getSameChart2(Chart chart, String description, int textColor, int background, int animateY)
    {

        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        //chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend2(chart);
        return chart;
    }

    private DataSet getData(DataSet dataSet)
    {
        //dataSet.setColors(colors);
        //dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }

    private BarData getBarData(ArrayList<ListaComisionesDetalleEntity> Lista)
    {
        BarDataSet barDataSet=(BarDataSet)getData(new BarDataSet(getBarEntries(Lista),""));
        //barDataSet.setBarShadowColor(Color.GRAY);
        barDataSet.setDrawIcons(false);

        int startColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
        int startColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_light);
        int startColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_light);
        int startColor4 = ContextCompat.getColor(getContext(), android.R.color.holo_green_light);
        int startColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_red_light);
        int endColor1 = ContextCompat.getColor(getContext(), android.R.color.holo_blue_dark);
        int endColor2 = ContextCompat.getColor(getContext(), android.R.color.holo_purple);
        int endColor3 = ContextCompat.getColor(getContext(), android.R.color.holo_green_dark);
        int endColor4 = ContextCompat.getColor(getContext(), android.R.color.holo_red_dark);
        int endColor5 = ContextCompat.getColor(getContext(), android.R.color.holo_orange_dark);

        List<GradientColor> gradientColors = new ArrayList<>();
        gradientColors.add(new GradientColor(startColor1, endColor1));
        gradientColors.add(new GradientColor(startColor2, endColor2));
        gradientColors.add(new GradientColor(startColor3, endColor3));
        gradientColors.add(new GradientColor(startColor4, endColor4));
        gradientColors.add(new GradientColor(startColor5, endColor5));

        barDataSet.setGradientColors(gradientColors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(barDataSet);

        BarData barData= new BarData(dataSets);
        //BarData barData= new BarData(barDataSet);
        //barData.setBarWidth(0.55f);
        barData.setBarWidth(0.40f);
        return barData;
    }
    public void cargarVariables(ArrayList<ListaComisionesDetalleEntity> Lista)
    {
        Indices=new String [Lista.size()];
       // colors=new int [Lista.size()];
        for(int i=0;i<Lista.size();i++)
        {
            Indices[i]=Lista.get(i).getVariable();
           // colors[i]=Color.BLUE;
        }

    }
*/


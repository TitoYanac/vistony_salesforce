package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.vistony.salesforce.Entity.Adapters.ListaComisionesDetalleEntity;
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

    public ListaComisionesDetalleAdapter(Context context, List<ListaComisionesDetalleEntity> objects) {
        super(context, 0, objects);
        ArraylistaComisionesDetalleEntity = objects;
        this.context = context;
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
            holder.v_gradiente_comisiones_detalle = (View) convertView.findViewById(R.id.v_gradiente_comisiones_detalle);
            //holder.barChartHorizonalComisionesDetalle = (BarChart) convertView.findViewById(R.id.barChartHorizonalComisionesDetalle);
            convertView.setTag(holder);
        } else {
            holder = (ListaComisionesDetalleAdapter.ViewHolder) convertView.getTag();
        }
        // Lead actual.
        final ListaComisionesDetalleEntity lead = getItem(position);
        // Setup.
        holder.tv_comisionesdetalle_variable.setText(lead.getVariable());
        holder.tv_comisionesdetalle_cuota.setText(lead.getCuota());
        holder.tv_comisionesdetalle_avance.setText(lead.getAvance());
        holder.tv_comisionesdetalle_porcentaje.setText(lead.getPorcentajeavance());
        ArrayList<ListaComisionesDetalleEntity> listaComisionesDetalleEntities = new ArrayList<>();
        listaComisionesDetalleEntities.add(lead);



        Drawable gradiente;
        if (Float.parseFloat(lead.getPorcentajeavance()) >= 0 && Float.parseFloat(lead.getPorcentajeavance()) <= 30)
        {

            int h = holder.v_gradiente_comisiones_detalle.getHeight();
            ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
            mDrawable.getPaint().setShader(new LinearGradient(0, 0, 0, h, Color.parseColor("#d50000"), Color.parseColor("#ff5722"), Shader.TileMode.REPEAT));
            holder.v_gradiente_comisiones_detalle.setBackgroundDrawable(mDrawable);

            gradiente = ResourcesCompat.getDrawable(getContext().getResources() , R.drawable.linea_rectangular_gradiente_muy_bajo, null);
            //holder.v_gradiente_comisiones_detalle.setBackground(gradiente);

        } else if (Float.parseFloat(lead.getPorcentajeavance()) >= 31 && Float.parseFloat(lead.getPorcentajeavance()) <= 60)
        {
            gradiente = ResourcesCompat.getDrawable(getContext().getResources() , R.drawable.linea_rectangular_gradiente_bajo, null);
            holder.v_gradiente_comisiones_detalle.setBackground(gradiente);

        } else if (Float.parseFloat(lead.getPorcentajeavance()) >= 61 && Float.parseFloat(lead.getPorcentajeavance()) <= 85)
        {
            gradiente = ResourcesCompat.getDrawable(getContext().getResources() , R.drawable.linea_rectangular_gradiente_medio, null);
            holder.v_gradiente_comisiones_detalle.setBackground(gradiente);

        } else if (Float.parseFloat(lead.getPorcentajeavance()) >= 86 && Float.parseFloat(lead.getPorcentajeavance()) <= 99)
        {
            gradiente = ResourcesCompat.getDrawable(getContext().getResources() , R.drawable.linea_rectangular_gradiente_alto, null);
            holder.v_gradiente_comisiones_detalle.setBackground(gradiente);
        } else if (Float.parseFloat(lead.getPorcentajeavance()) > 99)
        {
            gradiente = ResourcesCompat.getDrawable(getContext().getResources() , R.drawable.linea_rectangular_gradiente_muy_alto, null);
            holder.v_gradiente_comisiones_detalle.setBackground(gradiente);
        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv_comisionesdetalle_variable;
        TextView tv_comisionesdetalle_cuota;
        TextView tv_comisionesdetalle_avance;
        TextView tv_comisionesdetalle_porcentaje;
        View v_gradiente_comisiones_detalle;
        //BarChart barChartHorizonalComisionesDetalle;

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


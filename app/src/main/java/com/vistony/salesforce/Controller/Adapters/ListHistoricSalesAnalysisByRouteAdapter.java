package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Dao.SQLite.EscColoursDSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursDEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesOrderTraceabilityEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.HistoricSalesAnalysisByRoute;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListHistoricSalesAnalysisByRouteAdapter extends ArrayAdapter<HistoricSalesAnalysisByRouteEntity> {
    private android.content.Context Context;
    private List<HistoricSalesAnalysisByRouteEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<HistoricSalesAnalysisByRouteEntity> arrayList;
    ListaHistoricoFacturasLineasNoFacturadasAdapter listaHistoricoFacturasLineasNoFacturadasAdapter;
    private ProgressDialog pd;
    ListaHistoricoFacturasHistorialDespachoAdapter listaHistoricoFacturasHistorialDespachoAdapter;

    public ListHistoricSalesAnalysisByRouteAdapter(android.content.Context context, List<HistoricSalesAnalysisByRouteEntity> objects) {

        super(context, 0, objects);
        Context=context;
        this.Listanombres= objects;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<HistoricSalesAnalysisByRouteEntity>();
        this.arrayList.addAll(objects);

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
            for(HistoricSalesAnalysisByRouteEntity wp: arrayList)
            {
                if(wp.getClase_comercial().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    Listanombres.add(wp);
                }
                else if(wp.getCliente_id().toLowerCase(Locale.getDefault()).contains(charText))
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
    public HistoricSalesAnalysisByRouteEntity getItem(int position) {
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

        final ListHistoricSalesAnalysisByRouteAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_list_historic_sales_analysis_by_route,
                    parent,
                    false);

            holder = new ListHistoricSalesAnalysisByRouteAdapter.ViewHolder();
            holder.tv_class_commercial = (TextView) convertView.findViewById(R.id.tv_class_commercial);
            holder.tv_indicator_month_before = (TextView) convertView.findViewById(R.id.tv_indicator_month_before);
            holder.tv_indicator_biannual = (TextView) convertView.findViewById(R.id.tv_indicator_biannual);
            //holder.tv_gal_anio_actual_period_actual = (TextView) convertView.findViewById(R.id.tv_gal_anio_actual_period_actual);
            holder.imv_show_more = (ImageView) convertView.findViewById(R.id.imv_show_more);
            holder.layout = (ViewGroup) convertView.findViewById(R.id.content);
            holder.v_gradiente_historic_sales_analysis_by_route = (View) convertView.findViewById(R.id.v_gradiente_historic_sales_analysis_by_route);
            holder.cv_comision_historic_sales_analysis_by_route = (CardView) convertView.findViewById(R.id.cv_comision_historic_sales_analysis_by_route);
            holder.tv_quota_percent = (TextView) convertView.findViewById(R.id.tv_quota_percent);
            //holder.imv_detail = (ImageView) convertView.findViewById(R.id.imv_detail);
            holder.imv_detail_mes_anterior = (ImageView) convertView.findViewById(R.id.imv_detail_mes_anterior);
            holder.imv_detail_semestral = (ImageView) convertView.findViewById(R.id.imv_detail_semestral);
            holder.imv_detail_cuota = (ImageView) convertView.findViewById(R.id.imv_detail_cuota);
            holder.imv_alert_detail_mes_anterior = (ImageView) convertView.findViewById(R.id.imv_alert_detail_mes_anterior);
            holder.imv_alert_detail_semestral = (ImageView) convertView.findViewById(R.id.imv_alert_detail_semestral);
            holder.imv_alert_detail_cuota = (ImageView) convertView.findViewById(R.id.imv_alert_detail_cuota);
            holder.tv_month_current = (TextView) convertView.findViewById(R.id.tv_month_current);

            convertView.setTag(holder);
        } else {
            holder = (ListHistoricSalesAnalysisByRouteAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricSalesAnalysisByRouteEntity lead = getItem(position);

        // Setup.
        holder.tv_class_commercial.setText(lead.getClase_comercial());
        //holder.tv_gal_anio_actual_period_actual.setText(Convert.numberForView2(lead.getGalanioactualperiodoactual()));
        holder.tv_indicator_biannual.setText(Convert.numberForView(lead.getProm()));
        holder.tv_quota_percent.setText(Convert.numberForView(lead.getPorcentajeavancecuota()));
        holder.tv_indicator_month_before.setText(Convert.numberForView(lead.getProm2122()));
        holder.tv_month_current.setText(Convert.numberForView2(lead.getGalanioactualperiodoactual()));
        holder.layout.removeAllViews();
        if(Float.parseFloat(lead.getProm())>=0&&Float.parseFloat(lead.getProm())<80)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_dangerous_24);
            holder.imv_alert_detail_semestral.setImageDrawable(draw);
            holder.imv_alert_detail_semestral.setEnabled(false);
        }
        else if(Float.parseFloat(lead.getProm())>=80&&Float.parseFloat(lead.getProm())<100)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
            holder.imv_alert_detail_semestral.setImageDrawable(draw);
            holder.imv_alert_detail_semestral.setEnabled(false);
        }
        else if(Float.parseFloat(lead.getProm())>=100)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_circle_24);
            holder.imv_alert_detail_semestral.setImageDrawable(draw);
            holder.imv_alert_detail_semestral.setEnabled(false);
        }

        if(Float.parseFloat(lead.getProm2122())>=0&&Float.parseFloat(lead.getProm2122())<80)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_dangerous_24);
            holder.imv_alert_detail_mes_anterior.setImageDrawable(draw);
            holder.imv_alert_detail_mes_anterior.setEnabled(false);
        }
        else if(Float.parseFloat(lead.getProm2122())>=80&&Float.parseFloat(lead.getProm2122())<100)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
            holder.imv_alert_detail_mes_anterior.setImageDrawable(draw);
            holder.imv_alert_detail_mes_anterior.setEnabled(false);
        }
        else if(Float.parseFloat(lead.getProm2122())>=100)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_circle_24);
            holder.imv_alert_detail_mes_anterior.setImageDrawable(draw);
            holder.imv_alert_detail_mes_anterior.setEnabled(false);
        }

        if(Float.parseFloat(lead.getPorcentajeavancecuota())>=0&&Float.parseFloat(lead.getPorcentajeavancecuota())<80)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_dangerous_24);
            holder.imv_alert_detail_cuota.setImageDrawable(draw);
            holder.imv_alert_detail_cuota.setEnabled(false);
        }
        else if(Float.parseFloat(lead.getPorcentajeavancecuota())>=80&&Float.parseFloat(lead.getPorcentajeavancecuota())<100)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
            holder.imv_alert_detail_cuota.setImageDrawable(draw);
            holder.imv_alert_detail_cuota.setEnabled(false);
        }
        else if(Float.parseFloat(lead.getPorcentajeavancecuota())>=100)
        {
            Resources res = getContext().getResources(); // need this to fetch the drawable
            Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_circle_24);
            holder.imv_alert_detail_cuota.setImageDrawable(draw);
            holder.imv_alert_detail_cuota.setEnabled(false);
        }
        /*if(lead.getGalanioactualperiodoactual().equals("0.0")||lead.getGalanioanteriorperiodoactual().equals("0.0"))
        {
            lead.setProm2122("0");
        }
        else {
            lead.setProm2122(String.valueOf((Float.parseFloat(lead.getGalanioactualperiodoactual()) / Float.parseFloat(lead.getGalanioanteriorperiodoactual())) * 100));
        }*/

        //holder.tv_gal_anio_actual_period_actual_2.setText(lead.getGalanioactual1periodoanterior());
        //holder.tv_gal_promedio_trimestre_anio_anterior.setText(lead.getPromediotrimestreanioanterior());
        //holder.tv_gal_promedio_trimestre_anio_anterior_percent.setText(lead.getProm());
        //holder.tv_gal_anio_actual_period_actual_3.setText(lead.getGalanioactual2periodoAnterior());
        //holder.tv_month_before_year_before.setText(lead.getGalanioanteriorperiodoactual());

        ArrayList<EscColoursDEntity> escColoursDEntityArrayList=new ArrayList<>();
        EscColoursDSQLiteDao escColoursDSQLiteDao=new EscColoursDSQLiteDao(getContext());
        escColoursDEntityArrayList=escColoursDSQLiteDao.GetEscColours(
                "001",
                Float.parseFloat(lead.getProm2122())
        );
        Log.e("REOS","ListaComisionesDetalleAdapter-getView-escColoursDEntityArrayList.size():"+escColoursDEntityArrayList.size());
        Log.e("REOS","ListaComisionesDetalleAdapter-getView-lead.getClase_comercial()-lead.getGalanioactualperiodoactual():"+lead.getClase_comercial()+"-"+lead.getGalanioactualperiodoactual());
        for(int k=0;k<escColoursDEntityArrayList.size();k++)
        {
            GradientDrawable layer3 = new GradientDrawable();
            layer3.setShape(GradientDrawable.RECTANGLE);
            layer3.setGradientType(GradientDrawable.LINEAR_GRADIENT);

            //layer3.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(k).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(k).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
            layer3.setColors(new int[] { Color.GRAY,Color.GRAY}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
            holder.v_gradiente_historic_sales_analysis_by_route.setBackgroundDrawable(layer3);
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
            holder.cv_comision_historic_sales_analysis_by_route.setBackground(layerDrawable);


        }

        holder.imv_show_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.layout.getChildCount()==0)
                {
                    for(int i=0;i<HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.size();i++) {
                        if (lead.getClase_comercial().equals(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getClase_comercial())) {
                            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
                            int id = R.layout.layout_list_historic_sales_analysis_by_route_client;
                            RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(id, null, false);
                            TextView tv_class_commercial = (TextView) relativeLayout.findViewById(R.id.tv_class_commercial);
                            TextView tv_indicator_month_before = (TextView) relativeLayout.findViewById(R.id.tv_indicator_month_before);
                            TextView tv_indicator_biannual = (TextView) relativeLayout.findViewById(R.id.tv_indicator_biannual);
                            CardView cv_sales_analysis_by_route_client = (CardView)  relativeLayout.findViewById(R.id.cv_sales_analysis_by_route_client);
                            ImageView imv_detail_mes_anterior = (ImageView) relativeLayout.findViewById(R.id.imv_detail_mes_anterior);
                            ImageView imv_detail_semestral = (ImageView) relativeLayout.findViewById(R.id.imv_detail_semestral);
                            ImageView imv_alert_detail_mes_anterior = (ImageView) relativeLayout.findViewById(R.id.imv_alert_detail_mes_anterior);
                            ImageView imv_alert_detail_semestral = (ImageView) relativeLayout.findViewById(R.id.imv_alert_detail_semestral);
                            TextView tv_month_current = (TextView) relativeLayout.findViewById(R.id.tv_month_current);

                            tv_class_commercial.setText(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getCardname());
                            tv_indicator_biannual.setText(Convert.numberForView(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm()));
                            tv_indicator_month_before.setText(Convert.numberForView(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122()));
                            tv_month_current.setText(Convert.numberForView2(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getGalanioactualperiodoactual()));

                            holder.layout.addView(relativeLayout);



                            if(Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm())>=0&&Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm())<80)
                            {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable( R.drawable.ic_baseline_dangerous_24);
                                imv_alert_detail_semestral.setImageDrawable(draw);
                                imv_alert_detail_semestral.setEnabled(false);
                            }
                            else if(Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm())>=80&&Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm())<100)
                            {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
                                imv_alert_detail_semestral.setImageDrawable(draw);
                                imv_alert_detail_semestral.setEnabled(false);
                            }
                            else if(Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm())>=100)
                            {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_circle_24);
                                imv_alert_detail_semestral.setImageDrawable(draw);
                                imv_alert_detail_semestral.setEnabled(false);
                            }

                            if(Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122())>=0&&Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122())<80)
                            {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable( R.drawable.ic_baseline_dangerous_24);
                                imv_alert_detail_mes_anterior.setImageDrawable(draw);
                                imv_alert_detail_mes_anterior.setEnabled(false);
                            }
                            else if(Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122())>=80&&Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122())<100)
                            {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable( R.drawable.ic_baseline_warning_24);
                                imv_alert_detail_mes_anterior.setImageDrawable(draw);
                                imv_alert_detail_mes_anterior.setEnabled(false);
                            }
                            else if(Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122())>=100)
                            {
                                Resources res = getContext().getResources(); // need this to fetch the drawable
                                Drawable draw = res.getDrawable( R.drawable.ic_baseline_check_circle_24);
                                imv_alert_detail_mes_anterior.setImageDrawable(draw);
                                imv_alert_detail_mes_anterior.setEnabled(false);
                            }


                            imv_detail_mes_anterior.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*getalertFormulas("Comparativo Año Anterior Mes Anterior","Mes Actual","Año Mes Anterior"
                                            ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getGalanioactualperiodoactual()
                                            ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getGalanioanteriorperiodoactual()
                                            ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122()).show();*/

                                    Toast.makeText(getContext(), "Prueba imv_detail_mes_anterior", Toast.LENGTH_SHORT).show();
                                    for(int j=0;j<HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.size();j++) {
                                        if(lead.clase_comercial.equals(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getClase_comercial())){
                                            if(tv_class_commercial.getText().equals(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getCardname()))
                                            {
                                                getalertFormulas("Comparativo Año Anterior Mes Anterior","Mes Actual","Año Mes Anterior"
                                                 ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getGalanioactualperiodoactual()
                                                 ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getGalanioanteriorperiodoactual()
                                                 ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getProm2122()).show();
                                            }
                                        }
                                    }
                                }
                            });
                            imv_detail_semestral.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    /*getalertFormulas("Comparativo Semestre Actual vs Semestre Anterior","Semestre Actual","Semestre Año Anterior"
                                            ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getPromediotrimestreanioactual()
                                            ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getPromediotrimestreanioanterior()
                                            ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm()
                                    ).show();*/
                                    Toast.makeText(getContext(), "Prueba imv_detail_semestral", Toast.LENGTH_SHORT).show();
                                    for(int j=0;j<HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.size();j++) {
                                        if(lead.clase_comercial.equals(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getClase_comercial())){
                                            if(tv_class_commercial.getText().equals(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getCardname()))
                                            {
                                            getalertFormulas("Comparativo Semestre Actual vs Semestre Anterior","Semestre Actual","Semestre Año Anterior"
                                                    ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getPromediotrimestreanioactual()
                                                    ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getPromediotrimestreanioanterior()
                                                    ,HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(j).getProm()
                                            ).show();
                                            }
                                        }
                                    }
                                }
                            });

                            ArrayList<EscColoursDEntity> escColoursDEntityArrayList=new ArrayList<>();
                            EscColoursDSQLiteDao escColoursDSQLiteDao=new EscColoursDSQLiteDao(getContext());
                            escColoursDEntityArrayList=escColoursDSQLiteDao.GetEscColours(
                                    "001",
                                    Float.parseFloat(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122())
                            );
                            Log.e("REOS","ListaComisionesDetalleAdapter-getView-escColoursDEntityArrayList.size():"+escColoursDEntityArrayList.size());
                            for(int l=0;l<escColoursDEntityArrayList.size();l++)
                            {
                                GradientDrawable layer3 = new GradientDrawable();
                                layer3.setShape(GradientDrawable.RECTANGLE);
                                layer3.setGradientType(GradientDrawable.LINEAR_GRADIENT);

                                //layer3.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(l).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(l).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
                                layer3.setColors(new int[] {  Color.GRAY, Color.GRAY}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),

                                //holder.v_gradiente_comisiones_detalle.setBackgroundDrawable(layer3);
                                GradientDrawable layer1 = new GradientDrawable();
                                layer1.setShape(GradientDrawable.RECTANGLE);
                                layer1.setSize(33,33);
                                layer1.setColor(Color.WHITE);
                                layer1.setCornerRadius(20);

                                GradientDrawable layer2 = new GradientDrawable();
                                layer2.setCornerRadius(20);
                                //layer1.setShape(GradientDrawable.RECTANGLE);
                                //layer2.setColors(new int[] { Color.parseColor(escColoursDEntityArrayList.get(l).getColourmin()),Color.parseColor(escColoursDEntityArrayList.get(l).getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),
                                layer2.setColors(new int[] { Color.GRAY,Color.GRAY}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),

                                InsetDrawable insetLayer2 = new InsetDrawable(layer1, 8, 8, 8, 8);

                                LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]
                                        {layer2,insetLayer2});
                                cv_sales_analysis_by_route_client.setBackground(layerDrawable);


                            }


                        }
                    }

                }
                else {
                    //holder.layout.clearDisappearingChildren();
                    //holder.layout.clearFocus();
                    //holder.layout.clearFocus();
                    //holder.layout.clearDisappearingChildren();
                    //holder.layout.clearChildFocus(holder.layout.getFocusedChild());
                    //holder.layout.clearAnimation();
                    holder.layout.removeAllViews();
                }

            }
        });

        holder.imv_detail_mes_anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getalertFormulas("Comparativo Año Anterior Mes Anterior","Mes Actual","Año Mes Anterior",lead.getGalanioactualperiodoactual(),lead.getGalanioanteriorperiodoactual(),lead.getProm2122()).show();
            }
        });
        holder.imv_detail_semestral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getalertFormulas("Comparativo Semestre Actual vs Semestre Anterior","Semestre Actual","Semestre Año Anterior",lead.getPromediotrimestreanioactual(),lead.getPromediotrimestreanioanterior(),lead.getProm()).show();
            }
        });
        holder.imv_detail_cuota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getalertFormulas("Comparativo con Cuota","Mes Actual","Cuota",lead.getGalanioactualperiodoactual(),lead.getCuota(),lead.getPorcentajeavancecuota()).show();
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tv_class_commercial;
        TextView tv_indicator_month_before;
        TextView tv_indicator_biannual;
        TextView tv_quota_percent;
        TextView tv_gal_anio_actual_period_actual;
        ImageView imv_show_more;
        ViewGroup layout;
        View v_gradiente_historic_sales_analysis_by_route;
        CardView cv_comision_historic_sales_analysis_by_route;
        ImageView imv_detail_mes_anterior;
        ImageView imv_detail_semestral;
        ImageView imv_detail_cuota;
        ImageView imv_alert_detail_mes_anterior;
        ImageView imv_alert_detail_semestral;
        ImageView imv_alert_detail_cuota;
        TextView tv_month_current;
    }

    private Dialog getalertFormulas(String formula,String lblvariable1,String lblvariable2,String tvvariable1,String tvvariable2,String tvresultado) {
        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_dialog_list_formula);

        TextView textTitle = dialog.findViewById(R.id.tv_titulo);
        textTitle.setText("IMPORTANTE!!!");
        TextView textMsj = dialog.findViewById(R.id.tv_subtitulo);
        textMsj.setText(formula);
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        TextView lbl_variable1 = dialog.findViewById(R.id.lbl_variable1);
        TextView lbl_variable2 = dialog.findViewById(R.id.lbl_variable2);
        TextView tv_variable1 = dialog.findViewById(R.id.tv_variable1);
        TextView tv_variable2 = dialog.findViewById(R.id.tv_variable2);
        TextView tv_resultado = dialog.findViewById(R.id.tv_resultado);

        lbl_variable1.setText(lblvariable1);
        lbl_variable2.setText(lblvariable2);
        tv_variable1.setText(Convert.numberForView2(tvvariable1));
        tv_variable2.setText(Convert.numberForView2(tvvariable2));
        tv_resultado.setText(Convert.numberForView(tvresultado) );

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

package com.vistony.salesforce.Controller.Adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.kofigyan.stateprogressbar.StateProgressBar;
import com.vistony.salesforce.BuildConfig;
import com.vistony.salesforce.Controller.Utilitario.Convert;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
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
            holder.tv_gal_anio_actual_period_actual = (TextView) convertView.findViewById(R.id.tv_gal_anio_actual_period_actual);
            holder.tv_gal_anio_actual_period_actual_quote = (TextView) convertView.findViewById(R.id.tv_gal_anio_actual_period_actual_quote);
            holder.tv_gal_anio_actual_period_actual_advance = (TextView) convertView.findViewById(R.id.tv_gal_anio_actual_period_actual_advance);
            //holder.tv_gal_anio_actual_period_actual_2 = (TextView) convertView.findViewById(R.id.tv_gal_anio_actual_period_actual_2);
            //holder.tv_gal_promedio_trimestre_anio_anterior = (TextView) convertView.findViewById(R.id.tv_gal_promedio_trimestre_anio_anterior);
            //holder.tv_gal_promedio_trimestre_anio_anterior_percent = (TextView) convertView.findViewById(R.id.tv_gal_promedio_trimestre_anio_anterior_percent);
            //holder.tv_gal_anio_actual_period_actual_3 = (TextView) convertView.findViewById(R.id.tv_gal_anio_actual_period_actual_3);
            //holder.tv_month_before_year_before = (TextView) convertView.findViewById(R.id.tv_month_before_year_before);
            holder.imv_show_more = (ImageView) convertView.findViewById(R.id.imv_show_more);
            holder.layout=(ViewGroup) convertView.findViewById(R.id.content);
            convertView.setTag(holder);
        } else {
            holder = (ListHistoricSalesAnalysisByRouteAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final HistoricSalesAnalysisByRouteEntity lead = getItem(position);

        // Setup.
        holder.tv_class_commercial.setText(lead.getClase_comercial());
        holder.tv_gal_anio_actual_period_actual.setText(lead.getGalanioactualperiodoactual());
        holder.tv_gal_anio_actual_period_actual_quote.setText(lead.getGalanioanteriorperiodoactual());
        holder.tv_gal_anio_actual_period_actual_advance.setText(lead.getPorcentajeavancecuota());
        //holder.tv_gal_anio_actual_period_actual_2.setText(lead.getGalanioactual1periodoanterior());
        //holder.tv_gal_promedio_trimestre_anio_anterior.setText(lead.getPromediotrimestreanioanterior());
        //holder.tv_gal_promedio_trimestre_anio_anterior_percent.setText(lead.getProm());
        //holder.tv_gal_anio_actual_period_actual_3.setText(lead.getGalanioactual2periodoAnterior());
        //holder.tv_month_before_year_before.setText(lead.getGalanioanteriorperiodoactual());


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
                            TextView tv_gal_anio_actual_period_actual_quote = (TextView) relativeLayout.findViewById(R.id.tv_gal_anio_actual_period_actual_quote);
                            TextView tv_gal_anio_actual_period_actual = (TextView) relativeLayout.findViewById(R.id.tv_gal_anio_actual_period_actual);
                            TextView tv_gal_anio_actual_period_actual_advance = (TextView) relativeLayout.findViewById(R.id.tv_gal_anio_actual_period_actual_advance);
                            tv_class_commercial.setText(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getCardname());
                            tv_gal_anio_actual_period_actual_quote.setText(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getGalanioanteriorperiodoactual());
                            tv_gal_anio_actual_period_actual.setText(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getGalanioactualperiodoactual());
                            tv_gal_anio_actual_period_actual_advance.setText(HistoricSalesAnalysisByRoute.historicSalesAnalysisByRouteEntityList.get(i).getProm2122());
                            holder.layout.addView(relativeLayout);
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


        return convertView;
    }

    static class ViewHolder {
        TextView tv_class_commercial;
        TextView tv_gal_anio_actual_period_actual;
        TextView tv_gal_anio_actual_period_actual_quote;
        TextView tv_gal_anio_actual_period_actual_advance;
        TextView tv_gal_anio_actual_period_actual_2;
        TextView tv_gal_promedio_trimestre_anio_anterior;
        TextView tv_gal_promedio_trimestre_anio_anterior_percent;
        TextView tv_gal_anio_actual_period_actual_3;
        TextView tv_month_before_year_before;
        ImageView imv_show_more;
        ViewGroup layout;
    }
}

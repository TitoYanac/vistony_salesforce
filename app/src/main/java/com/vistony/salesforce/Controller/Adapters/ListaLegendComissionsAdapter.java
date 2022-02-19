package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Log;
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

import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursDEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.AgenciaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListaLegendComissionsAdapter extends ArrayAdapter<EscColoursDEntity> {

    public static ArrayList<ListaAgenciaEntity> araylistaAgenciaEntity;
    private android.content.Context Context;
    LayoutInflater inflater;

    public ListaLegendComissionsAdapter (android.content.Context context, List<EscColoursDEntity> objects) {

        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaAgenciaEntity= new ArrayList <ListaAgenciaEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaLegendComissionsAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_leyenda_comisiones_detalle,
                    parent,
                    false);

            holder = new ListaLegendComissionsAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            holder.tv_range_min = (TextView) convertView.findViewById(R.id.tv_range_min);
            holder.tv_range_max = (TextView) convertView.findViewById(R.id.tv_range_max);
            holder.tv_circulo = (TextView) convertView.findViewById(R.id.tv_circulo);

            holder.relativeListaAgencia=convertView.findViewById(R.id.relativeListaAgencia);
            convertView.setTag(holder);
        } else {
            holder = (ListaLegendComissionsAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final EscColoursDEntity lead = getItem(position);

        // Setup.
        Log.e("REOS", "ListaLegendComissionsAdapter-getView-lead.getId():" + lead.getId());
        Log.e("REOS", "ListaLegendComissionsAdapter-getView-lead.getRangemin():" + lead.getRangemin());
        Log.e("REOS", "ListaLegendComissionsAdapter-getView-lead.getRangemax():" + lead.getRangemax());
        holder.tv_id.setText(lead.getId());
        holder.tv_range_min.setText(lead.getRangemin());
        holder.tv_range_max.setText(lead.getRangemax());

        /*GradientDrawable layer1 = new GradientDrawable();
        layer1.setShape(GradientDrawable.RING);
        layer1.setSize(33,33);
        layer1.setColor(Color.WHITE);
        layer1.setCornerRadius(20);*/

        GradientDrawable layer2 = new GradientDrawable();
        layer2.setCornerRadius(20);
        //layer1.setShape(GradientDrawable.RECTANGLE);
        layer2.setShape(GradientDrawable.RECTANGLE);
        layer2.setColors(new int[] { Color.parseColor(lead.getColourmin()),Color.parseColor(lead.getColourmax())}); // please input your color from resource for color-4 getResources().getColor(R.color.color2),

      //  InsetDrawable insetLayer2 = new InsetDrawable(layer1, 8, 8, 8, 8);

        //LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]
        //        {layer2,insetLayer2});
        holder.tv_circulo.setBackground(layer2);
        return convertView;
    }
    static class ViewHolder {
        TextView tv_id;
        TextView tv_range_min;
        TextView tv_range_max;
        TextView tv_circulo;
        RelativeLayout relativeListaAgencia;
    }

}

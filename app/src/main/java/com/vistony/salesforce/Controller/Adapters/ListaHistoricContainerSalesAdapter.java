package com.vistony.salesforce.Controller.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.vistony.salesforce.Controller.Utilitario.Induvis;
import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPriceListEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.PriceListView;

import java.util.ArrayList;
import java.util.List;

public class ListaHistoricContainerSalesAdapter extends ArrayAdapter<ListaHistoricContainerSalesEntity> {
    public static List<ListaHistoricContainerSalesEntity> ArrayListaHistoricContainerSalesEntity = new ArrayList<ListaHistoricContainerSalesEntity>();;
    ListaParametrosEntity listaParametrosEntity;
    private Context context=getContext();
    private FragmentManager fragmentManager;
    PriceListView priceListView;
    String tipo;

    public ListaHistoricContainerSalesAdapter(Context context, List<ListaHistoricContainerSalesEntity> objects,String Tipo) {

        super( context,0, objects);
        ArrayListaHistoricContainerSalesEntity=objects;
        this.context=context;
        tipo=Tipo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //ArraylistaParametrosEntity= new ArrayList <ListaParametrosEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaHistoricContainerSalesAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            if(tipo.equals("SEMAFORO"))
            {
                Log.e("REOS","ListaHistoricContainerSalesAdapter-SEMAFORO");
            convertView = inflater.inflate(
                    R.layout.layout_lista_historic_container_sales_semaforo,
                    parent,
                    false);
             }
            else if(tipo.equals("SKU"))
            {
                Log.e("REOS","ListaHistoricContainerSalesAdapter-SKU");
                convertView = inflater.inflate(
                        R.layout.layout_lista_historic_container_sku,
                        parent,
                        false);
            }
            else
                {
                    Log.e("REOS","ListaHistoricContainerSalesAdapter-NO-SEMAFORO");
                    convertView = inflater.inflate(
                            R.layout.layout_lista_historic_container_sales_familia,
                            parent,
                            false);
                }

            holder = new ListaHistoricContainerSalesAdapter.ViewHolder();

            holder.tv_periodo = (TextView) convertView.findViewById(R.id.tv_periodo);
            holder.tv_montoacumulado = (TextView) convertView.findViewById(R.id.tv_montoacumulado);
           // holder.relativeListacontainersalesSemaforo = (RelativeLayout) convertView.findViewById(R.id.relativeListacontainersalesSemaforo);
            if(tipo.equals("SEMAFORO"))
            {

            }
            else
                {
                    holder.tv_variable = (TextView) convertView.findViewById(R.id.tv_variable);
                }
            if(tipo.equals("SKU")){
                holder.tv_galouns =  (TextView) convertView.findViewById(R.id.tv_galouns);
                holder.relativeListahistoriccontainersalessku = (RelativeLayout) convertView.findViewById(R.id.relativeListahistoriccontainersalessku);
            }

            convertView.setTag(holder);
        } else {
            holder = (ListaHistoricContainerSalesAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaHistoricContainerSalesEntity lead = getItem(position);

        // Setup.

        if(tipo.equals("SKU"))
        {
            String fecha,año,mes,dia;
            String[] sourcefechacuota= lead.getAnio().split(" ");
            fecha= sourcefechacuota[0];
            String[] sourcefechadesordenada= fecha.split("/");
            año=sourcefechadesordenada[2];
            mes=sourcefechadesordenada[0];
            dia=sourcefechadesordenada[1];

            if(mes.length()==1)
            {
                mes='0'+mes;
            }
            if(dia.length()==1)
            {
                dia='0'+dia;
            }
            holder.tv_periodo.setText(año+"/"+mes+"/"+dia);
            Induvis induvis=new Induvis();
            Long day=0L;
            try {
                day=induvis.getDiferenceDays(año +"-"+ mes +"-"+ dia);
            }catch (Exception e){
                Log.e("REOS","ListaHistoricContainerSalesAdapter.e"+e.toString());
            }
            if(day>90) {
                holder.relativeListahistoriccontainersalessku.setBackground(new ColorDrawable(Color.parseColor("#eF9a9a")));
            }
            holder.tv_galouns.setText(lead.getGaloun());
        }else
        {
            holder.tv_periodo.setText(lead.getAnio()+"-"+lead.getMes());
        }

        holder.tv_montoacumulado.setText(lead.getTotal());
        if(tipo.equals("SEMAFORO"))
        {

        }
        else
        {
            holder.tv_variable.setText(lead.getVariable());

        }


        return convertView;
    }

    static class ViewHolder {

        TextView tv_periodo;
        TextView tv_montoacumulado;
        TextView tv_variable;
        TextView tv_galouns;
        RelativeLayout relativeListacontainersalesSemaforo;
        RelativeLayout relativeListahistoriccontainersalesfamilia;
        RelativeLayout relativeListahistoriccontainersalessku;
    }

}

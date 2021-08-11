package com.vistony.salesforce.Controller.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.vistony.salesforce.Dao.SQLIte.PromocionCabeceraSQLiteDao;
import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleListaPromocionEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionCabeceraSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.R;
import com.vistony.salesforce.View.AgenciaView;

import java.util.ArrayList;
import java.util.List;

public class ListaOrdenVentaDetalleListaPromocionAdapter extends ArrayAdapter<ListaOrdenVentaDetalleListaPromocionEntity> {

    public static ArrayList<ListaAgenciaEntity> araylistaAgenciaEntity;
    private android.content.Context Context;
    private List<ListaAgenciaEntity> Listanombres =null;
    LayoutInflater inflater;
    private ArrayList<ListaAgenciaEntity> arrayList;
    ArrayList<AgenciaSQLiteEntity> listaAgenciaSQLiteEntity=new ArrayList<>();
    private FragmentManager fragmentManager;
    public AgenciaView agenciaView;

    public ListaOrdenVentaDetalleListaPromocionAdapter(android.content.Context context, List<ListaOrdenVentaDetalleListaPromocionEntity> objects) {
        super(context, 0, objects);
        Context=context;
        inflater = LayoutInflater.from(Context);
        this.arrayList=new ArrayList<ListaAgenciaEntity>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        araylistaAgenciaEntity= new ArrayList <ListaAgenciaEntity>();

        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final ListaOrdenVentaDetalleListaPromocionAdapter.ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.layout_lista_orden_venta_detalle_lista_promocion,
                    parent,
                    false);

            holder = new ListaOrdenVentaDetalleListaPromocionAdapter.ViewHolder();
            // holder.lbl_documento = (TextView) convertView.findViewById(R.id.lbl_documento);
            holder.tv_orden_venta_detalle_lista_promocion_id = (TextView) convertView.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_id);
            holder.tv_orden_venta_detalle_lista_promocion_promocion_id = (TextView) convertView.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_promocion_id);
            holder.tv_orden_venta_detalle_lista_promocion_cantidad_promocion = (TextView) convertView.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_cantidad_promocion);
            holder.tv_orden_venta_detalle_lista_promocion_preciobase = (TextView) convertView.findViewById(R.id.tv_orden_venta_detalle_lista_promocion_preciobase);
            convertView.setTag(holder);
        } else {
            holder = (ListaOrdenVentaDetalleListaPromocionAdapter.ViewHolder) convertView.getTag();
        }

        // Lead actual.
        final ListaOrdenVentaDetalleListaPromocionEntity lead = getItem(position);

        // Setup.
        holder.tv_orden_venta_detalle_lista_promocion_id.setText(lead.getOrden_venta_detalle_lista_promocion_id());
        holder.tv_orden_venta_detalle_lista_promocion_promocion_id.setText(lead.getOrden_venta_detalle_lista_promocion_promocion_id());
        holder.tv_orden_venta_detalle_lista_promocion_cantidad_promocion.setText(lead.getOrden_venta_detalle_lista_promocion_cantidad_promocion());

        ArrayList<PromocionCabeceraSQLiteEntity> listaPromocionCabeceraSQLiteEntity=new ArrayList<>();
        PromocionCabeceraSQLiteDao promocionCabeceraSQLiteDao=new PromocionCabeceraSQLiteDao(getContext());
        listaPromocionCabeceraSQLiteEntity=promocionCabeceraSQLiteDao.ObtenerPromocionCabeceraPorCodigo(
                SesionEntity.compania_id,
                SesionEntity.fuerzatrabajo_id,
                SesionEntity.usuario_id,
                lead.getOrden_venta_detalle_lista_promocion_lista_precio_id(),
                lead.getOrden_venta_detalle_lista_promocion_promocion_id()
        );
        Float calculo=0f;
        Log.e("REOS","ListaOrdenVentaDetalleListaPromocionAdapter-listaPromocionCabeceraSQLiteEntity.size(): "+listaPromocionCabeceraSQLiteEntity.size());
        Log.e("REOS","ListaOrdenVentaDetalleListaPromocionAdapter-lead.getOrden_venta_detalle_lista_promocion_preciobase(): "+lead.getOrden_venta_detalle_lista_promocion_preciobase());
        Log.e("REOS","ListaOrdenVentaDetalleListaPromocionAdapter-lead.getOrden_venta_detalle_lista_promocion_cantidad_promocion(): "+lead.getOrden_venta_detalle_lista_promocion_cantidad_promocion());
        for(int i=0;i<listaPromocionCabeceraSQLiteEntity.size();i++)
        {
            //calculo=Float.parseFloat(listaPromocionCabeceraSQLiteEntity.get(i).getTotal_preciobase())*Float.parseFloat(lead.getOrden_venta_detalle_lista_promocion_cantidad_promocion());
            calculo=Float.parseFloat(lead.getOrden_venta_detalle_lista_promocion_preciobase())*Float.parseFloat(lead.getOrden_venta_detalle_lista_promocion_cantidad_promocion());

        }
        holder.tv_orden_venta_detalle_lista_promocion_preciobase.setText(String.valueOf(calculo));
        return convertView;
    }

    static class ViewHolder {
        TextView tv_orden_venta_detalle_lista_promocion_id;
        TextView tv_orden_venta_detalle_lista_promocion_promocion_id;
        TextView tv_orden_venta_detalle_lista_promocion_cantidad_promocion;
        TextView tv_orden_venta_detalle_lista_promocion_preciobase;
    }

}

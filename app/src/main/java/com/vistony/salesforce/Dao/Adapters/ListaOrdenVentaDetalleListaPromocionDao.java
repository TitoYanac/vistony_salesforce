package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleListaPromocionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaOrdenVentaDetalleListaPromocionDao {
    Context context;
    public static ListaOrdenVentaDetalleListaPromocionDao repository = new ListaOrdenVentaDetalleListaPromocionDao();
    public HashMap<String, ListaOrdenVentaDetalleListaPromocionEntity> leads = new HashMap<>();

    public static ListaOrdenVentaDetalleListaPromocionDao getInstance() {
        return repository;
    }
    public ListaOrdenVentaDetalleListaPromocionDao()
    {

    }
    private void saveLead(ListaOrdenVentaDetalleListaPromocionEntity lead) {
        leads.put(lead.getOrden_venta_detalle_lista_promocion_id(), lead);
    }

    public List<ListaOrdenVentaDetalleListaPromocionEntity> getLeads(ArrayList<ListaPromocionCabeceraEntity> Lista) {
//        Lista.size();
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaOrdenVentaDetalleListaPromocionEntity(
                        String.valueOf(i+1),
                        Lista.get(i).getPromocion_id(),
                        Lista.get(i).getCantidadpromocion(),
                        Lista.get(i).getPreciobase(),
                        Lista.get(i).getLista_promocion_id()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

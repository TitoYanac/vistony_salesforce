package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEditarEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPromocionDetalleEditarDao {
    Context context;
    public static ListaPromocionDetalleEditarDao repository = new ListaPromocionDetalleEditarDao();
    public HashMap<String, ListaPromocionDetalleEditarEntity> leads = new HashMap<>();
    public static ListaPromocionDetalleEditarDao getInstance() {
        return repository;
    }

    public ListaPromocionDetalleEditarDao()
    {

    }

    private void saveLead(ListaPromocionDetalleEditarEntity lead) {
        leads.put(lead.getId(), lead);
    }

    public List<ListaPromocionDetalleEditarEntity> getLeads(ArrayList<ListaPromocionCabeceraEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                for(int j=0;j<Lista.get(i).getListaPromocionDetalleEntities().size();j++)
                {
                    saveLead(new ListaPromocionDetalleEditarEntity(
                            Lista.get(i).getListaPromocionDetalleEntities().get(j).getPromocion_detalle_id()
                            ,Lista.get(i).getListaPromocionDetalleEntities().get(j).getProducto_id()
                            ,Lista.get(i).getListaPromocionDetalleEntities().get(j).getProducto()
                            ,Lista.get(i).getListaPromocionDetalleEntities().get(j).getUmd()
                            ,Lista.get(i).getListaPromocionDetalleEntities().get(j).getCantidad()
                            ,Lista.get(i).getListaPromocionDetalleEntities().get(j).getCantidad()
                            ,true
                    ));
                }

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

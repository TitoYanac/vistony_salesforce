package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasLineasNoFacturadasEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricoFacturasLineasNoFacturadasDao {
    Context context;
    public static ListaHistoricoFacturasLineasNoFacturadasDao repository = new ListaHistoricoFacturasLineasNoFacturadasDao();
    public HashMap<String, ListaHistoricoFacturasLineasNoFacturadasEntity> leads = new HashMap<>();

    public static ListaHistoricoFacturasLineasNoFacturadasDao getInstance() {
        return repository;
    }

    public ListaHistoricoFacturasLineasNoFacturadasDao()
    {

    }

    private void saveLead(ListaHistoricoFacturasLineasNoFacturadasEntity lead) {
        leads.put(lead.getItem_ov(), lead);
    }

    public List<ListaHistoricoFacturasLineasNoFacturadasEntity> getLeads(ArrayList<ListaHistoricoFacturasLineasNoFacturadasEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaHistoricoFacturasLineasNoFacturadasEntity(
                        Lista.get(i).getItem_ov()
                        ,Lista.get(i).getProducto()
                        ,Lista.get(i).getUmd()
                        ,Lista.get(i).getCantidad()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

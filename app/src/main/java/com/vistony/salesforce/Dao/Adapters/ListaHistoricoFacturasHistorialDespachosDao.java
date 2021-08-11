package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasHistorialDespachosEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricoFacturasHistorialDespachosDao {
    Context context;
    public static ListaHistoricoFacturasHistorialDespachosDao repository = new ListaHistoricoFacturasHistorialDespachosDao();
    public HashMap<String, ListaHistoricoFacturasHistorialDespachosEntity> leads = new HashMap<>();

    public static ListaHistoricoFacturasHistorialDespachosDao getInstance() {
        return repository;
    }

    public ListaHistoricoFacturasHistorialDespachosDao()
    {

    }

    private void saveLead(ListaHistoricoFacturasHistorialDespachosEntity lead) {
        leads.put(lead.getItem(), lead);
    }

    public List<ListaHistoricoFacturasHistorialDespachosEntity> getLeads(ArrayList<ListaHistoricoFacturasHistorialDespachosEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaHistoricoFacturasHistorialDespachosEntity(
                        Lista.get(i).getItem()
                        ,Lista.get(i).getEstado()
                        ,Lista.get(i).getFecha()
                        ,Lista.get(i).getMotivo()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

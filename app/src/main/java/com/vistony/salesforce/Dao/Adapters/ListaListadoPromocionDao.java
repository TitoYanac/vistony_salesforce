package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaListadoPromocionEntity;
import com.vistony.salesforce.Entity.SQLite.ListaPromocionSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaListadoPromocionDao {
    Context context;

    public static ListaListadoPromocionDao repository = new ListaListadoPromocionDao();
    public HashMap<String, ListaListadoPromocionEntity> leads = new HashMap<>();



    public static ListaListadoPromocionDao getInstance() {
        return repository;
    }

    public ListaListadoPromocionDao()
    {

    }



    private void saveLead(ListaListadoPromocionEntity lead) {
        leads.put(lead.getId(), lead);
    }

    public List<ListaListadoPromocionEntity> getLeads(ArrayList<ListaPromocionSQLiteEntity> Lista) {

        if(!(leads==null))
        {
            leads.clear();
        }

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaListadoPromocionEntity(
                        Lista.get(i).getLista_promocion_id()
                        , Lista.get(i).getLista_promocion()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaAgenciaDao {
    Context context;

    public static ListaAgenciaDao repository = new ListaAgenciaDao();
    public HashMap<String, ListaAgenciaEntity> leads = new HashMap<>();



    public static ListaAgenciaDao getInstance() {
        return repository;
    }

    public ListaAgenciaDao()
    {

    }



    private void saveLead(ListaAgenciaEntity lead) {
        leads.put(lead.getAgencia_id(), lead);
    }

    public List<ListaAgenciaEntity> getLeads(ArrayList<AgenciaSQLiteEntity> Lista) {
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaAgenciaEntity(
                        Lista.get(i).getAgencia_id()
                        , Lista.get(i).getAgencia()
                        , Lista.get(i).getUbigeo_id()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }

}

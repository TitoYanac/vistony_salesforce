package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaDireccionClienteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaDireccionClienteDao {
    Context context;

    public static ListaDireccionClienteDao repository = new ListaDireccionClienteDao();
    public HashMap<String, ListaDireccionClienteEntity> leads = new HashMap<>();

    public static ListaDireccionClienteDao getInstance() {
        return repository;
    }

    public ListaDireccionClienteDao()
    {

    }

    private void saveLead(ListaDireccionClienteEntity lead) {
        leads.put(lead.getDomembarque_id(), lead);
    }

    public List<ListaDireccionClienteEntity> getLeads(ArrayList<ListaDireccionClienteEntity> Lista) {
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaDireccionClienteEntity(
                        Lista.get(i).getCliente_id()
                        , Lista.get(i).getDomembarque_id()
                        , Lista.get(i).getDireccion()
                        , Lista.get(i).getZona_id()
                        , Lista.get(i).getZona()
                        , Lista.get(i).getNombrefuerzatrabajo()
                        , Lista.get(i).getFuerzatrabajo_id()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

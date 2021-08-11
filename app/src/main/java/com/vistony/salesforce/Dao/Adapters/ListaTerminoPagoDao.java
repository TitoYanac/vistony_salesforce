package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaTerminoPagoEntity;
import com.vistony.salesforce.Entity.SQLite.TerminoPagoSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaTerminoPagoDao {
    Context context;

    public static ListaTerminoPagoDao repository = new ListaTerminoPagoDao();
    public HashMap<String, ListaTerminoPagoEntity> leads = new HashMap<>();



    public static ListaTerminoPagoDao getInstance() {
        return repository;
    }

    public ListaTerminoPagoDao()
    {

    }



    private void saveLead(ListaTerminoPagoEntity lead) {
        leads.put(lead.getTerminopago_id(), lead);
    }

    public List<ListaTerminoPagoEntity> getLeads(ArrayList<TerminoPagoSQLiteEntity> Lista) {
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaTerminoPagoEntity(
                        Lista.get(i).getTerminopago_id()
                        , Lista.get(i).getTerminopago()
                        , Lista.get(i).getContado()
                        ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

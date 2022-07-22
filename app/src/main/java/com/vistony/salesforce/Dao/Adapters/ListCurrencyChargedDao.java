package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListCurrencyChargedEntity;
import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListCurrencyChargedDao {

    public static ListCurrencyChargedDao repository = new ListCurrencyChargedDao();
    public HashMap<String, ListCurrencyChargedEntity> leads = new HashMap<>();
    public static ListCurrencyChargedDao getInstance() {
        return repository;
    }

    public ListCurrencyChargedDao()
    {

    }

    private void saveLead(ListCurrencyChargedEntity lead) {
        leads.put(lead.getId(), lead);
    }

    public List<ListCurrencyChargedEntity> getLeads(ArrayList<ListCurrencyChargedEntity> Lista) {
        leads.clear();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListCurrencyChargedEntity(
                          Lista.get(i).getId()
                        , Lista.get(i).getDeposit_id()
                        , Lista.get(i).getCurrency_id()
                        , Lista.get(i).getTypecurrency_id()
                        , Lista.get(i).getUnitcurrency_id()
                        , Lista.get(i).getQuantity()
                        , Lista.get(i).getAmount()
                        , Lista.get(i).isState_sp_typecurrency()
                        , Lista.get(i).isState_sp_unitcurrency()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

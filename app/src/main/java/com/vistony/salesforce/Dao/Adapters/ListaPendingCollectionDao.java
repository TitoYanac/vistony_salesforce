package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaPendingCollectionEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPriceListEntity;
import com.vistony.salesforce.Entity.SQLite.PriceListSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPendingCollectionDao {
    Context context;
    public static ListaPendingCollectionDao repository = new ListaPendingCollectionDao();
    public HashMap<String, ListaPendingCollectionEntity> leads = new HashMap<>();
    public static ListaPendingCollectionDao getInstance() {
        return repository;
    }

    public ListaPendingCollectionDao()
    {

    }
    private void saveLead(ListaPendingCollectionEntity lead) {
        leads.put(lead.getDate(), lead);
    }

    public List<ListaPendingCollectionEntity> getLeads(List<ListaPendingCollectionEntity> Lista) {
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaPendingCollectionEntity(
                        Lista.get(i).getDate()
                        ,Lista.get(i).getCount()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}
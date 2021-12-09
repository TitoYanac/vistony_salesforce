package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPriceListEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ParametrosSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.PriceListSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPriceListDao {
    Context context;
    public static ListaPriceListDao repository = new ListaPriceListDao();
    public HashMap<String, ListaPriceListEntity> leads = new HashMap<>();
    public static ListaPriceListDao getInstance() {
        return repository;
    }

    public ListaPriceListDao()
    {

    }
    private void saveLead(ListaPriceListEntity lead) {
        leads.put(lead.getPricelist_id(), lead);
    }

    public List<ListaPriceListEntity> getLeads(ArrayList<PriceListSQLiteEntity> Lista) {
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaPriceListEntity(
                         Lista.get(i).getPricelist_id()
                        ,Lista.get(i).getPriceList()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

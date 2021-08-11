package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaStockEntity;
import com.vistony.salesforce.Entity.SQLite.StockSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaStockDao {
    Context context;
    public static ListaStockDao repository = new ListaStockDao();
    public HashMap<String, ListaStockEntity> leads = new HashMap<>();

    public static ListaStockDao getInstance() {
        return repository;
    }

    public ListaStockDao()
    {

    }



    private void saveLead(ListaStockEntity lead) {
        leads.put(lead.getProducto_id()+lead.getUmd(), lead);
    }

    public List<ListaStockEntity> getLeads(ArrayList<StockSQLiteEntity> Lista) {
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaStockEntity(
                        Lista.get(i).getProducto_id()
                        ,Lista.get(i).getProducto()
                        ,Lista.get(i).getUmd()
                        ,Lista.get(i).getStock()
                        ,Lista.get(i).getAlmacen_id()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

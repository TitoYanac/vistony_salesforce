package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListKardexOfPaymentEntity;
import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListKardexOfPaymentDao {
    Context context;

    public static ListKardexOfPaymentDao repository = new ListKardexOfPaymentDao();
    public HashMap<String, ListKardexOfPaymentEntity> leads = new HashMap<>();

    public static ListKardexOfPaymentDao getInstance() {
        return repository;
    }

    public ListKardexOfPaymentDao()
    {

    }

    public void saveLead(ListKardexOfPaymentEntity lead) {
        leads.put(lead.getLegalnumber(), lead);
    }

    public List<ListKardexOfPaymentEntity> getLeads(List<ListKardexOfPaymentEntity> Lista) {
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListKardexOfPaymentEntity(
                        Lista.get(i).getLegalnumber()
                        , Lista.get(i).getInvoicedate()
                        , Lista.get(i).getDuedate()
                        , Lista.get(i).getDocAmount()
                        , Lista.get(i).getBalance()
                        , Lista.get(i).isInvoice()
                        , Lista.get(i).getPaymentterms()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

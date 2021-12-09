package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaQuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Adapters.ListaQuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerDetailEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaQuotaPerCustomerDetailDao {
    Context context;

    public static ListaQuotaPerCustomerDetailDao repository = new ListaQuotaPerCustomerDetailDao();
    public HashMap<Integer, ListaQuotasPerCustomerDetailEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaQuotaPerCustomerDetailDao getInstance() {
        return repository;
    }

    public ListaQuotaPerCustomerDetailDao()
    {

    }



    private void saveLead(ListaQuotasPerCustomerDetailEntity lead) {
        leads.put(Integer.parseInt(lead.getCuotas()), lead);
    }

    public List<ListaQuotasPerCustomerDetailEntity> getLeads(List<QuotasPerCustomerDetailEntity> Lista) {
        leads.clear();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaQuotasPerCustomerDetailEntity(
                        Lista.get(i).getCuota(),
                        Lista.get(i).getVencido(),
                        Lista.get(i).getCorriente(),
                        Lista.get(i).getPedido(),
                        Lista.get(i).getTotal(),
                        Lista.get(i).getFecha()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(leads.values());
    }

}

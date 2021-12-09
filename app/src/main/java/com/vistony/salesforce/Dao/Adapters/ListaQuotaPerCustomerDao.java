package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaQuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotasPerCustomerHeadEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaQuotaPerCustomerDao {
    Context context;

    public static ListaQuotaPerCustomerDao repository = new ListaQuotaPerCustomerDao();
    public HashMap<String, ListaQuotasPerCustomerHeadEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaQuotaPerCustomerDao getInstance() {
        return repository;
    }

    public ListaQuotaPerCustomerDao()
    {

    }



    private void saveLead(ListaQuotasPerCustomerHeadEntity lead) {
        leads.put(lead.getTramo()+lead.getCondicionpago(), lead);
    }

    public List<ListaQuotasPerCustomerHeadEntity> getLeads(List<QuotasPerCustomerHeadEntity> Lista) {
        leads.clear();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaQuotasPerCustomerHeadEntity(
                        Lista.get(i).getCondicionPago(),
                        Lista.get(i).getTramo(),
                        Lista.get(i).getSaldo(),
                        Lista.get(i).getCuotas()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(leads.values());
    }
}

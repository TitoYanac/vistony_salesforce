package com.vistony.salesforce.Dao.Adapters;

import com.vistony.salesforce.Entity.Adapters.ListCurrencyChargedEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.QuotationEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListHistoricQuotationDao {
    public static ListHistoricQuotationDao repository = new ListHistoricQuotationDao();
    public HashMap<String, QuotationEntity> leads = new HashMap<>();
    public static ListHistoricQuotationDao getInstance() {
        return repository;
    }

    public ListHistoricQuotationDao()
    {

    }

    private void saveLead(QuotationEntity lead) {
        leads.put(lead.getDocentry(), lead);
    }

    public List<QuotationEntity> getLeads(List<QuotationEntity> Lista) {
        leads.clear();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new QuotationEntity(
                        Lista.get(i).getObject()
                        , Lista.get(i).getDocnum()
                        , Lista.get(i).getDocentry()
                        , Lista.get(i).getCardcode()
                        , Lista.get(i).getLictradnum()
                        , Lista.get(i).getDocdate()
                        , Lista.get(i).getCardname()
                        , Lista.get(i).getAmounttotal()
                        , Lista.get(i).getApprovalstatus()
                        , Lista.get(i).getSlpcode()
                        , Lista.get(i).getAutorization()
                        , Lista.get(i).getReadygeneration()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

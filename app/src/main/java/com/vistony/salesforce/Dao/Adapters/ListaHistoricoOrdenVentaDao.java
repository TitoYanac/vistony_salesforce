package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricoOrdenVentaEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricoOrdenVentaDao {
    Context context;
    public static ListaHistoricoOrdenVentaDao repository = new ListaHistoricoOrdenVentaDao();
    public HashMap<String, ListaHistoricoOrdenVentaEntity> leads = new HashMap<>();

    public static ListaHistoricoOrdenVentaDao getInstance() {
        return repository;
    }

    public ListaHistoricoOrdenVentaDao()
    {

    }

    private void saveLead(ListaHistoricoOrdenVentaEntity lead) {
        leads.put(lead.getDocNum(), lead);
    }

    public List<ListaHistoricoOrdenVentaEntity> getLeads(ArrayList<ListaHistoricoOrdenVentaEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++){
                ListaHistoricoOrdenVentaEntity temp=new ListaHistoricoOrdenVentaEntity();
                       // Lista.get(i).getDocNum()
                temp.setCardCode(Lista.get(i).getCardCode());
                temp.setLicTradNum(Lista.get(i).getLicTradNum());
                temp.setCardName(Lista.get(i).getCardName());
                temp.setDocTotal(Lista.get(i).getDocTotal());
                temp.setApprovalStatus(Lista.get(i).getApprovalStatus());
                 temp.setApprovalCommentary(Lista.get(i).getApprovalCommentary());
                 temp.setSalesOrderID(Lista.get(i).getSalesOrderID());
                     //   ,Lista.get(i).isRecepcionERPOV()
                       // ,Lista.get(i).getComentariows()
                       // ,Lista.get(i).isEnvioERPOV()
                        temp.setDocNum(Lista.get(i).getDocNum());

                saveLead(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>(leads.values());
    }
}

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
        leads.put(lead.getOrdenventa_erp_id(), lead);
    }

    public List<ListaHistoricoOrdenVentaEntity> getLeads(ArrayList<ListaHistoricoOrdenVentaEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaHistoricoOrdenVentaEntity(
                        Lista.get(i).getOrdenventa_erp_id()
                        ,Lista.get(i).getCliente_id()
                        ,Lista.get(i).getRucdni()
                        ,Lista.get(i).getNombrecliente()
                        , Lista.get(i).getMontototalorden()
                        , Lista.get(i).getEstadoaprobacion()
                        , Lista.get(i).getComentarioaprobacion()
                        ,Lista.get(i).getOrdenventa_id()
                        ,Lista.get(i).isRecepcionERPOV()
                        ,Lista.get(i).getComentariows()
                        ,Lista.get(i).isEnvioERPOV()
                        ,Lista.get(i).getDocnum()

                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

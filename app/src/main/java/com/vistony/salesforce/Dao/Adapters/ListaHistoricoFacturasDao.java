package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricoFacturasEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricoFacturasDao {
    Context context;
    public static ListaHistoricoFacturasDao repository = new ListaHistoricoFacturasDao();
    public HashMap<String, ListaHistoricoFacturasEntity> leads = new HashMap<>();

    public static ListaHistoricoFacturasDao getInstance() {
        return repository;
    }

    public ListaHistoricoFacturasDao()
    {

    }

    private void saveLead(ListaHistoricoFacturasEntity lead) {
        leads.put(lead.getOrdenventa_erp_id(), lead);
    }

    public List<ListaHistoricoFacturasEntity> getLeads(ArrayList<ListaHistoricoFacturasEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaHistoricoFacturasEntity(
                        Lista.get(i).getOrdenventa_erp_id()
                        ,Lista.get(i).getMontoimporteordenventa()
                        ,Lista.get(i).getCliente_id()
                        ,Lista.get(i).getRucdni()
                        ,Lista.get(i).getNombrecliente()
                        ,Lista.get(i).getDocumento_id()
                        ,Lista.get(i).getNrofactura()
                        ,Lista.get(i).getFechaemisionfactura()
                        ,Lista.get(i).getMontoimportefactura()
                        ,Lista.get(i).getMontosaldofactura()
                        ,Lista.get(i).getNombrechofer()
                        , Lista.get(i).getFechaprogramaciondespacho()
                        , Lista.get(i).getEstadodespacho()
                        , Lista.get(i).getMotivoestadodespacho()
                        ,Lista.get(i).getTerminopago()
                        ,Lista.get(i).getTipo_factura()
                        ,Lista.get(i).getTelefonochofer()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

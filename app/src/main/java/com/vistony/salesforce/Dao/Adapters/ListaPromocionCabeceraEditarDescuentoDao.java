package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEditarEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPromocionCabeceraEditarDescuentoDao {
    Context context;
    public static ListaPromocionCabeceraEditarDescuentoDao repository = new ListaPromocionCabeceraEditarDescuentoDao();
    public HashMap<String, ListaPromocionDetalleEditarEntity> leads = new HashMap<>();
    public static ListaPromocionCabeceraEditarDescuentoDao getInstance() {
        return repository;
    }

    public ListaPromocionCabeceraEditarDescuentoDao()
    {

    }

    private void saveLead(ListaPromocionDetalleEditarEntity lead) {
        leads.put(lead.getId(), lead);
    }

    public List<ListaPromocionDetalleEditarEntity> getLeads(ArrayList<ListaPromocionCabeceraEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaPromocionDetalleEditarEntity(
                        String.valueOf(i+1)
                        //,"Descuento"
                        //,"% DESCUENTO"
                        ,"%"
                        ,"DESCUENTO"
                        ,"%"
                        ,Lista.get(i).getDescuento()
                        ,Lista.get(i).getDescuento()
                        ,true
                ));

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }

}

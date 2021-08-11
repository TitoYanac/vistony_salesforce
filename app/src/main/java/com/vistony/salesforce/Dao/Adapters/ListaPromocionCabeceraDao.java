package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionCabeceraEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPromocionCabeceraDao {
    Context context;
    public static ListaPromocionCabeceraDao repository = new ListaPromocionCabeceraDao();
    public HashMap<String, ListaPromocionCabeceraEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaPromocionCabeceraDao getInstance() {
        return repository;
    }

    public ListaPromocionCabeceraDao()
    {

    }

    private void saveLead(ListaPromocionCabeceraEntity lead) {
        leads.put(lead.getPromocion_id(), lead);
    }

    public List<ListaPromocionCabeceraEntity> getLeads(ArrayList<ListaPromocionCabeceraEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaPromocionCabeceraEntity(
                        Lista.get(i).getLista_promocion_id()
                        ,Lista.get(i).getPromocion_id()
                        ,Lista.get(i).getProducto()
                        ,Lista.get(i).getUmd()
                        , Lista.get(i).getCantidadcompra()
                        ,Lista.get(i).getCantidadpromocion()
                        ,true
                        ,Lista.get(i).getPreciobase()
                        ,Lista.get(i).getListaPromocionDetalleEntities()
                        ,Lista.get(i).getProducto_id()
                        ,Lista.get(i).getDescuento()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

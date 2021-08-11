package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaPromocionDetalleEntity;
import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaPromocionDetalleDao {
    Context context;
    public static ListaPromocionDetalleDao repository = new ListaPromocionDetalleDao();
    public HashMap<String, ListaPromocionDetalleEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaPromocionDetalleDao getInstance() {
        return repository;
    }

    public ListaPromocionDetalleDao()
    {

    }

    private void saveLead(ListaPromocionDetalleEntity lead) {
        leads.put(String.valueOf(lead.getId()), lead);
    }

    public List<ListaPromocionDetalleEntity> getLeads(ArrayList<PromocionDetalleSQLiteEntity> Lista) {
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaPromocionDetalleEntity(
                        Lista.get(i).getPromocion_detalle_id()
                        , Lista.get(i).getProducto_id()
                        , Lista.get(i).getProducto()
                        ,Lista.get(i).getUmd()
                        ,Lista.get(i).getCantidad()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

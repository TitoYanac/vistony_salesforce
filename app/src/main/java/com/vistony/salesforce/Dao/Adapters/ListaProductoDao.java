package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaProductoDao {
    Context context;

    public static ListaProductoDao repository = new ListaProductoDao();
    public HashMap<String, ListaProductoEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaProductoDao getInstance() {
        return repository;
    }

    public ListaProductoDao()
    {

    }



    private void saveLead(ListaProductoEntity lead) {
        leads.put(lead.getProducto_id()+lead.getUmd(), lead);
    }

    public List<ListaProductoEntity> getLeads(ArrayList<ListaProductoEntity> Lista) {
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaProductoEntity(
                        Lista.get(i).getProducto_item_id()
                        ,Lista.get(i).getProducto_id()
                        , Lista.get(i).getProducto()
                        ,Lista.get(i).getUmd()
                        ,Lista.get(i).getStock()
                        ,Lista.get(i).getPreciobase()
                        ,Lista.get(i).getPrecioigv()
                        ,Lista.get(i).getGal()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

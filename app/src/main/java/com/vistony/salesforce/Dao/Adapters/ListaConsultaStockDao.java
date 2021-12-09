package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaConsultaStockEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaConsultaStockDao {
    Context context;
    public static ListaConsultaStockDao repository = new ListaConsultaStockDao();
    public HashMap<String, ListaConsultaStockEntity> leads = new HashMap<>();
    public static ListaConsultaStockDao getInstance() {
        return repository;
    }
    public ListaConsultaStockDao()
    {

    }



    private void saveLead(ListaConsultaStockEntity lead) {
        leads.put(lead.getProducto_id()+lead.getUmd(), lead);
    }

    public List<ListaConsultaStockEntity> getLeads(ArrayList<ListaConsultaStockEntity> Lista) {
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                ListaConsultaStockEntity listaProductoEntity=new ListaConsultaStockEntity();
                listaProductoEntity.setProducto_item_id(Lista.get(i).getProducto_item_id());
                listaProductoEntity.setProducto_id(Lista.get(i).getProducto_id());
                listaProductoEntity.setProducto(Lista.get(i).getProducto());
                listaProductoEntity.setUmd(Lista.get(i).getUmd());
                listaProductoEntity.setStock(Lista.get(i).getStock());
                listaProductoEntity.setPreciocontadoigv(Lista.get(i).getPreciocontadoigv());
                listaProductoEntity.setPreciocreditoigv(Lista.get(i).getPreciocreditoigv());
                listaProductoEntity.setGal(Lista.get(i).getGal());
                listaProductoEntity.setPromotionenable(Lista.get(i).isPromotionenable());
                saveLead(listaProductoEntity);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>(leads.values());
    }
}

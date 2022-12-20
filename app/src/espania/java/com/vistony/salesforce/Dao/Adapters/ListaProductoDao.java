package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaProductoEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
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
                ListaProductoEntity listaProductoEntity=new ListaProductoEntity();

                listaProductoEntity.setProducto_item_id(Lista.get(i).getProducto_item_id());
                listaProductoEntity.setProducto_id(Lista.get(i).getProducto_id());
                listaProductoEntity.setProducto(Lista.get(i).getProducto());
                listaProductoEntity.setUmd(Lista.get(i).getUmd());
                listaProductoEntity.setStock(Lista.get(i).getStock());
                listaProductoEntity.setPreciobase(Lista.get(i).getPreciobase());
                listaProductoEntity.setPrecioigv(Lista.get(i).getPrecioigv());
                listaProductoEntity.setGal(Lista.get(i).getGal());
                listaProductoEntity.setPorcentaje_dsct(Lista.get(i).getPorcentaje_dsct());
                listaProductoEntity.setOiltax(Lista.get(i).getOiltax());
                listaProductoEntity.setLiter(Lista.get(i).getLiter());
                listaProductoEntity.setSIGAUS(Lista.get(i).getSIGAUS());
                saveLead(listaProductoEntity);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ArrayList<>(leads.values());
    }
}

package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaRutaVendedorEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.HashMap;

public class ListaRutaVendedorDao {
    Context context;

    public static ListaRutaVendedorDao repository = new ListaRutaVendedorDao();
    public HashMap<String, ListaRutaVendedorEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaRutaVendedorDao getInstance() {
        return repository;
    }

    public ListaRutaVendedorDao()
    {

    }



    /*private void saveLead(ListaRutaVendedorEntity lead) {
        leads.put(lead.getCliente_id(), lead);
    }

    public List<ListaRutaVendedorEntity> getLeads(ArrayList<RutaVendedorSQLiteEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();

        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaRutaVendedorEntity(
                        Lista.get(i).getCliente_id()
                        , Lista.get(i).getDireccion()
                        , Lista.get(i).getDomembarque_id()
                        ,Lista.get(i).getNombrecliente()
                        ,Lista.get(i).getOrdenvisita()
                        ,Lista.get(i).getSaldomn()
                        ,Lista.get(i).getZona()
                        ,1));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }*/
}

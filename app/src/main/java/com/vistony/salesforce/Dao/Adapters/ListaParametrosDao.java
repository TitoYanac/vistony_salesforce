package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaParametrosEntity;
import com.vistony.salesforce.Entity.SQLite.ParametrosSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaParametrosDao {
    Context context;

    public static ListaParametrosDao repository = new ListaParametrosDao();
    public HashMap<String, ListaParametrosEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaParametrosDao getInstance() {
        return repository;
    }

    public ListaParametrosDao()
    {

    }



    private void saveLead(ListaParametrosEntity lead) {
        leads.put(lead.getNombreparametro(), lead);
    }

    public List<ListaParametrosEntity> getLeads(ArrayList<ParametrosSQLiteEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();

        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaParametrosEntity(
                        false
                        , Lista.get(i).getNombreparametro()
                        , Lista.get(i).getCantidadregistros()
                        ,Lista.get(i).getFechacarga()
                        ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

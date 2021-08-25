package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.Adapters.ListaComisionesDetalleEntity;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.ComisionesSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaComisionesDetalleDao {
    Context context;

    public static ListaComisionesDetalleDao repository = new ListaComisionesDetalleDao();
    public HashMap<String, ListaComisionesDetalleEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;
    public ClienteSQlite clienteSQlite;
    ArrayList<ClienteSQLiteEntity> listaclienteSQLiteEntity;

    public static ListaComisionesDetalleDao getInstance() {
        return repository;
    }

    public ListaComisionesDetalleDao()
    {

    }



    private void saveLead(ListaComisionesDetalleEntity lead) {

        leads.put(lead.getVariable(),lead);
    }

    public List<ListaComisionesDetalleEntity> getLeads(ArrayList<ComisionesSQLiteEntity> Lista) {

        if(leads.size()>0)
        {
            leads.clear();
        }
        listaclienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaComisionesDetalleEntity(
                                SesionEntity.compania_id
                                ,Lista.get(i).getVariable()
                                ,Lista.get(i).getUmd()
                                ,Lista.get(i).getAvance()
                                ,Lista.get(i).getCuota()
                                ,String.valueOf (Float.parseFloat(Lista.get(i).getPorcentajeavance())*100)//+"%"
                        )
                );

            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }

}

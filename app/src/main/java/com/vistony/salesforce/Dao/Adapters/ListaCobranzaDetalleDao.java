package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Adapters.ListaCobranzaDetalleEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaCobranzaDetalleDao {
    Context context;

    public static ListaCobranzaDetalleDao repository = new ListaCobranzaDetalleDao();
    public HashMap<String, ListaCobranzaDetalleEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;


    public static ListaCobranzaDetalleDao getInstance() {
        return repository;
    }

    public ListaCobranzaDetalleDao()
    {

    }



    private void saveLead(ListaCobranzaDetalleEntity lead) {
        leads.put(lead.getCliente_id(), lead);
    }

    public List<ListaCobranzaDetalleEntity> getLeads(ArrayList<ListaClienteDetalleEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();

        if(leads.size()>0)
        {
            leads.clear();
        }
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaCobranzaDetalleEntity(
                        Lista.get(i).getCliente_id()
                        ,Lista.get(i).getNombrecliente()
                        ,Lista.get(i).getDocumento_id()
                        ,Lista.get(i).getNrodocumento()
                        ,(Lista.get(i).getSaldo())
                        ,Lista.get(i).getCobrado()
                        ,Lista.get(i).getNuevo_saldo()));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaSeguimientoFacturasEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaSeguimientoFacturasDao {
    Context context;

    public static ListaSeguimientoFacturasDao repository = new ListaSeguimientoFacturasDao();
    public HashMap<String, ListaSeguimientoFacturasEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaSeguimientoFacturasDao getInstance() {
        return repository;
    }

    public ListaSeguimientoFacturasDao()
    {

    }



    private void saveLead(ListaSeguimientoFacturasEntity lead) {
        leads.put(lead.getLegalnumber(), lead);
    }

    public List<ListaSeguimientoFacturasEntity> getLeads(ArrayList<ListaSeguimientoFacturasEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();

        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaSeguimientoFacturasEntity(
                        Lista.get(i).getOrdenERP_id()
                        , Lista.get(i).getDocumento_id()
                        , Lista.get(i).getLegalnumber()
                        ,Lista.get(i).getCliente_id()
                        ,Lista.get(i).getRucdni()
                        ,Lista.get(i).getNombrecliente()
                        ,Lista.get(i).getMontodocumento()
                        ,Lista.get(i).getNombrechofer()
                        ,Lista.get(i).getFechaprogramacion()
                        ,Lista.get(i).getEstadodespacho()));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

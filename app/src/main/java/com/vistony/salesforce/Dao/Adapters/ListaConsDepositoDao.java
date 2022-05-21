package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.CobranzaDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaConsDepositoDao {
    Context context;

    public static ListaConsDepositoDao repository = new ListaConsDepositoDao();
    public HashMap<String, ListaConsDepositoEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;
    public ClienteSQlite clienteSQlite;
    ArrayList<ClienteSQLiteEntity> listaclienteSQLiteEntity;

    public static ListaConsDepositoDao getInstance() {
        return repository;
    }

    public ListaConsDepositoDao()
    {

    }



    private void saveLead(ListaConsDepositoEntity lead) {
        leads.put(lead.getRecibo(), lead);
    }

    public List<ListaConsDepositoEntity> getLeads(ArrayList<CobranzaDetalleSQLiteEntity> Lista
            ,Context context,boolean estado
    ) {

        if(leads.size()>0)
        {
            leads.clear();
        }
        //clienteDetalleView =  new ClienteDetalleView();
        Lista.size();
        listaclienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        clienteSQlite = new ClienteSQlite(context);
        try {
            for( int i=0;i<Lista.size();i++)
            {
                /*String nombrecliente="",cliente_id="";
                cliente_id=Lista.get(i).getCliente_id();
                listaclienteSQLiteEntity= clienteSQlite.ObtenerDatosCliente(cliente_id, SesionEntity.compania_id);


                for(int g=0;g<listaclienteSQLiteEntity.size();g++)
                {
                    nombrecliente=listaclienteSQLiteEntity.get(g).getNombrecliente();
                }*/

                saveLead(new ListaConsDepositoEntity(
                        Lista.get(i).getCliente_id()
                        ,Lista.get(i).getCardname()
                        ,Lista.get(i).getRecibo()
                        ,Lista.get(i).getDocumento_id()
                        ,Lista.get(i).getNrofactura()
                        ,Lista.get(i).getFechacobranza()
                        ,Lista.get(i).getImportedocumento()
                        ,Lista.get(i).getSaldodocumento()
                        ,Lista.get(i).getSaldocobrado()
                        ,Lista.get(i).getNuevosaldodocumento()
                        ,estado
                        ,Lista.get(i).getChkbancarizado()
                        ,Lista.get(i).getPagodirecto()
                        ,Lista.get(i).getCollectioncheck()
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

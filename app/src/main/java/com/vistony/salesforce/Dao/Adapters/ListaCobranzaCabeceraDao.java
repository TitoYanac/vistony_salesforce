package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Dao.SQLIte.ClienteSQlite;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaCobranzaCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsDepositoEntity;
import com.vistony.salesforce.Entity.SesionEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.List;

public class ListaCobranzaCabeceraDao {
    Context context;

    public static ListaCobranzaCabeceraDao repository = new ListaCobranzaCabeceraDao();
    public ArrayList<ListaCobranzaCabeceraEntity> leads = new ArrayList<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;
    public ClienteSQlite clienteSQlite;
    ArrayList<ClienteSQLiteEntity> listaclienteSQLiteEntity;
    ListaCobranzaCabeceraEntity listaCobranzaCabeceraEntity;
    ArrayList<ListaCobranzaCabeceraEntity> listaCobranzaCabeceraEntities;
    public static ListaCobranzaCabeceraDao getInstance() {
        return repository;
    }

    public ListaCobranzaCabeceraDao()
    {

    }



    /*private void saveLead(ListaCobranzaCabeceraEntity lead) {

        leads.put(lead.getNrodocumento(), lead);
    }*/

    public List<ListaCobranzaCabeceraEntity> getLeads(ArrayList<ListaConsDepositoEntity> Lista, Context context) {

        if(leads.size()>0)
        {
            leads.clear();
        }
                //clienteDetalleView =  new ClienteDetalleView();
        listaCobranzaCabeceraEntities = new ArrayList<ListaCobranzaCabeceraEntity>();
        Lista.size();
        String cliente_id,nombrecliente="";
        listaclienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        clienteSQlite = new ClienteSQlite(context);




        try {
            for( int i=0;i<Lista.size();i++)
            {
                ListaCobranzaCabeceraEntity listaCobranzaCabeceraEntity = new ListaCobranzaCabeceraEntity();
                cliente_id=Lista.get(i).getCliente_id();
                listaclienteSQLiteEntity= clienteSQlite.ObtenerDatosCliente(cliente_id, SesionEntity.compania_id);


                for(int g=0;g<listaclienteSQLiteEntity.size();g++)
                {
                    nombrecliente=listaclienteSQLiteEntity.get(g).getNombrecliente();
                }

                listaCobranzaCabeceraEntity.cliente_id = nombrecliente;
                listaCobranzaCabeceraEntity.nrodocumento= Lista.get(i).getRecibo();
                listaCobranzaCabeceraEntity.saldocobrado= Lista.get(i).getCobrado();
                listaCobranzaCabeceraEntity.idetalle=1;
                listaCobranzaCabeceraEntities.add(listaCobranzaCabeceraEntity);
            }

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaCobranzaCabeceraEntities;
    }
}

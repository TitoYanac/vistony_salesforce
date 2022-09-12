package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaClienteCabeceraDao {
    Context context;

    public static ListaClienteCabeceraDao repository = new ListaClienteCabeceraDao();
    public HashMap<String, ListaClienteCabeceraEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;
    ArrayList<ListaClienteCabeceraEntity> listaCabeceraCabeceraEntities;

    ArrayList<ClienteSQLiteEntity> listaclienteSQLiteEntity;

    public static ListaClienteCabeceraDao getInstance() {
        return repository;
    }

    public ListaClienteCabeceraDao()
    {

    }



    private void saveLead(ListaClienteCabeceraEntity lead) {
        leads.put(lead.getCliente_id()+lead.getDomembarque_id(), lead);
    }

    public List<ListaClienteCabeceraEntity> getLeads(ArrayList<ListaClienteCabeceraEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();
        leads.clear();
        Lista.size();
        listaCabeceraCabeceraEntities = new ArrayList<ListaClienteCabeceraEntity>();
        listaclienteSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        //clienteSQliteDAO = new ClienteSQliteDAO(context);

        try {
            for( int i=0;i<Lista.size();i++)
            {

                saveLead(new ListaClienteCabeceraEntity(
                        Lista.get(i).getCliente_id()
                        , Lista.get(i).getNombrecliente()
                        , Lista.get(i).getDireccion()
                        ,Lista.get(i).getSaldo()
                        ,1
                        ,Lista.get(i).getMoneda()
                        ,Lista.get(i).getDomembarque_id()
                        ,Lista.get(i).getDomfactura_id()
                        ,Lista.get(i).getImpuesto_id()
                        ,Lista.get(i).getImpuesto()
                        ,Lista.get(i).getRucdni()
                        ,Lista.get(i).getCategoria()
                        ,Lista.get(i).getLinea_credito()

                        ,Lista.get(i).getTerminopago_id()
                        ,Lista.get(i).getZona_id()
                        ,Lista.get(i).getCompania_id()
                        ,Lista.get(i).getOrdenvisita()
                        ,Lista.get(i).getZona()
                        ,Lista.get(i).getTelefonofijo()
                        ,Lista.get(i).getTelefonomovil()
                        ,Lista.get(i).getCorreo()
                        ,Lista.get(i).getUbigeo_id()
                        ,Lista.get(i).getTipocambio()
                        ,Lista.get(i).getChk_visita()
                        ,Lista.get(i).getChk_pedido()
                        ,Lista.get(i).getChk_cobranza()
                        ,Lista.get(i).getChk_ruta()
                        ,Lista.get(i).getLinea_credito_usado()
                        ,Lista.get(i).getFecharuta()
                        ,Lista.get(i).getLista_precio()
                        ,Lista.get(i).getDocentry()
                        ,Lista.get(i).getLastpurchase()
                        ,Lista.get(i).getLineofbussiness()
                        ,Lista.get(i).getSaldosincontados()
                        ,Lista.get(i).getChkgeolocation()
                        ,Lista.get(i).getChkvisitsection()
                        ,Lista.get(i).getTerminopago()
                        ,Lista.get(i).getContado()
                        ,Lista.get(i).getLatitud()
                        ,Lista.get(i).getLongitud()
                        ,Lista.get(i).getControl_id()
                        ,Lista.get(i).getItem_id()
                        ,Lista.get(i).getAddresscode()
                ));
                Log.e("REOS","ListaClienteCabeceraDao.getLeads.Lista.get(i).getLastpurchase())"+Lista.get(i).getLastpurchase());
                Log.e("REOS","ListaClienteCabeceraDao.getLeads.Lista.get(i).getChk_ruta())"+Lista.get(i).getChk_ruta());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return
                new ArrayList<>(leads.values());
                //listaCabeceraCabeceraEntities;
    }

}

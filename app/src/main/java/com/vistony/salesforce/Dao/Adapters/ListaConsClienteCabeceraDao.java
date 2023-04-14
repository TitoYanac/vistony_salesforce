package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Entity.Adapters.ListaClienteCabeceraEntity;
import com.vistony.salesforce.Entity.Adapters.ListaConsClienteCabeceraEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaConsClienteCabeceraDao {
    Context context;
    public static ListaConsClienteCabeceraDao repository = new ListaConsClienteCabeceraDao();
    public HashMap<String, ListaConsClienteCabeceraEntity> leads = new HashMap<>();
    public static ListaConsClienteCabeceraDao getInstance() {
        return repository;
    }

    public ListaConsClienteCabeceraDao()
    {

    }
    private void saveLead(ListaConsClienteCabeceraEntity lead) {
        leads.put(lead.getCliente_id(), lead);
    }

    public List<ListaConsClienteCabeceraEntity> getLeads(ArrayList<ListaClienteCabeceraEntity> Lista) {
        leads.clear();
        try {
            for( int i=0;i<Lista.size();i++)
            {

                saveLead(new ListaConsClienteCabeceraEntity(
                        Lista.get(i).getCliente_id()
                        , Lista.get(i).getNombrecliente()
                        , Lista.get(i).getDireccion()
                        ,Lista.get(i).getSaldo()
                        ,1
                        ,Lista.get(i).getMoneda()
                        ,Lista.get(i).getDomembarque_id()
                        ,Lista.get(i).getImpuesto_id()
                        ,Lista.get(i).getImpuesto()
                        ,Lista.get(i).getRucdni()
                        ,Lista.get(i).getCategoria()
                        ,Lista.get(i).getLinea_credito()
                        ,Lista.get(i).getLinea_credito_usado()
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
                        ,Lista.get(i).getFecharuta()
                        ,Lista.get(i).getLastpurchase()
                        ,Lista.get(i).getLineofbussiness()
                        ,Lista.get(i).getTerminopago()
                        ,Lista.get(i).getContado()
                        ,Lista.get(i).getLatitud()
                        ,Lista.get(i).getLongitud()
                        ,Lista.get(i).getControl_id()
                        ,Lista.get(i).getItem_id()
                        ,Lista.get(i).getAddresscode()
                        ,Lista.get(i).getChkgeolocation()
                        ,Lista.get(i).getStatuscount()
                ));
                Log.e("REOS","ListaConsClienteCabeceraDao.getLeads.Lista.get(i).getLineofbussiness(): "+Lista.get(i).getLineofbussiness());
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

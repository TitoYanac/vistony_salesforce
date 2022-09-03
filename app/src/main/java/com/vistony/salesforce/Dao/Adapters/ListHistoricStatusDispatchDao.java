package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListHistoricStatusDispatchDao {
    Context context;

    public static ListHistoricStatusDispatchDao repository = new ListHistoricStatusDispatchDao();
    public HashMap<String, HistoricStatusDispatchEntity> leads = new HashMap<>();
    public static ListHistoricStatusDispatchDao getInstance() {
        return repository;
    }

    public ListHistoricStatusDispatchDao()
    {

    }
    private void saveLead(HistoricStatusDispatchEntity lead) {
        leads.put(lead.getEntrega_ID(), lead);
    }

    public List<HistoricStatusDispatchEntity> getLeads(List<HistoricStatusDispatchEntity> Lista) {
        Lista.size();
        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new HistoricStatusDispatchEntity(
                        Lista.get(i).getFuerzaTrabajo_ID()
                        , Lista.get(i).getUsuario_ID()
                        , Lista.get(i).getTipoDespacho_ID()
                        , Lista.get(i).getTipoDespacho()
                        , Lista.get(i).getMotivoDespacho_ID()
                        , Lista.get(i).getMotivoDespacho()
                        , Lista.get(i).getObservacion()
                        , Lista.get(i).getLatitud()
                        , Lista.get(i).getLongitud()
                        , Lista.get(i).getFecha()
                        , Lista.get(i).getHora()
                        , Lista.get(i).getEntrega_ID()
                        , Lista.get(i).getCliente_ID()
                        , Lista.get(i).getCliente()
                        , Lista.get(i).getEntrega()
                        , Lista.get(i).getFactura_ID()
                        , Lista.get(i).getFactura()
                        ,Lista.get(i).getFotoLocal()
                        ,Lista.get(i).getFotoGuia()
                        ,Lista.get(i).getChk_Recibido()
                        ,Lista.get(i).getMessageServerDispatch()
                        ,Lista.get(i).getMessageServerTimeDispatch()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

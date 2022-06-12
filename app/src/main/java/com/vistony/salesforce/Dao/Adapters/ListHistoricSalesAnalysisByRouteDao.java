package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricSalesAnalysisByRouteEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ListHistoricSalesAnalysisByRouteDao {
    Context context;

    public static ListHistoricSalesAnalysisByRouteDao repository = new ListHistoricSalesAnalysisByRouteDao();
    //public HashMap<Float, HistoricSalesAnalysisByRouteEntity> leads = new HashMap<>();
    public List<HistoricSalesAnalysisByRouteEntity> leads=new ArrayList<>();



    public static ListHistoricSalesAnalysisByRouteDao getInstance() {
        return repository;
    }

    public ListHistoricSalesAnalysisByRouteDao()
    {

    }



    /*private void saveLead(HistoricSalesAnalysisByRouteEntity lead) {
        leads.put(Float.parseFloat (lead.getId() ) , lead);
        Log.e("REOS","ListHistoricSalesAnalysisByRouteDao-saveLead-lead.getId()"+lead.getId());
    }*/

    public List<HistoricSalesAnalysisByRouteEntity> getLeads(List<HistoricSalesAnalysisByRouteEntity> Lista) {
        leads.clear();

        try {
            List<Float> slist=new ArrayList<>();
            for( int i=0;i<Lista.size();i++)
            {
                slist.add(Float.parseFloat(Lista.get(i).getGalanioactualperiodoactual()+String.valueOf(i)));
                Log.e("REOS","ListHistoricSalesAnalysisByRouteDao-getLeads-lead.getId()"+Float.parseFloat(Lista.get(i).getGalanioactualperiodoactual()+String.valueOf(i)));
            }
            Collections.sort(slist, Collections.reverseOrder());
            Log.e("REOS","ListHistoricSalesAnalysisByRouteDao-saveLead-slist"+slist);
            System.out.println("After Sorting: "+ slist);
            for (Float strCommercialclass : slist) {
                Log.e("REOS", "ListHistoricSalesAnalysisByRouteDao-saveLead-forearch-strCommercialclass" + strCommercialclass);
                for(int g=0;g<Lista.size();g++)
                {
                if (strCommercialclass==Float.parseFloat(Lista.get(g).getGalanioactualperiodoactual() + String.valueOf(g)))
                {
                    Log.e("REOS", "ListHistoricSalesAnalysisByRouteDao-saveLead-forearch-strCommercialclass-dentrodelif" + strCommercialclass);
                    HistoricSalesAnalysisByRouteEntity lead=new HistoricSalesAnalysisByRouteEntity(
                            Lista.get(g).getCliente_id()
                            , Lista.get(g).getCardname()
                            , Lista.get(g).getShiptocode()
                            , Lista.get(g).getStreet()
                            , Lista.get(g).getTerritoryid()
                            , Lista.get(g).getTerritory()
                            , Lista.get(g).getSlpcode()
                            , Lista.get(g).getDia()
                            , Lista.get(g).getClase_comercial()
                            , Lista.get(g).getGalanioactualperiodoactual()
                            , Lista.get(g).getGalanioactual1periodoanterior()
                            , Lista.get(g).getGalanioactual2periodoAnterior()
                            , Lista.get(g).getGalanioanteriorperiodoactual()
                            , Lista.get(g).getGalanioanterior1periodoanterior()
                            , Lista.get(g).getGalanioanterior2periodoanterior()
                            , Lista.get(g).getPromediotrimestreanioactual()
                            , Lista.get(g).getPromediotrimestreanioanterior()
                            , Lista.get(g).getProm()
                            , Lista.get(g).getProm2122()
                            , Lista.get(g).getCuota()
                            , Lista.get(g).getPorcentajeavancecuota()
                            , Lista.get(g).getGalanioactualperiodoactual() + String.valueOf(g)
                    );
                    leads.add(lead);
                }
            }
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return leads;
    }
}

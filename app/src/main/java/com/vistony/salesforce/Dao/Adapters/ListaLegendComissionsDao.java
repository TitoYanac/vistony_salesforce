package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHojaDespachoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.EscColoursDEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaLegendComissionsDao {
    Context context;

    public static ListaLegendComissionsDao repository = new ListaLegendComissionsDao();
    public HashMap<String, EscColoursDEntity> leads = new HashMap<>();

    public static ListaLegendComissionsDao getInstance() {
        return repository;
    }

    public ListaLegendComissionsDao()
    {

    }

    private void saveLead(EscColoursDEntity lead) {
        leads.put(lead.getId(), lead);
    }

    public List<EscColoursDEntity> getLeads(ArrayList<EscColoursDEntity> Lista) {
        leads.clear();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new EscColoursDEntity(
                        Lista.get(i).getCompania_id()
                        , Lista.get(i).getFuerzatrabajo_id()
                        , Lista.get(i).getUsuario_id()
                        , Lista.get(i).getId_esc_colours_c()
                        , Lista.get(i).getId()
                        , Lista.get(i).getRangemin()
                        ,Lista.get(i).getRangemax()
                        ,Lista.get(i).getColourmin()
                        ,Lista.get(i).getColourmax()
                        ,Lista.get(i).getDegrade()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

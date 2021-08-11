package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHojaDespachoEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHojaDespachoDao {
    Context context;

    public static ListaHojaDespachoDao repository = new ListaHojaDespachoDao();
    public HashMap<String, ListaHojaDespachoEntity> leads = new HashMap<>();

    public static ListaHojaDespachoDao getInstance() {
        return repository;
    }

    public ListaHojaDespachoDao()
    {

    }

    private void saveLead(ListaHojaDespachoEntity lead) {
        leads.put(lead.getNrofactura(), lead);
    }

    public List<ListaHojaDespachoEntity> getLeads(ArrayList<HojaDespachoSQLiteEntity> Lista) {
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new ListaHojaDespachoEntity(
                        Lista.get(i).getNombrecliente()
                        , Lista.get(i).getDireccion()
                        , Lista.get(i).getNrofactura()
                        , Lista.get(i).getSaldo()
                        , (Lista.get(i).getFuerzatrabajo_id()+" "+Lista.get(i).getNombrefuerzatrabajo())
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

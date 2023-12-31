package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.Adapters.ListaHojaDespachoEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoDetalleSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.HojaDespachoSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHojaDespachoDao {
    Context context;

    public static ListaHojaDespachoDao repository = new ListaHojaDespachoDao();
    public HashMap<Integer, ListaHojaDespachoEntity> leads = new HashMap<>();

    public static ListaHojaDespachoDao getInstance() {
        return repository;
    }

    public ListaHojaDespachoDao()
    {

    }

    private void saveLead(ListaHojaDespachoEntity lead) {
        try
        {
            leads.put(Integer.parseInt(lead.getItem_id()), lead);
        }catch (Exception e)
        {

        }
    }

    public List<ListaHojaDespachoEntity> getLeads(ArrayList<HojaDespachoDetalleSQLiteEntity> Lista) {
        leads.clear();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(  new ListaHojaDespachoEntity(
                        Lista.get(i).getNombrecliente()
                        , Lista.get(i).getDireccion()
                        , Lista.get(i).getFactura()
                        , Lista.get(i).getSaldo()
                        , (Lista.get(i).getFuerzatrabajo_factura_id()+" "+Lista.get(i).getFuerzatrabajo_factura())
                        , Lista.get(i).getTerminopago()
                        ,Lista.get(i).getCliente_id()
                        ,Lista.get(i).getDomembarque_id()
                        ,Lista.get(i).isChkupdatedispatch()
                        ,Lista.get(i).getControl_id()
                        ,Lista.get(i).getItem_id()
                        ,Lista.get(i).isChkvisitsectionstart()
                        ,Lista.get(i).isChkvisitsectionend()
                        ,Lista.get(i).getEntrega()
                        ,Lista.get(i).isChkcollection()
                        ,Lista.get(i).getEstado()
                        ,Lista.get(i).getOcurrencies()
                        ,Lista.get(i).getLatitud()
                        ,Lista.get(i).getLongitud()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

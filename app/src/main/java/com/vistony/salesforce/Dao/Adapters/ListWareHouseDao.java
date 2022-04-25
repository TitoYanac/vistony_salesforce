package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Adapters.ListaAgenciaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;
import com.vistony.salesforce.Entity.SQLite.AgenciaSQLiteEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListWareHouseDao {
    Context context;

    public static ListWareHouseDao repository = new ListWareHouseDao();
    public HashMap<String, WareHousesEntity> leads = new HashMap<>();



    public static ListWareHouseDao getInstance() {
        return repository;
    }

    public ListWareHouseDao()
    {

    }



    private void saveLead(WareHousesEntity lead) {
        leads.put(lead.getCodAlmacen(), lead);
    }

    public List<WareHousesEntity> getLeads(List<WareHousesEntity> Lista) {
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                saveLead(new WareHousesEntity(
                        Lista.get(i).getItemcode()
                        , Lista.get(i).getItemName()
                        , Lista.get(i).getUDM()
                        , Lista.get(i).getEnStock()
                        , Lista.get(i).getComprometido()
                        , Lista.get(i).getPedido()
                        , Lista.get(i).getDisponible()
                        , Lista.get(i).getCodAlmacen()
                        , Lista.get(i).getWhsName()
                        , Lista.get(i).getU_VIST_SUCUSU()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

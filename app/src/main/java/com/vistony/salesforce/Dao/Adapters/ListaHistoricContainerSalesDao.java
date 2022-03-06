package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Entity.Adapters.ListaHistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HistoricContainerSalesEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricContainerSalesDao {
    Context context;

    public static ListaHistoricContainerSalesDao repository = new ListaHistoricContainerSalesDao();
    public HashMap<String, ListaHistoricContainerSalesEntity> leads = new HashMap<>();
    public HashMap<Integer, ListaHistoricContainerSalesEntity> leadssku = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaHistoricContainerSalesDao getInstance() {
        return repository;
    }

    public ListaHistoricContainerSalesDao()
    {

    }



    private void saveLead(ListaHistoricContainerSalesEntity lead) {
        leads.put(lead.getAnio()+lead.getMes()+lead.getVariable(), lead);
    }
    private void saveLeadSKU(ListaHistoricContainerSalesEntity lead,int count) {
        leadssku.put(count, lead);
    }
    public List<ListaHistoricContainerSalesEntity> getLeads(List<HistoricContainerSalesEntity> Lista,String Tipo) {
        if(Tipo.equals("SKU"))
        {
            leadssku.clear();
        }else
        {
            leads.clear();
        }

        ArrayList<HistoricContainerSalesEntity> Listado=new ArrayList<>();
        Log.e("REOS","ListaHistoricContainerSalesDao-getLeads-Tipo"+Tipo);
        Log.e("REOS","ListaHistoricContainerSalesDao-getLeads-Lista.size()"+Lista.size());
        try {
                for( int i=0;i<Lista.size();i++)
                {
                    if(Tipo.equals("SKU"))
                    {
                        saveLeadSKU(new ListaHistoricContainerSalesEntity(
                                        Lista.get(i).getSlpCode(),
                                        Lista.get(i).getUserid(),
                                        Lista.get(i).getAnio(),
                                        Lista.get(i).getMes(),
                                        Lista.get(i).getVariable(),
                                        Lista.get(i).getMontototal(),
                                        Lista.get(i).getSumgaloun(),
                                        Lista.get(i).getUmd()
                                ),
                                i
                        );
                    }
                    else {
                        saveLead(new ListaHistoricContainerSalesEntity(
                                        Lista.get(i).getSlpCode(),
                                        Lista.get(i).getUserid(),
                                        Lista.get(i).getAnio(),
                                        Lista.get(i).getMes(),
                                        Lista.get(i).getVariable(),
                                        Lista.get(i).getMontototal(),
                                        Lista.get(i).getSumgaloun(),
                                        Lista.get(i).getUmd()
                                )
                        );
                    }

                }


        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS","ListaHistoricContainerSalesDao-getLeads-e"+e.toString());
        }
        Log.e("REOS","ListaHistoricContainerSalesDao-getLeads-leads"+leads.size());
        if(Tipo.equals("SKU"))
        {
            return new ArrayList<>(leadssku.values());
        }else
            {
                return new ArrayList<>(leads.values());
            }

    }
}

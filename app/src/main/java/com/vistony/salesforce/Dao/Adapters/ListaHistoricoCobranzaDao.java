package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoCobranzaEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricoCobranzaDao {
    Context context;

    public static ListaHistoricoCobranzaDao repository = new ListaHistoricoCobranzaDao();
    public HashMap<String, ListaHistoricoCobranzaEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaHistoricoCobranzaDao getInstance() {
        return repository;
    }

    public ListaHistoricoCobranzaDao()
    {

    }



    private void saveLead(ListaHistoricoCobranzaEntity lead) {
        leads.put(lead.getRecibo(), lead);
    }

    public List<ListaHistoricoCobranzaEntity> getLeads(ArrayList<ListaHistoricoCobranzaEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();
        leads.clear();
        Lista.size();

        try {
            for( int i=0;i<Lista.size();i++)
            {
                boolean conciliado=false;
                if(Lista.get(i).getEstado().equals("PENDIENTE"))
                {
                    conciliado=false;
                }
                else if(Lista.get(i).getEstado().equals("CONCILIADO"))
                {
                    conciliado=true;
                }
                saveLead(new ListaHistoricoCobranzaEntity(
                        Lista.get(i).getBancarizacion()
                        ,Lista.get(i).getBanco_id()
                        ,Lista.get(i).getCliente_id()
                        ,Lista.get(i).getCliente_nombre()
                        ,Lista.get(i).getComentario()
                        ,Lista.get(i).getCompania_id()
                        ,Lista.get(i).getDeposito_id()
                        ,Lista.get(i).getDetalle_item()
                        ,Lista.get(i).getDocumento_id()
                        ,Lista.get(i).getEstado()
                        ,Lista.get(i).getEstadoqr()
                        ,Lista.get(i).getFechacobranza()
                        ,Lista.get(i).getFechadeposito()
                        ,Lista.get(i).getFuerzatrabajo_id()
                        ,Lista.get(i).getImportedocumento()
                        ,Lista.get(i).getMontocobrado()
                        ,Lista.get(i).getMotivoanulacion()
                        ,Lista.get(i).getNro_documento()
                        ,Lista.get(i).getNuevosaldodocumento()
                        ,Lista.get(i).getRecibo()
                        ,Lista.get(i).getSaldodocumento()
                        ,Lista.get(i).getTipoingreso()
                        ,Lista.get(i).getUsuario_id()
                        ,conciliado
                        ,Lista.get(i).getChkwsrecibido()
                        ,Lista.get(i).getDepositodirecto()
                        ,Lista.get(i).getPagopos()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return new ArrayList<>(leads.values());
    }
}

package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;

import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaHistoricoDepositoEntity;
import com.vistony.salesforce.View.ClienteDetalleView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaHistoricoDepositoDao {
    Context context;

    public static ListaHistoricoDepositoDao repository = new ListaHistoricoDepositoDao();
    public HashMap<String, ListaHistoricoDepositoEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;



    public static ListaHistoricoDepositoDao getInstance() {
        return repository;
    }

    public ListaHistoricoDepositoDao()
    {

    }



    private void saveLead(ListaHistoricoDepositoEntity lead) {
        leads.put(lead.getDeposito_id()+lead.getMontodeposito(), lead);
    }

    public List<ListaHistoricoDepositoEntity> getLeads(ArrayList<ListaHistoricoDepositoEntity> Lista) {
        //clienteDetalleView =  new ClienteDetalleView();
        leads.clear();
        //Lista.size();

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

                /*String dia="",mes="",anio="",fecha="";
                String[] separada = Lista.get(i).getFechadeposito().split("/");
                    if(separada.length>1)
                    {
                        dia=separada[0];
                        mes=separada[1];
                        anio=separada[2];
                    }
                String[] separada2 = anio.split(" ");
                if(separada2.length>1)
                {
                    anio=separada2[0];
                }
              fecha=anio+"-"+mes+"-"+dia;*/
                saveLead(new ListaHistoricoDepositoEntity(
                        Lista.get(i).getBancarizacion()
                        ,Lista.get(i).getBanco_id()
                        , Lista.get(i).getComentario()
                        , Lista.get(i).getCompania_id()
                        , Lista.get(i).getDeposito_id()
                        , Lista.get(i).getEstado()
                        , Lista.get(i).getFechadeposito()
                        //,fecha
                        ,Lista.get(i).getFechadiferida()
                        , Lista.get(i).getFuerzatrabajo_id()
                        , Lista.get(i).getMontodeposito()
                        ,Lista.get(i).getMotivoanulacion()
                        ,Lista.get(i).getTipoingreso()
                        , Lista.get(i).getUsuario_id()
                        , conciliado
                        ,Lista.get(i).getImvdetalle()
                        ,Lista.get(i).getDepositodirecto()
                        ,Lista.get(i).getBankname()
                        ,Lista.get(i).getCode()
                )
                );
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(leads.values());
    }
}

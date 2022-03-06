package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Dao.SQLite.ClienteSQlite;
import com.vistony.salesforce.Entity.SQLite.ClienteSQLiteEntity;
import com.vistony.salesforce.Entity.SQLite.DocumentoDeudaSQLiteEntity;
import com.vistony.salesforce.Entity.Adapters.ListaClienteDetalleEntity;
import com.vistony.salesforce.View.ClienteDetalleView;
import com.vistony.salesforce.View.MenuView;
import com.vistony.salesforce.View.ParametrosView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaClienteDetalleDao {
    public ListaClienteDetalleDao(Context context){
        this.context=context;
    }
    Context context;
    MenuView menu;
    ParametrosView parametrosView;

            //getInstance().clienteDetalleView.getActivity();
    ClienteSQlite clienteSQlite;
    public static ListaClienteDetalleDao repository = new ListaClienteDetalleDao();
    public HashMap<String, ListaClienteDetalleEntity> leads = new HashMap<>();
    public ClienteDetalleView clienteDetalleView;
    public ClienteSQLiteEntity clienteSQLiteEntity;
    ArrayList<ClienteSQLiteEntity> listaclientesqlSQLiteEntity;
    public DocumentoDeudaSQLiteEntity documentoDeudaSQLiteEntity;
    ArrayList<ListaClienteDetalleEntity> listaClienteDetalleEntities;

    public static ListaClienteDetalleDao getInstance() {
        return repository;
    }

    public ListaClienteDetalleDao()
    {


        //saveLead(new ListaClienteDetalleEntity("FACTURA01", "01/01/2019", "01/01/2019","1000","1000",0));
        //saveLead(new ListaClienteDetalleEntity("FACTURA02", "01/01/2019", "01/01/2019","1000","1000",0));
        //saveLead(new ListaClienteDetalleEntity("FACTURA03", "01/01/2019", "01/01/2019","1000","1000",0));
        //saveLead(new Lead("Carlos Lopez", "Asistente", "Hospital Blue", R.drawable.lead_photo_2));
        //saveLead(new Lead("Sara Bonz", "Directora de Marketing", "Electrical Parts ltd", R.drawable.lead_photo_3));
        //saveLead(new Lead("Liliana Clarence", "Diseñadora de Producto", "Creativa App", R.drawable.lead_photo_4));
        //saveLead(new Lead("Benito Peralta", "Supervisor de Ventas", "Neumáticos Press", R.drawable.lead_photo_5));
        //saveLead(new Lead("Juan Jaramillo", "CEO", "Banco Nacional", R.drawable.lead_photo_6));
        //saveLead(new Lead("Christian Steps", "CTO", "Cooperativa Verde", R.drawable.lead_photo_7));
        //saveLead(new Lead("Alexa Giraldo", "Lead Programmer", "Frutisofy", R.drawable.lead_photo_8));
        //saveLead(new Lead("Linda Murillo", "Directora de Marketing", "Seguros Boliver", R.drawable.lead_photo_9));
        //saveLead(new Lead("Lizeth Astrada", "CEO", "Concesionario Motolox", R.drawable.lead_photo_10));

    }

  /*  public void chargeLead(String nrodocumento,String fechaemision,String fechavencimiento,String importe,String saldo,int checkbox)
    {
        saveLead(new ListaClienteDetalleEntity(nrodocumento,fechaemision,fechavencimiento,importe,saldo,0));

    }
    */
/*
    private void saveLead(ListaClienteDetalleEntity lead) {
        leads.put(lead.getNrodocumento(), lead);
    }
*/
    public List<ListaClienteDetalleEntity> getLeads(ArrayList<DocumentoDeudaSQLiteEntity> Lista) {
        if(leads.size()>0)
        {
            leads.clear();
        }
        parametrosView = new ParametrosView();
        clienteSQLiteEntity = new ClienteSQLiteEntity();
        listaclientesqlSQLiteEntity = new ArrayList<ClienteSQLiteEntity>();
        String nombrecliente="",domembarque_id="",direccion="";
        Lista.size();
        listaClienteDetalleEntities = new ArrayList<ListaClienteDetalleEntity>();

        try {
        for( int i=0;i<Lista.size();i++)
        {
            ListaClienteDetalleEntity listaClienteDetalleEntity = new ListaClienteDetalleEntity();

            listaclientesqlSQLiteEntity=ParametrosView.ObtenerDatosCliente(String.valueOf(Lista.get(i).getCliente_id()),
                                                String.valueOf(Lista.get(i).getCompania_id()));
            Log.e("REOS","ListaClienteDetalleDao1:  "+listaclientesqlSQLiteEntity.size());
            for (int j=0;j<listaclientesqlSQLiteEntity.size();j++)
            {
                nombrecliente=String.valueOf(listaclientesqlSQLiteEntity.get(j).getNombrecliente())  ;
                domembarque_id=String.valueOf(listaclientesqlSQLiteEntity.get(j).getDomembarque_id()) ;
                direccion=String.valueOf(listaclientesqlSQLiteEntity.get(j).getDireccion()) ;
            }
            Log.e("REOS","ListaClienteDetalleDao1:  "+domembarque_id);
            listaClienteDetalleEntity.cliente_id=Lista.get(i).getCliente_id();
            listaClienteDetalleEntity.nombrecliente=nombrecliente;
            listaClienteDetalleEntity.domembarque=domembarque_id;
            listaClienteDetalleEntity.direccion=direccion;
            listaClienteDetalleEntity.documento_id=Lista.get(i).getDocumento_id();
            listaClienteDetalleEntity.nrodocumento=Lista.get(i).getNrofactura();
            listaClienteDetalleEntity.fechaemision=Lista.get(i).getFechaemision();
            listaClienteDetalleEntity.fechavencimiento=Lista.get(i).getFechavencimiento();
            listaClienteDetalleEntity.importe=Lista.get(i).getImportefactura();
            listaClienteDetalleEntity.saldo=Lista.get(i).getSaldo();
            listaClienteDetalleEntity.cobrado="0";
            listaClienteDetalleEntity.nuevo_saldo="0";
            listaClienteDetalleEntity.imvclientedetalle=1;
            listaClienteDetalleEntity.moneda=Lista.get(i).getMoneda();
            listaClienteDetalleEntity.docentry=Lista.get(i).getDocumento_entry();
            listaClienteDetalleEntities.add(listaClienteDetalleEntity);
        }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return listaClienteDetalleEntities;
                //new ArrayList<>(leads.values());

    }
}

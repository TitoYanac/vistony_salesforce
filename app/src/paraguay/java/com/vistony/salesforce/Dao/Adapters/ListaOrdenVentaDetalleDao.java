package com.vistony.salesforce.Dao.Adapters;

import android.content.Context;
import android.util.Log;

import com.vistony.salesforce.Entity.Adapters.ListaOrdenVentaDetalleEntity;
import com.vistony.salesforce.ListenerBackPress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListaOrdenVentaDetalleDao {
    Context context;

    public static ListaOrdenVentaDetalleDao repository = new ListaOrdenVentaDetalleDao();
    public HashMap<Integer, ListaOrdenVentaDetalleEntity> leads = new HashMap<>();



    public static ListaOrdenVentaDetalleDao getInstance() {
        return repository;
    }

    public ListaOrdenVentaDetalleDao()
    {

    }



    private void saveLead(ListaOrdenVentaDetalleEntity lead) {
        leads.put(Integer.parseInt(lead.getOrden_detalle_item()),lead);
    }

    public List<ListaOrdenVentaDetalleEntity> getLeads(ArrayList<ListaOrdenVentaDetalleEntity> Lista) {
        //Lista.size();
        String contadodescuento="0";
        if(!(leads==null))
        {
            leads.clear();
        }
        try {
            for( int i=0;i<Lista.size();i++)
            {
                /*if(Lista.get(i).getOrden_detalle_descuentocontado().equals("true")&&
                        !Lista.get(i).getOrden_detalle_porcentaje_descuento().equals("100"))
                {
                    contadodescuento="3";
                }
                else
                {
                    contadodescuento="0";
                }*/
                Log.e("REOS", "ListaOrdenVentaDetalleDao.getLeads.Lista.get(i).getOrden_detalle_porcentaje_descuento(): " + Lista.get(i).getOrden_detalle_porcentaje_descuento());
                Log.e("REOS", "ListaOrdenVentaDetalleDao.getLeads.Lista.get(i).getOrden_detalle_monto_descuento(): " + Lista.get(i).getOrden_detalle_monto_descuento());
                saveLead(new ListaOrdenVentaDetalleEntity(
                        Lista.get(i).getOrden_detalle_item(),
                        Lista.get(i).getOrden_detalle_producto_id(),
                        Lista.get(i).getOrden_detalle_producto(),
                        Lista.get(i).getOrden_detalle_umd(),
                        Lista.get(i).getOrden_detalle_stock(),
                        Lista.get(i).getOrden_detalle_cantidad(),
                        Lista.get(i).getOrden_detalle_precio_unitario(),
                        Lista.get(i).getOrden_detalle_montosubtotal(),
                        //String.valueOf(Double.parseDouble(Lista.get(i).getOrden_detalle_porcentaje_descuento())+Double.parseDouble(contadodescuento)),
                        Lista.get(i).getOrden_detalle_porcentaje_descuento(),

                        Lista.get(i).getOrden_detalle_porcentaje_descuento_maximo(),
                        Lista.get(i).getOrden_detalle_monto_descuento(),
                        Lista.get(i).getOrden_detalle_monto_igv(),
                        Lista.get(i).getOrden_detalle_montototallinea(),
                        Lista.get(i).getOrden_detalle_lista_promocion_cabecera(),
                        Lista.get(i).getOrden_detalle_promocion_habilitada(),
                        Lista.get(i).getOrden_detalle_gal(),
                        Lista.get(i).getOrden_detalle_gal_acumulado(),
                        Lista.get(i).getOrden_detalle_montosubtotalcondescuento(),
                        Lista.get(i).getOrden_detalle_lista_orden_detalle_promocion(),
                        Lista.get(i).getOrden_detalle_descuentocontado(),
                        Lista.get(i).isOrden_detalle_chk_descuentocontado(),
                        Lista.get(i).getOrden_detalle_terminopago_id(),
                        Lista.get(i).isOrden_detalle_chk_descuentocontado_aplicado(),
                        Lista.get(i).isOrden_detalle_chk_descuentocontado_cabecera(),
                        Lista.get(i).getOrden_detalle_cardcode()
                ));
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            Log.e("REOS", "ListaOrdenVentaDetalleDao-getLeads-e:" + e.toString());
        }

        return new ArrayList<>(leads.values());
    }
}

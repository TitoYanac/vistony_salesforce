package com.vistony.salesforce.Entity.Adapters;

public class ListaOrdenVentaDetalleListaPromocionEntity {
    public String orden_venta_detalle_lista_promocion_id;
    public String orden_venta_detalle_lista_promocion_promocion_id;
    public String orden_venta_detalle_lista_promocion_cantidad_promocion;
    public String orden_venta_detalle_lista_promocion_preciobase;
    public String orden_venta_detalle_lista_promocion_lista_precio_id;


    public ListaOrdenVentaDetalleListaPromocionEntity(String orden_venta_detalle_lista_promocion_id, String orden_venta_detalle_lista_promocion_promocion_id, String orden_venta_detalle_lista_promocion_cantidad_promocion, String orden_venta_detalle_lista_promocion_preciobase, String orden_venta_detalle_lista_promocion_lista_precio_id) {
        this.orden_venta_detalle_lista_promocion_id = orden_venta_detalle_lista_promocion_id;
        this.orden_venta_detalle_lista_promocion_promocion_id = orden_venta_detalle_lista_promocion_promocion_id;
        this.orden_venta_detalle_lista_promocion_cantidad_promocion = orden_venta_detalle_lista_promocion_cantidad_promocion;
        this.orden_venta_detalle_lista_promocion_preciobase = orden_venta_detalle_lista_promocion_preciobase;
        this.orden_venta_detalle_lista_promocion_lista_precio_id = orden_venta_detalle_lista_promocion_lista_precio_id;
    }

    public ListaOrdenVentaDetalleListaPromocionEntity() {

    }

    public String getOrden_venta_detalle_lista_promocion_lista_precio_id() {
        return orden_venta_detalle_lista_promocion_lista_precio_id;
    }

    public void setOrden_venta_detalle_lista_promocion_lista_precio_id(String orden_venta_detalle_lista_promocion_lista_precio_id) {
        this.orden_venta_detalle_lista_promocion_lista_precio_id = orden_venta_detalle_lista_promocion_lista_precio_id;
    }

    public String getOrden_venta_detalle_lista_promocion_preciobase() {
        return orden_venta_detalle_lista_promocion_preciobase;
    }

    public void setOrden_venta_detalle_lista_promocion_preciobase(String orden_venta_detalle_lista_promocion_preciobase) {
        this.orden_venta_detalle_lista_promocion_preciobase = orden_venta_detalle_lista_promocion_preciobase;
    }

    public String getOrden_venta_detalle_lista_promocion_id() {
        return orden_venta_detalle_lista_promocion_id;
    }

    public void setOrden_venta_detalle_lista_promocion_id(String orden_venta_detalle_lista_promocion_id) {
        this.orden_venta_detalle_lista_promocion_id = orden_venta_detalle_lista_promocion_id;
    }

    public String getOrden_venta_detalle_lista_promocion_promocion_id() {
        return orden_venta_detalle_lista_promocion_promocion_id;
    }

    public void setOrden_venta_detalle_lista_promocion_promocion_id(String orden_venta_detalle_lista_promocion_promocion_id) {
        this.orden_venta_detalle_lista_promocion_promocion_id = orden_venta_detalle_lista_promocion_promocion_id;
    }

    public String getOrden_venta_detalle_lista_promocion_cantidad_promocion() {
        return orden_venta_detalle_lista_promocion_cantidad_promocion;
    }

    public void setOrden_venta_detalle_lista_promocion_cantidad_promocion(String orden_venta_detalle_lista_promocion_cantidad_promocion) {
        this.orden_venta_detalle_lista_promocion_cantidad_promocion = orden_venta_detalle_lista_promocion_cantidad_promocion;
    }
}

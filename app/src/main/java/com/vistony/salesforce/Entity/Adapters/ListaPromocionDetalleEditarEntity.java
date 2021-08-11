package com.vistony.salesforce.Entity.Adapters;

public class ListaPromocionDetalleEditarEntity {
    public String id;
    public String producto_id;
    public String producto;
    public String umd;
    public String cantidad;
    public String cantidad_editada;
    public boolean estadoitems;

    public ListaPromocionDetalleEditarEntity(String id, String producto_id, String producto, String umd, String cantidad, String cantidad_editada, boolean estadoitems) {
        this.id = id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.cantidad = cantidad;
        this.cantidad_editada = cantidad_editada;
        this.estadoitems = estadoitems;
    }

    public boolean isEstadoitems() {
        return estadoitems;
    }

    public void setEstadoitems(boolean estadoitems) {
        this.estadoitems = estadoitems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCantidad_editada() {
        return cantidad_editada;
    }

    public void setCantidad_editada(String cantidad_editada) {
        this.cantidad_editada = cantidad_editada;
    }
}

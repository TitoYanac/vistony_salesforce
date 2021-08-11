package com.vistony.salesforce.Entity.Adapters;

public class ListaPromocionDetalleEntity {
    public String id;
    public String producto_id;
    public String producto;
    public String umd;
    public String cantidad;

    public ListaPromocionDetalleEntity(String id, String producto_id, String producto, String umd, String cantidad) {
        this.id = id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.cantidad = cantidad;
    }

    public ListaPromocionDetalleEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }


    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

}

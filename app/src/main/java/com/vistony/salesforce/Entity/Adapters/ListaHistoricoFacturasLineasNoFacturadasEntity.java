package com.vistony.salesforce.Entity.Adapters;

public class ListaHistoricoFacturasLineasNoFacturadasEntity {
    public String item_ov;
    public String producto;
    public String umd;
    public String cantidad;

    public ListaHistoricoFacturasLineasNoFacturadasEntity(String item_ov, String producto, String umd, String cantidad) {
        this.item_ov = item_ov;
        this.producto = producto;
        this.umd = umd;
        this.cantidad = cantidad;
    }
    public ListaHistoricoFacturasLineasNoFacturadasEntity() {

    }

    public String getItem_ov() {
        return item_ov;
    }

    public void setItem_ov(String item_ov) {
        this.item_ov = item_ov;
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
}

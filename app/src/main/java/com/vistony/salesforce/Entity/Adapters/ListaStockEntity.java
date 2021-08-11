package com.vistony.salesforce.Entity.Adapters;

public class ListaStockEntity {

    public String producto_id;
    public String producto;
    public String umd;
    public String stock;
    public String almacen_id;

    public ListaStockEntity(String producto_id, String producto, String umd, String stock, String almacen_id) {
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.stock = stock;
        this.almacen_id = almacen_id;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getAlmacen_id() {
        return almacen_id;
    }

    public void setAlmacen_id(String almacen_id) {
        this.almacen_id = almacen_id;
    }
}

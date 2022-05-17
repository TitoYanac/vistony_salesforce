package com.vistony.salesforce.Entity.Adapters;

public class ListaProductoEntity {
    private String producto_item_id;
    private String producto_id;
    private String producto;
    private String umd;
    private String stock_general;
    private String stock_almacen;
    private String preciobase;
    private String precioigv;
    private String gal;
    private String porcentaje_descuento_max;
    private String stock;

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPorcentaje_descuento_max() {
        return porcentaje_descuento_max;
    }

    public void setPorcentaje_descuento_max(String porcentaje_descuento_max) {
        this.porcentaje_descuento_max = porcentaje_descuento_max;
    }

    public String getGal() {
        return gal;
    }

    public void setGal(String gal) {
        this.gal = gal;
    }

    public String getProducto_item_id() {
        return producto_item_id;
    }

    public void setProducto_item_id(String producto_item_id) {
        this.producto_item_id = producto_item_id;
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

    public String getStock_general() {
        return stock_general;
    }

    public void setStock_general(String stock_general) {
        this.stock_general = stock_general;
    }

    public String getStock_almacen() {
        return stock_almacen;
    }

    public void setStock_almacen(String stock_almacen) {
        this.stock_almacen = stock_almacen;
    }

    public String getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(String preciobase) {
        this.preciobase = preciobase;
    }

    public String getPrecioigv() {
        return precioigv;
    }

    public void setPrecioigv(String precioigv) {
        this.precioigv = precioigv;
    }
}

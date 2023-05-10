package com.vistony.salesforce.Entity.Adapters;

public class ListaProductoEntity {
    public String producto_item_id;
    public String producto_id;
    public String producto;
    public String umd;
    public String stock_general;
    public String stock_almacen;
    public String preciobase;
    public String precioigv;
    public String gal;
    public String porcentaje_dsct;
    public String porcentaje_descuento_max;

    public ListaProductoEntity(
            String producto_item_id,
            String producto_id,
            String producto,
            String stock_general,
            String stock_almacen,
            String preciobase,
            String precioigv,
            String gal,
            String porcentaje_dsct,
            String porcentaje_descuento_max

    ) {
        this.producto_item_id = producto_item_id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.stock_general=stock_general;
        this.stock_almacen=stock_almacen;
        this.umd = umd;
        this.preciobase = preciobase;
        this.precioigv = precioigv;
        this.gal = gal;
        this.porcentaje_dsct = porcentaje_dsct;
        this.porcentaje_descuento_max=porcentaje_descuento_max;
    }

    public ListaProductoEntity() {
    }

    public String getPorcentaje_dsct() {
        return porcentaje_dsct;
    }

    public void setPorcentaje_dsct(String porcentaje_dsct) {
        this.porcentaje_dsct = porcentaje_dsct;
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

    public String getPorcentaje_descuento_max() {
        return porcentaje_descuento_max;
    }

    public void setPorcentaje_descuento_max(String porcentaje_descuento_max) {
        this.porcentaje_descuento_max = porcentaje_descuento_max;
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

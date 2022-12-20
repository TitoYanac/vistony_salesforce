package com.vistony.salesforce.Entity.Adapters;

public class ListaProductoEntity {
    public String producto_item_id;
    public String producto_id;
    public String producto;
    public String umd;
    public String stock;
    public String preciobase;
    public String precioigv;
    public String gal;
    public String porcentaje_dsct;
    public String oiltax;
    public String liter;
    public String SIGAUS;

    public ListaProductoEntity(
            String producto_item_id, String producto_id, String producto, String umd, String stock, String preciobase, String precioigv
            , String gal, String porcentaje_dsct
            , String oiltax
            , String liter
            , String SIGAUS
    ) {
        this.producto_item_id = producto_item_id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.stock = stock;
        this.preciobase = preciobase;
        this.precioigv = precioigv;
        this.gal = gal;
        this.porcentaje_dsct = porcentaje_dsct;
        this.oiltax = oiltax;
        this.liter = liter;
        this.SIGAUS = SIGAUS;
    }

    public ListaProductoEntity() {
    }

    public String getOiltax() {
        return oiltax;
    }

    public void setOiltax(String oiltax) {
        this.oiltax = oiltax;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public String getSIGAUS() {
        return SIGAUS;
    }

    public void setSIGAUS(String SIGAUS) {
        this.SIGAUS = SIGAUS;
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

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
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

package com.vistony.salesforce.Entity.Adapters;

public class ListaConsultaStockEntity {
    public String producto_item_id;
    public String producto_id;
    public String producto;
    public String umd;
    public String stock;
    public String preciocontadoigv;
    public String preciocreditoigv;
    public String gal;
    public boolean promotionenable;

    public ListaConsultaStockEntity(String producto_item_id, String producto_id, String producto, String umd, String stock, String preciocontadoigv, String preciocreditoigv, String gal, boolean promotionenable) {
        this.producto_item_id = producto_item_id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.stock = stock;
        this.preciocontadoigv = preciocontadoigv;
        this.preciocreditoigv = preciocreditoigv;
        this.gal = gal;
        this.promotionenable = promotionenable;
    }

    public ListaConsultaStockEntity() {
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

    public String getPreciocontadoigv() {
        return preciocontadoigv;
    }

    public void setPreciocontadoigv(String preciocontadoigv) {
        this.preciocontadoigv = preciocontadoigv;
    }

    public String getPreciocreditoigv() {
        return preciocreditoigv;
    }

    public void setPreciocreditoigv(String preciocreditoigv) {
        this.preciocreditoigv = preciocreditoigv;
    }

    public String getGal() {
        return gal;
    }

    public void setGal(String gal) {
        this.gal = gal;
    }

    public boolean isPromotionenable() {
        return promotionenable;
    }

    public void setPromotionenable(boolean promotionenable) {
        this.promotionenable = promotionenable;
    }


}

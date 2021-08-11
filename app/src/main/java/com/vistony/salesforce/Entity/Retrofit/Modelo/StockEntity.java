package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class StockEntity {

    @SerializedName("WhsCode")
    private String almacen_id;

    @SerializedName("Committed")
    private String cmprometido;

    @SerializedName("Available")
    private String stock;

    @SerializedName("OnHand")
    private String enstock;

    @SerializedName("Ordered")
    private String pedido;

    @SerializedName("ItemName")
    private String producto;

    @SerializedName("ItemCode")
    private String producto_id;

    @SerializedName("Uom")
    private String umd;

    public String getCmprometido() {
        return cmprometido;
    }

    public void setCmprometido(String cmprometido) {
        this.cmprometido = cmprometido;
    }

    public String getEnstock() {
        return enstock;
    }

    public void setEnstock(String enstock) {
        this.enstock = enstock;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
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

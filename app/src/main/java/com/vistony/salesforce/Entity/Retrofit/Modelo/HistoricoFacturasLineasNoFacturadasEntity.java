package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class HistoricoFacturasLineasNoFacturadasEntity {

    @SerializedName("Item_OV")
    private String item_ov;

    @SerializedName("Producto")
    private String producto;

    @SerializedName("UMD")
    private String umd;

    @SerializedName("Cantidad")
    private String cantidad;

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

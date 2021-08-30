package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class ListaPrecioDetalleEntity {

    @SerializedName("Cash")
    private String contado;

    @SerializedName("Credit")
    private String credito;

    @SerializedName("ItemCode")
    private String producto_id;

    @SerializedName("ItemName")
    private String producto;

    @SerializedName("Uom")
    private String umd;

    @SerializedName("Gallons")
    private String gal;

    @SerializedName("Type")
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGal() {
        return gal;
    }

    public void setGal(String gal) {
        this.gal = gal;
    }

    public String getContado() {
        return contado;
    }

    public void setContado(String contado) {
        this.contado = contado;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
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
}

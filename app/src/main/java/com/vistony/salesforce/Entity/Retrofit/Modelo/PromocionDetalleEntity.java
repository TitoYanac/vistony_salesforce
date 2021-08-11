package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PromocionDetalleEntity {

    @NonNull
    @SerializedName("PromotionListID")
    private String lista_promocion_id;

    @NonNull
    @SerializedName("PromotionID")
    private String promocion_id;

    @NonNull
    @SerializedName("PromotionDetail")
    private String promocion_detalle_id;

    @NonNull
    @SerializedName("ItemCode")
    private String producto_id;

    @NonNull
    @SerializedName("ItemName")
    private String producto;

    @NonNull
    @SerializedName("Uom")
    private String umd;

    @NonNull
    @SerializedName("Quantity")
    private String cantidad;
/*
    @NonNull
    @SerializedName("PrecioBase")
    private String preciobase;

    @NonNull
    @SerializedName("ChkDescuento")
    private String chkdescuento;
*/
    @NonNull
    @SerializedName("DiscountPrcent")
    private String descuento;
/*
    @NonNull
    public String getChkdescuento() {
        return chkdescuento;
    }

    public void setChkdescuento(@NonNull String chkdescuento) {
        this.chkdescuento = chkdescuento;
    }
*/
    @NonNull
    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(@NonNull String descuento) {
        this.descuento = descuento;
    }
/*
    @NonNull
    public String getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(@NonNull String preciobase) {
        this.preciobase = preciobase;
    }
*/
    @NonNull
    public String getLista_promocion_id() {
        return lista_promocion_id;
    }

    public void setLista_promocion_id(@NonNull String lista_promocion_id) {
        this.lista_promocion_id = lista_promocion_id;
    }

    @NonNull
    public String getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(@NonNull String promocion_id) {
        this.promocion_id = promocion_id;
    }

    @NonNull
    public String getPromocion_detalle_id() {
        return promocion_detalle_id;
    }

    public void setPromocion_detalle_id(@NonNull String promocion_detalle_id) {
        this.promocion_detalle_id = promocion_detalle_id;
    }

    @NonNull
    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(@NonNull String producto_id) {
        this.producto_id = producto_id;
    }

    @NonNull
    public String getProducto() {
        return producto;
    }

    public void setProducto(@NonNull String producto) {
        this.producto = producto;
    }

    @NonNull
    public String getUmd() {
        return umd;
    }

    public void setUmd(@NonNull String umd) {
        this.umd = umd;
    }

    @NonNull
    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(@NonNull String cantidad) {
        this.cantidad = cantidad;
    }
}

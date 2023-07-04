package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PromocionCabeceraEntity {
    @NonNull
    @SerializedName("PromotionListID")
    private String lista_promocion_id;

    @NonNull
    @SerializedName("PromotionID")
    private String promocion_id;

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
    private String total_preciobase;
*/
    @NonNull
    @SerializedName("DiscountPrcent")
    private String descuento;

    @NonNull
    @SerializedName("Cantidad_Maxima")
    private String cantidad_maxima;

    @NonNull
    @SerializedName("Tipo_Malla")
    private String tipo_malla;

    @NonNull
    public String getCantidad_maxima() {
        return cantidad_maxima;
    }

    public void setCantidad_maxima(@NonNull String cantidad_maxima) {
        this.cantidad_maxima = cantidad_maxima;
    }

    @NonNull
    public String getTipo_malla() {
        return tipo_malla;
    }

    public void setTipo_malla(@NonNull String tipo_malla) {
        this.tipo_malla = tipo_malla;
    }

    @NonNull
    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(@NonNull String descuento) {
        this.descuento = descuento;
    }
/*
    @NonNull
    public String getTotal_preciobase() {
        return total_preciobase;
    }

    public void setTotal_preciobase(@NonNull String total_preciobase) {
        this.total_preciobase = total_preciobase;
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

package com.vistony.salesforce.Entity.SQLite;

import java.io.Serializable;

public class PromocionCabeceraSQLiteEntity implements Serializable {
    public String compania_id;
    public String lista_promocion_id;
    public String promocion_id;
    public String producto_id;
    public String producto;
    public String umd;
    public String cantidad;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String total_preciobase;
    public String descuento;

    public PromocionCabeceraSQLiteEntity(String compania_id, String lista_promocion_id, String promocion_id, String producto_id, String producto, String umd, String cantidad, String fuerzatrabajo_id, String usuario_id, String total_preciobase, String descuento) {
        this.compania_id = compania_id;
        this.lista_promocion_id = lista_promocion_id;
        this.promocion_id = promocion_id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.cantidad = cantidad;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.total_preciobase = total_preciobase;
        this.descuento = descuento;
    }

    public PromocionCabeceraSQLiteEntity() {

    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTotal_preciobase() {
        return total_preciobase;
    }

    public void setTotal_preciobase(String total_preciobase) {
        this.total_preciobase = total_preciobase;
    }

    public String getLista_promocion_id() {
        return lista_promocion_id;
    }

    public void setLista_promocion_id(String lista_promocion_id) {
        this.lista_promocion_id = lista_promocion_id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(String promocion_id) {
        this.promocion_id = promocion_id;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
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

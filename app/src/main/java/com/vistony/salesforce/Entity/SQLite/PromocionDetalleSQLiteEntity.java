package com.vistony.salesforce.Entity.SQLite;

import java.io.Serializable;

public class PromocionDetalleSQLiteEntity implements Serializable {
    public String compania_id;
    public String lista_promocion_id;
    public String promocion_id;
    public String promocion_detalle_id;
    public String producto_id;
    public String producto;
    public String umd;
    public String cantidad;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String preciobase;
    public String chkdescuento;
    public String descuento;
    public boolean statusEdit;

    public PromocionDetalleSQLiteEntity(String compania_id, String lista_promocion_id, String promocion_id, String promocion_detalle_id, String producto_id, String producto, String umd, String cantidad, String fuerzatrabajo_id, String usuario_id, String preciobase, String chkdescuento, String descuento) {
        this.compania_id = compania_id;
        this.lista_promocion_id = lista_promocion_id;
        this.promocion_id = promocion_id;
        this.promocion_detalle_id = promocion_detalle_id;
        this.producto_id = producto_id;
        this.producto = producto;
        this.umd = umd;
        this.cantidad = cantidad;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.preciobase = preciobase;
        this.chkdescuento = chkdescuento;
        this.descuento = descuento;
    }

    public PromocionDetalleSQLiteEntity() {
    }

    public boolean isStatusEdit() {
        return statusEdit;
    }

    public void setStatusEdit(boolean statusEdit) {
        this.statusEdit = statusEdit;
    }

    public String getChkdescuento() {
        return chkdescuento;
    }

    public void setChkdescuento(String chkdescuento) {
        this.chkdescuento = chkdescuento;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(String preciobase) {
        this.preciobase = preciobase;
    }

    public String getLista_promocion_id() {
        return lista_promocion_id;
    }

    public void setLista_promocion_id(String lista_promocion_id) {
        this.lista_promocion_id = lista_promocion_id;
    }

    public String getPromocion_detalle_id() {
        return promocion_detalle_id;
    }

    public void setPromocion_detalle_id(String promocion_detalle_id) {
        this.promocion_detalle_id = promocion_detalle_id;
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

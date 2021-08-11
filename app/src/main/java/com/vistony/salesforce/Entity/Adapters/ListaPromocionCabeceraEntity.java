package com.vistony.salesforce.Entity.Adapters;

import com.vistony.salesforce.Entity.SQLite.PromocionDetalleSQLiteEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaPromocionCabeceraEntity implements Serializable {

    public String lista_promocion_id;
    public String promocion_id;
    public String producto;
    public String umd;
    public String cantidadcompra;
    public String  cantidadpromocion;
    public boolean estadoitems;
    public String preciobase;
    public ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities;
    public String producto_id;
    public String descuento;

    public ListaPromocionCabeceraEntity() {

    }

    public ListaPromocionCabeceraEntity(String lista_promocion_id, String promocion_id, String producto, String umd, String cantidadcompra, String cantidadpromocion, boolean estadoitems, String preciobase, ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities, String producto_id, String descuento) {
        this.lista_promocion_id = lista_promocion_id;
        this.promocion_id = promocion_id;
        this.producto = producto;
        this.umd = umd;
        this.cantidadcompra = cantidadcompra;
        this.cantidadpromocion = cantidadpromocion;
        this.estadoitems = estadoitems;
        this.preciobase = preciobase;
        this.listaPromocionDetalleEntities = listaPromocionDetalleEntities;
        this.producto_id = producto_id;
        this.descuento = descuento;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public ArrayList<PromocionDetalleSQLiteEntity> getListaPromocionDetalleEntities() {
        return listaPromocionDetalleEntities;
    }

    public void setListaPromocionDetalleEntities(ArrayList<PromocionDetalleSQLiteEntity> listaPromocionDetalleEntities) {
        this.listaPromocionDetalleEntities = listaPromocionDetalleEntities;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public String getPreciobase() {
        return preciobase;
    }

    public void setPreciobase(String preciobase) {
        this.preciobase = preciobase;
    }

    public boolean isEstadoitems() {
        return estadoitems;
    }

    public void setEstadoitems(boolean estadoitems) {
        this.estadoitems = estadoitems;
    }

    public String getLista_promocion_id() {
        return lista_promocion_id;
    }

    public void setLista_promocion_id(String lista_promocion_id) {
        this.lista_promocion_id = lista_promocion_id;
    }

    public String getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(String promocion_id) {
        this.promocion_id = promocion_id;
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

    public String getCantidadcompra() {
        return cantidadcompra;
    }

    public void setCantidadcompra(String cantidadcompra) {
        this.cantidadcompra = cantidadcompra;
    }

    public String getCantidadpromocion() {
        return cantidadpromocion;
    }

    public void setCantidadpromocion(String cantidadpromocion) {
        this.cantidadpromocion = cantidadpromocion;
    }
}

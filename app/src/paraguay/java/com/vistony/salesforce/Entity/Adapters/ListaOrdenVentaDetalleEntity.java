package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaOrdenVentaDetalleEntity implements Serializable  {
    public String orden_detalle_item;
    public String orden_detalle_producto_id;
    public String orden_detalle_producto;
    public String orden_detalle_umd;
    public String orden_detalle_stock_almacen;
    public String orden_detalle_cantidad;
    public String orden_detalle_precio_unitario;
    public String orden_detalle_montosubtotal;
    public String orden_detalle_porcentaje_descuento;
    public String orden_detalle_porcentaje_descuento_maximo;
    public String orden_detalle_monto_descuento;
    public String orden_detalle_monto_igv;
    public String orden_detalle_montototallinea;
    public ArrayList<ListaPromocionCabeceraEntity> orden_detalle_lista_promocion_cabecera;
    public String orden_detalle_promocion_habilitada;
    public String orden_detalle_gal;
    public String orden_detalle_gal_acumulado;
    public String orden_detalle_montosubtotalcondescuento;
    public ArrayList<ListaOrdenVentaDetallePromocionEntity> orden_detalle_lista_orden_detalle_promocion;
    public String orden_detalle_descuentocontado;
    public boolean orden_detalle_chk_descuentocontado;
    public String orden_detalle_terminopago_id;
    public boolean orden_detalle_chk_descuentocontado_aplicado;
    public boolean orden_detalle_chk_descuentocontado_cabecera;
    public String orden_detalle_cardcode;
    public String orden_detalle_oil_tax;
    public String orden_detalle_liter;
    public String orden_detalle_SIGAUS;
    public String orden_detalle_currency;

    public ListaOrdenVentaDetalleEntity(String orden_detalle_item, String orden_detalle_producto_id, String orden_detalle_producto, String orden_detalle_umd, String orden_detalle_stock_almacen, String orden_detalle_cantidad, String orden_detalle_precio_unitario, String orden_detalle_montosubtotal, String orden_detalle_porcentaje_descuento, String orden_detalle_porcentaje_descuento_maximo, String orden_detalle_monto_descuento, String orden_detalle_monto_igv, String orden_detalle_montototallinea, ArrayList<ListaPromocionCabeceraEntity> orden_detalle_lista_promocion_cabecera, String orden_detalle_promocion_habilitada, String orden_detalle_gal, String orden_detalle_gal_acumulado, String orden_detalle_montosubtotalcondescuento, ArrayList<ListaOrdenVentaDetallePromocionEntity> orden_detalle_lista_orden_detalle_promocion, String orden_detalle_descuentocontado, boolean orden_detalle_chk_descuentocontado, String orden_detalle_terminopago_id, boolean orden_detalle_chk_descuentocontado_aplicado
            , boolean orden_detalle_chk_descuentocontado_cabecera
            , String orden_detalle_cardcode
            , String orden_detalle_oil_tax
            , String orden_detalle_liter
            , String orden_detalle_SIGAUS
            , String orden_detalle_currency
    ) {
        this.orden_detalle_item = orden_detalle_item;
        this.orden_detalle_producto_id = orden_detalle_producto_id;
        this.orden_detalle_producto = orden_detalle_producto;
        this.orden_detalle_umd = orden_detalle_umd;
        this.orden_detalle_stock_almacen = orden_detalle_stock_almacen;
        this.orden_detalle_cantidad = orden_detalle_cantidad;
        this.orden_detalle_precio_unitario = orden_detalle_precio_unitario;
        this.orden_detalle_montosubtotal = orden_detalle_montosubtotal;
        this.orden_detalle_porcentaje_descuento = orden_detalle_porcentaje_descuento;
        this.orden_detalle_porcentaje_descuento_maximo = orden_detalle_porcentaje_descuento_maximo;
        this.orden_detalle_monto_descuento = orden_detalle_monto_descuento;
        this.orden_detalle_monto_igv = orden_detalle_monto_igv;
        this.orden_detalle_montototallinea = orden_detalle_montototallinea;
        this.orden_detalle_lista_promocion_cabecera = orden_detalle_lista_promocion_cabecera;
        this.orden_detalle_promocion_habilitada = orden_detalle_promocion_habilitada;
        this.orden_detalle_gal = orden_detalle_gal;
        this.orden_detalle_gal_acumulado = orden_detalle_gal_acumulado;
        this.orden_detalle_montosubtotalcondescuento = orden_detalle_montosubtotalcondescuento;
        this.orden_detalle_lista_orden_detalle_promocion = orden_detalle_lista_orden_detalle_promocion;
        this.orden_detalle_descuentocontado = orden_detalle_descuentocontado;
        this.orden_detalle_chk_descuentocontado = orden_detalle_chk_descuentocontado;
        this.orden_detalle_terminopago_id = orden_detalle_terminopago_id;
        this.orden_detalle_chk_descuentocontado_aplicado = orden_detalle_chk_descuentocontado_aplicado;
        this.orden_detalle_chk_descuentocontado_cabecera = orden_detalle_chk_descuentocontado_cabecera;
        this.orden_detalle_cardcode = orden_detalle_cardcode;
        this.orden_detalle_oil_tax = orden_detalle_oil_tax;
        this.orden_detalle_liter = orden_detalle_liter;
        this.orden_detalle_SIGAUS = orden_detalle_SIGAUS;
        this.orden_detalle_currency = orden_detalle_currency;

    }

    public ListaOrdenVentaDetalleEntity() {

    }

    public String getOrden_detalle_currency() {
        return orden_detalle_currency;
    }

    public void setOrden_detalle_currency(String orden_detalle_currency) {
        this.orden_detalle_currency = orden_detalle_currency;
    }

    public String getOrden_detalle_oil_tax() {
        return orden_detalle_oil_tax;
    }

    public void setOrden_detalle_oil_tax(String orden_detalle_oil_tax) {
        this.orden_detalle_oil_tax = orden_detalle_oil_tax;
    }

    public String getOrden_detalle_liter() {
        return orden_detalle_liter;
    }

    public void setOrden_detalle_liter(String orden_detalle_liter) {
        this.orden_detalle_liter = orden_detalle_liter;
    }

    public String getOrden_detalle_SIGAUS() {
        return orden_detalle_SIGAUS;
    }

    public void setOrden_detalle_SIGAUS(String orden_detalle_SIGAUS) {
        this.orden_detalle_SIGAUS = orden_detalle_SIGAUS;
    }

    public String getOrden_detalle_porcentaje_descuento_maximo() {
        return orden_detalle_porcentaje_descuento_maximo;
    }

    public void setOrden_detalle_porcentaje_descuento_maximo(String orden_detalle_porcentaje_descuento_maximo) {
        this.orden_detalle_porcentaje_descuento_maximo = orden_detalle_porcentaje_descuento_maximo;
    }

    public String getOrden_detalle_cardcode() {
        return orden_detalle_cardcode;
    }

    public void setOrden_detalle_cardcode(String orden_detalle_cardcode) {
        this.orden_detalle_cardcode = orden_detalle_cardcode;
    }

    public boolean isOrden_detalle_chk_descuentocontado_cabecera() {
        return orden_detalle_chk_descuentocontado_cabecera;
    }

    public void setOrden_detalle_chk_descuentocontado_cabecera(boolean orden_detalle_chk_descuentocontado_cabecera) {
        this.orden_detalle_chk_descuentocontado_cabecera = orden_detalle_chk_descuentocontado_cabecera;
    }

    public boolean isOrden_detalle_chk_descuentocontado_aplicado() {
        return orden_detalle_chk_descuentocontado_aplicado;
    }

    public void setOrden_detalle_chk_descuentocontado_aplicado(boolean orden_detalle_chk_descuentocontado_aplicado) {
        this.orden_detalle_chk_descuentocontado_aplicado = orden_detalle_chk_descuentocontado_aplicado;
    }

    public String getOrden_detalle_terminopago_id() {
        return orden_detalle_terminopago_id;
    }

    public void setOrden_detalle_terminopago_id(String orden_detalle_terminopago_id) {
        this.orden_detalle_terminopago_id = orden_detalle_terminopago_id;
    }

    public boolean isOrden_detalle_chk_descuentocontado() {
        return orden_detalle_chk_descuentocontado;
    }

    public void setOrden_detalle_chk_descuentocontado(boolean orden_detalle_chk_descuentocontado) {
        this.orden_detalle_chk_descuentocontado = orden_detalle_chk_descuentocontado;
    }

    public String getOrden_detalle_descuentocontado() {
        return orden_detalle_descuentocontado;
    }

    public void setOrden_detalle_descuentocontado(String orden_detalle_descuentocontado) {
        this.orden_detalle_descuentocontado = orden_detalle_descuentocontado;
    }

    public ArrayList<ListaOrdenVentaDetallePromocionEntity> getOrden_detalle_lista_orden_detalle_promocion() {
        return orden_detalle_lista_orden_detalle_promocion;
    }

    public void setOrden_detalle_lista_orden_detalle_promocion(ArrayList<ListaOrdenVentaDetallePromocionEntity> orden_detalle_lista_orden_detalle_promocion) {
        this.orden_detalle_lista_orden_detalle_promocion = orden_detalle_lista_orden_detalle_promocion;
    }

    public String getOrden_detalle_montosubtotalcondescuento() {
        return orden_detalle_montosubtotalcondescuento;
    }

    public void setOrden_detalle_montosubtotalcondescuento(String orden_detalle_montosubtotalcondescuento) {
        this.orden_detalle_montosubtotalcondescuento = orden_detalle_montosubtotalcondescuento;
    }

    public String getOrden_detalle_gal_acumulado() {
        return orden_detalle_gal_acumulado;
    }

    public void setOrden_detalle_gal_acumulado(String orden_detalle_gal_acumulado) {
        this.orden_detalle_gal_acumulado = orden_detalle_gal_acumulado;
    }

    public String getOrden_detalle_gal() {
        return orden_detalle_gal;
    }

    public void setOrden_detalle_gal(String orden_detalle_gal) {
        this.orden_detalle_gal = orden_detalle_gal;
    }

    public String getOrden_detalle_item() {
        return orden_detalle_item;
    }

    public void setOrden_detalle_item(String orden_detalle_item) {
        this.orden_detalle_item = orden_detalle_item;
    }

    public String getOrden_detalle_producto_id() {
        return orden_detalle_producto_id;
    }

    public void setOrden_detalle_producto_id(String orden_detalle_producto_id) {
        this.orden_detalle_producto_id = orden_detalle_producto_id;
    }

    public String getOrden_detalle_producto() {
        return orden_detalle_producto;
    }

    public void setOrden_detalle_producto(String orden_detalle_producto) {
        this.orden_detalle_producto = orden_detalle_producto;
    }

    public String getOrden_detalle_umd() {
        return orden_detalle_umd;
    }

    public void setOrden_detalle_umd(String orden_detalle_umd) {
        this.orden_detalle_umd = orden_detalle_umd;
    }

    public String getOrden_detalle_stock_almacen() {
        return orden_detalle_stock_almacen;
    }

    public void setOrden_detalle_stock_almacen(String orden_detalle_stock_almacen) {
        this.orden_detalle_stock_almacen = orden_detalle_stock_almacen;
    }

    public String getOrden_detalle_cantidad() {
        return orden_detalle_cantidad;
    }

    public void setOrden_detalle_cantidad(String orden_detalle_cantidad) {
        this.orden_detalle_cantidad = orden_detalle_cantidad;
    }

    public String getOrden_detalle_precio_unitario() {
        return orden_detalle_precio_unitario;
    }

    public void setOrden_detalle_precio_unitario(String orden_detalle_precio_unitario) {
        this.orden_detalle_precio_unitario = orden_detalle_precio_unitario;
    }

    public String getOrden_detalle_montosubtotal() {
        return orden_detalle_montosubtotal;
    }

    public void setOrden_detalle_montosubtotal(String orden_detalle_montosubtotal) {
        this.orden_detalle_montosubtotal = orden_detalle_montosubtotal;
    }

    public String getOrden_detalle_porcentaje_descuento() {
        return orden_detalle_porcentaje_descuento;
    }

    public void setOrden_detalle_porcentaje_descuento(String orden_detalle_porcentaje_descuento) {
        this.orden_detalle_porcentaje_descuento = orden_detalle_porcentaje_descuento;
    }

    public String getOrden_detalle_monto_descuento() {
        return orden_detalle_monto_descuento;
    }

    public void setOrden_detalle_monto_descuento(String orden_detalle_monto_descuento) {
        this.orden_detalle_monto_descuento = orden_detalle_monto_descuento;
    }

    public String getOrden_detalle_monto_igv() {
        return orden_detalle_monto_igv;
    }

    public void setOrden_detalle_monto_igv(String orden_detalle_monto_igv) {
        this.orden_detalle_monto_igv = orden_detalle_monto_igv;
    }

    public String getOrden_detalle_montototallinea() {
        return orden_detalle_montototallinea;
    }

    public void setOrden_detalle_montototallinea(String orden_detalle_montototallinea) {
        this.orden_detalle_montototallinea = orden_detalle_montototallinea;
    }

    public ArrayList<ListaPromocionCabeceraEntity> getOrden_detalle_lista_promocion_cabecera() {
        return orden_detalle_lista_promocion_cabecera;
    }

    public void setOrden_detalle_lista_promocion_cabecera(ArrayList<ListaPromocionCabeceraEntity> orden_detalle_lista_promocion_cabecera) {
        this.orden_detalle_lista_promocion_cabecera = orden_detalle_lista_promocion_cabecera;
    }

    public String getOrden_detalle_promocion_habilitada() {
        return orden_detalle_promocion_habilitada;
    }

    public void setOrden_detalle_promocion_habilitada(String orden_detalle_promocion_habilitada) {
        this.orden_detalle_promocion_habilitada = orden_detalle_promocion_habilitada;
    }
}

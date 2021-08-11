package com.vistony.salesforce.Entity.SQLite;

public class OrdenVentaDetalleSQLiteEntity {
    public String compania_id;
    public String ordenventa_id;
    public String lineaordenventa_id;
    public String producto_id;
    public String umd;
    public String cantidad;
    public String preciounitario;
    public String montosubtotal;
    public String porcentajedescuento;
    public String montodescuento;
    public String montoimpuesto;
    public String montototallinea;
    public String lineareferencia;
    public String impuesto_id;
    public String producto;
    public String AcctCode;
    public String almacen_id;
    public String promocion_id;
    public String gal_unitario;
    public String gal_acumulado;
    public String U_SYP_FECAT07;
    public String montosubtotalcondescuento;
    public String chk_descuentocontado;


    public OrdenVentaDetalleSQLiteEntity(String compania_id, String ordenventa_id, String lineaordenventa_id, String producto_id, String umd, String cantidad, String preciounitario, String montosubtotal, String porcentajedescuento, String montodescuento, String montoimpuesto, String montototallinea, String lineareferencia, String impuesto_id, String producto, String acctCode, String almacen_id, String promocion_id, String gal_unitario, String gal_acumulado, String u_SYP_FECAT07, String montosubtotalcondescuento, String chk_descuentocontado) {
        this.compania_id = compania_id;
        this.ordenventa_id = ordenventa_id;
        this.lineaordenventa_id = lineaordenventa_id;
        this.producto_id = producto_id;
        this.umd = umd;
        this.cantidad = cantidad;
        this.preciounitario = preciounitario;
        this.montosubtotal = montosubtotal;
        this.porcentajedescuento = porcentajedescuento;
        this.montodescuento = montodescuento;
        this.montoimpuesto = montoimpuesto;
        this.montototallinea = montototallinea;
        this.lineareferencia = lineareferencia;
        this.impuesto_id = impuesto_id;
        this.producto = producto;
        AcctCode = acctCode;
        this.almacen_id = almacen_id;
        this.promocion_id = promocion_id;
        this.gal_unitario = gal_unitario;
        this.gal_acumulado = gal_acumulado;
        U_SYP_FECAT07 = u_SYP_FECAT07;
        this.montosubtotalcondescuento = montosubtotalcondescuento;
        this.chk_descuentocontado = chk_descuentocontado;
    }

    public OrdenVentaDetalleSQLiteEntity() {
    }

    public String getChk_descuentocontado() {
        return chk_descuentocontado;
    }

    public void setChk_descuentocontado(String chk_descuentocontado) {
        this.chk_descuentocontado = chk_descuentocontado;
    }

    public String getMontosubtotalcondescuento() {
        return montosubtotalcondescuento;
    }

    public void setMontosubtotalcondescuento(String montosubtotalcondescuento) {
        this.montosubtotalcondescuento = montosubtotalcondescuento;
    }

    public String getU_SYP_FECAT07() {
        return U_SYP_FECAT07;
    }

    public void setU_SYP_FECAT07(String u_SYP_FECAT07) {
        U_SYP_FECAT07 = u_SYP_FECAT07;
    }

    public String getGal_unitario() {
        return gal_unitario;
    }

    public void setGal_unitario(String gal_unitario) {
        this.gal_unitario = gal_unitario;
    }

    public String getGal_acumulado() {
        return gal_acumulado;
    }

    public void setGal_acumulado(String gal_acumulado) {
        this.gal_acumulado = gal_acumulado;
    }

    public String getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(String promocion_id) {
        this.promocion_id = promocion_id;
    }

    public String getAlmacen_id() {
        return almacen_id;
    }

    public void setAlmacen_id(String almacen_id) {
        this.almacen_id = almacen_id;
    }

    public String getImpuesto_id() {
        return impuesto_id;
    }

    public void setImpuesto_id(String impuesto_id) {
        this.impuesto_id = impuesto_id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getAcctCode() {
        return AcctCode;
    }

    public void setAcctCode(String acctCode) {
        AcctCode = acctCode;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getOrdenventa_id() {
        return ordenventa_id;
    }

    public void setOrdenventa_id(String ordenventa_id) {
        this.ordenventa_id = ordenventa_id;
    }

    public String getLineaordenventa_id() {
        return lineaordenventa_id;
    }

    public void setLineaordenventa_id(String lineaordenventa_id) {
        this.lineaordenventa_id = lineaordenventa_id;
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

    public String getPreciounitario() {
        return preciounitario;
    }

    public void setPreciounitario(String preciounitario) {
        this.preciounitario = preciounitario;
    }

    public String getMontosubtotal() {
        return montosubtotal;
    }

    public void setMontosubtotal(String montosubtotal) {
        this.montosubtotal = montosubtotal;
    }

    public String getPorcentajedescuento() {
        return porcentajedescuento;
    }

    public void setPorcentajedescuento(String porcentajedescuento) {
        this.porcentajedescuento = porcentajedescuento;
    }

    public String getMontodescuento() {
        return montodescuento;
    }

    public void setMontodescuento(String montodescuento) {
        this.montodescuento = montodescuento;
    }

    public String getMontoimpuesto() {
        return montoimpuesto;
    }

    public void setMontoimpuesto(String montoimpuesto) {
        this.montoimpuesto = montoimpuesto;
    }

    public String getMontototallinea() {
        return montototallinea;
    }

    public void setMontototallinea(String montototallinea) {
        this.montototallinea = montototallinea;
    }

    public String getLineareferencia() {
        return lineareferencia;
    }

    public void setLineareferencia(String lineareferencia) {
        this.lineareferencia = lineareferencia;
    }
}

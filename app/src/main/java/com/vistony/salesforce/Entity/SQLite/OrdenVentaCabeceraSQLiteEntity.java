package com.vistony.salesforce.Entity.SQLite;

public class OrdenVentaCabeceraSQLiteEntity {
    public String compania_id;
    public String ordenventa_id;
    public String cliente_id;
    public String domembarque_id;
    public String terminopago_id;
    public String agencia_id;
    public String moneda_id;
    public String comentario;
    public String almacen_id;
    public String impuesto_id;
    public String montosubtotal;
    public String montodescuento;
    public String montoimpuesto;
    public String montototal;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String enviadoERP;
    public String recibidoERP;
    public String ordenventa_ERP_id;
    public String listaprecio_id;
    public String planta_id;
    public String fecharegistro;
    public String tipocambio;
    public String fechatipocambio;
    public String rucdni;
    public String U_SYP_MDTD;
    public String U_SYP_MDSD;
    public String U_SYP_MDCD;
    public String U_SYP_MDMT;
    public String U_SYP_STATUS;
    public String DocType;
    public String mensajeWS;
    public String total_gal_acumulado;
    public String  descuentocontado;

    public OrdenVentaCabeceraSQLiteEntity(String compania_id, String ordenventa_id, String cliente_id, String domembarque_id, String terminopago_id, String agencia_id, String moneda_id, String comentario, String almacen_id, String impuesto_id, String montosubtotal, String montodescuento, String montoimpuesto, String montototal, String fuerzatrabajo_id, String usuario_id, String enviadoERP, String recibidoERP, String ordenventa_ERP_id, String listaprecio_id, String planta_id, String fecharegistro, String tipocambio, String fechatipocambio, String rucdni, String u_SYP_MDTD, String u_SYP_MDSD, String u_SYP_MDCD, String u_SYP_MDMT, String u_SYP_STATUS, String docType, String mensajeWS, String total_gal_acumulado, String descuentocontado) {
        this.compania_id = compania_id;
        this.ordenventa_id = ordenventa_id;
        this.cliente_id = cliente_id;
        this.domembarque_id = domembarque_id;
        this.terminopago_id = terminopago_id;
        this.agencia_id = agencia_id;
        this.moneda_id = moneda_id;
        this.comentario = comentario;
        this.almacen_id = almacen_id;
        this.impuesto_id = impuesto_id;
        this.montosubtotal = montosubtotal;
        this.montodescuento = montodescuento;
        this.montoimpuesto = montoimpuesto;
        this.montototal = montototal;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.enviadoERP = enviadoERP;
        this.recibidoERP = recibidoERP;
        this.ordenventa_ERP_id = ordenventa_ERP_id;
        this.listaprecio_id = listaprecio_id;
        this.planta_id = planta_id;
        this.fecharegistro = fecharegistro;
        this.tipocambio = tipocambio;
        this.fechatipocambio = fechatipocambio;
        this.rucdni = rucdni;
        U_SYP_MDTD = u_SYP_MDTD;
        U_SYP_MDSD = u_SYP_MDSD;
        U_SYP_MDCD = u_SYP_MDCD;
        U_SYP_MDMT = u_SYP_MDMT;
        U_SYP_STATUS = u_SYP_STATUS;
        DocType = docType;
        this.mensajeWS = mensajeWS;
        this.total_gal_acumulado = total_gal_acumulado;
        this.descuentocontado = descuentocontado;
    }

    public OrdenVentaCabeceraSQLiteEntity() {
    }

    public String getDescuentocontado() {
        return descuentocontado;
    }

    public void setDescuentocontado(String descuentocontado) {
        this.descuentocontado = descuentocontado;
    }

    public String getTotal_gal_acumulado() {
        return total_gal_acumulado;
    }

    public void setTotal_gal_acumulado(String total_gal_acumulado) {
        this.total_gal_acumulado = total_gal_acumulado;
    }

    public String getMensajeWS() {
        return mensajeWS;
    }

    public void setMensajeWS(String mensajeWS) {
        this.mensajeWS = mensajeWS;
    }

    public String getTipocambio() {
        return tipocambio;
    }

    public void setTipocambio(String tipocambio) {
        this.tipocambio = tipocambio;
    }

    public String getFechatipocambio() {
        return fechatipocambio;
    }

    public void setFechatipocambio(String fechatipocambio) {
        this.fechatipocambio = fechatipocambio;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getU_SYP_MDTD() {
        return U_SYP_MDTD;
    }

    public void setU_SYP_MDTD(String u_SYP_MDTD) {
        U_SYP_MDTD = u_SYP_MDTD;
    }

    public String getU_SYP_MDSD() {
        return U_SYP_MDSD;
    }

    public void setU_SYP_MDSD(String u_SYP_MDSD) {
        U_SYP_MDSD = u_SYP_MDSD;
    }

    public String getU_SYP_MDCD() {
        return U_SYP_MDCD;
    }

    public void setU_SYP_MDCD(String u_SYP_MDCD) {
        U_SYP_MDCD = u_SYP_MDCD;
    }

    public String getU_SYP_MDMT() {
        return U_SYP_MDMT;
    }

    public void setU_SYP_MDMT(String u_SYP_MDMT) {
        U_SYP_MDMT = u_SYP_MDMT;
    }

    public String getU_SYP_STATUS() {
        return U_SYP_STATUS;
    }

    public void setU_SYP_STATUS(String u_SYP_STATUS) {
        U_SYP_STATUS = u_SYP_STATUS;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getListaprecio_id() {
        return listaprecio_id;
    }

    public void setListaprecio_id(String listaprecio_id) {
        this.listaprecio_id = listaprecio_id;
    }

    public String getPlanta_id() {
        return planta_id;
    }

    public void setPlanta_id(String planta_id) {
        this.planta_id = planta_id;
    }

    public String getFecharegistro() {
        return fecharegistro;
    }

    public void setFecharegistro(String fecharegistro) {
        this.fecharegistro = fecharegistro;
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

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getTerminopago_id() {
        return terminopago_id;
    }

    public void setTerminopago_id(String terminopago_id) {
        this.terminopago_id = terminopago_id;
    }

    public String getAgencia_id() {
        return agencia_id;
    }

    public void setAgencia_id(String agencia_id) {
        this.agencia_id = agencia_id;
    }

    public String getMoneda_id() {
        return moneda_id;
    }

    public void setMoneda_id(String moneda_id) {
        this.moneda_id = moneda_id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public String getMontosubtotal() {
        return montosubtotal;
    }

    public void setMontosubtotal(String montosubtotal) {
        this.montosubtotal = montosubtotal;
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

    public String getMontototal() {
        return montototal;
    }

    public void setMontototal(String montototal) {
        this.montototal = montototal;
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

    public String getEnviadoERP() {
        return enviadoERP;
    }

    public void setEnviadoERP(String enviadoERP) {
        this.enviadoERP = enviadoERP;
    }

    public String getRecibidoERP() {
        return recibidoERP;
    }

    public void setRecibidoERP(String recibidoERP) {
        this.recibidoERP = recibidoERP;
    }

    public String getOrdenventa_ERP_id() {
        return ordenventa_ERP_id;
    }

    public void setOrdenventa_ERP_id(String ordenventa_ERP_id) {
        this.ordenventa_ERP_id = ordenventa_ERP_id;
    }
}

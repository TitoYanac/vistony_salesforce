package com.vistony.salesforce.Entity.Adapters;

public class ListaHistoricoFacturasEntity {
    public String ordenventa_erp_id;
    public String montoimporteordenventa;
    public String cliente_id;
    public String rucdni;
    public String nombrecliente;
    public String documento_id;
    public String nrofactura;
    public String fechaemisionfactura;
    public String montoimportefactura;
    public String montosaldofactura;
    public String nombrechofer;
    public String fechaprogramaciondespacho;
    public String estadodespacho;
    public String motivoestadodespacho;
    public String terminopago;
    public String tipo_factura;

    public ListaHistoricoFacturasEntity(String ordenventa_erp_id, String montoimporteordenventa, String cliente_id, String rucdni, String nombrecliente, String documento_id, String nrofactura, String fechaemisionfactura, String montoimportefactura, String montosaldofactura, String nombrechofer, String fechaprogramaciondespacho, String estadodespacho, String motivoestadodespacho, String terminopago, String tipo_factura) {
        this.ordenventa_erp_id = ordenventa_erp_id;
        this.montoimporteordenventa = montoimporteordenventa;
        this.cliente_id = cliente_id;
        this.rucdni = rucdni;
        this.nombrecliente = nombrecliente;
        this.documento_id = documento_id;
        this.nrofactura = nrofactura;
        this.fechaemisionfactura = fechaemisionfactura;
        this.montoimportefactura = montoimportefactura;
        this.montosaldofactura = montosaldofactura;
        this.nombrechofer = nombrechofer;
        this.fechaprogramaciondespacho = fechaprogramaciondespacho;
        this.estadodespacho = estadodespacho;
        this.motivoestadodespacho = motivoestadodespacho;
        this.terminopago = terminopago;
        this.tipo_factura = tipo_factura;
    }

    public ListaHistoricoFacturasEntity() {
    }


    public String getTipo_factura() {
        return tipo_factura;
    }

    public void setTipo_factura(String tipo_factura) {
        this.tipo_factura = tipo_factura;
    }

    public String getOrdenventa_erp_id() {
        return ordenventa_erp_id;
    }

    public void setOrdenventa_erp_id(String ordenventa_erp_id) {
        this.ordenventa_erp_id = ordenventa_erp_id;
    }

    public String getMontoimporteordenventa() {
        return montoimporteordenventa;
    }

    public void setMontoimporteordenventa(String montoimporteordenventa) {
        this.montoimporteordenventa = montoimporteordenventa;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getFechaemisionfactura() {
        return fechaemisionfactura;
    }

    public void setFechaemisionfactura(String fechaemisionfactura) {
        this.fechaemisionfactura = fechaemisionfactura;
    }

    public String getMontoimportefactura() {
        return montoimportefactura;
    }

    public void setMontoimportefactura(String montoimportefactura) {
        this.montoimportefactura = montoimportefactura;
    }

    public String getMontosaldofactura() {
        return montosaldofactura;
    }

    public void setMontosaldofactura(String montosaldofactura) {
        this.montosaldofactura = montosaldofactura;
    }

    public String getNombrechofer() {
        return nombrechofer;
    }

    public void setNombrechofer(String nombrechofer) {
        this.nombrechofer = nombrechofer;
    }

    public String getFechaprogramaciondespacho() {
        return fechaprogramaciondespacho;
    }

    public void setFechaprogramaciondespacho(String fechaprogramaciondespacho) {
        this.fechaprogramaciondespacho = fechaprogramaciondespacho;
    }

    public String getEstadodespacho() {
        return estadodespacho;
    }

    public void setEstadodespacho(String estadodespacho) {
        this.estadodespacho = estadodespacho;
    }

    public String getMotivoestadodespacho() {
        return motivoestadodespacho;
    }

    public void setMotivoestadodespacho(String motivoestadodespacho) {
        this.motivoestadodespacho = motivoestadodespacho;
    }
    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
    }
}

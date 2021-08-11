package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class HistoricoFacturasEntity {
    @SerializedName("OrdenVenta_ERP_ID")
    private String ordenventa_erp_id;

    @SerializedName("MontoImporteOrdenVenta")
    private String montoimporteordenventa;

    @SerializedName("Cliente_ID")
    private String cliente_id;

    @SerializedName("RucDni")
    private String rucdni;

    @SerializedName("NombreCliente")
    private String nombrecliente;

    @SerializedName("Documento_ID")
    private String documento_id;

    @SerializedName("NroFactura")
    private String nrofactura;

    @SerializedName("FechaEmisionFactura")
    private String fechaemisionfactura;

    @SerializedName("MontoImporteFactura")
    private String montoimportefactura;

    @SerializedName("MontoSaldoFactura")
    private String montosaldofactura;

    @SerializedName("NombreChofer")
    private String nombrechofer;

    @SerializedName("FechaProgramacionDespacho")
    private String fechaprogramaciondespacho;

    @SerializedName("EstadoDespacho")
    private String estadodespacho;

    @SerializedName("MotivoEstadoDespacho")
    private String motivoestadodespacho;

    @SerializedName("TerminoPago")
    private String terminopago;

    @SerializedName("Tipo_Factura")
    private String tipo_factura;

    public String getTipo_factura() {
        return tipo_factura;
    }

    public void setTipo_factura(String tipo_factura) {
        this.tipo_factura = tipo_factura;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
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
}

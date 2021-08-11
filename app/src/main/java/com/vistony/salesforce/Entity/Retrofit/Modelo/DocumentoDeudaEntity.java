package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class DocumentoDeudaEntity {

    @SerializedName("CardCode")
    private String clienteId;

    @SerializedName("DocID")
    private String documentoId;

    @SerializedName("ShipToCode")
    private String domicilioEmbarqueId;

    @SerializedName("TaxDate")
    private String fechaEmision;

    @SerializedName("DueDate")
    private String fechaVencimiento;

    @SerializedName("DocTotal")
    private String importeFactura;

    @SerializedName("Currency")
    private String moneda;

    @SerializedName("InvoiceNum")
    private String nroFactura;

    @SerializedName("Balance")
    private String saldo;

    @SerializedName("RawBalance")
    private String saldoSinProcesar;

    @NonNull
    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NonNull String clienteId) {
        this.clienteId = clienteId;
    }

    @NonNull
    public String getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(@NonNull String documentoId) {
        this.documentoId = documentoId;
    }

    @NonNull
    public String getDomicilioEmbarqueId() {
        return domicilioEmbarqueId;
    }

    public void setDomicilioEmbarqueId(@NonNull String domicilioEmbarqueId) {
        this.domicilioEmbarqueId = domicilioEmbarqueId;
    }

    @NonNull
    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(@NonNull String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    @NonNull
    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(@NonNull String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    @NonNull
    public String getImporteFactura() {
        return importeFactura;
    }

    public void setImporteFactura(@NonNull String importeFactura) {
        this.importeFactura = importeFactura;
    }

    @NonNull
    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(@NonNull String moneda) {
        this.moneda = moneda;
    }

    @NonNull
    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(@NonNull String nroFactura) {
        this.nroFactura = nroFactura;
    }

    @NonNull
    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(@NonNull String saldo) {
        this.saldo = saldo;
    }

    @NonNull
    public String getSaldoSinProcesar() {
        return saldoSinProcesar;
    }

    public void setSaldoSinProcesar(@NonNull String saldoSinProcesar) {
        this.saldoSinProcesar = saldoSinProcesar;
    }
}

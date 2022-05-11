package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class InvoicesEntity {

    @SerializedName("DocEntry")
    private String docEntry;

    @SerializedName("DocNum")
    private String documentoId;

    @SerializedName("ShipToCode")
    private String domicilioEmbarqueId;

    @SerializedName("TaxDate")
    private String fechaEmision;

    @SerializedName("DueDate")
    private String fechaVencimiento;

    @SerializedName("DocTotal")
    private String importeFactura;

    @SerializedName("Balance")
    private String saldo;

    @SerializedName("Currency")
    private String moneda;

    @SerializedName("LegalNumber")
    private String nroFactura;

    @SerializedName("RawBalance")
    private String saldoSinProcesar;

    @SerializedName("IDDriver")
    private String iddriver;

    @SerializedName("Mobile")
    private String mobile;

    @SerializedName("U_VIS_SalesOrderID")
    private String U_VIS_SalesOrderID;

    @SerializedName("FechaDespacho")
    private String fechadespacho;

    @SerializedName("EstadoDespacho")
    private String estadodespacho;

    @SerializedName("LegalNumberDelivery")
    private String legalnumberdelivery;

    @SerializedName("U_SYP_DT_OCUR")
    private String U_SYP_DT_OCUR;

    @SerializedName("PymntGroup")
    private String pymntgroup;

    public String getIddriver() {
        return iddriver;
    }

    public void setIddriver(String iddriver) {
        this.iddriver = iddriver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getU_VIS_SalesOrderID() {
        return U_VIS_SalesOrderID;
    }

    public void setU_VIS_SalesOrderID(String u_VIS_SalesOrderID) {
        U_VIS_SalesOrderID = u_VIS_SalesOrderID;
    }

    public String getFechadespacho() {
        return fechadespacho;
    }

    public void setFechadespacho(String fechadespacho) {
        this.fechadespacho = fechadespacho;
    }

    public String getEstadodespacho() {
        return estadodespacho;
    }

    public void setEstadodespacho(String estadodespacho) {
        this.estadodespacho = estadodespacho;
    }

    public String getLegalnumberdelivery() {
        return legalnumberdelivery;
    }

    public void setLegalnumberdelivery(String legalnumberdelivery) {
        this.legalnumberdelivery = legalnumberdelivery;
    }

    public String getU_SYP_DT_OCUR() {
        return U_SYP_DT_OCUR;
    }

    public void setU_SYP_DT_OCUR(String u_SYP_DT_OCUR) {
        U_SYP_DT_OCUR = u_SYP_DT_OCUR;
    }

    public String getPymntgroup() {
        return pymntgroup;
    }

    public void setPymntgroup(String pymntgroup) {
        this.pymntgroup = pymntgroup;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String docEntry) {
        this.docEntry = docEntry;
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

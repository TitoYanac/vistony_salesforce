package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class QuotasPerCustomerInvoiceEntity {
    @NonNull
    @SerializedName("TaxDate")
    private String fechaemision;
    @NonNull
    @SerializedName("DueDate")
    private String fechavencimiento;
    @NonNull
    @SerializedName("NumInvoince")
    private String nrofactura;
    @NonNull
    @SerializedName("DocTotal")
    private String importefactura;
    @NonNull
    @SerializedName("PymntGroup")
    private String condicionpago;
    @NonNull
    @SerializedName("DueDays")
    private String diasmora;
    @NonNull
    @SerializedName("QuotaAmmount")
    private String montocuota;
    @NonNull
    @SerializedName("QuotaNumber")
    private String cuota;
    @NonNull
    @SerializedName("Type")
    private String tipo;
    @NonNull
    @SerializedName("Dues")
    private String diasvencimiento;
    @NonNull
    @SerializedName("Balance")
    private String saldo;


    @NonNull
    public String getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(@NonNull String fechaemision) {
        this.fechaemision = fechaemision;
    }

    @NonNull
    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(@NonNull String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    @NonNull
    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(@NonNull String nrofactura) {
        this.nrofactura = nrofactura;
    }

    @NonNull
    public String getImportefactura() {
        return importefactura;
    }

    public void setImportefactura(@NonNull String importefactura) {
        this.importefactura = importefactura;
    }

    @NonNull
    public String getCondicionpago() {
        return condicionpago;
    }

    public void setCondicionpago(@NonNull String condicionpago) {
        this.condicionpago = condicionpago;
    }

    @NonNull
    public String getDiasmora() {
        return diasmora;
    }

    public void setDiasmora(@NonNull String diasmora) {
        this.diasmora = diasmora;
    }

    @NonNull
    public String getMontocuota() {
        return montocuota;
    }

    public void setMontocuota(@NonNull String montocuota) {
        this.montocuota = montocuota;
    }

    @NonNull
    public String getCuota() {
        return cuota;
    }

    public void setCuota(@NonNull String cuota) {
        this.cuota = cuota;
    }

    @NonNull
    public String getTipo() {
        return tipo;
    }

    public void setTipo(@NonNull String tipo) {
        this.tipo = tipo;
    }

    @NonNull
    public String getDiasvencimiento() {
        return diasvencimiento;
    }

    public void setDiasvencimiento(@NonNull String diasvencimiento) {
        this.diasvencimiento = diasvencimiento;
    }

    @NonNull
    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(@NonNull String saldo) {
        this.saldo = saldo;
    }
}

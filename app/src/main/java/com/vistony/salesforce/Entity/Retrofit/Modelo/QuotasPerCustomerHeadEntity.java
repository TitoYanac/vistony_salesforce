package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class QuotasPerCustomerHeadEntity {
    @NonNull
    @SerializedName("PymntGrp")
    private String condicionPago;
    @NonNull
    @SerializedName("Tramo")
    private String tramo;
    @NonNull
    @SerializedName("Balance")
    private String saldo;
    @NonNull
    @SerializedName("Quota")
    private String cuotas;

    @NonNull
    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(@NonNull String condicionPago) {
        this.condicionPago = condicionPago;
    }

    @NonNull
    public String getTramo() {
        return tramo;
    }

    public void setTramo(@NonNull String tramo) {
        this.tramo = tramo;
    }

    @NonNull
    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(@NonNull String saldo) {
        this.saldo = saldo;
    }

    @NonNull
    public String getCuotas() {
        return cuotas;
    }

    public void setCuotas(@NonNull String cuotas) {
        this.cuotas = cuotas;
    }
}

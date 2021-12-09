package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class ResumenDiarioEntity {
    @NonNull
    @SerializedName("ID")
    private String id;
    @NonNull
    @SerializedName("Variable")
    private String variable;
    @NonNull
    @SerializedName("Monto")
    private String amount;
    @NonNull
    @SerializedName("UDM")
    private String udm;

    @NonNull
    private String fechasap;

    @NonNull
    public String getFechasap() {
        return fechasap;
    }

    public void setFechasap(@NonNull String fechasap) {
        this.fechasap = fechasap;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getVariable() {
        return variable;
    }

    public void setVariable(@NonNull String variable) {
        this.variable = variable;
    }

    @NonNull
    public String getAmount() {
        return amount;
    }

    public void setAmount(@NonNull String amount) {
        this.amount = amount;
    }

    @NonNull
    public String getUdm() {
        return udm;
    }

    public void setUdm(@NonNull String udm) {
        this.udm = udm;
    }
}

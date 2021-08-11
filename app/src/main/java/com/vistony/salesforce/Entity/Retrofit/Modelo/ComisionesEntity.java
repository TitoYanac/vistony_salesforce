package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class ComisionesEntity {
    @SerializedName("Variable")
    private String variable;

    @SerializedName("Uom")
    private String umd;

    @SerializedName("Advance")
    private String avance;

    @SerializedName("Quota")
    private String cuota;

    @SerializedName("Percentage")
    private String porcentajeavance;

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getPorcentajeavance() {
        return porcentajeavance;
    }

    public void setPorcentajeavance(String porcentajeavance) {
        this.porcentajeavance = porcentajeavance;
    }
}

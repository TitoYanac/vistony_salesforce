package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class HistoricContainerSalesEntity {
    @SerializedName("SlpCode")
    public String SlpCode;

    @SerializedName("userid")
    public String userid;

    @SerializedName("company")
    public String company;

    @SerializedName("Year")
    public String anio;

    @SerializedName("Month")
    public String mes;

    @SerializedName("Variable")
    public String variable;

    @SerializedName("TotalMN")
    public String montototal;

    @SerializedName("Galones")
    public String sumgaloun;

    @SerializedName("UOM")
    public String umd;

    public String fechasap;

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

    public String getSumgaloun() {
        return sumgaloun;
    }

    public void setSumgaloun(String sumgaloun) {
        this.sumgaloun = sumgaloun;
    }

    public String getFechasap() {
        return fechasap;
    }

    public void setFechasap(String fechasap) {
        this.fechasap = fechasap;
    }

    public String getSlpCode() {
        return SlpCode;
    }

    public void setSlpCode(String slpCode) {
        SlpCode = slpCode;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getMontototal() {
        return montototal;
    }

    public void setMontototal(String montototal) {
        this.montototal = montototal;
    }
}

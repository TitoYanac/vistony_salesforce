package com.vistony.salesforce.Entity.Retrofit.Modelo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class SettingsUserEntity {

    @SerializedName("Language")
    private String Language;

    @SerializedName("Receip")
    private Integer recibo;

    @SerializedName("CostCenter")
    private String centrocosto;

    @SerializedName("BusinessUnit")
    private String unidadnegocio;

    @SerializedName("ProductionLine")
    private String lineaproduccion;

    @SerializedName("DiscAccount")
    private String discAccount;

    @SerializedName("CogsAcct")
    private String cogsAcct;

    @SerializedName("TaxCode")
    private String taxCode;

    @SerializedName("TaxRate")
    private double taxRate;

    @SerializedName("OutStock")
    private String outStock;

    @SerializedName("Db")
    private String flagBackup;

    @NonNull
    @SerializedName("UsePrinter")
    private String usePrinter;

    @NonNull
    @SerializedName("ChangeCurrency")
    private String ChangeCurrency;

    @NonNull
    @SerializedName("MaxDateDeposit")
    private String maxDateDeposit;

    @NonNull
    @SerializedName("CashDscnt")
    private String cashDscnt;

    @NonNull
    public String getCashDscnt() {
        return cashDscnt;
    }

    public void setCashDscnt(@NonNull String cashDscnt) {
        this.cashDscnt = cashDscnt;
    }

    @NonNull
    public String getMaxDateDeposit() {
        return maxDateDeposit;
    }

    public void setMaxDateDeposit(@NonNull String maxDateDeposit) {
        this.maxDateDeposit = maxDateDeposit;
    }

    @NonNull
    public String getChangeCurrency() {
        return ChangeCurrency;
    }

    public void setChangeCurrency(@NonNull String changeCurrency) {
        ChangeCurrency = changeCurrency;
    }

    @NonNull
    public String getUsePrinter() {
        return usePrinter;
    }

    public void setUsePrinter(@NonNull String usePrinter) {
        this.usePrinter = usePrinter;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public Integer getRecibo() {
        return recibo;
    }

    public void setRecibo(Integer recibo) {
        this.recibo = recibo;
    }

    public String getCentrocosto() {
        return centrocosto;
    }

    public void setCentrocosto(String centrocosto) {
        this.centrocosto = centrocosto;
    }

    public String getUnidadnegocio() {
        return unidadnegocio;
    }

    public void setUnidadnegocio(String unidadnegocio) {
        this.unidadnegocio = unidadnegocio;
    }

    public String getLineaproduccion() {
        return lineaproduccion;
    }

    public void setLineaproduccion(String lineaproduccion) {
        this.lineaproduccion = lineaproduccion;
    }

    public String getDiscAccount() {
        return discAccount;
    }

    public void setDiscAccount(String discAccount) {
        this.discAccount = discAccount;
    }

    public String getCogsAcct() {
        return cogsAcct;
    }

    public void setCogsAcct(String cogsAcct) {
        this.cogsAcct = cogsAcct;
    }

    public String getOutStock() {
        return outStock;
    }

    public void setOutStock(String outStock) {
        this.outStock = outStock;
    }

    public String getFlagBackup() {
        return flagBackup;
    }

    public void setFlagBackup(String flagBackup) {
        this.flagBackup = flagBackup;
    }
}

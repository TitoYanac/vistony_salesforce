package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class WarehouseEntity {
    @SerializedName("WhsCode")
    private String WhsCode;

    @SerializedName("WhsName")
    private String WhsName;

    @SerializedName("PriceListCash")
    private String PriceListCash;

    @SerializedName("PriceListCredit")
    private String PriceListCredit;

    @SerializedName("U_VIST_SUCUSU")
    private String U_VIST_SUCUSU;

    public String getU_VIST_SUCUSU() {
        return U_VIST_SUCUSU;
    }

    public void setU_VIST_SUCUSU(String u_VIST_SUCUSU) {
        U_VIST_SUCUSU = u_VIST_SUCUSU;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public String getPriceListCash() {
        return PriceListCash;
    }

    public void setPriceListCash(String priceListCash) {
        PriceListCash = priceListCash;
    }

    public String getPriceListCredit() {
        return PriceListCredit;
    }

    public void setPriceListCredit(String priceListCredit) {
        PriceListCredit = priceListCredit;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getWhsName() {
        return WhsName;
    }

    public void setWhsName(String whsName) {
        WhsName = whsName;
    }
}

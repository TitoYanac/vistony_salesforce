package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class PriceListEntity {
    @SerializedName("PriceList_ID")
    private String pricelist_id;

    @SerializedName("PriceList")
    private String pricelist;

    public String getPricelist_id() {
        return pricelist_id;
    }

    public void setPricelist_id(String pricelist_id) {
        this.pricelist_id = pricelist_id;
    }

    public String getPricelist() {
        return pricelist;
    }

    public void setPricelist(String pricelist) {
        this.pricelist = pricelist;
    }
}

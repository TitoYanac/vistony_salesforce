package com.vistony.salesforce.Entity.Adapters;

public class ListaPriceListEntity {
    public String pricelist_id;
    public String pricelist;

    public ListaPriceListEntity(String pricelist_id, String pricelist) {
        this.pricelist_id = pricelist_id;
        this.pricelist = pricelist;
    }

    public ListaPriceListEntity() {
    }

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

package com.vistony.salesforce.Entity.SQLite;

public class PriceListSQLiteEntity {
    public String compania_id;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String pricelist_id;
    public String priceList;

    public PriceListSQLiteEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String pricelist_id, String priceList) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.pricelist_id = pricelist_id;
        this.priceList = priceList;
    }

    public PriceListSQLiteEntity() {
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getPricelist_id() {
        return pricelist_id;
    }

    public void setPricelist_id(String pricelist_id) {
        this.pricelist_id = pricelist_id;
    }

    public String getPriceList() {
        return priceList;
    }

    public void setPriceList(String priceList) {
        this.priceList = priceList;
    }
}

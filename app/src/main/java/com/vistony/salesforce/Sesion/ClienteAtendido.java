package com.vistony.salesforce.Sesion;

public class ClienteAtendido {
    private String PymntGroup;
    private String CardCode;
    private String Chkpricelist;
    private String PriceList_id;
    private String PriceList;
    private String Ubigeo_ID;
    private String Currency_ID;

    public String getCurrency_ID() {
        return Currency_ID;
    }

    public void setCurrency_ID(String currency_ID) {
        Currency_ID = currency_ID;
    }

    public String getUbigeo_ID() {
        return Ubigeo_ID;
    }

    public void setUbigeo_ID(String ubigeo_ID) {
        Ubigeo_ID = ubigeo_ID;
    }

    public String getPymntGroup() {
        return PymntGroup;
    }

    public void setPymntGroup(String pymntGroup) {
        PymntGroup = pymntGroup;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getChkpricelist() {
        return Chkpricelist;
    }

    public void setChkpricelist(String chkpricelist) {
        Chkpricelist = chkpricelist;
    }

    public String getPriceList_id() {
        return PriceList_id;
    }

    public void setPriceList_id(String priceList_id) {
        PriceList_id = priceList_id;
    }

    public String getPriceList() {
        return PriceList;
    }

    public void setPriceList(String priceList) {
        PriceList = priceList;
    }
}

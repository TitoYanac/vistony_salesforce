package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class SellerRouteEntity {
    @SerializedName("CardCode")
    private String CardCode;

    @SerializedName("Address")
    private String Address;

    @SerializedName("Chk_Visit")
    private String Chk_Visit;

    @SerializedName("Chk_Pedido")
    private String Chk_Pedido;

    @SerializedName("Chk_Cobranza")
    private String Chk_Cobranza;

    @SerializedName("Chk_Ruta")
    private String Chk_Ruta;

    @SerializedName("FechaRuta")
    private String FechaRuta;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getChk_Visit() {
        return Chk_Visit;
    }

    public void setChk_Visit(String chk_Visit) {
        Chk_Visit = chk_Visit;
    }

    public String getChk_Pedido() {
        return Chk_Pedido;
    }

    public void setChk_Pedido(String chk_Pedido) {
        Chk_Pedido = chk_Pedido;
    }

    public String getChk_Cobranza() {
        return Chk_Cobranza;
    }

    public void setChk_Cobranza(String chk_Cobranza) {
        Chk_Cobranza = chk_Cobranza;
    }

    public String getChk_Ruta() {
        return Chk_Ruta;
    }

    public void setChk_Ruta(String chk_Ruta) {
        Chk_Ruta = chk_Ruta;
    }

    public String getFechaRuta() {
        return FechaRuta;
    }

    public void setFechaRuta(String fechaRuta) {
        FechaRuta = fechaRuta;
    }
}

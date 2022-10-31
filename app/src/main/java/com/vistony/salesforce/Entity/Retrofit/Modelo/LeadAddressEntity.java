package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class LeadAddressEntity
{
    private String AddressCode;

    private String CardCode;

    private String Latitude;

    private String Longitude;

    private String Photo;

    @SerializedName("ErrorCode")
    public String haveError;

    @SerializedName("Message")
    public String Message;

    public String chk_recibido;

    public String fecha;

    public String domembarque_id;

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getChk_recibido() {
        return chk_recibido;
    }

    public void setChk_recibido(String chk_recibido) {
        this.chk_recibido = chk_recibido;
    }

    public String getHaveError() {
        return haveError;
    }

    public void setHaveError(String haveError) {
        this.haveError = haveError;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getAddressCode() {
        return AddressCode;
    }

    public void setAddressCode(String addressCode) {
        AddressCode = addressCode;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}

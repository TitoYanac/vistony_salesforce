package com.vistony.salesforce.Entity.SQLite;

import com.google.gson.annotations.SerializedName;

public class VisitaSQLiteEntity {

    @SerializedName("IdVisit")
    private String idVisit;

    private String CardCode;

    private String Address;

    private String Date;

    private String Hour;

    private String Territory;

    private String SlpCode;

    private String UserId;

    private String Type;

    private String Observation;

    private String Longitude;

    private String Latitude;

    private String compania_id;

    private String chkenviado;

    private String chkrecibido;

    @SerializedName("ErrorCode")
    private String haveError;

    @SerializedName("Message")
    private String Message;

    private String AppVersion;

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getIdVisit() {
        return idVisit;
    }

    public void setIdVisit(String idVisit) {
        this.idVisit = idVisit;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

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

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getTerritory() {
        return Territory;
    }

    public void setTerritory(String territory) {
        Territory = territory;
    }

    public String getSlpCode() {
        return SlpCode;
    }

    public void setSlpCode(String slpCode) {
        SlpCode = slpCode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getObservation() {
        return Observation;
    }

    public void setObservation(String observation) {
        Observation = observation;
    }

    public String getChkenviado() {
        return chkenviado;
    }

    public void setChkenviado(String chkenviado) {
        this.chkenviado = chkenviado;
    }

    public String getChkrecibido() {
        return chkrecibido;
    }

    public void setChkrecibido(String chkrecibido) {
        this.chkrecibido = chkrecibido;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getHaveError() {
        return haveError;
    }

    public void setHaveError(String haveError) {
        this.haveError = haveError;
    }
}
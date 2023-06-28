package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class SalesOrderEntity {

    @SerializedName("DocEntry")
    private String DocEntry;

    @SerializedName("DocNum")
    private String DocNum;

    @SerializedName("Message")
    private String Message;

    @SerializedName("SalesOrderID")
    private String SalesOrderID;

    @SerializedName("ErrorCode")
    private String errorCode;

    @SerializedName("CardCode")
    private String cardCode;

    @SerializedName("DocDate")
    private String docDate;

    @SerializedName("SlpCode")
    private String slpCode;

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getDocDate() {
        return docDate;
    }

    public void setDocDate(String docDate) {
        this.docDate = docDate;
    }

    public String getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(String slpCode) {
        this.slpCode = slpCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSalesOrderID() {
        return SalesOrderID;
    }

    public void setSalesOrderID(String salesOrderID) {
        SalesOrderID = salesOrderID;
    }
}

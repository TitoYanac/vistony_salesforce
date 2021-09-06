package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CobranzaItemEntity {

    @SerializedName("ItemDetail")
    private String itemDetail;

    @SerializedName("Code")
    private String code;

    @SerializedName("ErrorCode")
    private String errorCode;

    @SerializedName("Message")
    private String message;


    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

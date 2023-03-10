package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class DepositEntity {

    private String Status;
    private String Comments;
    private String CancelReason;
    private String AmountDeposit;
    private String Date;
    private String Banking;
    private String UserID;
    private String DirectDeposit;

    @SerializedName("Deposit")
    private String Deposit;

    private String POSPay;
    private String DeferredDate;
    private String IncomeType;
    private String BankID;
    private String CompanyCode;
    private String SlpCode;

    @SerializedName("ErrorCode")
    private String ErrorCode;
    @SerializedName("Message")
    private String Message;

    @SerializedName("Code")
    private String Code;
    private String AppVersion;
    private String Model;
    private String Brand;
    private String OSVersion;
    private String Intent;
    private String U_VIS_CollectionSalesPerson;

    public String getU_VIS_CollectionSalesPerson() {
        return U_VIS_CollectionSalesPerson;
    }

    public void setU_VIS_CollectionSalesPerson(String u_VIS_CollectionSalesPerson) {
        U_VIS_CollectionSalesPerson = u_VIS_CollectionSalesPerson;
    }

    public String getIntent() {
        return Intent;
    }

    public void setIntent(String intent) {
        Intent = intent;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getOSVersion() {
        return OSVersion;
    }

    public void setOSVersion(String OSVersion) {
        this.OSVersion = OSVersion;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDeposit() {
        return Deposit;
    }

    public void setDeposit(String deposit) {
        Deposit = deposit;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String cancelReason) {
        CancelReason = cancelReason;
    }

    public String getAmountDeposit() {
        return AmountDeposit;
    }

    public void setAmountDeposit(String amountDeposit) {
        AmountDeposit = amountDeposit;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBanking() {
        return Banking;
    }

    public void setBanking(String banking) {
        Banking = banking;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getDirectDeposit() {
        return DirectDeposit;
    }

    public void setDirectDeposit(String directDeposit) {
        DirectDeposit = directDeposit;
    }

    public String getPOSPay() {
        return POSPay;
    }

    public void setPOSPay(String POSPay) {
        this.POSPay = POSPay;
    }

    public String getDeferredDate() {
        return DeferredDate;
    }

    public void setDeferredDate(String deferredDate) {
        DeferredDate = deferredDate;
    }

    public String getIncomeType() {
        return IncomeType;
    }

    public void setIncomeType(String incomeType) {
        IncomeType = incomeType;
    }

    public String getBankID() {
        return BankID;
    }

    public void setBankID(String bankID) {
        BankID = bankID;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getSlpCode() {
        return SlpCode;
    }

    public void setSlpCode(String slpCode) {
        SlpCode = slpCode;
    }
}

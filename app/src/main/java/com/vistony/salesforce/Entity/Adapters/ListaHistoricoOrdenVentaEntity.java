package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaHistoricoOrdenVentaEntity implements Serializable {
    public String SalesOrderID;
    public String CardCode;
    public String LicTradNum;
    public String CardName;
    public String DocTotal;
    public String ApprovalStatus;
    public String ApprovalCommentary;
    public String DocNum;
    public boolean recepcionERPOV;
    public String comentariows;
    public boolean envioERPOV;
    public String Object;

    public String getObject() {
        return Object;
    }

    public void setObject(String object) {
        Object = object;
    }

    public boolean isRecepcionERPOV() {
        return recepcionERPOV;
    }

    public void setRecepcionERPOV(boolean recepcionERPOV) {
        this.recepcionERPOV = recepcionERPOV;
    }

    public String getComentariows() {
        return comentariows;
    }

    public void setComentariows(String comentariows) {
        this.comentariows = comentariows;
    }

    public boolean isEnvioERPOV() {
        return envioERPOV;
    }

    public void setEnvioERPOV(boolean envioERPOV) {
        this.envioERPOV = envioERPOV;
    }

    public String getSalesOrderID() {
        return SalesOrderID;
    }

    public void setSalesOrderID(String salesOrderID) {
        SalesOrderID = salesOrderID;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getLicTradNum() {
        return LicTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        LicTradNum = licTradNum;
    }

    public String getCardName() {
        return CardName;
    }

    public void setCardName(String cardName) {
        CardName = cardName;
    }

    public String getDocTotal() {
        return DocTotal;
    }

    public void setDocTotal(String docTotal) {
        DocTotal = docTotal;
    }

    public String getApprovalStatus() {
        return ApprovalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        ApprovalStatus = approvalStatus;
    }

    public String getApprovalCommentary() {
        return ApprovalCommentary;
    }

    public void setApprovalCommentary(String approvalCommentary) {
        ApprovalCommentary = approvalCommentary;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }
}

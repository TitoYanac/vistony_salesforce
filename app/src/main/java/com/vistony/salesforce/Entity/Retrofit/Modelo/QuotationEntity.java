package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class QuotationEntity {
    @SerializedName("Object")
    private String object;

    @SerializedName("DocNum")
    private String docnum;

    @SerializedName("DocEntry")
    private String docentry;

    @SerializedName("CardCode")
    private String cardcode;

    @SerializedName("LicTradNum")
    private String lictradnum;

    @SerializedName("DocDate")
    private String docdate;

    @SerializedName("CardName")
    private String cardname;

    @SerializedName("AmountTotal")
    private String amounttotal;

    @SerializedName("ApprovalStatus")
    private String approvalstatus;

    @SerializedName("SlpCode")
    private String slpcode;

    @SerializedName("Autorization")
    private String autorization;

    @SerializedName("ReadyGeneration")
    private String readygeneration;

    public QuotationEntity(String object, String docnum, String docentry, String cardcode, String lictradnum, String docdate, String cardname, String amounttotal, String approvalstatus, String slpcode, String autorization, String readygeneration) {
        this.object = object;
        this.docnum = docnum;
        this.docentry = docentry;
        this.cardcode = cardcode;
        this.lictradnum = lictradnum;
        this.docdate = docdate;
        this.cardname = cardname;
        this.amounttotal = amounttotal;
        this.approvalstatus = approvalstatus;
        this.slpcode = slpcode;
        this.autorization = autorization;
        this.readygeneration = readygeneration;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDocnum() {
        return docnum;
    }

    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getDocentry() {
        return docentry;
    }

    public void setDocentry(String docentry) {
        this.docentry = docentry;
    }

    public String getCardcode() {
        return cardcode;
    }

    public void setCardcode(String cardcode) {
        this.cardcode = cardcode;
    }

    public String getLictradnum() {
        return lictradnum;
    }

    public void setLictradnum(String lictradnum) {
        this.lictradnum = lictradnum;
    }

    public String getDocdate() {
        return docdate;
    }

    public void setDocdate(String docdate) {
        this.docdate = docdate;
    }

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getAmounttotal() {
        return amounttotal;
    }

    public void setAmounttotal(String amounttotal) {
        this.amounttotal = amounttotal;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

    public String getSlpcode() {
        return slpcode;
    }

    public void setSlpcode(String slpcode) {
        this.slpcode = slpcode;
    }

    public String getAutorization() {
        return autorization;
    }

    public void setAutorization(String autorization) {
        this.autorization = autorization;
    }

    public String getReadygeneration() {
        return readygeneration;
    }

    public void setReadygeneration(String readygeneration) {
        this.readygeneration = readygeneration;
    }
}

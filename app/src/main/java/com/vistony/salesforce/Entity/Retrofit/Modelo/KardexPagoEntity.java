package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class KardexPagoEntity {
    @SerializedName("CardCode")
    private String cardCode;

    @SerializedName("DocCur")
    private String docCur;

    @SerializedName("U_SYP_MDSD")
    private String u_SYP_MDSD;

    @SerializedName("U_SYP_MDCD")
    private String u_SYP_MDCD;

    @SerializedName("TaxDate")
    private String taxDate;

    @SerializedName("DocDueDate")
    private String docDueDate;

    @SerializedName("DocTotal")
    private String docTotal;

    @SerializedName("DocEntry")
    private String docEntry;

    @SerializedName("ObjType")
    private String objType;

    @SerializedName("Balance")
    private String sALDO;

    @SerializedName("CardName")
    private String cardName;

    @SerializedName("LicTradNum")
    private String licTradNum;

    @SerializedName("Mobile")
    private String phone1;

    @SerializedName("SlpCode")
    private String u_VIS_SlpCode;

    @SerializedName("Street")
    private String street;

    @SerializedName("PymntGroup")
    private String pymntGroup;

    @SerializedName("Block")
    private String u_SYP_DEPA;

    @SerializedName("County")
    private String u_SYP_PROV;

    @SerializedName("City")
    private String u_SYP_DIST;

    @SerializedName("Importe")
    private String importe;

    @SerializedName("AmountCharged")
    private String importeCobrado;

    @SerializedName("IncomeDate")
    private String fECHADEPAGO;

    @SerializedName("OperationNumber")
    private String nROOPERA;

    @SerializedName("DocNum")
    private String docNum;

    @SerializedName("JrnlMemo")
    private String jrnlMemo;

    @SerializedName("Comments")
    private String comments;

    @SerializedName("Bank")
    private String banco;

    @SerializedName("NumAtCard")
    private String numAtCard;

    public String getNumAtCard() {
        return numAtCard;
    }

    public void setNumAtCard(String numAtCard) {
        this.numAtCard = numAtCard;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getDocCur() {
        return docCur;
    }

    public void setDocCur(String docCur) {
        this.docCur = docCur;
    }

    public String getU_SYP_MDSD() {
        return u_SYP_MDSD;
    }

    public void setU_SYP_MDSD(String u_SYP_MDSD) {
        this.u_SYP_MDSD = u_SYP_MDSD;
    }

    public String getU_SYP_MDCD() {
        return u_SYP_MDCD;
    }

    public void setU_SYP_MDCD(String u_SYP_MDCD) {
        this.u_SYP_MDCD = u_SYP_MDCD;
    }

    public String getTaxDate() {
        return taxDate;
    }

    public void setTaxDate(String taxDate) {
        this.taxDate = taxDate;
    }

    public String getDocDueDate() {
        return docDueDate;
    }

    public void setDocDueDate(String docDueDate) {
        this.docDueDate = docDueDate;
    }

    public String getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(String docTotal) {
        this.docTotal = docTotal;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String docEntry) {
        this.docEntry = docEntry;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getsALDO() {
        return sALDO;
    }

    public void setsALDO(String sALDO) {
        this.sALDO = sALDO;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getLicTradNum() {
        return licTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        this.licTradNum = licTradNum;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getU_VIS_SlpCode() {
        return u_VIS_SlpCode;
    }

    public void setU_VIS_SlpCode(String u_VIS_SlpCode) {
        this.u_VIS_SlpCode = u_VIS_SlpCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPymntGroup() {
        return pymntGroup;
    }

    public void setPymntGroup(String pymntGroup) {
        this.pymntGroup = pymntGroup;
    }

    public String getU_SYP_DEPA() {
        return u_SYP_DEPA;
    }

    public void setU_SYP_DEPA(String u_SYP_DEPA) {
        this.u_SYP_DEPA = u_SYP_DEPA;
    }

    public String getU_SYP_PROV() {
        return u_SYP_PROV;
    }

    public void setU_SYP_PROV(String u_SYP_PROV) {
        this.u_SYP_PROV = u_SYP_PROV;
    }

    public String getU_SYP_DIST() {
        return u_SYP_DIST;
    }

    public void setU_SYP_DIST(String u_SYP_DIST) {
        this.u_SYP_DIST = u_SYP_DIST;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getImporteCobrado() {
        return importeCobrado;
    }

    public void setImporteCobrado(String importeCobrado) {
        this.importeCobrado = importeCobrado;
    }

    public String getfECHADEPAGO() {
        return fECHADEPAGO;
    }

    public void setfECHADEPAGO(String fECHADEPAGO) {
        this.fECHADEPAGO = fECHADEPAGO;
    }

    public String getnROOPERA() {
        return nROOPERA;
    }

    public void setnROOPERA(String nROOPERA) {
        this.nROOPERA = nROOPERA;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getJrnlMemo() {
        return jrnlMemo;
    }

    public void setJrnlMemo(String jrnlMemo) {
        this.jrnlMemo = jrnlMemo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}

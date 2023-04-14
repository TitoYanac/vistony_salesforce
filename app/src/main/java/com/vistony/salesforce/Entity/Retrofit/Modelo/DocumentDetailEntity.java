package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class DocumentDetailEntity {
    @SerializedName("DocEntry")
    public String DocEntry;

    @SerializedName("LineNum")
    public String LineNum;

    @SerializedName("ItemCode")
    public String ItemCode;

    @SerializedName("Dscription")
    public String Dscription;

    @SerializedName("Quantity")
    public String Quantity;

    @SerializedName("LineTotal")
    public String LineTotal;

    @SerializedName("WhsCode")
    public String WhsCode;

    @SerializedName("LineStatus")
    public String LineStatus;

    @SerializedName("TaxCode")
    public String TaxCode;

    @SerializedName("DiscPrcnt")
    public String DiscPrcnt;

    @SerializedName("TaxOnly")
    public String TaxOnly;

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public String getLineNum() {
        return LineNum;
    }

    public void setLineNum(String lineNum) {
        LineNum = lineNum;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getDscription() {
        return Dscription;
    }

    public void setDscription(String dscription) {
        Dscription = dscription;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(String lineTotal) {
        LineTotal = lineTotal;
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getLineStatus() {
        return LineStatus;
    }

    public void setLineStatus(String lineStatus) {
        LineStatus = lineStatus;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
    }

    public String getDiscPrcnt() {
        return DiscPrcnt;
    }

    public void setDiscPrcnt(String discPrcnt) {
        DiscPrcnt = discPrcnt;
    }

    public String getTaxOnly() {
        return TaxOnly;
    }

    public void setTaxOnly(String taxOnly) {
        TaxOnly = taxOnly;
    }
}

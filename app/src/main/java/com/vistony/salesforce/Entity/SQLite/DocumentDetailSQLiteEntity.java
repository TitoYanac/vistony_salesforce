package com.vistony.salesforce.Entity.SQLite;

public class DocumentDetailSQLiteEntity {
    public String compania_id;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String DocEntry;
    public String LineNum;
    public String ItemCode;
    public String Dscription;
    public String Quantity;
    public String LineTotal;
    public String WhsCode;
    public String LineStatus;
    public String TaxCode;
    public String DiscPrcnt;
    public String TaxOnly;

    public DocumentDetailSQLiteEntity(String compania_id, String fuerzatrabajo_id, String usuario_id, String docEntry, String lineNum, String itemCode, String dscription, String quantity, String lineTotal, String whsCode, String lineStatus, String taxCode, String discPrcnt, String taxOnly) {
        this.compania_id = compania_id;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        DocEntry = docEntry;
        LineNum = lineNum;
        ItemCode = itemCode;
        Dscription = dscription;
        Quantity = quantity;
        LineTotal = lineTotal;
        WhsCode = whsCode;
        LineStatus = lineStatus;
        TaxCode = taxCode;
        DiscPrcnt = discPrcnt;
        TaxOnly = taxOnly;
    }

    public DocumentDetailSQLiteEntity() {
    }

    public String getWhsCode() {
        return WhsCode;
    }

    public void setWhsCode(String whsCode) {
        WhsCode = whsCode;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

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


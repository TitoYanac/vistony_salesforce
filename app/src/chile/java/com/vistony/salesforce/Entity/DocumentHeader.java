package com.vistony.salesforce.Entity;

import java.util.List;

public class DocumentHeader {
    private String CardCode;
    private String Comments;
    private String DocCurrency;
    private String DocDate;
    private String DocDueDate;
    private String DocType;
    private String U_VIS_SalesOrderID;
    private String DocumentsOwner;
    private String FederalTaxID;
    private String PayToCode;
    private String PaymentGroupCode;
    private String SalesPersonCode;
    private String ShipToCode;
    private String TaxDate;
    private List<DocumentLine> DocumentLines;

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getDocCurrency() {
        return DocCurrency;
    }

    public void setDocCurrency(String docCurrency) {
        DocCurrency = docCurrency;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getDocDueDate() {
        return DocDueDate;
    }

    public void setDocDueDate(String docDueDate) {
        DocDueDate = docDueDate;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getU_VIS_SalesOrderID() {
        return U_VIS_SalesOrderID;
    }

    public void setU_VIS_SalesOrderID(String u_VIS_SalesOrderID) {
        U_VIS_SalesOrderID = u_VIS_SalesOrderID;
    }

    public String getDocumentsOwner() {
        return DocumentsOwner;
    }

    public void setDocumentsOwner(String documentsOwner) {
        DocumentsOwner = documentsOwner;
    }

    public String getFederalTaxID() {
        return FederalTaxID;
    }

    public void setFederalTaxID(String federalTaxID) {
        FederalTaxID = federalTaxID;
    }

    public String getPayToCode() {
        return PayToCode;
    }

    public void setPayToCode(String payToCode) {
        PayToCode = payToCode;
    }

    public String getPaymentGroupCode() {
        return PaymentGroupCode;
    }

    public void setPaymentGroupCode(String paymentGroupCode) {
        PaymentGroupCode = paymentGroupCode;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public String getShipToCode() {
        return ShipToCode;
    }

    public void setShipToCode(String shipToCode) {
        ShipToCode = shipToCode;
    }

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public List<DocumentLine> getDocumentLines() {
        return DocumentLines;
    }

    public void setDocumentLines(List<DocumentLine> documentLines) {
        this.DocumentLines = documentLines;
    }
}

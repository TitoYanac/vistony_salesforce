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
    private String PayToCode; //domicilio de factura
    private String PaymentGroupCode;
    private String SalesPersonCode;
    private String ShipToCode; //domicilio de embarque
    private String TaxDate;
    private String DiscountPercent;
    private String U_VIS_AgencyCode;
    private String U_VIS_AgencyRUC;
    private String U_VIS_AgencyName;
    private String U_VIS_AgencyDir;
    private List<ApprovalRequest> document_ApprovalRequests;
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

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }

    public List<ApprovalRequest> getDocument_ApprovalRequests() {
        return document_ApprovalRequests;
    }

    public void setDocument_ApprovalRequests(List<ApprovalRequest> document_ApprovalRequests) {
        this.document_ApprovalRequests = document_ApprovalRequests;
    }

    public String getU_VIS_AgencyCode() {
        return U_VIS_AgencyCode;
    }

    public void setU_VIS_AgencyCode(String u_VIS_AgencyCode) {
        U_VIS_AgencyCode = u_VIS_AgencyCode;
    }

    public String getU_VIS_AgencyRUC() {
        return U_VIS_AgencyRUC;
    }

    public void setU_VIS_AgencyRUC(String u_VIS_AgencyRUC) {
        U_VIS_AgencyRUC = u_VIS_AgencyRUC;
    }

    public String getU_VIS_AgencyName() {
        return U_VIS_AgencyName;
    }

    public void setU_VIS_AgencyName(String u_VIS_AgencyName) {
        U_VIS_AgencyName = u_VIS_AgencyName;
    }

    public String getU_VIS_AgencyDir() {
        return U_VIS_AgencyDir;
    }

    public void setU_VIS_AgencyDir(String u_VIS_AgencyDir) {
        U_VIS_AgencyDir = u_VIS_AgencyDir;
    }
}

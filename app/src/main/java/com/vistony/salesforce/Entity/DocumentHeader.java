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
    private String U_VIS_AgencyCode;
    private String U_VIS_AgencyRUC;
    private String U_VIS_AgencyName;
    private String U_VIS_AgencyDir;
    private String Branch;
    private String ApPrcnt;
    private String ApCredit;
    private String ApDues;
    private String ApTPag;
    private String AppVersion;
    private String quotation;
    private String DocRate;
    private String U_SYP_MDMT;
    private String U_SYP_TVENTA;
    private String U_SYP_VIST_TG;
    private String U_SYP_DOCEXPORT;
    private String U_SYP_FEEST;
    private String U_SYP_FEMEX;
    private String U_SYP_FETO;
    private String Draft;
    private String Model;
    private String Brand;
    private String OSVersion;
    private String Intent;
    private String Route;
    private String U_SYP_PDTREV;
    private String U_SYP_PDTCRE;
    private String U_VIT_VENMOS;

    public String getU_VIT_VENMOS() {
        return U_VIT_VENMOS;
    }

    public void setU_VIT_VENMOS(String u_VIT_VENMOS) {
        U_VIT_VENMOS = u_VIT_VENMOS;
    }

    public String getU_SYP_PDTREV() {
        return U_SYP_PDTREV;
    }

    public void setU_SYP_PDTREV(String u_SYP_PDTREV) {
        U_SYP_PDTREV = u_SYP_PDTREV;
    }

    public String getU_SYP_PDTCRE() {
        return U_SYP_PDTCRE;
    }

    public void setU_SYP_PDTCRE(String u_SYP_PDTCRE) {
        U_SYP_PDTCRE = u_SYP_PDTCRE;
    }

    public String getRoute() {
        return Route;
    }

    public void setRoute(String route) {
        Route = route;
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

    private List<DocumentLine> DocumentLines;

    public String getDraft() {
        return Draft;
    }

    public void setDraft(String draft) {
        Draft = draft;
    }

    public String getU_SYP_DOCEXPORT() {
        return U_SYP_DOCEXPORT;
    }

    public void setU_SYP_DOCEXPORT(String u_SYP_DOCEXPORT) {
        U_SYP_DOCEXPORT = u_SYP_DOCEXPORT;
    }

    public String getU_SYP_FEEST() {
        return U_SYP_FEEST;
    }

    public void setU_SYP_FEEST(String u_SYP_FEEST) {
        U_SYP_FEEST = u_SYP_FEEST;
    }

    public String getU_SYP_FEMEX() {
        return U_SYP_FEMEX;
    }

    public void setU_SYP_FEMEX(String u_SYP_FEMEX) {
        U_SYP_FEMEX = u_SYP_FEMEX;
    }

    public String getU_SYP_FETO() {
        return U_SYP_FETO;
    }

    public void setU_SYP_FETO(String u_SYP_FETO) {
        U_SYP_FETO = u_SYP_FETO;
    }

    public String getU_SYP_MDMT() {
        return U_SYP_MDMT;
    }

    public void setU_SYP_MDMT(String u_SYP_MDMT) {
        U_SYP_MDMT = u_SYP_MDMT;
    }

    public String getU_SYP_TVENTA() {
        return U_SYP_TVENTA;
    }

    public void setU_SYP_TVENTA(String u_SYP_TVENTA) {
        U_SYP_TVENTA = u_SYP_TVENTA;
    }

    public String getU_SYP_VIST_TG() {
        return U_SYP_VIST_TG;
    }

    public void setU_SYP_VIST_TG(String u_SYP_VIST_TG) {
        U_SYP_VIST_TG = u_SYP_VIST_TG;
    }

    public String getDocRate() {
        return DocRate;
    }

    public void setDocRate(String docRate) {
        DocRate = docRate;
    }

    public String getQuotation() {
        return quotation;
    }

    public void setQuotation(String quotation) {
        this.quotation = quotation;
    }

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

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getApPrcnt() {
        return ApPrcnt;
    }

    public void setApPrcnt(String apPrcnt) {
        ApPrcnt = apPrcnt;
    }

    public String getApCredit() {
        return ApCredit;
    }

    public void setApCredit(String apCredit) {
        ApCredit = apCredit;
    }

    public String getApDues() {
        return ApDues;
    }

    public void setApDues(String apDues) {
        ApDues = apDues;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getApTPag() {
        return ApTPag;
    }

    public void setApTPag(String apTPag) {
        ApTPag = apTPag;
    }
}

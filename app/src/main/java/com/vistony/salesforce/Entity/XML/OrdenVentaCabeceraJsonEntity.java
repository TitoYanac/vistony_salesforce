package com.vistony.salesforce.Entity.XML;

import java.util.List;

public class OrdenVentaCabeceraJsonEntity {
    public  String DocDate;
    public String DocDueDate;
    public String CardCode;
    public String DocCurrency;
    public String DocRate;
    public String TaxDate;
    public String U_SYP_MDTD;
    public String U_SYP_MDSD;
    public String U_SYP_MDCD;
    public String U_SYP_MDMT;
    public String U_SYP_STATUS;
    public String Comments;
    public String DocType;
    public String FederalTaxID;
    public String SalesPersonCode;
    public List<DocumentLines> DocumentLines;
    public String PaymentGroupCode;
    public String ShipToCode;
    public String PayToCode ;
    public String U_VIS_CompleteOV;
    public String DocumentsOwner;
    public String U_SYP_DOCEXPORT;
    public String U_SYP_FEEST;
    public String U_SYP_TVENTA;
    public String U_SYP_FETO;
    public String U_SYP_FEMEX;
    public String U_VIS_SalesOrderID;
    public String U_SYP_MDCT;
    public String U_VIS_OVCommentary;
    public String U_VIS_ENCommentary;
    public String U_VIS_INCommentary;
    public String U_VIST_SUCUSU;
    public String U_SYP_VIST_TG;
    public String U_VIS_AgencyCode;
    public String U_VIS_AgencyRUC;
    public String U_VIS_AgencyName;
    public String U_VIS_AgencyDir;

    public OrdenVentaCabeceraJsonEntity(String docDate, String docDueDate, String cardCode, String docCurrency, String docRate, String taxDate, String u_SYP_MDTD, String u_SYP_MDSD, String u_SYP_MDCD, String u_SYP_MDMT, String u_SYP_STATUS, String comments, String docType, String federalTaxID, String salesPersonCode, List<DocumentLines> documentLines, String paymentGroupCode, String shipToCode, String payToCode, String u_VIS_CompleteOV, String documentsOwner, String u_SYP_DOCEXPORT, String u_SYP_FEEST, String u_SYP_TVENTA, String u_SYP_FETO, String u_SYP_FEMEX, String u_VIS_SalesOrderID, String u_SYP_MDCT, String u_VIS_OVCommentary, String u_VIS_ENCommentary, String u_VIS_INCommentary, String u_VIST_SUCUSU, String u_SYP_VIST_TG, String u_VIS_AgencyCode, String u_VIS_AgencyRUC, String u_VIS_AgencyName, String u_VIS_AgencyDir) {
        DocDate = docDate;
        DocDueDate = docDueDate;
        CardCode = cardCode;
        DocCurrency = docCurrency;
        DocRate = docRate;
        TaxDate = taxDate;
        U_SYP_MDTD = u_SYP_MDTD;
        U_SYP_MDSD = u_SYP_MDSD;
        U_SYP_MDCD = u_SYP_MDCD;
        U_SYP_MDMT = u_SYP_MDMT;
        U_SYP_STATUS = u_SYP_STATUS;
        Comments = comments;
        DocType = docType;
        FederalTaxID = federalTaxID;
        SalesPersonCode = salesPersonCode;
        DocumentLines = documentLines;
        PaymentGroupCode = paymentGroupCode;
        ShipToCode = shipToCode;
        PayToCode = payToCode;
        U_VIS_CompleteOV = u_VIS_CompleteOV;
        DocumentsOwner = documentsOwner;
        U_SYP_DOCEXPORT = u_SYP_DOCEXPORT;
        U_SYP_FEEST = u_SYP_FEEST;
        U_SYP_TVENTA = u_SYP_TVENTA;
        U_SYP_FETO = u_SYP_FETO;
        U_SYP_FEMEX = u_SYP_FEMEX;
        U_VIS_SalesOrderID = u_VIS_SalesOrderID;
        U_SYP_MDCT = u_SYP_MDCT;
        U_VIS_OVCommentary = u_VIS_OVCommentary;
        U_VIS_ENCommentary = u_VIS_ENCommentary;
        U_VIS_INCommentary = u_VIS_INCommentary;
        U_VIST_SUCUSU = u_VIST_SUCUSU;
        U_SYP_VIST_TG = u_SYP_VIST_TG;
        U_VIS_AgencyCode = u_VIS_AgencyCode;
        U_VIS_AgencyRUC = u_VIS_AgencyRUC;
        U_VIS_AgencyName = u_VIS_AgencyName;
        U_VIS_AgencyDir = u_VIS_AgencyDir;
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

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getDocCurrency() {
        return DocCurrency;
    }

    public void setDocCurrency(String docCurrency) {
        DocCurrency = docCurrency;
    }

    public String getDocRate() {
        return DocRate;
    }

    public void setDocRate(String docRate) {
        DocRate = docRate;
    }

    public String getTaxDate() {
        return TaxDate;
    }

    public void setTaxDate(String taxDate) {
        TaxDate = taxDate;
    }

    public String getU_SYP_MDTD() {
        return U_SYP_MDTD;
    }

    public void setU_SYP_MDTD(String u_SYP_MDTD) {
        U_SYP_MDTD = u_SYP_MDTD;
    }

    public String getU_SYP_MDSD() {
        return U_SYP_MDSD;
    }

    public void setU_SYP_MDSD(String u_SYP_MDSD) {
        U_SYP_MDSD = u_SYP_MDSD;
    }

    public String getU_SYP_MDCD() {
        return U_SYP_MDCD;
    }

    public void setU_SYP_MDCD(String u_SYP_MDCD) {
        U_SYP_MDCD = u_SYP_MDCD;
    }

    public String getU_SYP_MDMT() {
        return U_SYP_MDMT;
    }

    public void setU_SYP_MDMT(String u_SYP_MDMT) {
        U_SYP_MDMT = u_SYP_MDMT;
    }

    public String getU_SYP_STATUS() {
        return U_SYP_STATUS;
    }

    public void setU_SYP_STATUS(String u_SYP_STATUS) {
        U_SYP_STATUS = u_SYP_STATUS;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getDocType() {
        return DocType;
    }

    public void setDocType(String docType) {
        DocType = docType;
    }

    public String getFederalTaxID() {
        return FederalTaxID;
    }

    public void setFederalTaxID(String federalTaxID) {
        FederalTaxID = federalTaxID;
    }

    public String getSalesPersonCode() {
        return SalesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        SalesPersonCode = salesPersonCode;
    }

    public List<DocumentLines> getDocumentLines() {
        return DocumentLines;
    }

    public void setDocumentLines(List<DocumentLines> documentLines) {
        DocumentLines = documentLines;
    }

    public String getPaymentGroupCode() {
        return PaymentGroupCode;
    }

    public void setPaymentGroupCode(String paymentGroupCode) {
        PaymentGroupCode = paymentGroupCode;
    }

    public String getShipToCode() {
        return ShipToCode;
    }

    public void setShipToCode(String shipToCode) {
        ShipToCode = shipToCode;
    }

    public String getPayToCode() {
        return PayToCode;
    }

    public void setPayToCode(String payToCode) {
        PayToCode = payToCode;
    }

    public String getU_VIS_CompleteOV() {
        return U_VIS_CompleteOV;
    }

    public void setU_VIS_CompleteOV(String u_VIS_CompleteOV) {
        U_VIS_CompleteOV = u_VIS_CompleteOV;
    }

    public String getDocumentsOwner() {
        return DocumentsOwner;
    }

    public void setDocumentsOwner(String documentsOwner) {
        DocumentsOwner = documentsOwner;
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

    public String getU_SYP_TVENTA() {
        return U_SYP_TVENTA;
    }

    public void setU_SYP_TVENTA(String u_SYP_TVENTA) {
        U_SYP_TVENTA = u_SYP_TVENTA;
    }

    public String getU_SYP_FETO() {
        return U_SYP_FETO;
    }

    public void setU_SYP_FETO(String u_SYP_FETO) {
        U_SYP_FETO = u_SYP_FETO;
    }

    public String getU_SYP_FEMEX() {
        return U_SYP_FEMEX;
    }

    public void setU_SYP_FEMEX(String u_SYP_FEMEX) {
        U_SYP_FEMEX = u_SYP_FEMEX;
    }

    public String getU_VIS_SalesOrderID() {
        return U_VIS_SalesOrderID;
    }

    public void setU_VIS_SalesOrderID(String u_VIS_SalesOrderID) {
        U_VIS_SalesOrderID = u_VIS_SalesOrderID;
    }

    public String getU_SYP_MDCT() {
        return U_SYP_MDCT;
    }

    public void setU_SYP_MDCT(String u_SYP_MDCT) {
        U_SYP_MDCT = u_SYP_MDCT;
    }

    public String getU_VIS_OVCommentary() {
        return U_VIS_OVCommentary;
    }

    public void setU_VIS_OVCommentary(String u_VIS_OVCommentary) {
        U_VIS_OVCommentary = u_VIS_OVCommentary;
    }

    public String getU_VIS_ENCommentary() {
        return U_VIS_ENCommentary;
    }

    public void setU_VIS_ENCommentary(String u_VIS_ENCommentary) {
        U_VIS_ENCommentary = u_VIS_ENCommentary;
    }

    public String getU_VIS_INCommentary() {
        return U_VIS_INCommentary;
    }

    public void setU_VIS_INCommentary(String u_VIS_INCommentary) {
        U_VIS_INCommentary = u_VIS_INCommentary;
    }

    public String getU_VIST_SUCUSU() {
        return U_VIST_SUCUSU;
    }

    public void setU_VIST_SUCUSU(String u_VIST_SUCUSU) {
        U_VIST_SUCUSU = u_VIST_SUCUSU;
    }

    public String getU_SYP_VIST_TG() {
        return U_SYP_VIST_TG;
    }

    public void setU_SYP_VIST_TG(String u_SYP_VIST_TG) {
        U_SYP_VIST_TG = u_SYP_VIST_TG;
    }

    public String getU_VIS_AgencyCode() {
        return U_VIS_AgencyCode;
    }

    public void setU_VIS_AgencyCode(String u_VIS_AgencyCode) {
        U_VIS_AgencyCode = u_VIS_AgencyCode;
    }
}

package com.vistony.salesforce.Entity.XML;

public class DocumentLines {
    public String ItemCode;
    public String WarehouseCode;
    public String Quantity;
    public String Dscription;
    //public String AcctCode;
    public String COGSAccountCode;
    public String Price;
    public String DiscountPercent;
    public String LineTotal;
    public String TaxCode;
    public String CostingCode;
    public String CostingCode2;
    public String CostingCode3;
    public String AcctCode;
    public String TaxOnly;
    public String U_SYP_FECAT07;
    public String U_VIS_PromID;
    public String U_VIS_PromLineID;
    public String U_VIST_CTAINGDCTO;

    public DocumentLines(String itemCode, String warehouseCode, String quantity, String dscription, String COGSAccountCode, String price, String discountPercent, String lineTotal, String taxCode, String costingCode, String costingCode2, String costingCode3, String acctCode, String taxOnly, String u_SYP_FECAT07, String u_VIS_PromID, String u_VIS_PromLineID, String u_VIST_CTAINGDCTO) {
        ItemCode = itemCode;
        WarehouseCode = warehouseCode;
        Quantity = quantity;
        Dscription = dscription;
        this.COGSAccountCode = COGSAccountCode;
        Price = price;
        DiscountPercent = discountPercent;
        LineTotal = lineTotal;
        TaxCode = taxCode;
        CostingCode = costingCode;
        CostingCode2 = costingCode2;
        CostingCode3 = costingCode3;
        AcctCode = acctCode;
        TaxOnly = taxOnly;
        U_SYP_FECAT07 = u_SYP_FECAT07;
        U_VIS_PromID = u_VIS_PromID;
        U_VIS_PromLineID = u_VIS_PromLineID;
        U_VIST_CTAINGDCTO = u_VIST_CTAINGDCTO;
    }

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
    }

    public String getAcctCode() {
        return AcctCode;
    }

    public void setAcctCode(String acctCode) {
        AcctCode = acctCode;
    }

    public String getTaxOnly() {
        return TaxOnly;
    }

    public void setTaxOnly(String taxOnly) {
        TaxOnly = taxOnly;
    }

    public String getU_SYP_FECAT07() {
        return U_SYP_FECAT07;
    }

    public void setU_SYP_FECAT07(String u_SYP_FECAT07) {
        U_SYP_FECAT07 = u_SYP_FECAT07;
    }

    public String getU_VIS_PromID() {
        return U_VIS_PromID;
    }

    public void setU_VIS_PromID(String u_VIS_PromID) {
        U_VIS_PromID = u_VIS_PromID;
    }

    public String getU_VIS_PromLineID() {
        return U_VIS_PromLineID;
    }

    public void setU_VIS_PromLineID(String u_VIS_PromLineID) {
        U_VIS_PromLineID = u_VIS_PromLineID;
    }

    public String getU_VIST_CTAINGDCTO() {
        return U_VIST_CTAINGDCTO;
    }

    public void setU_VIST_CTAINGDCTO(String u_VIST_CTAINGDCTO) {
        U_VIST_CTAINGDCTO = u_VIST_CTAINGDCTO;
    }

    public String getCostingCode() {
        return CostingCode;
    }

    public void setCostingCode(String costingCode) {
        CostingCode = costingCode;
    }

    public String getCostingCode2() {
        return CostingCode2;
    }

    public void setCostingCode2(String costingCode2) {
        CostingCode2 = costingCode2;
    }

    public String getCostingCode3() {
        return CostingCode3;
    }

    public void setCostingCode3(String costingCode3) {
        CostingCode3 = costingCode3;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getWarehouseCode() {
        return WarehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getDscription() {
        return Dscription;
    }

    public void setDscription(String dscription) {
        Dscription = dscription;
    }

    public String getCOGSAccountCode() {
        return COGSAccountCode;
    }

    public void setCOGSAccountCode(String COGSAccountCode) {
        this.COGSAccountCode = COGSAccountCode;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(String lineTotal) {
        LineTotal = lineTotal;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
    }
}

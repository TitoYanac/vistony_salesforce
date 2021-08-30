package com.vistony.salesforce.Entity;

public class DocumentLine {

    private String CostingCode;
    private String CostingCode2;
    private String DiscountPercent;
    private String ItemCode;
    private String Dscription;
    private String Price;
    private String Quantity;
    private String TaxCode;
    private String TaxOnly;
    private String WarehouseCode;

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

    public String getDiscountPercent() {
        return DiscountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        DiscountPercent = discountPercent;
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

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getTaxCode() {
        return TaxCode;
    }

    public void setTaxCode(String taxCode) {
        TaxCode = taxCode;
    }

    public String getTaxOnly() {
        return TaxOnly;
    }

    public void setTaxOnly(String taxOnly) {
        TaxOnly = taxOnly;
    }

    public String getWarehouseCode() {
        return WarehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        WarehouseCode = warehouseCode;
    }
}

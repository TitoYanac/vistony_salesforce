package com.vistony.salesforce.Entity.View;

public class TotalSalesOrder {
    private String subtotal;
    private String igv;
    private String total;
    private String descuento;
    private String galones;

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getGalones() {
        return galones;
    }

    public void setGalones(String galones) {
        this.galones = galones;
    }
}

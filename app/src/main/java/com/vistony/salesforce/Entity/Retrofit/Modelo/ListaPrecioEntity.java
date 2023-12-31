package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class ListaPrecioEntity {

    @SerializedName("Cash")
    private String contado;

    @SerializedName("Credit")
    private String credito;

    @SerializedName("ItemCode")
    private String producto_id;

    @SerializedName("ItemName")
    private String producto;

    @SerializedName("Uom")
    private String umd;

    @SerializedName("Gallons")
    private String gal;

    @SerializedName("Type")
    private String tipo;

    @SerializedName("DiscPrcnt")
    private String porcentaje_descuento;

    @SerializedName("WhsStock")
    private String stock_almacen;

    @SerializedName("StockTotal")
    private String stock_general;

    @SerializedName("CashDscnt")
    private String cashdscnt;

    @SerializedName("Units")
    private String unit;

    @SerializedName("OilTax")
    private String oiltax;

    @SerializedName("Liter")
    private String liter;

    @SerializedName("SIGAUS")
    private String SIGAUS;

    @SerializedName("MonedaAdicional")
    private String MonedaAdicional;

    @SerializedName("MonedaAdicionalContado")
    private String MonedaAdicionalContado;

    @SerializedName("MonedaAdicionalCredito")
    private String MonedaAdicionalCredito;

    @SerializedName("CodePriceListCash")
    private String CodePriceListCash;

    @SerializedName("CodePriceListCredit")
    private String CodePriceListCredit;

    @SerializedName("CodAlmacen")
    private String CodAlmacen;

    public String getCodAlmacen() {
        return CodAlmacen;
    }

    public void setCodAlmacen(String codAlmacen) {
        CodAlmacen = codAlmacen;
    }

    public String getCodePriceListCash() {
        return CodePriceListCash;
    }

    public void setCodePriceListCash(String codePriceListCash) {
        CodePriceListCash = codePriceListCash;
    }

    public String getCodePriceListCredit() {
        return CodePriceListCredit;
    }

    public void setCodePriceListCredit(String codePriceListCredit) {
        CodePriceListCredit = codePriceListCredit;
    }

    public String getMonedaAdicional() {
        return MonedaAdicional;
    }

    public void setMonedaAdicional(String monedaAdicional) {
        MonedaAdicional = monedaAdicional;
    }

    public String getMonedaAdicionalContado() {
        return MonedaAdicionalContado;
    }

    public void setMonedaAdicionalContado(String monedaAdicionalContado) {
        MonedaAdicionalContado = monedaAdicionalContado;
    }

    public String getMonedaAdicionalCredito() {
        return MonedaAdicionalCredito;
    }

    public void setMonedaAdicionalCredito(String monedaAdicionalCredito) {
        MonedaAdicionalCredito = monedaAdicionalCredito;
    }

    public String getOiltax() {
        return oiltax;
    }

    public void setOiltax(String oiltax) {
        this.oiltax = oiltax;
    }

    public String getLiter() {
        return liter;
    }

    public void setLiter(String liter) {
        this.liter = liter;
    }

    public String getSIGAUS() {
        return SIGAUS;
    }

    public void setSIGAUS(String SIGAUS) {
        this.SIGAUS = SIGAUS;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCashdscnt() {
        return cashdscnt;
    }

    public void setCashdscnt(String cashdscnt) {
        this.cashdscnt = cashdscnt;
    }

    public String getStock_almacen() {
        return stock_almacen;
    }

    public void setStock_almacen(String stock_almacen) {
        this.stock_almacen = stock_almacen;
    }

    public String getStock_general() {
        return stock_general;
    }

    public void setStock_general(String stock_general) {
        this.stock_general = stock_general;
    }

    public String getPorcentaje_descuento() {
        return porcentaje_descuento;
    }

    public void setPorcentaje_descuento(String porcentaje_descuento) {
        this.porcentaje_descuento = porcentaje_descuento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGal() {
        return gal;
    }

    public void setGal(String gal) {
        this.gal = gal;
    }

    public String getContado() {
        return contado;
    }

    public void setContado(String contado) {
        this.contado = contado;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(String producto_id) {
        this.producto_id = producto_id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }
}

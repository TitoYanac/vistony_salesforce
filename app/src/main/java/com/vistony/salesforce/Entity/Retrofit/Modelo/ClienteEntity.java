package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*Mismos attr fuerzaTrabajo con objtenerCliente*/

public class ClienteEntity {

    @SerializedName("CardCode")
    private String clienteId;

    @SerializedName("Address")
    private String direccion;

    @SerializedName("LicTradNum")
    private String LicTradNum;

    @SerializedName("PayToCode")
    private String domicilioFactura;

    @SerializedName("CardName")
    private String nombre;

    @SerializedName("VisitOrder")
    private String ordenVisita;

    @SerializedName("Territory")
    private String zona;

    @SerializedName("TerritoryId")
    private String zonaId;

    @SerializedName("Currency")
    private String moneda;

    @SerializedName("Phone")
    private String telefoFijo;

    @SerializedName("CellPhone")
    private String telefonoMovil;

    @SerializedName("Email")
    private String correo;

    @SerializedName("ZipCode")
    private String ubigeoId;

    @SerializedName("Category")
    private String categoria;

    @SerializedName("CreditLimit")
    private String linea_credito;

    @SerializedName("Balance")
    private String linea_credito_usado;

    @SerializedName("PymntGroup")
    private String terminoPago_id;

    @SerializedName("PriceList")
    private String lista_precio;

    @SerializedName("DueDays")
    private String diasVencidos;

    @SerializedName("Invoices")
    private List<InvoicesEntity> invoices;

    @SerializedName("Addresses")
    private List<AddressEntity> address;

    @SerializedName("LineOfBusiness")
    private String lineofbusiness;

    @SerializedName("LastPurchase")
    private String lastpurchase;

    @SerializedName("SinTerminoContado")
    private String statuscounted;

    @SerializedName("CustomerWhiteList")
    private String customerwhitelist;



    public String getCustomerwhitelist() {
        return customerwhitelist;
    }

    public void setCustomerwhitelist(String customerwhitelist) {
        this.customerwhitelist = customerwhitelist;
    }

    public String getStatuscounted() {
        return statuscounted;
    }

    public void setStatuscounted(String statuscounted) {
        this.statuscounted = statuscounted;
    }

    public String getLastpurchase() {
        return lastpurchase;
    }

    public void setLastpurchase(String lastpurchase) {
        this.lastpurchase = lastpurchase;
    }

    public String getLineofbusiness() {
        return lineofbusiness;
    }

    public void setLineofbusiness(String lineofbusiness) {
        this.lineofbusiness = lineofbusiness;
    }

    public String getLista_precio() {
        return lista_precio;
    }

    public void setLista_precio(String lista_precio) {
        this.lista_precio = lista_precio;
    }

    public String getLicTradNum() {
        return LicTradNum;
    }

    public void setLicTradNum(String licTradNum) {
        LicTradNum = licTradNum;
    }

    public List<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }

    public String getLinea_credito_usado() {
        return linea_credito_usado;
    }

    public void setLinea_credito_usado(String linea_credito_usado) {
        this.linea_credito_usado = linea_credito_usado;
    }

    public List<InvoicesEntity> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoicesEntity> invoices) {
        this.invoices = invoices;
    }

    public String getlinea_credito_usado() {
        return linea_credito_usado;
    }

    public void setlinea_credito_usado(String linea_credito_usado) {
        this.linea_credito_usado = linea_credito_usado;
    }

    public String getTerminoPago_id() {
        return terminoPago_id;
    }

    public void setTerminoPago_id(String terminoPago_id) {
        this.terminoPago_id = terminoPago_id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLinea_credito() {
        return linea_credito;
    }

    public void setLinea_credito(String linea_credito) {
        this.linea_credito = linea_credito;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDomicilioFactura() {
        return domicilioFactura;
    }

    public void setDomicilioFactura(String domicilioFactura) {
        this.domicilioFactura = domicilioFactura;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrdenVisita() {
        return ordenVisita;
    }

    public void setOrdenVisita(String ordenVisita) {
        this.ordenVisita = ordenVisita;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getZonaId() {
        return zonaId;
    }

    public void setZonaId(String zonaId) {
        this.zonaId = zonaId;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTelefoFijo() {
        return telefoFijo;
    }

    public void setTelefoFijo(String telefoFijo) {
        this.telefoFijo = telefoFijo;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUbigeoId() {
        return ubigeoId;
    }

    public void setUbigeoId(String ubigeoId) {
        this.ubigeoId = ubigeoId;
    }

    public String getDiasVencidos() {
        return diasVencidos;
    }

    public void setDiasVencidos(String diasVencidos) {
        this.diasVencidos = diasVencidos;
    }
}

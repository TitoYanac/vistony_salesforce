package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/*Mismos attr fuerzaTrabajo con objtenerCliente*/

public class CustomersEntity {

    @SerializedName("CardCode")
    private String clienteId;

    @SerializedName("Address")
    private String direccion;

    @SerializedName("ShipToCode")
    private String domicilioEmbarque;

    @SerializedName("CardName")
    private String nombre;

    @SerializedName("VisitOrder")
    private String ordenVisita;

    @SerializedName("Territory")
    private String zona;

    @SerializedName("TerritoryId")
    private String zonaId;

    @SerializedName("RucDni")
    private String documento;

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

    /*
    @SerializedName("Impuesto_ID")
    private String impuesto_id;

    @SerializedName("Impuesto")
    private String impuesto;

    @SerializedName("TipoCambio")
    private String tipocambio;
    */

    @SerializedName("Category")
    private String categoria;

    @SerializedName("CreditLimit")
    private String linea_credito;

    @SerializedName("Balance")
    private String linea_credito_usado;

    @SerializedName("PymntGroup")
    private String terminoPago_id;

    @SerializedName("Invoices")
    private List<InvoicesEntity> invoices;

    @SerializedName("Addresses")
    private List<AddressEntity> address;

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

/*
    public String getImpuesto_id() {
        return impuesto_id;
    }

    public void setImpuesto_id(String impuesto_id) {
        this.impuesto_id = impuesto_id;
    }

    public String getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(String impuesto) {
        this.impuesto = impuesto;
    }

    public String getTipocambio() {
        return tipocambio;
    }

    public void setTipocambio(String tipocambio) {
        this.tipocambio = tipocambio;
    }*/

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

    public String getDomicilioEmbarque() {
        return domicilioEmbarque;
    }

    public void setDomicilioEmbarque(String domicilioEmbarque) {
        this.domicilioEmbarque = domicilioEmbarque;
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

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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
}

package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class RutaVendedorEntity {

    @SerializedName("Cliente_ID")
    private String clienteId;

    @SerializedName("Direccion")
    private String direccion;

    @SerializedName("Domembarque_ID")
    private String domicilioEmbarque;

    @SerializedName("NombreCliente")
    private String nombre;

    @SerializedName("OrdenVisita")
    private String ordenVisita;

    @SerializedName("SaldoMN")
    private String saldomn;

    @SerializedName("Zona")
    private String zona;

    @SerializedName("Zona_ID")
    private String zona_Id;

    @SerializedName("Rucdni")
    private String rucdni;

    @SerializedName("Moneda")
    private String moneda;

    @SerializedName("TelefonoFijo")
    private String telefonoFijo;

    @SerializedName("TelefonoMovil")
    private String telefonoMovil;

    @SerializedName("Correo")
    private String correo;

    @SerializedName("Ubigeo_ID")
    private String ubigeo_id;

    @SerializedName("Impuesto_ID")
    private String impuesto_id;

    @SerializedName("Impuesto")
    private String impuesto;

    @SerializedName("TipoCambio")
    private String tipocambio;

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

    public String getSaldomn() {
        return saldomn;
    }

    public void setSaldomn(String saldomn) {
        this.saldomn = saldomn;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getZona_Id() {
        return zona_Id;
    }

    public void setZona_Id(String zona_Id) {
        this.zona_Id = zona_Id;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
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

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

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
    }
}

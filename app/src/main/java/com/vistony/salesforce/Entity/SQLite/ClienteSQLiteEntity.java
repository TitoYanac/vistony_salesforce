package com.vistony.salesforce.Entity.SQLite;

import com.vistony.salesforce.Entity.Retrofit.Modelo.AddressEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.InvoicesEntity;

import java.util.List;

public class ClienteSQLiteEntity {

    private String cliente_id;
    private String compania_id;
    private String nombrecliente;
    private String domembarque_id;
    private String direccion;
    private String zona_id;
    private String orden;
    private String zona;
    private String rucdni;
    private String moneda;
    private String telefonofijo;
    private String telefonomovil;
    private String correo;
    private String ubigeo_id;
    private String impuesto_id;
    private String impuesto;
    private String tipocambio;
    private String categoria;
    private String linea_credito;
    private String linea_credito_usado;
    private String terminopago_id;
    private String lista_precio;
    private List<AddressEntity> listAddress;
    private List<InvoicesEntity> listInvoice;


    public ClienteSQLiteEntity(){

    }

    public ClienteSQLiteEntity(String cliente_id, String compania_id, String nombrecliente, String domembarque_id, String direccion, String zona_id, String orden, String zona, String rucdni, String moneda, String telefonofijo, String telefonomovil, String correo, String ubigeo_id, String impuesto_id, String impuesto, String tipocambio, String categoria, String linea_credito,String linea_credito_usado, String terminopago_id,String lista_precio,List<AddressEntity> listAddress,List<InvoicesEntity> listInvoice) {
        this.cliente_id = cliente_id;
        this.compania_id = compania_id;
        this.nombrecliente = nombrecliente;
        this.domembarque_id = domembarque_id;
        this.direccion = direccion;
        this.zona_id = zona_id;
        this.orden = orden;
        this.zona = zona;
        this.rucdni = rucdni;
        this.moneda = moneda;
        this.telefonofijo = telefonofijo;
        this.telefonomovil = telefonomovil;
        this.correo = correo;
        this.ubigeo_id = ubigeo_id;
        this.impuesto_id = impuesto_id;
        this.impuesto = impuesto;
        this.tipocambio = tipocambio;
        this.categoria = categoria;
        this.linea_credito = linea_credito;
        this.linea_credito_usado=linea_credito_usado;
        this.terminopago_id = terminopago_id;
        this.lista_precio=lista_precio;
        this.listAddress=listAddress;
        this.listInvoice=listInvoice;
    }

    public String getLista_precio() {
        return lista_precio;
    }

    public void setLista_precio(String lista_precio) {
        this.lista_precio = lista_precio;
    }

    public List<AddressEntity> getListAddress() {
        return listAddress;
    }

    public void setListAddress(List<AddressEntity> listAddress) {
        this.listAddress = listAddress;
    }

    public List<InvoicesEntity> getListInvoice() {
        return listInvoice;
    }

    public void setListInvoice(List<InvoicesEntity> listInvoice) {
        this.listInvoice = listInvoice;
    }

    public String getLinea_credito_usado() {
        return linea_credito_usado;
    }

    public void setLinea_credito_usado(String linea_credito_usado) {
        this.linea_credito_usado = linea_credito_usado;
    }

    public String getTerminopago_id() {
        return terminopago_id;
    }

    public void setTerminopago_id(String terminopago_id) {
        this.terminopago_id = terminopago_id;
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

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getTelefonomovil() {
        return telefonomovil;
    }

    public void setTelefonomovil(String telefonomovil) {
        this.telefonomovil = telefonomovil;
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



package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaConsClienteCabeceraEntity implements Serializable {
    public String cliente_id;
    public String nombrecliente;
    public String direccion;
    public String saldo;
    public int imvclientecabecera;
    public String moneda;
    public String domembarque_id;
    public String impuesto_id;
    public String impuesto;
    public String rucdni;
    public String categoria;
    public String linea_credito;
    public String terminopago_id;
    public String zona_id;
    public String compania_id;
    public String ordenvisita;
    public String zona;
    public String telefonofijo;
    public String telefonomovil;
    public String correo;
    public String ubigeo_id;
    public String tipocambio;
    public String chk_visita;
    public String chk_pedido;
    public String chk_cobranza;
    public String chk_ruta;
    public String fecharuta;
    public String linea_credito_usado;


    public ListaConsClienteCabeceraEntity(String cliente_id, String nombrecliente, String direccion, String saldo, int imvclientecabecera, String moneda, String domembarque_id, String impuesto_id, String impuesto, String rucdni, String categoria, String linea_credito,String linea_credito_usado, String terminopago_id, String zona_id, String compania_id, String ordenvisita, String zona, String telefonofijo, String telefonomovil, String correo, String ubigeo_id, String tipocambio, String chk_visita, String chk_pedido, String chk_cobranza, String chk_ruta, String fecharuta) {
        this.cliente_id = cliente_id;
        this.nombrecliente = nombrecliente;
        this.direccion = direccion;
        this.saldo = saldo;
        this.imvclientecabecera = imvclientecabecera;
        this.moneda = moneda;
        this.domembarque_id = domembarque_id;
        this.impuesto_id = impuesto_id;
        this.impuesto = impuesto;
        this.rucdni = rucdni;
        this.categoria = categoria;
        this.linea_credito = linea_credito;
        this.linea_credito_usado=linea_credito_usado;
        this.terminopago_id = terminopago_id;
        this.zona_id = zona_id;
        this.compania_id = compania_id;
        this.ordenvisita = ordenvisita;
        this.zona = zona;
        this.telefonofijo = telefonofijo;
        this.telefonomovil = telefonomovil;
        this.correo = correo;
        this.ubigeo_id = ubigeo_id;
        this.tipocambio = tipocambio;
        this.chk_visita = chk_visita;
        this.chk_pedido = chk_pedido;
        this.chk_cobranza = chk_cobranza;
        this.chk_ruta = chk_ruta;
        this.fecharuta = fecharuta;
    }

    public ListaConsClienteCabeceraEntity() {

    }

    public String getLinea_credito_usado() {
        return linea_credito_usado;
    }

    public void setLinea_credito_usado(String linea_credito_usado) {
        this.linea_credito_usado = linea_credito_usado;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getOrdenvisita() {
        return ordenvisita;
    }

    public void setOrdenvisita(String ordenvisita) {
        this.ordenvisita = ordenvisita;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public String getTipocambio() {
        return tipocambio;
    }

    public void setTipocambio(String tipocambio) {
        this.tipocambio = tipocambio;
    }

    public String getChk_visita() {
        return chk_visita;
    }

    public void setChk_visita(String chk_visita) {
        this.chk_visita = chk_visita;
    }

    public String getChk_pedido() {
        return chk_pedido;
    }

    public void setChk_pedido(String chk_pedido) {
        this.chk_pedido = chk_pedido;
    }

    public String getChk_cobranza() {
        return chk_cobranza;
    }

    public void setChk_cobranza(String chk_cobranza) {
        this.chk_cobranza = chk_cobranza;
    }

    public String getChk_ruta() {
        return chk_ruta;
    }

    public void setChk_ruta(String chk_ruta) {
        this.chk_ruta = chk_ruta;
    }

    public String getFecharuta() {
        return fecharuta;
    }

    public void setFecharuta(String fecharuta) {
        this.fecharuta = fecharuta;
    }

    public String getZona_id() {
        return zona_id;
    }

    public void setZona_id(String zona_id) {
        this.zona_id = zona_id;
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

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
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

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getImvclientecabecera() {
        return imvclientecabecera;
    }

    public void setImvclientecabecera(int imvclientecabecera) {
        this.imvclientecabecera = imvclientecabecera;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }
}

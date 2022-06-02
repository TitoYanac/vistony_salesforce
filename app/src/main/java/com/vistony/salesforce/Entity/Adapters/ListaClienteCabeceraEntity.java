package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaClienteCabeceraEntity implements Serializable {
    private String cliente_id;
    private String nombrecliente;
    private String direccion;
    private String saldo;
    private int imvclientecabecera;
    private String moneda;
    private String domembarque_id;
    private String domfactura_id;
    private String impuesto_id;
    private String impuesto;
    private String rucdni;
    private String categoria;
    private String linea_credito;
    private String terminopago_id;
    private String zona_id;
    private String compania_id;
    private String ordenvisita;
    private String zona;
    private String telefonofijo;
    private String telefonomovil;
    private String correo;
    private String ubigeo_id;
    private String tipocambio;
    private String chk_visita;
    private String chk_pedido;
    private String chk_cobranza;
    private String chk_ruta;
    private String linea_credito_usado;
    private String fecharuta;
    private String lista_precio;
    private String docentry;
    private String lastpurchase;
    private String lineofbussiness;
    private String saldosincontados;
    private String chkgeolocation;
    private String chkvisitsection;
    private String terminopago;
    private String contado;

    public ListaClienteCabeceraEntity(
            String cliente_id,
            String nombrecliente,
            String direccion,
            String saldo,
            int imvclientecabecera, String moneda, String domembarque_id, String domfactura_id, String impuesto_id, String impuesto, String rucdni, String categoria, String linea_credito, String terminopago_id, String zona_id, String compania_id, String ordenvisita, String zona, String telefonofijo, String telefonomovil, String correo, String ubigeo_id, String tipocambio, String chk_visita, String chk_pedido, String chk_cobranza, String chk_ruta, String linea_credito_usado, String fecharuta, String lista_precio, String docentry, String lastpurchase, String lineofbussiness, String saldosincontados,String chkgeolocation,String chkvisitsection,String terminopago,String contado) {
        this.cliente_id = cliente_id;
        this.nombrecliente = nombrecliente;
        this.direccion = direccion;
        this.saldo = saldo;
        this.imvclientecabecera = imvclientecabecera;
        this.moneda = moneda;
        this.domembarque_id = domembarque_id;
        this.domfactura_id = domfactura_id;
        this.impuesto_id = impuesto_id;
        this.impuesto = impuesto;
        this.rucdni = rucdni;
        this.categoria = categoria;
        this.linea_credito = linea_credito;
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
        this.linea_credito_usado = linea_credito_usado;
        this.fecharuta = fecharuta;
        this.lista_precio = lista_precio;
        this.docentry = docentry;
        this.lastpurchase = lastpurchase;
        this.lineofbussiness = lineofbussiness;
        this.saldosincontados = saldosincontados;
        this.chkgeolocation = chkgeolocation;
        this.chkvisitsection = chkvisitsection;
        this.terminopago = terminopago;
        this.contado = contado;

    }

    public String getContado() {
        return contado;
    }

    public void setContado(String contado) {
        this.contado = contado;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
    }

    public String getChkvisitsection() {
        return chkvisitsection;
    }

    public void setChkvisitsection(String chkvisitsection) {
        this.chkvisitsection = chkvisitsection;
    }

    public String getChkgeolocation() {
        return chkgeolocation;
    }

    public void setChkgeolocation(String chkgeolocation) {
        this.chkgeolocation = chkgeolocation;
    }

    public String getSaldosincontados() {
        return saldosincontados;
    }

    public void setSaldosincontados(String saldosincontados) {
        this.saldosincontados = saldosincontados;
    }

    public String getLineofbussiness() {
        return lineofbussiness;
    }

    public void setLineofbussiness(String lineofbussiness) {
        this.lineofbussiness = lineofbussiness;
    }

    public String getLastpurchase() {
        return lastpurchase;
    }

    public void setLastpurchase(String lastpurchase) {
        this.lastpurchase = lastpurchase;
    }

    public String getDocentry() {
        return docentry;
    }

    public void setDocentry(String docentry) {
        this.docentry = docentry;
    }

    public String getDomfactura_id() {
        return domfactura_id;
    }

    public void setDomfactura_id(String domfactura_id) {
        this.domfactura_id = domfactura_id;
    }

    public String getLista_precio() {
        return lista_precio;
    }

    public void setLista_precio(String lista_precio) {
        this.lista_precio = lista_precio;
    }

    public String getLinea_credito_usado() {
        return linea_credito_usado;
    }

    public void setLinea_credito_usado(String linea_credito_usado) {
        this.linea_credito_usado = linea_credito_usado;
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

    public String getTipocambio() {
        return tipocambio;
    }

    public void setTipocambio(String tipocambio) {
        this.tipocambio = tipocambio;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
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

    public ListaClienteCabeceraEntity(){}

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

package com.vistony.salesforce.Entity.Adapters;

public class ListaCobranzaDetalleEntity {
    public String cliente_id;
    public String nombrecliente;
    public String documento_id;
    public String nrodocumento;
    public String saldo;
    public String cobrado;
    public String nuevosaldo;

    public ListaCobranzaDetalleEntity() {
    }

    public ListaCobranzaDetalleEntity(String cliente_id, String nombrecliente, String documento_id, String nrodocumento, String saldo, String cobrado, String nuevosaldo) {
        this.cliente_id = cliente_id;
        this.nombrecliente = nombrecliente;
        this.documento_id = documento_id;
        this.nrodocumento = nrodocumento;
        this.saldo = saldo;
        this.cobrado = cobrado;
        this.nuevosaldo = nuevosaldo;
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

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCobrado() {
        return cobrado;
    }

    public void setCobrado(String cobrado) {
        this.cobrado = cobrado;
    }

    public String getNuevosaldo() {
        return nuevosaldo;
    }

    public void setNuevosaldo(String nuevosaldo) {
        this.nuevosaldo = nuevosaldo;
    }
}

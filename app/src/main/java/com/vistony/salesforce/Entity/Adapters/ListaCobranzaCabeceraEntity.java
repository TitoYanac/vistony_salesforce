package com.vistony.salesforce.Entity.Adapters;

public class ListaCobranzaCabeceraEntity {
    public String cliente_id;
    public String nrodocumento;
    public String saldocobrado;
    public int idetalle;

    public ListaCobranzaCabeceraEntity(String cliente_id, String nrodocumento, String saldocobrado, int idetalle) {
        this.cliente_id = cliente_id;
        this.nrodocumento = nrodocumento;
        this.saldocobrado = saldocobrado;
        this.idetalle = idetalle;
    }

    public ListaCobranzaCabeceraEntity(){}
    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNrodocumento() {
        return nrodocumento;
    }

    public void setNrodocumento(String nrodocumento) {
        this.nrodocumento = nrodocumento;
    }

    public String getSaldocobrado() {
        return saldocobrado;
    }

    public void setSaldocobrado(String saldocobrado) {
        this.saldocobrado = saldocobrado;
    }

    public int getIdetalle() {
        return idetalle;
    }

    public void setIdetalle(int idetalle) {
        this.idetalle = idetalle;
    }
}

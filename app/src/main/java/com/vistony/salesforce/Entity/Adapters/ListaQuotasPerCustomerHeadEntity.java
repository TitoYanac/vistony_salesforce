package com.vistony.salesforce.Entity.Adapters;

public class ListaQuotasPerCustomerHeadEntity {
    public String condicionpago;
    public String tramo;
    public String saldo;
    public String cuotas;

    public ListaQuotasPerCustomerHeadEntity(String condicionpago, String tramo, String saldo, String cuotas) {
        this.condicionpago = condicionpago;
        this.tramo = tramo;
        this.saldo = saldo;
        this.cuotas = cuotas;
    }

    public ListaQuotasPerCustomerHeadEntity() {

    }

    public String getCondicionpago() {
        return condicionpago;
    }

    public void setCondicionpago(String condicionpago) {
        this.condicionpago = condicionpago;
    }

    public String getTramo() {
        return tramo;
    }

    public void setTramo(String tramo) {
        this.tramo = tramo;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getCuotas() {
        return cuotas;
    }

    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
    }
}

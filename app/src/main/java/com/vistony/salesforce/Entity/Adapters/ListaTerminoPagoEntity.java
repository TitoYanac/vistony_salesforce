package com.vistony.salesforce.Entity.Adapters;

public class ListaTerminoPagoEntity {
    public String terminopago_id;
    public String terminopago;
    //public String listaprecio_id;
    public String contado;

    public ListaTerminoPagoEntity(String terminopago_id, String terminopago, String contado) {
        this.terminopago_id = terminopago_id;
        this.terminopago = terminopago;
        this.contado = contado;
    }

    public String getTerminopago_id() {
        return terminopago_id;
    }

    public void setTerminopago_id(String terminopago_id) {
        this.terminopago_id = terminopago_id;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
    }

    public String getContado() {
        return contado;
    }

    public void setContado(String contado) {
        this.contado = contado;
    }
}

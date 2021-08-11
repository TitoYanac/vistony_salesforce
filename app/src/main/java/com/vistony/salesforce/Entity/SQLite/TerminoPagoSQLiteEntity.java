package com.vistony.salesforce.Entity.SQLite;

public class TerminoPagoSQLiteEntity {
    public String terminopago_id;
    public String compania_id;
    public String terminopago;
    //public String listaprecio_id;
    public String contado;
    public String dias_vencimiento;

    public TerminoPagoSQLiteEntity(String terminopago_id, String compania_id, String terminopago, String contado, String dias_vencimiento) {
        this.terminopago_id = terminopago_id;
        this.compania_id = compania_id;
        this.terminopago = terminopago;
        this.contado = contado;
        this.dias_vencimiento = dias_vencimiento;
    }

    public TerminoPagoSQLiteEntity() {
    }

    public String getDias_vencimiento() {
        return dias_vencimiento;
    }

    public void setDias_vencimiento(String dias_vencimiento) {
        this.dias_vencimiento = dias_vencimiento;
    }

    public String getContado() {
        return contado;
    }

    public void setContado(String contado) {
        this.contado = contado;
    }

    public String getTerminopago_id() {
        return terminopago_id;
    }

    public void setTerminopago_id(String terminopago_id) {
        this.terminopago_id = terminopago_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
    }
}

package com.vistony.salesforce.Entity.Adapters;

public class ListaHojaDespachoEntity {

    public String nombrecliente;
    public String direccion;
    public String nrofactura;
    public String saldo;
    public String nombrefuerzatrabajo;
    public String terminopago;
    public String cliente_id;
    public String domembarque_id;
    public boolean chkupdatedispatch;

    public ListaHojaDespachoEntity(
            String nombrecliente, String direccion, String nrofactura, String saldo, String nombrefuerzatrabajo,
            String terminopago, String cliente_id, String domembarque_id,boolean chkupdatedispatch) {
        this.nombrecliente = nombrecliente;
        this.direccion = direccion;
        this.nrofactura = nrofactura;
        this.saldo = saldo;
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
        this.terminopago = terminopago;
        this.cliente_id = cliente_id;
        this.domembarque_id = domembarque_id;
        this.chkupdatedispatch = chkupdatedispatch;
    }

    public ListaHojaDespachoEntity() {

    }

    public boolean isChkupdatedispatch() {
        return chkupdatedispatch;
    }

    public void setChkupdatedispatch(boolean chkupdatedispatch) {
        this.chkupdatedispatch = chkupdatedispatch;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembarque_id) {
        this.domembarque_id = domembarque_id;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
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

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getNombrefuerzatrabajo() {
        return nombrefuerzatrabajo;
    }

    public void setNombrefuerzatrabajo(String nombrefuerzatrabajo) {
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }
}

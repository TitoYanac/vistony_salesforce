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
    public String control_id;
    public String item_id;
    public boolean chkvisitsectionstart;
    public boolean chkvisitsectionend;
    public String entrega;
    public boolean chkcollection;
    public String estado;
    public String ocurrencia;
    public String latitude;
    public String longitude;

    public ListaHojaDespachoEntity(
            String nombrecliente, String direccion, String nrofactura, String saldo, String nombrefuerzatrabajo,
            String terminopago, String cliente_id, String domembarque_id,boolean chkupdatedispatch,
            String control_id,String item_id,boolean chkvisitsectionstart,boolean chkvisitsectionend,
            String entrega,boolean chkcollection,String estado,String ocurrencia,
            String latitude,String longitude

    ) {
        this.nombrecliente = nombrecliente;
        this.direccion = direccion;
        this.nrofactura = nrofactura;
        this.saldo = saldo;
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
        this.terminopago = terminopago;
        this.cliente_id = cliente_id;
        this.domembarque_id = domembarque_id;
        this.chkupdatedispatch = chkupdatedispatch;
        this.control_id = control_id;
        this.item_id = item_id;
        this.chkvisitsectionstart = chkvisitsectionstart;
        this.chkvisitsectionend = chkvisitsectionend;
        this.entrega = entrega;
        this.chkcollection = chkcollection;
        this.estado = estado;
        this.ocurrencia = ocurrencia;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public ListaHojaDespachoEntity() {

    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getOcurrencia() {
        return ocurrencia;
    }

    public void setOcurrencia(String ocurrencia) {
        this.ocurrencia = ocurrencia;
    }

    public boolean isChkcollection() {
        return chkcollection;
    }

    public void setChkcollection(boolean chkcollection) {
        this.chkcollection = chkcollection;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public boolean isChkvisitsectionstart() {
        return chkvisitsectionstart;
    }

    public void setChkvisitsectionstart(boolean chkvisitsectionstart) {
        this.chkvisitsectionstart = chkvisitsectionstart;
    }

    public boolean isChkvisitsectionend() {
        return chkvisitsectionend;
    }

    public void setChkvisitsectionend(boolean chkvisitsectionend) {
        this.chkvisitsectionend = chkvisitsectionend;
    }

    public String getControl_id() {
        return control_id;
    }

    public void setControl_id(String control_id) {
        this.control_id = control_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
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

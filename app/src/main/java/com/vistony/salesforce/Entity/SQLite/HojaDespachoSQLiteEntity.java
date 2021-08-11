package com.vistony.salesforce.Entity.SQLite;

public class HojaDespachoSQLiteEntity {
    public String compania_id;
    public String control_id;
    public String item_id;
    public String cliente_id;
    public String documento_id;
    public String empaque_id;
    public String nroempaque;
    public String nrofactura;
    public String estado;
    public String fuerzatrabajo_id;
    public String nombrefuerzatrabajo;
    public String domembarque_id;
    public String direccion;
    public String saldo;
    public String nombrecliente;

    public HojaDespachoSQLiteEntity(String compania_id, String control_id, String item_id, String cliente_id, String documento_id, String empaque_id, String nroempaque, String nrofactura, String estado, String fuerzatrabajo_id, String nombrefuerzatrabajo, String domembarque_id, String direccion, String saldo, String nombrecliente) {
        this.compania_id = compania_id;
        this.control_id = control_id;
        this.item_id = item_id;
        this.cliente_id = cliente_id;
        this.documento_id = documento_id;
        this.empaque_id = empaque_id;
        this.nroempaque = nroempaque;
        this.nrofactura = nrofactura;
        this.estado = estado;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
        this.domembarque_id = domembarque_id;
        this.direccion = direccion;
        this.saldo = saldo;
        this.nombrecliente = nombrecliente;
    }

    public HojaDespachoSQLiteEntity() {

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

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
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

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getEmpaque_id() {
        return empaque_id;
    }

    public void setEmpaque_id(String empaque_id) {
        this.empaque_id = empaque_id;
    }

    public String getNroempaque() {
        return nroempaque;
    }

    public void setNroempaque(String nroempaque) {
        this.nroempaque = nroempaque;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public String getNombrefuerzatrabajo() {
        return nombrefuerzatrabajo;
    }

    public void setNombrefuerzatrabajo(String nombrefuerzatrabajo) {
        this.nombrefuerzatrabajo = nombrefuerzatrabajo;
    }
}

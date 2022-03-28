package com.vistony.salesforce.Entity.SQLite;

public class DocumentoDeudaSQLiteEntity {
    public String documento_id;
    public String documento_entry;
    public String compania_id;
    public String cliente_id;
    public String fuerzatrabajo_id;
    public String domembarque_id;
    public String fechaemision;
    public String fechavencimiento;
    public String nrofactura;
    public String moneda;
    public String importefactura;
    public String saldo;
    public String saldo_sin_procesar;
    public String pymntgroup;

    public DocumentoDeudaSQLiteEntity(String documento_id,String documento_entry, String compania_id, String cliente_id, String domembarque_id, String nrofactura, String fuerzatrabajo_id, String fechaemision, String fechavencimiento, String moneda, String importefactura, String saldo, String saldo_sin_procesar,String pymntgroup) {
        this.documento_id = documento_id;
        this.documento_entry = documento_entry;
        this.compania_id = compania_id;
        this.cliente_id = cliente_id;
        this.nrofactura = nrofactura;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.domembarque_id = domembarque_id;
        this.fechaemision = fechaemision;
        this.fechavencimiento = fechavencimiento;
        this.moneda = moneda;
        this.importefactura = importefactura;
        this.saldo = saldo;
        this.saldo_sin_procesar = saldo_sin_procesar;
        this.pymntgroup = pymntgroup;

    }

    public DocumentoDeudaSQLiteEntity()
    {
    }

    public String getPymntgroup() {
        return pymntgroup;
    }

    public void setPymntgroup(String pymntgroup) {
        this.pymntgroup = pymntgroup;
    }

    public String getDocumento_entry() {
        return documento_entry;
    }

    public void setDocumento_entry(String documento_entry) {
        this.documento_entry = documento_entry;
    }

    public String getDomembarque_id() {
        return domembarque_id;
    }

    public String getDomembaque_id() {
        return domembarque_id;
    }

    public void setDomembarque_id(String domembaque_id) {
        this.domembarque_id = domembaque_id;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNrofactura() {
        return nrofactura;
    }

    public void setNrofactura(String nrofactura) {
        this.nrofactura = nrofactura;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(String fechaemision) {
        this.fechaemision = fechaemision;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getImportefactura() {
        return importefactura;
    }

    public void setImportefactura(String importefactura) {
        this.importefactura = importefactura;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getSaldo_sin_procesar() {
        return saldo_sin_procesar;
    }

    public void setSaldo_sin_procesar(String saldo_sin_procesar) {
        this.saldo_sin_procesar = saldo_sin_procesar;
    }
}

package com.vistony.salesforce.Entity.Adapters;

public class ListaSeguimientoFacturasEntity {
    public String ordenERP_id;
    public String documento_id;
    public String legalnumber;
    public String cliente_id;
    public String rucdni;
    public String nombrecliente;
    public String montodocumento;
    public String nombrechofer;
    public String fechaprogramacion;
    public String estadodespacho;

    public ListaSeguimientoFacturasEntity(String ordenERP_id, String documento_id, String legalnumber, String cliente_id, String rucdni, String nombrecliente, String montodocumento, String nombrechofer, String fechaprogramacion
            , String estadodespacho
    ) {
        this.ordenERP_id = ordenERP_id;
        this.documento_id = documento_id;
        this.legalnumber = legalnumber;
        this.cliente_id = cliente_id;
        this.rucdni = rucdni;
        this.nombrecliente = nombrecliente;
        this.montodocumento = montodocumento;
        this.nombrechofer = nombrechofer;
        this.fechaprogramacion = fechaprogramacion;
        this.estadodespacho = estadodespacho;
    }

    public String getOrdenERP_id() {
        return ordenERP_id;
    }

    public void setOrdenERP_id(String ordenERP_id) {
        this.ordenERP_id = ordenERP_id;
    }

    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public String getLegalnumber() {
        return legalnumber;
    }

    public void setLegalnumber(String legalnumber) {
        this.legalnumber = legalnumber;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getMontodocumento() {
        return montodocumento;
    }

    public void setMontodocumento(String montodocumento) {
        this.montodocumento = montodocumento;
    }

    public String getNombrechofer() {
        return nombrechofer;
    }

    public void setNombrechofer(String nombrechofer) {
        this.nombrechofer = nombrechofer;
    }

    public String getFechaprogramacion() {
        return fechaprogramacion;
    }

    public void setFechaprogramacion(String fechaprogramacion) {
        this.fechaprogramacion = fechaprogramacion;
    }

    public String getEstadodespacho() {
        return estadodespacho;
    }

    public void setEstadodespacho(String estadodespacho) {
        this.estadodespacho = estadodespacho;
    }
}

package com.vistony.salesforce.Entity.Adapters;

public class ListaParametrosEntity {
    public boolean chkparametro;
    public String nombreparametro;
    public String cantidad;
    public String fechacarga;

    public ListaParametrosEntity(boolean chkparametro, String nombreparametro, String cantidad, String fechacarga) {
        this.chkparametro = chkparametro;
        this.nombreparametro = nombreparametro;
        this.cantidad = cantidad;
        this.fechacarga = fechacarga;
    }

    public ListaParametrosEntity() {
    }

    public String getFechacarga() {
        return fechacarga;
    }

    public void setFechacarga(String fechacarga) {
        this.fechacarga = fechacarga;
    }

    public boolean isChkparametro() {
        return chkparametro;
    }

    public void setChkparametro(boolean chkparametro) {
        this.chkparametro = chkparametro;
    }

    public String getNombreparametro() {
        return nombreparametro;
    }

    public void setNombreparametro(String nombreparametro) {
        this.nombreparametro = nombreparametro;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}

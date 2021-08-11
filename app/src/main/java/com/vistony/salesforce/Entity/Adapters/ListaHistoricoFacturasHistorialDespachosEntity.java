package com.vistony.salesforce.Entity.Adapters;

public class ListaHistoricoFacturasHistorialDespachosEntity {
    public String item;
    public String estado;
    public String fecha;
    public String motivo;

    public ListaHistoricoFacturasHistorialDespachosEntity(String item, String estado, String fecha, String motivo) {
        this.item = item;
        this.estado = estado;
        this.fecha = fecha;
        this.motivo = motivo;
    }

    public ListaHistoricoFacturasHistorialDespachosEntity() {
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}

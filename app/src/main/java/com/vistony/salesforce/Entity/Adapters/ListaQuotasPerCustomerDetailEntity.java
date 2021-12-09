package com.vistony.salesforce.Entity.Adapters;

public class ListaQuotasPerCustomerDetailEntity {
    public String cuotas;
    public String vencido;
    public String corriente;
    public String pedido;
    public String total;
    public String fecha;

    public ListaQuotasPerCustomerDetailEntity(String cuotas, String vencido, String corriente, String pedido, String total, String fecha) {
        this.cuotas = cuotas;
        this.vencido = vencido;
        this.corriente = corriente;
        this.pedido = pedido;
        this.total = total;
        this.fecha = fecha;
    }

    public ListaQuotasPerCustomerDetailEntity() {
    }

    public String getCuotas() {
        return cuotas;
    }

    public void setCuotas(String cuotas) {
        this.cuotas = cuotas;
    }

    public String getVencido() {
        return vencido;
    }

    public void setVencido(String vencido) {
        this.vencido = vencido;
    }

    public String getCorriente() {
        return corriente;
    }

    public void setCorriente(String corriente) {
        this.corriente = corriente;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}

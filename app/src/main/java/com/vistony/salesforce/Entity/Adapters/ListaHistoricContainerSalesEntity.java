package com.vistony.salesforce.Entity.Adapters;

public class ListaHistoricContainerSalesEntity {
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String anio;
    public String mes;
    public String variable;
    public String total;

    public ListaHistoricContainerSalesEntity(String fuerzatrabajo_id, String usuario_id, String anio, String mes, String variable, String total) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.anio = anio;
        this.mes = mes;
        this.variable = variable;
        this.total = total;

    }

    public ListaHistoricContainerSalesEntity() {
    }


    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

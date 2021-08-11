package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class TerminoPagoEntity {

    @SerializedName("PymntGroup")
    private String terminopago_id;

    @SerializedName("PymntTerm")
    private String terminopago;

    @SerializedName("Cash")
    private String contado;

    @SerializedName("DueDays")
    private String dias_vencimiento;

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
//public String getListaprecio_id() {return listaprecio_id;}

    //public void setListaprecio_id(String listaprecio_id) {this.listaprecio_id = listaprecio_id;}

    public String getTerminopago_id() {
        return terminopago_id;
    }

    public void setTerminopago_id(String terminopago_id) {
        this.terminopago_id = terminopago_id;
    }

    public String getTerminopago() {
        return terminopago;
    }

    public void setTerminopago(String terminopago) {
        this.terminopago = terminopago;
    }
}

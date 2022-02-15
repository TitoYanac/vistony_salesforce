package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class DetailDispatchSheetEntity {
    @SerializedName("Control_ID")
    private String controlid;

    @SerializedName("Item")
    private String itemid;

    @SerializedName("CardCode")
    private String clienteid;

    @SerializedName("ShipToCode")
    private String domembarque_id;

    @SerializedName("Address")
    private String direccion;

    @SerializedName("InvoiceNum")
    private String factura_id;

    @SerializedName("DeliveryNum")
    private String entrega_id;

    @SerializedName("DeliveryLegalNumber")
    private String entrega;

    @SerializedName("InvoiceLegalNumber")
    private String factura;

    @SerializedName("Balance")
    private String saldo;

    @SerializedName("Status")
    private String estado;

    @SerializedName("SlpCode")
    private String fuerzatrabajo_id;

    @SerializedName("SlpName")
    private String fuerzatrabajo;

    @SerializedName("TerminoPago_ID")
    private String terminoPago_id;

    @SerializedName("PymntGroup")
    private String terminoPago;

    @SerializedName("Weight")
    private String peso;

    @SerializedName("Comentario_Despacho")
    private String comentario_despacho;

    public String getControlid() {
        return controlid;
    }

    public void setControlid(String controlid) {
        this.controlid = controlid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getClienteid() {
        return clienteid;
    }

    public void setClienteid(String clienteid) {
        this.clienteid = clienteid;
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

    public String getFactura_id() {
        return factura_id;
    }

    public void setFactura_id(String factura_id) {
        this.factura_id = factura_id;
    }

    public String getEntrega_id() {
        return entrega_id;
    }

    public void setEntrega_id(String entrega_id) {
        this.entrega_id = entrega_id;
    }

    public String getEntrega() {
        return entrega;
    }

    public void setEntrega(String entrega) {
        this.entrega = entrega;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFuerzatrabajo_id() {
        return fuerzatrabajo_id;
    }

    public void setFuerzatrabajo_id(String fuerzatrabajo_id) {
        this.fuerzatrabajo_id = fuerzatrabajo_id;
    }

    public String getFuerzatrabajo() {
        return fuerzatrabajo;
    }

    public void setFuerzatrabajo(String fuerzatrabajo) {
        this.fuerzatrabajo = fuerzatrabajo;
    }

    public String getTerminoPago_id() {
        return terminoPago_id;
    }

    public void setTerminoPago_id(String terminoPago_id) {
        this.terminoPago_id = terminoPago_id;
    }

    public String getTerminoPago() {
        return terminoPago;
    }

    public void setTerminoPago(String terminoPago) {
        this.terminoPago = terminoPago;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getComentario_despacho() {
        return comentario_despacho;
    }

    public void setComentario_despacho(String comentario_despacho) {
        this.comentario_despacho = comentario_despacho;
    }
}

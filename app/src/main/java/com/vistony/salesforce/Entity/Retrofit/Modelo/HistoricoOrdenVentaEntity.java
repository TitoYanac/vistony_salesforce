package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class HistoricoOrdenVentaEntity {

    @SerializedName("DocNum")
    private String docnum;

    @SerializedName("SalesOrderID")
    private String ordenventa_id;

    @SerializedName("CardCode")
    private String cliente_id;

    @SerializedName("LicTradNum")
    private String rucdni;

    @SerializedName("CardName")
    private String nombrecliente;

    @SerializedName("DocTotal")
    private String montototalorden;

    @SerializedName("ApprovalStatus")
    private String estadoaprobacion;

    @SerializedName("ApprovalCommentary")
    private String comentarioaprobacion;

    @SerializedName("Object")
    private String object;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getDocnum() {
        return docnum;
    }

    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getOrdenventa_id() {
        return ordenventa_id;
    }

    public void setOrdenventa_id(String ordenventa_id) {
        this.ordenventa_id = ordenventa_id;
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

    public String getMontototalorden() {
        return montototalorden;
    }

    public void setMontototalorden(String montototalorden) {
        this.montototalorden = montototalorden;
    }

    public String getEstadoaprobacion() {
        return estadoaprobacion;
    }

    public void setEstadoaprobacion(String estadoaprobacion) {
        this.estadoaprobacion = estadoaprobacion;
    }

    public String getComentarioaprobacion() {
        return comentarioaprobacion;
    }

    public void setComentarioaprobacion(String comentarioaprobacion) {
        this.comentarioaprobacion = comentarioaprobacion;
    }
}

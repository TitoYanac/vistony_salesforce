package com.vistony.salesforce.Entity.Adapters;

import java.io.Serializable;

public class ListaHistoricoOrdenVentaEntity implements Serializable {
    public String ordenventa_erp_id;
    public String cliente_id;
    public String rucdni;
    public String nombrecliente;
    public String montototalorden;
    public String estadoaprobacion;
    public String comentarioaprobacion;
    public String ordenventa_id;
    public boolean recepcionERPOV;
    public String comentariows;
    public boolean envioERPOV;
    public String docnum;

    public ListaHistoricoOrdenVentaEntity(String ordenventa_erp_id, String cliente_id, String rucdni, String nombrecliente, String montototalorden, String estadoaprobacion, String comentarioaprobacion, String ordenventa_id, boolean recepcionERPOV, String comentariows, boolean envioERPOV, String docnum) {
        this.ordenventa_erp_id = ordenventa_erp_id;
        this.cliente_id = cliente_id;
        this.rucdni = rucdni;
        this.nombrecliente = nombrecliente;
        this.montototalorden = montototalorden;
        this.estadoaprobacion = estadoaprobacion;
        this.comentarioaprobacion = comentarioaprobacion;
        this.ordenventa_id = ordenventa_id;
        this.recepcionERPOV = recepcionERPOV;
        this.comentariows = comentariows;
        this.envioERPOV = envioERPOV;
        this.docnum = docnum;
    }

    public ListaHistoricoOrdenVentaEntity() {
    }

    public String getDocnum() {
        return docnum;
    }

    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getComentariows() {
        return comentariows;
    }

    public void setComentariows(String comentariows) {
        this.comentariows = comentariows;
    }

    public boolean isEnvioERPOV() {
        return envioERPOV;
    }

    public void setEnvioERPOV(boolean envioERPOV) {
        this.envioERPOV = envioERPOV;
    }

    public boolean isRecepcionERPOV() {
        return recepcionERPOV;
    }

    public void setRecepcionERPOV(boolean recepcionERPOV) {
        this.recepcionERPOV = recepcionERPOV;
    }

    public String getOrdenventa_id() {
        return ordenventa_id;
    }

    public void setOrdenventa_id(String ordenventa_id) {
        this.ordenventa_id = ordenventa_id;
    }

    public String getOrdenventa_erp_id() {
        return ordenventa_erp_id;
    }

    public void setOrdenventa_erp_id(String ordenventa_erp_id) {
        this.ordenventa_erp_id = ordenventa_erp_id;
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

package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EscColoursCEntity {
    private String compania_id;

    private String fuerzatrabajo_id;

    private String usuario_id;

    @SerializedName("Code")
    private String id;

    @SerializedName("Description")
    private String scale;

    @SerializedName("Status")
    private String status;

    @SerializedName("Detail")
    private List<EscColoursDEntity> escColoursDEntityList;

    public List<EscColoursDEntity> getEscColoursDEntityList() {
        return escColoursDEntityList;
    }

    public void setEscColoursDEntityList(List<EscColoursDEntity> escColoursDEntityList) {
        this.escColoursDEntityList = escColoursDEntityList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
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
}

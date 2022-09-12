package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class TypeDispatchEntity {
    @SerializedName("Value")
    public String typedispatch_id;

    @SerializedName("Dscription")
    public String typedispatch;

    public String compania_id;

    public String fuerzatrabajo_id;

    public String usuario_id;

    @SerializedName("Flag")
    public String statusupdate;

    public String getStatusupdate() {
        return statusupdate;
    }

    public void setStatusupdate(String statusupdate) {
        this.statusupdate = statusupdate;
    }

    public String getTypedispatch_id() {
        return typedispatch_id;
    }

    public void setTypedispatch_id(String typedispatch_id) {
        this.typedispatch_id = typedispatch_id;
    }

    public String getTypedispatch() {
        return typedispatch;
    }

    public void setTypedispatch(String typedispatch) {
        this.typedispatch = typedispatch;
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

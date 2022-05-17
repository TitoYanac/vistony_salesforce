package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class ReasonDispatchEntity {
    @SerializedName("Value")
    public String reasondispatch_id;

    @SerializedName("Dscription")
    public String reasondispatch;

    @SerializedName("typedispatch_id")
    public String typedispatch_id;

    public String compania_id;

    public String fuerzatrabajo_id;

    public String usuario_id;

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

    public String getReasondispatch_id() {
        return reasondispatch_id;
    }

    public void setReasondispatch_id(String reasondispatch_id) {
        this.reasondispatch_id = reasondispatch_id;
    }

    public String getReasondispatch() {
        return reasondispatch;
    }

    public void setReasondispatch(String reasondispatch) {
        this.reasondispatch = reasondispatch;
    }

    public String getTypedispatch_id() {
        return typedispatch_id;
    }

    public void setTypedispatch_id(String typedispatch_id) {
        this.typedispatch_id = typedispatch_id;
    }
}

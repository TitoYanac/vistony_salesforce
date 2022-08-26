package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class QuoteEffectivenessEntity {
    private String compania_id;

    private String fuerzatrabajo_id;

    private String usuario_id;

    @SerializedName("Code")
    private String code;

    @SerializedName("Tipo")
    private String type;

    @SerializedName("CuotaDia")
    private String quote;

    @SerializedName("CuotaDiaUOM")
    private String umd;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }
}

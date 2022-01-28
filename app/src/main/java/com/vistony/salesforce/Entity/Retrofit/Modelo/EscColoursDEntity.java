package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class EscColoursDEntity {
    private String compania_id;

    private String fuerzatrabajo_id;

    private String usuario_id;

    @SerializedName("Id_esc_colours_c")
    private String id_esc_colours_c;

    @SerializedName("Id")
    private String id;

    @SerializedName("Rangemin")
    private String rangemin;

    @SerializedName("Rangemax")
    private String rangemax;

    @SerializedName("Colourmin")
    private String colourmin;

    @SerializedName("Colourmax")
    private String colourmax;

    @SerializedName("Degrade")
    private String degrade;

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

    public String getId_esc_colours_c() {
        return id_esc_colours_c;
    }

    public void setId_esc_colours_c(String id_esc_colours_c) {
        this.id_esc_colours_c = id_esc_colours_c;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRangemin() {
        return rangemin;
    }

    public void setRangemin(String rangemin) {
        this.rangemin = rangemin;
    }

    public String getRangemax() {
        return rangemax;
    }

    public void setRangemax(String rangemax) {
        this.rangemax = rangemax;
    }

    public String getColourmin() {
        return colourmin;
    }

    public void setColourmin(String colourmin) {
        this.colourmin = colourmin;
    }

    public String getColourmax() {
        return colourmax;
    }

    public void setColourmax(String colourmax) {
        this.colourmax = colourmax;
    }

    public String getDegrade() {
        return degrade;
    }

    public void setDegrade(String degrade) {
        this.degrade = degrade;
    }
}

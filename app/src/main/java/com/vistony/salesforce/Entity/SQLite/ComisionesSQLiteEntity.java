package com.vistony.salesforce.Entity.SQLite;

public class ComisionesSQLiteEntity {
    public String compania_id;
    public String variable;
    public String umd;
    public String avance;
    public String cuota;
    public String porcentajeavance;
    public String fuerzatrabajo_id;
    public String usuario_id;
    public String esc_colours;
    public String hidedata;

    public ComisionesSQLiteEntity(String compania_id, String variable, String umd, String avance, String cuota, String porcentajeavance, String fuerzatrabajo_id, String usuario_id, String esc_colours, String hidedata) {
        this.compania_id = compania_id;
        this.variable = variable;
        this.umd = umd;
        this.avance = avance;
        this.cuota = cuota;
        this.porcentajeavance = porcentajeavance;
        this.fuerzatrabajo_id = fuerzatrabajo_id;
        this.usuario_id = usuario_id;
        this.esc_colours = esc_colours;
        this.hidedata = hidedata;
    }

    public ComisionesSQLiteEntity() {
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

    public String getEsc_colours() {
        return esc_colours;
    }

    public void setEsc_colours(String esc_colours) {
        this.esc_colours = esc_colours;
    }

    public String getHidedata() {
        return hidedata;
    }

    public void setHidedata(String hidedata) {
        this.hidedata = hidedata;
    }

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getUmd() {
        return umd;
    }

    public void setUmd(String umd) {
        this.umd = umd;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getPorcentajeavance() {
        return porcentajeavance;
    }

    public void setPorcentajeavance(String porcentajeavance) {
        this.porcentajeavance = porcentajeavance;
    }
}

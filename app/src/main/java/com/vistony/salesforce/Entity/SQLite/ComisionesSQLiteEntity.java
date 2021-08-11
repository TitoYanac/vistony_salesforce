package com.vistony.salesforce.Entity.SQLite;

public class ComisionesSQLiteEntity {
    public String compania_id;
    public String variable;
    public String umd;
    public String avance;
    public String cuota;
    public String porcentajeavance;

    public ComisionesSQLiteEntity(String compania_id, String variable, String umd, String avance, String cuota, String porcentajeavance) {
        this.compania_id = compania_id;
        this.variable = variable;
        this.umd = umd;
        this.avance = avance;
        this.cuota = cuota;
        this.porcentajeavance = porcentajeavance;
    }

    public ComisionesSQLiteEntity() {
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

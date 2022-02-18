package com.vistony.salesforce.Entity.Adapters;

public class ListaComisionesDetalleEntity {
    public String compania_id;
    public String variable;
    public String umd;
    public String avance;
    public String cuota;
    public String porcentajeavance;
    public String codecolor;
    public String hidedate;


    public ListaComisionesDetalleEntity(String compania_id, String variable, String umd, String avance, String cuota, String porcentajeavance, String codecolor, String hidedate) {
        this.compania_id = compania_id;
        this.variable = variable;
        this.umd = umd;
        this.avance = avance;
        this.cuota = cuota;
        this.porcentajeavance = porcentajeavance;
        this.codecolor = codecolor;
        this.hidedate = hidedate;
    }

    public ListaComisionesDetalleEntity() {
    }

    public String getCodecolor() {
        return codecolor;
    }

    public void setCodecolor(String codecolor) {
        this.codecolor = codecolor;
    }

    public String getHidedate() {
        return hidedate;
    }

    public void setHidedate(String hidedate) {
        this.hidedate = hidedate;
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

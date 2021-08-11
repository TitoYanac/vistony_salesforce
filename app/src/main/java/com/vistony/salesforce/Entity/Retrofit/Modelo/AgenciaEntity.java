package com.vistony.salesforce.Entity.Retrofit.Modelo;

import com.google.gson.annotations.SerializedName;

public class AgenciaEntity {
    @SerializedName("AgencyID")
    private String agencia_id;

    @SerializedName("Agency")
    private String agencia;

    @SerializedName("ZipCode")
    private String ubigeo_id;

    @SerializedName("RUC")
    private String ruc;

    @SerializedName("Direccion")
    private String direccion;

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAgencia_id() {
        return agencia_id;
    }

    public void setAgencia_id(String agencia_id) {
        this.agencia_id = agencia_id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getUbigeo_id() {
        return ubigeo_id;
    }

    public void setUbigeo_id(String ubigeo_id) {
        this.ubigeo_id = ubigeo_id;
    }
}

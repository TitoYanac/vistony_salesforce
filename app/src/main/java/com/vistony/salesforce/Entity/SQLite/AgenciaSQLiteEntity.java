package com.vistony.salesforce.Entity.SQLite;

public class AgenciaSQLiteEntity {
    public String compania_id;
    public String agencia_id;
    public String agencia;
    public String ubigeo_id;
    public String ruc;
    public String direccion;


    public AgenciaSQLiteEntity(String compania_id, String agencia_id, String agencia, String ubigeo_id, String ruc, String direccion) {
        this.compania_id = compania_id;
        this.agencia_id = agencia_id;
        this.agencia = agencia;
        this.ubigeo_id = ubigeo_id;
        this.ruc = ruc;
        this.direccion = direccion;
    }

    public AgenciaSQLiteEntity() {
    }

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

    public String getCompania_id() {
        return compania_id;
    }

    public void setCompania_id(String compania_id) {
        this.compania_id = compania_id;
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

package com.vistony.salesforce.Entity.Adapters;

public class ListaAgenciaEntity {
    public String agencia_id;
    public String agencia;
    public String ubigeo;

    public ListaAgenciaEntity(String agencia_id, String agencia, String ubigeo) {
        this.agencia_id = agencia_id;
        this.agencia = agencia;
        this.ubigeo = ubigeo;
    }

    public ListaAgenciaEntity() {
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

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }
}

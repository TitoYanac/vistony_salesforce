package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ClienteEntity;

import java.util.List;

public class ClienteEntityResponse {
    @SerializedName("Customers")
    //@SerializedName("data")
    private List<ClienteEntity> ClienteEntity;

    //@SerializedName("Seguridad")
    //private List<SeguridadEntity> SeguridadEntity;

    public List<ClienteEntity> getClienteEntity() {
        return ClienteEntity;
    }
    public void setClienteEntity(List<ClienteEntity> ClienteEntity) {
        this.ClienteEntity = ClienteEntity;
    }

/*
    public List<com.vistony.salesforce.Entity.Retrofit.Modelo.SeguridadEntity> getSeguridadEntity() {
        return SeguridadEntity;
    }

    public void setSeguridadEntity(List<com.vistony.salesforce.Entity.Retrofit.Modelo.SeguridadEntity> seguridadEntity) {
        SeguridadEntity = seguridadEntity;
    }

 */
}

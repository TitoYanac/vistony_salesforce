package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ClienteEntity;

import java.util.List;

public class ClienteEntityResponse {
    @SerializedName("Customers")
    private List<ClienteEntity> ClienteEntity;

    public List<ClienteEntity> getClienteEntity() {
        return ClienteEntity;
    }
    public void setClienteEntity(List<ClienteEntity> ClienteEntity) {
        this.ClienteEntity = ClienteEntity;
    }
}

package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DireccionClienteEntity;

import java.util.List;

public class DireccionClienteEntityResponse {
    @SerializedName("Addresses")
    //@SerializedName("data")
    private List<DireccionClienteEntity> DireccionClienteEntity;

    public List<DireccionClienteEntity> getDireccionClienteEntity() {
        return DireccionClienteEntity;
    }

    public void setDireccionClienteEntity(List<DireccionClienteEntity> DireccionClienteEntity) {
        this.DireccionClienteEntity = DireccionClienteEntity;
    }
}

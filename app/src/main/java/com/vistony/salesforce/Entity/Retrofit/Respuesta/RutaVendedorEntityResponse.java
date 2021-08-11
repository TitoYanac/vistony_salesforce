package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.RutaVendedorEntity;

import java.util.List;

public class RutaVendedorEntityResponse {
    @SerializedName("data")
    private List<RutaVendedorEntity> RutaVendedorEntity;

    public List<RutaVendedorEntity> getRutaVendedorEntity() {
        return RutaVendedorEntity;
    }

    public void setRutaVendedorEntity(List<RutaVendedorEntity> RutaVendedorEntity) {
        this.RutaVendedorEntity = RutaVendedorEntity;
    }
}
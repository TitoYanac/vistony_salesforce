package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TerminoPagoEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.UbigeoEntity;

import java.util.List;

public class UbigeoEntityResponse {
    @SerializedName("Ubigeos")
    private List<UbigeoEntity> ubigeoEntity;

    public List<UbigeoEntity> getUbigeoEntity() {
        return ubigeoEntity;
    }

    public void setUbigeoEntity(List<UbigeoEntity> ubigeoEntity) {
        this.ubigeoEntity = ubigeoEntity;
    }
}

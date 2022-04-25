package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;

import java.util.List;

public class TypeDispatchEntityResponse {
    @SerializedName("Obtener_TypeDispatchResult")
    private List<TypeDispatchEntity> typeDispatchEntity;
    public TypeDispatchEntityResponse(List<TypeDispatchEntity> typeDispatchEntity) {
        this.typeDispatchEntity = typeDispatchEntity;
    }
    public List<TypeDispatchEntity> getTypeDispatchEntities() {
        return typeDispatchEntity;
    }

}

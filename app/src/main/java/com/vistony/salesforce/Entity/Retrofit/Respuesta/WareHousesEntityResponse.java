package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.VersionEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WareHousesEntity;

import java.util.List;

public class WareHousesEntityResponse {
    @SerializedName("Obtener_WareHousesResult")

    private List<WareHousesEntity> wareHousesEntities;

    public WareHousesEntityResponse(List<WareHousesEntity> wareHousesEntities) {
        this.wareHousesEntities = wareHousesEntities;
    }

    public List<WareHousesEntity> getWareHousesEntities() {
        return wareHousesEntities;
    }

}

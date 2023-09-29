package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ObjectEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.WarehouseEntity;

import java.util.List;

public class WarehouseEntityResponse {
    @SerializedName("WarehouseList")
    private List<WarehouseEntity> warehouseEntity;

    public WarehouseEntityResponse (List<WarehouseEntity> warehouseEntity)  {
        this.warehouseEntity = warehouseEntity;
    }

    public List<WarehouseEntity> getWarehouseEntityResponse() {
        return warehouseEntity;
    }
}

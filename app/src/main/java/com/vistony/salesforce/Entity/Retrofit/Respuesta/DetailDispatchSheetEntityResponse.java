package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.DetailDispatchSheetEntity;

import java.util.List;

public class DetailDispatchSheetEntityResponse {
    @SerializedName("Obtener_DespachoDResult")
    private List<DetailDispatchSheetEntity> detailDispatchSheetEntity;

    public List<DetailDispatchSheetEntity> getDetailDispatchSheetEntityEntity() {
        return detailDispatchSheetEntity;
    }

    public void setDetailDispatchSheetEntity(List<DetailDispatchSheetEntity> detailDispatchSheetEntity) {
        this.detailDispatchSheetEntity = detailDispatchSheetEntity;
    }
}

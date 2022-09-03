package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderDispatchSheetEntity;

import java.util.List;

public class HeaderDispatchSheetEntityResponse {
    @SerializedName("Dispatch")
    //@SerializedName("Obtener_DespachoCResult")
    private List<HeaderDispatchSheetEntity> headerDispatchSheetEntity;

    public List<HeaderDispatchSheetEntity> getHeaderDispatchSheetEntity() {
        return headerDispatchSheetEntity;
    }

    public void setHeaderDispatchSheetEntity(List<HeaderDispatchSheetEntity> headerDispatchSheetEntity) {
        this.headerDispatchSheetEntity = headerDispatchSheetEntity;
    }
}

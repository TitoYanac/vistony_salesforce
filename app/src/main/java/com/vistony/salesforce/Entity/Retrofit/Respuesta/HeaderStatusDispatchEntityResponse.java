package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.HeaderStatusDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.StatusDispatchEntity;

import java.util.List;

public class HeaderStatusDispatchEntityResponse {
    @SerializedName("Dispatch")
    private List<HeaderStatusDispatchEntity> headerStatusDispatchEntity;

    public void setHeaderStatusDispatchEntityResponse(List<HeaderStatusDispatchEntity> headerStatusDispatchEntity) {
        this.headerStatusDispatchEntity = headerStatusDispatchEntity;
    }
    public List<HeaderStatusDispatchEntity> getHeaderStatusDispatchEntityResponse() {
        return headerStatusDispatchEntity;
    }
}

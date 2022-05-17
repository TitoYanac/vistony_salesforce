package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.ReasonDispatchEntity;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TypeDispatchEntity;

import java.util.List;

public class ReasonDispatchEntityResponse {
    @SerializedName("Ocurrencies")
    private List<ReasonDispatchEntity> reasonDispatchEntity;
    public ReasonDispatchEntityResponse(List<ReasonDispatchEntity> reasonDispatchEntity) {
        this.reasonDispatchEntity = reasonDispatchEntity;
    }
    public List<ReasonDispatchEntity> getReasonDispatchEntities() {
        return reasonDispatchEntity;
    }
}

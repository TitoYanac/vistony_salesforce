package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.TerminoPagoEntity;

import java.util.List;

public class TerminoPagoEntityResponse {
    @SerializedName("PaymentTerms")
    private List<TerminoPagoEntity> TerminoPagoEntity;

    public List<TerminoPagoEntity> getTerminoPagoEntity() {
        return TerminoPagoEntity;
    }

    public void setTerminoPagoEntity(List<TerminoPagoEntity> TerminoPagoEntity) {
        this.TerminoPagoEntity = TerminoPagoEntity;
    }
}

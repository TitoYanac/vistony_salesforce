package com.vistony.salesforce.Entity.Retrofit.Respuesta;

import com.google.gson.annotations.SerializedName;
import com.vistony.salesforce.Entity.Retrofit.Modelo.MotivoVisitaEntity;

import java.util.List;

public class MotivoVisitaEntityResponse {
    @SerializedName("Reasons")
    private List<MotivoVisitaEntity> motivoVisitaEntity;

    public MotivoVisitaEntityResponse(List<MotivoVisitaEntity> motivoVisitaEntity)  {
        this.motivoVisitaEntity = motivoVisitaEntity;
    }

    public List<MotivoVisitaEntity> getMotivoVisitaEntity() {
        return motivoVisitaEntity;
    }
}
